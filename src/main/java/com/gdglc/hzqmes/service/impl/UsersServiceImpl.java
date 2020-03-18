package com.gdglc.hzqmes.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.PageForm;
import com.gdglc.hzqmes.common.form.UsersForm;
import com.gdglc.hzqmes.dao.UserRoleDao;
import com.gdglc.hzqmes.dao.UsersDao;
import com.gdglc.hzqmes.enums.ResultEnum;
import com.gdglc.hzqmes.exception.BusinessException;
import com.gdglc.hzqmes.po.UserRole;
import com.gdglc.hzqmes.po.Users;
import com.gdglc.hzqmes.security.exceptions.InactiveUserException;
import com.gdglc.hzqmes.security.model.UserContext;
import com.gdglc.hzqmes.service.UsersService;
import com.gdglc.hzqmes.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.springframework.transaction.annotation.Propagation.REQUIRED;


/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author gdglc
 * @since 2019-01-21
 */
@Service
@Slf4j
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersDao usersDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Optional<Users> getUserByUsername(String username) {
        return Optional.ofNullable(usersDao.selectUserByUsername(username));
    }


    @Override
    public IPage<Users> getByCondition(UsersForm usersForm) {
        PageForm pageForm = usersForm.getPageForm();
        IPage iPage =PageUtil.initPage(pageForm);
        return usersDao.selectByCondition(iPage, usersForm);
    }


    @Override
    public Users selectUserById(Integer id) {
        return usersDao.selectUserById(id);
    }


    @Override
    public Integer checkUserByUsername(Integer id,String username) {
        return usersDao.checkUserByUsername(id,username);
    }


    @Override
    public Integer checkUserEmailAndMobile(Integer id, String str) {
        return usersDao.checkUserEmailAndMobile(id,str);
    }

    @Override
    @Transactional(propagation= REQUIRED, rollbackFor = Exception.class)
    public void updateUserInfoById(Users users, List<Integer> roleIdList) {
        //清空redis缓存的用户信息
        clearUserIonfoInRedis(null, users.getUsername());
        users.setPassword(StringUtils.trimToNull(users.getPassword()));
        usersDao.updateById(users);
        // 获取用户的角色列表，删除用户的角色列表后重新插入用户角色
        List<UserRole> userRoles = userRoleDao.selectList(new QueryWrapper<>(new UserRole(users.getId())));
        if (userRoles !=null && !userRoles.isEmpty()) {
            userRoles.forEach(userRole -> userRoleDao.deleteById(userRole.getId()));
        }
        // 如果没有查询到角色，则直接插入
        for (Integer roleId : roleIdList) {
            userRoleDao.insert(new UserRole(users.getId(), roleId));
        }
    }

    @Override
    public void updateUserLoginTimeAndIp(Users users) {
        usersDao.updateUserLoginTimeAndIp(users);
        clearUserIonfoInRedis(null, users.getUsername());
    }

    @Override
    @Transactional(propagation= REQUIRED, rollbackFor = Exception.class)
    public void addUser(Users users, List<Integer> roleIdList) {
        Optional<Users> usersOptional = getUserByUsername(users.getUsername());
        if (!usersOptional.isPresent()) {
            usersDao.insert(users);
            for (Integer roleId : roleIdList) {
                userRoleDao.insert(new UserRole(users.getId(), roleId));
            }
        } else {
            throw new BusinessException(ResultEnum.USERNAME_ALREADY_EXISTS);
        }
    }

    @Override
    public void updateUserStatusById(Integer id, Integer status) {
        usersDao.updateUserStatusById(new Integer[]{id},status);
        clearUserIonfoInRedis(id, null);
    }


    @Override
    public void updateUserStatusByIds(Integer[] ids,Integer status) {
        List<String> usernameKeys = new ArrayList<>();
        List<String> idKeys = new ArrayList<>();
      try{
          for (Integer id: ids) {
              String username = redisTemplate.opsForValue().get(id + "_username");
              if (StringUtils.isNotBlank(username)) {
                  usernameKeys.add(username + "::userInfo");
                  idKeys.add(id + "_username");
              }
          }
          //批量清空redis缓存的用户信息
          if(!usernameKeys.isEmpty()){
              redisTemplate.delete(usernameKeys);
          }
          if(!idKeys.isEmpty()) {
              redisTemplate.delete(idKeys);
          }
      }catch (Exception e) {
          log.error("redis 清空用户信息失败",e);
      }
        usersDao.updateUserStatusById(ids,status);
    }

    /**
     *
     *获取当前用户信息
     * @return Appuser
     */
    @Override
    public Users findUserInContext() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();
        if (auth.getPrincipal() != null && auth.getPrincipal() instanceof UserContext) {
            UserContext userContext = (UserContext) auth.getPrincipal();
            String key = userContext.getUsername() + "::userInfo";
            String userInfoString = redisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(userInfoString)) {
                return JSON.parseObject(userInfoString, Users.class);
            }
            Users users = usersDao.selectUserByUsername(userContext.getUsername());
            if(users == null) {
                throw new UsernameNotFoundException("用户不存在");
            } else if (users.getStatus() == -1) {
                throw new InactiveUserException("用户未被激活，请联系管理员");
            } else {
                userInfoString = JSON.toJSONString(users);
                //保存到redis，有效时间24小时
                redisTemplate.opsForValue().set(key,userInfoString, 24, TimeUnit.HOURS);
                redisTemplate.opsForValue().set(users.getId() + "_username", userContext.getUsername(), 24, TimeUnit.HOURS);
                return users;
            }
        }
        return null;
    }

    /**
     *  清空redis缓存的用户信息
     * @param userId
     */
    private void clearUserIonfoInRedis(Integer userId, String userName) {
        try{
            String username =null;
            if(userId !=null) {
                username = redisTemplate.opsForValue().get(userId + "_username");
            } else if (userName!=null){
                username = userName;
            }
            //清空redis缓存的用户信息
            if (StringUtils.isNotBlank(username)) {
                redisTemplate.delete(username + "::userInfo");
                if(userId !=null) {
                    redisTemplate.delete(userId + "_username");
                }
            }
        }catch (Exception e) {
            log.error("redis 清空用户信息失败",e);
        }
    }
}

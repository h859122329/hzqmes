package com.gdglc.hzqmes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.UsersForm;
import com.gdglc.hzqmes.po.Users;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author gdglc
 * @since 2019-01-21
 */
public interface UsersService {
    /**
     * 根据用户名获取用户信息，用于登录验证
     * @param username
     * @return
     */
    Optional<Users> getUserByUsername(String username);

    /**
     * 根据id 获取用户信息
     * @param id
     * @return
     */
    Users selectUserById(Integer id);


    /**
     * 检查 username 在数据库中是否已存在
     * @param id
     * @param username
     * @return
     */
    Integer checkUserByUsername(Integer id,String username);
    /**
     * 根据条件分页查询
     * @param usersForm
     * @return
     */
    IPage<Users> getByCondition(UsersForm usersForm);

    /**
     * 根据id 修改用户信息
     * @param users
     */
    void updateUserInfoById(Users users, List<Integer> roleIdList);

    /**
     * 添加用户
     * @param users
     */
    void addUser(Users users, List<Integer> roleIdList);


    /**
     * 根据用户ID修改用户的状态
     * @param id
     * @param status
     */
    void updateUserStatusById( Integer id,  Integer status);


    /**
     * 根据id  把status修改成1 不做物理删除
     * @param ids
     */
    void updateUserStatusByIds(Integer[] ids,Integer status);


    /**
     *
     *获取当前用户信息
     * @return Appuser
     */
    Users findUserInContext();


    /**
     * 判断邮箱 或手机号是否 存在
     * @param id
     * @param str
     * @return
     */
    Integer checkUserEmailAndMobile(Integer id,String str);



    /**
     * 更新用户登录时间
     * @param users
     */
    void updateUserLoginTimeAndIp(Users users);

}

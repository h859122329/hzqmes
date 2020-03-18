package com.gdglc.hzqmes.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.UsersForm;
import com.gdglc.hzqmes.po.Users;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author gdglc
 * @since 2019-01-21
 */
public interface UsersDao extends BaseMapper<Users> {
    /**
     * 根据用户名获取用户信息，用于登录验证
     * @param username
     * @return
     */
    Users selectUserByUsername(String username);

    /**
     * 检查 username 在数据库中是否已存在
     * @param id
     * @param username
     * @return
     */
    Integer checkUserByUsername(@Param("id") Integer id, @Param("username") String username);

    /**
     * 根据条件分页查询
     * @param page
     * @param usersForm
     * @return
     */
    IPage<Users> selectByCondition(IPage page, @Param("usersForm") UsersForm usersForm);


    /**
     * 根据用户ID修改用户的状态
     * @param ids
     * @param status
     */
    void updateUserStatusById(@Param("ids") Integer[] ids, @Param("status") Integer status);

    /**
     * 根据id 获取用户信息
     * @param id
     * @return
     */
    Users selectUserById(Integer id);


    /**
     * 判断邮箱 或手机号是否 存在
     * @param id
     * @param str
     * @return
     */
    Integer checkUserEmailAndMobile(@Param("id") Integer id, @Param("str") String str);

    /**
     * 更新用户的登录时间和登录IP
     * @param users
     */
    void updateUserLoginTimeAndIp(Users users);
}

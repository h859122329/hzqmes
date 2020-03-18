package com.gdglc.hzqmes.security.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author:ZhongGuoce
 * @date:2019-01-22
 * @time:18:23
 */
@Data
public class SystemUser {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名邮件或者手机，用于登陆用
     */
    @TableField("username")
    private String username;
    /**
     * 登录密码
     */
    @JsonIgnore
    @TableField("password")
    private String password;

    /**
     * 头像
     */
    private String face;

    /**
     * 昵称
     */
    private String nickname;

    @TableField(exist = false)
    private Boolean isBackground;

    public void setPasswords(String password) {
        this.password = password;
    }

    public SystemUser() {
    }

    public SystemUser(Integer id) {
        this.id = id;
    }
}

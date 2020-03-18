package com.gdglc.hzqmes.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Data
@ToString
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名邮件或者手机，用于登陆用
     */
    @TableField("username")
    private String username;

    /**
     * 0:禁止访问 1:游客 2:注册用户 3:学生 4:咨询师 5:班主任 6:学校管理员 7:平台管理员
     */
    @TableField("user_role")
    private Integer userRole;

    @TableField("password")
    private String password;

    @TableField("face")
    private String face;

    /**
     * 为了兼容DISCUZ 设立的用户名存储的字段
     */
    @TableField("ext0")
    private String ext0;

    @TableField("nickname")
    private String nickname;

    @TableField("reg_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regTime;

    @TableField("reg_ip")
    private String regIp;

    @TableField("last_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastTime;

    @TableField("last_ip")
    private String lastIp;

    /**
     * -1代表需要激活   0 代表正常  1 代表删除
     */
    @TableField("status")
    private Integer status;

    /**
     * 认证的邮件
     */
    @TableField("email")
    private String email;

    /**
     * 认证的手机号码
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 性别 0:女 1:男
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 生日
     */
    @TableField("birth_day")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    @TableField("token")
    private String token;

    @TableField(exist = false)
    private List<Role> roles;
}

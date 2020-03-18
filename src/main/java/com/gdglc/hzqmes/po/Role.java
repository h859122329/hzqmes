package com.gdglc.hzqmes.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户组
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    @TableField("role_name")
    private String roleName;

    @TableField("vip")
    private Integer vip;

    @TableField("sort_order")
    private Integer sortOrder;

    public String authority() {
        return "ROLE_" + this.roleName;
    }
}

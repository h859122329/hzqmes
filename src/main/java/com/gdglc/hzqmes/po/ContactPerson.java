package com.gdglc.hzqmes.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * <p>
 * 客户联系人
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Data
public class ContactPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联客户的ID
     */
    @TableField("customer_id")
    private Integer customerId;

    /**
     * 联系人姓名
     */
    @NotEmpty(message = "需指定联系人姓名")
    @TableField("contact_name")
    private String contactName;

    /**
     * 联系人类型，参考code_static CONTYPE
     */
    @NotEmpty(message = "需指定联系人类型")
    @TableField("contact_type")
    private String contactType;

    /**
     * 简介
     */
    @TableField("brief")
    private String brief;

    /**
     * 详细描述
     */
    @TableField("description")
    private String description;


}

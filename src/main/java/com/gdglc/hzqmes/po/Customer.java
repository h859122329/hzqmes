package com.gdglc.hzqmes.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Data
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 记录状态  -1:草稿  0:作废  1: 正常/启用
     */
    @TableField("active_flag")
    private Integer activeFlag;


    /**
     * 关联用户的ID
     */
    @TableField("user_id")
    private Integer userId;

    @TableField(exist = false)
    private Users customerUser;

    @NotEmpty(message = "客户名称不能为空")
    @TableField("customer_na" +
            "me")
    private String customerName;

    @TableField("remark")
    private String remark;

    /**
     * 是否含税 0: 否 1:是
     */
    @NotNull
    @TableField("is_tax_free")
    private Boolean isTaxFree;

    @TableField("tax_rate")
    private BigDecimal taxRate;

    /**
     * 0:现金 1:月结 2: 3:
     */
    @TableField("settle_type")
    private Integer settleType;

    /**
     * 每个月的结算日(如果月结)
     */
    @TableField("day_of_settle")
    private Integer dayOfSettle;

    /**
     * 客户登记 A~F
     */
    @NotEmpty(message = "客户级别不得为空")
    @TableField("customer_level")
    private String customerLevel;

    /**
     * 是否正在合作
     */
    @TableField("is_cooperating")
    private Integer isCooperating;

    /**
     * 客户登记日（创建日）
     */
    @TableField("reg_date")
    @JsonFormat(timezone = "GMT-8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate regDate;

    /**
     * 记录创建时间
     */
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    /**
     * 创建者
     */
    @TableField("create_uid")
    private Integer createUid;

    @TableField(exist = false)
    private Users createUser;

}

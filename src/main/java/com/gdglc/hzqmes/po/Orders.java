package com.gdglc.hzqmes.po;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户订单表
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户ID
     */
    @TableField("customer_id")
    private Integer customerId;

    /**
     * 订单号，手动输入
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 交付日期
     */
    @TableField("deliver_date")
    private LocalDate deliverDate;

    /**
     * 下单日期(手动输入)
     */
    @TableField("order_date")
    private LocalDate orderDate;

    /**
     * 订单状态 -1: 草稿  0:已提交  1:已完成 2: 已取消 3:已交付
     */
    @TableField("order_status")
    private Integer orderStatus;

    /**
     * 是否质量要求严格 0: 否 1:是
     */
    @TableField("is_strict_quality")
    private Boolean isStrictQuality;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("create_uid")
    private Integer createUid;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("update_uid")
    private Integer updateUid;


}

package com.gdglc.hzqmes.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 生产指令表
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManufactureInstruction implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单产品id
     */
    @TableField("order_product_id")
    private Integer orderProductId;

    /**
     * 产品id
     */
    @TableField("product_id")
    private Integer productId;

    /**
     * 生产批次号，手动输入，原则上不能重复
     */
    @TableField("batch_no")
    private String batchNo;

    /**
     * 订单数量，个(SKU)
     */
    @TableField("order_quantity")
    private Integer orderQuantity;

    /**
     * 排产数量，个(SKU)
     */
    @TableField("planned_quantity")
    private Integer plannedQuantity;

    /**
     * 实际生产数量，个(SKU)
     */
    @TableField("actual_yield")
    private Integer actualYield;

    /**
     * 所剩数量，个(SKU)
     */
    @TableField("balance")
    private Integer balance;

    /**
     * 订单相关信息，把产品、规格，简要信息整合,形成简要快照信息
     */
    @TableField("brief")
    private String brief;

    /**
     * 计划开始时间
     */
    @TableField("planned_begin_time")
    private LocalDateTime plannedBeginTime;

    /**
     * 计划结束时间
     */
    @TableField("planned_end_time")
    private LocalDateTime plannedEndTime;

    /**
     * 实际开始时间
     */
    @TableField("actual_begin_time")
    private LocalDateTime actualBeginTime;

    /**
     * 实际结束时间
     */
    @TableField("actual_end_time")
    private LocalDateTime actualEndTime;

    /**
     * 负责人
     */
    @TableField("responsible_people")
    private String responsiblePeople;

    /**
     * 指令状态 0:未开始 1:已完成 2:进行中 3:已取消
     */
    @TableField("instruction_status")
    private Integer instructionStatus;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("create_uid")
    private Integer createUid;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("update_uid")
    private Integer updateUid;


}

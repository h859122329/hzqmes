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
 * 生产指令步骤表
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ManufactureInstructionSteps implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 生产指令id
     */
    @TableField("instruction_id")
    private Integer instructionId;

    /**
     * 步骤编码,ref: code_static表 INSTEP
     */
    @TableField("step_code")
    private String stepCode;

    /**
     * 负责人
     */
    @TableField("step_responsible_people")
    private String stepResponsiblePeople;

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
     * 指令步骤状态 0:未开始 1:已完成 2:进行中 3:已取消
     */
    @TableField("step_status")
    private Integer stepStatus;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("create_uid")
    private Integer createUid;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("update_uid")
    private Integer updateUid;


}

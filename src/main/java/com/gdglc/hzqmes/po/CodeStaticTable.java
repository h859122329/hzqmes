package com.gdglc.hzqmes.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Data
public class CodeStaticTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据项代码,如国家-中国: CN
     */
    @TableField("code_id_cd")
    private String codeIdCd;

    /**
     * 表数据项类型编号，如:国家 CTRY
     */
    @TableField("code_type")
    private String codeType;

    /**
     * 表数据项同一类型不懂分组，如咨询师能看到的列表,该字段用CONS标识,秘书用SPVR标识
     */
    @TableField("code_group")
    private String codeGroup;

    /**
     * 数据项简述
     */
    @TableField("code_short_description")
    private String codeShortDescription;

    /**
     * 数据项描述
     */
    @TableField("code_full_desc_text")
    private String codeFullDescText;

    /**
     * 数据项代表的数值，如有需要的场景使用
     */
    @TableField("code_value")
    private Integer codeValue;

    /**
     * 有效标识
     */
    @TableField("code_enable_flag")
    private String codeEnableFlag;

    /**
     * 显示顺序，根据同一组内排序
     */
    @TableField("code_display_order")
    private Integer codeDisplayOrder;


}

package com.gdglc.hzqmes.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 联系方式
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Data
public class ContactNo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 联系人类型，参考code_static CONTNOTP
     */
    @NotNull(message = "需指定联系方式类型")
    @TableField("number_type")
    private String numberType;

    /**
     * 联系方式持有者类型 参考code_static CONTHOLD
     */
    @TableField("item_type")
    private String itemType;

    /**
     * 联系方式持有者ID
     */
    @TableField("item_id")
    private Integer itemId;

    /**
     * 联系方式的值
     */
    @NotEmpty(message = "联系方式不能为空")
    @TableField("content")
    private String content;

}

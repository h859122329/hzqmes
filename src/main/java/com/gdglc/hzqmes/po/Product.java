package com.gdglc.hzqmes.po;

import java.math.BigDecimal;
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
 * 产品表
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 记录状态  -1:草稿  0:作废  1: 正常/启用
     */
    @TableField("active_flag")
    private Integer activeFlag;

    /**
     * 产品名
     */
    @TableField("product_name")
    private String productName;

    /**
     * 产品类型
     */
    @TableField("category_id")
    private Integer categoryId;

    @TableField(exist = false)
    private Category category;

    /**
     * 客户ID
     */
    @TableField("customer_id")
    private Integer customerId;

    @TableField(exist = false)
    private Customer customer;

    /**
     * 产品编码/SKU码
     */
    @TableField("sku_code")
    private String skuCode;

    /**
     * 规格
     */
    @TableField("specification")
    private String specification;

    /**
     * 尺寸
     */
    @TableField("size")
    private String size;

    /**
     * 产品单价
     */
    @TableField("unit_price")
    private BigDecimal unitPrice;

    /**
     * 是否外发 1:外发 0:自己生产
     */
    @TableField("is_out_sourced")
    private Boolean isOutSourced;

    /**
     * 是否需要吹膜 0:否 1:是
     */
    @TableField("need_blow_membrane")
    private Boolean needBlowMembrane;

    /**
     * 是否需要过电子(仅当吹膜步骤需要时有效) 0: 不需要 1:单面电子 2:双面电子
     */
    @TableField("is_corona_reqired")
    private Integer isCoronaReqired;

    /**
     * 是否需要折料 0:否 1:是
     */
    @TableField("need_folding")
    private Boolean needFolding;

    /**
     * 是否需要印刷 0:否 1:是
     */
    @TableField("need_printing")
    private Boolean needPrinting;

    /**
     * 需要印刷的内容、位置(图片，从pic_data获取)
     */
    @TableField("print_content")
    private String printContent;

    /**
     * 是否需要切袋 0:否 1:是
     */
    @TableField("need_cutting")
    private Boolean needCutting;

    /**
     * 是否需要打孔(仅当需要切袋时候有效) 0:否 1:是
     */
    @TableField("need_punching")
    private Boolean needPunching;

    /**
     * 打孔要求描述、数量、位置(仅当需要打孔时候有效，图片，从pic_data获取)
     */
    @TableField("punch_info")
    private String punchInfo;

    /**
     * 产品等级 A~F,排序用
     */
    @TableField("product_level")
    private String productLevel;

    /**
     * 是否正在使用 0:否 1:是
     */
    @TableField("is_in_use")
    private Boolean isInUse;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @TableField("create_uid")
    private Integer createUid;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_uid")
    private Integer updateUid;

    @TableField("update_time")
    private LocalDateTime updateTime;


}

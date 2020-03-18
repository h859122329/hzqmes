package com.gdglc.hzqmes.po;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 订单id
     */
    @TableField("order_id")
    private Integer orderId;

    /**
     * 产品id
     */
    @TableField("product_id")
    private Integer productId;

    @TableField("quantity")
    private Integer quantity;

    /**
     * 下单时候，产品实际单价
     */
    @TableField("unit_price")
    private BigDecimal unitPrice;

    /**
     * 订单相关信息，把产品、规格，简要信息整合,形成简要快照信息
     */
    @TableField("brief")
    private String brief;


}

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
 * 客户地址
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Data
public class CustomerAddr implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联客户的ID
     */
    @TableField("customer_id")
    private Integer customerId;

    /**
     * 地址类型,参考code_static ADDTYPE
     */
    @NotNull(message = "需要指定地址类型")
    @TableField("addr_type")
    private String addrType;

    /**
     * 地址
     */
    @NotEmpty(message = "地址不得为空")
    @TableField("address_line")
    private String addressLine;

    /**
     * 所在城市-区(不带)
     */
    @NotNull(message = "地址需指定所在市/区")
    @TableField("area_id")
    private Integer areaId;

    /**
     * 邮政编码
     */
    @TableField("zip_code")
    private String zipCode;


}

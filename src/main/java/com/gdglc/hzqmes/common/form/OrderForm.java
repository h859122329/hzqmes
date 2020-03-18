package com.gdglc.hzqmes.common.form;

import lombok.Data;

@Data
public class OrderForm {

    /**
     * 订单编号
     */
    private  String orderNo;

    /**
     * 订单状态
     */
    private Integer orderStatus;

    /**
     * 客户id
     */
    private Integer customerId;
}

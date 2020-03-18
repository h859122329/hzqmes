package com.gdglc.hzqmes.common.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.sql.Date;

@Data
public class OrderVo {

    private Integer id;
    /**
     * 订单编号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 客户id
     * */
    @TableField("customer_id")
    private Integer customerId;

    /**
     * 订单状态
     */
    @TableField("order_status")
    private Integer orderStatus;

    /**
     * 下单日期
     */
    @TableField("order_date")
    private Date orderDate;

    /**
     * 客户姓名
     */
    @TableField("customer_name")
    private String  customerName;

    /**
     * 交付日期
     */
    @TableField("deliver_date")
    private Date deliverDate;

    private String orderStatusText;

    public void setOrderStatusText() {

        if(this.orderStatus==-1){
            this.orderStatusText = "草稿";
        }
        if(this.orderStatus==0){
            this.orderStatusText = "已提交";
        }
        if(this.orderStatus==1){
            this.orderStatusText = "已完成";
        }
        if(this.orderStatus==2){
            this.orderStatusText = "已取消";
        }
        if(this.orderStatus==3){
            this.orderStatusText = "已交付";
        }

    }
}

package com.gdglc.hzqmes.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.OrderForm;
import com.gdglc.hzqmes.common.form.PageForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.common.vo.OrderVo;
import com.gdglc.hzqmes.response.ResultResponse;
import com.gdglc.hzqmes.util.PageUtil;
import com.gdglc.hzqmes.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gdglc.hzqmes.service.OrdersService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ListIterator;

/**
 * <p>
 * 客户订单表 前端控制器
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired private OrdersService orderService;

    @RequestMapping("/findAllOrder")
    public ResultResponse findAllOrder( OrderForm orderForm, PageForm pageForm){
        IPage page = PageUtil.initPage(pageForm);
        List<OrderVo> order  = orderService.findAll(orderForm,page);
        ListIterator<OrderVo>  it = order.listIterator();
        while (it.hasNext()){
            it.next().setOrderStatusText();
        }
        return ResultVOUtil.success(order);
    }



    @RequestMapping("/removeOneOrder")
    public ResultResponse removeOneOrder(Integer orderId){
        orderService.removeOneOrder(orderId);
        return ResultVOUtil.success();
    }
    @RequestMapping("/searchOrderDataCount")
    public ResultResponse searchOrderDataCount(OrderForm orderForm){
        int count = orderService.searchOrderDataCount(orderForm);
        return ResultVOUtil.success(count);
    }

    @RequestMapping("/removeManOrder")
    public ResultResponse removeManOrder(@RequestBody List<Integer> orderNos){
        orderService.deleteManOrder(orderNos);
        return ResultVOUtil.success();
    }


    @RequestMapping("/searchOneCustomer")
    public ResultResponse searchOneCustomerDetail(OrderForm orderForm){
        System.out.println("customerid ------------:"+orderForm.getCustomerId());
        CustomerDetailVo customerDetailVo = orderService.searchOneCustomer(orderForm);
        return ResultVOUtil.success(customerDetailVo);
    }

}

package com.gdglc.hzqmes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.OrderForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.common.vo.OrderVo;
import com.gdglc.hzqmes.po.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
/**
 * <p>
 * 客户订单表 服务类
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
public interface OrdersService extends IService<Orders> {

    /**
     * <p>
     * 查询订单信息
     * </p>
     *
     * @author gdglc
     * @since 2019-10-08
     */
    public  List<OrderVo>  findAll(OrderForm orderForm, IPage page);


    public void removeOneOrder(int id);

    public int searchOrderDataCount(OrderForm orderForm);

    public void deleteManOrder(List<Integer> orderNos);

    public CustomerDetailVo searchOneCustomer(OrderForm orderForm);
}

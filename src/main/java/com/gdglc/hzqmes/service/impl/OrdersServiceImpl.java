package com.gdglc.hzqmes.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.OrderForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.common.vo.OrderVo;
import com.gdglc.hzqmes.dao.CustomerDao;
import com.gdglc.hzqmes.po.Customer;
import com.gdglc.hzqmes.po.Orders;
import com.gdglc.hzqmes.dao.OrdersDao;
import com.gdglc.hzqmes.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import java.util.List;

/**
 * <p>
 * 客户订单表 服务实现类
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, Orders> implements OrdersService {

    @Autowired private OrdersDao orderDao;

    @Autowired private CustomerDao customerDao;

    @Override
    public List<OrderVo> findAll(OrderForm orderForm, IPage page) {
        return orderDao.findAll(orderForm,page);
    }

    @Override
    public void removeOneOrder(int id){orderDao.deleteById(id);}

    @Override
    public int searchOrderDataCount(OrderForm orderForm){return orderDao.searchOrderDataCount(orderForm);}

    @Override
    public void deleteManOrder(List<Integer> orderNos){orderDao.deleteManOrder(orderNos);}

    @Override
    public CustomerDetailVo searchOneCustomer(OrderForm orderForm){
        Customer customer = customerDao.selectById(orderForm.getCustomerId());
        CustomerDetailVo customerDetailVo = new CustomerDetailVo();
        BeanUtils.copyProperties(customer,customerDetailVo);
        return customerDetailVo;
    }






}

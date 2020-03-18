package com.gdglc.hzqmes.service.order;

import com.gdglc.hzqmes.common.form.OrderForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.service.OrdersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class orderServiceTest {


    @Autowired
    private OrdersService ordersService;

    @Test
    public void testSearchOneCustomer(){
        OrderForm orderForm = new OrderForm();
        orderForm.setCustomerId(2);
        CustomerDetailVo customerDetailVo = ordersService.searchOneCustomer(orderForm);
    }

}

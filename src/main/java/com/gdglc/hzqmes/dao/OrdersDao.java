package com.gdglc.hzqmes.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.OrderForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.common.vo.OrderVo;
import com.gdglc.hzqmes.po.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 客户订单表 Mapper 接口
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
public interface OrdersDao extends BaseMapper<Orders> {
    List<OrderVo> findAll(@Param("orderForm")OrderForm orderForm,IPage page);

    int searchOrderDataCount(@Param("orderForm") OrderForm orderForm);

    void deleteManOrder (@Param("orderNo")List<Integer> orderNo);



}

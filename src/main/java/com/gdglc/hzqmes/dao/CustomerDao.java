package com.gdglc.hzqmes.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.CustomerSearchForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.po.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
public interface CustomerDao extends BaseMapper<Customer> {

    IPage<CustomerDetailVo> selectCustomerBySearchCriteria(IPage page, @Param("searchForm") CustomerSearchForm customerSearchForm);



}

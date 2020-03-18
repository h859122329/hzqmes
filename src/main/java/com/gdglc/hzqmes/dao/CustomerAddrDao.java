package com.gdglc.hzqmes.dao;

import com.gdglc.hzqmes.po.CustomerAddr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 * 客户地址 Mapper 接口
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
public interface CustomerAddrDao extends BaseMapper<CustomerAddr> {

    public List<CustomerAddr> getAddressByCustomerId(Integer customerId);

}

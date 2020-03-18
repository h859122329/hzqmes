package com.gdglc.hzqmes.dao;

import com.gdglc.hzqmes.po.ContactNo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 联系方式 Mapper 接口
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
public interface ContactNoDao extends BaseMapper<ContactNo> {

    /**
     * 根据客户Id 获取所有相关联系方式
     * @param customerId
     * @return
     */
    public List<ContactNo> getContactsByCustomerId(Integer customerId);

}

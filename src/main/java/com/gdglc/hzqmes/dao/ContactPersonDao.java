package com.gdglc.hzqmes.dao;

import com.gdglc.hzqmes.po.ContactPerson;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 客户联系人 Mapper 接口
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
public interface ContactPersonDao extends BaseMapper<ContactPerson> {

    public List<ContactPerson> getContactPersonByCustomerId(Integer customerId);
}

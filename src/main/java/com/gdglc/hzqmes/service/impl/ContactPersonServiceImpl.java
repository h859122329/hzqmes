package com.gdglc.hzqmes.service.impl;

import com.gdglc.hzqmes.dao.ContactPersonDao;
import com.gdglc.hzqmes.service.ContactPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户联系人 服务实现类
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Service
public class ContactPersonServiceImpl implements ContactPersonService {
    @Autowired
    private ContactPersonDao contactPersonDao;
}

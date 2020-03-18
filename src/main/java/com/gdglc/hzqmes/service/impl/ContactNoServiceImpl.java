package com.gdglc.hzqmes.service.impl;

import com.gdglc.hzqmes.dao.ContactNoDao;
import com.gdglc.hzqmes.service.ContactNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 联系方式 服务实现类
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Service
public class ContactNoServiceImpl implements ContactNoService {
    @Autowired
    private ContactNoDao contactNoDao;
}

package com.gdglc.hzqmes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdglc.hzqmes.po.Role;
import com.gdglc.hzqmes.dao.RoleDao;
import com.gdglc.hzqmes.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户组 服务实现类
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> getRoles() {
        return roleDao.selectList(new QueryWrapper<>());
    }
}

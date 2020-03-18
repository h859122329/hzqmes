package com.gdglc.hzqmes.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdglc.hzqmes.dao.CategoryDao;
import com.gdglc.hzqmes.po.Category;
import com.gdglc.hzqmes.po.CodeStaticTable;
import com.gdglc.hzqmes.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;


    /**
     * 根据id得到一个分类
     * @param moduleId
     * @return
     */
    @Override
    public List<Category> getCategoryListById(Integer moduleId) {
        Category category = new Category();
        category.setModuleId(moduleId);

        List result = categoryDao.selectList(new QueryWrapper<>(category));
        return result;
    }

    @Override
    public void delete(Integer id) {

    }

}

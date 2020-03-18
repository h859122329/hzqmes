package com.gdglc.hzqmes.service;


import com.gdglc.hzqmes.po.Category;
import com.gdglc.hzqmes.po.CodeStaticTable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
public interface CategoryService{
    /**
     * 根据id得到一个分类
     * @param moduleId
     * @return
     */
    @Cacheable( value = "getCategoryListById",key = "#moduleId"  )
    public List<Category> getCategoryListById(Integer moduleId);


    /**
     * 删除对应的缓存
     * 详细请看文章 https://www.cnblogs.com/wuhaidong/p/10297657.html
     * */
    @CacheEvict(value="getCategoryListById", key = "#moduleId")
    public void delete(Integer id);
}

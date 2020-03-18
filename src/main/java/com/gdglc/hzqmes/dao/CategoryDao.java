package com.gdglc.hzqmes.dao;

import com.gdglc.hzqmes.po.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdglc.hzqmes.po.CustomerAddr;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 分类表 Mapper 接口
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
public interface CategoryDao extends BaseMapper<Category> {

    public Category getCategoryById(Integer categoryId);
}

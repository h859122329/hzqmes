package com.gdglc.hzqmes.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.CustomerSearchForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.common.vo.ProductVo;
import com.gdglc.hzqmes.po.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 产品表 Mapper 接口
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
public interface ProductDao extends BaseMapper<Product> {

    IPage<Product> searchProductDetailByCritera(IPage iPage, @Param("searchForm") Product searchForm);

    ProductVo getProductDetailById(Integer id);
}

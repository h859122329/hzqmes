package com.gdglc.hzqmes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.CustomerSearchForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.po.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品表 服务类
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
public interface ProductService extends IService<Product> {

    void modifyProduct(Product product);

    Integer getSkuCode(String skuCode);

    void insert(Product product);

    IPage<Product> searchProductDetailByCritera(Product searchForm, IPage iPage);

    /**
     * 批量把产品删除
     * @param productIds
     */
    void batchMarkDeleteProduct(List<Integer> productIds);

    Product getProductDetailById(Integer id);

}

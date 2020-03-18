package com.gdglc.hzqmes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.AppConstants;
import com.gdglc.hzqmes.common.form.CustomerSearchForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.po.Customer;
import com.gdglc.hzqmes.po.Product;
import com.gdglc.hzqmes.dao.ProductDao;
import com.gdglc.hzqmes.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品表 服务实现类
 * </p>
 *
 * @author gdglc
 * @since 2019-10-08
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements ProductService {
    
    @Autowired
    private ProductDao productDao;

    @Override
    public void modifyProduct(Product product) {
        productDao.updateById(product);
    }

    @Override
    public Integer getSkuCode(String skuCode) {
        return productDao.selectCount(new QueryWrapper<Product>().eq("sku_code",skuCode));
    }

    @Override
    public void insert(Product product) {
        productDao.insert(product);
    }

    /**
     * 根据搜索条件查询产品列表
     *
     * @param searchForm
     * @param iPage
     * @return
     */
    @Override
    public IPage<Product> searchProductDetailByCritera(Product searchForm, IPage iPage) {
        IPage<Product> result = productDao.searchProductDetailByCritera(iPage,searchForm);
        return  result;
    }

    @Override
    public void batchMarkDeleteProduct(List<Integer> productIds) {
        List<Product> productsList = productDao.selectBatchIds(productIds);
        for (Product product :
                productsList) {
            product.setActiveFlag(0);
            productDao.updateById(product);
        }
    }

    @Override
    public Product getProductDetailById(Integer id) {
        return productDao.getProductDetailById(id);
    }
}

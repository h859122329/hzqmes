package com.gdglc.hzqmes.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.CustomerForm;
import com.gdglc.hzqmes.common.form.CustomerSearchForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.po.Customer;

import java.util.List;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
public interface CustomerService {

    /**
     * 创建客户信息
     *
     * @param customerForm
     */
    public void createCustomer(CustomerForm customerForm);

    /**
     * 修改客户信息
     *
     * @param customerForm
     */
    public void updateCustomer(CustomerForm customerForm);

    /**
     * 逻辑删除/禁用客户
     *
     * @param customerForm
     */
    public void disableCustomer(CustomerForm customerForm);

    /**
     * 启用客户
     *
     * @param customerForm
     */
    public void enableCustomer(CustomerForm customerForm);

    /**
     * 物理删除客户信息，及相关联系人、联系方式、地址信息
     *
     * @param customerForm
     */
    public void deleteCustomer(CustomerForm customerForm);


    /**
     * 获得客户信息详情
     *
     * @param customerId
     * @return
     */
    public CustomerDetailVo getCustomerDetailById(Integer customerId);

    /**
     * 获得一个被启用的客户信息详情
     *
     * @param customerId
     * @param customerId
     * @return
     */
    public CustomerDetailVo getActiveCustomerDetailById(Integer customerId);

    /**
     * 根据搜索条件查询客户列表
     * @param searchForm
     * @return
     */
    public IPage<CustomerDetailVo> searchCustomerDetailByCritera(CustomerSearchForm searchForm, IPage iPage);

    /**
     * 把客户标识为删除(非物理删除)
     * @param customerId
     */
    public void markDeleteCustomer( Integer customerId);


    /**
     * 批量把客户标识为删除(非物理删除)
     * @param customerIds
     */
    public void batchMarkDeleteCustomer( List<Integer> customerIds);
}

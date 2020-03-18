package com.gdglc.hzqmes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.AppConstants;
import com.gdglc.hzqmes.common.form.CustomerForm;
import com.gdglc.hzqmes.common.form.CustomerSearchForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.dao.ContactNoDao;
import com.gdglc.hzqmes.dao.ContactPersonDao;
import com.gdglc.hzqmes.dao.CustomerAddrDao;
import com.gdglc.hzqmes.dao.CustomerDao;
import com.gdglc.hzqmes.enums.ResultEnum;
import com.gdglc.hzqmes.exception.BusinessException;
import com.gdglc.hzqmes.po.ContactNo;
import com.gdglc.hzqmes.po.ContactPerson;
import com.gdglc.hzqmes.po.Customer;
import com.gdglc.hzqmes.po.CustomerAddr;
import com.gdglc.hzqmes.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author gdglc
 * @since 2019-09-12
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerAddrDao customerAddrDao;

    @Autowired
    private ContactNoDao contactNoDao;

    @Autowired
    private ContactPersonDao contactPersonDao;



    @Override
    public void createCustomer(CustomerForm customerForm) {

        Customer customer = customerForm.getCustomer();

        List<CustomerAddr> customerAddrList = customerForm.getCustomerAddrList();
        List<ContactNo> contactNoList = customerForm.getContactNoList();
        List<ContactPerson> contactPersonList = customerForm.getContactPersonList();

        if( customer == null || customerAddrList == null){
            throw new BusinessException(ResultEnum.DATA_FIELD_MISSING);
        }

        //部分校验交由spring boot 控制器
        validateCreateCustomer(customerForm);

        customer.setActiveFlag(AppConstants.ACTIVE_FLAG_ACTIVE);
        customer.setCreateTime(LocalDateTime.now());
        customer.setCreateUid(customerForm.getCurrentUser().getId());
        customerDao.insert(customer);

        //客户地址
        for (CustomerAddr customerAddr : customerAddrList
                ) {
           customerAddr.setCustomerId(customer.getId());
           customerAddrDao.insert(customerAddr);
        }

        //客户联系人
        for (ContactPerson contactPerson : contactPersonList
                ) {
            contactPerson.setCustomerId(customer.getId());
            contactPersonDao.insert(contactPerson);
        }

        //联系电话
        for (ContactNo contactNo: contactNoList
             ) {
            contactNo.setItemType(AppConstants.CONTACT_PERSON_HOLDER_CUSTOMER);
            contactNo.setItemId(customer.getId());
            contactNoDao.insert(contactNo);
        }

    }

    @Override
    public void updateCustomer(CustomerForm customerForm) {

        Customer customer = customerForm.getCustomer();

        List<CustomerAddr> customerAddrList = customerForm.getCustomerAddrList();
        List<ContactNo> contactNoList = customerForm.getContactNoList();
        List<ContactPerson> contactPersonList = customerForm.getContactPersonList();

        if( customer == null || customerAddrList == null){
            throw new BusinessException(ResultEnum.DATA_FIELD_MISSING);
        }

        //部分校验交由spring boot 控制器
        validateUpdateCustomer(customerForm);

        customerDao.updateById(customer);

        //客户地址
        for (CustomerAddr customerAddr : customerAddrList) {
            if(customerAddr.getId()!= null){
                customerAddrDao.updateById(customerAddr);
            }else {
                customerAddr.setCustomerId(customer.getId());
                customerAddrDao.insert(customerAddr);
            }
        }

        //客户联系人
        for (ContactPerson contactPerson : contactPersonList) {
            if (contactPerson.getId()!= null){
                contactPersonDao.updateById(contactPerson);
            }else {
                contactPerson.setCustomerId(customer.getId());
                contactPersonDao.insert(contactPerson);
            }
        }

        //联系电话
        for (ContactNo contactNo: contactNoList) {
            if(contactNo.getId()!= null){
                contactNoDao.updateById(contactNo);
            }else {
                contactNo.setItemType(AppConstants.CONTACT_PERSON_HOLDER_CUSTOMER);
                contactNo.setItemId(customer.getId());
                contactNoDao.insert(contactNo);
            }
        }

        List<CustomerAddr> deteleCustomerAddrList = customerForm.getDeleteCustomerAddrList();
        List<ContactNo> deleteContactNoList = customerForm.getDeleteContactNoList();
        List<ContactPerson> deleteContactPersonList = customerForm.getDeleteContactPersonList();

        //清除被删客户地址
        for (CustomerAddr customerAddr : deteleCustomerAddrList) {
            customerAddrDao.deleteById(customerAddr);
        }

        //清除被删客户联系人
        for (ContactPerson contactPerson : deleteContactPersonList) {
            contactPersonDao.deleteById(contactPerson);
        }

        //清除被删联系电话
        for (ContactNo contactNo: deleteContactNoList) {
            contactNoDao.deleteById(contactNo);
        }

    }

    @Override
    public void disableCustomer(CustomerForm customerForm) {
        Customer customer = customerForm.getCustomer();

        Customer searchCustomer = new Customer();
        searchCustomer.setId(customer.getId());
        Customer result= customerDao.selectOne(new QueryWrapper<>(searchCustomer));
        if (result == null){
            throw new BusinessException(ResultEnum.DATA_NOT_EXIST,"该客户不存在");
        }
//        result.setActiveFlag(0);
        result.setIsCooperating(0);

        //Todo 更新修改人、修改时间

        customerDao.updateById(result);
    }

    @Override
    public void enableCustomer(CustomerForm customerForm) {

        Customer customer = customerForm.getCustomer();

        Customer searchCustomer = new Customer();
        searchCustomer.setId(customer.getId());
        Customer result= customerDao.selectOne(new QueryWrapper<>(searchCustomer));
        if (result == null){
            throw new BusinessException(ResultEnum.DATA_NOT_EXIST,"该客户不存在");
        }
//        result.setActiveFlag(1);
        result.setIsCooperating(1);

        //Todo 更新修改人、修改时间
        //result.set
        customerDao.updateById(result);
    }

    @Override
    public void deleteCustomer(CustomerForm customerForm) {

        Customer customer = customerForm.getCustomer();

        if( customer == null  ){
            throw new BusinessException(ResultEnum.DATA_FIELD_MISSING);
        }

        Customer searchCustomer = new Customer();
        searchCustomer.setId(customer.getId());
        Customer result= customerDao.selectOne(new QueryWrapper<>(searchCustomer));
        if (result == null){
            throw new BusinessException("该客户不存在");
        }

        CustomerAddr searchAdd = new CustomerAddr();
        searchAdd.setCustomerId(customer.getId());
        customerAddrDao.delete(new QueryWrapper<>(searchAdd));

        ContactNo searchContactNo = new ContactNo();
        searchContactNo.setItemId(customer.getId());
        searchContactNo.setItemType(AppConstants.CONTACT_PERSON_HOLDER_CUSTOMER);
        contactNoDao.delete(new QueryWrapper<>(searchContactNo));

        ContactPerson searchContactPerson = new ContactPerson();
        searchContactPerson.setCustomerId(customer.getId());
        contactPersonDao.delete(new QueryWrapper<>(searchContactPerson));

        customerDao.deleteById(searchCustomer);
    }

    /**
     * 获得客户信息详情
     *
     * @param customerId
     * @return
     */
    @Override
    public CustomerDetailVo getCustomerDetailById(Integer customerId) {

        Customer searchCustomer = new Customer();
        searchCustomer.setId(customerId);

        return getCustomerDetail(searchCustomer);
    }

    /**
     * 获得一个被启用的客户信息详情
     *
     * @param customerId
     * @return
     */
    @Override
    public CustomerDetailVo getActiveCustomerDetailById(Integer customerId) {

        Customer searchCustomer = new Customer();
        searchCustomer.setId(customerId);
        searchCustomer.setActiveFlag(AppConstants.ACTIVE_FLAG_ACTIVE);

        return getCustomerDetail(searchCustomer);
    }

    /**
     * 根据搜索条件查询客户列表
     *
     * @param searchForm
     * @param iPage
     * @return
     */
    @Override
    public IPage<CustomerDetailVo> searchCustomerDetailByCritera(CustomerSearchForm searchForm, IPage iPage) {

        IPage<CustomerDetailVo> result = customerDao.selectCustomerBySearchCriteria(iPage,searchForm);

        List<CustomerDetailVo> resultList = result.getRecords();
        for (CustomerDetailVo detail : resultList) {
            System.out.println( detail);
        }

        return  result;
    }

    /**
     * 把客户标识为删除(非物理删除)
     *
     * @param customerId
     */
    @Override
    public void markDeleteCustomer(Integer customerId) {
       Customer customer = customerDao.selectById(customerId);
       customer.setActiveFlag(AppConstants.INTEGER_ZERO);
       customerDao.updateById(customer);
    }

    @Override
    public void batchMarkDeleteCustomer(List<Integer> customerIds){
        List<Customer> customerList = customerDao.selectBatchIds(customerIds);

        for (Customer customer :
                customerList) {
            customer.setActiveFlag(AppConstants.INTEGER_ZERO);
            customerDao.updateById(customer);
        }
    }

    /**
     * 根据给定的 Customer 条件查询一个客户信息详情（含地址、联系人、联系方式）
     * @param searchCustomer
     * @return
     */
    private CustomerDetailVo getCustomerDetail(Customer searchCustomer){
        CustomerDetailVo customerDetailVo = new CustomerDetailVo();

        ContactNo searchContactNo = new ContactNo();
        ContactPerson searchContactPerson = new ContactPerson();
        CustomerAddr searchCustomerAddr = new CustomerAddr();

        searchCustomerAddr.setCustomerId(searchCustomer.getId());
        searchContactPerson.setCustomerId(searchCustomer.getId());

        searchContactNo.setItemId(searchCustomer.getId());
        searchContactNo.setItemType(AppConstants.CONTHOLD_CUSTOMER);

        Customer customer = customerDao.selectOne(new QueryWrapper<>(searchCustomer));

        BeanUtils.copyProperties(customer,customerDetailVo);
        customerDetailVo.setContactNoList(contactNoDao.selectList(new QueryWrapper<>(searchContactNo)));
        customerDetailVo.setCustomerAddrList(customerAddrDao.selectList(new QueryWrapper<>(searchCustomerAddr)));
        customerDetailVo.setContactPersonList(contactPersonDao.selectList(new QueryWrapper<>(searchContactPerson)));

        return customerDetailVo;
    }

    /**
     * 检测创建客户表单
     * @param customerForm
     */
    private void validateCreateCustomer(CustomerForm customerForm){

        Customer customer = customerForm.getCustomer();
        if(!customer.getIsTaxFree().booleanValue() && customer.getTaxRate() == null){
          throw new BusinessException("含税的必须制定税率");
        }

        if(customer.getSettleType().equals(AppConstants.SETTLE_TYPE_MONTHLY) && customer.getDayOfSettle() ==null){
            throw new BusinessException("月结客户必须指定月结日期");
        }

        List<CustomerAddr> customerAddrList = customerForm.getCustomerAddrList();

        for (CustomerAddr addr :
                customerAddrList) {
            if(addr.getAddrType() == null){
                throw new BusinessException("需指定地址类型");
            }
        }

    }

    /**
     * 校验修改客户信息
     * @param customerForm
     */
    private void validateUpdateCustomer(CustomerForm customerForm){

        Customer customer = customerForm.getCustomer();
        if(!customer.getIsTaxFree().booleanValue() && customer.getTaxRate() == null){
            throw new BusinessException("含税的必须制定税率");
        }

        if(customer.getSettleType().equals(AppConstants.SETTLE_TYPE_MONTHLY) && customer.getDayOfSettle() ==null){
            throw new BusinessException("月结客户必须指定月结日期");
        }

        List<CustomerAddr> customerAddrList = customerForm.getCustomerAddrList();

        for (CustomerAddr addr :
                customerAddrList) {
            if(addr.getAddrType() == null){
                throw new BusinessException("需指定地址类型");
            }
        }

        Customer searchCustomer = new Customer();
        searchCustomer.setId(customer.getId());
        int count = customerDao.selectCount(new QueryWrapper<>(searchCustomer));
        if (count <=0){
            throw new BusinessException("该客户不存在");
        }
    }

}

package com.gdglc.hzqmes.service.customer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.AppConstants;
import com.gdglc.hzqmes.common.form.CustomerForm;
import com.gdglc.hzqmes.common.form.CustomerSearchForm;
import com.gdglc.hzqmes.common.form.PageForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.po.*;
import com.gdglc.hzqmes.service.CustomerService;
import com.gdglc.hzqmes.util.PageUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Leyenda on 2019/10/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreateCustomer(){

        Customer customer = new Customer();
        customer.setCustomerLevel("F");
        customer.setCustomerName("张小敬");
        customer.setIsCooperating(new Integer(1));
        customer.setIsTaxFree(Boolean.FALSE);
        customer.setTaxRate(new BigDecimal(6.5));
        customer.setSettleType(new Integer(1));
        customer.setDayOfSettle(1);
        customer.setRegDate(LocalDate.now());

        CustomerForm customerForm= new CustomerForm();
        customerForm.setCustomer(customer);

        Users user = new Users();
        user.setId(1);
        user.setNickname("Yatta");
        customerForm.setCurrentUser(user);

        List<CustomerAddr> customerAddrList = new LinkedList<>();

        CustomerAddr addr = new CustomerAddr();
        addr.setAddrType(AppConstants.ADDTYPE_OFFICE);
        addr.setAreaId(2067);
        addr.setAddressLine("天安数码城2046房");
        addr.setZipCode("523832");
        customerAddrList.add(addr);

        addr = new CustomerAddr();
        addr.setAddrType(AppConstants.ADDTYPE_REG);
        addr.setAreaId(2067);
        addr.setAddressLine("天安数码城2048房");
        addr.setZipCode("523832");
        customerAddrList.add(addr);

        customerForm.setCustomerAddrList(customerAddrList);


        List<ContactNo> contactNoList = new LinkedList<>();

        ContactNo contactNo = new ContactNo();
        contactNo.setNumberType(AppConstants.CONTNOTP_MOBILE);
        contactNo.setContent("135315000000");
        contactNoList.add(contactNo);

        contactNo = new ContactNo();
        contactNo.setNumberType(AppConstants.CONTNOTP_PHONE);
        contactNo.setContent("020-83590234");
        contactNoList.add(contactNo);

        customerForm.setContactNoList(contactNoList);

        List<ContactPerson> contactPersonList = new LinkedList<>();

        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setContactType(AppConstants.CONTYPE_GM);
        contactPerson.setContactName("缺了或多");
        contactPerson.setBrief("渣男");
        contactPersonList.add(contactPerson);

        contactPerson = new ContactPerson();
        contactPerson.setContactType(AppConstants.CONTYPE_FINANCE);
        contactPerson.setContactName("檀祺");
        contactPerson.setBrief("渣女");
        contactPersonList.add(contactPerson);

        customerForm.setContactPersonList(contactPersonList);

        customerService.createCustomer(customerForm);

    }

    @Test
    public void testUpdateCustomer(){

        Customer customer = new Customer();
        customer.setId(2);
        customer.setCustomerLevel("F");
        customer.setCustomerName("雷佳音");
        customer.setIsCooperating(new Integer(1));
        customer.setIsTaxFree(Boolean.FALSE);
        customer.setTaxRate(new BigDecimal(6.5));
        customer.setSettleType(new Integer(1));
        customer.setDayOfSettle(1);
        customer.setRegDate(LocalDate.now());

        CustomerForm customerForm= new CustomerForm();
        customerForm.setCustomer(customer);

        Users user = new Users();
        user.setId(1);
        user.setNickname("imochi");
        customerForm.setCurrentUser(user);

        List<CustomerAddr> customerAddrList = new LinkedList<>();

        CustomerAddr addr = new CustomerAddr();
        addr.setId(3);
        addr.setAddrType(AppConstants.ADDTYPE_OFFICE);
        addr.setAreaId(2067);
        addr.setAddressLine("平安坊");
        addr.setZipCode("523832");
        customerAddrList.add(addr);

        addr = new CustomerAddr();
        addr.setAddrType(AppConstants.ADDTYPE_REG);
        addr.setAreaId(2067);
        addr.setAddressLine("天安数码城2048房");
        addr.setZipCode("523832");
        customerAddrList.add(addr);

        customerForm.setCustomerAddrList(customerAddrList);


        List<CustomerAddr> deleteCustomerAddrList = new LinkedList<>();

        addr = new CustomerAddr();
        addr.setId(4);
        addr.setAddrType(AppConstants.ADDTYPE_OFFICE);
        addr.setAreaId(2067);
        addr.setAddressLine("AAAA");
        addr.setZipCode("523832");
        deleteCustomerAddrList.add(addr);

        customerForm.setDeleteCustomerAddrList(deleteCustomerAddrList);

        List<ContactNo> contactNoList = new LinkedList<>();

        ContactNo contactNo = new ContactNo();
        contactNo.setId(3);
        contactNo.setNumberType(AppConstants.CONTNOTP_MOBILE);
        contactNo.setContent("135315000000");
        contactNoList.add(contactNo);

        contactNo = new ContactNo();
        contactNo.setNumberType(AppConstants.CONTNOTP_PHONE);
        contactNo.setContent("020-83590234");
        contactNoList.add(contactNo);

        customerForm.setContactNoList(contactNoList);

        List<ContactNo> deteleContactNoList = new LinkedList<>();

        contactNo = new ContactNo();
        contactNo.setId(4);
        contactNo.setNumberType(AppConstants.CONTNOTP_MOBILE);
        contactNo.setContent("135315000000");
        deteleContactNoList.add(contactNo);

        customerForm.setDeleteContactNoList(deteleContactNoList);


        List<ContactPerson> contactPersonList = new LinkedList<>();

        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setId(3);
        contactPerson.setContactType(AppConstants.CONTYPE_GM);
        contactPerson.setContactName("大阿文");
        contactPerson.setBrief("渣男");
        contactPersonList.add(contactPerson);

        contactPerson = new ContactPerson();
        contactPerson.setContactType(AppConstants.CONTYPE_FINANCE);
        contactPerson.setContactName("孙小美");
        contactPerson.setBrief("渣女2");
        contactPersonList.add(contactPerson);

        customerForm.setContactPersonList(contactPersonList);

        List<ContactPerson> deleteContactPersonList = new LinkedList<>();

        contactPerson = new ContactPerson();
        contactPerson.setId(4);
        contactPerson.setContactType(AppConstants.CONTYPE_GM);
        contactPerson.setContactName("大阿文");
        contactPerson.setBrief("渣男");
        deleteContactPersonList.add(contactPerson);

        customerForm.setDeleteContactPersonList(deleteContactPersonList);

        customerService.updateCustomer(customerForm);

    }

    @Test
    @Transactional
    public void testDeleteCustomer(){
        CustomerForm customerForm = new CustomerForm();

        Customer customer = new Customer();
        customer.setId(2);

        List<CustomerAddr> customerAddrList = new LinkedList<>();
        List<ContactNo> contactNoList = new LinkedList<>();
        List<ContactPerson> contactPersonList = new LinkedList<>();

        customerForm.setCustomer(customer);
        customerForm.setCustomerAddrList(customerAddrList);
        customerForm.setContactPersonList(contactPersonList);
        customerForm.setContactNoList(contactNoList);

        customerService.deleteCustomer(customerForm);
    }

    @Test
    public void testSearchCustomerByCritera()
    {
        CustomerSearchForm searchForm = new CustomerSearchForm();
//        searchForm.setBeginRegDate(LocalDate.parse("2019-09-03"));

        searchForm.setCustomerName("雷佳");
        PageForm pageForm = new PageForm();
        pageForm.setCurrent(0);
        pageForm.setSize(10);

        IPage page = PageUtil.initPage(pageForm);

        List<CustomerDetailVo> customerList = customerService.searchCustomerDetailByCritera(searchForm,page).getRecords();

        for (CustomerDetailVo detail :
                customerList) {

            System.out.println("cust name:"+detail.getCustomerName());
            System.out.println(detail.toString());
        }
        System.out.println(customerList);
    }
}

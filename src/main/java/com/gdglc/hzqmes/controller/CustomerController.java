package com.gdglc.hzqmes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gdglc.hzqmes.common.form.CustomerBatchIdForm;
import com.gdglc.hzqmes.common.form.CustomerForm;
import com.gdglc.hzqmes.common.form.CustomerSearchForm;
import com.gdglc.hzqmes.common.form.PageForm;
import com.gdglc.hzqmes.common.vo.CustomerDetailVo;
import com.gdglc.hzqmes.po.*;
import com.gdglc.hzqmes.response.ResultResponse;
import com.gdglc.hzqmes.service.CustomerService;
import com.gdglc.hzqmes.service.UsersService;
import com.gdglc.hzqmes.util.PageUtil;
import com.gdglc.hzqmes.util.ResultVOUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Leyenda on 2019/10/12.
 */
@Slf4j
@Api(tags = "客户管理管理")
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UsersService usersService;

    @ApiOperation("创建客户")
    @ApiImplicitParams({
        @ApiImplicitParam(name="customerForm", value = "客户表单对象", type = "CustomerForm")
    })
    @PostMapping("/createCustomer")
    public ResultResponse createCustomer(@Valid @RequestBody CustomerForm customerForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Users currentUser = usersService.findUserInContext();

        customerForm.setCurrentUser(currentUser);

        Customer customer = customerForm.getCustomer();

        customer.setActiveFlag(1);
        customer.setCreateUid(currentUser.getId());
        customer.setCreateTime(LocalDateTime.now());
        customer.setRegDate(LocalDate.now());

        customerService.createCustomer(customerForm);

        return ResultVOUtil.success();

    }

    @PostMapping("/updateCustomer")
    public ResultResponse updateCustomer(@Valid @RequestBody CustomerForm customerForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        Users currentUser = usersService.findUserInContext();

        customerForm.setCurrentUser(currentUser);

        Customer customer = customerForm.getCustomer();

        customerService.updateCustomer(customerForm);

        return ResultVOUtil.success();

    }

    @RequestMapping("/markDeleteCustomer")
    public ResultResponse markDeleteCustomer(Integer customerId){
        customerService.markDeleteCustomer(customerId);
        return ResultVOUtil.success();
    }

    @ApiOperation("批量删除客户")
    @RequestMapping("/batchMarkDeleteCustomer")
    public ResultResponse batchMarkDeleteCustomer(@RequestBody CustomerBatchIdForm customerBatchIdForm){
//        ArrayList<Integer> customerIdList = new ArrayList<>();

//        for (String customerId :
//                customerIds) {
//            customerIdList.add(Integer.parseInt(customerId));
//        }
        customerService.batchMarkDeleteCustomer(customerBatchIdForm.getCustomerIds());
        return ResultVOUtil.success();
    }

    @RequestMapping("/deleteCustomer")
    public ResultResponse deleteCustomer(CustomerForm customerForm){

        customerService.deleteCustomer(customerForm);

        return ResultVOUtil.success();
    }

    /**
     * 分页搜索客户
     * @param searchForm
     * @param pageForm
     * @return 分页的客户列表(带关联详情)
     */
    @RequestMapping("/searchCustomerDetails")
    public  ResultResponse searchCustomerDetails(CustomerSearchForm searchForm, PageForm pageForm){
        Users currentUser = usersService.findUserInContext();
        System.out.println(searchForm.getCustomerName());
        searchForm.setCurrentUser(currentUser);
        IPage iPage = PageUtil.initPage(pageForm);
        return ResultVOUtil.success(customerService.searchCustomerDetailByCritera(searchForm,iPage));
    }

    @RequestMapping("/getCustomerDetailsById")
    public ResultResponse getCustomerDetailsById(Integer customerId){
        return ResultVOUtil.success(customerService.getCustomerDetailById(customerId));
    }

    @RequestMapping("/enableCustomer")
    public ResultResponse enableCustomer(Integer customerId){
        CustomerForm form = new CustomerForm();
        Customer customer = new Customer();
        customer.setId(customerId);
        form.setCustomer(customer);
        customerService.enableCustomer(form);
        return ResultVOUtil.success();
    }

    @RequestMapping("/getCustomerForEdit")
    public ResultResponse getCustomerForEdit(Integer customerId){
        CustomerForm result = new CustomerForm();
        CustomerDetailVo customerDetailVo = customerService.getCustomerDetailById(customerId);
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDetailVo, customer);
        result.setCustomer(customer);
        result.setContactNoList(customerDetailVo.getContactNoList());
        result.setContactPersonList(customerDetailVo.getContactPersonList());
        result.setCustomerAddrList(customerDetailVo.getCustomerAddrList());
        result.setDeleteContactPersonList(new LinkedList<ContactPerson>());
        result.setDeleteContactNoList(new LinkedList<ContactNo>());
        result.setDeleteCustomerAddrList(new LinkedList<CustomerAddr>());
        return ResultVOUtil.success(result);
    }

    @RequestMapping("/disableCustomer")
    public ResultResponse disableCustomer(Integer customerId){
        CustomerForm form = new CustomerForm();
        Customer customer = new Customer();
        customer.setId(customerId);
        form.setCustomer(customer);
        customerService.disableCustomer(form);
        return ResultVOUtil.success();
    }
}

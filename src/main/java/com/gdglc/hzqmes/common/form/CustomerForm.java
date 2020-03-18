package com.gdglc.hzqmes.common.form;

import com.gdglc.hzqmes.po.*;
import lombok.Data;

import java.util.List;

/**
 * Created by Leyenda on 2019/10/8.
 */
@Data
public class CustomerForm {

    /**
     *  当前登录用户
     */
    private Users currentUser;

    /**
     *  客户对象
     */
    private Customer customer;

    /**
     *  客户地址列表
     */
    private List<CustomerAddr> customerAddrList;

    /**
     * 被删除的客户地址列表
     */
    private List<CustomerAddr> deleteCustomerAddrList;

    /**
     * 联系方式列表
     */
    private List<ContactNo> contactNoList;

    /**
     * 被删除联系方式列表
     */
    private List<ContactNo> deleteContactNoList;

    /**
     * 联系人列表
     */
    private List<ContactPerson> contactPersonList;

    /**
     * 被删除的联系人列表
     */
    private List<ContactPerson> deleteContactPersonList;


}

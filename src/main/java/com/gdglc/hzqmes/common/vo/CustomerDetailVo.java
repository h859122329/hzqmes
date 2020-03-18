package com.gdglc.hzqmes.common.vo;

import com.gdglc.hzqmes.po.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by Leyenda on 2019/10/14.
 */
@Data
public class CustomerDetailVo extends Customer{


    private List<CustomerAddr> customerAddrList;

    private List<ContactNo> contactNoList;

    private List<ContactPerson> contactPersonList;

}

package com.gdglc.hzqmes.common;

import java.math.BigDecimal;

/**
 * Created by Leyenda on 2019/2/14.
 */
public class AppConstants {
    /**
     * 限流标识
     */
    public static final String LIMIT_ALL = "HZQMES_LIMIT_ALL";

    /**
     * 用户角色常量
     */
    public static final String ROLE_CUSTOMER = "CUSTOMER";
    public static final String ROLE_FINANCE = "FINANCE";
    public static final String ROLE_FACTORY_ADMIN = "FACTORY_ADMIN";
    public static final String ROLE_PLATFORM_ADMIN = "PLATFORM_ADMIN";


    /**
     * 联系人类型 - LEGAL 法人代表
     */
    public static final String CONTYPE_LEGAL = "LEGAL";
    /**
     * 联系人类型 - GRADE 董事长
     */
    public static final String CONTYPE_GRADE = "GRADE";
    /**
     * 联系人类型 - GM 总经理
     */
    public static final String CONTYPE_GM = "GM";
    /**
     * 联系人类型 - SALES 销售
     */
    public static final String CONTYPE_SALES = "SALES";
    /**
     * 联系人类型 - OTHER 其它
     */
    public static final String CONTYPE_OTHER = "OTHER";
    /**
     * 联系人类型 - FINANCE 财务
     */
    public static final String CONTYPE_FINANCE = "FINANCE";
    /**
     * 联系人类型 - HOUSEWARE 仓管
     */
    public static final String CONTYPE_HOUSEWARE = "HOUSEWARE";



    /**
     * 联系方式类型 - PHONE 固话
     */
    public static final String CONTNOTP_PHONE = "PHONE";
    /**
     * 联系方式类型 - MOBILE 手机
     */
    public static final String CONTNOTP_MOBILE = "MOBILE";
    /**
     * 联系方式类型 - FAX 传真
     */
    public static final String CONTNOTP_FAX = "FAX";
    /**
     * 联系方式类型 - QQ
     */
    public static final String CONTNOTP_QQ = "QQ";
    /**
     * 联系方式类型 - WECHAT 微信
     */
    public static final String CONTNOTP_WECHAT = "WECHAT";
    /**
     * 联系方式类型 - EMAIL 邮箱
     */
    public static final String CONTNOTP_EMAIL = "EMAIL";
    /**
     * 联系方式类型 - OTHER 其他
     */
    public static final String CONTNOTP_OTHER = "OTHER";



    /**
     * 联系方式持有者类型 - CUSTOMER 客户
     */
    public static final String CONTHOLD_CUSTOMER = "CUSTOMER";
    /**
     * 联系方式持有者类型 - MANUFACT 供应商
     */
    public static final String CONTHOLD_MANUFACT = "MANUFACT";
    /**
     * 联系方式持有者类型 - CONTACT 联系人
     */
    public static final String CONTHOLD_CONTACT = "CONTACT";



    /**
     * 地址类型 - REG 工商登记住所
     */
    public static final String ADDTYPE_REG = "REG";
    /**
     * 地址类型 - OFFICE 办公室
     */
    public static final String ADDTYPE_OFFICE = "OFFICE";
    /**
     * 地址类型 - HW 仓库
     */
    public static final String ADDTYPE_HW = "HW";
    /**
     * 地址类型 - SHIPPING 配送地址
     */
    public static final String ADDTYPE_SHIPPING = "SHIPPING";
    /**
     * 地址类型 - OTHER 其他
     */
    public static final String ADDTYPE_OTHER = "OTHER";


    public static final Integer SETTLE_TYPE_CASH = 0;
    public static final Integer SETTLE_TYPE_MONTHLY = 1;

    public static final String CONTACT_PERSON_HOLDER_CUSTOMER = "CUSTOMER";
    public static final String CONTACT_PERSON_HOLDER_MANUFACTURE = "MANUFACT";
    public static final String CONTACT_PERSON_HOLDER_CONTACT = "CONTACT";


    public static final Integer INTEGER_ZERO = new Integer(0);
    public static final BigDecimal BIGDECIMAL_ZERO= new BigDecimal("0.0");
    public static final Float FLOAT_ZERO = new Float(0.0);
    public static final Double DOUBLE_ZERO = new Double(0.0);

    public static final Integer INTEGER_ONE = new Integer(1);
    public static final BigDecimal BIGDECIMAL_ONE = new BigDecimal("1.0");
    public static final Float FLOAT_ONE = new Float(1.0);
    public static final Double DOUBLE_ONE = new Double(1.0);

    public static final Integer ACTIVE_FLAG_INACTIVE = new Integer(0);
    public static final Integer ACTIVE_FLAG_ACTIVE = new Integer(1);


}

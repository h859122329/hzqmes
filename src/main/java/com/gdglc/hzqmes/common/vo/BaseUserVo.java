package com.gdglc.hzqmes.common.vo;

import lombok.Data;

/**
 * @author:dengyuxiao
 * @date:2019-02-20
 * @time:10:11
 */
@Data
public class BaseUserVo {
    /**
     * 用户ID
     */
    private Integer id;


    /**
     * 邮箱
     */
    private String ellipsisEmail;

    /**
     * 是否绑定邮箱
     */
    private String isBindEmail;

    /**
     * 手机号 加密   180****4444
     */
    private String ellipsisMobile;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 账户余额
     */
    private Integer moneyBal;

    /**
     * 积分
     */
    private Integer score;

    public String getEllipsisMobile() {
        if(mobile==null || mobile.isEmpty()){
            return null;
        }
        return mobile.substring(0,3)+"****"+mobile.substring(7);
    }

    public String getEllipsisEmail() {
        if(ellipsisEmail==null || ellipsisEmail.isEmpty()){
            return null;
        }
        int length = ellipsisEmail.indexOf("@");
        int sublength=length / 2;
        String str="";
        for(int i=0;i<sublength;i++){
            str+="*";
        }
        return ellipsisEmail.substring(0,length-sublength)+str+ellipsisEmail.substring(ellipsisEmail.indexOf("@"));
    }

    public Boolean getIsBindEmail() {
        return    (this.ellipsisEmail==null || this.ellipsisEmail.isEmpty())? false:true;
    }


    /**
     * -1代表需要激活   0 代表正常  1 代表删除
     */
    private Integer status;
}

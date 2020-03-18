package com.gdglc.hzqmes.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Title: 获取访问用户的ip地址</p>
 * @author swift
 */
@Slf4j
public class HttpUtil {
    private HttpUtil() {
        throw new IllegalStateException("HttpUtil class");
    }
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String comma = ",";
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            log.info("Proxy-Client-IP:{}",ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            log.info("WL-Proxy-Client-IP:{}",ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            log.info("RemoteAddr:{}",ip);
        }
        if (ip.equals("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }
        // 如果ip里面包含",",则按逗号分隔，取第一个ip
        if(ip.contains(comma)){
            ip = ip.split(",")[0];
        }
        return ip;
    }

    /**
     * 获取访问的设备
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request){
        String userAgent = request.getHeader("user-agent").toLowerCase();;
        if(userAgent.contains("micromessenger")){
            return "微信";
        }else if(userAgent.contains("android")){
            return "安卓";
        }else if(userAgent.contains("iphone")|| userAgent.contains("ipad")|| userAgent.contains("ipod")){
            return "苹果";
        }else{
            return "电脑";
        }
    }
}

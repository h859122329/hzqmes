package com.gdglc.hzqmes.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author:ZhongGuoce
 * @date:2019-02-28
 * @time:08:31
 */
@Setter
@Component
@ConfigurationProperties(prefix = "url-patterns")
public class UrlPatterns {
    private String[] platformAdminPatterns;

    private String[] factoryAdminPatterns;

    private String[] financePatterns;

    private String[] customerPatterns;

    public String[] getPlatformAdminPatterns() {
        return platformAdminPatterns != null ? platformAdminPatterns : new String[0];
    }

    public String[] getFactoryAdminPatterns() {
        return factoryAdminPatterns !=null ? factoryAdminPatterns : new String[0];
    }

    public String[] getFinancePatterns() {
        return financePatterns != null ? financePatterns : new String[0];
    }

    public String[] getCustomerPatterns() {
        return customerPatterns !=null ? customerPatterns : new String[0];
    }
}


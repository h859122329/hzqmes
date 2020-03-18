package com.gdglc.hzqmes.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * @author:ZhongGuoce
 * @date:2019-03-10
 * @time:17:47
 */
@Configuration
public class PropertySourcesConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(
                new ClassPathResource("config/security/ignored.yml"),
                new ClassPathResource("config/security/single/platform-admin-patterns.yml"),
                new ClassPathResource("config/security/single/factory-admin-patterns.yml"),
                new ClassPathResource("config/security/single/finance-patterns.yml"),
                new ClassPathResource("config/security/single/customer-patterns.yml")
                );
        configurer.setProperties(yaml.getObject());
        return configurer;
    }
}



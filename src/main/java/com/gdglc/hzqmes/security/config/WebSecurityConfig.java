package com.gdglc.hzqmes.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdglc.hzqmes.common.AppConstants;
import com.gdglc.hzqmes.config.IgnoredUrlsProperties;
import com.gdglc.hzqmes.config.UrlPatterns;
import com.gdglc.hzqmes.security.CustomCorsFilter;
import com.gdglc.hzqmes.security.RestAuthenticationEntryPoint;
import com.gdglc.hzqmes.security.auth.ajax.AjaxAuthenticationProvider;
import com.gdglc.hzqmes.security.auth.ajax.AjaxAwareLogoutSuccessHandler;
import com.gdglc.hzqmes.security.auth.ajax.AjaxLoginProcessingFilter;
import com.gdglc.hzqmes.security.auth.jwt.JwtAuthenticationProvider;
import com.gdglc.hzqmes.security.auth.jwt.JwtTokenAuthenticationProcessingFilter;
import com.gdglc.hzqmes.security.auth.jwt.SkipPathRequestMatcher;
import com.gdglc.hzqmes.security.auth.jwt.extractor.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * WebSecurityConfig
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String AUTHENTICATION_URL = "/api/auth/login";
    public static final String UNAUTHENTICATION_URL = "/api/auth/logout";
    public static final String API_ROOT_URL = "/api/**";

    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Autowired
    private UrlPatterns urlPatterns;

    @Autowired private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired private AuthenticationSuccessHandler successHandler;
    @Autowired private AuthenticationFailureHandler failureHandler;
    @Autowired private AjaxAuthenticationProvider ajaxAuthenticationProvider;
    @Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired private AjaxAwareLogoutSuccessHandler logoutSuccessHandler;
    @Autowired private TokenExtractor tokenExtractor;

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private ObjectMapper objectMapper;

    protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter(String loginEntryPoint) throws Exception {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(loginEntryPoint, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) throws Exception {
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
        JwtTokenAuthenticationProcessingFilter filter
            = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // We don't need CSRF for JWT based authentication
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(this.authenticationEntryPoint)

            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
                .authorizeRequests()
                .antMatchers(ignoredUrlsProperties.getUrls().toArray(new String[ignoredUrlsProperties.getUrls().size()]))
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers(urlPatterns.getPlatformAdminPatterns()).hasRole(AppConstants.ROLE_PLATFORM_ADMIN)
                .antMatchers(urlPatterns.getFactoryAdminPatterns()).hasRole(AppConstants.ROLE_FACTORY_ADMIN)
                .antMatchers(urlPatterns.getFinancePatterns()).hasRole(AppConstants.ROLE_FINANCE)
                .antMatchers(urlPatterns.getCustomerPatterns()).hasRole(AppConstants.ROLE_CUSTOMER)
                // Protected API End-points
                .antMatchers(API_ROOT_URL).authenticated()
                .and()
                .logout().logoutUrl(UNAUTHENTICATION_URL)
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildAjaxLoginProcessingFilter(AUTHENTICATION_URL), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(ignoredUrlsProperties.getUrls(),
                API_ROOT_URL), UsernamePasswordAuthenticationFilter.class);
    }
}

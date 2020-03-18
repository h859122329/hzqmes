package com.gdglc.hzqmes.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.gdglc.hzqmes.annotation.RequestLimit;
import com.gdglc.hzqmes.common.AppConstants;
import com.gdglc.hzqmes.common.limit.RedisRequestLimiter;
import com.gdglc.hzqmes.exception.BusinessException;
import com.gdglc.hzqmes.security.model.UserContext;
import com.gdglc.hzqmes.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: 控制访问次数的拦截器类，配合注解使用</p>
 * @author swift
 */
@Slf4j
@Component
public class RequestLimitInterceptor implements HandlerInterceptor {

    @Value("${hzqmes.rateLimit.enable}")
    private boolean rateLimitEnable;

    @Value("${hzqmes.rateLimit.limit}")
    private Integer limit;

    @Value("${hzqmes.rateLimit.timeout}")
    private Integer timeout;

    @Autowired
    private RedisRequestLimiter redisRequestLimiter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 全局限流
        if(rateLimitEnable){
            String token1 = redisRequestLimiter.acquireTokenFromBucket(AppConstants.LIMIT_ALL, limit, timeout);
            if (StrUtil.isBlank(token1)) {
                throw new BusinessException("当前访问总人数太多啦，请稍后再试");
            }
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequestLimit requestLimit = handlerMethod.getMethodAnnotation(RequestLimit.class);
            if (requestLimit == null) {
                return true;
            }
            // ip限流
            //1.如果用户有登录，则获取用户的用户名作为point的一部分
            String username = "";
            SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = ctx.getAuthentication();
            if(auth !=null && auth.getPrincipal() instanceof UserContext){
                UserContext userContext = (UserContext) auth.getPrincipal();
                username = userContext.getUsername();
            }
            //2.获取访问用户的ip
            String ip = HttpUtil.getIpAddr(request);


            //3.获取请求的地址
            String url = request.getRequestURL().toString();
            int limit = requestLimit.limit();
            int timeout = requestLimit.timeout();
            String token2 = redisRequestLimiter.acquireTokenFromBucket(url.concat(ip).concat(username), limit, timeout);
            if (StrUtil.isBlank(token2)) {
                log.info("用户["+username+"]IP[" + ip + "]请求url地址[" + url + "]超过了限定的次数[" + requestLimit.limit() + "]");
                    throw new BusinessException("你手速怎么这么快，请点慢一点");
            }
        }
        return true;
    }
}

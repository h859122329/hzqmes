package com.gdglc.hzqmes.security.auth.ajax;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdglc.hzqmes.security.enums.ErrorCode;
import com.gdglc.hzqmes.security.exceptions.AuthMethodNotSupportedException;
import com.gdglc.hzqmes.security.exceptions.InactiveUserException;
import com.gdglc.hzqmes.security.exceptions.IncorrectPasswordsException;
import com.gdglc.hzqmes.security.exceptions.JwtExpiredTokenException;
import com.gdglc.hzqmes.security.vo.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@Component
@Slf4j
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final ObjectMapper mapper;
    private final StringRedisTemplate redisTemplate;

	@Value("${hzqmes.loginTimeLimit}")
	private Integer loginTimeLimit;

	@Value("${hzqmes.loginAfterTime}")
	private Integer loginAfterTime;

    @Autowired
    public AjaxAwareAuthenticationFailureHandler(ObjectMapper mapper,StringRedisTemplate redisTemplate) {
        this.mapper = mapper;
        this.redisTemplate = redisTemplate;
    }
    
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException e) throws IOException, ServletException {
		Integer httpStatusValue = HttpStatus.UNAUTHORIZED.value();
		ErrorResponse errorResponse;
		if (e instanceof BadCredentialsException) {
			httpStatusValue = HttpStatus.PAYMENT_REQUIRED.value();
			errorResponse = ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED);
		} else if (e instanceof UsernameNotFoundException || e instanceof IncorrectPasswordsException) {
			httpStatusValue = HttpStatus.PAYMENT_REQUIRED.value();
			String message;
			String username = request.getAttribute("username") !=null ? request.getAttribute("username").toString() : null;
			String key = "loginTimeLimit:"+username;
			String value = redisTemplate.opsForValue().get(key);
			if(StrUtil.isBlank(value)){
				value = "0";
			}
			//获取已登录错误次数
			int loginFailTime = Integer.parseInt(value)+1;
			int restLoginTime = loginTimeLimit - loginFailTime;
			if (restLoginTime > 0) {
				// 记录错误次数
				redisTemplate.opsForValue().set(key, String.valueOf(loginFailTime), loginAfterTime, TimeUnit.MINUTES);
				if (restLoginTime == 1) {
					redisTemplate.opsForValue().set("loginFailFlag:"+username, "fail", loginAfterTime, TimeUnit.MINUTES);
				}
			}
			if(restLoginTime<=3&&restLoginTime>0){
				message = "用户名或密码错误，还有"+restLoginTime+"次尝试机会";
			} else if(restLoginTime<=0) {
				message = "登录错误次数超过限制，请"+loginAfterTime+"分钟后再试";
			} else {
				message = "用户名或密码错误";
			}
			errorResponse = ErrorResponse.of(message, ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED);
		} else if (e instanceof JwtExpiredTokenException) {
			errorResponse = ErrorResponse.of(e.getMessage(), ErrorCode.JWT_TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED);
		} else if (e instanceof AuthMethodNotSupportedException || e instanceof InactiveUserException) {
			errorResponse = ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED);
		} else {
			errorResponse = ErrorResponse.of(e.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED);
		}
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setStatus(httpStatusValue);
		mapper.writeValue(response.getWriter(),errorResponse);
	}

}

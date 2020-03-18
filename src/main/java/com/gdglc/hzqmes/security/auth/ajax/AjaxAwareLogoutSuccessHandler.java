package com.gdglc.hzqmes.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdglc.hzqmes.security.auth.jwt.extractor.TokenExtractor;
import com.gdglc.hzqmes.security.config.JwtSettings;
import com.gdglc.hzqmes.security.config.WebSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AjaxAwareAuthenticationSuccessHandler
 * 
 * @author vladimir.stankovic
 *
 *         Aug 3, 2016
 */
@Component
@Slf4j
public class AjaxAwareLogoutSuccessHandler implements LogoutSuccessHandler{
    private final ObjectMapper mapper;
    private final StringRedisTemplate redisTemplate;
    private final JwtSettings jwtSettings;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;

    @Autowired
    public AjaxAwareLogoutSuccessHandler(final ObjectMapper mapper, final JwtSettings jwtSettings, StringRedisTemplate redisTemplate) {
        this.mapper = mapper;
        this.redisTemplate = redisTemplate;
        this.jwtSettings = jwtSettings;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(null);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("logoutSuccessUrl", "/login");

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        mapper.writeValue(response.getWriter(), tokenMap);

        clearAuthenticationAttributes(request);

    }


    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     * 
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}

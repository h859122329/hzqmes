package com.gdglc.hzqmes.security.auth.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdglc.hzqmes.po.Users;
import com.gdglc.hzqmes.security.model.UserContext;
import com.gdglc.hzqmes.security.model.token.JwtToken;
import com.gdglc.hzqmes.security.model.token.JwtTokenFactory;
import com.gdglc.hzqmes.service.UsersService;
import com.gdglc.hzqmes.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AjaxAwareAuthenticationSuccessHandler
 * 
 * @author vladimir.stankovic
 *
 *         Aug 3, 2016
 */
@Component
public class AjaxAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper mapper;
    private final JwtTokenFactory tokenFactory;
    private final UsersService usersService;
    private final StringRedisTemplate redisTemplate;


    @Autowired
    public AjaxAwareAuthenticationSuccessHandler(final ObjectMapper mapper, final JwtTokenFactory tokenFactory,StringRedisTemplate redisTemplate, final UsersService usersService) {
        this.mapper = mapper;
        this.tokenFactory = tokenFactory;
        this.usersService = usersService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        UserContext userContext = (UserContext) authentication.getPrincipal();

        String loginTimeLimitKey = "loginTimeLimit:"+userContext.getUsername();

        // 登录成功后清除登录失败的错误次数
        if (StringUtils.isNotBlank(redisTemplate.opsForValue().get(loginTimeLimitKey))) {
            redisTemplate.delete(loginTimeLimitKey);
        }

        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);

        Map<String, Object> tokenMap = new HashMap<>(16);
        tokenMap.put("avatar", userContext.getAvatar());
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("nickname", userContext.getNickname());
        tokenMap.put("authorities", userContext.getAuthorities().stream()
                .map(authority -> authority.getAuthority()).collect(Collectors.joining(",")));

        // 更新用户最后一次登录的时间和ip
        Users updateUsers = new Users();
        updateUsers.setUsername(userContext.getUsername());
        updateUsers.setLastIp(HttpUtil.getIpAddr(request));
        updateUsers.setLastTime(LocalDateTime.now());
        usersService.updateUserLoginTimeAndIp(updateUsers);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
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

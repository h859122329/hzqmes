package com.gdglc.hzqmes.security.auth.ajax;

import cn.hutool.core.util.StrUtil;
import com.gdglc.hzqmes.po.Users;
import com.gdglc.hzqmes.security.exceptions.InactiveUserException;
import com.gdglc.hzqmes.security.exceptions.IncorrectPasswordsException;
import com.gdglc.hzqmes.security.model.UserContext;
import com.gdglc.hzqmes.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@Component
@Slf4j
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder encoder;
    private final UsersService usersService;
    private final StringRedisTemplate redisTemplate;


    @Autowired
    public AjaxAuthenticationProvider(final UsersService usersService, final BCryptPasswordEncoder encoder, final StringRedisTemplate redisTemplate) {
        this.usersService = usersService;
        this.encoder = encoder;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        String flagKey = "loginFailFlag:"+username;
        String value = redisTemplate.opsForValue().get(flagKey);
        if(StrUtil.isNotBlank(value)){
            Long expire = redisTemplate.getExpire(flagKey, TimeUnit.MINUTES);
            //超过限制次数
            if (expire != null) {
                throw new BadCredentialsException("登录错误次数超过限制，请"+expire+"分钟后再试");
            }
            throw new BadCredentialsException("登录错误次数超过限制，请稍后再试");
        }


        // 校验用户是否存在，不存在则抛异常
        Users user = usersService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在: " + username));
        if (user.getStatus() == -1) {
            throw new InactiveUserException("用户未被激活，请联系管理员");
        }

        // 校验密码是否正确
        if (!encoder.matches(password, user.getPassword())) {
            throw new IncorrectPasswordsException("密码错误,请重试");
        }

        if (user.getRoles() == null) {throw new InsufficientAuthenticationException("User has no roles assigned");}

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getUsername(), authorities, user.getFace(), user.getNickname());
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

package com.gdglc.hzqmes.security.auth.jwt;

import com.gdglc.hzqmes.exception.BusinessException;
import com.gdglc.hzqmes.security.auth.JwtAuthenticationToken;
import com.gdglc.hzqmes.security.config.JwtSettings;
import com.gdglc.hzqmes.security.exceptions.JwtExpiredTokenException;
import com.gdglc.hzqmes.security.model.UserContext;
import com.gdglc.hzqmes.security.model.token.RawAccessJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * An {@link AuthenticationProvider} implementation that will use provided
 * instance of {@link } to perform authentication.
 * 
 * @author vladimir.stankovic
 *
 * Aug 5, 2016
 */
@Component
@Slf4j
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtSettings jwtSettings;
    
    @Autowired
    public JwtAuthenticationProvider(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        try{
            String nullStr = "null";
            if (nullStr.equals(rawAccessToken.getToken())) {
                log.error("Invalid JWT Token");
                throw new BadCredentialsException("无效的登录凭证");
            }

            Jws<Claims>  jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
            String subject = jwsClaims.getBody().getSubject();
            List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
            List<GrantedAuthority> authorities = scopes.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UserContext context = UserContext.create(subject, authorities);
            return new JwtAuthenticationToken(context, context.getAuthorities());
        }catch (BusinessException e) {
            log.info("JWT Token is expired", e);
            throw new JwtExpiredTokenException(rawAccessToken, "登录凭证已过期,请重新登录", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

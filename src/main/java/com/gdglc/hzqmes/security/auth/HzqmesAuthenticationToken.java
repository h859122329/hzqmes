package com.gdglc.hzqmes.security.auth;

import com.gdglc.hzqmes.security.auth.ajax.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author:ZhongGuoce
 * @date:2019-01-22
 * @time:17:49
 */
public class HzqmesAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private LoginRequest loginRequest;

    public HzqmesAuthenticationToken(LoginRequest loginRequest) {
        super(loginRequest.getUsername(), loginRequest.getPassword());
        this.loginRequest = loginRequest;
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }
}

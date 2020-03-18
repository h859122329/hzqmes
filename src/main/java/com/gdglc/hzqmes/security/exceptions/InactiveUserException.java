package com.gdglc.hzqmes.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * @author:ZhongGuoce
 * @date:2019-03-18
 * @time:09:51ss
 */
public class InactiveUserException extends AuthenticationException {

    public InactiveUserException(String msg) {
        super(msg);
    }
}

package com.gdglc.hzqmes.security.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * @author swift
 */
public class IncorrectPasswordsException extends AuthenticationException {
    public IncorrectPasswordsException(String msg) {
        super(msg);
    }
}

package com.gdglc.hzqmes.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdglc.hzqmes.enums.ResultEnum;
import com.gdglc.hzqmes.response.ResultResponse;
import com.gdglc.hzqmes.security.enums.ErrorCode;
import com.gdglc.hzqmes.security.exceptions.InactiveUserException;
import com.gdglc.hzqmes.security.vo.ErrorResponse;
import com.gdglc.hzqmes.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private  ObjectMapper mapper;
    /**
     * 异常报错
     */
    @ExceptionHandler(value=Exception.class)
    @ResponseBody
    public void handleException(Exception exception)
    {
        log.error("error",exception);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes !=null && attributes.getResponse() !=null) {
            HttpServletResponse response = attributes.getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            ResultResponse resultResponse = null;
            if (exception instanceof UsernameNotFoundException || exception instanceof InactiveUserException) {
                if (exception instanceof UsernameNotFoundException) {
                    response.setStatus(HttpStatus.PAYMENT_REQUIRED.value());
                } else {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                }
                try {
                    mapper.writeValue(response.getWriter(), ErrorResponse.of(exception.getMessage(), ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
                } catch (IOException e) {
                    log.error("error", e);
                }
            } else {
                response.setStatus(HttpStatus.NOT_IMPLEMENTED.value());
                if (exception instanceof BusinessException) {
                    BusinessException businessException = (BusinessException) exception;
                    if (businessException.getCode() != 9999) {
                        resultResponse = ResultVOUtil.error(businessException.getMessage(), businessException.getCode(), businessException.getData());
                    }
                }
                if (resultResponse == null) {
                    resultResponse = ResultVOUtil.error(ResultEnum.SERVER_ERROR.getMessage());
                }
                try {
                    mapper.writeValue(response.getWriter(), resultResponse);
                } catch (IOException e) {
                    log.error("error", e);
                }
            }
        }
    }
}

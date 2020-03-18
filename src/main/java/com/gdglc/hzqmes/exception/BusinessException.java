package com.gdglc.hzqmes.exception;

import com.gdglc.hzqmes.enums.ResultEnum;

public class BusinessException extends  RuntimeException{
    private final Integer code;
    private final Object data;

    public BusinessException(String message) {
        super(message);
        this.code = 500;
        this.data = null;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.code = 9999;
        this.data = null;
    }

    public BusinessException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.data = null;
    }
    public BusinessException(ResultEnum resultEnum, Object data){
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
        this.data = data;
    }
    public BusinessException(Integer code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }
    public Object getData() {
        return this.data;
    }
}

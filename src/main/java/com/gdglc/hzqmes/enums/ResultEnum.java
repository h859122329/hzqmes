package com.gdglc.hzqmes.enums;

public enum ResultEnum {
    /**
     * 服务器异常，请联系管理员
     */
    SERVER_ERROR(0, "服务器异常，请联系管理员"),

    /**
     * 参数不正确
     */
    PARAM_ERROR(1, "参数不正确"),

    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(2,"数据不存在"),

    /**
     * 数据重复录入
     */
    DATA_DUPLICATION(3,"数据重复录入"),

    /**
     * 该用户不存在
     */
    USER_NOT_EXIST(4,"该用户不存在"),

    /**
     * 用户密码不正确
     */
    APPUSER_PASSWORD_WRONG(5,"用户密码不正确"),

    /**
     * HTTP请求超出设定的限制
     */
    REQUEST_LIMIT_ERROR(6,"HTTP请求超出设定的限制"),

    /**
     * 文件上传失败
     */
    FILE_UPLOAD_ERROR(7, "文件上传失败，请联系管理员"),

    /**
     * excel文件数据错误
     */
    EXCEL_UPLOAD_ERROR(8, "学生信息导入失败，请按准确填写学生信息"),

    /**
     * excel文件数据错误
     */
    EXCEL_UPLOAD_NULLERROR(9, "学生信息导入失败，每位学生信息不能留空"),

    /**
     * 非法请求
     */
    ILLEGAL_SUBMIT(10,"非法请求"),

    /**
     * 数据状态异常
     */
    DATA_STATUS_ERROR(11,"数据状态异常"),

    /**
     * 缺少所需数据项
     */
    DATA_FIELD_MISSING(12, "缺少所需数据项"),

    /**
     * 用户名已存在
     */
    USERNAME_ALREADY_EXISTS(13,"用户名已存在");

    private Integer code;

    private String message;


    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}

package com.gdglc.hzqmes.annotation;

import java.lang.annotation.*;

/**
 * <p>Title:  ip限流注解</p>
 * @author swift
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {
    /**
     *
     * 允许访问的次数，默认值5
     */
    int limit() default 5;

    /**
     *
     * 时间段，单位为毫秒，默认值1秒
     */
    int timeout() default 1000;
}

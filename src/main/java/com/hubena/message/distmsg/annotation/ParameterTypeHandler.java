package com.hubena.message.distmsg.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息通知类消息接收方法注解，用于标识消息接收方法取到参数类型.
 * @author 曾谢波
 * 2018年7月4日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ParameterTypeHandler {

}
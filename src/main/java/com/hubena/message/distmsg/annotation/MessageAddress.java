package com.hubena.message.distmsg.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息通知消息接收类注解，与{@link @ParameterTypeHandler}相配合
 * @author 曾谢波
 * 2018年7月4日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageAddress {
 /**
  * 消息地址值
  * @return
  */
 String value() default "";
}
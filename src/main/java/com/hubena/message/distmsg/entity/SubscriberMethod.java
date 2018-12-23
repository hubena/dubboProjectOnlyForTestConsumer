package com.hubena.message.distmsg.entity;

import java.lang.reflect.Method;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



/**
 * 存放订阅者即最终消息接受者类逻辑处理方法.
 * @author 曾谢波
 * 2018年7月4日
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SubscriberMethod {
 /**
  * 订阅者类 ,被注解{@link @MessageAddress}修饰
  */
 private Object subscriber;
 /**
  * 订阅者，被注解{@link @ParameterTypeHandler}修饰
  */
 private Method method;
 /**
  * 方法参数是否为String类型
  */
 private Boolean isStringParameter;
 
 public Object getSubscriber() {
  return subscriber;
 }
 public void setSubscriber(Object subscriber) {
  this.subscriber = subscriber;
 }
 public Method getMethod() {
  return method;
 }
 public void setMethod(Method method) {
  this.method = method;
 }
 public Boolean getIsStringParameter() {
  return isStringParameter;
 }
 public void setIsStringParameter(Boolean isStringParameter) {
  this.isStringParameter = isStringParameter;
 }
 @Override
 public String toString() {
  return "SubscriberMethod [subscriber=" + subscriber + ", method="
    + method + ", isStringParameter=" + isStringParameter + "]";
 }
 
}
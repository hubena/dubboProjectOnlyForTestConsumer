package com.hubena.message.distmsg.configuration;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.hubena.message.distmsg.annotation.MessageAddress;
import com.hubena.message.distmsg.annotation.ParameterTypeHandler;
import com.hubena.message.distmsg.entity.CustodyMessageContent;
import com.hubena.message.distmsg.entity.SubscriberMethod;
import com.hubena.message.distmsg.subscriber.list.ICustodySubscriberList;

/**
 * 得到容器并在容器事件中操作
 * @author 曾谢波
 * 2018年6月1日
 */

@Component
public class MessageNotificationSpringEvent implements ApplicationContextAware, ApplicationListener<ApplicationContextEvent>{
 private static Logger logger = LoggerFactory.getLogger(MessageNotificationSpringEvent.class);
 private ApplicationContext applicationContext;
 private Boolean contextRefreshBoolean = false;
 //订阅者列表存放Map
 private ConcurrentHashMap<String, ICustodySubscriberList> subscriberListHashMap;
 
 //获取spring上下文
 @Override
 public void setApplicationContext(ApplicationContext applicationContext) {
  this.applicationContext = applicationContext;
 }
 
 public ConcurrentHashMap<String, ICustodySubscriberList> getSubscriberListHashMap() {
  return subscriberListHashMap;
 }
 
  /**
   * 容器加载完成及刷新事件
   */
 @Override
 public void onApplicationEvent(ApplicationContextEvent event) {
  if (this.applicationContext == event.getApplicationContext() && event.getApplicationContext().getParent() == null) {
	// 如果是容器初始化刷新事件则注册订阅者列表
   if (event instanceof ContextRefreshedEvent) {
    if (contextRefreshBoolean) {
     return;
    }
    registerObserver();
    contextRefreshBoolean = true;
    return;
   }
  }
 }
 
 /**
   * 注册订阅者
   */
 private void registerObserver(){
  
  //订阅者实现类
  Map<String, Object> messageAddressMap = applicationContext.getBeansWithAnnotation(MessageAddress.class);
  if (messageAddressMap == null || messageAddressMap.isEmpty()) {
   logger.info("订阅者注册失败，订阅者为空.");
   return;
  }
  subscriberListHashMap = new ConcurrentHashMap<String, ICustodySubscriberList>();
  Collection<Object> subscribers = messageAddressMap.values();
  
  for (Object subscriber : subscribers) {
   SubscriberMethod subscriberMethod = applicationContext.getBean(SubscriberMethod.class);
   try {
    getSubscriberMethod(subscriber, subscriberMethod);
   } catch (Exception e) {
    logger.error("加载订阅者方法失败：{}", e);
    continue;
   }
   String messageAddress = getMessageAddress(subscriber.getClass());
   if (subscriberListHashMap.containsKey(messageAddress)) {
    subscriberListHashMap.get(messageAddress).registerSubscriberMethod(subscriberMethod);
    continue;
   }
   ICustodySubscriberList custodySubscriberList = (ICustodySubscriberList) applicationContext.getBean(ICustodySubscriberList.class);
   custodySubscriberList.setMessageAddress(messageAddress);
   custodySubscriberList.registerSubscriberMethod(subscriberMethod);
   subscriberListHashMap.put(messageAddress, custodySubscriberList);
  }
  logger.info("subscriber注册成功.");
 }
 
  /**
   * 取到订阅者类方法实体类
   * @param subscriber
   * @param subscriberMethod
   * @return
   * @throws Exception
   */
 private void getSubscriberMethod(Object subscriber, SubscriberMethod subscriberMethod) throws Exception {
  byte methodCount = 0;
  Method[] methods = subscriber.getClass().getDeclaredMethods();
  for (Method method : methods) {
   Annotation annotation = method.getAnnotation(ParameterTypeHandler.class);
   if (annotation == null) {
    continue;
   }
   methodCount++;
   if (methodCount > 1) {
    throw new Exception("同一个类中@ParameterTypeHandler注解修饰的方法只能有一个.");
   }
   
   Class<?>[] parameterClass = method.getParameterTypes();
   if (parameterClass.length != 1) {
    throw new Exception("@ParameterTypeHandler注解修饰的方法参数数量只能为1.");
   }
   subscriberMethod.setSubscriber(subscriber);
   subscriberMethod.setMethod(method);
   // 如果传过来的消息实体类型与方法参数类型相同或是其子类
   if (CustodyMessageContent.class.isAssignableFrom(parameterClass[0])) {
    subscriberMethod.setIsStringParameter(false);
   } else if (String.class.isAssignableFrom(parameterClass[0])) { // 如果方法参数为String则穿Json字符串给业务方法，反序列化由其自己完成，用于复杂嵌套或者集合实体类
    subscriberMethod.setIsStringParameter(true);
   } else {
    throw new Exception("@ParameterTypeHandler娉ㄨВ淇グ鐨勬柟娉曞弬鏁扮被鍨嬪彧鑳戒负String绫诲瀷鎴栬�匔ustodyMessageContent绫诲瀷");
   }
  }
  if (methodCount == 0) {
   throw new Exception("被注解@MessageAddress修饰的类必须要有一个被@ParameterTypeHandler注解修饰的方法");
  }
 }
 
 private String getMessageAddress(Class<?> classs) {
  String messageAddressValue = new String();
  if (classs.isAnnotationPresent(MessageAddress.class)) {
   MessageAddress messageAddress = classs.getAnnotation(MessageAddress.class);
   messageAddressValue = messageAddress.value();
  }
  return messageAddressValue;
 }
}
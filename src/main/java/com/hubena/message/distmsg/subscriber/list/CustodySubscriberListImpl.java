package com.hubena.message.distmsg.subscriber.list;

import java.lang.reflect.Type;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.hubena.message.distmsg.entity.SubscriberMethod;

/**
 * 缓存刷新事件订阅者列表维护类
 * @author 曾谢波
 * 2018年5月31日
 */
@Component(value = "custodySubscriberListImpl")
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustodySubscriberListImpl implements ICustodySubscriberList{
 private static final Logger logger = LoggerFactory.getLogger(CustodySubscriberListImpl.class);
 protected CopyOnWriteArrayList<SubscriberMethod> subscriberMethodList = new CopyOnWriteArrayList<SubscriberMethod>();
 // 消息地址
 protected String messageAddress;
 @Override
 public void registerSubscriberMethod(SubscriberMethod subscriberMethod) {
  subscriberMethodList.add(subscriberMethod);
 }

 @Override
 public void removeSubscriberMethod(SubscriberMethod subscriberMethod) {
  subscriberMethodList.remove(subscriberMethod);
 }

 @Override
 public void notifySubscriber(String jsonString, String typeId) throws Exception {
//  Class<?> messageTypeClass = Class.forName(typeId); // 后续看是否需要消息实体类类型
  ObjectMapper jsonObjectMapper = new ObjectMapper();
  Object contentObject = null;
  for (SubscriberMethod subscriberMethod : subscriberMethodList) {
   
   if (subscriberMethod.getIsStringParameter()) {
    subscriberMethod.getMethod().invoke(subscriberMethod.getSubscriber(), jsonString);
   } else {
    Type[] types = subscriberMethod.getMethod().getGenericParameterTypes();
    JavaType javaType = TypeFactory.defaultInstance().constructType(types[0]);
    contentObject = jsonObjectMapper.readValue(jsonString, javaType);
    logger.debug("反序列化之后的消息实体类型：{}", contentObject.toString());
    // 执行业务方法
    subscriberMethod.getMethod().invoke(subscriberMethod.getSubscriber(), contentObject);
   }
  }
 }
 
 @Override
 public CopyOnWriteArrayList<SubscriberMethod> getSubscriberMethodList() {
  return subscriberMethodList;
 }

 @Override
 public String getMessageAddress() {
  return messageAddress;
 }

 @Override
 public void setMessageAddress(String messageAddress) {
  this.messageAddress = messageAddress;
 }

}
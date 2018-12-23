package com.hubena.message.distmsg.subscriber.list;

import java.util.concurrent.CopyOnWriteArrayList;

import com.hubena.message.distmsg.entity.SubscriberMethod;

/**
 * 维护订阅者列表并通知订阅者消息
 * @author 曾谢波
 * 2018年5月31日
 */
public interface ICustodySubscriberList {
 /**
  * 注册订阅者方法实体类到订阅者列表中
  * @param subscriberMethod
  */
 void registerSubscriberMethod(SubscriberMethod subscriberMethod);
 /**
  * 删除订阅者方法实体类
  * @param subscriber
  */
 void removeSubscriberMethod(SubscriberMethod subscriberMethod);

 /**
  * 通知注册到列表中的订阅者
  * @param jsonString
  * @param typeId 消息实体类类型
  * @throws Exception
  */
 void notifySubscriber(String jsonString, String typeId) throws Exception;
 
 /**
  * 获取订阅者方法实体类列表
  * @return
  */
 public CopyOnWriteArrayList<SubscriberMethod> getSubscriberMethodList();

 /**
  * 获取消息地址
  * @return
  */
 public String getMessageAddress();

 /**
  * 设置消息地址
  * @param messageAddress
  */
 public void setMessageAddress(String messageAddress);
 
}
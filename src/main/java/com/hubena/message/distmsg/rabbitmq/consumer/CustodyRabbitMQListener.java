package com.hubena.message.distmsg.rabbitmq.consumer;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.hubena.message.distmsg.configuration.MessageNotificationSpringEvent;
import com.hubena.message.distmsg.configuration.SendMessageModeCondition;
import com.hubena.message.distmsg.constant.MessageConstant;
import com.hubena.message.distmsg.subscriber.list.ICustodySubscriberList;


@Component
@Conditional(value = {SendMessageModeCondition.class})
public class CustodyRabbitMQListener {
 private static final Logger logger = LoggerFactory.getLogger(CustodyRabbitMQListener.class);
 @Autowired
 private MessageNotificationSpringEvent springEvent;
 
 @RabbitListener(queues = {"#{refreshQueue.getName()}"})
 public void rabbitMQListenerHandler(Message message) {
  try {
   String messageJson = new String(message.getBody(), message.getMessageProperties().getContentEncoding());
   if (messageJson == null || messageJson.isEmpty()) {
    logger.error("收到消息通知，但消息内容为空！");
    return;
   }
   
   Map<String, Object> headerMap = message.getMessageProperties().getHeaders();
   String messageAddress = headerMap.get(MessageConstant.MESSAGE_ADDRESS).toString();
   if (messageAddress == null || messageAddress.isEmpty()) {
    logger.error("messageAddress为空！");
    return;
   }
   
   ConcurrentHashMap<String, ICustodySubscriberList> subscriberListMap = springEvent.getSubscriberListHashMap();
   if (subscriberListMap == null || subscriberListMap.isEmpty() || !subscriberListMap.containsKey(messageAddress)) {
    logger.error("消息注册者为空或消息事件不存在.");
    return;
   }
   
   ICustodySubscriberList subscriberList = subscriberListMap.get(messageAddress);
   if (subscriberList.getSubscriberMethodList().isEmpty()) {
    logger.error("订阅者为空，未设置订阅者类.");
    return;
   }
   // 取到消息实体类类型
   String typeId = (String) message.getMessageProperties().getHeaders().get(AbstractJavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME);
   // 通知订阅者，执行真正的业务逻辑
   subscriberList.notifySubscriber(messageJson, typeId);
   
   logger.info("收到消息并成功送达订阅者,消息内容为：{}", messageJson);
  } catch (UnsupportedEncodingException unsupportedEncodingException) {
   logger.error("Rabbitmq接收消息转码报错：{}", unsupportedEncodingException);
  } catch (Exception exception) {
   logger.error("Rabbitmq接收处理消息失败{}", exception);
  }
 }
}
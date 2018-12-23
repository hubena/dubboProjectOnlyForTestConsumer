package com.hubena.message.distmsg.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hubena.message.distmsg.entity.CustodyMessageContent;
import com.hubena.message.distmsg.entity.RabbitMQProperty;
import com.hubena.message.distmsg.entity.SocketNotificationProperty;
import com.hubena.message.distmsg.rabbitmq.producer.ICustodyRabbitMQPublisher;
import com.hubena.message.distmsg.socket.request.publisher.ICustodySocketPublisher;

@Service
public class CustodyPublisherMessageImpl implements ICustodyPublisherMessage {
 @SuppressWarnings("unused")
 private static final Logger log = LoggerFactory.getLogger(CustodyPublisherMessageImpl.class);
 @Autowired
 private SocketNotificationProperty messageProperty;
 @Autowired
 private RabbitMQProperty rabbitMQProperty;
// @Autowired
// private ICustodySocketPublisher custodySocketPublisher;
 @Autowired
 private ICustodyRabbitMQPublisher publisher;
 
 @Override
 public <T> void sendMessage(String messageAddress, CustodyMessageContent<T> messageContent) throws Exception {
  if (messageContent == null || messageAddress == null || messageAddress.isEmpty()) {
   throw new Exception("messageAddress or messageContent is empty");
  }
  // 判断是否为发送socket消息
//  if (messageProperty.isSendSocket()) {
//   custodySocketPublisher.sendSocketMessage(messageAddress, messageContent);
//   return;
//  }
  // 发送RabbitMQ消息
  publisher.sendDistributedFanoutMessageForMultiClass(rabbitMQProperty.getFanoutExchange(), messageAddress, messageContent);
 }

}
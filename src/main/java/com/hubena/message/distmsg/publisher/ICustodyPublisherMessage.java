package com.hubena.message.distmsg.publisher;

import com.hubena.message.distmsg.entity.CustodyMessageContent;

public interface ICustodyPublisherMessage {
 
 /**
  * 发送消息方法，有两种选项，发送socket或者发送RabbitMQ消息，
  * 通过message_notification_socket.properties文件中isSendSocket属性配置
  * @param <T>
  * @param messageAddress 消息地址
  * @param messageContent 发送的消息实体类
  * @throws Exception
  */
 <T> void sendMessage(String messageAddress, CustodyMessageContent<T> messageContent) throws Exception;
}
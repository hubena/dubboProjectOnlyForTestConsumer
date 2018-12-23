package com.hubena.message.distmsg.socket.request.publisher;

import com.hubena.message.distmsg.entity.CustodyMessageContent;

public interface ICustodySocketPublisher {
 
 /**
  * 发送Socket消息
  * @param <T>
  * @param messageAddress 消息地址
  * @param messageContent 消息内容
  */
 <T> void sendSocketMessage(String messageAddress, CustodyMessageContent<T> messageContent);
}
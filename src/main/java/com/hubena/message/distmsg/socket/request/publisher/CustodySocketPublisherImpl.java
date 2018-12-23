package com.hubena.message.distmsg.socket.request.publisher;
/*package com.hubena.distmsg.socket.request.publisher;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hubena.distmsg.constant.MessageConstant;
import com.hubena.distmsg.constant.MessageEventIdConstant;
import com.hubena.distmsg.entity.CustodyMessageContent;
import com.hubena.distmsg.entity.SocketNotificationProperty;
import com.hubena.distmsg.socket.request.task.ICustodySocketAsyncSendMessageTask;
import com.hubena.distmsg.util.JsonUtil;

import server.Profile;
import core.AppContext;
import frame.Request;

@Component
public class CustodySocketPublisherImpl implements ICustodySocketPublisher {
 private static Logger logger = LoggerFactory.getLogger(CustodySocketPublisherImpl.class);
 @Autowired
 private SocketNotificationProperty messageProperty;
 @Autowired
 private ICustodySocketAsyncSendMessageTask socketAsyncSendMessageTask;
 @Autowired
 private ICustodySocketClient custodySocketClient;
 
 @Override
 public <T> void sendSocketMessage(String messageAddress, CustodyMessageContent<T> messageContent) {
  List<String> ipList = messageProperty.getMessageIPList();
  if (ipList == null || ipList.isEmpty()) {
   logger.error("MessageIPList is Null");
   return;
  }
  String port = messageProperty.getPort();
  if (port == null || port.isEmpty()) {
   logger.error("Port is Null");
   return;
  }
  
  String type = messageProperty.getType();
  if (type == null || type.isEmpty()) {
   logger.error("type is Null");
   return;
  }
  
  Profile pfProfile = AppContext.getApplicationContext().getServerFile();
  //组装发送数据
  Request request = custodySocketClient.getRequest(type,
    MessageEventIdConstant.MESSAGE_DISTRIBUTE_HANDLER_ADDRESS, " ", " ", pfProfile);
  String jsonString = JsonUtil.objectToJsonString(messageContent);
  request.putBin(MessageConstant.MESSAGE_ITF, MessageConstant.MESSAGE_CONTENT_FLD, jsonString);
  request.putBin(MessageConstant.MESSAGE_ITF, MessageConstant.MESSAGE_ADDRESS_FLD, messageAddress);
  request.putBin(MessageConstant.MESSAGE_ITF, MessageConstant.MESSAGE_CLASS_TYPE_FLD, messageContent.getClass().getName());
  
  try {
   for (String host : ipList) {
    // 异步执行消息发送
    socketAsyncSendMessageTask.socketSendMessageAsync(host, Integer.valueOf(port), request);
   }
   
  } catch (RejectedExecutionException e) {
   logger.error("Socket发送信息失败：{}", e.getMessage());
  } catch (Exception e) {
   logger.error("Socket发送消息错误信息为：{}", e);
  }
 }
}*/
package com.hubena.message.distmsg.socket.request.task;
/*package com.hubena.distmsg.socket.request.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hubena.distmsg.constant.MessageConstant;
import com.hubena.distmsg.socket.request.publisher.ICustodySocketClient;

import frame.Request;
import frame.Response;

*//**
 * 异步方法类，方法使用{@link @Async}自动调用线程池执行方法.
 * @author 曾谢波
 * 2018年7月12日
 *//*
@Component
public class CustodySocketAsyncSendMessageTaskImpl implements ICustodySocketAsyncSendMessageTask {
 private static Logger log = LoggerFactory.getLogger(CustodySocketAsyncSendMessageTaskImpl.class);
 @Autowired
 private ICustodySocketClient custodySocketClient;
 
 @Async
 @Override
 public void socketSendMessageAsync(String host, int port, Request request) {
  try {
   Response response = custodySocketClient.getResponse(host, port, request);
   if (!response.isSuccess()) {
    log.info("发送消息失败，返回信息为：{}",response.getRetMessage());
    return;
   }
   String responseMessage = response.getVar(MessageConstant.MESSAGE_ITF,
     MessageConstant.MESSAGE_RESPONSE_CODE_FLD, 0);
   log.info("host:{},发送消息返回处理消息为：{}", host, responseMessage);
  } catch (Exception e) {
   log.error("发送消息通知报错{}", e);
  }
 }
}*/
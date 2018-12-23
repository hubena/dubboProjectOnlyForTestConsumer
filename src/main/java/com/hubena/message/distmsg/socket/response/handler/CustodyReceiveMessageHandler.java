package com.hubena.message.distmsg.socket.response.handler;
/*package com.hubena.distmsg.socket.response.handler;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.hubena.distmsg.configuration.MessageNotificationSpringEvent;
import com.hubena.distmsg.constant.MessageConstant;
import com.hubena.distmsg.constant.MessageEventIdConstant;
import com.hubena.distmsg.subscriber.list.ICustodySubscriberList;

import ccs.CcsBase;
import frame.Request;
import frame.Response;

*//**
 * 消息接收方处理类Prid，prid值即controller中Value值必须为10位.
 * @author 曾谢波
 * 2018年5月31日
 *//*
@Controller(value = MessageEventIdConstant.MESSAGE_DISTRIBUTE_HANDLER_ADDRESS)
public class CustodyReceiveMessageHandler extends CcsBase {
 private static Logger logger = LoggerFactory.getLogger(CustodyReceiveMessageHandler.class);
 
 @Autowired
 private MessageNotificationSpringEvent springEvent;
 
 public Response doProc(Connection conn, Request req) throws Exception {
  Response res = req.createResponse();
  String ip = new String(req.getBin(MessageConstant.BSVRHEAD_ITF, MessageConstant.RQIPAD_FLD), MessageConstant.ENCODING);
  logger.info("收到{}消息通知.", ip);

  //传过来的消息地址，用于匹配Subscriber订阅者
  String messageAddress = getRequestData(req, MessageConstant.MESSAGE_ADDRESS_FLD);
  if (messageAddress == null || messageAddress.isEmpty()) {
   throw new Exception("EventId为空");
  }
  ConcurrentHashMap<String, ICustodySubscriberList> subscriberListMap = springEvent.getSubscriberListHashMap();
  if (subscriberListMap == null || subscriberListMap.isEmpty() || !subscriberListMap.containsKey(messageAddress)) {
   throw new Exception("消息注册者为空或消息事件不存在.");
  }
  
  ICustodySubscriberList subscriberList = subscriberListMap.get(messageAddress);
  if (subscriberList.getSubscriberMethodList().isEmpty()) {
   throw new Exception("订阅者为空，未设置订阅者类.");
  }
  String dataJson = getRequestData(req, MessageConstant.MESSAGE_CONTENT_FLD);
  if (dataJson == null || dataJson.isEmpty()) {
   throw new Exception("收到消息通知，但消息内容为空！");
  }
  // 取到消息实体类类型
  String typeId = getRequestData(req, MessageConstant.MESSAGE_CLASS_TYPE_FLD);

  subscriberList.notifySubscriber(dataJson, typeId);
  
  putResponseMessage(res, MessageConstant.MESSAGE_RESPONSE_CODE_FLD, "消息成功送达订阅者.");
  logger.debug("收到消息并成功送达订阅者,消息内容为：{}", dataJson);
  return res;
 }
 
 *//**
  * 获取request中message数据.
  * @param request
  * @param dataFld 数据在request变长VL的fld中存放位置名
  * @return
  * @throws UnsupportedEncodingException
  *//*
 private String getRequestData(Request request, String dataFld) throws UnsupportedEncodingException{
  byte[] bytes = request.getBin(MessageConstant.MESSAGE_ITF, dataFld);
  if (bytes == null) {
   return null;
  }
  String data  = new String(bytes, MessageConstant.ENCODING);
  return data;
 }
 
 
 *//**
  * 将消息放入response中.
  * @param response
  * @param responseFld 返回的的消息内容在response变长VL的fld中存放位置名
  * @param responseMessage
  *//*
 private void putResponseMessage(Response response, String responseFld, String responseMessage){
  response.putBin(MessageConstant.MESSAGE_ITF, responseFld, responseMessage);
 }


}*/
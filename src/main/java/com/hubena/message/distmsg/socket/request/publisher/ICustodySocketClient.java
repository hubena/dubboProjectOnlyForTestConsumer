package com.hubena.message.distmsg.socket.request.publisher;
/*package com.hubena.distmsg.socket.request.publisher;

import server.Profile;
import frame.Request;
import frame.Response;

*//**
 * 用于发送Socket消息
 * @author 曾谢波
 * @since 2018年8月28日
 *//*
public interface ICustodySocketClient {
 
 *//**
  * 用于组装要发送的消息实体
  * @param type 发送到接收方用于识别分发处理类，在service.text文件中有配置：[service]ccs=ccs.CcsController
  * @param prid 接收端处理类Controller的bean名
  * @param userId 可以为空，但是至少为长度为1的空字符串
  * @param groupId 可以为空，但是至少为长度为1的空字符串
  * @param prf 资源文件，当使用VL边长方式发送消息时需要此参数（从ccsflddef.txt中读取）解析报文
  * @return
  *//*
 public Request getRequest(String type, String prid, String userId, String groupId, Profile prf);
 
 *//**
  * 用于发送消息并获取回复消息
  * @param host
  * @param port
  * @param request
  * @return
  * @throws Exception
  *//*
 public Response getResponse(String host, int port, Request request) throws Exception; 
}*/
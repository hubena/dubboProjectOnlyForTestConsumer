package com.hubena.message.distmsg.socket.request.publisher;
/*package com.hubena.distmsg.socket.request.publisher;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hubena.distmsg.constant.MessageConstant;
import com.hubena.distmsg.entity.SocketNotificationProperty;

import server.Platform;
import server.Profile;
import frame.Request;
import frame.Response;

@Component
public class CustodySocketClientImpl implements ICustodySocketClient {
 private static Logger logger = LoggerFactory.getLogger(CustodySocketClientImpl.class);
 @Autowired
 private SocketNotificationProperty socketNotificationProperty;

 @Override
 public Request getRequest(String type, String prid, String userId, String groupId, Profile prf) {
  Request request = new Request();
  String ip = Platform.getHostIp();
        request.putBin(MessageConstant.BSVRHEAD_ITF, MessageConstant.RQTYPE_FLD, type);
        request.putBin(MessageConstant.BSVRHEAD_ITF, MessageConstant.RQPRID_FLD, prid);
        request.putBin(MessageConstant.BSVRHEAD_ITF, MessageConstant.RQUSER_FLD, userId);
        request.putBin(MessageConstant.BSVRHEAD_ITF, MessageConstant.RQGROUP_FLD, groupId);
        request.putBin(MessageConstant.BSVRHEAD_ITF, MessageConstant.RQIPAD_FLD, ip);
        request.setProfile(prf);
        return request;
    }
 
 @Override
 public Response getResponse(String host, int port, Request request) throws Exception {
  Response response = new Response();
  try (Socket socket = new Socket(host, port);
    OutputStream outs = socket.getOutputStream();
    InputStream ins = socket.getInputStream();
   ) {
   
   byte[] requestBytes = request.getByteArray();
   if (logger.isDebugEnabled()) {
    String s = new String(requestBytes, MessageConstant.ENCODING);
   logger.debug("host:" + host +" requestMessage stringLength = " + s.length() + " and requestBytesLenght = "
      + requestBytes.length);
    logger.debug("host:" + host +" requestMessage requestData=" + s);
   }
   if ((requestBytes.length <= 0) || (requestBytes.length > socketNotificationProperty.getMsgMaxLengthInt())) {
    throw new Exception(
      "SocketClient: send-data length overflow(" + requestBytes.length
        + ")");
   }
   // send data
   outs.write(htonl(requestBytes.length), 0, 4);
   outs.write(requestBytes, 0, requestBytes.length);

   // receive data
   // 设置超时时间，时间为毫秒
   socket.setSoTimeout(socketNotificationProperty.getTimeout());
//   socket.setSoTimeout(0);
   
   byte head[] = receive(ins, 4);
   int len = ntohl(head);
   if ((len <= 0) || (len > socketNotificationProperty.getMsgMaxLengthInt())) {
    throw new Exception(
      "SocketClient: receive-data length overflow(" + len
        + ")");
   }
   byte[] respData = receive(ins, len);
   if (logger.isDebugEnabled()) {
    String s = new String(respData, MessageConstant.ENCODING);
    logger.debug("host:" + host +" responseMessage stringLength = " + s.length() + " and responseBytesLength=" + len);
    logger.debug("host:" + host +" responseMessage StringData=" + s);
   }
   response.check(respData);
   return response;
  }
 }
 
 private static byte[] receive(InputStream ins, int len) throws Exception {
  int count = 0;
  byte[] b = new byte[len];

  while (count < len) {
   int rcv = ins.read(b, count, len - count);
   if (rcv <= 0)
    break;
   count += rcv;
  }

  if (count != len) {
   throw new Exception("SocketClient: receive-data failed");
  }

  return b;
 }
 
 private static byte[] htonl(int h) {
  byte n[] = new byte[4];
  n[0] = (byte) ((h >> 24) & 0xff);
  n[1] = (byte) ((h >> 16) & 0xff);
  n[2] = (byte) ((h >> 8) & 0xff);
  n[3] = (byte) (h & 0xff);

  return n;
 }

 private static int ntohl(byte[] n) {
  int h = ((int) n[0] << 24) | (((int) n[1] << 16) & 0xffffff)
    | (((int) n[2] << 8) & 0xffff) | ((int) n[3] & 0xff);

  return h;
 }
}*/
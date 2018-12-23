package com.hubena.message.distmsg.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * socket消息通知属性配置文件属性映射类
 * @author 曾谢波
 * 2018年6月22日
 */
@Component(value = "socketNotificationProperty")
public class SocketNotificationProperty implements Serializable{
 private static final long serialVersionUID = 1L;
 
 /*#######线程池配置######*/
 @Value("#{messageProperties[coreThreadNum]}")
 private Integer coreThreadNum;
 @Value("#{messageProperties[maxThreadNum]}")
 private Integer maxThreadNum;
 @Value("#{messageProperties[queueCapacity]}")
 private Integer queueCapacity;
 
 /*###发送消息通知Socket配置###*/
 // 最大闲置时间，超过core数量的闲置线程会销毁
 @Value("#{messageProperties[keepaliveTime]}")
 private Integer keepaliveTime;
 // 设置为静态则不用注入可直接引用
 @Value("#{messageProperties[timeout]}")
 private Integer timeout;
 // 最大能发送及读取的文件,单位为byte
 @Value("#{messageProperties[msgMaxLengthInt]}")
 private Integer msgMaxLengthInt;
 
 /*#####IP地址配置#####*/
 @Value("#{messageProperties[messageIPList]?.split(',')}")
 private List<String> messageIPList;
 @Value("#{messageProperties[port]}")
 private String port;
 
 // 发送到接收方用于识别分发处理类，在service.text文件中有配置：[service]ccs=ccs.CcsController
 @Value("#{messageProperties[type]}")
 private String type;
 
 //使用Socket还是Rabbitmq发送消息开关,默认设置为true
 @Value("#{messageProperties[isSendSocket]?:true}")
 private boolean sendSocket;
 
 public Integer getCoreThreadNum() {
  return coreThreadNum;
 }
 public void setCoreThreadNum(Integer coreThreadNum) {
  this.coreThreadNum = coreThreadNum;
 }
 public Integer getMaxThreadNum() {
  return maxThreadNum;
 }
 public void setMaxThreadNum(Integer maxThreadNum) {
  this.maxThreadNum = maxThreadNum;
 }
 public Integer getQueueCapacity() {
  return queueCapacity;
 }
 public void setQueueCapacity(Integer queueCapacity) {
  this.queueCapacity = queueCapacity;
 }
 public Integer getKeepaliveTime() {
  return keepaliveTime;
 }
 public void setKeepaliveTime(Integer keepaliveTime) {
  this.keepaliveTime = keepaliveTime;
 }
 public Integer getTimeout() {
  return timeout;
 }
 public void setTimeout(Integer timeout) {
  this.timeout = timeout;
 }

 public Integer getMsgMaxLengthInt() {
  return msgMaxLengthInt;
 }
 public  void setMsgMaxLengthInt(Integer msgMaxLengthInt) {
  this.msgMaxLengthInt = msgMaxLengthInt;
 }

 public List<String> getMessageIPList() {
  return messageIPList;
 }
 public void setMessageIPList(List<String> messageIPList) {
  this.messageIPList = messageIPList;
 }
 public String getPort() {
  return port;
 }
 public void setPort(String port) {
  this.port = port;
 }
 public String getType() {
  return type;
 }
 public void setType(String type) {
  this.type = type;
 }
 public boolean isSendSocket() {
  return sendSocket;
 }
 public void setSendSocket(boolean sendSocket) {
  this.sendSocket = sendSocket;
 }
 @Override
 public String toString() {
  return "SocketNotificationProperty [coreThreadNum=" + coreThreadNum
    + ", maxThreadNum=" + maxThreadNum + ", queueCapacity="
    + queueCapacity + ", keepaliveTime=" + keepaliveTime
    + ", messageIPList=" + messageIPList + ", port=" + port
    + ", type=" + type + ", sendSocket=" + sendSocket + ", timeout=" + timeout + ", msgMaxLengthInt=" + msgMaxLengthInt + "]";
 }
 
}
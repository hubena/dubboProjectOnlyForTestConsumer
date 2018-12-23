package com.hubena.message.distmsg.entity;



/**
 * 消息实体类
 * @author 曾谢波
 * 2018年5月31日
 */
public class CustodyMessageContent<T> {
 // 发信人
 private String addresser;
 // data类最好重写toString()方法，方便调试
 private T data;
 
 public String getAddresser() {
  return addresser;
 }
 public void setAddresser(String addresser) {
  this.addresser = addresser;
 }
 public T getData() {
  return data;
 }
 public void setData(T data) {
  this.data = data;
 }
 @Override
 public String toString() {
  return "MessageContent [addresser=" + addresser + ", data=" + data
    + "]";
 }
}
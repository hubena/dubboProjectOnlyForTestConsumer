package com.hubena.message.distmsg.rabbitmq.producer;

/**
 * 不同exchange发送消息封装类
 * @author 曾谢波
 * 2018年6月21日
 */
public interface ICustodyRabbitMQPublisher {
 
 /**
  * 广播发送rabbitmq消息，此方法用于发送消息到任意系统集群每台服务器上.<br>
  * 通过注解{@link @MessageAddress}与{@link @ParameterTypeHandler}实现.
  * @param exchange 目的交换机地址，不同系统集群队列绑定不同交换机
  * @param messageAddress 消息地址
  * @param message 消息体
  */
 void sendDistributedFanoutMessageForMultiClass(String exchange, final String messageAddress, Object message);
 
 /**
  * 发送topic模式消息，可用于发送消息到目的集群随机唯一一台服务器上.
  * @param exchangeName
  * @param routingKey
  * @param message
  */
 void sendTopicMessage(String exchangeName, String routingKey, Object message);
 
 /**
  * 发送direct模式消息
  * @param exchangeName
  * @param routingKey
  * @param message
  */
 void sendDirectMessage(String exchangeName, String routingKey, Object message);
 
 
 
 
}
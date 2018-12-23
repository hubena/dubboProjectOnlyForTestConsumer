package com.hubena.message.distmsg.rabbitmq.producer;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hubena.message.distmsg.constant.MessageConstant;
import com.hubena.message.distmsg.entity.RabbitMQProperty;

/**
 * 不同exchange发送消息封装类
 * @author 曾谢波
 * 2018年6月21日
 */
@Component
public class CustodyRabbitMQPublisherImpl implements ICustodyRabbitMQPublisher {
 private static final Logger logger = LoggerFactory.getLogger(CustodyRabbitMQPublisherImpl.class);
 @Resource(name = "fanoutRabbitTemplate")
 private RabbitTemplate fanoutRabbitTemplate;
 
 @Resource(name = "directRabbitTemplate")
 private RabbitTemplate directRabbitTemplate;
 
 @Resource(name = "topicRabbitTemplate")
 private RabbitTemplate topicRabbitTemplate;
 
 @Autowired
 private RabbitMQProperty rabbitMQProperty;
 
 @Override
 public void sendDistributedFanoutMessageForMultiClass(String exchange, final String messageAddress, Object message) {
  logger.info("RabbitMQ开始发送本集群fanout消息:{}", message.toString());
  
  // convertAndSend方法时默认发的持久化的信息，进去源码可以看到第三个参数是Message对象，
  // 而实例化Message对象的时候需要提供MessageProperties类对象，
  // 在MessageProperties类型默认的为持久化消息,PERSISTENT=2
  fanoutRabbitTemplate.convertAndSend(exchange, "", message, new MessagePostProcessor() {

      @Override
      public Message postProcessMessage(Message message) throws AmqpException {
          message.getMessageProperties().setHeader(MessageConstant.MESSAGE_ADDRESS, messageAddress);
          return message;
      }

  });
  logger.info("RabbitMQ结束发送本集群fanout消息");
 }
 
 
 @Override
 public void sendTopicMessage(String exchangeName, String routingKey, Object message) {
  logger.info("RabbitMQ开始发送topic消息:{},路由键为：{}", message.toString(), routingKey);
  topicRabbitTemplate.convertAndSend(exchangeName, routingKey, message);
  logger.info("RabbitMQ结束发送topic消息");
 }

 @Override
 public void sendDirectMessage(String exchangeName, String routingKey, Object message) {
  logger.info("RabbitMQ开始发送direct消息:{},路由键为：{}", message.toString(), routingKey);
  directRabbitTemplate.convertAndSend(exchangeName, routingKey, message);
  logger.info("RabbitMQ结束发送direct消息");
 }
 
}
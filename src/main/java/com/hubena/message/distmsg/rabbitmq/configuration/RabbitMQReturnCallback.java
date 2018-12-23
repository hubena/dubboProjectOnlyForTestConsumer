package com.hubena.message.distmsg.rabbitmq.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 1、如果消息没有到exchange,则confirm回调,ack=false.
 * 2、如果消息到达exchange,则confirm回调,ack=true.
 * 3、exchange到queue成功,则不回调return.
 * 4、exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了).
 * <pre/>
 * @author 曾谢波
 * @since 2018年8月30日
 */
@Component
public class RabbitMQReturnCallback implements ReturnCallback {
 private static final Logger logger = LoggerFactory.getLogger(RabbitMQReturnCallback.class);
 
 @Override
 public void returnedMessage(Message message, int replyCode,
   String replyText, String exchange, String routingKey) {
  logger.error("消息未到达队列，exchange为：{},routingKey为：{},replyCode为：{},replyText为：{}",
    exchange, routingKey, replyCode, replyText);
  
 }

}
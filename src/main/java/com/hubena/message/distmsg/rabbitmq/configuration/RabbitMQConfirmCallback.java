package com.hubena.message.distmsg.rabbitmq.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
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
public class RabbitMQConfirmCallback implements ConfirmCallback {
 private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfirmCallback.class);
 @Override
 public void confirm(CorrelationData correlationData, boolean ack,
   String cause) {
  if (ack) {
   logger.debug("消息到达交换机,CorrelationID为:{},cause为：{}", String.valueOf(correlationData), cause);
   return;
  }
  logger.error("消息未到达交换机,CorrelationID为:{},cause为：{}", String.valueOf(correlationData), cause);
  
 }

}
package com.hubena.message.distmsg.configuration;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.hubena.message.distmsg.constant.MessageConstant;

/**
 * 用于判断是否加载Bean：<br>
 * 当配置文件中配置为发送Socket时，则发送不加载RabbitMQ相关bean。
 * @author 曾谢波
 * @since 2018年8月8日
 */
public class SendMessageModeCondition implements Condition {
 @Override
 public boolean matches(ConditionContext context,
   AnnotatedTypeMetadata metadata) {
  if (context.getEnvironment().getProperty(MessageConstant.SEND_MESSAGE_MODE_CONDITION_KEY).contains(
   MessageConstant.SEND_MESSAGE_MODE_CONDITION_VALUE)) {
   return false;
  }
  return true;
 }

}
package com.hubena.message.distmsg.configuration;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.hubena.message.distmsg.entity.RabbitMQProperty;

/**
 * RabbitMQ Bean配置文件
 * @author 曾谢波
 * 2018年6月22日
 */
@Configuration
@EnableRabbit
@Import(value = {RabbitMQProducerConfiguration.class, RabbitMQConsumerConfiguration.class})
public class RabbitMQConfiguration {
 @Autowired
 private RabbitMQProperty rabbitMQProperty;
 
  /**
   * RabbitMQ连接工厂配置
   * @return
   */
 @Bean
 public ConnectionFactory connectionFactory() {
  
  CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
  connectionFactory.setVirtualHost(rabbitMQProperty.getVirtualHost());
  connectionFactory.setAddresses(rabbitMQProperty.getAddresses());
  connectionFactory.setUsername(rabbitMQProperty.getUsername());
  connectionFactory.setPassword(rabbitMQProperty.getPassword());
  //当前配置的最大通道允许空闲，如果设置channelCheckoutTimeout大于零则为最大数量限制，超过阻塞
  connectionFactory.setChannelCacheSize(rabbitMQProperty.getChannelCacheSize());
  		  /*
           * 同样一个RabbitTemplate只支持一个ConfirmCallback。
           * 对于发布确认，template要求CachingConnectionFactory的publisherConfirms属性设置为true。
           * 如果客户端通过setConfirmCallback(ConfirmCallback callback)注册了RabbitTemplate.ConfirmCallback，那么确认消息将被发送到客户端。
           * 这个回调函数必须实现以下方法：
           * void confirm(CorrelationData correlationData, booleanack);
           */
  connectionFactory.setPublisherConfirms(true);
	  		 /*
	           * 对于每一个RabbitTemplate只支持一个ReturnCallback。
	           * 对于返回消息，模板的mandatory属性必须被设定为true，
	           * 它同样要求CachingConnectionFactory的publisherReturns属性被设定为true。
	           * 如果客户端通过调用setReturnCallback(ReturnCallback callback)注册了RabbitTemplate.ReturnCallback，那么返回将被发送到客户端。
	           * 这个回调函数必须实现下列方法：
	           *void returnedMessage(Message message, intreplyCode, String replyText,String exchange, String routingKey);
	           */
  connectionFactory.setPublisherReturns(true);
  return connectionFactory;
 }

 
  /**
   * 声明队列交换机等,时机为connectionFactory.createConnection()事件发生时，当rabbitTemplate发送请求时会发生此事件.<br>
   * 自带了retryTemplate，当rabbitServer连接不上时，每次createConnection()事件发生时会自动重连3次.<br>
   * 与rabbitTemplate的retryTemplate策略类似.<br>
   * 声明交换机、队列、绑定等不应该在以下类中进行，因为有可能交换机等还未初始化完成，<br>
   * 要么在消息接收端SimpleMessageListenerContainer让其自动声明，<br>
   * 要么等待createConnection发生即第一次发送消息事件发生触发声明事件.
   * @param connectionFactory
   * @return
   */
 @Bean
 public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
  return new RabbitAdmin(connectionFactory);
 }
 
 
  /**
   * Json序列化
   * @return
   */
 @Bean
 public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
  return new Jackson2JsonMessageConverter();
 }
}
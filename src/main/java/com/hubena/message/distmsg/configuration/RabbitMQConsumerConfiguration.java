package com.hubena.message.distmsg.configuration;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import com.hubena.message.distmsg.entity.RabbitMQProperty;

/**
 * 消费者配置类
 * @author 曾谢波
 * 2018年6月22日
 */
@Configuration
@Conditional(value = {SendMessageModeCondition.class})
public class RabbitMQConsumerConfiguration {
 @Autowired
 private RabbitMQProperty rabbitMQProperty;
  /**
   * 注意，bean名必须为rabbitListenerContainerFactory.
   * 监听器，用于注释@RabbitListener监听.
   * 当rabbitmqServer重启后会重连，因为SimpleRabbitListenerContainerFactory会一直重试直到服务重启.
   * rabbitmqServer突然断开连接SimpleRabbitListenerContainerFactory也会重连，不一定是服务启动才重连.
   * @return
   */
 @Bean(value = "rabbitListenerContainerFactory")
 public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
   ConnectionFactory connectionFactory,
   Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
  SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
  simpleRabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
  simpleRabbitListenerContainerFactory.setMessageConverter(jackson2JsonMessageConverter);
  // 可以使用3个线程的核心池大小和10个线程的最大池大小调用Consumer方法。此并发线程数是相对于Container而言，而不是相对每个队列，即每个队列共享此限制数量的线程池。
  simpleRabbitListenerContainerFactory.setConcurrentConsumers(rabbitMQProperty.getConcurrentConsumers());
  simpleRabbitListenerContainerFactory.setMaxConcurrentConsumers(rabbitMQProperty.getMaxConcurrentConsumers());
  simpleRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);  // 自动确认收到消息
  // 单个消费者一次只能处理一个未确认消息，2.0版本默认未确认消息为250
  simpleRabbitListenerContainerFactory.setPrefetchCount(rabbitMQProperty.getPrefetchCount());
  	/*
     * 如果maxConcurrentConsumers大于concurrentConsumers，并且消费者数量超过concurrentConsumers，
     * 则指定不返回任何数据的连续接收尝试次数; 之后我们考虑停止消费者。
     * 空闲时间实际上是receiveTimeout * txSize *此值，因为使用者线程等待最多接收时间的消息达txSize次。 默认是10个连续的空闲。
     */
  simpleRabbitListenerContainerFactory.setReceiveTimeout(rabbitMQProperty.getReceiveTimeout());
  return simpleRabbitListenerContainerFactory;
 };
}
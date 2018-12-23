package com.hubena.message.distmsg.configuration;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.hubena.message.distmsg.entity.RabbitMQProperty;
import com.hubena.message.distmsg.entity.SocketNotificationProperty;
import com.hubena.message.distmsg.rabbitmq.configuration.RabbitMQConfirmCallback;
import com.hubena.message.distmsg.rabbitmq.configuration.RabbitMQReturnCallback;

/**
 * 生产者配置类
 * @author 曾谢波
 * 2018年6月25日
 */
@Configuration
public class RabbitMQProducerConfiguration {
 private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducerConfiguration.class);
 @Autowired
 private RabbitMQProperty rabbitMQProperty;
 @Autowired
 private RabbitAdmin rabbitAdmin;
 
  /**
   * 定义广播类型交换机。<br>
   * durable持久化设为true，但是autoDelete设为true，服务器应该在不再使用时删除交换机，所以实际上并没有持久化。
   * 即服务重新启动时交换机不会继续存在。
   * @return
   */
 @Bean
 public FanoutExchange fanoutExchange() {
  FanoutExchange fanoutExchange = new FanoutExchange(rabbitMQProperty.getFanoutExchange(), false, true, null);
  logger.debug("鍒濆鍖杅anoutExchange锛歿}", fanoutExchange.getName());
  return fanoutExchange;
 }
 
  /**
   * 定义点对点direct交换机.
   * @return
   */
 @Bean
 public DirectExchange directExchange() {
  DirectExchange directExchange = new DirectExchange(rabbitMQProperty.getDirectExchange(), true, false);
  logger.debug("鍒濆鍖杁irectExchange锛歿}", directExchange.getName());
  return directExchange;
 }
 
  /**
   * 定义topicExchange类型交换机。
   * @return
   */
 @Bean
 public TopicExchange topicExchange() {
  TopicExchange topicExchange = new TopicExchange(rabbitMQProperty.getTopicExchange(), true, false);
  logger.debug("鍒濆鍖杢opicExchange锛歿}", topicExchange.getName());
  return topicExchange;
 }
 
  /**
   * 定义queue说明(注意：此队列为非持久化队列，若要发送持久化消息需要定义持久化队列)：<br>
   * durable:是否持久化 <br>
   * exclusive: 仅创建者可以使用的私有队列，断开后自动删除 （为true时，队列并不能持久化）,最好不要定义为exclusive，
   * 否则当队列未被RabbitMQ自动删除重启时会报错："cannot obtain exclusive access to locked queue".<br>
   * auto_delete: 当所有消费客户端连接断开后，是否自动删除队列（为true时，队列并不能持久化）<br>
   * queueName为：QueuePrefix+uuid+queue<br>
   * <pre>
   * 注意:
   *     1、交换机Exchange与队列Queue的durable属性可以不相同，不影响消息持久化.
   *     2、要实现消息持久化消息发送消息时需要设置MessageProperties.setDeliveryMode并设置Queue的durable属性为true,
   *       MessageProperties类型默认的为持久化消息,DeliveryMode=PERSISTENT=2，所以用AMQP包发持久化消息
   *       不用设置DeliveryMode.
   *     3、如果exchange和queue都是持久化的，那么它们之间的binding也是持久化的.
   * <pre>
   * @return
   */
 @Bean
 public Queue refreshQueue() {
  String uuid = UUID.randomUUID().toString();
  String queueName = String.format("%s.%s.queue", rabbitMQProperty.getFanoutQueuePrefix(), uuid);
  Queue refreshQueue = new Queue(queueName, false, false, true, null);
  logger.debug("鍒濆鍖杛efreshQueue锛歿}", refreshQueue.getName());
  return refreshQueue;
 }
 
  /**
   * directExchange用directQueue.<br>
   * @return
   */
 @Bean
 public Queue directQueue() {
  Queue directQueue = new Queue(rabbitMQProperty.getDirectQueue(), true, false, false, null);
  logger.debug("鍒濆鍖杁irectQueue锛歿}", directQueue.getName());
  return directQueue;
 }
 
  /**
   * topicExchange用topicQueue.<br>
   * @return
   */
 @Bean
 public Queue topicQueue() {
  Queue topicQueue = new Queue(rabbitMQProperty.getTopicQueue(), true, false, false, null);
  logger.debug("鍒濆鍖杢opicQueue锛歿}", topicQueue.getName());
  return topicQueue;
 }
 
  /**
   * 绑定fanoutExchange交换机与队列
   * @param fanoutExchange
   * @param refreshQueue
   * @return
   */
 @Bean
 public Binding fanoutBinding(FanoutExchange fanoutExchange, Queue refreshQueue) {
  return BindingBuilder.bind(refreshQueue).to(fanoutExchange);
 }
 
  /**
   * 绑定directExchange交换机与队列.<br>
   * 注意：同一个队列可以和不同交换机绑定，并可以接收不同交换机发送的消息.
   * @param directExchange
   * @param directQueue
   * @return
   */
 @Bean
 public Binding directBinding(DirectExchange directExchange, Queue directQueue) {
  return BindingBuilder.bind(directQueue).to(directExchange).with(rabbitMQProperty.getDirectRoutingKey());
 }
 
  /**
   * 绑定topicExchange交换机与队列.<br>
   * 注意，topic模式的Word指的是点号.分隔的一个word，而不是一个字母：<br>
   * * (star) can substitute for exactly one word.<br>
   * # (hash) can substitute for zero or more words.
   * @param topicExchange
   * @param topicQueue
   * @return
   */
 @Bean
 public Binding topicBinding(TopicExchange topicExchange, Queue topicQueue) {
  return BindingBuilder.bind(topicQueue).to(topicExchange).with(rabbitMQProperty.getTopicPattern());
 }
 
  /**
   * 调用发送消息方法，如果失败且发生Exception，则会重试（默认最多三次），如果失败，则执行“recover”方法。
   * RetryTemplate：设置重试策略并设置指数延迟策略，默认为重试三次
   * ExponentialBackOffPolicy：设置指数延迟重试策略, 重试等待在这两个值之间均态分布
   * multiplier即指定延迟倍数:比如initialInterval=5000l,multiplier=2,则第一次重试为5秒，第二次为10秒，第三次为20秒
   * @return
   */
 @Bean
 public RetryTemplate retryTemplate() {
  RetryTemplate retryTemplate = new RetryTemplate();
  ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
  exponentialBackOffPolicy.setMaxInterval(10000);
  exponentialBackOffPolicy.setInitialInterval(500);
  exponentialBackOffPolicy.setMultiplier(10.0);
  retryTemplate.setBackOffPolicy(exponentialBackOffPolicy);
  return retryTemplate;
 }
 
 /**
   * fanoutExchange用RabbitTemplate.<br>
   * @param connectionFactory
   * @param retryTemplate 重试模板
   * @param jackson2JsonMessageConverter 序列化
   * @param fanoutExchange
   * @return
   */
 @Bean(value = "fanoutRabbitTemplate")
 public RabbitTemplate fanoutRabbitTemplate(
   ConnectionFactory connectionFactory,
   RetryTemplate retryTemplate,
   Jackson2JsonMessageConverter jackson2JsonMessageConverter,
   FanoutExchange fanoutExchange) {
  RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
  rabbitTemplate.setRetryTemplate(retryTemplate);
  rabbitTemplate.setReplyTimeout(rabbitMQProperty.getRabbitmqReplyTimeout()); // 鍚宻ocket
  rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
  return rabbitTemplate;
 }
 
 

 /**
   * directExchange交换机用RabbitTemplate.<br>
   * 若要设置不同的回调类(ConfirmCallback、ReturnCallback)，则应设置scope 为prototype,
   * 且调用每次调用RabbitTemplate都应从容器中取.<br>
   * 若要共用RabbitTemplate则回调类处理逻辑也会共用.
   * @param connectionFactory
   * @param retryTemplate
   * @param jackson2JsonMessageConverter
   * @param directExchange
   * @return
   */
 @Bean(value = "directRabbitTemplate")
 public RabbitTemplate directRabbitTemplate(ConnectionFactory connectionFactory,
   RetryTemplate retryTemplate, DirectExchange directExchange,
   Jackson2JsonMessageConverter jackson2JsonMessageConverter,
   RabbitMQConfirmCallback rabbitMQConfirmCallback,
   RabbitMQReturnCallback rabbitMQReturnCallback) {
  RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
  rabbitTemplate.setRetryTemplate(retryTemplate);
  rabbitTemplate.setReplyTimeout(rabbitMQProperty.getRabbitmqReplyTimeout()); // 鍚宻ocket
  rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
  rabbitTemplate.setConfirmCallback(rabbitMQConfirmCallback);
  rabbitTemplate.setReturnCallback(rabbitMQReturnCallback);
  rabbitTemplate.setMandatory(true);
  return rabbitTemplate;
 }
 
 
  /**
   * topicExchange交换机用RabbitTemplate.
   * @param connectionFactory
   * @param retryTemplate
   * @param jackson2JsonMessageConverter
   * @param topicExchange
   * @return
   */
 @Bean(value = "topicRabbitTemplate")
 public RabbitTemplate topicRabbitTemplate(ConnectionFactory connectionFactory,
   RetryTemplate retryTemplate,TopicExchange topicExchange,
   Jackson2JsonMessageConverter jackson2JsonMessageConverter,
   RabbitMQConfirmCallback rabbitMQConfirmCallback,
   RabbitMQReturnCallback rabbitMQReturnCallback) {
  RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
  rabbitTemplate.setRetryTemplate(retryTemplate);
  rabbitTemplate.setReplyTimeout(rabbitMQProperty.getRabbitmqReplyTimeout()); // 鍚宻ocket
  rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
  rabbitTemplate.setConfirmCallback(rabbitMQConfirmCallback);
  rabbitTemplate.setReturnCallback(rabbitMQReturnCallback);
  rabbitTemplate.setMandatory(true);
  return rabbitTemplate;
 }
}
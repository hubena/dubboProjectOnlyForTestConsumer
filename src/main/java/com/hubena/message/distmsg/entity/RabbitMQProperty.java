package com.hubena.message.distmsg.entity;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * rabbitMQ属性配置文件属性映射类
 * @author 曾谢波
 * 2018年6月22日
 */
@Component(value = "rabbitMQProperty")
public class RabbitMQProperty implements Serializable{
 private static final long serialVersionUID = 1L;
 
 /*----------------- 参数配置 ----------------*/
 //[]中值带点.则必须用单引号包围，否则取不到值
 @Value(value = "#{messageProperties['rabbitmq.virtualHost']}")
 private String virtualHost;
 @Value(value = "#{messageProperties['rabbitmq.addresses']}")
 private String addresses;
 @Value(value = "#{messageProperties['rabbitmq.username']}")
 private String username;
 @Value(value = "#{messageProperties['rabbitmq.password']}")
 private String password;
 /**
  * 为空则取默认值25.
  */
 @Value(value = "#{messageProperties['rabbitmq.channelCacheSize']?:25}")
 private Integer channelCacheSize;
 @Value(value = "#{messageProperties['rabbitmq.reply.timeout']}")
 private Long rabbitmqReplyTimeout;
 @Value(value = "#{messageProperties['rabbitmq.concurrent.consumers']}")
 private Integer concurrentConsumers;
 @Value(value = "#{messageProperties['rabbitmq.max.concurrent.consumers']}")
 private Integer maxConcurrentConsumers;
 @Value(value = "#{messageProperties['rabbitmq.receive.timeout']}")
 private Long receiveTimeout;
 
 /**
  * RabbitMQ每个消费者每次从队列获取消息数，设置为一，保证有序性且不会占用太多内存.
  */
 @Value(value = "#{messageProperties['rabbitmq.prefetch.count']}")
 private Integer prefetchCount;
 
 /*----------------- 本服务器声明的不同模式交换机、队列、匹配模式、路由键配置 ----------------*/
 
 /**
  * 本服务器声明的fanout交换机，同一系统集群此值相同,在message_notification_rabbitmq.properties配置文件中配置.
  */
 @Value(value = "#{messageProperties['rabbitmq.fanout.exchange']}")
 private String fanoutExchange;
 /**
  * 本服务器声明的与fanout交换机绑定的队列前缀，在message_notification_rabbitmq.properties配置文件中配置.
  */
 @Value(value = "#{messageProperties['rabbitmq.fanout.queue.prefix']}")
 private String fanoutQueuePrefix;
 
 /**
  * 本服务器声明的主题匹配模式交换机，同一系统集群此值相同,在message_notification_rabbitmq.properties配置文件中配置.
  */
 @Value(value = "#{messageProperties['rabbitmq.topic.exchange']}")
 private String topicExchange;
 /**
  * 本服务器声明的与topic交换机绑定的队列.同一系统集群此值相同,在message_notification_rabbitmq.properties配置文件中配置.
  */
 @Value(value = "#{messageProperties['rabbitmq.topic.queue']}")
 private String topicQueue;
 /**
  * 主题匹配模式pattern.同一系统集群此值相同,在message_notification_rabbitmq.properties配置文件中配置.
  */
 @Value(value = "#{messageProperties['rabbitmq.topic.queue.pattern']}")
 private String topicPattern;
 
 /**
  * 点对点直发交换机.
  */
 @Value(value = "#{messageProperties['rabbitmq.direct.exchange']}")
 private String directExchange;
 /**
  * 点对点直发用队列.
  */
 @Value(value = "#{messageProperties['rabbitmq.direct.queue']}")
 private String directQueue;
 
 /**
  * 点对点直发用队列路由键.
  */
 @Value(value = "#{messageProperties['rabbitmq.direct.routing.key']}")
 private String directRoutingKey;
 
 
 
 


 /**
  * @return virtualHost
  */
 public String getVirtualHost() {
  return virtualHost;
 }

 /**
  * @param virtualHost 要设置的 virtualHost
  */
 public void setVirtualHost(String virtualHost) {
  this.virtualHost = virtualHost;
 }

 /**
  * @return addresses
  */
 public String getAddresses() {
  return addresses;
 }

 /**
  * @param addresses 要设置的 addresses
  */
 public void setAddresses(String addresses) {
  this.addresses = addresses;
 }

 /**
  * @return username
  */
 public String getUsername() {
  return username;
 }

 /**
  * @param username 要设置的 username
  */
 public void setUsername(String username) {
  this.username = username;
 }

 /**
  * @return password
  */
 public String getPassword() {
  return password;
 }

 /**
  * @param password 要设置的 password
  */
 public void setPassword(String password) {
  this.password = password;
 }

 /**
  * @return channelCacheSize
  */
 public Integer getChannelCacheSize() {
  return channelCacheSize;
 }

 /**
  * @param channelCacheSize 要设置的 channelCacheSize
  */
 public void setChannelCacheSize(Integer channelCacheSize) {
  this.channelCacheSize = channelCacheSize;
 }

 /**
  * @return rabbitmqReplyTimeout
  */
 public Long getRabbitmqReplyTimeout() {
  return rabbitmqReplyTimeout;
 }

 /**
  * @param rabbitmqReplyTimeout 要设置的 rabbitmqReplyTimeout
  */
 public void setRabbitmqReplyTimeout(Long rabbitmqReplyTimeout) {
  this.rabbitmqReplyTimeout = rabbitmqReplyTimeout;
 }

 /**
  * @return concurrentConsumers
  */
 public Integer getConcurrentConsumers() {
  return concurrentConsumers;
 }

 /**
  * @param concurrentConsumers 要设置的 concurrentConsumers
  */
 public void setConcurrentConsumers(Integer concurrentConsumers) {
  this.concurrentConsumers = concurrentConsumers;
 }

 /**
  * @return maxConcurrentConsumers
  */
 public Integer getMaxConcurrentConsumers() {
  return maxConcurrentConsumers;
 }

 /**
  * @param maxConcurrentConsumers 要设置的 maxConcurrentConsumers
  */
 public void setMaxConcurrentConsumers(Integer maxConcurrentConsumers) {
  this.maxConcurrentConsumers = maxConcurrentConsumers;
 }

 /**
  * @return receiveTimeout
  */
 public Long getReceiveTimeout() {
  return receiveTimeout;
 }

 /**
  * @param receiveTimeout 要设置的 receiveTimeout
  */
 public void setReceiveTimeout(Long receiveTimeout) {
  this.receiveTimeout = receiveTimeout;
 }

 /**
  * @return prefetchCount
  */
 public Integer getPrefetchCount() {
  return prefetchCount;
 }

 /**
  * @param prefetchCount 要设置的 prefetchCount
  */
 public void setPrefetchCount(Integer prefetchCount) {
  this.prefetchCount = prefetchCount;
 }

 /**
  * @return fanoutExchange
  */
 public String getFanoutExchange() {
  return fanoutExchange;
 }

 /**
  * @param fanoutExchange 要设置的 fanoutExchange
  */
 public void setFanoutExchange(String fanoutExchange) {
  this.fanoutExchange = fanoutExchange;
 }

 /**
  * @return fanoutQueuePrefix
  */
 public String getFanoutQueuePrefix() {
  return fanoutQueuePrefix;
 }

 /**
  * @param fanoutQueuePrefix 要设置的 fanoutQueuePrefix
  */
 public void setFanoutQueuePrefix(String fanoutQueuePrefix) {
  this.fanoutQueuePrefix = fanoutQueuePrefix;
 }

 /**
  * @return topicExchange
  */
 public String getTopicExchange() {
  return topicExchange;
 }

 /**
  * @param topicExchange 要设置的 topicExchange
  */
 public void setTopicExchange(String topicExchange) {
  this.topicExchange = topicExchange;
 }

 /**
  * @return topicQueue
  */
 public String getTopicQueue() {
  return topicQueue;
 }

 /**
  * @param topicQueue 要设置的 topicQueue
  */
 public void setTopicQueue(String topicQueue) {
  this.topicQueue = topicQueue;
 }

 /**
  * @return topicPattern
  */
 public String getTopicPattern() {
  return topicPattern;
 }

 /**
  * @param topicPattern 要设置的 topicPattern
  */
 public void setTopicPattern(String topicPattern) {
  this.topicPattern = topicPattern;
 }

 /**
  * @return directExchange
  */
 public String getDirectExchange() {
  return directExchange;
 }

 /**
  * @param directExchange 要设置的 directExchange
  */
 public void setDirectExchange(String directExchange) {
  this.directExchange = directExchange;
 }

 /**
  * @return directQueue
  */
 public String getDirectQueue() {
  return directQueue;
 }

 /**
  * @param directQueue 要设置的 directQueue
  */
 public void setDirectQueue(String directQueue) {
  this.directQueue = directQueue;
 }

 /**
  * @return directRoutingKey
  */
 public String getDirectRoutingKey() {
  return directRoutingKey;
 }

 /**
  * @param directRoutingKey 要设置的 directRoutingKey
  */
 public void setDirectRoutingKey(String directRoutingKey) {
  this.directRoutingKey = directRoutingKey;
 }

 /* （非 Javadoc）
  * @see java.lang.Object#toString()
  */
 @Override
 public String toString() {
  return "RabbitMQProperty [virtualHost=" + virtualHost + ", addresses="
    + addresses + ", username=" + username + ", password="
    + password + ", channelCacheSize=" + channelCacheSize
    + ", rabbitmqReplyTimeout=" + rabbitmqReplyTimeout
    + ", concurrentConsumers=" + concurrentConsumers
    + ", maxConcurrentConsumers=" + maxConcurrentConsumers
    + ", receiveTimeout=" + receiveTimeout + ", prefetchCount="
    + prefetchCount + ", fanoutExchange=" + fanoutExchange
    + ", fanoutQueuePrefix=" + fanoutQueuePrefix
    + ", topicExchange=" + topicExchange + ", topicQueue="
    + topicQueue + ", topicPattern=" + topicPattern
    + ", directExchange=" + directExchange + ", directQueue="
    + directQueue + ", directRoutingKey=" + directRoutingKey + "]";
 }

}
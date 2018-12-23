package com.hubena.message.distmsg.configuration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.hubena.message.distmsg.constant.MessageConstant;

/**
 * 消息通知机制配置类.<br>
 * 注解<code>@PropertySource(value = {"classpath:/data/message/message_notification_socket.properties"})</code>
 * 仅用于{@link SendMessageModeCondition.class}获取配置用.
 * @author 曾谢波
 * 2018年6月22日
 */
@Configuration
@Import(value = {RabbitMQConfiguration.class, SocketNotificationConfiguration.class})
@ComponentScan(basePackages = {"distmsg"})
//在springboot中要在application.properties文件中定义condition类才能读取到
@PropertySource(value = {"classpath:/data/message/message_notification_socket.properties"})
public class MessageNotificationSpringConfiguration {
 private static final Logger logger = LoggerFactory.getLogger(MessageNotificationSpringConfiguration.class);
 private static final String PROPERTIES_FACTORY_BEAN_NAME = "messageProperties";
 
  /**
   * 发送消息配置文件资源地址，配置为将data/message/路径下所有properties都读入系统.
   */
 private static final String SEND_MESSAGE_RESOURCES_PATH = "classpath*:data/message/*.properties";
 
 @Bean(value = PROPERTIES_FACTORY_BEAN_NAME)
 public PropertiesFactoryBean messagePropertiesBean() {
  PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
  try {
   ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
   Resource[] resource = resolver.getResources(SEND_MESSAGE_RESOURCES_PATH);
   propertiesFactoryBean.setLocations(resource);
  } catch (IOException e) {
   logger.error("解析data/message/*.properties文件报错.{}", e);
  }
  propertiesFactoryBean.setFileEncoding(MessageConstant.ENCODING_SPRING);
  return propertiesFactoryBean;
 }
}
package com.hubena.message.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hubena.message.distmsg.annotation.MessageAddress;
import com.hubena.message.distmsg.annotation.ParameterTypeHandler;
import com.hubena.message.distmsg.entity.CustodyMessageContent;

@Component
@MessageAddress(value = "messageAddressTest")
public class RabbitmqConsumer {
	private static final Logger logger = LoggerFactory.getLogger(RabbitmqConsumer.class);
	@ParameterTypeHandler
	public void messageConsumer(CustodyMessageContent<String> content) {
		logger.error("-----收到的rabbitmq消息为：{}", content);
	}
}

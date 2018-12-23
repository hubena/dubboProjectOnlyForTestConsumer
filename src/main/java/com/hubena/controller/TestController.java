package com.hubena.controller;

import java.text.MessageFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hubena.dubbo.service.ISayHelloWorld;
import com.hubena.entity.Response;
import com.hubena.message.distmsg.entity.CustodyMessageContent;
import com.hubena.message.distmsg.publisher.ICustodyPublisherMessage;

@RestController
@RequestMapping(path="/get")
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Reference
	private ISayHelloWorld iSayHelloWorld;
	
	@Autowired
	private ICustodyPublisherMessage iCustodyPublisherMessage;
	
	@RequestMapping(value="/helloWorld",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public Response getHelloWorld(String id) {
		String helloString = iSayHelloWorld.sayHelloWorld();
		logger.error(MessageFormat.format("远程调用返回的string为：{0}", helloString));
		Response response = new Response();
		response.setName(helloString);
		CustodyMessageContent<String> content = new CustodyMessageContent<>();
		content.setAddresser("messageAddressTest");
		content.setData("rabbitmq发送的消息。小明");
		try {
			iCustodyPublisherMessage.sendMessage("messageAddressTest", content);
		} catch (Exception e) {
			logger.error("发送消息报错：", e);
		}
		return response;
	}
}

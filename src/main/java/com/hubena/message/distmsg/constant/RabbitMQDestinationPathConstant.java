package com.hubena.message.distmsg.constant;

/**
 * 用于配置RabbitMQ发送消息时的消息目的地址常量类.
 * @author 曾谢波
 * @since 2018年8月31日
 */
public class RabbitMQDestinationPathConstant {
 /* ---------------- 配置要发送的广播交换机地址  ------------- */
 /**
  * 托管核心广播交换机地址.
  */
 public static final String CORE_FANOUT_EXCHANGE = "core.distmsg.fanout.exchange";
 /**
  * 非一级配置广播交换机地址.
  */
 public static final String FYJ_FANOUT_EXCHANGE = "fyj.distmsg.fanout.exchange";
 /**
  * 网托配置广播交换机地址
  */
 public static final String TGBANK_FANOUT_EXCHANGE = "tgbank.distmsg.fanout.exchange";
 /**
  * 外包配置广播交换机地址
  */
 public static final String WB_FANOUT_EXCHANGE = "wb.distmsg.fanout.exchange";
 
 
 /* ---------- 配置要发送的topic交换机地址及路由键值 --------- */
 /**
  * 托管核心topic交换机地址.
  */
 public static final String CORE_TOPIC_EXCHANGE = "core.distmsg.topic.exchange";
 /**
  * 托管核心topic路由键.
  */
 public static final String CORE_TOPIC_ROUTING_KEY = "core.distmsg.topic.queue";
 
 /**
  * 非一级topic交换机地址.
  */
 public static final String FYJ_TOPIC_EXCHANGE = "fyj.distmsg.topic.exchange";
 /**
  * 非一级topic路由键.
  */
 public static final String FYJ_TOPIC_ROUTING_KEY = "fyj.distmsg.topic.queue";
 
 /**
  * 网上托管银行topic交换机地址.
  */
 public static final String TGBANK_TOPIC_EXCHANGE = "tgbank.distmsg.topic.exchange";
 /**
  * 网上托管银行topic路由键.
  */
 public static final String TGBANK_TOPIC_ROUTING_KEY = "tgbank.distmsg.topic.queue";
 
 /**
  * 外包topic交换机地址.
  */
 public static final String WB_TOPIC_EXCHANGE = "wb.distmsg.topic.exchange";
 /**
  * 外包topic路由键.
  */
 public static final String WB_TOPIC_ROUTING_KEY = "wb.distmsg.topic.queue";

}
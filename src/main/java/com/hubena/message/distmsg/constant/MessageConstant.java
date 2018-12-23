package com.hubena.message.distmsg.constant;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.hubena.message.distmsg.configuration.SendMessageModeCondition;

/**
 * 消息发送功能公共常量配置
 * @author 曾谢波
 * 2018年6月1日
 */
public class MessageConstant {
 /**
  * 项目字符默认编码，包括request与response中字符编码
  */
 public static final String ENCODING = "GB2312";
 public static final String ENCODING_SPRING = "UTF-8";
 /**
  * 消息接口发送参数在request中变长VL的itf中存放位置名，固定为10位，超出部分丢弃
  */
 public static final String MESSAGE_ITF = "MESSAGECON";
 /**
  * 消息地址在request变长VL的fld中存放位置名，固定为6位，超出部分被丢弃
  */
 public static final String MESSAGE_ADDRESS_FLD = "ADDRDS";
 /**
  * MessageContent发送的消息内容在request变长VL的fld中存放位置名，固定为6位，超出部分被丢弃
  */
 public static final String MESSAGE_CONTENT_FLD = "MESSAG";
 /**
  * 发送MessageContent消息实体的类类型在request变长VL的fld中存放位置名，固定为6位，超出部分被丢弃
  */
 public static final String MESSAGE_CLASS_TYPE_FLD = "TYPEID";
 
 /**
  * 返回的的消息内容在response变长VL的fld中存放位置名，固定为6位，超出部分被丢弃
  */
 public static final String MESSAGE_RESPONSE_CODE_FLD = "RECODE";
 
 
 /**
  * VL变长报文头部信息ITF位置存放名，固定十位，超出部分丢弃
  */
 public static final String BSVRHEAD_ITF = "$BSVRHEAD$";
 /**
  * 返回信息码_retcode在response变长VL的fld中存放的位置名，固定为6位，超出部分丢弃
  */
 public static final String RPCODE_FLD = "RPCODE";
 /**
  * 返回错误信息_errmsg在response变长VL的fld中存放的位置名，固定为6位，超出部分丢弃
  */
 public static final String RPMESG_FLD = "RPMESG";
 /**
  * 请求类型，即在service文件中配置的[service]
  * ccs=ccs.CcsController
  */
 public static final String RQTYPE_FLD = "RQTYPE";
 /**
  * 发送者请求处理类，在@Controller中配置.
  */
 public static final String RQPRID_FLD = "RQPRID";
 /**
  * 发送者用户名.
  */
 public static final String RQUSER_FLD = "RQUSER";
 
 public static final String RQGROUP_FLD = "RQGROUP";
 /**
  * 发送者IP地址在request变长VL的fld中存放的位置名，固定为6位，超出部分丢弃
  */
 public static final String RQIPAD_FLD = "RQIPAD";
 
 /**
  * 消息地址，如缓存刷新事件cacherefresh事件
  */
 public static final String MESSAGE_ADDRESS = "messageAddress";
 /**
  * Spring自带线程池{@link ThreadPoolTaskExecutor}.
  * 可与注解{@link @Async}配合使用，使方法异步执行.
  */
 public static final String SPRING_ASYNC_THREAD_POOL = "SpringAsyncThreadPool";
 
 /**
  * 用于{@link SendMessageModeCondition#matches} 的条件判断.
  */
 public static final String SEND_MESSAGE_MODE_CONDITION_KEY = "isSendSocket";
 
 /**
  * 用于{@link SendMessageModeCondition#matches} 的条件判断.
  */
 public static final String SEND_MESSAGE_MODE_CONDITION_VALUE = "true";
}
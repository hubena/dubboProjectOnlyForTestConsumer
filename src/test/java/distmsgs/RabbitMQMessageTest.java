package distmsgs;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hubena.message.distmsg.constant.RabbitMQDestinationPathConstant;
import com.hubena.message.distmsg.entity.CustodyMessageContent;
import com.hubena.message.distmsg.entity.RabbitMQProperty;
import com.hubena.message.distmsg.rabbitmq.producer.ICustodyRabbitMQPublisher;

/**
 * RabbitMQ点对点Direct模式及主题匹配Topic模式发送消息测试.
 * @author 曾谢波
 * @since 2018年8月30日
 */
public class RabbitMQMessageTest extends BaseTest {
 private static final Logger logger = LoggerFactory.getLogger(RabbitMQMessageTest.class);
 @Autowired
 private ICustodyRabbitMQPublisher iCustodyRabbitMQPublisher;
 @Autowired
 private RabbitMQProperty rabbitMQProperty;
 
 @Resource(name = "messageProperties")
 private Properties messageProperties;
 
 private static CustodyMessageContent<HashMap<String, StudentTest>> messageContent;
 private static CustodyMessageContent<StudentTest> messageContentForFanout;
 
 @BeforeClass
 public static void beforeTestRun() {
  // 要发送的消息实体
  StudentTest studentTest = new StudentTest();
  studentTest.setName("小明");
  studentTest.setAge(33);
  HashMap<String, StudentTest> hashMap = new HashMap<String, StudentTest>();
  hashMap.put("student", studentTest);
  // 包装消息实体的实体类
  messageContent = new CustodyMessageContent<HashMap<String, StudentTest>>();
  messageContent.setAddresser("000001");
  messageContent.setData(hashMap);
  
  messageContentForFanout = new CustodyMessageContent<StudentTest>();
  messageContentForFanout.setAddresser("000002");
  messageContentForFanout.setData(studentTest);
 }
 
 /**
  * 点对点发送消息测试.
  */
 @Test
 public void testDirectExchangeMessage() {
  iCustodyRabbitMQPublisher.sendDirectMessage(rabbitMQProperty.getDirectExchange(),
   rabbitMQProperty.getDirectRoutingKey(), messageContent);
  logger.debug("发送Direct消息成功");
  Assert.assertTrue(true);
 }
 
 /**
  * 主题匹配发送消息测试.
  */
 @Test
 public void testTopicExchangeMessage() {
  iCustodyRabbitMQPublisher.sendTopicMessage(RabbitMQDestinationPathConstant.CORE_TOPIC_EXCHANGE,
   RabbitMQDestinationPathConstant.CORE_TOPIC_ROUTING_KEY, messageContent);
  logger.debug("发送Topic消息成功");
  Assert.assertTrue(true);
 }
 
 /**
  * fanout广播模式发送消息测试.
  * @throws InterruptedException
  */
 @Test
 public void testFanoutExchangeMessage() throws InterruptedException {
  iCustodyRabbitMQPublisher.sendDistributedFanoutMessageForMultiClass(
   rabbitMQProperty.getFanoutExchange(), "test.event", messageContentForFanout);
  logger.debug("发送Fanout消息成功");
  Assert.assertTrue(true);
  TimeUnit.SECONDS.sleep(2);
 }
 
 /**
  * 打印message_notification_rabbitmq.properties及message_notification_socket.properties配置文件读取到系统中的值.
  */
 @Test
 public void testPrintProperties() {
  logger.debug("messageProperties参数信息：{}", messageProperties.toString());
 }
}

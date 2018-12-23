package distmsgs;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.hubena.message.distmsg.entity.CustodyMessageContent;
import com.hubena.message.distmsg.entity.RabbitMQProperty;
import com.hubena.message.distmsg.entity.SocketNotificationProperty;
import com.hubena.message.distmsg.publisher.ICustodyPublisherMessage;

/**
 * 设置初始化参数，若要使用自己的/core-server/src/main/resources/data/log.properties配置打印日志的话. <br>
 * 则要设置VM 自变量(vm arguments)为以下：<br>
 * -Djava.util.logging.config.file=src/main/resources/data/log.properties <br>
 * -Djava.util.logging.manager=core.log.jdkLog.JDKLogManager <br>
 * 设置配置文件参数（主要用于数据源寻找配置文件）：-Dbsvr.rootpath=target/classes/data/ <br>
 *
 * @author 曾谢波 2018年6月15日
 */
public class MessageSpringTest extends BaseTest {
 @SuppressWarnings("unused")
 private static final Logger logger = LoggerFactory
   .getLogger(MessageSpringTest.class);
 @Autowired
 private ICustodyPublisherMessage custodyPublisher;
 @Autowired
 private RabbitMQProperty rabbitMQProperty;
 @Autowired
 private SocketNotificationProperty socketNotificationProperty;
 @Autowired
 private ApplicationContext applicationContext;

 /**
  * 只发送一次消息测试
  * @throws Exception
  */
 @Test
 public void testSendMessageOnce() throws Exception {
  // 要发送的消息实体
  StudentTest studentTest = new StudentTest();
  studentTest.setName("小明");
  studentTest.setAge(33);
  // 包装消息实体的实体类
  CustodyMessageContent<StudentTest> messageContent = new CustodyMessageContent<StudentTest>();
  messageContent.setAddresser("000001");
  messageContent.setData(studentTest);
  custodyPublisher.sendMessage("test.event", messageContent);
  Assert.assertTrue(true);
  TimeUnit.SECONDS.sleep(3);
 }
 
 
 /**
  * 控制台命令方式发起多次消息测试
  *
  * @throws Exception
  */
// @Test
 public void testCommandSendMessageRepeatable() throws Exception {
  try (Scanner scanner = new Scanner(System.in);) {
   // 要发送的消息实体
   StudentTest studentTest = new StudentTest();
   studentTest.setName("小明");
   studentTest.setAge(33);
   // 包装消息实体的实体类
   CustodyMessageContent<StudentTest> messageContent = new CustodyMessageContent<StudentTest>();
   messageContent.setAddresser("000001");
   messageContent.setData(studentTest);
   while (true) {
    System.out.println("请输入命令：");
    String readString = scanner.nextLine();
    if ("start".equals(readString)) {
     custodyPublisher.sendMessage("test.event", messageContent);
    }
   }
  } catch (Exception e) {
   throw e;
  }

 }

 /**
  * 单次并发测试
  *
  * @throws InterruptedException
  */
 // @Test
 public void testConcurrentSendMessageOnce() throws InterruptedException {
  final CountDownLatch countDownLatch = new CountDownLatch(30);
  int count = 30;
  for (int i = 0; i < count; i++) {
   new Thread(new Runnable() {
    @Override
    public void run() {
     // 要发送的消息实体
     StudentTest studentTest = new StudentTest();
     studentTest.setName("小明");
     studentTest.setAge(33);
     // 包装消息实体的实体类
     CustodyMessageContent<StudentTest> messageContent = new CustodyMessageContent<StudentTest>();
     messageContent.setAddresser("000001");
     messageContent.setData(studentTest);
     try {
      countDownLatch.countDown();
      countDownLatch.await();
      custodyPublisher.sendMessage("test.event",
        messageContent);
     } catch (Exception e) {
      e.printStackTrace();
     }
    }
   }).start();
  }

 }

 /**
  * 控制台命令方式发起多次并发测试
  *
  * @throws IOException
  * @throws InterruptedException
  */
 // @Test
 public void testCommandConcurrentSendMessageRepeatable()
   throws IOException, InterruptedException {
  try (Scanner scanner = new Scanner(System.in);) {
   while (true) {
    System.out.println("请输入命令：");
    String readString = scanner.nextLine();
    if ("start".equals(readString)) {
     testConcurrentSendMessageOnce();
    }
   }
  } catch (Exception e) {
   throw e;
  }

 }

}
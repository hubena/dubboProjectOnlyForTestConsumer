/**
 * <h1>消息通知功能使用说明：</h1>
 * <pre>
 * 此包主要有以下三个功能：
 * 1、向本系统集群每台服务器广播消息。通过调用{@link ICustodyPublisherMessage#sendMessage}方法实现，此方法内部又有两种发送方式：
 *     <strong>方法发送端需要做的配置:</strong>
 *     a、通过Socket发送消息，需要的配置：
 *         I、要配置首先要配置message_notification_socket.properties文件中如下属性：
 *             <strong>isSendSocket=true</strong>:代表发送模式为Socket,
 *             <strong>messageIPList</strong>：值为本系统集群IP地址列表，
 *             <strong>port</strong>：为服务器端口。
 *     b、通过RabbitMQ发送广播消息，需要的配置：
 *         I、要配置首先要配置message_notification_socket.properties文件中如下属性：
 *             <strong>isSendSocket=false</strong>:代表发送模式为RabbitMQ,
 *             <strong>rabbitmq.fanout.exchange</strong>：本机声明交换机地址，
 *             <strong>rabbitmq.fanout.queue.prefix</strong>：本机声明队列前缀。
 *         注意，此种模式交换机及队列都是非持久化的，队列名为配置的前缀加上UUID生成的随机数。
 *         发送端测试用例在MessageSpringTest中testSendMessageOnce()方法中。
 *     <strong>方法接收端需要做的配置：</strong>
 *     a、接收端主要用到两个注解：
 *         {@link MessageAddress}：用于注解在消息接收端类上，value取值为{@link ICustodyPublisherMessage#sendMessage}发送消息方法的第一个参数即消息地址。
 *         {@link ParameterTypeHandler}:用于注解在消息接收端方法上，指定接收方法。
 *         注意：
 *             A、可以有多个被{@link MessageAddress}注解修饰的方法，消息会广播到每一个类。
 *             B、一个类中只能有一个{@link ParameterTypeHandler}注解修饰的方法。
 *             C、被{@link ParameterTypeHandler}注解修饰的方法只能有一个参数，且参数只能为{@link String}或{@link CustodyMessageContent}。
 *     接收端测试用例在TestCustodySubscriberOneImpl中execute(CustodyMessageContent)方法中。
 *    
 * 2、向其他系统集群中的随机一台服务器发送消息。使用RabbitMQ主题匹配Topic模式发送，
 *     a、调用方法{@link ICustodyRabbitMQPublisher#sendTopicMessage(String, String, Object)}，
 *         第一个参数为目的交换机地址，第二个参数为路由键值，第三个为要发送的消息实体。
 *         目的交换机地址及路由键值在{@link RabbitMQDestinationPathConstant}中“配置要发送的topic交换机地址及路由键值 ”中配置。
 *     发送端测试用例在RabbitMQMessageTest中testTopicExchangeMessage()方法中。
 *     接收端测试用例在RabbitMQListenerImpl中topicRabbitListener方法中。
 *    
 * 3、向任意系统集群每台服务器广播消息。通过fanout广播模式发送消息：
 *     a、调用方法{@link ICustodyRabbitMQPublisher#sendDistributedFanoutMessageForMultiClass(String, String, Object)}，
 *         第一个参数为目的交换机地址，第二个参数为消息目的地址，第三个参数为要发送的消息实体。
 *         目的交换机地址在{@link RabbitMQDestinationPathConstant}中“配置要发送的广播交换机地址 ”中配置，
 *         消息目的地址在{@link distmsg.constant.MessageEventIdConstant}中配置。
 *         发送端实例在RabbitMQMessageTest类的testFanoutExchangeMessage()方法中。
 *         接收端实例在TestCustodySubscriberOneImpl类及TestCustodySubscriberTwoImpl类中。
 * </pre>
 */
package com.hubena.message.distmsg;
import com.hubena.message.distmsg.annotation.MessageAddress;
import com.hubena.message.distmsg.annotation.ParameterTypeHandler;
import com.hubena.message.distmsg.constant.RabbitMQDestinationPathConstant;
import com.hubena.message.distmsg.entity.CustodyMessageContent;
import com.hubena.message.distmsg.publisher.ICustodyPublisherMessage;
import com.hubena.message.distmsg.rabbitmq.producer.ICustodyRabbitMQPublisher;

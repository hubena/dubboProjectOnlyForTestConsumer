package distmsgs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * 测试时要多定义一次classpath:/data/message/message_notification_socket.properties文件，<br>
 * 以防止{@link SendMessageModeCondition#matches}方法ConditionContext参数为null。虽然这样会导致定义两次读两次数据.
 * @author 曾谢波
 * @since 2018年8月31日
 */
@Configuration
@ComponentScan(value = {"distmsgs"})
@ImportResource(value = {"classpath:data/spring-config.xml"})
@PropertySource(value = {"classpath:/data/message/message_notification_socket.properties"})
public class TestSpringConfiguration {

}
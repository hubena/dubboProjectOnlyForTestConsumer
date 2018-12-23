package distmsgs;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;


@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestSpringConfiguration.class})
//@ContextConfiguration(value = {"classpath:data/spring-config.xml"})
public class BaseTest {
 @BeforeClass
 public static void initlizeDataSource() throws Exception {
 }
}
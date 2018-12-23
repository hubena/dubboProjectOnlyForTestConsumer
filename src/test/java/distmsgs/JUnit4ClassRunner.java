
package distmsgs;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 * 设置初始化参数，若要使用自己的/core-server/src/main/resources/data/log.properties配置打印日志的话. <br>
 * 则要设置VM 自变量(vm arguments)为以下：<br>
 * -Djava.util.logging.config.file=src/main/resources/data/log.properties <br>
 * -Djava.util.logging.manager=core.log.jdkLog.JDKLogManager <br>
 * 设置配置文件参数（主要用于数据源寻找配置文件）：-Dbsvr.rootpath=target/classes/data/ <br>
 * @author 曾谢波
 * 2018年6月14日
 */
public class JUnit4ClassRunner extends SpringJUnit4ClassRunner{
 static {
  // 传入初始化数据源参数       
  System.setProperty("bsvr.rootpath", "target/classes/data/");
 }
 
 public JUnit4ClassRunner(Class<?> clazz) throws InitializationError {
  super(clazz);
 }
 
 
}
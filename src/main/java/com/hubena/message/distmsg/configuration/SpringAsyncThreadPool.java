package com.hubena.message.distmsg.configuration;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.hubena.message.distmsg.constant.MessageConstant;
import com.hubena.message.distmsg.entity.SocketNotificationProperty;

/**
 * 实现Spring自带线程池{@link ThreadPoolTaskExecutor}.
 * 可与注解{@link @Async}配合使用，使方法异步执行.
 * @author 曾谢波
 * 2018年7月12日
 */
@Component
public class SpringAsyncThreadPool implements AsyncConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(SpringAsyncThreadPool.class);
	@Autowired
	private SocketNotificationProperty messageProperty;

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(messageProperty.getCoreThreadNum());
		threadPoolTaskExecutor.setMaxPoolSize(messageProperty.getMaxThreadNum());
		threadPoolTaskExecutor.setKeepAliveSeconds(messageProperty.getKeepaliveTime());
		threadPoolTaskExecutor.setThreadNamePrefix(MessageConstant.SPRING_ASYNC_THREAD_POOL);
		threadPoolTaskExecutor.setThreadPriority(Thread.NORM_PRIORITY);
		threadPoolTaskExecutor.setDaemon(true); // 璁剧疆涓哄畧鎶ょ嚎绋嬩笌鍚︽剰涔変笉澶э紝spring瀹瑰櫒鍏抽棴锛岀嚎绋嬫睜鍏抽棴
		threadPoolTaskExecutor.setQueueCapacity(messageProperty.getQueueCapacity());
		threadPoolTaskExecutor.setRejectedExecutionHandler(new AbortPolicy());
		threadPoolTaskExecutor.setAllowCoreThreadTimeOut(false); // default
		threadPoolTaskExecutor.initialize();
		logger.info("*************************璁剧疆娑堟伅閫氱煡绾跨▼姹犲弬鏁�********************");
		logger.info("TaskThread option[MaxThreadNum] is {}", messageProperty.getCoreThreadNum());
		logger.info("TaskThread option[CoreThreadNum] is {}", messageProperty.getMaxThreadNum());
		logger.info("TaskThread option[QueueCapacity] is {}", messageProperty.getQueueCapacity());
		return threadPoolTaskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new MessageAsynUcaughtExceptionHandler();
	}

	 /**
	  * 异常处理实现类.
	  * @author 曾谢波
	  * 2018年7月12日
	  */
	class MessageAsynUcaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

		@Override
		public void handleUncaughtException(Throwable ex, Method method, Object... params) {
			logger.error("鏂规硶 {} 鎵ц鎶涘嚭寮傚父锛屽紓甯镐俊鎭负锛歿}, 鏂规硶鍙傛暟涓猴細{}", method.getName(), ex.getMessage(),
					Arrays.toString(params));

		}

	}

}
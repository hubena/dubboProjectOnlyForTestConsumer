package com.hubena;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * springboot启动类.
 *
 * @author 曾谢波
 * @since 2018年10月28日
 */
@ComponentScan(value = {"com.hubena"})
@SpringBootApplication
public class DubboSpringBootConsumerMain {
	public static void main(String[] args) {
		SpringApplication.run(DubboSpringBootConsumerMain.class, args);
	}
	
}

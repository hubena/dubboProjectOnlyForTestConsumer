package com.hubena.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.hubena.configuration.dubbo.DubboConsumerConfiguration;

/**
 * spring顶层配置类
 *
 * @author 曾谢波
 * @since 2018年10月28日
 */

@Configuration
@Import(value = {DubboConsumerConfiguration.class})
public class SpringConfiguration {

}

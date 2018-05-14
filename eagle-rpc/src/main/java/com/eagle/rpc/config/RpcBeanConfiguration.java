package com.eagle.rpc.config;

import com.eagle.rpc.handler.ConsumerBeanFactoryPostProcessor;
import com.eagle.rpc.handler.ConsumerInstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcBeanConfiguration {
	@Bean
	public ConsumerBeanFactoryPostProcessor getConsumerBeanFactoryPostProcessor(){
		return new ConsumerBeanFactoryPostProcessor();
	}

	@Bean
	public ConsumerInstantiationAwareBeanPostProcessor getConsumerInstantiationAwareBeanPostProcessor(){
		return new ConsumerInstantiationAwareBeanPostProcessor();
	}
}

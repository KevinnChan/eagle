package com.eagle.rpc.handler;

import com.eagle.rpc.bean.ConsumerBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Map;

public class ConsumerBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
		Map<String,ConsumerBean> consumerBeanMap = defaultListableBeanFactory.getBeansOfType(ConsumerBean.class);
		for(ConsumerBean consumerBean : consumerBeanMap.values()){
			AbstractBeanDefinition abstractBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(consumerBean.getInterfaceName()).getBeanDefinition();
			defaultListableBeanFactory.registerBeanDefinition(consumerBean.getInterfaceName(),abstractBeanDefinition);
		}
	}
}

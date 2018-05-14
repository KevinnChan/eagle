package com.eagle.rpc.handler;

import com.eagle.rpc.utils.RpcContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

public class ConsumerInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		Object consumerBean = RpcContext.getConsumerBeanFactory().getBeanInstance(beanName);

		if (consumerBean != null) {
			return consumerBean;
		}

		return super.postProcessBeforeInstantiation(beanClass, beanName);
	}
}

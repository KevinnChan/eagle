package com.eagle.rpc.utils;

import com.eagle.rpc.factory.RpcBeanFactory;
import com.eagle.rpc.factory.impl.ConsumerBeanFactory;
import com.eagle.rpc.factory.impl.ProviderBeanFactory;

public class RpcContext {
	private static final RpcBeanFactory PROVIDER_BEAN_FACTORY = new ProviderBeanFactory();

	private static final RpcBeanFactory CONSUMER_BEAN_FACTORY = new ConsumerBeanFactory();

	public static RpcBeanFactory getProviderBeanFactory(){
		return PROVIDER_BEAN_FACTORY;
	}

	public static RpcBeanFactory getConsumerBeanFactory(){
		return CONSUMER_BEAN_FACTORY;
	}
}

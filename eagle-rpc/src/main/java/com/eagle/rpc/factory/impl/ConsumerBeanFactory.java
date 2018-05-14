package com.eagle.rpc.factory.impl;

import com.eagle.rpc.bean.ConsumerBean;
import com.eagle.rpc.bean.RpcBean;
import com.eagle.rpc.factory.RpcBeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConsumerBeanFactory implements RpcBeanFactory {

	private static final Map<String, ConsumerBean> consumerBeanCache = new ConcurrentHashMap<>();

	@Override
	public Object getBean(String interfaceName) {
		return consumerBeanCache.get(interfaceName);
	}

	@Override
	public Object getBeanInstance(String interfaceName) {
		return consumerBeanCache.get(interfaceName).getTarget();
	}

	@Override
	public void register(RpcBean bean) {
		consumerBeanCache.put(bean.getInterfaceName(), (ConsumerBean) bean);
	}
}

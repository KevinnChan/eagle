package com.eagle.rpc.factory.impl;

import com.eagle.rpc.bean.ProviderBean;
import com.eagle.rpc.bean.RpcBean;
import com.eagle.rpc.factory.RpcBeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProviderBeanFactory implements RpcBeanFactory {

	private static final Map<String,ProviderBean> providerBeanCache = new ConcurrentHashMap<>();

	@Override
	public Object getBean(String interfaceName) {
		return providerBeanCache.get(interfaceName);
	}

	@Override
	public Object getBeanInstance(String interfaceName) {
		return providerBeanCache.get(interfaceName).getTarget();
	}

	@Override
	public void register(RpcBean bean) {
		providerBeanCache.put(bean.getInterfaceName(), (ProviderBean) bean);
	}
}

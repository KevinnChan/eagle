package com.eagle.rpc.handler;

import com.eagle.common.bean.RpcRequest;
import com.eagle.common.bean.SerializationConfig;
import com.eagle.common.serialization.SerializerEngine;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class ConsumerBeanProxyHandler implements InvocationHandler {

	@Resource
	private SerializationConfig serializationConfig;

	public <T> T getProxyObject(Class clz){
		return (T) Proxy.newProxyInstance(clz.getClassLoader(),new Class[]{clz},this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		RpcRequest request = new RpcRequest();
		request.setRequestId(UUID.randomUUID().toString());
		request.setClassName(method.getDeclaringClass().getName());
		request.setMethodName(method.getName());
		request.setParameters(args);
		request.setParameterTypes(method.getParameterTypes());

		byte[] requestData = SerializerEngine.serialize(request,serializationConfig.getSerializationMethod());

		return null;
	}
}

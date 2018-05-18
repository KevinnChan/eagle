package com.eagle.rpc.handler;

import com.eagle.common.bean.RpcRequest;
import com.eagle.common.bean.RpcResponse;
import com.eagle.common.bean.SerializationConfig;
import com.eagle.common.serialization.SerializerEngine;
import com.eagle.rpc.utils.HttpUtils;
import com.eagle.rpc.utils.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Slf4j
public class ConsumerBeanProxyHandler implements InvocationHandler {

	@Resource
	private SerializationConfig serializationConfig;

	public <T> T getProxyObject(Class clz) {
		return (T) Proxy.newProxyInstance(clz.getClassLoader(), new Class[]{clz}, this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		Callable<RpcResponse> callable = (() -> requestHandler(method, args));

		ThreadPoolTaskExecutor threadPoolTaskExecutor = ThreadPoolUtils.getThreadPool("");

		Future<RpcResponse> future = threadPoolTaskExecutor.submit(callable);

		RpcResponse rpcResponse = future.get();

		if (rpcResponse.getThrowable() != null) {
			throw rpcResponse.getThrowable();
		}

		return rpcResponse.getResponseData();
	}

	public RpcResponse requestHandler(Method method, Object[] args) {
		RpcRequest request = new RpcRequest();
		request.setRequestId(UUID.randomUUID().toString());
		request.setClassName(method.getDeclaringClass().getName());
		request.setMethodName(method.getName());
		request.setParameters(args);
		request.setParameterTypes(method.getParameterTypes());

		log.info("远程请求开始：{}", request);

		byte[] requestData = SerializerEngine.serialize(request, serializationConfig.getSerializationMethod());
		String url = "http://127.0.0.1:9001/eagle-rpc";
		byte[] responseData = HttpUtils.doPost(url, requestData);
		RpcResponse rpcResponse = SerializerEngine.deserialize(responseData, RpcResponse.class, serializationConfig.getSerializationMethod());

		log.info("远程请求结束：{}", rpcResponse);

		return rpcResponse;


	}
}

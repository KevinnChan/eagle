package com.eagle.remoting.http.servlet;

import com.eagle.common.bean.RpcRequest;
import com.eagle.common.bean.RpcResponse;
import com.eagle.common.bean.SerializationConfig;
import com.eagle.common.serialization.SerializerEngine;
import com.eagle.rpc.utils.RpcContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@Slf4j
public class JettyServlet extends HttpServlet {

	private static final String APPLICATION_JSON = "application/json;charset=utf-8";

	@Resource
	private SerializationConfig serializationConfig;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		if(!req.getMethod().equals("POST")){
			throw new RuntimeException("Unsupported Request Method : " + req.getMethod() + ", POST Accept Only.");
		}

		long startTime = System.currentTimeMillis();
		RpcResponse response = new RpcResponse();
		try {
			RpcRequest request = SerializerEngine.deserialize(IOUtils.toByteArray(req.getInputStream()), RpcRequest.class, serializationConfig.getSerializationMethod());
			log.info("远程请求调用开始[" + request.getClassName() + "." + request.getMethodName() + "] 参数 "
					+ request.getParameters());
			response.setRequestId(request.getRequestId());
			Object target = RpcContext.getProviderBeanFactory().getBeanInstance(request.getClassName());

			if (target == null) {
				throw new RuntimeException(request.getClassName() + " is not support");
			}

			Method method = target.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
			response.setResponseData(method.invoke(target, request.getParameters()));
			resp.setStatus(HttpServletResponse.SC_OK);// http code 200
			resp.setContentType(APPLICATION_JSON);

			byte[] responseData = SerializerEngine.serialize(response,serializationConfig.getSerializationMethod());
			resp.getOutputStream().write(responseData);
		} catch (Exception e) {
			response.setThrowable(e);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);// http code 500
			byte[] responseData = SerializerEngine.serialize(response,serializationConfig.getSerializationMethod());
			resp.getOutputStream().write(responseData);
			log.error("Remoting invoked exception caused: ", e);
		}

		log.info("远程请求调用结束:[ " + response + " ], 耗时: [" + (System.currentTimeMillis() - startTime) + "ms]");
	}
}

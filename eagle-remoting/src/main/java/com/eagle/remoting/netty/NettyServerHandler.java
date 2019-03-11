package com.eagle.remoting.netty;

import com.eagle.common.bean.RpcRequest;
import com.eagle.common.bean.RpcResponse;
import com.eagle.rpc.utils.RpcContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * Created by Kevin on 2018/9/10.
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

	@Override
	public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
		channelHandlerContext.flush();
	}

	@Override
	public void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest request) throws Exception {
		if (channelHandlerContext.channel().isWritable()) {
			channelHandlerContext.writeAndFlush(doInvoke(request,true));
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) {
		log.error("netty server handler error: ", throwable.getMessage());
		channelHandlerContext.close();
	}


	private RpcResponse doInvoke(RpcRequest request, Boolean isTest) {
		log.info("接收到请求 {}",request);

		if(isTest){
			RpcResponse response = new RpcResponse();
			response.setResponseData(request);
			return response;
		}
		return doInvoke(request);
	}

	private RpcResponse doInvoke(RpcRequest request) {
		long startTime = System.currentTimeMillis();
		RpcResponse response = new RpcResponse();

		try {
			log.info("远程请求调用开始[" + request.getClassName() + "." + request.getMethodName() + "] 参数 "
					+ request.getParameters());
			response.setRequestId(request.getRequestId());
			Object target = RpcContext.getProviderBeanFactory().getBeanInstance(request.getClassName());

			if (target == null) {
				throw new RuntimeException(request.getClassName() + " is not support");
			}

			Method method = target.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
			response.setResponseData(method.invoke(target, request.getParameters()));

		} catch (Exception e) {
			response.setThrowable(e);
			log.error("Remoting invoked exception caused: ", e);
		}
		log.info("远程请求调用结束:[ " + response + " ], 耗时: [" + (System.currentTimeMillis() - startTime) + "ms]");

		return response;
	}
}

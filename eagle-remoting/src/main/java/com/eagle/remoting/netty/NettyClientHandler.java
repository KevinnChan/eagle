package com.eagle.remoting.netty;

import com.eagle.common.bean.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Kevin on 2018/9/11.
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<RpcResponse>{

	@Override
	public void channelReadComplete(ChannelHandlerContext channelHandlerContext) {
		channelHandlerContext.flush();
	}

	@Override
	public void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response) throws Exception {
		log.info("receive data from server: " + response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable){
		log.error("netty client handler error: ",throwable.getMessage());
		channelHandlerContext.close();
	}
}

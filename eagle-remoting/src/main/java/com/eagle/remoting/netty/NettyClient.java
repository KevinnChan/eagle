package com.eagle.remoting.netty;

import com.eagle.common.bean.RpcRequest;
import com.eagle.common.enums.SerializationEnum;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Created by Kevin on 2018/9/10.
 */
@Slf4j
public class NettyClient {

	public static void main(String[] args) {
		new NettyClient().connect("127.0.0.1",8080);
	}

	public void connect(String host,int port){
		// 创建客户端处理I/O读写的线程组
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
			// NioSocketChannel
			.channel(NioSocketChannel.class)
			// 设置TCP参数
			.option(ChannelOption.TCP_NODELAY,true)
			.handler(new ChannelInitializer<NioSocketChannel>() {
				@Override
				protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
					nioSocketChannel.pipeline().addLast(new FastJsonEncoder(SerializationEnum.FASTJSON));
					nioSocketChannel.pipeline().addLast(new FastJsonDecoder(RpcRequest.class, SerializationEnum.FASTJSON));
					// client I/O handler
					nioSocketChannel.pipeline().addLast(new NettyClientHandler());
				}
			});


			// 发起异步连接操作
			ChannelFuture cf = bootstrap.connect(host,port).sync();
			for (int i =0;i<1000;i++){
				// 待发送的数据
				RpcRequest request = new RpcRequest();
				request.setRequestId(UUID.randomUUID().toString());

				// 向服务端发送数据
				ChannelFuture channelFuture = cf.channel().writeAndFlush(request);
				channelFuture.syncUninterruptibly();
			}

			cf.channel().closeFuture().sync();

		} catch (Exception e) {
			log.error("netty client exception: ",e);
		} finally {
			group.shutdownGracefully();
		}
	}
}

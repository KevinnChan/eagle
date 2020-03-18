package com.eagle.remoting.netty.server;

import com.eagle.common.bean.RpcRequest;
import com.eagle.common.enums.SerializationEnum;
import com.eagle.remoting.netty.codec.json.FastJsonDecoder;
import com.eagle.remoting.netty.codec.json.FastJsonEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Kevin on 2018/9/10.
 */
@Slf4j
public class NettyServer {

	public static void main(String[] args) throws Exception{
		new NettyServer().bind(8080);
	}

	public void bind(int port) throws Exception{
		// 创建两个 EventLoopGroup 实例
		// EventLoopGroup 包含一组专门处理网络事件的NIO线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try{
			// 创建服务端服务启动类
			ServerBootstrap b = new ServerBootstrap();
			// 设置NIO线程组
			b.group(bossGroup,workerGroup)
			// 设置socketChannel 对应处理 JDK 类 ServerSocketChannel
			.channel(NioServerSocketChannel.class)
			// 设置TCP参数 连接请求最大队列长度
			.option(ChannelOption.SO_BACKLOG,1024)
			// 设置I/O事件处理类 用来处理消息的编码及我们的业务逻辑
			.childHandler(new ChannelInitializer<NioSocketChannel>() {
				@Override
				protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
					// 参数为事件处理类
					nioSocketChannel.pipeline().addLast(new FastJsonEncoder(SerializationEnum.FASTJSON));
					nioSocketChannel.pipeline().addLast(new FastJsonDecoder(RpcRequest.class, SerializationEnum.FASTJSON));
					nioSocketChannel.pipeline().addLast(new NettyServerHandler());
				}
			});

			//绑定端口 同步等待成功
			ChannelFuture channelFuture = b.bind(port).sync();
			//等待服务端监听端口关闭
			channelFuture.channel().closeFuture().sync();

		} catch (Exception e){
			log.error("netty server error: ",e);
		} finally {
			// 优雅释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}

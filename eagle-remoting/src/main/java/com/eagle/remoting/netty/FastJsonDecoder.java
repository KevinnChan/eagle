package com.eagle.remoting.netty;

import com.eagle.common.enums.SerializationEnum;
import com.eagle.common.serialization.SerializerEngine;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Kevin on 2018/9/11.
 */
public class FastJsonDecoder extends ByteToMessageDecoder {

	private Class<?> genericClass;

	private SerializationEnum serializeType;

	public FastJsonDecoder(Class<?> genericClass, SerializationEnum serializeType) {
		this.genericClass = genericClass;
		this.serializeType = serializeType;
	}


	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
		//读取消息头，整个消息的长度字段
		if (in.readableBytes() < 4) {
			return;
		}

		in.markReaderIndex();
		int dataLength = in.readInt();
		if (dataLength < 0) {
			channelHandlerContext.close();
		}

		//读取body
		if (in.readableBytes() < dataLength) {
			in.resetReaderIndex();
			return;
		}

		byte[] data = new byte[dataLength];
		in.readBytes(data);

		Object obj = SerializerEngine.deserialize(data, genericClass, serializeType);
		out.add(obj);
	}
}
package com.eagle.remoting.netty;

import com.eagle.common.enums.SerializationEnum;
import com.eagle.common.serialization.SerializerEngine;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Kevin on 2018/9/11.
 */
public class FastJsonEncoder extends MessageToByteEncoder {

	private SerializationEnum serializeType;

	public FastJsonEncoder(SerializationEnum serializeType) {
		this.serializeType = serializeType;
	}

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, Object in, ByteBuf out) throws Exception {
		byte[] data = SerializerEngine.serialize(in, serializeType);
		//首先写入消息长度
		out.writeInt(data.length);
		// body
		out.writeBytes(data);
	}
}

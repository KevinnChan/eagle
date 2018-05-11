package com.eagle.remoting.serialization.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.eagle.remoting.serialization.Serializable;

/**
 * motan<渣浪> fastjson 序列化
 * 嵌套的不能搞
 * @author Kevin
 *
 */
public class FastJsonSupport implements Serializable {
	@Override
	public <T> byte[] serialize(T object) {
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.config(SerializerFeature.WriteClassName, true);
        serializer.write(object);
        return out.toBytes(null);
	}

	@Override
	public <T> T deserialize(byte[] text,Class<T> className) {
		try {
			return JSON.parseObject(new String(text),className);
		} catch (Exception e) {
			throw new RuntimeException("Serialization error by FastJson cause: " , e);
		}
	}
}

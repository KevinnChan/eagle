package com.eagle.common.serialization.impl;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.eagle.common.serialization.Serializable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//import org.springframework.objenesis.Objenesis;
//import org.springframework.objenesis.ObjenesisStd;

public class ProtoStuffSupport implements Serializable {

	private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap();

	//private static Objenesis objenesis = new ObjenesisStd(true);


	private static <T> Schema<T> getSchema(Class<T> cls) {
		Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
		if (schema == null) {
			schema = RuntimeSchema.createFrom(cls);
			cachedSchema.put(cls, schema);
		}

		return schema;
	}


	public <T> byte[] serialize(T object) {
		Class<T> cls = (Class<T>) object.getClass();
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

		try {
			Schema<T> schema = getSchema(cls);
			return ProtostuffIOUtil.toByteArray(object, schema, buffer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			buffer.clear();
		}
	}

	public <T> T deserialize(byte[] data, Class<T> type) {
		try {
			T message = type.newInstance();
			Schema<T> schema = getSchema(type);
			ProtostuffIOUtil.mergeFrom(data, message, schema);
			return message;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

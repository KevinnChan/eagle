package com.eagle.common.serialization;

import com.eagle.common.enums.SerializationEnum;
import com.eagle.common.serialization.impl.FastJsonSupport;
import com.eagle.common.serialization.impl.ProtoStuffSupport;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SerializerEngine {
	private static final Map<String, Serializable> serializerEngine = new ConcurrentHashMap<>();

	static {
		serializerEngine.put(SerializationEnum.FASTJSON.getName(), new FastJsonSupport());
		serializerEngine.put(SerializationEnum.PROTOSTUFF.getName(), new ProtoStuffSupport());
	}

	public static <T> byte[] serialize(T object, String type) {
		return getSerializableInstance(type).serialize(object);
	}

	public static <T> T deserialize(byte[] data, Class<T> clz, String type) {
		return getSerializableInstance(type).deserialize(data, clz);
	}

	private static Serializable getSerializableInstance(String type) {
		if (type != null && serializerEngine.containsKey(type)) {
			return serializerEngine.get(type);
		}

		throw new RuntimeException("Unsupported serialization type");
	}
}

package com.eagle.remoting.serialization;

import com.eagle.common.enums.SerializationEnum;
import com.eagle.remoting.serialization.impl.FastJsonSupport;
import com.eagle.remoting.serialization.impl.ProtoStuffSupport;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SerializerEngine {
	private static final Map<Integer, Serializable> serializerEngine = new ConcurrentHashMap<>();

	static {
		serializerEngine.put(SerializationEnum.FASTJSON.getType(), new FastJsonSupport());
		serializerEngine.put(SerializationEnum.PROTOSTUFF.getType(), new ProtoStuffSupport());
	}

	public static <T> byte[] serialize(T object, Integer type) {
		return getSerializableInstance(type).serialize(object);
	}

	public static <T> T deserialize(byte[] data, Class<T> clz, Integer type) {
		return getSerializableInstance(type).deserialize(data, clz);
	}

	private static Serializable getSerializableInstance(Integer type) {
		if (type != null && serializerEngine.containsKey(type)) {
			return serializerEngine.get(type);
		}

		throw new RuntimeException("Unsupported serialization type");
	}
}

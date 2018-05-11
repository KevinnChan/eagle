package com.eagle.remoting.serialization;

public interface Serializable {
	<T> byte[] serialize(T object);
	
	<T> T deserialize(byte[] data, Class<T> type);
}

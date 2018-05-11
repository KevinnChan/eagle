package com.eagle.common.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SerializationEnum {
	JACKSON(0,"jackson"),
	FASTJSON(1,"fastjson"),
	PROTOSTUFF(2,"protostuff"),
	HESSIAN(3,"hessian"),
	JDK(4,"jdk");

	private int type;
	private String name;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	int getTypeByName(String name){
		for (SerializationEnum serializationEnum : SerializationEnum.values()){
			if(name.equals(serializationEnum.getName())){
				return serializationEnum.type;
			}
		}

		return 0;
	}
}

package com.eagle.common.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SerializationEnum {
	//JACKSON("jackson"),
	FASTJSON("fastjson"),
	PROTOSTUFF("protostuff");
	//HESSIAN("hessian"),
	//JDK("jdk");

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

package com.eagle.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Kevin on 2018/4/9.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	// 请求流水号  1.日志   2. 异步返回区分的唯一标识
	private String requestId;
	// 请求类名
	private String className;
	// 请求方法
	private String methodName;
	// 参数类型 不能加<?> 不然序列化报错
	private Class[] parameterTypes;
	// 具体参数
	private Object[] parameters;
}

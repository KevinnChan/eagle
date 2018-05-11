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
public class RpcResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	// 请求流水号
	private String requestId;
	// 正常返回
	private Object responseData;
	// 异常情况
	private Throwable throwable;
}

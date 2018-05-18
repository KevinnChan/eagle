package com.eagle.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ThreadPoolConfig {
	private String serverName;
	private Integer corePoolSize;
	private Integer maxPoolSize;
	private Integer keepAliveSeconds;
	private Integer queueCapacity;
	private String rejectedExecutionHandler;

	private Boolean userDefault = true;
}

package com.eagle.rpc.utils;

import com.eagle.common.bean.ThreadPoolConfig;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolUtils {
	private static final Map<String,ThreadPoolTaskExecutor> THREAD_POOL_TASK_EXECUTOR_MAP = new ConcurrentHashMap<>();

	public static void create(List<ThreadPoolConfig> threadPoolConfigList){
		if(!CollectionUtils.isEmpty(threadPoolConfigList)){
			for (ThreadPoolConfig threadPoolConfig : threadPoolConfigList){

				if(threadPoolConfig.getUserDefault()){
					threadPoolConfig = defaultConfig();
				}

				ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
				threadPoolTaskExecutor.setCorePoolSize(threadPoolConfig.getCorePoolSize());
				threadPoolTaskExecutor.setMaxPoolSize(threadPoolConfig.getMaxPoolSize());
				threadPoolTaskExecutor.setKeepAliveSeconds(threadPoolConfig.getKeepAliveSeconds());
				threadPoolTaskExecutor.setQueueCapacity(threadPoolConfig.getQueueCapacity());
				threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

				THREAD_POOL_TASK_EXECUTOR_MAP.put(threadPoolConfig.getServerName(),threadPoolTaskExecutor);

			}
		}
	}

	public static ThreadPoolTaskExecutor getThreadPool(String serverName){
		if(THREAD_POOL_TASK_EXECUTOR_MAP.containsKey(serverName)){
			return THREAD_POOL_TASK_EXECUTOR_MAP.get(serverName);
		}

		return null;
	}

	private static ThreadPoolConfig defaultConfig(){
		ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();
		threadPoolConfig.setCorePoolSize(10);
		threadPoolConfig.setMaxPoolSize(20);
		threadPoolConfig.setKeepAliveSeconds(3000);
		threadPoolConfig.setQueueCapacity(1000);

		return threadPoolConfig;
	}
}

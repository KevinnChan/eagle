package com.eagle.rpc.bean;

import com.eagle.rpc.utils.RpcContext;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;

@ToString
public class ProviderBean extends RpcBean implements InitializingBean {

	@Override
	public void afterPropertiesSet() {
		RpcContext.getProviderBeanFactory().register(this);
	}
}

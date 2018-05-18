package com.eagle.rpc.factory;

public interface RpcBeanFactory extends Registrable {
	Object getBean(String interfaceName);

	Object getBeanInstance(String interfaceName);
}

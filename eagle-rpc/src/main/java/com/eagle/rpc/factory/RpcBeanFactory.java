package com.eagle.rpc.factory;

public interface RpcBeanFactory extends Registable{
	Object getBean(String interfaceName);

	Object getBeanInstance(String interfaceName);
}

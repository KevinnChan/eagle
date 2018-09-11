package com.eagle.rpc.bean;

import com.eagle.rpc.handler.ConsumerBeanProxyHandler;
import com.eagle.rpc.utils.RpcContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;

@Data
@ToString
@NoArgsConstructor
public class ConsumerBean extends RpcBean implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws ClassNotFoundException {
		this.setTarget(new ConsumerBeanProxyHandler().getProxyObject(Class.forName(this.getInterfaceName())));
		RpcContext.getConsumerBeanFactory().register(this);
	}

}

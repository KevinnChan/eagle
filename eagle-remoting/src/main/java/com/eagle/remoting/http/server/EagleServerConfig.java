package com.eagle.remoting.http.server;

import com.eagle.common.bean.ServerConfig;
import com.eagle.remoting.http.server.impl.EagleJettyServer;
import org.springframework.beans.factory.InitializingBean;

public class EagleServerConfig implements InitializingBean {

	private Integer port;

	private ServerConfig serverConfig = new ServerConfig();

	private EagleJettyServer eagleServer = new EagleJettyServer();

	private static Boolean START_SERVER = false;

	public void setPort(Integer port) {
		portValidationCheck(port);
		this.serverConfig.setPort(port);
		this.eagleServer.setServerConfig(serverConfig);
		START_SERVER = true;
	}

	private void portValidationCheck(Integer port) {
		if (port == null || port <= 0 || port >= 65535) {
			throw new IllegalArgumentException("Invalid port: " + port);
		}
	}

	@Override
	public void afterPropertiesSet(){
		if (START_SERVER) {
			eagleServer.startServer();
		}
	}
}

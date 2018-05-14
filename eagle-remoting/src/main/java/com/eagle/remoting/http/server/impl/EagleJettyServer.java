package com.eagle.remoting.http.server.impl;

import com.eagle.common.bean.ServerConfig;
import com.eagle.remoting.http.server.EagleServer;
import com.eagle.remoting.http.servlet.JettyServlet;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


@Slf4j
public class EagleJettyServer implements EagleServer {

	private Server server;

	private ServerConfig serverConfig;

	@Override
	public void startServer() {

		if (serverConfig == null) {
			throw new RuntimeException("ServerConfig is not initialized check your config please.");
		}

		try {
			server = new Server(serverConfig.getPort());
			ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
			servletContextHandler.setContextPath("/eagle-rpc");
			servletContextHandler.addServlet(new ServletHolder(new JettyServlet()), "/*");
			server.setHandler(servletContextHandler);
			server.start();
		} catch (Exception e) {
			log.error("Fail to start jetty server cause: ", e);
		}
	}

	@Override
	public void stopServer() {
		if (server != null) {
			try {
				server.stop();
			} catch (Exception e) {
				log.error("Fail to stop jetty server cause: ", e);
			}
		}
	}

	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}
}

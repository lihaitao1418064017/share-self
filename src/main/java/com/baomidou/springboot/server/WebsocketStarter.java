package com.baomidou.springboot.server;

import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerStarter;

import java.io.IOException;

/**
* @Description:    启动类
* @Author:         LiHaitao
* @CreateDate:     2018/8/14 22:01
* @UpdateUser:
* @UpdateDate:     2018/8/14 22:01
* @UpdateRemark:
* @Version:        1.0.0
*/
public class WebsocketStarter {

	private WsServerStarter wsServerStarter;
	private ServerGroupContext serverGroupContext;


	public WebsocketStarter(int port, WsMsgHandler wsMsgHandler) throws Exception {
		wsServerStarter = new WsServerStarter(port, wsMsgHandler);
		serverGroupContext = wsServerStarter.getServerGroupContext();
		serverGroupContext.setName(ServerConfig.PROTOCOL_NAME);
		serverGroupContext.setServerAioListener(ServerAioListener.me);
		//设置ip监控
		serverGroupContext.setIpStatListener(IpStatListener.me);
		//设置ip统计时间段
		serverGroupContext.ipStats.addDurations(ServerConfig.IpStatDuration.IPSTAT_DURATIONS);
		//设置心跳超时时间
		serverGroupContext.setHeartbeatTimeout(ServerConfig.HEARTBEAT_TIMEOUT);
		//如果你希望通过wss来访问，就加上下面的代码吧，不过首先你得有SSL证书（证书必须和域名相匹配，否则可能访问不了ssl）
//		String keyStoreFile = "classpath:config/ssl/keystore.jks";
//		String trustStoreFile = "classpath:config/ssl/keystore.jks";
//		String keyStorePwd = "214323428310224";
//		serverGroupContext.useSsl(keyStoreFile, trustStoreFile, keyStorePwd);
		
	}
	/**
	 * @author tanyaowu
	 * @throws IOException
	 */
	public static void start() throws Exception {
		WebsocketStarter appStarter = new WebsocketStarter(ServerConfig.SERVER_PORT, WsMsgHandler.me);
		appStarter.wsServerStarter.start();
	}

	/**
	 * @return the serverGroupContext
	 */
	public ServerGroupContext getServerGroupContext() {
		return serverGroupContext;
	}

	public WsServerStarter getWsServerStarter() {
		return wsServerStarter;
	}
	/**
	* @Description:    通信接口启动入口
	* @Author:         Lihaitao
	* @Date:       2018/8/15 17:39
	* @UpdateUser:
	* @UpdateRemark:
	*/
	public static void main(String[] args) throws Exception {
		start();
	}

}

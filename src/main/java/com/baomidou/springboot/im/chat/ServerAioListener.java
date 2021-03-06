/**
 * 
 */
package com.baomidou.springboot.im.chat;

import com.baomidou.springboot.common.ConstantsPub;
import com.baomidou.springboot.config.ServerConfig;
import com.baomidou.springboot.auth.entity.User;
import com.baomidou.springboot.auth.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.server.WsServerAioListener;

/**
* @Description:    根据情况完成此类，监听
* @Author:         LiHaitao
* @CreateDate:     2018/8/14 22:00
* @UpdateUser:
* @UpdateDate:     2018/8/14 22:00
* @UpdateRemark:
* @Version:        1.0.0
*/
public class ServerAioListener extends WsServerAioListener {
	private static Logger log = LoggerFactory.getLogger(ServerAioListener.class);

	public static final ServerAioListener me = new ServerAioListener();
	@Autowired
	private IUserService userService;

	private ServerAioListener() {

	}

	@Override
	public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
		super.onAfterConnected(channelContext, isConnected, isReconnect);
		if (log.isInfoEnabled()) {
			log.info("onAfterConnected\r\n{}", channelContext);
		}
		System.err.println("aio-------------------");
	}

	@Override
	public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) throws Exception {
		super.onAfterSent(channelContext, packet, isSentSuccess);
		if (log.isInfoEnabled()) {
			log.info("onAfterSent\r\n{}\r\n{}", packet.logstr(), channelContext);
		}
	}

	@Override
	public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
		super.onBeforeClose(channelContext, throwable, remark, isRemove);
		if (log.isInfoEnabled()) {
			log.info("onBeforeClose\r\n{}", channelContext);
		}

		WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();

		if (wsSessionContext.isHandshaked()) {
			String userid=channelContext.getUserid();
			User user=userService.selectById(userid);
			int count = Aio.getAllChannelContexts(channelContext.getGroupContext()).getObj().size();

			String msg = user.getName() + " 离开了，现在共有【" + count + "】人在线";
			//用tio-websocket，服务器发送到客户端的Packet都是WsResponse
			WsResponse wsResponse = WsResponse.fromText(msg, ServerConfig.CHARSET);
			//群发出离线结果
			Aio.sendToGroup(channelContext.getGroupContext(), ConstantsPub.GROUP_ID, wsResponse);
		}
	}

	@Override
	public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize) throws Exception {
		super.onAfterDecoded(channelContext, packet, packetSize);
		if (log.isInfoEnabled()) {
			log.info("onAfterDecoded\r\n{}\r\n{}", packet.logstr(), channelContext);
		}
	}

	@Override
	public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes) throws Exception {
		super.onAfterReceivedBytes(channelContext, receivedBytes);
		if (log.isInfoEnabled()) {
			log.info("onAfterReceivedBytes\r\n{}", channelContext);
		}
	}

	@Override
	public void onAfterHandled(ChannelContext channelContext, Packet packet, long cost) throws Exception {
		super.onAfterHandled(channelContext, packet, cost);

		/*if (log.isInfoEnabled()) {
			log.info("onAfterHandled\r\n{}\r\n{}", packet.logstr(), channelContext);
		}*/
	}

}

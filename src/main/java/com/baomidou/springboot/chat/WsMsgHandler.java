package com.baomidou.springboot.chat;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.springboot.common.ConstantsPub;
import com.baomidou.springboot.domain.FriendGroup;
import com.baomidou.springboot.domain.GroupUser;
import com.baomidou.springboot.domain.dto.Msg;
import com.baomidou.springboot.service.IFriendGroupService;
import com.baomidou.springboot.service.IGroupUserService;
import com.baomidou.springboot.service.IMsgService;
import com.baomidou.springboot.util.IDGenerator;
import com.baomidou.springboot.util.MsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.util.List;

/**
* @Description:    通信入口
* @Author:         LiHaitao
* @CreateDate:     2018/8/14 22:08
* @UpdateUser:
* @UpdateDate:     2018/8/14 22:08
* @UpdateRemark:
* @Version:        1.0.0
*/
@SuppressWarnings("MagicConstant")
public class WsMsgHandler implements IWsMsgHandler {
	private static Logger log = LoggerFactory.getLogger(WsMsgHandler.class);

	public static final WsMsgHandler me = new WsMsgHandler();

	@Autowired
	private IMsgService msgService;

	@Autowired
	private IFriendGroupService friendGroupService;

	@Autowired
	private IGroupUserService groupUserService;


	private WsMsgHandler() {

	}

	/**
	 * 握手时走这个方法，业务可以在这里获取cookie，request参数等
	 */
	@Override
	public HttpResponse handshake(HttpRequest request, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
		String clientip = request.getClientIp();
		System.err.println(clientip+"clientip===========");
		log.info("收到来自{}的ws握手包\r\n{}", clientip, request.toString());
		return httpResponse;
	}

	/**
	* @Description:    连接时执行
	* @Author:         Lihaitao
	* @Date:       2018/8/15 18:02
	* @UpdateUser:
	* @UpdateRemark:
	*/
	@Override
	public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
		/**用户连接*/
		String userId = httpRequest.getParam("id");
		/**为用户绑定通道*/
		Aio.bindUser(channelContext,userId);
		msgService.sendOfflineMessage(channelContext,userId);
		/**为用户绑定群组*/
		groupUserService.bingAllGroup(userId,channelContext);
		log.info("Connection successful！");
	}

	/**
	 * 字节消息（binaryType = arraybuffer）过来后会走这个方法
	 */
	@Override
	public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
		return null;
	}

	/**
	 * 当客户端发close flag时，会走这个方法，这相当于一个协议，一般不会走到这个方法
	 */
	@Override
	public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
		Aio.remove(channelContext, "receive close flag");
		log.info("close flag");
		return null;
	}

	/**
	* @Description:    字符消息过来会走这个方法
	* @Author:         LiHaitao
	* @Date:       2018/8/15 18:02
	* @UpdateUser:
	* @UpdateRemark:
	*/
	@Override
	public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
	        /**处理消息*/
			Boolean suc=msgService.sendOnlineMessage(channelContext,text);
			if (suc.equals(false)){
			    log.info("消息处理失败");
            }
		return true;
	}

}

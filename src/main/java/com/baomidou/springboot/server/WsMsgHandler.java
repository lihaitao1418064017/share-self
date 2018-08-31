package com.baomidou.springboot.server;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.springboot.common.ConstantsPub;
import com.baomidou.springboot.pojo.Msg;
import com.baomidou.springboot.util.MsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
* @Description:    通信入口
* @Author:         LiHaitao
* @CreateDate:     2018/8/14 22:08
* @UpdateUser:
* @UpdateDate:     2018/8/14 22:08
* @UpdateRemark:
* @Version:        1.0.0
*/
public class WsMsgHandler implements IWsMsgHandler {
	private static Logger log = LoggerFactory.getLogger(WsMsgHandler.class);

	public static final WsMsgHandler me = new WsMsgHandler();

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

//		//绑定到群组，后面会有群发
		Aio.bindGroup(channelContext, ConstantsPub.GROUP_ID);
//		//获取聊天人数
//		int count = Tio.getAllChannelContexts(channelContext.groupContext).getObj().size();

		String userId = httpRequest.getParam("id");
		Aio.bindUser(channelContext,userId);
		System.err.println("userid---->"+userId);

//		String msg = channelContext.getClientNode().toString() + " 进来了，现在共有【" + count + "】人在线";
		String msg="Connection successful！";
		//用tio-websocket，服务器发送到客户端的Packet都是WsResponse
		WsResponse wsResponse = WsResponse.fromText(msg, ServerConfig.CHARSET);
		//群发
		Aio.sendToGroup(channelContext.getGroupContext(), ConstantsPub.GROUP_ID, wsResponse);
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
		//将字符消息json转化成消息对象

		String jsonStr = JSONUtil.toJsonStr(text);
		Msg msg= JSON.parseObject(jsonStr,Msg.class);
		System.err.println("msg:  "+msg.toString());
//		WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
//		HttpRequest httpRequest = wsSessionContext.getHandshakeRequestPacket();//获取websocket握手包

		if (MsgUtil.existsUser(msg.getTo(),channelContext)) {
		System.err.println("发给------>"+msg.getTo());
			MsgUtil.sendToUser(msg.getTo(),msg,channelContext);
		}
		return null;
	}

}

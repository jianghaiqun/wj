package com.sinosoft.framework.utility.weixin.wxtools.vo.recv;

import com.sinosoft.framework.utility.weixin.wxtools.vo.WxRightsBaseMsg;

public class WxRightsMsg extends WxRightsBaseMsg {
	
	public WxRightsMsg(String openId,String appId,String timeStamp,String msgType,String feedBackId
			,String reason,String appSignature,String signMethod) {
		super(openId,appId,timeStamp,msgType,feedBackId
				,reason,appSignature,signMethod);
	}
	
	public WxRightsMsg(WxRightsMsg msg) {
		this(msg.getOpenId(),msg.getAppId(),msg.getTimeStamp(),msg.getMsgType(),msg.getFeedBackId()
				,msg.getReason(),msg.getAppSignature(),msg.getSignMethod());
	}
	
}

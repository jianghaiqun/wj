package com.sinosoft.framework.utility.weixin.wxtools.vo;


public class WxRightsBaseMsg {
	private String openId;//用户唯一标识符
	private String appId;//商户id
	private String timeStamp;//申请时间
	private String msgType;//申请类型
	private String feedBackId;//投诉单号
	private String reason;//原因
	private String appSignature;//唯一标识
	private String signMethod;//
	
	public WxRightsBaseMsg(String openId,String appId,String timeStamp,String msgType,String feedBackId
			,String reason,String appSignature,String signMethod) {
		this.openId = openId;
		this.appId = appId;
		this.timeStamp = timeStamp;
		this.msgType = msgType;
		this.feedBackId = feedBackId;
		this.reason = reason;
		this.appSignature = appSignature;
		this.signMethod = signMethod;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getFeedBackId() {
		return feedBackId;
	}

	public void setFeedBackId(String feedBackId) {
		this.feedBackId = feedBackId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAppSignature() {
		return appSignature;
	}

	public void setAppSignature(String appSignature) {
		this.appSignature = appSignature;
	}

	public String getSignMethod() {
		return signMethod;
	}

	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}
	
}

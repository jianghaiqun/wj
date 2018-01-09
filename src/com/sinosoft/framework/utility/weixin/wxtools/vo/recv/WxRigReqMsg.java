package com.sinosoft.framework.utility.weixin.wxtools.vo.recv;

public class WxRigReqMsg extends WxRightsMsg {
	
	private String transId;// 交易号
	private String extInfo;//备注信息、电话等
	private String solution;//用户希望解决方案
	
	public WxRigReqMsg(WxRightsMsg msg,String transId,String extInfo,String solution) {
		super(msg);
		this.transId = transId;
		this.extInfo = extInfo;
		this.solution = solution;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	 
}

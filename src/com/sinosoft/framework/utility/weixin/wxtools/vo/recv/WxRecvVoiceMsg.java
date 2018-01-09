package com.sinosoft.framework.utility.weixin.wxtools.vo.recv;

public class WxRecvVoiceMsg extends WxRecvMsg {
	
	private String mediaId;
	private String format;
	
	public WxRecvVoiceMsg(WxRecvMsg msg,String mediaId,String format) {
		super(msg);
		this.mediaId = mediaId;
		this.format = format;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}

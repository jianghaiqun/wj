package com.sinosoft.framework.utility.weixin.wxtools.vo.send;

import org.jdom.Document;
import org.jdom.Element;

public class WxSendImgMsg extends WxSendMsg {
	private String pic;
	
	public WxSendImgMsg(WxSendMsg msg,String pic) {
		super(msg);
		setMsgType("image");
		this.pic = pic;
	}
	
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	@Override
	public Document toDocument() {
		Document doc = super.toDocument();
		Element image = createElement(doc.getRootElement(), "Image", "");
		createElement(image, "MediaId", getPic());
		return doc;
	}
}

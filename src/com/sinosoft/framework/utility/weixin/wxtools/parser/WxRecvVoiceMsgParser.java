package com.sinosoft.framework.utility.weixin.wxtools.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvVoiceMsg;

public class WxRecvVoiceMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvVoiceMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		String event = getElementText(root, "MediaId");
		String eventKey = getElementText(root, "Format");
		
		return new WxRecvVoiceMsg(msg, event,eventKey);
	}

}

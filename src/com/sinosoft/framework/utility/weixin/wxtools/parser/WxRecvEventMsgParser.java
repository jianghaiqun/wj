package com.sinosoft.framework.utility.weixin.wxtools.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvEventMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvMsg;

public class WxRecvEventMsgParser extends WxRecvMsgBaseParser {

	@Override
	protected WxRecvEventMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		String event = getElementText(root, "Event");
		String eventKey = getElementText(root, "EventKey");
		
		return new WxRecvEventMsg(msg, event,eventKey);
	}

}

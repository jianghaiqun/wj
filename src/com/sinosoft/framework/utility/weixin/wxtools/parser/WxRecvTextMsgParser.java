package com.sinosoft.framework.utility.weixin.wxtools.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvTextMsg;

public class WxRecvTextMsgParser extends WxRecvMsgBaseParser{

	@Override
	protected WxRecvTextMsg parser(Element root, WxRecvMsg msg) throws JDOMException {
		return new WxRecvTextMsg(msg, getElementText(root, "Content"));
	}

	
}

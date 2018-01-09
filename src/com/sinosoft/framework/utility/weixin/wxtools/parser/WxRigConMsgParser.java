package com.sinosoft.framework.utility.weixin.wxtools.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRigConMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRightsMsg;

public class WxRigConMsgParser extends WxRightsMsgBaseParser {

	@Override
	protected WxRigConMsg parser(Element root, WxRightsMsg msg) throws JDOMException {
		
		return new WxRigConMsg(msg);
	}

}

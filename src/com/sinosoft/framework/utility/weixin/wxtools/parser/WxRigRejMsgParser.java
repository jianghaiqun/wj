package com.sinosoft.framework.utility.weixin.wxtools.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRigRejMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRightsMsg;

public class WxRigRejMsgParser extends WxRightsMsgBaseParser {

	@Override
	protected WxRigRejMsg parser(Element root, WxRightsMsg msg) throws JDOMException {
		
		return new WxRigRejMsg(msg);
	}

}

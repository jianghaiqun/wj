package com.sinosoft.framework.utility.weixin.wxtools.parser;

import org.jdom.Element;
import org.jdom.JDOMException;

import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRigReqMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRightsMsg;

public class WxRigReqMsgParser extends WxRightsMsgBaseParser {

	@Override
	protected WxRigReqMsg parser(Element root, WxRightsMsg msg) throws JDOMException {
		String transId = getElementText(root, "TransId");
		String extInfo = getElementText(root, "Solution");
		String solution = getElementText(root, "ExtInfo");
		
		return new WxRigReqMsg(msg, transId,extInfo,solution);
	}

}

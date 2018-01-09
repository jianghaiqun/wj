package com.sinosoft.framework.utility.weixin.wxtools.parser;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRightsMsg;

public abstract class WxRightsMsgBaseParser implements WxRightsMsgParser {

	@Override
	public WxRightsMsg parser(Document doc) throws JDOMException {
		
		Element root = doc.getRootElement();
		String openId = getElementText(root, "OpenId");
		String appId = getElementText(root, "AppId");
		String timeStamp = getElementText(root, "TimeStamp");
		String msgType = getElementText(root, "MsgType");
		String feedBackId = getElementText(root, "FeedBackId");
		String reason = getElementText(root, "Reason");
		String appSignature = getElementText(root, "AppSignature");
		String signMethod = getElementText(root, "SignMethod");
		
		return parser(root,new WxRightsMsg(openId,appId,timeStamp,msgType,feedBackId
				,reason,appSignature,signMethod));
	}
	
	protected abstract WxRightsMsg parser(Element root,WxRightsMsg msg) throws JDOMException;
	
	protected String getElementText(Element elem,String xpath) throws JDOMException {
		/*Text text = ((Text)XPath.selectSingleNode(elem, xpath+"/text()"));
		if(null == text) {
			return "";
		}
		return text.getText();*/
		
		return elem.getChildText(xpath);
	}

}

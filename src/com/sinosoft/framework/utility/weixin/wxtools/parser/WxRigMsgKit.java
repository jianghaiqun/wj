package com.sinosoft.framework.utility.weixin.wxtools.parser;

import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRightsMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.send.WxSendMsg;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public final class WxRigMsgKit {
	
	private static final Map<String, WxRightsMsgParser> rightsParserMap = new HashMap<String, WxRightsMsgParser>();
	
	static {
		// 维权申请解析
		rightsParserMap.put("request", new WxRigReqMsgParser());
		// 维权确认解析
		rightsParserMap.put("confirm", new WxRigConMsgParser());
		// 维权拒绝接卸
		rightsParserMap.put("reject", new WxRigRejMsgParser());
	}
	
	public static WxRightsMsg parse(InputStream in) throws JDOMException, IOException {
		Document dom = new SAXBuilder().build(in);
		Element msgType = dom.getRootElement().getChild("MsgType");
		if(null != msgType) {
			String txt = msgType.getText().toLowerCase();
			WxRightsMsgParser parser = rightsParserMap.get(txt);
			if(null != parser) {
				return parser.parser(dom);
			}
		}
		return null;
	}
	
	public static Document parse(WxSendMsg msg) throws JDOMException {
		return msg.toDocument();
	}
}

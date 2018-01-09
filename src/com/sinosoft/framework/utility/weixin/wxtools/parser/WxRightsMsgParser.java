package com.sinosoft.framework.utility.weixin.wxtools.parser;

import org.jdom.Document;
import org.jdom.JDOMException;

import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRightsMsg;

public interface WxRightsMsgParser {
	WxRightsMsg parser(Document doc) throws JDOMException;
}

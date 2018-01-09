package com.sinosoft.framework.utility.weixin.wxtools.parser;

import org.jdom.Document;
import org.jdom.JDOMException;

import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvMsg;

public interface WxRecvMsgParser {
	WxRecvMsg parser(Document doc) throws JDOMException;
}

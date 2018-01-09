package com.sinosoft.sms.messageinterface.parse;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.schema.LIMessageSentSchema;
import com.sinosoft.sms.messageinterface.process.MessageInfo;
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zhouxiang
 * @version  : 1.00
 * @date     : 2012-08-08
 * @direction: 短信接口解析服务类
 ******************************************************************************/

public class MessageParseBL implements MessageParse
{
    MessageInfo mMessageInfo = new MessageInfo();
    //private VData tParseResult = new VData();
    
    private Mapx<String, Object> tParseResult=new Mapx<String, Object>();

    public Mapx<String, Object> parseVData(Mapx<String, Object> mMap, String tServiceCode)
    {
        Document mDoc = null;
        LIMessageSentSchema tLIMessageSentSchema = new LIMessageSentSchema();

        Element tRootElement = new Element(MessageCode.ROOTDATA);
        mMessageInfo.init(mMap, tServiceCode);

        /**处理公共信息*/
        tRootElement.addContent(mMessageInfo.getPublicElement());
        /**处理短信信息*/
        tRootElement.addContent(mMessageInfo.getMessageElement());

        mDoc = new Document(tRootElement);
        tLIMessageSentSchema = mMessageInfo.getLIMessageSentSchema();

        //tParseResult.add(mDoc);
        tParseResult.put("Document", mDoc);
        tParseResult.put("LIMessageSentSchema", tLIMessageSentSchema);

        return tParseResult;
    }


}

package com.sinosoft.sms.messageinterface;


/**
 * @Author周翔
 * @Date 2012-7-31
 * @Mail zhouxiang@sinosoft.com.cn
 */

public class MessageServiceFactory {

	private static MessageServiceFactory cMessageServiceFactory = new MessageServiceFactory();
    private MessageServiceFactory()
    {

    }

    public static MessageServiceFactory newInstance()
    {
        return cMessageServiceFactory;
    }

    public MessageService getService()
    {
        return new MessageSinoService();
    }
	
	
}
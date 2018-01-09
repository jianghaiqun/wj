package com.sinosoft.sms.messageinterface;

import com.sinosoft.framework.utility.Mapx;


/**
 * @Author周翔
 * @Date 2012-7-31
 * @Mail zhouxiang@sinosoft.com.cn
 */

public interface MessageService {

    public boolean doService(Mapx<String, Object> mMap,String tServiceCode);
	
	
}
package com.sinosoft.sms.messageinterface.parse;

import com.sinosoft.framework.utility.Mapx;
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
 * @direction: 短信接口解析服务入口类
 ******************************************************************************/
public interface MessageParse
{
    public Mapx<String, Object> parseVData(Mapx<String, Object> mMap, String tServiceCode);
}

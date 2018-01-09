package com.sinosoft.sms.messageinterface.business;

import com.sinosoft.framework.utility.Mapx;
/*******************************************************************************
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zhouxiang
 * @version  : 1.00
 * @date     : 2012-08-08
 * @direction: 短信接口业务服务入口类
 ******************************************************************************/
public interface MessageBusiness
{
        /**提交业务处理*/
        public boolean submitData(Mapx<String, Object> mMap,String ServiceCode);
        /**获取处理后数据*/
	public Mapx<String, Object> getResult();

}

package com.sinosoft.shop;

import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.Application;
import com.sinosoft.schema.ZCSiteSchema;

/**
 * 站点属性
 * 
 */

public class ShopSite extends Page {
	public static Mapx init(Mapx params) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(Application.getCurrentSiteID());
		site.fill();
		return site.toMapx();
	}

   //发布站点首页
	public void publishIndex() {
		Publisher p = new Publisher();
		if (p.publishIndex(Application.getCurrentSiteID())) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("保存数据发生错误!");
		}
	}

}

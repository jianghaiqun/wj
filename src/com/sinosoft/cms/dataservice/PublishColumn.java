package com.sinosoft.cms.dataservice;

import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;

public class PublishColumn extends Page {

	/**
	 * 初始化查询日期
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initSearch(Mapx params) {
		String dateStr = DateUtil.toString(new Date(), "yyyy-MM-dd");
		Mapx map = new Mapx();
		map.put("today", dateStr);
		return map;
	}

}

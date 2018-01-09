package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

/**
 * 
 * @author wangwenying
 * 
 */
public class RefundFileManage extends Page {
	
	public static void dg1DataBind(DataGridAction dga) {
		String startDate = (String) dga.getParams().get("startDate");
		String endDate = (String) dga.getParams().get("endDate");
		QueryBuilder qb = new QueryBuilder("select * from RefundFileInfo where 1=1 ");
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and date_format(createDate, '%Y-%m-%d') >= ?", startDate.trim());
		}

		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and date_format(createDate, '%Y-%m-%d') <= ?", endDate.trim());
		}
	
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		qb.append(" order by ID desc");
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dga.setTotal(qb);
		dga.bindData(dt);
	}
}

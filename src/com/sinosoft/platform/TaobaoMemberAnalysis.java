package com.sinosoft.platform;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.DataTable;

/**
 * 淘宝新注册会员统计管理
 * @author guozc
 * @date 2017-12-26
 */
public class TaobaoMemberAnalysis extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String year = (String) dga.getParams().get("year");
		String month = (String) dga.getParams().get("month");
		StringBuffer listSql = new StringBuffer();
		listSql.append("select a.id,a.month,a.registerCount,a.repurchaseCount,a.convertRatio,a.repurchasePremium");
		listSql.append(" from taobaomemberanalysis a where status = '2'");
		if(StringUtil.isNotEmpty(year)){
			listSql.append(" and a.year = '"+year+"'");
		}
		if(StringUtil.isNotEmpty(month)){
			listSql.append(" and a.month = '"+month+"'");
		}
		listSql.append(" order by a.month");
		QueryBuilder qb = new QueryBuilder(listSql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public static Mapx<String, Object> initDialog(Mapx<String, Object> params) {

		return params;
	}
}
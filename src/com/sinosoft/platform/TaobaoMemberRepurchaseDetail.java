package com.sinosoft.platform;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.DataTable;

/**
 * 淘宝会员复购详情管理
 * @author guozc
 * @date 2017-12-27
 */
public class TaobaoMemberRepurchaseDetail extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String analysisId = (String) dga.getParams().get("analysisId");
		StringBuffer listSql = new StringBuffer();
		listSql.append("select a.id,a.analysisId,a.productId,a.productName,a.prem,date_format(a.orderDate,'%Y-%c-%d %h:%i:%s') as orderDate");
		listSql.append(" from taobaomemberrepurchasedetail a where 1=1");
		if(StringUtil.isNotEmpty(analysisId)){
			listSql.append(" and a.analysisId = '"+analysisId+"'");
		}
		listSql.append(" order by a.orderDate");
		QueryBuilder qb = new QueryBuilder(listSql.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public static Mapx<String, Object> initDialog(Mapx<String, Object> params) {

		return params;
	}
}
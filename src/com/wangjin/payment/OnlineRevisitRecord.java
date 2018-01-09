package com.wangjin.payment;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.DataTable;

/**
 * 在线回访记录管理
 * @author guozc
 * @date 2017-08-17
 */
public class OnlineRevisitRecord extends Page {
	
	public static Mapx<String, String> init(Mapx<String, String> params) {
		params.put("status", HtmlUtil.codeToOptions("OnlineRevisit.Status", true));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String beginCreatedate = dga.getParams().getString("beginCreatedate");
		String endCreatedate = dga.getParams().getString("endCreatedate");
		String beginCreatetime = dga.getParams().getString("beginCreatetime");
		String endCreatetime = dga.getParams().getString("endCreatetime");
		String status = dga.getParams().getString("status");
		StringBuffer listSql = new StringBuffer();
		listSql.append("select a.id,a.applicantName,a.applicantMobile,a.productName,a.orderSn,"
				+ "date_format(a.createDate,'%Y-%c-%d %H:%i:%s') as createDate,b.CodeName as status,a.prop1,"
				+ "a.prop2,a.prop3,a.prop4");
		listSql.append(" from onlinerevisitrecord a,zdcode b");
		listSql.append(" where b.CodeType = 'OnlineRevisit.Status' and a.status = b.CodeValue");
		QueryBuilder qb = new QueryBuilder(listSql.toString());
		if(StringUtil.isNotEmpty(beginCreatedate)){
			if(StringUtil.isEmpty(beginCreatetime)){
				beginCreatetime = "00:00:00";
			}else {
				if (beginCreatetime.length() == 7) {
					beginCreatetime = "0" + beginCreatetime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(" + "'"
					+ beginCreatedate.trim() + " " + beginCreatetime.trim() + "')");
		}
		if(StringUtil.isNotEmpty(endCreatedate)){
			if(StringUtil.isEmpty(endCreatetime)){
				endCreatetime = "23:59:59";
			}else{
				if (endCreatetime.length() == 7) {
					endCreatetime = "0" + endCreatetime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP(" + "'"
					+ endCreatedate.trim() + " " + endCreatetime.trim() + "')");
		}
		if(StringUtil.isNotEmpty(status)){
			qb.append(" and a.status = ? ", status);
		}
		qb.append(" order by a.createDate desc ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public static Mapx<String, Object> initDialog(Mapx<String, Object> params) {
		return params;
	}
}
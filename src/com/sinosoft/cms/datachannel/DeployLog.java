package com.sinosoft.cms.datachannel;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.schema.ZCDeployLogSchema;
import com.sinosoft.schema.ZCDeployLogSet;

public class DeployLog extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String sql1 = "select a.id,a.jobid,a.message,a.begintime,a.endtime,b.method,b.host,b.source,b.target,"
				+ "(select codename from zdcode where codetype='DeployMethod' and parentCode='DeployMethod' and codevalue=b.method) as methodDesc "
				+ "from ZCDeployLog a,ZCDeployJob b where  a.JobID=b.ID and b.SiteID=? order by a.begintime desc";
		QueryBuilder qb = new QueryBuilder(sql1, Application.getCurrentSiteID());
		dga.bindData(qb);
	}

	public static Mapx initDialog(Mapx params) {
		String sql = "select a.id,a.jobid,a.message,a.begintime,a.endtime,b.method,b.host,b.source,b.target,"
				+ "(select codename from zdcode where codetype='DeployMethod' and parentCode='DeployMethod' and codevalue=b.method) as methodDesc"
				+ " from ZCDeployLog a,ZCDeployJob b where  a.JobID=b.ID and a.ID=?";
		DataTable dt = new QueryBuilder(sql, params.getString("ID")).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			params.putAll(dt.get(0).toCaseIgnoreMapx());
		}
		return params;
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String tsql = "where id in (" + ids + ")";
		ZCDeployLogSchema ZCDeployLog = new ZCDeployLogSchema();
		ZCDeployLogSet set = ZCDeployLog.query(new QueryBuilder(tsql));

		Transaction trans = new Transaction();
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void delAll() {
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from ZCDeployLog where siteid=?", Application.getCurrentSiteID()));
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
}

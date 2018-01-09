package com.sinosoft.cms.datachannel;

import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCDeployConfigSchema;
import com.sinosoft.schema.ZCDeployConfigSet;

/**
 * 部署配置
 * 
 */
public class DeployConfig extends Page {
	public static Mapx init(Mapx params) {
		return null;
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from ZCDeployConfig where siteid=?", Application
				.getCurrentSiteID());
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("Method", CacheManager.getMapx("Code", "DeployMethod"));
		dga.bindData(dt);
	}

	public void add() {
		ZCDeployConfigSchema deployConfig = new ZCDeployConfigSchema();
		deployConfig.setID(NoUtil.getMaxID("DeployConfigID"));
		deployConfig.setSiteID(Application.getCurrentSiteID());

		String sourceDir = $V("SourceDir");
		sourceDir = sourceDir.replace('\\', '/');
		deployConfig.setSourceDir(sourceDir);

		String target = $V("TargetDir");
		target = target.replace('\\', '/');
		deployConfig.setTargetDir(target);
		deployConfig.setMethod($V("Method"));
		deployConfig.setHost($V("Host"));
		String port = $V("Port");
		if (port != null && !"".equals(port)) {
			deployConfig.setPort(Integer.parseInt(port));
		}
		deployConfig.setUserName($V("UserName"));
		deployConfig.setPassword($V("Password"));
		String beginDate = $V("BeginDate");
		String beginTime = $V("BeginTime");

		if (!"".equals(beginDate) && !"null".equals(beginDate) && beginDate != null) {
			deployConfig.setBeginTime(DateUtil.parseDateTime(beginDate + " " + beginTime));
		}

		deployConfig.setUseFlag(Integer.parseInt($V("UseFlag")));
		deployConfig.setAddTime(new Date());
		deployConfig.setAddUser(User.getUserName());
		if (deployConfig.insert()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String tsql = "where id in (" + ids + ")";
		ZCDeployConfigSchema ZCDeployConfig = new ZCDeployConfigSchema();
		ZCDeployConfigSet set = ZCDeployConfig.query(new QueryBuilder(tsql));

		Transaction trans = new Transaction();
		trans.add(set, Transaction.DELETE_AND_BACKUP);

		trans.add(new QueryBuilder("delete from ZCDeployJob where configID in (" + ids + ")"));
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void edit() {
		ZCDeployConfigSchema deployConfig = new ZCDeployConfigSchema();
		deployConfig.setID(Long.parseLong($V("ID")));
		if (!deployConfig.fill()) {
			Response.setStatus(0);
			Response.setMessage("没有对应的定时任务!");
		}
		deployConfig.setSourceDir($V("SourceDir"));
		deployConfig.setTargetDir($V("TargetDir"));
		deployConfig.setMethod($V("Method"));
		deployConfig.setHost($V("Host"));
		String port = $V("Port");
		if (port != null && !"".equals(port)) {
			deployConfig.setPort(Integer.parseInt(port));
		}
		deployConfig.setUserName($V("UserName"));
		deployConfig.setPassword($V("Password"));
		String beginDate = $V("BeginDate");
		String beginTime = $V("BeginTime");

		if (!"".equals(beginDate) && !"null".equals(beginDate) && beginDate != null) {
			deployConfig.setBeginTime(DateUtil.parseDateTime(beginDate + " " + beginTime));
		}
		deployConfig.setUseFlag(Integer.parseInt($V("UseFlag")));
		deployConfig.setModifyTime(new Date());
		deployConfig.setModifyUser(User.getUserName());

		Transaction trans = new Transaction();
		trans.add(deployConfig, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public void deploy() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] idArr = ids.split(",");
		Deploy deploy = new Deploy();
		for (int i = 0; i < idArr.length; i++) {
			long configID = Long.parseLong(idArr[i]);
			deploy.addOneJob(configID, true);
		}
	}
}

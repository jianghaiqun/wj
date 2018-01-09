package com.sinosoft.cms.dataservice;

import java.util.Date;

import com.sinosoft.cms.document.Article;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZDUserSchema;

public class PublishStaff extends Page {

	/**
	 * 初始化查询日期
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initStaff(Mapx params) {
		String dateStr = DateUtil.toString(new Date(), "yyyy-MM-dd");
		Mapx map = new Mapx();
		map.put("today", dateStr);
		return map;
	}

	public static Mapx initInputor(Mapx params) {
		String dateStr = DateUtil.toString(new Date(), "yyyy-MM-dd");
		Mapx map = new Mapx();
		map.put("today", dateStr);
		String username = params.getString("Username");
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		if (user.fill()) {
			map.putAll(user.toMapx());
		}
		map.put("Username", username);
		return map;
	}

	public static Mapx initInputorColumn(Mapx params) {
		String dateStr = DateUtil.toString(new Date(), "yyyy-MM-dd");
		Mapx map = new Mapx();
		map.put("today", dateStr);
		String username = params.getString("ColumnInputor");
		String catalogID = params.getString("CatalogID");
		String catalogInnerCode = params.getString("CatalogInnerCode");
		map.put("CatalogID", catalogID);
		map.put("CatalogInnerCode", catalogInnerCode);
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		if (user.fill()) {
			map.putAll(user.toMapx());
		}
		map.put("ColumnInputor", username);

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(catalogID);
		if (catalog.fill()) {
			map.put("CatalogName", catalog.getName());
		}

		return map;
	}

	/**
	 * 获得发布统计的数据
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");

		QueryBuilder qb = new QueryBuilder(
				"select b.UserName,b.RealName as Inputor,b.BranchInnerCode,"
						+ " (select name from zdbranch d where d.branchinnercode=b.branchinnercode) as branchname,"
						//+ " sum(case when a.id is null then 0 else 1 end) as ArticleCount,"
						+ " (sum(case when a.Status ="+ Article.STATUS_DRAFT+" then 1 else 0 end)+"
						+ " sum(case when a.Status ="+ Article.STATUS_WORKFLOW+" then 1 else 0 end)+"
						+ " sum(case when a.Status ="+ Article.STATUS_TOPUBLISH+" then 1 else 0 end)+"
						+ " sum(case when a.Status ="+ Article.STATUS_PUBLISHED+" then 1 else 0 end)+"
						+ " sum(case when a.Status ="+ Article.STATUS_OFFLINE+" then 1 else 0 end)+"
						+ " sum(case when a.Status ="+ Article.STATUS_ARCHIVE+" then 1 else 0 end)) ArticleCount,"
						
						+ " sum(case when a.Status ="+ Article.STATUS_DRAFT+" then 1 else 0 end) as DraftCount,"
						+ " sum(case when a.Status ="+ Article.STATUS_WORKFLOW+" then 1 else 0 end) as WorkflowCount,"
						+ " sum(case when a.Status ="+ Article.STATUS_TOPUBLISH+" then 1 else 0 end) as ToPublishCount,"
						+ " sum(case when a.Status ="+ Article.STATUS_PUBLISHED+" then 1 else 0 end) as PublishCount,"
						+ " sum(case when a.Status ="+ Article.STATUS_OFFLINE+" then 1 else 0 end) as OfflineCount,"
						+ " sum(case when a.Status ="+ Article.STATUS_ARCHIVE+" then 1 else 0 end) as ArchiveCount"
						+ " from zduser b left outer join zcarticle a on a.AddUser=b.UserName  "
						+ " and a.SiteID=? ", Application.getCurrentSiteID());
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and a.AddTime>?", DateUtil.parse(startDate, "yyyy-MM-dd"));
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and a.AddTime<?", DateUtil.addDay(DateUtil.parse(endDate, "yyyy-MM-dd"), 1));
		}
		qb.append(" group by b.UserName,b.realname,b.branchinnercode order by b.BranchInnerCode");

		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
	}


	public static void dg11DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String username = dga.getParam("Username");
		
		QueryBuilder qb = new QueryBuilder(
				"select a.ID,a.Name as CatalogName,a.InnerCode,a.TreeLevel, "
				+ " (select orderflag from zccatalog where zccatalog.id=a.id) as orderflag,"
				+ " sum(case when b.id is null then 0 else 1 end) as ArticleCount,"
				+ " sum(case when b.Status ="+ Article.STATUS_DRAFT+" then 1 else 0 end) as DraftCount,"
				+ " sum(case when b.Status ="+ Article.STATUS_WORKFLOW+" then 1 else 0 end) as WorkflowCount,"
				+ " sum(case when b.Status ="+ Article.STATUS_TOPUBLISH+" then 1 else 0 end) as ToPublishCount,"
				+ " sum(case when b.Status ="+ Article.STATUS_PUBLISHED+" then 1 else 0 end) as PublishCount,"
				+ " sum(case when b.Status ="+ Article.STATUS_OFFLINE+" then 1 else 0 end) as OfflineCount,"
				+ " sum(case when b.Status ="+ Article.STATUS_ARCHIVE+" then 1 else 0 end) as ArchiveCount"
						+ " from zccatalog a left join zcarticle b on a.id=b.catalogid ");
		if (StringUtil.isNotEmpty(username)) {
			qb.append(" and b.adduser=?", username);
		}
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and b.AddTime>?", DateUtil.parse(startDate, "yyyy-MM-dd"));
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and b.AddTime<?", DateUtil.addDay(DateUtil.parse(endDate, "yyyy-MM-dd"), 1));
		}
		
		qb.append(" where a.SiteID=? and a.Type=1 group by a.ID,a.Name,a.InnerCode,a.TreeLevel order by InnerCode", Application.getCurrentSiteID());
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
	}
	
	/**
	 * 某个录入人对某个具体栏目的发布统计
	 * 
	 * @param dga
	 */
	public static void dg12DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String username = dga.getParam("ColumnInputor");
		String catalogInnerCode = dga.getParam("CatalogInnerCode");

		QueryBuilder qb = new QueryBuilder("select zcarticle.*,(select name from zccatalog where zccatalog.id=zcarticle.catalogid ) as CatalogName from zcarticle where SiteID=? ", Application
				.getCurrentSiteID());
		if (StringUtil.isNotEmpty(username)) {
			qb.append(" and AddUser=?", username);
		}
		if (StringUtil.isNotEmpty(catalogInnerCode)) {
			qb.append(" and CatalogInnerCode like ?", catalogInnerCode + "%");
		}

		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and AddTime>?", DateUtil.parse(startDate, "yyyy-MM-dd"));
		}
		
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and AddTime<?", DateUtil.addDay(DateUtil.parse(endDate, "yyyy-MM-dd"), 1));
		}

		qb.append(" order by CatalogInnerCode,OrderFlag desc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("Status", Article.STATUS_MAP);
		dt.insertColumn("LastModifyTime");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (StringUtil.isEmpty(dt.getString(i, "ModifyTime"))) {
				dt.set(i, "LastModifyTime", dt.get(i, "AddTime"));
			} else {
				dt.set(i, "LastModifyTime", dt.get(i, "ModifyTime"));
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	public static void dg13DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		QueryBuilder qb = new QueryBuilder(
				"select s.id,s.name,"
				+ "sum(case when a.Status ="+ Article.STATUS_PUBLISHED+" then 1 else 0 end) as PublishCount"
				+ " from zcsite s left join zcarticle a on s.id=a.siteid ");
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and a.AddTime>?", DateUtil.parse(startDate, "yyyy-MM-dd"));
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and a.AddTime<?", DateUtil.addDay(DateUtil.parse(endDate, "yyyy-MM-dd"), 1));
		}
		
		qb.append("group by s.id order by PublishCount desc");
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
	}
	//zh
	public static void dg14DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		QueryBuilder qb = new QueryBuilder("select s.id ID,s.name SName,b.aprop4 Pro,b.cou Coun "
				+ " from zcsite s left join"
				+ " (select c.siteid sid,a.prop4 aprop4,a.cou cou"
				+ " from zccatalog c right join "
				+ " (select count(*) cou,right(prop4,4) prop4 "
				+ " from zcarticle" + " where prop4 like 'tss%'"
				+" and catalogid=9285");
		qb.append(" and status=? ", Article.STATUS_PUBLISHED);
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and AddTime>?", DateUtil.parse(startDate,
					"yyyy-MM-dd"));
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and AddTime<?", DateUtil.addDay(DateUtil.parse(
					endDate, "yyyy-MM-dd"), 1));
		}
		qb.append(" group by prop4" + " having count(*)>=1) a"
				+ " on c.id= a.prop4" + " group by c.siteid) b"
				+ " on s.id=b.sid" 
				+ " where s.id between 229 and 249"
				+ " group by s.id"
				+ " order by b.cou desc,s.id;");
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
	}
	
	public static void dg15DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		QueryBuilder qb = new QueryBuilder("select s.id ID,s.name SName,b.aprop4 Pro,b.cou Coun "
				+ " from zcsite s left join"
				+ " (select c.siteid sid,a.prop4 aprop4,a.cou cou"
				+ " from zccatalog c right join "
				+ " (select count(*) cou,right(prop4,4) prop4 "
				+ " from zcarticle" + " where prop4 like 'tss%'" 
				+ " and catalogid=9286");
		qb.append(" and status=? ", Article.STATUS_PUBLISHED);
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and AddTime>?", DateUtil.parse(startDate,
					"yyyy-MM-dd"));
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and AddTime<?", DateUtil.addDay(DateUtil.parse(
					endDate, "yyyy-MM-dd"), 1));
		}
		qb.append(" group by prop4" + " having count(*)>=1) a"
				+ " on c.id= a.prop4" + " group by c.siteid) b"
				+ " on s.id=b.sid" 
				+ " where s.id>249"
				+ " group by s.id"
				+ " order by b.cou desc,s.id;");
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
	}
}

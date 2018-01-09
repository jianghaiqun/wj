package com.sinosoft.cms.site;

import java.util.Date;

import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.schema.ZCMagazineIssueSchema;
import com.sinosoft.schema.ZCMagazineIssueSet;
import com.sinosoft.schema.ZCMagazineSchema;

/**
 * 期刊期数
 * 
 */
public class MagazineIssue extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		long magazineID = Long.parseLong(dga.getParam("MagazineID"));
		QueryBuilder qb = new QueryBuilder(
				"select ID,MagazineID,year,PeriodNum,CoverImage,Status,Memo,publishDate as pubDate,addtime "
						+ "from ZCMagazineIssue where magazineID=? order by ID desc", magazineID);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dt.getDataColumn("pubDate").setDateFormat("yy-MM-dd");
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		return params;
	}

	public static Mapx initDialog(Mapx params) {
		String magazineIssueID = params.getString("ID");
		String magazineID = params.getString("MagazineID");
		String coverImage = "upload/Image/nopicture.jpg";
		if (StringUtil.isNotEmpty(magazineIssueID)) {
			ZCMagazineIssueSchema magazineIssue = new ZCMagazineIssueSchema();
			magazineIssue.setID(magazineIssueID);
			magazineIssue.fill();
			params = magazineIssue.toMapx();
			coverImage = magazineIssue.getCoverImage();
			params.put("PicSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + coverImage);
		} else {
			params.put("SiteID", Application.getCurrentSiteID() + "");
			params.put("CoverImage", coverImage);
			params.put("PicSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + coverImage);
			if (StringUtil.isNotEmpty(magazineID)) {
				DataTable catalogDt = new QueryBuilder(
						"select * from zccatalog where parentid=(select max(id) from zccatalog where parentid=?)",
						Long.parseLong(magazineID)).executeDataTable();
				if (catalogDt != null) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < catalogDt.getRowCount(); i++) {
						sb.append("<input type=\"checkbox\" name=\"catalog\" value=\"" + catalogDt.getString(i, "id")
								+ "\" checked>" + catalogDt.getString(i, "name") + "");
						if (i == 3) {
							sb.append("<br>");
						}
					}
					params.put("LastCatalog", sb.toString());
				}
			}
		}
		return params;
	}

	public void add() {
		long magazineID = Long.parseLong($V("MagazineID"));
		Transaction trans = new Transaction();

		Catalog catalog = new Catalog();
		Request.put("Name", $V("Year") + "年第" + $V("PeriodNum") + "期");
		Request.put("ParentID", magazineID + "");
		Request.put("Alias", $V("Year") + $V("PeriodNum"));
		Request.put("Type", Catalog.MAGAZINE + "");
		Request.put("ImagePath", $V("CoverImage"));

		ZCCatalogSchema catalogSchema = catalog.add(Request, trans);

		ZCMagazineIssueSchema issue = new ZCMagazineIssueSchema();
		issue.setID(catalogSchema.getID());
		issue.setValue(Request);
		issue.setAddTime(new Date());
		issue.setAddUser(User.getUserName());
		issue.setStatus(1);
		trans.add(issue, Transaction.INSERT);

		// 默认沿用上一期的模板设置
		QueryBuilder qb = new QueryBuilder("select DetailTemplate,ListTemplate from ZCCatalog "
				+ "where Type=? and ParentID=? order by ID desc",Catalog.MAGAZINE, issue.getMagazineID());
		DataTable dt = qb.executePagedDataTable(1, 0);
		if(dt.getRowCount()>0){
			if(StringUtil.isNotEmpty(dt.getString(0, "ListTemplate"))){
				catalogSchema.setListTemplate(dt.getString(0, "ListTemplate"));
			}else{
				catalogSchema.setListTemplate("/template/list.html");
			}
			if(StringUtil.isNotEmpty(dt.getString(0, "DetailTemplate"))){
				catalogSchema.setDetailTemplate(dt.getString(0, "DetailTemplate"));
			}else{
				catalogSchema.setDetailTemplate("/template/detail.html");	
			}
		}else{
			catalogSchema.setListTemplate("/template/list.html");
			catalogSchema.setDetailTemplate("/template/detail.html");	
		}
		ZCMagazineSchema magazine = new ZCMagazineSchema();
		magazine.setID(magazineID);
		if (magazine.fill()) {
			long  totalissue = magazine.getTotal();
			String currentyear = "";
			String periodnum = "";
			String coverimage = "";
			if (StringUtil.isEmpty(magazine.getCurrentYear()) || (Long.parseLong($V("Year")) > Long.parseLong(magazine.getCurrentYear()))) {
				 currentyear =  $V("Year");
				 periodnum = $V("PeriodNum");
				 coverimage = $V("CoverImage");
			}else {
				 currentyear =  magazine.getCurrentYear();
				 coverimage = magazine.getCoverImage();
				 periodnum = (Long.parseLong($V("PeriodNum")) > Long.parseLong(magazine.getCurrentPeriodNum())) ? $V("PeriodNum") : magazine.getCurrentPeriodNum();
			}
			magazine.setTotal(totalissue + 1);
			magazine.setCurrentYear(currentyear);
			magazine.setCurrentPeriodNum(periodnum);
			magazine.setCoverImage(coverimage);
			magazine.setModifyTime(new Date());
			magazine.setModifyUser(User.getUserName());
			trans.add(magazine, Transaction.UPDATE);
		}

		trans.add(new QueryBuilder("update zccatalog set ImagePath=? where id=?", $V("CoverImage"), magazineID));

		if (trans.commit()) {
			CatalogUtil.update(catalogSchema.getID());
			if (StringUtil.isNotEmpty($V("CatalogIDs"))) {
				if (!StringUtil.checkID($V("CatalogIDs"))) {
					Response.setStatus(0);
					Response.setMessage("发生错误!");
					return;
				}
				ZCCatalogSchema catalogLastIssue = new ZCCatalogSchema();
				if (!StringUtil.checkID($V("CatalogIDs"))) {
					return;// SQL注入
				}
				ZCCatalogSet set = catalogLastIssue.query(new QueryBuilder("where id in(" + $V("CatalogIDs") + ")"));
				for (int i = 0; i < set.size(); i++) {
					catalog.add(catalogSchema, set.get(i), trans);
				}
				trans.commit();
			}
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public void edit() {
		long magazineID = Long.parseLong($V("MagazineID"));
		Transaction trans = new Transaction();

		ZCMagazineIssueSchema issue = new ZCMagazineIssueSchema();
		issue.setID(Long.parseLong($V("ID")));
		if (!issue.fill()) {
			Response.setStatus(0);
			Response.setMessage("没有找到期号!");
			return;
		}
		issue.setValue(Request);
		issue.setModifyTime(new Date());
		issue.setModifyUser(User.getUserName());
		issue.setStatus(1);
		trans.add(issue, Transaction.UPDATE);

		ZCMagazineSchema magazine = new ZCMagazineSchema();
		magazine.setID(magazineID);
		if (magazine.fill()) {
			magazine.setTotal(magazine.getTotal() + 1);
			magazine.setCurrentYear($V("Year"));
			magazine.setCurrentPeriodNum($V("PeriodNum"));
			magazine.setCoverImage($V("CoverImage"));
			magazine.setModifyTime(new Date());
			magazine.setModifyUser(User.getUserName());
			trans.add(magazine, Transaction.UPDATE);
		}
		trans.add(new QueryBuilder("update zccatalog set ImagePath=? where id=?", $V("CoverImage"), magazineID));
		trans.add(new QueryBuilder("update zccatalog set ImagePath=? where id=?", $V("CoverImage"), issue.getID()));

		if (trans.commit()) {
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
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCMagazineIssueSchema MagazineIssue = new ZCMagazineIssueSchema();
		ZCMagazineIssueSet set = MagazineIssue.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		
		String [] idArray = ids.split("\\,");
		for (int i = 0; i < idArray.length; i++) {
			Catalog.deleteCatalog(trans, Long.parseLong(idArray[i]));
		}
		
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		String id = $V("ID");
		DataTable dt = new QueryBuilder("select path,filename from zcimage where id=?", Long.parseLong(ID)).executeDataTable();
		if (dt.getRowCount() > 0) {
			this.Response.put("picSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + "s_"
					+ dt.get(0, "filename").toString());
			this.Response.put("CoverImage", dt.get(0, "path").toString() + "1_" + dt.get(0, "filename").toString());
		}
		Transaction trans = new Transaction();
		ZCMagazineIssueSchema magazineIssue = new ZCMagazineIssueSchema();
		if (StringUtil.isNotEmpty(id)) {
			magazineIssue.setID(id);
			magazineIssue.fill();
			magazineIssue.setValue(this.Request);
			magazineIssue.setCoverImage((String) this.Response.get("CoverImage"));
			trans.add(magazineIssue, Transaction.UPDATE);
			trans.commit();
		} else {
			return;
		}
	}

}

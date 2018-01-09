package com.sinosoft.cms.site;

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
import com.sinosoft.schema.ZCPaperIssueSchema;
import com.sinosoft.schema.ZCPaperIssueSet;
import com.sinosoft.schema.ZCPaperSchema;

import java.util.Date;

/**
 * 报纸期数
 * 
 */
public class NewspaperIssue extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		long PaperID = Long.parseLong(dga.getParam("NewspaperID"));
		QueryBuilder qb = new QueryBuilder(
				"select ID,PaperID,year,PeriodNum,CoverImage,Status,Memo,publishDate as pubDate,addtime "
						+ "from ZCPaperIssue where PaperID=? order by ID desc", PaperID);
		dga.bindData(qb);
	}

	public static Mapx init(Mapx params) {
		return params;
	}

	public void add() {
		long paperID = Long.parseLong($V("NewspaperID"));
		Transaction trans = new Transaction();
		Catalog catalog = new Catalog();
		Request.put("Name", $V("PublishDate") + "(" + $V("Year") + "年" + $V("PeriodNum") + "期)");
		Request.put("ParentID", paperID + "");
		Request.put("Alias", $V("Year") + $V("PeriodNum"));
		Request.put("Type", Catalog.NEWSPAPER + "");

		long catalogID = (catalog.add(Request, trans)).getID();

		ZCPaperIssueSchema issue = new ZCPaperIssueSchema();
		issue.setPaperID(paperID);
		issue.setID(catalogID);
		issue.setValue(Request);
		issue.setAddTime(new Date());
		issue.setAddUser(User.getUserName());
		issue.setStatus(1);
		trans.add(issue, Transaction.INSERT);

		ZCPaperSchema paper = new ZCPaperSchema();
		paper.setID(paperID);
		if (paper.fill()) {
			paper.setTotal(paper.getTotal() + 1);
			paper.setCurrentYear($V("Year"));
			paper.setCurrentPeriodNum($V("PeriodNum"));
			paper.setCoverImage($V("CoverImage"));
			paper.setModifyTime(new Date());
			paper.setModifyUser(User.getUserName());
			trans.add(paper, Transaction.UPDATE);
		}
		trans.add(new QueryBuilder("update zccatalog set imagePath=? where id=?", $V("CoverImage"), paperID));

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public void edit() {
		long paperID = Long.parseLong($V("PaperID"));
		Transaction trans = new Transaction();

		ZCPaperIssueSchema issue = new ZCPaperIssueSchema();
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

		ZCPaperSchema Paper = new ZCPaperSchema();
		Paper.setID(paperID);
		if (Paper.fill()) {
			Paper.setTotal(Paper.getTotal() + 1);
			Paper.setCurrentYear($V("Year"));
			Paper.setCurrentPeriodNum($V("PeriodNum"));
			Paper.setCoverImage($V("CoverImage"));
			Paper.setModifyTime(new Date());
			Paper.setModifyUser(User.getUserName());
			trans.add(Paper, Transaction.UPDATE);
		}
		trans.add(new QueryBuilder("update zccatalog set imageID=? where id=?", $V("CoverImage"), paperID));

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
		Transaction trans = new Transaction();
		ZCPaperIssueSchema paperIssue = new ZCPaperIssueSchema();
		ZCPaperIssueSet set = paperIssue.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		ZCCatalogSet catalogSet = catalog.query(new QueryBuilder("where id in (" + ids + ") or parentid in (" + ids
				+ ")"));
		trans.add(catalogSet, Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		DataTable dt = new QueryBuilder("select path,filename from zcimage where id=?", Long.parseLong(ID)).executeDataTable();
		;
		if (dt.getRowCount() > 0) {
			this.Response.put("picSrc", Config.getContextPath() + Config.getValue("UploadDir")
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + "s_"
					+ dt.get(0, "filename").toString());
		} else {
			this.Response.put("picSrc", "../Images/nopicture.gif");
		}
	}
}

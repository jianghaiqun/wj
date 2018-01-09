package com.sinosoft.cms.site;

import java.util.Date;

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
import com.sinosoft.schema.ZCPaperIssueSchema;
import com.sinosoft.schema.ZCPaperIssueSet;
import com.sinosoft.schema.ZCPaperPageSchema;
import com.sinosoft.schema.ZCPaperSchema;

/**
 * 报纸版面
 * 
 */
public class NewspaperPage extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		long issueID = Long.parseLong(dga.getParam("CatalogID"));
		QueryBuilder qb = new QueryBuilder(
				"select a.ID,a.IssueID,a.pageNo,a.Title,a.PaperImage,"
						+ "(select concat(path,srcfilename) from zcimage where id=a.PaperImage) as Image,a.PDFFile,"
						+ "(select concat(path,srcfilename) from zcattachment where id=a.pdffile) as File,a.Memo,b.listtemplate,b.detailtemplate "
						+ "from ZCPaperPage a,zccatalog b where a.issueid=? and a.id = b.id order by a.pageno", issueID);
		dga.bindData(qb);
	}

	public static Mapx init(Mapx params) {
		return params;
	}

	public void add() {
		long issueID = Long.parseLong($V("IssueID"));
		Transaction trans = new Transaction();

		Catalog catalog = new Catalog();
		Request.put("Name", "第" + $V("PageNo") + "版 " + $V("Title"));
		Request.put("ParentID", issueID + "");
		Request.put("Alias", $V("PageNo"));
		Request.put("Type", Catalog.NEWSPAPER + "");

		long catalogID = (catalog.add(Request, trans)).getID();

		ZCPaperPageSchema page = new ZCPaperPageSchema();
		page.setID(catalogID);
		page.setValue(Request);
		page.setAddTime(new Date());
		page.setAddUser(User.getUserName());
		trans.add(page, Transaction.INSERT);

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public void edit() {
		long PaperID = Long.parseLong($V("PaperID"));
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
		Paper.setID(PaperID);
		if (Paper.fill()) {
			Paper.setTotal(Paper.getTotal() + 1);
			Paper.setCurrentYear($V("Year"));
			Paper.setCurrentPeriodNum($V("PeriodNum"));
			Paper.setCoverImage($V("CoverImage"));
			Paper.setModifyTime(new Date());
			Paper.setModifyUser(User.getUserName());
			trans.add(Paper, Transaction.UPDATE);
		}
		trans.add(new QueryBuilder("update zccatalog set imageID=? where id=?", $V("CoverImage"), PaperID));

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

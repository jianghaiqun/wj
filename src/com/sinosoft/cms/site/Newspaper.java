package com.sinosoft.cms.site;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.controls.TreeItem;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.Application;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCPaperIssueSchema;
import com.sinosoft.schema.ZCPaperSchema;

import java.util.Date;
import java.util.List;

public class Newspaper extends Page {
	public static void treeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		String siteID = (obj != null) ? obj.toString() : Application.getCurrentSiteID()+"";
		int catalogType = Catalog.NEWSPAPER;
		DataTable dt = null;

		QueryBuilder qb = new QueryBuilder("select ID,ParentID,TreeLevel,Name from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel<3",catalogType,siteID);
	    dt = qb.executeDataTable();
	    
		String siteName = "报纸库";
		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType)) {// 单选
			ta.setRootText("<input type='radio' name=CatalogID id='_site' value='" + siteID + "'><label for='_site'>"
					+ siteName + "</label>");
		} else if ("2".equals(inputType)) {// 多选、全选
			ta.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='" + siteID
					+ "' onclick='selectAll()'><label for='_site'>" + siteName + "</label>");
		} else {
			ta.setRootText(siteName);
		}

		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 0; i < items.size(); i++) {
			TreeItem item = (TreeItem)items.get(i);
			if (item.getLevel() == 1)
				item.setIcon("Icons/icon008a2.gif");
		}
	}
	
	public static void docTreeDataBind(TreeAction ta) {
		String siteID = Application.getCurrentSiteID()+"";
		int catalogType = Catalog.NEWSPAPER;
		DataTable dt = null;

	    dt = new QueryBuilder("select ID,ParentID,TreeLevel,Name from ZCCatalog Where Type = ? and SiteID = ?",catalogType,siteID).executeDataTable();
		String siteName = "报纸库";

		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType)) {// 单选
			ta.setRootText("<input type='radio' name=CatalogID id='_site' value='" + siteID + "'><label for='_site'>"
					+ siteName + "</label>");
		} else if ("2".equals(inputType)) {// 多选、全选
			ta.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='" + siteID
					+ "' onclick='selectAll()'><label for='_site'>" + siteName + "</label>");
		} else {
			ta.setRootText(siteName);
		}

		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 0; i < items.size(); i++) {
			TreeItem item = (TreeItem)items.get(i);
			if (item.getLevel() == 1){
				item.setIcon("Icons/icon008a1.gif");
			}else if(item.getLevel() == 2){
				item.setIcon("Icons/icon018a11.gif");
			}else if(item.getLevel() == 3){
				item.setIcon("Icons/icon5.gif");
			}
				
		}
	}

	public static Mapx initDialog(Mapx params) {
		String sql = "select CodeName,CodeValue from ZDCode where ParentCode !='System' and CodeType ='Period' Order by CodeOrder";
		DataTable dt1 = new QueryBuilder(sql).executeDataTable();
		
		Object o1 = params.get("NewspaperID");
		if (o1 != null) {
			long ID = Long.parseLong(o1.toString());
			ZCPaperSchema paper = new ZCPaperSchema();
			paper.setID(ID);
			if (paper.fill()) {
				Mapx map = paper.toMapx();
				String imagePath = PubFun.getImagePath((String) map.get("CoverImage"));
				if (imagePath == null) {
					imagePath = "../Images/nopicture.gif";
				} else {
					imagePath = Config.getContextPath() + Config.getValue("UploadDir")
							+ SiteUtil.getAlias(paper.getSiteID()) + "/" + imagePath;
				}

				map.put("PicSrc", imagePath);
				map.put("optionPeriod", HtmlUtil.dataTableToOptions(dt1));
				return map;
			}
			return null;
		} else {
			params.put("SiteID", Application.getCurrentSiteID()+"");
			params.put("PicSrc", "../Images/nopicture.gif");
			params.put("CoverTemplate", "/template/Paper.html");
			params.put("optionPeriod", HtmlUtil.dataTableToOptions(dt1));
			return params;
		}
	}

	public static Mapx initIssue(Mapx params) {
		String sql = "select concat(year,'年',periodNum,'期') as Name,ID from zcPaperissue"
				+ " where PaperID=(select min(id) from zcPaper where siteid=" + Application.getCurrentSiteID()
				+ ") order by id desc";
		DataTable dt1 = new QueryBuilder(sql).executeDataTable();
		params.put("optionIssue", HtmlUtil.dataTableToOptions(dt1));
		return params;
	}

	public static Mapx init(Mapx params) {
		Object o1 = params.get("NewspaperID");
		if (o1 != null) {
			long ID = Long.parseLong(o1.toString());
			ZCPaperSchema Paper = new ZCPaperSchema();
			Paper.setID(ID);
			if (Paper.fill()) {
				Mapx map = Paper.toMapx();
				return map;
			}
			return params;
		}
		return params;
	}

	// 添加期刊，在期刊同级添加一个栏目
	public void add() {
		Transaction trans = new Transaction();
		Catalog catalog = new Catalog();
		long catalogID = (catalog.add(Request, trans)).getID();

		ZCPaperSchema Paper = new ZCPaperSchema();
		Paper.setID(catalogID);
		Paper.setValue(Request);
		Paper.setAddTime(new Date());
		Paper.setAddUser(User.getUserName());
		trans.add(Paper, Transaction.INSERT);
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("插入数据发生错误!");
		}
	}

	public void edit() {
		Transaction trans = new Transaction();
		ZCPaperSchema Paper = new ZCPaperSchema();
		Paper.setID(Long.parseLong($V("NewspaperID")));
		if (!Paper.fill()) {
			Response.setStatus(0);
			Response.setMessage("修改数据发生错误!");
			return;
		}
		Paper.setValue(Request);
		Paper.setModifyTime(new Date());
		Paper.setModifyUser(User.getUserName());
		trans.add(Paper, Transaction.UPDATE);

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong($V("NewspaperID")));
		catalog.fill();
		catalog.setValue(Request);
		catalog.setIndexTemplate($V("CoverTemplate"));
		catalog.setModifyUser(User.getUserName());
		catalog.setModifyTime(new Date());
		trans.add(catalog, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("修改数据发生错误!");
		}
	}

	public void del() {
		Transaction trans = new Transaction();
		String ID = $V("CatalogID");

		// 删除相关栏目
		Catalog.deleteCatalog(trans, Long.parseLong(ID));

		ZCPaperSchema Paper = new ZCPaperSchema();
		Paper.setID(Long.parseLong(ID));
		Paper.fill();

		// 期刊
		trans.add(Paper, Transaction.DELETE_AND_BACKUP);
		// 期刊期数
		trans.add(new ZCPaperIssueSchema().query(new QueryBuilder(" where PaperID =?",ID)), Transaction.DELETE_AND_BACKUP);
		
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("删除期刊失败");
		}
	}

}

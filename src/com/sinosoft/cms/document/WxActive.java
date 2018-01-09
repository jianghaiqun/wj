package com.sinosoft.cms.document;
 
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.DataTableUtil;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.WxActiveSchema;
import com.sinosoft.schema.WxActiveSet;
import com.sinosoft.schema.ZCInnerDeploySchema;
import com.sinosoft.schema.ZCInnerDeploySet;

/**
 * 文章管理
 * 
 */
public class WxActive extends Page {

	
	// 编辑器页面初始化
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx init(Mapx params) {
		Mapx map = new Mapx();
		String articleID = (String) params.get("ArticleID");
		QueryBuilder qb = new QueryBuilder(" SELECT * FROM WxActive WHERE id=? ");
		qb.add(articleID);
		DataTable dt = qb.executeDataTable();
		if(dt!=null && dt.getRowCount()>=1){
			map.put("ID", dt.getString(0, "ID"));
			map.put("Title", dt.getString(0, "Title"));
			map.put("Summery", dt.getString(0, "Summery"));
			map.put("Author", dt.getString(0, "Author"));
			map.put("Content", dt.getString(0, "Content"));
		}

		String catalogID = (String) params.get("CatalogID");
		String issueID = (String) params.get("IssueID");
		String title = (String) params.get("Title");
		try {
			if (StringUtil.isNotEmpty(title)) {
				title = URLDecoder.decode(title, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
		}

		map.put("CatalogID", catalogID);
		articleID = NoUtil.getMaxID("DocID") + "";
		map.put("CatalogID", catalogID);
		map.put("CommentFlag", "1");

		map.put("DownlineDate", "2099-12-31");
		map.put("DownlineTime", "23:59:59");

		String archiveTime = "";
		if (CatalogUtil.getArchiveTime(catalogID) != null) {
			archiveTime = DateUtil.toDateTimeString(CatalogUtil.getArchiveTime(catalogID));
		}

		if (StringUtil.isNotEmpty(archiveTime)) {
			map.put("ArchiveDate", archiveTime.substring(0, 10));
			map.put("ArchiveTime", archiveTime.substring(11));
		}

		map.put("Pages", new Integer(1));
		map.put("ContentPages", "''");

		map.put("ReferTarget", "");
		map.put("ReferType", "1");

		// 工作流
		String html = "";
		map.put("buttonDiv", html);

		// 自定义字段
		map.put("CustomColumn", ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID));

		// 文档属性
		map.put("Attribute", HtmlUtil.codeToCheckboxes("Attribute", "ArticleAttribute"));

		map.put("RiskType", HtmlUtil.codeToOptions("RiskType" , "G00"));
		map.put("CompanyID", HtmlUtil.codeToOptions("SupplierCode", true ));
		

		map.put("SourceSign", HtmlUtil.codeToRadios("SourceSign", "SourceSign", "A"));

		// 初始化文章引导图
		String imageLogo = "../Images/addpicture.jpg";
		map.put("Logo", "");
		map.put("ImgLogo", imageLogo);

		map.put("ReferTargetCount", 0);
		map.put("ClusterTargetCount", 0);

		map.put("ReferDisplay", "style='display:none'");

		// Prop1表示文档是否作废 modify by wyli 2010-07-28
		map.put("Prop1", HtmlUtil.codeToRadios("Prop1", "YesOrNo", "N"));
	
		ZCInnerDeploySchema deploy = new ZCInnerDeploySchema();
		deploy.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
		ZCInnerDeploySet set = deploy.query();
		if (set.size() > 0) {
			dt = DataTableUtil.txtToDataTable(set.get(0).getTargetCatalog(), (String[]) null, "\t", "\n");
			if (dt.getDataColumn("MD5") == null) {
				dt.insertColumn("MD5");
			}
			// 如果是自动分发，则全选上
			if ("1".equals(set.get(0).getDeployType())) {
				map.put("ClusterTargetCount", dt.getRowCount() + "/" + dt.getRowCount());
				for (int i = 0; i < dt.getRowCount(); i++) {
					dt.set(i, "MD5", StringUtil.md5Hex(dt.getString(i, "ServerAddr") + "," + dt.getString(i, "SiteID") + "," + dt.getString(i, "CatalogID")));
				}
				map.put("ClusterTarget", StringUtil.join(dt.getColumnValues("MD5")));
			} else {
				map.put("ClusterTargetCount", "0/" + dt.getRowCount());
			}
		}

		long siteID = 0;
		map.put("SiteID", CatalogUtil.getSiteID(catalogID));
		map.put("CatalogName", CatalogUtil.getName(catalogID));
		map.put("IssueID", issueID);
		map.put("InnerCode", CatalogUtil.getInnerCode(catalogID));

		// css样式
		String cssFile = new QueryBuilder("select editorcss from zcsite where id=?", siteID).executeString();
		if (StringUtil.isNotEmpty(cssFile)) {
			String staticDir = Config.getContextPath() + Config.getValue("Statical.TargetDir").replace('\\', '/');
			staticDir = staticDir + "/" + Application.getCurrentSiteAlias() + "/" + cssFile;
			staticDir = staticDir.replaceAll("/+", "/");
			map.put("CssPath", staticDir);
		} else {
			map.put("CssPath", Config.getContextPath() + "Editor/editor/css/fck_editorarea.css");
		}

		String defaultImageValue = Config.getContextPath() + Config.getValue("UploadDir") + "/" + Application.getCurrentSiteAlias() + "/upload/Image/nopicture.jpg";
		defaultImageValue = defaultImageValue.replaceAll("/+", "/");
		map.put("defaultImageValue", defaultImageValue);
	
		return map;
	}
	/**
	 * 保存文章
	 */
	@SuppressWarnings("unchecked")
	public boolean save() {
		
		String activeID= $V("activeID");
		WxActiveSchema article = new WxActiveSchema();
		if(StringUtil.isEmpty(activeID)){
			activeID = NoUtil.getMaxNo("ActiveID");
			article.setCreateDate(PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
		}
		String title= $V("Title");
		String summery= $V("Summery");
		String content= $V("Content");
		String author = $V("Author");
		article.setModifyDate(PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
		
		Transaction trans = new Transaction();
		
		article.setID(activeID);

		String method = $V("Method");

		if ("update".equals(method)||"delete".equals(method)) {
			article.fill();
		}
		article.setTitle(title);
		article.setSummery(summery);
		article.setContent(content);
		article.setAuthor(author);
		article.setActiveStatus("1");
		
		if ("save".equals(method)) {
			trans.add(article, Transaction.INSERT);
		}else if ("update".equals(method)) {
			trans.add(article, Transaction.UPDATE);
		}else if ("delete".equals(method)) {
			trans.add(article, Transaction.DELETE);
		}
		
		if(trans.commit()){
			Response.put("SaveTime", DateUtil.getCurrentDateTime());
			if ("save".equals(method)) {
				Response.setStatus(1);
				Response.setMessage("微信活动信息保存成功！");
			}else if ("update".equals(method)) {
				Response.setStatus(1);
				Response.setMessage("微信活动信息更新成功！");
			}else if ("delete".equals(method)) {
				Response.setStatus(1);
				Response.setMessage("微信活动信息删除成功！");
			}
		}else{
			Response.setStatus(0);
			Response.setMessage("发生错误：操作失败！"); 
			return false;
		}
		return true;
		
	}
	public static void dg1DataBind(DataGridAction dga) {
		
		
		String startDate = (String) dga.getParams().get("StartDate");
		String endDate = (String) dga.getParams().get("EndDate");
		String listType = (String) dga.getParams().get("Type");
		
		QueryBuilder qb = new QueryBuilder(" SELECT ID,Title,Summery,Author,Content,CASE WHEN ActiveStatus='1' THEN '已上线' ELSE '已下线' END AS ActiveStatus,CreateDate,ModifyDate FROM WxActive WHERE 1=1 ");
		
		if(StringUtil.isNotEmpty(startDate)){
			qb.append(" And ModifyDate >= '"+startDate+"'");
		}
		if(StringUtil.isNotEmpty(endDate)){
			qb.append(" And ModifyDate <= '"+endDate+"'");
		}
		if(StringUtil.isNotEmpty(listType)&&!"ALL".equals(listType)){
			qb.append(" And ActiveStatus = "+listType);
		}
		
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
		
	}
	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, "删除文章失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}

		Transaction trans = new Transaction();
		WxActiveSchema article = new WxActiveSchema();
		WxActiveSet set = article.query(new QueryBuilder("where id in (" + ids + ")"));

		trans.add(set, Transaction.DELETE);

		if (trans.commit()) {

			Response.setStatus(1);
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
	public void onLine() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, "删除文章失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}

		QueryBuilder qb = new QueryBuilder(" UPDATE WxActive SET ActiveStatus='1',ModifyDate='"+PubFun.getCurrentDate()+" "+PubFun.getCurrentTime()+"' where id in (" + ids + ")");
		Transaction trans = new Transaction();

		trans.add(qb);

		if (trans.commit()) {

			Response.setStatus(1);
			Response.setMessage("操作成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
	public void unLine() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.ARTICLE, UserLog.ARTICLE_DELARTICLE, "删除文章失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("错误的参数!");
			return;
		}

		QueryBuilder qb = new QueryBuilder(" UPDATE WxActive SET ActiveStatus='0',ModifyDate='"+PubFun.getCurrentDate()+" "+PubFun.getCurrentTime()+"' where id in (" + ids + ")");
		Transaction trans = new Transaction();

		trans.add(qb);

		if (trans.commit()) {

			Response.setStatus(1);
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

}

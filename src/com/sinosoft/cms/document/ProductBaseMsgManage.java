package com.sinosoft.cms.document;
 
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.webservice.CmsService;
import com.sinosoft.cms.webservice.CmsServiceImpl;
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
import com.sinosoft.schema.ProductBaseInfoSchema;
import com.sinosoft.schema.ProductBaseInfoSet;
import com.sinosoft.schema.ZCInnerDeploySchema;
import com.sinosoft.schema.ZCInnerDeploySet;

/**
 * 文章管理
 * 
 */
public class ProductBaseMsgManage extends Page {

	
	// 编辑器页面初始化
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx init(Mapx params) {
		Mapx map = new Mapx();
		String articleID = (String) params.get("ArticleID");
		QueryBuilder qb = new QueryBuilder(" SELECT b.ProductID,b.ProductName,a.ID as ID,a.BaseInfo,a.FrontInfo,a.TailInfo,a.IsFlag,a.ComFlag FROM  productbaseinfo a RIGHT JOIN sdproduct b ON(a.productid=b.productid) WHERE  b.productid=? ");
		qb.add(articleID);
		DataTable dt = qb.executeDataTable();
		if(dt!=null && dt.getRowCount()>=1){
			map.put("ID", dt.getString(0, "ID"));
			map.put("ProductID", dt.getString(0, "ProductID"));
			map.put("Title", dt.getString(0, "ProductName"));
			map.put("FrontInfo", dt.getString(0, "FrontInfo"));
			map.put("TailInfo", dt.getString(0, "TailInfo"));
			map.put("IsFlag", dt.getString(0, "IsFlag"));
			map.put("ComFlag", dt.getString(0, "ComFlag"));
			map.put("Content", dt.getString(0, "BaseInfo"));
		}
		map.put("ComFlag1", HtmlUtil.codeToOptions("IsMustInput", true));
		map.put("IsFlag1", HtmlUtil.codeToOptions("IsMustInput", true));

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
		
		try{
			String activeID= $V("activeID");
			
			ProductBaseInfoSchema article = new ProductBaseInfoSchema();
			if(StringUtil.isEmpty(activeID)){
				activeID = NoUtil.getMaxNo("ActiveID");
				article.setCreateDate(PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
			}
			String productID = $V("ProductID");
			String FrontInfo = $V("FrontInfo");
			String TailInfo = $V("TailInfo");
			String IsFlag = $V("IsFlag");
			String ComFlag = $V("ComFlag");
			String Content = $V("Content");
			
			Transaction trans = new Transaction();
			
			article.setid(activeID);

			String method = $V("Method");

			String comCode = new QueryBuilder(" select Remark6 from sdproduct where productid='"+productID+"' ").executeString();
			//判断品牌下是否已经配置公共头、尾部信息
			int tCount = new QueryBuilder(" select count(1) from productbaseinfo where ComCode='"+comCode+"' and productid != '"+productID+"' and ComFlag='Y' ").executeInt();
			
			if ("update".equals(method)||"delete".equals(method)) {
				article.fill();
			}
			if ("save".equals(method)) {
				ProductBaseInfoSchema article1 = new ProductBaseInfoSchema();
				ProductBaseInfoSet set = article1.query(new QueryBuilder("where productID='"+productID+"' order by modifydate desc"));
				if(set!=null && set.size()>=1){
					article = set.get(0);
					method = "update";
				}
			}
			
			if ("update".equals(method)||"save".equals(method)) {
				/*if(ComFlag.equals("Y")&&"".equals(FrontInfo.trim())&&"".equals(TailInfo.trim())){
					Response.setStatus(0);
					Response.setMessage("如要关联品牌，公共头、尾部信息不能同时为空！");
					return false;
				}*/
				if(ComFlag.equals("Y")&&tCount>=1){
					Response.setStatus(0);
					Response.setMessage("同一品牌下只能设置一组公共头、尾部信息！");
					return false;
				}
			}
			article.setProductID(productID);
			article.setFrontInfo(FrontInfo);
			article.setTailInfo(TailInfo);
			article.setIsFlag(IsFlag);
			article.setComFlag(ComFlag);
			article.setComCode(comCode);
			article.setBaseInfo(Content);
			article.setModifyDate(PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());
			
			if ("save".equals(method)) {
				trans.add(article, Transaction.INSERT);
			}else if ("update".equals(method)) {
				trans.add(article, Transaction.UPDATE);
			}else if ("delete".equals(method)) {
				trans.add(article, Transaction.DELETE);
			}
			QueryBuilder qb1 = new QueryBuilder(" select ProductID from sdproduct where ProductID='"+productID+"' ");
			DataTable dt1 = qb1.executeDataTable();
			if("Y".equals(ComFlag)){
				qb1 = new QueryBuilder(" select ProductID from sdproduct where Remark6='"+comCode+"' and IsPublish='Y'  ");
				dt1 = qb1.executeDataTable();
			}
			if(trans.commit()){
				Response.put("SaveTime", DateUtil.getCurrentDateTime());
				if ("save".equals(method)) {
					Response.setStatus(1);
					Response.setMessage("操作成功！");
				}else if ("update".equals(method)) {
					Response.setStatus(1);
					Response.setMessage("操作成功！");
				}else if ("delete".equals(method)) {
					Response.setStatus(1);
					Response.setMessage("操作成功！");
				}
				//同步产品
				CmsService csi = new CmsServiceImpl();
				int pCount = dt1.getRowCount();
				boolean publishFlag = true;
				String info = "[";
				for(int i=0;i<pCount;i++){
					if(!csi.publishArticle(dt1.getString(i, "ProductID"), "")){
						publishFlag = false;
						info +=dt1.getString(i, "ProductID");
					}
				}
				info+="]";
				if(!publishFlag){
					Response.setStatus(0);
					Response.setMessage("发生错误：同步产品失败！"+info); 
					return false;
				}
			}else{
				Response.setStatus(0);
				Response.setMessage("发生错误：操作失败！"); 
				return false;
			}
			return true;
		}catch(Exception e){
			Response.setStatus(0);
			Response.setMessage("发生错误：操作失败！"); 
			return false;
		}
		
	}
	public static void dg1DataBind(DataGridAction dga) {

		StringBuffer sql = new StringBuffer("  SELECT b.ID ID,a.productid,a.productname," +
				" CASE WHEN (b.baseinfo IS NULL OR b.baseinfo='') THEN '未配置' ELSE '已配置' END AS 'BaseInfo'," +
				" CASE WHEN (b.frontinfo IS NULL OR b.frontinfo='') THEN '未配置' ELSE '已配置' END AS 'FrontInfo'," +
				" CASE WHEN (b.tailinfo IS NULL OR b.tailinfo='') THEN '未配置' ELSE '已配置' END AS 'TailInfo'," +
				" b.IsFlag,b.ComFlag, "+
				" b.ModifyDate FROM sdproduct a LEFT JOIN productbaseinfo b ON(a.productid = b.productid) where a.Ispublish = 'Y' ");
		String productName = (String) dga.getParams().get("productName");
		String productCode = (String) dga.getParams().get("productCode");
		String comName = (String) dga.getParams().get("comName");
		String comFlag = (String) dga.getParams().get("comFlag");
		if (StringUtil.isNotEmpty(productName)) {
			sql.append(" and a.productName like '%"+productName+"%'");
		}
		if (StringUtil.isNotEmpty(productCode)) {
			sql.append(" and a.productID = '"+productCode+"'");
		}
		if (StringUtil.isNotEmpty(productName)) {
			sql.append(" and a.productName like '%"+productName+"%'");
		}
		if (StringUtil.isNotEmpty(comName)) {
			sql.append(" and a.InsureName like '%"+comName+"%'");
		}
		if (StringUtil.isNotEmpty(comFlag)) {
			sql.append(" and b.ComFlag = '"+comFlag+"'");
		}
		sql.append(" order by b.modifydate desc");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);	
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
		ProductBaseInfoSchema article = new ProductBaseInfoSchema();
		ProductBaseInfoSet set = article.query(new QueryBuilder("where id in (" + ids + ")"));

		trans.add(set, Transaction.DELETE);
		QueryBuilder qb1 = new QueryBuilder(" select ProductID,Remark6 from sdproduct where ProductID='"+set.get(0).getProductID()+"' ");
		DataTable dt1 = qb1.executeDataTable();
		if("Y".equals(set.get(0).getComFlag())){
			qb1 = new QueryBuilder(" select ProductID from sdproduct where Remark6='"+dt1.getString(0, "Remark6")+"' and IsPublish='Y'  ");
			dt1 = qb1.executeDataTable();
		}
		if (trans.commit()) {
			//同步产品
			CmsService csi = new CmsServiceImpl();
			int pCount = dt1.getRowCount();
			boolean publishFlag = true;
			String info = "[";
			for(int i=0;i<pCount;i++){
				if(!csi.publishArticle(dt1.getString(i, "ProductID"), "")){
					publishFlag = false;
					info +=dt1.getString(i, "ProductID");
				}
			}
			info+="]";
			if(!publishFlag){
				Response.setStatus(0);
				Response.setMessage("发生错误：同步产品失败！"+info); 
				return;
			}
			Response.setStatus(1);
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}


}

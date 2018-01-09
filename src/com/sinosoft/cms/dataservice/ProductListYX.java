package com.sinosoft.cms.dataservice;

import com.sinosoft.cms.site.CatalogCJWT;
import com.sinosoft.cms.site.CatalogShowConfig;
import com.sinosoft.cms.site.CatalogYXCP;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.controls.TreeItem;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDMarketProductSchema;
import com.sinosoft.schema.SDProductDutySchema;
import com.sinosoft.schema.SDProductHighlightsSchema;

import java.util.Date;
import java.util.List;

public class ProductListYX extends Page {

	/**
	 * 页面初始化
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx init(Mapx params) {
		QueryBuilder qb_insuranceCompany = new QueryBuilder("SELECT codename,codevalue FROM zdcode WHERE codetype='SupplierCode' AND codevalue NOT LIKE '%sys%' ");
		DataTable dt_insuranceCompany = qb_insuranceCompany.executeDataTable();
		params.put("SupplierCode", HtmlUtil.dataTableToOptions(dt_insuranceCompany, true));
		return params;
	}
	/**
	 * 页面初始化
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initDialog(Mapx params) {
		params.put("productId",params.get("productId"));
		return params;
	}

	/**
	 * 元素数据查询
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select * from sdproduct where ispublish = 'Y' ";

		String productName = (String) dga.getParams().get("productName");
		String productID = (String) dga.getParams().get("productID");
		String SupplierCode = (String) dga.getParams().get("SupplierCode");

		QueryBuilder qb = new QueryBuilder(sql);

		if (StringUtil.isNotEmpty(productName)) {
			qb.append(" and ProductName like ? ", "%"+productName+"%");
		}

		if (StringUtil.isNotEmpty(productID)) {
			qb.append(" and ProductID = ? ",productID);
		}

		if (StringUtil.isNotEmpty(SupplierCode)) {
			qb.append(" and Remark6 = ? ",SupplierCode);
		}

		qb.append(" order by ProductID");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public void dg1DataBindDialog(DataGridAction dga) {
		String productID = $V("productId");
		
		StringBuffer sb = new StringBuffer();
		sb.append("select * FROM sdProductHighlights where productID ='"+productID+"'");
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	public void dg1DataBindDialog2(DataGridAction dga) {
		String productID = $V("productId");
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.*,b.DutyName as dutyDetail FROM SDProductDuty a LEFT JOIN FMDuty b ON a.DutyCode=b.DutyCode WHERE productid ='"+productID+"' order by a.orderby");
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	public void addHighlights(){
		String productID = $V("productId");
		String content = $V("content");
		Date date = new Date();
		Transaction tran = new Transaction();
		SDProductHighlightsSchema  tHighlights = new SDProductHighlightsSchema();
		tHighlights.setId(NoUtil.getMaxNo("HighlightId","SN"));
		tHighlights.setProductID(productID);
		tHighlights.setDetail(content);
		tHighlights.setCreateDate(date);
		tHighlights.setModifyDate(date);
		tHighlights.setOperater(User.getUserName());
		tran.add(tHighlights,Transaction.INSERT);
		if(tran.commit()){
			Response.setStatus(1);
			Response.setMessage("保存成功！");
		}else{
			Response.setStatus(0);
			Response.setMessage("保存失败！");
			return;
		}
		saveMarketProduct(productID);
	}
	public void updateHighlights(){
		String productID = $V("productId");
		String content = $V("content");
		String id = $V("Id");
		Transaction tran = new Transaction();
		SDProductHighlightsSchema  tHighlights = new SDProductHighlightsSchema();
		tHighlights.setId(id);
		tHighlights.setProductID(productID);
		if(tHighlights.fill()){
			tHighlights.setDetail(content);
			tHighlights.setModifyDate(new Date());
			tHighlights.setOperater(User.getUserName());
			tran.add(tHighlights,Transaction.UPDATE);
		}else{
			Response.setStatus(0);
			Response.setMessage("传入ID错误！");
			return;
		}
		if(tran.commit()){
			Response.setStatus(1);
			Response.setMessage("保存成功！");
		}else{
			Response.setStatus(0);
			Response.setMessage("保存失败！");
			return;
		}
		saveMarketProduct(productID);
	}
	public void deleteHighlights(){
		String productID = $V("productId");
		String id = $V("Id");
		Transaction tran = new Transaction();
		SDProductHighlightsSchema  tHighlights = new SDProductHighlightsSchema();
		tHighlights.setId(id);
		tHighlights.setProductID(productID);
		if(tHighlights.fill()){
			tran.add(tHighlights,Transaction.DELETE);
		}else{
			Response.setStatus(0);
			Response.setMessage("传入ID错误！");
			return;
		}
		if(tran.commit()){
			Response.setStatus(1);
			Response.setMessage("保存成功！");
		}else{
			Response.setStatus(0);
			Response.setMessage("保存失败！");
			return;
		}
		saveMarketProduct(productID);
	}
	
	public void dg1Edit(){
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		String productID = "";
		for (int i = 0; i < dt.getRowCount(); i++) {
			SDProductDutySchema tProductDuty = new SDProductDutySchema();
			productID = dt.getString(i, "productID");
			tProductDuty.setProductID(productID);
			tProductDuty.setDutyCode(dt.getString(i, "dutyCode"));
			if(tProductDuty.fill()){
				tProductDuty.setOrderBy(dt.getString(i, "OrderBy"));
				trans.add(tProductDuty,Transaction.UPDATE);
			}else{
				Response.setStatus(0);
				Response.setMessage("传入ID错误！");
				return;
			}
		}
		if (trans.commit()) {
			saveMarketProduct(productID);
			Response.setStatus(1);
			Response.setMessage("修改成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("修改失败!");
		}
	}
	
	public static Mapx initEidt(Mapx params){
		String serverContext = Config.getFrontServerContextPath();
		params.put("serverContext", serverContext);
		
		String articleId = params.getString("ArticleID");
		params.put("ArticleID", articleId);
		
		StringBuffer navHTML = new StringBuffer();
		DataTable dt = new QueryBuilder("select NavDetail from SDMarketNav where MarketID ='"+articleId+"'").executeDataTable();
		if(dt.getRowCount()>0){
			navHTML.append(dt.getString(0, 0));
		}
		params.put("navHTML", navHTML);
		
		String color = "#F5F5F5";
		String WordRight = "";
		String WordRightLink = "";
		String txtHtml = "";
		String navHtml = "";
		String PictruePath ="";
		String PictruePaths ="";
		String PictrueLink ="";
		String WordLeft ="";
		String WordLeftColor ="";
		String WordLeftBack ="";
		String preImg = "";
		String preImgs = "";
		String PrePicHid = "";
		DataTable dt1 = new QueryBuilder("select * from SDMarketing where id ='"+articleId+"'").executeDataTable();
		if(dt1.getRowCount()>0){
			WordRight = dt1.getString(0, "WordRight");
			WordRightLink = dt1.getString(0, "WordRightLink");
			txtHtml = dt1.getString(0, "txtHtml");
			if (StringUtil.isNotEmpty(dt1.getString(0, "Color"))) {
				color = dt1.getString(0, "Color");
			}
			navHtml = dt1.getString(0, "navHtml");
			PictruePath = dt1.getString(0, "PictruePath");
			PictrueLink = dt1.getString(0, "PictrueLink");
			WordLeft = dt1.getString(0, "WordLeft");
			WordLeftColor = dt1.getString(0, "WordLeftColor");
			WordLeftBack = dt1.getString(0, "WordLeftBack");
			preImg = dt1.getString(0, "PrePicPath");
			PrePicHid = dt1.getString(0, "PrePicHid");
		}
		params.put("WordRight", WordRight);
		params.put("WordRightLink", WordRightLink);
		params.put("txtColor", color);
		params.put("txtHtml", txtHtml);
		params.put("menuCont", navHtml);
		if(StringUtil.isEmpty(PictruePath)){
			PictruePath = Config.getValue("StaticResourcePath")+"/images/img_banner.jpg";
			PictruePaths = Config.getValue("StaticResourcePath")+"/images/img_bnr_01.jpg";
		}else{
			PictruePaths = PictruePath;
		}
		if(StringUtil.isEmpty(preImg)){
			preImg = Config.getValue("StaticResourcePath")+"/images/img_intro_cont.jpg";
			preImgs = Config.getValue("StaticResourcePath")+"/images/img_intro_01.jpg";
		}else{
			preImgs = preImg;
		}
		
		params.put("preImg", preImg);
		params.put("preImgs", preImgs);
		params.put("PictruePath", PictruePath);
		params.put("PictruePaths", PictruePaths);
		params.put("PictrueLink", PictrueLink);
		params.put("WordLeft", WordLeft);
		params.put("WordLeftColor", WordLeftColor);
		params.put("WordLeftBack", WordLeftBack);
		params.put("PrePicHid", PrePicHid);
		
		return params;
	}
	
	public static String getProductDetailHtml(String productID){
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT b.id,b.url,b.title,b.logo,b.risktype");
		sql.append(" FROM zccatalog a,zcarticle b,zdcolumnvalue c WHERE a.id=b.catalogid ");
		sql.append(" AND a.innercode LIKE '002313%' AND b.prop4 ='"+productID+"' AND b.TYPE='1' GROUP BY a.ID");
		DataTable dt = new QueryBuilder(sql.toString()).executeDataTable(); 
		String relaId = "";
		String url = "";
		String title = "";
		String logo = "";
		String risktype = "";
		if(dt.getRowCount()>0){
			relaId = dt.getString(0, "id");
			url = dt.getString(0, "url");
			title = dt.getString(0, "title");
			logo = dt.getString(0, "logo");
			risktype = dt.getString(0, "risktype");
		}
		
		String sql_Sup ="SELECT textvalue FROM zdcolumnvalue WHERE columncode ='SupplierCode' AND relaid='"+relaId+"'";
		String SupplierCode2 = (String) new QueryBuilder(sql_Sup).executeOneValue();
		
		String sql_Sale ="SELECT textvalue FROM zdcolumnvalue WHERE columncode ='SalesVolume' AND relaid='"+relaId+"'";
		String Sale = (String) new QueryBuilder(sql_Sale).executeOneValue();
		
		String sql_Cal ="SELECT textvalue FROM zdcolumnvalue WHERE columncode ='CalHTML2' AND relaid='"+relaId+"'";
		String CalHTML2 = (String) new QueryBuilder(sql_Cal).executeOneValue();
		
		String sql_MarkPrice ="SELECT textvalue FROM zdcolumnvalue WHERE columncode ='prodcutMarkPrice' AND relaid='"+relaId+"'";
		String MarkPrice = (String) new QueryBuilder(sql_MarkPrice).executeOneValue();
		
		String sql_RiskCode ="SELECT textvalue FROM zdcolumnvalue WHERE columncode ='RiskCode' AND relaid='"+relaId+"'";
		String RiskCode = (String) new QueryBuilder(sql_RiskCode).executeOneValue();
		
		/*String sql_Adapt ="SELECT textvalue FROM zdcolumnvalue WHERE columncode ='AdaptPeopleInfo' AND relaid='"+relaId+"'";
		String Adapt = (String) new QueryBuilder(sql_Adapt).executeOneValue();*/
		
		
		String sql_Highlights = "SELECT detail FROM SDProductHighlights WHERE productid ='"+productID+"' ";
		DataTable dtH = new QueryBuilder(sql_Highlights).executeDataTable();
		String BrightSpot = "";
		if(dtH.getRowCount()>0){
			StringBuffer html = new StringBuffer();
			html.append("<ul class=\"d_list_con\">");
			for(int i=0;i<dtH.getRowCount();i++){
				html.append("<li><span>"+dtH.getString(i, 0)+"</span></li>");
			}
			html.append("</ul>");
			BrightSpot = html.toString();
		}else{
			String sql_BrightSpot ="SELECT textvalue FROM zdcolumnvalue WHERE columncode ='FEMRiskBrightSpot' AND relaid='"+relaId+"'";
			BrightSpot = (String) new QueryBuilder(sql_BrightSpot).executeOneValue();
		}
		//mod by wangej 20160104 按照orderby排序取4条数据  并replace隐藏属性list_5为list_0
		StringBuffer sql_DutyHTML = new StringBuffer();
		sql_DutyHTML.append("SELECT ProductID,DutyCode,REPLACE (DutyName,'list_5','list_0') DutyName, ");
		sql_DutyHTML.append("DutyHtmlWap,OrderBy,CreateDate,ModifyDate,Operater,Prop1,Prop2 ");
		sql_DutyHTML.append("FROM sdProductDuty WHERE productid = '"+productID+"' ORDER BY orderby LIMIT 4 ");
		DataTable dtD = new QueryBuilder(sql_DutyHTML.toString()).executeDataTable();
		String DutyHTML = "";
		if(dtD.getRowCount()>0){
			StringBuffer html = new StringBuffer();
			html.append("<div id=\"divRiskAppDuty_"+productID+"\" class=\"CRiskAppDuty\">");
			html.append("<div class=\"title\">");
			html.append("<span class=\"CDutyCol_1\">责任项目</span>");
			html.append("<span class=\"CDutyCol_2\">保额</span>");
			html.append("<span class=\"CDutyCol_3\">责任描述</span>");
			html.append("</div>");
			
			for(int i=0;i<dtD.getRowCount();i++){
				html.append(dtD.getString(i, "DutyName"));
			}
			html.append("</div>");
			DutyHTML = html.toString();
		}else{
			String sql_BrightSpot ="SELECT textvalue FROM zdcolumnvalue WHERE columncode ='DutyHTML' AND relaid='"+relaId+"'";
			DutyHTML = (String) new QueryBuilder(sql_BrightSpot).executeOneValue();
		}
		
		
		
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"cf\">");
		sb.append("<div class=\"stc_shopboxs\">");
		sb.append("<div class=\"product_title\">");
		sb.append("<a target=\"_blank\" href=\""+Config.getFrontServerContextPath()+"/"+url+"\" class=\"CInsuranceCompany icon_C"+SupplierCode2+"\"></a><span class=\"productName\"> <a target=\"_blank\" href=\""+Config.getFrontServerContextPath()+"/"+url+"\"");
		sb.append("\"><h2 class=\"ziti\">"+title+"</h2></a>");
		sb.append("</span> <span class=\"SalesVolume\" id=\"SalesV_"+RiskCode+"\">(累计销量：<b>"+Sale+"</b>)</span><span id=\"productIntegral_209601001\" style=\"display: none;\"></span>");
		sb.append("</div>");
		sb.append("<div class=\"product_condition\">");
		sb.append(CalHTML2);
		sb.append("</div>");
		sb.append("<div class=\"product_info\">");
		sb.append("<div class=\"product_info_bor\">");
		sb.append("<p class=\"list_Sale tribe-action\" id=\"Activity_"+RiskCode+"\">");
		sb.append("<span id=\"Diy_Title_Activity_"+RiskCode+"\" style=\"display: none;\"></span>");
		sb.append("<em id=\"Diy_Activity_"+RiskCode+"\" style=\"display: none;\"></em></p>");
		sb.append("<div class=\"prodcutMark\">");
		sb.append("<ul class=\"price\">"+MarkPrice+"</ul>");
		sb.append("<ul class=\"btn\">");
		sb.append("<li class=\"btn1\"><span onclick=\"chakan('"+Config.getFrontServerContextPath()+"/"+url+"')\">查看详情</span></li>");
		sb.append("<li class=\"btn2\"><span onclick=\"submitp('"+RiskCode+"');\" id=\"id_"+RiskCode+"\">加入收藏</span><span onclick=\"showcp('"+title+"','"+logo+"','"+RiskCode+"','"+Config.getServerContext()+"','"+risktype+"');\" class=\"add_cp_list\">加入对比</span></li>");
		sb.append("</ul>");
		sb.append("</div>");
		sb.append("</div>");
		/*sb.append("<div class=\"AdaptPeopleInfo\">"+Adapt+"</div>");*/
		sb.append("<div class=\"productFeature\">"+BrightSpot+"</div>");
		sb.append(DutyHTML);
		sb.append("</div></div>");
		sb.append("</div>");
		
		return sb.toString();
	}
	
	public static void saveMarketProduct(String productID){
		SDMarketProductSchema tMarketProduct = new SDMarketProductSchema();
		Transaction trans = new Transaction();
		String proDetailHtml = getProductDetailHtml(productID);
		tMarketProduct.setProductID(productID);
		try{
			if(tMarketProduct.fill()){
				tMarketProduct.setProDetailHtml(proDetailHtml);
				trans.add(tMarketProduct,Transaction.UPDATE);
			}else{
				tMarketProduct.setProDetailHtml(proDetailHtml);
				trans.add(tMarketProduct,Transaction.INSERT);
			}
			trans.commit();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
	}
	
	public static String saveProductDutyHtml(String productID,String dutyCode,String dutyHtml, String dutyHtmlForWap){
		SDProductDutySchema tProductDuty = new SDProductDutySchema();
		Transaction trans = new Transaction();
		tProductDuty.setProductID(productID);
		tProductDuty.setDutyCode(dutyCode);
		Date date = new Date();
		try{		
			if(!tProductDuty.fill()){
				tProductDuty.setCreateDate(date);
				tProductDuty.setDutyName(dutyHtml);
				tProductDuty.setDutyHtmlWap(dutyHtmlForWap);
				tProductDuty.setModifyDate(date);
				tProductDuty.setOperater(User.getUserName());
				tProductDuty.setOrderBy(NoUtil.getMaxNo("DutyId","SN"));
				trans.add(tProductDuty,Transaction.INSERT);
				trans.commit();
			}else{
				tProductDuty.setDutyName(dutyHtml);
				tProductDuty.setDutyHtmlWap(dutyHtmlForWap);
				tProductDuty.setModifyDate(date);
				tProductDuty.setOperater(User.getUserName());
				//mod by wangej 20160104 业务自行配置orderby 更新的时候orderby不应该做处理
				//tProductDuty.setOrderBy(NoUtil.getMaxNo("DutyId","SN"));
				trans.add(tProductDuty,Transaction.UPDATE);
				trans.commit();
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return "";
	}
	
	/**
	 * 向外提供显示栏目树
	 * 
	 */
	public static void treeDataBind(TreeAction ta) {
		treeDataBind1(ta);
	}
	
	/**
	 * 编辑时要根据ID预先选中树形的某引起节点
	 */
	public static void treeDataBind1(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		long siteID = (obj != null) ? Long.parseLong(obj.toString()) : Application.getCurrentSiteID();
		Object typeObj = ta.getParams().get("CatalogType");
		int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : CatalogCJWT.CATALOG;

		String IDs = ta.getParam("IDs");
		if (StringUtil.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.DocList.LastCatalog");
		}
		String[] codes = CatalogYXCP.getSelectedCatalogList(IDs, CatalogShowConfig.getArticleCatalogShowLevel());

		DataTable dt = null;
		ta.setLevel(CatalogShowConfig.getArticleCatalogShowLevel());
		
		String innercode = (String)new QueryBuilder("SELECT innercode FROM ZCCatalog WHERE NAME = '营销产品列表'").executeOneValue();
		
		QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog"
							+ " Where Type = ? and SiteID = ? and TreeLevel-1 <=? and innercode like '"+innercode+"%' order by orderflag,innercode");
		qb.add(catalogType);
		qb.add(siteID);
		qb.add(ta.getLevel());
		dt = qb.executeDataTable();
		CatalogYXCP.prepareSelectedCatalogData(dt, codes, catalogType, siteID, ta.getLevel());

		String siteName = "文档库";
		if (catalogType == CatalogCJWT.SHOP_GOODS) {
			siteName = "商品库";
		} else if (catalogType == CatalogCJWT.SHOP_GOODS_BRAND) {
			siteName = "商品品牌库";
		}
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.ARTICLE, dr.getString("InnerCode"), Priv.ARTICLE_BROWSE);
			}
		});

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
		CatalogYXCP.addSelectedBranches(ta, codes);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
		}
	}

}

/**
 * 
 */
package com.sinosoft.cms.document;

import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.RecommendToDetailSchema;
import com.sinosoft.schema.RecommendToDetailSet;

/**
 * @author Administrator
 *
 */
public class RecommendToDetail extends Page {

	public static Mapx<String, String> init(Mapx<String, String> params) {
		String codeValue = params.get("codeValue");
		DataTable dt = new QueryBuilder(
				"SELECT a.ProductID, a.ProductName, r.Prop2 FROM RecommendToDetail r, sdsearchrelaproduct a WHERE r.Prop1=? and a.Prop1 = r.RelaArticleID order by r.Prop2+0 asc ",
				codeValue).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowcount = dt.getRowCount();
			for (int i = 0; i < rowcount; i++) {
				params.put("ProductId"+dt.getString(i, 2), dt.getString(i, 0));
				params.put("ProductName"+dt.getString(i, 2), dt.getString(i, 1));
			}
		}
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		String codeValue = (String) dga.getParams().get("codeValue");
		DataTable dt = new QueryBuilder(
				"SELECT DISTINCT r.RelaArticleID,a.ProductName FROM sdsearchrelaproduct a, RecommendToDetail r, sdproduct s WHERE s.ProductType = ? AND s.IsPublish='Y' AND s.ProductID=r.ProductID AND r.RelaArticleID = a.Prop1 AND r.Prop1 IS NULL ",
				codeValue).executeDataTable();
		dga.bindData(dt);
	}
	
	public static void dg2DataBind(DataGridAction dga) {
		DataTable dt = new QueryBuilder(
				"SELECT CodeValue, CodeName FROM zdcode WHERE CodeType = 'MainProductType' and ParentCode='MainProductType'").executeDataTable();
		dga.bindData(dt);
	}
	public static void dg1DataBindAllProduct(DataGridAction dga) {
		String productname = (String) dga.getParams().get("productname");
		String codeValue = (String) dga.getParams().get("codeValue");
		QueryBuilder qb_product = new QueryBuilder("SELECT productid as id,productname FROM sdproduct WHERE IsPublish='Y' and ProductType=? ",codeValue);
		if (StringUtil.isNotEmpty(productname)) {
			qb_product.append(" and productname like "+"'%"+ productname.trim() + "%'");
		}
		qb_product.append(" ORDER BY productid ");
		qb_product.append(dga.getSortString());
		dga.setTotal(qb_product);
		DataTable dt = qb_product.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "id", dt.getString(i, "id"));
			dt.set(i, "productname", dt.getString(i, "productname"));
		}
		dga.bindData(dt);
	}
	
	public void saveRecomProduct() {
		String codeValue = Request.getString("codeValue");
		String productId1 = Request.getString("ProductId1");
		String productId2 = Request.getString("ProductId2");
		String productId3 = Request.getString("ProductId3");
		
		if (StringUtil.isEmpty(codeValue)) {
			Response.setStatus(0);
			Response.setMessage("请选择分类再设置推荐产品！");
			return;
		}
		
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from RecommendToDetail where Prop1=?", codeValue));
		
		String catalogID = new QueryBuilder("select Remark1 from SDProduct where ProductType = ? limit 0,1 ", codeValue).executeString();
		RecommendToDetailSet tRecommendToDetailSet = new RecommendToDetailSet();
		
		if (StringUtil.isNotEmpty(productId1)) {
			tRecommendToDetailSet.add(insert(codeValue, catalogID, productId1, "1"));
		}
		
		if (StringUtil.isNotEmpty(productId2)) {
			tRecommendToDetailSet.add(insert(codeValue, catalogID, productId2, "2"));
		}
		
		if (StringUtil.isNotEmpty(productId3)) {
			tRecommendToDetailSet.add(insert(codeValue, catalogID, productId3, "3"));
		}
		if (tRecommendToDetailSet.size() > 0) {
			trans.add(tRecommendToDetailSet, Transaction.INSERT);
		}
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("设置推荐产品成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("设置推荐产品失败！");
		}
		
	}
	
	private RecommendToDetailSchema insert(String productType, String catalogID, String recomProductId, String orderFlag) {
		String sql = "select Prop1 from sdsearchrelaproduct where ProductID = ?";
		RecommendToDetailSchema tRecommendToDetailSchema = new RecommendToDetailSchema();
		tRecommendToDetailSchema.setid(NoUtil
				.getMaxIDLocal("RecommendToDetail")
				+ "");
		tRecommendToDetailSchema
				.setMakeDate(new Date());
		tRecommendToDetailSchema
				.setModifyDate(new Date());
		tRecommendToDetailSchema
				.setProductID("");
		tRecommendToDetailSchema
				.setCatalogID(catalogID);
		tRecommendToDetailSchema.setArticleID("");
		tRecommendToDetailSchema
				.setRelaArticleID(new QueryBuilder(sql, recomProductId).executeString());
		tRecommendToDetailSchema.setProp1(productType);
		tRecommendToDetailSchema.setProp2(orderFlag);
		return tRecommendToDetailSchema;
	}
	
	public String getRecommendToDetail(String riskCode) {
		StringBuffer html = new StringBuffer();
		DataTable dt = getRecomProductInfo(riskCode);
		if (dt != null && dt.getRowCount() > 0) {
			int rowcount = dt.getRowCount();
			for (int i = 0; i < rowcount; i++) {
				html.append("<li class=\"usershop_list\"><p>");
				html.append("<a target=\"_blank\" href=\""+dt.getString(i, "URL"));
				if (StringUtil.isNotEmpty(dt.getString(i, "RelaArticleID"))) {
					html.append("?link_id=d"+dt.getString(i, "RelaArticleID"));
					html.append("\" onclick=\"javascript:void(0);return(VL_FileDL(this));return false;\" ");
					html.append("exturl=\"http://www.kaixinbao.com/r"+dt.getString(i, "RelaArticleID"));
					html.append("\" vlpageid=\"r"+dt.getString(i, "RelaArticleID"));
				}
				html.append("\"><span class=\"us_title\">"+dt.getString(i, "ProductName")+"</span>");
				html.append("<span class=\"us_text\">"+dt.getString(i, "AdaptPeopleInfoListV3")+"</span></a></p>");
				html.append("<p><span name=\"Ajax_Prict_"+dt.getString(i, "ProductID")+"\" class=\"shop_m\">");
				html.append("<em>￥</em>"+dt.getString(i, "InitPrem")+"</span>");
				html.append("<span class=\"sale_num\">"+dt.getString(i, "SalesVolume")+"人已购买</span></p></li>");
			}
		}
		
		return html.toString();
	}
	
	public DataTable getRecomProductInfo(String riskCode) {
		DataTable dt = null;
		dt = new QueryBuilder("select r.RelaArticleID,a.ProductID,a.ProductName,a.URL,a.SalesVolume,a.InitPrem,a.AdaptPeopleInfoListV3,a.DutyHTML2,a.SupplierCode2,a.AdaptPeopleInfo from sdsearchrelaproduct a, RecommendToDetail r, sdproduct s where a.Prop1=r.RelaArticleID and r.Prop1=s.ProductType and s.ProductID = ? and a.ProductID != ? order by r.Prop2 + 0 asc limit 3", riskCode, riskCode).executeDataTable();
		int rowcount = 3;
		String productIds = "";
		if (dt != null && dt.getRowCount() > 0) {
			rowcount = rowcount - dt.getRowCount();
			for (int i = 0; i < dt.getRowCount(); i++) {
				productIds += " and a.ProductID != '"+dt.getString(i, "ProductID")+"' ";
			}
		}
		if (rowcount > 0) {
			String orderSql =  " order by rand() limit " + rowcount;
			DataTable dt1 = new QueryBuilder("select '' as RelaArticleID,a.ProductID,a.ProductName,a.URL,a.SalesVolume,a.InitPrem,a.AdaptPeopleInfoListV3,a.DutyHTML2,a.SupplierCode2,a.AdaptPeopleInfo from sdsearchrelaproduct a, RecommendToDetail r where a.Prop1=r.RelaArticleID and r.ProductID=? "+productIds+orderSql, riskCode).executeDataTable();
			if (dt != null) {
				if (dt1 != null && dt1.getRowCount() > 0) {
					rowcount = dt1.getRowCount();
					for (int i = 0; i < rowcount; i++) {
						dt.insertRow(dt1.getDataRow(i));
					}
				}
			} else {
				return dt1;
			}
		}
		return dt;
	}
}

package com.sinosoft.wap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Set;

import sun.misc.BASE64Decoder;

import com.finance.util.JedisCommonUtil;
import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.CustomClaimsDataInfoSchema;
import com.sinosoft.schema.ProductRelaInfoSchema;
import com.sinosoft.schema.ProductRelaInfoSet;
import com.sinosoft.schema.WapPictureUrlSchema;
import com.sinosoft.schema.WapPictureUrlSet;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;

public class ProductManage extends Page {

	/**
	 * 限时优惠产品页面初始化
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")  
	public static Mapx initLimitProduct(Mapx params) {
		 
		String sql =" SELECT a.productName,b.Preferential,b.startdate,b.enddate FROM sdproduct a "
				   +" LEFT JOIN productrelainfo b ON a.productid = b.productid "
				   +" WHERE a.productid = '"+params.get("productId")+"'";
		
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		
		int line = dt.getRowCount();
	
		if(line ==1){
			params.put("productName", dt.get(0, "productName"));
			params.put("Preferential",dt.get(0, "Preferential"));
			Object o1 = dt.get(0, "StartDate");
			Object o2 = dt.get(0, "EndDate");
			//去掉具体time
			if(o1!=null&&o2!=null){
				params.put("StartDate", dt.get(0, "StartDate").toString().substring(0,10));
				params.put("EndDate", dt.get(0, "EndDate").toString().substring(0,10));
				params.put("StartTime", dt.get(0, "StartDate").toString().substring(11,19));
				params.put("EndTime", dt.get(0, "EndDate").toString().substring(11,19));
			}
		}
		
		return params;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx initUploadPictureDialog(Mapx params) {
		params.put("WapProductPictureType", HtmlUtil.codeToOptions("WapProductPictureType", false));
		return params;
	}
	/**
	 * 元素数据查询
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {

		StringBuffer sql = new StringBuffer("  SELECT a.productID id,a.ProductName,a.Ispublish wapIspublish,b.IsPublish,b.HotFlag,b.RecommendFlag,b.Preferential,b.StartDate,b.EndDate,b.Prop1 AS TopFlag,b.Prop5,b.Prop2 "
				+ ",(SELECT PictureUrl FROM wapPictureUrl WHERE RiskCode = a.productid and type='HeadPicture' )AS wapPictureUrl "
					+" FROM sdsearchrelaproduct e,sdproduct a "
					+" LEFT JOIN productrelainfo b ON (a.productid = b.productid) WHERE a.productid = e.productid and a.Ispublish = 'Y' AND (a.producttype IN ('A','B','C','D','E') or (a.JFHtmlPath is not null and a.JFHtmlPath!='')) "
					// 2014-11-11 wap站健康告知产品上线
					//+" AND NOT EXISTS ( SELECT 1 FROM healthyInfo c WHERE c.ProductId =a.productid)"
					+" AND NOT EXISTS ( SELECT 1 FROM zdconfig WHERE TYPE = 'questionPaper' AND VALUE=a.productid) ");
		String productName = (String) dga.getParams().get("productName");
		String IsPublish = (String) dga.getParams().get("IsPublish");
		String RecommendFlag = (String) dga.getParams().get("RecommendFlag");
		String TopFlag = (String) dga.getParams().get("TopFlag"); 
		String HotFlag = (String) dga.getParams().get("HotFlag"); 
		String Preferential = (String) dga.getParams().get("Preferential");
		if (StringUtil.isNotEmpty(productName)) {
			sql.append(" and a.productName like '%"+productName+"%'");
		}
		if (StringUtil.isNotEmpty(IsPublish)) {
			sql.append(" and b.IsPublish = '"+IsPublish+"'");
		}
		if (StringUtil.isNotEmpty(RecommendFlag)) {
			sql.append(" and b.RecommendFlag = '"+RecommendFlag+"'");
		}
		if (StringUtil.isNotEmpty(HotFlag)) {
			sql.append(" and b.HotFlag = '"+HotFlag+"'");
		}
		if (StringUtil.isNotEmpty(Preferential)) {
			sql.append(" and b.Preferential = '"+Preferential+"'");
		}
		if (StringUtil.isNotEmpty(TopFlag) && "Y".equals(TopFlag)) {
			sql.append(" and b.Prop1 = '"+TopFlag+"'");
		}
		sql.append(" order by id");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		//DataTable dt = qb.executeDataTable();
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);	
		dga.bindData(dt);
		
	}
	public void queryWapPicture(DataGridAction dga) {
		StringBuffer sql = new StringBuffer("select ID,PictureUrl, orderFlag, ");
		sql.append(" (select codeName from zdcode where codetype='WapProductPictureType' and ParentCode='WapProductPictureType' and CodeValue=type) as typeName ");
		sql.append(" from wapPictureUrl where riskcode=? order by orderFlag asc ");
		QueryBuilder qb = new QueryBuilder(sql.toString(),  dga.getParams().getString("productid"));
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);	
		dga.bindData(dt);
	}

	public void delWapProductPicture() {
		String id = Request.getString("id");
		String ServerPath = Config.getContextRealPath()+"/wwwroot/kxb";
		String StaticResourcePath = Config.getValue("StaticResourcePath");
		String PictureUrl = "";
		WapPictureUrlSchema fileSchema = new WapPictureUrlSchema();
		fileSchema.setID(id);
		if (fileSchema.fill()) {
			PictureUrl=fileSchema.getPictureUrl();
			PictureUrl = PictureUrl.replace(StaticResourcePath,ServerPath);
			File file = new File(PictureUrl);
			if (file.exists()) {
				file.delete();
			}
			fileSchema.delete();
		} else {
			Response.setStatus(0);
			Response.setMessage("图片数据未查询到!");
		}
	}
	
	/**
	 * 限时优惠
	 */
	public void saveLimitProduct() {
		try {
			String productId = $V("productId");
			System.err.println("**********productId********|"+productId);
			if (StringUtil.isEmpty(productId)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}
			if (!StringUtil.checkID(productId)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			String StartDate = $V("StartDate");
			String EndDate = $V("EndDate");
			String Preferential = $V("Preferential");
			String StartTime = $V("StartTime");
			String EndTime = $V("EndTime");
			if (StringUtil.isEmpty(StartDate)||StringUtil.isEmpty(EndDate)) {
				Response.setStatus(0);
				Response.setMessage("请至少录入限时优惠日期!");
				return;
			}
			
			StartDate +=" "+StartTime;
			EndDate +=" "+EndTime;
			
			System.err.println("*****|"+Preferential+"|********|"+StartDate+"|***********|"+EndDate+"|********|"+StartTime+"|*****|"+EndTime+"|*****");
			
			Transaction trans = new Transaction();
			ProductRelaInfoSchema tProductRelaInfoSchema = new ProductRelaInfoSchema ();
			tProductRelaInfoSchema.setProductID(productId);
			if(tProductRelaInfoSchema.fill()){
				String sql = " UPDATE productrelainfo SET Preferential = '"+Preferential+"',StartDate='"+StartDate+"',EndDate='"+EndDate+"' WHERE productid = "+productId+" ";
				QueryBuilder qb = new QueryBuilder(sql);
				trans.add(qb);
			}else{
				String sql = " INSERT INTO productrelainfo (productid,Preferential,StartDate,EndDate) VALUES  ('"+productId+"','"+Preferential+"','"+StartDate+"','"+EndDate+"') ";
				QueryBuilder qb = new QueryBuilder(sql);
				trans.add(qb);
			}
			boolean flag = trans.commit();
			
			if(flag){
				Response.setStatus(1);
				Response.setMessage("限时产品设置成功!");
			}else{
				Response.setStatus(0);
				Response.setMessage("限时产品设置失败!");
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
		}
	}
	/**
	 * 判断主站是否是限时优惠产品
	 */
	public void isLimitProduct() {
		try {
			String productId = $V("IDs");
			if (StringUtil.isEmpty(productId)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}
			if (!StringUtil.checkID(productId)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			String sql = " SELECT BackUp2,RiskCode FROM femrisk  WHERE RiskCode = '"+productId+"';";
			QueryBuilder qb = new QueryBuilder(sql);
			Object value = qb.executeOneValue();
			logger.info("**************************value is {}", value);
			//查到一条记录
			if(value != null && !"".equals(value) && !"0".equals(value)){
				Response.setStatus(1);
			}else{
				Response.setStatus(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("查询失败! 异常原因：" + e.getMessage());
		}
	}

	
	/**
	 * 产品上线
	 */
	public void upProduct() {
		try {
			String ids = $V("IDs");
			//DataTable dt = (DataTable) Request.get("Data");
			System.err.println("*****************ids is "+ids);
			if (StringUtil.isEmpty(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			
			String[] arr = ids.split(",");
			ProductRelaInfoSchema tProductRelaInfoSchema = null;
			Transaction trans = new Transaction();
			String riskcode = "";
			for(int i=0;i<arr.length;i++){
				String cProductId = arr[i];
				tProductRelaInfoSchema = new ProductRelaInfoSchema ();
				tProductRelaInfoSchema.setProductID(cProductId);
				if(tProductRelaInfoSchema.fill()){
					String sql = " UPDATE productrelainfo SET ispublish = 'Y' WHERE productid = "+cProductId+" ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}else{
					String sql = " INSERT INTO productrelainfo (productid,ispublish,MakeDate) VALUES  ('"+cProductId+"','Y','"+new PubFun().getCurrentDate()+"') ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}
				riskcode += "'" + cProductId + "',";
			}
			boolean flag = trans.commit();
			
			if(flag){
				Response.setStatus(1);
				String message = "产品上线成功!";
				message += publishArticle(riskcode);
				Response.setMessage(message);
			}else{
				Response.setStatus(0);
				Response.setMessage("产品上线失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("上线更新失败! 异常原因：" + e.getMessage());
		}
	}
	
	/**
	 * wap站产品上下线需发布对应主站产品的详细页，使手机端客户访问直接跳转wap站
	 * 
	 * @param riskcodes
	 *            产品编码
	 * @return
	 */
	private String publishArticle(String riskcodes) {
		String message = "";
		if (StringUtil.isNotEmpty(riskcodes)) {
			riskcodes = riskcodes.substring(0, riskcodes.length() - 1);
			DataTable dt = new QueryBuilder(
					"select Prop1 from sdsearchrelaproduct where ProductID in ("
							+ riskcodes + ")").executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				String articleIds = "";
				int rowcount = dt.getRowCount();
				for (int i = 0; i < rowcount; i++) {
					articleIds += dt.getString(i, 0) + ",";
				}
				if (StringUtil.isNotEmpty(articleIds)) {
					ZCArticleSet set = new ZCArticleSet();
					articleIds = articleIds.substring(0,
							articleIds.length() - 1);
					set = new ZCArticleSchema()
							.query(new QueryBuilder(" where ID in ("
									+ articleIds + ") and status != ?",
									Article.STATUS_OFFLINE));
					Publisher publisher = new Publisher();
					if (!publisher.publishArticle(set)) {
						message += "发布主站详细页失败！";
					}
					
				}
			}
			
			//清除缓存start
			QueryBuilder qb_cache = new  QueryBuilder("SELECT code FROM fdcode WHERE codetype='ERiskType'");
			
			DataTable dt_catch = qb_cache.executeDataTable();
			for(DataRow dr : dt_catch){ 
				String riskType = dr.getString(0);
				if(StringUtil.isNotEmpty(riskType)&&riskType.length()>0){
					Set<String>  keySet = JedisCommonUtil.keys(1,riskType.charAt(0)+"*");
					String[] keyArray = null;
					if(keySet!=null&&keySet.size()>0){
					keyArray =   (String[]) keySet.toArray(new String[keySet.size()]);
					logger.info("wap站产品管理上下线清缓存{}", keyArray.toString());
					JedisCommonUtil.remove(1,keyArray);
					}
					 keySet = JedisCommonUtil.keys(2,riskType.charAt(0)+"*");
					 if(keySet!=null&&keySet.size()>0){
					 keyArray =   (String[]) keySet.toArray(new String[keySet.size()]); 
					 JedisCommonUtil.remove(2,keyArray);
					}	 
				}
			}
			//b2bapp版本升级
			updateB2bAppVersion();
	       //清除缓存end	
		}
		
		return message;
	}
	
	/**
	 * 产品下线
	 */
	public void downProduct() {
		try {
			String ids = $V("IDs");
			if (StringUtil.isEmpty(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			
			String[] arr = ids.split(",");
			ProductRelaInfoSchema tProductRelaInfoSchema = null;
			Transaction trans = new Transaction();
			String riskcode = "";
			for(int i=0;i<arr.length;i++){
				String cProductId = arr[i];
				tProductRelaInfoSchema = new ProductRelaInfoSchema ();
				tProductRelaInfoSchema.setProductID(cProductId);
				if(tProductRelaInfoSchema.fill()){
					String sql = " UPDATE productrelainfo SET ispublish = 'N' WHERE productid = "+cProductId+" ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}else{
					String sql = " INSERT INTO productrelainfo (productid,ispublish,MakeDate) VALUES  ('"+cProductId+"','N','"+new PubFun().getCurrentDate()+"') ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}
				riskcode += "'" + cProductId + "',";
			}
			boolean flag = trans.commit();
			
			if(flag){
				Response.setStatus(1);
				String message = "产品下线成功!";
				message += publishArticle(riskcode);
				Response.setMessage(message);
			}else{
				Response.setStatus(0);
				Response.setMessage("产品下线失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("下线更新失败! 异常原因：" + e.getMessage());
		}
	}
	/**
	 * 产品推荐
	 */
	public void recommendProduct() {
		try {
			String ids = $V("IDs");
			if (StringUtil.isEmpty(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			
			String[] arr = ids.split(",");
			ProductRelaInfoSchema tProductRelaInfoSchema = null;
			Transaction trans = new Transaction();
			for(int i=0;i<arr.length;i++){
				String cProductId = arr[i];
				tProductRelaInfoSchema = new ProductRelaInfoSchema ();
				tProductRelaInfoSchema.setProductID(cProductId);
				if(tProductRelaInfoSchema.fill()){
					String sql = " UPDATE productrelainfo SET RecommendFlag = 'Y' WHERE productid = "+cProductId+" ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}else{
					String sql = " INSERT INTO productrelainfo (productid,RecommendFlag,MakeDate) VALUES  ('"+cProductId+"','Y','"+new PubFun().getCurrentDate()+"') ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}
			}
			boolean flag = trans.commit();
			
			if(flag){
				Response.setStatus(1);
				Response.setMessage("产品推荐成功!");
			}else{
				Response.setStatus(0);
				Response.setMessage("产品推荐失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("产品推荐失败! 异常原因：" + e.getMessage());
		}
	}
	
	/**
	 * 产品推荐取消
	 */
	public void cancelRecommendProduct() {
		try {
			String ids = $V("IDs");
			if (StringUtil.isEmpty(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			String[] arr = ids.split(",");
			ProductRelaInfoSchema tProductRelaInfoSchema = null;
			Transaction trans = new Transaction();
			for(int i=0;i<arr.length;i++){
				String cProductId = arr[i];
				tProductRelaInfoSchema = new ProductRelaInfoSchema ();
				tProductRelaInfoSchema.setProductID(cProductId);
				if(tProductRelaInfoSchema.fill()){
					String sql = " UPDATE productrelainfo SET RecommendFlag = 'N' WHERE productid = "+cProductId+" ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}else{
					String sql = " INSERT INTO productrelainfo (productid,RecommendFlag,MakeDate) VALUES  ('"+cProductId+"','N','"+new PubFun().getCurrentDate()+"') ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}
			}
			boolean flag = trans.commit();
			
			if(flag){
				Response.setStatus(1);
				Response.setMessage("取消产品推荐成功!");
			}else{
				Response.setStatus(0);
				Response.setMessage("取消产品推荐失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("取消产品推荐失败! 异常原因：" + e.getMessage());
		}
	}
	
	/**
	 * 产品热销
	 */
	public void hotProduct() {
		try {
			String ids = $V("IDs");
			if (StringUtil.isEmpty(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			
			String[] arr = ids.split(",");
			ProductRelaInfoSchema tProductRelaInfoSchema = null;
			Transaction trans = new Transaction();
			for(int i=0;i<arr.length;i++){
				String cProductId = arr[i];
				tProductRelaInfoSchema = new ProductRelaInfoSchema ();
				tProductRelaInfoSchema.setProductID(cProductId);
				if(tProductRelaInfoSchema.fill()){
					String sql = " UPDATE productrelainfo SET HotFlag = 'Y' WHERE productid = "+cProductId+" ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}else{
					String sql = " INSERT INTO productrelainfo (productid,HotFlag,MakeDate) VALUES  ('"+cProductId+"','Y','"+new PubFun().getCurrentDate()+"') ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}
			}
			boolean flag = trans.commit();
			
			if(flag){
				Response.setStatus(1);
				Response.setMessage("产品热销成功!");
			}else{
				Response.setStatus(0);
				Response.setMessage("产品热销失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("产品热销失败! 异常原因：" + e.getMessage());
		}
	}
	/**
	 * 产品热销取消
	 */
	public void cancelHotProduct() {
		try {
			String ids = $V("IDs");
			if (StringUtil.isEmpty(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			String[] arr = ids.split(",");
			ProductRelaInfoSchema tProductRelaInfoSchema = null;
			Transaction trans = new Transaction();
			for(int i=0;i<arr.length;i++){
				String cProductId = arr[i];
				tProductRelaInfoSchema = new ProductRelaInfoSchema ();
				tProductRelaInfoSchema.setProductID(cProductId);
				if(tProductRelaInfoSchema.fill()){
					String sql = " UPDATE productrelainfo SET HotFlag = 'N' WHERE productid = "+cProductId+" ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}else{
					String sql = " INSERT INTO productrelainfo (productid,HotFlag,MakeDate) VALUES  ('"+cProductId+"','N','"+new PubFun().getCurrentDate()+"') ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}
			}
			boolean flag = trans.commit();
			
			if(flag){
				Response.setStatus(1);
				Response.setMessage("取消产品热销成功!");
			}else{
				Response.setStatus(0);
				Response.setMessage("取消产品热销失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("取消产品热销失败! 异常原因：" + e.getMessage());
		}
	}
	/**
	 * 产品热销
	 */
	@SuppressWarnings("static-access")
	public void topProduct() {
		try {
			String ids = $V("IDs");
			if (StringUtil.isEmpty(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			
			String[] arr = ids.split(",");
			ProductRelaInfoSchema tProductRelaInfoSchema = null;
			Transaction trans = new Transaction();
			for(int i=0;i<arr.length;i++){
				String cProductId = arr[i];
				tProductRelaInfoSchema = new ProductRelaInfoSchema ();
				tProductRelaInfoSchema.setProductID(cProductId);
				if(tProductRelaInfoSchema.fill()){
					String sql = " UPDATE productrelainfo SET Prop1 = 'Y',Modifydate = '"+new PubFun().getCurrent()+"' WHERE productid = "+cProductId+" ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}else{
					String sql = " INSERT INTO productrelainfo (productid,Prop1,MakeDate) VALUES  ('"+cProductId+"','Y','"+new PubFun().getCurrentDate()+"') ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}
			}
			boolean flag = trans.commit();
			
			if(flag){
				Response.setStatus(1);
				Response.setMessage("产品精选成功!");
			}else{
				Response.setStatus(0);
				Response.setMessage("产品精选失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("产品精选失败! 异常原因：" + e.getMessage());
		}
	}
	/**
	 * 产品热销取消
	 */
	@SuppressWarnings("static-access")
	public void cancelTopProduct() {
		try {
			String ids = $V("IDs");
			if (StringUtil.isEmpty(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			String[] arr = ids.split(",");
			ProductRelaInfoSchema tProductRelaInfoSchema = null;
			Transaction trans = new Transaction();
			for(int i=0;i<arr.length;i++){
				String cProductId = arr[i];
				tProductRelaInfoSchema = new ProductRelaInfoSchema ();
				tProductRelaInfoSchema.setProductID(cProductId);
				if(tProductRelaInfoSchema.fill()){
					String sql = " UPDATE productrelainfo SET Prop1 = NULL,Modifydate = NULL WHERE productid = "+cProductId+" ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}else{
					String sql = " INSERT INTO productrelainfo (productid,Prop1,MakeDate) VALUES  ('"+cProductId+"','N','"+new PubFun().getCurrentDate()+"') ";
					QueryBuilder qb = new QueryBuilder(sql);
					trans.add(qb);
				}
			}
			boolean flag = trans.commit();
			
			if(flag){
				Response.setStatus(1);
				Response.setMessage("取消产品精选成功!");
			}else{
				Response.setStatus(0);
				Response.setMessage("取消产品精选失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("取消产品精选失败! 异常原因：" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * ObtainNav:(缓存清空 ). <br/>
	 *
	 * @param param
	 * @return
	 */
	public void cleanCache(){
		try {
			/** 首页轮播图的缓存标签  added by fangdz*/
			String homeimageplayerAliasKey = Constant.HOMEIMAGEPLAYER;
			/** 首页每日优惠的缓存标签  added by fangdz*/
			String wapHomePromotionProductAliasKey = Constant.WAPHOMEPROMOTIONPRODUCT;
			/** 首页轮导航图标的缓存标签  added by fangdz*/
			String wapHomeNavListAliasKey = Constant.WAPHOMENAVLIST;
			boolean homeImagePlayerRm = JedisCommonUtil.remove(1, homeimageplayerAliasKey);
			boolean wapHomePromotionProductRm = JedisCommonUtil.remove(1, wapHomePromotionProductAliasKey);
			boolean wapHomeNavListRm = JedisCommonUtil.remove(1, wapHomeNavListAliasKey);
	
			if(homeImagePlayerRm && wapHomePromotionProductRm && wapHomeNavListRm){
				Response.setMessage("缓存清理成功! ");
				
			}else{
				Response.setMessage("缓存清理失败!");
			}			
		} catch (Exception e) {
			logger.error("类CleanCache执行方法发生异常！" + e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("缓存清理失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 调整顺序
	 */
	public void setSortNum() {
		DataTable dt = (DataTable) Request.get("DT");
		ProductRelaInfoSet set = new ProductRelaInfoSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ProductRelaInfoSchema schema = new ProductRelaInfoSchema();
			schema.setProductID(dt.getString(i, "id"));// 根据页面选中的主键ID，从库中选出本条记录
			schema.fill();
			schema.setProp2(dt.getString(i, "prop2")); // 页面本条记录的修改值填入相应位置

			set.add(schema);
		}
		if (set.update()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}

	}
	
	/**
	 * 调整顺序
	 */
	public void setPictureSortNum() {
		DataTable dt = (DataTable) Request.get("DT");
		WapPictureUrlSet set = new WapPictureUrlSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			WapPictureUrlSchema schema = new WapPictureUrlSchema();
			schema.setID(dt.getString(i, "ID"));// 根据页面选中的主键ID，从库中选出本条记录
			schema.fill();
			schema.setOrderFlag(dt.getString(i, "orderFlag")); // 页面本条记录的修改值填入相应位置

			set.add(schema);
		}
		if (set.update()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}
	
	/**
	 * 更新b2bapp版本
	 * @return
	 */
	private boolean updateB2bAppVersion(){
		QueryBuilder qb_cache = new  QueryBuilder("select codevalue from  zdcode where codetype ='b2b_app_version'");
		String version = String.valueOf(qb_cache.executeOneValue());
		String prefix="";
		String suffix="";
		int suffixNum=0;
		
		if(version.indexOf(".")>0){
		 prefix = version.substring(0, version.indexOf(".")+1); 
		 suffix = version.substring(version.indexOf(".")+1);
		 try{
				suffixNum = Integer.parseInt(suffix)+1;	
				suffix = suffixNum+"";
			}catch(Exception e){
				suffix="99";
						
			}
		}
		else{
			prefix=version;
			suffix=".99"; 
		}
		
		
		version = prefix+suffix; 
		String updateVersion = "update zdcode set codevalue='"+version+"' where codetype ='b2b_app_version'"; 
		QueryBuilder qb_update = new  QueryBuilder(updateVersion); 
		try {
			qb_update.executeNoQuery();
		} catch (Exception e) {
			logger.error("wap站产品管理更新版本错误"+e.getMessage(), e);
		} 
		return true;
	}
	
	
	/**
	 * 上传图片
	 */
	public void uploadPicture() {
		String ServerPath = Config.getContextRealPath()+"/wwwroot/kxb";
		String pictureFormat =  $V("pictureFormat");
		String productId =  $V("productId");
		String base64 = $V("imgBase64");
		base64 = base64.substring(base64.indexOf(",")+1,base64.length());
		String thistime = DateUtil.toString(new Date(), "yyyy/MM/dd/");
		String path = "/upload/WapPicture/"+thistime ;
		String pathName = DateUtil.toString(new Date(), "yyyyMMddHHmmss")+pictureFormat;
		String setType =  $V("type"); 
		if("01".equals(setType)){
			int COUNT = new  QueryBuilder(" SELECT COUNT(PictureUrl) FROM wapPictureUrl WHERE RiskCode = '"+productId+"' AND TYPE='01'  ").executeInt();
			if(COUNT>0){
				Response.setStatus(0);
				Response.setMessage("目前不支持多张头图!");
				return;
			}
		}
		 try {
			base64ToFile(base64,ServerPath , path,pathName,productId,setType);
		} catch (Exception e) {
			Response.setStatus(0);
			Response.setMessage("发生错误:"+e.toString());
			e.printStackTrace();
		}
		
		Response.setStatus(1);
		Response.setMessage("上传成功");

		
  

	}
	
	/**
	 * 上传头图逻辑
	 */
	public void base64ToFile(String base64, String ServerPath ,String path,String pathName,String productId ,String setType )throws Exception{		
		File dirname = new File(ServerPath+path); 
		if (!dirname.isDirectory()){ //目录不存在 
		     dirname.mkdirs();
		} 
		
		BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] bytes1 = decoder.decodeBuffer(base64);
        OutputStream out=new FileOutputStream(ServerPath+path+pathName);
        out.write(bytes1);
        out.flush();
        out.close();    
        OSSUploadFile.uploadFile(ServerPath+path+pathName);
        
        WapPictureUrlSchema wapPictureUrlSchema = new WapPictureUrlSchema();
        wapPictureUrlSchema.setID(NoUtil.getMaxNo("WapPictureUrl").toString());
        wapPictureUrlSchema.setCreateDate(new Date());
        wapPictureUrlSchema.setRiskCode(productId);
        wapPictureUrlSchema.setType(setType);
        wapPictureUrlSchema.setPictureUrl(Config.getValue("StaticResourcePath")+path+pathName);
        wapPictureUrlSchema.setOrderFlag(System.currentTimeMillis());
        wapPictureUrlSchema.setProp1("");
        wapPictureUrlSchema.setProp2("");
        wapPictureUrlSchema.setProp3("");
        wapPictureUrlSchema.setProp4("");
        wapPictureUrlSchema.setProp5("");
        wapPictureUrlSchema.setProp6("");
        wapPictureUrlSchema.setProp7("");
        wapPictureUrlSchema.insert();
	}
	
	/**
	 * 初始化wappicture页面
	 * 
	 * @param params
	 * @return
	 */
		public void initWapPicture() {
			String productId =  $V("productId");
			String picture = new  QueryBuilder(" SELECT PictureUrl FROM wapPictureUrl WHERE RiskCode = '"+productId+"' AND TYPE='HeadPicture'  ").executeString();
			if(StringUtil.isEmpty(picture)){
				Response.setStatus(0);
				return;
			}
			Response.setStatus(1);
			Response.setMessage(picture);
		}
	
		/**
		 * 初始化wappicture_cpts页面
		 * 
		 * @param params
		 * @return
		 */
			public void initWapPicture_CPTS() {
				String productId =  $V("productId");
				String PictureUrls = ""; 
				QueryBuilder qb = new QueryBuilder(" SELECT PictureUrl FROM wapPictureUrl WHERE RiskCode = '"+productId+"' AND TYPE='ChanPinTeSe'  ");
				DataTable dt = qb.executeDataTable();
				if(dt != null && dt.getRowCount() > 0){
					for(int i = 0 ; i < dt.getRowCount();i++){
						if(StringUtil.isEmpty(PictureUrls)){
							PictureUrls = dt.getString(i, "PictureUrl");
						}else{
							PictureUrls = PictureUrls + "______AND______"+ dt.getString(i, "PictureUrl");
						}
					}
				}
				if(StringUtil.isEmpty(PictureUrls)){
					Response.setStatus(0);
					return;
				}
				Response.setStatus(1);
				Response.setMessage(PictureUrls);
			}
			
	public static void main(String[] args) {
		
		
//		String sql = " SELECT BackUp2,RiskCode FROM femrisk  WHERE RiskCode = '1111111111';";
//		QueryBuilder qb = new QueryBuilder(sql);
//		Object value = qb.executeOneValue();
//	
//		System.out.println("**************************value is "+value);
		
//		Mapx mapx = new Mapx();
//		mapx.put("productId", 101501009);
//		System.err.println(initLimitProduct(mapx));
		
		
		System.err.println(true&&false);
		
	}

}
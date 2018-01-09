package com.sinosoft.cms.document;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDPromotionDetailModuleSchema;
import com.sinosoft.schema.SDPromotionDetailProductSchema;
import com.sinosoft.schema.SDPromotionHomePageActivitySchema;
import com.sinosoft.schema.SDPromotionHomePageModuleSchema;
import com.sinosoft.schema.SDPromotionHomePageSellingBrandSchema;
import com.sinosoft.schema.SDPromotionHomePageSpecialProductSchema;
import com.sinosoft.schema.SDPromotionProductHighlightsSchema;
import com.sinosoft.schema.ZCImageSchema;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
* @ClassName: PromotionManage 
* @Description: TODO(促销频道管理) 
* @author XXX 
* @date 2015年7月9日 下午2:39:46 
*
* <p> 修改历史</p>
* <p> 序号 日期 修改人 修改原因</p>
 */

public class PromotionManage extends Page {
	/**
	 * 
	* @Title: dg1DataBind 
	* @Description: TODO(初始化查询模板列表) 
	* @return void    返回类型 
	* @author
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String CatalogID=Config.getValue("PromotionCatalogID");
		String title = String.valueOf(dga.getParams().get("title"));
		String startDate = String.valueOf(dga.getParams().get("StartDate"));
		String endDate = String.valueOf(dga.getParams().get("EndDate"));
		QueryBuilder qb = new QueryBuilder("select ID,Attribute,Title,URL,PublishDate,Status,Author from ZCArticle where CatalogID=?");
		qb.add(CatalogID);
		if (StringUtil.isNotEmpty(title)) {
			qb.append(" and title like ? ", "%" + title.trim() + "%");
		}
		if (StringUtil.isNotEmpty(startDate)) {
			startDate += " 00:00:00";
			qb.append(" and publishdate >= ? ", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			endDate += " 23:59:59";
			qb.append(" and publishdate <= ? ", endDate);
		}
		qb.append(" and status=?", Article.STATUS_PUBLISHED);
		qb.append(" order by publishdate desc");
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dt.getDataColumn("PublishDate").setDateFormat("yy-MM-dd HH:mm");
		}
		dt.insertColumn("Icon");
		String url = new QueryBuilder("select URL from ZCsite").executeOneValue() + "/";
		dt.insertColumn("HeadURL");
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (StringUtil.isNotEmpty(url)) {
					dt.set(i, "HeadURL", url);
				}
			}
		}
		dga.bindData(dt);
	}
	/**
	 * 
	* @Title: dg1ModelDataBind 
	* @Description: TODO(模块信息查询) 
	* @return void    返回类型 
	* @author
	 */
	public static void dg1ModelDataBind(DataGridAction dga) {
		String articleid=(String)dga.getParams().get("articleid");
		QueryBuilder qb = new QueryBuilder("select * from SDPromotionDetailModule where DocumentId=?");
		qb.add(articleid);
		String ModuleName = String.valueOf(dga.getParams().get("ModuleName"));
		if (StringUtil.isNotEmpty(ModuleName)) {
			qb.append(" and ModuleName like ?", "%" + ModuleName.trim() + "%");
		}
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		
		dt.decodeColumn("ModuleType",CacheManager.getMapx("Code", "PromotionDetailType"));
		dt.decodeColumn("ModuleTheme",CacheManager.getMapx("Code", "ModuleTheme"));
		dga.bindData(dt);
	}
	/**
	 * 
	* @Title: initDialog 
	* @Description: TODO(新建、编辑模块初始化数据) 
	* @return Mapx<String,String>    返回类型 
	* @author
	 */
	public static Mapx<String, String> initDialog(Mapx<String, String> params) {
		params.put("ModuleType",HtmlUtil.codeToOptions("PromotionDetailType", true));//模块类别
		params.put("ModuleTheme",HtmlUtil.codeToOptions("ModuleTheme", true));//模块主题
		String ID = params.getString("ID");
		if(StringUtil.isNotEmpty(ID)){
			QueryBuilder qb = new QueryBuilder("SELECT   *   FROM  SDPromotionDetailModule  where id=?");
			qb.add(ID);
			DataTable dt = qb.executeDataTable();
			DataRow dr = dt.getDataRow(0);
			for (int i = 0; i < dr.getColumnCount(); i++) {
				String ColumnName=dr.getDataColumn(i).getColumnName();
				if("ModuleType".equals(ColumnName)){
					params.put("ModuleTypeName", dr.getString("ModuleType"));
				}else if("ModuleTheme".equals(ColumnName)){
					params.put("ModuleThemeName", dr.getString("ModuleTheme"));
				}else{
					params.put(ColumnName, dr.getString(ColumnName));
				}
			}
			DataTable dt_Product = new QueryBuilder("SELECT   productid   FROM  SDPromotionDetailProduct  where moduleid=?",ID).executeDataTable();
			StringBuffer products=new StringBuffer("");
			for (int i = 0; i <dt_Product.getRowCount(); i++) {
				products.append(dt_Product.getString(i,0)+",");
			}
			if(products.length()>0){
				products.deleteCharAt(products.length()-1);
			}
			params.put("products",products.toString());
		}
		return params;
	}
	/**
	 * 
	* @Title: initProductDialog 
	* @Description: TODO(编辑模板 初始化查询产品信息) 
	* @return Mapx<String,String>    返回类型 
	* @author
	 */
	public static Mapx<String, String> initProductDialog(Mapx<String, String> params) {
		String ID = params.getString("ID");
		params.put("productnum","0");
		if(StringUtil.isNotEmpty(ID)){
			DataTable dt_Product = new QueryBuilder("SELECT   productid   FROM  SDPromotionDetailProduct  where moduleid=?",ID).executeDataTable();
			StringBuffer products=new StringBuffer("");
			for (int i = 0; i <dt_Product.getRowCount(); i++) {
				products.append(dt_Product.getString(i,0)+",");
			}
			if(products.length()>0){
				products.deleteCharAt(products.length()-1);
			}
			params.put("products",products.toString());
			params.put("productnum",dt_Product.getRowCount()+"");
		}
		return params;
	}
	/**
	 * 
	* @Title: saveModel 
	* @Description: TODO(保存模块信息) 
	* @return void    返回类型 
	* @author
	 */
	public void saveModel() {
		//事务创建
		Transaction trans = new Transaction();
		SDPromotionDetailModuleSchema  SDPromotionDetailModule=new SDPromotionDetailModuleSchema();
		String ModelStatusFlag=Request.getString("ModelStatusFlag");
		//促销模板页
		String articleid=Request.getString("articleid");
		SDPromotionDetailModule.setDocumentId(articleid);
//		String productID=Request.getString("productID");
//		if(StringUtil.isEmpty(productID)){
//			Response.setStatus(0);
//			Response.setMessage("还没有选择归属于该模块的产品！");
//			return;
//		}
		try {
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			//编辑
			if("edit".equals(ModelStatusFlag)){
				SDPromotionDetailModule.setId(Request.getString("ID"));
				if(SDPromotionDetailModule.fill()){
					SDPromotionDetailModule.setValue(Request);
					SDPromotionDetailModule.setModifyUser(User.getUserName());
					SDPromotionDetailModule.setModifyDate(date);
					trans.add(SDPromotionDetailModule,Transaction.UPDATE);
//					//保存模块产品信息
//					this.saveProductInfo(trans, SDPromotionDetailModule.getId(), productID.split(","), date);
				}else{
					Response.setStatus(0);
					Response.setMessage("保存模块初始化查询失败！");
					return;
				}
			}else{//新增
				SDPromotionDetailModule.setValue(Request);
				SDPromotionDetailModule.setId(NoUtil.getMaxNo("PromotionModelID", 11));
				SDPromotionDetailModule.setCreateUser(User.getUserName());
				SDPromotionDetailModule.setCreateDate(date);
				SDPromotionDetailModule.setOrderFlag(System.currentTimeMillis());
				trans.add(SDPromotionDetailModule,Transaction.INSERT);
//				//保存模块产品信息
//				this.saveProductInfo(trans, SDPromotionDetailModule.getId(), productID.split(","), date);
			}
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("模块保存成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("模块保存失败！");
				return;
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
	}
//	/**
//	 * 
//	* @Title: saveProductInfo 
//	* @Description: TODO(保存模块产品信息) 
//	* @return void    返回类型 
//	* @author
//	 */
//	private void saveProductInfo(Transaction trans,String ModuleId,String[] productid,Date createdate){
//		DataTable dt_pruduct=new QueryBuilder("select productid  from  SDPromotionDetailProduct where ModuleId=?",ModuleId).executeDataTable();
//		StringBuffer product_del=new StringBuffer("not in (");
//		for (int i = 0; i < productid.length; i++) {
//			//剔除已保存的产品信息
//			for (int j = 0; j < dt_pruduct.getRowCount(); j++) {
//				if(productid[i].equals(dt_pruduct.getString(j,0))){
//					break;
//				}
//			}
//			SDPromotionDetailProductSchema SDPromotionDetailProduct=new SDPromotionDetailProductSchema();
//			SDPromotionDetailProduct.setId(NoUtil.getMaxNo("PromotionProductID", 11));
//			SDPromotionDetailProduct.setProductId(productid[i]);
//			SDPromotionDetailProduct.setModuleId(ModuleId);
//			SDPromotionDetailProduct.setOrderFlag(System.currentTimeMillis());
//			SDPromotionDetailProduct.setCreateUser(User.getUserName());
//			SDPromotionDetailProduct.setCreateDate(createdate);
//			trans.add(SDPromotionDetailProduct,Transaction.INSERT);
//			product_del.append("'"+productid[i]+"',");
//		}
//		product_del.deleteCharAt(product_del.length()-1).append(")");
//		trans.add(new QueryBuilder ("delete from SDPromotionDetailProduct where ModuleId=? and productid "+product_del.toString(),ModuleId));
//	}
	
	/**
	 * 
	* @Title: saveModel 
	* @Description: TODO(保存模块顺序) 
	* @return void    返回类型 
	* @author
	 */
	public void saveModelIndex() {
		DataTable dt = (DataTable) Request.get("DT");
		if (dt == null || dt.getRowCount() == 0) {
			Response.setStatus(0);
			Response.setMessage("数据未修改!");
			return;
		}
		Transaction trans = new Transaction();
		for (int j = 0; j < dt.getRowCount(); j++) {
			String id=dt.getString(j, "ID");
			String OrderFlag=dt.getString(j, "OrderFlag");
			String ModuleName=dt.getString(j, "ModuleName");
			if(!StringUtil.isLong(OrderFlag)){
				Response.setStatus(0);
				Response.setMessage(ModuleName+"的次序值有误，只能为正整数!");
				return;
			}else{
				SDPromotionDetailModuleSchema SDPromotionDetailModule=new SDPromotionDetailModuleSchema();
				SDPromotionDetailModule.setId(id);
				if(SDPromotionDetailModule.fill()){
					SDPromotionDetailModule.setOrderFlag(OrderFlag);
					SDPromotionDetailModule.setModifyUser(User.getUserName());
					// 定义时间格式
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						Date date = sdf.parse(PubFun.getCurrent());
						SDPromotionDetailModule.setModifyDate(date);
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
					}
					trans.add(SDPromotionDetailModule,Transaction.UPDATE);
				}
			}
		}
		if(!trans.commit()){
			Response.setStatus(1);
			Response.setMessage("模块顺序保存成功！");
			return;
		}
	}
	/**
	 * 
	* @Title: deleteModel 
	* @Description: TODO(删除模块) 
	* @return void    返回类型 
	* @author
	 */
	public void deleteModel() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDs = ids.split(",");
		Transaction trans = new Transaction();
		StringBuffer PromotionDetail=new StringBuffer(" in (");
		for (int n = 0; n < IDs.length; n++) {
			SDPromotionDetailModuleSchema SDPromotionDetailModule = new SDPromotionDetailModuleSchema();
			SDPromotionDetailModule.setId(IDs[n]);
			if (SDPromotionDetailModule.fill()) {
				trans.add(SDPromotionDetailModule, Transaction.DELETE);
				PromotionDetail.append("'"+SDPromotionDetailModule.getId()+"',");
			} else {
				Response.setLogInfo(1, "无效主键数据!");
				logger.error("删除促销频道模块发生错误,模块ID为:{}", IDs[n]);
			}
		}
		PromotionDetail.deleteCharAt(PromotionDetail.length()-1).append(")");
		trans.add(new QueryBuilder("delete from  SDPromotionProductHighlights where DetailId in (select id from  SDPromotionDetailProduct where ModuleId "+PromotionDetail.toString()+")"));
		trans.add(new QueryBuilder("delete from  SDPromotionDetailProduct where ModuleId "+PromotionDetail.toString()));
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}
	/**
	 * 
	 * @Title: dg1DataBindProduct
	 * @Description: TODO(初始化产品)
	 * @return void 返回类型
	 * @author 
	 */
	public static void dg1DataBindProduct(DataGridAction dga) {
		String ID = (String) dga.getParams().get("ID");
		if(StringUtil.isNotEmpty(ID)){
			String productname = (String) dga.getParams().get("productname");
			QueryBuilder qb_product = new QueryBuilder("SELECT pro.id as id ,pro.productid as  productid,pro.ProductName as ProductName,prod.IsPublish as IsPublish,pro.OrderFlag as OrderFlag FROM SDPromotionDetailProduct pro left join  sdproduct prod on pro.productid=prod.productid  WHERE   pro.ModuleId=? ",ID);
			if (StringUtil.isNotEmpty(productname)) {
				qb_product.append(" and pro.productname like "+"'%"+ productname.trim() + "%'");
			}
			qb_product.append(" ORDER BY prod.productid ");
			qb_product.append(dga.getSortString());
			dga.setTotal(qb_product);
			DataTable dt = qb_product.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
			Transaction trans=new Transaction();
			for (int i = 0; i < dt.getRowCount(); i++) {
				if(!"Y".equals(dt.getString(i, "IsPublish").trim())){
					trans.add(new QueryBuilder("delete from SDPromotionProductHighlights where DetailId in (select id from SDPromotionDetailProduct where id=?)",dt.getString(i, "id")));
					trans.add(new QueryBuilder("delete from SDPromotionDetailProduct where id=?",dt.getString(i, "id")));
				}
			}
			dga.bindData(dt);
			trans.commit();
		}else{
			dga.bindData(new DataTable());
		}
	}
	/**
	 * 
	 * @Title: dg1DataBindProduct
	 * @Description: TODO(初始化产品)
	 * @return void 返回类型
	 * @author 
	 */
	public static void dg1DataBindAllProduct(DataGridAction dga) {
		String productname = (String) dga.getParams().get("productname");
		QueryBuilder qb_product = new QueryBuilder("SELECT productid as  id,productname FROM    sdproduct    WHERE  IsPublish='Y' ");
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
	/**
	 * 
	* @Title: addProduct 
	* @Description: TODO(添加产品) 
	* @return void    返回类型 
	* @author
	 */
	public void addProduct() {
		String productID=Request.getString("productID");
		if(StringUtil.isEmpty(productID)){
			Response.setStatus(0);
			Response.setMessage("没有勾选任何产品!");
			return;
		}
		Transaction trans = new Transaction();
		String ModelID=Request.getString("ModelID");
		DataTable dt_product =new QueryBuilder("select pro.productid as productid,sdpro.productname as productname from SDPromotionDetailProduct pro left join sdproduct sdpro on pro.productid=sdpro.productid  where ModuleId=?",ModelID).executeDataTable();
		String [] product_array=productID.split(",");
		StringBuffer product_str=new StringBuffer("");
		for (int i = 0; i < product_array.length; i++) {
			product_str.append("'"+product_array[i]+"',");
		}
		product_str.deleteCharAt(product_str.length()-1);
		DataTable dt_pro =new QueryBuilder("select productid,productname,HtmlPath  from sdproduct where productid in ("+product_str+")").executeDataTable();
		for (int i = 0; i < dt_pro.getRowCount(); i++) {
			for (int j = 0; j < dt_product.getRowCount(); j++) {
				if(dt_pro.getString(i,0).equals(dt_product.getString(j,0))){
					Response.setStatus(0);
					Response.setMessage("产品编码为："+dt_product.getString(j,0)+"的产品已添加，请选择其他产品!");
					return;
				}
			}
			SDPromotionDetailProductSchema SDPromotionDetailProduct=new SDPromotionDetailProductSchema();
			SDPromotionDetailProduct.setId(NoUtil.getMaxNo("PromotionProductID", 11));
			SDPromotionDetailProduct.setProductId(dt_pro.getString(i,0));
			SDPromotionDetailProduct.setProductName(dt_pro.getString(i,1));
			SDPromotionDetailProduct.setBuyURL(dt_pro.getString(i,2));
			SDPromotionDetailProduct.setModuleId(ModelID);
			SDPromotionDetailProduct.setOrderFlag(System.currentTimeMillis());
			SDPromotionDetailProduct.setCreateUser(User.getUserName());
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date date = sdf.parse(PubFun.getCurrent());
				SDPromotionDetailProduct.setCreateDate(date);
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
			trans.add(SDPromotionDetailProduct,Transaction.INSERT);
		}
		if(trans.commit()){
			Response.put("productnum",dt_product.getRowCount()+product_array.length);
			Response.setStatus(1);
			Response.setMessage("产品添加成功！");
			return;
		}else{
			Response.setStatus(0);
			Response.setMessage("产品添加失败！");
			return;
		}
	}
	/**
	 * 
	* @Title: deleteModelProduct 
	* @Description: TODO(删除模块产品) 
	* @return void    返回类型 
	* @author
	 */
	public void deleteModelProduct() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDs = ids.split(",");
		Transaction trans = new Transaction();
		StringBuffer Highlights=new StringBuffer("in (");
		String  moduleid="";
		for (int n = 0; n < IDs.length; n++) {
			SDPromotionDetailProductSchema SDPromotionDetailProduct = new SDPromotionDetailProductSchema();
			SDPromotionDetailProduct.setId(IDs[n]);
			if (SDPromotionDetailProduct.fill()) {
				moduleid=SDPromotionDetailProduct.getModuleId();
				trans.add(SDPromotionDetailProduct, Transaction.DELETE);
				Highlights.append("'"+SDPromotionDetailProduct.getId()+"',");
			} else {
				Response.setLogInfo(1, "无效主键数据!");
				logger.error("删除促销频道相关产品发生错误,模块ID为:{}", IDs[n]);
				return;
			}
		}
		Highlights.deleteCharAt(Highlights.length()-1).append(")");
		trans.add(new QueryBuilder("delete from  SDPromotionProductHighlights where DetailId "+Highlights.toString()));
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
			String  num = new QueryBuilder("SELECT   count(1)   FROM  SDPromotionDetailProduct  where moduleid=?",moduleid).executeString();
			if(StringUtil.isEmpty(num)){
				num="0";
			}
			Response.put("productnum",num);
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}
	/**
	 * 
	* @Title: saveProductInfo 
	* @Description: TODO(保存产品信息) 
	* @return void    返回类型 
	* @author
	 */
	public void saveProductInfo() {
		DataTable dt = (DataTable) Request.get("DT");
		if (dt == null || dt.getRowCount() == 0) {
			Response.setStatus(0);
			Response.setMessage("数据未修改!");
			return;
		}
		Transaction trans = new Transaction();
		for (int j = 0; j < dt.getRowCount(); j++) {
			String id=dt.getString(j, "ID");
			String OrderFlag=dt.getString(j, "OrderFlag");
			String ProductName=dt.getString(j, "ProductName");
			if(!StringUtil.isLong(OrderFlag)){
				Response.setStatus(0);
				Response.setMessage(ProductName+"的次序值有误，只能为正整数!");
				return;
			}else{
				SDPromotionDetailProductSchema SDPromotionDetailProduct=new SDPromotionDetailProductSchema();
				SDPromotionDetailProduct.setId(id);
				if(SDPromotionDetailProduct.fill()){
					SDPromotionDetailProduct.setOrderFlag(OrderFlag);
					SDPromotionDetailProduct.setProductName(ProductName);
					SDPromotionDetailProduct.setModifyUser(User.getUserName());
					try {
						// 定义时间格式
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date date = sdf.parse(PubFun.getCurrent());
						SDPromotionDetailProduct.setModifyDate(date);
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
					}
					trans.add(SDPromotionDetailProduct,Transaction.UPDATE);
				}
			}
		}
		if(!trans.commit()){
			Response.setStatus(1);
			Response.setMessage("模块顺序保存成功！");
			return;
		}
	}
	/**
	 * 
	* @Title: initHighlightsDialog 
	* @Description: TODO(新建、编辑产品亮点初始化数据) 
	* @return Mapx<String,String>    返回类型 
	* @author
	 */
	public static Mapx<String, String> initHighlightsDialog(Mapx<String, String> params) {
		String ID = params.getString("ID");
		if(StringUtil.isNotEmpty(ID)){
			StringBuffer Detail1=new StringBuffer("");
			StringBuffer Detail2=new StringBuffer("");
			DataTable dt = new QueryBuilder("SELECT  ProductId,Detail1,Detail2,OrderFlag,DetailId   FROM  SDPromotionProductHighlights  where DetailId=?",ID).executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++) {
				Detail1.append(dt.getString(i, 1) + ",");
				Detail2.append(dt.getString(i, 2) + ",");
				if(i==0){
					params.put("ProductId",dt.getString(0, 0));
				}
			}
			if(StringUtil.isEmpty(Detail1)){
				params.put("Detail1","");
			}else{
				params.put("Detail1",Detail1.deleteCharAt(Detail1.length()-1).toString());
			}
			if(StringUtil.isEmpty(Detail2)){
				params.put("Detail2","");
			}else{
				params.put("Detail2",Detail2.deleteCharAt(Detail2.length()-1).toString());
			}
			params.put("DetailId",ID);
			DataTable dt_ModuleType = new QueryBuilder("SELECT  ModuleType   FROM  SDPromotionDetailModule  where id in (select ModuleId from SDPromotionDetailProduct where id=?)",ID).executeDataTable();
			params.put("ModuleType",dt_ModuleType.getString(0, 0));
		}
		return params;
	}
	/**
	 * 
	* @Title: setHighlights 
	* @Description: TODO(设置产品亮点) 
	* @return void    返回类型 
	* @author
	 */
	public void setHighlights() {
		String DetailId=Request.getString("DetailId");
		String productId=Request.getString("productId");
		if(StringUtil.isEmpty(DetailId)){
			Response.setStatus(0);
			Response.setMessage("保存亮点信息异常，ModelID："+DetailId+"!");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from SDPromotionProductHighlights   where DetailId=?",DetailId));
		String num = Request.getString("DetailNum");
		String index = Request.getString("index");
		String[] index_array=index.split(",");
		for (int i = 0; i < Integer.parseInt(num); i++) {
			SDPromotionProductHighlightsSchema  SDPromotionProductHighlightsSchema=new SDPromotionProductHighlightsSchema();
			SDPromotionProductHighlightsSchema.setid(NoUtil.getMaxNo("ProHighlightsID", 11));
			SDPromotionProductHighlightsSchema.setProductId(productId);
			//描述1
			String Detail1 = Request.getString("Detail1_" +index_array[i]);
			//描述2
			String Detail2 = Request.getString("Detail2_" +index_array[i]);
			SDPromotionProductHighlightsSchema.setDetail1(Detail1);
			SDPromotionProductHighlightsSchema.setDetail2(Detail2);
			SDPromotionProductHighlightsSchema.setOrderFlag(System.currentTimeMillis());
			SDPromotionProductHighlightsSchema.setDetailId(DetailId);
			SDPromotionProductHighlightsSchema.setCreateUser(User.getUserName());
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date date= sdf.parse(PubFun.getCurrent());
				SDPromotionProductHighlightsSchema.setCreateDate(date);
			} catch (ParseException e) {
				logger.error(e.getMessage(), e);
			}
			trans.add(SDPromotionProductHighlightsSchema,Transaction.INSERT);
		}
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("产品亮点设置成功！");
			return;
		}else{
			Response.setStatus(0);
			Response.setMessage("产品亮点设置失败！");
			return;
		}
	}
	/**
	 * 
	* @Title: dg1DataBind 
	* @Description: TODO(初始化查询模板列表) 
	* @return void    返回类型 
	* @author
	 */
	public static void dg1DataBindHomepage(DataGridAction dga) {
		String title = String.valueOf(dga.getParams().get("title"));
		String startDate = String.valueOf(dga.getParams().get("StartDate"));
		String endDate = String.valueOf(dga.getParams().get("EndDate"));
		QueryBuilder qb = new QueryBuilder("select * from SDPromotionHomePageModule where 1=1");
		if (StringUtil.isNotEmpty(title)) {
			qb.append(" and ModuleName like ? ", "%" + title.trim() + "%");
		}
		if (StringUtil.isNotEmpty(startDate)) {
			startDate += " 00:00:00";
			qb.append(" and CreateDate >= ? ", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			endDate += " 23:59:59";
			qb.append(" and CreateDate <= ? ", endDate);
		}
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		dt.decodeColumn("isShow",CacheManager.getMapx("Code", "PromotionHomePageShow"));
		dga.bindData(dt);
	}
	/**
	 * 
	* @Title: deleteModeleteHomepageModeldel 
	* @Description: TODO(删除促销频道首页模块) 
	* @return void    返回类型 
	* @author
	 */
	public void deleteHomepageModel() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDs = ids.split(",");
		Transaction trans = new Transaction();
		StringBuffer PromotionModel=new StringBuffer("in (");
		for (int n = 0; n < IDs.length; n++) {
			SDPromotionHomePageModuleSchema SDPromotionHomePageModule = new SDPromotionHomePageModuleSchema();
			SDPromotionHomePageModule.setId(IDs[n]);
			if (SDPromotionHomePageModule.fill()) {
				trans.add(SDPromotionHomePageModule, Transaction.DELETE);
				PromotionModel.append("'"+SDPromotionHomePageModule.getId()+"',");
			} else {
				Response.setLogInfo(1, "无效主键数据!");
				logger.error("删除促销频道模块发生错误,模块ID为:{}", IDs[n]);
			}
		}
		PromotionModel.deleteCharAt(PromotionModel.length()-1).append(")");
		trans.add(new QueryBuilder("delete from  SDPromotionHomePageActivity where ModuleID "+PromotionModel.toString()));
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}
	/**
	 * 
	* @Title: saveHomepageModel 
	* @Description: TODO(保存首页模块信息) 
	* @return void    返回类型 
	* @author
	 */
	public void saveHomepageModel() {
		//事务创建
		Transaction trans = new Transaction();
		SDPromotionHomePageModuleSchema SDPromotionHomePageModule=new SDPromotionHomePageModuleSchema();
		String ModelStatusFlag=Request.getString("ModelStatusFlag");
		try {
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			//编辑
			if("edit".equals(ModelStatusFlag)){
				SDPromotionHomePageModule.setId(Request.getString("ID"));
				if(SDPromotionHomePageModule.fill()){
					SDPromotionHomePageModule.setValue(Request);
					SDPromotionHomePageModule.setModifyUser(User.getUserName());
					SDPromotionHomePageModule.setModifyDate(date);
					trans.add(SDPromotionHomePageModule,Transaction.UPDATE);
				}else{
					Response.setStatus(0);
					Response.setMessage("保存模块初始化查询失败！");
					return;
				}
			}else{//新增
				SDPromotionHomePageModule.setValue(Request);
				SDPromotionHomePageModule.setId(NoUtil.getMaxNo("PromotionPageID", 11));
				SDPromotionHomePageModule.setCreateUser(User.getUserName());
				SDPromotionHomePageModule.setCreateDate(date);
				SDPromotionHomePageModule.setOrderFlag(System.currentTimeMillis());
				trans.add(SDPromotionHomePageModule,Transaction.INSERT);
			}
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("模块保存成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("模块保存失败！");
				return;
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	* @Title: initHomepageDialog 
	* @Description: TODO(首页模块添加、编辑初始化) 
	* @return Mapx<String,String>    返回类型 
	* @author
	 */
	public static Mapx<String, String> initHomepageDialog(Mapx<String, String> params) {
		params.put("isShow",HtmlUtil.codeToRadios("isShow","PromotionHomePageShow","Y"));//模块是否展示
		String ID = params.getString("ID");
		if(StringUtil.isNotEmpty(ID)){
			QueryBuilder qb = new QueryBuilder("SELECT   *   FROM  SDPromotionHomePageModule  where id=?",ID);
			DataTable dt = qb.executeDataTable();
			DataRow dr = dt.getDataRow(0);
			for (int i = 0; i < dr.getColumnCount(); i++) {
				String ColumnName=dr.getDataColumn(i).getColumnName();
				if("isShow".equals(ColumnName)){
					params.put("isShow",HtmlUtil.codeToRadios("isShow","PromotionHomePageShow",dr.getString(ColumnName)));//模块是否展示
				}else{
					params.put(ColumnName, dr.getString(ColumnName));
				}
			}
		}
		return params;
	}
	/**
	 * 
	* @Title: saveHomepageModelIndex 
	* @Description: TODO(保存促销首页模块顺序) 
	* @return void    返回类型 
	* @author
	 */
	public void saveHomepageModelIndex() {
		DataTable dt = (DataTable) Request.get("DT");
		if (dt == null || dt.getRowCount() == 0) {
			Response.setStatus(0);
			Response.setMessage("数据未修改!");
			return;
		}
		Transaction trans = new Transaction();
		for (int j = 0; j < dt.getRowCount(); j++) {
			String id=dt.getString(j, "ID");
			String OrderFlag=dt.getString(j, "OrderFlag");
			String ModuleName=dt.getString(j, "ModuleName");
			if(!StringUtil.isLong(OrderFlag)){
				Response.setStatus(0);
				Response.setMessage(ModuleName+"的次序值有误，只能为正整数!");
				return;
			}else{
				SDPromotionHomePageModuleSchema SDPromotionHomePageModule=new SDPromotionHomePageModuleSchema();
				SDPromotionHomePageModule.setId(id);
				if(SDPromotionHomePageModule.fill()){
					SDPromotionHomePageModule.setOrderFlag(OrderFlag);
					SDPromotionHomePageModule.setModifyUser(User.getUserName());
					// 定义时间格式
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						Date date = sdf.parse(PubFun.getCurrent());
						SDPromotionHomePageModule.setModifyDate(date);
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
					}
					trans.add(SDPromotionHomePageModule,Transaction.UPDATE);
				}
			}
		}
		if(!trans.commit()){
			Response.setStatus(1);
			Response.setMessage("模块顺序保存成功！");
			return;
		}
	}
	/**
	 * 
	* @Title: dg1DataBindHomepageActivity 
	* @Description: TODO(查询模块对应的活动) 
	* @return void    返回类型 
	* @author
	 */
	public static void dg1DataBindHomepageActivity(DataGridAction dga) {
		String ModuleID = String.valueOf(dga.getParams().get("ModuleID"));
		String title = String.valueOf(dga.getParams().get("title"));
		QueryBuilder qb = new QueryBuilder("select * from sdpromotionhomepageactivity where  1=1  and ModuleID=?",ModuleID);
		if (StringUtil.isNotEmpty(title)) {
			qb.append(" and ActivityName like ?", "%" + title.trim() + "%");
		}
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		dga.bindData(dt);
	}
	/**
	 * 
	* @Title: getImgSrc 
	* @Description: TODO(获取图片路径) 
	* @return void    返回类型 
	* @author
	 */
	@SuppressWarnings("unchecked")
	public void getImgSrc() {
		String ImgID = $V("ImgID");
		ZCImageSchema image = new ZCImageSchema();
		image.setID(ImgID);
		if (image.fill()) {
			this.Response.put("ImgSrc", (image.getPath() + image.getSrcFileName()).replaceAll("//", "/"));
		} else {
			return;
		}
	}
	/**
	 * 
	* @Title: initDialogActivity 
	* @Description: TODO(新增、编辑活动初始化数据) 
	* @return Mapx<String,String>    返回类型 
	* @author
	 */
	public static Mapx<String, String> initDialogActivity(Mapx<String, String> params) {
		params.put("UploadFilePath", Config.getValue("StaticResourcePath")+"/");//图片根目录
		params.put("isShow",HtmlUtil.codeToRadios("isShow","PromotionHomePageShow","Y"));//模块是否展示
		String ID = params.getString("ID");
		if(StringUtil.isNotEmpty(ID)){
			QueryBuilder qb = new QueryBuilder("SELECT   *   FROM  SDPromotionHomePageActivity  where id=?",ID);
			DataTable dt = qb.executeDataTable();
			DataRow dr = dt.getDataRow(0);
			for (int i = 0; i < dr.getColumnCount(); i++) {
				String ColumnName=dr.getDataColumn(i).getColumnName();
				if("endtime".equals(ColumnName)){
					String endtime =dr.getString(ColumnName);
					if (endtime.length() != 0) {
						params.put("endDate",endtime.substring(0,endtime.indexOf(" ")));
						params.put("endTime",endtime.substring(endtime.indexOf(" ") + 1,endtime.length()));
					} else {
						params.put("endDate", "");
						params.put("endTime", "");
					}
				}else if("isShow".equals(ColumnName)){
					params.put("isShow",HtmlUtil.codeToRadios("isShow","PromotionHomePageShow",dr.getString(ColumnName)));//模块是否展示
				}else{
					params.put(ColumnName, dr.getString(ColumnName));
				}
			}
		}
		return params;
	}
	/**
	 * 
	* @Title: saveActivity 
	* @Description: TODO(保存活动信息) 
	* @return void    返回类型 
	* @author
	 */
	public void saveActivity() {
		//事务创建
		Transaction trans = new Transaction();
		SDPromotionHomePageActivitySchema SDPromotionHomePageActivity=new SDPromotionHomePageActivitySchema();
		String ModuleID=Request.getString("ModuleID");
		String ActivityStatusFlag=Request.getString("ActivityStatusFlag");
		try {
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			String endtime = Request.getString("endDate")+" "+ Request.getString("endTime");
			//编辑
			if("edit".equals(ActivityStatusFlag)){
				SDPromotionHomePageActivity.setId(Request.getString("ID"));
				if(SDPromotionHomePageActivity.fill()){
					SDPromotionHomePageActivity.setValue(Request);
					SDPromotionHomePageActivity.setModuleID(ModuleID);
					SDPromotionHomePageActivity.setendtime(sdf.parse(endtime));
					SDPromotionHomePageActivity.setModifyUser(User.getUserName());
					SDPromotionHomePageActivity.setModifyDate(date);
					trans.add(SDPromotionHomePageActivity,Transaction.UPDATE);
				}else{
					Response.setStatus(0);
					Response.setMessage("活动初始化查询失败！");
					return;
				}
			}else{//新增
				SDPromotionHomePageActivity.setValue(Request);
				SDPromotionHomePageActivity.setModuleID(ModuleID);
				SDPromotionHomePageActivity.setId(NoUtil.getMaxNo("PromotionActivityID", 11));
				SDPromotionHomePageActivity.setCreateUser(User.getUserName());
				SDPromotionHomePageActivity.setCreateDate(date);
				SDPromotionHomePageActivity.setOrderFlag(System.currentTimeMillis());
				SDPromotionHomePageActivity.setendtime(sdf.parse(endtime));
				trans.add(SDPromotionHomePageActivity,Transaction.INSERT);
			}
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("活动保存成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("活动保存失败！");
				return;
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	* @Title: saveActivityIndex 
	* @Description: TODO(保存活动顺序) 
	* @return void    返回类型 
	* @author
	 */
	public void saveActivityIndex() {
		DataTable dt = (DataTable) Request.get("DT");
		if (dt == null || dt.getRowCount() == 0) {
			Response.setStatus(0);
			Response.setMessage("数据未修改!");
			return;
		}
		Transaction trans = new Transaction();
		for (int j = 0; j < dt.getRowCount(); j++) {
			String id=dt.getString(j, "ID");
			String OrderFlag=dt.getString(j, "OrderFlag");
			String ActivityName=dt.getString(j, "ActivityName");
			if(!StringUtil.isLong(OrderFlag)){
				Response.setStatus(0);
				Response.setMessage(ActivityName+"的次序值有误，只能为正整数!");
				return;
			}else{
				SDPromotionHomePageActivitySchema SDPromotionHomePageActivity=new SDPromotionHomePageActivitySchema();
				SDPromotionHomePageActivity.setId(id);
				if(SDPromotionHomePageActivity.fill()){
					SDPromotionHomePageActivity.setOrderFlag(OrderFlag);
					SDPromotionHomePageActivity.setModifyUser(User.getUserName());
					// 定义时间格式
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						Date date = sdf.parse(PubFun.getCurrent());
						SDPromotionHomePageActivity.setModifyDate(date);
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
					}
					trans.add(SDPromotionHomePageActivity,Transaction.UPDATE);
				}
			}
		}
		if(!trans.commit()){
			Response.setStatus(1);
			Response.setMessage("模块顺序保存成功！");
			return;
		}
	}
	/**
	 * 
	* @Title: deleteActivity 
	* @Description: TODO(删除活动信息) 
	* @return void    返回类型 
	* @author
	 */
	public void deleteActivity() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDs = ids.split(",");
		Transaction trans = new Transaction();
		for (int n = 0; n < IDs.length; n++) {
			SDPromotionHomePageActivitySchema SDPromotionHomePageActivity = new SDPromotionHomePageActivitySchema();
			SDPromotionHomePageActivity.setId(IDs[n]);
			if (SDPromotionHomePageActivity.fill()) {
				trans.add(SDPromotionHomePageActivity, Transaction.DELETE);
			} else {
				Response.setLogInfo(1, "无效主键数据!");
				logger.error("删除促销频道模块发生错误,模块ID为:{}", IDs[n]);
			}
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}
	/**
	 * 
	* @Title: dg1DataBindHomepageSellingBrand 
	* @Description: TODO(查询模块对应的活动) 
	* @return void    返回类型 
	* @author
	 */
	public static void dg1DataBindHomepageSellingBrand(DataGridAction dga) {
		String BrandName = String.valueOf(dga.getParams().get("BrandName"));
		QueryBuilder qb = new QueryBuilder("select * from SDPromotionHomePageSellingBrand where 1=1 ");
		if (StringUtil.isNotEmpty(BrandName)) {
			qb.append(" and BrandName like ?", "%" + BrandName.trim() + "%");
		}
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		dt.decodeColumn("isShow",CacheManager.getMapx("Code", "PromotionHomePageShow"));
		dga.bindData(dt);
	}
	/**
	 * 
	* @Title: saveHomepageSellingBrandIndex 
	* @Description: TODO(保存促销首页热卖品牌顺序) 
	* @return void    返回类型 
	* @author
	 */
	public void saveHomepageSellingBrandIndex() {
		DataTable dt = (DataTable) Request.get("DT");
		if (dt == null || dt.getRowCount() == 0) {
			Response.setStatus(0);
			Response.setMessage("数据未修改!");
			return;
		}
		Transaction trans = new Transaction();
		for (int j = 0; j < dt.getRowCount(); j++) {
			String id=dt.getString(j, "ID");
			String OrderFlag=dt.getString(j, "OrderFlag");
			String BrandName=dt.getString(j, "BrandName");
			if(!StringUtil.isLong(OrderFlag)){
				Response.setStatus(0);
				Response.setMessage(BrandName+"的次序值有误，只能为正整数!");
				return;
			}else{
				SDPromotionHomePageSellingBrandSchema SDPromotionHomePageSellingBrand=new SDPromotionHomePageSellingBrandSchema();
				SDPromotionHomePageSellingBrand.setId(id);
				if(SDPromotionHomePageSellingBrand.fill()){
					SDPromotionHomePageSellingBrand.setOrderFlag(OrderFlag);
					SDPromotionHomePageSellingBrand.setModifyUser(User.getUserName());
					// 定义时间格式
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						Date date = sdf.parse(PubFun.getCurrent());
						SDPromotionHomePageSellingBrand.setModifyDate(date);
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
					}
					trans.add(SDPromotionHomePageSellingBrand,Transaction.UPDATE);
				}
			}
		}
		if(!trans.commit()){
			Response.setStatus(1);
			Response.setMessage("热卖品牌顺序保存成功！");
			return;
		}
	}
	/**
	 * 
	* @Title: deleteSellintBrand 
	* @Description: TODO(删除热卖品牌) 
	* @return void    返回类型 
	* @author
	 */
	public void deleteSellintBrand() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] IDs = ids.split(",");
		Transaction trans = new Transaction();
		for (int n = 0; n < IDs.length; n++) {
			SDPromotionHomePageSellingBrandSchema SDPromotionHomePageSellingBrand = new SDPromotionHomePageSellingBrandSchema();
			SDPromotionHomePageSellingBrand.setId(IDs[n]);
			if (SDPromotionHomePageSellingBrand.fill()) {
				trans.add(SDPromotionHomePageSellingBrand, Transaction.DELETE);
			} else {
				Response.setLogInfo(1, "无效主键数据!");
				logger.error("删除促销频道热卖品牌发生错误,模块ID为:{}", IDs[n]);
			}
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "操作失败!");
		}
	}
	/**
	 * 
	* @Title: saveSellingBrand 
	* @Description: TODO(保存热卖品牌信息) 
	* @return void    返回类型 
	* @author
	 */
	public void saveSellingBrand() {
		//事务创建
		Transaction trans = new Transaction();
		SDPromotionHomePageSellingBrandSchema SDPromotionHomePageSellingBrand=new SDPromotionHomePageSellingBrandSchema();
		String SellingBrandStatusFlag=Request.getString("SellingBrandStatusFlag");
		try {
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			//编辑
			if("edit".equals(SellingBrandStatusFlag)){
				SDPromotionHomePageSellingBrand.setId(Request.getString("ID"));
				if(SDPromotionHomePageSellingBrand.fill()){
					SDPromotionHomePageSellingBrand.setValue(Request);
					SDPromotionHomePageSellingBrand.setModifyUser(User.getUserName());
					SDPromotionHomePageSellingBrand.setModifyDate(date);
					trans.add(SDPromotionHomePageSellingBrand,Transaction.UPDATE);
				}else{
					Response.setStatus(0);
					Response.setMessage("活动初始化查询失败！");
					return;
				}
			}else{//新增
				SDPromotionHomePageSellingBrand.setValue(Request);
				SDPromotionHomePageSellingBrand.setId(NoUtil.getMaxNo("PromotionSellingID", 11));
				SDPromotionHomePageSellingBrand.setCreateUser(User.getUserName());
				SDPromotionHomePageSellingBrand.setCreateDate(date);
				SDPromotionHomePageSellingBrand.setOrderFlag(System.currentTimeMillis());
				trans.add(SDPromotionHomePageSellingBrand,Transaction.INSERT);
			}
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("活动保存成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("活动保存失败！");
				return;
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	* @Title: initDialogSellingBrand 
	* @Description: TODO(新增、编辑热销品牌初始化数据) 
	* @return Mapx<String,String>    返回类型 
	* @author
	 */
	public static Mapx<String, String> initDialogSellingBrand(Mapx<String, String> params) {
		params.put("UploadFilePath", Config.getValue("StaticResourcePath")+"/");//图片根目录
		params.put("isShow",HtmlUtil.codeToRadios("isShow","PromotionHomePageShow","Y"));//模块是否展示
		String ID = params.getString("ID");
		if(StringUtil.isNotEmpty(ID)){
			QueryBuilder qb = new QueryBuilder("SELECT   *   FROM  SDPromotionHomePageSellingBrand  where id=?",ID);
			DataTable dt = qb.executeDataTable();
			DataRow dr = dt.getDataRow(0);
			for (int i = 0; i < dr.getColumnCount(); i++) {
				String ColumnName=dr.getDataColumn(i).getColumnName();
				if("isShow".equals(ColumnName)){
					params.put("isShow",HtmlUtil.codeToRadios("isShow","PromotionHomePageShow",dr.getString(ColumnName)));//模块是否展示
				}else{
					params.put(ColumnName, dr.getString(ColumnName));
				}
			}
		}
		return params;
	}
	/**
	 * 
	* @Title: initDialogSellingBrand 
	* @Description: TODO(新增、编辑特殊促销产品初始化数据) 
	* @return Mapx<String,String>    返回类型 
	* @author
	 */
	public static Mapx<String, String> initDialogSpecialProduct(Mapx<String, String> params) {
		params.put("UploadFilePath", Config.getValue("StaticResourcePath")+"/");//图片根目录
//		params.put("isShow",HtmlUtil.codeToRadios("isShow","PromotionHomePageShow","Y"));//模块是否展示
//		String ID = params.getString("ID");
//		if(StringUtil.isNotEmpty(ID)){
			QueryBuilder qb = new QueryBuilder("SELECT   *   FROM  SDPromotionHomePageSpecialProduct   ");
			DataTable dt = qb.executeDataTable();
			if(dt.getRowCount()<1){
				return params;
			}
			DataRow dr = dt.getDataRow(0);
			for (int i = 0; i < dr.getColumnCount(); i++) {
				String ColumnName=dr.getDataColumn(i).getColumnName();
				if("endtime".equals(ColumnName)){
					String endtime =dr.getString(ColumnName);
					if (endtime.length() != 0) {
						params.put("endDate",endtime.substring(0,endtime.indexOf(" ")));
						params.put("endTime",endtime.substring(endtime.indexOf(" ") + 1,endtime.length()));
					} else {
						params.put("endDate", "");
						params.put("endTime", "");
					}
				}else if("productid".equals(ColumnName)){
					params.put("productid", dr.getString(ColumnName));
					params.put("productname1",new QueryBuilder("select productname from sdproduct where productid=?",dr.getString(ColumnName)).executeString());//模块是否展示
				}else{
					params.put(ColumnName, dr.getString(ColumnName));
				}
			}
		return params;
	}
	
	/**
	 * 
	* @Title: saveSpecialProduct 
	* @Description: TODO(保存特殊促销产品) 
	* @return void    返回类型 
	* @author
	 */
	public void saveSpecialProduct() {
		//事务创建
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from SDPromotionHomePageSpecialProduct "));
		SDPromotionHomePageSpecialProductSchema SDPromotionHomePageSpecialProduct=new SDPromotionHomePageSpecialProductSchema();
		try {
			// 定义时间格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date= sdf.parse(PubFun.getCurrent());
			SDPromotionHomePageSpecialProduct.setValue(Request);
			SDPromotionHomePageSpecialProduct.setId(NoUtil.getMaxNo("PromotionSpecialID", 11));
			// 定义时间格式
			String endtime = Request.getString("endDate")+" "+ Request.getString("endTime");
			String productid=Request.getString("productid");
			DataTable  dt=new QueryBuilder("select HtmlPath,Remark4 from  sdproduct where ProductID =?",productid).executeDataTable();
			SDPromotionHomePageSpecialProduct.setURL(dt.getString(0, 0));
			SDPromotionHomePageSpecialProduct.setproductid(productid);
			SDPromotionHomePageSpecialProduct.setinitprice(dt.getString(0, 1));
			SDPromotionHomePageSpecialProduct.setendtime(sdf.parse(endtime));
			SDPromotionHomePageSpecialProduct.setCreateUser(User.getUserName());
			SDPromotionHomePageSpecialProduct.setCreateDate(date);
			trans.add(SDPromotionHomePageSpecialProduct,Transaction.INSERT);
			if(trans.commit()){
				Response.setStatus(1);
				Response.setMessage("倒计时产品保存成功！");
				return;
			}else{
				Response.setStatus(0);
				Response.setMessage("倒计时产品保存失败！");
				return;
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
	}
}

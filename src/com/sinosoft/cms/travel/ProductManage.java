/**
 * 
 */
package com.sinosoft.cms.travel;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ClaimsInfoCompanyClassifySchema;
import com.sinosoft.schema.OtProductInfoSchema;
import com.sinosoft.schema.OtProductInfoSet;
import com.sinosoft.schema.OtProductLabelInfoSchema;
import com.sinosoft.schema.OtProductPriceSchema;
import com.sinosoft.schema.ZCImageSchema;
import com.sinosoft.schema.ZCImageSet;

/**
 * @author wangcaiyun
 *
 */
public class ProductManage extends Page {

	public static Mapx<String, String> init(Mapx<String, String> params) {
		params.put("state",HtmlUtil.codeToOptions("YesOrNo", true));
		params.put("productType",HtmlUtil.codeToOptions("OtProduct.Type", true));
		params.put("moduleType",HtmlUtil.codeToOptions("OtProduct.ModuleType", true));
		return params;
	}
	
	public static Mapx<String, String> initDialog(Mapx<String, String> params) {
		params.put("YesOrNo",HtmlUtil.codeToOptions("YesOrNo", false));
		params.put("state", "N");
		params.put("proType",HtmlUtil.codeToOptions("OtProduct.Type", false));
		//归属模块
		params.put("ModelType",HtmlUtil.codeToCheckboxes("moduleType", "OtProduct.ModuleType"));
		params.put("Classify",HtmlUtil.codeToOptions("OtProduct.Classify", false));
		//图片根目录
		params.put("UploadFilePath", Config.getValue("StaticResourcePath")+"/");
		String id = params.getString("id");
		if(StringUtil.isNotEmpty(id)){
			DataTable dt = new QueryBuilder("SELECT * FROM OtProductInfo where id=?", id).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				DataRow dr = dt.getDataRow(0);
				// 产品名
				params.put("productName", dr.getString("productName"));
				params.put("shortName", dr.getString("shortName"));
				
				String StartDate=dr.getString("startDate");
				//开始时间
				if(StringUtil.isNotEmpty(StartDate)){
					params.put("StartDate",StartDate.substring(0,10));
					params.put("StartTime",StartDate.substring(11));
				}else{
					params.put("StartDate","");
					params.put("StartTime","");
				}
				//结束时间
				String EndDate=dr.getString("endDate");
				if(StringUtil.isNotEmpty(EndDate)){
					params.put("EndDate",EndDate.substring(0,10));
					params.put("EndTime",EndDate.substring(11));
				}else{
					params.put("EndDate","");
					params.put("EndTime","");
				}
				// 产品类型
				params.put("productType", dr.getString("productType"));
				// 产品分类
				params.put("productClassify", dr.getString("productClassify"));
				params.put("reserveDay", dr.getString("reserveDay"));
				// 所属模块
				params.put("ModelType", HtmlUtil.mapxToCheckboxes("moduleType",
						CacheManager.getMapx("Code", "OtProduct.ModuleType"),
						dr.getString("moduleType").split(","), null));
				// 上架状态
				params.put("state", dr.getString("state"));
				// 出发地
				params.put("birthland", dr.getString("birthland"));
				// 目的地
				params.put("destination", dr.getString("destination"));
				// 温馨提示
				params.put("prompt", dr.getString("prompt"));
				// 排序
				params.put("sortNum", dr.getString("sortNum"));
				// 产品描述
				params.put("productDesc", dr.getString("productDesc"));
				
			}
		}
		return params;
	}
	
	public static Mapx initDialog2(Mapx params) {
		String productId = params.getString("productId");
		if(StringUtil.isNotEmpty(productId)){
			StringBuffer Detail=new StringBuffer("");
			DataTable dt = new QueryBuilder("SELECT labelName FROM OtProductLabelInfo where productId=? order by sortNum asc",productId).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int rowCount = dt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					Detail.append(dt.getString(i, 0) + ",");
				}
				if(StringUtil.isEmpty(Detail)){
					params.put("Detail","");
				}else{
					params.put("Detail",Detail.deleteCharAt(Detail.length()-1).toString());
				}
			}
		}
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		String productName = (String) dga.getParams().get("productName");
		String productType = (String) dga.getParams().get("productType");
		String state = (String) dga.getParams().get("state");
		String moduleType = (String) dga.getParams().get("moduleType");
		String birthland = (String) dga.getParams().get("birthland");
		String destination = (String) dga.getParams().get("destination");
		
		QueryBuilder qb = new QueryBuilder("select *,'' productTypeName, '' moduleTypeName, '' classifyName,'' stateName from OtProductInfo where state !='0' ");
		// 产品名
		if (StringUtil.isNotEmpty(productName)) {
			qb.append(" and productName like " + "'%" + productName + "%'");
		}
		// 产品类型
		if (StringUtil.isNotEmpty(productType)) {
			qb.append(" and productType = ?", productType);
		}
		// 上架状态
		if (StringUtil.isNotEmpty(state)) {
			qb.append(" and state = ?", state);
		}
		// 归属模块
		if (StringUtil.isNotEmpty(moduleType)) {
			qb.append(" and FIND_IN_SET(?, moduleType)", moduleType);
		}
		// 出发地
		if (StringUtil.isNotEmpty(birthland)) {
			qb.append(" and birthland like " + "'%" + birthland + "%'");
		}
		// 目的地
		if (StringUtil.isNotEmpty(destination)) {
			qb.append(" and destination like " + "'%" + destination + "%'");
		}
		qb.append(" order by sortNum asc, createDate desc");
		
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			Mapx map_state=CacheManager.getMapx("Code", "YesOrNo");
			Mapx map_model=CacheManager.getMapx("Code", "OtProduct.ModuleType");
			Mapx map_type=CacheManager.getMapx("Code", "OtProduct.Type");
			Mapx map_classify=CacheManager.getMapx("Code", "OtProduct.Classify");
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				// 上架状态
				dt.set(i, "stateName", map_state.get(dt.getString(i, "state")));
				// 产品类型
				dt.set(i, "productTypeName", map_type.get(dt.getString(i, "productType")));
				// 产品分类
				if (StringUtil.isNotEmpty(dt.getString(i, "productClassify"))) {
					dt.set(i, "classifyName", map_classify.get(dt.getString(i, "productClassify")));
				}
				// 归属模块
				String moduleTypes=dt.getString(i, "moduleType");
				if (StringUtil.isNotEmpty(moduleTypes)) {
					String modelname="";
					String modeltype[]=moduleTypes.split(",");
					for (int j = 0; j < modeltype.length; j++) {
						modelname=(modelname+","+map_model.get(modeltype[j]));
					}
					if(StringUtil.isNotEmpty(modelname)){
						dt.set(i, "moduleTypeName",modelname.substring(1));
					}
				}
			}
		}
		dga.bindData(dt);
	}
	
	public static void dg2DataBind(DataListAction dga) {
		String productId = (String) dga.getParams().get("productId");
		String path = Config.getValue("StaticResourcePath")+"/";
		QueryBuilder qb = new QueryBuilder("select id, CONCAT('"+path+"', imagePath, imageName) path from OtProductImageInfo where productId = ? order by ModifyDate desc ", productId);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public void dg3DataBind(DataGridAction dga) {
		String productId = (String) dga.getParams().get("productId");
		QueryBuilder qb = new QueryBuilder("select id,travelDate, price, storeNum, createDate, createUser, modifyUser, modifyDate from OtProductPrice where productId = ? order by travelDate asc", productId);
		dga.setTotal(qb);
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
	}
	
	public void dell() {
		String id = Request.getString("id");
		if (StringUtil.isEmpty(id)) {
			Response.setStatus(0);
			Response.setMessage("请选择要删除的产品！");
			return;
		}
		//事务创建
		OtProductInfoSchema schema = new OtProductInfoSchema();
		schema.setid(id);
		if (schema.fill()) {
			// 删除状态：0
			schema.setstate("0");
			schema.setmodifyUser(User.getUserName());
			schema.setmodifyDate(new Date());
			
			if (schema.update()) {
				Response.setStatus(1);
				Response.setMessage("产品删除成功！");
			} else {
				Response.setStatus(0);
				Response.setMessage("产品删除失败！");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("未查询到要删除的产品！");
		}
	}
	
	public void delImage() {
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from OtProductImageInfo where id in ('" + IDs.replaceAll(",", "','") + "')"));
		String path = Config.getValue("StaticResourcePath")+"/";
		DataTable dt = new QueryBuilder("select CONCAT('"+path+"', imagePath, imageName) path from OtProductImageInfo where id in ('" + IDs.replaceAll(",", "','") + "')").executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				FileUtil.delete(dt.getString(i, 0));
			}
		}
		if (trans.commit()) {
			this.Response.setLogInfo(1, "删除图片成功");
		} else {
			this.Response.setLogInfo(0, "删除图片失败");
		}
	}
	
	public void saveTripPrice() {
		String productId = $V("productId");
		DataTable dt = Request.getDataTable("Data");
		if (dt != null && dt.getRowCount() > 0) {
			Transaction trans = new Transaction();
			int row = dt.getRowCount();
			String id = "";
			Date date = new Date();
			OtProductPriceSchema schema;
			for (int i = 0; i < row; i++) {
				id = dt.getString(i, "id");
				schema = new OtProductPriceSchema();
				if (StringUtil.isEmpty(id)) {
					schema.setid(String.valueOf(NoUtil.getMaxID("OtProTripPriceID")));
					schema.settravelDate(dt.getDate(i, "travelDate"));
					schema.setprice(dt.getString(i, "price"));
					schema.setstoreNum(dt.getInt(i, "storeNum"));
					schema.setproductId(productId);
					schema.setcreateDate(date);
					schema.setcreateUser(User.getUserName());
					schema.setmodifyDate(date);
					schema.setmodifyUser(User.getUserName());
					trans.add(schema, Transaction.INSERT);
					
				} else {
					schema.setid(id);
					if (schema.fill()) {
						boolean changeFlag = false;
						if (schema.gettravelDate()
								.compareTo(dt.getDate(i, "travelDate")) != 0) {
							schema.settravelDate(dt.getDate(i, "travelDate"));
							changeFlag = true;
						}
						
						if (new BigDecimal(schema.getprice()).compareTo(new BigDecimal(dt.getString(i, "price"))) != 0) {
							schema.setprice(dt.getString(i, "price"));
							changeFlag = true;
						}
						
						if (schema.getstoreNum() != dt.getInt(i, "storeNum")) {
							schema.setstoreNum(dt.getInt(i, "storeNum"));
							changeFlag = true;
						}
						
						if (changeFlag) {
							schema.setmodifyDate(date);
							schema.setmodifyUser(User.getUserName());
							trans.add(schema, Transaction.UPDATE);
						}
						
					}
				}
			}
			
			if (trans.commit()) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("请添加完数据再保存!");
		}
		
	}
	
	public void setProductLabel() {
		String productId=Request.getString("productId");
		if (StringUtil.isEmpty(productId)) {
			Response.setStatus(0);
			Response.setMessage("请选择要设置标签的产品!");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from OtProductLabelInfo where productId=?",productId));
		String num = Request.getString("DetailNum");
		String index = Request.getString("index");
		String[] index_array=index.split(",");
		OtProductLabelInfoSchema schema;
		for (int i = 0; i < Integer.parseInt(num); i++) {
			schema = new OtProductLabelInfoSchema();
			schema.setid(productId+"_"+(i+1));
			schema.setproductId(productId);
			schema.setlabelName(Request.getString("Detail_" +index_array[i]));
			schema.setsortNum(i+1);
			schema.setcreateUser(User.getUserName());
			schema.setcreateDate(new Date());
			trans.add(schema, Transaction.INSERT);
		}
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("标签设置成功！");
		}else{
			Response.setStatus(0);
			Response.setMessage("标签设置失败！");
		}
		
	}
	
	public void online() {
		String ids = $V("IDs");
		if (StringUtil.isEmpty(ids)) {
			Response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
		ids = "'" + ids.replace(",", "','") + "'";
		// 校验图片是否设置
		String checkSql = "select p.productName from OtProductInfo p where p.id in ("+ ids
				+ ")  and NOT EXISTS(select * from OtProductImageInfo where productId = p.id) ";
		DataTable dt = new QueryBuilder(checkSql).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			Response.setLogInfo(0, "产品：" + dt.getString(0, 0) + "的图片未设置，请设置后再上架！");
			return;
		}
		String updateSql = "update OtProductInfo set state = ?, shelfTime = ?, modifyDate = ?, modifyUser = ? where Id in (" + ids + ")";
		QueryBuilder updateQb = new QueryBuilder(updateSql);
		updateQb.add("Y");
		updateQb.add(DateUtil.getCurrentDateTime());
		updateQb.add(DateUtil.getCurrentDateTime());
		updateQb.add(User.getUserName());
		Transaction trans = new Transaction();
		trans.add(updateQb);
		if (trans.commit()) {
			Response.setLogInfo(1, "上架成功!");
		} else {
			Response.setLogInfo(0, "更新数据库发生错误!");
		}
	}
	
	/**
	 * 下架处理
	 */
	public void downline() {
		String ids = $V("IDs");
		if (StringUtil.isEmpty(ids)) {
			Response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
		ids = "'" + ids.replace(",", "','") + "'";
		String updateSql = "update OtProductInfo set state = ?, "
				+ "modifyDate = ?, modifyUser = ? where Id in (" + ids + ")";
		QueryBuilder qb = new QueryBuilder(updateSql);
		qb.add("N");
		qb.add(DateUtil.getCurrentDateTime());
		qb.add(User.getUserName());
		Transaction trans = new Transaction();
		trans.add(qb);
		if (trans.commit()) {
			Response.setLogInfo(1, "下架成功!");
		} else {
			Response.setLogInfo(0, "更新数据库发生错误!");
		}
	}
	
	public void sortSave() {
		DataTable dt = (DataTable) Request.get("DT");
		OtProductInfoSet set = new OtProductInfoSet();
		OtProductInfoSchema schema;
		for (int i = 0; i < dt.getRowCount(); i++) {
			schema = new OtProductInfoSchema();
			schema.setid(dt.getString(i, "id"));
			schema.fill();
			schema.setsortNum(dt.getString(i, "sortNum"));
			schema.setmodifyDate(new Date());
			schema.setmodifyUser(User.getUserName());

			set.add(schema);
		}
		
		if (set.update()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("保存发生错误!");
		}
	}
	
	public void saveProduct() {
		//事务创建
		Transaction trans = new Transaction();
		OtProductInfoSchema schema = new OtProductInfoSchema();
		String operation=Request.getString("operation");
		
		//归属模块
		String ModelType=Request.getString("moduleType");
		if(StringUtil.isEmpty(ModelType)){
			Response.setStatus(0);
			Response.setMessage("归属模块不能为空！");
			return;
		}
		
		Date date= new Date();
		//编辑
		if("edit".equals(operation)){
			schema.setid(Request.getString("id"));
			if(!schema.fill()){
				Response.setStatus(0);
				Response.setMessage("编辑没有查询到产品信息！");
				return;
			}
			if ("Y".equals(Request.getString("state")) && "N".equals(schema.getstate())) {
				schema.setshelfTime(date);
			}
			schema.setValue(Request);
		}else{
			schema.setValue(Request);
			if ("Y".equals(schema.getstate())) {
				schema.setshelfTime(date);
			}
			schema.setid(String.valueOf(NoUtil.getMaxID("OtProductID")));
		}
		
		//开始时间
		String StartDate=Request.getString("createStartDate");
		String StartTime=Request.getString("createStartTime");
		schema.setstartDate(DateUtil.parseDateTime(StartDate.trim()+" "+StartTime.trim()));
		//结束时间
		String EndDate=Request.getString("createEndDate");
		String EndTime=Request.getString("createEndTime");
		schema.setendDate(DateUtil.parseDateTime(EndDate.trim()+" "+EndTime.trim()));
		
		//编辑
		if("edit".equals(operation)){
			schema.setmodifyUser(User.getUserName());
			schema.setmodifyDate(date);
			trans.add(schema,Transaction.UPDATE);
		}else{
			//新增
			schema.setcreateUser(User.getUserName());
			schema.setcreateDate(date);
			schema.setsortNum(System.currentTimeMillis());
			trans.add(schema,Transaction.INSERT);
		}
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("产品保存成功！");
			return;
		}else{
			Response.setStatus(0);
			Response.setMessage("产品保存失败！");
			return;
		}
	}
	
	public void getPicSrc() {
		String ids = $V("PicID");
		if (!StringUtil.checkID(ids)) {
			return;
		}
		if (StringUtil.isEmpty(ids)) {
			ids = "''";
		}
		ZCImageSet imageSet = new ZCImageSchema().query(new QueryBuilder(" where id in (" + ids + ") order by id desc"));

		String path = Config.getValue("StaticResourcePath");
		StringBuffer images = new StringBuffer();
		if (imageSet.size() > 0) {
			images.append(path + "/" + imageSet.get(0).getPath() + imageSet.get(0).getFileName());
			this.Response.put("picSrc", images.toString());
		} 
	}
}

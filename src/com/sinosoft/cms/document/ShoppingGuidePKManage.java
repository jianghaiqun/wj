/**
 * 
 */
package com.sinosoft.cms.document;

import cn.com.sinosoft.service.impl.OrderConfigNewServiceImpl;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.PKProductAdvantageInfoSchema;
import com.sinosoft.schema.PKProductAdvantageInfoSet;
import com.sinosoft.schema.PKProductDutyInfoSchema;
import com.sinosoft.schema.PKProductFactoryInfoSchema;
import com.sinosoft.schema.PKProductFactoryInfoSet;
import com.sinosoft.schema.PKProductInfoSchema;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FERiskAppFactor;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.ProductInfoResponse;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.CalProductPrem;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.FEMRiskFactorList;
import com.sinosoft.webservice.productPrem.ProductPremServiceStub.ProductPremResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class ShoppingGuidePKManage extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String CatalogID=Config.getValue("ShoppingGuideCatalogID");
		String title = String.valueOf(dga.getParams().get("title"));
		String startDate = String.valueOf(dga.getParams().get("StartDate"));
		String endDate = String.valueOf(dga.getParams().get("EndDate"));
		QueryBuilder qb = new QueryBuilder(" SELECT z.ID,z.Attribute,z.Title,z.URL,z.PublishDate,z.STATUS,z.Author , ( SELECT CASE WHEN textvalue='健康险' THEN 'plan' ELSE '' END FROM zdcolumnvalue   WHERE relaId=z.id AND columncode = 'baoxianfenlei') AS textvalue FROM ZCArticle z  WHERE z.CatalogID=?");
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
	
	public static void dg2DataBind(DataGridAction dga) {
		String articleId = dga.getParam("articleid");
		DataTable dt = new QueryBuilder(
				"SELECT ID, DutyName, OrderFlag, DutyAmnt1, Percent1, KeyTips1, DutyDesc1, DutyAmnt2, Percent2, KeyTips2, DutyDesc2  ,prop1,prop2,prop3,prop4 "
				+ "FROM PKProductDutyInfo where ArticleId=?  ",
				articleId).executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public static Mapx<String, String> initPKProductDialog(Mapx<String, String> params) {
		String articleId = params.getString("articleid");
		if(StringUtil.isNotEmpty(articleId)){
			DataTable dt_Product = new QueryBuilder("SELECT ProductId1, ProductName1, ProductId2, ProductName2 FROM PKProductInfo where ArticleId=?",articleId).executeDataTable();
			if (dt_Product != null && dt_Product.getRowCount() > 0) {
				params.put("ProductId1",dt_Product.getString(0, 0));
				params.put("ProductName1",dt_Product.getString(0, 1));
				params.put("ProductId2",dt_Product.getString(0, 2));
				params.put("ProductName2",dt_Product.getString(0, 3));
			}
		}
		return params;
	}
	
	public static Mapx<String, String> initPKDutyDialog(Mapx<String, String> params) {
		String articleId = params.getString("articleid");
		if(StringUtil.isNotEmpty(articleId)){
			DataTable dt_Product = new QueryBuilder("SELECT ProductName1, ProductName2 FROM PKProductInfo where ArticleId=?",articleId).executeDataTable();
			if (dt_Product != null && dt_Product.getRowCount() > 0) {
				params.put("ProductName1",dt_Product.getString(0, 0));
				params.put("ProductName2",dt_Product.getString(0, 1));
			}
		}
		params.put("KeyTips",HtmlUtil.codeToOptions("YesOrNo", false));
		
		return params;
	}
	
	public static Mapx<String, String> initPKAdvantageDialog(Mapx<String, String> params) {
		String articleId = params.getString("articleid");
		if(StringUtil.isNotEmpty(articleId)){
			String OrderFlag = "";
			String Info1 = "";
			String Info2 = "";
			DataTable dt = new QueryBuilder("SELECT OrderFlag, Info1, Info2 FROM PKProductAdvantageInfo where ArticleId=? order by OrderFlag asc",articleId).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int rowCount = dt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					OrderFlag += ("," + dt.getString(i, 0));
					Info1 += ("," + dt.getString(i, 1));
					Info2 += ("," + dt.getString(i, 2));
				}
				params.put("OrderFlag", OrderFlag.substring(1));
				params.put("Info1", Info1.substring(1));
				params.put("Info2", Info2.substring(1));
			}
		}
		return params;
	}
	
	public static void dg1DataBindAllProduct(DataGridAction dga) {
		String productname = (String) dga.getParams().get("productname");
		QueryBuilder qb_product = new QueryBuilder("SELECT productid as  id,productname FROM sdproduct WHERE  IsPublish='Y' and ComplicatedFlag = 'N' ");
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
	
	public void savePKAdvantage() {
		String articleid=Request.getString("articleid");
		if(StringUtil.isEmpty(articleid)){
			Response.setStatus(0);
			Response.setMessage("未关联文章，请选择要编辑的文章！");
			return;
		}
		if (!checkPKProduct(articleid)) {
			Response.setStatus(0);
			Response.setMessage("请先设置PK产品！");
			return;
		}
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from PKProductAdvantageInfo where ArticleId=?",articleid));
		String date = DateUtil.getCurrentDate("yyyyMMdd");
		Date da = new Date();
		String username = User.getUserName();
		PKProductAdvantageInfoSet set = new PKProductAdvantageInfoSet();
		PKProductAdvantageInfoSchema schema;
		String num = Request.getString("DetailNum");
		String index = Request.getString("index");
		String[] index_array=index.split(",");
		for (int i = 0; i < Integer.parseInt(num); i++) {
			schema = new PKProductAdvantageInfoSchema();
			schema.setID(date + NoUtil.getMaxNo("PKAdvantageInfo", 11));
			schema.setArticleId(articleid);
			schema.setOrderFlag(i+1);
			schema.setInfo1(Request.getString("Info1_" +index_array[i]));
			schema.setInfo2(Request.getString("Info2_" +index_array[i]));
			schema.setCreateDate(da);
			schema.setCreateUser(username);
			set.add(schema);
		}
		trans.add(set, Transaction.INSERT);
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("PK产品优势信息保存成功！");
			return;
		}else{
			Response.setStatus(0);
			Response.setMessage("PK产品优势信息保存失败！");
			return;
		}
	}
	
	public void savePKDuty() {
		String date = DateUtil.getCurrentDate("yyyyMMdd");
		PKProductDutyInfoSchema schema = new PKProductDutyInfoSchema();
		Transaction trans = new Transaction();
		int operateFlag = Transaction.INSERT;
		
		String operate = Request.getString("operate");
		String id = Request.getString("ID");
		String articleid = Request.getString("articleid");
		String prop1 = Request.getString("prop1");
		String prop2 = Request.getString("prop2");
		String prop3 = Request.getString("prop3");
		String prop4 = Request.getString("prop4");
		String textvalue = Request.getString("textvalue");
		if (StringUtil.isEmpty(articleid)) {
			Response.setStatus(0);
			Response.setMessage("未关联文章，请选择要编辑的文章！");
			return;
		}
		if (!checkPKProduct(articleid)) {
			Response.setStatus(0);
			Response.setMessage("请先设置PK产品！");
			return;
		}
		if ("Delete".equals(operate)) {
			if (StringUtil.isEmpty(id)) {
				Response.setStatus(0);
				Response.setMessage("请选择要删除的PK保障列表中的数据！");
				return;
			}
			trans.add(new QueryBuilder("delete from PKProductDutyInfo where ID = ?",id));
			if (trans.commit()) {
				Response.setStatus(1);
				Response.setMessage("删除成功！");
				return;
			} else {
				Response.setStatus(0);
				Response.setMessage("删除失败！");
				return;
			}
		} else if ("Update".equals(operate)) {
			if (StringUtil.isEmpty(id)) {
				Response.setStatus(0);
				Response.setMessage("请选择要修改的PK保障列表中的数据！");
				return;
			}
			schema.setID(id);
			if (schema.fill()) {
				schema.setModifyDate(new Date());
				schema.setModifyUser(User.getUserName());
				operateFlag = Transaction.UPDATE;
			} else {
				Response.setStatus(0);
				Response.setMessage("该PK保障在数据库中不存在！");
				return;
			}
			
		} else if ("Insert".equals(operate)) {
			int COUNTPKProductDutyInfo = new QueryBuilder("SELECT COUNT(1) FROM PKProductDutyInfo  WHERE articleid='"+Request.getString("articleid")+"'").executeInt();
			if( COUNTPKProductDutyInfo >=8){
				Response.setStatus(0);
				Response.setMessage("PK保障信息最多设置8条");
				return;
			}
			schema.setID(date + NoUtil.getMaxNo("PKProductDutyInfo", 11));
			schema.setCreateDate(new Date());
			schema.setCreateUser(User.getUserName());
		} else {
			Response.setStatus(0);
			Response.setMessage("操作方法有误！");
			return;
		}
		schema.setArticleId(Request.getString("articleid"));
		schema.setDutyName(Request.getString("DutyName"));
		schema.setOrderFlag(Request.getString("OrderFlag"));
		schema.setDutyAmnt1(Request.getString("DutyAmnt1"));
		schema.setPercent1(Request.getString("Percent1"));
		schema.setKeyTips1(Request.getString("KeyTips1"));
		schema.setDutyDesc1(Request.getString("DutyDesc1"));
		schema.setDutyAmnt2(Request.getString("DutyAmnt2"));
		schema.setPercent2(Request.getString("Percent2"));
		schema.setKeyTips2(Request.getString("KeyTips2"));
		schema.setDutyDesc2(Request.getString("DutyDesc2"));
		schema.setProp1(prop1);
		schema.setProp2(prop2);
		schema.setProp3(prop3);
		schema.setProp4(prop4);
		trans.add(schema, operateFlag);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("保存成功！");
			return;
		} else {
			Response.setStatus(0);
			Response.setMessage("保存失败！");
			return;
		}
	}
	
	/**
	 * 校验文章PK产品是否设置
	 * 
	 * @param articleId
	 * @return
	 */
	private boolean checkPKProduct(String articleId) {
		if (new QueryBuilder(
				"select count(1) from PKProductInfo where ArticleId = ?",
				articleId).executeInt() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void savePKProduct() {
		//事务创建
		Transaction trans = new Transaction();
		String articleId = Request.getString("articleId");
		String productId1 = Request.getString("productId1");
		String productId2 = Request.getString("productId2");
		if (StringUtil.isEmpty(productId1) || StringUtil.isEmpty(productId2)) {
			Response.setStatus(0);
			Response.setMessage("请选择PK产品！");
			return;
		}
		
		if (StringUtil.isEmpty(articleId)) {
			Response.setStatus(0);
			Response.setMessage("请选择PK文章！");
			return;
		}
		
		if (productId1.equals(productId2)) {
			Response.setStatus(0);
			Response.setMessage("PK产品不能选择同一款产品！");
			return;
		}
		String sql = "select IsPublish, ComplicatedFlag, ProductID, ProductName, HtmlPath, Remark4 from sdproduct where ProductID = ?";
		DataTable dt1 = new QueryBuilder(sql, productId1).executeDataTable();
		// 校验产品1是否存在
		if (dt1 == null || dt1.getRowCount() == 0) {
			Response.setStatus(0);
			Response.setMessage("PK产品1信息不存在！");
			return;
		}
		Map<String, String> product1Map = new HashMap<String, String>();
		DataRow dr = dt1.getDataRow(0);
		for (int i = 0; i < dr.getColumnCount(); i++) {
			product1Map.put(dr.getDataColumn(i).getColumnName(), dr.getString(i));
		}
		// 校验产品1是否下架
		if (!"Y".equals(product1Map.get("IsPublish"))) {
			Response.setStatus(0);
			Response.setMessage("PK产品1已下架，请重新选择PK产品！");
			return;
		}
		// 校验产品1是否为简单险，只有简单险才可PK
		if ("Y".equals(product1Map.get("ComplicatedFlag"))) {
			Response.setStatus(0);
			Response.setMessage("PK产品1是复杂险，PK产品必须为简单险，请重新选择！");
			return;
		}
		
		dt1 = new QueryBuilder(sql, productId2).executeDataTable();
		// 校验产品2是否存在
		if (dt1 == null || dt1.getRowCount() == 0) {
			Response.setStatus(0);
			Response.setMessage("PK产品2信息不存在！");
			return;
		}
		Map<String, String> product2Map = new HashMap<String, String>();
		dr = dt1.getDataRow(0);
		for (int i = 0; i < dr.getColumnCount(); i++) {
			product2Map.put(dr.getDataColumn(i).getColumnName(), dr.getString(i));
		}
		// 校验产品2是否下架
		if (!"Y".equals(product2Map.get("IsPublish"))) {
			Response.setStatus(0);
			Response.setMessage("PK产品2已下架，请重新选择PK产品！");
			return;
		}
		// 校验产品2是否为简单险，只有简单险才可PK
		if ("Y".equals(product2Map.get("ComplicatedFlag"))) {
			Response.setStatus(0);
			Response.setMessage("PK产品2是复杂险，PK产品必须为简单险，请重新选择！");
			return;
		}
		
		// 记录产品所有的保障期限
		Map<String, String> map = new HashMap<String, String>();
		// 记录产品1除保障期限的其他默认投保要输
		Map<String, String> map1 = new HashMap<String, String>();
		// 同步PK产品投保要素信息并保存
		try {
		    if (!dealPKProductFactoryInfo(productId1, trans, map, map1)) {
		    	Response.setStatus(0);
				Response.setMessage("产品1同步数据为空，请检查产品！");
				return;
		    }
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("产品1同步数据错误！");
			return;
		}
		// 记录产品2除保障期限的其他默认投保要输
		Map<String, String> map2 = new HashMap<String, String>();
		try {
		    if (!dealPKProductFactoryInfo(productId2, trans, map, map2)) {
		    	Response.setStatus(0);
				Response.setMessage("产品2同步数据为空，请检查产品！");
				return;
		    }
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("产品2同步数据错误！");
			return;
		}
		// 默认行程
		String defaultValue = Config.getValue("ShoppingGuideDefalutPeriod");
		// 取得两产品默认行程对应的保障期限
		Map<String, String> reMap = dealSchedule(map.get(productId1), map.get(productId2), Integer.valueOf(convertDate(defaultValue)));
		
		// 产品1保费试算
		map1.put(reMap.get("ProductAppCode1")+"_Period", reMap.get("ProductPeriod1"));
		try {
			String price = premDoCal(map1, productId1);
			product1Map.put("Remark4", price.split(",")[0]);
			product1Map.put("Prem", price.split(",")[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("产品1保费失算异常，请重试！");
			return;
		}
		
		// 产品2保费试算
		map2.put(reMap.get("ProductAppCode2")+"_Period", reMap.get("ProductPeriod2"));
		try {
			String price = premDoCal(map2, productId2);
			product2Map.put("Remark4", price.split(",")[0]);
			product2Map.put("Prem", price.split(",")[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("产品2保费失算异常，请重试！");
			return;
		}
		
		
		dealPKProductInfo(articleId, product1Map, product2Map, reMap, trans);
		
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("产品添加成功！");
			return;
		} else {
			Response.setStatus(0);
			Response.setMessage("产品添加失败！");
			return;
		}
	}
	
	public String premDoCal(Map<String, String> map, String riskcode) throws Exception {
		CalProductPrem[] calProductPrem = new CalProductPrem[1];
		Iterator<String> itr = map.keySet().iterator();
		FEMRiskFactorList[] fEMRiskFactorList = new FEMRiskFactorList[map.size()];
		int iCount = 0;
		String key = "";
		while (itr.hasNext()) {
			key = itr.next();
			fEMRiskFactorList[iCount] = new FEMRiskFactorList();
			fEMRiskFactorList[iCount].setAppFactorCode(key.split("_")[0]);
			fEMRiskFactorList[iCount].setFactorType(key.split("_")[1]);
			fEMRiskFactorList[iCount].setFactorValue(map.get(key));
			if ("TextAge".equals(fEMRiskFactorList[iCount].getFactorType())) {
				String age = map.get(key);
				if (StringUtil.isNotEmpty(age)) {
					String birthday = new OrderConfigNewServiceImpl().getBrithdayByFactor(null, age);
					fEMRiskFactorList[iCount].setFactorValue(birthday);
				}
			}
			fEMRiskFactorList[iCount].setIsPremCalFacotor("Y");
			iCount++;
		}
		
		calProductPrem[0] = new CalProductPrem();
		calProductPrem[0].setRiskCode(riskcode);
		calProductPrem[0].setFEMRiskFactorList(fEMRiskFactorList);
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("CalProductPrem", calProductPrem);
		ProductPremResponse ProductPrem = ProductWebservice.ProductPremSereviceImpl(mp, null);
		return ProductPrem.getDiscountTotalPrice()+","+ProductPrem.getTotalPrice();
	}
	
	public Map<String, String> dealSchedule(String period1, String period2, int defaultVale) {
		Map<String, String> map = new HashMap<String, String>();
		String[] appCode1 = period1.split(";")[0].split(",");
		String[] periods1 = period1.split(";")[1].split(",");
		String[] appCode2 = period2.split(";")[0].split(",");
		String[] periods2 = period2.split(";")[1].split(",");
		int len = periods1.length;
		String lower = "";
		String upper = "";
		// 取得产品1默认行程天数所在的保障区间
		for (int i = 0; i < len; i++) {
			if (periods1[i].contains("-")) {
				//下限
				lower = periods1[i].split("-")[0];
				lower = convertDate(lower);
				if (defaultVale <= Integer.valueOf(lower)) {
					map.put("ProductPeriod1", periods1[i]);
					map.put("ProductAppCode1", appCode1[i]);
					break;
				}
				
				//上限
				upper = periods1[i].split("-")[1];
				upper = convertDate(upper);
				if (defaultVale <= Integer.valueOf(upper)) {
					map.put("ProductPeriod1", periods1[i]);
					map.put("ProductAppCode1", appCode1[i]);
					break;
				}
			} else {
				lower = convertDate(periods1[i]);
				if (defaultVale <= Integer.valueOf(lower)) {
					map.put("ProductPeriod1", periods1[i]);
					map.put("ProductAppCode1", appCode1[i]);
					break;
				}
			}
			
		}
		// 取得产品2默认行程天数所在的保障区间
		len = periods2.length;
		for (int i = 0; i < len; i++) {
			if (periods2[i].contains("-")) {
				//下限
				lower = periods2[i].split("-")[0];
				lower = convertDate(lower);
				if (defaultVale <= Integer.valueOf(lower)) {
					map.put("ProductPeriod2", periods2[i]);
					map.put("ProductAppCode2", appCode2[i]);
					break;
				}
				//上限
				upper = periods2[i].split("-")[1];
				upper = convertDate(upper);
				if (defaultVale <= Integer.valueOf(upper)) {
					map.put("ProductPeriod2", periods2[i]);
					map.put("ProductAppCode2", appCode2[i]);
					break;
				}
			} else {
				lower = convertDate(periods2[i]);
				if (defaultVale <= Integer.valueOf(lower)) {
					map.put("ProductPeriod2", periods2[i]);
					map.put("ProductAppCode2", appCode2[i]);
					break;
				}
			}
			
		}
		map.put("defaultVale", defaultVale+"D");
		// 产品1、产品2都不包含行程天数时取得两个产品保障期限交集的最小天数1天
		if (StringUtil.isEmpty(map.get("ProductPeriod1"))) {
			map.put("ProductPeriod1", periods1[0]);
			map.put("ProductAppCode1", appCode1[0]);
			map.put("defaultVale", "1D");
		}
		
		if (StringUtil.isEmpty(map.get("ProductPeriod2"))) {
			map.put("ProductPeriod2", periods2[0]);
			map.put("ProductAppCode2", appCode2[0]);
			map.put("defaultVale", "1D");
		}
		return map;
	}
	
	public String convertDate(String period) {
		try {
			String unit = period.substring(period.length()-1);
			String time = period.substring(0, period.length()-1);
			if ("Y".equals(unit)) {
				time = String.valueOf(Integer.valueOf(time)*365);
			} else if ("M".equals(unit)) {
				time = String.valueOf(Integer.valueOf(time)*30);
			}
			return time;
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "0";
	}
	
	private boolean dealPKProductFactoryInfo(String productId, Transaction trans, Map<String, String> map, Map<String, String> map1) throws Exception  {
		String date = DateUtil.getCurrentDate("yyyyMMdd");
		Date da = new Date();
		String user = User.getUserName();
		PKProductFactoryInfoSchema schema = new PKProductFactoryInfoSchema();
		PKProductFactoryInfoSet set = new PKProductFactoryInfoSet();
		int count = new QueryBuilder("select count(1) from PKProductFactoryInfo where RiskCode = ?", productId).executeInt();
		// 同步产品信息
		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter.put("RiskCode", new String[] { productId });
		
		ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(paramter, "");
		FMRisk[] list = productInfo.getFMRisk();
		if (list != null && list.length > 0 && list[0] != null) {
			FMRisk fmRisk = list[0];
			FERiskAppFactor[] appFactors = fmRisk.getFERiskAppFactor();
			for (FERiskAppFactor app : appFactors) {
				schema = new PKProductFactoryInfoSchema();
				com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList[] femRiskFactorList = app.getFEMRiskFactorList();
				if (femRiskFactorList != null && femRiskFactorList.length > 0) {
					schema = new PKProductFactoryInfoSchema();
					int len = femRiskFactorList.length;
					String factorValue = "";
					String appFactorCode = "";
					if ("Period".equals(app.getFactorType())) {
						for (int i = 0; i < len; i++) {
							schema = new PKProductFactoryInfoSchema();
							schema.setID(date + NoUtil.getMaxNo("PKFactoryInfoID", 11));
							schema.setRiskCode(productId);
							schema.setAppFactorCode(femRiskFactorList[i].getAppFactorCode());
							schema.setFactoryType(app.getFactorType());
							schema.setFactoryName(app.getFactorTypeName());
							schema.setFactoryValue(femRiskFactorList[i].getFactorValue());
							schema.setCreateDate(da);
							schema.setCreateUser(user);
							set.add(schema);
							factorValue += (","+femRiskFactorList[i].getFactorValue());
							appFactorCode += (","+femRiskFactorList[i].getAppFactorCode());
						}
						factorValue = factorValue.substring(1);
						appFactorCode = appFactorCode.substring(1);
						map.put(productId, appFactorCode+";"+factorValue);
						continue;
					} else {
						factorValue = femRiskFactorList[0].getFactorValue();
						appFactorCode = femRiskFactorList[0].getAppFactorCode();
						for (int i = 0; i < len; i++) {
							if ("Y".equals(femRiskFactorList[i].getDefaultValue())) {
								factorValue = femRiskFactorList[i].getFactorValue();
								appFactorCode= femRiskFactorList[i].getAppFactorCode();
								
								break;
							}
						}
					}
					map1.put(appFactorCode+"_"+app.getFactorType(), factorValue);
					schema.setID(date + NoUtil.getMaxNo("PKFactoryInfoID", 11));
					schema.setRiskCode(productId);
					schema.setAppFactorCode(appFactorCode);
					schema.setFactoryType(app.getFactorType());
					schema.setFactoryName(app.getFactorTypeName());
					schema.setFactoryValue(factorValue);
					schema.setCreateDate(da);
					schema.setCreateUser(user);
					set.add(schema);
				}
			}
		} else {
			logger.warn("同步产品（产品编码："+productId+"）产品数据为空！");
			return false;
		}
		
		if (count > 0) {
			trans.add(new QueryBuilder("delete from PKProductFactoryInfo where RiskCode = ?", productId));
		}
		trans.add(set, Transaction.INSERT);
		return true;
	}
	
	private void dealPKProductInfo (String articleId, Map<String, String> product1Map, Map<String, String> product2Map, Map<String, String> reMap, Transaction trans) {
		PKProductInfoSchema schema = new PKProductInfoSchema();
		int operateFlag = Transaction.INSERT;
		String id = new QueryBuilder("select ID from PKProductInfo where ArticleId = ?", articleId).executeString();
		String date = DateUtil.getCurrentDate("yyyyMMdd");
		Date da = new Date();
		String user = User.getUserName();
		if (StringUtil.isEmpty(id)) {
			id = date + NoUtil.getMaxNo("PKProductInfoID", 11);
			schema.setID(id);
			schema.setArticleId(articleId);
			schema.setCreateDate(da);
			schema.setCreateUser(user);
		} else {
			schema.setID(id);
			if (schema.fill()) {
				operateFlag = Transaction.UPDATE;
				schema.setModifyDate(da);
				schema.setModifyUser(user);
			}
		}
		schema.setProductId1(product1Map.get("ProductID"));
		schema.setProductName1(product1Map.get("ProductName"));
		schema.setProductUrl1(product1Map.get("HtmlPath"));
		schema.setDiscountPrice1(product1Map.get("Remark4"));
		schema.setPrem1(product1Map.get("Prem"));
		schema.setProductPeriod1(reMap.get("ProductPeriod1"));
		schema.setProductId2(product2Map.get("ProductID"));
		schema.setProductName2(product2Map.get("ProductName"));
		schema.setProductUrl2(product2Map.get("HtmlPath"));
		schema.setProductPeriod2(reMap.get("ProductPeriod2"));
		schema.setDiscountPrice2(product2Map.get("Remark4"));
		schema.setPrem2(product2Map.get("Prem"));
		schema.setInitPeriod(reMap.get("defaultVale"));
		trans.add(schema, operateFlag);
	}

	public static String getPKArticleTab(String articleId, String articleIds) {
		String tabHtml = "<ul class=\"g-guide-navs cf\">";
		String[] arti = articleIds.split(",");
		int len = arti.length;
		String whereSql = "";
		for (int i = 0; i < len; i++) {
			whereSql += "?,";
		}
		
		whereSql = (whereSql.substring(0, whereSql.length() - 1));
		// 取得文章链接
		QueryBuilder qb = new QueryBuilder("select ID,URL from zcarticle where ID in (" + whereSql + ")");
		for (int i = 0; i < len; i++) {
			qb.add(arti[i]);
		}
		DataTable dt = qb.executeDataTable();
		if (dt == null || dt.getRowCount() == 0) {
			return "";
		}
		String serverContextPath = Config.getFrontServerContextPath() + "/";
		Map<String, String> mapUrl = new HashMap<String, String>();
		int rowcount = dt.getRowCount();
		for (int i = 0; i < rowcount; i++) {
			mapUrl.put(dt.getString(i, 0), serverContextPath + dt.getString(i, 1));
		}
		// 取得文章tab名称
		qb = new QueryBuilder("select RelaID,TextValue from zdcolumnvalue where ColumnCode='tabName' and RelaID in (" + whereSql + ")");
		for (int i = 0; i < len; i++) {
			qb.add(arti[i]);
		}
		dt = qb.executeDataTable();
		if (dt == null || dt.getRowCount() == 0) {
			return "";
		}
		Map<String, String> mapTabName = new HashMap<String, String>();
		for (int i = 0; i < rowcount; i++) {
			mapTabName.put(dt.getString(i, 0), dt.getString(i, 1));
		}
		// 拼装前台tab
		for (int i = 0; i < len; i++) {
			if (StringUtil.isNotEmpty(arti[i])) {
				if (articleId.equals(arti[i])) {
					tabHtml += "<li class=\"select\">";
				} else {
					tabHtml += "<li>";
				}
				tabHtml += ("<a href='"+mapUrl.get(arti[i])+"' target=\"_self\"  exturl=\"http://www.kaixinbao.com/DGtab\" vlpageid=\"DGtab\" onclick=\"javascript:void(0);return(VL_FileDL(this));return false;\">"+mapTabName.get(arti[i])+"</a></li>");
			}
		}
		
		tabHtml += "</ul>";
		return tabHtml;
	}
	
	/**
	 * 取得文章拼装信息
	 * @param articleId
	 * @return
	 */
	public String getPKInfo(String articleId) {
		StringBuffer sb = new StringBuffer();
		
		// 取得PK产品信息
		Map<String, String> map = getPKProductInfo(articleId);
		sb.append("<div class=\"g-pk-tit-con\"><h3 class=\"g-gpk-tit f_mi\"><span>");
		sb.append(map.get("ProductName1") + "</span></h3><span class=\"g-pk-icon\"></span>");
		sb.append("<h3 class=\"g-gpk-tit2 f_mi\"><span>" + map.get("ProductName2") + "</span></h3>");
		sb.append("</div><div class=\"clear\"></div>");
		
		// 拼装PK保障信息
		sb.append(getPKDutyHtml(articleId));
		
		// 拼装PK优势信息
		sb.append(getPKAdvantageInfo(articleId));
		
		// 拼装行程推荐html
		if (!map.isEmpty()) {
			sb.append(getRecommentSchedule(map, articleId));
		}
		
		return sb.toString();
	}
	
	public Map<String, String> getPKProductInfo(String articleId) {
		DataTable dt = new QueryBuilder("select ProductId1, ProductName1, DiscountPrice1, Prem1, ProductUrl1, ProductPeriod1, ProductId2, ProductName2, DiscountPrice2, Prem2, ProductUrl2, ProductPeriod2, InitPeriod from PKProductInfo where ArticleId = ? limit 0,1", articleId).executeDataTable();
		Map<String, String> map = new HashMap<String, String>();
		if (dt != null && dt.getRowCount() > 0) {
			DataColumn[] colu = dt.getDataRow(0).getDataColumns();
			int len = colu.length;
			for (int i = 0; i < len; i++) {
				map.put(colu[i].getColumnName(), dt.getString(0, colu[i].getColumnName()));
			}
		}
		return map;
	}
	
	/**
	 * 拼装PK保障信息
	 * @param articleId
	 * @return PK保障信息
	 */
	private String getPKDutyHtml(String articleId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"g-max-tit f_mi\"><span><em class=\"pk-max\"></em>保障PK</span></div><div class=\"g-bzpk-con\">");
		DataTable dt = new QueryBuilder("select DutyName, DutyAmnt1, Percent1, KeyTips1, DutyDesc1, DutyAmnt2, Percent2, KeyTips2, DutyDesc2 from PKProductDutyInfo where ArticleId = ? order by OrderFlag asc", articleId).executeDataTable();	
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				sb.append("<dl class=\"g-bzpk-dl\"><dd class=\"g-pk-left hovercs\">");
				sb.append("<span style=\"width:"+dt.getString(i, "Percent1")+"\">");
				sb.append(dt.getString(i, "DutyAmnt1")+"</span>");
				if ("Y".equals(dt.getString(i, "KeyTips1"))) {
					sb.append("<div class=\"ts_tip\"></div>");
				}
				sb.append("<div class=\"g-pk-desf\" style=\"display:none;\"><em class=\"g-pk-cficon\"></em>");
				sb.append("<p>"+dt.getString(i, "DutyDesc1")+"</p><em class=\"g-pk-jiao\" ></em>");
				sb.append("</div></dd><dd class=\"g-pk-dt\">"+dt.getString(i, "DutyName")+"</dd>");
				sb.append("<dd class=\"g-pk-right hovercs\"><span style=\"width:"+dt.getString(i, "Percent2")+"\">");
				sb.append(dt.getString(i, "DutyAmnt2")+"</span>");
				sb.append("<div class=\"g-pk-desf\" style=\"display:none;\">");
				sb.append("<em class=\"g-pk-cficon\"></em><p>"+dt.getString(i, "DutyDesc2")+"</p>");
				sb.append("<em class=\"g-pk-jiao\" ></em></div>");
				if ("Y".equals(dt.getString(i, "KeyTips2"))) {
					sb.append("<div class=\"ts_tip\"></div>");
				}
				sb.append("</dd></dl>");
			}
		}
		sb.append("</div><div class=\"clear\"></div>");
		return sb.toString();
	}
	
	/**
	 * 拼装PK优势信息
	 * @param articleId
	 * @return PK优势信息
	 */
	private String getPKAdvantageInfo(String articleId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"g-max-tit f_mi\"><span><em class=\"pk-max pk-max1\"></em>优势PK</span></div>");
		sb.append("<div class=\"gys-pk-con\">");
		DataTable dt = new QueryBuilder("select OrderFlag, Info1, Info2 from PKProductAdvantageInfo where ArticleId = ? order by OrderFlag asc", articleId).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			// 产品1优势信息html
			StringBuffer advantage1Sb = new StringBuffer();
			advantage1Sb.append("<dl class=\"gys-pk-dl gys-pk-dll\" style=\"display:none\">");
			// 产品2优势信息html
			StringBuffer advantage2Sb = new StringBuffer();
			advantage2Sb.append("<div class=\"gys-pk-icon\" style=\"display:none\"></div>");
			advantage2Sb.append("<dl class=\"gys-pk-dl gys-pk-dlr\" style=\"display:none\">");
			// 显示序号
			String ShowNo = "";
			// 产品1优势信息
			String info1 = "";
			// 产品2优势信息
			String info2 = "";
			for (int i = 0; i < rowCount; i++) {
				ShowNo = dt.getString(i, "OrderFlag");
				info1 = dt.getString(i, "Info1");
				info2 = dt.getString(i, "Info2");
				if (StringUtil.isNotEmpty(info1)) {
					advantage1Sb.append("<dd class=\"gys-dd"+ShowNo+" hovercs\"><em>"+ShowNo+"</em><span>"+info1+"</span>");
					advantage1Sb.append("<div style=\"display: none;\" class=\"g-pk-desf\"><i class=\"g-pk-cficon\"></i><p>");
					advantage1Sb.append(info1+"</p><i class=\"g-pk-jiao\"></i></div></dd>");
				}
				if (StringUtil.isNotEmpty(info2)) {
					advantage2Sb.append("<dd class=\"gys2-dd"+ShowNo+" hovercs\"><em>"+ShowNo+"</em><span>"+info2+"</span>");
					advantage2Sb.append("<div style=\"display: none;\" class=\"g-pk-desf\"><i class=\"g-pk-cficon\"></i><p>");
					advantage2Sb.append(info2+"</p><i class=\"g-pk-jiao\"></i></div></dd>");
				}
			}
			
			advantage1Sb.append("</dl>");
			advantage2Sb.append("</dl>");
			sb.append(advantage1Sb);
			sb.append(advantage2Sb);
		}
		
		sb.append("<div class=\"clear\"></div></div>");
		
		return sb.toString();
	}
	
	/**
	 * 校验是否是一年期
	 * @param period
	 * @return
	 */
	private boolean checkYearPeriod(String period) {
		if (period.endsWith("D")) {
			if ("365".equals(period.substring(0, period.length()-1))) {
				return true;
			}
		} else if (period.endsWith("Y")) {
			if ("1".equals(period.substring(0, period.length()-1))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 拼装行程推荐html
	 * @param map
	 * @param articleId
	 * @return 行程推荐html
	 */
	private String getRecommentSchedule(Map<String, String> map, String articleId) {
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"g-guide-pkshop cf\">");
		// 当两款产品保障期限都只有一个并且相同的情况，不需要用户选择行程，不显示行程天数及选择行程的日期
		boolean flag = true;
		DataTable dt = new QueryBuilder("select FactoryValue from PKProductFactoryInfo where RiskCode in (?, ?) and FactoryType = 'Period'", map.get("ProductId1"), map.get("ProductId2")).executeDataTable();
		if (dt != null && dt.getRowCount() == 2) {
			if (dt.getString(0, 0).equals(dt.getString(1, 0))) {
				flag = false;
			} else {
				if (checkYearPeriod(dt.getString(0, 0)) && checkYearPeriod(dt.getString(1, 0))) {
					flag = false;
				}
			}
		}
		String weixiProDetail = " exturl=\"http://www.kaixinbao.com/DGtlink\" vlpageid=\"DGtlink\" onclick=\"javascript:void(0);return(VL_FileDL(this));return false;\" ";
		if (flag) {
			sb.append("<div class=\"pkshop-tit\" ><span id=\"defaultPeriod\">" + map.get("InitPeriod").replace("D", "天") + "行程");
			sb.append("</span><a class=\"g-again-xc\" href=\"javascript:void(0)\" exturl=\"http://www.kaixinbao.com/DGdate\" vlpageid=\"DGdate\" onclick=\"reselectXC();return(VL_FileDL(this));return false;\" >重选行程</a>");
			sb.append("<div class=\"ga-again-time  tagshow cf\" style=\"display:none\">");
			sb.append("<em class=\"ag-jiao-s\"></em><table class=\"ga-again-table\">");
			sb.append("<tr><td class=\"ag-again-td\">出发日期：</td>");
			sb.append("<td><input type=\"text\" onblur=\"isBirthDate('txtStartDay','DayE')\" ");
			sb.append("onfocus=\"ErroClear('DayE')\" onclick=\"WdatePicker({skin:'whyGreen',minDate:'%y-%M-#{%d+1}'})\" ");
			sb.append("name=\"txtStartDay\" class=\"input_selectday\" id=\"txtStartDay\"></td></tr>");
			sb.append("<tr><td class=\"ag-again-td\">结束日期：</td>");
			sb.append("<td><input type=\"text\" onblur=\"isBirthDate('txtEndDay','DayE')\" ");
			sb.append("onfocus=\"ErroClear('DayE')\" class=\"input_selectday\" ");
			sb.append("onclick=\"WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}'})\" ");
			sb.append("id=\"txtEndDay\" name=\"txtEndDay\"></td></tr>");
			sb.append("<tr><td colspan=\"2\"><div id=\"DayE\"style=\"display:none\" class=\"day-error\"></div><a class=\"g-again-ok\" href=\"javascript:void(0)\" exturl=\"http://www.kaixinbao.com/DGbutton\" vlpageid=\"DGbutton\" onclick=\"chooseSchedule('txtStartDay','txtEndDay','"+articleId+"');return(VL_FileDL(this));return false;\">确定</a>");
			sb.append("<span class=\"g-again-no\">取消</span></td></tr></table></div></div>");
		}
		
		sb.append("<div class=\"pk-shopdes\"><em class=\"pk-shop-tips\">推荐一</em>");
		sb.append("<a href=\""+map.get("ProductUrl1")+"\" target=\"_blank\" class=\"pk-shop-tit f_mi\" "+ weixiProDetail + ">");
		sb.append(map.get("ProductName1")+"</a><span class=\"pk-shop-moey f_mi\" name=\"R_Ajax_PKPrice_"+map.get("ProductId1")+"\">￥");
		sb.append(map.get("DiscountPrice1")+"</span><span class=\"pk-shop-ymoney f_mi\" style=\"display:none\" name=\"Clear_Ajax_PKPrice_"+map.get("ProductId1")+"\">");
		if (!map.get("DiscountPrice1").equals(map.get("Prem1"))) {
			sb.append("￥" + map.get("Prem1"));
		}
		sb.append("</span><a href=\""+map.get("ProductUrl1")+"\" target=\"_blank\" rel=\"nofollow\" class=\"pk-shop-go\""+ weixiProDetail + ">去看看</a></div>");
		sb.append("<div class=\"pk-shopdes\"><em class=\"pk-shop-tips\">推荐二</em>");
		sb.append("<a href=\""+map.get("ProductUrl2")+"\" target=\"_blank\" class=\"pk-shop-tit f_mi\""+ weixiProDetail + ">");
		sb.append(map.get("ProductName2")+"</a><span class=\"pk-shop-moey f_mi\" name=\"R_Ajax_PKPrice_"+map.get("ProductId2")+"\">￥");
		sb.append(map.get("DiscountPrice2")+"</span><span class=\"pk-shop-ymoney f_mi\" style=\"display:none\" name=\"Clear_Ajax_PKPrice_"+map.get("ProductId2")+"\">");
		if (!map.get("DiscountPrice2").equals(map.get("Prem2"))) {
			sb.append("￥" + map.get("Prem2"));
		}
		sb.append("</span><a href=\""+map.get("ProductUrl2")+"\" target=\"_blank\" rel=\"nofollow\" class=\"pk-shop-go\""+ weixiProDetail + ">去看看</a></div></div>");

		return sb.toString();
	}
}

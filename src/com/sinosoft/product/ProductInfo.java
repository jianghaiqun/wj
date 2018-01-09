package com.sinosoft.product;

import com.finance.util.JedisCommonUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;

import java.util.Date;

public class ProductInfo extends Page  {

	/**
	 * 页面初始化
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Mapx init(Mapx params) {
		
		return params;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx  initRelaition(Mapx params) {
		
		String IDs = params.get("Id") + "";
		
		if (StringUtil.isNotEmpty(IDs)) {
			DataTable dt = new QueryBuilder(" SELECT ProductName,Remark6 FROM sdproduct WHERE ProductID=?  ",IDs).executeDataTable();
			params.put("ProductName", dt.get(0, 0));
			params.put("ComCode", dt.get(0, 1));
		}
		return params;
	}

	/**
	 * 产品信息查询
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		
		StringBuffer sb = new StringBuffer(" SELECT ProductID Id,ProductID,ProductName,'' Relation,'' BnfRelation,'' Certificate,'' Period,'' appntAge,'''' appntAgeTips,'' FROM sdproduct WHERE IsPublish='Y'  ");
		
		String ProductID = (String) dga.getParams().get("ProductID");
		String ProductName = (String) dga.getParams().get("ProductName");
		
		if(StringUtil.isNotEmpty(ProductID)){
			sb.append(" AND ProductID like '"+ProductID+"%'");
		}
		if(StringUtil.isNotEmpty(ProductName)){
			sb.append(" AND ProductName like '"+ProductName+"%'");
		}
		sb.append(" ORDER BY remark6 DESC");
		

		QueryBuilder qb = new QueryBuilder(sb.toString());

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		for(int i=0;i<dt.getRowCount();i++){
			DataTable dt1 = new QueryBuilder(" SELECT codeName FROM dictionary  WHERE codetype='Relationship' AND productid=?",dt.get(i, 0)).executeDataTable();
			String info = "";
			if(dt1!=null && dt1.getRowCount()>=1){
				for(int j=0;j<dt1.getRowCount();j++){
					info +=" "+dt1.getString(j, 0);
				}
			}
			dt.set(i, "Relation",info );
			
			dt1 = new QueryBuilder(" SELECT codeName FROM dictionary  WHERE codetype='BnfRelationship' AND productid=?",dt.get(i, 0)).executeDataTable();
			info = "";
			if(dt1!=null && dt1.getRowCount()>=1){
				for(int j=0;j<dt1.getRowCount();j++){
					info +=" "+dt1.getString(j, 0);
				}
			}
			dt.set(i, "BnfRelation",info );
			
		    dt1 = new QueryBuilder(" SELECT codeName FROM dictionary  WHERE codetype='Certificate' AND productid=?",dt.get(i, 0)).executeDataTable();
		    info = "";
			if(dt1!=null && dt1.getRowCount()>=1){
				for(int j=0;j<dt1.getRowCount();j++){
					info +=" "+dt1.getString(j, 0);
				}
			}
			dt.set(i, "Certificate",info );
			
			dt1 = new QueryBuilder(" SELECT IFNULL(startPeriod,1),IFNULL(endPeriod,365) FROM productperiod WHERE riskcode=?",dt.get(i, 0)).executeDataTable();
		    info = "";
			if(dt1!=null && dt1.getRowCount()>=1){
				info = dt1.getString(0, 0)+"-"+dt1.getString(0, 1);
			}
			dt.set(i, "Period", info);
			
			dt1 = new QueryBuilder(" SELECT prop1,prop2,memo FROM zdcode WHERE codetype='InsuredDate' and CodeValue = ?",dt.get(i, 0)).executeDataTable();
			info = "";
			String appntAgeTips = "";
			if(dt1!=null && dt1.getRowCount()>=1){
				if(StringUtil.isNotEmpty(dt1.getString(0, 0)) && StringUtil.isNotEmpty(dt1.getString(0, 1))){
					info = dt1.getString(0, 0)+"-"+dt1.getString(0, 1);
				}
				appntAgeTips = dt1.getString(0, 2);
			}
			dt.set(i, "appntAge", info);
			dt.set(i, "appntAgeTips", appntAgeTips);
		}
		dga.bindData(dt);
	}
	/**
	 * 被保人关系查询
	 * 
	 * @param dga
	 */
	public void dg2DataBind(DataGridAction dga) {
		
		
		String Id = (String) dga.getParams().get("Id");

		String ComCode = new QueryBuilder(" SELECT Remark6 FROM sdproduct WHERE ProductID=?  ",Id).executeString();
		QueryBuilder qb =dealData(ComCode,"Relationship");

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	/**
	 * 受益人与被保人关系查询
	 * 
	 * @param dga
	 */
	public void dg10DataBind(DataGridAction dga) {
		
		
		String Id = (String) dga.getParams().get("Id");

		String ComCode = new QueryBuilder(" SELECT Remark6 FROM sdproduct WHERE ProductID=?  ",Id).executeString();
		QueryBuilder qb =dealData(ComCode,"BnfRelationship");

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	/**
	 * 证件类型查询
	 * 
	 * @param dga
	 */
	public void dg3DataBind(DataGridAction dga) {
		
		String Id = (String) dga.getParams().get("Id");
		
		String ComCode = new QueryBuilder(" SELECT Remark6 FROM sdproduct WHERE ProductID=?  ",Id).executeString();

		QueryBuilder qb =dealData(ComCode,"certificate");

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	/**
	 * 淘宝数据查询
	 * 
	 * @param dga
	 */
	public void dg4DataBind(DataGridAction dga) {
		
		String ProductID = (String) dga.getParams().get("ProductID");
		String ProductName = (String) dga.getParams().get("ProductName");
		
		StringBuffer sb = new StringBuffer(" SELECT ERiskID,ProductName,CustomCode,PlanCode,CoverageYear,Period,AgeIssue,TextAge,Gender,PaymentMethods,MAKEDATE FROM jdproductc WHERE 1=1  ");
		
		
		if(StringUtil.isNotEmpty(ProductID)){
			sb.append(" AND ERiskID like '"+ProductID+"%'");
		}
		if(StringUtil.isNotEmpty(ProductName)){
			sb.append(" AND ProductName like '"+ProductName+"%'");
		}
		sb.append(" ORDER BY ERiskID ");
		QueryBuilder qb = new QueryBuilder(sb.toString());

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	//处理基础信息
	public static QueryBuilder dealData(String comCode,String type){
		StringBuffer sb = new StringBuffer(" SELECT ID,codeValue,codeName,CodeEnName FROM dictionary  WHERE codetype=? AND insurancecode=? AND (productid IS NULL OR productid='') order by ID ");
		
		String ComCode = comCode;

		QueryBuilder qb = new QueryBuilder(sb.toString());
		qb.add(type);
		qb.add(ComCode);

		return qb;
	}
	//处理基础信息
	public static QueryBuilder dealtbData(String productid,String comCode,String type,String channelSn){
		
		StringBuffer sb = new StringBuffer(" SELECT ID,codeValue,codeName,tbcodeValue,tbcodeName FROM jdtdictionary  WHERE codetype=? AND insurancecode=? AND (productid IS NULL OR productid='') and remark1 = ? order by ID  ");
		if(StringUtil.isNotEmpty(productid)){
			sb = new StringBuffer(" SELECT ID,codeValue,codeName,tbcodeValue,tbcodeName FROM jdtdictionary  WHERE codetype=? AND productid=? and remark1 = ? order by ID  ");
		}
		
		String ComCode = comCode;

		QueryBuilder qb = new QueryBuilder(sb.toString());
		qb.add(type);
		if(StringUtil.isNotEmpty(productid)){
			qb.add(productid);
		}else{
			qb.add(ComCode);
		}
		qb.add(channelSn);
		
		return qb;
	}
	/**
	 * 投保人关系保存
	 */
	public void addRelation() {
		try {
			String Product = $V("Id");
			String Id = $V("Data");
			Transaction trans = new Transaction();
			int b =Id.split(",").length;
			trans.add(new QueryBuilder("DELETE FROM dictionary  WHERE codetype='Relationship' AND productid=?",Product));
			for (int i = 0; i < b; i++) {

				String id = Id.split(",")[i];
				DataTable dt1 = new QueryBuilder("SELECT * FROM dictionary  WHERE id=?",id).executeDataTable();
				String id_index = NoUtil.getMaxNo("DICTIONARYID")+"";
				
				StringBuffer sb = new StringBuffer(" INSERT INTO dictionary VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
				QueryBuilder qb = new QueryBuilder(sb.toString());
				qb.add(id_index);
				qb.add(dt1.get(0, 1));
				qb.add(dt1.get(0, 2));
				qb.add(dt1.get(0, 3));
				qb.add(dt1.get(0, 4));
				qb.add(dt1.get(0, 5));
				qb.add(dt1.get(0, 6));
				qb.add(dt1.get(0, 7));
				qb.add(dt1.get(0, 8));
				qb.add(dt1.get(0, 9));
				qb.add(Product);
				qb.add(dt1.get(0, 11));
				qb.add("");
				trans.add(qb);
				
			}
			if(!trans.commit()){
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
				return;
			}
			//add by miaozhiqiang start
			//清除产品缓存
			JedisCommonUtil.remove(0, Product+"_BUYINIT");
			//add by miaozhiqiang end
			Response.setStatus(1);
			Response.setMessage("保存成功!");
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
			return;
		}
	}
	/**
	 * 受益人关系保存
	 */
	public void addBnfRelation() {
		try {
			String Product = $V("Id");
			String Id = $V("Data");
			Transaction trans = new Transaction();
			int b =Id.split(",").length;
			trans.add(new QueryBuilder("DELETE FROM dictionary  WHERE codetype='BnfRelationship' AND productid=?",Product));
			for (int i = 0; i < b; i++) {

				String id = Id.split(",")[i];
				DataTable dt1 = new QueryBuilder("SELECT * FROM dictionary  WHERE id=?",id).executeDataTable();
				String id_index = NoUtil.getMaxNo("DICTIONARYID")+"";
				
				StringBuffer sb = new StringBuffer(" INSERT INTO dictionary VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
				QueryBuilder qb = new QueryBuilder(sb.toString());
				qb.add(id_index);
				qb.add(dt1.get(0, 1));
				qb.add(dt1.get(0, 2));
				qb.add(dt1.get(0, 3));
				qb.add(dt1.get(0, 4));
				qb.add(dt1.get(0, 5));
				qb.add(dt1.get(0, 6));
				qb.add(dt1.get(0, 7));
				qb.add(dt1.get(0, 8));
				qb.add(dt1.get(0, 9));
				qb.add(Product);
				qb.add(dt1.get(0, 11));
				qb.add("");
				trans.add(qb);
				
			}
			if(!trans.commit()){
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
				return;
			}
			//add by miaozhiqiang start
			//清除产品缓存
			JedisCommonUtil.remove(0, Product+"_BUYINIT");
			//add by miaozhiqiang end
			Response.setStatus(1);
			Response.setMessage("保存成功!");
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
			return;
		}
	}
	
	/**
	 * 投保人关系保存
	 */
	public void addCertificate() {
		try {
			String Product = $V("Id");
			String Id = $V("Data");
			Transaction trans = new Transaction();
			int b =Id.split(",").length;
			trans.add(new QueryBuilder("DELETE FROM dictionary  WHERE codetype='certificate' AND productid=?",Product));
			for (int i = 0; i < b; i++) {

				String id = Id.split(",")[i];
				DataTable dt1 = new QueryBuilder("SELECT * FROM dictionary  WHERE id=?",id).executeDataTable();
				String id_index = NoUtil.getMaxNo("DICTIONARYID")+"";
				
				StringBuffer sb = new StringBuffer(" INSERT INTO dictionary VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
				QueryBuilder qb = new QueryBuilder(sb.toString());
				qb.add(id_index);
				qb.add(dt1.get(0, 1));
				qb.add(dt1.get(0, 2));
				qb.add(dt1.get(0, 3));
				qb.add(dt1.get(0, 4));
				qb.add(dt1.get(0, 5));
				qb.add(dt1.get(0, 6));
				qb.add(dt1.get(0, 7));
				qb.add(dt1.get(0, 8));
				qb.add(dt1.get(0, 9));
				qb.add(Product);
				qb.add(dt1.get(0, 11));
				qb.add("");
				trans.add(qb);
				
			}
			if(!trans.commit()){
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
				return;
			}
			//add by miaozhiqiang start
			//清除产品缓存
			JedisCommonUtil.remove(0, Product+"_BUYINIT");
			//add by miaozhiqiang end
			Response.setStatus(1);
			Response.setMessage("保存成功!");
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
			return;
		}
	}
	/**
	 * 产品保险期间限制保存
	 */
	public void addPeriod() {
		try {
			String Product = $V("Id");
			String StartDate = $V("StartDate"); 
			String EndDate = $V("EndDate");
			if(StringUtil.isEmpty(StartDate)){
				StartDate = "1";
			}
			if(StringUtil.isEmpty(EndDate)){
				EndDate = "365";
			}
			if(!StringUtil.isDigit(StartDate) || !StringUtil.isDigit(EndDate)){
				Response.setStatus(0);
				Response.setMessage("保险期间必须是数字!");
				return;
			}
			Transaction trans = new Transaction();
			trans.add(new QueryBuilder("DELETE FROM productperiod WHERE RiskCode=?",Product));
			String ComCode = new QueryBuilder(" SELECT Remark6 FROM sdproduct WHERE ProductID=?  ",Product).executeString();
			StringBuffer sb = new StringBuffer(" INSERT INTO productperiod VALUES(?,?,?,?,?,?,?,?) ");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			qb.add(Product);
			qb.add(new Date());
			qb.add(new Date());
			qb.add(ComCode);
			qb.add("");
			qb.add(Product);
			qb.add(StartDate);
			qb.add(EndDate);
			trans.add(qb);
			if(!trans.commit()){
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
				return;
			}
			//add by miaozhiqiang start
			//清除产品缓存
			JedisCommonUtil.remove(0, Product+"_BUYINIT");
			//add by miaozhiqiang end
			Response.setStatus(1);
			Response.setMessage("保存成功!");
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
			return;
		}
	}
	/**
	 * 保险公司基础信息查询
	 * 
	 * @param dga
	 */
	public static void dg5DataBind(DataGridAction dga) {
		
		StringBuffer sb = new StringBuffer(" SELECT Remark6 Id,Remark6,InsureName,'' Relation,'' BnfRelation,'' Certificate,'' Sex FROM sdproduct where 1=1  ");
		
		String ComCode = (String) dga.getParams().get("ComCode");
		String InsureName = (String) dga.getParams().get("InsureName");
		
		if(StringUtil.isNotEmpty(ComCode)){
			sb.append(" AND Remark6 like '"+ComCode+"%'");
		}
		if(StringUtil.isNotEmpty(InsureName)){
			sb.append(" AND InsureName like '"+InsureName+"%'");
		}
		sb.append("  GROUP BY remark6 ORDER BY remark6 DESC");
		

		QueryBuilder qb = new QueryBuilder(sb.toString());

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		for(int i=0;i<dt.getRowCount();i++){
			DataTable dt1 = new QueryBuilder(" SELECT codeName FROM dictionary  WHERE codetype='Relationship' AND (productId IS NULL OR productId='') AND insuranceCode=?",dt.get(i, 0)).executeDataTable();
			String info = "";
			if(dt1!=null && dt1.getRowCount()>=1){
				for(int j=0;j<dt1.getRowCount();j++){
					info +=" "+dt1.getString(j, 0);
				}
			}
			dt.set(i, "Relation",info );
			
			dt1 = new QueryBuilder(" SELECT codeName FROM dictionary  WHERE codetype='BnfRelationship' AND (productId IS NULL OR productId='') AND insuranceCode=?",dt.get(i, 0)).executeDataTable();
			info = "";
			if(dt1!=null && dt1.getRowCount()>=1){
				for(int j=0;j<dt1.getRowCount();j++){
					info +=" "+dt1.getString(j, 0);
				}
			}
			dt.set(i, "BnfRelation",info );
			
		    dt1 = new QueryBuilder(" SELECT codeName FROM dictionary  WHERE codetype='Certificate' AND (productId IS NULL OR productId='') AND insuranceCode=?",dt.get(i, 0)).executeDataTable();
		    info = "";
			if(dt1!=null && dt1.getRowCount()>=1){
				for(int j=0;j<dt1.getRowCount();j++){
					info +=" "+dt1.getString(j, 0);
				}
			}
			dt.set(i, "Certificate",info );
			
			dt1 = new QueryBuilder(" SELECT codeName FROM dictionary  WHERE codetype='Sex' AND (productId IS NULL OR productId='') AND insuranceCode=?",dt.get(i, 0)).executeDataTable();
		    info = "";
			if(dt1!=null && dt1.getRowCount()>=1){
				for(int j=0;j<dt1.getRowCount();j++){
					info +=" "+dt1.getString(j, 0);
				}
			}
			dt.set(i, "Sex", info);
		}
		dga.bindData(dt);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx initbase(Mapx params) {
		params.put("BaseTypeList", HtmlUtil.codeToOptions("BaseTypeTB", false,"Sex"));
		params.put("TravelAddress", HtmlUtil.codeToOptions("YesOrNo", true));
		params.put("TravelShenGen", HtmlUtil.codeToOptions("YesOrNo", true));
		String IDs = params.get("Id") + "";
		
		if (StringUtil.isNotEmpty(IDs)) {
			DataTable dt = new QueryBuilder(" SELECT Remark6,InsureName FROM sdproduct WHERE Remark6=?  ",IDs).executeDataTable();
			params.put("InsureName", dt.get(0, 1));
			params.put("ComCode", dt.get(0, 0));
		}
		return params;
	}
	/**
	 * 证件类型查询
	 * 
	 * @param dga
	 */
	public void dg6DataBind(DataGridAction dga) {
		
		String Id = (String) dga.getParams().get("Id");

		String type = $V("BaseInfoType");
		
		if(StringUtil.isEmpty(type)){
			type="Sex";
		}
		QueryBuilder qb =dealData(Id,type);

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	/**
	 * 基本信息配置保存
	 */
	public void addBaseInfo() {
		try {
			String ComCode = $V("Id");
			String codeType = $V("BaseInfoType");
			Transaction trans = new Transaction();
			DataTable dt = Request.getDataTable("Data");
			int b = dt.getRowCount();
			trans.add(new QueryBuilder("DELETE FROM dictionary  WHERE codetype=? AND insuranceCode=? AND (productId IS NULL OR productId='')",codeType,ComCode));
			for (int i = 0; i < b; i++) {
				DataRow dr = dt.getDataRow(i);
				String codeValue=dr.getString("CodeValue");
				String codeName=dr.getString("CodeName");
				String codeEnName=dr.getString("CodeEnName");
				String id_index = NoUtil.getMaxNo("DICTIONARYID")+"";
				
				StringBuffer sb = new StringBuffer(" INSERT INTO dictionary VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
				QueryBuilder qb = new QueryBuilder(sb.toString());
				qb.add(id_index);
				qb.add(new Date());
				qb.add(new Date());
				qb.add(codeEnName);
				qb.add(codeName);
				qb.add(codeType);
				qb.add(new QueryBuilder(" SELECT CodeName FROM zdcode WHERE codetype='BaseType' AND codevalue=? ",codeType).executeString());
				qb.add(codeValue);
				qb.add(ComCode);
				qb.add("");
				qb.add("");
				qb.add("");
				qb.add("");
				trans.add(qb);
				
			}
			if(!trans.commit()){
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
				return;
			}
			Response.setStatus(1);
			Response.setMessage("保存成功!");
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
			return;
		}
	}
	/**
	 * 淘宝数据同步
	 * 证件类型、性别、与投保人关系、是否录入单选旅游目的地
	 */
	public void tbdatasyn() {
		try {
			String ComCode = $V("Id"); //保险公司编码
			String channelSn = $V("channelSn"); //保险公司编码
			String codeType = $V("BaseInfoType");//同步数据类型：证件类型、性别、关系
			String TBProductID = $V("TBProductID");//淘宝同步产品
			String REProductID = $V("REProductID");//关联主站产品ID
			String traveladdress = $V("TravelAddress");//产品但旅游目的地信息
			String TravelShenGen = $V("TravelShenGen");//多选旅游目的地是否含有申根字样
			String channel_sql = "taobao";//默认取淘宝的编码
			if(StringUtil.isNotEmpty(channelSn)&&"QN".equals(channelSn)){
				channel_sql="qunar";
			}
			
			String AREProductID = TBProductID;
			if(StringUtil.isNotEmpty(REProductID)){
				AREProductID = REProductID;
			}
			Transaction trans = new Transaction();
			//是否同步产品
			boolean synproduct = true;
			if(StringUtil.isEmpty(TBProductID)){
				synproduct = false;
			}
			if(!synproduct){
				DataTable dt = new QueryBuilder("SELECT * FROM dictionary  WHERE codetype=? AND insuranceCode=? AND (productId IS NULL OR productId='')",codeType,ComCode).executeDataTable();
				int b = dt.getRowCount();
				trans.add(new QueryBuilder("DELETE FROM jdtdictionary  WHERE codetype=? AND insuranceCode=? and remark1='"+channelSn+"' AND (productId IS NULL OR productId='')",codeType,ComCode));
				for (int i = 0; i < b; i++) {
					DataRow dr = dt.getDataRow(i);
					String id_index = NoUtil.getMaxNo("DICTIONARYID")+"";
					
					StringBuffer sb = new StringBuffer(" INSERT INTO jdtdictionary VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
					QueryBuilder qb = new QueryBuilder(sb.toString());
					
					DataTable dt1 = new QueryBuilder("SELECT codevalue,codename FROM dictionary WHERE codetype='"+codeType+"' AND insuranceCode='"+channel_sql+"' AND codename = '"+dr.getString(4)+"' ").executeDataTable();
					String tbcode = "";
					String tbname = "";
					if(dt1!=null && dt1.getRowCount()>=1){
						tbcode = dt1.getString(0, 0);
						tbname = dt1.getString(0, 1);
					}
					if(StringUtil.isEmpty(tbcode)){
						continue;
					}
					if("certificate".equals(codeType) && "其他".equals(dr.getString(4)) && !"QN".equals(channelSn) ){
						qb.add(id_index);
						qb.add(new Date());
						qb.add(new Date());
						qb.add(dr.getString(5));
						qb.add(dr.getString(6));
						qb.add("3");
						qb.add("回乡证");
						qb.add(dr.getString(7));
						qb.add(dr.getString(4));
						qb.add(dr.getString(8));
						qb.add(dr.getString(10));
						qb.add("");
						qb.add(channelSn);
						qb.add("");
						qb.add("");
						trans.add(qb);
						
						qb = new QueryBuilder(sb.toString());
						id_index = NoUtil.getMaxNo("DICTIONARYID")+"";
						qb.add(id_index);
						qb.add(new Date());
						qb.add(new Date());
						qb.add(dr.getString(5));
						qb.add(dr.getString(6));
						qb.add("4");
						qb.add("台胞证");
						qb.add(dr.getString(7));
						qb.add(dr.getString(4));
						qb.add(dr.getString(8));
						qb.add(TBProductID);
						qb.add("");
						qb.add(channelSn);
						qb.add("");
						qb.add("");
						trans.add(qb);
						
					}else{
						qb.add(id_index);
						qb.add(new Date());
						qb.add(new Date());
						qb.add(dr.getString(5));
						qb.add(dr.getString(6));
						qb.add(tbcode);
						qb.add(tbname);
						qb.add(dr.getString(7));
						qb.add(dr.getString(4));
						qb.add(dr.getString(8));
						qb.add(TBProductID);
						if("Y".equals(TravelShenGen) && "CountryCode".equals(codeType)){
							qb.add(dr.getString(7));
						}else{
							qb.add("");
						}
						qb.add(channelSn);
						qb.add("");
						qb.add("");
						trans.add(qb);
					}
					
					
				}
			}else{
				DataTable dt = new QueryBuilder("SELECT * FROM dictionary  WHERE codetype=? AND productId=? ",codeType,AREProductID).executeDataTable();
				int b = dt.getRowCount();
				trans.add(new QueryBuilder("DELETE FROM jdtdictionary  WHERE codetype=? AND productId=?",codeType,TBProductID));
				for (int i = 0; i < b; i++) {
					DataRow dr = dt.getDataRow(i);
					String id_index = NoUtil.getMaxNo("DICTIONARYID")+"";
					
					StringBuffer sb = new StringBuffer(" INSERT INTO jdtdictionary VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
					QueryBuilder qb = new QueryBuilder(sb.toString());
					
					DataTable dt1 = new QueryBuilder("SELECT codevalue,codename FROM dictionary WHERE codetype='"+codeType+"' AND insuranceCode='"+channel_sql+"' AND codename = '"+dr.getString(4)+"' ").executeDataTable();
					String tbcode = "";
					String tbname = "";
					if(dt1!=null && dt1.getRowCount()>=1){
						tbcode = dt1.getString(0, 0);
						tbname = dt1.getString(0, 1);
					}
					if(StringUtil.isEmpty(tbcode)){
						continue;
					}
					if("certificate".equals(codeType) && "其他".equals(dr.getString(4))){
						qb.add(id_index);
						qb.add(new Date());
						qb.add(new Date());
						qb.add(dr.getString(5));
						qb.add(dr.getString(6));
						qb.add("3");
						qb.add("回乡证");
						qb.add(dr.getString(7));
						qb.add(dr.getString(4));
						qb.add(dr.getString(8));
						qb.add(TBProductID);
						qb.add("");
						qb.add(channelSn);
						qb.add("");
						qb.add("");
						trans.add(qb);
						
						qb = new QueryBuilder(sb.toString());
						id_index = NoUtil.getMaxNo("DICTIONARYID")+"";
						qb.add(id_index);
						qb.add(new Date());
						qb.add(new Date());
						qb.add(dr.getString(5));
						qb.add(dr.getString(6));
						qb.add("4");
						qb.add("台胞证");
						qb.add(dr.getString(7));
						qb.add(dr.getString(4));
						qb.add(dr.getString(8));
						qb.add(TBProductID);
						qb.add("");
						qb.add(channelSn);
						qb.add("");
						qb.add("");
						trans.add(qb);
						
					}else{
						qb.add(id_index);
						qb.add(new Date());
						qb.add(new Date());
						qb.add(dr.getString(5));
						qb.add(dr.getString(6));
						qb.add(tbcode);
						qb.add(tbname);
						qb.add(dr.getString(7));
						qb.add(dr.getString(4));
						qb.add(dr.getString(8));
						qb.add(TBProductID);
						if("Y".equals(TravelShenGen) && "CountryCode".equals(codeType)){
							qb.add(dr.getString(7));
						}else{
							qb.add("");
						}
						qb.add(channelSn);
						qb.add("");
						qb.add("");
						trans.add(qb);
					}
					
				}
				//处理产品单旅游目的地信息
				if(StringUtil.isNotEmpty(traveladdress)){
					trans.add(new QueryBuilder("DELETE FROM jdcode WHERE codetype='TBDestFromDataBase' AND `CODE` =?",TBProductID));
					if("Y".equals(traveladdress)){
						StringBuffer sb = new StringBuffer("INSERT INTO jdcode(CodeType,CODE) VALUES(?,?)");
						QueryBuilder qb = new QueryBuilder(sb.toString());
						qb.add("TBDestFromDataBase");
						qb.add(TBProductID);
						trans.add(qb);
					}
				}

			}
			
			//针对单独上淘宝的产品 需要特殊处理，供淘宝核保试用
			if(StringUtil.isNotEmpty(AREProductID) && StringUtil.isNotEmpty(TBProductID) && !TBProductID.equals(AREProductID)){
				
				trans.add(new QueryBuilder("DELETE FROM dictionary  WHERE codetype=? AND productId=?",codeType,TBProductID));
				DataTable dt = new QueryBuilder("SELECT * FROM dictionary  WHERE codetype=? AND productId=? ",codeType,AREProductID).executeDataTable();
				StringBuffer sb = new StringBuffer(" INSERT INTO dictionary VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
				if(dt!=null && dt.getRowCount()>=1){
					for(int k=0;k<dt.getRowCount();k++){
						QueryBuilder qb = new QueryBuilder(sb.toString());
						String id_index = NoUtil.getMaxNo("DICTIONARYID")+"";
						qb.add(id_index);
						qb.add(dt.getString(k, 1));
						qb.add(dt.getString(k, 2));
						qb.add(dt.getString(k, 3));
						qb.add(dt.getString(k, 4));
						qb.add(dt.getString(k, 5));
						qb.add(dt.getString(k, 6));
						qb.add(dt.getString(k, 7));
						qb.add(dt.getString(k, 8));
						qb.add(dt.getString(k, 9));
						qb.add(TBProductID);
						qb.add(dt.getString(k, 11));
						qb.add("");
						trans.add(qb);
					}
				}
			}
			if(!trans.commit()){
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
				return;
			}
			Response.setStatus(1);
			Response.setMessage("保存成功!");
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
			return;
		}
	}
	/**
	 * 淘宝信息查询
	 * 
	 * @param dga
	 */
	public void dg7DataBind(DataGridAction dga) {
		
		String Id = (String) dga.getParams().get("Id");
		
		String productid = $V("TBProductID");

		String type = $V("BaseInfoType");
		
		String channelSn = $V("channelSn");
		
		if(StringUtil.isEmpty(type)){
			type="Sex";
		}
		QueryBuilder qb =dealtbData(productid,Id,type,channelSn);

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	/**
	 * 主站旅游目的地信息查询
	 * 
	 * @param dga
	 */
	public void dg8DataBind(DataGridAction dga) {
		
		
		String productid = $V("ProductID");
		String ComCode = $V("ComCode");

		StringBuffer sb = new StringBuffer(" SELECT ProductID,InsuranceCode,CodeValue,CodeName,CodeEnName,FlagType,ComFlag,CreateDate,DefFlag FROM dictionary WHERE codetype='CountryCode'  ");
		
		if(StringUtil.isNotEmpty(productid)){
			sb.append(" AND productId like '"+productid+"%'");
		}else if(StringUtil.isNotEmpty(ComCode)){
			sb.append(" AND insuranceCode like '"+ComCode+"%' ");
		}
		sb.append(" ORDER BY ProductID");
		QueryBuilder qb = new QueryBuilder(sb.toString());

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	/**
	 * 主站职业信息查询
	 * 
	 * @param dga
	 */
	public void dg9DataBind(DataGridAction dga) {
		
		
		String ComCode = $V("ComCode");

		StringBuffer sb = new StringBuffer(" SELECT b.ID,b.CODE,b.NAME,b.Parent_id,(SELECT a.name FROM occupation a WHERE a.id=b.Parent_id LIMIT 1 ) Parent_Name,b.grade,b.Insurancecompany FROM occupation b WHERE 1=1 ");
		
		if(StringUtil.isNotEmpty(ComCode)){
			sb.append(" AND b.insurancecompany='"+ComCode+"'");
		}
		sb.append(" ORDER BY b.ID ");
		QueryBuilder qb = new QueryBuilder(sb.toString());

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	/**
	 * 投保人年龄范围保存
	 */
	public void addAppntAge() {
		try {
			String productId = $V("Id");
			String minAge = $V("minAge"); 
			String maxAge = $V("maxAge");
			String appntAgeTips = $V("appntAgeTips");
			Transaction trans = new Transaction();
			trans.add(new QueryBuilder("DELETE FROM zdcode WHERE codetype='InsuredDate' and CodeValue = ?",productId));
			if(StringUtil.isEmpty(minAge) && StringUtil.isEmpty(maxAge) && StringUtil.isEmpty(appntAgeTips)){
				if(!trans.commit()){
					Response.setStatus(0);
					Response.setMessage("保存失败，操作数据库时发生错误!");
					return;
				}
				Response.setStatus(1);
				Response.setMessage("保存成功!");
				return;
			}else{
				if(!(StringUtil.isNotEmpty(minAge) && StringUtil.isNotEmpty(maxAge) && StringUtil.isNotEmpty(appntAgeTips))){
					Response.setStatus(0);
					Response.setMessage("请补全未填项或全部置为空!");
					return;
				}
			}
			if(!StringUtil.isDigit(minAge) || !StringUtil.isDigit(maxAge)){
				Response.setStatus(0);
				Response.setMessage("投保人年龄必须是数字!");
				return;
			}
			if(Double.valueOf(minAge).compareTo(Double.valueOf(maxAge)) > 0){
				Response.setStatus(0);
				Response.setMessage("最小年龄不能大于最大年龄!");
				return;
			}
			StringBuffer sb = new StringBuffer("INSERT INTO `zdcode` (`CodeType`, `ParentCode`, `CodeValue`, `CodeName`, `CodeOrder`, `prop1`, `prop2`, `Prop3`, `Prop4`, `Memo`, `AddTime`, `AddUser`, `ModifyTime`, `ModifyUser`) "
					+ "VALUES ('InsuredDate', 'InsuredDate', ?, '', '0', ?, ?, NULL, NULL, ?, now(), '', NULL, NULL);");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			qb.add(productId);
			qb.add(minAge);
			qb.add(maxAge);
			qb.add(appntAgeTips);
			trans.add(qb);
			if(!trans.commit()){
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
				return;
			}
			Response.setStatus(1);
			Response.setMessage("保存成功!");
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
			return;
		}
	}
}

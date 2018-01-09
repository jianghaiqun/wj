package com.sinosoft.platform;

import java.util.ArrayList;
import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ChannelCostsSchema;
import com.sinosoft.schema.ChannelCostsSet;
import com.sinosoft.schema.MarketingChannelsSchema;
import com.sinosoft.schema.MarketingChannelsSet;
import com.sinosoft.schema.MarketingConfigSchema;
import com.sinosoft.schema.MarketingConfigSet;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;

public class Marking extends Page {
	private static ArrayList wrongList = new ArrayList();

	public static Mapx init(Mapx params) {
		// 下拉框
		Mapx map = new Mapx();
		String maxNM = NoUtil.getMaxID("ArticleLogID") + "";
		map.put("AdvertisementNM", maxNM);
		params.put("Markingzd", HtmlUtil.codeToOptions("Marking.Search", true));// 所属机构
		params.put("Markingkg", HtmlUtil.codeToOptions("Marking.Product", true));// 类别
		params.put("ProductCategory",HtmlUtil.codeToOptions("ProductCategory", true));// 细类
		params.put("ChannelTypeCode",HtmlUtil.codeToOptions("ChannelTypeCode", true));// 渠道类别
		params.put("CompanyName", HtmlUtil.codeToOptions("CompanyName", true));// 所属公司
		params.put("AdvTypeCode", HtmlUtil.codeToOptions("AdvTypeCode", true));// 广告类型
		params.put("ShowFormCode", HtmlUtil.codeToOptions("ShowFormCode", true));// 展现形式
		params.put("ChannelName", HtmlUtil.codeToOptions("ChannelName", true));// 渠道名称
		return params;
	}	
	public static Mapx initDialog(Mapx params) {
		String Serial = params.getString("Serial");
		if (StringUtil.isNotEmpty(Serial)) {
			MarketingChannelsSchema MarketingChannelsSchemas = new MarketingChannelsSchema();
			//MarketingChannelsSchemas.setSerial(Serial);
			MarketingChannelsSet MarketingSet = MarketingChannelsSchemas.query(new QueryBuilder("where Serial ='" + Serial + "'"));
			MarketingChannelsSchemas = MarketingSet.get(0);
			Mapx map = new Mapx();		
			String ConfigSerial = MarketingChannelsSchemas.getConfigSerial();				
			String SendAddress = MarketingChannelsSchemas.getSendAddress();
			MarketingConfigSchema MarketingConfigSchemas = new MarketingConfigSchema();	
			//MarketingConfigSchemas.setSerial(ConfigSerial);
			MarketingConfigSet MarketingConfigSets = MarketingConfigSchemas.query(new QueryBuilder("where Serial ='" + ConfigSerial + "'"));
			MarketingConfigSchemas = MarketingConfigSets.get(0);
			String LandingPage = MarketingConfigSchemas.getLandingPage();
			String Remark = MarketingChannelsSchemas.getRemark();
			String CompanyName = MarketingChannelsSchemas.getCompanyName();
			map.put("LandingPage",LandingPage);
			map.put("SendAddress",SendAddress);
			map.put("Remark",Remark);
			map.put("CompanyName", CompanyName);
			map.put("TerminalCode", HtmlUtil.codeToOptions("Marking.Product",MarketingChannelsSchemas.getTerminalCode()));
			map.put("ProductMajorCategory", HtmlUtil.codeToOptions("Marking.Product",MarketingChannelsSchemas.getProductMajorCategory()));
			map.put("ProductCategory", HtmlUtil.codeToOptions("ProductCategory", MarketingChannelsSchemas.getProductCategory()));
			map.put("ChannelTypeCode", HtmlUtil.codeToOptions("ChannelTypeCode", MarketingChannelsSchemas.getChannelTypeCode()));
			map.put("AdvTypeCode", HtmlUtil.codeToOptions("AdvTypeCode",MarketingChannelsSchemas.getAdvTypeCode()));
			map.put("ShowFormCode", HtmlUtil.codeToOptions("ShowFormCode",MarketingChannelsSchemas.getShowFormCode()));
			map.put("ChannelNameCode", HtmlUtil.codeToOptions("ChannelName", MarketingChannelsSchemas.getChannelNameCode()));
			map.put("Serial", Serial);
			map.put("ConfigSerial",ConfigSerial);

			return map;
		} else {
			return params;
		}
	}
	public void AdvertisementNM() {
		String maxNM = NoUtil.getMaxID("ArticleLogID") + "";
		$S("maxNM", maxNM);
	}

	public void GenerationNM() {
		String TerminalCode = Request.getString("TerminalCode");// 营销终端
		String ProductMajorCategory = Request.getString("ProductMajorCategory");// 产品大类：
		String ProductCategory = Request.getString("ProductCategory");// 产品细类：
		String ChannelTypeCode = Request.getString("ChannelTypeCode");// 渠道类型
		String AdvTypeCode = Request.getString("AdvTypeCode");// 广告类型：
		String ShowFormCode = Request.getString("ShowFormCode");// 展现形式
		String AdvertisementNM = Request.getString("AdvertisementNM");// 广告流水号
		String ChannelNameCode = Request.getString("ChannelNameCode");// 渠道名称
		String CompanyName = Request.getString("CompanyName");// 渠道公司名称
		QueryBuilder QueryBuilder1 = new QueryBuilder(
				"Select codevalue from zdcode where codename = '"
						+ TerminalCode + "'");
		QueryBuilder QueryBuilder2 = new QueryBuilder(
				"Select codevalue from zdcode where codename = '"
						+ ProductMajorCategory + "'");
		QueryBuilder QueryBuilder3 = new QueryBuilder(
				"Select codevalue from zdcode where codename = '"
						+ ProductCategory + "'");
		QueryBuilder QueryBuilder4 = new QueryBuilder(
				"Select codevalue from zdcode where codename = '"
						+ ChannelTypeCode + "'");
		QueryBuilder QueryBuilder5 = new QueryBuilder(
				"Select codevalue from zdcode where codename = '" 
		                + AdvTypeCode+ "'");
		QueryBuilder QueryBuilder6 = new QueryBuilder(
				"Select codevalue from zdcode where codename = '"
						+ ShowFormCode + "'");
		QueryBuilder QueryBuilder7 = new QueryBuilder(
				"Select codevalue from zdcode where codename = '"
						+ ChannelNameCode + "'");
		QueryBuilder QueryBuilder8 = new QueryBuilder(
				"Select codevalue from zdcode where codename = '" + CompanyName
						+ "'");

		String TerminalCodef = QueryBuilder1.executeString();
		String ProductMajorCategoryf = QueryBuilder2.executeString();
		String ProductCategoryf = QueryBuilder3.executeString();
		String ChannelTypeCodef = QueryBuilder4.executeString();// 渠道类型代码
		String AdvTypeCodef = QueryBuilder5.executeString();// 广告类型代码
		String ShowFormCodef = QueryBuilder6.executeString();// 展现形式代码
		String ChannelNameCodef = QueryBuilder7.executeString();// 渠道名称代码
		String CompanySerial = QueryBuilder8.executeString();// 渠道公司编码

		if (TerminalCodef == null) {
			TerminalCodef = "";// 营销终端代码
		}
		if (ProductMajorCategoryf == null) {
			ProductMajorCategoryf = "";// 产品大类代码
		}
		if (ProductCategoryf == null) {
			ProductCategoryf = "";// 产品细类代码
		}

		if (ChannelTypeCodef == null) {
			ChannelTypeCodef = "";// 渠道类型代码
		}
		if (AdvTypeCodef == null) {
			AdvTypeCodef = "";// 广告类型代码
		}

		if (ShowFormCodef == null) {
			ShowFormCodef = "";// 展现形式代码
		}
		if (ChannelNameCodef == null) {
			ChannelNameCodef = "";// 渠道名称代码
		}
		if (CompanySerial == null) {
			CompanySerial = "";// 渠道公司编码
		}
		String ChannelCode1 = TerminalCodef + ProductMajorCategoryf
				+ ProductCategoryf + ChannelTypeCodef + AdvTypeCodef
				+ ShowFormCodef + AdvertisementNM;
		String ChannelCodeHiden = NoUtil.getMaxID("ChannelCodeHiden") + "";
		$S("ChannelCode", ChannelCode1);
		$S("ChannelCodeHiden", ChannelCodeHiden);
		$S("TerminalCodef", TerminalCodef);
		$S("ProductMajorCategoryf", ProductMajorCategoryf);
		$S("ProductCategoryf", ProductCategoryf);
		$S("ChannelTypeCodef", ChannelTypeCodef);
		$S("AdvTypeCodef", AdvTypeCodef);
		$S("ShowFormCodef", ShowFormCodef);
		$S("ChannelNameCodef", ChannelNameCodef);
		$S("CompanySerial", CompanySerial);

	}
	public void add() {
		String ChannelCode = Request.getString("ChannelCode");//渠道编码
		String SendAddress =Request.getString("SendAddress"); //投放地址
		//String CompanySerial = "";//渠道公司编码
		String CompanyName = Request.getString("CompanyName");//渠道公司名称
		String Remark = Request.getString("Remark");//备注
		String AdvSerial =Request.getString("AdvSerial"); //广告编号
		String TerminalCode = Request.getString("TerminalCode");//营销终端代码
		String ProductMajorCategory = Request.getString("ProductMajorCategory");//产品大类代码
		String ProductCategory = Request.getString("ProductCategory");//产品细类代码
		String ChannelTypeCode =Request.getString("ChannelTypeCode"); //渠道类型代码
		String AdvTypeCode = Request.getString("AdvTypeCode");//广告类型代码
		String ShowFormCode = Request.getString("ShowFormCode");//展现形式代码
		String ChannelNameCode = Request.getString("ChannelNameCode");//渠道名称代码
		
		/*String TerminalName = "";//营销终端名称
		String ProductMajorCategoryName = "";//产品大类名称
		String ProductCategoryName = "";//产品细类名称
		String ChannelTypeName = "";//渠道类型名称
		String AdvTypeName = "";//广告类型名称
		String ShowFormName = "";//展现形式名称
		String ChannelName = "";//渠道名称
		String Insurancecompany =""; //保险公司名称
		String ChannelCodeHiden = "";//渠道隐藏编码
*/		
		QueryBuilder QueryBuilderTer = new QueryBuilder();
		QueryBuilderTer.setSQL("Select codename from zdcode where codevalue = '"+TerminalCode+"' and codetype = 'Marking.Search'");
		String TerminalName = QueryBuilderTer.executeString();//营销终端名称
		
		QueryBuilder QueryBuilderPrc = new QueryBuilder();
		QueryBuilderPrc.setSQL("Select codename from zdcode where codevalue = '"+ProductMajorCategory+"' and codetype = 'Marking.Product'");
		String ProductMajorCategoryName = QueryBuilderPrc.executeString();//产品大类名称
		
		QueryBuilder QueryBuilderPrx = new QueryBuilder();
		QueryBuilderPrx.setSQL("Select codename from zdcode where codevalue = '"+ProductCategory+"' and codetype = 'ProductCategory'");
		String ProductCategoryName = QueryBuilderPrx.executeString();//产品细类名称

		QueryBuilder QueryBuilderCt = new QueryBuilder();
		QueryBuilderCt.setSQL("Select codename from zdcode where codevalue = '"+ChannelTypeCode+"' and codetype = 'ChannelTypeCode'");
		String ChannelTypeName = QueryBuilderCt.executeString();//渠道类型名称
		
		QueryBuilder QueryBuilderAt = new QueryBuilder();
		QueryBuilderAt.setSQL("Select codename from zdcode where codevalue = '"+AdvTypeCode+"' and codetype = 'AdvTypeCode'");
		String AdvTypeName = QueryBuilderAt.executeString();//广告类型名称
		
		QueryBuilder QueryBuilderSf = new QueryBuilder();
		QueryBuilderSf.setSQL("Select codename from zdcode where codevalue = '"+ShowFormCode+"' and codetype = 'ShowFormCode'");
		String ShowFormName = QueryBuilderSf.executeString();//展现形式名称
		
		QueryBuilder QueryBuilderCn = new QueryBuilder();
		QueryBuilderCn.setSQL("Select codename from zdcode where codevalue = '"+ChannelNameCode+"' and codetype = 'ChannelName'");
		String ChannelName = QueryBuilderCn.executeString();//渠道名称
		
		QueryBuilder QueryBuilderComN = new QueryBuilder();
		QueryBuilderComN.setSQL("Select codevalue from zdcode where codename = '"+CompanyName+"' and codetype = 'CompanyName'");
		String CompanySerial = QueryBuilderComN.executeString();//渠道公司编码
		
		
		
		
		String MarketingChannelsID = NoUtil.getMaxID("MarketingChannelsID")+ "";
		String MarketingConfigID = NoUtil.getMaxID("MarketingConfigID") + "";
		Date dt = new Date();

		MarketingConfigSchema MarketingConfig = new MarketingConfigSchema();
		MarketingConfig.setSerial(MarketingConfigID);
		MarketingConfig.setLandingPage(Request.getString("LandingPage"));
		MarketingConfig.setAddTime(dt);

		if (!MarketingConfig.insert()) {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
		
		MarketingChannelsSchema MarketingChannel = new MarketingChannelsSchema();
		MarketingChannel.setSerial(MarketingChannelsID);
		MarketingChannel.setConfigSerial(MarketingConfigID);
		MarketingChannel.setChannelCode(Request.getString("ChannelCode"));//渠道编码
		MarketingChannel.setSendAddress(Request.getString("SendAddress"));//投放地址
		MarketingChannel.setCompanySerial(CompanySerial);//渠道公司编码
		MarketingChannel.setCompanyName(Request.getString("CompanyName"));//渠道公司名称
		MarketingChannel.setRemark(Request.getString("Remark"));//备注
		MarketingChannel.setAdvSerial(Request.getString("AdvertisementNM"));//广告编号
		MarketingChannel.setTerminalCode(Request.getString("TerminalCode"));//营销终端代码
		MarketingChannel.setProductMajorCategory(Request.getString("ProductMajorCategory"));//产品大类代码
		MarketingChannel.setProductCategory(Request.getString("ProductCategory"));//产品细类代码
		MarketingChannel.setChannelTypeCode(Request.getString("ChannelTypeCode"));//渠道类型代码
		MarketingChannel.setAdvTypeCode(Request.getString("AdvTypeCode"));//广告类型代码
		MarketingChannel.setShowFormCode(Request.getString("ShowFormCode"));//展现形式代码
		MarketingChannel.setChannelNameCode(Request.getString("ChannelNameCode"));//渠道名称代码
		
		MarketingChannel.setTerminalName(TerminalName);//营销终端名称
		MarketingChannel.setProductMajorCategoryName(ProductMajorCategoryName);//产品大类名称
		MarketingChannel.setProductCategoryName(ProductCategoryName);//产品细类名称
		MarketingChannel.setChannelTypeName(ChannelTypeName);//渠道类型名称
		MarketingChannel.setAdvTypeName(AdvTypeName);//广告类型名称
		MarketingChannel.setShowFormName(ShowFormName);//展现形式名称
		MarketingChannel.setChannelName(ChannelName);//渠道名称
		MarketingChannel.setInsurancecompany(Request.getString("Insurancecompany"));//保险公司名称
		MarketingChannel.setChannelCodeHiden(Request.getString("ChannelCodeHiden"));//渠道隐藏编码
		

		if (!MarketingChannel.insert()) {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
		Response.setStatus(1);
		Response.setMessage("添加成功！");
	}

	public static void dg1DataBind(DataGridAction dga) {

		String TerminalCode = (String) dga.getParams().get("Markingzd");
		String ProductCategory = (String) dga.getParams().get("ProductCategory");
		String ProductName = (String) dga.getParams().get("ProductName");
		String Componyname = (String) dga.getParams().get("Componyname");
		String ChannelTypeCode = (String) dga.getParams().get("ChannelTypeCode");
		String ChannelCompany = (String) dga.getParams().get("ChannelCompany");
		String AdvTypeCode = (String) dga.getParams().get("AdvTypeCode");
		String ShowFormCode = (String) dga.getParams().get("ShowFormCode");
		String startDate = (String) dga.getParams().get("StartDate");
		String endDate = (String) dga.getParams().get("EndDate");
		QueryBuilder qb = new QueryBuilder(
				"select a.Serial,a.ConfigSerial,a.AdvSerial,a.Remark,b.LandingPage,a.SendAddress,a.TerminalName,a.Insurancecompany,a.ProductMajorCategoryName,a.ChannelTypeName,a.CompanyName,a.AdvTypeName,a.ShowFormName from (MarketingChannels  a) left JOIN MarketingConfig  b on a.ConfigSerial = b.Serial"
						+ " where 1=1");
		if (StringUtil.isNotEmpty(TerminalCode)) {
			qb.append(" and TerminalCode = ? ", TerminalCode);
		}
		if (StringUtil.isNotEmpty(startDate)) {
			startDate += " 00:00:00";
			qb.append(" and publishdate >= ? ", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			endDate += " 23:59:59";
			qb.append(" and publishdate <= ? ", endDate);
		}

		if (StringUtil.isNotEmpty(ProductCategory)) {
			qb.append(" and ProductCategory=?", ProductCategory);
		}
		if (StringUtil.isNotEmpty(ProductName)) {
			qb.append(" and ProductName=?", ProductName);
		}
		if (StringUtil.isNotEmpty(Componyname)) {
			qb.append(" and Componyname=?", Componyname);
		}
		if (StringUtil.isNotEmpty(ChannelTypeCode)) {
			qb.append(" and ChannelTypeCode=?", ChannelTypeCode);
		}
		if (StringUtil.isNotEmpty(ChannelCompany)) {
			qb.append(" and ChannelCompany=?", ChannelCompany);
		}
		if (StringUtil.isNotEmpty(AdvTypeCode)) {
			qb.append(" and AdvTypeCode=?", AdvTypeCode);
		}
		if (StringUtil.isNotEmpty(ShowFormCode)) {
			qb.append(" and ShowFormCode=?", ShowFormCode);
		}
		
		qb.append(dga.getSortString());		
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		/*if (dt != null && dt.getRowCount() > 0) {
			dt.getDataColumn("PublishDate").setDateFormat("yy-MM-dd HH:mm");
		}*/
		/*dt.insertColumn("AdvSerial");
		dt.insertColumn("Remark");
		dt.insertColumn("SendAddress");
		dt.insertColumn("Landing");
		dt.insertColumn("uv");
		dt.insertColumn("pv");
		dt.insertColumn("TerminalName");
		dt.insertColumn("Insurancecompany");
		dt.insertColumn("ProductMajorCategoryName");
		dt.insertColumn("ChannelTypeName");
		dt.insertColumn("CompanyName");
		dt.insertColumn("AdvTypeName");
		dt.insertColumn("ShowFormName");
		dt.insertColumn("Serial");		
		dt.insertColumn("ConfigSerial");*/
		for (int i = 0; i < dt.getRowCount(); i++) {
			String Serial = dt.getString(i, "Serial");
			String ConfigSerial = dt.getString(i, "ConfigSerial");
			String AdvSerial = dt.getString(i, "AdvSerial");
			String Remark = dt.getString(i, "Remark");
			String SendAddress = dt.getString(i, "SendAddress");
			String LandingPage = dt.getString(i, "LandingPage");
			String TerminalName = dt.getString(i, "TerminalName");
			String Insurancecompany = dt.getString(i, "Insurancecompany");
			String ProductMajorCategoryName = dt.getString(i, "ProductMajorCategoryName");
			String ChannelTypeName = dt.getString(i, "ChannelTypeName");
			String CompanyName = dt.getString(i, "CompanyName");
			String AdvTypeName = dt.getString(i, "AdvTypeName");
			String ShowFormName = dt.getString(i, "ShowFormName");
			dt.set(i, "AdvSerial", AdvSerial);
			dt.set(i, "Remark", Remark);
			dt.set(i, "SendAddress", SendAddress);
			dt.set(i, "LandingPage", LandingPage);
			/*dt.set(i, "uv", "");
			dt.set(i, "pv", "");*/
			dt.set(i, "TerminalName", TerminalName);
			dt.set(i, "Insurancecompany", Insurancecompany);
			dt.set(i, "ProductMajorCategoryName", ProductMajorCategoryName);
			dt.set(i, "ChannelTypeName", ChannelTypeName);
			dt.set(i, "CompanyName", CompanyName);
			dt.set(i, "AdvTypeName", AdvTypeName);
			dt.set(i, "ShowFormName", ShowFormName);
			dt.set(i, "Serial", Serial);
			dt.set(i, "ConfigSerial", ConfigSerial);
		}
		dga.bindData(dt);
	}

	public void del() {
		DataTable dt = (DataTable) Request.get("IDs");
		int i = dt.getRowCount();
		DataRow dr = dt.getDataRow(0);
		String MarketingConfigID = dr.getString(0);
		String MarketingChannelsID = dr.getString(1);
		MarketingConfigSchema MarketingConfig = new MarketingConfigSchema();
		MarketingConfig.setSerial(MarketingConfigID);
		if (!MarketingConfig.delete()) {
			Response.setStatus(0);
			Response.setMessage("发生错误!删除MarketingConfig表失败");
		}
		MarketingChannelsSchema MarketingChannel = new MarketingChannelsSchema();
		MarketingChannel.setSerial(MarketingChannelsID);
		//MarketingChannel.setConfigSerial(MarketingConfigID);
		if (!MarketingChannel.delete()) {
			Response.setStatus(0);
			Response.setMessage("发生错误!删除MarketingChannes表失败");
		}
		Response.setLogInfo(1, "删除成功");
	}

	public void dg1DataBindCost(DataGridAction dga) {
		String channelCode = Request.getString("ChannelCode");
		//String ChannelCodes = da.getDate(0, 5).toString();
		ChannelCostsSchema  ChannelCostsSchemat = new ChannelCostsSchema();
		ChannelCostsSchemat.setChannelCode(channelCode);
		ChannelCostsSet ChannelCostsSett = ChannelCostsSchemat.query();
		int o = ChannelCostsSett.size();
		if (o>0) {
			String sql = "select ID,CostType,SCost,TransNode,FixedCosts,ChannelCode from channelcosts where ChannelCode = '"
					+ channelCode + "'";
			QueryBuilder qb = new QueryBuilder(sql);
			dga.setTotal(qb);
			DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
					dga.getPageIndex());
			dt.insertColumn("cId");
			dt.insertColumn("cCostType");
			dt.insertColumn("cSCost");
			dt.insertColumn("cTransNode");
			dt.insertColumn("cFixedCosts");
			dt.insertColumn("cChannelCode");
			for (int i = 0; i < dt.getRowCount(); i++) {
				String Id = dt.getString(i, "ID");
				String CostType = dt.getString(i, "CostType");
				String SCost = dt.getString(i, "SCost");
				String TransNode = dt.getString(i, "TransNode");
				String FixedCosts = dt.getString(i, "FixedCosts");
				String ChannelCode = dt.getString(i, "ChannelCode");
				dt.set(i, "cId", Id);
				dt.set(i, "cCostType", CostType);
				dt.set(i, "cSCost", SCost);
				dt.set(i, "cTransNode", TransNode);
				dt.set(i, "cFixedCosts", FixedCosts);
				dt.set(i, "cChannelCode", ChannelCode);
			}
			dga.bindData(dt);
		}else{
		String sql = "select ID,CostType,SCost,TransNode,FixedCosts,ChannelCode from channelcosts where ChannelCode = '' ";
		QueryBuilder qb = new QueryBuilder(sql);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dt.insertColumn("cId");
		dt.insertColumn("cCostType");
		dt.insertColumn("cSCost");
		dt.insertColumn("cTransNode");
		dt.insertColumn("cFixedCosts");
		dt.insertColumn("cChannelCode");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String Id = dt.getString(i, "ID");
			String CostType = dt.getString(i, "CostType");
			String SCost = dt.getString(i, "SCost");
			String TransNode = dt.getString(i, "TransNode");
			String FixedCosts = dt.getString(i, "FixedCosts");
			// String ChannelCode = dt.getString(i, "ChannelCode");
			dt.set(i, "cId", Id);
			dt.set(i, "cCostType", CostType);
			dt.set(i, "cSCost", SCost);
			dt.set(i, "cTransNode", TransNode);
			dt.set(i, "cFixedCosts", FixedCosts);
			dt.set(i, "cChannelCode", channelCode);
		}
		dga.bindData(dt);
	}
	}
	public void ChannelCostsave() {
		DataTable dt = (DataTable) Request.get("IDs");
		int i = dt.getRowCount();
		DataRow dr = dt.getDataRow(0);
		DataRow dr1 = null;
		if (i > 1) {
			dr1 = dt.getDataRow(1);
		}
		if (dr != null && dr1 != null) {
			String CostTypech = dr.getString(1);
			String CostTypech2 = dr1.getString(1);
			if (CostTypech.equals("固定费用") || CostTypech2.equals("固定费用")) {
				// dr
				String CostType = dr.getString(1);
				String SCost = dr.getString(2);
				String TransNode = dr.getString(3);
				String FixedCosts = dr.getString(4);
				String ChannelCode = dr.getString(5);
				String ChannelCostID = NoUtil.getMaxID("ChannelCostID") + "";
				ChannelCostsSchema ChannelCostsSchemau = new ChannelCostsSchema();
				ChannelCostsSchemau.setID(ChannelCostID);
				ChannelCostsSchemau.setCostType(CostType);
				ChannelCostsSchemau.setSCost(SCost);
				ChannelCostsSchemau.setTransNode(TransNode);
				ChannelCostsSchemau.setFixedCosts(FixedCosts);
				ChannelCostsSchemau.setChannelCode(ChannelCode);
				if (!ChannelCostsSchemau.insert()) {
					Response.setStatus(0);
					Response.setMessage("发生错误!");
					return;
				}
				// dr1
				CostType = dr1.getString(1);
				SCost = dr1.getString(2);
				TransNode = dr1.getString(3);
				FixedCosts = dr1.getString(4);
				ChannelCode = dr1.getString(5);
				ChannelCostID = NoUtil.getMaxID("ChannelCostID") + "";
				ChannelCostsSchemau = new ChannelCostsSchema();
				ChannelCostsSchemau.setID(ChannelCostID);
				ChannelCostsSchemau.setCostType(CostType);
				ChannelCostsSchemau.setSCost(SCost);
				ChannelCostsSchemau.setTransNode(TransNode);
				ChannelCostsSchemau.setFixedCosts(FixedCosts);
				ChannelCostsSchemau.setChannelCode(ChannelCode);
				if (!ChannelCostsSchemau.insert()) {
					Response.setStatus(0);
					Response.setMessage("发生错误!");
					return;
				}
				Response.setStatus(1);
				Response.setMessage("添加成功！");
				return;
			} else {
				Response.setStatus(0);
				Response.setMessage("只有固定费用才能搭配其他费用模式，否则只能选取一种费用模式");
				return;
			}
		} else {
			if (dr != null) {
				String CostType = dr.getString(1);
				String SCost = dr.getString(2);
				String TransNode = dr.getString(3);
				String FixedCosts = dr.getString(4);
				String ChannelCode = dr.getString(11);
				String ChannelCostID = NoUtil.getMaxID("ChannelCostID") + "";
				ChannelCostsSchema ChannelCostsSchemau = new ChannelCostsSchema();
				ChannelCostsSchemau.setID(ChannelCostID);
				ChannelCostsSchemau.setCostType(CostType);
				ChannelCostsSchemau.setSCost(SCost);
				ChannelCostsSchemau.setTransNode(TransNode);
				ChannelCostsSchemau.setFixedCosts(FixedCosts);
				ChannelCostsSchemau.setChannelCode(ChannelCode);
				if (!ChannelCostsSchemau.insert()) {
					Response.setStatus(0);
					Response.setMessage("发生错误!");
					return;
				}
			}
			if (dr1 != null) {
				String CostType = dr1.getString(1);
				String SCost = dr1.getString(2);
				String TransNode = dr1.getString(3);
				String FixedCosts = dr1.getString(4);
				String ChannelCode = dr1.getString(5);
				String ChannelCostID = NoUtil.getMaxID("ChannelCostID") + "";
				ChannelCostsSchema ChannelCostsSchemau = new ChannelCostsSchema();
				ChannelCostsSchemau.setID(ChannelCostID);
				ChannelCostsSchemau.setCostType(CostType);
				ChannelCostsSchemau.setSCost(SCost);
				ChannelCostsSchemau.setTransNode(TransNode);
				ChannelCostsSchemau.setFixedCosts(FixedCosts);
				ChannelCostsSchemau.setChannelCode(ChannelCode);
				if (!ChannelCostsSchemau.insert()) {
					Response.setStatus(0);
					Response.setMessage("发生错误!");
					return;
				}
			}
			Response.setStatus(1);
			Response.setMessage("添加成功！");
			return;
		}
	}
	public  void edit(){
		String ChannelCode = Request.getString("ChannelCode");//渠道编码
		String SendAddress =Request.getString("SendAddress"); //投放地址
		//String CompanySerial = "";//渠道公司编码
		String CompanyName = Request.getString("CompanyName");//渠道公司名称
		String Remark = Request.getString("Remark");//备注
		String AdvSerial =Request.getString("AdvSerial"); //广告编号
		String TerminalCode = Request.getString("TerminalCode");//营销终端代码
		String ProductMajorCategory = Request.getString("ProductMajorCategory");//产品大类代码
		String ProductCategory = Request.getString("ProductCategory");//产品细类代码
		String ChannelTypeCode =Request.getString("ChannelTypeCode"); //渠道类型代码
		String AdvTypeCode = Request.getString("AdvTypeCode");//广告类型代码
		String ShowFormCode = Request.getString("ShowFormCode");//展现形式代码
		String ChannelNameCode = Request.getString("ChannelNameCode");//渠道名称代码
		
		/*String TerminalName = "";//营销终端名称
		String ProductMajorCategoryName = "";//产品大类名称
		String ProductCategoryName = "";//产品细类名称
		String ChannelTypeName = "";//渠道类型名称
		String AdvTypeName = "";//广告类型名称
		String ShowFormName = "";//展现形式名称
		String ChannelName = "";//渠道名称
		String Insurancecompany =""; //保险公司名称
		String ChannelCodeHiden = "";//渠道隐藏编码
*/		
		QueryBuilder QueryBuilderTer = new QueryBuilder();
		QueryBuilderTer.setSQL("Select codename from zdcode where codevalue = '"+TerminalCode+"' and codetype = 'Marking.Search'");
		String TerminalName = QueryBuilderTer.executeString();//营销终端名称
		
		QueryBuilder QueryBuilderPrc = new QueryBuilder();
		QueryBuilderPrc.setSQL("Select codename from zdcode where codevalue = '"+ProductMajorCategory+"' and codetype = 'Marking.Product'");
		String ProductMajorCategoryName = QueryBuilderPrc.executeString();//产品大类名称
		
		QueryBuilder QueryBuilderPrx = new QueryBuilder();
		QueryBuilderPrx.setSQL("Select codename from zdcode where codevalue = '"+ProductCategory+"' and codetype = 'ProductCategory'");
		String ProductCategoryName = QueryBuilderPrx.executeString();//产品细类名称

		QueryBuilder QueryBuilderCt = new QueryBuilder();
		QueryBuilderCt.setSQL("Select codename from zdcode where codevalue = '"+ChannelTypeCode+"' and codetype = 'ChannelTypeCode'");
		String ChannelTypeName = QueryBuilderCt.executeString();//渠道类型名称
		
		QueryBuilder QueryBuilderAt = new QueryBuilder();
		QueryBuilderAt.setSQL("Select codename from zdcode where codevalue = '"+AdvTypeCode+"' and codetype = 'AdvTypeCode'");
		String AdvTypeName = QueryBuilderAt.executeString();//广告类型名称
		
		QueryBuilder QueryBuilderSf = new QueryBuilder();
		QueryBuilderSf.setSQL("Select codename from zdcode where codevalue = '"+ShowFormCode+"' and codetype = 'ShowFormCode'");
		String ShowFormName = QueryBuilderSf.executeString();//展现形式名称
		
		QueryBuilder QueryBuilderCn = new QueryBuilder();
		QueryBuilderCn.setSQL("Select codename from zdcode where codevalue = '"+ChannelNameCode+"' and codetype = 'ChannelName'");
		String ChannelName = QueryBuilderCn.executeString();//渠道名称
		
		QueryBuilder QueryBuilderComN = new QueryBuilder();
		QueryBuilderComN.setSQL("Select codevalue from zdcode where codename = '"+CompanyName+"' and codetype = 'CompanyName'");
		String CompanySerial = QueryBuilderComN.executeString();//渠道公司编码
		
		
		
		
		String MarketingChannelsID = Request.getString("Serial");
		String MarketingConfigID =Request.getString("ConfigSerial");
		Date dt = new Date();

		MarketingConfigSchema MarketingConfig = new MarketingConfigSchema();
		MarketingConfig.setSerial(MarketingConfigID);
		MarketingConfig.setLandingPage(Request.getString("LandingPage"));
		MarketingConfig.setAddTime(dt);

		if (!MarketingConfig.update()) {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
		
		MarketingChannelsSchema MarketingChannel = new MarketingChannelsSchema();
		MarketingChannel.setSerial(MarketingChannelsID);
		MarketingChannel.setConfigSerial(MarketingConfigID);
		MarketingChannel.setChannelCode(Request.getString("ChannelCode"));//渠道编码
		MarketingChannel.setSendAddress(Request.getString("SendAddress"));//投放地址
		MarketingChannel.setCompanySerial(CompanySerial);//渠道公司编码
		MarketingChannel.setCompanyName(Request.getString("CompanyName"));//渠道公司名称
		MarketingChannel.setRemark(Request.getString("Remark"));//备注
		MarketingChannel.setAdvSerial(Request.getString("AdvertisementNM"));//广告编号
		MarketingChannel.setTerminalCode(Request.getString("TerminalCode"));//营销终端代码
		MarketingChannel.setProductMajorCategory(Request.getString("ProductMajorCategory"));//产品大类代码
		MarketingChannel.setProductCategory(Request.getString("ProductCategory"));//产品细类代码
		MarketingChannel.setChannelTypeCode(Request.getString("ChannelTypeCode"));//渠道类型代码
		MarketingChannel.setAdvTypeCode(Request.getString("AdvTypeCode"));//广告类型代码
		MarketingChannel.setShowFormCode(Request.getString("ShowFormCode"));//展现形式代码
		MarketingChannel.setChannelNameCode(Request.getString("ChannelNameCode"));//渠道名称代码
		
		MarketingChannel.setTerminalName(TerminalName);//营销终端名称
		MarketingChannel.setProductMajorCategoryName(ProductMajorCategoryName);//产品大类名称
		MarketingChannel.setProductCategoryName(ProductCategoryName);//产品细类名称
		MarketingChannel.setChannelTypeName(ChannelTypeName);//渠道类型名称
		MarketingChannel.setAdvTypeName(AdvTypeName);//广告类型名称
		MarketingChannel.setShowFormName(ShowFormName);//展现形式名称
		MarketingChannel.setChannelName(ChannelName);//渠道名称
		MarketingChannel.setInsurancecompany(Request.getString("Insurancecompany"));//保险公司名称
		MarketingChannel.setChannelCodeHiden(Request.getString("ChannelCodeHiden"));//渠道隐藏编码
		

		if (!MarketingChannel.update()) {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
		Response.setStatus(1);
		Response.setMessage("更新成功！");
	}
}
package com.sinosoft.cms.publish;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.site.CatalogExtend;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZDColumnRelaSchema;
import com.sinosoft.schema.ZDColumnRelaSet;
import com.sinosoft.schema.ZDColumnSchema;
import com.sinosoft.schema.ZDColumnValueSchema;
import com.sinosoft.schema.ZDColumnValueSet;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.searchInfo.SearchInfoServiceStub.FEMSearchProperties;
import com.sinosoft.webservice.searchInfo.SearchInfoServiceStub.FEMSearchRela;
import com.sinosoft.webservice.searchInfo.SearchInfoServiceStub.SearchInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AutomaticPublishCatalog {

	private static final Logger logger = LoggerFactory.getLogger(AutomaticPublishCatalog.class);

	private String mERiskSubType = "";
	private static long mCatalogID;
	private FEMSearchProperties[] mFEMSearchProperties = null;

	public static  boolean publishing(long tCatalogID, String tERiskSubType) {
		AutomaticPublishCatalog apc = new AutomaticPublishCatalog(tCatalogID,
				tERiskSubType);
//		if (!apc.getProductInfo()) {
//			return false;
//		}
//		if (!apc.syncCatalogAttribute()) {
//			return false;
//		}
//		String nERiskSubType="";
//		String innerCode = "";
//		nERiskSubType = tERiskSubType.substring(0, 1);
//		String sql1 = "select InnerCode from zccatalog where id = ?";
//		QueryBuilder qb2 = new QueryBuilder(sql1);
//		qb2.add(tCatalogID);
//		try{
//		innerCode = (String)qb2.executeOneValue();
//		} catch (Exception e){
//			LogUtil.info("获取栏目内部编码异常："+e.getMessage());
//			e.printStackTrace();
//		}
//		
//		Combination com = new Combination();
//		List<List<String>> list = com.getAllCombination(tERiskSubType);
//		for (int i = 0; list != null && i < list.size(); i++) {
//			List<String> b = list.get(i);
//			if (b != null) {
//				for (String ss : b) {
////					String kk="";
//					String[] conditionsplit = ss.split("\\||,");
//					String sql="select a.riskcode from femrisk a where 1=1"+" ";
//					for(int j=0;j<conditionsplit.length;j++){
//						if((conditionsplit[j]).startsWith("default_")){
//							continue;
//						}else{
//								if("Y".equals(ValueCheck(conditionsplit[j])) && "01".equals(ValueCheck1(conditionsplit[j])))
//								{
//									sql+="or exists (select 1 from FEMProductRelaCondition where searchcode='" + conditionsplit[j] + "' and riskcode = a.riskcode and ERiskSubType = '"+tERiskSubType+"')" + " ";
//								}else{
//									sql+="and exists (select 1 from FEMProductRelaCondition where searchcode='" + conditionsplit[j] + "' and riskcode = a.riskcode and ERiskSubType = '"+tERiskSubType+"')" + " ";
//								}
//							}
//						}	
//					QueryBuilder qb1 = new QueryBuilder(sql);
//					DataTable dt = qb1.executeDataTable();
//					DataTable dt1 = new DataTable();
//					Transaction tran = new Transaction();
//					
//					FEMSearchProductInfoSchema FEMSearchProductInfo = new FEMSearchProductInfoSchema();
//					FEMSearchProductInfoSet FEMSearchSet = new FEMSearchProductInfoSet();
//					FEMSearchProductInfoSet FEMSearchSet2 = new FEMSearchProductInfoSet();
//					FEMSearchProductInfo.setSearchID(ss);
//					FEMSearchSet = FEMSearchProductInfo.query();
//					tran.add(FEMSearchSet, Transaction.DELETE);
//					
////					FEMSearchProductHTMLSchema FEMSearchProductHTML = new FEMSearchProductHTMLSchema();
////					FEMSearchProductHTMLSet FEMSearchProductSet = new FEMSearchProductHTMLSet();
////					FEMSearchProductHTML.setSearchID(ss);
////					FEMSearchProductSet = FEMSearchProductHTML.query();
////					trans.add(FEMSearchProductSet, Transaction.DELETE);
//					for(int j=0;j<dt.getRowCount();j++)
//					{	
//						
//						sql="select a.title,a.URL,(select textvalue from zdcolumnvalue where columncode = 'SalesVolume' and relaid = a.id) as SalesVolume,"
//							+"(select textvalue from zdcolumnvalue where ColumnCode = 'CalHTML2' and relaid = a.id) as CalHTML2,"
//							+"(select textvalue from zdcolumnvalue where ColumnCode = 'AdaptPeopleInfo' and relaid = a.id) as AdaptPeopleInfo,"
//							+"(select textvalue from zdcolumnvalue where ColumnCode = 'FEMRiskBrightSpot' and relaid = a.id) as FEMRiskBrightSpot,"
//							+"(select textvalue from zdcolumnvalue where ColumnCode = 'DutyHTML' and relaid = a.id) as DutyHTML,"
//							+"(select textvalue from zdcolumnvalue where ColumnCode = 'prodcutMarkPrice' and relaid = a.id) as prodcutMarkPrice ,"
//							+"(select textvalue from zdcolumnvalue where ColumnCode = 'SupplierCode2' and relaid = a.id) as SupplierCode2, "
//							+"(select textvalue from zdcolumnvalue where ColumnCode = 'Popular' and relaid = a.id) as Popular, "
//							+"(select textvalue from zdcolumnvalue where ColumnCode = 'InitPrem' and relaid = a.id) as InitPrem,"
//							+"(select textvalue from zdcolumnvalue where ColumnCode = 'riskcode' and relaid = a.id ) as RiskCode"
//							+" from zcarticle a where type='1' "
//							+"and exists (select 1 from zdcolumnvalue where ColumnCode = 'RiskCode' and TextValue = ? and a.id = RelaID)"
////							+"and exists (select 1 from zccatalog where producttype like '"+nERiskSubType+"%' and a.CatalogID = id)"
//							+"and cataloginnercode like '002313%' ";
//						qb1 = new QueryBuilder(sql);
//						qb1.add(dt.get(j).getString("riskcode"));
////						qb1.add(innerCode);
//						dt1 = qb1.executeDataTable();
//						if(dt1.getRowCount()>0){
//							String html="";
//							html+="<div class=\"product_title\">";
//							html+="<span class=\"CInsuranceCompany icon_C"+dt1.get(0).getString("SupplierCode2") +"\"  ></span> <span class=\"productName\"> " +
//									"<a href=\""+ dt1.get(0).getString("URL") +"\" target=\"_blank\"><h2 class=\"ziti\">"+dt1.get(0).getString("Title")+"</h2></a></span>";
//							html+="<span class=\"SalesVolume\">(累计销量："+dt1.get(0).getString("SalesVolume") +")</span>";
//							html+="<span style=\"display: none;\" id=\"productIntegral_"+ dt1.get(0).getString("RiskCode") +"}\">${Article.Prop2}</span>";
//						    html+="</div><div class=\"product_condition\">";
//							html+=dt1.get(0).getString("CalHTML2");
//						    html+="</div><div class=\"product_info\"><div class=\"product_info_bor\"><div class=\"prodcutMark\">";
//							html+="<ul class=\"price\" >"+dt1.get(0).getString("prodcutMarkPrice")+"</ul><ul class=\"btn\">";			
//							html+="<li class=\"btn1\"><span onclick=\"chakan('${Article.Link}')\">查看详情</span></li>";
//							html+="<li class=\"btn2\"><span onclick=\"submitp('${Article.RiskCode}');\">加入收藏</span><span class=\"add_cp_list\" style=\"margin-left: 6px;\"";
//							html+="onclick=\"showcp('${Article.title}','${Article.logo}','${Article.riskcode}','${Site.prop6}','${catalog.producttype}');\">加入对比</span></li>";
//							html+="</ul></div></div>";				
//							html+="<div class=\"AdaptPeopleInfo\">"+dt1.get(0).getString("AdaptPeopleInfo")+"</div>";			
//							html+="<div class=\"productFeature\">"+dt1.get(0).getString("FEMRiskBrightSpot")+"</div>";
//							html+=dt1.get(0).getString("DutyHTML");
//							html+="</div>";
//							
//							FEMSearchProductInfoSchema FEMSearchProductInfo1 = new FEMSearchProductInfoSchema();
//							FEMSearchProductInfo1.setid(NoUtil.getMaxID("FEMSearchID"));
//							FEMSearchProductInfo1.setSearchID(ss);
//							FEMSearchProductInfo1.setTitle(dt1.get(0).getString("Title"));
//							FEMSearchProductInfo1.setFEMRiskBrightSpot(dt1.get(0).getString("FEMRiskBrightSpot"));
//							FEMSearchProductInfo1.setAdaptPeopleInfo(dt1.get(0).getString("AdaptPeopleInfo"));
//							FEMSearchProductInfo1.setSupplierCode2(dt1.get(0).getString("SupplierCode2"));
//							FEMSearchProductInfo1.setURL(dt1.get(0).getString("URL"));
//							FEMSearchProductInfo1.setCalHTML2(dt1.get(0).getString("CalHTML2"));
//							FEMSearchProductInfo1.setDutyHTML(dt1.get(0).getString("DutyHTML"));
//							FEMSearchProductInfo1.setprodcutMarkPrice(dt1.get(0).getString("ProdcutMarkPrice"));
//							FEMSearchProductInfo1.setSalesVolume(dt1.get(0).getString("SalesVolume"));
//							FEMSearchProductInfo1.setPopular(dt1.get(0).getString("Popular"));
//							FEMSearchProductInfo1.setRiskCode(dt1.get(0).getString("RiskCode"));
//							FEMSearchProductInfo1.setInitPrem(dt1.get(0).getString("InitPrem"));
//							FEMSearchProductInfo1.setDynamicHTML(html);
//							FEMSearchProductInfo1.setProp1("");
//							FEMSearchProductInfo1.setProp2("");
//							FEMSearchProductInfo1.setProp3("");
//							FEMSearchProductInfo1.setProp4("");
//							FEMSearchProductInfo1.setcreateDate(new Date());
//							FEMSearchProductInfo1.setModifyDate(new Date());
//							FEMSearchSet2.add(FEMSearchProductInfo1);
//							
////							FEMSearchProductHTML.setID(NoUtil.getMaxID("FEMSearchHTMLID"));
////							FEMSearchProductHTML.setDynamicHTML(html);
////							FEMSearchProductHTML.setSalesVolume(dt.get(0).getString("SalesVolume"));
////							FEMSearchProductHTML.setPopular(dt.get(0).getString("Popular"));
////							FEMSearchProductHTML.setInitPrem(dt.get(0).getString("InitPrem"));
////							FEMSearchProductHTML.setcreateDate(new Date());
////							trans.add(FEMSearchProductHTML, Transaction.INSERT);
//						}
//					}
//					try{
//						tran.add(FEMSearchSet2, Transaction.INSERT);
//						tran.commit();
//					} catch (Exception e){
//						LogUtil.info("FEMSearchProductInfo表数据提交发生异常："+e.getMessage());
//						e.printStackTrace();
//					}
//				}
//			}
//		}
		
		return true;
}

	public AutomaticPublishCatalog(long tCatalogID, String tERiskSubType) {
		this.mCatalogID = tCatalogID;
		this.mERiskSubType = tERiskSubType;
	}

	private boolean getProductInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ERiskSubType", this.mERiskSubType);
		try {
			SearchInfoResponse siInfo = ProductWebservice
					.SearchInfoServiceImpl(map, null);
			mFEMSearchProperties = siInfo.getFEMSearchProperties();
			return true;
		} catch (Exception e) {
			logger.error("筛选页面接口获取数据失败：{}", e.getMessage());
		}
		return false;
	}

	private boolean syncCatalogAttribute() {
		try {
			if (mFEMSearchProperties != null && mFEMSearchProperties.length > 0) {
				Transaction trans = new Transaction();
				String tHTML = "<div id=\"divSearchConditions\" class=\"CsearchConditions\">";
				for (int i = 0; i < mFEMSearchProperties.length; i++) {
					tHTML += getHTMLContent(
							mFEMSearchProperties[i].getSearchCode(),
							mFEMSearchProperties[i].getSearchName(),
							mFEMSearchProperties[i].getFEMSearchRela());
				}
				tHTML += "</div>";
				if (!setOneSearchInfo(trans, "Catalog_SearchConditions",
						"产品筛选条件", tHTML)) {
					return false;
				}
				if (!trans.commit()) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	public static boolean setOneSearchInfo(Transaction trans, String tColumnCode,
			String tColumnName, String tTextValue) {
		ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
		rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_EXTEND);
		rela.setRelaID(String.valueOf(mCatalogID));
		rela.setColumnCode(tColumnCode);
		ZDColumnRelaSet relaSet = rela.query();
		if (relaSet != null && relaSet.size() > 0) {
			rela = relaSet.get(0);
			rela.setModifyTime(new Date());
			rela.setModifyUser(User.getUserName());
		} else {
			rela.setID(NoUtil.getMaxID("ColumnRelaID"));
			rela.setColumnID(NoUtil.getMaxID("ColumnID"));
			rela.setAddTime(new Date());
			rela.setAddUser(User.getUserName());
			rela.setModifyTime(rela.getAddTime());
			rela.setModifyUser(rela.getAddUser());
		}

		ZDColumnSchema column = new ZDColumnSchema();
		column.setID(rela.getColumnID());
		column.setCode(tColumnCode);
		column.setName(tColumnName);
		column.setSiteID(Application.getCurrentSiteID());
		column.setInputType(CatalogExtend.HtmlInput);
		column.setVerifyType(ColumnUtil.STRING);
		column.setIsMandatory("N");
		column.setOrderFlag(OrderUtil.getDefaultOrder());
		column.setAddTime(rela.getAddTime());
		column.setAddUser(rela.getAddUser());
		rela.setModifyTime(rela.getAddTime());
		rela.setModifyUser(rela.getAddUser());

		ZDColumnValueSchema value = new ZDColumnValueSchema();
		value.setColumnID(rela.getColumnID());
		value.setColumnCode(rela.getColumnCode());
		value.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_EXTEND);
		value.setRelaID(String.valueOf(mCatalogID));
		ZDColumnValueSet valueSet = value.query();
		if (valueSet != null && valueSet.size() > 0) {
			value = valueSet.get(0);
		} else {
			value.setID(NoUtil.getMaxID("ColumnValueID"));
		}
		value.setTextValue(tTextValue);

		ZCCatalogSchema catalog = CatalogUtil.getSchema(mCatalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, Transaction.UPDATE);

		trans.add(column, Transaction.DELETE_AND_INSERT);
		trans.add(rela, Transaction.DELETE_AND_INSERT);
		trans.add(value, Transaction.DELETE_AND_INSERT);
		return true;
	}

	private String getHTMLContent(String tSearchCode, String tSearchName,
			FEMSearchRela[] tFEMSearchRelas) {
		String tHTML = "";
		if (tFEMSearchRelas != null && tFEMSearchRelas.length > 0
				&& tFEMSearchRelas[0] != null) {
			tHTML = "<span class=\"CConditionName\">" + tSearchName
					+ "：</span><ul id=\"SearchCondition_" + tSearchCode + "\">";
			tHTML += "<li class=\"li_selected\">"
					+ "<span onclick=\"searchInfo('SearchCondition_"
					+ tSearchCode + "', this);\"" + " name=\"" + tSearchCode
					+ "\">不限</span></li>";
			int iCount = 0;
			for (int i = 0; i < tFEMSearchRelas.length; i++) {
				if (tFEMSearchRelas[i] == null
						|| tFEMSearchRelas[i].getSearchValue() == null
						|| "".equals(tFEMSearchRelas[i].getSearchValue())) {
					iCount++;
					continue;
				}
				tHTML += "<li><span onclick=\"searchInfo('SearchCondition_"
						+ tSearchCode + "', this);\"" + " name=\""
						+ tSearchCode + "\">"
						+ tFEMSearchRelas[i].getSearchValue() + "</span></li>";
			}
			if (iCount == tFEMSearchRelas.length) {
				return "";
			}
			tHTML += "</ul>";
		}
		return tHTML;
	}
}

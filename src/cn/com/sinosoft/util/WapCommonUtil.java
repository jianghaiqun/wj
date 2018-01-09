package cn.com.sinosoft.util;

import cn.com.sinosoft.entity.SDOrder;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActivityCalculate;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class WapCommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(WapCommonUtil.class);

	public static DecimalFormat df = new DecimalFormat("0.00"); // 保留几位小数
	
	public static Map<String, Object> wapGetAreaInfo(String USER,Map<String, Object> tPARAMETERS) {
 
		// 解析入参
		String tParentKey = String.valueOf(tPARAMETERS.get("ParentKey"));// 地区父节点
		String tProdCode = String.valueOf(tPARAMETERS.get("ProdCode"));// 产品编码
		String tCompanyID = String.valueOf(tPARAMETERS.get("CompanyID"));// 保险公司编码
		List<String> errorinfos = new ArrayList<String>();
		if (StringUtil.isEmpty(tProdCode) || StringUtil.isEmpty(tCompanyID) || tProdCode.equals("") || tCompanyID.equals("")) {
			errorinfos.add("Area001");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0012",null);
		}
		
		Map<String, Object> areaInfoMap = new HashMap<String, Object>();
		JSONArray areaSubInfo = new JSONArray();
		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = new StringBuffer(" SELECT id,NAME FROM AREA WHERE 1=1 ");
		// 如果父节点为空，则查询的是一级地区，
		// 先根据产品查，如果未查到则再根据保险公司查
		// 如果父节点不为空，则只根据父节点的值查询 
		try {
			if (StringUtil.isEmpty(tParentKey)) {
				tradeSQL.append(" AND (parent_id is null or parent_id='') AND ProductId= '" + tProdCode + "' ORDER BY id ASC;");
				tQueryBuilder.setSQL(tradeSQL.toString());
				DataTable tradeDT = tQueryBuilder.executeDataTable();
				if (tradeDT != null && tradeDT.getRowCount() > 0) {
					for (int i = 0; i < tradeDT.getRowCount(); i++) {
						Map<String, Object> areaSubMap = new HashMap<String, Object>();
						areaSubMap.put("Name", tradeDT.getString(i, 1));
						areaSubMap.put("Code", tradeDT.getString(i, 0));
						areaSubMap.put("ParentCode", "");
						areaSubInfo.put(areaSubMap);
					}
					areaInfoMap.put("AreaInfos", areaSubInfo);
				} else {
					tradeSQL = new StringBuffer("SELECT id,NAME FROM AREA WHERE 1=1 AND (parent_id is null or parent_id='') AND insuranceCompany= '" + tCompanyID + "' ORDER BY id ASC;");
					tQueryBuilder.setSQL(tradeSQL.toString());
					tradeDT = tQueryBuilder.executeDataTable();
					if (tradeDT != null && tradeDT.getRowCount() > 0) {
						for (int i = 0; i < tradeDT.getRowCount(); i++) {
							Map<String, Object> areaSubMap = new HashMap<String, Object>();
							areaSubMap.put("Name", tradeDT.getString(i, 1));
							areaSubMap.put("Code", tradeDT.getString(i, 0));
							areaSubMap.put("ParentCode", "");
							areaSubInfo.put(areaSubMap);
						}
						
					}
				}
			} else {
				tradeSQL.append(" AND parent_id = '" + tParentKey + "' ORDER BY id ASC;");
				tQueryBuilder.setSQL(tradeSQL.toString());
				DataTable tradeDT = tQueryBuilder.executeDataTable();
				if (tradeDT != null && tradeDT.getRowCount() > 0) {
					for (int i = 0; i < tradeDT.getRowCount(); i++) {
						Map<String, Object> areaSubMap = new HashMap<String, Object>();
						areaSubMap.put("Name", tradeDT.getString(i, 1));
						areaSubMap.put("Code", tradeDT.getString(i, 0));
						areaSubMap.put("ParentCode", tParentKey);
						areaSubInfo.put(areaSubMap);
					}
				}
			}
		} catch (Exception e) {
			errorinfos.add("Area002");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0012",null);
		}
		
		areaInfoMap.put("AreaInfos", areaSubInfo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", USER);
		resultMap.put("REQUESTTYPE", "KXBJRT0012");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", areaInfoMap);
		return resultMap;
	}

	public static Map<String, Object> wapGetOccuInfo(String USER,Map<String, Object> tPARAMETERS) {
		// 解析入参
		String tParentKey = String.valueOf(tPARAMETERS.get("ParentKey"));// 职业父节点
		String tProdCode = String.valueOf(tPARAMETERS.get("ProdCode"));// 产品编码
		String tCompanyID = String.valueOf(tPARAMETERS.get("CompanyID"));// 保险公司编码
		List<String> errorinfos = new ArrayList<String>();
		if (StringUtil.isEmpty(tProdCode) || StringUtil.isEmpty(tCompanyID) || tProdCode.equals("") || tCompanyID.equals("")) {
			errorinfos.add("Occu001");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0013",null);
		}
		Map<String, Object> OccuInfoMap = new HashMap<String, Object>();
		JSONArray OccuSubInfo = new JSONArray();
		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = null;
		try {
			if (StringUtil.isEmpty(tParentKey)) {
				tradeSQL = new StringBuffer("SELECT a.Occup FROM sdproduct a WHERE a.productid='" + tProdCode + "';");
				tQueryBuilder.setSQL(tradeSQL.toString());
				DataTable tradeDT = tQueryBuilder.executeDataTable();
				if (tradeDT != null && tradeDT.getRowCount() > 0) {
					String tOccup = tradeDT.getString(0, 0);
					if (!"".equals(tOccup)) {
						String newOccup[] = tOccup.split("-");//如果数据库中只有1位？
						if(newOccup!=null&&newOccup.length>1){
							String mingrade = newOccup[0];
							String maxgrade = newOccup[1];
							tradeSQL = new StringBuffer(" SELECT id,NAME FROM occupation WHERE 1=1 AND (parent_id is null or parent_id='')  AND ((grade<='" + maxgrade + "' AND grade>='" + mingrade 
									+ "') or grade='0' or grade='' or grade is null or grade='null') AND insuranceCompany = '" + tCompanyID + "' ORDER BY id ASC;");
						}else{
							tradeSQL = new StringBuffer("SELECT id,NAME FROM occupation WHERE 1=1 AND (parent_id is null or parent_id='')  AND (grade='"+tOccup+ "' or grade='0' or grade='' or grade is null or grade='null' ) AND insuranceCompany = '" + tCompanyID + "' ORDER BY id ASC;");
						}
						
						tQueryBuilder.setSQL(tradeSQL.toString());
						tradeDT = tQueryBuilder.executeDataTable();
						if (tradeDT != null && tradeDT.getRowCount() > 0) {
							for (int i = 0; i < tradeDT.getRowCount(); i++) {
								Map<String, Object> OccuSubMap = new HashMap<String, Object>();
								OccuSubMap.put("Name", tradeDT.getString(i, 1));
								OccuSubMap.put("Code", tradeDT.getString(i, 0));
								OccuSubMap.put("ParentCode", "");
								OccuSubInfo.put(OccuSubMap);
							}

						}

					} else {
						tradeSQL = new StringBuffer(" SELECT id,NAME FROM occupation WHERE 1=1 AND (parent_id is null or parent_id='') AND insuranceCompany = '" + tCompanyID + "' ORDER BY id ASC;");
						tQueryBuilder.setSQL(tradeSQL.toString());
						tradeDT = tQueryBuilder.executeDataTable();
						if (tradeDT != null && tradeDT.getRowCount() > 0) {
							for (int i = 0; i < tradeDT.getRowCount(); i++) {
								Map<String, Object> OccuSubMap = new HashMap<String, Object>();
								OccuSubMap.put("Name", tradeDT.getString(i, 1));
								OccuSubMap.put("Code", tradeDT.getString(i, 0));
								OccuSubMap.put("ParentCode", "");
								OccuSubInfo.put(OccuSubMap);
							}

						}
					}

				}
			}else{
				tradeSQL = new StringBuffer(" SELECT id,NAME FROM occupation WHERE 1=1 AND parent_id = '" + tParentKey + "' ORDER BY id ASC; ");
				tQueryBuilder.setSQL(tradeSQL.toString());
				DataTable tradeDT = tQueryBuilder.executeDataTable();
				if (tradeDT != null && tradeDT.getRowCount() > 0) {
					for (int i = 0; i < tradeDT.getRowCount(); i++) {
						Map<String, Object> OccuSubMap = new HashMap<String, Object>();
						OccuSubMap.put("Name", tradeDT.getString(i, 1));
						OccuSubMap.put("Code", tradeDT.getString(i, 0));
						OccuSubMap.put("ParentCode", tParentKey);
						OccuSubInfo.put(OccuSubMap);
					}
				}
			}
		} catch (Exception e) {
			errorinfos.add("Occu002");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0013",null);
		}

		OccuInfoMap.put("OccupationInfos", OccuSubInfo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", USER);
		resultMap.put("REQUESTTYPE", "KXBJRT0013");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", OccuInfoMap);

		return resultMap;
	}

	public static Map<String, Object> wapGetRelationShip(String USER,Map<String, Object> tPARAMETERS) {

		// 解析入参
		String tProdCode = String.valueOf(tPARAMETERS.get("ProdCode"));// 产品编码
		String tCompanyID = String.valueOf(tPARAMETERS.get("CompanyID"));// 保险公司编码
		List<String> errorinfos = new ArrayList<String>();
		if (StringUtil.isEmpty(tProdCode) || StringUtil.isEmpty(tCompanyID) || tProdCode.equals("") || tCompanyID.equals("")) {
			errorinfos.add("RelationShip001");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0030",null);
		}
		Map<String, Object> RelationshipMap = new HashMap<String, Object>();
		JSONArray RelationSubInfo = new JSONArray();
		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = new StringBuffer("SELECT codeValue,codeName FROM dictionary WHERE codeType='Relationship' AND ProductId='" + tProdCode + "' ORDER BY id ASC;");
		tQueryBuilder.setSQL(tradeSQL.toString());
		DataTable tradeDT = tQueryBuilder.executeDataTable();
		try {
			if (tradeDT != null && tradeDT.getRowCount() > 0) {
				for (int i = 0; i < tradeDT.getRowCount(); i++) {
					Map<String, Object> RelationSubMap = new HashMap<String, Object>();
					RelationSubMap.put("Name", tradeDT.getString(i, 1));
					RelationSubMap.put("Code", tradeDT.getString(i, 0));
					RelationSubInfo.put(RelationSubMap);
				}
			} else {
				tradeSQL = new StringBuffer("SELECT codeValue,codeName FROM dictionary WHERE codeType='Relationship' AND insuranceCode='" + tCompanyID + "' AND (ProductId is null OR ProductId ='') ORDER BY id ASC;");
				tQueryBuilder.setSQL(tradeSQL.toString());
				tradeDT = tQueryBuilder.executeDataTable();
				if (tradeDT != null && tradeDT.getRowCount() > 0) {
					for (int i = 0; i < tradeDT.getRowCount(); i++) {
						Map<String, Object> RelationSubMap = new HashMap<String, Object>();
						RelationSubMap.put("Name", tradeDT.getString(i, 1));
						RelationSubMap.put("Code", tradeDT.getString(i, 0));
						RelationSubInfo.put(RelationSubMap);
					}
				}
			}
		} catch (Exception e) {
			errorinfos.add("RelationShip002");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0030",null);
		}
		
		RelationshipMap.put("RelationInfos", RelationSubInfo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", USER);
		resultMap.put("REQUESTTYPE", "KXBJRT0030");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", RelationshipMap);
		return resultMap;
	}

	public static Map<String, Object> wapGetCardInfo(String USER,Map<String, Object> tPARAMETERS) {
		// 解析入参
		String tProdCode = String.valueOf(tPARAMETERS.get("ProdCode"));// 产品编码
		String tCompanyID = String.valueOf(tPARAMETERS.get("CompanyID"));// 保险公司编码
		List<String> errorinfos = new ArrayList<String>();
		if (StringUtil.isEmpty(tProdCode) || StringUtil.isEmpty(tCompanyID) || tProdCode.equals("") || tCompanyID.equals("")) {
			errorinfos.add("Card001");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0031",null);
		}
		Map<String, Object> CardInfoMap = new HashMap<String, Object>();
		JSONArray CardSubInfo = new JSONArray();
		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = new StringBuffer("SELECT codeValue,codeName FROM dictionary WHERE codeType='certificate' AND ProductId='" + tProdCode + "' ORDER BY id ASC;");
		tQueryBuilder.setSQL(tradeSQL.toString());
		DataTable tradeDT = tQueryBuilder.executeDataTable();
		try {
			if (tradeDT != null && tradeDT.getRowCount() > 0) {
				for (int i = 0; i < tradeDT.getRowCount(); i++) {
					Map<String, Object> CardSubMap = new HashMap<String, Object>();
					CardSubMap.put("Name", tradeDT.getString(i, 1));
					CardSubMap.put("Code", tradeDT.getString(i, 0));
					CardSubInfo.put(CardSubMap);
				}
			} else {
				tradeSQL = new StringBuffer("SELECT codeValue,codeName FROM dictionary WHERE codeType='certificate' AND insuranceCode='" + tCompanyID + "' AND (ProductId is null OR ProductId ='') ORDER BY id ASC;");
				tQueryBuilder.setSQL(tradeSQL.toString());
				tradeDT = tQueryBuilder.executeDataTable();
				if (tradeDT != null && tradeDT.getRowCount() > 0) {
					for (int i = 0; i < tradeDT.getRowCount(); i++) {
						Map<String, Object> CardSubMap = new HashMap<String, Object>();
						CardSubMap.put("Name", tradeDT.getString(i, 1));
						CardSubMap.put("Code", tradeDT.getString(i, 0));
						CardSubInfo.put(CardSubMap);
					}
				}
			}
		} catch (Exception e) {
			errorinfos.add("Card002");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0031",null);
		}
		
		CardInfoMap.put("CardInfos", CardSubInfo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", USER);
		resultMap.put("REQUESTTYPE", "KXBJRT0031");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", CardInfoMap);
		return resultMap;
	}

	public static Map<String, Object> wapGetSexInfo(String USER,Map<String, Object> tPARAMETERS) {
		// 解析入参
		String tProdCode = String.valueOf(tPARAMETERS.get("ProdCode"));// 产品编码
		String tCompanyID = String.valueOf(tPARAMETERS.get("CompanyID"));// 保险公司编码
		List<String> errorinfos = new ArrayList<String>();
		if (StringUtil.isEmpty(tProdCode) || StringUtil.isEmpty(tCompanyID) || tProdCode.equals("") || tCompanyID.equals("")) {
			errorinfos.add("Sex001");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0032",null);
		}
		Map<String, Object> SexInfoMap = new HashMap<String, Object>();
		JSONArray SexSubInfo = new JSONArray();
		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = new StringBuffer("SELECT codeValue,codeName FROM dictionary WHERE codeType='Sex' AND ProductId='" + tProdCode + "' ORDER BY id ASC;");
		tQueryBuilder.setSQL(tradeSQL.toString());
		DataTable tradeDT = tQueryBuilder.executeDataTable();
		try {
			if (tradeDT != null && tradeDT.getRowCount() > 0) {
				for (int i = 0; i < tradeDT.getRowCount(); i++) {
					Map<String, Object> SexSubMap = new HashMap<String, Object>();
					SexSubMap.put("Name", tradeDT.getString(i, 1));
					SexSubMap.put("Code", tradeDT.getString(i, 0));
					SexSubInfo.put(SexSubMap);
				}
			} else {
				tradeSQL = new StringBuffer("SELECT codeValue,codeName FROM dictionary WHERE codeType='Sex' AND insuranceCode='" + tCompanyID + "' AND (ProductId is null OR ProductId ='') ORDER BY id ASC;");
				tQueryBuilder.setSQL(tradeSQL.toString());
				tradeDT = tQueryBuilder.executeDataTable();
				if (tradeDT != null && tradeDT.getRowCount() > 0) {
					for (int i = 0; i < tradeDT.getRowCount(); i++) {
						Map<String, Object> SexSubMap = new HashMap<String, Object>();
						SexSubMap.put("Name", tradeDT.getString(i, 1));
						SexSubMap.put("Code", tradeDT.getString(i, 0));
						SexSubInfo.put(SexSubMap);
					}
				}
			}
		} catch (Exception e) {
			errorinfos.add("Sex002");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0032",null);
		}
		
		SexInfoMap.put("SexInfos", SexSubInfo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", USER);
		resultMap.put("REQUESTTYPE", "KXBJRT0032");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", SexInfoMap);
		return resultMap;
	}

	public static Map<String, Object> wapHouseTypeInfo(String USER,Map<String, Object> tPARAMETERS) {
		// 解析入参
		String tProdCode = String.valueOf(tPARAMETERS.get("ProdCode"));// 产品编码
		String tCompanyID = String.valueOf(tPARAMETERS.get("CompanyID"));// 保险公司编码
		List<String> errorinfos = new ArrayList<String>();
		if (StringUtil.isEmpty(tProdCode) || StringUtil.isEmpty(tCompanyID) || tProdCode.equals("") || tCompanyID.equals("")) {
			errorinfos.add("HourseType001");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0050",null);
		}
		Map<String, Object> SexInfoMap = new HashMap<String, Object>();
		JSONArray SexSubInfo = new JSONArray();
		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = new StringBuffer("SELECT id,codeName FROM dictionary WHERE codeType='hourseType' AND ProductId='" + tProdCode + "' ORDER BY id ASC;");
		tQueryBuilder.setSQL(tradeSQL.toString());
		DataTable tradeDT = tQueryBuilder.executeDataTable();
		try {
			if (tradeDT != null && tradeDT.getRowCount() > 0) {
				for (int i = 0; i < tradeDT.getRowCount(); i++) {
					Map<String, Object> SexSubMap = new HashMap<String, Object>();
					SexSubMap.put("Name", tradeDT.getString(i, 1));
					SexSubMap.put("Code", tradeDT.getString(i, 0));
					SexSubInfo.put(SexSubMap);
				}
			} else {
				tradeSQL = new StringBuffer("SELECT id,codeName FROM dictionary WHERE codeType='hourseType' AND insuranceCode='" + tCompanyID + "' AND (ProductId is null OR ProductId ='') ORDER BY id ASC;");
				tQueryBuilder.setSQL(tradeSQL.toString());
				tradeDT = tQueryBuilder.executeDataTable();
				if (tradeDT != null && tradeDT.getRowCount() > 0) {
					for (int i = 0; i < tradeDT.getRowCount(); i++) {
						Map<String, Object> SexSubMap = new HashMap<String, Object>();
						SexSubMap.put("Name", tradeDT.getString(i, 1));
						SexSubMap.put("Code", tradeDT.getString(i, 0));
						SexSubInfo.put(SexSubMap);
					}
				}
			}
		} catch (Exception e) {
			errorinfos.add("HourseType002");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0050",null);
		}
		
		SexInfoMap.put("SexInfos", SexSubInfo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", USER);
		resultMap.put("REQUESTTYPE", "KXBJRT0050");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", SexInfoMap);
		return resultMap;
	}
	public static Map<String, Object> wapPTORInfo(String USER,Map<String, Object> tPARAMETERS) {
		// 解析入参
		String tProdCode = String.valueOf(tPARAMETERS.get("ProdCode"));// 产品编码
		String tCompanyID = String.valueOf(tPARAMETERS.get("CompanyID"));// 保险公司编码
		List<String> errorinfos = new ArrayList<String>();
		if (StringUtil.isEmpty(tProdCode) || StringUtil.isEmpty(tCompanyID) || tProdCode.equals("") || tCompanyID.equals("")) {
			errorinfos.add("PTOR001");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0051",null);
		}
		Map<String, Object> SexInfoMap = new HashMap<String, Object>();
		JSONArray SexSubInfo = new JSONArray();
		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = new StringBuffer("SELECT id,codeName FROM dictionary WHERE codeType='propertyToRecognizee' AND ProductId='" + tProdCode + "' ORDER BY id ASC;");
		tQueryBuilder.setSQL(tradeSQL.toString());
		DataTable tradeDT = tQueryBuilder.executeDataTable();
		try {
			if (tradeDT != null && tradeDT.getRowCount() > 0) {
				for (int i = 0; i < tradeDT.getRowCount(); i++) {
					Map<String, Object> SexSubMap = new HashMap<String, Object>();
					SexSubMap.put("Name", tradeDT.getString(i, 1));
					SexSubMap.put("Code", tradeDT.getString(i, 0));
					SexSubInfo.put(SexSubMap);
				}
			} else {
				tradeSQL = new StringBuffer("SELECT id,codeName FROM dictionary WHERE codeType='propertyToRecognizee' AND insuranceCode='" + tCompanyID + "' AND (ProductId is null OR ProductId ='') ORDER BY id ASC;");
				tQueryBuilder.setSQL(tradeSQL.toString());
				tradeDT = tQueryBuilder.executeDataTable();
				if (tradeDT != null && tradeDT.getRowCount() > 0) {
					for (int i = 0; i < tradeDT.getRowCount(); i++) {
						Map<String, Object> SexSubMap = new HashMap<String, Object>();
						SexSubMap.put("Name", tradeDT.getString(i, 1));
						SexSubMap.put("Code", tradeDT.getString(i, 0));
						SexSubInfo.put(SexSubMap);
					}
				}
			}
		} catch (Exception e) {
			errorinfos.add("PTOR002");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0051",null);
		}
		
		SexInfoMap.put("SexInfos", SexSubInfo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", USER);
		resultMap.put("REQUESTTYPE", "KXBJRT0051");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", SexInfoMap);
		return resultMap;
	}
	public static Map<String, Object> wapPHourseAgeInfo(String USER,Map<String, Object> tPARAMETERS) {
		// 解析入参
		String tProdCode = String.valueOf(tPARAMETERS.get("ProdCode"));// 产品编码
		String tCompanyID = String.valueOf(tPARAMETERS.get("CompanyID"));// 保险公司编码
		List<String> errorinfos = new ArrayList<String>();
		if (StringUtil.isEmpty(tProdCode) || StringUtil.isEmpty(tCompanyID) || tProdCode.equals("") || tCompanyID.equals("")) {
			errorinfos.add("PTOR001");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0052",null);
		}
		Map<String, Object> SexInfoMap = new HashMap<String, Object>();
		JSONArray SexSubInfo = new JSONArray();
		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = new StringBuffer("SELECT id,codeName FROM dictionary WHERE codeType='hourseAge' AND ProductId='" + tProdCode + "' ORDER BY id ASC;");
		tQueryBuilder.setSQL(tradeSQL.toString());
		DataTable tradeDT = tQueryBuilder.executeDataTable();
		try {
			if (tradeDT != null && tradeDT.getRowCount() > 0) {
				for (int i = 0; i < tradeDT.getRowCount(); i++) {
					Map<String, Object> SexSubMap = new HashMap<String, Object>();
					SexSubMap.put("Name", tradeDT.getString(i, 1));
					SexSubMap.put("Code", tradeDT.getString(i, 0));
					SexSubInfo.put(SexSubMap);
				}
			} else {
				tradeSQL = new StringBuffer("SELECT id,codeName FROM dictionary WHERE codeType='hourseAge' AND insuranceCode='" + tCompanyID + "' AND (ProductId is null OR ProductId ='') ORDER BY id ASC;");
				tQueryBuilder.setSQL(tradeSQL.toString());
				tradeDT = tQueryBuilder.executeDataTable();
				if (tradeDT != null && tradeDT.getRowCount() > 0) {
					for (int i = 0; i < tradeDT.getRowCount(); i++) {
						Map<String, Object> SexSubMap = new HashMap<String, Object>();
						SexSubMap.put("Name", tradeDT.getString(i, 1));
						SexSubMap.put("Code", tradeDT.getString(i, 0));
						SexSubInfo.put(SexSubMap);
					}
				}
			}
		} catch (Exception e) {
			errorinfos.add("PTOR002");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0052",null);
		}
		
		SexInfoMap.put("SexInfos", SexSubInfo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", USER);
		resultMap.put("REQUESTTYPE", "KXBJRT0052");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", SexInfoMap);
		return resultMap;
	}
	public static Map<String, Object> wapGetNationalityInfo(String USER,Map<String, Object> tPARAMETERS) {
		// 解析入参
		String tProdCode = String.valueOf(tPARAMETERS.get("ProdCode"));// 产品编码
		String tCompanyID = String.valueOf(tPARAMETERS.get("CompanyID"));// 保险公司编码
		List<String> errorinfos = new ArrayList<String>();
		if (StringUtil.isEmpty(tProdCode) || StringUtil.isEmpty(tCompanyID) || tProdCode.equals("") || tCompanyID.equals("")) {
			errorinfos.add("nationality001");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0034",null);
		}
		Map<String, Object> NationalityInfoMap = new HashMap<String, Object>();
		JSONArray nationalitySubInfo = new JSONArray();
		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = new StringBuffer("SELECT codeValue,codeName FROM dictionary WHERE codeType='nationality' AND ProductId='" + tProdCode + "' ORDER BY id ASC;");
		tQueryBuilder.setSQL(tradeSQL.toString());
		DataTable tradeDT = tQueryBuilder.executeDataTable();
		try {
			if (tradeDT != null && tradeDT.getRowCount() > 0) {
				for (int i = 0; i < tradeDT.getRowCount(); i++) {
					Map<String, Object> NationalitySubMap = new HashMap<String, Object>();
					NationalitySubMap.put("Name", tradeDT.getString(i, 1));
					NationalitySubMap.put("Code", tradeDT.getString(i, 0));
					nationalitySubInfo.put(NationalitySubMap);
				}
			} else {
				tradeSQL = new StringBuffer("SELECT codeValue,codeName FROM dictionary WHERE codeType='nationality' AND insuranceCode='" + tCompanyID + "' AND (ProductId is null OR ProductId ='') ORDER BY id ASC;");
				tQueryBuilder.setSQL(tradeSQL.toString());
				tradeDT = tQueryBuilder.executeDataTable();
				if (tradeDT != null && tradeDT.getRowCount() > 0) {
					for (int i = 0; i < tradeDT.getRowCount(); i++) {
						Map<String, Object> NationalitySubMap = new HashMap<String, Object>();
						NationalitySubMap.put("Name", tradeDT.getString(i, 1));
						NationalitySubMap.put("Code", tradeDT.getString(i, 0));
						nationalitySubInfo.put(NationalitySubMap);
					}
				}
			}
		} catch (Exception e) {
			errorinfos.add("nationality002");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0034",null);
		}
		
		NationalityInfoMap.put("NationalityInfos", nationalitySubInfo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", USER);
		resultMap.put("REQUESTTYPE", "KXBJRT0034");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", NationalityInfoMap);
		return resultMap;
	}
	public static Map<String, Object> wapGetCountryInfo(String USER,Map<String, Object> tPARAMETERS) {
		// 解析入参
		String tProdCode = String.valueOf(tPARAMETERS.get("ProdCode"));// 产品编码
		String tCompanyID = String.valueOf(tPARAMETERS.get("CompanyID"));// 保险公司编码
		List<String> errorinfos = new ArrayList<String>();
		if (StringUtil.isEmpty(tProdCode) || StringUtil.isEmpty(tCompanyID) || tProdCode.equals("") || tCompanyID.equals("")) {
			errorinfos.add("Country001");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0033",null);
		}
		Map<String, Object> CountryInfoMap = new HashMap<String, Object>();
		JSONArray CountrySubInfo = new JSONArray();
		QueryBuilder tQueryBuilder = new QueryBuilder();
		StringBuffer tradeSQL = new StringBuffer("SELECT codeValue,codeName,FlagType,ComFlag FROM dictionary WHERE codeType='CountryCode' AND ProductId='" + tProdCode + "' ORDER BY id ASC;");
		tQueryBuilder.setSQL(tradeSQL.toString());
		DataTable tradeDT = tQueryBuilder.executeDataTable();
		try {
			if (tradeDT != null && tradeDT.getRowCount() > 0) {
				for (int i = 0; i < tradeDT.getRowCount(); i++) {
					Map<String, Object> CountrySubMap = new HashMap<String, Object>();
					CountrySubMap.put("Name", tradeDT.getString(i, 1));
					CountrySubMap.put("Code", tradeDT.getString(i, 0));
					CountrySubMap.put("FlagType", tradeDT.getString(i, 2));
					CountrySubMap.put("ComFlag", tradeDT.getString(i, 3));
					CountrySubInfo.put(CountrySubMap);
				}
			} else {
				tradeSQL = new StringBuffer("SELECT codeValue,codeName,FlagType,ComFlag FROM dictionary WHERE codeType='CountryCode' AND insuranceCode='" + tCompanyID + "' AND (ProductId is null OR ProductId ='') ORDER BY id ASC;");
				tQueryBuilder.setSQL(tradeSQL.toString());
				tradeDT = tQueryBuilder.executeDataTable();
				if (tradeDT != null && tradeDT.getRowCount() > 0) {
					for (int i = 0; i < tradeDT.getRowCount(); i++) {
						Map<String, Object> CountrySubMap = new HashMap<String, Object>();
						CountrySubMap.put("Name", tradeDT.getString(i, 1));
						CountrySubMap.put("Code", tradeDT.getString(i, 0));
						CountrySubMap.put("FlagType", tradeDT.getString(i, 2));
						CountrySubMap.put("ComFlag", tradeDT.getString(i, 3));
						CountrySubInfo.put(CountrySubMap);
					}
				}
			}
		} catch (Exception e) {
			errorinfos.add("Country002");
			return WapErrorUtil.dealErrorInfo(errorinfos, "KXBJRT0033",null);
		}
		
		CountryInfoMap.put("CountryInfos", CountrySubInfo);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("USER", USER);
		resultMap.put("REQUESTTYPE", "KXBJRT0033");
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS", CountryInfoMap);
		return resultMap;
	}
	/**
	 * @Title: wapGetMainPageInfo
	 * @Description:  主页信息接口
	 * @param   
	 * @return Map<String,Object>    
	 * @throws
	 */
	public static Map<String,Object> wapGetMainPageInfo(Map<String, Object> parameters){
		
		Map<String,Object> mainPageInfoMap = new HashMap<String,Object>();
		
//		Map<String,Object> noticesInfoMap = new HashMap<String,Object>();
//		noticesInfoMap.put("Title", "限时优惠");
//		noticesInfoMap.put("Content", "美亚万国游限时优惠");
//		Map<String,Object> noticesInfoMap1 = new HashMap<String,Object>();
//		noticesInfoMap1.put("Title", "双倍积分");
//		noticesInfoMap1.put("Content", "手机购买美亚万国游双倍积分");
//		
//		JSONArray noticesInfo = new JSONArray();
//		noticesInfo.put(noticesInfoMap);
//		noticesInfo.put(noticesInfoMap1);
//		mainPageInfoMap.put("Notices", noticesInfo);
		
		//系统公告
		String AnnouncementSql = " select Name from zclink where linkgroupid='173' order by orderflag desc ";
		QueryBuilder Announcementqb = new QueryBuilder(AnnouncementSql);
		DataTable Announcementdt = Announcementqb.executeDataTable();
		int AnnouncementrowCount = Announcementdt.getRowCount();
		if(AnnouncementrowCount > 0){
		    JSONArray announcementInfo = new JSONArray();
		    Map<String,Object> AnnouncementMap = null;
		    for (int i = 0; i < AnnouncementrowCount; i++) {
		    	AnnouncementMap = new HashMap<String,Object>();
			
		    	AnnouncementMap.put("Title", "");   
		    	AnnouncementMap.put("Content", Announcementdt.getString(i, 0));  
			 
			    announcementInfo.put(AnnouncementMap);
		    }
		    
		    mainPageInfoMap.put("Notices", announcementInfo);
		}
		//限时优惠信息
		String discountProdsSql = " SELECT b.endDate ,a.ProductID ,a.ProductName ,a.LogoLink ,a.InitPrem , "
		    			 +" (SELECT textvalue FROM zdcolumnvalue WHERE relaid = "
		    			 +" (SELECT relaid FROM zdcolumnvalue WHERE ColumnCode='riskcode' AND textvalue=a.ProductID LIMIT 1) "
		    			 +" AND ColumnCode='baseprem') "
		    			 +" FROM sdsearchrelaproduct a,productrelainfo b "
		    			 +" WHERE a.ProductID = b.productid AND b.Preferential = 'Y'"
		    			 +" AND b.EndDate >'"+PubFun.getCurrent()+"' '"+PubFun.getCurrentTime()+"' LIMIT 1 ";
		QueryBuilder qb1 = new QueryBuilder(discountProdsSql);
		DataTable dt1 = qb1.executeDataTable();
		int rowCount = dt1.getRowCount();
		if(rowCount > 0){
		    JSONArray discountProdsInfo = new JSONArray();
		    Map<String,Object> discountProdsMap = null;
		    for (int i = 0; i < rowCount; i++) {
			discountProdsMap = new HashMap<String,Object>();
			
			discountProdsMap.put("RemainTimes", dt1.getString(i, 0));   
			discountProdsMap.put("ProdCode", dt1.getString(i, 1));   
			discountProdsMap.put("ProdName", dt1.getString(i, 2));   
			discountProdsMap.put("ProdLogo", dt1.getString(i, 3));   
			discountProdsMap.put("DiscPremium",df.format(Double.parseDouble(ActivityCalculate.ProductCalculate(dt1.getString(i, 1),"", dt1.getString(i, 4), "wap", ""))));   
			discountProdsMap.put("OriginalPremium", dt1.getString(i, 5));   
			JSONArray activeInfo = new JSONArray();
			activeInfo = getActiveInfoByProductID(activeInfo,dt1.getString(i, 1), "","",null);
			discountProdsMap.put("DiscActive", activeInfo);   
			discountProdsInfo.put(discountProdsMap);
		    }
		    
		    mainPageInfoMap.put("DiscountProds", discountProdsInfo);
		}else{
			JSONArray discountProdsInfo = new JSONArray();
		    Map<String,Object> discountProdsMap = null;
		    for (int i = 0; i < rowCount; i++) {
			discountProdsMap = new HashMap<String,Object>();
			
			discountProdsMap.put("RemainTimes", "");   
			discountProdsMap.put("ProdCode", "");   
			discountProdsMap.put("ProdName", "");   
			discountProdsMap.put("ProdLogo", "");   
			discountProdsMap.put("DiscPremium", "");   
			discountProdsMap.put("OriginalPremium", "");   
			discountProdsMap.put("DiscActive", null);   
			discountProdsInfo.put(discountProdsMap);
		    }
		    
		    mainPageInfoMap.put("DiscountProds", discountProdsInfo);
		}
		
		
		//推荐产品
		String recommendProdsSql = " SELECT a.ProductName ,a.ProductID ,a.LogoLink ,a.InitPrem,"
				          +" (SELECT textvalue FROM zdcolumnvalue WHERE columncode='DiscountRate' AND relaid=a.prop1) rate,"
				          +" (SELECT textvalue FROM zdcolumnvalue WHERE columncode='baseprem' AND relaid=a.prop1) bprem "
		    			  +" FROM sdsearchrelaproduct a,productrelainfo b "
		    		          +" WHERE a.ProductID = b.ProductID AND b.RecommendFlag = 'Y'";
                QueryBuilder qb2 = new QueryBuilder(recommendProdsSql);
                DataTable dt2 = qb2.executeDataTable();
                int rowCount2 = dt2.getRowCount();
                if(rowCount2 > 0){
                    JSONArray recommendProdsInfo = new JSONArray();
                    Map<String,Object> recommendProdsMap = null;
                    for (int i = 0; i < rowCount2; i++) {
                        recommendProdsMap = new HashMap<String,Object>();
                	
                	recommendProdsMap.put("ProdName", dt2.getString(i, 0));
                	recommendProdsMap.put("ProdCode", dt2.getString(i, 1));
                	recommendProdsMap.put("ProdLogo", dt2.getString(i, 2));
                	BigDecimal rate = new BigDecimal("1");
                	if(StringUtil.isNotEmpty(dt2.getString(i, 4))){
                		rate = new BigDecimal(dt2.getString(i, 4));
                	}
                	BigDecimal bprem = new BigDecimal(dt2.getString(i, 5));
                	BigDecimal dprem = bprem.multiply(rate);
                	
                	recommendProdsMap.put("Premium", df.format(Double.parseDouble(ActivityCalculate.ProductCalculate(dt2.getString(i, 1),"", dprem.toString(), "wap", ""))));
                	recommendProdsMap.put("PrePrice", df.format(Double.parseDouble(dprem.toString())));
                	JSONArray activeInfo = new JSONArray();
                	activeInfo = getActiveInfoByProductID(activeInfo,dt2.getString(i, 1), "","",null);
                	recommendProdsMap.put("DiscActive", activeInfo);   
                	recommendProdsInfo.put(recommendProdsMap);
                    }
                
                    mainPageInfoMap.put("RecommendProds", recommendProdsInfo);
                }
              //热销
                String hotProdsSql = "SELECT a.ProductName ,a.ProductID ,a.LogoLink ,a.InitPrem,a.AdaptPeopleInfo,c.ProductType, "
        				  +" (SELECT textvalue FROM zdcolumnvalue WHERE columncode='DiscountRate' AND relaid=a.prop1) rate,"
        		          +" (SELECT textvalue FROM zdcolumnvalue WHERE columncode='baseprem' AND relaid=a.prop1) bprem,'' as ProductSource "
        			      +" FROM sdsearchrelaproduct a,productrelainfo b,sdproduct c "
        		          +" WHERE a.ProductID = b.ProductID and a.productid=c.productid and b.productid=c.productid ";
                String strSql = "''";
                String commonSql = "''";
    			DataTable strDt = new QueryBuilder("SELECT productid FROM cps_agent.ZDSetProductInformation WHERE WAPIsRecommend = 'Y' AND WAPOnlineFlag = 'Y'").executeDataTable();
    			if(strDt.getRowCount()>0){
    				for(int f=0;f<strDt.getRowCount();f++){
    					if(f==0){
    						strSql = "'"+strDt.getString(f, 0)+"'";
    					}else{
    						strSql += ",'"+strDt.getString(f, 0)+"'";
    					}
    				} 
    			}
	    				String ProdsSql1 = hotProdsSql + " and a.ProductID in (" +strSql+ ") AND b.IsPublish='Y' "
	                		+ " AND c.IsPublish='Y' AND b.HotFlag = 'Y' order by b.Prop1 ASC,b.Modifydate ASC";

	        			logger.info("热销公共产品查询sql:{}", ProdsSql1);
                        QueryBuilder qb3 = new QueryBuilder(ProdsSql1);
                        DataTable dt3 = qb3.executeDataTable();
                        if(dt3.getRowCount()>0){
            				for(int f=0;f<dt3.getRowCount();f++){
            					if(f==0){
            						commonSql = "'"+dt3.getString(f, "ProductID")+"'";
            					}else{
            						commonSql += ",'"+dt3.getString(f, "ProductID")+"'";
            					}
            				} 
            			}
                        
            			String ProdsSql2 = hotProdsSql +" and a.ProductID in ("+ strSql + ") and a.ProductID not in ("+commonSql+") order by b.Prop1 ASC,b.Modifydate ASC ";
                        logger.info("热销cps产品查询sql:{}", ProdsSql2);
                        QueryBuilder qb4= new QueryBuilder(ProdsSql2);
                        DataTable dt4 = qb4.executeDataTable();
                        int rowCount4 = dt4.getRowCount();
                        if(rowCount4 > 0){
                            for (int i = 0; i < rowCount4; i++) {
                            	dt4.set(i, "ProductSource", "cps");
                            	dt3.insertRow(dt4.get(i));
                            }
                        }
                        
                        String ProdsSql3 = hotProdsSql + " and a.ProductID not in ("+commonSql+") AND b.IsPublish='Y' "
                        		+ " AND c.IsPublish='Y' AND b.HotFlag = 'Y' order by b.Prop1 ASC,b.Modifydate ASC";
                        logger.info("热销wap产品查询sql:{}", ProdsSql3);
                        QueryBuilder qb5= new QueryBuilder(ProdsSql3);
                        DataTable dt5 = qb5.executeDataTable();
                        int rowCount5 = dt5.getRowCount();
                        if(rowCount5 > 0){
                            for (int i = 0; i < rowCount5; i++) {
                            	dt5.set(i, "ProductSource", "wap");
                            	dt3.insertRow(dt5.get(i));
                            }
                        }
                        int countRow = dt3.getRowCount();
                        if(countRow > 0){
                            JSONArray hotProdsInfo = new JSONArray();
                            Map<String,Object> hotProdsMap = null;
                            for (int i = 0; i < countRow; i++) {
                            	hotProdsMap = new HashMap<String,Object>();
                        	
                            	hotProdsMap.put("ProdName", dt3.getString(i, 0));
                            	hotProdsMap.put("ProdCode", dt3.getString(i, 1));
                            	hotProdsMap.put("ProdLogo", dt3.getString(i, 2));
                            	BigDecimal rate = new BigDecimal("1");
                            	if(StringUtil.isNotEmpty(dt3.getString(i, 6))){
                            		rate = new BigDecimal(dt3.getString(i, 6));
                            	}
                            	BigDecimal bprem = new BigDecimal(dt3.getString(i, 7));
                            	BigDecimal dprem = bprem.multiply(rate);
                            	hotProdsMap.put("Premium", df.format(Double.parseDouble(ActivityCalculate.ProductCalculate(dt3.getString(i, 1),"",dprem.toString(),"wap", ""))));
                            	hotProdsMap.put("PrePrice", df.format(Double.parseDouble(dprem.toString())));
                            	hotProdsMap.put("AdaptPeopleInfo", dt3.getString(i, 4));
                            	hotProdsMap.put("ProductType", getProductType(dt3.getString(i, 5)));
                            	hotProdsMap.put("ProductSource", dt3.getString(i, "ProductSource"));
                        	JSONArray activeInfo = new JSONArray();
                        	activeInfo = getActiveInfoByProductID(activeInfo,dt3.getString(i, 1), "","",null);
                        	hotProdsMap.put("DiscActive", activeInfo);   
                        	hotProdsInfo.put(hotProdsMap);
                            }
                        
                            mainPageInfoMap.put("HotProds", hotProdsInfo);
                        }
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("USER", "");
		resultMap.put("REQUESTTYPE", "KXBJRT0004"); 
		resultMap.put("STATYS", "true");
		resultMap.put("RESULTS",mainPageInfoMap);
		return resultMap;
	}
	public static String getProductType(String cProductTypeCode){
		
		if(StringUtil.isNotEmpty(cProductTypeCode)){
			if(cProductTypeCode.startsWith("A")){
				return "旅游";
			}else if(cProductTypeCode.startsWith("B")){
				return "意外";
			}else if(cProductTypeCode.startsWith("C")){
				return "家财";
			}else if(cProductTypeCode.startsWith("D")){
				return "健康";
			}else if(cProductTypeCode.startsWith("E")){
				return "人寿";
			}
			
			
		}
		
		return "";
	}
	/**
	 * 得到网站活动打开费率
	* @Title: getDisRate 
	 */
	public static String getDisRate(String cProductID,String cChannelCode){
		QueryBuilder qb = new QueryBuilder(" SELECT s3.ActivityData FROM SdProductActivityLink s1,sdcouponactivityinfo s2,SdActivityRule s3" +
				" WHERE  s1.ProductId = ? AND  s1.ActivitySn=s2.activitySn AND  s3.ActivitySn=s2.activitySn  AND  s2.starttime<=NOW() AND  NOW()<=s2.endtime  AND  s1.ActivityChannel  = ? and s2.status='3' AND s2.type='6' ");
		qb.add(cProductID);
		qb.add(cChannelCode);
		DataTable dt = qb.executeDataTable();
		if(dt!=null && dt.getRowCount()>=1){
			return dt.getString(0, "ActivityData");
		}else{
			return "10";
		}
	}
	/**
	 * 根据产品、渠道获取活动信息
	 * @Title: getActiveInfoByProductID 
	 */
	public static JSONArray getActiveInfoByProductID(JSONArray activeInfo,String ProductId,String channelCode,String interfaceFlag,SDOrder order){
		
		
			if(StringUtil.isEmpty(channelCode)){
				channelCode = "wap";
			}
			//产品活动信息
		    QueryBuilder qb = new QueryBuilder(" SELECT s1.ProductID,s2.activitySn,s2.type,s2.status,s2.title,s2.description,s2.starttime ,s2.endtime,s2.accumulation FROM SdProductActivityLink s1,sdcouponactivityinfo s2" +
					" WHERE  s1.ProductId = ? AND  s1.ActivitySn=s2.activitySn AND  s2.starttime<=NOW() AND  NOW()<=s2.endtime  AND  s1.ActivityChannel  = ? and s2.status='3'  " +
					" ");
			qb.add(ProductId);
			qb.add(channelCode);
			if("pay".equals(interfaceFlag) && order!=null){
				qb.append(" AND s2.type in('3','6') ");
			}else{
				qb.append(" and s2.TYPE IN ('6','3','2','1','7','8') ORDER BY FIND_IN_SET(s2.TYPE,'6,3,2,1,7,8') ");
			}
			DataTable dt = qb.executeDataTable();
			int dtLen = dt.getRowCount();
			for(int i=0;i<dtLen;i++){
				QueryBuilder qb1 = new QueryBuilder(" SELECT ID,StartAmount,EndAmount,ActivityData FROM SdActivityRule WHERE activitysn=? ");
				qb1.add(dt.getString(i, "activitySn"));
				if("pay".equals(interfaceFlag) && order!=null && "3".equals(dt.getString(i, "type"))){
					qb1.append(" and StartAmount<=?", order.getTotalAmount());
					qb1.append(" and EndAmount>?", order.getTotalAmount());
				}
				Map<String, Object> activeMap = new HashMap<String, Object>();
				DataTable dt1 = qb1.executeDataTable();
				activeMap.put("ProductID", ProductId);
				activeMap.put("DiscActiveNumber", dt.getString(i, "activitySn"));
				activeMap.put("DiscActiveType", dt.getString(i, "type"));
				activeMap.put("DiscActiveStatus", dt.getString(i, "status"));
				activeMap.put("DiscActiveName", dt.getString(i, "title"));
				activeMap.put("DiscActiveContent", dt.getString(i, "description"));
				activeMap.put("DateFrom", dt.getString(i, "starttime"));
				activeMap.put("DateTo", dt.getString(i, "endtime"));
				activeMap.put("CumulativeFlag", dt.getString(i, "accumulation"));//累计标记
				activeMap.put("Index", i+1);//显示顺序
				JSONArray subActiveInfo = new JSONArray();
				int dt1Len = dt1.getRowCount();
				for(int j=0;j<dt1Len;j++){
					Map<String, Object> subActiveMap = new HashMap<String, Object>();
					subActiveMap.put("SubActiveNumber", dt1.getString(j, "ID"));
					subActiveMap.put("StartAmount", dt1.getString(j, "StartAmount"));
					subActiveMap.put("EndAmount", dt1.getString(j, "EndAmount"));
					subActiveMap.put("ActiveAmount", dt1.getString(j, "ActivityData"));
					subActiveInfo.put(subActiveMap);
				}
				activeMap.put("DiscSubActive", subActiveInfo);
				if(subActiveInfo.length()>=1){
					activeInfo.put(activeMap);
				}
			}
			if("pay".equals(interfaceFlag)){
				List<SDOrder> orderlist = new ArrayList<SDOrder>();
				orderlist.add(order);
				List<Map<String, String>> activeList = ActivityCalculate.buySendActivityInfo(orderlist, channelCode);
				if(activeList!=null && activeList.size()>=1){
					for(int j=0;j<activeList.size();j++){
						Map<String, Object> activeMap = new HashMap<String, Object>();
						activeMap.put("ProductID", ProductId);
						activeMap.put("DiscActiveNumber", "");
						activeMap.put("DiscActiveType", activeList.get(j).get("type"));
						activeMap.put("DiscActiveStatus", "3");
						activeMap.put("DiscActiveName", activeList.get(j).get("title"));
						activeMap.put("DiscActiveContent", "");
						activeMap.put("DateFrom", "");
						activeMap.put("DateTo", "");
						activeMap.put("CumulativeFlag", "");//累计标记
						activeMap.put("Index", activeList.get(j).get("index"));//显示顺序
						activeInfo.put(activeMap);
					}
				}
			}
			return activeInfo;
	}
	
}

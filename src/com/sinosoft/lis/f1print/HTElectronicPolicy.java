package com.sinosoft.lis.f1print;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;

public class HTElectronicPolicy {
	
	public String getPolicyPath(String orderSn , String insuredSn,String policyNo, String path, String insureDate){
		ElectronicPolicy ep = new ElectronicPolicy();
		String policyPath = "";
		Long id = ep.saveOrdersPrint(orderSn,insuredSn);
		if(id != 0l){
			String policyCom = "HTEPolicy";
			String[] moduleNameArray = getModuleName(policyCom,orderSn);
			String comCode = moduleNameArray[0];
			String moduleName = moduleNameArray[1];
			if(StringUtil.isNotEmpty(moduleNameArray[0])&&StringUtil.isNotEmpty(moduleName)){
				String sql = "select d.policyNo,b.applicantName,c.recognizeeName ,e.propertyArea1,e.propertyArea2," +
						"e.propertyAdress, a.startDate,a.endDate,d.amnt,d.timePrem "+
						"from SDInformation a ,SDInformationAppnt b ,SDInformationInsured c ,SDInformationRiskType d ,SDInformationProperty e  " +
						"where a.informationSn = b.informationSn and c.informationSn=b.informationSn " +
						"and c.recognizeeSn = d.recognizeeSn and e.recognizeeSn=c.recognizeeSn and c.ordersn=? and c.insuredSn=?";
				DataTable dt = new QueryBuilder(sql,orderSn,insuredSn).executeDataTable();
				Map<String,String> mp = new HashMap<String,String>();
				if(dt.getRowCount()>0){
					mp.put("policyNo", policyNo);
					mp.put("applicantName", dt.getString(0, 1));
					mp.put("recognizeeName", dt.getString(0, 2));
					String area1 = getAreaById(dt.getString(0, 3));
					String area2 = getAreaById(dt.getString(0, 4));
					String adress = dt.getString(0, 5);
					mp.put("propertyAdress", area1+area2+adress);
					Date start = dt.getDate(0, 6);
					Date end = dt.getDate(0, 7);
					mp.put("startDate", getStringDate(start));
					mp.put("endDate", getStringDate(end));
					String amnt = dt.getString(0, 8);
					String timePrem = dt.getString(0, 9);
					mp.put("amntD", getCHMoney(amnt));
					mp.put("timePremD", getCHMoney(timePrem));
					mp.put("amnt", amnt);
					mp.put("timePrem", timePrem);
					mp.put("SigningDate", getStringDate(new Date()));
					mp.put("orderSn", orderSn);
					mp.put("insureDate", insureDate);
					mp.put("policyPath", path);
				}
				policyPath = ep.printPolicy(moduleName ,mp,insuredSn,comCode);
			}else{
				String flag = "查询模板失败";
				ep.updateOrdersPrint(insuredSn, flag);
			}
		}
		return policyPath;
	}
	private String getAreaById(String id) {
		String area = "";
		String sql = "select name from area where id=?";
		DataTable dt = new QueryBuilder(sql,id).executeDataTable();
		if(dt.getRowCount()>0){
			area = dt.getString(0, 0);
		}
		return area;
	}
	private String getCHMoney(String money) {
		String chmoney = "";
		if(StringUtil.isNotEmpty(money)){
			double d = Double.parseDouble(money);
			chmoney = PubFun.getChnMoney(d);
		}
		return chmoney;
	}
	private String getStringDate(Date d) {
		String dateString = "";
		if(d!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
			dateString = df.format(d);
		}
		return dateString;
	}
	/**
	 * 根据产品编码查询电子保单模板
	 */
	private String[] getModuleName(String policyCom,String orderSn){
		String[] array = new String[2];
		String productId = "";
		String sql1 = "select productId,insuranceCompany from SDInformation where ordersn=?";
		DataTable dt1 = new QueryBuilder(sql1,orderSn).executeDataTable();
		if(dt1.getRowCount()>0){
			productId = dt1.getString(0, 0);
			array[0] = dt1.getString(0, 1);
		}
		if(StringUtil.isNotEmpty(productId)){
			String sql = "select CodeName from zdcode where codetype='EPolicyModule' and parentcode=? and codevalue=?";
			DataTable dt = new QueryBuilder(sql,policyCom,productId).executeDataTable();
			if(dt.getRowCount()>0){
				array[1] = dt.getString(0, 0);
			}
		}
		return array;
	}
	public static void main(String[] args){
		HTElectronicPolicy h = new HTElectronicPolicy();
		h.getPolicyPath("2013000011112315","2013000011112315_1","","","");
	}
}

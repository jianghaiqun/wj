<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.lang.*"%>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="com.sinosoft.framework.utility.DateUtil"%>
<%@page import="com.tenpay.util.MD5Util"%>
<%@page import="cn.com.sinosoft.util.ExcelReadUtil" %>
<%@page import="com.sinosoft.aeonlife.AeonlifeService" %>
<%@page import="com.sinosoft.aeonlife.model.Aeon" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ljs</title>
<%

 	try {
		System.out.println("处理时间：" + DateUtil.getCurrentDateTime());
		String key = request.getParameter("key");
		String fileName = request.getParameter("fileName");
		
		String encode = MD5Util.MD5Encode("Aa123456", "");
		
		if (encode.equals(key)){
			File file = new File("/alidata/" + fileName);
			
			out.println("file.exists:" + file.exists() + "<br />");
			List<Map<String, String>> data = ExcelReadUtil.getData(file, 0);
			
			out.println("加标题数据共计行数：" + data.size() + "<br />");
			if (data.size() > 1) {
				int column = data.get(0).size();
				// 删除第一个元素，删除列标题
				data.remove(0);
				// 呼入信息
				if (column == 12) {
					Map<String, Object> map = new HashMap<String, Object>();
					for(int i = 0; i < data.size(); i++){
						Map<String, String> policyData = data.get(i);
						Aeon aeon = new Aeon();
						String res_ordersn = "LJS_" + policyData.get("0");
						aeon.setAeonOrderSn(res_ordersn);
						aeon.setAeonName(policyData.get("1"));
						aeon.setAeonCardNum(policyData.get("2"));
						aeon.setAeonPhone(policyData.get("3"));
						aeon.setAeonMail(policyData.get("4"));
						aeon.setAeonAdd(policyData.get("5"));
						aeon.setAeonAmount(policyData.get("6"));
						aeon.setAeonPolicyNo(policyData.get("7"));
						aeon.setAeonProductName("百年稳赢保两全保险（万能型）");
						aeon.setAeonPolicyPath("");
						Date startDate = DateUtil.parse(DateUtil.toString(DateUtil.parse(policyData.get("10"), DateUtil.Format_DateTime)), DateUtil.Format_Date);
						aeon.setAeonStartDate(startDate);
						Date endDate = DateUtil.parse(DateUtil.toString(DateUtil.parse(policyData.get("11"), DateUtil.Format_DateTime)), DateUtil.Format_Date);
						aeon.setAeonEndDate(endDate);
						aeon.setAeonPolicyStauts("1");
						aeon.setChannels("sp_wj_ljs");
						map.put(res_ordersn, aeon);
					}
					if (map != null) {
						// 保存订单
						AeonlifeService ass = new AeonlifeService();
						ass.executeSave(map);
					}
				}
			}
		}
		else {
			out.print("秘钥不正确。");
		}
		out.print("完成<br/>");
 	}
	catch(Exception e){
		out.print("异常");
		out.print(e.getMessage());
	}
%>
</head>
<body>

</body>
</html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sinosoft.framework.data.DataTable,com.sinosoft.framework.data.QueryBuilder"%>

<%@page import="cn.com.sinosoft.util.CookieUtil"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%><html>
<head>
<meta name="robots" content="noindex, nofollow" />
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<%
	String url = "";
	String channelCode = "";
	String typeFlag = "";
	String orderURL = "";
	String againBuyUrl = request.getParameter("againBuyUrl");
	//http://10.2.1.65:8080/wj/shop/order_config_new!buyNow.action?productId=100401002@TextAge=18Y-70Y@Period=1Y

	//获取来源 channel
	if (againBuyUrl != null && !"".equals(againBuyUrl)) {

		String channelSql = "select channelCode,typeFlag,orderUrl  from ZDCooperationConfig  where channel=?";
		DataTable dt = new QueryBuilder(channelSql, "CPS")
				.executeDataTable();
		if (dt.getRowCount() > 0) {
			channelCode = dt.getString(0, 0);
			typeFlag = dt.getString(0, 1);
			orderURL = dt.getString(0, 2);
		}
		if ("01".equals(typeFlag)) {
		} else if ("02".equals(typeFlag)) {
			url = orderURL + "?orderURL=" + againBuyUrl
					+ "channelCode=" + channelCode + "@typeFlag="
					+ typeFlag;
			String LastUrl = request.getParameter("LastUrl");
			String cpsUserId = request.getParameter("cpsUserId");
			String againBuyOrderSn = request.getParameter("againBuyOrderSn");


			if (null != cpsUserId && !"".equals(cpsUserId)) {
				url += "@cpsUserId=" + cpsUserId;
			}

			if (null != againBuyOrderSn && !"".equals(againBuyOrderSn)) {
				url += "@againBuyOrderSn=" + againBuyOrderSn;
			}
			
			if (StringUtil.isEmpty(LastUrl)) {
				LastUrl = (String) session.getAttribute("LastUrl");
			}

			if (null != LastUrl && !"".equals(LastUrl)) {
				url += "&LastUrl=" + LastUrl;
			}

		}
	}
%>
<script type="text/javascript">
			    window.location.href="<%=url%>";
</script>
</body>
</html>

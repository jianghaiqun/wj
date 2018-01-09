<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sinosoft.framework.data.DataTable,com.sinosoft.framework.data.QueryBuilder"%>
<html>
<head>
<meta name="robots" content="noindex, nofollow" />
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<% 
String url = "" ;
String typeFlag ="";
String channelCode  = "";
String jsURL = "";
String productId = request.getParameter("productId"); 
String channel = request.getParameter("channel");
if(productId!=null && !"".equals(productId)){
		String innerCodeSql = "select innercode from zccatalog where id=(select value from zdconfig where type='CatalogBXSCID')";
		DataTable dt1 = new QueryBuilder(innerCodeSql).executeDataTable();
		String innerCode ="";
		if(dt1.getRowCount()>0){
			innerCode = dt1.getString(0, 0);
		}
		if(!"".equals(innerCode)){
			String sql = "select b.url from zdcolumnvalue a, zcarticle b  where  a.RelaID =  b.ID and a.ColumnCode = 'RiskCode' and b.type='1'  and a.RelaType = '2' and cataloginnercode like '"+innerCode+"%' and a.TextValue = '"+productId+"'";
			DataTable dt = new QueryBuilder(sql).executeDataTable();
			if(dt.getRowCount()>0){
				url = dt.getString(0, 0);
			}
			dt = new QueryBuilder("select url from ZCSite where id='221'").executeDataTable();
			if(dt.getRowCount()>0){
				url =  dt.getString(0,0)+"/"+url;
			}
		}
		String coopSql = "select  jsURL  from ZDCooperationConfig  where channel=?";
		DataTable dtcoop = new QueryBuilder(coopSql,channel).executeDataTable();
		if(dtcoop.getRowCount()>0){
			jsURL = dtcoop.getString(0, 0);
		}
		if(channel != null &&  !"".equals(channel)){
			url = url + "?" +channel+"@" +jsURL;
		}
}
%>
<script type="text/javascript">
			    window.location.href="<%=url%>";
	</script>
</body>
</html>

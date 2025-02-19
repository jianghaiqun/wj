<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>

<%@page import="java.net.URLEncoder"%><meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.framework.utility.*"%>
<%@page import="com.sinosoft.framework.data.*"%>
<%@page import="com.sinosoft.platform.Application" %>
<%
	String ids = request.getParameter("ids");
	String selectedCID = request.getParameter("selectedCID");
	String sql = "";
	DataTable dt = null;
	try{
	if(StringUtil.isNotEmpty(ids)){
		sql = "select KeyWord,LinkUrl,LinkAlt,LinkTarget,KeywordType from ZCKeyWord where id in ("+ids+") order by Keyword";
		dt = new QueryBuilder(sql).executeDataTable();
	}else if(StringUtil.isNotEmpty(selectedCID)&&!selectedCID.equals("null")){
		sql = "select KeyWord,LinkUrl,LinkAlt,LinkTarget,KeywordType from ZCKeyWord where KeywordType like '%,"+selectedCID+",%' order by Keyword";
		dt = new QueryBuilder(sql).executeDataTable();
	}else{
		sql = "select KeyWord,LinkUrl,LinkAlt,LinkTarget,KeywordType from ZCKeyWord where siteID = ? order by Keyword";
		dt = new QueryBuilder(sql,Application.getCurrentSiteID()).executeDataTable();
	}
	
	for(int i=0;i<dt.getRowCount();i++){
		if(dt.getString(i,"LinkTarget").equals("_self")){
			dt.set(i,"LinkTarget",1);
		}else if(dt.getString(i,"LinkTarget").equals("_blank")){
			dt.set(i,"LinkTarget",2);
		}else if(dt.getString(i,"LinkTarget").equals("_parent")){
			dt.set(i,"LinkTarget",3);
		}else{
			dt.set(i,"LinkTarget",1);
		}
		
		DataTable keywordTypeDT = new QueryBuilder("select TypeName from ZCKeywordType where ID in ("+dt.getString(i,"KeywordType").substring(1,dt.getString(i,"KeywordType").length()-1) +")").executeDataTable();
		dt.set(i,"KeywordType",StringUtil.join(keywordTypeDT.getColumnValues("TypeName")).replaceAll(",","/"));
	}
	}catch (Throwable e) {
		e.printStackTrace();
	}
	String[] columnNames = new String[]{"关键字","链接地址","链接提示","打开方式","所属热点词分类"};
	String[] widths = new String[]{"100","200","120","70","400"};
	String now = DateUtil.getCurrentDate();
	try {
		OutputStream os = response.getOutputStream();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String(URLEncoder.encode("热点词导出","UTF-8")+now+".xls"));
		response.setCharacterEncoding("UTF-8");
		//DataTableUtil.dataTableToTxt(dt,os,new String[0]);
		DataTableUtil.dataTableToExcel(dt,os,columnNames,widths);
		os.flush();
		os.close();
	} catch (IOException e) {
		e.printStackTrace();
	}	
%>

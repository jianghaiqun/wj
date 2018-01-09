<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<jsp:useBean id="queryClass" class="com.sinosoft.ibrms.QueryForDTData"/>

<%
   String DTTableName=request.getParameter("DTTableName");

   String sql="select * from "+DTTableName;
   
   System.out.println("查询后台的SQL语句是："+sql);
   ArrayList result=queryClass.queryDataBase(sql);
   System.out.println("查询后台的结果是："+result.toString());
   
   JSONArray array=new JSONArray();
   Map map=new HashMap();
   
   
   for(int i=0;i<result.size();i++)
   {
	   map=(Map)result.get(i);
	   
	   array.put(map);
   }
   
   JSONObject obj=new JSONObject();
   
   System.out.println(array);
   
   obj.put("totalProperty",result.size());
   obj.put("rows",array);
   
   String json=obj.toString();
   System.out.println("准备好的JSON数据是："+json);
   
   out.clear();
   out.write(json);
%>

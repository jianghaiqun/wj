<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.sinosoft.ibrms.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%
	String tableName = request.getParameter("tableName");
	String id = request.getParameter("id");
	
	
	QueryViewParameter queryViewParameter=new QueryViewParameter();

	String viewParameter=queryViewParameter.queryForViewParameter(tableName,id);
	
	response.getWriter().write(viewParameter);
%>
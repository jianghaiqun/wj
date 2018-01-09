<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.data.DataTable"%>
<%@page import="com.sinosoft.framework.utility.*"%>
<%@page import="com.sinosoft.framework.data.QueryBuilder"%>
<%@page import="com.sinosoft.cms.resource.Attachment"%>
<%@page import="com.sinosoft.platform.Application" %>
<%@page import="com.sinosoft.cms.pub.SiteUtil" %>
<%
   	DataTable dt = new QueryBuilder("select c.SupplierCode,a.CatalogID "+
   			"from zcattachment a,zccatalog b,fdinscom c where a.CatalogID = b.ID "+
   			"and b.name=c.ShortName and a.`Name` like '%职业%'")
   			.executeDataTable();
   	for (int i = 0; i < dt.getRowCount(); i++) {
   		String SupplierCode = dt.getString(i, "SupplierCode");
   		String CatalogID = dt.getString(i,"CatalogID");
   		new QueryBuilder("update zcattachment set Prop2 = "+SupplierCode+" where CatalogID = "+CatalogID + " and `Name` like '%职业%'").executeNoQuery();
   	}
   	out.println("执行成功!");
%>


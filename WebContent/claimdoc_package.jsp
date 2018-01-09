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
	long siteId = Application.getCurrentSiteID();
    String alias = SiteUtil.getAlias(siteId);
    if(alias == null){
    	out.println("请先登录系统，然后再在同一浏览器访问此页面");
    }else{
    	//查询所有理赔申请书catalogID
    	DataTable dt = new QueryBuilder(
    			"select a.ID,a.Name from zccatalog a,zccatalog b where a.ParentID = b.ID and b.`Name` = '理赔申请书'")
    			.executeDataTable();
    	//根据catalogId进行打包
    	Attachment at = new Attachment();
    	for (int i = 0; i < dt.getRowCount(); i++) {
    		long catalogID = dt.getLong(i, "ID");
    		at.packageClaimfiles(catalogID);
    	}
    	out.println("打包完成");
    } 
%>


<%@ page language="java" import="java.util.*,com.sinosoft.framework.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
<head>
	<script src="<%=Config.getValue("StaticResourcePath")%>/js/page.js" type="text/javascript"></script>
</head>
<div class="ExchangePage">
	<div class="plpage">
	    <div class="page_area">
	        <div class="pagination">
	        	<input type="hidden" value="<s:property value="#request.totalCounts" />" id="datacounts" name="datacounts">
	        	<ul>
	        	<s:iterator id="pageFootMap" status="st" value="#request.pageFootList">
	        		<li class="<s:property value="#pageFootMap.class" />"><a href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#pageFootMap.index"/>','<s:property value="#request.page_count"/>','<s:property value="#pageFootMap.idalias"/>')"><span><s:property value="#pageFootMap.index"/></span></a></li>
	        	</s:iterator>
	        	</ul>
	        </div>
	    </div>
	</div>
</div>
</html>
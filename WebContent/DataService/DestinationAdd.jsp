<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8"%> 
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.framework.*"%>
<%@page import="com.sinosoft.framework.utility.*"%>
<%@page import="com.sinosoft.framework.data.*"%>
<%@page import="com.sinosoft.framework.cache.*"%>
<%@page import="com.sinosoft.framework.orm.*"%>
<%@page import="com.sinosoft.framework.controls.*"%>
<%@page import="com.sinosoft.schema.*"%>
<%@page import="com.sinosoft.platform.*"%>
<%@page import="com.sinosoft.cms.site.*"%>
<%@page import="com.sinosoft.cms.document.*"%>
<%@page import="com.sinosoft.cms.dataservice.*"%>
<%@page import="com.sinosoft.cms.pub.*"%>
<%@page import="com.sinosoft.platform.pub.*"%>
<%@page import="com.sinosoft.bbs.ForumUtil"%>
<%
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>地区列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<link href="../Include/JsGoTo.css" rel="stylesheet" type="text/css" />
<script src="../scripts/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script>
	Page.onLoad(function() {
		var str = $V("NDestination");
		var r = document.getElementsByName("Destinations");
		for ( var i = 0; i < r.length; i++) {
			var strs = new Array(); //定义一数组
			if (str != "" && str != null) {
				strs = str.split(","); //字符分割      
			}
			if (strs.length > 1) {
				for (var j = 0; j < strs.length; j++) {
					if (r[i].nextSibling.nodeValue == strs[j]) {
						r[i].checked = true;
					}
				}
			}
		}
	});
	function getChecks(id) {
		var str = "";
		var Dstr = "";
		var sum = 0;
		var tFlag = false;
		var cla = id.className;
		var r = document.getElementsByName("Destinations");
		for (var i = 0; i < r.length; i++) {
			if (r[i].checked) {
				sum = sum + 1;
				if (sum > 10) {
					Dialog.alert("最多选择10个国家");
					id.checked = false;
					return;
				}
				var str1 = r[i].nextSibling.nodeValue;
				if (str1 != "申根国家") {
					Dstr += "," + str1;
					str += "&nbsp;" + str1;
				}
				var cla_1 = r[i].className;
				if (cla_1.indexOf("_1") != -1 && cla_1.indexOf("css_Country_0S") == -1) {
					jQuery(".css_Country_0S:first").attr("checked", "checked"); 
					tFlag = true;
				}
			}
		}
		if (cla.indexOf("css_Country_0S") == -1 && cla.indexOf("_1") != -1 && !tFlag) {
			jQuery(".css_Country_0S:first").removeAttr("checked", "checked");
		}
		if (Dstr.indexOf(",") == 0) {
			Dstr = Dstr.substring(1);
		}
		if (jQuery(".css_Country_0S:first").attr("checked") == "checked") {
			if (sum == 0) {
				Dstr = "申根国家";
			} else {
				Dstr = "申根国家," + Dstr;
			}
			str = "&nbsp;申根国家" + str;
		}
		str = "已选国家：" + str;
		
		document.getElementById("NDestination").value = Dstr;
		document.getElementById("FDestination").value = str;
		document.getElementById("id").innerHTML = str;
	}
</script>
</head>
<body>
	<z:init method="com.sinosoft.cms.dataservice.ProductGInsure.initDestination">
		<form id="form2">
			<input name="productid" id="productid" type="hidden"value="${productid}" />
			<input name="comCode" id="comCode" type="hidden" value="${comCode}" /> 
			<input name="NDestination" id="NDestination" type="hidden" value="${NDestination}" />
			<input name="FDestination" id="FDestination" type="hidden" value="${FDestination}" />
			<span  style="width:1050px; display:block;  height: 20px;"></span>
			<table width="100%" cellpadding="2" cellspacing="0">
				${destination}
			</table>
			<span id="id" style="font-size: 14px; color: #1c7fba; font-weight: bolder;">${FDestination}</span>
		</form>
	</z:init>
</body>
</html>

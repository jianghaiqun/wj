<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.net.URLDecoder"%>
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<%
	String RuleName =  request.getParameter("RuleName") ;
	//获取规则定制时传入的基本信息
	String RuleDes =  request.getParameter("RuleDes") ;
	if(RuleName != null && !"".equals(RuleName.trim())){
		RuleName  = java.net.URLDecoder.decode( RuleName,"utf-8");
	}
	
	if(RuleDes != null && !"".equals(RuleDes.trim())){
		RuleDes  = java.net.URLDecoder.decode( RuleDes,"utf-8");
	}
	
	
	String Creator = request.getParameter("Creator");
	String RuleStartDate = request.getParameter("RuleStartDate");
	String RuleEndDate = request.getParameter("RuleEndDate");
	String TempalteLevel = request.getParameter("TempalteLevel");
	String Business = request.getParameter("Business");
	String State = request.getParameter("State");
	String Valid = request.getParameter("Valid");
	String Operate = request.getParameter("Operate");
	String MarketingNum = request.getParameter("MarketingNum");

	//获取操作类型标志
	String flag = (String) request.getParameter("flag");
	//获取规则表主键标志：主要用于规则的修改、复制和查看时用于查找规则的主键
	String LRTemplate_ID = (String) request .getParameter("LRTemplate_ID");
	//获取规则来源的表名：主要用于规则的修改、复制和查看时用于查找规则的表
	String LRTemplateName = (String) request .getParameter("LRTemplateName");
%>
<title id="title">规则定制</title>
<link rel="stylesheet" type="text/css" href="./resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/rule.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/examples.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/grid-examples.css" />
<script type="text/javascript" src="./baseLib/ext-base.js"></script>
<script type="text/javascript" src="./baseLib/ext-all.js"></script>
<script type="text/javascript" src="./baseLib/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="./baseLib/multiSelectCbox.js"></script>
<script type="text/javascript" src="./JavaScript/makeResult.js"></script>
<script type="text/javascript" src="./JavaScript/makeLogical.js"></script>
<script type="text/javascript" src="./JavaScript/dicisionTable.js"></script>
<script type="text/javascript" src="./JavaScript/viewParameter.js"></script>
<script type="text/javascript" src="./JavaScript/resultSQL.js"></script>

<script type="text/javascript">
       var flag=<%=flag%>;
       var LRTemplate_ID='<%=LRTemplate_ID%>';
       var State='<%=State%>';
       var LRTemplateName='<%=LRTemplateName%>';
    </script>
<script type="text/javascript" src="./RuleMake.js">
    </script>
<%@include file="./RuleMakeInit.jsp"%>

</head>
<body onload="initRule();initButtons()">
<div id="RuleDisplay"
    
	style="height: 300;width:3000; overflow-y: auto; overflow-x: visible">
<div id="conditions"
	style="width: 100; height: 10; color: #0754D4; font-size: 22; font-weight: bold; cursor: hand"
	onmouseout="normalLight()" onmouseover="hightLight()"
	onclick="showOrHideMenu()">如果:</div>
<br />
<br />
<div id="Result"
	style="position: ralative; left: -120; width: 100; height: 10; color: #0754D4; font-size: 22; font-weight: bold; cursor: hand"
	onmouseout="normalLight()" onmouseover="hightLight()" onclick="showResult()">那么:</div>
<br />
<br />

<div id="display"
	style="position: absolute; display: none; width: 200px; height: 200px; overflow-y: auto; overflow-x: auto; border: thin lightblue solid; background: #E7F2FB;"></div>

<!--  <input type="input" id="Result" name="Result" size='80' maxlength=40
	value="自核不通过。" />
	-->
	</div>
<br>
<br>
<br>
<div>
<div id="buttonForm">
<input type="button" class=cssButton name="displayDisicionTable" value="生成决策表" onclick="comfirmLogic()" /> &nbsp;&nbsp;&nbsp; <input type="button"
	class=cssButton name="submitData" value="提交数据" onclick="submitData()" />
&nbsp;&nbsp;&nbsp; <input type="hidden" class=cssButton name="logicToTable" value="逻辑入决策表"
	onclick="dataToTable()" /> &nbsp;&nbsp;&nbsp; <input type="button"
	class=cssButton name="modifyLogic" value="修改逻辑" onclick="modifyLogic()" /> 
</div>
<br>
<br>
<div id="grid-example"></div>
</div>
<form method="post" name="fm" action="./RuleMakeSave.jsp"><!-- 用于存储规则定制时基本信息的标识 -->
<input type="hidden" id="TableName" name="TableName"></input> 
<input type="hidden" id="BOMS" name="BOMS"></input> 
<input type="hidden" id="SQLPara" name="SQLPara"></input> 
<input type="hidden" id="ViewPara" name="ViewPara"></input> 
<input type="hidden" id="SQLStatement" name="SQLStatement"></input> 
<input type="hidden" id="DTData" name="DTData" /> 
<input type="hidden" id="CreateTable" name="CreateTable"></input> 
<input type="hidden" id="RuleCh" name="RuleCh"></input> 
<input
	type="hidden" id="TableColumnName" name="TableColumnName"></input>
<input
	type="hidden" id="ColumnDataType" name="ColumnDataType"></input>
<input type="hidden"
	id="RuleName" name="RuleName" value=<%=RuleName%>></input> <input type="hidden"
	id="RuleDes" name="RuleDes" value=<%=RuleDes%>></input> <input type="hidden"
	id="Creator" name="Creator" value=<%=Creator%>></input> <input type="hidden"
	id="RuleStartDate" name="RuleStartDate" value=<%=RuleStartDate%>></input> <input
	type="hidden" id="RuleEndDate"  name="RuleEndDate" value=<%=RuleEndDate%>></input> <input
	type="hidden" id="TempalteLevel" name="TempalteLevel" value=<%=TempalteLevel%>></input> <input
	type="hidden" id="Business" name="Business" value=<%=Business%>> <input
	type="hidden" id="State" name="State" value=<%=State%>></input> <input
	type="hidden" id="Valid" name="Valid" value=<%=Valid%>></input> <!-- 用于存储规则定制时决策表数据类型的标识 -->
<input type="hidden" id="Types" name="Types"></input> <!-- 用于存储规则定制时基本操作类型的标识 --> <input
	type="hidden" id="flag" name="flag" value=<%=flag%>></input> <!-- 用于存储规则定制时规则主键的标识 -->
<input type="hidden" id="LRTemplate_ID" name="LRTemplate_ID" value=<%=LRTemplate_ID%>></input>
<!-- 用于标识规则修改时的操作类型 --> <input type="hidden" id="Operation" name="Operation"></input>
<input type="hidden" id="MarketingNum" name="MarketingNum" value=<%=MarketingNum%>></input>

</form>
</body>
</html>

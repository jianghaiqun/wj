<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="./JavaScript/resultSQL.js"></script>
<script src="../Framework/Main.js"></script>
<%
	//获取规则定制时传入的基本信息
	String RuleTemplateName = request.getParameter("RuleTemplateName"); 
	String RuleTemplateDes =  request.getParameter("RuleTemplateDes");
	
	if(RuleTemplateName != null & !"".equals(RuleTemplateName)){
		 RuleTemplateName = java.net.URLDecoder.decode(RuleTemplateName,"utf-8"); 
	}
	if(RuleTemplateDes != null & !"".equals(RuleTemplateDes)){
		RuleTemplateDes = java.net.URLDecoder.decode(RuleTemplateDes,"utf-8"); 
	}
	
	String Creator = request.getParameter("Creator");
	String flag = request.getParameter("flag");
	String Operate = request.getParameter("Operate");
	String LRTemplateName = request.getParameter("LRTemplateName");
	String LRTemplate_ID = request.getParameter("LRTemplate_ID");
%>

<title>规则定制</title>
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
   var Operate="<%=Operate%>";
   var LRTemplateName = "<%=LRTemplateName%>";
   var LRTemplate_ID = "<%=LRTemplate_ID%>";
</script>
<script type="text/javascript" src="./RuleTemplateMake.js"></script>
<%@include file="./RuleTemplateMakeInit.jsp"%>

</head>
<body onload="initRule();initButtons()">
<div id="RuleDisplay"
    
	style="width:3000; overflow-y: auto; overflow-x: visible">
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
<input type="button" class=cssButton name="submitData" value="提交数据" onclick="submitData()" />
&nbsp;&nbsp;&nbsp; 

<!-- 以下按钮均无用，只是为了调用而不修改原来js防止报错。 -->
<input type="hidden" class=cssButton name="logicToTable" value="逻辑入决策表"
	onclick="dataToTable()" /> &nbsp;&nbsp;&nbsp; <input type="hidden"
	class=cssButton name="modifyLogic" value="修改逻辑" onclick="modifyLogic()" /> 
</div>
<br>	
<br>
<div id="grid-example"></div>
</div>
<form method="post" name="fm" action=""><!-- 用于存储规则定制时基本信息的标识 -->
<input type="hidden" id="RuleTemplateName"  name="RuleTemplateName" value=<%=RuleTemplateName%>></input>
<input type="hidden" id="RuleTemplateDes"  name="RuleTemplateDes" value=<%=RuleTemplateDes%>></input>
<input type="hidden" id="Creator" name="Creator" value=<%=Creator%>></input>
<input type="hidden" id="TableName" name="TableName"></input>
<input type="hidden" id="BOMS" name="BOMS"></input>
<input type="hidden" id="SQLPara" name="SQLPara"></input> 
<input type="hidden" id="ViewPara" name="ViewPara"></input> 
<input type="hidden" id="SQLStatement" name="SQLStatement"></input> 
<input type="hidden" id="DTData" name="DTData" /> 
<input type="hidden" id="CreateTable" name="CreateTable"></input> 
<input type="hidden" id="RuleCh" name="RuleCh" value=""></input> 
<input type="hidden" id="TableColumnName" name="TableColumnName"></input>
<input type="hidden" id="ColumnDataType" name="ColumnDataType"></input>
<input type="hidden" id="Types" name="Types"></input> <!-- 用于存储规则定制时基本操作类型的标识 -->
<input type="hidden" id="flag" name="flag" value=<%=flag%>></input>
<!-- 用于标识规则修改时的操作类型 -->
<input type="hidden" id="Operation" name="Operation"></input>
<input type="hidden" id="LRTemplate_ID" name="LRTemplate_ID" value=<%=LRTemplate_ID%>>
<input type="hidden" id="Operate" name="Operate" value=<%=Operate%>>

</input>

</form>
</body>
</html>

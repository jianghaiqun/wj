<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<%
	String LRTemplateName = request.getParameter("LRTemplateName");
	System.out.println("LRTemplateName::" + LRTemplateName);
	String LRTemplate_ID = request.getParameter("LRTemplate_ID");
	System.out.println("LRTemplate_ID::" + LRTemplate_ID);
%>
<title>规则测试</title>
<LINK href="../Include/Default.css" rel=stylesheet type=text/css>
<LINK href="../common/css/tab.css" rel=stylesheet type=text/css>
<link rel="stylesheet" type="text/css" href="./resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/rule.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/examples.css" />
<link rel="stylesheet" type="text/css" href="./resources/css/grid-examples.css" />
<script type="text/javascript" src="./baseLib/ext-base.js"></script>
<script type="text/javascript" src="./baseLib/ext-all.js"></script>
<script type="text/javascript" src="./baseLib/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="./baseLib/multiSelectCbox.js"></script>
<script type="text/javascript" src="./JavaScript/dicisionTable.js"></script>
<script type="text/javascript" src="./JavaScript/makeResult.js"></script>
<script type="text/javascript" src="./JavaScript/makeLogical.js"></script>
<script type="text/javascript" src="./JavaScript/viewParameter.js"></script>
<script type="text/javascript" src="./JavaScript/resultSQL.js"></script>
<script type="text/javascript" src="RuleTestDetails.js"></script>
<script language="JavaScript">
	var flag=0;
	var LRTemplateName = "<%=LRTemplateName%>";
	var LRTemplate_ID = "<%=LRTemplate_ID%>";
	/* document.onkeydown = function(event){
		event = getEvent(event);
		if(event.keyCode==13){ //摁下的是回车键
			testRule();
		}
	} */
</script>
</head>
<body onload="initForm();">
	<input type="hidden" id="strResult" />
	<form action="./RuleTemplateSave.jsp" method=post name=fm
		target="fraTitle">
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable" style="table-layout: fixed;">
			<tr>
				<td><fieldset>
						<legend>
							<label>规则描述</label>
						</legend>
						<table>
							<tr>
								<td width="5%"/>
								<td width="100%">
								<div><div id="RuleDisplay"
										style="width: 3000; overflow-y: auto; overflow-x: visible">
										<div id="conditions" align="left"
											style="width: 100; height: 10; color: #0754D4; font-size: 22; font-weight: bold; cursor: hand"
											onmouseout="normalLight()" onmouseover="hightLight()"
											onclick="showOrHideMenu()">如果:</div>
										<br clear = "left"/>
										<div id="Result"
											style="position: ralative; left: -120; width: 100; height: 10; color: #0754D4; font-size: 22; font-weight: bold; cursor: hand"
											onmouseout="normalLight()" onmouseover="hightLight()"
											onclick="showResult()">那么:</div>
										<br /> <br />
										<div id="display"
											style="position: absolute; display: none; width: 200px; height: 200px; overflow-y: auto; overflow-x: auto; border: thin lightblue solid; background: #E7F2FB;"></div>
									</div></div></td>
							</tr>
							<tr>
								<td width="5%"/>
								<td width="100%"><div id="grid-example"></div></td>
							</tr>
						</table>
					</fieldset></td>
			</tr>
			<tr>
				<td><br />
				</td>
			</tr>
			<tr>
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">

					<tr>
						<td><fieldset>
								<legend>
									<label>规则数据录入</label>
								</legend>
								<table width="100%" id="LogicalTable">

								</table>
							</fieldset></td>
					</tr>
					<tr>
						<td>
							<table border="0">
								<tr>
									<td><z:tbutton onClick="testRule();">
											<img src="../Icons/icon403a5.gif" />规则测试</z:tbutton>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</tr>
			<tr>
				<td><br />
				</td>
			</tr>
			<tr>
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">
					<tr>
						<td><fieldset>
								<legend>
									<label>规则测试结果</label>
								</legend>
								<table width="100%" id="ResultTable">
								</table>
								<label style="color: blue; display: none" id="labeldes"></label>
							</fieldset></td>
					</tr>
				</table>
			</tr>
		</table>
		<input type="hidden" id="arrResult" value=""> <input
			type="hidden" id="LRTemplateName" value="<%=LRTemplateName%>">
		<input type="hidden" id="LRTemplate_ID" value="<%=LRTemplate_ID%>">

	</form>
</body>
</html>
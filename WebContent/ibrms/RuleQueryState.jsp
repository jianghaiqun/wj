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
	String pageFlag = request.getParameter("PageFlag");
%>
<script language="JavaScript">
function initForm() {
	var pageFlag = '<%=pageFlag%>';

	document.getElementById('RuleTest').style.display = "none";
	document.getElementById('RuleQueryExamine').style.display = "none";
	document.getElementById('RuleIssue').style.display = "none";
	document.getElementById('RuleNullify').style.display = "none";
	document.getElementById('RuleMain').style.display = "none";
	document.getElementById('RuleUpdate').style.display = "none";
	if (pageFlag == "0") {
		fm.QueryState.value = "2";//待修改
		fm.LRTemplateName.value="lrtemplatet";
		document.getElementById('RuleMain').style.display = "";
	}
	if (pageFlag == "1") {
		fm.QueryState.value = "1";//待测试
		fm.LRTemplateName.value="lrtemplatet";
		document.getElementById('RuleTest').style.display = "";
	}
	if (pageFlag == "2") {
		fm.QueryState.value = "3";//待审批
		fm.LRTemplateName.value="lrtemplatet";
		document.getElementById('RuleQueryExamine').style.display = "";
	}
	if (pageFlag == "3") {
		fm.QueryState.value = "4";//待发布
		fm.LRTemplateName.value="lrtemplatet";
		document.getElementById('RuleIssue').style.display = "";
	}
	if (pageFlag == "4") {
		fm.QueryState.value = "7";//可使用状态即可作废状态
		fm.LRTemplateName.value="lrtemplatet";
		document.getElementById('RuleNullify').style.display = "";
	}
	if (pageFlag == "5") {
		fm.QueryState.value = "7";
		fm.LRTemplateName.value="lrtemplate";
		/* document.getElementById('RuleUpdate').style.display = ""; */
	}
}
</script>
<script src="../ibrms/RuleQueryState.js"></script>
<title>规则状态</title>
<LINK href="../Include/Default.css" rel=stylesheet type=text/css>
</head>
<body onload="initForm();">

	<form action="./RuleStateSave.jsp" method=post name=fm target="fraTitle">

		<Div id="divGroupPol1" style="display: ''">

			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable" style="table-layout: fixed;">
				<tr>
					<td><fieldset>
							<legend>
								<label>规则查询</label>
							</legend>
							<table width="100%">

								<TR>
									<TD width="17%">规则名</TD>
									<TD width="17%"><Input type="text" id=RuleName name=RuleName
										style="width: 170px"  verify="Length<21"></TD>
									<TD width="17%">功能描述</TD>
									<TD width="17%"><Input type="text" id=RuleDes name=RuleDes
										style="width: 170px" verify="Length<500"></TD>
									<TD width="17%">业务模块</TD>
									<TD width="17%"><z:select id="Business" name="Business"
											listWidth="170" input="true" style="width:170px"
											code="ibrmsbusiness" lazy="true" showValue="true"></z:select></TD>
								</TR>
								<TR>
									<TD width="17%">生效日期</TD>
									<td><input name="RuleStartDate" type="text" class="input1" id="RuleStartDate"
											ztype="Date" /></td>
									<TD width="17%">失效日期</TD>
									<td><input name="RuleEndDate" type="text" class="input1" id="RuleEndDate"
											ztype="Date" /></td>
								</TR>
							</table>

						</fieldset></td>
				</tr>
				<tr>
					<td>
						<table border="0">
							<tr>
								<td><z:tbutton onClick="queryDataRule();">
									<img src="../Icons/icon403a5.gif" />查询</z:tbutton>
								</td>
								<td><z:tbutton onClick="detailsView();">
									<img src="../Icons/icon403a5.gif" />查看明细</z:tbutton>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<table width="100%" border="0" cellspacing="0" cellpadding="6"
						class="blockTable" style="table-layout: fixed;">
						<tr>
							<td>规则列表</td>
						</tr>
						<tr>
							<td style="padding: 0px 5px;"><z:datagrid
									id="RuleQueryDataGrid" size="200"
									method="com.sinosoft.ibrms.RuleDataQueryUI.RuleTemplateTDataBind"
									autoFill="false" scroll="true" page="true" lazy="true" size="10"
									multiSelect="false">
									<table cellpadding="1" cellspacing="1" fixedHeight="250px"
										class="dataTable" afterdrag="afterRowDragEnd">
										<tr ztype="head" class="dataTableHead">
											<td width="3%" ztype="selector" field="id">&nbsp;</td>
											<td width="5%" ztype="RowNo" drag="true">序号</td>
											<td width="12%">规则名称</td>
											<td width="12%">业务模块</td>
											<td style="display: none;">规则级别</td>
											<td width="12%">生效日期</td>
											<td width="12%">失效日期</td>
											<td width="12%">创建者</td>
											<td style="display: none;">状态</td>
											<td width="32%">功能描述</td>
											<td style="display: none;">来源表名</td>
											<td style="display: none;">ID</td>

										</tr>
										<tr onClick="onselect('${ID}','${RuleName}','${Business}','${Templatelevel}','${StartDate}','${EndDate}','${Creator}','${State}','${Description}','${MarketingNum}')">
											<td width="3%" ztype="selector" field="id">&nbsp;</td>
											<td width="8%">&nbsp;</td>
											<td width="12%">${RuleName}</td>
											<td width="12%">${cBusiness}</td>
											<td style="display: none;">${Templatelevel}</td>
											<td width="12%">${StartDate}</td>
											<td width="12%">${EndDate}</td>
											<td width="12%">${Creator}</td>
											<td style="display: none;">${State}</td>
											<td width="32%">${Description}</td>
											<td style="display: none;">${TbName}</td>
											<td style="display: none;">${ID}</td>
											<td style="display: none;">${Business}</td>
											<td style="display: none;">${MarketingNum}</td>

										</tr>
										<tr ztype="PageBar">
											<td colspan="22">${PageBar}</td>
										</tr>
									</table>
								</z:datagrid>
							</td>
						</tr>
					</table>
				</tr>
				
				
			</table>
		</Div>
		<Div id="RuleTest">
			<z:tbutton onClick="TestRule();"><img src="../Icons/icon023a1.gif" />测试</z:tbutton>
			<z:tbutton onClick="TestSucc();"><img src="../Icons/icon404a4.gif" />测试通过</z:tbutton>
			<z:tbutton onClick="TestFail();"><img src="../Icons/icon404a3.gif" />测试不通过</z:tbutton>	
		</Div>
		<Div id="RuleQueryExamine">
			<z:tbutton onClick="ExamineSucc();"><img src="../Icons/icon404a4.gif" />审批通过</z:tbutton>
			<z:tbutton onClick="ExamineFail();"><img src="../Icons/icon404a3.gif" />审批不通过</z:tbutton>	
		</Div>
		<Div id="RuleIssue">
			<z:tbutton onClick="RuleIssue();"><img src="../Icons/icon023a1.gif" />发布</z:tbutton>
		</Div>
		<Div id="RuleNullify">
			<z:tbutton onClick="RuleNullify();"><img src="../Icons/icon404a3.gif" />作废</z:tbutton>	
		</Div>
		<Div id="RuleMain">
			<z:tbutton onClick="AddRule();"><img src="../Icons/icon403a5.gif" />增加</z:tbutton>
			<z:tbutton onClick="UpdateRule();"><img src="../Icons/icon403a10.gif" />修改</z:tbutton>
			<z:tbutton onClick="DeleteRule();"><img src="../Icons/icon403a11.gif" />删除</z:tbutton>	
		</Div>
		<Div id="RuleUpdate">
			<z:tbutton onClick="ReUpdateRule();"><img src="../Icons/icon403a10.gif" />返回修改</z:tbutton>	
		</Div>
		<input type="hidden" id="QueryState" name="QueryState"><!-- 查询的状态 -->
		<input type="hidden" id="LRTemplate_ID" name="LRTemplate_ID"><!-- 查询的id -->
		<input type="hidden" id="LRTemplateName" name="LRTemplateName"><!-- 查询表明 -->
		<input type="hidden" id="State" name="State">
		<input type="hidden" id="RuleNameT" name="RuleNameT">
		<input type="hidden" id="CreatorT" name="CreatorT">
		<input type="hidden" id="RuleStartDateT" name="RuleStartDateT">
		<input type="hidden" id="RuleEndDateT" name="RuleEndDateT">
		<input type="hidden" id="TempalateLevelT" name="TemplateLevelT">
		<input type="hidden" id="BusinessT" name="BusinessT">
		<input type="hidden" id="ValidT" name="ValidT">
		<input type="hidden" id="RuleDesT" name="RuleDesT">
		<input type="hidden" id="MarketingNum" name="MarketingNum" value="">
		<input type="hidden" id="UserName" name="UserName" value=<%=User.getUserName()%>>

	</form>
</body>
</html>
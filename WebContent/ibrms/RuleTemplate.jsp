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
	fm.RuleTemplateName.value="";
	fm.RuleTemplateDes.value="";
	fm.Creator.value="";
	fm.LRTemplate_ID.value="";
	fm.LRTemplateName.value="";
	DataGrid.clear('RuleTemplateQueryDataGrid');
}
</script>
<script src="../ibrms/RuleTemplate.js"></script>
<title>规则状态</title>
<LINK href="../Include/Default.css" rel=stylesheet type=text/css>
<LINK href="../common/css/tab.css" rel=stylesheet type=text/css>
</head>
<body onload="initForm();">

	<form action="./RuleTemplateSave.jsp" method=post name=fm target="fraTitle">

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
									<TD width="17%"><Input type="text" id=RuleTemplateNameT name=RuleTemplateNameT
										style="width: 170px"  verify="Length<21"></TD>
									<TD width="17%">功能描述</TD>
									<TD width="17%"><Input type="text" id=RuleTemplateDesT name=RuleTemplateDesT
										style="width: 170px" verify="Length<500"></TD>
								</TR>
							</table>

						</fieldset></td>
				</tr>
				<tr>
					<td>
						<table border="0">
							<tr>
								<td><z:tbutton onClick="queryDataRuleTemplate();">
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
									id="RuleTemplateQueryDataGrid" size="200"
									method="com.sinosoft.ibrms.RuleDataQueryUI.LRRuleTemplateDataBind"
									autoFill="false" scroll="true" lazy="true" size="10"
									multiSelect="false">
									<table cellpadding="1" cellspacing="1" fixedHeight="250px"
										class="dataTable" afterdrag="afterRowDragEnd">
										<tr ztype="head" class="dataTableHead">
											<td width="3%" ztype="selector" field="id">&nbsp;</td>
											<td width="5%" ztype="RowNo" drag="true">序号</td>
											<td width="12%">规则名称</td>
											<td width="12%">创建者</td>
											<td width="32%">功能描述</td>
											<td style="display: none;">来源表名</td>
											<td style="display: none;">ID</td>

										</tr>
										<tr onClick="onselect('${ID}','${RuleTemplateName}','${Description}','${Creator}')">
											<td width="3%" ztype="selector" field="id">&nbsp;</td>
											<td width="8%">&nbsp;</td>
											<td width="12%">${RuleTemplateName}</td>
											<td width="12%">${Creator}</td>
											<td width="32%">${Description}</td>
											<td style="display: none;">${TbName}</td>
											<td style="display: none;">${ID}</td>

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
		<Div>
			<z:tbutton onClick="AddRule();"><img src="../Icons/icon403a5.gif" />增加</z:tbutton>
			<z:tbutton onClick="UpdateRule();"><img src="../Icons/icon403a10.gif" />修改</z:tbutton>
			<z:tbutton onClick="DeleteRule();"><img src="../Icons/icon403a11.gif" />删除</z:tbutton>	
		</Div>
		<input type="hidden" id="Operate" name="Operate">
		<input type="hidden" id="RuleTemplateName" name="RuleTemplateName">
		<input type="hidden" id="RuleTemplateDes" name="RuleTemplateDes">
		<input type="hidden" id="Creator" name="Creator">
		<input type="hidden" id="LRTemplate_ID" name="LRTemplate_ID"> 
		<input type="hidden" id="LRTemplateName" name="LRTemplateName"> 
		
	</form> 
</body>
</html>
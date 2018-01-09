<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function setValue(){
	var dr = DataGrid.getSelectedData("FactorGrid").Rows[0];
	Form.setValue1(DataGrid.getSelectedData("FactorGrid").Rows[0],"fm");
}

function add() {
	var dt = DataGrid.getSelectedData("FactorGrid");
	if(dt.Rows.length == 1){
		Dialog.alert("选中PK保障列表数据只能修改!");
		return;
	}
	if(Verify.hasError()){
		return;
	}
	var dc_info = Form.getData("fm");
	dc_info.add("operate","Insert");
	Server.sendRequest("com.sinosoft.cms.document.ShoppingGuidePKManage.savePKDuty",dc_info,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){location = location;});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
	
}


function edit() {
	var dt = DataGrid.getSelectedData("FactorGrid");
	if(dt.Rows.length == 0){
		Dialog.alert("请选择要修改的PK保障列表数据!");
		return;
	}
	if(Verify.hasError()){
		return;
	}
	var dc_info = Form.getData("fm");
	dc_info.add("operate","Update");
	Server.sendRequest("com.sinosoft.cms.document.ShoppingGuidePKManage.savePKDuty",dc_info,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){location = location;});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}

function del() {
	var dt = DataGrid.getSelectedData("FactorGrid");
	if(dt.Rows.length == 0){
		Dialog.alert("请选择要修改的PK保障列表数据!");
		return;
	}
	if(Verify.hasError()){
		return;
	}
	var dc_info = Form.getData("fm");
	dc_info.add("operate","Delete");
	Server.sendRequest("com.sinosoft.cms.document.ShoppingGuidePKManage.savePKDuty",dc_info,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){location = location;});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}


</script>
</head>
<body>
<form name="fm" id="fm">
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<z:init method="com.sinosoft.cms.document.ShoppingGuidePKManage.initPKDutyDialog">
			<tr class=common>
				<td width="40%" height="30">产品1：<input id="ProductName1" value="${ProductName1}" type="text" readonly style="width:220px;"/>
				</td>
				<td width="40%" height="30">产品2：<input id="ProductName2" value="${ProductName2}" type="text" readonly style="width:220px;" />
				</td>
			</tr>
			<tr>
				<td style="padding: 0px 5px;" colspan="2">
				<input type="hidden" id="articleid" name="articleid" value="${articleid}">
				<input type="hidden" id="textvalue" name="textvalue" value="${textvalue}">
				<fieldset><legend> <label>PK保障列表</label> </legend>
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">
					<tr>
						<td style="padding: 0px 5px;"><z:datagrid id="FactorGrid" 
							method="com.sinosoft.cms.document.ShoppingGuidePKManage.dg2DataBind"
							 scroll="true" multiSelect="false" size="50">
							<table width="100%" cellpadding="2" cellspacing="0"
								class="dataTable" 
								fixedHeight="100px" afterdrag="afterRowDragEnd">
								<tr ztype="head" class="dataTableHead">
									<td width="10%" ztype="RowNo" drag="true"><img
										src="../Framework/Images/icon_drag.gif" width="16" height="16">
									</td>
									<td width="10%" ztype="selector" field="id">&nbsp;</td>
									<td width="30%"><b>PK保障名称</b></td>
									<td width="30%"><b>显示顺序</b></td>
								</tr>
								<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" onclick="setValue();">
									<td width="50px">&nbsp;</td>
									<td width="50px">&nbsp;</td>
									<td width="150px">${DutyName}</td>
									<td width="150px">${OrderFlag}</td>
								</tr>
							</table>
						</z:datagrid></td>
					</tr>
				</table>
				</fieldset>
				</td>
			</tr>
			
			<tr><td width="" height="20" colspan="2"></td>
			</tr>
			<tr>
				<td width="" height="30" colspan="2">PK保障名称：<input id="DutyName" verify="PK保障名称|NotNull"><input type="hidden" id="ID">
				显示顺序：<input id="OrderFlag" verify="PK保障名称|NotNull&&Int"></td>
			</tr>
			<tr>
				<td width="65%" style="padding:6px 10px;" class="blockTd" colspan="2">
				<div style="float: left">
					<z:tbutton onClick="add();"><img src="../Icons/icon024a2.gif" width="20" height="20" />新增</z:tbutton>
					<z:tbutton onClick="edit();"><img src="../Icons/icon024a4.gif" width="20" height="20" />修改</z:tbutton>
					<z:tbutton onClick="del();"><img src="../Icons/icon403a11.gif" width="20" height="20" />删除</z:tbutton>
					</div>
				</td>
			</tr>

			<tr>
				<td width="" height="30" colspan="2">产品1保障额度：<input id="DutyAmnt1" size="10">&nbsp;&nbsp;产品1进度百分比：<input id="Percent1" size="10">
			</tr>
			
			<tr id="planNo1">
				<td height="30" colspan="2">计划2的额度：<input id="prop1" size="7"   >&nbsp;&nbsp;计划3的额度 <input id="prop2" size="7"   ></td>
			</tr>
				
			<tr>
				<td width="" height="30" colspan="2">产品1保障说明：<input id="DutyDesc1" size="95"></td>
			</tr>
			<tr>
				<td width="" height="30" colspan="2">产品2保障额度：<input id="DutyAmnt2" size="10">
				&nbsp;&nbsp;产品2进度百分比：<input id="Percent2" size="10">
				<%-- &nbsp;&nbsp;产品2重点提示：<z:select id="KeyTips2"
					value="N" style="width:50px" listWidth="55"
				    showValue="true">${KeyTips}</z:select> --%></td>
			</tr>
			<tr id="planNo2">
				<td height="30" colspan="2">计划2的额度：<input id="prop3" size="7"   >&nbsp;&nbsp;计划3的额度 <input id="prop4" size="7"   ></td>
			</tr>
			<tr>
				<td width="" height="30" colspan="2">产品2保障说明：<input id="DutyDesc2" size="95"></td>
			</tr>
		</z:init>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
<script type="text/javascript">
 var textvalue = document.getElementById("textvalue").value;
 if(textvalue !="plan"){
	 document.getElementById("planNo1").style.display = 'none';  
	 document.getElementById("planNo2").style.display = 'none';  
 }
</script>
</html>
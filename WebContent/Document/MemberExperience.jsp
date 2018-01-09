<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员经验管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/DateTime.js"></script>

<!--会员经验管理-->
<script><!--
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 260;
	diag.Title = "新增会员经验";
	diag.URL = "Document/MemberExperienceaddDialog.jsp";
	diag.onLoad = function(){
		$DW.$("ID").focus();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新建会员经验信息";
	diag.show();
}

function addSave(){
	if($DW.Verify.hasError()){
	  return;
     }
	var dc = $DW.Form.getData("form2");
	dc.add("username",$DW.$NV("username"));
	Server.sendRequest("com.sinosoft.message.MemberExperience.add",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
		if(response.Status==1){
			$D.close();
			DataGrid.loadData('dg1');
		}
		});
	});
}
function doSearch(){
	var startdate = $V("StartDate");
	var enddate = $V("EndDate");
	if(startdate>enddate){
		Dialog.alert("起始日期不得早于截止日期！");
		return;		
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","username",$V("username"));
	DataGrid.setParam("dg1","Manner",$V("Manner"));
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要编辑的行！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("只能修改1条信息！");
		return;
	}

	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "会员经验修改";
	
	diag.URL = "Document/MemberExperienceDialog.jsp?ID="+dr.get("ID");
	diag.onLoad = function(){
		$DW.$("ID").focus();
	};	
	diag.OKEvent = editSave;
	diag.show();
	
	
}
function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.message.MemberExperience.save",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
		});
	});
}
function datelimit(){
	var startdate = $V("StartDate");
	var enddate = $V("EndDate");
	if(startdate>enddate){
		Dialog.alert("起始日期不得早于截止日期！");
		return;		
	}
}

</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />会员列表</td>
				</tr>
				
				<tr>
					<td style="padding:8px 10px;">
                    <div style="float: right">
					 
				 	从&nbsp;
					<input value="" type="text" id="StartDate" size="14" name="StartDate" ztype="Date"  class="inputText" verify="NotNull">
					至&nbsp;
					<input value="" type="text" id="EndDate" size="14" name="EndDate" ztype="Date"  class="inputText" verify="NotNull" onChange="datelimit();">                  
                  	用户名：<input name="username" type="text" id="username" value="" style="width:90px"> 
                  	方式:
                   	<z:select style="width:90px;" >
						<select name="Manner" id="Manner" "> 
						<option value=""></option> 
		                  <option value="0">收入 </option>
		                  <option value="1">支出 </option>
		                </select>
                
					</z:select>
					
                    <input type="button" name="Submit" value="查询" onClick="doSearch()"></div>
                    <z:tbutton onClick="add()"><img src="../Icons/icon021a2.gif" />新建</z:tbutton> 
                    <z:tbutton onClick="edit()"><img src="../Icons/icon021a4.gif" />修改</z:tbutton> 
                       
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.message.MemberExperience.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
						<td width="4%" ztype="RowNo">&nbsp;</td>
						<td width="4%" ztype="selector" field="ID">&nbsp;</td>
						
						<td><b>用户名</b></td>
						<td><b>邮箱</b></td>
						<td><b>电话</b></td>
						<td><b>经验</b></td>
						<td><b>经验来源</b></td>
						<td><b>经验收入/支出</b></td>
						<td><b>时间</b></td>
						
					</tr>
					<tr ondblclick="edit();">
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>${username}</td>
						<td>${email}</td>
						<td>${mobileNO}</td>
						<td>${Experience}</td>
						<td>${Source}</td>
						<td>${CodeName}</td>
						<td>${CreateDate} ${CreateTime}</td>
						
					</tr>
					<tr ztype="pagebar">
					  <td colspan="10" align="center">${PageBar}</td>
					</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>

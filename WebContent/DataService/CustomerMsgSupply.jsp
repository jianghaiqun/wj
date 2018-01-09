<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 360;
	diag.Title = "客户信息完善";
	diag.URL = "DataService/CustomerMsgSupplyDialog.jsp";
	diag.onLoad = function(){
		$DW.init();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新建";
	diag.show();
}
function addSave(){
	if($DW.Verify.hasError()){
		  return;
	}
	var dc = $DW.Form.getData("form2");
	//证件有效期校验
	var applicantStartID = $DW.document.getElementById("applicantStartID").value;
	var applicantEndID = $DW.document.getElementById("applicantEndID").value;
	if(!applicantStartID ||! applicantEndID){
		Dialog.alert("投保人证件开始日期、结束日期不可以为空！");
		return;
	}
	var relationCode = $DW.document.getElementById("insuredRelation").value;
	if(relationCode && "00"==relationCode){//本人只校验投保人
		
	}else{
		var recognizeeStartID = $DW.document.getElementById("recognizeeStartID").value;
		var recognizeeEndID = $DW.document.getElementById("recognizeeEndID").value;
		if(!recognizeeStartID ||! recognizeeEndID){
			Dialog.alert("被保人证件开始日期、结束日期不可以为空！");
			return;
		}
		
	}
	
	Dialog.wait("正在保存...");
	Server.sendRequest("com.sinosoft.cms.dataservice.CustomerMsgSupply.save",dc,function(){
		var response = Server.getResponse();
		Dialog.endWait();
		$DW.document.getElementById("id").value = response.id;
		Dialog.alert(response.Message,function(){
			
		if(response.Status==1){
			$D.close();
			DataGrid.loadData('dg1');
		}
		});
	});
}

function edit(id,flag){
	if(flag!=0){
		flag=1;
	}
    var arr = DataGrid.getSelectedValue("dg1");
	if(id&&id!=""){
		arr = new Array();
		arr[0] = id;
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要完善的客户信息！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	var msgValid  = drs[0].get("msgValid");
	if("完整"==msgValid){
		Dialog.alert("信息已完善，不可以修改 ！");
		return;
	}
	
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 360;
	diag.Title = "客户信息完善修改";
	diag.URL = "DataService/CustomerMsgSupplyDialog.jsp?id="+arr[0];
	diag.onLoad = function(){
		$DW.init();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改客户完善信息";
	diag.show(); 
}
function view(id){
    var arr = DataGrid.getSelectedValue("dg1");
	if(id&&id!=""){
		arr = new Array();
		arr[0] = id;
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要查看的完善信息！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 360;
	diag.Title = "客户信息完善查看";
	diag.URL = "DataService/CustomerMsgSupplyDialog.jsp?flag=0&id="+arr[0];
	diag.onLoad = function(){
		$DW.init();
	};  
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看完善信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","applicantName",$V("applicantName"));
	DataGrid.setParam("dg1","applicantIdentityId",$V("applicantIdentityId"));
	DataGrid.setParam("dg1","recognizeeMobile",$V("recognizeeMobile"));
	DataGrid.loadData("dg1",function (){});
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
					<tr>
						<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />客户信息完善</td>
					</tr>
					<tr>
						<td>
							<table>
								<tr>
										<td>投保人姓名：</td>
										<td><input name="applicantName" type="text" id="applicantName" value="" style="width: 90px" class="inputText"></td>
										<td>投保人证件号码：</td>
										<td><input name="applicantIdentityId" type="text" id="applicantIdentityId" value="" 	style="width: 90px" class="inputText"></td>
										<td>被保人手机号：</td>
										<td><input name="recognizeeMobile" type="text" id="recognizeeMobile" value="" style="width: 90px" class="inputText"></td>
								</tr>
								<tr>
			               			<td><input type="button" name="Submit" value="查询" onClick="doSearch()"></td>
			            		</tr>
							</table>
						</td>
						</tr>
						<tr>
		                    <td>
					                    <z:tbutton onClick="add()"><img src="../Icons/icon021a2.gif" />新建</z:tbutton> 
					                    <z:tbutton onClick="edit()"><img src="../Icons/icon021a4.gif" />修改</z:tbutton> 
		                    </td>
						</tr>
						<tr>
							<td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
							<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.CustomerMsgSupply.dg1DataBind" size="20" lazy="false">
								<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
									<tr ztype="head" class="dataTableHead">
										<td width="5%" ztype="RowNo"><strong>序号</strong></td>
										<td width="5%" ztype="selector" field="id">${id}</td>
										<td width="10%"><b>投保人姓名</b></td>
										<td width="15%"><b>投保人证件号码</b></td>
										<td width="10%"><b>被保人姓名</b></td>
										<td width="10%"><b>被保人证件号</b></td>
										<td width="10%"><b>被保人手机号</b></td>
										<td width="5%"><b>渠道</b></td>
										<td width="5%"><b>完善状态</b></td>
									</tr>
									<tr onDblClick="view('${id}')" style1="background-color:#FFFFFF"
										style2="background-color:#F9FBFC">
										<td width="4%">&nbsp;</td>
										<td>&nbsp;</td>
										<td title="${applicantName}">${applicantName}</td>
										<td title="${applicantIdentityId}">${applicantIdentityId}</td>
										<td title="${recognizeeName}">${recognizeeName}</td>
										<td title="${recognizeeIdentityId}">${recognizeeIdentityId}</td>
										<td title="${recognizeeMobile}">${recognizeeMobile}</td>
										<td title="${tbCustomerChannel}">${tbCustomerChannel}</td>
										<td title="${msgValid}">${msgValid}</td>
									</tr>
									<tr ztype="pagebar">
										<td height="25" colspan="11">${PageBar}</td>
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
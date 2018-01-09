<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function addSave(){
	if($DW.Verify.hasError()){
	  return;
     }
	var Password=$DW.$V("Password");
	if(!/^((\d+[a-zA-Z_]\w*)|([a-zA-Z_]+\d\w*))$/.test(Password)){
		Dialog.alert("密码只能由6-16个英文、数字、及下划线任两个组合组成！");
		return;
	}
	var dc = $DW.Form.getData("form2");
	// dc.add("Gender",$DW.$NV("Gender"));
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.add",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
		if(response.Status==1){
			$D.close();
			DataGrid.loadData('dg1');
		}
		});
	});
}

function view(id){
    var arr = DataGrid.getSelectedValue("dg1");
	if(id&&id!=""){
		arr = new Array();
		arr[0] = id;
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要查看的会员！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 1200;
	diag.Height = 280;
	diag.Title = "会员信息查看";
	diag.URL = "DataService/MemberEditDialog.jsp?flag=0&id="+arr[0];
	diag.onLoad = function(){
		$DW.init();
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看会员信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	//DataGrid.setParam("dg1","Status",$V("Status"));
	DataGrid.setParam("dg1","realName",$V("realName"));
	DataGrid.setParam("dg1","id",$V("id"));
	DataGrid.setParam("dg1","SEmail",$V("SEmail"));
	DataGrid.setParam("dg1","IDNO",$V("IDNO"));
	DataGrid.setParam("dg1","mobileNo",$V("mobileNo"));
	DataGrid.setParam("dg1","telephoneNO",$V("telephoneNO"));
	// 订单查询
	DataGrid.setParam("dg1","applicantName",$V("applicantName"));
	DataGrid.setParam("dg1","policyNumber",$V("policyNumber"));
	DataGrid.setParam("dg1","orderSn",$V("orderSn"));
	// DataGrid.setParam("dg1","carLicence",$V("carLicence"));
	// DataGrid.setParam("dg1","carType",$V("carType"));
	DataGrid.setParam("dg1","startCreateDate",$V("startCreateDate"));
	DataGrid.setParam("dg1","endCreateDate",$V("endCreateDate"));
	if(($V("startCreateDate") == null || $V("startCreateDate") == "") 
			&& ($V("startCreateDate") != null && $V("startCreateDate") != "") ){
		Dialog.alert("请填写创建开始日期！");
		return;
	}
	if(($V("endCreateDate") == null || $V("endCreateDate") == "") 
			&& ($V("endCreateDate") != null && $V("endCreateDate") != "") ){
		Dialog.alert("请填写创建终止日期！");
		return;
	}
	//DataGrid.setParam("dg1","startCreateTime",$V("startCreateTime"));
	//DataGrid.setParam("dg1","endCreateTime",$V("endCreateTime"));
	DataGrid.setParam("dg1","Search","search");
	DataGrid.loadData("dg1");
}

function doCheck(status){
	if(status==""){
		return;
	}
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要审核的会员！");
		return;
	}
	var dc = new DataCollection();	
	dc.add("UserNames",arr.join(","));
	//dc.add("Status",status);
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.doCheck",dc,function(){
		var response = Server.getResponse();
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			DataGrid.loadData('dg1');
		}
	});
}

function checkAddress(username){
	var diag = new Dialog("Diag1");
	diag.Width = 750;
	diag.Height = 320;
	diag.Title = "会员地址列表";
	diag.URL = "DataService/MemberAddr.jsp?UserName="+username;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}
function doCollect(id){
    var arr = DataGrid.getSelectedValue("dg1");
	if(id&&id!=""){
		arr = new Array();
		arr[0] = id;
		alert(id);
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择查询的产品所属会员！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 360;
	diag.Title = "查看修改产品信息";
	diag.URL = "DataService/Mycollect.jsp?id="+arr[0];
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看修改产品信息";
	//diag.Message = "修改会员级别、姓名、分数、折扣等";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}
function orderDetail(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择订单所属会员！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 1100;
	diag.Height = 450;
	diag.Title = "查看会员订单信息";
	diag.URL = "DataService/MemberOrderInquiry.jsp?memberid="+arr[0];
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看会员订单信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}
function sendResetPWD(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择一条记录！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var data = DataGrid.getSelectedData("dg1");
	var dc = new DataCollection();	
	var email = data.get(0,"email");
	dc.add("email",email);
	//dc.add("Status",status);
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.sendPwdResetRUL",dc,function(){
		var response = Server.getResponse();
		if(response.Status==1){
			Dialog.alert(response.Message);
			DataGrid.loadData('dg1');
		}else{
			Dialog.alert(response.Message);
		}
	});
	
	// fm1.mobileNOOrEmail.value = email;
	// fm1.action = "";
	// fm1.submit();
	// location.href = sinosoft.base+"/shop/member!sendPasswordRecoverMail.action?mobileNoOrEmail="+email;
}
</script>

</head>
<body>
<form action="../shop/member!sendPasswordRecoverMail.action" name="fm1" method="post" id="passwordRecoverForm">
	<input type="hidden" name="mobileNOOrEmail" id="mobileNOOrEmail" value="">
</form>
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
				<!--  状态：
	                    <z:select id="Status" name="Status" style="width:80px" listWidth="80" onChange="doSearch();">
						<select><option value=""></option>
							<option value="X">等待审核</option>
							<option value="Y">审核通过</option>
							<option value="N">审核不通过</option></select>
						</z:select>   
						-->
				<td>
				<table>
					<tr>
						<td>真实姓名：</td>
						<td><input name="realName" type="text" id="realName" value=""
							style="width: 90px" class="inputText"></td>
						<td>会员ID：</td>
						<td><input name="id" type="text" id="id" value=""
							style="width: 90px" class="inputText"></td>
						<td>E-mail：</td>
						<td><input name="SEmail" type="text" id="SEmail" value=""
							style="width: 90px" class="inputText"></td>
						<td>证件号码：</td>
						<td><input name="IDNO" type="text" id="IDNO" value=""
							style="width: 90px" class="inputText"></td>
						<td>手机号码：</td>
						<td><input name="mobileNo" type="text" id="mobileNo" value=""
							style="width: 90px" class="inputText"></td>
						<td>电话号码：</td>
						<td><input name="telephoneNO" type="text" id="telephoneNO"
							value="" style="width: 90px" class="inputText"></td>
					</tr>
					<tr>
						<td>投保人姓名：</td>
						<td><input name="applicantName" type="text" id="applicantName" value=""
							style="width: 90px" class="inputText"></td>
						<td>保单号：</td>
						<td><input name="policyNumber" type="text" id="policyNumber" value=""
							style="width: 90px" class="inputText"></td>
						<td>订单号：</td>
						<td><input name="orderSn" type="text" id="orderSn" value=""
							style="width: 90px" class="inputText"></td>
						<td>车牌：</td>
						<td><input name="carLicence" type="text" id="carLicence" value=""
							style="width: 90px" class="inputText"></td>
						<td>车型：</td>
						<td><input name="carType" type="text" id="carType" value=""
							style="width: 90px" class="inputText"></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td>创建开始日期</td>
						<td><input name="startCreateDate" type="text" id="startCreateDate" value=""
							style="width: 90px" class="inputText" ztype="date">							
						</td>
						<td>创建终止日期</td>
						<td><input name="endCreateDate" type="text" id="endCreateDate" value=""
							style="width: 90px" class="inputText" ztype="date"></td>
					</tr>
					<tr>
               			<td><input type="button" name="Submit" value="查询" onClick="doSearch()"></td>
            		</tr>
				</table>
				</td>
				</tr>
				<tr>
                    <!--  <td>
                    <z:tbutton onClick="doCollect()"><img src="../Icons/icon021a4.gif" width="20" height="20" />产品收藏</z:tbutton>
                    <z:tbutton onClick="orderDetail()"><img src="../Icons/icon021a2.gif" width="20" height="20" />订单信息</z:tbutton>
                    <z:tbutton onClick="sendResetPWD()"><img src="../Icons/icon021a2.gif" width="20" height="20" />发送密码重置</z:tbutton>
                    </td>
                   <td type="hidden">
                    <z:tbutton onClick="doCheck('Y')"><img src="../Icons/icon404a4.gif" width="20" height="20" />审核通过</z:tbutton>
                    <z:tbutton onClick="doCheck('N')"><img src="../Icons/icon404a3.gif" width="20" height="20" />不通过</z:tbutton>
					</td> -->
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.Member.dg1DataBind2" size="10"  multiSelect="false" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo" drag="true"><strong>序号</strong></td>
								<td width="3%" ztype="selector" field="id">&nbsp;</td>
								<td width="10%"><b>E-mail</b></td>
								<td width="8%"><b>手机号码</b></td>
								<td width="6%"><b>用户名</b></td>
								<td width="6%"><b>真实姓名</b></td>
								<td width="6%"><b>证件类型</b></td>
								<td width="10%"><b>证件号码</b></td>
								<td width="10%"><b>创建日期</b></td>
								<td width="6%"><b>会员类型</b></td>
								<td width="10%"><b>最近登录时间</b></td>
							</tr>
							<tr  style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td>&nbsp;</td>
								<td>${email}</td>
								<td>${mobileNo}</td>
								<td>${username}</td>
								<td>${realName}</td>
								<td>${IDTypeName}</td>
								<td>${IDNO}</td>
								<td>${createDate}</td>
								<td>${VIPTypename}</td>
								<td>${loginDate}</td>
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
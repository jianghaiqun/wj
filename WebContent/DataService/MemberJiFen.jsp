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
function edit(id){
    var arr = DataGrid.getSelectedValue("dg1");
	if(id&&id!=""){
		arr = new Array();
		arr[0] = id;
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的会员！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 1200;
	diag.Height = 280;
	diag.Title = "会员可用积分修改";
	diag.URL = "DataService/MemberQueryDialog.jsp?flag=2&id="+arr[0];
	diag.onLoad = function(){
		$DW.init();
	};
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改会员可用积分";
	//diag.Message = "修改会员级别、姓名、分数、折扣等";
	diag.show();
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
	diag.URL = "DataService/MemberEditDialog.jsp?id="+arr[0];
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看会员信息";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}
function editSave(){
	var dc = $DW.Form.getData("form2");
    //dc.add("sex",$DW.$NV("Gender"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.dg1Edit",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message);
		if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
		}
	});
}

function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","realName",$V("realName"));
	DataGrid.setParam("dg1","SEmail",$V("SEmail"));
	DataGrid.setParam("dg1","IDNO",$V("IDNO"));
	DataGrid.setParam("dg1","mobileNo",$V("mobileNo"));
	// 订单查询
	DataGrid.setParam("dg1","startCreateDate",$V("startCreateDate"));
	DataGrid.setParam("dg1","endCreateDate",$V("endCreateDate"));
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
		Dialog.alert("请先选择查询的订单所属会员！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 900;
	diag.Height = 380;
	diag.Title = "查看会员订单信息";
	diag.URL = "DataService/MemberOrderInquiry.jsp?id="+arr[0];
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
}

function query(id){
	var arr = DataGrid.getSelectedValue("dg1");
	if(id&&id!=""){
		arr = new Array();
		arr[0] = id;
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择查询的积分所属会员！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 900;
	diag.Height = 380;
	diag.Title = "会员积分明细";
	diag.URL = "DataService/MemberJiFenInquiry.jsp?id="+arr[0];
	diag.ShowMessageRow = true;
	diag.MessageTitle = "会员积分明细";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
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
				<td>
				<table>
					<tr>
						<td>真实姓名：</td>
						<td><input name="realName" type="text" id="realName" value=""
							style="width: 90px" class="inputText"></td>
						<td>E-mail：</td>
						<td><input name="SEmail" type="text" id="SEmail" value=""
							style="width: 90px" class="inputText"></td>
						<td>手机号码：</td>
						<td><input name="mobileNo" type="text" id="mobileNo" value=""
							style="width: 90px" class="inputText"></td>	
						<td>证件号码：</td>
						<td><input name="IDNO" type="text" id="IDNO" value=""
							style="width: 90px" class="inputText"></td>
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
                    <td>
                     <z:tbutton onClick="query()"><img src="../Icons/icon021a4.gif" />查看</z:tbutton>
                     <z:tbutton onClick="edit()"><img src="../Icons/icon021a4.gif" />可用积分修改</z:tbutton> 
                    </td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.Member.dg1DataBind2" 
					lazy="true" size="10">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo"><strong>序号</strong></td>
								<td width="3%" ztype="selector" field="id">&nbsp;</td>
								<td width="9%"><b>E-mail</b></td>
								<td width="7%"><b>手机号码</b></td>
								<td width="5%"><b>用户名</b></td>
								<td width="5%"><b>真实姓名</b></td>
								<td width="5%"><b>证件类型</b></td>
								<td width="10%"><b>证件号码</b></td>
								<td width="10%"><b>创建日期</b></td>
								<td width="6%"><b>会员类型</b></td>
								<td width="6%"><b>会员等级</b></td>
								<td width="5%"><b>积分</b></td>
								<td width="10%"><b>最近登录时间</b></td>
							</tr>
							<tr onDblClick="edit('${id}')" style1="background-color:#FFFFFF"
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
								<td>${memberRank_idName}</td>
								<td>${currentValidatePoint}</td>
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
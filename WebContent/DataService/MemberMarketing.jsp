<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员营销管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function doSearch(){
	DataGrid.setParam("dg1","BeginDate",$V("BeginDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.setParam("dg1","Email",$V("Email"));
	DataGrid.setParam("dg1","MobileNo",$V("MobileNo"));
	DataGrid.setParam("dg1","QueryStatus",$V("QueryStatus"));
	DataGrid.setParam("dg1","fromWap",$V("fromWap"));
	DataGrid.loadData("dg1",getAllMember);
}

//setTimeout("getAllMember()", 500);
function getAllMember() {
	var dt = getDataTable();
	if(dt.Rows.length > 0){

		var zCount = dt.Rows[0].get("MemberActive");
		var z1Count = dt.Rows[0].get("MemberActive1");
		var bCount = dt.Rows[0].get("MemberPassive");
		var qCount = dt.Rows[0].get("MemberNumber");

		
		
		
		document.getElementById("qMember").innerHTML = "&nbsp;&nbsp;全部会员数量：" + qCount;
		document.getElementById("zMember").innerHTML = "&nbsp;&nbsp;主动注册会员数量：" + (parseFloat(zCount)+parseFloat(z1Count));
		document.getElementById("bMember").innerHTML = "&nbsp;&nbsp;被动注册会员数量：" + bCount;
	}else{
		document.getElementById("qMember").innerHTML = "&nbsp;&nbsp;全部会员数量：0";
		document.getElementById("zMember").innerHTML = "&nbsp;&nbsp;主动注册会员数量：0";
		document.getElementById("bMember").innerHTML = "&nbsp;&nbsp;被动注册会员数量：0";
	}
}
function getDataTable() {
	var dg = $("dg1");
	var dt = dg.DataSource;
	var names = ["MemberNumber", "MemberActive", "MemberPassive"];
	var map = {};
	for ( var i = 0; i < names.length; i++) {
		var eles = $N(names[i]);
		var arr = [];
		if (eles != null) {
			for ( var j = 0; j < eles.length; j++) {
				arr.push($V(eles[j]));
			}
		}
		map[names[i]] = arr;
	}
	for ( var i = 0; i < dt.getRowCount(); i++) {
		for ( var j = 0; j < names.length; j++) {
			dt.Rows[i].set(names[j], map[names[j]][i]);
		}
	}
	return dt;
}


function orderDetail(MemberID){
	var arr = DataGrid.getSelectedValue("dg1");
	var BeginDate = $V("BeginDate");
	var EndDate = $V("EndDate");
	var diag = new Dialog("Diag1");
	diag.Width = 1000;
	diag.Height = 600;
	diag.Title = "营销管理-明细";
	diag.URL = "DataService/MemberMarketingInquiry.jsp?MemberID="+MemberID+"&BeginDate="+BeginDate+"&EndDate="+EndDate;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "明细";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

</script>

</head>
<body>

	<z:init method="com.sinosoft.cms.dataservice.MemberMarketing.initMember">
		<input type="hidden" id="result_qMember" value="${result_qMember}">
		<input type="hidden" id="result_zMember" value="${result_zMember}">
		<input type="hidden" id="result_z1Member" value="${result_z1Member}">
		<input type="hidden" id="result_bMember" value="${result_bMember}">
	</z:init>

	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />会员营销</td>
				</tr>
				<tr>
				<td>
				<table>
					<tr>
						<td>&nbsp;&nbsp;时间区间：</td>
						<td><input name="BeginDate" type="text" id="BeginDate"
							style="width: 120px" class="inputText" ztype="date"></td>
							
						<td>&nbsp;&nbsp;到 ：</td>
						<td><input name="EndDate" type="text" id="EndDate"
							style="width: 120px" class="inputText" ztype="date"></td>
						
						<td>&nbsp;&nbsp;会员邮箱：</td>
						<td><input name="Email" type="text" id="Email" value=""
							style="width: 120px" class="inputText"></td>
						<td>&nbsp;&nbsp;手机号码：</td>
						<td><input name="MobileNo" type="text" id="MobileNo" value=""
							style="width: 120px" class="inputText"></td>
							
						<td>&nbsp;&nbsp;查询类型：</td>
						<td><select style="width:100px;" name="QueryStatus" id="QueryStatus">
							<option value="1">全部</option>
							<option value="2">增量</option>
						</select>
						</td>
						<td>&nbsp;&nbsp;会员来源：</td>
						<td><z:select name="fromWap" id="fromWap">
										<span value="" selected></span>
										<span value="1">淘宝</span>
										<span value="2">主站</span>
									</z:select>
						</td>
               			<td>&nbsp;&nbsp;<input type="button" name="Submit" value="查询" onClick="doSearch()"></td>
				</table>
				</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<div style="width:1500px;  overflow: auto;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.MemberMarketing.dg1DataBind" size="20" lazy="true">
						<table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo"><strong>序号</strong></td>
								<td width="3%" ztype="selector" field="id">&nbsp;</td>
								<td width="5%"><b>回购次数</b></td>
								<td width="5%"><b>回购保费</b></td>
								<td width="5%"><b>回购实付</b></td>
								<td width="5%"><b>回购单数</b></td>
								<td width="5%"><b>撤单金额</b></td>
								<td width="5%"><b>撤单数量</b></td>
								<td width="4%"><b>姓名</b></td>
								<td width="4%"><b>性别</b></td>
								<td width="7%"><b>生日</b></td>
								<td width="8%"><b>证件号码</b></td>
								<td width="8%"><b>地址</b></td>
								<td width="6%"><b>手机号码</b></td>
								<td width="8%"><b>E-mail</b></td>
								<td width="5%"><b>会员特征</b></td>
								<td width="9%"><b>注册时间</b></td>
								<td width="6%"><b>会员来源</b></td>
								<td width="0"></td>
								<td width="0"></td>
								<td width="0"></td>
							</tr>
							<tr onDblClick="orderDetail('${MemberID}')" style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="4%">&nbsp;</td>
								<td>&nbsp;</td>
								<td title="${zCount}">${zCount}</td>
								<td title="${sTotalAmount}">${sTotalAmount}</td>
								<td title="${sPayAmunt}">${sPayAmunt}</td>
								<td title="${cOrders}">${cOrders}</td>
								<td title="${cPrice}">${cPrice}</td>
								<td title="${cNumber}">${cNumber}</td>
								<td title="${mUserName}">${mUserName}</td>
								<td title="${mSexName}">${mSexName}</td>
								<td title="${mBirthday}">${mBirthday}</td>
								<td title="${mIDNO}">${mIDNO}</td>
								<td title="${mAddress}">${mAddress}</td>
								<td title="${mMobileNO}">${mMobileNO}</td>
								<td title="${mEmail}">${mEmail}</td>
								<td title="${mType}">${mType}</td>
								<td title="${mCreateDate}">${mCreateDate}</td>
								<td title="${fromName}">${fromName}</td>
								<td style="display: none"><input name="MemberNumber" type="text" id="MemberNumber" 
									value="${MemberNumber}" /></td>
								<td style="display: none"><input name="MemberActive" type="text" id="MemberActive" 
									value="${MemberActive}" /></td>
								<td style="display: none"><input name="MemberPassive" type="text" id="MemberPassive" 
									value="${MemberPassive}" /></td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></div>
					</td>
				</tr>
				<tr>
					<td>
					 <span id="qMember"></span>
					 <span id="zMember"></span>
					 <span id="bMember"></span>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
<script type="text/javascript">
getMemberCount();
function getMemberCount() {
	var zCount = document.getElementById("result_zMember").value;
	var z1Count = document.getElementById("result_z1Member").value;
	var bCount = document.getElementById("result_bMember").value;
	var qCount = document.getElementById("result_qMember").value;

	document.getElementById("qMember").innerHTML = "&nbsp;&nbsp;全部会员数量："+qCount;
	document.getElementById("zMember").innerHTML = "&nbsp;&nbsp;主动注册会员数量："+(parseFloat(zCount)+parseFloat(z1Count));
	document.getElementById("bMember").innerHTML = "&nbsp;&nbsp;被动注册会员数量："+bCount;
}

</script>
</html>
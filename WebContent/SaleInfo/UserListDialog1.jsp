<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script>

function viewUser(id){
    var arr = DataGrid.getSelectedValue("dg2");
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
	var diag = new Dialog("Diag2");
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
function exportStaff(){
	DataGrid.toExcel("dg2",1);
}
</script>

</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.dataservice.Member.initDialog">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
						<tr>
                    <td>
                    <z:tbutton onClick="exportStaff()"><img src="../Icons/icon021a3.gif" />导出EXCEL</z:tbutton>
                    </td>
				</tr>
						<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg2" method="com.wangjin.infoseeker.TotalInfo.dg6DataBind_UerList" size="10">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="2%" ztype="selector" field="id">&nbsp;</td>
								<td width="15%" style="text-align:center;"><b>E-mail</b></td>
								<td width="8%" style="text-align:center;"><b>手机号码</b></td>
								<td width="5%" style="text-align:center;"><b>真实姓名</b></td>
								<td width="20%" style="text-align:center;"><b>会员ID</b></td>
								<td width="5%" style="text-align:center;"><b>购买次数</b></td>
								<td width="12%" style="text-align:center;"><b>累计保费</b></td>
								<td width="12%" style="text-align:center;"><b>创建日期</b></td>
								<td width="12%" style="text-align:center;"><b>最近登录时间</b></td>
							</tr>
							<tr onDblClick="viewUser('${id}')" style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td style="text-align:center;"></td>
								<td>&nbsp;</td>
								<td>${email}</td>
								<td>${mobileNo}</td>
								<td>${realName}</td>
								<td>${id}</td>
								<td>${orderCount}</td>
								<td>${totalAmount}</td>
								<td style="text-align:center;">${createDate}</td>
								<td style="text-align:center;">${loginDate}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
	</table>
	 <input name="cPrice" type="hidden" value="${cPrice}" id="cPrice" />
	 <input name="startDate" type="hidden" value="${startDate}" id="startDate" />
	 <input name="endDate" type="hidden" value="${endDate}" id="endDate" />
	 <input name="cancelFlag" type="hidden" value="${cancelFlag}" id="cancelFlag" />
	</form>
</z:init>
</body>
</html>

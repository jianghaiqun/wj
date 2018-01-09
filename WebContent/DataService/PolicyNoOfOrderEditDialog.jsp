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
<script type="text/javascript">
Page.onLoad(function(){

	$("dg1").afterEdit = function(tr,dr){
		dr.set("policyNo",$V("policyNo"));
		//dr.set("svaliDate",$V("timePrem"));
		//dr.set("evaliDate",$V("evaliDate"));
		//dr.set("CancelDate",$V("CancelDate"));
		//dr.set("svaliDate",$V("svaliDate"));
		//dr.set("evaliDate",$V("evaliDate"));
		dr.set("id",$V("id"));
		return true;
	}
});
function editOrderItem(){
	DataGrid.save("dg1","com.wangjin.cms.orders.QueryOrders.editOrderItemUpdate",function(){DataGrid.loadData('dg1');});
}
function checkPWD(){
	var password=$V("NewPassword");
	var confirmPassword=$V("ConfirmPassword");
	if(password==confirmPassword){
		return true;
	} else{
		return false;
	}
}

function editInformationAISave(){
	var dc = $DW.Form.getData("form2");
	dc.add("id",$DW.$NV("id"));
	//var orderSn1=$DW.$NV("orderSn");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.wangjin.cms.orders.QueryMember.dg1Edit4",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message);
		if(response.Status==1){
			//$D.close();
			DataGrid.loadData('dg1');
		}
	});
}

</script>
</head>
<body class="dialogBody">
	<form id="form2">
									<input type="hidden" id="order_Sn" name="order_Sn" value="${orderSn}" style="width:150px"/>
									<input type="hidden" id="recognizee_Sn" name="recognizee_Sn" value="${recognizeeSn}" style="width:150px"/>
								    <input type="hidden" id="information_Sn" name="information_Sn" value="${informationSn}" style="width:150px"/>
								    <input type="hidden" id="order_id" name="order_id" value="${id}" style="width:150px"/>
				                <input name="id" type="hidden" value="${id}" style="width:150px" class="inputText" id="id" />
	             <table width="100%" cellpadding="2" cellspacing="0"  class="blockTable" >
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 订单信息条目</td>
		          </tr>
				<tr>
					<td style="padding:8px 10px;">
					</td>
				</tr>
				<tr>
				   <td colspan="4">
		            	   <z:tbutton onClick="editOrderItem()"  id="editOrderItemid" >
		            	     <img src="../Icons/icon021a4.gif" width="20" height="20"/>修改保单信息条目
		            	   </z:tbutton>
		           	</td>
	            </tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.cms.orders.QueryOrders.orderInquery2" size="10">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="3%" ztype="RowNo"><strong>序号</strong></td>
								<td width="3%" ztype="selector" field="id">&nbsp;</td>
								<td width="13%"><b>创建日期</b></td>
								<td width="15%"><b>保单号</b></td>
								<td width="18%"><b>产品名称</b></td>
								<td width="6%"><b>产品单价</b></td>
								<td width="5%"><b>投保人</b></td>
								<td width="5%"><b>被保人</b></td>
								<td width="13%"><b>保单起保日期</b></td>
								<td width="13%"><b>保单终止日期</b></td>
								<td width="15%"><b>撤单日期</b></td>
							</tr>
							<tr style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td>&nbsp;</td>
								<td>${createDate}</td>
                                <td>${policyNo}</td>
								<td>${riskName}</td>
								<td style="text-align:right;">${recognizeePrem}</td>
								<td>${applicantName}</td>
								<td>${recognizeeName}</td>
								<td>${svaliDate}</td>
								<td>${evaliDate}</td>
								<td>${CancelDate}</td>
							</tr>
							<tr ztype="edit" bgcolor="#E1F3FF">
								<td bgcolor="#E1F3FF">&nbsp;</td>
								<td>&nbsp;</td>
								<td>${createDate}</td>
								<td><input name="policyNo" type="text" id="policyNo"
									value="${policyNo}" size="28"></td>
								<td>${riskName}</td>
								<td>${recognizeePrem}</td>
								<td>${applicantName}</td>
								<td>${recognizeeName}</td>
								<td>${svaliDate}</td>
								<td>${evaliDate}</td>
								<td>${CancelDate}</td>
								<td type="hidden">${baodanId}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
	</form>
</body>
</html>
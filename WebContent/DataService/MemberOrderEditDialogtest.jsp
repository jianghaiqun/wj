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
		//dr.set("policyNo",$V("policyNo"));
		//dr.set("svaliDate",$V("timePrem"));
		//dr.set("evaliDate",$V("evaliDate"));
		//dr.set("CancelDate",$V("CancelDate"));
		dr.set("svaliDate",$V("svaliDate"));
		dr.set("evaliDate",$V("evaliDate"));
		dr.set("id",$V("id"));
		return true;
	}
});
function editOrderItem(){
	if($V("orderStatusHidden")=="7")
	{
		alert("已支付状态不允许修改！如需修改保单号，请进入保单修改菜单页面！");
		return;
	}
	
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
function editInformationAI(id){
	var orderSn = document.getElementById("order_Sn").value;
	var recognizeeSn = document.getElementById("recognizee_Sn").value;
	var informationSn = document.getElementById("information_Sn").value;
    var arr = DataGrid.getSelectedValue("dg1");
    if(id&&id!=""){
		arr = new Array();
		arr[0] = id;
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的条目！");
		return;
	}
	if(!arr||arr.length>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag2");
	diag.Width = 960;
	diag.Height = 360;
	
	//mod by wangej - 20130815 - 已支付的订单除了保单号其他信息不允许修改  获取当前选中的被保人id - begin
	var dt = DataGrid.getSelectedData("dg1");
	if(!dt||dt.getRowCount()==0){
		Dialog.alert("请先选择要编辑的条目！");
		return;
	}
	if(dt.getRowCount() > 1)
		{
			Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
			return;
		}

	var dr = dt.getDataRow(0);
	var recognizeeSnSelect = dr.get("recognizeeSn"); 
	
	if($V("orderStatusHidden")=="7")
	{
		 
		diag.Title = "投被保人信息查看";
		diag.URL = "DataService/MemberOrderEditDialogtestdetail.jsp?orderSn="+orderSn+"&&recognizeeSn="+recognizeeSnSelect+"&&informationSn="+informationSn;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "查看投被保人信息 ";
		//diag.Message = "修改订单条目信息 ";
		diag.show();
		diag.OKButton.hide();
		diag.CancelButton.value="关闭";
	}else{
		diag.Title = "投被保人信息修改";
		diag.URL = "DataService/MemberOrderEditDialogtestdetail.jsp?orderSn="+orderSn+"&&recognizeeSn="+recognizeeSnSelect+"&&informationSn="+informationSn;
	    diag.OKEvent = editInformationAISave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "修改投被保人信息 ";
		//diag.Message = "修改订单条目信息 ";
		diag.show();
	}
	//mod by wangej - 20130815 - 已支付的订单除了保单号其他信息不允许修改  获取当前选中的被保人id - end
	
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
//修改订单信息
function editOrders(){
	var dc = Form.getData("form2");
	if(Verify.hasError()){
		return;
	}
	if($V("orderStatusHidden")=="7")
		{
			alert("已支付状态不允许修改！");
			
			return;
		}
	Server.sendRequest("com.wangjin.cms.orders.QueryOrders.editOrders",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message);
		if(response.Status==1){
				//$D.close();
				//DataGrid.loadData('dg1');
		}
	});
}


</script>
</head>
<body class="dialogBody">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0"  class="blockTable" >
	            <tr> <td>
	            <z:init method="com.wangjin.cms.orders.QueryOrders.initDialog2">
	                  <fieldset>
		                    <table width="100%" cellpadding="2" cellspacing="0" >
								<tr> <td valign="middle" class="blockTd"><img src="../Icons/icon021a1.gif" /> 订单基本信息：</td></tr>
								<tr>
									<input type="hidden" id="order_Sn" name="order_Sn" value="${orderSn}" style="width:150px"/>
									<input type="hidden" id="recognizee_Sn" name="recognizee_Sn" value="${recognizeeSn}" style="width:150px"/>
								    <input type="hidden" id="information_Sn" name="information_Sn" value="${informationSn}" style="width:150px"/>
								    <input type="hidden" id="orderStatusHidden" name="orderStatusHidden" value="${orderStatusHidden}" style="width:150px"/>
								    
								    <input type="hidden" id="order_id" name="order_id" value="${id}" style="width:150px"/>
									<td height="25" align="right" class="tdgrey1">创建日期：</td>
									<td height="25"><b></b><input type="text" id="createDate" name="createDate" value="${createDate}" style="width:150px" readonly="readonly"/></td>
									<td height="25" align="right" class="tdgrey1">订单编号：</td>
									<td height="25">
						            <input name="orderSn" type="text" value="${orderSn}" style="width:150px"  id="orderSn"  readonly="readonly"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">订单状态：</td>
									<td height="25">
						               <z:select id="orderStatus" name="orderStatus" style="width:150px" listWidth="150"> ${orderStatus}</z:select></td>
								</tr>
								<tr>
									<td height="25" align="right" class="tdgrey1">订单原价：</td>
									<td height="25">
						            <input name="productTotalPrice" type="text" value="${productTotalPrice}" style="width:150px" class="inputText" id="productTotalPrice" />
						            </td>
						            <td height="25" align="right" class="tdgrey1">已付金额：</td>
									<td height="25">
						            <input name="payAmount" type="text" value="${payPrice}" style="width:150px" class="inputText" id="payAmount"  />
						            </td>
						            <td height="25" align="right" class="tdgrey1">订单总额：</td>
									<td height="25">
									<input name="totalAmount" type="text" value="${totalAmount}" style="width:150px" class="inputText" id="totalAmount" / >
									</td>
								</tr>
			                      <tr>
					               <td><z:tbutton onClick="editOrders()"  id="editOrdersid" > 
						               <img src="../Icons/icon021a4.gif" width="20" height="20"/>修改基本订单信息
						            	   </z:tbutton>
						           </td>
					             </tr>
				              </table>
				                <input name="id" type="hidden" value="${id}" style="width:150px" class="inputText" id="id" />
				       </fieldset>
				       
				      </z:init>
				       </td>
	             </tr>
	             </table>
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
		            	     <img src="../Icons/icon021a4.gif" width="20" height="20"/>修改订单信息条目
		            	   </z:tbutton>
		            	   <z:tbutton onClick="editInformationAI()"  id="editInformationAppnt" >
		            	     <img src="../Icons/icon021a4.gif" width="20" height="20"/>修改投/被保人信息
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
							<tr ztype="edit" bgcolor="#E1F3FF" >
								<td bgcolor="#E1F3FF">&nbsp;</td>
								<td>&nbsp;</td>
								<td>${createDate}</td>
								<td>${policyNo}></td>
								<td>${riskName}</td>
								<td>${recognizeePrem}</td>
								<td>${applicantName}</td>
								<td>${recognizeeName}</td>
								<td><input name="svaliDate" type="text" id="svaliDate" 
									value="${svaliDate}" size="20" ztype="date"></td>
								<td><input name="evaliDate" type="text" id="evaliDate" 
									value="${evaliDate}" size="20" ztype="date"></td>
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
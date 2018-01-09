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
		dr.set("effective",$V("effective"));
		dr.set("fail",$V("fail"));
		dr.set("productPrice",$V("productPrice"));
		return true;
	}

	//window.location.href="order!linkOrderDetails.action?orderSn=2013000000000051"
});
function editOrderItem(){
	DataGrid.save("dg1","com.sinosoft.cms.dataservice.Member.editOrderItemUpdate",function(){DataGrid.loadData('dg1');});
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
	diag.Title = "会员信息修改";
	diag.URL = "DataService/MemberOrderEditDialogtestdetail.jsp?id="+arr[0];
     diag.OKEvent = editInformationAISave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改订单条目信息 ";
	//diag.Message = "修改订单条目信息 ";
	 diag.show();
}
function editInformationAISave(){
	var dc = $DW.Form.getData("form2");
	dc.add("id",$DW.$NV("id"));
	//var orderSn1=$DW.$NV("orderSn");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.dg1Edit4",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message);
		if(response.Status==1){
			$D.close();
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
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.editOrders",dc,function(){
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
	            <z:init method="com.sinosoft.cms.dataservice.Member.initDialog2">
	                  <fieldset>
		                    <table width="100%" cellpadding="2" cellspacing="0" >
		                    
		                    			                     
			                  	<tr> <td valign="middle" class="blockTd" style="width:150px"><img src="../Icons/icon021a1.gif" />投保人基本信息</td></tr>
								<tr>
									<input type="hidden" id="order_id" name="order_id" value="${id}" style="width:230px" />
									<td height="25" align="right" class="tdgrey1">姓名：</td>
									<td height="25"><b></b><input type="text" id="createDate" name="createDate" value="风天扬" style="width:230px" readonly="readonly"/></td>
									<td height="25" align="right" class="tdgrey1">性别：</td>
									<td height="25">
						            <input name="orderSn" type="text" value="男" style="width:230px"  id="orderSn"  readonly="readonly"/>
								</tr>
								
								<tr>
									<td height="25" align="right" class="tdgrey1">证件类型：</td>
									<td height="25">
						            <input name="paidAmount" type="text" value="身份证" style="width:230px" class="inputText" id="paidAmount" />
						            </td>
						            <td height="25" align="right" class="tdgrey1">证件号码：</td>
									<td height="25">
						            <input name="paymentFee" type="text" value="142401198708301819" style="width:230px" class="inputText" id="paymentFee"  />
								</tr>
								
				                 <tr>
				                 <td height="25" align="right" class="tdgrey1">手机号码：</td>
									<td height="25">
						            <input name="totalAmount" type="text" value="15901088888" style="width:230px" class="inputText" id="totalAmount" />
						            </td>
						            <td height="25" align="right" class="tdgrey1">电子邮箱：</td>
									<td height="25">
						            <input name="totalAmount" type="text" value="fengtianyang@sinosoft.com.cn" style="width:230px" class="inputText" id="totalAmount" />
						            </td>
			                     </tr>
			                     
			                     
								<tr><td valign="middle" class="blockTd" style="width:150px"><img src="../Icons/icon021a1.gif" />保单信息 </td></tr>
								<tr>
									<input type="hidden" id="order_id" name="order_id" value="${id}" style="width:230px" />
									<td height="25" align="right" class="tdgrey1">订单号：</td>
									<td height="25"><b></b><input type="text" id="createDate" name="createDate" value="201300000000005" style="width:230px" readonly="readonly"/></td>
									<td height="25" align="right" class="tdgrey1">产品名称：</td>
									<td height="25">
						            <input name="orderSn" type="text" value="平安财险畅行天下（全球）基础计划" style="width:230px"  id="orderSn"  readonly="readonly"/>
								<tr>
									<td height="25" align="right" class="tdgrey1">投保日期：</td>
									<td height="25">
						            <input name="paidAmount" type="text" value="2012-11-19" style="width:230px" class="inputText" id="paidAmount" />
						            </td>
						            <td height="25" align="right" class="tdgrey1">保单起止日期：</td>
									<td height="25">
						            <input name="paymentFee" type="text" value="2012-11-20 00时 - 2012-11-26 24时" style="width:230px" class="inputText" id="paymentFee"  />
						            </td>
								</tr>
				                 <tr>
				                 <td height="25" align="right" class="tdgrey1">保费合计：</td>
									<td height="25">
						            <input name="totalAmount" type="text" value="￥155" style="width:230px" class="inputText" id="totalAmount" />
						            </td>
						           <td height="25" align="right" class="tdgrey1">受益人：</td>
									<td height="25">
						            <input name="totalAmount" type="text" value="法定继承人" style="width:230px" class="inputText" id="totalAmount" />
						            </td>
			                     </tr>
				              </table>
				       </fieldset>
				      </z:init>
				       </td>
	             </tr>
	             </table>
	

	             
	             
	             <table width="100%" cellpadding="2" cellspacing="0"  class="blockTable" >
			     <tr>
		              <td valign="middle" class="blockTd" style="width:150px"><img src="../Icons/icon021a1.gif" width="20" height="20" />被保人信息</td>
		          </tr>
				<tr>
					<td style="padding:8px 10px;">
					</td>
				</tr>
				<tr>
	            </tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.Member.orderInquery2" size="10">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="4%" ztype="RowNo"><strong>序号</strong></td>
								<td width="8%"><b>姓名</b></td>
								<td width="5%"><b>性别</b></td>
								<td width="8%"><b>与投保人关系</b></td>
								<td width="10%"><b>证件类型</b></td>
								<td width="15%"><b>证件号码</b></td>
								<td width="10%"><b>出生日期</b></td>
								<td width="10%"><b>手机号码</b></td>
								<td width="20%"><b>电子邮箱</b></td>
							</tr>
							<tr style="background-color:#F9FBFC">
								<td width="10%">&nbsp;</td>
								<td>风天扬 </td>
								<td>男</td>
								<td>本人</td>
								<td>身份证</td>
								<td>142401198708301111</td>
								<td>1987-08-30</td>
								<td>15901088888</td>
								<td>fengtianyang@sinosoft.com.cn</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
			
		    <input name="id" type="hidden" value="${id}" style="width:230px" class="inputText" id="id" />
	</form>
</body>
</html>
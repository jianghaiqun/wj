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

//显示投保人和被保人的信息
function showInformationAI(id,recognizeeSn,informationSn){
	 var arr = DataGrid.getSelectedData("dg1");
	 if(!arr || arr.getRowCount() == 0){
			Dialog.alert("请先选择要查看的条目！");
			return;
		}
		if(!arr||arr.getRowCount()>=2){
			Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
			return;
		}
		if(id == null){
			id=arr.get(0,"orderSn");
		}
		if(recognizeeSn == null){
			recognizeeSn=arr.get(0,"recognizeeSn");
		}
		if(informationSn == null){
			informationSn=arr.get(0,"informationSn");
		}

	var diag = new Dialog("Diag3");
	diag.Width = 960;
	diag.Height = 360;
	diag.Title = "查看投被保人信息";
	diag.URL = "DataService/MemberOrderShowInsureAppDetail.jsp?orderSn="+id+"&&recognizeeSn="+recognizeeSn+"&&informationSn="+informationSn;
    diag.OKEvent = editInformationAISave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "查看投被保人信息 ";
	//diag.Message = "修改订单条目信息 ";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
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
	            <z:init method="com.wangjin.cms.orders.QueryOrders.initDialog2">
	                  <fieldset>
		                    <table width="100%" cellpadding="2" cellspacing="0" >
								<tr> <td valign="middle" class="blockTd"><img src="../Icons/icon021a1.gif" /> 订单基本信息</td></tr>
								<tr>
								    <input type="hidden" id="order_Sn" name="order_Sn" value="${orderSn}" style="width:150px" />
								    <input type="hidden" id="recognizee_Sn" name="recognizee_Sn" value="${recognizeeSn}" style="width:150px" />
								    <input type="hidden" id="information_Sn" name="information_Sn" value="${informationSn}" style="width:150px" />
									<td height="25" align="right" class="tdgrey1">订单号码：</td>
									<td height="25">
						            <input name="orderSn" type="text" value="${orderSn}" style="width:150px"  id="orderSn"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">产品：</td>
									<td height="25">
						            <input name="productName" type="text" value="${productName}" style="width:160px"  id="productName"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">承保公司：</td>
									<td height="25">
						            <input name="insurCompany" type="text" value="${insurCompany}" style="width:150px"  id="insurCompany"  disabled="true"/>
						            </td>
						         </tr>
						         <tr>
						            <td height="25" align="right" class="tdgrey1">保险起期：</td>
									<td height="25">
						            <input name="svalidate" type="text" value="${svalidate}" style="width:150px"  id="svalidate"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">保险止期：</td>
									<td height="25">
						            <input name="evaliDate" type="text" value="${evaliDate}" style="width:160px"  id="evaliDate"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1" >创建时间：</td>
									<td height="25"><b></b><input type="text" id="createDate" name="createDate" value="${createDate}" style="width:150px" disabled="true"/></td>
						         </tr>  
						         <tr>
							         <td height="25" align="right" class="tdgrey1">订单原价：</td>
									<td height="25">
									<input name="productTotalPrice" type="text" value="${productTotalPrice}" style="width:150px" class="inputText" id="productTotalPrice"  disabled="true" / >
									</td>
							         <td height="25" align="right" class="tdgrey1">订单总价：</td>
										<td height="25">
						                <input name="totalAmount" type="text" value="${totalAmount}" style="width:160px" class="inputText" id="totalAmount"  disabled="true"/>
						                </td>
							         <td height="25" align="right" class="tdgrey1">订单实付：</td>
										<td height="25">
						                <input name="paidAmount" type="text" value="${payPrice}" style="width:150px" class="inputText" id="paidAmount"  disabled="true"/>
						                </td>
						         </tr>	
						         <tr>
							         <td height="25" align="right" class="tdgrey1">保单份数：</td>
										<td height="25">
							            <input name="policySum" type="text" value="${policyNumber}" style="width:150px"  id="policySum"  disabled="true"/>
							         </td>
							         <td height="25" align="right" class="tdgrey1">递送费用：</td>
										<td height="25">
							            <input name="recCost" type="text" value="" style="width:160px"  id="recCost"  disabled="true"/>
							         </td>
							         <td height="25" align="right" class="tdgrey1">会员 ID：</td>
										<td height="25">
							            <input name="mid" type="text" value="${memberid}" style="width:150px"  id="memberid"  disabled="true"/>
							         </td>
						         </tr>
						         <tr>
						            <td height="25" align="right" class="tdgrey1">订单状态：</td>
									<td height="25">
						               <z:select id="orderStatus" name="orderStatus" style="width:150px" listWidth="150"  disabled="true"> ${orderStatus}</z:select></td>
						               
						                <td height="25" align="right" class="tdgrey1">商户订单号：</td>
										<td height="25">
							            <input name="paySn" type="text" value="${paySn}" style="width:160px"  id="paySn"  disabled="true"/>
							         </td>
								</tr>
								<tr> <td valign="middle" class="blockTd"><img src="../Icons/icon021a1.gif" /> 购买优惠信息</td></tr>
								<tr>
									<td height="25" align="right" class="tdgrey1">优惠券优惠说明：</td>
									<td height="25">
						            <input name="direction" type="text" value="${direction}" style="width:150px"  id="direction"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">优惠券优惠金额：</td>
									<td height="25">
						            <input name="parValue" type="text" value="${parValue}" style="width:160px"  id="parValue"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">优惠券号：</td>
									<td height="25">
						            <input name="couponSn" type="text" value="${couponSn}" style="width:150px"  id="couponSn"  disabled="true"/>
						            </td>
						         </tr>
						         <tr>
									<td height="25" align="right" class="tdgrey1">${HDtype}说明：</td>
									<td height="25">
						            <input name="HDdirection" type="text" value="${HDdescription}" style="width:150px"  id="HDdirection"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">活动优惠金额：</td>
									<td height="25">
						            <input name="orderActivity" type="text" value="${orderActivity}" style="width:160px"  id="orderActivity"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">活动号：</td>
									<td height="25">
						            <input name="ActivitySn" type="text" value="${ActivitySn}" style="width:150px"  id="ActivitySn"  disabled="true"/>
						            </td>
						         </tr>
				              </table>
				       </fieldset>
				      </z:init>
				       </td>
	             </tr>
	             </table>
	             
	             
	             
	              <table width="100%" cellpadding="2" cellspacing="0"  class="blockTable" >
	            <tr> <td>
	            <z:init method="com.wangjin.cms.orders.QueryOrders.orderInqueryApp2">
	                  	<fieldset>
		                    <table width="100%" cellpadding="2" cellspacing="0" >
								<tr> <td valign="middle" class="blockTd"><img src="../Icons/icon021a1.gif" /> 投保人信息</td></tr>
								<tr>
									<td height="25" align="right" class="tdgrey1">姓名：</td>
									<td height="25">
						            <input name="applicantName" type="text" value="${applicantName}" style="width:150px"  id="applicantName"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">证件类型：</td>
									<td height="25">
						            <input name="applicantIdentityTypeName" type="text" value="${appIdentityTypeName}" style="width:150px"  id="applicantIdentityTypeName"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">证件号码：</td>
									<td height="25">
						            <input name="applicantIdentityId" type="text" value="${applicantIdentityId}" style="width:150px"  id="applicantIdentityId"  disabled="true"/>
						            </td>
						         </tr>
						         <tr>
									<td height="25" align="right" class="tdgrey1">性别：</td>
									<td height="25">
						            <input name="applicantSexName" type="text" value="${appSexName}" style="width:150px"  id="applicantSexName"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">出生日期：</td>
									<td height="25">
						            <input name="applicantBirthday" type="text" value="${applicantBirthday}" style="width:150px"  id="applicantBirthday"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">手机号码：</td>
									<td height="25">
						            <input name="applicantMobile" type="text" value="${applicantMobile}" style="width:150px"  id="applicantMobile"  disabled="true"/>
						            </td>
						         </tr>
						         <tr>
						            <td height="25" align="right" class="tdgrey1">电子邮箱：</td>
									<td height="25">
						            <input name="applicantMail" type="text" value="${applicantMail}" style="width:150px"  id="applicantMail"  disabled="true"/>
						            </td>
						            <td height="25" align="right" class="tdgrey1">投被关系：</td>
									<td height="25">
						            <input name="recognizeeAppntRelationName" type="text" value="${recognizeeAppntRelationName}" style="width:150px"  id="recognizeeAppntRelationName"  disabled="true"/>
						            </td>
						         </tr>
				              </table>
				       </fieldset>
				      </z:init>
				       </td>
	             </tr>
	             </table>
	             	             <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 保单基本信息</td>
		          </tr>
				<tr>
					<td style="padding:8px 10px;">
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.cms.orders.QueryOrders.orderInquery2" size="10">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="480px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id">&nbsp;</td>
								<td width="140" style="text-align:center;"><b>保单号</b></td>
								<td width="70" style="text-align:center;"><b>被保人姓名</b></td>
								<td width="30" style="text-align:center;"><b>性别</b></td>
								<td width="60" style="text-align:center;"><b>证件类型</b></td>
								<td width="100" style="text-align:center;"><b>证件号码</b></td>
								<td width="60" style="text-align:center;"><b>出生日期</b></td>
								<td width="70" style="text-align:center;"><b>手机号码</b></td>
								<td width="90" style="text-align:center;"><b>电子邮箱</b></td>
								<td width="30" style="text-align:center;"><b>关系</b></td>
								<td width="40" style="text-align:center;"><b>受益人</b></td>
								<td width="40" style="text-align:center;"><b>单价</b></td>
								<td width="40" style="text-align:center;"><b>实付</b></td>
								<td width="90" style="text-align:center;"><b>撤单时间</b></td>
								<td width="90" style="text-align:center;"><b>保单状态</b></td>
								<td width="90" style="text-align:center;"><b>承保信息</b></td>
								<td width="60" style="text-align:center;"><b>备注信息</b></td>
							</tr>
							<tr style="background-color:#F9FBFC"  onDblClick="showInformationAI('${orderSn}','${recognizeeSn}','${informationSn}')">
								<td width="3%">&nbsp;</td>
								<td>&nbsp;</td>
								<td style="text-align:right;" title="${policyNo}">${policyNo}</td>
								<td style="text-align:right;" title="${recognizeeName}">${recognizeeName}</td>
								<td style="text-align:right;" title="${recognizeeSexName}">${recognizeeSexName}</td>
								<td style="text-align:right;" title="${recognizeeIdentityTypeName}">${recognizeeIdentityTypeName}</td>
								<td style="text-align:right;" title="${recognizeeIdentityId}">${recognizeeIdentityId}</td>
								<td style="text-align:right;" title="${recognizeeBirthday}">${recognizeeBirthday}</td>
								<td style="text-align:right;" title="${recognizeeMobile}">${recognizeeMobile}</td>
								<td style="text-align:right;" title="${recognizeeMail}">${recognizeeMail}</td>
								<td style="text-align:right;" title="${recognizeeAppntRelationName}">${recognizeeAppntRelationName}</td>
								<td style="text-align:right;" title="法定继承人">法定继承人</td>
								<td style="text-align:right;" title="${recognizeePrem}">${recognizeePrem}</td>
								<td style="text-align:right;" title="${payPrice}">${payPrice}</td>
								<td style="text-align:right;" title="${CancelDate}">${CancelDate}</td>
								<td style="text-align:right;" title="${status}">${status}</td>
								<td style="text-align:right;" title="${insureMsg}">${insureMsg}</td>
								<td style="text-align:right;" title="${remark}">${remark}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</table>
			
		    <input name="id" type="hidden" value="${id}" style="width:150px" class="inputText" id="id" />
	</form>
</body>
</html>
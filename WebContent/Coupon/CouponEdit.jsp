<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">

jQuery(document).ready(function(){
		var startDate = jQuery('#startDate').val();
		if (startDate != "") {
			$("startDate").disable();
			$("startTime").disable();
			$("endDate").disable();
			$("endTime").disable();
			$("startDate").ztype="String";
			$("startTime").ztype="String";
			$("endDate").ztype="String";
			$("endTime").ztype="String";
		}
	});
//各个批次优惠券信息列表
function getCouponBatchList(){
	var couponBatchother=jQuery("#batchother").val();
	var couponBatch=jQuery("#batch").val();
	var diag = new Dialog("Diag2");
	diag.Width = 650;
	diag.Height = 370;
	diag.Title = "各批次优惠券列表";
	diag.URL = "Activity/CouponBatchDialog.jsp?couponBatchother="+couponBatchother+"&couponBatch="+couponBatch;
	diag.OKEvent = choosecouponbatch;
	diag.show();
	diag.addButton("appendinsuranceCompany","选择",chooseCouponBatch);
	diag.CancelButton.value = "取消";
}
//确定优惠券批次
function choosecouponbatch(){
	var couponBatch2other=$DW.$V("couponBatch2other");
	var couponBatch2=$DW.$V("couponBatch2");
	jQuery("#batchother").val(couponBatch2other);
	jQuery("#batch").val(couponBatch2other);
	$D.close();
}
//选择优惠券批次
function chooseCouponBatch(){
	var arr = $DW.DataGrid.getSelectedValue("dg2");
	if (!arr || arr.length == 0) {
		//Dialog.alert("您还没有选择批次！");
		//return false;
		$DW.$S("couponBatch2other","");
		$DW.$S("couponBatch2","");
	}else{
		var batch=$DW.DataGrid.getSelectedValueByColumns("dg2","batch");
		$DW.$S("couponBatch2other",batch);
		$DW.$S("couponBatch2",arr.join());
	}
}
function getProvideList(){
	var diag = new Dialog("Diag2");
	diag.Width = 550;
	diag.Height = 350;
	diag.Title = "发放人列表";
	diag.URL = "Coupon/ProvideUserDialog.jsp?provideuserparam=ceshi";
	diag.OKEvent = choose;
	diag.show();
}
//发放人列表
function getProvideList(){
	var provideUserother=jQuery("#provideUserother").val();
	var provideUser=jQuery("#provideUser").val();
	var diag = new Dialog("Diag2");
	diag.Width = 550;
	diag.Height = 370;
	diag.Title = "发放人列表";
	diag.URL = "Coupon/ProvideUserDialog.jsp?provideUserother="+provideUserother+"&provideUser="+provideUser;
	diag.OKEvent = chooseprovide;
	diag.show();
	diag.addButton("appendUser","追加",appenduser);
	diag.addButton("appendUser","选择",chooseProvide);
	diag.CancelButton.value = "取消";
	//$DW.$S("provideUser2other",provideUserother);
	//$DW.$S("provideUser2",provideUser);
}
//险种列表
function getRiskCodeList(){
	var riskCodeother=jQuery("#riskCodeother").val();
	var riskCode=jQuery("#riskCode").val();
	var diag = new Dialog("Diag3");
	diag.Width = 550;
	diag.Height = 370;
	diag.Title = "险种列表";
	diag.URL = "Coupon/RiskCodeDialog.jsp?riskCodeother="+riskCodeother+"&riskCode="+riskCode;
	diag.OKEvent = chooseriskcode;
	diag.show();
	diag.addButton("appendriskCode","追加",appendRiskCode);
	diag.addButton("appendriskCode","选择",chooseRiskCode);
	diag.CancelButton.value = "取消";
	//$DW.$S("riskCode2other",riskCodeother);
	//$DW.$S("riskCode2",riskCode);
}
//保险公司列表
function getInsuranceCompanyList(){
	var insuranceCompanyother=jQuery("#insuranceCompanyother").val();
	var insuranceCompany=jQuery("#insuranceCompany").val();
	var diag = new Dialog("Diag4");
	diag.Width = 550;
	diag.Height = 370;
	diag.Title = "保险公司列表";
	diag.URL = "Coupon/InsuranceCompanyDialog.jsp?insuranceCompanyother="+insuranceCompanyother+"&insuranceCompany="+insuranceCompany;
	diag.OKEvent = chooseinsurancecompany;
	diag.show();
	diag.addButton("appendinsuranceCompany","追加",appendInsuranceCompany);
	diag.addButton("appendinsuranceCompany","选择",chooseInsuranceCompany);
	diag.CancelButton.value = "取消";
	//$DW.$S("insuranceCompany2other",insuranceCompanyother);
	//$DW.$S("insuranceCompany2",insuranceCompany);
}

//产品列表
function getProductList() {
	var productother=jQuery("#productother").val();
	var product=jQuery("#product").val();
	var diag = new Dialog("Diag2");
	diag.Width = 550;
	diag.Height = 370;
	diag.Title = "产品列表";
	diag.URL = "./Activity/ProductDialog.jsp";
	diag.OKEvent = chooseproduct;
	diag.show();
	diag.addButton("appendriskCode","追加",appendProduct);
	diag.addButton("appendriskCode","选择",chooseProduct);
	diag.CancelButton.value = "取消";
	var  producttimeoutID=setTimeout(function (){
		$DW.$S("product2",product);
		$DW.$S("product2other",productother);
		var riskcodeArray=product.split(",");
		for(var i=0;i<riskcodeArray.length;i++){
			$DW.DataGrid.select($DW.$("dg2"),riskcodeArray[i]);
		}
		clearTimeout(producttimeoutID);
	}, 1000 );
}

//选择产品
function chooseProduct(){
	var arr;
	var ActivityChannel = jQuery('#ActivityChannel').val();
	if (ActivityChannel == 'tb') {
		arr = $DW.DataGrid.getSelectedValue("dg4");
	} else {
		arr = $DW.DataGrid.getSelectedValue("dg2");
	}
	if (!arr || arr.length == 0) {
		//Dialog.alert("您还没有选择险种！");
		//return false;
		$DW.$S("product2other","");
		$DW.$S("product2","");
	}else{
		var productname=$DW.DataGrid.getSelectedValueByColumns("dg2","productname");
		$DW.$S("product2other",productname);
		$DW.$S("product2",arr.join());
	}
}
//确定产品
function chooseproduct(){
	var product2other=$DW.$V("product2other");
	var product2=$DW.$V("product2");
	jQuery("#productother").val(product2other);
	jQuery("#product").val(product2);
	$D.close();
}
//产品追加
function appendProduct(){
	var arr;
	var ActivityChannel = jQuery('#ActivityChannel').val();

	var productname=$DW.DataGrid.getSelectedValueByColumns("dg2","productname");
	publicAppend("product2other",productname);
	arr = $DW.DataGrid.getSelectedValue("dg2");
	
	publicAppend("product2",arr.join());
	
}
//选择发放人
function chooseProvide(){
	//用户名
	var arr = $DW.DataGrid.getSelectedValue("dg2");
	if (!arr || arr.length == 0) {
		//Dialog.alert("您还没有选择用户！");
		//return false;
		$DW.$S("provideUser2other","");
		$DW.$S("provideUser2","");
	}else{
		var realname=$DW.DataGrid.getSelectedValueByColumns("dg2","realname");
		$DW.$S("provideUser2other",realname);
		$DW.$S("provideUser2",arr.join());
	}
}
//选择险种
function chooseRiskCode(){
	//用户名
	var arr = $DW.DataGrid.getSelectedValue("dg2");
	if (!arr || arr.length == 0) {
		//Dialog.alert("您还没有选择用户！");
		//return false;
		$DW.$S("riskCode2other","");
		$DW.$S("riskCode2","");
	}else{
		var codename=$DW.DataGrid.getSelectedValueByColumns("dg2","codename");
		$DW.$S("riskCode2other",codename);
		$DW.$S("riskCode2",arr.join());
	}
}
//选择保险公司
function chooseInsuranceCompany(){
	//用户名
	var arr = $DW.DataGrid.getSelectedValue("dg2");
	if (!arr || arr.length == 0) {
		//Dialog.alert("您还没有选择用户！");
		//return false;
		$DW.$S("insuranceCompany2other","");
		$DW.$S("insuranceCompany2","");
	}else{
		var codename=$DW.DataGrid.getSelectedValueByColumns("dg2","codename");
		$DW.$S("insuranceCompany2other",codename);
		$DW.$S("insuranceCompany2",arr.join());
	}
}
//发放日追加
function appenduser(){
	var arr = $DW.DataGrid.getSelectedValue("dg2");
	var realname=$DW.DataGrid.getSelectedValueByColumns("dg2","realname");
	publicAppend("provideUser2",arr.join());
	publicAppend("provideUser2other",realname);
}
//险种追加
function appendRiskCode(){
	var arr = $DW.DataGrid.getSelectedValue("dg2");
	var codename=$DW.DataGrid.getSelectedValueByColumns("dg2","codename");
	publicAppend("riskCode2",arr.join());
	publicAppend("riskCode2other",codename);
}
//保险公司追加
function appendInsuranceCompany(){
	var arr = $DW.DataGrid.getSelectedValue("dg2");
	var codename=$DW.DataGrid.getSelectedValueByColumns("dg2","codename");
	publicAppend("insuranceCompany2",arr.join());
	publicAppend("insuranceCompany2other",codename);
}
function publicAppend(name,str){
	if (!str || str.length == 0) {
		Dialog.alert("您还没有选择！");
		return false;
	}else{
		var provideUser=$DW.$V(name);
		if(provideUser==""||provideUser==null){
			$DW.$S(name,str);
			//$D.close();
		}else{
			var userarray=str.split(",");
			var str="";
			for(var i=0;i<userarray.length;i++){
				if((provideUser+",").indexOf(userarray[i]+",")==-1){
					str+=userarray[i]+",";
				}
			}
			if(str!=""){
				$DW.$S(name,provideUser+","+str.substring(0,str.length-1));
			}
		}
	}
}
//确定发放人
function chooseprovide(){
	var provideUser2other=$DW.$V("provideUser2other");
	var provideUser2=$DW.$V("provideUser2");
	jQuery("#provideUserother").val(provideUser2other);
	jQuery("#provideUser").val(provideUser2);
	$D.close();
}
//确定险种
function chooseriskcode(){
	var riskCode2other=$DW.$V("riskCode2other");
	var riskCode2=$DW.$V("riskCode2");
	jQuery("#riskCodeother").val(riskCode2other);
	jQuery("#riskCode").val(riskCode2);
	$D.close();
}
//确定保险公司
function chooseinsurancecompany(){
	var insuranceCompany2other=$DW.$V("insuranceCompany2other");
	var insuranceCompany2=$DW.$V("insuranceCompany2");
	jQuery("#insuranceCompanyother").val(insuranceCompany2other);
	jQuery("#insuranceCompany").val(insuranceCompany2);
	$D.close();
}
</script>
</head>
<body class="dialogBody" >
<z:init method="com.wangjin.coupon.CouponInfo.initDialog">
<form id="form3">
		<input type="hidden" value="${ID}" id="ID" name="ID">
		<input type="hidden" value="${couponsn}" id="couponsn" name="couponsn">
		<input type="hidden" value="${startT}" id="startT" name="startT">
		<input type="hidden" value="${endT}" id="endT" name="endT">
		<input type="hidden" value="${createtime}" id="createtime" name="createtime">
		<input type="hidden" value="${prop3}" id="couponType" name="couponType">
		<table width="1050" height="350" align="center" cellpadding="2" cellspacing="0">
		<tr>
										<td style="padding: 6px;" class="blockTd">
										<z:tree id="${ID}" style="height:440px;width:200px;" method="com.sinosoft.platform.Channel.treeDataBindForCoupon" level="3" lazy="true" resizeable="true">
											<img src="../Framework/Images/icon_drag.gif" width="16" height="16">
											<p cid='${ID}' level="${TreeLevel}"><input type="checkbox" name="channel" id='channel_${ID}' value='${ChannelCode}' ${Checked} ${disabled}><label for="channel_${ID}"><span id="span_${ID}">${Name}</span></label></p>
										</z:tree>
										</td>
			<td valign="top">
	    			<fieldset>
						 <table>
						          <tr>
						          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">使用说明：</td>
						          <td ><input value="${direction}" type="text" id="direction" name="direction" ztype="String"  class="inputText" size="20" verify="使用说明|NotNull"></td>
						          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">消费金额：</td>
						          <td width="150px"><input value="${payAmount}" type="text" id="payAmount" name="payAmount" ztype="String"  class="inputText" size="20" ><span id="payAmountCheck"></span></td>
						          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						          <td style="display: ${parValueDis};" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">面值：</td>
						          <td style="display: ${parValueDis};"><input value="${parValue}" type="text" id="parValue" name="parValue" ztype="String"  class="inputText" size="20"></td>
						          <td style="display: ${discountDis};" height="35" id="discount_td" align="right" bordercolor="#eeeeee" class="tdgrey1">折扣：</td>
						          <td style="display: ${discountDis};" id="discountShow_td"><z:select id="prop4" value="${prop4}">${discountInit}</z:select></td>
						          </tr>
					     </table>
	    				 <table>
						          <tr>
						          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">简短描述：</td>
						          <td ><input value="${shortName}" type="text" id="shortName" name="shortName" ztype="String"  class="inputText" size="20" verify="简短描述|NotNull"></td>
						          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">申请人&nbsp;&nbsp;&nbsp;：</td>
						          <td ><z:select id="createUser" style="width:118px" verify="申请人|NotNull" value="${createUser}">${createUserInit}</z:select></td>				          
								  <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">&nbsp;&nbsp;&nbsp;用&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;途：</td>
						          <td width="150px"><z:select id="purpose" style="width:117px"  disabled="true"  value="${purpose}">${purposeInit}</z:select></td>
						          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1"></td>
						          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1"></td>
						          <td ><input value="${useTimes}" type="hidden" id="useTimes" name="useTimes" ztype="String"  class="inputText" size="20" ><span id="useTimesCheck"></span></td>
						          </tr>
					     </table>
					     <table>
						          <tr>
						          <td height="35" align="left" bordercolor="#eeeeee" class="tdgrey1">开始时间：</td>
		       				      <td><input name="startDate" id="startDate" value="${startDate}"  type="text" size="14" ztype="Date" />
		       				      <input name="startTime" id="startTime" value="${startTime}" type="text" size="14" ztype="Time"/></td>
		       				      <td>&nbsp;&nbsp;&nbsp;</td>
						          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">结束时间：</td>
						          <td> <input name="endDate" id="endDate" value="${endDate}" type="text" size="14" ztype="Date"/>
						          <input name="endTime" id="endTime" value="${endTime}" type="text" size="14" ztype="Time"/></td>
						          </tr>
					     </table>
					     <table >
						           <td style="display: ${discountDis};" height="35" align="right" bordercolor="#eeeeee">最高抵扣：</td>
						         <td style="display: ${discountDis};"><input type="text" name="maxDeduction" ztype="String"  class="inputText" size="20" id="maxDeduction" value="${maxDeduction}"></td>

						          	<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;发&nbsp;&nbsp;&nbsp;放&nbsp;&nbsp;&nbsp;渠&nbsp;&nbsp;&nbsp;道：</td>
						          	<td>${issuechannelCheckbox}</td>
						          </tr>
					     </table>
					      <table >
						          <tr>
						          	<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">是否可以与活动共用：</td>
						          	<td><input type="checkbox" id="prop1" name="prop1" value="Y" ${Prop1} /></td>
								    <td>&nbsp;&nbsp;&nbsp;</td>
								    <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">是否发送优惠券提醒通知（微信消息、短信、邮件）</td>
								  	<td><input type="checkbox" id="remindFlag" name="remindFlag" value="Y" ${remindFlag} /></td>
						          </tr>
					     </table>
					     <table>
						          <tr>
						          	<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">优惠公司：</td><td >
						          		<input name="insuranceCompanyother" type="text" value="${insuranceCompanyother}" style="width:350px" class="inputText" id="insuranceCompanyother" disabled="disabled"
              							size="30"   readonly = "readonly" /> 
              							<input name="insuranceCompany" type="hidden" value="${insuranceCompany}" style="width:350px" class="inputText" id="insuranceCompany" disabled="disabled"
              							size="30"   readonly = "readonly" /> 
						          <input type="button" id="chooseInsuranceCompany" name="chooseInsuranceCompany" value="查 找" onClick="getInsuranceCompanyList()">
						          </td>
						          </tr>
						 </table>
						 <table>
						          <tr >
						          	<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" >优惠险种：</td><td >
						          	<input name="riskCodeother" type="text" value="${riskCodeother}" style="width:350px" class="inputText" id="riskCodeother" disabled="disabled"
              							size="30"   readonly = "readonly" /> 
              							<input name="riskCode" type="hidden" value="${riskCode}" style="width:350px" class="inputText" id="riskCode" disabled="disabled"
              							size="30"   readonly = "readonly" /> 
						          <input type="button" id="chooseRiskCode" name="chooseRiskCode" value="查 找" onClick="getRiskCodeList()">
						          	</td>
						          </tr>
						 </table>
						 <table id="productTb">
						          <tr >
						          	<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" >优惠产品：</td><td >
						          	<input name="productother" type="text" value="${productother}" style="width:350px" class="inputText" id="productother" disabled="disabled"
              							size="30"   readonly = "readonly" /> 
              							<input name="product" type="hidden" value="${product}" style="width:350px" class="inputText" id="product" disabled="disabled"
              							size="30"   readonly = "readonly" /> 
						          <input type="button" id="chooseProduct" name="chooseProduct" value="查 找" onClick="getProductList()">
						          	</td>
						          </tr>
						 </table>
						 <table>
						          <tr>
						          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" >发放人&nbsp;&nbsp;&nbsp;：</td>
						          <td ><input name="provideUserother" type="text" value="${provideUserother}" style="width:350px" class="inputText" id="provideUserother" disabled="disabled"
              							size="30"   readonly = "readonly" /> 
              							<input name="provideUser" type="hidden" value="${provideUser}" style="width:350px" class="inputText" id="provideUser" disabled="disabled"
              							size="30"   readonly = "readonly" /> 
						          <input type="button" id="chooseProvide" name="chooseProvide" value="查 找" onClick="getProvideList()">
						          </td>
						          </tr>
					    </table>
					    <br/>
					    <br/>
					    <br/>
	               </fieldset>
			</td>
	    </tr>
	    </table>
	</form>
</z:init>
</body>
</html>

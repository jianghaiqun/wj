<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>报文重新发送</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="../wwwroot/kxb/js/min-jquery.js"></script>
<script type="text/javascript" src="../wwwroot/kxb/js/jquery.cookie.js"></script>
<script type="text/javascript" src="../wwwroot/kxb/js/jquery.form.js"></script>
<script src="../Framework/Main.js"></script>
<script>
var notGoMXB ='';
function send(){
	if(notGoMXB!=''){
		Dialog.alert(notGoMXB)
		return;
	}
	var dc = Form.getData("form2");
	if(Verify.hasError()){
		return;
	}
	
	Dialog.confirm("确认是否重发报文？",function(){
		Dialog.wait("正在发送报文...");
		Server.sendRequest("com.sinosoft.cms.dataservice.Retransmit.send",dc,function(response){
			Dialog.closeEx();
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
				}
			});
		});
	});
}


function teamSend(){
	var dc = Form.getData("form2");
	if(Verify.hasError()){
		return;
	}
	
	Dialog.confirm("确认是否重发报文？",function(){
		Dialog.wait("正在发送报文...");
		Server.sendRequest("com.sinosoft.cms.dataservice.Retransmit.teamSend",dc,function(response){
			Dialog.closeEx();
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
				}
			});
		});
	});
}

function showpostpone(){
	var check_flag=jQuery('#showPostpone').is(':checked');
	if(check_flag==true){
		jQuery("#svalidateBB").show();
		jQuery("#svalidateAA").hide();
	}else{
		jQuery("#svalidateAA").show();
		jQuery("#svalidateBB").hide();
		
	}
	queryOrderDate();
	 
}
function queryOrderDate(){
	var dc = new DataCollection();
	dc.add("OrderSn", $V("OrderSn"));
	var ordersn=$V("OrderSn");
	if(ordersn==null||ordersn==""){
		jQuery("#svalidateA").val('');
		jQuery("#svalidateB").val('');
		jQuery("#evaliDate").val('');
		return;
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.Retransmit.queryOrder",
			dc, function() {
				var response = Server.getResponse();
				var strs= new Array(); //定义一数组 
				strs=response.Message.split("&");
				jQuery("#svalidateA").val(strs[0]);
				jQuery("#svalidateB").val(strs[0]);
				jQuery("#evaliDate").val(strs[1]);
				jQuery("#ensureLimit").val(strs[2]);
				jQuery("#ensureLimitType").val(strs[3]);
				jQuery("#tbTradeSeriNo").val(strs[4]);
				jQuery("#CompanyID").val(strs[5]);
				if(strs[6]!=''){
					notGoMXB = strs[6];
					Dialog.alert(notGoMXB)
				}else{
					notGoMXB='';
				}
			});
}

//add by wangej 增加查询条件淘宝渠道订单号
function queryTBTradeSeriNoData(){
	var dc = new DataCollection();
	dc.add("tbTradeSeriNo", $V("tbTradeSeriNo"));
	var tbTradeSeriNo=$V("tbTradeSeriNo");
	if(tbTradeSeriNo==null||tbTradeSeriNo==""){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.Retransmit.queryOrderSn",
			dc, function() {
				var response = Server.getResponse();
				var strs=response.Message;
				if(response.Status==1 ){
					jQuery("#OrderSn").val(strs);
					jQuery("#svalidateA").val(strs);
					jQuery("#svalidateB").val(strs);
					queryOrderDate();
					return;
				}else if(response.Status == 0 ){
					alert(strs);
					return;
				}else{
					alert(strs);
					return;
				}

			});
}

function effchange(){
	var protectionPeriodLast = document.getElementById("ensureLimit").value;//保障期限后段
	var protectionPeriodTy = document.getElementById("ensureLimitType").value;//保障期限类型Y,M,D
	var effective = document.getElementById("svalidateB").value;
	var temp = addDate(protectionPeriodTy,protectionPeriodLast,effective);
	document.getElementById("evaliDate").value =temp;
}

function  addDate(type,NumDay,dtDate) { 
	 var   reg = /^(\d{4})-(\d{1,2})-(\d{1,2})$/;
		var   date_   =   new   Date( ); 
	 if (arr = dtDate.match(reg)) {
	      var yy = Number(arr[1]);
		  var mm = Number(arr[2]);
		  var dd = Number(arr[3]);
		  var all_ = mm + '/' + dd + '/' +yy;
		  date_ = new Date(all_);
	 } else {
		 var myDate = new Date();
	 }
	lIntval   =   parseInt(NumDay); 
	switch(type) { 
			case   'Y' : 
				date_.setYear(date_.getFullYear()   +   lIntval) ;
				date_.setDate(date_.getDate()  -  1) ;
			break; 
			
			case   'M': 
				date_.setMonth(date_.getMonth()   +   lIntval) ;
				date_.setDate(date_.getDate() - 1) ;
			break; 
			
			case   'D': 
				date_.setDate(date_.getDate()   +   lIntval) ;
				date_.setDate(date_.getDate() - 1) ;
			break ;
			 
	} 
	var mm = date_.getMonth() + 1;
	var dd = date_.getDate();
	if(mm < 10){
	    mm = '0' +mm;
	}
    if(dd < 10){
	    dd = '0' +dd;
	}
    
	return   date_.getFullYear()   + '-'   +   mm   +   '-'   + dd;
}

</script>
</head>
<body>
<z:init method="com.sinosoft.cms.dataservice.Retransmit.init">
<form id="form2">
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	    <tr valign="top">
	      <td>
	      	<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
		          <tr>
		            <td valign="middle" class="blockTd"><img src="../Icons/icon022a1.gif" width="20" height="20" />重复报文</td>
		          </tr>
	           		<tr>
						<td align="center">订单号：</td>
						<td><input name="OrderSn" type="text" class="input1" id="OrderSn" value="" onblur="queryOrderDate();" size="30" verify="订单号|NotNull&&Length=16"/></td>
					</tr>
					<tr>
						<td align="center">淘宝渠道订单号：</td>
						<td><input name="tbTradeSeriNo" type="text" class="input1" id="tbTradeSeriNo" value="" onblur="queryTBTradeSeriNoData();" size="30" verify="淘宝渠道订单号"/></td>
					</tr>
					<tr>
						<td align="center">保险公司编码：</td>
						<td><input name="CompanyID" type="text" class="input1"   id="CompanyID" value="" size="30"  readonly="readonly" style="width:160px;border-color: #ccc; background:#f5f5f5 none;"/></td>
		           </tr>
					<tr id ="svalidateAA">
						<td align="center">起始日期：</td>
						<td><input name="svalidateA" type="text" class="input1"   id="svalidateA" value="${svalidateA}" size="30"  readonly="readonly" style="width:160px;border-color: #ccc; background:#f5f5f5 none;"/></td>
					</tr>
					<tr id ="svalidateBB" style="display: none">
						<td align="center">起始日期：</td>
						<td><input name="svalidateB" type="text" class="input1"  onblur = "effchange()" id="svalidateB" value="${svalidateB}" size="30" ztype="date" readonly="readonly" style="width:160px;border-color: #ccc; background:#f5f5f5 none;"/></td>
					</tr>
					<tr>
						<td align="center">终止日期：</td>
						<td><input name="evaliDate" type="text" class="input1"   id="evaliDate" value="${evaliDate}" size="30"  readonly="readonly" style="width:160px;border-color: #ccc; background:#f5f5f5 none;"/></td>
					</tr>
					<tr><input type="hidden" id="ensureLimit" name="ensureLimit" value="${ensureLimit}" /></tr>
					<tr><input type="hidden" id="ensureLimitType" name="ensureLimitType" value="${ensureLimitType}" /></tr>
					<tr>
						<td align="center">是否顺延：</td>
						<td><input name="showPostpone" type="checkbox" class="input1" id="showPostpone" value="" size="30" onclick="showpostpone()"/></td>
					</tr>
					<tr>
						<td  colspan="2" align="center">
							<z:tbutton onClick="send()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>发送报文</z:tbutton>
							<z:tbutton onClick="teamSend()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>团险发送报文</z:tbutton>
						</td>
					 
					</tr>
	     	 </table>
	      </td>
	    </tr>
	</table>
</form>
</z:init>
</body>
</html>

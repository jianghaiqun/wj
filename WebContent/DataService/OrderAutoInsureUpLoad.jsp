<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="../wwwroot/kxb/style/new_header.css"/>
<link rel="stylesheet" type="text/css" href="../wwwroot/kxb/style/new_logo.css">
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="../wwwroot/kxb/style/shop/css/new_shops.css"/>

<link rel="stylesheet" type="text/css" href="../wwwroot/kxb/style/skins/default.css"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
.shop_day{ background:url(../images/day_07.gif) no-repeat 98% 6px;}
</style>

<script type="text/javascript" src="../wwwroot/kxb/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="../wwwroot/kxb/js/jquery.form.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.jqDnR.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.jqModal.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="../wwwroot/kxb/js/template/common/js/base.js"></script>
<script type="text/javascript" src="../wwwroot/kxb/js/Common.js"></script>
<script type="text/javascript" src="../Framework/Main.js"></script>
<script type="text/javascript" src="../Framework/Spell.js"></script>

<script type="text/javascript" src="../template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../template/shop/js/calendar.js"></script> 
<script type="text/javascript" src="../wwwroot/kxb/js/productDutyNew.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/redesign/re_base.js"></script>
 <script type="text/javascript" src="InsureInfoUpLoad.js"></script>
  <script type="text/javascript">
 function loadRelation(){
		 /* 修改对保险期限的处理，以保证暂存后能够正确带出曾经保存的数据 */
		 var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
		 if(("true"==protectionPeriodFlag) && ("" == "")){
			var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
			
			 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
			 var d = new Date();
			 d.setDate(d.getDate()+1);
			 var ndate = d.getFullYear()+"-"+add_zero(d.getMonth()+1)+"-"+add_zero(d.getDate());
			 document.getElementById("effective").value = ndate;
			 var temp = addDate(protectionPeriodTy,protectionPeriodLast, ndate);
			 document.getElementById("fail").value = temp;
			 document.getElementById("fail_").value = temp;
		 }
	}


 function setEffectiveDate() {
 	var period = document.getElementById("period").value;
 	if (period != null && period != "") {
 		var periods = period.split("-");
 		var periodBe = "";
 		var periodAf = "";
 		var protectionPeriodTy = "";
 		var protectionPeriodLast = "";
 		if (periods != null && periods.length == 1) {
 			// 保障期限前段
 			periodBe = periods[0];
 			// 保障期限后段
 			periodAf = periods[0];

 		} else if (periods != null && periods.length == 2) {
 			// 保障期限前段
 			periodBe = periods[0];
 			// 保障期限后段
 			periodAf = periods[1];
 		}
 		
 		if (periodBe != null && periodBe != "" && periodBe.length > 1) {
 			protectionPeriodTy = periodBe.substring(periodBe.length - 1);// 保障期限类型
 		}
 		
 		if (periodAf == null || periodAf == "") {
 			periodAf = periodBe;
 		}
 		
 		if (periodAf != null && periodAf != "" && periodAf.length > 1) {
 			protectionPeriodLast = periodAf.substring(0, periodAf.length - 1);
 		}
 		document.getElementById("protectionPeriodTy").value = protectionPeriodTy;
 		document.getElementById("protectionPeriodLast").value = protectionPeriodLast;
 		document.getElementById("protectionPeriodFlag").value = "true";
 	}
 	effchange();
 }
 
 function getHealthInfo() {
		var dg = $("dghealth");
		var dt = dg.DataSource;
		var names = ["healthid", "selectFlag"];
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
		var result = "";
		
		for ( var i = 0; i < dt.getRowCount(); i++) {
			result += ("|"+map[names[0]][i]+"-"+map[names[1]][i]);
		}
		if (result != '') {
			result = result.substring(1);
		}
		return result;
	}
 
 /**
  * 上传按钮的处理
  * @returns {Boolean}
  */
 function ImportExcel() {
 	var startDate = document.getElementById("effective").value;
 	if (startDate == null || startDate == '') {
 		Dialog.alert("请选择保单生效日期！");
 		return false;
 	}
 	var file = document.getElementById("importInsureFile").value;
 	if (file == null || file == '') {
 		Dialog.alert("请选择上传的文件！");
 		return false;
 	}
 	if(Verify.hasError()){
		return false;
	}
	
 	var productId = document.getElementById("productID").value;
 	var param = "";
 	var tr = document.getElementsByTagName('tr');
 	for (var i = 0; i < tr.length; i++) {
 		var id = tr[i].id;
 		if (id != "" && id != null) {
 			id = id.replace("TR", "");
 			if (tr[i].style.display == undefined || tr[i].style.display == "") {
 				param += "&" + id + "Value=" + $V(id);
 			}
 		}
 	}
 	
 	param += "&activityDesc="+$V("activityDesc");
 	var period = document.getElementById("period").value;
 	param += "&periodValue="+period;
 	param += "&channelSn="+$V("channel");
 	param += "&applicantIDtype="+$V("appIDtype");
 	param += "&applicantSex="+$V("appSex");
 	param += "&sendEmailFlag="+$V("isSendEmail");
 	
     if (jQuery("#occupation").is(":visible")) {
		 param += "&occupationLevelOne="+$V("occupationLevelOne");
		 param += "&occupationLevelTwo="+$V("occupationLevelTwo");
		 param += "&occupationLevelThree="+$V("occupationLevelThree");
     }
 	var endDate = document.getElementById("fail").value;
 	var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;
 	var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;
 	
 	var healthInfo = getHealthInfo();
 	if (healthInfo != "") {
 		param += "&healthInfo="+healthInfo;
 	}
 	var turl=sinosoft.base+"/shop/order_auto_insure!upLoadSave.action?productId="+productId + "&startDate=" + startDate 
	  + "&endDate=" + endDate + "&protectionPeriodTy=" + protectionPeriodTy + "&protectionPeriodLast=" + protectionPeriodLast + param;
 	
    /*前台加密两次，后台解密一次*/
	turl = encodeURI(turl);
	turl = encodeURI(turl); 
	 
		var options = { 
		url:turl, 
		async:true,
		type:"POST",  
		dataType: "json",
		resetForm:false,
	success: function(data){ 
		Dialog.closeEx();
		var Status = data.Status;
		var Msg = data.Msg;
		if(Status=="1"){
			alert("上传失败！\n"+Msg);
		}else{
			alert("上传成功！");
		}
	 }
	};  
	Dialog.wait("正在上传......");
    jQuery('#fm').ajaxSubmit(options); 
 	
 }

 // 获取职业下级菜单
 function getNextLevelOccupation(param) {
 	var id = $V(param);
	 var occupationLevel;
	 if (param == "occupationLevelOne") {
		 occupationLevel = "occupationLevelTwo";
		 jQuery('#occupationLevelThree').html("");
	 } else if (param == "occupationLevelTwo") {
		 occupationLevel = "occupationLevelThree";
	 }
	 if(id!=""){
		 jQuery.ajax({
			 type:'post',
			 url: sinosoft.base+"/shop/order_auto_insure!getNextLevelOccupation.action?id="+id,
			 dataType: "json",
			 success: function(data) {
				if (data.status == "0") {
					jQuery('#'+occupationLevel).html(data.data);
				}
			 }
		 });
	 } else {
		 jQuery('#'+occupationLevel).html("");
	 }

 }
 </script>
</head>
<body class="dialogBody" onLoad="init();loadRelation();">
	<iframe src="javascript:void(0);" name="targetFrame" width="0"
		height="0" frameborder="0"></iframe>
	<form name="fm" id="fm" target="targetFrame" 
		method="post" enctype="multipart/form-data">
		<table width="100%" cellpadding="2" cellspacing="0" class="blockTable">
			<z:init method="com.sinosoft.cms.dataservice.OrderAutoInsure.initDialog">
				<tr>
					<td colspan="3" align="center"><label>产品名称：</label>${productName} <input
						type="hidden" id="productID" value="${productID}" />
					</td>
				</tr>
				<tr><td></td><td></td><td></td></tr>
				<tr>
					<td width="100px" align="right">保险期限：</td>
					<td td width="150px"><z:select name="period" id="period" style="width:100px;" onChange="setEffectiveDate()">${period}</z:select></td>
					<td>自<input id="effective" name="startDate"
						type="text"
						onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}',maxDate:'%y-%M-{%d+700}'})"
						onFocus="effchange()" value="" verify="保单起保日期|NOTNULL"
						class="shop_day" style="width:100px;" />零时起 &nbsp;至 <input id="fail_" name="fail_"
						disabled="disabled" style="border: 0; background: transparent;width:100px;"
						value="" /> 二十四时 <input id="fail" type="hidden" value="" /> 
						<input type="hidden" id="protectionPeriodTy" name="protectionPeriodTy" value="${protectionPeriodTy}">
						<input type="hidden" id="protectionPeriodLast" name="protectionPeriodLast" value="${protectionPeriodLast}"> 
						<input type="hidden" id="protectionPeriodFlag" name="protectionPeriodFlag" value="${protectionPeriodFlag}">
					</td>
				</tr>
				<tr style="display:none" id="planTR">
					<td align="right">计划：</td>
					<td colspan="2"><z:select name="plan" id="plan" style="width:100px;">${plan}</z:select></td>
				</tr>
				<tr style="display:none" id="occupTR">
					<td align="right">职业类别：</td>
					<td colspan="2"><z:select name="occup" id="occup" style="width:100px;">${occup}</z:select></td>
				</tr>
				<tr style="display:none" id="textAgeTR">
					<td align="right">投保年龄：</td>
					<td colspan="2"><z:select name="textAge" id="textAge" style="width:100px;">${textAge}</z:select></td>
				</tr>
				<tr style="display:none" id="appTypeTR">
					<td align="right">缴费方式：</td>
					<td colspan="2"><z:select name="appType" id="appType" style="width:100px;">${appType}</z:select></td>
				</tr>
				<tr style="display:none" id="feeYearTR">
					<td align="right">缴费年期：</td>
					<td colspan="2"><z:select name="feeYear" id="feeYear" style="width:100px;">${feeYear}</z:select></td>
				</tr>
				<tr style="display:none" id="gradeTR">
					<td align="right">产品级别：</td>
					<td colspan="2"><z:select name="grade" id="grade" style="width:100px;">${grade}</z:select></td>
				</tr>
				<tr style="display:none" id="appLevelTR">
					<td align="right">投保档次：</td>
					<td colspan="2"><z:select name="appLevel" id="appLevel" style="width:100px;">${appLevel}</z:select></td>
				</tr>
				<tr style="display:none" id="mulPeopleTR">
					<td align="right">保险责任多类人群：</td>
					<td colspan="2"><z:select name="mulPeople" id="mulPeople" style="width:100px;">${mulPeople}</z:select></td>
				</tr>
				<tr>
					<td align="right">订单渠道：</td>
					<td colspan="2">
						<z:select id="channel" name="channel" style="width:100px;">${channelSn}</z:select>
						
					</td>
				</tr>
				<tr height="20px">
					<td colspan="3" ></td>
				</tr>
				<tr>
					<td colspan="3">
						投保人信息（<span style="color:red;">未成年人需填写</span>）<br>
						<table>
							<tr>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;姓名：</td>
								<td><input type="text" id="applicantName" name="applicantName" size="10" verify="投保人姓名|UFO&&LEN>2"/></td>
								<td>英文名：</td>
								<td><input type="text" id="applicantEnName" name="applicantEnName" style="width:100px" verify="投保人英文名|UFO&&LEN>2"/></td>
								<td>证件类型：</td>
								<td><z:select id="appIDtype" name="appIDtype" style="width:100px">${IdentityType}</z:select></td>
								<td>证件号：</td>
								<td><input type="text" id="applicantID" name="applicantID" size="30" /></td>
							</tr>
								
							<tr>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;性别：</td>
								<td><z:select id="appSex" name="appSex" style="width:40px;">${Sex}</z:select></td>
								<td>出生日期：</td>
								<td><input name="applicantBirthday" type="text" style="width:100px"  id="applicantBirthday" ztype="date" verify="投保人出生日期|Date&&APPAGE"/></td>
								<td>手机号：</td>
								<td><input type="text" id="applicantMobile" name="applicantMobile" style="width:100px" maxlength="11" verify="投保人手机号码|PHONE"/></td>
								<td>邮箱：</td>
								<td><input type="text" id="applicantMail" name="applicantMail" size="30" verify="投保人电子邮箱|Email"/></td>
							</tr>
						</table>
						
					</td>
				</tr>
				<tr id="occupation" style="display: ${disStyle}">
					<td align="right">职业：</td>
					<td colspan="2">
						<select id="occupationLevelOne" name="occupationLevelOne" style="width:200px;" onChange="getNextLevelOccupation('occupationLevelOne')">${occupationLevelOne}</select>
						<select id="occupationLevelTwo" name="occupationLevelTwo" style="width:200px;" onChange="getNextLevelOccupation('occupationLevelTwo')"></select>
						<select id="occupationLevelThree" name="occupationLevelThree" style="width:200px;"></select>
					</td>
				</tr>
				<tr height="20px">
					<td align="right">是否发送电子保单邮件：</td>
					<td colspan="2" ><z:select id="isSendEmail" name="isSendEmail" style="width:100px">${YesOrNo}</z:select></td>
					
				</tr>
				<tr height="20px">
					<td align="right">活动短信描述：</td>
					<td colspan="2" ><input type="text" id="activityDesc" name="activityDesc" size="80" verify="活动短信描述|NotNull" /></td>
					
				</tr>
				
				<tr height="20px">
					<td colspan="3" ></td>
				</tr>
				<tr>
					<td colspan="3" align="center">
						<input type="file" style="width: 300px" name="importInsureFile" id="importInsureFile" onchange="getFileSuff();" />
						<input name="importBtn" type="button" class="inputButton" id="importBtn" onClick="return ImportExcel();" value="上  传" /> 
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<A href="../traveltemplate/${templateXls}">批量导入模板下载</A>
					</td>
				</tr>
				<tr height="20px">
					<td colspan="3" ></td>
				</tr>
				<tr>
					<td colspan="3">
					 健康告知信息
					<z:datagrid id="dghealth" method="com.sinosoft.cms.dataservice.OrderAutoInsure.healthInquery" >
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="300px" >
							<tr ztype="head" class="dataTableHead">
								<td width="90" style="text-align:center;"><b>告知栏</b></td>
								<td width="10" style="text-align:center;"><b>选项</b></td>
								<td style="display:none"></td>
							</tr>
							<tr >
								<td >${showinfo}</td>
								<td><z:select name="selectFlag"  style="width:50px;" value="N">${@healthyFlag}</z:select></td>
								<td style="display:none"><input name="healthid" type="text" id="healthid" 
									value="${healthid}" /></td>
							</tr>
						</table>
					</z:datagrid></td>
				</tr>
			</z:init>
		</table>
	</form>
</body>
</html>
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
<script type="text/javascript" src="../template/common/js/jquery.jqDnR.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.jqModal.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="../wwwroot/kxb/wj/template/common/js/base.js"></script>
<script type="text/javascript" src="../wwwroot/kxb/js/Common.js"></script>
<script type="text/javascript" src="../Framework/Main.js"></script>
<script type="text/javascript" src="../Framework/Spell.js"></script>

<script type="text/javascript" src="../template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="../template/shop/js/calendar.js"></script> 
<script type="text/javascript" src="../wwwroot/kxb/js/productDutyNew.js"></script>

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
 	var productId = document.getElementById("productID").value;
 	var param = "";
 	var tr = document.getElementsByTagName('tr');
 	for (var i = 0; i < tr.length; i++) {
 		var id = tr[i].id;
 		if (id != "" && id != null) {
 			id = id.replace("TR", "");
 			if (tr[i].style.display == undefined || tr[i].style.display == "") {
 				param += "&" + id + "=" + document.getElementById(id).value;
 			}
 		}
 	}
 	var period = document.getElementById("period").value;
 	param += "&period="+period;
 	var endDate = document.getElementById("fail").value;
 	var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;
 	var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;
 	Dialog.confirm("确认要导入？",function() { 
 		Dialog.wait("正在生成......");
 		fm.action = "InsureInfoUpLoadSave.jsp?productId="+productId + "&startDate=" + startDate 
 				  + "&endDate=" + endDate + "&protectionPeriodTy=" + protectionPeriodTy + "&protectionPeriodLast=" + protectionPeriodLast + param;
 		fm.submit();
 	});
 }
 </script>
</head>
<body class="dialogBody" onLoad="init();loadRelation();">
	<iframe src="javascript:void(0);" name="targetFrame" width="0"
		height="0" frameborder="0"></iframe>
	<form name="fm" target="targetFrame" action="InsureInfoUpLoadSave.jsp"
		method="post" enctype="multipart/form-data">
		<table width="100%" cellpadding="2" cellspacing="0" class="blockTable">
			<z:init method="com.sinosoft.cms.dataservice.OrderImport.initDialog">
				<tr>
					<td colspan="3" align="center"><laber>产品名称：</laber>${productName} <input
						type="hidden" id="productID" value="${productID}" />
					</td>
				</tr>
				<tr><td></td><td></td><td></td></tr>
				<tr>
					<td width="100px" align="right">保险期限：</td>
					<td><z:select name="period" id="period" style="width:100px;" onChange="setEffectiveDate()">${period}</z:select></td>
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
				<tr height="100px">
					<td colspan="3" align="center">
						<input type="file" style="width: 300px" name="importInsureFile" id="importInsureFile" onchange="getFileSuff();" />
						<input name="importBtn" type="button" class="inputButton" id="importBtn" onClick="return ImportExcel();" value="上  传" /> 
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center"><A href="./ADTemplate/OrderDataTemplate.xls">批量导入模板下载</A></td>
				</tr>
				<td colspan="3" align="center"><A href="./ADTemplate/Certificate.xls">证件类型-数字字典</A>
				    <A href="./ADTemplate/Relationship.xls">与投保人关系-数字字典</A>
					<A href="./ADTemplate/Sex.xls">性别-数字字典</A></td>
				<tr>
				<td colspan="3" align="center"><A href="./ADTemplate/Area.xls">所在地区-数字字典</A>
				    <A href="./ADTemplate/Occupation.xls">职业-数字字典</A>
					<A href="./ADTemplate/Country.xls">旅游目的地-数字字典</A></td>
				<tr>
				</tr>
			</z:init>
		</table>
	</form>
</body>
</html>
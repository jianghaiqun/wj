<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="org.dom4j.*"%>
<%@page import="org.dom4j.io.*"%> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script> 
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
	function sear() {
		DataGrid.setParam("dg1", Constant.PageIndex, 0); 
		DataGrid.setParam("dg1", "batchflag","");
		DataGrid.setParam("dg1", "couponSn", $V("couponSn"));
		DataGrid.setParam("dg1", "createStartDate", $V("createStartDate"));
		DataGrid.setParam("dg1", "createStartTime", $V("createStartTime"));
		DataGrid.setParam("dg1", "createEndDate", $V("createEndDate"));
		DataGrid.setParam("dg1", "createEndTime", $V("createEndTime"));
		DataGrid.setParam("dg1", "createuserSearch", $V("createuserSearch"));
		DataGrid.setParam("dg1", "provideUserSearch", $V("provideUserSearch"));
		DataGrid.setParam("dg1", "riskcodeSearch", $V("riskcodeSearch"));
		DataGrid.setParam("dg1", "insuranceCompanySearch", $V("insuranceCompanySearch"));
		DataGrid.setParam("dg1", "directionSearch", $V("directionSearch"));
		DataGrid.setParam("dg1", "batch", $V("batch"));
		DataGrid.setParam("dg1", "prop3", $V("prop3"));
		DataGrid.setParam("dg1", "issuechannel", $V("issuechannel"));
		DataGrid.setParam("dg1", "channel", $V("channel"));
		DataGrid.loadData("dg1");
	}
	var createWindowW,createWindowD;
	function addCoupon() {
		var diag = new Dialog("Diag1");
		diag.Width = 1150;
		diag.Height = 500;
		diag.Title = "新建优惠劵";
		diag.URL = "Coupon/CouponAdd.jsp?isApply=1";
		diag.onLoad = function() {
			createWindowD = diag;
			createWindowW = diag.Window;
		};
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "新建优惠劵";
		diag.show();
	}
	function addCpsCoupon() {
		var diag = new Dialog("Diag1");
		diag.Width = 1150;
		diag.Height = 500;
		diag.Title = "新建优惠劵";
		diag.URL = "Coupon/CouponAdd.jsp?ActivityChannel=cps&isApply=1";
		diag.onLoad = function() {
			$DW.$('productTb').hide();
			createWindowD = diag;
			createWindowW = diag.Window;
		};
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "新建优惠劵";
		diag.show();
		
	}
	
	function addSave() {
		//$DW.$V  
		//$DW.$("").innerHTML
		//$DW.$S(
		//日期
		if (!checkDate()) {
			return;
		}
		//时间
		if (!checkTime()) {
			return;
		}
		//检查数字输入
		if (checkNum() == "false") {
			return;
		}
		var parValue = $DW.$V('parValue');
		var payAmount = $DW.$V('payAmount');
		var couponType = $DW.$V('prop3');
		var provideUserother = $DW.$V('provideUserother');
		
		if(parseInt(payAmount)<0){
			Dialog.alert("消费金额必须大于等于0！");
			return ;
		}
		if (couponType == '01') {
			if (parValue == null || parValue == '') {
				Dialog.alert("面值不能为空！");
				return ;
			}
			if(parseFloat(parValue)<=0){
				Dialog.alert("面值必须大于0！");
				return ;
			}
		} else {
			var discount = $DW.$V('prop4');
			if (discount == null || discount == '') {
				Dialog.alert("请选择折扣！");
				return ;
			}
		}
		//if(parseInt(payAmount)<=parseInt(parValue)){
			//Dialog.alert("消费金额必须大于面值！");
			//return ;
		//}
		if(provideUserother==""||provideUserother==null){
			Dialog.alert("发放人为必填项！");
			return ;
		}
		var dc = $DW.Form.getData("form2");
		//检查发放渠道是否必选
		var issuechannel = dc.map.issuechannel;
		if(issuechannel == null){
			Dialog.alert("发放渠道为必选项！");
			return ;
		}
		//检查应用渠道是否必选
		var channel = dc.map.channel;
		if(channel == null){
			Dialog.alert("应用渠道为必选项");
			return;
		}
		
		if ($DW.Verify.hasError()) {
			return;
		}
		//检查申请人邮箱是否为空
		var dcCheckEmail = new DataCollection();
		dcCheckEmail.add("applyUser", $DW.$V('createUser'));
		Server.sendRequest("com.wangjin.coupon.CouponInfo.checkApplyEmail", dcCheckEmail,
			function(response) {
				if(response.Status != 1){
					Dialog.alert(response.Message);
				}else{
					Dialog.confirm("确认要生成优惠券？",function() {
						Server.sendRequest("com.wangjin.coupon.CouponInfo.asyncAdd", dc, function(response) {
							var taskID = response.get("TaskID");
							var p = new Progress(taskID, "正在生成优惠券......");
							p.show(function(){
								createWindowD.close();
								DataGrid.loadData("dg1");
							});
						});
					});
				}
			});
	}
	function checkDate() {
		if(!$DW.$V){
			$DW = createWindowW;
		}
		var startDate = $DW.$V('startDate');
		var endDate = $DW.$V('endDate');
		var startTime = $DW.$V("startTime");
		var endTime = $DW.$V("endTime");
		if (startDate != "" || endDate != "" || startTime != "" || endTime != "") {
			if (startDate == "") {
				Dialog.alert("您还没有选择开始日期！");
				return false;
			}else if (endDate == "") {
				Dialog.alert("您还没有选择结束日期！");
				return false;
			}else if (startTime == "") {
				Dialog.alert("您还没有选择开始时间！");
				return false;
			}else if (endTime == "") {
				Dialog.alert("您还没有选择结束时间！");
				return false;
			}
		} 
		var a = new Date((startDate+" "+startTime).replace(/-/g,"/"));
		var b = new Date((endDate+" "+endTime).replace(/-/g,"/"));
		if(a>b) {
			Dialog.alert("开始日期大于结束日期！");
			return false;
		} else {
			return true;
		}
	}
	function checkTime() {
		if(!$DW.$V){
			$DW = createWindowW;
		}
		var startDate = $DW.$V("startDate");
		var endDate = $DW.$V("endDate");
		var a = new Date((startDate+" "+startTime).replace(/-/g,"/"));
		var b = new Date((endDate+" "+endTime).replace(/-/g,"/")); 
		if (startDate == endDate) {
			var startTime = $DW.$V("startTime");
			var endTime = $DW.$V("endTime");
			if (endTime != "" && startTime == "") {
				Dialog.alert("您还没有选择开始时间！");
				return false;
			} else if (startTime != "" && endTime == "") {
				Dialog.alert("您还没有选择结束时间！");
				return false;
			} else if (endTime != "" && startTime != "" && (a>b)) {
				Dialog.alert("开始时间大于结束时间！");
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
	function checkNum() {
		if(!$DW.$V){
			$DW = createWindowW;
		}
		var payAmount = $DW.$V('payAmount');
		var parValue = $DW.$V('parValue');
		//生成数量
		var createNum = $DW.$V('createNum');
		var useTimes = $DW.$V('useTimes');
		var maxDeduction = $DW.$V('maxDeduction');
		var checkMessage = "请输入正整数！";
		var checkMessage1 = " 最多两位小数！";
		var numcheck = /^[0-9]*$/;
		var numcheck1 = /^(([0-9]*)|([0-9]*(.[0-9]{1,2})))$/;
		var flag = "true";
		if (payAmount != "") {
			if (!numcheck.test(payAmount)) {
				//$DW.$("payAmountCheck").innerHTML = checkMessage;
				Dialog.alert("消费金额"+checkMessage);
				flag = "false";
			} else {
				$DW.$("payAmountCheck").innerHTML = "";
			}
		} else {
			$DW.$("payAmountCheck").innerHTML = "";
		}
		if (parValue != "") {
			if (!numcheck1.test(parValue)) {
				//$DW.$("parValueCheck").innerHTML = checkMessage;
				Dialog.alert("面值"+checkMessage1);
				flag = "false";
			}
		}
		if (createNum != "") {
			if (!numcheck.test(createNum)) {
				//$DW.$("createNumCheck").innerHTML = checkMessage;
				Dialog.alert("生成数量"+checkMessage);
				flag = "false";
			} else {
				$DW.$("createNumCheck").innerHTML = "";
			}
		} else {
			$DW.$("createNumCheck").innerHTML = "";
		}
		if(maxDeduction != ""){
			if (!numcheck.test(maxDeduction)) {
				Dialog.alert("最高抵扣"+checkMessage);
				flag = "false";
			} 
		}
		if (useTimes != "") {
			if (!numcheck.test(useTimes)) {
				//$DW.$("useTimesCheck").innerHTML = checkMessage;
				Dialog.alert("使用次数"+checkMessage);
				flag = "false";
			} else {
				$DW.$("useTimesCheck").innerHTML = "";
			}
		} else {
			$DW.$("useTimesCheck").innerHTML = "";
		}
		return flag;
	}
	function checkEditNum() {
		var payAmount = $DW.$V('payAmount');
		var parValue = $DW.$V('parValue');
		var useTimes = $DW.$V('useTimes');
		var maxDeduction = $DW.$V('maxDeduction');
		var checkMessage = "请输入整数！";
		var checkMessage1 = " 最多两位小数！";
		var numcheck = /^[0-9]*$/;
		var numcheck1 = /^(([0-9]*)|([0-9]*(.[0-9]{1,2})))$/;
		var flag = "true";
		if (payAmount != "") {
			if (!numcheck.test(payAmount)) {
				//$DW.$("payAmountCheck").innerHTML = checkMessage;
				Dialog.alert("消费金额"+checkMessage);
				flag = "false";
			} else {
				$DW.$("payAmountCheck").innerHTML = "";
			}
		} else {
			$DW.$("payAmountCheck").innerHTML = "";
		}
		if (parValue != "") {
			if (!numcheck1.test(parValue)) {
				//$DW.$("parValueCheck").innerHTML = checkMessage;
				Dialog.alert("面值"+checkMessage1);
				flag = "false";
			}
		}
		if (useTimes != "") {
			if (!numcheck.test(useTimes)) {
				//$DW.$("useTimesCheck").innerHTML = checkMessage;
				Dialog.alert("使用次数"+checkMessage);
				flag = "false";
			} else {
				$DW.$("useTimesCheck").innerHTML = "";
			}
		} else {
			$DW.$("useTimesCheck").innerHTML = "";
		}
		if(maxDeduction != ""){
			if (!numcheck.test(maxDeduction)) {
				Dialog.alert("最高抵扣"+checkMessage);
				flag = "false";
			} 
		}
		return flag;
	}
	function del() {
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要删除的优惠券！");
			return;
		}
		//只有未使用状态的优惠券才可以更改
		var status=DataGrid.getSelectedValueByColumn("dg1","status");
		if(status!="未使用"){
			Dialog.alert("只有未使用状态的优惠券才可以删除！");
			return;
		}
		//已关联活动的优惠券不允许更改
		var activitysn=DataGrid.getSelectedValueByColumn("dg1","activitysn");
		if(!(activitysn==""||activitysn==null)){
			Dialog.alert("已关联活动的优惠券不可以删除！");
			return;
		}
		Dialog.confirm("删除后不可恢复，确认要删除？", function() {
			var dc = new DataCollection();
			dc.add("IDs", arr.join());
			Server.sendRequest("com.wangjin.coupon.CouponInfo.delete", dc,
					function(response) {
						Dialog.alert(response.Message, function() {
							if (response.Status == 1) {
								DataGrid.loadData("dg1");
							}
						});
					});
		});
	}
	function edit() {
		var dt = DataGrid.getSelectedData("dg1");
		var drs = dt.Rows;
		if (!drs || drs.length == 0) {
			Dialog.alert("请先选择要修改的选项！");
			return;
		}
		if (drs.length > 1) {
			Dialog.alert("只能选择1条信息修改！");
			return;
		}
		//已关联活动的优惠券不允许更改
		var purpose=DataGrid.getSelectedValueByColumn("dg1","purpose");
		if(purpose=="活动发放"){
			Dialog.alert("用途为\"活动发放\"的优惠券不可以修改！");
			return;
		}
		//已关联活动的优惠券不允许更改
		var activitysn=DataGrid.getSelectedValueByColumn("dg1","activitysn");
		if(!(activitysn==""||activitysn==null)){
			Dialog.alert("已关联活动的优惠券不可以修改！");
			return;
		}
		//只有未使用状态的优惠券才可以更改
		var status=DataGrid.getSelectedValueByColumn("dg1","status");
		if(status!="未使用"){
			Dialog.alert("只有未使用状态的优惠券才可以修改！");
			return;
		}
		dr = drs[0];
		editDialog(dr);
	}
	function editDialog(dr) {
		var diag = new Dialog("Diag1");
		diag.Width = 1150;
		diag.Height = 500;
		diag.Title = "编辑优惠券";
		diag.URL = "Coupon/CouponEdit.jsp?ID=" + dr.get("ID");
		diag.onLoad = function() {
			if (dr.get("channelsn") == 'wap_cps_agent') {
				$DW.$("productTb").hide();
			}
			$DW.$("direction").focus();
		};
		diag.OKEvent = editSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "编辑优惠券";
		diag.Message = "编辑优惠券";
		diag.show();
	}
	function editSave() {
		//日期
		if (!checkDate()) {
			return;
		}
		//时间
		if (!checkTime()) {
			return;
		}
		//检查数字输入
		if (checkEditNum() == "false") {
			return;
		}
		var parValue = $DW.$V('parValue');
		var payAmount = $DW.$V('payAmount');
		var couponType = $DW.$V('couponType');
		if (couponType == '01') {
			if (parValue == null || parValue == '') {
				Dialog.alert("面值不能为空！");
				return ;
			}
			if(parseFloat(parValue)<=0){
				Dialog.alert("面值必须大于0！");
				return ;
			}
		} else {
			var discount = $DW.$V('prop4');
			if (discount == null || discount == '') {
				Dialog.alert("请选择折扣！");
				return ;
			}
		}
		
		if(parseInt(payAmount)<0){
			Dialog.alert("消费金额必须大于等于0！");
			return ;
		}
		//if(parseInt(payAmount)<=parseInt(parValue)){
			//Dialog.alert("消费金额必须大于面值！");
			//return ;
		//}
		var provideUserother = $DW.$V('provideUserother');
		if(provideUserother==""||provideUserother==null){
			Dialog.alert("发放人为必填项！");
			return ;
		}
		var dc = $DW.Form.getData("form3");
		//检查发放渠道是否必选
		var issuechannel = dc.map.issuechannel;
		if(issuechannel == null){
			Dialog.alert("发放渠道为必选项！");
			return ;
		}
		//检查应用渠道是否必选
		var channel = dc.map.channel;
		if(channel == null){
			Dialog.alert("应用渠道为必选项");
			return;
		}
		if ($DW.Verify.hasError()) {
			return;
		}
		Server.sendRequest("com.wangjin.coupon.CouponInfo.edit", dc,
				function() {
					var response = Server.getResponse();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
							$D.close();
						}
					});
				});
	}
	//展示浮动框
	function message_td(t){
		var innertext=t.innerHTML;
		if(innertext.indexOf("&nbsp;")!=-1){
			innertext=innertext.replace(new RegExp("&nbsp;", 'g'),"");
		}
		if(innertext==""||innertext==null){
			t.title="";
		}else{
			t.title=t.innerHTML;
		}
	}
	DataGrid.toExcel = function(ele,toExcelPageFlag,rows){
		//发放需要的优惠券参数
		var arr = DataGrid.getSelectedValue("dg1");
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		
		ele = $(ele);
		var diag = new Dialog("选择要导出的列","Framework/Controls/DataGridToExcelDialog.html",500,150);
		diag.OKEvent = function(){
			var columns = $DW.$N("Column");
			var columnIndexes = [],columnWidths = [];
			for(var i=0;i<columns.length;i++){
				if(columns[i].checked){
					columnIndexes.push(columns[i].value);
					columnWidths.push($(columns[i]).$A("columnWidth"));
				}
			}
			$D.close();
			 if(toExcelPageFlag){
				var Batch = $V("batch");
				dc.add("batchsn",Batch);
				//发放优惠券操作
				Server.sendRequest("com.wangjin.coupon.CouponInfo.provideExcelVali", dc,
						function(response) {
								if (response.Status != 0) {
									DataGrid.toExcelSubmit(ele,toExcelPageFlag,columnIndexes,columnWidths,rows);
									//发放优惠券操作
									Server.sendRequest("com.wangjin.coupon.CouponInfo.provideExcel", dc,
											function(response) {
												Dialog.alert(response.Message, function() {
													if (response.Status == 1) {
													}
												});
											});
								}else{
									Dialog.alert(response.Message, function() {
										
									});
								}
						});
			}else{
				dc.add("batchsn",'');
				//发放优惠券操作
				Server.sendRequest("com.wangjin.coupon.CouponInfo.provideExcel", dc,
						function(response) {
							Dialog.alert(response.Message, function() {
								if (response.Status == 1) {
									DataGrid.loadData("dg1");
									DataGrid.toExcelSubmit(ele,toExcelPageFlag,columnIndexes,columnWidths,rows);
								}
							});
						});
			} 
		}
		diag.addParam("DataGridID",ele.id);
		diag.show();
	}
	//根据column名来取得value值
	DataGrid.getSelectedValueByColumn = function(ele, column) {
		ele = $(ele);
		var ds = ele.DataSource;
		for ( var i = 0; i < ds.Columns.length; i++) {
			if (ds.Columns[i].Name == column.toLowerCase()) {
				for ( var k = 1; k < ele.rows.length; k++) {
					if (ele.rows[k].Selected) {
						return ds.Values[k - 1][i];
					}
				}
			}
		}
	}

	/**
	* 给审核人员发送邮件
	*/
	function sendApproveEmail(){
		var batchNo = jQuery("#batchNo").val();
		var approveEmail = jQuery("#approveEmail").val();
		if(batchNo == ''){
			Dialog.alert("请填写需要审核的优惠券批次号！");
			return;
		}
		if(approveEmail == ''){
			Dialog.alert("请填写要发送的审核人员邮箱地址！");
			return;
		}
		var dcSendApproveEmail = new DataCollection();
		dcSendApproveEmail.add("batchNo", batchNo);
		dcSendApproveEmail.add("approveEmail", approveEmail);
		var approveCCEmail = jQuery("#approveCCEmail").val();
		if(approveCCEmail != ''){
			dcSendApproveEmail.add("approveCCEmail", approveCCEmail);
		}
		Dialog.wait("正在发送邮件......");
		Server.sendRequest("com.wangjin.coupon.CouponInfo.sendApproveEmail", dcSendApproveEmail,
			function(response) {
				Dialog.endWait();
				Dialog.alert(response.Message);
			});
	}
</script>
<style>
#hotarea {
	width: 160px;
	height: 120px;
	border: #147 1px solid;
	background: #ca6 url(../Platform/Images/picture.jpg) no-repeat;
	position: relative
}

#hotarea  a {
	position: absolute;
	display: block;
	width: 35px;
	height: 25px;
	border: #fff 1px dashed;
	text-align: center;
	line-height: 24px;
	color: #fff;
}

#hotarea  a:hover {
	text-decoration: none;
	border: #fff 1px solid;
	color: #fff
}
</style>
</head>
<body>
<input type="hidden" id="CatalogID">
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" id="ConfigTable">
			<tr>
				<td valign="top">
				<fieldset><legend> <label>优惠劵</label> </legend> <z:init
					method="com.wangjin.coupon.CouponInfo.init">
					<form id="form1">
					<input type="hidden" value="${username}" id="username" name="username">
					<input type="hidden" value="${realname}" id="realname" name="realname">
					<table>
						<tr>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">优惠劵号：</td>
							<td><input value="" type="text" id="couponSn"
								name="couponSn" ztype="String" class="inputText" size="20"></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">优惠券类型：</td>
							<td><z:select id="prop3" style="width:117px">${couponTypeInit1}</z:select></td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">所属批次：</td>
							<td><z:select id="batch" style="width:117px">${batch}</z:select>
							</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">险&nbsp;&nbsp;&nbsp;种&nbsp;&nbsp;：&nbsp;</td>
							<td><z:select id="riskcodeSearch" style="width:117px">${riskcodeInit}</z:select></td>
							<td>&nbsp;&nbsp;&nbsp;</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">优惠公司：</td>
							<td><z:select id="insuranceCompanySearch" style="width:117px">${insuranceCompanyInit}</z:select></td>
						</tr>
					</table>
					<table>
						<tr>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">申请人&nbsp;&nbsp;：&nbsp;</td>
							<td><input value="" type="text" id="createuserSearch" name="createuserSearch"
								ztype="String" class="inputText" size="20"></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">发放人&nbsp;&nbsp;&nbsp;：</td>
							<td><input value="" type="text" id="provideUserSearch" name="provideUserSearch"
								ztype="String" class="inputText" size="20"></td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">&nbsp;&nbsp;&nbsp;发放渠道：</td>
							<td>
								<z:select id="issuechannel" style="width:117px">${issuechannelSelect}</z:select>
							</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">&nbsp;使用说明：</td>
							<td><input value="" type="text" id="directionSearch" name="directionSearch"
								ztype="String" class="inputText" size="20"></td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">&nbsp;应用渠道：</td>
							<td>
								<z:select id="channel" style="width:117px">${channelSelect}</z:select>
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">申请开始时间：</td>
							<td><input name="createStartDate" id="createStartDate"
								value="${yesterday}" type="text" size="14" ztype="Date" /></td>
							<td><input name="createStartTime" id="createStartTime"
								value="" type="text" size="14" ztype="Time" /></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">申请结束时间：</td>
							<td><input name="createEndDate" id="createEndDate"
								value="${today}" type="text" size="14" ztype="Date" /></td>
							<td><input name="createEndTime" id="createEndTime" value=""
								type="text" size="14" ztype="Time" /></td>
						</tr>
					</table>
					</form>
				</z:init></fieldset>
				<table>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
					 <z:tButtonPurview>${_Coupon_CouponApply_Button}</z:tButtonPurview>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="padding: 0px 5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">
					<tr>
						<td
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
						<z:datagrid id="dg1"
							method="com.wangjin.coupon.CouponInfo.dg1DataBindForApprove" size="12"
							autoFill="true" scroll="true" lazy="false" multiSelect="true" >
							<table width="100%" cellpadding="2" cellspacing="0"
								class="dataTable" afterdrag="afterRowDragEnd"
								style="table-layout: fixed" fixedHeight="240px">
								<tr ztype="head" class="dataTableHead">
									<td width="30" height="30" ztype="RowNo" drag="true"><img
										src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
									<td width="30" height="30" ztype="Selector" field="id">&nbsp;</td>
									<td width="250"><strong>优惠券号</strong></td>
									<td width="120"><strong>优惠券批次</strong></td>
									<td width="120"><strong>优惠券类型</strong></td>
									<td width="120"><strong>优惠险种</strong></td>
									<td width="120"><strong>优惠公司</strong></td>
									<td width="120"><strong>优惠产品</strong></td>
									<td width="50" ><strong>折扣</strong></td>
									<td width="50" ><strong>面值</strong></td>
									<td width="150"><strong>用途</strong></td>
									<td width="80"><strong>消费金额</strong></td>
									<td width="80"><strong>使用次数</strong></td>
									<td width="200"><strong>使用说明</strong></td>
									<td width="150"><strong>&nbsp;&nbsp;&nbsp;起始时间</strong></td>
									<td width="150"><strong>&nbsp;&nbsp;&nbsp;终止时间</strong></td>
									<td width="50"><strong>状态</strong></td>
									<td width="120"><strong>使用单号</strong></td>
									<td width="150"><strong>优惠券使用时间</strong></td>
									<td width="150"><strong>所属会员</strong></td>
									<td width="150"><strong>所属邮箱</strong></td>
									<td width="100"><strong>所属手机</strong></td>
									<td width="80"><strong>优惠券申请人</strong></td>
									<td width="150"><strong>&nbsp;&nbsp;&nbsp;申请时间</strong></td>
									<td width="150"><strong>&nbsp;&nbsp;&nbsp;发放人</strong></td>
									<td width="150"><strong>&nbsp;&nbsp;&nbsp;实际发放人</strong></td>
									<td width="150"><strong>活动号</strong></td>
								</tr>
								<tr onDblClick="edit(${id});"
									style="background-color: #F9FBFC">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td onmouseover="message_td(this)" style="text-align:left;">${couponSn}</td>
									<td  onmouseover="message_td(this)" style="text-align:left;">${batch}</td>
									<td  onmouseover="message_td(this)" style="text-align:left;">${couponType}</td>
									<td  onmouseover="message_td(this)" style="text-align:left;">${riskCode}</td>
									<td  onmouseover="message_td(this)" style="text-align:left;">${insuranceCompany}</td>
									<td  onmouseover="message_td(this)" style="text-align:left;">${productName}</td>
									<td  onmouseover="message_td(this)" >${prop4}</td>
									<td  onmouseover="message_td(this)" >${parValue}</td>
									<td  onmouseover="message_td(this)" style="text-align:left;">${purpose}</td>
									<td  onmouseover="message_td(this)" style="text-align:right;">${payAmount}</td>
									<td  onmouseover="message_td(this)" style="text-align:center;">${useTimes}</td>
									<td  onmouseover="message_td(this)" style="text-align:left;">${direction}</td>
									<td  onmouseover="message_td(this)" style="text-align:center;">${startTime}</td>
									<td  onmouseover="message_td(this)" style="text-align:center;">${endTime}</td>
									<td  onmouseover="message_td(this)" style="text-align:center;">${status}</td>
									<td  onmouseover="message_td(this)" style="text-align:left;">${orderSn}</td>
									<td  onmouseover="message_td(this)" style="text-align:center;">${payTime}</td>
									<td  onmouseover="message_td(this)" style="text-align:center;">${memberId}</td>
									<td  onmouseover="message_td(this)" style="text-align:center;">${mail}</td>
									<td  onmouseover="message_td(this)" style="text-align:center;">${mobile}</td>
									<td  onmouseover="message_td(this)" style="text-align:right;">${createUser}</td>
									<td  onmouseover="message_td(this)" style="text-align:center;">${createDate}</td>
									<td  onmouseover="message_td(this)" style="text-align:center;">${provideUser}</td>
									<td  onmouseover="message_td(this)" style="text-align:center;">${realProvideUser}</td>
									<td  onmouseover="message_td(this)" style="text-align:center;">${activitysn}</td>
								</tr>
								<tr ztype="pagebar">
									<td colspan="9" align="left">${PageBar}</td>
								</tr>
							</table>
						</z:datagrid></td>
					</tr>
				</table>
				</td>
				<tr>
			</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<div style="margin-left:20px">
	批次号:<input type="text" id="batchNo"/>&nbsp;&nbsp; 
	审核人邮箱:<input type="text" id="approveEmail" value="liuyifeng@kaixinbao.com" style="width:200px" />&nbsp;&nbsp;
	抄送人邮箱:<input type="text" id="approveCCEmail" value="yangxiaoqing@kaixinbao.com" style="width:200px" />&nbsp;&nbsp;
	<input type="button" value="发送审核人员" onclick="sendApproveEmail()"/>
</div>
</body>
</html>
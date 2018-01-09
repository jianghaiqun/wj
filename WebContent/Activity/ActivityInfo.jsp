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
		DataGrid.setParam("dg1", "activitysn", $V("activitysn"));
		DataGrid.setParam("dg1", "title", $V("title"));
		DataGrid.setParam("dg1", "status", $V("status"));
		DataGrid.setParam("dg1", "createuser", $V("createuser"));
		DataGrid.setParam("dg1", "description", $V("description"));
		DataGrid.setParam("dg1", "riskcodeSearch", $V("riskcodeSearch"));
		DataGrid.setParam("dg1", "insuranceCompanySearch", $V("insuranceCompanySearch"));
		DataGrid.setParam("dg1", "startDate", $V("startDate"));
		DataGrid.setParam("dg1", "startTime", $V("startTime"));
		DataGrid.setParam("dg1", "endDate", $V("endDate"));
		DataGrid.setParam("dg1", "endTime", $V("endTime"));
		DataGrid.setParam("dg1", "createStartDate", $V("createStartDate"));
		DataGrid.setParam("dg1", "createStartTime", $V("createStartTime"));
		DataGrid.setParam("dg1", "createEndDate", $V("createEndDate"));
		DataGrid.setParam("dg1", "createEndTime", $V("createEndTime"));
		DataGrid.loadData("dg1");
	}
/**
 * 新建活动
 */
	function addActivity() {
		var diag = new Dialog("Diag1");
		diag.Width = 1150;
		diag.Height = 450;
		diag.Title = "新建活动";
		diag.URL = "Activity/ActivityAdd.jsp";
		diag.onLoad = function() {
		};
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "新建活动";
		diag.show();
	}
/**
 * 新建活动的保存
 */
	function addSave() {
		//日期
		if (!checkDate()) {
			return;
		}
		//时间
		if (!checkTime()) {
			return;
		}
		//活动类型为非“注册送”时，公司和险种必须有一个不为空
		var insuranceCompanyother = $DW.$V("insuranceCompanyother");
		var riskCodeother = $DW.$V("riskCodeother");
		var productother = $DW.$V("productother");
		var type = $DW.$V("type");
		var ActivityChannel = $DW.$V("ActivityChannel");
		if (ActivityChannel == 'tb') {
			if (productother=='' || productother==null) {
				Dialog.alert("请选择活动产品！");
				return;
			}
		} else {
			if(insuranceCompanyother==''&&riskCodeother==''&&type!=0&&type!=4&&type!=5&&type!=11&&productother==''){
				Dialog.alert("当没有选择活动产品时,活动公司和活动险种不能同时为空！");
				return;
			}
		}

		if((insuranceCompanyother!=''|| riskCodeother!='')&&type!=0&&type!=4&&type!=5&&productother!=''){
			Dialog.alert("活动产品和活动公司或活动险种不能同时选择！");
			return;
		}
		//当自动获取优惠券为“否”时，优惠券数量应大于0
		var autoCreate = $DW.$V("autoCreate");
		if(autoCreate=='1'){
			var couponnum = $DW.$V("couponnum");
			if(couponnum==""||parseInt(couponnum)<=0){
				Dialog.alert("优惠券数量应大于0！");
				return;
			}
		}
		//当活动类型为非“注册送优惠券”时，优惠券金额为必填项
		var type = $DW.$V("type");
		var payAmount = $DW.$V("payAmount_0");
		var prop1 = $DW.$V("prop1_0");
		if(type=="1"||type=="3"){
			if(payAmount==""||payAmount==null){
				Dialog.alert("消费金额为必填项！");
				return;
			}
		}
		if(type=="1"||type=="2"){
			var autoCreate = $DW.$V("autoCreate");
			if(autoCreate==""||autoCreate==null){
				Dialog.alert("自动获取优惠券为必填项！");
				return;
			}
		}
		if(type=="3"){
			var prop1 = $DW.$V("prop1_0");
			if(prop1==""||prop1==null){
				Dialog.alert("优惠金额为必填项！");
				return;
			}
			if(parseInt(payAmount)<=parseInt(prop1)){
				Dialog.alert("优惠金额必须小于消费金额！");
				return;
			}
		}
		if(type=="0"||type=="1"||type=="2"||type=="4"||type=="5"){
			var batch = $DW.$V("batch");
			if(batch==""||batch==null){
				Dialog.alert("优惠券批次为必选项！");
				return;
			}
		}
		if (type=="11") {
			var batch1 = $DW.$V("batch_1");
			var batch2;
			var batch3;
			if(batch1==""||batch1==null){
				Dialog.alert("优惠券批次为必选项！");
				return;
			}

			if($DW.$("batch_2")){
                batch2 = $DW.$V("batch_2");
				if(batch2==""||batch2==null){
					Dialog.alert("优惠券批次为必选项！");
					return;
				} else if (batch1 == batch2) {
                    Dialog.alert("输入的优惠券批次号不可以重复！");
                    return;
				}
			}

			if($DW.$("batch_3")){
                batch3 = $DW.$V("batch_3");
				if(batch3==""||batch3==null){
					Dialog.alert("优惠券批次为必选项！");
					return;
				} else if (batch1 == batch3) {
                    Dialog.alert("输入的优惠券批次号不可以重复！");
                    return;
                }
			}

            if($DW.$("batch_2") && $DW.$("batch_3")){
                if (batch2 == batch3) {
                    Dialog.alert("输入的优惠券批次号不可以重复！");
                    return;
                }
            }

            autoCreate = $DW.$V("autoCreate1");
            if(autoCreate==""||autoCreate==null){
                Dialog.alert("自动获取优惠券为必填项！");
                return;
            }

		}
		//消费金额应大于0
		var payAmount = $DW.$V("payAmount_0");
		if((payAmount==""||parseInt(payAmount)<=0)&&(type=="1"||type=="3")){
			Dialog.alert("消费金额应大于0！");
			return;
		}
		//优惠金额应大于0
		var prop1 = $DW.$V("prop1_0");
		if((prop1==""||parseInt(prop1)<=0)&&type=="3"){
			Dialog.alert("优惠金额应大于0！");
			return;
		}
		var dc = $DW.Form.getData("form2");
		if ($DW.Verify.hasError()) {
			return;
		}

		Dialog.confirm("确认要生成活动？",function() {
			Dialog.wait("正在生成活动......");
			Server.sendRequest("com.wangjin.activity.ActivityInfo.add", dc, function() {
				Dialog.closeEx();
				var response = Server.getResponse();
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						$D.close();
						DataGrid.loadData("dg1");
					}
				});
			});
		});
	}

	function addTBActivity() {
		var diag = new Dialog("Diag1");
		diag.Width = 850;
		diag.Height = 350;
		diag.Title = "新建活动";
		diag.URL = "Activity/ActivityAdd.jsp?ActivityChannel=tb";
		diag.onLoad = function() {
		};
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "新建淘宝活动";
		diag.show();
	}

	function checkDate() {
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
		var startDate = $DW.$V("startDate");
		var endDate = $DW.$V("endDate");
		var a = new Date((startDate+" "+startTime).replace(/-/g,"/"));
		var b = new Date((endDate+" "+endTime).replace(/-/g,"/"));
		var startTime = $DW.$V("startTime");
		var endTime = $DW.$V("endTime");
		if (startDate == endDate) {
			if (endTime != "" && startTime == "") {
				Dialog.alert("您还没有选择开始时间！");
				return false;
			} else if (startTime != "" && endTime == "") {
				Dialog.alert("您还没有选择结束时间！");
				return false;
			} else if (endTime != "" && startTime != "" &&(a>b)) {
				Dialog.alert("开始时间大于结束时间！");
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
	//活动删除
	function del() {
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要删除的活动！");
			return;
		}
		//只有待发布状态的活动才可以删除
		var status=DataGrid.getSelectedValueByColumn("dg1","status");
		if(status!="待发布"){
			Dialog.alert("只有待发布状态的活动才可以删除！");
			return;
		}
		Dialog.confirm("删除后不可恢复，确认要删除？", function() {
			var dc = new DataCollection();
			dc.add("IDs", arr.join());
			Server.sendRequest("com.wangjin.activity.ActivityInfo.delete", dc,
					function(response) {
						Dialog.alert(response.Message, function() {
							if (response.Status == 1) {
								DataGrid.loadData("dg1");
							}
						});
					});
		});
	}
	//活动发布
	function provide() {
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要发布的活动！");
			return;
		}
		//获得勾选的固定列的值
		var valueByColumn=DataGrid.getSelectedValueByColumn("dg1","status");
		if(valueByColumn!="待发布"){
			Dialog.alert("只有\"待发布\"状态的活动才可以发布！");
			return;
		}
		Dialog.confirm("发布后的活动只可以暂停，确认发布？", function() {
			Dialog.wait("正在发布活动......");
			var dc = new DataCollection();
			dc.add("IDs", arr.join());
			Server.sendRequest("com.wangjin.activity.ActivityInfo.provide", dc,
					function(response) {
					Dialog.closeEx();
						Dialog.alert(response.Message, function() {
							if (response.Status == 1) {
								$D.close();
								DataGrid.loadData("dg1");
							}
						});
					});
		});
	}
	//更新活动产品
	function updateproduct() {
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要更新的活动！");
			return;
		}
		Dialog.confirm("确定将最新的产品更新到该活动么？", function() {
			var dc = new DataCollection();
			dc.add("IDs", arr.join());
			Server.sendRequest("com.wangjin.activity.ActivityInfo.updateproduct", dc,
					function(response) {
						Dialog.alert(response.Message, function() {
							if (response.Status == 1) {
								DataGrid.loadData("dg1");
								$D.close();
							}
						});
					});
		});
	}
	function provideSave() {
		var arr = DataGrid.getSelectedValue("dg1");
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Server.sendRequest("com.wangjin.activity.ActivityInfo.provide", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
							$D.close();
						}
					});
				});
	}
	//活动暂停
	function frozen() {
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要暂停的行！");
			return;
		}
		Dialog.confirm("确认要暂停么？", function() {
			Dialog.wait("正在暂停活动......");
			var dc = new DataCollection();
			dc.add("IDs", arr.join());
			Server.sendRequest("com.wangjin.activity.ActivityInfo.frozen", dc, function(
					response) {
				Dialog.closeEx();
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						$D.close();
						DataGrid.loadData("dg1");
					}
				});
			});
		});
	}
	//活动恢复
	function unFrozen() {
		var arr = DataGrid.getSelectedValue("dg1");
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要恢复的行！");
			return;
		}
		//获得勾选的固定列的值
		var valueByColumn=DataGrid.getSelectedValueByColumn("dg1","status");
		if(valueByColumn!="已暂停"){
			Dialog.alert("只有\"已暂停\"状态的活动才可以恢复！");
			return;
		}
		Dialog.confirm("确认要恢复么？", function() {
			Dialog.wait("正在恢复活动......");
			var dc = new DataCollection();
			dc.add("IDs", arr.join());
			Server.sendRequest("com.wangjin.activity.ActivityInfo.unFrozen", dc, function(
					response) {
				Dialog.closeEx();
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						$D.close();
						DataGrid.loadData("dg1");
					}
				});
			});
		});
	}
	function detail() {
		var dt = DataGrid.getSelectedData("dg1");
		var drs = dt.Rows;
		if (!drs || drs.length == 0) {
			Dialog.alert("请先选择要查看的活动！");
			return;
		}
		if (drs.length > 1) {
			Dialog.alert("只能选择1条活动查看！");
			return;
		}
		dr = drs[0];
		detailDialog(dr);
	}
	function detailDialog(dr) {
		var diag = new Dialog("Diag1");
		diag.Width = 1150;
		diag.Height = 450;
		diag.Title = "查看活动";
		diag.URL = "Activity/ActivityDetail.jsp?ID=" + dr.get("ID");
		diag.ShowMessageRow = true;
		diag.MessageTitle = "查看活动";
		diag.Message = "查看活动";
		diag.show();
	}
	function edit() {
		var dt = DataGrid.getSelectedData("dg1");
		var drs = dt.Rows;
		if (!drs || drs.length == 0) {
			Dialog.alert("请先选择要修改的活动！");
			return;
		}
		if (drs.length > 1) {
			Dialog.alert("只能选择1条信息修改！");
			return;
		}
		//只有待发布状态的活动才可以更改
		var status=DataGrid.getSelectedValueByColumn("dg1","status");
		if(status!="待发布"){
			Dialog.alert("只有待发布状态的活动才可以修改！");
			return;
		}
		dr = drs[0];
		editDialog(dr);
	}
	function editDialog(dr) {
		var diag = new Dialog("Diag1");
		diag.Width = 1150;
		diag.Height = 450;
		diag.Title = "编辑活动";
		diag.URL = "Activity/ActivityEdit.jsp?ID=" + dr.get("ID");
		diag.onLoad = function() {
			$DW.$("title").focus();
		};
		diag.OKEvent = editSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "编辑活动";
		diag.Message = "编辑活动";
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
		//活动类型为非“注册送”时，公司和险种必须有一个不为空
		var insuranceCompanyother = $DW.$V("insuranceCompanyother");
		var riskCodeother = $DW.$V("riskCodeother");
		var productother = $DW.$V("productother");
		var type = $DW.$V("type");
		var ActivityChannel = $DW.$V("ActivityChannel");
		if (ActivityChannel == 'tb') {
			if (productother=='' || productother==null) {
				Dialog.alert("请选择活动产品！");
				return;
			}
		} else {
			if(insuranceCompanyother==''&&riskCodeother==''&&type!=0&&type!=4&&type!=5&&type!=11&&productother==''){
				Dialog.alert("当没有选择活动产品时,活动公司和活动险种不能同时为空！");
				return;
			}
		}
		if((insuranceCompanyother!=''|| riskCodeother!='')&&type!=0&&type!=4&&type!=5&&productother!=''){
			Dialog.alert("活动产品和活动公司或活动险种不能同时选择！");
			return;
		}
		//当自动获取优惠券为“否”时，优惠券数量应大于0
		var autoCreate = $DW.$V("autoCreate");
		if(autoCreate=='1'){
			var couponnum = $DW.$V("couponnum");
			if(couponnum==""||parseInt(couponnum)<=0){
				Dialog.alert("优惠券数量应大于0！");
				return;
			}
		}
		//当活动类型为非“注册送优惠券”时，优惠券金额为必填项
		var type = $DW.$V("type");
		var payAmount = $DW.$V("payAmount_0");
		if(type=="1"||type=="3"){
			if(payAmount==""||payAmount==null){
				Dialog.alert("消费金额为必填项！");
				return;
			}
		}
		if(type=="1"||type=="2"){
			var autoCreate = $DW.$V("autoCreate");
			if(autoCreate==""||autoCreate==null){
				Dialog.alert("自动获取优惠券为必填项！");
				return;
			}
		}
		if(type=="3"){
			var prop1 = $DW.$V("prop1_0");
			if(prop1==""||prop1==null){
				Dialog.alert("优惠金额为必填项！");
				return;
			}
			if(parseInt(payAmount)<=parseInt(prop1)){
				Dialog.alert("优惠金额必须小于消费金额！");
				return;
			}
		}
		if(type=="0"||type=="1"||type=="2"||type=="4"||type=="5"){
			var batch = $DW.$V("batch");
			if(batch==""||batch==null){
				Dialog.alert("优惠券批次为必选项！");
				return;
			}
		}
		//消费金额应大于0
		var payAmount = $DW.$V("payAmount_0");
		if((payAmount==""||parseInt(payAmount)<=0)&&(type=="1"||type=="3")){
			Dialog.alert("消费金额应大于0！");
			return;
		}
		//优惠金额应大于0
		var prop1 = $DW.$V("prop1_0");
		if((prop1==""||parseInt(prop1)<=0)&&type=="3"){
			Dialog.alert("优惠金额应大于0！");
			return;
		}
		var dc = $DW.Form.getData("form3");
		if ($DW.Verify.hasError()) {
			return;
		}
		Dialog.confirm("确认要保存活动？",function() {
			Dialog.wait("正在保存活动信息......");
			Server.sendRequest("com.wangjin.activity.ActivityInfo.edit", dc,function() {
					Dialog.closeEx();
					var response = Server.getResponse();
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							$D.close();
							DataGrid.loadData("dg1");
						}
					});
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

    function imgUpload() {
        var dt = DataGrid.getSelectedData("dg1");
        var drs = dt.Rows;
        var type=DataGrid.getSelectedValueByColumn("dg1","type");
        if(!drs||drs.length==0||type!="推荐有礼"){
            Dialog.alert("只有推荐有礼活动才可以上传图片！");
            return;
        }
        dr = drs[0];

        var diag = new Dialog("Diag1");
        diag.Width = 700;
        diag.Height = 400;
        diag.Title = "图片管理";
        diag.URL = "Activity/ActivityImgUploadDialog.jsp?activitySn="+dr.get("activitysn");

        diag.show();
        diag.OKButton.hide();
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
				<fieldset><legend> <label>营销活动</label> </legend> <z:init
					method="com.wangjin.activity.ActivityInfo.init">
					<form id="form1" >
					<input type="hidden" value="${username}" id="username" name="username">
					<input type="hidden" value="${realname}" id="realname" name="realname">
					<table>
						<tr>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">活动编码：</td>
							<td><input value="" type="text" id="activitysn"
								name="activitysn" ztype="String" class="inputText" size="20"></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">活动名称：</td>
							<td><input value="" type="text" id="title" name="title"
								ztype="String" class="inputText" size="20"></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">&nbsp;&nbsp;&nbsp;&nbsp;状态：</td>
							<td><z:select id="status" style="width:117px">${statusInit}</z:select></td>
						</tr>
					</table>
					<table>
						<tr>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">创建人&nbsp;&nbsp;：&nbsp;</td>
							<td><input value="" type="text" id="createuser" name="createuser"
								ztype="String" class="inputText" size="20"></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">活动描述：</td>
							<td><input value="" type="text" id="description" name="description"
								ztype="String" class="inputText" size="20"></td>
							<td></td>
							<td></td>
						</tr>
					</table>
					<table>
						<tr>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">险&nbsp;&nbsp;&nbsp;种&nbsp;&nbsp;：&nbsp;</td>
							<td><z:select id="riskcodeSearch" style="width:117px">${riskcodeInit}</z:select></td>
							<td>&nbsp;&nbsp;&nbsp;</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">优惠公司：</td>
							<td><z:select id="insuranceCompanySearch" style="width:117px">${insuranceCompanyInit}</z:select></td>
							<td ></td>
							<td></td>
						</tr>
					</table>
					<table>
						<tr>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">活动开始时间：</td>
							<td><input name="startDate" id="startDate" value=""
								type="text" size="14" ztype="Date" /></td>
							<td><input name="startTime" id="startTime" value=""
								type="text" size="14" ztype="Time" /></td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td height="35" align="right" bordercolor="#eeeeee"
								class="tdgrey1">活动结束时间：</td>
							<td><input name="endDate" id="endDate" value=""
								type="text" size="14" ztype="Date" /></td>
							<td><input name="endTime" id="endTime" value=""
								type="text" size="14" ztype="Time" /></td>
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
					 <z:tButtonPurview>${_Activity_ActivityInfo_Button}</z:tButtonPurview>
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
							method="com.wangjin.activity.ActivityInfo.dg1DataBind" size="10"
							autoFill="true" scroll="true" lazy="false" multiSelect="true">
							<table width="300%" cellpadding="2" cellspacing="0"
								class="dataTable" afterdrag="afterRowDragEnd"
								style="table-layout: fixed" fixedHeight="343px">
								<tr ztype="head" class="dataTableHead">
									<td width="30" height="30" ztype="RowNo" drag="true"><img
										src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
									<td width="30" height="30" ztype="Selector" field="id">&nbsp;</td>
									<td ><strong>活动编码</strong></td>
									<td ><strong>活动名称</strong></td>
									<td ><strong>活动简述</strong></td>
									<td ><strong>活动描述</strong></td>
									<td ><strong>优惠券数量</strong></td>
									<td ><strong>优惠券批次</strong></td>
									<td ><strong>活动类型</strong></td>
									<td ><strong>活动险种</strong></td>
									<td ><strong>活动公司</strong></td>
									<td ><strong>活动产品</strong></td>
									<td ><strong>消费金额</strong></td>
									<td ><strong>优惠金额(折扣率或积分倍数)</strong></td>
									<td ><strong>累计</strong></td>
									<td ><strong>起始时间</strong></td>
									<td ><strong>终止时间</strong></td>
									<td ><strong>是否累加</strong></td>
									<td ><strong>活动状态</strong></td>
									<td ><strong>自动生成</strong></td>
									<td ><strong>申请人</strong></td>
									<td ><strong>申请时间</strong></td>
									<td ><strong>修改人</strong></td>
									<td ><strong>修改时间</strong></td>

								</tr>
								<tr onDblClick="edit(${id});"
									style="background-color: #F9FBFC">
									<td onmouseover="">&nbsp;</td>
									<td>&nbsp;</td>
									<td onmouseover="message_td(this)">${activitysn}</td>
									<td onmouseover="message_td(this)">${title}</td>
									<td onmouseover="message_td(this)">${prop2}</td>
									<td onmouseover="message_td(this)">${description}</td>
									<td onmouseover="message_td(this)">${couponnum}</td>
									<td onmouseover="message_td(this)">${batch}</td>
									<td onmouseover="message_td(this)">${type}</td>
									<td onmouseover="message_td(this)">${riskcode}</td>
									<td onmouseover="message_td(this)">${insurancecompany}</td>
									<td onmouseover="message_td(this)">${product}</td>
									<td onmouseover="message_td(this)">${payamount}</td>
									<td onmouseover="message_td(this)">${prop1}</td>
									<td onmouseover="message_td(this)">${accumulation}</td>
									<td onmouseover="message_td(this)">${starttime}</td>
									<td onmouseover="message_td(this)">${endtime}</td>
									<td onmouseover="message_td(this)">${accumulation}</td>
									<td onmouseover="message_td(this)">${status}</td>
									<td onmouseover="message_td(this)">${autocreate}</td>
									<td onmouseover="message_td(this)">${createuser}</td>
									<td onmouseover="message_td(this)">${createtime}</td>
									<td onmouseover="message_td(this)">${modifyuser}</td>
									<td onmouseover="message_td(this)">${modifytime}</td>
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
</body>
</html>
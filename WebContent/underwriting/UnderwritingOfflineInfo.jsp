<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>线下核保信息列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript">

function sear(index) {
	var createStartDate = $V("createStartDate");
	var createEndDate = $V("createEndDate");
	if (createStartDate != null && createStartDate != '' && createEndDate != null && createEndDate != '') {
		if (createStartDate>createEndDate) {
			Dialog.alert("申请开始时间不能大于结束时间！");
			return;
		}
	}
	if (index=='_ZVING_PAGEINDEX') {
		index = 0;
	}
	DataGrid.setParam("dg1", Constant.PageIndex, index);
	DataGrid.setParam("dg1", "name", $V("name"));
	DataGrid.setParam("dg1", "mobile", $V("mobile"));
	DataGrid.setParam("dg1", "email", $V("email"));
	DataGrid.setParam("dg1", "applicantIdentityId", $V("applicantIdentityId"));
	DataGrid.setParam("dg1", "productName", $V("productName"));
	DataGrid.setParam("dg1", "dealStatus", $V("dealStatus"));
	DataGrid.setParam("dg1", "createStartDate", createStartDate);
	DataGrid.setParam("dg1", "createEndDate", createEndDate);
	DataGrid.setParam("dg1","contant",$NV("contant"));
	DataGrid.loadData("dg1");
	
}
/*
* 审核处理
*/
function Auditing(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要审核的申请信息！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能审核一个申请信息！");
		return;
	}
	dr = drs[0];
	var dealStatus = dr.get("deal_status");
	if (dealStatus == '3') {
		Dialog.alert("该申请信息已作废，不能进行审核操作！");
		return;
	}
	
	var diag = new Dialog("Diag1");
	diag.Width = 750;
	diag.Height = 570;
	diag.Title = "审核处理";
	if (dr.get("productId") == '224801001') {
		diag.URL = "underwriting/UnderwritingOfflineInfoAuditing_pdf.jsp?infoID=" + dr.get("id");
//		diag.OKEvent = createPdfFile;
		diag.show();
		diag.OKButton.hide();
//		diag.addButton("appendinsuranceCompany","生成PDF并下载",downloadPdf);
//		diag.OKButton.value = "发送邮件给保险公司";
	} else {
		diag.URL = "underwriting/UnderwritingOfflineInfoAuditing.jsp?infoID=" + dr.get("id");
		diag.OKEvent = sendEmailToCompany;
		diag.show();
		diag.OKButton.value = "发送邮件给保险公司";
	}
}

function downloadPdf(){
    var dt = DataGrid.getSelectedData("dg1");
    var dr = dt.Rows[0];
	var download="Y";
	var dc = new DataCollection();
	dc.add("infoID",$DW.$V("infoID"));
	dc.add("download",download);
    dc.add("productName",dr.get("productName"));
	Server.sendRequest("com.wangjin.underwriting.UnderwritingOfflineInfo.createPdfFile",dc,function(response){
		if(response.Status==1){
			window.open("../Payment/download.jsp?FilePath="+response.Message );
		}else{
			Dialog.alert("下载失败");
		}

	});
}
 

function createPdfFile() {
    var dt = DataGrid.getSelectedData("dg1");
    var dr = dt.Rows[0];
	var download="N";
	var dc = new DataCollection();
	dc.add("infoID",$DW.$V("infoID"));
	dc.add("download",download);
    dc.add("productName",dr.get("productName"));
	Server.sendRequest("com.wangjin.underwriting.UnderwritingOfflineInfo.createPdfFile",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();sear(Constant.PageIndex);
			}
		});
	});
}

function sendEmailToCompany(){
    var dt = DataGrid.getSelectedData("dg1");
    var dr = dt.Rows[0];

	var arr = $DW.$NV("ImageID");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要发送的图片！");
		return;
	}
	imageID = arr.join();
	count=arr.length;
	var companyEmail = $DW.$V("companyEmail")
	if (companyEmail == null || companyEmail == '') {
		Dialog.alert("请填写保险公司邮箱！");
		return;
	}
	Dialog.confirm("你确认发送这"+count+"张图片吗？",function(){
		var dc = new DataCollection();
        dc.add("productName",dr.get("productName"));
		dc.add("IDs",imageID);
		dc.add("companyEmail",companyEmail);
		dc.add("remark",$DW.$V("remark"));
		dc.add("infoID",$DW.$V("infoID"));
		Server.sendRequest("com.wangjin.underwriting.UnderwritingOfflineInfo.sendEmailToCompany",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					$D.close();
					sear(Constant.PageIndex);
				}
			});
		});
	});
}
/*
* 完结处理
*/
function dealOver(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要审核的申请信息！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能审核一个申请信息！");
		return;
	}
	dr = drs[0];
	var dealStatus = dr.get("deal_status");
	if (dealStatus == '3') {
		Dialog.alert("该申请信息已作废，不能进行完结操作！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 750;
	diag.Height = 570;
	diag.Title = "完结处理";
	diag.URL = "underwriting/UnderwritingOfflineInfoFinished.jsp?infoID=" + dr.get("id");
	diag.OKEvent = sendUWResult;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "完结处理";
	diag.show();
	diag.OKButton.value = "给客户发送邮件";
}
function sendUWResult() {
	var dc = $DW.Form.getData("form2");
	
	var UnderWritingStatus = dc.get("UnderWritingStatus");
	if (UnderWritingStatus==null||UnderWritingStatus=='') {
		Dialog.alert("请选择核保结果");
		return;
	}
	
	var metaDescription= $DW.getMetaDescription();
	if(metaDescription==null||metaDescription==''){
		Dialog.alert("请填写邮件内容");
		return;
	}
	dc.add("metaDescription",metaDescription);
	
	Server.sendRequest("com.wangjin.underwriting.UnderwritingOfflineInfo.sendUWResult",dc,function(response){
		Dialog.alert(response.Message,function(){
					if(response.Status==1){
						$D.close();sear(Constant.PageIndex);
					}
				});
		
	});
}
//保全记录
function baoquanedit(orderSn, id) {
	var arr = DataGrid.getSelectedData("dg1");
	if (!arr || arr.getRowCount() == 0) {
		Dialog.alert("请先选择要处理的条目！");
		return;
	}
	if (!arr || arr.getRowCount() >= 2) {
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	if (id == null || id == '') {
		id = arr.get(0, "id");
	}
	var d = new Dialog("Diag1");
	d.Width = 550;
	d.Height = 450;
	d.Title = "保全记录";
	d.URL = "DataService/PaymentBaoquanEdit.jsp?prop1=offline_" + id;
	d.ShowMessageRow = true;
	d.MessageTitle = "保全记录 ";
	d.show();
	d.OKButton.hide();
	d.CancelButton.value = "关闭";
}

//作废
function invalid(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要作废的信息！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能作废一条信息！");
		return;
	}
	dr = drs[0];
	var dc = new DataCollection();
	
	dc.add("id",dr.get("id"));
	var dealStatus= dr.get("deal_status");
	var underwritingStatus= dr.get("underwritingStatus");
	
	if(!(dealStatus=="0" && underwritingStatus=="")){
		Dialog.alert("该申请信息不满足作废条件，不能作废！")
		return;
	}
	dc.add("dealStatus",dealStatus);
	dc.add("underwritingStatus",underwritingStatus);
	Dialog.confirm("作废后不可恢复，您确认要作废该申请吗？",function(){
		Server.sendRequest("com.wangjin.underwriting.UnderwritingOfflineInfo.invalid",dc,function(response){
			if(response.Status==1){
				Dialog.alert(response.Message);
				sear(Constant.PageIndex);
			}else{
				Dialog.alert(response.Message);
			}
		});
	});
}

	// 批量下载核保信息，类型是图片，目前只适用于百年康惠保
	function batchDownload(){
        var dt = DataGrid.getSelectedData("dg1");

        var drs = dt.Rows;
        if (!drs || drs.length == 0) {
            Dialog.alert("请先选择要下载的核保信息！");
            return;
        }

        var dc = new DataCollection();
        dc.add("tableData", dt);
        Dialog.wait("正在批量打包中...");
        Server.sendRequest("com.wangjin.underwriting.UnderwritingOfflineInfo.batchDownload",dc,function(response){
            Dialog.closeEx();
            if(response.Status==1){
                window.open("../Payment/download.jsp?FilePath="+response.Message );
            } else {
                Dialog.alert(response.Message);
			}
        });
	}

</script>
</head>
<body>

	<div id="StaffStat">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
		<td>
		<table width="20" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding: 6px;" class="blockTd"><z:tree id="tree1"
					style="height:440px;width:120px;"
					method="com.sinosoft.platform.Channel.treeDataBind"
					level="3" lazy="true" resizeable="true">
					<img src="../Framework/Images/icon_drag.gif" width="16" height="16">
					<p cid='${ID}' level="${TreeLevel}"><input type="checkbox"
						name="contant" id='contant_${ID}' value='${ChannelCode}'
						onClick="onCheck(this);"><label for="contant_${ID}"><span id="span_${ID}">${Name}</span></label></p>
				</z:tree></td>
			</tr>
		</table>
		
		</td>
			<td>
			
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<z:init method="com.wangjin.underwriting.UnderwritingOfflineInfo.init">
		<tr valign="top">
			<td>
			<table width="60%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
				<tr>
					<td width="60px">姓名：</td>
					<td><input value="" type="text" id="name" name="name" ztype="String" class="inputText" size="20" /></td>
					<td>手机号：</td>
					<td><input value="" type="text" id="mobile" name="mobile" ztype="String" class="inputText" size="20" /></td>
					<td>邮箱：</td>
					<td><input value="" type="text" id="email" name="email" ztype="String" class="inputText" size="30" /></td>
					<td>证件号：</td>
					<td><input value="" type="text" id=applicantIdentityId name="applicantIdentityId" ztype="String" class="inputText" size="30" /></td>
				</tr>
				<tr>
					<td>产品名称：</td>
					<td><input value="" type="text" id="productName" name="productName" ztype="String" class="inputText" size="20" /></td>
					<td>处理状态：</td>
					<td><z:select id="dealStatus" name="dealStatus" style="width:117px">${dealStatus}</z:select></td>
					<td>申请时间：</td>
					<td colspan="3"><input name="createStartDate" id="createStartDate" value="" type="text" size="14" ztype="Date" />&nbsp;&nbsp;至&nbsp;&nbsp;
						<input name="createEndDate" id="createEndDate" value="" type="text" size="14" ztype="Date" />
					</td>
				</tr>
			 </table>
			</td>
		</tr>
		</z:init>
				<tr>
					<td><z:tButtonPurview>${_underwriting_UnderwritingOfflineInfo_Button}</z:tButtonPurview></td>
				</tr>
		<tr>
				<td style="padding: 0px 0px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.underwriting.UnderwritingOfflineInfo.dg1DataBind" size="15" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="370px">
							<tr ztype="head" class="dataTableHead">
								<td width="40" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
                                 <td width="120" style="text-align:center;"><b>产品名称</b></td>
                                 <td width="50" style="text-align:center;"><b>姓名</b></td>
                                 <td width="70" style="text-align:center;"><b>保全记录</b></td>
                                 <td width="70" style="text-align:center;"><b>手机号</b></td>
                                 <td width="110" style="text-align:center;"><b>邮箱</b></td>
                                 <td width="70" style="text-align:center;"><b>申请时间</b></td>
                                 <td width="70" style="text-align:center;"><b>渠道</b></td>
                                 <td width="60" style="text-align:center;"><b>处理状态</b></td>
                                 <td width="60" style="text-align:center;"><b>核保状态</b></td>
                                 <td width="70" style="text-align:center;"><b>完结时间</b></td>
                                 <td width="70" style="text-align:center;"><b>修改时间</b></td>
                                 <td width="100" style="text-align:center;"><b>情况说明</b></td>
                                 <td width="100" style="text-align:center;"><b>证件号</b></td>
                                 <td width="70" style="text-align:center;"><b>主要症状</b></td>
                                 <td width="70" style="text-align:center;"><b>具体诊断</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="40">&nbsp;</td>
								<td width="15">&nbsp;<input name="id" type="hidden" id="id" value="${id}" /></td>
								 <td style="text-align:left;" title="${productName}">${productName}</td>
								 <td style="text-align:left;" title="${name}">${name}</td>
								 <td style="text-align:left;" title="${records}">${records}</td>
								 <td style="text-align:left;" title="${mobile}">${mobile}</td>
								 <td style="text-align:left;" title="${email}">${email}</td>
								 <td style="text-align:left;" title="${createTime}">${createTime}</td>
								 <td style="text-align:left;" title="${channelsn}">${channelsn}</td>
								 <td style="text-align:left;" title="${dealStatus}">${dealStatus}</td>
								 <td style="text-align:left;" title="${underwritingStatus}">${underwritingStatus}</td>
								 <td style="text-align:left;" title="${finishTime}">${finishTime}</td>
								 <td style="text-align:left;" title="${modifyTime}">${modifyTime}</td>
								 <td style="text-align:left;" title="${situationDesc}">${situationDesc}</td>
								 <td style="text-align:left;" title="${idno}">${idno}</td>
								 <td style="text-align:left;" title="${mainSymptoms}">${mainSymptoms}</td>
								 <td style="text-align:left;" title="${diseaseName}">${diseaseName}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="13">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
					</tr>
				  </table>
				
			
			</td>
		</tr>
	</table>
			</tr>
	</table>
	</div>
	
	
</body>
</html>
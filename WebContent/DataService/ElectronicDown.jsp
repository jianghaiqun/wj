<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@ page import="com.sinosoft.framework.Config"%>
<html>
<head> 
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>电子保单</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script> 
var comCodes = "<%= Config.getValue("UnsupportedPolicyDownload") %>";
var invoiceCodes = "<%= Config.getValue("SupportedInvoiceDownload") %>";
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","orderSn",$V("orderSn"));
	DataGrid.setParam("dg1","riskName",$V("riskName"));
	DataGrid.setParam("dg1","policyNo",$V("policyNo"));
	DataGrid.setParam("dg1","supplierCode",$V("supplierCode"));
	DataGrid.setParam("dg1","createDate",$V("createDate"));
	DataGrid.setParam("dg1","endCreateDate",$V("endCreateDate"));
	DataGrid.loadData("dg1");
}


function electronicDownload(){
	var arr = DataGrid.getSelectedData("dg1");
	if (arr == null || arr.getRowCount() == 0){
		Dialog.alert("请先选择要生成电子保单的条目 ！");
		return;
	}
	if(arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	var dr = arr.Rows[0];
	if (dr.get("riskCode") == null) {
			Dialog.alert("数据异常，无法识别保险公司！");
			return;
		}
	
	if(comCodes.indexOf(dr.get("riskCode").substr(0,4)) != -1){
		// dr.get("riskCode").indexOf("1036")
		Dialog.alert("此保险公司不支持保单下载");
		return;
	}
		var dc = new DataCollection();
		var arr = DataGrid.getSelectedValue("dg1");
		dc.add("IDs", arr.join());
		dc.add("riskCode", dr.get("riskCode"));
		Dialog.wait("正在下载，请稍后......");
		Server.sendRequest(
				"com.wangjin.cms.orders.QueryOrders.electronicDownload", dc,
				function() {
					var response = Server.getResponse();
					Dialog.closeEx();
					Dialog.alert(response.Message);
					if (response.Status == 1) {
						DataGrid.loadData('dg1');
					}
				});
	}
	
	
function electronicAllDownload(){
	var arr = DataGrid.getSelectedData("dg1");
	if (arr == null || arr.getRowCount() == 0){
		Dialog.alert("请先选择要下载订单的条目 ！");
		return;
	}
	if(arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	var dr = arr.Rows[0];
	if (dr.get("riskCode") == null) {
			Dialog.alert("数据异常，无法识别保险公司！");
			return;
		}
	
	if(comCodes.indexOf(dr.get("riskCode").substr(0,4)) != -1){
		Dialog.alert("此保险公司不支持保单下载");
		return;
	}
		var dc = new DataCollection();
		var arr = DataGrid.getSelectedValue("dg1");
		dc.add("IDs", arr.join());
		dc.add("orderSn", dr.get("orderSn"));
		dc.add("riskCode", dr.get("riskCode"));
		Dialog.wait("正在下载，请稍后......");
		Server.sendRequest(
				"com.wangjin.cms.orders.QueryOrders.downloadByOrderSn", dc,
				function() {
					var response = Server.getResponse();
					Dialog.closeEx();
 					if (response.Status == 0) {
						Dialog.alert(response.Message);
					} 
					if (response.Status == 1) {
						 fm1.action = "../aeongroup/FileDownLoad.jsp?filepath="
							+ response.get("OrderPath")
							+ "&filename="
							+ response.get("FileName");
					fm1.submit(); 
					}
				});
	}
	
function electronicInvoice(){
	//var type = 'CPS';
	var arr = DataGrid.getSelectedData("dg1");
	if (arr == null || arr.getRowCount() == 0){
		Dialog.alert("请先选择要生成电子发票的条目！");
		return;
	}
	if(arr.getRowCount()>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	var dr = arr.Rows[0];
	if (dr.get("riskCode") == null) {
		Dialog.alert("数据异常，无法识别保险公司！");
		return;
	}
	if(invoiceCodes.indexOf(dr.get("riskCode").substr(0,4)) == -1){//invoiceCodes 支持电子发票下载的 保险公司|发票开始日期
		Dialog.alert("此保险公司不支持电子发票下载");
		return;
	}
	var enableDate = invoiceCodes.split("|")[1];
	var startDate = dr.get("startDate");
	if(startDate){
		startDate = startDate.substring(0,10);
	}
	if(startDate && dateCompare(startDate, enableDate)==-1){//startDate < enableDate
		Dialog.alert("此保险公司只支持起保日期\""+enableDate+"\"之后的订单下载，该单的起保日期为：\""+startDate+"\"");
		return;
	}
	
	var arr = DataGrid.getSelectedValue("dg1");
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 250;
	diag.Title = "获取电子发票" ;
	diag.URL = "DataService/ElectronicInvoiceDown.jsp?IDs="+arr+"&riskCode="+dr.get("riskCode");
	
	diag.OKEvent = electronicInvoiceDown;
	diag.show();
	diag.OKButton.value="发送";
	diag.CancelButton.value = "关闭";
	
}	

function electronicInvoiceDown() {
	
	var dc = $DW.Form.getData("form2");
	var applicantMail = dc.get("applicantMail");
	var applicantMobile = dc.get("applicantMobile");
	if(applicantMail.replace(/^ +| +$/g,'')=='' && applicantMobile.replace(/^ +| +$/g,'')==''){
		Dialog.alert("邮箱，手机号必选其一！");
		return;
	}
	Server.sendRequest("com.wangjin.cms.orders.QueryOrders.electronicInvoiceDown",dc,function(){
		var response = Server.getResponse();
		if(response.Status=="1"){
			$D.close();
		}
		Dialog.alert(response.Message);
	});
	
}

function dateCompare(date1,date2){
	date1 = date1.replace(/\-/gi,"/");
	date2 = date2.replace(/\-/gi,"/");
	var time1 = new Date(date1).getTime();
	var time2 = new Date(date2).getTime();
	if(time1 > time2){
		return 1;
	}else if(time1 == time2){
		return 0;
	}else{
		return -1;
	}
}
	
</script>
</head>
<body>
	<form action="./FileDownLoad.jsp" method=post name=fm1>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../Icons/icon022a1.gif" width="20" height="20" />电子保单下载 </td>
            </tr>
            <tr>
				<td>
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td nowrap>订单号：</td>
							<td> <input name="orderSn" type="text" id="orderSn" value="" style="width:120px"></td>
							<td nowrap>产品名称：</td>
							<td> <input name="riskName" type="text" id="riskName" value="" style="width:120px"></td>
							<td nowrap>保单号：</td>
							<td> <input name="policyNo" type="text" id="policyNo" value="" style="width:120px"></td>
							<td nowrap>保险公司：</td>
							<td><z:select style="width:120px;"><select name="supplierCode" id="supplierCode" > 
							<option value=""></option> 
		                 	<option value="2007">平安财险 </option>
		                 	<option value="2071">大众保险 </option>
		                 	<option value="2023">华泰保险 </option>
		                 	<option value="1048">大都会人寿 </option>
		                 	<option value="1004">中国人保 </option>
		                 	<option value="2034">美亚保险 </option>
		                 	<option value="2049">安联保险 </option>
		                 	<option value="2011">太平洋财险 </option>
		                 	<option value="1002">中国人寿 </option>
		                 	<option value="2043">华安财险 </option>
		                 	<option value="1014">新华 </option>
		                 	<option value="1018">太平养老 </option>
		                 	<option value="1038">民生保险 </option>
		                 	<option value="0007">太平人寿 </option>
		                 	<option value="2046">永安人寿 </option>
		                 	<option value="1061">合众保险 </option>
		                 	<option value="2096">安盛保险</option>
		                 	<option value="2100">富德保险 </option>
		                 	<option value="2101">人保财险 </option>
		                 	<option value="2103">百年理财险</option>
		                 	<option value="2105">泰康在线</option>
		                	</select></z:select></td>
		                	<td nowrap>订单生成时间 从：</td>
							<td nowrap> <input name="createDate" type="text" id="createDate" value="" style="width:100px" ztype="date" verify="Date" >
							至：<input name="endCreateDate" type="text" id="endCreateDate" value=""style="width:100px" ztype="date" verify="Date"></td>
						</tr>
						<tr>
						   <td  colspan="8">
			            	   <z:tbutton onClick="doSearch()" id="Submitbutton" >
			            	     <img src="../Icons/icon005a2.gif" width="20" height="20"/>查询
			            	   </z:tbutton>
			            	 <z:tbutton onClick="electronicDownload()"><img src="../Icons/icon033a16.gif" width="20" height="20"/>电子保单下载</z:tbutton>
			            	 <z:tbutton onClick="electronicAllDownload()"><img src="../Icons/icon033a16.gif" width="20" height="20"/>按订单号下载</z:tbutton> 
			            	 <z:tbutton onClick="electronicInvoice()"><img src="../Icons/icon033a16.gif" width="20" height="20"/>获取电子发票</z:tbutton> 
			            	</td>
		            	</tr>
		             </table>
				</td>
			</tr>
            <tr>
              <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			  <z:datagrid id="dg1" method="com.wangjin.cms.orders.QueryOrders.electronicDown"  size="15" scroll="true"  lazy="true">
                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                  <tr ztype="head" class="dataTableHead">
                    <td  width="2%" ztype="rowno">&nbsp;</td>
                    <td width="3%" ztype="selector" field="id">&nbsp;</td>
                    <td width="17%">产品名称</td>
                    <td width="15%">订单号</td>
                    <td width="15%">渠道订单号</td>
                    <td width="15%">保单号</td>
                    <td width="10%">电子保单</td>
                    <td style="display: none">起保时间</td>
                  </tr>
                  <tr style1="background-color:#FFFFFF" style2="background-color:#F7F8FF">
                    <td align="center">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>${riskName}<input type="hidden" id="riskCode" name="riskCode" value="${riskCode}"/></td>
                    <td>${OrderSn}</td>
                    <td>${tbTradeSeriNo}</td>
                    <td>${policyNo}</td>
                    <td><a href="${electronicpath}" target="_blank">下载</a></td>
                  	<td style="display: none">${startDate}</td>
                  </tr>
                  <tr ztype="pagebar">
					 <td height="25" colspan="11">${PageBar}</td>
				  </tr>
                </table>
              </z:datagrid></td>
            </tr>
        </table></td>
      </tr>
    </table>
</body>
	</form>
</html>
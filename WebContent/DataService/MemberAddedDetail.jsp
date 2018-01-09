<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户增量明细清单</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var KID = "<%=KID%>";
function doSearch(){ 
	
	
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","BeginDate",$V("createDate"));
	DataGrid.setParam("dg1","EndDate",$V("endCreateDate"));
	//DataGrid.setParam("dg1","From",$V("From"));
	DataGrid.loadData("dg1");
}
function exportStaff(){
	DataGrid.toExcel("dg1",1);
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" />客户增量明细清单</td>
		          </tr>
				<tr>
				<td width="700px">
				<z:init method="com.sinosoft.cms.dataservice.MemberAddedDetail.init">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
		                 	<td>开始时间：</td>
							<td> <input name="createDate" type="text" id="createDate" value="${createDate}" style="width:100px" ztype="date"></td>
							<td width="60px">结束时间：</td>
							<td><input name="endCreateDate" type="text" id="endCreateDate" value="${endCreateDate}"style="width:100px" ztype="date"></td>
							<%-- <td>投/被被保人：</td>
							<td><z:select style="width:100px;" name="From" id="From">
								<option value="appnt">投保人</option>
								<option value="insured">被保人</option>
							</z:select> 
							</td>		--%>					 
							<td width="250px">
			            	   <z:tbutton onClick="doSearch()"   id="Submitbutton" >
			            	     <img src="../Icons/icon005a2.gif" width="20" height="20"/>查询
			            	   </z:tbutton>
			            	   <z:tbutton onClick="exportStaff()" id="exportStaff">
			            	   <img src="../Icons/icon031a7.gif"  width="20" height="20"/>导出EXCEL</z:tbutton>
			            	</td>
						</tr>
		             </table>
		             </z:init>
				</td>
			</tr>
			<tr>
			  <td style="padding: 0px 5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.MemberAddedDetail.dg1DataBind" autoFill="true"  scroll="true" lazy="false" size="20"  multiSelect="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="table-layout : fixed" fixedHeight="480px" afterdrag="afterRowDragEnd">
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="60" style="text-align:center;"><b>渠道</b></td>
								<td width="60" style="text-align:center;"><b>姓名</b></td>
								<td width="30" style="text-align:center;"><b>性别</b></td>
								<td width="65" style="text-align:center;"><b>生日</b></td>
								<td width="60" style="text-align:center;"><b>证件类型</b></td>
								<td width="100" style="text-align:center;"><b>证件号码</b></td>
								<td width="80" style="text-align:center;"><b>手机</b></td>
								<td width="120" style="text-align:center;"><b>邮箱</b></td>
								<td width="120" style="text-align:center;"><b>所属会员</b></td>
								<td width="70" style="text-align:center;"><b>新增时间</b></td>
								<td width="120" style="text-align:center;"><b>订单号</b></td>
							</tr>
							<tr style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td>${channalName}</td>
								<td>${applicantName}</td>
								<td>${applicantSexName}</td>
								<td>${applicantBirthday}</td>
								<td>${applicantIdentityTypeName}</td>
								<td>${applicantIdentityId}</td>
								<td>${applicantMobile}</td>
								<td>${applicantMail}</td>
								<td>${member}</td>
								<td>${modifyDate}</td>
								<td>${orderSn}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
								<input name="id" type="hidden" id="id" value="${id}" style="width:150px">
							</tr>
						</table>
					  </z:datagrid></td>
					</tr>
				  </table>
				</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>

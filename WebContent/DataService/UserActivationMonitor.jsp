<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户激活成本监控</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function doSearch(){
	var sd = $V("createDate");
	var ed = $V("endCreateDate");
	if(ed < sd){
	    Dialog.alert("结束日期不能小于开始日期！");
		return;
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","createDate",$V("createDate"));
	DataGrid.setParam("dg1","endCreateDate",$V("endCreateDate"));
 	DataGrid.setParam("dg1","category",$V("category"));
	DataGrid.setParam("dg1","sendWay",$V("sendWay"));
	DataGrid.setParam("dg1","sendStatus",$V("sendStatus"));
	DataGrid.loadData("dg1");
}

</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 用户激活成本监控</td>
		          </tr>
				<tr>
				<td>
				  <z:init method="com.sinosoft.cms.dataservice.UserActivationMonitor.init">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>分类：</td>
							<td> <z:select style="width:100px;"><select name=category id="category"  > 
							<option value=""></option> 
							<option value="收藏">收藏</option>
		                 	<option value="暂存">暂存</option>
		                 	<option value="待支付">待支付</option>
		                 	</select></z:select></td>
		                 	<td>发送方式：</td>
		                 	<td> <z:select style="width:100px;"><select name="sendWay" id="sendWay"  > 
							<option value=""></option> 
							<option value="1">邮箱</option>
		                 	<option value="2">短信</option>
		                 	</select></z:select></td>
		                 	
							<!-- <td> <input name="userMemberName" type="text" id="userMemberName" value="" style="width:100px" /></td> -->
							<td>发送状态</td>
		                 	<td> <z:select style="width:100px;"><select name="sendStatus" id="sendStatus"  > 
							<option value=""></option> 
							<option value="Y">已发送</option>
		                 	<option value="N">未发送</option>
		                 	</select></z:select></td>							
		                 	 <%-- <td><z:select id="investigationStatus" name="investigationStatus"  style="width:100px">${investigationStatus}</z:select></td> --%>
							<td>发送时间 从:</td>
							<td> <input name="createDate" type="text" id="createDate" value="${createDate}" style="width:100px" ztype="date"></td>
							<td>到</td>
							<td><input name="endCreateDate" type="text" id="endCreateDate" value="${endCreateDate}" style="width:100px" ztype="date"></td>
						</tr>
					

						<tr>
						   <td  colspan="4">
			            	   <z:tbutton onClick="doSearch()"   id="Submitbutton" >
			            	     <img src="../Icons/icon005a2.gif" width="20" height="20"/>查询
			            	   </z:tbutton>
			            	</td>
		            	</tr>
		             </table>
		           </z:init>
				</td>
			</tr>
				<tr>
						  <td style="padding: 0px 5px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="6"
									class="blockTable" style="table-layout: fixed;" >
										<tr>
												<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
													<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.UserActivationMonitor.searchUserActivationMonitor" scroll="true" lazy="true" size="20" >
														<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" style="table-layout : fixed"  fixedHeight="480px"  afterdrag="afterRowDragEnd">
															<tr ztype="head" class="dataTableHead">
																<td  width="30" ztype="RowNo"><strong>序号</strong></td>
																<td  width="15"  ztype="selector" field="insuredSn">&nbsp;</td>
																<td  width="120"><b>类别</b></td>
																<td  width="120"><b>创建日期</b></td>
																<td  width="130"><b>降价</b></td>
																<td  width="150"><b>保险种类</b></td>
																<td  width="100"><b>发送方式</b></td>
																<td  width="120"><b>发送状态</b></td>
																<td  width="120"><b>产品名</b></td>
																<td  width="120"><b>用户id</b></td>
																<td  width="120"><b>活动编码</b></td>
															</tr>
															<tr  style="background-color:#F9FBFC">
																<td width="10%">&nbsp;</td>
																<td>&nbsp;</td>
																<td>${category}</td>
																<td>${createdate}</td>
																<td>${cheap}</td>
																<td title="${ProductGenera}">${ProductGenera}</td>
																<td>${sendWay}</td>
																<td>${sendStatus}</td>
																<td title="${productName}">${productName}</td>
																<td>${memberid}</td>
																<td>${activitysn}</td>
															</tr>
															<tr ztype="pagebar">
																<td height="25" colspan="11">${PageBar}</td>
															</tr>
														</table>
													</z:datagrid>
												</td>
										</tr>
								</table>
							</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>

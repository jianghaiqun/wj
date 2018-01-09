<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>搜索帮助</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var KID = "<%=KID%>";
function doSearch(){
	var sd = $V("createDate");
	var ed = $V("endCreateDate");
 	var sl = $V("searchLike");
	var is = $V("investigationStatus");

 
 
	if(ed < sd){
	    Dialog.alert("结束日期不能小于开始日期！");
		return;
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","createDate",$V("createDate"));
	DataGrid.setParam("dg1","endCreateDate",$V("endCreateDate"));
 	DataGrid.setParam("dg1","searchLike",$V("searchLike"));
	DataGrid.setParam("dg1","investigationStatus",$V("investigationStatus"));
	DataGrid.setParam("dg1","userMemberName",$V("userMemberName"));
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
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 搜索帮助</td>
		          </tr>
				<tr>
				<td>
				  <z:init method="com.sinosoft.cms.dataservice.SearchHelp.init">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>用户分类：</td>
							<td> <z:select style="width:100px;"><select name="searchLike" id="searchLike"  > 
							<option value=""></option> 
							<option value="1">匿名用户</option>
		                 	<option value="2">登录用户</option>
		                 	</select></z:select></td>
		                 	<td>用户名：</td>
							<td> <input name="userMemberName" type="text" id="userMemberName" value="" style="width:100px" /></td>
							<td>调查分类</td>
		                 	 <td><z:select id="investigationStatus" name="investigationStatus"  style="width:100px">${investigationStatus}</z:select></td>
							<td>订单时间 从:</td>
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
													<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.SearchHelp.searchHelpSearch" scroll="true" lazy="true" size="20" >
														<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" style="table-layout : fixed"  fixedHeight="480px"  afterdrag="afterRowDragEnd">
															<tr ztype="head" class="dataTableHead">
																<td  width="30" ztype="RowNo"><strong>序号</strong></td>
																<td  width="15"  ztype="selector" field="insuredSn">&nbsp;</td>
																<td  width="120"><b>用户名</b></td>
																<td  width="120"><b>用户id</b></td>
																<td  width="130"><b>调查分类</b></td>
																<td  width="250"><b>留言</b></td>
																<td  width="100"><b>提出时间</b></td>
																<td  width="120"><b>关键字</b></td>
															</tr>
															<tr  style="background-color:#F9FBFC">
																<td width="10%">&nbsp;</td>
																<td>&nbsp;</td>
																<td>${userName}</td>
																<td>${memberId}</td>
																<td>${investigationStatus}</td>
																<td title="${leaveMessage}">${leaveMessage}</td>
																<td>${createTime}</td>
																<td>${searchName}</td>

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

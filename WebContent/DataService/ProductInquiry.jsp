<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品浏览信息</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","username",$V("username"));
	DataGrid.setParam("dg1","productname",$V("productname"));
	DataGrid.setParam("dg1","CreateDatefrom",$V("CreateDatefrom"));
	DataGrid.setParam("dg1","CreateDateto",$V("CreateDateto"));
	DataGrid.loadData("dg1");
}
function doblank(){
	username.value="";
	productname.value="";
	CreateDatefrom.value="";
	CreateDateto.value="";
	
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 产品浏览信息</td>
		          </tr>
				<tr>
				<td>
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>会员名称:</td>
							<td> <input name="username" type="text" id="username" value="" style="width:150px"></td>
							<td>产品名称：</td>
							<td> <input name="productname" type="text" id="productname" value="" style="width:150px"></td>
						</tr>
						<tr>
							<td>浏览时间从：</td>
							<td> <input name="CreateDatefrom" type="text" id="CreateDatefrom" value="" style="width:150px" ztype="date"/></td>
							<td>到</td>
							<td> <input name="CreateDateto" type="text" id="CreateDateto" value="" style="width:150px" ztype="date"/></td>
							
						</tr>
						<tr>
						   <td>
			            	   <z:tbutton onClick="doSearch()"   id="Submitbutton" >
			            	     <img src="../Icons/icon005a2.gif" width="20" height="20"/>查询
			            	   </z:tbutton>
			            	</td>
			            	<td>
			            	 <z:tbutton onClick="doblank()"><img src="../Icons/icon401a3.gif" width="20" height="20" />清空查询条件</z:tbutton>
			            	  	</td>
		            	  </tr>
		             </table>
            	
				</td>
			</tr>
				 
				<tr>
					<td style="padding:8px 10px;">
                   
            
					</td>
				</tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.Member.productInquery" size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="2%" ztype="RowNo"><strong>序号</strong></td>
								<td width="20%"><b>会员ID</b></td>
								<td width="10%"><b>会员名称</b></td>
								<td width="10%"><b>浏览日期</b></td>
								<td width="10%"><b>浏览时间</b></td>
								<td width="10%"><b>产品ID</b></td>
								<td width="10%"><b>产品名称</b></td>
							</tr>
							<tr onDblClick="edit('${orderSn}')" style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="10%">&nbsp;</td>
								<td>${MemberID}</td>
								<td>${username}</td>
								<td>${CreateDate}</td>
								<td>${CreateTime}</td>
								<td>${ProductID}</td>
								<td>${productname}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
							
						</table>
						
					</z:datagrid></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>

</html>

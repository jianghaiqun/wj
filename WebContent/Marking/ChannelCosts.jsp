<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
var _dg4;
Page.onLoad(function(){
	$("dg3").afterEdit = function(tr,dr){
		dr.set("cSCost",$V("cSCost"));
		dr.set("cTransNode",$V("cTransNode"));
		dr.set("cFixedCosts",$V("cFixedCosts"));
		return true;
	}
	_dg4=$("dg3");
});
function save(){
	var arr = DataGrid.getSelectedValue("dg3");
	var DT = DataGrid.getSelectedData("dg3");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要保存的行！");
		return;
	}
	Dialog.confirm("确定要保存该项吗？",function(){
		var dc = new DataCollection();	
		dc.add("IDs",DT);
		Server.sendRequest("com.sinosoft.platform.Marking.ChannelCostsave",dc,function(){
			var response = Server.getResponse();
			if(response.Status==1){
				Dialog.alert(response.Message);
			}else{
				DataGrid.loadData("dg3");
			}
		});
	},function(){
		return;
	});
}
</script>
<style>
#hotarea{width:160px; height:120px; border:#147 1px solid; background:#ca6 url(../Platform/Images/picture.jpg) no-repeat; position:relative}
#hotarea  a{ position:absolute; display:block; width:35px; height:25px; border:#fff 1px dashed; text-align:center; line-height:24px; color:#fff;}
#hotarea  a:hover{ text-decoration:none; border:#fff 1px solid; color:#fff}
</style>
</head>
<body>
                	<z:datagrid id="dg3" method="com.sinosoft.platform.Marking.dg1DataBindCost" size="8">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" height="30" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="3%" ztype="selector" field="ID" >模式选择</td>
							<td width="2%" ztype="RowNo"><b>序号</b></td>
							<td width="3%"><strong>费用模式</strong></td>
							<td width="3%"><strong>单价（元）</strong></td>
							<td width="4%"><strong>转化节点（件）</strong></td>
							<td width="4%"><strong>固定费用（元）</strong></td>
							<td width="4%"><strong>渠道编码</strong></td>
						</tr>
						<tr>
						    <td>&nbsp;</td>
						    <td onclick='select();'>&nbsp;</td>
						    <td>${cID}</td>
							<td>${cCostType}</td>
							<td>${cSCost}</td>
							<td>${cTransNode}</td>			 
							<td>${cFixedCosts}</td>
							<td>${cChannelCode}</td>			 
						</tr>
						<tr ztype="edit" bgcolor="#E1F3FF">
								<td>&nbsp;</td>
						        <td>&nbsp;</td>
						        <td>${cID}</td>
							    <td>${cCostType}</td>
								<td><input name="cSCost" type="text" id="cSCost" value="${cSCost}" size="10"></td>
								<td><input name="cTransNode" type="text" id="cTransNode" value="${cTransNode}" size="10"></td>
								<td><input name="cFixedCosts" type="text" id="cFixedCosts" value="${cFixedCosts}" size="10"></td>
								<td>${cChannelCode}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="9" align="left">${PageBar}</td>					
						</tr>
					</table>
				   </z:datagrid>
				<td><z:tbutton id="b2" onClick="save()"><img src="../Icons/icon006a7.gif" width="20" height="20"/>保存</z:tbutton></td> 
			 
</body>
</html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品积分管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
 

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if( ele.id == 'CompanyNo' || ele.id == 'ProductName'|| ele.id == 'Submitbutton'){
			search();
		}
	}
}

function search(){
	DataGrid.setParam("integral_dg2",Constant.PageIndex,0);
	DataGrid.setParam("integral_dg2","CompanyNo",$V("CompanyNo"));
	DataGrid.setParam("integral_dg2","ProductName",$V("ProductName"));
	DataGrid.loadData("integral_dg2");
}

Page.onLoad(function(){
	$("integral_dg2").afterEdit = function(tr,dr){
		
		if(Verify.hasError()){
	 		return;
    	}
		
		dr.set("GivePoints",$V("GivePoints"));
		dr.set("BuyPoints",$V("BuyPoints"));  
		
		
		return true;
	}
});

function save(){
	var flag=DataGrid.getSelectedValueByColumnCheck("integral_dg2","GivePoints","BuyPoints")
	if(flag){
		DataGrid.save("integral_dg2","com.sinosoft.points.IntegralSystem.saveProductIntegral",function(){DataGrid.loadData('integral_dg2');});
	}
}
//校验是否为正小数
DataGrid.getSelectedValueByColumnCheck = function(ele, column1,column2) {
	ele = $(ele);
	var ds = ele.DataSource;
	for ( var i = 0; i < ds.Columns.length; i++) {
		if (ds.Columns[i].Name == column1.toLowerCase()||ds.Columns[i].Name == column2.toLowerCase()) {
			for ( var k = 1; k < ele.rows.length; k++) {
				if (ele.rows[k].Selected) {
					var val=ds.Values[k - 1][i];
					if(isNaN(val)){
						Dialog.alert("请填写正小数！");
						return false;
					}else if(parseFloat(val)<0){
						Dialog.alert("不可以使用负数！");
						return false;
					}else if(parseFloat(val)>1){
						Dialog.alert("数值必须小于1！");
						return false;
					}
				}
			}
		}
	}
	return  true;
} 
</script>
</head>
<body>
<z:init method="com.sinosoft.points.IntegralSystem.initProductIntegral">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
          <tr>
				<td style="padding:2px 10px;">
					<div style="float:left;"> 
			    		 保险公司&nbsp;<z:select id='CompanyNo'>${CompanyList}</z:select>&nbsp;
						 产品名称&nbsp;<input name="ProductName" type="text" id="ProductName"> &nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
						<input type="button" name="Savebutton" id="Savebutton" value="保存" onClick="save()">
				    </div>
				</td>
			</tr>
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="integral_dg2" method="com.sinosoft.points.IntegralSystem.ProductIntegralSearch"  size="15" autoFill="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="20%"><strong>产品名称</strong></td>
                  <td width="8%"><strong>产品路径</strong></td>
                  <td width="8%"><strong>产品状态</strong></td>
                  <td width="12%"><strong>产品赠送积分</strong></td>
                  <td width="17%"><strong>产品购买抵值积分</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC"  >
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${ProductName}</td>
                  <td><a href="${HtmlPath}" target="_blank">查看</a></td>
                  <td>${IsPublish}</td>
                  <td> ${GivePoints}</td>
                  <td> ${BuyPoints}</td>
                </tr>
                 <tr ztype="edit" bgcolor="#E1F3FF">
                    <td align="center" bgcolor="#E1F3FF">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>${ProductName}</td>
                    <td><a href="${HtmlPath}" target="_blank">查看</a></td>
                    <td>${IsPublish}</td>
                    <td><input name="GivePoints" type="text" id="GivePoints" value="${GivePoints}" size="20" verify="DoubleNumber"></td>
                    <td><input name="BuyPoints" type="text" id="BuyPoints" value="${BuyPoints}" size="20" verify="DoubleNumber"></td>
                  </tr>
                <tr ztype="pagebar">
					<td colspan="6" align="left">${PageBar}</td>
				</tr>
              </table>
            </z:datagrid></td>
          </tr>
      </table></td>
    </tr>
  </table>
 </z:init>
</body>
</html>

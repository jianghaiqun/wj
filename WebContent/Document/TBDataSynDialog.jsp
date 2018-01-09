<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%> 
<html>
<head> 
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>证件类型配置</title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
function save(){ 
	var dc = Form.getData("form2");
	dc.add("Id",$("Id").value); 
	if(Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.product.ProductInfo.tbdatasyn",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				//暂时不做任何处理
			}
		});
	});
}

function search(){
	DataGrid.setParam("module_dginput","BaseInfoType",$V("BaseInfoType"));
	DataGrid.setParam("module_dginput","TBProductID",$V("TBProductID"));
	DataGrid.setParam("module_dginput","channelSn",$V("channelSn"));
	DataGrid.loadData("module_dginput");
}
</script>
	</head>
	<body class="dialogBody">
	<z:init method="com.sinosoft.product.ProductInfo.initbase">
	<form id="form2">
	<table width="100%" height="50%" border="0">
		<tr>
			<td valign="middle">
			<table width="900" height="30" align="center" cellpadding="2"
				cellspacing="0" border="0">
				<tr>
					<td width="200" height="25"></td>
					<td width="700"></td>
				</tr>
				<tr>
					<td align="right">保险公司编码：</td>
					<td  >${Id}<input name="Id" type="hidden" id="Id" value="${Id}"/><input name="ComCode" type="hidden" id="ComCode" value="${ComCode}"/></td>
					
				</tr>
				<tr>
					<td align="right">保险公司名称名称：</td>
					<td  >${InsureName}</td>
				</tr>
				<tr>
					<td align="right">同步产品编码：</td>
					<td  ><input name="TBProductID" type="text" id="TBProductID" value="${TBProductID}"/> <font color="red">注：当该项为空时，同步保险公司信息，不包括保险公司下产品！ </font></td>
				</tr>
				<tr>
					<td align="right">关联主站产品编码：</td>
					<td  ><input name="REProductID" type="text" id="REProductID" value="${REProductID}"/> <font color="red">注：当淘宝/去哪儿产品为专属产品时，需要录入该项！ </font></td>
				</tr>
				<tr>
					<td align="right">同步数据类型：</td>
					<td>
					<z:select id="BaseInfoType" style="width:100px;" verify="元素类型|NotNull">${BaseTypeList}</z:select>
					</td>
				</tr>
				<tr>
					<td align="right">是否为单选旅游目的地：</td>
					<td>
					<z:select id="TravelAddress" style="width:100px;">${TravelAddress}</z:select>
					<font color="red">注：当该项选择“是”的时候，请确认该同步产品在主站已经配置旅游目的地信息！ </font>
					</td>
				</tr>
				<tr>
					<td align="right">旅游目的地是否带有申根字样：</td>
					<td>
					<z:select id="TravelShenGen" style="width:100px;">${TravelShenGen}</z:select>
					<font color="red">注：当同步【同步类型】选择“旅游目的地”的时候，根据保险公司是否需要“申根”字样配置该项！ </font>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<input type="hidden" id="channelSn" name="channelSn" value="${channelSn}"/>
	 <input type="button" name="Submitbutton" id="Submitbutton1" value="同步" onClick="save()">
	 <input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
	<z:datagrid id="module_dginput" method="com.sinosoft.product.ProductInfo.dg7DataBind">
            <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
              <tr ztype="head" class="dataTableHead">
                <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                <td width="3%" ztype="selector" field="id">&nbsp;</td>
                <td width="5%"><strong>主站编码</strong></td>
                <td width="10%"><strong>主站显示值</strong></td>
 				<td width="5%"><strong>淘宝/去哪儿编码</strong></td>
                <td width="10%"><strong>淘宝/去哪儿显示值</strong></td>
              </tr>
              <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>${codeValue}</td>
                <td>${codeName}</td>
                <td>${tbcodeValue}</td>
                <td>${tbcodeName}</td>
              </tr>
            </table>
          </z:datagrid>
    </form>
    </z:init>
	</body>
	</html>

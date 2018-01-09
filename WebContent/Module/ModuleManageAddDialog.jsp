<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.module.ModuleManage.initModuleElement">
	<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>生成模版元素</title>
	<script src="../Framework/Main.js"></script>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		Page.onLoad(function(){
			$("module_dg2").afterEdit = function(tr,dr){ 
				dr.set("OrderFlag",$V("OrderFlag"));
				return true;
			}
		});
		function search(){
			DataGrid.setParam("module_dg2",Constant.PageIndex,0);
			DataGrid.setParam("module_dg2","ElementType",$V("ElementType"));
			DataGrid.setParam("module_dg2","ElementName",$V("ElementName"));
			DataGrid.loadData("module_dg2");
		}
	</script>
	</head>
	<body class="dialogBody">
	<form id="form2">
	<table width="100%" height="100%" border="0">
		<tr>
			<td style="padding:3px 5px;">
			   <fieldset>
	   			   <legend><label>模版基本信息</label></legend>
					<table width="600" height="90" align="center" cellpadding="2"
						cellspacing="0" border="0">
						<tr>
							<td width="80" height="10"></td>
							<td></td>
						</tr>
						<tr>
							<td align="right">模块名称：</td>
							<td  ><input name="ModuleName" verify="模块名称|NotNull&&Length<30"
								type="text" value=""   class="input1" id="ModuleName"
								size="50" /> </td>
						</tr>
						<tr>
							<td align="right">模块类型：</td>
							<td>
							<z:select id="ModuleType" style="width:100px;" verify="模块类型|NotNull">${ModuleTypeList}</z:select>
							</td>
						</tr>
						<tr>
							<td align="right">备注：</td>
							<td><input name="Memo" type="text" class="input1" id="Memo"
								value="" size="30" verify="备注|Length<50"/></td>
						</tr>
					</table>
				</fieldset>
			</td>
		</tr>
	   <tr>
			<td style="padding:3px 5px;">
				<fieldset>
	   			   <legend><label>元素查询信息</label></legend>
					<div style="float:left;"> 
			    		 元素类型&nbsp;<z:select id='ElementType'>${ModuleTypeList}</z:select>&nbsp;
						 元素名称&nbsp;<input name="ElementName" type="text" id="ElementName"> &nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				    </div>
				  </fieldset>
			</td>
		</tr>
		<tr>
			 <td style="padding:0px 5px;">
				  <z:datagrid id="module_dg2" method="com.sinosoft.module.Module.dg1DataBind" page="false" lazy="true">
		              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
		                <tr ztype="head" class="dataTableHead">
		                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
		                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
		                  <td width="20%"><strong>元素名称</strong></td>
		                  <td width="15%"><strong>元素类型</strong></td>
		                  <td width="20%"><strong>备注</strong></td>
		                  <td width="10%"><strong>排序</strong></td>
		                </tr>
		                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" >
		                  <td>&nbsp;</td>
		                  <td>&nbsp;</td>
		                  <td>${ElementName}</td>
		                   <td>${ElementTypeName} </td>
		                  <td>${Memo}</td>
		                  <td>${OrderFlag}</td>
		                </tr>
		                <tr ztype="edit">
							  <td>&nbsp;</td>
							  <td>&nbsp;</td>
			                  <td>${ElementName}</td>
			                  <td>${ElementTypeName} </td>
			                  <td>${Memo}</td>
			                  <td><input type="text" name="OrderFlag" id="OrderFlag" size="6" value="${OrderFlag}" verify="NotNull&&Number"/></td>
						</tr>
		              </table>
		            </z:datagrid>
			</td>
		</tr>
	</table>
	</form>
	</body>
	</html>
</z:init>

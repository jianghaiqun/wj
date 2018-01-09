<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.module.ModuleConfigure.initEditModuleElement">
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
		
		function add(){
			var diag = new Dialog("Diag2");
			diag.Width = 700;
			diag.Height = 400;
			diag.Title = "模块信息列表" ;
			diag.URL = "Module/ModuleConfigure_Add.jsp?FactorId=" + $V("FactorId");
			diag.onLoad = function(){
				$DW.$("ModuleType").focus();
			};
			diag.OKEvent = addSave_;
			diag.show();
		}

		function addSave_(){
			var dc = Form.getData("form2");
			var dt = $DW.DataGrid.getSelectedData("module_dg5");
			if(!dt || dt.getRowCount() == 0){
				Dialog.alert("请先要添加模版的数据！");
				return;
			}
			dc.add("DT",dt);
			dc.add("FactorId",$V("FactorId"));
			Server.sendRequest("com.sinosoft.module.ModuleConfigure.addModuleConfigure",dc,function(response){
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						$D.close();
						DataGrid.loadData("module_dg2");
					}
				})
			});
		}

	
		function saveOrderFlag() {
			DataGrid.save("module_dg2", "com.sinosoft.module.ModuleConfigure.saveOrderFlag",
					function() {
						DataGrid.loadData('module_dg2');
					});
		}
		
		function saveModuleConfigure() {
			var dc = Form.getData("form2");
			if(Verify.hasError()){
				return;
			}
			Server.sendRequest("com.sinosoft.module.ModuleConfigure.saveModuleConfigure",dc,function(response){
				Dialog.alert(response.Message,function(){
				})
			});
		}

		function del() {
			var arr = DataGrid.getSelectedValue("module_dg2");
			if (!arr || arr.length == 0) {
				Dialog.alert("请先选择要删除的行！");
				return;
			}
			Dialog.confirm("删除后可能影响购买流程，确认要删除？", function() {
				var dc = new DataCollection();
				dc.add("IDs", arr.join());
				Server.sendRequest(
						"com.sinosoft.module.ModuleConfigure.delModuleElement", dc,
						function(response) {
							Dialog.alert(response.Message, function() {
								if (response.Status == 1) {
									DataGrid.loadData("module_dg2");
								}
							});
						});
			});
		}
		
	</script>
	</head>
	<body class="dialogBody">
	<form id="form2">
	<input name="FactorId" type="hidden" value="${FactorId}"   id="FactorId"   />
	<table width="100%" height="100%" border="0">
		<tr>
			<td style="padding:3px 5px;">
			   <fieldset>
	   			   <legend><label>模版要素基本信息</label></legend>
	   			   <table width="100%" height="100%" border="0">
				  	<tr>
						<td >
							    <z:tbutton onClick="saveModuleConfigure()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>保存</z:tbutton>
			           	</td>
			        </tr>
					<tr>
						<td >
							<table width="600" height="90" align="center" cellpadding="2"
								cellspacing="0" border="0">
								<tr>
									<td width="80" height="10"></td>
									<td></td>
								</tr>
<!--								<tr>-->
<!--									<td align="right">主模版：</td>-->
<!--									<td>-->
<!--									<z:select id="MainTemplate" style="width:100px;" value="${MainTemplate}" verify="主模版|NotNull">${MainTemplateList}</z:select>-->
<!--									</td>-->
<!--								</tr>-->
								<tr>
									<td align="right">要素名称：</td>
									<td  ><input name="FactorName" verify="要素名称|NotNull&&Length<30"
										type="text" value="${FactorName}"   class="input1" id="FactorName"
										size="50" /> </td>
								</tr>
<!--								<tr>-->
<!--									<td align="right">要素类型：</td>-->
<!--									<td>-->
<!--									<z:select id="FactorType" style="width:100px;" verify="要素类型|NotNull"  >${FactorTypeList}</z:select>-->
<!--									</td>-->
<!--								</tr>-->
<!--								<tr>-->
<!--									<td align="right">要素编码：</td>-->
<!--									<td  ><input name="FactorCode" verify="要素编码|NotNull&&Length<30"-->
<!--										type="text" value="${FactorCode}"   class="input1" id="FactorCode"-->
<!--										size="50" /> </td>-->
<!--								</tr>-->
								<tr>
									<td align="right">备注：</td>
									<td><input name="Memo" type="text" class="input1" id="Memo"
										value="${Memo}" size="30" verify="备注|Length<50"/></td>
								</tr>
							</table>
							</td>
						</tr>
						</table>
				</fieldset>
			</td>
		</tr>
	   <tr>
			<td style="padding:3px 5px;">
				<fieldset>
	   			   <legend><label>模块查询信息</label></legend>
						 <z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>添加</z:tbutton>
					    <z:tbutton onClick="saveOrderFlag()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>保存排序</z:tbutton>
		         	    <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
				  </fieldset>
			</td>
		</tr>
		<tr>
			 <td style="padding:0px 5px;">
				  <z:datagrid id="module_dg2" method="com.sinosoft.module.ModuleConfigure.dg3DataBind" page="false" lazy="false">
		              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
		                <tr ztype="head" class="dataTableHead">
		                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
		                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
		                  <td width="20%"><strong>模块名称</strong></td>
		                  <td width="15%"><strong>模块类型</strong></td>
		                  <td width="20%"><strong>备注</strong></td>
		                  <td width="10%"><strong>排序</strong></td>
		                </tr>
		                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" >
		                  <td>&nbsp;</td>
		                  <td>&nbsp;</td>
		                  <td>${ModuleName}</td>
		                   <td>${ModuleTypeName} </td>
		                  <td>${Memo}</td>
		                  <td>${OrderFlag}</td>
		                </tr>
		                <tr ztype="edit">
							  <td>&nbsp;</td>
							  <td>&nbsp;</td>
			                  <td>${ModuleName}</td>
			                  <td>${ModuleTypeName} </td>
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

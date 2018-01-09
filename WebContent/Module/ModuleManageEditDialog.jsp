<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.module.ModuleManage.initModuleElementEdit">
	<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>生成模版元素</title>
	<script src="../Framework/Main.js"></script>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		Page.onLoad(function(){
			$("module_dg4").afterEdit = function(tr,dr){ 
				dr.set("OrderFlag",$V("OrderFlag"));
				return true;
			}
		});

		function add(){
			var diag = new Dialog("Diag2");
			diag.Width = 700;
			diag.Height = 400;
			diag.Title = "元素列表" ;
			diag.URL = "Module/ModuleManage_Add.jsp" ;
			diag.onLoad = function(){
				$DW.$("ModuleName").focus();
			};
			diag.OKEvent = addSave_;
			diag.show();
		}

		function addSave_(){
			var dc = new DataCollection();
			var dt = $DW.DataGrid.getSelectedData("module_dg5");
			if(!dt || dt.getRowCount() == 0){
				Dialog.alert("请先要添加模版的数据！");
				return;
			}
			dc.add("DT",dt);
			dc.add("ModuleCode",$V("Id"));
			Server.sendRequest("com.sinosoft.module.ModuleManage.addModuleElement",dc,function(response){
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						$D.close();
						DataGrid.loadData("module_dg4");
					}
				})
			});
		}

	
		function saveOrderFlag() {
			DataGrid.save("module_dg4", "com.sinosoft.module.ModuleManage.saveOrderFlag",
					function() {
						DataGrid.loadData('module_dg4');
					});
		}
		
		function saveModuleMessage() {
			var dc = Form.getData("form2");
			if(Verify.hasError()){
				return;
			}
			Server.sendRequest("com.sinosoft.module.ModuleManage.saveModuleMessage",dc,function(response){
				Dialog.alert(response.Message,function(){
				})
			});
		}

		function del() {
			var arr = DataGrid.getSelectedValue("module_dg4");
			if (!arr || arr.length == 0) {
				Dialog.alert("请先选择要删除的行！");
				return;
			}
			Dialog.confirm("删除后可能影响购买流程，确认要删除？", function() {
				var dc = new DataCollection();
				dc.add("IDs", arr.join());
				Server.sendRequest(
						"com.sinosoft.module.ModuleManage.delModuleElement", dc,
						function(response) {
							Dialog.alert(response.Message, function() {
								if (response.Status == 1) {
									DataGrid.loadData("module_dg4");
								}
							});
						});
			});
		}
</script>
	</head>
	<body class="dialogBody">
	<form id="form2">
	 	<fieldset style="margin: 3px 5px;">
	    <legend><label>模版信息修改操作区域</label></legend>
			<table width="100%" height="100%" border="0">
			  	<tr>
					<td >
						    <z:tbutton onClick="saveModuleMessage()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>保存</z:tbutton>
		           	</td>
		        </tr>
				<tr>
					<td >
							<table width="600" height="90" align="center" cellpadding="2"
								cellspacing="0" border="0">
								<tr>
									<td align="right">模块名称：</td>
									<td  ><input name="ModuleName" verify="元素名称|NotNull&&Length<30"
										type="text" value="${ModuleName}"   class="input1" id="ModuleName"
										size="50" /> <input type="hidden" name="Id" id="Id" value="${Id}"> </td>
								</tr>
								<tr>
									<td align="right">模块类型：</td>
									<td>
									<z:select id="ModuleType" style="width:100px;" verify="元素类型|NotNull">${ModuleTypeList}</z:select>
									</td>
								</tr>
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
		<table width="100%" height="100%" border="0">
	   	<tr>
			<td style="padding: 3px 5px;">
			    <fieldset>
	   			   <legend><label>元素信息修改操作区域</label></legend>
				    <z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>添加</z:tbutton>
				    <z:tbutton onClick="saveOrderFlag()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>保存</z:tbutton>
	         	    <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
         	    </fieldset>
           	</td>
          </tr>
		<tr>
			 <td style="padding:0px 5px;">
				  <z:datagrid id="module_dg4" method="com.sinosoft.module.ModuleManage.editDataBind" page="false"  >
		              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
		                <tr ztype="head" class="dataTableHead">
		                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
		                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
		                  <td width="20%"><strong>元素名称</strong></td>
		                  <td width="20%"><strong>元素类型</strong></td>
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

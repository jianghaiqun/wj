<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.cms.document.WapSiteBidPageManage.initProductMenu">
	<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>标题菜单设置</title>
	<script src="../Framework/Main.js"></script>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		Page.onLoad(function(){
			$("menu_dg1").afterEdit = function(tr,dr){ 
				dr.set("OrderFlag",$V("OrderFlag"));
				return true;
			}
		});
		
		function productContentEditer(ID) {

			var diag = new Dialog("Diag2");
			diag.Width = 600;
			diag.Height = 300;
			diag.Title = "产品内容编辑";
			diag.URL = "Document/WapBidProductContent.jsp?WapBidMenuId="+ID+"&DocumentId=" + $V("DocumentId");
			diag.CancelEvent = CancelClose;
			diag.show();
			diag.OKButton.hide();
			diag.CancelButton.value = "关  闭";
		}

		function CancelClose(){
			DataGrid.loadData("menu_dg1");
			$D.close();
		}
		
		function add(){
			if ($("menu_dg1").rows.length >= 5) {
				Dialog.alert("标题菜单最多可添加3个！");
				return;
			}
			var diag = new Dialog("Diag2");
			diag.Width = 350;
			diag.Height = 280;
			diag.Title = "标题菜单添加";
			diag.URL = "Document/WapBidProductMenu_add.jsp?DocumentId=" + $V("DocumentId");
			diag.onLoad = function(){
				$DW.$("MenuName").focus();
			};
			diag.OKEvent = addSave_;
			diag.show();
		}

		function addSave_(){
			var dc = $DW.Form.getData("form2");
			if ($DW.Verify.hasError()) {
				return;
			}
			Server.sendRequest("com.sinosoft.cms.document.WapSiteBidPageManage.saveMenu",
				dc, function() {
				Dialog.endWait();
				var response = Server.getResponse();
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						DataGrid.loadData("menu_dg1");
						$D.close();
					}
				});
			});
		}
	
		function saveOrderFlag() {
			DataGrid.save("menu_dg1", "com.sinosoft.cms.document.WapSiteBidPageManage.saveOrderFlag",
				function() {
				DataGrid.loadData('menu_dg1');
			});
		}
		
		function del() {
			var arr = DataGrid.getSelectedValue("menu_dg1");
			if (!arr || arr.length == 0) {
				Dialog.alert("请先选择要删除的行！");
				return;
			}
			Dialog.confirm("确认要删除？", function() {
				var dc = new DataCollection();
				dc.add("IDs", arr.join());
				Server.sendRequest(
						"com.sinosoft.cms.document.WapSiteBidPageManage.delMenus", dc,
					function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("menu_dg1");
						}
					});
				});
			});
		}
		
	</script>
	</head>
	<body class="dialogBody">
	<form id="form2">
	<input name="DocumentId" type="hidden" value="${DocumentId}" id="DocumentId" />
	<table width="100%" border="0">
	   <tr>
			<td style="padding:3px 5px;">
				<fieldset>
	   			   <legend><label>标题菜单设置</label></legend>
						 <z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>添加</z:tbutton>
					    <z:tbutton onClick="saveOrderFlag()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>保存</z:tbutton>
		         	    <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
				  </fieldset>
			</td>
		</tr>
		<tr>
			 <td style="padding:0px 5px;">
				  <z:datagrid id="menu_dg1" method="com.sinosoft.cms.document.WapSiteBidPageManage.dgMenuDataBind" page="false" lazy="false">
		              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
		                <tr ztype="head" class="dataTableHead">
		                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
		                  <td width="3%" ztype="selector" field="ID">&nbsp;</td>
		                  <td width="20%"><strong>菜单名称</strong></td>
		                  <td width="20%"><strong>备注</strong></td>
		                  <td width="20%"><strong>产品数量</strong></td>
		                  <td width="10%"><strong>排序</strong></td>
		                </tr>
		                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" >
		                  <td>&nbsp;</td>
		                  <td>&nbsp;</td>
		                  <td><a href="#" onclick="productContentEditer('${ID}')">${MenuName}</a></td>
		                  <td>${Description}</td>
			              <td>${PCount}</td>
		                  <td>${OrderFlag}</td>
		                </tr>
		                <tr ztype="edit">
							  <td>&nbsp;</td>
							  <td>&nbsp;</td>
			                  <td>${MenuName}</td>
			                  <td>${Description}</td>
			                  <td>${PCount}</td>
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

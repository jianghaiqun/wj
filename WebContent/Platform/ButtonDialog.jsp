<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script>
	function onMenuChange(){
		var MenuID = $V("MenuID");
		if(MenuID != null){
		    document.getElementById("ParentID").conditionValue = MenuID;
		    Selector.loadData2(document.getElementById("ParentID"),MenuID );
		}
	}
	
	function onParentChange(){
		if($V("ParentID") != null && $V("ParentID") != ''){
			var dc = new DataCollection();
			dc.add("ParentID",$V("ParentID"));
			
			Server.sendRequest("com.sinosoft.platform.Button.getCode",dc,function(response){
				$S("URL", response.Message);
			});
		}
	}
	function selectIcon(){
		var diag = new Dialog("Diag2");
		diag.Width = 600;
		diag.Height = 300;
		diag.Title = "选择图标";
		diag.URL = "Platform/Icon.jsp";
		diag.OKEvent = afterSelectIcon;
		diag.show();
	}

	function afterSelectIcon(){
		if(!$DW.Icon){
			Dialog.alert("请选择菜单要使用的图标");
			return;
		}
		$("IconPic").src = $DW.Icon;
		$S("Icon",$DW.Icon);
		$D.close();
	}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<z:init method="com.sinosoft.platform.Button.init">
<body class="dialogBody">
<form id="form2">
	 <input name="ID" type="hidden" id="ID" value="${ID}"/>
	 <input name="OperType" type="hidden" id="OperType" value="${OperType}"/>
	<table width="100%" height="100%" border="0">
		<tr>
			<td>
				<table width="500"  align="center" cellpadding="2"　cellspacing="0" border="0">
					<tr>
						<td align="right" width="15%">上级菜单：</td>
						<td width="85%">
							<z:select id="MenuID"  name="MenuID"    onChange="onMenuChange()" >${TopMenu}</z:select>
				            <z:select id="ParentID" name="ParentID" onChange="onParentChange()" code="MenuChild" conditionField="Parent_ID" conditionValue="${Parent_ID}"  defaultblank="" lazy="true" verify="上级菜单|NotNull" >${ParentMenu}</z:select>
						</td>
					</tr>
					<tr>
						<td align="right">路径：</td>
						<td >
							 <input name="URL" verify="路径|NotNull" type="text" value="${URL}" style="width:242px" class="input1" id="URL" size=20 />
							 <br/>按钮在tab页签内,请输入页签的路径. 
						</td>
					</tr>	
					<tr>
						<td align="right">名称：</td>
						<td width="260">
							 <input name="Name" verify="名称|NotNull" type="text" value="${Name}" style="width:150px" class="input1" id="Name" size=15 /> 
							
						</td>
					</tr>
					<tr>
						<td align="right">图标：</td>
						<td><label><img src="../${Icon}" style="border:1px" onClick="selectIcon()" name="IconPic" width="24" height="24"
							align="absmiddle" id="IconPic">（单击选择图标）</label>
							<input type="hidden" name="Icon" id="Icon" value="${Icon}">
						</td>
					</tr>
					<tr>
						<td align="right">按钮类型：</td>
						<td>
							<z:select id="Type" name="Type" style="width:100px;" >${TypeList}</z:select>
						</td>
					</tr>
					
					<tr >
						<td align="right">按钮事件：</td>
						<td>
							<input name="OnClick"  type="text" value="${OnClick}"  class="input1" id="OnClick"  verify="按钮事件|NotNull" />
						</td>
					</tr>
					
					<tr >
						<td align="right">按钮ID：</td>
						<td>
							<input name="ButtonID"  type="text" value="${ButtonID}"  class="input1" id="ButtonID" />
						</td>
					</tr>
					
					<tr style="display: none;">
						<td align="right">是否可以点击：</td>
						<td>
							<input name="Disabled"  type="hidden" value="0"  class="input1" id="Disabled"  />
						</td>
					</tr>
					
					<tr>
						<td align="right">备注：</td>
						<td><input name="Memo" type="text" class="input1" id="Memo" value="${Memo}" size="30" /></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</body>
</z:init>
</html>

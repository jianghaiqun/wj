<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>元素详细信息</title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	function add(){
		DataGrid.insertRow("module_dginput");
	}
	function del(){
		var dg = $("module_dginput");
		var arr = dg.getSelectedRows();
		if(!arr||arr.length==0){
			Dialog.alert("请先选择要删除的列!");
			return;
		}
		Dialog.confirm("确认删除？",function(){
			for(var i=arr.length-1;i>=0;i--){
				dg.deleteRow(arr[i].rowIndex);
			}
		});
		
	}
	function save(){
		
		var dc = Form.getData("form2");
		var dt = getDataTable();
		dc.add("Id",$("Id").value); 
		dc.add("Data", dt);
		if(Verify.hasError()){
			return;
		}
		Server.sendRequest("com.sinosoft.module.Module.add3",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					//暂时不做任何处理
				}
			});
		});
	}
	function getDataTable(){
		var dg = $("module_dginput");
		var dt = dg.DataSource;
		var names = ["InfoID","InputType","InputCode","InputName","IsMustInput","InfoType","IsModify","ValidateInfo","Description","OrderFlag" ,"DataNode","Prop1","Prop2"];
		var map = {};
		for(var i=0;i<names.length;i++){
			var eles = $N(names[i]);
			var arr = [];
			if(eles!=null){
				for(var j=0;j<eles.length;j++){
					arr.push($V(eles[j]));
				}
			}
			map[names[i]] = arr;
		}
		for(var i=0;i<dt.getRowCount();i++){
			for(var j=0;j<names.length;j++){
				dt.Rows[i].set(names[j],map[names[j]][i]);
			}
		}
		return dt;
	}
	</script>
	</head>
	<body class="dialogBody">
	<z:init method="com.sinosoft.module.Module.initModuleList1">
	
	
	<form id="form2">

<input name="Id" type="hidden" id="Id" value="${Id}"/>

	<z:datagrid id="module_dginput" method="com.sinosoft.module.Module.dg3DataBind">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td style="display:none;"></td>
                  <td align="center" width="5%"><strong>归属</strong></td>
                  <td align="center" width="10%"><strong>元素编码</strong></td>
                  <td align="center" width="15%"><strong>元素名称</strong></td>
                  <td align="center" width="15%"><strong>校验信息</strong></td>
                  <td align="center" width="10%"><strong>必录</strong></td>
                  <td align="center" width="10%"><strong>可编辑</strong></td>
                  <td align="center" width="15%"><strong>输入项类型</strong></td>
                  <td align="center" width="15%"><strong>单位说明</strong></td>
                  <td align="center" width="10%"><strong>元素排序</strong></td>
                  <td align="center" width="14%"><strong>数据集名称</strong></td>
                  <td align="center" width="14%"><strong>元素样式</strong></td>
                  <td align="center" width="14%"><strong>元素提示</strong></td>
                </tr>
                <tr>
                  <td align="center">&nbsp;</td>
                  <td align="center">&nbsp;</td>
                  <td align="center" style="display:none;"><input name="infoid" type="hidden" id="infoid" value="${infoID}" size="32"></td>
                  <td align="center"><input name="InputType" type="text" id="InputType" value="${InputType}" verify="归属|NotNull&&Length<32" style="width:100%;" ></td>
                  <td align="center"><input name="InputCode" type="text" id="InputCode" value="${InputCode}" verify="元素编码|NotNull&&Length<80" style="width:100%;" ></td>
                  <td align="center"><input name="InputName" type="text" id="InputName" value="${InputName}" verify="元素名称|NotNull&&Length<50" style="width:100%;" ></td>
                  <td align="center"><input name="ValidateInfo" type="text" id="ValidateInfo" value="${ValidateInfo}" verify="校验信息|Length<100" style="width:100%;" ></td>
                  <td align="center"><z:select  name="IsMustInput" style="width:100%;" value="${IsMustInput}" verify="是否必录|NotNull">${@IsMustInput}</z:select></td>
                  <td align="center"><z:select  name="IsModify" style="width:100%"  value="${IsModify}" verify="是否可编辑|NotNull">${@IsModify}</z:select></td>
                  <td align="center"><z:select  name="InfoType" style="width:100%"  value="${InfoType}" verify="输入项类型|NotNull">${@InfoType}</z:select></td>
                  <td align="center"><input name="Description" type="text" id="Description" value="${Description}" style="width:100%;" ></td>
                  <td align="center"><input name="OrderFlag" type="text" id="OrderFlag" value="${OrderFlag}" style="width:100%;" ></td>
                  <td align="center"><input name="DataNode" type="text" id="DataNode" value="${DataNode}" verify="数据集名称|Length<50" style="width:100%;" ></td>
                  <td align="center"><input name="Prop1" type="text" id="Prop1" value="${Prop1}" verify="元素样式|Length<50" style="width:100%;" ></td>
                  <td align="center"><input name="Prop2" type="text" id="Prop2" value="${Prop2}" verify="元素提示|Length<50" style="width:100%;" ></td>
                </tr>
              </table>
            </z:datagrid>
     <z:tbutton onClick="add()"><img src="../Icons/icon005a2.gif" width="20" height="20"/>添加</z:tbutton>
     <z:tbutton onClick="del()"><img src="../Icons/icon005a4.gif" width="20" height="20"/>删除</z:tbutton>
     <z:tbutton onClick="save()"><img src="../Icons/icon005a4.gif" width="20" height="20"/>保存</z:tbutton>
     
    </form>
    </z:init>
	</body>
	</html>

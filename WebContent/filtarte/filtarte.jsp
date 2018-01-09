<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>筛选管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

	function ProAllCondtion() {
		Dialog.confirm("您确认要生成条件组合吗，生成时间可能较长？", function() {
			Server.sendRequest("com.sinosoft.product.Filtrate.ProAllCondtion",
					null, function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在生成...");
						p.show();
					});
		});
	}
	
	function ProAllProduct() {
		Dialog.confirm("您确认要生成全部筛选产品吗，生成时间可能较长？", function() {
			Server.sendRequest("com.sinosoft.product.Filtrate.ProAllProduct",
					null, function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在生成...");
						p.show();
					});
		});
	}

	document.onkeydown = function(event) {
		event = getEvent(event);
		if (event.keyCode == 13) {
			var ele = event.srcElement || event.target;
			if (ele.id == 'SearchID' || ele.id == 'SearchName'
					|| ele.id == 'ERiskSubType' || ele.id == 'Submitbutton') {
				search();
			}
		}
	}

	function search() {
		DataGrid.setParam("filtrate_dg1", Constant.PageIndex, 0);
		DataGrid.setParam("filtrate_dg1", "SearchID", $V("SearchID"));
		DataGrid.setParam("filtrate_dg1", "SearchName", $V("SearchName"));
		DataGrid.setParam("filtrate_dg1", "ERiskSubType", $V("ERiskSubType"));
		DataGrid.loadData("filtrate_dg1");
	}

	function synProductBySelect(){
		var arr = DataGrid.getSelectedValue("filtrate_dg1");
		if(!arr||arr.length==0){
			Dialog.alert("先选择要生成的数据!");
			return;
		}
		Dialog.confirm("确认要生成？",function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Dialog.wait("正在生成...");
			Server.sendRequest("com.sinosoft.product.Filtrate.synProductByOne",dc,function(response){
				Dialog.closeEx();
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						DataGrid.loadData("filtrate_dg1");
					}
				});
			});
		});
	}

	function synProductByAll() {
		var dc = new DataCollection();
		dc.add("SearchID", $V("SearchID"));
		dc.add("SearchName", $V("SearchName"));
		dc.add("ERiskSubType", $V("ERiskSubType"));
		
		Dialog.confirm("您确认要生成全部结果筛选产品吗，生成时间可能较长？", function() {
			Server.sendRequest("com.sinosoft.product.Filtrate.synProductByAll",
					dc, function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在生成...");
						p.show();
					});
		});
	}
	
</script>
</head>
<body>
<z:init method="com.sinosoft.product.Filtrate.init">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td>
	      <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
	          <tr>
	            <td valign="middle" class="blockTd"><img src="../Icons/icon022a1.gif" width="20" height="20" />筛选管理</td>
	          </tr>
					<tr>
						<td style="padding:0 8px 4px;">
	              		    <z:tbutton onClick="ProAllCondtion()"><img src="../Icons/icon022a4.gif" width="20" height="20"/>生成全部条件组合</z:tbutton>
						    <z:tbutton onClick="ProAllProduct()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>生成全部筛选产品</z:tbutton>
		            	</td>
		           </tr>
	      </table>
      </td>
    </tr>
    
   <tr>
		<td style="padding:2px 10px;">
			<div style="float:left;"> 
				 筛选组合编码&nbsp;<input name="SearchID" type="text" id="SearchID"> &nbsp;
				 筛选条件&nbsp;<input name="SearchName" type="text" id="SearchName"> &nbsp;
				 所属分类&nbsp;<z:select id='ERiskSubType'>${ERiskSubTypeList}</z:select>&nbsp;
				<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				<input type="button" name="synProductBySelect" id="synProductBySelect" value="生成选中筛选产品" onClick="synProductBySelect()">
				<input type="button" name="synProductByAll" id="synProductByAll" value="生成全部查询筛选产品" onClick="synProductByAll()">
				
		    </div>
		</td>
	</tr>
			
     <tr>   
        <td style="padding:0px 5px;">
			<z:datagrid id="filtrate_dg1" method="com.sinosoft.product.Filtrate.dg1DataBind"  size="15" autoFill="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="35%"><strong>筛选组合编码</strong></td>
                  <td width="35%"><strong>筛选条件</strong></td>
                  <td width="5%"><strong>所属分类</strong></td>
                  <td width="5%"><strong>产品个数</strong></td>
                  <td width="10%"><strong>创建时间</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" ondblclick="edit();">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${SearchID}</td>
                  <td>${SearchName}</td>
                  <td>${ERiskSubTypeName}</td>
                  <td>${ProductCount}</td>
                  <td>${CreateDate}</td>
                </tr>
                <tr ztype="pagebar">
					<td colspan="6" align="left">${PageBar}</td>
				</tr>
              </table>
            </z:datagrid>
           </td>
       </tr>
  </table>
 </z:init>
</body>
</html>

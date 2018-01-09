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
			Dialog.confirm("确认同步全部产品吗，同步时间可能较长?",function(){
				var dc = new DataCollection();
				Dialog.wait("正在生成...");
				Server.sendRequest("com.sinosoft.product.Filtrate.productSynchroAll",dc,function(response){
					Dialog.closeEx();
						if(response.Status==1){
							DataGrid.loadData("filtrate_dg1");
						}
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
<z:init method="com.sinosoft.product.Filtrate.initProductSynchro">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td>
	      <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
	          <tr>
	          </tr>
					<tr>
						<td style="padding:0 8px 4px;">
		            	</td>
		           </tr>
	      </table>
      </td>
    </tr>
    
   <tr>
		<td style="padding:2px 10px;">
			<div style="float:left;"> 
				<input type="button" name="synProductBySelect" id="synProductBySelect" value="全部产品信息同步" onClick="synProductBySelect()">
				
		    </div>
		</td>
	</tr>
			
     <tr>   
        <td style="padding:0px 5px;">
			<z:datagrid id="filtrate_dg1" method="com.sinosoft.product.Filtrate.dg1DataBindTb"  size="15" autoFill="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="15%"><strong>产品ID</strong></td>
                  <td width="20%"><strong>产品名称</strong></td>
                  <td width="35%"><strong>产品链接</strong></td>
                  <td width="30%"><strong>创建时间</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" ondblclick="edit();">
                  <td>&nbsp;</td>
                  <td>${productId}</td>
                  <td>${productName}</td>
                  <td>${URL}</td>
                  <td>${createDate}</td>
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>订单信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
//订单查询
function doSearch(){  
	var obj=document.getElementsByName('checkItem');
	var s=''; 
	for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked) s+=obj[i].value+',';
	} 
	if(s==''){
		alert('请在美行保和 网站联盟中至少选择一个平台');
		return;
	}
	DataGrid.setParam("dg1","startDate",$V("startDate"));
	DataGrid.setParam("dg1","channel",s);
	DataGrid.setParam("dg1","endDate",$V("endDate"));
	DataGrid.setParam("dg1","IntermediaryMember",$V("IntermediaryMember"));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
	var dc = new DataCollection();
	dc.add("startDate",$V("startDate"));
	dc.add("channel",s);
	dc.add("endDate",$V("endDate"));
	dc.add("IntermediaryMember",$V("IntermediaryMember"));
	dc.add("dg2",Constant.PageIndex,0);
	Server.sendRequest("com.wangjin.cms.orders.CPSQueryOrders.ordersAnlySum",dc,function(data){
		jQuery.each(jQuery('b.sumBTag'),function(i,o){
			var text = data.map.Table.Values[0][i];
			jQuery(o).text(text).attr('title',text);
		});
	});
}
function exportExcel(){
	var obj=document.getElementsByName('checkItem');
	var s=''; 
	for(var i=0; i<obj.length; i++){ 
		if(obj[i].checked) s+=obj[i].value+',';
	} 
	if(s==''){
		alert('请在美行保和 网站联盟中至少选择一个平台');
		return;
	}
	//提示后台是导出excel操作
	var ele = $('dg1');
	var diag = new Dialog("选择要导出的列","./Framework/Controls/DataGridToExcelDialog.html?DataGridID=dg1",500,150);
	diag.OKEvent = function(){
		var columns = $DW.$N("Column");
		var columnIndexes = [],columnWidths = [];
		for(var i=0;i<columns.length;i++){
			if(columns[i].checked){
				columnIndexes.push(columns[i].value);
				columnWidths.push($(columns[i]).$A("columnWidth"));
			}
		}
		$D.close();
		var xls = "_Excel_";
		var doc = window.document.body;
		var f = $(xls+"_Form");
		if(f){
			f.outerHTML = "";//要清空上次导出设置
		}
		f  = document.createElement("form");
		doc.appendChild(f);
		f.id = xls+"_Form";
		f.method="post";
		f.action = Server.ContextPath+"Framework/Controls/DataGridToExcel.jsp";
		var inputs = ele.Params.keys;
		for(var i = 0;i<inputs.length;i++){
			var input = $(xls+inputs[i]);
			if(!input){
				input  = document.createElement("input");
				
			}
			input.type="hidden";
			input.id =xls+inputs[i];
			input.name = xls+inputs[i];
			input.value=DataGrid.getParam(ele,inputs[i]);
			f.appendChild(input);
		}
		
		var input = $(xls+"_ZVING_ToExcelPageFlag");
		if(!input){
			input  = document.createElement("input");
			
		}
		input.type = "hidden";
		input.id =xls+"_ZVING_ToExcelPageFlag";
		input.name = xls+"_ZVING_ToExcelPageFlag";
		input.value= "1";
		f.appendChild(input);

		input = $(xls+"_ZVING_Widths");
		if(!input){
			input  = document.createElement("input");
		}
		input.type="hidden";
		input.id =xls+"_ZVING_Widths";
		input.name = xls+"_ZVING_Widths";
		input.value = columnWidths.join();
		f.appendChild(input);
		
		input = $(xls+"_ZVING_Indexes");
		if(!input){
			input  = document.createElement("input");
		}
		input.type="hidden";
		input.id =xls+"_ZVING_Indexes";
		input.name = xls+"_ZVING_Indexes";
		input.value = columnIndexes.join();
		f.appendChild(input);
		
		input = $(xls+"_isExportExcel");
		if(!input){
			input  = document.createElement("input");
		}
		input.type="hidden";
		input.id =xls+"_isExportExcel";
		input.name = xls+"_isExportExcel";
		input.value = "1";
		f.appendChild(input);
		f.submit();
	}
	diag.addParam("DataGridID",ele.id);
	diag.show();
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="bottom">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 分销订单数据分析</td>
		          </tr>
				<tr>
				<td>
				<z:init method="com.wangjin.cms.orders.CPSQueryOrders.init">
				<input type="hidden" id="mediamanager" name="mediamanager" value="${mediamanager}"/>
					 <table  cellspacing="0" cellpadding="3" width="60%">
						<tr>
							<td>业务平台：</td>
							<td> 
								<input id="b2b_radio" class="inputRadio" type="radio" value="b2b" name="checkItem" checked>
								<label for="b2b_radio">美行保</label>
								<input id="cps_radio" class="inputRadio" type="radio" value="cps" name="checkItem">
								<label for="cps_radio">网站联盟</label>
								<!-- <input id="agent_radio" class="inputRadio" type="checkbox" value="agent" name="checkItem" disabled="disabled">
								<label for="agent_radio">网金所</label> -->
							</td>
						</tr>
						<tr><td></td></tr>
						<tr>
							<td>媒介经理：</td>
							<td> <input name="IntermediaryMember" type="text" id="IntermediaryMember" value="" style="width:100px" />
		                	<td>统计时间 从：</td>
		                	<td> <input name="startDate" type="text" id="startDate" value="${yesterday}" style="width:100px" ztype="date"></td>
							<td>至：</td>
							<td><input name="endDate" type="text" id="endDate" value="${yesterday}"style="width:100px" ztype="date"></td>
							<td colspan="8" nowrap>
			            	   <z:tbutton onClick="doSearch();" id="doSearch"><img src="../Icons/icon021a4.gif" width="20" height="20" />查询</z:tbutton>
		            	   		<z:tbutton onClick="exportExcel();" id="exportExcel"><img src="../Icons/icon003a20.gif" width="20" height="20" />导出EXCEL</z:tbutton>
		            	   		</td>
						</tr>
		             </table>
		             </z:init>
				</td>
			</tr>
			  <tr>
				<td style="padding: 0px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:0px;padding-right:0px;padding-bottom:0px;">
					<z:datagrid id="dg1" method="com.wangjin.cms.orders.CPSQueryOrders.ordersAnly" size="20" scroll="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="480px" >
							<tr ztype="head" class="dataTableHead" >
								<td   ><b>合作账号</b></td>
								<td style="width:120px;" ><b>订单号</b></td>
								<td style="width:180px;" ><b>产品</b></td>
								<td   ><b>保费</b></td>
								<td style="width: 90px;"  ><b>公司手续费比例</b></td>
								<td  style="width: 75px;" ><b>手续费净收入</b></td>
								<td   ><b>原价</b></td>
								<td   ><b>现价</b></td>
								<td  style="width: 100px;" ><b>合作方手续费比例</b></td>
								<td  ><b>优惠</b></td>
								<td style="width: 80px;" ><b>结算服务费</b></td>
								<td  ><b>备注</b></td>
								<td  ><b>提奖比例</b></td>
								<td  ><b>提奖金额</b></td>
								<td   ><b>总成本</b></td>
								<td   ><b>利润</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td  title="${userid}">${userid}</td>
								<td style="width:120px;" title="${orderSn}">${orderSn}</td>
								<td style="width:180px;" title="${productName}">${productName}</td>
								<td  title="${productPrice}">${productPrice}</td>
								<td  title="${Rate}">${Rate}</td>
								<td  title="${income}">${income}</td>
								<td  title="${oldprice}">${oldprice}</td>
								<td  title="${nowprice}">${nowprice}</td>
								<td  title="${hzfrate}">${hzfrate}</td>
								<td  title="${discount}">${discount}</td>
								<td  title="${balance}">${balance}</td>
								<td  title="${remark}">${remark}</td>
								<td  title="${extractrate}">${extractrate}</td>
								<td  title="${extractaccount}">${extractaccount}</td>
								<td  title="${totalcost}">${totalcost}</td>
								<td  title="${profit}">${profit}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="16">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
					</tr>
				  </table>
				</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
			<div style="padding:0 6px;position: relative;top:-17px;">
		<table width="100%"  class="dataTable" style="text-align: center;table-layout : fixed;margin:0;" >
			<tr ztype="head" class="dataTableHead" >
				<td   ><b>合计&nbsp;&nbsp;</b></td>
				<td style="width:120px;" ><b>&nbsp;&nbsp;&nbsp;</b></td>
				<td style="width:180px;"><b>&nbsp;&nbsp;</b></td>
				<td   ><b class="sumBTag" id="sumproductprice"></b></td>
				<td  style="width: 90px;" ><b>&nbsp;</b></td>
				<td  style="width: 75px;" ><b class="sumBTag" id="sumincome"></b></td>
				<td   ><b class="sumBTag" id="sumoldprice"></b></td>
				<td   ><b class="sumBTag" id="sumnowprice"></b></td>
				<td  style="width: 100px;" ><b>&nbsp;</b></td>
				<td  ><b>&nbsp;</b></td>
				<td style="width: 80px;" ><b>&nbsp;</b></td>
				<td  ><b>&nbsp;</b></td>
				<td  ><b>&nbsp;</b></td>
				<td  ><b class="sumBTag" id="sumextractaccount"></b></td>
				<td   ><b class="sumBTag" id="sumtotalcost"></b></td>
				<td   ><b class="sumBTag" id="sumprofit"></b></td>
			</tr>
		</table>
	</div>
	
	<script type="text/javascript">
	var mediamanager = document.getElementById("mediamanager").value;
	if("Y" == mediamanager){
		document.getElementById("IntermediaryMember").disabled = true;
	}
	
	</script>
</body>
</html>

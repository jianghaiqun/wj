<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	function checkLen(obj) {
		var maxChars = 200;//最多字符数 
		if (obj.value.length > maxChars)
			obj.value = obj.value.substring(0, maxChars);
		var curr = maxChars - obj.value.length;
		document.getElementById("count").innerHTML = curr.toString();
	}
	//保全记录添加
	function addRemark() {
		var rem = $V("rem");
		if(""==$V("rem")){
			Dialog.alert("保全记录不能为空！");
			return;
		}
		var dc = Form.getData("fm_reply");
		Server.sendRequest("com.wangjin.cms.orders.QueryOrders.checkRemark",dc,function(){
			var response = Server.getResponse();
			if(response.Status==2){
				Dialog.confirm("内容重复，继续添加吗?",function(){
					add();
					});
			}else{
				add();
			}
		});
	}
	
	function add(){
		$S("hidoperateType","add");
		var dc = Form.getData("fm_reply");
		Server.sendRequest("com.wangjin.cms.orders.QueryOrders.save",dc,function(response){
			Dialog.alert(response.Message,function(){
			if(response.Status==1){
				//$D.close();
				DataGrid.loadData("dg1");
				document.getElementById("remarkdefault").value=response.get("Group");
			}
			});
		});
		$S("rem","");
		document.getElementById("count").innerHTML=200;
	}
	function delRemark(){
		var orderSn=$V("orderSn");
		var id=$V("id");
		if($V("hidoperateType")!="didupdate"){
			Dialog.alert("请选择删除条目！");
			return;
		}
		Dialog.confirm("确定要删除此记录吗？",function(){
			var dc = new DataCollection();	
			dc.add("orderSn",orderSn);
			dc.add("id",id);
			Server.sendRequest("com.wangjin.cms.orders.QueryOrders.del",dc,function(response){
				if(response.Status==0){
					Dialog.alert(response.Message);
				}else{
					Dialog.alert(response.Message);
					DataGrid.loadData("dg1");
					document.getElementById("remarkdefault").value=response.get("Group");
				}
			});
			 $S("rem","");
			 document.getElementById("count").innerHTML=200;
			 document.getElementById("del").disabled=true;
		});
	}
	//保全记录修改
	function saveRemark(){
		var rem = $V("rem");
		if($V("hidoperateType")!="didupdate"){
			Dialog.alert("请选择修改条目！");
			return;
		}
		$S("hidoperateType","update");
		if(""==rem){
			Dialog.alert("保全记录不能为空！");
			return;
		}
		var dc = Form.getData("fm_reply");
		Server.sendRequest("com.wangjin.cms.orders.QueryOrders.update",dc,function(response){
			Dialog.alert(response.Message,function(){
			if(response.Status==1){
				//$D.close();
				DataGrid.loadData("dg1");
				document.getElementById("remarkdefault").value=response.get("Group");;
			}
			});
		});
		 $S("rem","");
		 document.getElementById("count").innerHTML=200;
	}
	
	function setValue(){
		$S("hidoperateType","didupdate");
		$S("rem", "");
		var re = $V("remarkdefault");
		var dr = DataGrid.getSelectedData("dg1").Rows[0];
		Form.setValue(DataGrid.getSelectedData("dg1").Rows[0], "fm_reply");
		var r = dr.get("OperateTime");
		$S("rem", dr.get("remark"));
		checkLen(document.getElementById("rem"));
		 if(r!=re){
			 document.getElementById("rem").disabled=true;
			 document.getElementById("del").disabled=true;
			 document.getElementById("che").disabled=true;
			 document.getElementById("add").disabled=true;
			 document.getElementById("save").disabled=true;
		 }else{
			 document.getElementById("rem").disabled=false;
			 document.getElementById("del").disabled=false;
			 document.getElementById("che").disabled=false;
			 document.getElementById("add").disabled=false;
			 document.getElementById("save").disabled=false;
		 }
	}
</script>
</head>
<body class="dialogBody">
		<table width="100%" border="0" cellspacing="0" cellpadding="3"
			class="blockTable">
			<tr>
				<td
					style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
					<z:datagrid id="dg1"
						method="com.wangjin.cms.orders.QueryOrders.baoquan" size="5"
						scroll="false">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable"
							style="text-align: center; table-layout: fixed;"
							fixedHeight="480px">
							<tr ztype="head" class="dataTableHead">
								<td width="150" style="text-align: center;"><b>保全记录</b>
								</td>
								<td width="80" style="text-align: center;"><b>操作用户</b>
								</td>
								<td width="120" style="text-align: center;"><b>修改时间</b>
								</td>
								<td width="120" style="text-align: center;"><b>操作</b>
								</td>
							</tr>
							<tr style="background-color: #F9FBFC" onclick="setValue()">
								<td style="text-align: center;" title="${remark}">${remark}</td>
								<td style="text-align: center;" title="${OperateName}">${OperateName}</td>
								<td style="text-align: center;" title="${OperateTime}">${OperateTime}</td>
								<td style="text-align: center;" title="${OperateType}">${OperateType}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid>
				</td>
			</tr>
		</table>
		<z:init method="com.wangjin.cms.orders.QueryOrders.baoquanModi">
          <form name="fm_reply" id="fm_reply">
			<table width="100%" cellpadding="2" cellspacing="0"
				class="blockTable" id="remark">
				<tr height="200px">
					<td align="center"><textarea id="rem"
							style="width: 300px; height: 100px" onkeyup="checkLen(this)"></textarea>
						<div id="che">
							您还可以输入 <span id="count">200</span> 个文字
						</div></td>
					<td>
					<input type="hidden" id="orderSn" value="${orderSn}" /> 
					<input type="hidden" id="id" value="${id}" /> 
					<input type="hidden" id="hidoperateType" value="add" />
					<input type="hidden" id="OperateName" value="${OperateName}" />
					<input type="hidden" id="remarkdefault" value="${remarkdefault}" />
					</td>
				</tr>
				<tr>
					<td style="text-align: center;"><input id="add" name="add" onClick="addRemark();"
						type='button' value='添加'>
						<input id="del" name="del" onClick="delRemark();"
						type='button' value='删除'>
						<input id="save" name="save" onClick="saveRemark();"
						type='button' value='修改'>
					</td>
				</tr>
			</table>
           </form>
		</z:init>
	
</body>
</html>
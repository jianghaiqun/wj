<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script type="text/javascript">

function checkPWD(){
	var password=$V("NewPassword");
	var confirmPassword=$V("ConfirmPassword");
	if(password==confirmPassword){
		return true;
	} else{
		return false;
	}
}
function edit1(id){
    var arr = DataGrid.getSelectedValue("dg1");
	if(id&&id!=""){
		arr = new Array();
		arr[0] = id;
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的条目！");
		return;
	}
	if(!arr||arr.length>=2){
		Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
		return;
	}
	var diag = new Dialog("Diag2");
	diag.Width = 900;
	diag.Height = 240;
	diag.Title = "会员信息修改";
	diag.URL = "DataService/MemberOrderEditDialogtestdetail.jsp?id="+arr[0];
     diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改订单条目信息 ";
	//diag.Message = "修改订单条目信息 ";
	 diag.show();
}
function editSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("id",$DW.$NV("id"));
	//var orderSn1=$DW.$NV("orderSn");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.dg1Edit4",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message);
		if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
		}
	});
}
</script>

</head>
<body class="dialogBody">

	<form id="form2">
	  
	<table width="100%" cellpadding="2" cellspacing="0"  class="blockTable" >
	
	            
	             <tr><table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			     <tr>
		              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 产品信息</td>
		          </tr>
			
				 
				<tr>
					<td style="padding:8px 10px;">
                   
            
					</td>
				</tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.Member.collectInquery" size="10">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="10%" ztype="RowNo"><strong>序号</strong></td>
								
								<td width="20%"><b>产品名称</b></td>
								<td width="20%"><b>保险公司</b></td>
								<td width="20%"><b>产品类型</b></td>
								<td width="20%"><b>收藏时间</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="10%">&nbsp;</td>
							
								<td>${pname}</td>
								<td>${iname}</td>
								<td>${pgenera}</td>
								<td>${cdate}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
							
						</table>
						
					</z:datagrid></td>
				</tr>
			</table>
	              </tr>
	
		</table>
		   
	</form>

</body>
</html>

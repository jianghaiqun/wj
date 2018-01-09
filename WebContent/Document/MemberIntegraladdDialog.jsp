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


function getUserList(){
	var diag = new Dialog("Diag2");
	diag.Width = 550;
	diag.Height = 400;
	diag.Title = "会员列表";
	diag.URL = "Document/MemberMesageDialog.jsp";
	diag.OKEvent = addUser;
	diag.show();
}

function addUser(){
	var dt = $DW.DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	var User;
	$S("memberId",drs[0].get("ID"));
	if(drs[0].get("UserName") == ""){
		if(drs[0].get("email") == ""){
			User = drs[0].get("mobileNO");
		}else{
			User = drs[0].get("email");
		}	
	}else{
		User = drs[0].get("UserName");
	}	
	$S("UserName",User);
	$D.close();;
}

</script>
</head>
<z:init method="com.sinosoft.message.MemberIntegral.initIntegralSource">
<body class="dialogBody">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
		<tr>
			<td width="492" height="10"></td>
			<td width="612"></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">用户名：</td>
			<td height="25">
            <input name="UserName" type="text" value="" style="width:150px" class="inputText" id="UserName" 
             type="text" size="30" verify="接收人|NotNull" condition="!$V('UserName')" element="chooseUser" readonly = "readonly" disabled="disabled"/> 
             <input type="hidden" id="memberId" name="memberId" >
			<span id="nameCheck"></span><input type="button" id="chooseUser"
				name="chooseUser" value="查找.." onClick="getUserList()"></td>
		</tr>
		
		<tr>
			<td height="25" align="right" class="tdgrey1" >积分：</td>
			<td height="25">
            <input name="Integral" type="text" value="" style="width:150px" class="inputText" id="Integral"  verify="积分|NotNull"/>
            </td>
		</tr>
		
		<tr>
			<td align="right">积分来源：</td>
			<td><z:select  id="Source" style="width:150px;" >
				${integralSource}
				</z:select>
			</td>
		</tr>
		
		<tr>
			<td align="right">积分状态：</td>
			<td><z:select style="width:150px;" >
				<select name="Status" id="Status">
                  <option value="0">正常</option>
                  <%--<option value="1">冻结 </option>--%>
                </select>
				</z:select>
			</td>
		</tr>
		<tr>
			<td align="right">积分收入/支出：</td>
			<td><z:select style="width:150px;" >
				<select name="Manner" id="Manner">
                  <option value="0">收入 </option>
                  <option value="1">支出 </option>
                </select>
				</z:select>
			</td>
		</tr>
		<tr>
			<td align="right">备注：</td>
			<td>
				<textarea id="description" verify="备注|NotNull"></textarea>
			</td>
		</tr>
	</table>
	</form>
</body>
</z:init>
</html>

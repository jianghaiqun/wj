<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>淘宝状态更新</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"  src="../wwwroot/kxb/js/min-jquery.js"></script>
<script type="text/javascript" src="../wwwroot/kxb/js/jquery.cookie.js"></script>
<script type="text/javascript" src="../wwwroot/kxb/js/jquery.form.js"></script>
<script src="../Framework/Main.js"></script>
<script>
function send(){
	var dc = Form.getData("form2");
	if(Verify.hasError()){
		return;
	}
	
	Dialog.confirm("确认是否更新？",function(){
		Dialog.wait("正在更新交易状态...");
		Server.sendRequest("com.sinosoft.cms.dataservice.TBStatusUpdate.tbStatusUpdate",dc,function(response){
			Dialog.closeEx();
			Dialog.alert(response.Message,function(){
				
			});
		});
	});
}
function TBComplete(){
	var dc = Form.getData("form2");
	Dialog.confirm("确认是否完善出单数据？",function(){
		Dialog.wait("正在完善出单数据...");
		Server.sendRequest("com.sinosoft.cms.dataservice.Retransmit.TBComplete",dc,function(response){
			Dialog.closeEx();
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
				}
			});
		});
	});
}
function TBDelete(){
	var dc = Form.getData("form2");
	Dialog.confirm("确认是否删除该订单？",function(){
		Dialog.wait("正在删除该订单...");
		Server.sendRequest("com.sinosoft.cms.dataservice.Retransmit.TBDelete",dc,function(response){
			Dialog.closeEx();
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
				}
			});
		});
	});
}

</script>
</head>
<body>
<form id="form2">
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	    <tr valign="top">
	      <td>
	      	<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
		          <tr>
		            <td valign="middle" class="blockTd"><img src="../Icons/icon022a1.gif" width="20" height="20" />淘宝状态更新</td>
		          </tr>
	           		<tr>
						<td align="center" width="12%">订单号：</td>
						<td><input name="OrderSn" type="text" class="input1" id="OrderSn" value="" size="30" verify="订单号|NotNull&&Length=16"/></td>
					</tr>
					<tr>
					<td  colspan="2" align="center">
							<z:tbutton onClick="TBDelete()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>淘宝订单删除（需要完善的订单）</z:tbutton>
						</td>
					</tr>
					<tr>
						<td  colspan="2" align="center">
							<z:tbutton onClick="TBComplete()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>淘宝出单数据完善</z:tbutton>
						</td>
					</tr>
					<tr>
						<td  colspan="2" align="center">
							<z:tbutton onClick="send()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>更新</z:tbutton>
						</td>
					</tr>
						
	     	 </table>
	      </td>
	    </tr>
	</table>
</form>
</body>
</html>

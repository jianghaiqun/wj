<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>校验信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />

<link rel="stylesheet" type="text/css" href="${staticPath}/style/commonality.css" />



<script type="text/javascript">
function validateData(){
var name=$("#realName").val();
var NO=$("#mobile").val();
if(name==""){
$.tip("真实姓名不能为空");
return false;
}
if(NO==""){
$.tip("手机号不能为空");
return false;
}
return true;
}
 
</script>
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header.shtml">
<div style="text-align:center;margin-top:30px;">
	<form action="lottery!toIndex.action" method="post" onsubmit="return validateData();">
		 <input type="hidden" name="orderNo" value="${orderNo}"/>
		<table width="300px;" >
			<tr  height="40px;">
				 <td width="30%" >真实姓名：&nbsp;</td>
				 <td width="70%">
				   <input type="text"  name="realName" id="realName"/>
				</td>
			</tr>
			<tr height="40px;"> 
				<td > 手机号：&nbsp;</td>
				<td>
					<input type="text"  name="mobile" id="mobile"/>
				</td>
			</tr>
			<tr  height="40px;">
				<td colspan='2'>
					<input type="submit" value="确认" class="headsearchbtn">
				</td>
			</tr>	
		</table>
	</form>
</div>
<#include "/wwwroot/kxb/block/kxb_footer.shtml">		
		
</body>
</html>
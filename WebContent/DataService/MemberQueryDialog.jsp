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

function init(){
	 var flag=$V("flag");
	 if(flag=="2"){
		 $("UserName").disable();
		 $("realName").disable();
		 $("sex").disable();
		 $("email").disable();
		 $("mobileNO").disable();
		 $("telephoneNO").disable();
		 $("IDType").disable();
		 $("IDNO").disable();
		 $("zipcode").disable();
		 $("QQNO").disable();
		 $("birthday").disable();
		 $("personalURL").disable();
		 $("location").disable();
		 $("address").disable();
		 $("faxNO").disable();
		 $("hobby").disable();
		 $("marriageStatus").disable();
		 $("registerIp").disable();
		 $("expiricalValue").disable();
		 $("VIPType").disable();
		 $("createDate").disable();
		 $("point").disable();
		 $("usedpoint").disable();
	 }
}

</script>
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.dataservice.Member.initDialog">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
		<tr>
			<td height="25" align="right" class="tdgrey1">用户名：</td>
			<td height="25">
			<input type="text" id="UserName" name="UserName" value="${UserName}" style="width:150px" />
			<span id="nameCheck"></span>
			</td>
	
			<td height="25" align="right" class="tdgrey1">真实姓名：</td>
			<td height="25">
            <input name="realName" type="text" value="${realName}" style="width:150px" class="inputText" id="realName" />
            </td>
            	<td height="25" align="right" class="tdgrey1">性别：</td>
			<td height="25">
			 <z:select id="sex" name="sex" style="width:150px" listWidth="150"> ${sex}</z:select>
            </td>
		</tr>
		 <tr>
		
		
			<td height="25" align="right" class="tdgrey1">电子邮箱：</td>
			<td height="25">
            <input name="email" type="text" value="${email}" style="width:150px" class="inputText" id="email" />
            <span id="emailCheck"></span>
            </td>
		
			<td height="25" align="right" class="tdgrey1">手机号：</td>
			<td height="25">
            <input name="mobileNO" type="text" value="${mobileNO}" style="width:150px" class="inputText" id="mobileNO"/>
            </td>
            <td height="25" align="right" class="tdgrey1">电话号码：</td>
			<td height="25">
            <input name="telephoneNO" type="text" value="${telephoneNO}" style="width:150px" class="inputText" id="telephoneNO" 
            />
            </td>
		</tr>
		<tr>
			
			<td height="25" align="right" class="tdgrey1">证件类型：</td>
			<td height="25">
            <z:select id="IDType" name="IDType" style="width:150px" listWidth="150"> ${IDType}</z:select>
            </td>
            <td height="25" align="right" class="tdgrey1">证件号码：</td>
			<td height="25">
             <input name="IDNO" type="text" value="${IDNO}" style="width:150px" class="inputText" id="IDNO" />
             <span id="IDNOCheck"></span>
            </td>
			<td height="25" align="right" class="tdgrey1">邮政编码：</td>
			<td height="25">
            <input name="zipcode" type="text" value="${zipcode}" style="width:150px" class="inputText" id="zipcode" />
            <span id="zipCheck"></span></td>
		</tr>
		<tr>
			
		  
			<td height="25" align="right" class="tdgrey1">QQ号：</td>
			<td height="25">
            <input name="QQNO" type="text" value="${QQNO}" style="width:150px" class="inputText" id="QQNO" />
            </td>
	
			<td height="25" align="right" class="tdgrey1">生日：</td>
			<td height="25">
            <input name="birthday" type="text" value="${birthday}" style="width:150px" class="inputText" id="birthday"  ztype="date"/>
            </td>
            	<td height="25" align="right" class="tdgrey1">个人网址：</td>
			<td height="25">
            <input name="personalURL" type="text" value="${personalURL}" style="width:150px" class="inputText" id="personalURL"  />
            </td>
		</tr>
		<tr>
		
	        <td height="25" align="right" class="tdgrey1">所在地：</td>
			<td height="25">
            <input name="location" type="text" value="${location}" style="width:150px" class="inputText" id="location" />
            </td>
			<td height="25" align="right" class="tdgrey1">联系地址：</td>
			<td height="25">
            <input name="address" type="text" value="${address}" style="width:150px" class="inputText" id="address"  />
            </td>
            <td height="25" align="right" class="tdgrey1">传真号：</td>
			<td height="25">
            <input name="faxNO" type="text" value="${faxNO}" style="width:150px" class="inputText" id="faxNO" />
            <span id="faxCheck"></span>
            </td>
		</tr>
		<tr>
			
	
			<td height="25" align="right" class="tdgrey1">爱好：</td>
			<td height="25">
            <input name="hobby" type="text" value="${hobby}" style="width:150px" class="inputText" id="hobby" />
            </td>
	
			<td height="25" align="right" class="tdgrey1">婚姻状况：</td>
			<td height="25">
            <z:select id="marriageStatus" name="marriageStatus" style="width:150px" listWidth="150"> ${marriageStatus}</z:select>
            </td>
            <td height="25" align="right" class="tdgrey1">注册IP：</td>
			<td height="25">
            <input name="registerIp" type="text" value="${registerIp}" style="width:150px" class="inputText" id="registerIp" verify="注册IP|NotNull" />
            </td>
		
		</tr>
		<tr>
			
            	<td height="25" align="right" class="tdgrey1">冻结积分：</td>
			<td height="25">
            <input name="point" type="text" value="${point}" style="width:150px" class="inputText" id="point" verify="积分|NotNull"/>
            </td>
            	<td height="25" align="right" class="tdgrey1">已用积分：</td>
			<td height="25">
            <input name="usedpoint" type="text" value="${usedpoint}" style="width:150px" class="inputText" id="usedpoint" />
            </td>
	
			<td height="25" align="right" class="tdgrey1">当前可用积分：</td>
			<td height="25">
            <input name="currentValidatePoint" type="text" value="${currentValidatePoint}" style="width:150px" class="inputText" id="currentValidatePoint" />
            </td>
            
		</tr>
		<tr>
		
		   <td height="25" align="right" class="tdgrey1">经验值：</td>
			<td height="25">
            <input name="expiricalValue" type="text"  value="${expiricalValue}" style="width:150px" class="inputText" id="expiricalValue" />
            </td>
		
            <td height="25" align="right" class="tdgrey1">会员类型：</td>
			<td height="25">
            <z:select id="VIPType" name="VIPType" style="width:150px" listWidth="150"> ${VIPType}</z:select>
			</td>
			<td height="25" align="right" class="tdgrey1">创建日期：</td>
			<td height="25">
            <input name="createDate" type="text" value="${createDate}" style="width:150px" class="inputText" id="createDate" ztype="date"/>
            </td>
		</tr>

		
     
	</table>
	 <input name="id" type="hidden" value="${id}" id="id" />
	 <input name="flag" type="hidden" value="${flag}" id="flag" />
	</form>
</z:init>
</body>
</html>

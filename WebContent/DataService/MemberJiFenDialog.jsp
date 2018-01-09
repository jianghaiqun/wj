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


//检测输入的登录名是否重名
	function checkName2(){
		var UserName=$V("UserName");
		var valid=/^([a-zA-Z_][a-zA-Z_0-9]{3,16})$/;
		if(!valid.test(UserName)){
			$("nameCheck").innerHTML="<font color='red'>用户名由4-16个英文字符、数字、下划线组成且不能以数字开头!</font>";
			return;
		}
		var dc=new DataCollection();
		dc.add("UserName",UserName);
		Server.sendRequest("com.sinosoft.cms.dataservice.Member.checkName2",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				$("nameCheck").innerHTML="<font color='red'>存在的登录名,请更换</font>";
				$S("UserName","");
				
			}else{
				$("nameCheck").innerHTML="<font color='red'></font>";
				}
		});
	}
//检测输入的会员ID号是否重名
	function checkName3(){
		var id=$V("id");
		var dc=new DataCollection();
		dc.add("id",id);
		Server.sendRequest("com.sinosoft.cms.dataservice.Member.checkName3",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				$("nameCheckid").innerHTML="<font color='red'>存在的会员ID号,请更换</font>";
				$S("id","");
				
			}else{
				$("nameCheckid").innerHTML="<font color='red'></font>";
				}
		});
	}
	function Transfer(){
		var location=$V("location");
		document.getElementById("address").value=location;
	}

	function checkEmail(){
		var email = $V("email");
		if(email == null || "" == email){
			return;
		}
		var dc=new DataCollection();
		dc.add("email",email);
		Server.sendRequest("com.sinosoft.cms.dataservice.Member.checkEmail",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				$("emailCheck").innerHTML="<font color='red'>已存在的E-Mail,请更换</font>";
				$S("email","");
			}else{
				$("emailCheck").innerHTML="<font color='red'></font>";
			}
		});
	}
	function validateZipCode(){
		var zipcode=$V("zipcode");
		var zip=/^[0-9]{6}$/;
		if(!zip.test(zipcode)){
			$("zipCheck").innerHTML="<font color='red'>邮政编码格式不对！</font>";
		}else{
			$("zipCheck").innerHTML="<font color='red'></font>";
		}
	}
	function cfaxNO(){
		var faxNO =$V("faxNO");
		if(!/^(\d{3}-\d{8}(-\d{1,4})?|\d{4}-\d{7,8}(-\d{1,4})?)(\/\d{3}-\d{8}(-\d{1,4})?|\/\d{4}-\d{7,8}(-\d{1,4})?)*$/.test(faxNO)){
			$("faxCheck").innerHTML="<font color='red'>传真格式不正确！</font>";
		}else{
			$("faxCheck").innerHTML="<font color='red'></font>";
		}
	}
	function checkPWD(){
		var password=$V("NewPassword");
		var confirmPassword=$V("ConfirmPassword");
		if(password==confirmPassword){
			return true;
		} else{
			return false;
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
			<input type="text" id="UserName" name="UserName" value="${UserName}" style="width:150px" onBlur="checkName2()"  verify="用户名由4-16个英文字符、数字、下划线组成且不能以数字开头|Regex=^([a-zA-Z_][a-zA-Z_0-9]{3,16})$"/>
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
            <input name="email" type="text" value="${email}" style="width:150px" class="inputText" id="email" onBlur="checkEmail()" verify="电子邮件|NotNull&&Email" />
            <span id="emailCheck"></span>
            </td>
		
			<td height="25" align="right" class="tdgrey1">手机号：</td>
			<td height="25">
            <input name="mobileNO" type="text" value="${mobileNO}" style="width:150px" class="inputText" id="mobileNO" verify="手机号必须是第一位为1，第二位为3-8的11位数字|Regex=^[1][3-8]\d{9}$" />
            </td>
            <td height="25" align="right" class="tdgrey1">电话号码：</td>
			<td height="25">
            <input name="telephoneNO" type="text" value="${telephoneNO}" style="width:150px" class="inputText" id="telephoneNO" 
            verify="3或4位区号+“-”+7或8位号码|Regex=\d{3,4}[-]\d{7,8}$"/>
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
            </td>
			<td height="25" align="right" class="tdgrey1">邮政编码：</td>
			<td height="25">
            <input name="zipcode" type="text" value="${zipcode}" style="width:150px" class="inputText" id="zipcode" onblur="validateZipCode()" verify="中国邮政编码为6位数字,且首位不能为0|Regex=^[1-9]\d{5}$"/>
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
            <input name="personalURL" type="text" value="${personalURL}" style="width:150px" class="inputText" id="personalURL" verify="网址格式不正确|Regex=^([hH]{1}[tT]{2}[pP]{1}\://)? [w{1,3}|W{1,3}]{3}\.\w+(\.[a-zA-Z]+)+$" />
            </td>
		</tr>
		<tr>
		
	        <td height="25" align="right" class="tdgrey1">所在地：</td>
			<td height="25">
            <input name="location" type="text" value="${location}" style="width:150px" class="inputText" id="location" onBlur="Transfer()" />
            </td>
			<td height="25" align="right" class="tdgrey1">联系地址：</td>
			<td height="25">
            <input name="address" type="text" value="${address}" style="width:150px" class="inputText" id="address"  />
            </td>
            <td height="25" align="right" class="tdgrey1">传真号：</td>
			<td height="25">
            <input name="faxNO" type="text" value="${faxNO}" style="width:150px" class="inputText" id="faxNO" onblur="cfaxNO()" verify="格式为区号-传真号|Regex=^(\d{3}-\d{8}(-\d{1,4})?|\d{4}-\d{7,8}(-\d{1,4})?)(\/\d{3}-\d{8}(-\d{1,4})?|\/\d{4}-\d{7,8}(-\d{1,4})?)*$"/>
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
			
            	<td height="25" align="right" class="tdgrey1">积分：</td>
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
            <input name="expiricalValue" type="text" value="${expiricalValue}" style="width:150px" class="inputText" id="expiricalValue" />
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
	 <input name="id" type="hidden" value="${id}" style="width:150px" class="inputText" id="id" />
	</form>
</z:init>
</body>
</html>

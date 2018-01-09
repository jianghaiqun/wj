<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
//检测输入的登录名是否重名
function checkName(){
	var UserName=$V("UserName");
	var valid=/^([a-zA-Z_][a-zA-Z_0-9]{3,16})$/;
	if(!valid.test(UserName)){
		$("nameCheck").innerHTML="<font color='red'>用户名由4-16个英文字符、数字、下划线组成且不能以数字开头!</font>";
		return;
	}
	var dc=new DataCollection();
	dc.add("UserName",UserName);
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.checkName",dc,function(){
		var response = Server.getResponse();
		if(response.Status==0){
			$("nameCheck").innerHTML="<font color='red'>已存在的登录名,请更换</font>";
			$S("UserName","");
		}else{
			$("nameCheck").innerHTML="<font color='red'></font>";
		}
	});
}

function validateRealName(){
	var realName=$V("realName");
	if(!/^(([\u0391-\uFFE5])*([a-zA-Z]*))$/.test(realName)){
		$("realCheck").innerHTML="<font color='red'>只允许包含中文、英文</font>";
        return;
	}
	
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

function checkIDNO(){
	var idno=$V("IDNO");
	var idtype=$V("IDType");
	var no=/^\d{17}(\d|x)$/i;
	if(!no.test(idno)&&idtype=="0"){
		$("IDNOCheck").innerHTML="<font color='red'>请输入有效的18位身份证号码！</font>";
	}else{
		$("IDNOCheck").innerHTML="";
	}
}
function checkPWD_R(){
	var Password=$V("Password");
	if(!/^((\d+[a-zA-Z_]\w*)|([a-zA-Z_]+\d\w*))$/.test(Password)){
		$("pwdCheck").innerHTML="<font color='red'>密码只能由6-16个英文、数字、及下划线任两个组合组成</font>";
	}else{
		$("pwdCheck").innerHTML= "";
	}
	
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
function checkMobile(){
	var mobileNO = $V("mobileNO");
	if(mobileNO == null || "" == mobileNO){
		return;
	}
	var dc=new DataCollection();
	dc.add("mobileNO",mobileNO);
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.checkMobile",dc,function(){
		var response = Server.getResponse();
		if(response.Status==0){
			$("mobileCheck").innerHTML="<font color='red'>已存在的手机号,请更换</font>";
			$S("mobileNO","");
		}else{
			$("mobileCheck").innerHTML="<font color='red'></font>";
		}
	});
}

function checkPWD(){
	var password=$V("Password");
	var confirmPassword=$V("ConfirmPassword");
	if(password==confirmPassword){
		return true;
	} else{
		return false;
	}
}
//检测输入的会员ID号是否重名
function checkName3(){
	var ID=$V("ID");
	var dc=new DataCollection();
	dc.add("ID",ID);
	Server.sendRequest("com.sinosoft.cms.dataservice.Member.checkName4",dc,function(){
		var response = Server.getResponse();
		if(response.Status==0){
			$("nameCheckid").innerHTML="<font color='red'>存在的会员ID号,请更换</font>";
			$S("ID","");
			
		}else{
			$("nameCheckid").innerHTML="<font color='red'></font>";
			}
	});
}
function Transfer(){
	var location=$V("location");
	document.getElementById("address").value=location;
}
rnd.today=new Date();
rnd.seed=rnd.today.getTime();
function rnd() {
　　　　rnd.seed = (rnd.seed*9301+49297) % 233280;
　　　　return rnd.seed/(233280.0);
};

function Random(){
	var number = 999999;
	var pwd = "1235456"
	pwd = Math.ceil(rnd()*number)+"erd";
	document.getElementById("Password").value = pwd;
	document.getElementById("ConfirmPassword").value = pwd;
}

function areaLink(){
	var value_id = $V("addressF");
	if (value_id != null) {
		Selector.loadData2(document.getElementById("location"), value_id);
	}
}
</script>

</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.dataservice.Member.initAddDialog">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
		<tr>
			<td width="492" height="10"></td>
			<td width="612"></td>
		</tr>
			
		<tr>
			<td height="25" align="right" class="tdgrey1">用户名：</td>
			<td height="25">
            <input name="UserName" type="text" value="" style="width:150px" class="inputText" id="UserName" onBlur="checkName()" verify="用户名由4-16个英文字符、数字、下划线组成且不能以数字开头!|Regex=^([a-zA-Z_][a-zA-Z_0-9]{3,16})$" />
			<span id="nameCheck"></span></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">密码：</td>
			<td height="25">
            <input name="Password" type="password" value="" style="width:150px" class="inputText" id="Password" onBlur="checkPWD_R()" 
            verify="密码只能由6-16个英文、数字、及下划线任两个组合组成|Regex=(\w+)&&NotNull&&Length>5&&Length<17" />
			<span id="pwdCheck"></span>
            <input type="button" id="random" value="随机" onclick="Random();">
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">重复密码：</td>
			<td height="25">
            <input name="ConfirmPassword" type="password" value="" style="width:150px" class="inputText" id="ConfirmPassword" verify="两次密码必须相同|Script=checkPWD()&&NotNull&&password" /></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">真实姓名：</td>
			<td height="25">
            <input name="realName" type="text" value="" style="width:150px" class="inputText" id="realName" onBlur="validateRealName()" 
            verify="真实姓名&&NotNull" /><span id="realCheck"></span>
            </td>
		</tr>
        <tr>
			<td height="25" align="right" class="tdgrey1">E-mail：</td>
			<td height="25">
            <input name="email" type="text" value="" style="width:150px" class="inputText" id="email" onBlur="checkEmail()" verify="电子邮件|NotNull&&Email" />
            <span id="emailCheck"></span>
            </td>
		</tr>
		 <tr>
			<td height="25" align="right" class="tdgrey1">手机号：</td>
			<td height="25">
            <input name="mobileNO" type="text" value="" style="width:150px" class="inputText" id="mobileNO" onBlur="checkMobile()" verify="手机号必须是第一位为1，第二位为3-8的11位数字|Regex=^[1][3-8]\d{9}$"/>
             <span id="mobileCheck"></span></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">电话：</td>
			<td height="25">
            <input name="telephoneNO" type="text" value="" style="width:150px" class="inputText" id="telephoneNO" verify="3或4位区号+“-”+7或8位号码|Regex=\d{3,4}[-]\d{7,8}$"/>
            </td>
		</tr>
		 <tr>
			<td height="25" align="right" class="tdgrey1">证件类型：</td>
			<td height="25">
			  <z:select id="IDType" name="IDType" style="width:150px" listWidth="150"> ${IDType}</z:select>
            </td>
		</tr>
		 <tr>
			<td height="25" align="right" class="tdgrey1">证件号码：</td>
			<td height="25">
            <input name="IDNO" type="text" value="" style="width:150px" class="inputText" id="IDNO" onBlur="checkIDNO()"/>
            <span id="IDNOCheck"></span>
            </td>
		</tr>
	    <tr>
			<td height="25" align="right" class="tdgrey1">QQ号码：</td>
			<td height="25">
            <input name="QQNO" type="text" value="" style="width:150px" class="inputText" id="QQNO" />
            </td>
		</tr>
		 <tr>
			<td height="25" align="right" class="tdgrey1">生日：</td>
			<td height="25">
            <input name="birthday" type="text" value="" style="width:150px" class="inputText" id="birthday" ztype="date" />
            </td>
		</tr>
		 <tr>
			<td height="25" align="right" class="tdgrey1">所在地：</td>
			<td height="25">
            <z:select id="addressF" code="areaF1" 
            	listWidth="80" style="width:80px" onChange="areaLink()" lazy="true"></z:select>
            <z:select id="location" name="location" code="areaS1" conditionField="parent_id" conditionValue="" 
            	listWidth="80" style="width:80px"  lazy="true"></z:select>
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">联系地址：</td>
			<td height="25">
            <input name="address" type="text" value="${location1}" style="width:150px" class="inputText" id="address" />
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">邮政编码：</td>
			<td height="25">
            <input name="zipcode" type="text" value="" style="width:150px" class="inputText" id="zipcode" onblur="validateZipCode()" verify="中国邮政编码为6位数字,且首位不能为0|Regex=^[1-9]\d{5}$"/>
            <span id="zipCheck"></span></td>
		</tr>
		 <tr>
			<td height="25" align="right" class="tdgrey1">个人网址：</td>
			<td height="25">
            <input name="personalURL" type="text" value="" style="width:150px" class="inputText" id="personalURL" />
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">会员类型：</td>
			<td height="25">
            <z:select id="VIPType" name="Type" style="width:150px" listWidth="150"> ${VIPType}</z:select>
			</td>
		</tr>
		<tr>
			<td height="25" align="right">性别：</td>
			<td height="25">
			   <z:select id="sex" name="Type" style="width:150px" listWidth="150"> ${sex}</z:select></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">婚姻状况：</td>
			<td height="25">
			 <z:select id="marriageStatus" name="marriageStatus" style="width:150px" listWidth="150"> ${marriageStatus}</z:select>
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">行业类别：</td>
			<td height="25">
            <input name="industryType" type="text" value="" style="width:150px" class="inputText" id="industryType" />
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">职位：</td>
			<td height="25">
            <input name="position" type="text" value="" style="width:150px" class="inputText" id="position" />
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">兴趣爱好：</td>
			<td height="25">
			 <input type="checkbox" name="hobby" value="网购">网购
			  <input type="checkbox" name="hobby" value="网游">网游
			  <input type="checkbox" name="hobby" value="健康">网游
			  <br>
			    <input type="checkbox" name="hobby" value="养老">养老
			    <input type="checkbox" name="hobby" value="理财">理财
			     <input type="checkbox" name="hobby" value="养生">养生
			     <br>
			      <input type="checkbox" name="hobby" value="教育">教育
			       <input type="checkbox" name="hobby" value="电玩">电玩
			        <input type="checkbox" name="hobby" value="运动">运动
			        <br>
				    <input type="checkbox" name="hobby" value="旅游">旅游
				     <input type="checkbox" name="hobby" value="美食">美食
				      <input type="checkbox" name="hobby" value="健身">健身
				        <br>
				          <input type="checkbox" name="hobby" value="瑜伽">瑜伽
				            <input type="checkbox" name="hobby" value="摄影">摄影
				              <input type="checkbox" name="hobby" value="棋牌">棋牌
				              <br>
				                <input type="checkbox" name="hobby" value="戏曲">戏曲
				                  <input type="checkbox" name="hobby" value="文学">文学
				                    <input type="checkbox" name="hobby" value="歌剧">歌剧
				                    <br>
				                      <input type="checkbox" name="hobby" value="美术">美术
				                        <input type="checkbox" name="hobby" value="医疗">医疗
				                          <input type="checkbox" name="hobby" value="乐器">乐器
				                          <br>
				                            <input type="checkbox" name="hobby" value="手工">手工
				                              <input type="checkbox" name="hobby" value="科学">科学
				                                <input type="checkbox" name="hobby" value="慈善">慈善
				                                <br>
				                                  <input type="checkbox" name="hobby" value="军事">军事
				                                    <input type="checkbox" name="hobby" value="休闲活动">休闲活动
				                                    <br>
				                                      <input type="checkbox" name="hobby" value="文艺活动">文艺活动
				                                        <input type="checkbox" name="hobby" value="其他">其他
				                                          
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">账号是否启用：</td>
			<td height="25">
			 <z:select id="isAccountEnabled" name="isAccountEnabled" style="width:150px" listWidth="150"> ${isAccountEnabled}</z:select>
 
            </td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">账号是否锁定：</td>
			<td height="25">
			 <z:select id="isAccountLocked" name="isAccountLocked" style="width:150px" listWidth="150"> ${isAccountLocked}</z:select>

            </td>
		</tr>
        ${Columns}
	</table>
	<input name="ip" type="hidden" value=<%=request.getRemoteAddr()%> style="width:150px" class="inputText" id="ip" />
	</form>
</z:init>
</body>
</html>
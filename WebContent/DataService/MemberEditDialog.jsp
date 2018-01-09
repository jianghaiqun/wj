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
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">

function init(){
	if (jQuery("#vipFlag").val()=='Y'){
		jQuery("#vipCheckBox").attr("checked", true);
	 }
	 var flag=$V("flag");
	 if(flag=="1"){
		 $("point").disable();
		 $("usedpoint").disable();
		 $("currentValidatePoint").disable();
	 }else if(flag=="0"){
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
		 $("point").disable();
		 $("usedpoint").disable();
		 $("currentValidatePoint").disable();
		 $("expiricalValue").disable();
		 $("VIPType").disable();
		 $("createDate").disable();
		 $("vipCheckBox").disable();
	 }else if(flag=="2"){
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
		 $("vipCheckBox").disable();
	 }
	 
	 
}

function checkVIP() {
	var check_flag=jQuery('#vipCheckBox').is(':checked');
	if(check_flag==true){
		jQuery("#vipFlag").attr("value","Y");
	} else {
		jQuery("#vipFlag").attr("value","N");
	}
}
//检测输入的登录名是否重名
	function checkName2(){
		var UserName=$V("UserName");
		var dc=new DataCollection();
		dc.add("UserName",UserName);
		if (UserName != null && UserName != '') {
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
	function areaLink(){
		var value_id = $V("addressF");
		if (value_id != null) {
			Selector.loadData2(document.getElementById("location"), value_id);
		}
	}
	
</script>

</head>
<body class="dialogBody">
<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
	<tr>
	<td valign="middle" class="blockTd">
	<img src="../Icons/icon018a6.gif" width="20" height="20" /> 订单信息</td>
	</tr>
	<tr>
	<td>
	<z:init method="com.sinosoft.cms.dataservice.Member.initDialog">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
		<tr>
			<td height="25" align="right" class="tdgrey1">用户名：</td>
			<td height="25">
			<input type="text" id="UserName" name="UserName" value="${UserName}" style="width:150px" onBlur="checkName2()" />
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
            <input name="email" type="text" value="${email}" style="width:150px" class="inputText" id="email" onBlur="checkEmail()" verify="电子邮件|Email" />
            <span id="emailCheck"></span>
            </td>
		
			<td height="25" align="right" class="tdgrey1">手机号：</td>
			<td height="25">
            <input name="mobileNO" type="text" value="${mobileNO}" style="width:150px" class="inputText" id="mobileNO"  />
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
             <input name="IDNO" type="text" value="${IDNO}" style="width:150px" class="inputText" id="IDNO" onBlur="checkIDNO()"/>
             <span id="IDNOCheck"></span>
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
            <z:select id="addressF"  
            	listWidth="80" style="width:80px" code="areaF1" onChange="areaLink()">${provinceList}</z:select>
            <z:select id="location" code="areaS1" conditionField="parent_id" conditionValue=""
            	listWidth="80" style="width:80px" >${cityList}</z:select>
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
		<tr>
			<td height="25" align="right" class="tdgrey1">VIP：</td>
			<td height="25"><input type="checkbox" onclick="checkVIP()" id="vipCheckBox"/><input name="vipFlag" id="vipFlag" value="${vipFlag}" type="hidden" /></td>
			<td height="25"></td>
			<td height="25"></td>
			<td height="25"></td>
			<td height="25"></td>
		</tr>	  
	</table>
	 <input name="id" type="hidden" value="${id}" id="id" />
	 <input name="hidlocation" type="hidden" value="${location}" id="hidlocation" />
	 <input name="flag" type="hidden" value="${flag}" id="flag" />
	</form>
</z:init>
				</td>
			</tr>
			  <tr>
				<td style="padding: 0px 5px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.cms.dataservice.Member.memberOrderInquery" size="10" scroll="true" >
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" style="text-align: center;table-layout : fixed;" fixedHeight="250px" >
							<tr ztype="head" class="dataTableHead">
								<td width="30" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						        <td width="120" style="text-align:center;"><b>订单编号</b></td>
						        <td width="50" style="text-align:center;"><b>投保人</b></td>
						         <td width="70" style="text-align:center;"><b>投保人性别</b></td>
						        <td width="70" style="text-align:center;"><b>被保险人数</b></td>
						        <td width="100" style="text-align:center;"><b>起保日期</b></td>
						        <td width="100" style="text-align:center;"><b>终止日期</b></td>
						        <td width="120" style="text-align:center;"><b>修改时间</b></td>
								<td width="150" style="text-align:center;"><b>产品名称</b></td>
								<td width="55" style="text-align:center;"><b>产品计划</b></td>
								<td width="50" style="text-align:center;"><b>已付费</b></td>
								<td width="50" style="text-align:center;"><b>实际支付</b></td>
								<td width="55" style="text-align:center;"><b>订单状态</b></td>
								
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="3%">&nbsp;</td>
								<td style="text-align:center;">&nbsp;</td>
								<td style="text-align:center;" title="${orderSn}">${orderSn}</td>
								<td style="text-align:center;" title="${ApplicantName}">${ApplicantName}</td>
								<td style="text-align:center;" title="${applicantSexName}">${applicantSexName}</td>
								<td style="text-align:center;" title="${recognizeeNu}">${recognizeeNu}</td>
								<td style="text-align:center;" title="${svalidate}">${svalidate}</td>
								<td style="text-align:center;" title="${evalidate}">${evalidate}</td>
								<td style="text-align:center;" title="${ModifyDate}">${ModifyDate}</td>
								<td title="${ProductName}">${ProductName}</td>
								<td title="${planName}">${planName}</td>
								<td style="text-align:right;" title="${notfee}">${notfee}</td>
								<td style="text-align:right;" title="${payPrice}">${payPrice}</td>
								<td style="text-align:center;" title="${orderstatusname}">${orderstatusname}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
								<input name="id" type="hidden" id="id" value="${id}" style="width:150px">
								
							</tr>
						</table>
					</z:datagrid></td>
					</tr>
				  </table>
				</td>
				</tr>
			</table>
</body>
</html>

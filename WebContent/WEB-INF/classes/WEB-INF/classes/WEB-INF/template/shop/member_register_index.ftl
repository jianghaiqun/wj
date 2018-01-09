<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员注册</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />

<link href="${shopStaticPath}/template/shop/css/StyCss.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/MemberCenterSty.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/IDvalidate.js"></script>
<script type="text/javascript">



</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>	
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">

<div class="wrapnormal">
<div class="sheetrig">
<br/>
<form id="registerWindowfmwgq" name="fm"  action="member!normalRegister.action" method="post" onsubmit = "return validatefm();" >
<input type='hidden' name='windowNewAddr' id='windowNewAddr' value='${windowAddr}'>
<table>
	<tr>
		<td><label><span style="color:red;">*</span>注册方式：</label></td>
		<td><input type="text" name="registerUserName" id="registerUserName1" onfocus="if(this.value=='请输入电子邮箱/手机号'){this.value='';}" onblur="if(this.value==''){this.value='请输入电子邮箱/手机号';};validateRegisterUserName1();"   value="请输入电子邮箱/手机号" style="width: 180px;font-size:12px;height:18px;margin-top:3px;" /><span id="status1" style="color:red;font-size: 12px;"></span>
		</td>
	</tr>
	<tr style="display:none;" id="ptzcsjhxs">
		<td><label>手机号：</label></td>
		<td><input type="text" name="member.mobileNO" id="ptzcsjh" onblur="ptzcsjhyz();" onclick="ptzcsjhss();" style="width: 180px;color: #666666" /><span id="status2" style="color:red;font-size: 13px;"></span>
		</td>
	</tr>
	
	<tr>
	    <td><label><span style="color:red;">*</span>验证码：</label></td>
		<td><input type="text" name="validateCode" id="validateCodesss" style="width: 100px;height:18px;" onblur="validate333();" value=""/>&nbsp;&nbsp;<input type="button" disabled="true" id="sendVC1" onclick="sendVCode1()" class="btnorange" style="font-size: 13px;" value="发送验证码"/><span id="validateCodeStatus" style="color:red;font-size: 13px;"></span>
		</td>
	</tr>
	<tr>
		<td><label><span style="color:red;">*</span>密&nbsp;&nbsp;&nbsp;码：</label></td>
		<td><input type="password"  id="registerWindowPasswordsss" class="inputstyle02" style="width: 180px;height:18px;" onclick = "wosi2();" name="member.password" onblur="validate222();"/><span id="registerWindowPasswordStatus" style="color:black;font-size: 13px;">密码只能由6-16个英文、数字、及下划线任两个组合组成</span>
		</td>
	</tr>
	<tr>
		<td><label><span style="color:red;">*</span>重复密码：</label></td>
		<td><input type="password" name="rePassword" class="inputstyle02" id="rePasswordsss" onclick = "wosi3();" style="width: 180px;height:18px;" onblur="validatePassword1();"  /><span id="rePasswordStatus" style="color:red; font-size: 13px;"></span>
		</td>
	</tr>
</table>

<table id="registerHide" style="display:none;">
		<tr><td><label>用户名：</label></td>
			<td><input type="text" name="member.username" id="UserName1" class="inputstyle02" style="width: 180px;" onclick = "wosi5();" onblur="validateUserName1();" /><span id="userNameStatus" style="color:red;font-size: 13px;"></span>
			</td>
		</tr>
		
		<tr><td><label>QQ号码：</label></td>
			<td><input type="text" class="inputstyle02" style="width: 180px;" name="member.QQNO" /></td>
		</tr>
		
		<tr><td><label>真实姓名：</label></td>
			<td><input type="text" class="inputstyle02" id="RealName" style="width: 180px;" onclick = "wosi6();"onblur="validateRealName();"name="member.realName" /><span id="vRealName" style="color:red;font-size: 13px;"></span>
			</td>
		</tr>
		
		<tr><td><label>证件类型：</label></td>
			<td><select name="member.IDType" id="sel"  style="width: 180px;" onchange="qtlx();" >
			<option value="-1">---请选择---</option>
	           <#if (listid != null && listid?size > 0)>
				  	<#list listid as list> <option value="${list.dictSerial}">${list.dictName}</option> </#list>
			   </#if>
			</select>
			</td>
		</tr>
		
		<tr><td><label>证件号码：</label></td>
			<td><input type="text" id="IDNO"  class="inputstyle02" style="width: 180px;" name="member.IDNO" onblur="sw();" /><span id="zjhmyz" style="color:red;font-size: 13px;"></span>
			</td>
		</tr>
		<tr><td><label>性别：</label></td>
			<td><input type="radio" name="member.sex" value="0" checked="checked"/>男&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="member.sex" value="1" />女
		</td></tr>
		
		<tr><td><label>出生日期：</label></td>
			 <td><input type="text" onfocus="WdatePicker({skin:'whyGreen',startDate:'1980-01-01'})" style="width: 180px;" class="inputstyle02" name="member.birthday" id="wall"/>
		</td></tr>
		
		<tr><td><label>所在地：</label></td>
			<td><input type="text" name="member.location"  style="width: 180px;"  class="areaSelect hidden { messagePosition: \'#areaMessagePosition\'}" /><span id="areaMessagePosition" ></span>
			</td></tr>
		<tr><td><label>联系地址：</label></td>
			<td><input type="text"  class="inputstyle02" style="width: 180px;" name="member.address"/>
			</td></tr>
		<tr><td><label>邮政编码：</label></td>
			<td><input type="text" class="inputstyle02" id="ZipCode" style="width: 180px;" onclick = "wosi4();" onblur="validateZipCode();" name="member.zipcode"/><span id="vZipCode" style="color:red;font-size: 13px;"></span>
			</td></tr>
		<tr><td><label>个人网址：</label></td>
			<td><input type="text" class="inputstyle02" style="width: 180px;" name="member.personalURL"/>
			</td></tr>
		<tr><td><label>电话：</label></td>
			<td><input type="text" class="inputstyle02" style="width: 180px;" onclick = "wosi();" id="dhhm" onblur="validatedhhm();" name="member.telephoneNO"/><span id="vdhhm" style="color:red;font-size: 13px;"></span>
			</td></tr>
		<tr><td><label>传真：</label></td>
			<td><input type="text" class="inputstyle02" style="width: 180px;" id="faxNO" onclick="wosi7();" onblur="made();" name="member.faxNO"/><span id="ml" style="color:red;font-size: 13px;"></span>
			</td></tr>
		<tr><td><label>婚姻状况：</label></td>
			<td><select name="member.marriageStatus"><option value="-1">请选择</option><option value="0">未婚</option><option value="1">已婚</option></select>
			</td></tr>
		<tr><td><label>职位：</label></td>
			<td><input type="text" class="inputstyle02" style="width: 180px;" name="member.position"/>
			<input type="hidden" name="member.VIPFrom" value="0"/>
			<input type="hidden" name="serialNO" value="${serialNO}"/>
			<input type="hidden" name="codeType" value="${codeType}"/>
			</td></tr>
		<tr><td><label>会员类型：</label></td>
			<td><select name="member.VIPType" ><option value="">请选择</option><option value="Person">个人</option><option value="Company">团体</option></select>
			</td></tr>
		<tr><td> <label>兴趣爱好：</label></td>
		    <td>
		    	 <table>
		    	 <#assign index = 0>
		    	 <#list listHobby as list>
			        <#if (index%8==0)>
			        <tr>
		            </#if>
		             <td style="font-size: 12px;">
					   <input type="checkbox" name="love" value="${list.dictSerial}" <#list listLove as le ><#if (list.dictSerial==le.dictSerial)!>checked</#if> </#list>/>
					   ${list.dictName}&nbsp;
					 </td>
				  <#if (index%8==7)>
		            </tr>
		          </#if>
		     	<#assign index=index+1 />
				   </#list>
		    	 <!-- tr>
	       <td><input type="checkbox" name="love" value="0"/>&nbsp;网购&nbsp; </td>
	       <td><input type="checkbox" name="love" value="1" />&nbsp;网游&nbsp;</td>
	       <td><input type="checkbox" name="love" value="10" />&nbsp;旅游&nbsp;</td>
	       <td><input type="checkbox" name="love" value="11" />&nbsp;美食&nbsp;</td>
	       <td><input type="checkbox" name="love" value="12" />&nbsp;健身&nbsp;</td>
	       <td><input type="checkbox" name="love" value="13"/>&nbsp;瑜伽&nbsp;</td>
	       <td><input type="checkbox" name="love" value="14"/>&nbsp;摄影&nbsp;</td>
	       <td><input type="checkbox" name="love" value="15"/>&nbsp;棋牌&nbsp;</td>
	       <td><input type="checkbox" name="love" value="16"/>&nbsp;戏曲&nbsp;</td>
	       <td><input type="checkbox" name="love" value="25"/>&nbsp;休闲活动&nbsp;</td> 
	       </tr>
		   <tr> 
		   <td><input type="checkbox" name="love" value="17"/>&nbsp;文学&nbsp;</td>
	       <td><input type="checkbox" name="love" value="18"/>&nbsp;歌剧&nbsp;</td>
	       <td><input type="checkbox" name="love" value="19"/>&nbsp;美术&nbsp;</td>
	       <td><input type="checkbox" name="love" value="2"/>&nbsp;医疗&nbsp;</td>
	       <td><input type="checkbox" name="love" value="20"/>&nbsp;乐器&nbsp;</td>
	       <td><input type="checkbox" name="love" value="21"/>&nbsp;手工&nbsp;</td>
	       <td><input type="checkbox" name="love" value="22"/>&nbsp;科学&nbsp;</td>
	       <td><input type="checkbox" name="love" value="23"/>&nbsp;慈善&nbsp;</td>
	       <td><input type="checkbox" name="love" value="24"/>&nbsp;军事&nbsp;</td>
	       <td><input type="checkbox" name="love" value="26"/>&nbsp;文艺活动&nbsp;</td> 
		   </tr>
		    <tr> 
	       <td><input type="checkbox" name="love" value="27"/>&nbsp;其他&nbsp;</td>
	       <td><input type="checkbox" name="love" value="3"/>&nbsp;健康&nbsp;</td>
	       <td><input type="checkbox" name="love" value="4"/>&nbsp;养老&nbsp;</td>
	       <td><input type="checkbox" name="love" value="5"/>&nbsp;理财&nbsp;</td>
	       <td><input type="checkbox" name="love" value="6"/>&nbsp;养生&nbsp;</td>
	       <td><input type="checkbox" name="love" value="7"/>&nbsp;教育&nbsp;</td>
	       <td><input type="checkbox" name="love" value="8"/>&nbsp;电玩&nbsp;</td>
	       <td><input type="checkbox" name="love" value="9"/>&nbsp;运动&nbsp;</td> 
		   </tr -->
		 </table>
		    
		    </td>
		</tr>
 </table>
   <br/>
       <table>
	       <tr><td>
				<label></label>
				<input type="checkbox" id="isAgreeAgreement_" name="isAgreeAgreement" class="{required: true, messages: {required: \'必须同意注册协议，才可进行注册操作！\'}}" value="true" checked messagePosition="#isAgreeAgreementMessagePosition" /><a id="showAgreementWindow" class="showAgreementWindow" href="#">已阅读并同意《注册协议》</a><span id="isAgreeAgreementMessagePosition"></span>
				</td></tr>	
			<tr><td>
				<label></label>
				<input type="submit" id="registerWindowSubmit"   class="btnorange" style="font-size: 13px;" value="提交" hidefocus="true" />
				<input type="hidden" name="counttime" id="timesrrr" value="0"/>
				<input type="hidden" name="date" id="sendtime"/>
				<input type="hidden" id="odd" value="0"/>
			    <input type="button"   onclick = "showycy();"  id="showButton" class="btnorange" style="font-size: 13px;" value="展开隐藏域"/>
			    <input type="hidden"   id="odd" value="0"/>
		   	</td></tr>
		</table>
		
		<input type="hidden" name="exists" id="isexists" value="0"/>
		<input type="hidden" name="exists" id="cunzai" value="1"/>
		</form>
    </div>
	<div id="agreementWindow" class="agreementWindow" >
			<div class="windowTop">
				<div class="windowTitle">注册协议</div>
				<a class="windowClose agreementWindowClose" href="#" hidefocus="true"></a>
			</div>
			<div class="windowMiddle">
				<div id="agreementContent"></div>
				<input type="button" id="agreementButton_" class="agreementButton agreementWindowClose" value="" hidefocus="true" />
			</div>
			<div class="windowBottom"></div>
	</div>
</div>	
</div>
	<div class="clear"></div>
	<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<script type="text/javascript">

jQuery().ready( function() {
	jQuery.agreementWindowShow = function () {
		jQuery("#agreementWindow").jqmShow();
	};
// 协议悬浮窗口
    jQuery("#agreementWindow").jqm({
		modal: true,// 是否开启模态窗口
		overlay: 0,// 屏蔽层透明度
		trigger: ".showAgreementWindow",// 激活元素
		closeClass: "agreementWindowClose",// 关闭按钮
		onShow: function(hash){
			if (jQuery.trim(jQuery("#agreementContent").html()) == "") {
				jQuery.ajax({
					beforeSend: function(data) {
						jQuery("#agreementContent").html('<span class="loadingIcon">&nbsp;</span> 加载中...');
					},
					url: sinosoft.base + "/shop/member!getAgreement.action",
					success: function(data){
						jQuery("#agreementContent").html(data);
					}
				});
			}
			hash.w.fadeIn();
	    }
	}).jqDrag(".windowTop");
	
	jQuery("#agreementButton_").click( function() {
    	jQuery("#isAgreeAgreement_").attr("checked", true);
    });
});
function ptzcsjhss(){
 jQuery("#status2").html("建议您填写,以便我们提供免费的短信服务.开心保承诺:不绑定任何收费服务!");
}


function ptzcsjhyz(){
var regu = /^[1][3-8][0-9]{9}$/;
var registerUserName = jQuery("#ptzcsjh").val();
if(registerUserName==""||registerUserName==null||typeof(registerUserName)=="undefined"){jQuery("#status2").html(""); }
else{
	if (!regu.test(registerUserName)) {
		jQuery("#status2").html("请输入正确手机号");
		jQuery("#ptzcsjh").attr("value","");
		return false;
	}
	jQuery.ajax({
	        url: sinosoft.base+"/shop/member!checkEmail.action?em="+registerUserName,
	        type: "post",          
			dataType: "json",
			success: function(data) {
				if (data == false) {
					jQuery("#status2").html("此手机号系统已存在");
					jQuery("#isexists").attr("value","1");
					
				}else{
				    jQuery("#status2").html("");
				    jQuery("#isexists").attr("value","0");
				    
	            }
			}
});
}
}

function wosi7(){
jQuery("#ml").html("格式为区号-传真号 例如：0451-25252525");

}

function made(){
	var cz = jQuery("#faxNO").val();
	if(cz==null||typeof cz == "undefined"||cz==""){}
	else{
	if(!/^(\d{3}-\d{8}(-\d{1,4})?|\d{4}-\d{7,8}(-\d{1,4})?)(\/\d{3}-\d{8}(-\d{1,4})?|\/\d{4}-\d{7,8}(-\d{1,4})?)*$/.test(cz)){
		jQuery("#ml").html("传真格式不正确");
		return false;
		}
	}
	jQuery("#ml").html("");
}

function wosi2(){
document.getElementById("registerWindowPasswordStatus").style.color='red';
jQuery("#registerWindowPasswordStatus").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
}
function wosi3(){
jQuery("#rePasswordStatus").html("请输入重复密码");
}
function wosi4(){
jQuery("#vZipCode").html("编码格式为6位数字");
}

function validate222(){
var password=jQuery("#registerWindowPasswordsss").val();
	if(password==""||typeof(password)=="undefined"){
	jQuery("#registerWindowPasswordStatus").html("");
	jQuery("#registerWindowPasswordStatus").html("密码不能为空");
    return false;}
    if(/^[0-9]+$/.test(password)){
	jQuery("#registerWindowPasswordStatus").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#registerWindowPasswordsss").attr("value","");
    return false;
	}
	if(/^[_]+$/.test(password)){
	jQuery("#registerWindowPasswordStatus").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#registerWindowPasswordsss").attr("value","");
    return false;
	}
	if(/^[a-zA-Z]+$/.test(password)){
	jQuery("#registerWindowPasswordStatus").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#registerWindowPasswordsss").attr("value","");
    return false;
	}
    if(!/^[a-zA-Z_0-9]{6,16}$/.test(password)){
	jQuery("#registerWindowPasswordStatus").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#registerWindowPasswordsss").attr("value","");
    return false;
	}
	jQuery("#registerWindowPasswordStatus").html("");

}



function validatedhhm(){
var dhhm = jQuery("#dhhm").val();
if(dhhm==null||typeof dhhm == "undefined"||dhhm==""){}
else{
if(!/^(\d{3}-\d{8}(-\d{1,4})?|\d{4}-\d{7,8}(-\d{1,4})?)(\/\d{3}-\d{8}(-\d{1,4})?|\/\d{4}-\d{7,8}(-\d{1,4})?)*$/.test(dhhm)){
	jQuery("#vdhhm").html("固话格式不正确");
	return false;
}


}
jQuery("#vdhhm").html("");
}
//==========吴高强添加======================//
//页面加载时默认推荐使用手机注册
jQuery().ready(function(){
	var $areaSelect = jQuery(".areaSelect");
	$areaSelect.lSelect({
		url: sinosoft.base +"/shop/area!ajaxChildrenArea.action"// Json数据获取url
	});
	jQuery("#registerHide").hide();
	});

//验证邮政编码
function validateZipCode(){
	var value=jQuery("#ZipCode").val();
	var zip = /^[0-9]{6}$/;
	if(value == null || value == "" || "undefined" == typeof(value)){}
	else{
     if(!zip.test(value)){
		jQuery("#vZipCode").html("邮政编码格式不对！");
		jQuery("#ZipCode").attr("value","");
	    return false;
	}
}
jQuery("#vZipCode").html("");

}
//验证邮箱是否已注册
function validateRegisterUserName1(){
	var registerUserName = jQuery("#registerUserName1").val();
	if(registerUserName == null || registerUserName == "" || "undefined" == typeof(registerUserName)){
		jQuery("#status1").html("邮箱号/手机号不能为空");
		jQuery("#sendVC1").attr("disabled" ,true);
		
		return false;
	}
	
	// 判断是邮箱还是手机号
	var myReg = /^[-_A-Za-z0-9\.]+@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;  //验证邮箱的正则
	var regu = /^[1][3-8][0-9]{9}$/;
	if (!myReg.test(registerUserName) && !regu.test(registerUserName)) {
		jQuery("#status1").html("请输入正确的邮箱号/手机号");
		jQuery("#sendVC1").attr("disabled" ,true);
		//jQuery("#registerUserName1").attr("value","");
		return false;
	}

	 jQuery.ajax({
	        url: sinosoft.base+"/shop/member!checkEmail.action?em="+registerUserName,
	        type: "post",          
			dataType: "json",
			success: function(data) {
			if (data == false) {
					jQuery("#status1").html("此邮箱/手机号已注册");
					if(regu.test(registerUserName)){
		           jQuery("#ptzcsjhxs").hide();
		           }
					jQuery("#sendVC1").attr("disabled" ,true);
					jQuery("#registerUserName1").attr("value","");
		        }else{
				    jQuery("#status1").html("");
				    jQuery("#sendVC1").attr("disabled" ,false);
				    if (myReg.test(registerUserName)) {
		            radio = 0;
		            jQuery("#ptzcsjhxs").show();
		           }else if(regu.test(registerUserName)){
		           jQuery("#ptzcsjhxs").hide();
		           }
				}
			}
		});
}

function wosi6(){
jQuery("#vRealName").html("只允许包含中文、英文");
}
function validateRealName(){
	var userName = jQuery("#RealName").val();
	var name=/^(([\u0391-\uFFE5])*([a-zA-Z]*))$/;
	if(userName==null||userName==""||typeof(userName)=="undefined"){}
	else if(!name.test(userName)){
		jQuery("#vRealName").html("只允许包含中文、英文");
		jQuery("#RealName").attr("value","");
        return false;
	}
	jQuery("#vRealName").html("");
}
function wosi5(){
jQuery("#userNameStatus").html("用户名由4-16个英文字符、数字、下划线组成且不能以数字开头!");
}
function validateUserName1(){
	var userName = jQuery("#UserName1").val();
	var name=/^([a-zA-Z_][a-zA-Z_0-9]{3,16})$/;
	if(userName==null||userName==""||typeof(userName)=="undefined"){jQuery("#userNameStatus").html("");}
	else
	if(!name.test(userName)){
		jQuery("#userNameStatus").html("用户名由4-16个英文字符、数字、下划线组成且不能以数字开头!");
		jQuery("#UserName1").attr("value","");
	    return false;
	}
	 jQuery.ajax({
	        url: sinosoft.base+"/shop/member!checkUserName.action?em="+userName,
	        type: "post",
			dataType: "json",
			success: function(data) {
				if (data == false) {
					jQuery("#userNameStatus").html("此用户名已注册！");
					jQuery("#UserName1").attr("value","");
					jQuery("#cunzai").attr("value","0");
		            return false;
				}else{
				  jQuery("#userNameStatus").html("");
				  jQuery("#cunzai").attr("value","1");
					
				}
			}
		});


}

function validate333(){
cccc=jQuery("#validateCodesss").val();
	if(cccc==""||typeof(cccc)=="undefined"){
	jQuery("#validateCodeStatus").html("验证码不能为空");
	jQuery("#validateCodesss").attr("value","");

	return false;}
	jQuery("#validateCodeStatus").html("");

}
var total ;
var se ;  
function OneSecond1_(){  
	jQuery("#sendVC1").attr("value",  total/1000);
	if(total <= 0){
		jQuery("#sendVC1").attr("value",  "发送验证码");
		jQuery("#sendVC1").attr("disabled",false);
		clearInterval(se);
		total = 1 * 60 * 1000;
		
	} else {
		jQuery("#sendVC1").attr("disabled","disabled");
	}
	total -= 1000;  
	
} 
function ThreeSecond2_(){  
	jQuery("#sendVC1").attr("value",  total/1000);
	if(total <= 0){
		jQuery("#sendVC1").attr("value",  "发送验证码");
		jQuery("#sendVC1").attr("disabled",false);
		clearInterval(se);
		total = 1 * 180 * 1000;
		
	} else {
		jQuery("#sendVC1").attr("disabled","disabled");
	}
	total -= 1000;  
} 


//发送验证码	 
function sendVCode1(){
	var count = jQuery("#timesrrr").val();
	
	var registerUserName = jQuery("#registerUserName1").val();
	var radio = -1;
	var myReg1 = /^[_A-Za-z0-9\.]+@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;  //验证邮箱的正则
	var regu1 = /^[1][3-8][0-9]{9}$/;
	if (myReg1.test(registerUserName)) {
		radio = 0;
		total=1 * 180 * 1000;
		se = setInterval("ThreeSecond2_()",1000);
	}else if(regu1.test(registerUserName)){
		if(Number(count)>=5){
			jQuery("#validateCodeStatus").html( "系统已给您发送了5次验证码了,为确保个人信息安全系统暂不给您发送!");
			return false;
		}
		radio = 1;
		total=1 * 60 * 1000;
		se = setInterval("OneSecond1_()",1000);
		var count2 = jQuery("#timesrrr").val();
	   jQuery("#timesrrr").attr("value", Number(count2)+Number(1));
	}  
	if(radio == -1){
		jQuery("#status1").html("请输入正确的邮箱号/手机号");
		jQuery("#registerUserName1").attr("value","");
		return false;
	}
	
  jQuery.ajax({
				url: sinosoft.base + "/shop/member!sendCode.action?rtype="+radio+"&ways="+registerUserName,
				type: "post",
				dataType: "json",
				success: function(data){
				
				}
	});
}





function sw(){
var sell=document.getElementById("sel");
sel_value = sell.options[sell.selectedIndex].value;
if(sel_value==0){
	Id=document.getElementById("IDNO").value;
	var flag = IdCardValidate(Id);
	if(flag==false){
	jQuery("#zjhmyz").html("请输入有效的18位身份证号码！");
	}else{
	jQuery("#zjhmyz").html("");
    getAge(Id);
	}
	return  flag;	
}else{
jQuery("#zjhmyz").html("");
return  true;	
}
}
function getAge(Id){
	if(Id.length==15){
	var ss = Id.substring(6,8);
	var year=19+ss;
	var mm = Id.substring(8,10);
	var dd=Id.substring(10,12);
	var sq = year+"-"+mm+"-"+dd;
	jQuery("#wall").attr("value",sq);
	}else if(Id.length==18){
	var year1 = Id.substring(6,10);
	var mm1 = Id.substring(10,12);
	var dd1 = Id.substring(12,14);
	var sq1 = year1+"-"+mm1+"-"+dd1;
	jQuery("#wall").attr("value",sq1);
	}
}


function qtlx(){
var sell=document.getElementById("sel");
sel_value = sell.options[sell.selectedIndex].value;
if(sel_value==null||sel_value==""||typeof(sel_value)=="undefined"){}
else{
if(sel_value==0){
	Id=document.getElementById("IDNO").value;
	if(Id==null||Id==""||typeof(Id)=="undefined"){}
	else{
	var flag = IdCardValidate(Id);
	if(flag==false){
	jQuery("#zjhmyz").html("身份证校验不通过！");
	}else{
	jQuery("#zjhmyz").html("");
	}}
	return  flag;	
}else{
jQuery("#zjhmyz").html("");
return  true;	
}
}
jQuery("#zjhmyz").html("");
}




function wosi(){
jQuery("#vdhhm").html("格式为：区号-固话-分机号  分机号可无  多个号码用/隔开 例如010-57884487-4487");
}



function  validatePassword1(){
	var registerWindowPassword=jQuery("#registerWindowPasswordsss").val();
	var rePassword=jQuery("#rePasswordsss").val();
	if(rePassword == null || rePassword == "" || "undefined" == typeof(rePassword)){
		jQuery("#rePasswordStatus").html("重复密码不能为空");
	
		return false;
	}
	if(rePassword!=registerWindowPassword){
		jQuery("#rePasswordStatus").html("两次密码输入不一致");
		jQuery("#registerWindowPasswordsss").attr("value","");
		jQuery("#rePasswordsss").attr("value","");
	
		return false;
}
if(rePassword==registerWindowPassword){
jQuery("#rePasswordStatus").html("");
}}
function validatefm(){
	var registerUserName = jQuery("#registerUserName1").val();
	if(registerUserName == null || registerUserName == "" || "undefined" == typeof(registerUserName)){
		jQuery("#status1").html("邮箱号/手机号不能为空");
		return false;
	}
	
	// 判断是邮箱还是手机号
	var myReg = /^[-_A-Za-z0-9\.]+@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/;  //验证邮箱的正则
	var regu = /^[1][3-8][0-9]{9}$/;
	if (!myReg.test(registerUserName) && !regu.test(registerUserName)) {
	jQuery("#status1").html("");
		jQuery("#status1").html("请输入正确的邮箱号/手机号");
		jQuery("#registerUserName1").attr("value","");
		jQuery("#registerUserName1").focus();
		return false;
	}
	jQuery("#status1").html("");
	cccc=jQuery("#validateCodesss").val();
	if(cccc==""||typeof(cccc)=="undefined"){
	jQuery("#validateCodeStatus").html("验证码不能为空");
	jQuery("#validateCodesss").focus();
	return false;}
	jQuery("#validateCodeStatus").html("");
	var password=jQuery("#registerWindowPasswordsss").val();
	if(password==""||typeof(password)=="undefined"){
	jQuery("#registerWindowPasswordStatus").html("");
	jQuery("#registerWindowPasswordStatus").html("密码不能为空");
	jQuery("#registerWindowPasswordsss").focus();
	return false;}
	if(/^[0-9]+$/.test(password)){
	jQuery("#registerWindowPasswordStatus").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#registerWindowPasswordsss").attr("value","");
	jQuery("#registerWindowPasswordsss").focus();
	return false;
	}
	if(/^[_]+$/.test(password)){
	jQuery("#registerWindowPasswordStatus").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#registerWindowPasswordsss").attr("value","");
	jQuery("#registerWindowPasswordsss").focus();
	return false;
	}
	if(/^[a-zA-Z]+$/.test(password)){
	jQuery("#registerWindowPasswordStatus").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#registerWindowPasswordsss").attr("value","");
	jQuery("#registerWindowPasswordsss").focus();
	return false;
	}
	if(!/^[a-zA-Z0-9_]{6,16}$/.test(password)){
	jQuery("#registerWindowPasswordStatus").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
	jQuery("#registerWindowPasswordsss").attr("value","");
	jQuery("#registerWindowPasswordsss").focus();
	return false;
	}
	jQuery("#registerWindowPasswordStatus").html("");
	b=jQuery("#rePasswordsss").val();
	if(b==""||"undefined"==typeof(b)){
	jQuery("#rePasswordStatus").html("重复密码不能为空");
	jQuery("#rePasswordsss").attr("value","");
	jQuery("#rePasswordsss").focus();
	return false;}
	jQuery("#rePasswordStatus").html("");
	if(password!=b){
	jQuery("#rePasswordStatus").html("密码不一致");
	jQuery("#rePasswordsss").attr("value","");
	jQuery("#rePasswordsss").focus();
	return false;
	}
	jQuery("#rePasswordStatus").html("");
	var value=jQuery("#ZipCode").val();
	var zip = /^[0-9]{6}$/;
	if(value == null || value == "" || "undefined" == typeof(value)){}
	else{
     if(!zip.test(value)){
		jQuery("#vZipCode").html("邮政编码格式不对！");
		jQuery("#ZipCode").attr("value","");
	    return false;
	}}
    jQuery("#vZipCode").html("");

	var userName = jQuery("#RealName").val();
	var name=/^(([\u0391-\uFFE5])*([a-zA-Z]*))$/;
	if(userName==null||userName==""||typeof(userName)=="undefined"){}
	else if(!name.test(userName)){
		jQuery("#vRealName").html("只允许包含中文、英文");
		jQuery("#RealName").attr("value","");
        return false;
	}
	jQuery("#vRealName").html("");
	
	var userName = jQuery("#UserName1").val();
	var name=/^([a-zA-Z_][a-zA-Z_0-9]{3,16})$/;
	if(userName==null||userName==""||typeof(userName)=="undefined"){}
	else
	if(!name.test(userName)){
		jQuery("#userNameStatus").html("用户名由4-16个英文字符、数字、下划线组成且不能以数字开头!");
		jQuery("#UserName1").attr("value","");
	    return false;
	}
	var flag22 = sw();
	if(flag22==false){
	jQuery("#IDNO").attr("value","");
	jQuery("#IDNO").focus();
	return false;}
	var dhhm = jQuery("#dhhm").val();
    if(dhhm==null||typeof dhhm == "undefined"||dhhm==""){}
    else{
    if(!/^(\d{3}-\d{8}(-\d{1,4})?|\d{4}-\d{7,8}(-\d{1,4})?)(\/\d{3}-\d{8}(-\d{1,4})?|\/\d{4}-\d{7,8}(-\d{1,4})?)*$/.test(dhhm)){
	jQuery("#vdhhm").html("固话格式不正确");
	return false;
    }
    }
   jQuery("#vdhhm").html("");
   
	var kkkkk = jQuery("#isexists").val();
	var kkkkk2 = jQuery("#cunzai").val();
	if(kkkkk==1){
	return false;}
	if(kkkkk2==0){
	return false;}
	return true;
	
}


function showycy(){
var ddd = 	jQuery("#odd").val();
if(ddd==0){
jQuery("#registerHide").show();
	jQuery("#showButton").attr("value","关闭隐藏域");
	jQuery("#odd").attr("value","1");
	}else {
	jQuery("#registerHide").hide();
	jQuery("#showButton").attr("value","展开隐藏域");
	jQuery("#odd").attr("value","0");
}
}

</script>	
	


</body>
</html>
/***
 *
 *	http://www.sinosoft.com.cn
 *
 **/

$().ready( function() {

	// 注册窗口显示
	$.registerWindowShow = function () {
		
		$("#registerWindow").jqmShow();
	}
	
	// 协议窗口显示
	$.agreementWindowShow = function () {
		$("#agreementWindow").jqmShow();
	}

	// 注册窗口
	//var registerWindowHtml = '<div id="registerWindow" class="registerWindow"><div class="windowTop"><div class="windowTitle">会员注册</div><a class="windowClose registerWindowClose" href="#" hidefocus="true"></a></div><div class="windowMiddle"><form id="registerWindowForm" action="../../../../../template/shop/js/' + sinosoft.base + '/shop/member!ajaxRegister.action" method="post"><table><tr><th>用户名: </th><td><input type="text" name="member.username" class="formText {required: true, username: true, remote: \'' + sinosoft.base + '/shop/member!checkUsername.action\', minlength: 2, maxlength: 20, messages: {required: \'请输入用户名!\', username: \'不允许包含特殊字符!\', remote: \'用户名已存在,请重新输入!\'}}" title="用户名只允许包含中文、英文、数字和下划线!" /></td></tr><tr><th>密   码: </th><td><input type="password" id="registerWindowPassword" name="member.password" class="formText {required: true, minlength: 4, maxlength: 20, messages: {required: \'请输入密码!\', minlength: \'密码长度不能小于4\', maxlength: \'密码长度不能大于20\'}}" /></td></tr><tr><th>重复密码: </th><td><input type="password" name="rePassword" class="formText {equalTo: \'#registerWindowPassword\', messages: {equalTo: \'两次密码输入不一致!\'}}" title="密码长度只允许在4-20之间!" /></td></tr><tr><th>E-mail: </th><td><input type="text" name="member.email" class="formText {required: true, email: true, messages: {required: \'请输入E-mail!\', email: \'E-mail格式错误!\'}}" /></td></tr><tr><th>验证码: </th><td><input type="text" id="registerWindowCaptcha" name="j_captcha" class="formTextS {required: true, messages: {required: \'请输入验证码!\'}}" messagePosition="#registerWindowCaptchaMessagePosition" /><img id="registerWindowCaptchaImage" src="" alt="换一张" /><span id="registerWindowCaptchaMessagePosition"></span></td></tr><tr><th>&nbsp;</th><td><label><input type="checkbox" id="isAgreeAgreement" name="isAgreeAgreement" class="{required: true, messages: {required: \'必须同意注册协议，才可进行注册操作！\'}}" value="true" checked messagePosition="#isAgreeAgreementMessagePosition" /><a id="showAgreementWindow" class="showAgreementWindow" href="#">已阅读并同意《注册协议》</a></label><span id="isAgreeAgreementMessagePosition"></span></td></tr><tr><th>&nbsp;</th><td><input type="submit" id="registerWindowSubmit" class="registerWindowSubmit" value="" hidefocus="true" /></td></tr></table></form></div><div class="windowBottom"></div></div>';
	var registerWindowHtml = '<div id="registerWindow" class="registerWindow"><div class="windowTop"><div class="windowTitle">会员注册</div><a class="windowClose registerWindowClose" href="#" hidefocus="true"></a></div><div class="windowMiddle"><form id="registerWindowForm"  name="fm" action="../../../../../template/shop/js/' + sinosoft.base + '/shop/member!ajaxRegister.action" method="post"  onsubmit="return validateForm();"><table><tr><th>注册方式:</th><td><input type="radio" name="member.registerType" value="0" onclick="showEmail();" />邮箱注册<input type="radio" name="member.registerType" value="1" onclick="showMobileNo();" checked="checked"/>手机注册<label class="requireField">*</label></td></tr><tr id="Email"><th>邮   箱: </th><td><input type="text" name="member.email"  id="email" onblur="validateEmail();" /></td></tr><tr id="mobileNo"><th>手机号:</th><td><input type="text" onblur="validateMobileNO();" name="member.mobileNO" id="mobileNOO"/><label class="requireField">*</label></td></tr><tr><th>  </th><td><input type="button" disabled="true" id="sendVC" onclick="sendVCode()" value="发送验证码"><span id="status"></span></td></tr><tr><th>密   码: </th><td><input type="password" id="registerWindowPassword" name="member.password" class="formText {required: true, minlength: 6, maxlength: 16, messages: {required: \'请输入密码!\', minlength: \'密码长度不能小于6\', maxlength: \'密码长度不能大于16\'}}" /></td></tr><tr><th>重复密码: </th><td><input type="password" name="rePassword" id="rePassword" onblur="validatePassword();" class="formText {equalTo: \'#registerWindowPassword\', messages: {equalTo: \'两次密码输入不一致!\'}}" title="密码长度只允许在6-16之间!"  /></td></tr><tr><th>验证码:</th><td><input type="text" name="validateCode" id="validateCode2"  class="formText {required: true, validateCode: true}" /><label class="requireField">*</label></td></tr><tr><th>&nbsp;</th><td><label><input type="checkbox" id="isAgreeAgreement" name="isAgreeAgreement" class="{required: true, messages: {required: \'必须同意注册协议，才可进行注册操作！\'}}" value="true" checked messagePosition="#isAgreeAgreementMessagePosition" /><a id="showAgreementWindow" class="showAgreementWindow" href="#">已阅读并同意《注册协议》</a></label><span id="isAgreeAgreementMessagePosition"></span></td></tr><tr><th>&nbsp;</th><td><input type="submit" id="registerWindowSubmit" class="registerWindowSubmit" value="" hidefocus="true" /></td></tr><tr><td><input type="button" id="showButton" onclick="showHideFiled()" style="display:none" value="展开隐藏域"/><td><tr><table id="hideFiled" style="display:none"><tr><th>用户名:</th><td><input type="text" name="member.userName" class="formText{ member.userName: true,minlength: 4, maxlength: 16} "/></td></tr><tr><th>QQ号码:</th><td><input type="text" name="member.QQNO" /></td></tr><tr><th>真实姓名：</th><td><input type="text" name="member.realName" /></td></tr><tr><th>证件类型：</th><td><select name="member.IDType" id="sel" ><option value="0">身份证</option><option value="1">护照</option><option value="2">军官证</option><option value="3">士兵证</option><option value="4">港澳台回乡证或台胞证</option><option value="5">出生证</option><option value="6">户口本</option></select></td></tr><tr><th>证件号码：</th><td><input type="text" id="IDNO" name="member.IDNO" onblur="sw();" /></td></tr><tr><th>性别：</th><td><input type="radio" name="member.sex" value="0" />男<input type="radio" name="member.sex" value="1" />女</td></tr><tr><th>出生日期:</th><td><input type="text" onfocus="setday(this);" name="member.birthday" /></td></tr><tr><th>所在地</th><td><input type="text" name="member.location"  class="areaSelect hidden { messagePosition: \'#areaMessagePosition\'}" /><span id="areaMessagePosition"></span></td></tr><tr><th>联系地址:</th><td><input type="text" name="member.address"></td></tr><tr><th>邮政编码:</th><td><input type="text" name="member.zipcode"></td></tr><tr><th>个人网址:</th><td><input type="text" name="member.personalURL"></td></tr><tr><th>电话：</th><td><input type="text" name="member.telephoneNO"/></td></tr><tr><th>传真：</th><td><input type="text" name="member.faxNO"/></td></tr><tr><th>传真：</th><td><select name="member.marriageStatus"><option value="2">离婚</option><option value="0">未婚</option><option value="1">已婚</option><option value="3">其他</option></select></td></tr><tr><th>职位：</th><td><input type="text" name="member.position"/></td></tr><input type="hidden" name="member.VIPFrom" value="0"/><tr><th>会员类型：</th><td><select name="member.VIPType" ><option value="0">个人</option><option value="1">团体</option></select></td></tr><tr><th>兴趣爱好：</th><td><input type="checkbox" name="love" value="0"/>网购<input type="checkbox" name="love" value="1" />网游<input type="checkbox" name="love" value="10" />旅游<input type="checkbox" name="love" value="11" />美食<input type="checkbox" name="love" value="12" />健身<input type="checkbox" name="love" value="13"/>瑜伽<input type="checkbox" name="love" value="14"/>摄影<input type="checkbox" name="love" value="15"/>棋牌<input type="checkbox" name="love" value="16"/>戏曲<input type="checkbox" name="love" value="17"/>文学<input type="checkbox" name="love" value="18"/>歌剧<input type="checkbox" name="love" value="19"/>美术<input type="checkbox" name="love" value="2"/>医疗<input type="checkbox" name="love" value="20"/>乐器<input type="checkbox" name="love" value="21"/>手工<input type="checkbox" name="love" value="22"/>科学<input type="checkbox" name="love" value="23"/>慈善<input type="checkbox" name="love" value="24"/>军事<input type="checkbox" name="love" value="25"/>休闲活动<input type="checkbox" name="love" value="26"/>文艺活动<input type="checkbox" name="love" value="27"/>其他<input type="checkbox" name="love" value="3"/>健康<input type="checkbox" name="love" value="4"/>养老<input type="checkbox" name="love" value="5"/>理财<input type="checkbox" name="love" value="6"/>养生<input type="checkbox" name="love" value="7"/>教育<input type="checkbox" name="love" value="8"/>电玩<input type="checkbox" name="love" value="9"/>运动</td></tr><input type="hidden" name="counttime" id="times" value="0"><input type="hidden" name="date" id="sendtime"><input type="hidden" id="odd" value="0"/></table></table></div></form>';

	// 协议窗口
	var agreementWindowHtml = '<div id="agreementWindow" class="agreementWindow"><div class="windowTop"><div class="windowTitle">注册协议</div><a class="windowClose agreementWindowClose" href="#" hidefocus="true"></a></div><div class="windowMiddle"><div id="agreementContent"></div><input type="button" id="agreementButton" class="agreementButton agreementWindowClose" value="" hidefocus="true" /></div><div class="windowBottom"></div></div>';
	
	$("body").prepend(registerWindowHtml).append(agreementWindowHtml);
	
	// 注册悬浮窗口
    $("#registerWindow").jqm({
		modal: true,// 是否开启模态窗口
		overlay: 30,// 屏蔽层透明度
		trigger: ".showRegisterWindow",// 激活元素
		closeClass: "registerWindowClose",// 关闭按钮
		onHide: function(hash) {
			$("#registerWindowForm").resetForm();
			hash.o.remove();
    		hash.w.fadeOut();
    		
    	},
    	onShow: function(hash){
    		hash.w.fadeIn();
    		registerWindowCaptchaImageRefresh();
	    }
	}).jqDrag(".windowTop");
	
	// 协议悬浮窗口
    $("#agreementWindow").jqm({
		modal: true,// 是否开启模态窗口
		overlay: 0,// 屏蔽层透明度
		trigger: ".showAgreementWindow",// 激活元素
		closeClass: "agreementWindowClose",// 关闭按钮
		onShow: function(hash){
			if ($.trim($("#agreementContent").html()) == "") {
				$.ajax({
					beforeSend: function(data) {
						$("#agreementContent").html('<span class="loadingIcon"> </span> 加载中...');
					},
					url: sinosoft.base + "/shop/member!getAgreement.action",
					success: function(data){
						$("#agreementContent").html(data);
					}
				});
			}
			hash.w.fadeIn();
	    }
	}).jqDrag(".windowTop");
	
	$("#agreementButton").click( function() {
    	$("#isAgreeAgreement").attr("checked", true);
    });
	
	// 刷新验证码图片
	function registerWindowCaptchaImageRefresh() {
		$("#registerWindowCaptchaImage").attr("src", sinosoft.base + "/captcha.jpg?timestamp" + (new Date()).valueOf());
	}
	
	// 点击刷新验证码图片
	$("#registerWindowCaptchaImage").click( function() {
		registerWindowCaptchaImageRefresh();
	});

});


//==========吴高强添加======================//
//页面加载时默认推荐使用手机注册
$(document).ready(function(){
	$("#Email").hide();
	$("#mobileNo").show();
	$("#hideFiled").hide();
	var $areaSelect = $(".areaSelect");
	$areaSelect.lSelect({
		url: sinosoft.base +"/shop/area!ajaxChildrenArea.action"// Json数据获取url
	});
	});

//根据注册方式显示或隐藏手机号或邮箱
function showEmail(){
	$("#Email").show();
	$("#mobileNo").hide();
	}
	function showMobileNo(){
	$("#Email").hide();
	$("#mobileNo").show();
	}
	function showHideFiled(){
		var pp=$("#odd").val();
		if(pp=="0"){
		$("#hideFiled").show();
		$("#showButton").attr("value","关闭隐藏域");
		$("#odd").attr("value","1");
		}else {
		$("#hideFiled").hide();
		$("#showButton").attr("value","展开隐藏域");
		$("#odd").attr("value","0");
		}
	}

	
	//密码一致时显示可以展开隐藏域的按钮
	function  validatePassword(){
	a=document.getElementById("registerWindowPassword").value;
	b=document.getElementById("rePassword").value;
	if(!(a==null||a=="")){
	if(a==b)
	$("#showButton").show();
	}
	}
	 
//验证邮箱是否已注册
	function validateEmail(){
		id=$("#email").val();
		if(id==""||"undefined"==typeof(id)){
			$.tip("邮箱号不能为空");
			return false;
		}

		if(!/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(id)){
			$.tip("您输入的邮箱格式不正确，请重新输入!");
		return false;
		}
		 $.ajax({
			        url: sinosoft.base+"/shop/member!checkEmail.action?em="+id,
			        type: "post",
					dataType: "json",
					success: function(data) {
						if (data==false) {
							$.tip("此邮箱已注册");
							$("#sendVC").attr("disabled" ,true);
						}else{
							$.tip("此邮箱可以注册");
							$("#sendVC").attr("disabled" ,false);
							
						}
					}
				});
	  }
//验证手机号是否已注册
function validateMobileNO(){
	var mobileNO=$("#mobileNOO").val();
	if(mobileNO==""||"undefined"==typeof(mobileNO)){
		$.tip("手机号不能为空");
		return false;
	}

	if(!/^(13[0-9]{9}|15[5-9][0-9]{8}|15[0-3][0-9]{8}|18[0-9][0-9]{8}|147[0-9]{8}|17[6-8][0-9]{8})$/.test(mobileNO)){
		$.tip("您输入的手机格式不正确，请重新输入!");
	return false;
	}
	
$.ajax({
		    url: sinosoft.base+"/shop/member!checkMobileNO.action?NO="+mobileNO,
	        type: "post",
			dataType: "json",
			success: function(data){
				if (data==false) {
					$.tip("此手机号已注册");
					$("#sendVC").attr("disabled" ,true);
					}else{
						$.tip("此手机号可以注册");
						$("#sendVC").attr("disabled" ,false);
						
					}
				}
		});
}


function starttime(){
var Time = 120; //设置时间　单位：秒
var h,m,s,t=null; 
if(t==null)
t = setInterval(function(){e(--Time)},1000);}
function e(Time)
{h = Math.floor(Time/3600);
     m = Math.floor((Time-h*3600)/60);
     s = Time-m*60;   
  
     if (Time==0) {
    	
    	 $("#sendVC").attr("value","重新发送");
    	 $("#sendVC").attr("disabled",false);
    	 clearInterval(t);
    	 }   
     else{ $("#sendVC").attr("value", appZero(h)+":"+appZero(m)+":"+appZero(s)); 
          $("#sendVC").attr("disabled",true);
      }
}

function appZero(n){return(("00"+ n).substr(("00"+ n).length-2));}//自动补零程序

//发送验证码	 
function sendVCode(){
	 var radio=document.getElementsByName("member.registerType");//获取注册方式
	 var count = $("#times").val();
	 var ways=0;
	  for(var i=0;i<radio.length;i++){
		if(radio[i].checked==true){
			radio=radio[i].value;
		  break;
		}
	  } //获取对应注册方式所填写的号码
	 if(radio==0){
		 ways=document.getElementById("email").value;
	 }
	  if(radio==1){
		  ways=document.getElementById("mobileNOO").value;
		 
		  if(count=="0"){
			  var sendTime =new Date().getTime();
			  $("#sendtime").attr("value",sendTime);
			 
		  }
		  
	}
if(count=="0"){

 $.ajax({
		url: sinosoft.base + "/shop/member!sendCode.action?rtype="+radio+"&ways="+ways,
		type: "post",
		dataType: "json",
		success: function(data){
			document.getElementById("status").innerHTML=data;
			$("#sendVC").attr("value", countDown);
			$("#sendVC").attr("disable",true);
		}
	});
 starttime();
 $("#times").attr("value" ,"1");}
else if(Number(count)<=Number(5)){
	var newdate = new Date().getTime();
	var olddate = $("#sendtime").val();
	if((newdate-olddate)<(1000*60*2)){
		document.getElementById("status").innerHTML="您的操作过于频繁啊！";
	}else{
		var sendTime = new Date().getTime();
		document.getElementById("status").innerHTML="";
		 $.ajax({
				url: sinosoft.base + "/shop/member!sendCode.action?rtype="+radio+"&ways="+ways,
				type: "post",
				dataType: "json",
				success: function(data){
					document.getElementById("status").innerHTML=data;
					
				}
			});
		 starttime();
		 var counts = Number(count) + Number(1);
		 $("#sendtime").attr("value",sendTime);
		 $("#times").attr("value" ,counts); 
	}
	
}else{
	document.getElementById("status").innerHTML="至多只能发送5次！";
}
}


function checkIdcard(myId){

	var Errors=new Array(
	"身份证号码验证通过!",
	"身份证号码位数不对!",
	"身份证号码出生日期超出范围或含有非法字符!",
	"身份证号码校验错误!",
	"身份证地区非法!"
	);
	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
	var Y,JYM;
	var S,M;
	var idcard=myId;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	//地区检验
	if(area[parseInt(idcard.substr(0,2))]==null) alert(Errors[4]);
	//身份号码位数及格式检验
	switch(idcard.length){
	case 15:
	if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
	ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性
	} else {
	ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性
	}
	if(ereg.test(idcard)) alert(Errors[0]);
	else alert(Errors[2]);
	break;
	case 18:
	//18位身份号码检测
	//出生日期的合法性检查
	//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
	//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
	if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
	ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式
	} else {
	ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式
	}
	if(ereg.test(idcard)){//测试出生日期的合法性
	//计算校验位
	S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
	+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
	+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
	+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
	+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
	+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
	+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
	+ parseInt(idcard_array[7]) * 1
	+ parseInt(idcard_array[8]) * 6
	+ parseInt(idcard_array[9]) * 3 ;
	Y = S % 11;
	M = "F";
	JYM = "10X98765432";
	M = JYM.substr(Y,1);//判断校验位
	if(M == idcard_array[17]) alert(Errors[0]); //检测ID的校验位
	else aler(Errors[3]);
	}
	else alert(Errors[2]);
	break;
	default:
	alert(Errors[1]);
	break;
	}
	}


function sw(){
	
var sell=document.getElementById("sel");
sel_value = sell.options[sell.selectedIndex].value;
if(sel_value==0){
	Id=document.getElementById("IDNO").value;
	checkIdcard(Id);	
}
}
function validateForm(){
	
	var radioValue=$(':radio[name="member.registerType"]:checked').val();
	if(radioValue=="0"){
		id=$("#email").val();
			if(id==""||"undefined"==typeof(id)){
				$.tip("邮箱号不能为空");
				return false;
			}

			if(!/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(id)){
				$.tip("您输入的邮箱格式不正确，请重新输入!");
			return false;
			}
	}else if(radioValue=="1"){
	var mobileNO=$("#mobileNOO").val();
		if(mobileNO==""||"undefined"==typeof(mobileNO)){
			$.tip("手机号不能为空");
			return false;
		}

		if(!/^(13[0-9]{9}|15[5-9][0-9]{8}|15[0-3][0-9]{8}|18[5-9][0-9]{8}|147[0-9]{8}|17[6-8][0-9]{8})$/.test(mobileNO)){
			$.tip("您输入的手机格式不正确，请重新输入!");
		return false;
		}
		}
	var password=$("#registerWindowPassword").val();
	if(password==""||typeof(password)=="undefined"){
	$.tip("密码不能为空");
	return false;}
	b=$("#rePassword").val();
	if(b==""||"undefined"==typeof(b)){
	$.tip("重复密码不能为空");
	return false;}
	cccc=$("#validateCode2").val();
	if(cccc==""||typeof(cccc)=="undefined"){
	$.tip("验证码不能为空");
	return false;}
	$("#registerWindowForm").ajaxSubmit({
		dataType: "json",
		success: function(data) {
		if (data.status == "success") {
			$.tip(data.status, data.message);
			$.flushHeaderInfo();
			$("#registerWindow").jqmHide();
		} else {
			$.tip(data.status, data.message);
			$.flushHeaderInfo();
			$("#registerWindow").jqmHide();
		}
		$("#registerWindowSubmit").attr("disabled", false);
	}
	});
	return false;

	}
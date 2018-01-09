$(function() {
	$(document).on('click', '.title', function() {
		console.log("1");
		var that = $(this);
		that.toggleClass("on");
		if (that.hasClass("on")) {
			that.siblings("ul").addClass("hide");
		} else {
			that.siblings("ul").removeClass("hide");
		}
	});
	// 关闭提示
	$("#tip").on('click', '.close', function() {
		$("#lock").addClass("hide");
		$("#tip").addClass("hide");
	});
	// 投保额度
	$(".select").on('click', 'a', function() {
		$(".select a").removeClass("blue");
		$(this).addClass("blue");
		var data = $(this).attr("data-num");
		$("#data").val(data / 1000);
		$("#data2").html(data+".00");
	});
	//提取身份信息
	function getData(){
	   var ID=$('#idnumber');
	   var bd=document.getElementById('bd');
	   var sex=$('#sex');
	   if(!/^\d{6}((?:19|20)((?:\d{2}(?:0[13578]|1[02])(?:0[1-9]|[12]\d|3[01]))|(?:\d{2}(?:0[13456789]|1[012])(?:0[1-9]|[12]\d|30))|(?:\d{2}02(?:0[1-9]|1\d|2[0-8]))|(?:(?:0[48]|[2468][048]|[13579][26])0229)))\d{2}(\d)[xX\d]$/.test(ID.value)){
		  alert('身份证号非法.');
		  return;
	   }
	   year.value=(RegExp.$1).substr(0,4)+'-'+(RegExp.$1).substr(4,2)+'-'+(RegExp.$1).substr(6,2);
	   sex.value=(parseInt(RegExp.$2)%2==0?'男':'女');
	   console.log("aaaa");
	}
	$(".contral").on('click', '.right', function() {
		var data = parseInt($("#data").val()) + 1;
		if(data >= 200)
			data = 200;
		$("#data").val(data);
		$("#data2").html((data * 1000)+".00");
		$(".select a").removeClass("blue");
		$('a.border').each(function(){
			var data1 = $(this).attr("data-num") / 1000;
			if(data1==data){
				$(this).addClass("blue");
			}
		});
	});
	$(".contral").on('click', '.left', function() {
		var data = parseInt($("#data").val()) - 1;
		if (data <= 0)
			data = 1;
		$("#data").val(data);
		$("#data2").html((data * 1000)+".00");
		$(".select a").removeClass("blue");
		$('a.border').each(function(){
			var data1 = $(this).attr("data-num") / 1000;
			if(data1==data){
				$(this).addClass("blue");
			}
		});
	});
	$('.btn_contral').on('click','#appointment',function(){
		$('#appointment_number').removeClass("hide");
		var that=$(this);
		that.addClass("bg_grey");
	});
	// 验证-姓名
	valid('name').validator({
		'#name' : {
			wrong : '请输入正确的用户名',
			handler : 'empty',
			right : '',
			empty : '',
			bind : {
				focus : '',
				blur : true
			}
		}
	});
	$('.box_big').on('blur', 'input#name', function() {
		var result = validname();
		if (result == false) {
			$("#lock").removeClass("hide");
			$("#tip").removeClass("hide");
			$("#tip .comment").html("请输入正确的用户名");
			$(this).val("请输入姓名");
		}
	});
	// 验证-身份证
	/*valid('idnumber').validator({
		'#idnumber' : {
			wrong : '一定要填写18位有效身份证号码哟，请仔细检查',
			handler : 'idnumber',
			right : '',
			empty : '',
			bind : {
				focus : '',
				blur : true
			}
		}
	});*/
	$('.box_big').on('blur', 'input#idnumber', function() {
		
		if (!checkId(jQuery("#idnumber").val(),"")) { 
			$("#lock").removeClass("hide");
			$("#tip").removeClass("hide");
			$("#tip .comment").html("请输入正确的身份证号");
			$(this).val("请输入身份证号");
			return;
		} 
		var that=$(this);
		var data=that.val();
		var year=data.substring(6,10);
		var month=data.substring(10,12);
		var day=data.substring(12,14);
		var birthday = year+"-"+month+"-"+day;
		if(!CheckAppAge(birthday)){
			$("#lock").removeClass("hide");
			$("#tip").removeClass("hide");
			$("#tip .comment").html("投保人年龄不符合投保要求，需在18-60周岁之间!");
			$(this).val("请输入身份证号");
			return;
		}
	});
	// 验证-手机
	valid('tel').validator({
		'#tel' : {
			wrong : '请输入正确的手机号码',
			handler : 'phone',
			right : '',
			empty : '',
			bind : {
				focus : '',
				blur : true
			}
		}
	});
	$('.box_big').on('blur', 'input#tel', function() {
		var result = validtel();
		if (result == false) {
			$("#lock").removeClass("hide");
			$("#tip").removeClass("hide");
			$("#tip .comment").html("请输入正确的手机号码");
			$(this).val("请输入手机号码");
		}
	});
	// 验证-邮箱
	valid('email').validator({
		'#email' : {
			wrong : '请输入正确的电子邮箱',
			handler : 'email',
			right : '',
			empty : '',
			bind : {
				focus : '',
				blur : true
			// 必填项
			}
		}
	});
	$('.box_big').on('blur', 'input#email', function() {
		var result = validemail();
		if (result == false) {
			$("#lock").removeClass("hide");
			$("#tip").removeClass("hide");
			$("#tip .comment").html("请输入正确的电子邮箱");
			$(this).val("请输入邮箱");
		}
	});
	// 验证-银行卡
	/*valid('yhk').validator({
		'#yhk' : {
			wrong : '请输入正确的银行卡号',
			handler : 'core',
			right : '',
			empty : '',
			limit : /^\d+$/,
			bind : {
				focus : '',
				blur : true
			// 必填项
			}
		}
	});*/
	$('.box_big').on('blur', 'input#yhk', function() {
		var result = validyhk();
		console.log(result);
		if (result == false || $("input#yhk").val().length<=4) {
			$("#lock").removeClass("hide");
			$("#tip").removeClass("hide");
			$("#tip .comment").html("请输入正确的银行卡号");
			$(this).val("请输入银行卡号");
		}
	});
	$('.box_big').on('click', 'input', function() {
		$(this).val("");
	});
	/**
	 * 返回修改初始化份数
	 */
	var tNum = $("#data").val();
	$(".select a").removeClass("blue");
	if(tNum!=null && tNum!="" && tNum!="0"){
		$('a.border').each(function(){
			var data = $(this).attr("data-num") / 1000;
			if(tNum==data){
				$(this).addClass("blue");
			}
		});
	}else{
		$("#data").val("1");
		$("#data2").html("1000.00");
		$(".select a").removeClass("blue");
	}
 
});
function valididnumber(){
	return checkId(jQuery("#idnumber").val(),"");
}
function checkId(value, element) { 
	 var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
	 var iSum = 0;
	 var strIDno = value;
	 if(!/^\d{17}(\d|x)$/i.test(strIDno)&&!/^\d{15}$/i.test(strIDno)){
	     return false; //非法身份证号
	 }
	 if(aCity[parseInt(strIDno.substr(0,2))]==null){
	       return false;// 非法地区
	 }
	 
	     // 判断是否大于2078年，小于1900年
	     var year =strIDno.substring(6,10);
	     if (year<1900 || year>2078 ){ 
	         return false;//非法生日
	     }

	    //18位身份证处理

	   //在后面的运算中x相当于数字10,所以转换成a
	    strIDno = strIDno.replace(/x$/i,"a");

	  sBirthday=strIDno.substr(6,4)+"-"+Number(strIDno.substr(10,2))+"-"+Number(strIDno.substr(12,2));
	  var d = new Date(sBirthday.replace(/-/g,"/"));
	  if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
	         return false;//非法生日
	   }
	    // 身份证编码规范验证
	  for(var i = 17;i>=0;i --)
	   iSum += (Math.pow(2,i) % 11) * parseInt(strIDno.charAt(17 - i),11);
	  if(iSum%11!=1){
	      return false;// 非法身份证号
	   }
	   // 判断是否屏蔽身份证
	    var words = new Array();
	    words = new Array("11111119111111111","12121219121212121");

	    for(var k=0;k<words.length;k++){
	        if (strIDno.indexOf(words[k])!=-1){
	            return false;
	        }
	    }

	 return true;
}
/**
 * 用出生日期计算年龄，目前不支持yyyymmdd模式
 * 参数，出生日期yy－mm－dd
 * 返回  年龄
 */
function calAge(birthday)
{
	if(birthday.indexOf("-")==-1){
		return "";
	}
	var arrBirthday = birthday.split("-");
	if (arrBirthday.length == 1)
	{
		if(arrBirthday[0].length != 8)
		{
			return "";
		}
		var arrBirthdays = new Array();
		arrBirthdays[0] = arrBirthday[0].substring(0, 4);
		arrBirthdays[1] = arrBirthday[0].substring(4, 6);
		arrBirthdays[2] = arrBirthday[0].substring(6, 8);
		var today = new Date();
		var arrToday = new Array();
		arrToday[0] = today.getFullYear();
		arrToday[1] = today.getMonth() + 1;
		arrToday[2] = today.getDate();
		var age = arrToday[0] - arrBirthdays[0] - 1;
		//当前月大于出生月
		if(arrToday[1] > arrBirthdays[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthdays[1])
		{
			//当前月小于出生月
			return age;
		}
		else if(arrToday[2] >= arrBirthdays[2])
		{
			//当前月等于出生月的时候，看出生日
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
	else
	{
		if(arrBirthday[1].length == 1)
		{
			arrBirthday[1] = "0" + arrBirthday[1];
		}
		if(arrBirthday[2].length == 1)
		{
			arrBirthday[2] = "0" + arrBirthday[2];
		}
		var today = new Date();
		var arrToday = new Array();
		arrToday[0] = today.getFullYear();
		arrToday[1] = today.getMonth() + 1;
		arrToday[2] = today.getDate();
		var age = arrToday[0] - arrBirthday[0] - 1;
		//当前月大于出生月
		if(arrToday[1] > arrBirthday[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthday[1])
		{
			//当前月小于出生月
			return age;
		}
		else if(arrToday[2] >= arrBirthday[2])
		{
			//当前月等于出生月的时候，看出生日
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
}
/**
 * 计算投保人年龄，判定是否异常
 * 正常返回true，异常返回false
 */
function CheckAppAge(birthday)
{
	var i = calAge(birthday);
	if (i>=61 ||i<=17)
	{
		return false;
	}
	else
	{
		return true;
	}
}
 

function validateForm() {
	
	var rname=jQuery("#rname").val();
	
	//姓名
	if(rname=="" || "undefined"==typeof(rname))
	{
		document.getElementById("isrm").innerHTML="";
	}
	else if(!/^(([\u0391-\uFFE5])*([a-zA-Z]*))$/.test(rname))
	{
		document.getElementById("isrm").innerHTML="真实姓名格式不正确";
		jQuery("#rname").attr("value","");
		jQuery("#rname").focus();
		return;
	}
	
	var selectValue = $('#xuanzhong option:selected').val();
	var idno = $("#IDNO").val();
	if(selectValue==0 && idno.indexOf("请输入")==-1){
		if(!checkIDCard(idno)){
			return;
		}
	}
	
	$("#inputForm").submit();
}

function checkIDCard(id) {
	 var idNum = id;
	 var errors = new Array(
	  "验证通过",
	  "身份证号码位数不对",
	   "身份证含有非法字符",
	  "身份证号码校验错误",
	  "身份证地区非法"
	 );
	 //身份号码位数及格式检验
	 var re;
	 var len = idNum.length;
	 //身份证位数检验
	 if (len != 15 && len != 18) {
	     jQuery("#zjhmyz").html(errors[1]);
	     return;
	 } else if (len == 15) {
	     re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
	 } else {
	     re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})([0-9xX])$/);
	 }
	 var area = { 11: "北京", 12: "天津", 13: "河北", 14: "山西",
	     15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海",
	     32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西",
	     37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东",
	     45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州",
	     53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海",
	     64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门",
	     91: "国外"
	 }
	 var idcard_array = new Array();
	 idcard_array = idNum.split("");
	 //地区检验
	 if (area[parseInt(idNum.substr(0, 2))] == null) {
	     jQuery("#zjhmyz").html(errors[4]);
	     return;
	 }
	 //出生日期正确性检验
	 var a = idNum.match(re);
	 if (a != null) {
	     if (len == 15) {
	         var DD = new Date("19" + a[3] + "/" + a[4] + "/" + a[5]);
	         var flag = DD.getYear() == a[3] && (DD.getMonth() + 1) == a[4] && DD.getDate() == a[5];
	     }
	     else if (len == 18) {
	         var DD = new Date(a[3] + "/" + a[4] + "/" + a[5]);
	         var flag = DD.getFullYear() == a[3] && (DD.getMonth() + 1) == a[4] && DD.getDate() == a[5];
	     }
	     if (!flag) {
	         //return false;
	         return "身份证出生日期不对！"; 
	     }
	     //检验校验位
	     if (len == 18) {
	         S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
	            + (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
	            + (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
	            + (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
	            + (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
	            + (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
	            + (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
	            + parseInt(idcard_array[7]) * 1
	            + parseInt(idcard_array[8]) * 6
	            + parseInt(idcard_array[9]) * 3;
	         Y = S % 11;
	         M = "F";
	         JYM = "10X98765432";
	         M = JYM.substr(Y, 1); //判断校验位
	         //检测ID的校验位
	         if (M == idcard_array[17]) {
	             return true;
	             //return ""; 
	         }
	         else {
	             //return false;
	        	 jQuery("#zjhmyz").html(errors[3]);
	             return ;
	         }
	     }
	 } else {
	     //return false;
		 jQuery("#zjhmyz").html(errors[2]);
	     return ;
	 }
	 return true;
	}

/**
 * 查询所有过支付订单的投保人姓名
 */
function getPaidAppntByMemberId(){
	jQuery.ajax({
		url: sinosoft.base+"/shop/member_info_maintain!getPaidAppntByMemberId.action?memberID="+jQuery("#memberId").val(),
		dataType: "json",
		type:"GET", 
		async: false,
		success: function(data) {
			if(data.Flag=="Suc"){
				return data.paidAppnts;
			}else if(data.Flag=="Fal"){
				return null;
			}else{
				alert(data.Emessage);
			}
		}
	});
}

/**
 * 快速查询投保人信息
 */
function getAppntInfoByName(appntName){
	jQuery.ajax({
		url: sinosoft.base+"/shop/member_info_maintain!getAppntInfoByName.action?info_Name="+encodeURIComponent(encodeURIComponent(appntName)),
		dataType: "json",
		type:"GET", 
		async: false,
		success: function(data) {
			if(data.Flag=="Suc"){
				fillAppnt(data);
			}else if(data.Flag=="Fal"){
				alert("没有查到该被保人的相关信息，请选择其他被保人或者直接录入被保人信息！");
			}else{
				alert(data.Emessage);
			}
		}
	});
}

/**
 * 回显投保人五大元素
 * @param data
 */
function fillAppnt(data){
	var appnt = data.appnt;
	if(appnt.applicantName!=null&&appnt.applicantName!=""){
		jQuery("#rname").val(appnt.applicantName);	
	}
	if(appnt.applicantSexName!=null&&appnt.applicantSexName!=""){
		if(appnt.applicantSexName=="男"){
			jQuery("#sex0").attr("checked",'checked');
		}else{
			jQuery("#sex1").attr("checked",'checked');
		}
	}
	if(appnt.applicantBirthday!=null&&appnt.applicantBirthday!=""){
		jQuery("#wall").val(appnt.applicantBirthday);	
	}
	if(appnt.applicantIdentityTypeName!=null&&appnt.applicantIdentityTypeName!=""){
		var idType = jQuery.trim(appnt.applicantIdentityTypeName);
		if(idType=="身份证"){
			jQuery("#xuanzhong").val("0");
		}else if(idType=="护照"){
			jQuery("#xuanzhong").val("1");
		}else if(idType=="军官证"){
			jQuery("#xuanzhong").val("2");
		}else if(idType=="士兵证"){
			jQuery("#xuanzhong").val("3");
		}else if(idType=="港澳回乡证或台胞证"){
			jQuery("#xuanzhong").val("4");
		}else if(idType=="出生证"){
			jQuery("#xuanzhong").val("5");
		}else if(idType=="户口本"){
			jQuery("#xuanzhong").val("6");
		}else{
			jQuery("#xuanzhong").val("7");
		}
	}
	if(appnt.applicantIdentityId!=null&&appnt.applicantIdentityId!=""){
		jQuery("#IDNO").val(appnt.applicantIdentityId);	
	}
	jQuery("#area option").eq(0).attr("selected",true);
	jQuery("#city").val("");
	jQuery("#address").val("");
	jQuery("#zipcode").val("");
	jQuery("#inputForm_member_marriageStatus").val("-1");
	jQuery("#inputForm_member_VIPType").val("-1");
	jQuery("#industryType").val("");
	jQuery("#position").val("");
}

jQuery(function(){
	//获取所有过支付订单的投保人姓名并显示
	var paidAppntList;
	jQuery.ajax({
		url: sinosoft.base+"/shop/member_info_maintain!getPaidAppntByMemberId.action",
		dataType: "json",
		type:"GET", 
		async: false,
		success: function(data) {
			if(data.Flag=="Suc"){
				paidAppntList = data.paidAppnts;
			}else if(data.Flag=="Fal"){
				paidAppntList = null;
			}else{
				alert(data.Emessage);
			}
		}
	});
	if(paidAppntList == null || paidAppntList.length == 0) {
		jQuery(".use_desli dd").hide();
	} else {
		for(var i = 0;i < paidAppntList.length && i < 10; i++) {
			var $node = jQuery("<dd class='user_names'><label><input type='radio' name='RadioGroup1' value='" + 
					paidAppntList[i] + "' id='RadioGroup1_" + i + "' onclick='getAppntInfoByName(\"" + paidAppntList[i] + "\")'/>"
					+ paidAppntList[i] + "</label></dd>");
			jQuery("#user_more_dd").before($node);
		}
	}
	
	// 常用投保人展示快速完善会员信息交互
	var usernum = jQuery(".use_desli >dd.user_names").length;
	var user = jQuery('.use_desli dd.user_names:gt(4)');
	var user_btn = jQuery('#user_more');
	if (usernum > 5) {
		user_btn.show();
		user.hide();
		user_btn.click(function() {
			if (user.is(":visible")) {
				user.hide();
				user_btn.text("更多>>");
			} else {
				user.show();
				user_btn.text("收起>>");
			}
		})
	} else {
		user_btn.hide();
	}
});

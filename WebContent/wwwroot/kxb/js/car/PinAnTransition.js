var carNo = $('#carnum').val();
// 动态加载页面
$(document)
		.ready(
				function createAddTypeTable() {
					if ($('#carnum').attr("disabled") == false) {
						$('#CarProperty').removeAttr("checked");
					}
					if("1"==jQuery("#CarProperty").val()){
						$("#carnum").attr("disabled") == false;
						hidCarNo();
						$("#CarProperty").attr('checked', 'true');
					}
					// 邮箱地址的验证
					jQuery.validator
							.addMethod(
									"isEmail",
									function(value, element) {
										var chrnum = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
										return this.optional(element)
												|| (chrnum.test(value));
									}, "邮箱地址格式错误!");
					/*
					 * // 车牌号的验证 jQuery.validator .addMethod( "isLicense",
					 * function(value, element) { var chrnum =
					 * /^([\u4e00-\u9fa5]|[\uFE30-\uFFA0])[a-zA-Z][a-zA-Z0-9]{5,6}$/;
					 * return this.optional(element) || (chrnum.test(value)); },
					 * "车牌号格式错误!");
					 */
					// 车价验证
					jQuery.validator.addMethod("isCarVlue", function(value,
							element) {
						var chrnum = /^0.*$/;
						return this.optional(element) || (!chrnum.test(value));
					}, "车价不允许以0开头!");
					// 允许输入汉字,字母
					jQuery.validator
							.addMethod(
									"hanzizimu",
									function(value, element) {
										var pattern_cn = /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0]|[a-zA-Z()]|[·]+)*$/gi;
										return this.optional(element)
												|| (pattern_cn.test(value));
									}, "只能输入汉字、字母、·!");
					// 验证联系电话是否正确
					jQuery.validator.addMethod("isMobile", function(value,
							element) {
						var chrnum = /^1[3458][0-9]{9}$/;
						return this.optional(element) || (chrnum.test(value));
					}, "联系电话格式错误!");
					//验证车牌号码是否正确
					jQuery.validator.addMethod("isCarNo",
							function(value, element) {
								var chrnum1 = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼][A-Z][-|－]?([A-Z0-9]{5,6})$/;
								var chrnum2 = /^[京津沪渝][-|－]?[*]$/;
								var returnflag = chrnum1.test(value) || chrnum2.test(value);
								return this.optional(element) || returnflag;
							}, "车牌号码格式错误!");
					$("#carForm").validate({
						rules : {
							"car.Address" : {
								required : true
							},
							"car.PlateNo" : {
								required : true,
								isCarNo : true
							},
							"car.InsuranceDate" : {
								required : true
							},
							"car.CarValue" : {
								required : true,
								isCarVlue : true
							},
							"car.CarOwner" : {
								required : true,
								maxlength : 15,
								hanzizimu : true
							},
							"car.ContactPhone" : {
								required : true,
								maxlength : 12,
								isMobile : true
							},
							"car.ContactEmail" : {
								required : true,
								isEmail : true
							},
							"car.BuyDate" : {
								required : true
							}

						},
						messages : {
							"car.Address" : {
								required : "请选择车辆所在地!"
							},
							"car.PlateNo" : {
								required : "请填写车牌号!"
							},
							"car.InsuranceDate" : {
								required : "请选择保险日期!"
							},
							"car.CarValue" : {
								required : "请输入车价!"
							},
							"car.CarOwner" : {
								required : "请输入车主姓名!",
								maxlength : "姓名应为1~15个字符!"
							},
							"car.ContactPhone" : {
								required : "请输入联系电话!",
								maxlength : "联系电话应为1~12个字符!"
							},
							"car.ContactEmail" : {
								required : "请输入邮箱!"
							},
							"car.BuyDate" : {
								required : "请选择购车年月!"
							}
						}
					});
				});
var count = 1;
/**
 * 表单验证
 * 
 * @return
 */
function doSave(pageLink) {
	var Validator = $("#carForm").validate();
	var CarProperty = $("#CarProperty").val();
	if (Validator.form()) {
		/*// 车牌中不能含有警、军、海、空、北、沈、兰、济、南、广、成
		var carNb = $("#carnum").val();
		var str = "警,军,海,空,北,沈,兰,济,南,广,成";

		for (i = 0; i < str.length; i++) {
			if (carNb.indexOf(str.split(",")[i]) != -1) {
				alert("车牌首字不能以\"警、军、海、空、北、沈、兰、济、南、广、成\" 开头");
				return;
			}
		}*/
		// 车主姓名不允许填写“不详”、“不祥”、“未知”、“不知道”、“公司”、“有限”、“集团”、“股份”词语
		var CarOwner = $("#CarOwner").val();
		var str = "不详,不祥,未知,不知道,公司,有限,集团,股份";

		for (j = 0; j < str.length; j++) {
			if (CarOwner.indexOf(str.split(",")[j]) != -1) {
				alert("姓名不允许填写\"不详、不祥、未知、不知道、公司、有限、集团、股份\" 词语");
				return;
			}
		}
		
		var InsureYear = $("#InsureYear").val();
		if(InsureYear==""){
			$("#errMsg").show();
			document.getElementById('errMsg2').innerHTML = "<strong class=\"msg-box n-right n-default\">"
					+ "<strong class=\"msg-wrap n-error\">"
					+ "<strong class=\"n-icon\"></strong><strong class=\"n-msg\" >"
					+ "请输入投保年份!</strong></strong></strong>";
			return false;
		}
		
		MiddlePA(pageLink);
	}
}

function callBackFun(data) {
	// 加载等待效果.然后跳转至保险公司.
	var cpsBjUrl = $("#CpsBgURL").val();
	var comp = $("#companyNo").val();
	var url;
	if (comp == "2021") {
		url = cpsBjUrl + 'login/' + data.jumpPath;
	} else {
		url = data.jumpPath;
	}
	window.location.href = url;
}
// 隐藏车牌号码
function hidCarNo() {
	if ($("#carnum").attr("disabled") == true) {
		$("#carnum").removeAttr("disabled");
		$("#carnum").attr('value', carNo);
		$("#CarProperty").val("");
		$("#BuyDate").val("");
	} else {
		$("#carnum").attr('disabled', 'disabled');
		$("#carnum").attr('value', '');
		$("#CarProperty").val("1");
		$("#errMsg").hide();
		var myDate = new Date();
		var year = myDate.getFullYear();
		var mon = myDate.getMonth() + 1;
		if (mon < 10) {
			mon = "0" + mon;
		}
		$("#BuyDate").val(year + "-" + mon);

	}

}
// 车牌号码校验
function CheckPlateNu() {
	var carNb = $("#carnum").val();
	var chrnum = /^([\u4e00-\u9fa5]|[\uFE30-\uFFA0])[a-zA-Z][a-zA-Z0-9]{5,6}$/;
	var num =  /\s+/;
	if (carNb == "") {
		$("#errMsg").show();
		document.getElementById('errMsg').innerHTML = "<strong class=\"msg-box n-right n-default\">"
				+ "<strong class=\"msg-wrap n-error\">"
				+ "<strong class=\"n-icon\"></strong><strong class=\"n-msg\" >"
				+ "请输入车牌号码!</strong></strong></strong>";
		return false;
	} else if(num.test(carNb)){
		$("#errMsg").show();
		document.getElementById('errMsg').innerHTML = "<strong class=\"msg-box n-right n-default\">"
				+ "<strong class=\"msg-wrap n-error\">"
				+ "<strong class=\"n-icon\"></strong><strong class=\"n-msg\" >"
				+ "车牌号码不可输入空格!</strong></strong></strong>";
	}else if (!chrnum.test(carNb)) {
		$("#errMsg").show();
		document.getElementById('errMsg').innerHTML = "<strong class=\"msg-box n-right n-default\">"
				+ "<strong class=\"msg-wrap n-error\">"
				+ "<strong class=\"n-icon\"></strong><strong class=\"n-msg\" >"
				+ "车牌号码格式错误!</strong></strong></strong>";
		return false;
	}else {
		$("#errMsg").hide();
	}

	return true;
}
// 校验车价
function CheckCarValue() {
	var CarValue = $("#CarValue").val();
	//var chrnum = CarValue.split(".");
	//var numm = /^[0-9]*$/;
	//var numAll = /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/;
	
	var numAll = /^\d+(|(\.\d{2})?|(\.\d{1})?)$/;
	if (!numAll.test(CarValue)){
		$("#errMsg1").show();
		document.getElementById('errMsg1').innerHTML = "<strong class=\"msg-box n-right n-default\">"
				+ "<strong class=\"msg-wrap n-error\">"
				+ "<strong class=\"n-icon\"></strong><strong class=\"n-msg\" >"
				+ "车价格式错误!</strong></strong></strong>";
		return;
		
	} else if (( parseInt(CarValue) > 9999) || ( parseInt(CarValue) < 0) ) {
		$("#errMsg1").show();
		document.getElementById('errMsg1').innerHTML = "<strong class=\"msg-box n-right n-default\">"
				+ "<strong class=\"msg-wrap n-error\">"
				+ "<strong class=\"n-icon\"></strong><strong class=\"n-msg\" >"
				+ "车价需小于10000万元!</strong></strong></strong>";
		return;
	}else {
		$("#errMsg1").hide();
	}
	

}

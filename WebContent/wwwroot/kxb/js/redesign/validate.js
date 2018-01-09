/**
 * @des: 开心保Wap： 表单校验
 * @maker: SongZairan
 * @date: 2016.6.1
 */
$.fn.wjDataVelify = function(opt) {

	var _opts, obj,
		jTarArea, jTarElms,jSbtBtn,
		tarBlurFunc, sbtClickFunc;

	return (function() {
		_opts = $.extend({
			tarArea : ".insure_list",		//校验的DOM作用域
			tarElms : ":input[data-verify]",	//校验的DOM元素集合
			selElms : ["elm_sel", "sel_date", "tour_sel"],	//选择框日期框的DOM元素集合
			sbtBtn : ".btn_insure a",		//提交按钮
			callBack : function() {}
		}, opt);

		_initDom();
		_addEvent();
	})();

	//初始化DOM元素
	function _initDom() {
		jTarArea = $(_opts.tarArea);
		jTarElms = $(_opts.tarElms, jTarArea);
		jSbtBtn = $(_opts.sbtBtn);
	}

	//事件绑定函数集合
	function _addEvent() {
		jTarElms.live("blur", tarBlurFunc);
		jSbtBtn.on("click", sbtClickFunc);
	}

	//校验的DOM元素blur事件后的回调函数
	function tarBlurFunc() {
		var verify = $(this).attr("data-verify");

		if(!verify || verify == "") return;

		var vefyAll = verify.split("&&");

		for(var i=0;i<vefyAll.length; i++) {
			var _key = vefyAll[i].split("|")[0];
			var _tip = vefyAll[i].split("|")[1];

			if(_key.match(/\[.*\]/)) {
				var _nKey = _key.split('[')[0];
				var _oper = _key.match(/\[.*\]/);
				var _len = _oper[0].split(',');
				var _min = _len[0].slice(1);
				var _max = _len[1].slice(0, -1);

				objValidate["TIP_FUN"](this, _nKey, _tip, _min, _max);
			} else {
				objValidate["TIP_FUN"](this, _key, _tip);
			}

			if(objValidate["IS_TIP"]) break;
		}
	}

	//提交按钮后的总体校验
	function sbtClickFunc(e) {
		e.preventDefault();

		var valiList = jTarArea;
		var inputElms = valiList.find(":input:not(:hidden)");

		inputElms.each(function(i, v) {
			if($(v).hasClass(_opts.selElms[0]) || $(v).hasClass(_opts.selElms[1]) || $(v).hasClass(_opts.selElms[2]) || $(v).hasClass(_opts.selElms[3])) {
				if($(v).val() == "请选择") {
					var errItem = $(v).parent().prev().find("span").text();

					$(v).parents("li").eq(0).find(objValidate["TIP_CLS"]).remove();
					$(v).parent().after($(objValidate["TIP_DOM"][0] + "请选择" + errItem + objValidate["TIP_DOM"][1]).fadeIn());
				}
			} else {
				$(v).blur();
			}
		});

		if(valiList.find(objValidate["TIP_CLS"]).length > 0) {
			if($(this).attr("data-button") != "popup") {
				var oError = valiList.find(objValidate["TIP_CLS"]).eq(0);
				var _top = oError.offset().top - oError.outerHeight() - $(".header_box").height();
				$("html, body").animate({scrollTop: _top}, 400);
			}
		} else {
			_opts.callBack();
		}
	}

}


/**
 * @des: 开心保Wap：投被保人表单校验
 * @maker: Songzairan
 * @date: 2015.9.28
 */
objValidate = {

	 "IS_TIP" : false,
	"TIP_CLS" : ".error",
	"TIP_DOM" : ["<div class='error'><em class='icon_e'></em><span>", "</span></div>"],
	
	"TIP_FUN" : function(elm, key, errorTxt, minLen, maxLen) {

		var condition;
		var elmValue = $(elm).val();
		var cardValue = $(elm).parents("li").siblings().find(".card_type").val();   //填写投保人身份证校验
		var iCardVal = $(elm).parents("li").siblings().find("#i_card").text();  	//填写投被保人身份证校验

		$(elm).val($.trim(elmValue));
		switch(key)
		{
			case "NOTNULL" :
				condition = (elmValue == "" || elmValue == null);
				break;
			case "LENGTH" :
				condition = (elmValue.length < minLen || elmValue.length > maxLen);
				break;
			case "CHINESE" :
				condition = (elmValue.match(/^[\u4E00-\u9FA5]+(\·)?[\u4E00-\u9FA5]+$/) == null);
				break;
			case "ENGLISH" :
				condition = (elmValue.match(/^([A-Za-z]+\s?)*[A-Za-z]$/) == null);
				break;
			case "CHANDEH" :
				condition = (elmValue.match(/((^[\u4E00-\u9FA5]+(\·)?[\u4E00-\u9FA5]+$)|(^([A-Za-z]+\s?)*[A-Za-z]$))/) == null);
				break;
			case "NUMANDEH" :
				condition = (elmValue.match(/^[0-9A-Za-z]+$/) == null);
				break;
			case "NUM" :
				condition = (elmValue.match(/^[0-9]+$/) == null);
				break;
			case "SELECTCARD" : 
				condition = (cardValue == "请选择" && elmValue != "");
				break;
			case "EMAIL" :
				condition = (elmValue.match(/^[A-Za-z0-9-_.]+[@][A-Za-z0-9]+([-_.][A-Za-z0-9]+)?(\.com|\.cn|\.net|\.org|\.hk|\.uk|\.cc|\.edu)$/) == null);
				break;
			case "TELPHONE" :
				condition = (elmValue.match(/^(13|14|15|17|18)\d{9}$/) == null);
				break;
			case "CARDTYPE" :
				condition = false;
				if(cardValue == "身份证" || iCardVal == "身份证"){
					var iSum = 0;
					if(!/^\d{17}(\d|x)$/i.test(elmValue)&&!/^\d{15}$/i.test(elmValue)) {
						 if(!/^\d{16}(x{2})$/i.test(elmValue)) {
							 condition=true;
						     errorTxt='请填写正确的身份证号';
						 }
					 }
					 //18位身份证处理
					 //在后面的运算中x相当于数字10,所以转换成a
					 if(!condition){
						 elmValue = elmValue.replace(/x$/i,"a");
						 sBirthday=elmValue.substr(6,4)+"-"+Number(elmValue.substr(10,2))+"-"+Number(elmValue.substr(12,2));
						 var d = new Date(sBirthday.replace(/-/g,"/"));
						 if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())){
							 condition=true;
						     errorTxt='请填写正确的身份证号';
						 }
					 }
					 if(!condition){
						 // 身份证编码规范验证
						  for(var i = 17;i>=0;i --)
						   iSum += (Math.pow(2,i) % 11) * parseInt(elmValue.charAt(17 - i),11);
						  if(/^\d{16}(x{1})(a{1})$/i.test(elmValue)){
							  iSum=12;
						  }
						  if(iSum%11!=1){
						      condition=true;
						      errorTxt='请填写正确的身份证号';
						   }
					 }
					 if(!condition){
						 // 判断是否屏蔽身份证
						  var words = new Array();
						  words = new Array("11111119111111111","12121219121212121");
						  for(var k=0;k<words.length;k++){
					        if (elmValue.indexOf(words[k])!=-1){
					        	condition=true;
							    errorTxt='请填写正确的身份证号';
					        }
						  }
					 }
				}
				var _thiscardtype = $(elm).parents(".fill_cont").find(".card_type").val();
				$(elm).parents(".fill_cont").siblings().each(function(){
					var _cardtype = $(this).find(".card_type").val();
					var _cardvalue= $(this).find("input[id$='recognizeeIdentityId']").val();
					if(_cardtype==_thiscardtype&&_cardvalue==elmValue){
						condition=true;
					    errorTxt='该证件号已购买此产品';
					}
				});
				break;
			case "EMAILANDTEL" :
				if(elmValue.match(/^[0-9]\d*$/) != null){
					var myreg = /^\d{11}$/; 
					var re=new RegExp(myreg);      
					if(!re.test(elmValue)){
						errorTxt='手机号码不正确';
						condition=true;	
					}
				}else{
					condition = false;
					if (elmValue != "")
					{
						var s=elmValue;
						var i = 1;
						var len = s.length;
						if (len > 50)
						{
							errorTxt='邮箱格式不正确';
							condition=true;
						}
						var pos1 = s.indexOf("@");
						var pos2 = s.indexOf(".");
						var pos3 = s.lastIndexOf("@");
						var pos4 = s.lastIndexOf(".");
	
						if ((pos1 <= 0)||(pos1 == len)||(pos2 <= 0)||(pos2 == len))
						{
							errorTxt='邮箱格式不正确';
							condition=true;
						}
						else
						{
							if( (pos1 == pos2 - 1) || (pos1 == pos2 + 1)|| ( pos1 != pos3 )|| ( pos4 < pos3 ) )
							{
								errorTxt='邮箱格式不正确';
								condition=true;
				    		}
				    	}
						var bag="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@";
						for (var i = 0; i < s.length; i++) {
							var c = s.charAt(i);
							if (bag.indexOf(c) == -1) {
								errorTxt='邮箱格式不正确';
								condition=true;
							}
						}
						var badChar ="><,[]{}?/+=|\:;!#$%^&()`";
						var  charflag=true;
						for (var i = 0; i < s.length; i++) {
							var c = s.charAt(i);
							if (badChar.indexOf(c) == -1) {
								errorTxt='邮箱格式不正确';
								charflag=false;
								break;
							}
						}
						if (charflag)
						{
							errorTxt='邮箱格式不正确';
							condition=true;
						}
					}
				}
				break;
			case "ZIPCODE" :
				condition = (elmValue.match(/^[0-9]{5,6}$/) == null);
				break;
			case "FIGHTTIME" :
				condition = false;
				var startdate = jQuery("#startdate").val()+" 00:00:00"; 
				startdate = new Date(startdate.replace(/\-/g, "\/"));  
				var enddate = jQuery("#enddate").val()+" 23:59:59";
				enddate = new Date(enddate.replace(/\-/g, "\/"));  
				if(new Date(elmValue.replace(/\-/g, "\/"))<startdate){
					errorTxt='起飞时间应不早于起保日期';
					condition=true;
				} else if (new Date(elmValue.replace(/\-/g, "\/"))>enddate){
					errorTxt='起飞时间应不晚于止保日期';
					condition=true;
				}else{
					errorTxt='';
				}
				break;
		    case "BENEFIT" : //<%--受益比例只能为大于0且小于等于100之间的整数--%>
				condition = false;
				var regu = /^[-]{0,1}[0-9]{1,}$/;
				if (regu.test(elmValue)) {
					if (str <= 0 || str > 100) {
						condition=true;
					}
					else {
						condition=false;
					}
				} else {
			    	condition=true;
				}
		    case "APPAGE" : 
				var arrBirthday = elmValue.split("-");
				if (arrBirthday.length == 1) {
					if (arrBirthday[0].length != 8) {
						condition = true;
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
					if (arrToday[1] > arrBirthdays[1]) {
						age = age + 1;
					} else if (arrToday[1] < arrBirthdays[1]) {
						//当前月小于出生月
					} else if (arrToday[2] >= arrBirthdays[2]) {
						//当前月等于出生月的时候，看出生日
						age = age + 1;
					}
				} else {
					if (arrBirthday[1].length == 1) {
						arrBirthday[1] = "0" + arrBirthday[1];
					}
					if (arrBirthday[2].length == 1) {
						arrBirthday[2] = "0" + arrBirthday[2];
					}
					var today = new Date();
					var arrToday = new Array();
					arrToday[0] = today.getFullYear();
					arrToday[1] = today.getMonth() + 1;
					arrToday[2] = today.getDate();
					var age = arrToday[0] - arrBirthday[0] - 1;
					//当前月大于出生月
					if (arrToday[1] > arrBirthday[1]) {
						age = age + 1;
					} else if (arrToday[1] < arrBirthday[1]) {
						//当前月小于出生月
					} else if (arrToday[2] >= arrBirthday[2]) {
						//当前月等于出生月的时候，看出生日
						age = age + 1;
					}
				}
				if (i>=151 ||i<=17){
					condition = true;
				}
				break;
			case "ADDRESS" :
		    	var sum = 0 ;
				for (var i=0; i<elmValue.length; i++) {
					var c = elmValue.charCodeAt(i);
					if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {

			  		} else {
			  			sum++;
			  		}
			  	}
				if(sum<=6){
			  		condition = true;
			  	} else {
			  		condition = false;
			  	}
		    	break;
			case "TRAVEL" :
		    	var comCode = jQuery("#productid").val().substring(0,4);
	    		var r = /\s+/g; 
	    		var s = elmValue.replace(r, " "); 
	    		if(comCode=="2071"){
	    			var len = s.length;
	    			if(len>=51){
	    				condition = true;
	    				errorTxt='旅游目的地长度应小于等于50';
	    			}
	    		}else{ 
	    			var arr = s.split(" "); 
	    			var alen = arr.length;  
	    			var tlen=11;
	    			if(s.indexOf("申根国家")!=-1){
	    				tlen=12;
	    			}
	    			if(alen>=tlen){
	    				condition = true;
	    				errorTxt='旅游目的地国家数应小于等于10';
	    			}
	    		}
	    		break;
		    case "BANKNO" :	//请填写正确的银行卡号
	    		var reg = new RegExp("^[0-9]*$");
				if (elmValue=="" || !reg.test(elmValue) || elmValue.length < 16){
	    			condition = true;
	    		}
		    	break;
			case "CFMBANKNO" :	//确认银行卡号
				var jBankNo = $(".bank_no");
				if(jBankNo.first().val() != jBankNo.last().val()) {
					condition = true;
				}
				break;
			case "CFMPASSWORD" ://确认密码
				var jPassword = $(".pass_word");
				if(jPassword.first().val() != jPassword.last().val()) {
					condition = true;
				}
				break;
			case "BFYPROPORTION" :	//受益比例
				if(elmValue < 1 || elmValue > 100) condition = true;
				break;
		}
		$(elm).parent().next(objValidate["TIP_CLS"]).remove();
	
		if(condition) { 
			var _tip = objValidate["TIP_DOM"][0] + errorTxt + objValidate["TIP_DOM"][1];

			$(elm).parent().after($(_tip).fadeIn());
			objValidate["IS_TIP"] = true;
		} else {
			objValidate["IS_TIP"] = false;
		}
		
	}
	
}
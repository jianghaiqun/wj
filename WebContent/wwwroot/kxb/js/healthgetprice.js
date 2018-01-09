/*
 *@des:前海人寿保费试算
 *@maker:dongqi.guo
 *@time:2014-12-17-15:20
 */

var userAge;
function changeAge() {
	var userage = parseInt(getAge(jQuery('#inpRiskAppFactor_TextAge').val()));
	var maxage = parseInt(jQuery('#qhuserMaxAge').val()); //投保年龄最大值
	var minage = parseInt(jQuery('#qhuserAge').val()) //投保年龄最小值
	var shopdata = jQuery('ul[id$=_FeeYear]');
	var shopmoney= jQuery('ul[id$=_Plan]');
	var shop_data_child = shopdata.children();
	var shop_data_id = []; //获取默认续费期限的元素id
	var z_list = []; //中转数组
	var select_li = shopdata.children("li_selected");

	if (userage > maxage) {
		alert("年龄不能超过" + jQuery('#qhuserMaxAge').val() + "周岁！");
		jQuery('#UserBirthDay').val('');
		return false;
	}
	if (jQuery('#qhDayOrYear').val() == "1") {
		//最小年龄的单位为周岁
		if (userage < minage) {
			alert("年龄不能小于" + jQuery('#qhuserAge').val() + "周岁！");
			jQuery('#UserBirthDay').val('')
			return false;
		}
	}

	jQuery('#user_age').html(userage + "周岁"); //提示用户输入的年龄

	var strdata = []; // 为保存JSON KEYNAME使用
	var strmoney = [];

	jQuery.each(AppFacDutyRela, function(key, val) {
		var strdata_id=shopdata.attr("id")+"";
		strdata = eval("AppFacDutyRela[key]."+strdata_id);
		//console.log(strdata);
		var shopmoney_id=shopmoney.attr("id")+"";
		strmoney = eval("AppFacDutyRela[key]."+shopmoney_id);
		if (userage == key) {
			var paySpanVal = shopdata.find(".li_selected").children().attr("id");
			var paySpanVal2 = shopmoney.find(".li_selected").children().attr("id");
			shopdata.children().hide();
			shopmoney.children().hide();

			//交费期限

			function SelectList(lists, vars) {
				for (var j = 0; j < lists.length; j++) {
					var s = "#" + lists[j];
					jQuery(s).parent().show();
				}
				if (IsInArray(lists, vars) == false) {
					var SpanId = lists[lists.length - 1];
					var d = "#" + SpanId;
					jQuery(d).click();
				}
			}

			SelectList(strdata, paySpanVal)
			SelectList(strmoney, paySpanVal2)

			//修改侧边栏快速筛选的值
			var jCp = jQuery(".cp_descon");
			var jCpDiv = jQuery(".tb_age:not(:hidden), .paln_con:not(:hidden)",jCp);
			jQuery(".bnr_sel>.select_ul").empty();

			jCpDiv.each(function(i, v) {
				var _vEm = jQuery(v).find(".cp_title_ms").text();
				var _vInput = jQuery(v).find("ul > .li_selected").children()
						.text();
				var _vList = jQuery(v).children().find(
						"span:not(.help_selec_ss, .elect_plan,:hidden)");

				for (var x = 0; x < _vList.length; x++) {
					if (_vList.eq(x).children().is("a")) {
						var _txt = _vList.eq(x).html();
					} else {
						var _txt = _vList.eq(x).text();
					}
					jQuery(".zIn0" + (i + 1) + ">.select_ul").append(
							"<li><span>" + _txt + "</span></li>");

				}

				jQuery(".zIn0" + (i + 1) + "> .txt_sel").val(_vInput);

			})

		}

	});

}
// 获取json二维数字对象的长度
function getJsonObjLength(jsonObj) {
	var Length = 0;
	for ( var item in jsonObj) {
		Length++;
	}
	return Length;
}
//判断变量是否存储在数组中，用来定位用户的选择操作
function IsInArray(arr, val) {
	var testStr = ',' + arr.join(",") + ",";
	return testStr.indexOf("," + val + ",") != -1;
}

//计算年龄，格式化出生日期
function getAge(birth) {
	var r = birth.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	if (r == null)
		return;
	var d = new Date(r[1], r[3] - 1, r[4]);
	var date = GetNowDateAdd(1);

	var newday = date.getDate();
	var newmonth = date.getMonth();
	var newyear = date.getFullYear();

	if ((newyear < r[1]) == true) {
		alert('出生日期不能大于当前日期');
		return;
	}
	if ((newyear == r[1] && newmonth < (r[3] - 1)) == true) {
		alert('出生日期不能大于当前日期');
		return;
	}
	if ((newyear == r[1] && (newmonth == (r[3] - 1)) && newday < r[4]) == true) {
		alert('出生日期不能大于当前日期');
		return;
	}

	if (date.getMonth() + 1 > r[3]) {
		return (newyear - r[1]);
	} else if (date.getMonth() + 1 == r[3]) {
		if (newday >= r[4]) {
			return (newyear - r[1]);
		} else {
			return (newyear - r[1] - 1);
		}
	} else {
		return (newyear - r[1] - 1);
	}
}

//今天的日期加上 addday 之后的日期
function GetNowDateAdd(addday) {
	var today = new Date();
	var day1 = today.getDate();
	day1 = parseInt(day1) + parseInt(addday);
	var end = new Date(today.getFullYear(), today.getMonth(), day1);
	return end;
}

setInEnd('Checkzjnum_long', 'zjnum_day', 'Zjnum_tip'); 
jQuery('#Checkzjnum_long').bind("click", function () { setInEnd('Checkzjnum_long', 'zjnum_day', 'Zjnum_tip'); });


 function setInEnd(checkident,txtident,tipident)
{
    if(jQuery('#'+checkident).attr("checked")==true)
    {
        jQuery('#'+txtident).val("");
        jQuery('#'+txtident).attr("disabled","disabled"); 
        jQuery('#'+tipident).addClass("yz_mes_yes");
    }
    else
    {
      jQuery('#'+txtident).attr("disabled",""); 
     jQuery('#'+tipident).removeClass("yz_mes_yes");
    }
}
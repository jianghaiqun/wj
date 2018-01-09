/**
 * 
 */

function inswindowCps(txt) {
	var src = getcookie('inswindow_src');
	var channel = getcookie('inswindow_channel');
	if (src == 'inswindow_cps' && channel == 'cps') {
		document.getElementById('inswindow').innerHTML = txt;
	}
}
function hideMessage(orderSn) {
	jQuery("#error_" + orderSn).hide();
}
function getcookie(name) {
	var strcookie = document.cookie;
	var arrcookie = strcookie.split("; ");
	for (var i = 0; i < arrcookie.length; i++) {
		var arr = arrcookie[i].split("=");
		if (arr[0] == name)
			return arr[1];
	}
	return "";
}
var p_url = jQuery("#jrhsURL").val();
sethash();

var Class = {
	create : function() {
		return function() {
			this.initialize.apply(this, arguments);
		};
	}
};
var Extend = function(destination, source) {
	for ( var property in source) {
		destination[property] = source[property];
	}
};
function stopDefault(e) {
	if (e && e.preventDefault) {
		e.preventDefault();
	} else {
		window.event.returnValue = false;
	}
	return false;
}

/**
 * 星星打分组件
 */
var Stars = Class.create();
Stars.prototype = {
	initialize : function(star, options) {
		this.SetOptions(options); // 默认属性
		var flag = 999; // 定义全局指针
		var isIE = (document.all) ? true : false; // IE?
		var starlist = document.getElementById(star).getElementsByTagName('a'); // 星星列表
		var input = document.getElementById(this.options.Input)
				|| document.getElementById(star + "_input"); // 输出结果
		var tips = document.getElementById(this.options.Tips)
				|| document.getElementById(star + "_tips"); // 打印提示
		var nowClass = " " + this.options.nowClass; // 定义选中星星样式名
		var tipsTxt = this.options.tipsTxt; // 定义提示文案
		var tipsstyle = this.options.tipsstyle; // 定义提示文案样式
		var len = starlist.length; // 星星数量

		for (i = 0; i < len; i++) { // 绑定事件 点击 鼠标滑过
			starlist[i].value = i;
			starlist[i].onclick = function(e) {
				stopDefault(e);
				this.className = this.className + nowClass;
				flag = this.value;
				input.value = this.getAttribute("star:value");
				tips.innerHTML = tipsTxt[this.value];
				var s = jQuery(this).parent().parent().siblings(".error_red")
						.text("");
			};
			starlist[i].onmouseover = function() {
				if (flag < 999) {
					var reg = RegExp(nowClass, "g");
					starlist[flag].className = starlist[flag].className
							.replace(reg, "");
					// tips.innerHTML = tipsTxt[this.value]
				}
			};
			starlist[i].onmouseout = function() {
				if (flag < 999) {
					starlist[flag].className = starlist[flag].className
							+ nowClass;

				}
			};
		}
		;
		if (isIE) { // FIX IE下样式错误
			var li = document.getElementById(star).getElementsByTagName('li');
			for (var i = 0, len = li.length; i < len; i++) {
				var c = li[i];
				if (c) {
					c.className = c.getElementsByTagName('a')[0].className;
				}
			}
		}
	},
	// 设置默认属性
	SetOptions : function(options) {
		this.options = {// 默认值
			Input : "",// 设置触保存分数的INPUT
			Tips : "",// 设置提示文案容器
			nowClass : "current-rating",// 选中的样式名
			tipsTxt : [ '<span class=\'yb_style\'>一般</span>',
					'<span class=\'yb_style\'>一般</span>',
					'<span class=\'my_style\'>满意</span>',
					'<span class=\'my_style\'>满意</span>',
					'<span class=\'jx_style\'>惊喜</span>' ]
		// 提示文案
		};
		Extend(this.options, options || {});
	}
};

function showComment(ordersn) {
	var Stars1 = new Stars('stars1_' + ordersn);
	var Stars2 = new Stars('stars2_' + ordersn);
	var Stars3 = new Stars('stars3_' + ordersn);
	var Stars4 = new Stars('stars4_' + ordersn);
}

jQuery(function() {
	var ordersn = jQuery("#OrdId").val().replace('<br>', '').split(',');

	jQuery.each(ordersn, function(i, n) {
		showComment(n);
	});
});
// 获取字符串的字节长度
function getByteLength(str) {
	if (null == str) {
		return 4;
	}
	if ("undefined" == typeof str) {
		return 9;
	}
	var len = str.length;
	var byteLen = 0;
	for (var i = 0; i < len; i++) {
		if (str.charCodeAt(i) > 255) {
			byteLen += 2;
		} else {
			byteLen += 1;
		}
	}
	return byteLen;
}
function submitComment(orderSn) {
	// 评论内容
	var content = jQuery("#content_" + orderSn).val();
	if (content == null || content == '' || getByteLength(content) < 20
			|| "产品是否给力？分享你的购买心得给大家吧!" == content) {
		jQuery("#error_" + orderSn).text("您需要填写10个以上的字哦！");
		jQuery("#error_"+orderSn).css("display","inline-block");
		return;
	} else if (getByteLength(content) > 1000) {
		jQuery("#error_" + orderSn).text("字数超了～评论的内容只能写500个字以内哟！");
		jQuery("#error_"+orderSn).css("display","inline-block");
		return;
	}

	// 保障范围
	var CoverageScore = document.getElementById("stars1_" + orderSn + "_input").value;
	if (CoverageScore == null || CoverageScore == ''||!jQuery("#stars1_"+orderSn).find("a").hasClass("current-rating")) {
		jQuery("#stars_" + orderSn + "_error").text("您的评分是我们前进的动力");
		return;
	}
	// 保障程度
	var DescribeScore = document.getElementById("stars2_" + orderSn + "_input").value;
	if (DescribeScore == null || DescribeScore == ''|| !jQuery("#stars2_"+orderSn).find("a").hasClass("current-rating")) {
		jQuery("#stars_" + orderSn + "_error").text("您的评分是我们前进的动力");
		return;
	}
	// 保障价格
	var PolicyScore = document.getElementById("stars3_" + orderSn + "_input").value;
	if (PolicyScore == null || PolicyScore == ''||!jQuery("#stars3_"+orderSn).find("a").hasClass("current-rating")) {
		jQuery("#stars_" + orderSn + "_error").text("您的评分是我们前进的动力");
		return;
	}
	// 售后服务
	var clientScore = document.getElementById("stars4_" + orderSn + "_input").value;
	if (clientScore == null || clientScore == ''||!jQuery("#stars4_"+orderSn).find("a").hasClass("current-rating")) {
		jQuery("#stars_" + orderSn + "_error").text("您的评分是我们前进的动力");
		return;
	}
	// 投保目的
	var purpose = jQuery("#purpose_" + orderSn).val();
	if (purpose == null || "undefined" == typeof(purpose)) {
 		purpose = "";
 	}
	
	jQuery("#cp_pl_btns_"+orderSn).addClass(" vsbtn"); 
	jQuery("#cp_pl_btns_"+orderSn).attr("disabled","disabled"); 
	jQuery.ajaxLoadImg.show('comment_LoadImg_'+orderSn);
	jQuery.ajax({
		type : 'post',
		url : sinosoft.base + '/shop/comment!commentSubmit.action',
		dataType : "json",
		data : "orderSn=" + orderSn + "&coverageScore=" + CoverageScore
				+ "&content=" + content + "&describeScore=" + DescribeScore
				+ "&policyScore=" + PolicyScore + "&clientScore=" + clientScore
				+ "&purpose=" + purpose+"&source=pay",
		async : true,
		success : function(data) {
			jQuery.ajaxLoadImg.hide('comment_LoadImg_'+orderSn);
 			jQuery("#cp_pl_btns_"+orderSn).attr("disabled","");
		 	jQuery("#cp_pl_btns_"+orderSn).removeClass(" vsbtn"); 
			if (data.status == "success") {
				jQuery("#stars1_" + orderSn + "_input").val("");
				jQuery("#stars2_" + orderSn + "_input").val("");
				jQuery("#stars3_" + orderSn + "_input").val("");
				jQuery("#stars4_" + orderSn + "_input").val("");
				jQuery("#comment_" + orderSn).find(".success_plcs").show();
				jQuery("#comment_" + orderSn).find(".success_plcs").find("i")
						.text(data.Points);
				jQuery("#comment_" + orderSn).find(".order_tits").hide();
				jQuery("#comment_" + orderSn).find(".order-xing").hide();
				jQuery("#comment_" + orderSn).find(".order-xing-pl").hide();
			} else {
				jQuery("#stars_"+orderSn+"_error").text(data.message);
			}
		}
	});
}
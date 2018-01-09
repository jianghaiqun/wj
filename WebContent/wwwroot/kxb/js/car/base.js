/***
 *
 *	http://www.sinosoft.com.cn
 *
 **/
 
var sinosoft = {
	front: "http://localhost:8080",
	base: "http://localhost:8080/wj",
	staticPath : "http://localhost:8080/",
	jsPath: "http://localhost:8080/js",
	cssPath: "http://localhost:8080/style",
	currencySign: "￥",// 货币符号
	currencyUnit: "元",// 货币单位
	priceScale: "2",// 商品价格精确位数
	priceRoundType: "roundHalfUp",// 商品价格精确方式
	orderScale: "2",// 订单金额精确位数
	orderRoundType: "roundHalfUp"// 订单金额精确方式
};

if(!window.XMLHttpRequest) {

	// 解决IE6透明PNG图片BUG
	//DD_belatedPNG.fix(".png");
	
	// 解决IE6不缓存背景图片问题
	document.execCommand("BackgroundImageCache", false, true);

}

// 添加收藏夹
function addFavorite(url, title) {
	if (document.all) {
		window.external.addFavorite(url, title);
	} else if (window.sidebar) {
		window.sidebar.addPanel(title, url, "");
	}
}

// 浮点数加法运算
function floatAdd(arg1, arg2) {
	var r1, r2, m;
	try{
		r1 = arg1.toString().split(".")[1].length;
	} catch(e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch(e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
}

// 浮点数减法运算
function floatSub(arg1, arg2) {
	var r1, r2, m, n;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch(e) {
		r1 = 0
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch(e) {
		r2 = 0
	}
	m = Math.pow(10, Math.max(r1, r2));
	n = (r1 >= r2) ? r1 : r2;
	return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

// 浮点数乘法运算
function floatMul(arg1, arg2) {    
	var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
	try {
		m += s1.split(".")[1].length;
	} catch(e) {}
	try {
		m += s2.split(".")[1].length;
	} catch(e) {}
	return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

// 浮点数除法运算
function floatDiv(arg1, arg2) {
	var t1 = 0, t2 = 0, r1, r2;    
	try {
		t1 = arg1.toString().split(".")[1].length;
	} catch(e) {}
	try {
		t2 = arg2.toString().split(".")[1].length;
	} catch(e) {}
	with(Math) {
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * pow(10, t2 - t1);
	}
}

// 设置数值精度
function setScale(value, scale, roundingMode) {
	if (roundingMode.toLowerCase() == "roundhalfup") {
		return (Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale)).toFixed(scale);
	} else if (roundingMode.toLowerCase() == "roundup") {
		return (Math.ceil(value * Math.pow(10, scale)) / Math.pow(10, scale)).toFixed(scale);
	} else {
		return (Math.floor(value * Math.pow(10, scale)) / Math.pow(10, scale)).toFixed(scale);
	}
}

// 格式化商品价格货币
function priceCurrencyFormat(price) {
	price = setScale(price, sinosoft.priceScale, sinosoft.priceRoundType);
	return sinosoft.currencySign + price;
}

// 格式化商品价格货币（包含货币单位）
function priceUnitCurrencyFormat(price) {
	price = setScale(price, sinosoft.priceScale, sinosoft.priceRoundType);
	return sinosoft.currencySign + price + sinosoft.currencyUnit;
}

// 格式化订单金额货币
function orderCurrencyFormat(price) {
	price = setScale(price, sinosoft.orderScale, sinosoft.orderRoundType);
	return sinosoft.currencySign + price;
}

// 格式化订单金额货币（包含货币单位）
function orderUnitCurrencyFormat(price) {
	price = setScale(price, sinosoft.orderScale, sinosoft.orderRoundType);
	return sinosoft.currencySign + price + sinosoft.currencyUnit;
}

jQuery().ready( function() {
	// 滑动提示框
	jQuery("body").prepend('<div id="tipWindow" class="tipWindow"><span class="icon">&nbsp;</span><span class="messageText"></span></div>');
	
	// 滑动提示框
	jQuery.tip = function () {
		var $tipWindow = jQuery("#tipWindow");
		var $icon = jQuery("#tipWindow .icon");
		var $messageText = jQuery("#tipWindow .messageText");
		var messageType;
		var messageText;
		if (arguments.length == 1) {
			messageType = "warn";
			messageText = arguments[0];
		} else {
			messageType = arguments[0];
			messageText = arguments[1];
		}
		if (messageType == "success") {
			$icon.removeClass("warn").removeClass("error").addClass("success");
		} else if (messageType == "error") {
			$icon.removeClass("warn").removeClass("success").addClass("error");
		} else {
			$icon.removeClass("success").removeClass("error").addClass("warn");
		}
		$messageText.html(messageText);
		$tipWindow.css({"margin-left": "-" + parseInt($tipWindow.width() / 2) + "px", "left": "50%"});
		setTimeout(function() {
			$tipWindow.animate({left: 0, opacity: "hide"}, "slow");
		}, 3000);
		$tipWindow.show();
	}
});
/***
 *
 *	http://www.sinosoft.com.cn
 *
 **/

$().ready( function() {

	$productListForm = $("#productListForm");
	$pageNumber = $("#pageNumber");
	$pageSize = $("#pageSize");
	$orderType = $("#orderType");
	$viewType = $("#viewType");
	$tableType = $("#tableType");
	$pictureType = $("#pictureType");
	$addFavorite = $(".addFavorite");
	$addCartItem = $(".addCartItem");
	$addCartItemTip = $("#addCartItemTip");
	$tipClose = $("#addCartItemTip .tipClose");
	$addCartItemTipMessageIcon = $("#addCartItemTipMessageIcon");
	$addCartItemTipMessage = $("#addCartItemTipMessage");
	$addCartItemTipInfo = $("#addCartItemTipInfo");
	
	// 商品分类菜单
	$(".productCategory ul.menu").superfish({
		delay: 100
	});
	
	// 缩略图滚动栏
	$(".productContent .scrollable").scrollable({
		speed: 600
	});
	
	// Tab效果
	$(".productContent ul.productImageTab").tabs(".productImage .tabContent", {
		event: "mouseover"// 触发tab切换的事件
	});
	
	// Tab效果
	$(".productContent ul.productAttributeTab").tabs(".productBottom .tabContent", {
		effect: "fade",// 逐渐显示动画
		fadeInSpeed: 500,// 动画显示速度
		event: "mouseover"// 触发tab切换的事件
	});
	
	// 商品图片放大镜效果
	$("a.zoom").zoomimage({
		opacity: 0.6,
		controlsTrigger: "mouseover",
		controls: true,
		centered: true,
		hideSource: true
	});
	
	// 更多商品参数
	var productAttributeTab = $(".productContent ul.productAttributeTab").tabs();
	$("#moreProductAttribute").click( function() {
		var index = productAttributeTab.getIndex();
		if (index == 0) {
			productAttributeTab.next();
		}
	});
	
	// 每页显示数
	$pageSize.change( function() {
		$pageNumber.val("1");
		$productListForm.submit();
	});
	
	// 商品排序
	$orderType.change( function() {
		$pageNumber.val("1");
		$productListForm.submit();
	});
	
	// 列表方式查看
	$tableType.click( function() {
		$viewType.val("tableType");
		$productListForm.submit();
		return false;
	});
	
	// 图片方式查看
	$pictureType.click( function() {
		$viewType.val("pictureType");
		$productListForm.submit();
		return false;
	});
	
	// 添加商品至购物车
	$addCartItem.click( function() {
		var $this = $(this);
		var id = $this.metadata().id;
		
		var x = $this.offset().left - 50;
		var y = $this.offset().top + $this.height() + 6;
		$addCartItemTip.css({"left" :x, "top" :y});
		
		var quantity = $("#quantity").val();
		if (quantity == null) {
			quantity = 1;
		}
		var reg = /^[0-9]*[1-9][0-9]*$/;
		if (!reg.test(quantity)) {
			$addCartItemTipMessageIcon.removeClass("successIcon").addClass("errorIcon");
			$addCartItemTipMessage.text("加入购物车失败！");
			$addCartItemTipInfo.text("商品数量必须为正整数！");
			$addCartItemTip.fadeIn();
			return false;
		}
		$.ajax({
			url: sinosoft.base + "/shop/cart_item!ajaxAdd.action",
			data: {"id": id, "quantity": quantity},
			dataType: "json",
			beforeSend: function() {
				$this.attr("disabled", true);
			},
			success: function(data) {
				$.flushCartItemList();
				if (data.status == "success") {
					$addCartItemTipMessageIcon.removeClass("errorIcon").addClass("successIcon");
					$addCartItemTipMessage.text(data.message);
					$addCartItemTipInfo.text("共计商品：" + data.totalQuantity + "件，总计金额：" + data.totalPrice);
				} else if (data.status == "error") {
					$addCartItemTipMessageIcon.removeClass("successIcon").addClass("errorIcon");
					$addCartItemTipMessage.text(data.message);
					$addCartItemTipInfo.empty();
				}
				$addCartItemTip.fadeIn();
				$this.attr("disabled", false);
			}
		});
	});
	
	// 添加购物车提示框隐藏
	$tipClose.click( function() {
		$addCartItemTip.fadeOut();
		return false;
	});
	
	// 产品收藏
	$addFavorite.click( function() {
		var $this = $(this);
		if ($.cookie("loginMemberUsername") == null) {
			$.flushHeaderInfo();
			$.loginWindowShow();
			return false; 
		} else {
			var id = $(this).metadata().id;
			$.ajax({
				url: sinosoft.base + "/shop/favorite!ajaxAdd.action",
				data: {"id": id},
				dataType: "json",
				beforeSend: function() {
					$this.attr("disabled", true);
				},
				success: function(data) {
					$.tip(data.status, data.message);
					$this.attr("disabled", false);
				},
				error: function(data) {
					if ($.cookie("loginMemberUsername") == null) {
						$.flushHeaderInfo();
						$.loginWindowShow();
						return false;
					}
					$this.attr("disabled", false);
				}
			});
		}
	});
	
	// 添加商品浏览记录
	if ($.cookie("loginMemberUsername") == null) {//对于普通用户
	var maxProductHistoryListCount = 5; // 最大商品浏览记录数
//   //吴高强添加	
//    function date2str(x,y) {
//	 var z = {M:x.getMonth()+1,d:x.getDate(),h:x.getHours(),m:x.getMinutes(),s:x.getSeconds()};
//	 y = y.replace(/(M+|d+|h+|m+|s+)/g,function(v) {return ((v.length>1?"0":"")+eval('z.'+v.slice(-1))).slice(-2)});
//	 return y.replace(/(y+)/g,function(v) {return x.getFullYear().toString().slice(-v.length)});
//			}
//	date1=date2str(new Date(),"yyyy-MM-dd");
	$.addProductHistory = function(name, htmlFilePath) {
		var productHistory = {
			name: name,
			htmlFilePath: htmlFilePath
//			date :date1
		};
		var productHistoryArray = new Array();
		var productHistoryListCookie = $.cookie("productHistoryList");
		if(productHistoryListCookie) {
			productHistoryArray = eval(productHistoryListCookie);
		}
		var productHistoryListHtml = "";
		for (var i in productHistoryArray) {
			productHistoryListHtml += '<li><a href="' + productHistoryArray[i].htmlFilePath + '">' + productHistoryArray[i].name + '</a></li>';
		}
		for (var i in productHistoryArray) {
			if(productHistoryArray[i].htmlFilePath == htmlFilePath) {
				return;
			}
		}
		//吴高强添加
//		function   getDateFromString(strDate) 
//		{ 
//	        var   arrYmd   =   strDate.split( "- ");; 
//	        var   numYear   =   parseInt(arrYmd[0]); 
//	        var   numMonth   =   parseInt(arrYmd[1])   -   1; 
//	        var   numDay   =   parseInt(arrYmd[2]); 
//	        return   new   Date(numYear,   numMonth,   numDay); 
//	   } 
			if(productHistoryArray.length >= maxProductHistoryListCount) {
 			productHistoryArray.shift();
    		}
		//吴高强添加
//		for (var i in productHistoryArray) {
//			datefrom = getDateFromString(date1);
//			dateto = getDateFromString(productHistoryArray[i].date);
//			var numDays =(datefrom - dateto )/(1000*60*60*24); 
//
//			if(numDays>183) {
//			 productHistoryArray.shift();
//			}
//		}
		productHistoryArray.push(productHistory);
		var newProductHistoryCookieString = "";
		for (var i in productHistoryArray) {
			newProductHistoryCookieString += ',{name: "' + productHistoryArray[i].name + '", htmlFilePath: "' + productHistoryArray[i].htmlFilePath + '"}'
		}
		newProductHistoryCookieString = "[" + newProductHistoryCookieString.substring(1, newProductHistoryCookieString.length) + "]";
		$.cookie("productHistoryList", newProductHistoryCookieString, {path: "/"});
	}
// 商品浏览记录列表
	var productHistoryArray = new Array();
	var productHistoryListCookie = $.cookie("productHistoryList");
	if(productHistoryListCookie) {
		productHistoryArray = eval(productHistoryListCookie);
	}
	var productHistoryListHtml = "";
	for (var i in productHistoryArray) {
		productHistoryListHtml += '<li><span class="icon">&nbsp;</span><a href="' + productHistoryArray[i].htmlFilePath + '">' + productHistoryArray[i].name + '</a></li>';
	}
	$("#productHistoryListDetail").html(productHistoryListHtml);

}//else {
//	//添加浏览记录到数据库
//	$.ajax({
//		url: sinosoft.base + "/shop/favorite!ajaxAdd.action",
//		data: {"id": id},
//		dataType: "json",
//		beforeSend: function() {
//			$this.attr("disabled", true);
//		},
//		success: function(data) {
//			$.tip(data.status, data.message);
//			$this.attr("disabled", false);
//		},
//		error: function(data) {
//			if ($.cookie("loginMemberUsername") == null) {
//				$.flushHeaderInfo();
//				$.loginWindowShow();
//				return false;
//			}
//			$this.attr("disabled", false);
//		}
//	});
//	
//	
//}
	});
/***
 *
 *	http://www.sinosoft.com.cn
 *
 **/

jQuery().ready( function() {
	
	// 刷新header登录、注册信息
	jQuery.flushHeaderInfo = function () {
		var $headerShowLoginWindow = jQuery("#headerShowLoginWindow");
		var $headerShowRegisterWindow = jQuery("#headerShowRegisterWindow");
		var $headerLoginMemberUsername = jQuery("#headerLoginMemberUsername");
		var $headerMemberCenter = jQuery("#headerMemberCenter");
		var $headerRegister = jQuery("#headerRegister");
		var $headerLogout = jQuery("#headerLogout");
		 jQuery.ajax({
		        url: sinosoft.base+"/shop/member!checkLogin.action",
		        type: "post",
				dataType: "json",
				success: function(data) {
					if (data == false) {
						if(jQuery.cookie("loginMemberUsername") != null) {
							$headerLoginMemberUsername.text(jQuery.cookie("loginMemberUsername"));
							$headerMemberCenter.show();
							$headerLogout.show();
							$headerRegister.hide();
							$headerShowLoginWindow.hide();
							$headerShowRegisterWindow.hide();
						} 
						
					}else{
						$headerLoginMemberUsername.text("");
						$headerShowLoginWindow.show();
						$headerShowRegisterWindow.show();
						$headerMemberCenter.hide();
						$headerRegister.show();
						$headerLogout.hide();
						
					}
				}
			});
//		if(jQuery.cookie("loginMemberUsername") != null) {
//			$headerLoginMemberUsername.text(jQuery.cookie("loginMemberUsername"));
//			$headerMemberCenter.show();
//			$headerLogout.show();
//			$headerRegister.hide();
//			$headerShowLoginWindow.hide();
//			$headerShowRegisterWindow.hide();
//		} else {
//			$headerLoginMemberUsername.text("");
//			$headerShowLoginWindow.show();
//			$headerShowRegisterWindow.show();
//			$headerMemberCenter.hide();
//			$headerRegister.show();
//			$headerLogout.hide();
//		}
		 
	};
	
	jQuery.flushHeaderInfo();
	
	// 商品搜索
	$productSearchKeyword = jQuery("#productSearchKeyword");
	productSearchKeywordDefaultValue = jQuery("#productSearchKeyword").val();
	$productSearchKeyword.focus( function() {
		if ($productSearchKeyword.val() == productSearchKeywordDefaultValue) {
			$productSearchKeyword.val("");
		}
	});
	
	$productSearchKeyword.blur( function() {
		if ($productSearchKeyword.val() == "") {
			$productSearchKeyword.val(productSearchKeywordDefaultValue);
		}
	});
	
	jQuery("#productSearchForm").submit( function() {
		if (jQuery.trim($productSearchKeyword.val()) == "" || jQuery.trim($productSearchKeyword.val()) == productSearchKeywordDefaultValue) {
			return false;
		}
	});
	
	// 显示购物车信息
	var maxShowCartItemListCount = 30; // 最大购物车项数，为null则不限制
	var isAjaxDate = true;
	jQuery(".showCartItemList").hover(
		function() {
			jQuery("#cartItemListDetail").stop(true, true);
			jQuery("#cartItemListDetail").fadeIn();
			if (isAjaxDate) {
				jQuery.ajax({
					url: sinosoft.base + "/shop/cart_item!ajaxList.action",
					dataType: "json",
					beforeSend: function() {
						jQuery("#cartItemListDetail").html('<li><span class="loadingIcon">&nbsp;</span>正在加载...</li>');
					},
					success: function(data) {
						isAjaxDate = false;
						var cartItemInfoHtml = "";
						var cartItemListHtml = "";
						jQuery.each(data, function(index, object) {
							if (index == 0) {
								if (object.totalQuantity == 0) {
									cartItemInfoHtml = '<li>您的购物车中暂无商品！</li>';
								} else {
									cartItemInfoHtml = '<li><span class="rightArea">共 ' + object.totalQuantity + ' 件 总计：<strong>' + object.totalPrice + '</strong></span></li>';
								}
							} else {
								cartItemListHtml = '<li><a class="leftArea" href="' + sinosoft.base + object.htmlFilePath + '" title="' + object.name + '">' + object.name.substring(0, 12) + '</a><span class="rightArea"><strong>' + object.price + ' × ' + object.quantity + '</strong></span></li>' + cartItemListHtml;
							}
							if (maxShowCartItemListCount != null && index >= maxShowCartItemListCount) {
								return false;
							}
						});
						jQuery("#cartItemListDetail").html(cartItemListHtml + cartItemInfoHtml);
					}
				});
			}
		},
		function() {
			jQuery("#cartItemListDetail").fadeOut();
		}
	);
	
	jQuery("#cartItemListDetail").hover(
		function() {
			jQuery("#cartItemListDetail").stop(true, true);
			jQuery("#cartItemListDetail").show();
		},
		function() {
			jQuery("#cartItemListDetail").fadeOut();
		}
	);
	
	// 刷新购物车信息
	jQuery.flushCartItemList = function() {
		isAjaxDate = true;
	};
	
	
	/**
===========	 * 以下代码显示公司简介======================================================================================
	 */
	// 显示公司简介信息
//			var maxShowCartItemListCount = 30; // 最大购物车项数，为null则不限制
			var isAjaxDate1 = true;
			jQuery(".showBrandInstructionList").hover(
				function() {
					jQuery("#brandInstructionListDetail").stop(true, true);
					jQuery("#brandInstructionListDetail").fadeIn();
					if (isAjaxDate1) {
						jQuery.ajax({
							url: sinosoft.base + "/shop/product!ajaxList.action",
							dataType: "json",
							beforeSend: function() {
								jQuery("#brandInstructionListDetail").html('<li><span class="loadingIcon">&nbsp;</span>正在加载...</li>');
							},
							success: function(data) {
								isAjaxDate1 = false;
								var cartItemInfoHtml1 = "";
								var cartItemListHtml = "";
								jQuery.each(data, function(index, object) {
									if (index == 0) {
//										if (object.totalQuantity == 0) {
//											cartItemInfoHtml1 = '<li>公司简介：中科软朱勇sky</li>';
//										} else {
											cartItemInfoHtml1 = '公司简介:中科软朱勇='+object.sky;
//										}
									} 
								});
								jQuery("#brandInstructionListDetail").html(cartItemListHtml + cartItemInfoHtml1);
							}
						});
					}
				},
				function() {
					jQuery("#brandInstructionListDetail").fadeOut();
				}
			);
	
	jQuery("#brandInstructionListDetail").hover(
			function() {
				jQuery("#brandInstructionListDetail").stop(true, true);
				jQuery("#brandInstructionListDetail").show();
			},
			function() {
				jQuery("#brandInstructionListDetail").fadeOut();
			}
	);
	
	// 刷新公司简介
	jQuery.flushBrandInstructionList = function() {
		isAjaxDate1 = true;
	};

});
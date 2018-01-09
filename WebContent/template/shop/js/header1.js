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
		var $YesLogin = jQuery("#YesLogin");
		var $NoLogin = jQuery("#NotLogin");
		jQuery.ajax({
	        url: sinosoft.base+"/shop/member!checkLogin.action",
	        type: "post",
			dataType: "json",
			success: function(data) {
				if (data == false) {
		if(jQuery.cookie("loginMemberUsername") != null) {
			$headerLoginMemberUsername.text(jQuery.cookie("loginMemberUsername"));
			$YesLogin.show();
			$NoLogin.hide();
			//zhangjinquan 11180 req20121204001-评论修改-增加评论用户和评论内容刷新 2012-12-11 start
			var tCommentUser = document.getElementById('CommentUser');
			if ((null != tCommentUser) && ("undefined" != typeof(tCommentUser)))
			{
				tCommentUser.innerHTML = jQuery.cookie("loginMemberUsername");
				tCommentUser.title = jQuery.cookie("loginMemberUsername");
				var oldComment = jQuery.cookie('CmntContent');
				if ((null != oldComment) && ("" != oldComment))
				{
					document.getElementById('CmntContent').value = oldComment;
					jQuery.cookie('CmntContent', null, {path: '/'});
				}
				var oldCmntChecked = jQuery.cookie('CmntAnonymous');
				if ("1" == oldCmntChecked)
				{
					document.getElementById('Anonymous').checked = true;
					jQuery.cookie('CmntAnonymous', null, {path: '/'});
				}
			}
			//zhangjinquan 11180 req20121204001-评论修改-增加评论用户和评论内容刷新 2012-12-11 end
			//zhangjinquan 11180 req20121204001-评论修改-增加评论框刷新 2012-12-11 start
			var trevw10cont02 = document.getElementById('revw10cont02');
			if ((null != trevw10cont02) && ("undefined" != typeof(trevw10cont02)))
			{
				trevw10cont02.style.display="block";
				document.getElementById('revw10cont01').style.display="none";
			}
			//zhangjinquan 11180 req20121204001-评论修改-增加评论框刷新 2012-12-11 end
//			$headerMemberCenter.show();
//			$headerLogout.show();
//			$headerRegister.hide();
//			$headerShowLoginWindow.hide();
//			$headerShowRegisterWindow.hide();
		}} else {
			$headerLoginMemberUsername.text("");
			$YesLogin.hide();
			$NoLogin.show();
			//zhangjinquan 11180 req20121204001-评论修改-增加评论框刷新 2012-12-11 start
			var trevw10cont02 = document.getElementById('revw10cont02');
			if ((null != trevw10cont02) && ("undefined" != typeof(trevw10cont02)))
			{
				trevw10cont02.style.display="none";
				document.getElementById('revw10cont01').style.display="block";
			}
			//zhangjinquan 11180 req20121204001-评论修改-增加评论框刷新 2012-12-11 end
//			$headerShowLoginWindow.show();
//			$headerShowRegisterWindow.show();
//			$headerMemberCenter.hide();
//			$headerRegister.show();
//			$headerLogout.hide();
		}}});
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
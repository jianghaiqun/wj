/** 
 * @des: 产品首页：定位联系客服按钮
 * @maker: Songzairan
 * @date: 2015.04.13
 */
jQuery.fn.autoServChat = function(opt) {
	
	var _opt, oMainArea, oMainArea, nLeft;

	return this.each(function() {

		_opt = jQuery.extend({
		
			parElm: this,
			chatElm: "#servfloat"
			
		}, opt);
		
		_staticInit();
		_monitor();
	
	});
	
	function _staticInit() {
		
		oMainArea = jQuery(_opt.parElm);
		oServChat = jQuery(_opt.chatElm);
		
		nLeft = oMainArea.offset().left + oMainArea.width();
		oServChat.show().css("left", nLeft);
		
	}
	
	function _monitor() {
		
		jQuery(window).resize(function() {
		
			nLeft = oMainArea.offset().left + oMainArea.width();
			oServChat.show().css("left", nLeft);
		
		});
		
	}	
		
}

/** 
 * @des: 产品排序：销量排序和价格排序
 * @maker: Songzairan
 * @date: 2015.04.13
 */
jQuery.fn.productOrderList = function(opt) {
 
	var _opt, oParBox, oOrderBox, oSaleBtn, oPrixBtn;

	return this.each(function() {

		_opt = jQuery.extend({
		
			parElm: this,
			orderBox: ".order",
			nextBox: ".products",
			orderArea: ".orderArea",
			saleElm: ".sale",
			prixElm: ".prix"
			
		}, opt);
		
		_staticInit();
		_monitor();
		
	});
	
	function _staticInit() {
		
		oParBox = jQuery(_opt.parElm);
		oOrderBox = oParBox.find(_opt.orderBox ,oParBox);
		oSaleBtn = jQuery(_opt.saleElm, oOrderBox);
		oPrixBtn = jQuery(_opt.prixElm, oOrderBox);

	}
	
	function _monitor() {
		
		oSaleBtn.unbind("click").click(function() {
			
			var oListBox = jQuery(this).parents(_opt.orderBox).next(_opt.nextBox);		
			var nArr = jQuery(".cf > .stc_shopboxs > .product_title > .SalesVolume > b", oListBox);
			
			orderList(this, oListBox, nArr);
			
		});
		
		oPrixBtn.unbind("click").click(function() {
			
			var oListBox = jQuery(this).parents(_opt.orderBox).next(_opt.nextBox);
			var nArr = jQuery(".cf > .stc_shopboxs > .product_info > .product_info_bor > .prodcutMark > .price > li:not(.priceB) > span", oListBox);

			orderList(this, oListBox, nArr);
			
		});
		
	}
	
	function orderList(oBtnElm, oListBox, elmArr) {
			
		if(jQuery(oBtnElm).hasClass("order01")) {
			
			var isOrder = true;
			
			jQuery(oBtnElm).removeClass("order01")
						   .addClass("order02");
			
		} else if(jQuery(oBtnElm).hasClass("order02")) {
			
			var isOrder = false;
			
			jQuery(oBtnElm).removeClass("order02")
						   .addClass("order01");
			
		}
		
		var nSortArr = elmArr.sort(function(a, b) {
			jQuery(a).find("em").remove();
			jQuery(b).find("em").remove();
			
			var _a = parseFloat(jQuery(a).text());
			var _b = parseFloat(jQuery(b).text());
			
			if(isOrder) {
			
				if(_a > _b) return -1;				
				if(_a < _b) return 1;
				
			} else {
			
				if(_a > _b) return 1;				
				if(_a < _b) return -1;
				
			}
			
			return 0;
			
		});
			
		oListBox.find(_opt.orderArea).empty();
		nSortArr.each(function(i, v) {
			
			jQuery(v).parents(".cf").appendTo(oListBox.find(_opt.orderArea));
			
		});
		
	}
	
}

/** 
 * @des: 模板后台操作：导航修改
 * @maker: Songzairan
 * @date: 2015.04.13
 */
 jQuery.fn.menuBoxEdit = function(opt) {
	
	var _opt, oBtnMenu, oEditBox, oDarkBox, oBtnClose, oEditList, oEditArea, oFinBtn, oBtnBox, oAddBtn, oDelBtn, oPrmp, isAdd;

	return this.each(function() {

		_opt = jQuery.extend({
		
			parElm: this,
			menuList: "#menuArea",
			editElm: "#editMenu",
			editClose: ".close",
			editList: ".editList",
			editArea: ".editArea",
			finElm: ".btnEdit",
			prmpElm: ".prmp",
			btnBox: ".btnArea",
			addElm: ".add",
			delElm: ".del",
			txtEdit: ".editText",
			urlEdit: ".editLink",
			darkElm: "#dark" 
			
		}, opt);
		
		_staticInit();
		_monitor();
	
	});
	
	function _staticInit() {
		
		oBtnMenu = jQuery(_opt.parElm);
		oMenuList = jQuery(_opt.menuList);
		oEditBox = jQuery(_opt.editElm);
		oDarkBox = jQuery(_opt.darkElm);
		oBtnClose = jQuery(_opt.editClose, oEditBox);
		oEditList = jQuery(_opt.editList, oEditBox);
		aArrList = oEditList.find("li > a");
		oEditArea = jQuery(_opt.editArea, oEditBox);
		oFinBtn = jQuery(_opt.finElm, oEditArea);
		oPrmp = jQuery(_opt.prmpElm, oEditArea);
		oBtnBox = jQuery(_opt.btnBox, oEditBox);
		oAddBtn = jQuery(_opt.addElm, oBtnBox);
		oDelBtn = jQuery(_opt.delElm, oBtnBox);
		isAdd = false;
		
		jQuery(_opt.txtEdit, oEditArea).val("");
		jQuery(_opt.urlEdit, oEditArea).val("");

	}
	
	function _monitor() {
		
		oBtnMenu.click(function(e) {
		
			e.preventDefault();
			
			oEditBox.show();
			oDarkBox.show();
			
			oMenuList.find("li").each(function(i, v) {
				
				jQuery(v).appendTo(oEditList);
				
			});
			
			
			oEditList.find("li > a").unbind("click").click(function(e) {
			
				e.preventDefault();
				
				oEditList.find("li > a").removeClass("hover");
				jQuery(this).addClass("hover");
				
				var _txt = jQuery(this).text();
				var _url = jQuery(this).attr("href");
				
				jQuery(_opt.txtEdit, oEditArea).val(_txt);
				jQuery(_opt.urlEdit, oEditArea).val(_url);
				
			});
			
		});
		
		oBtnClose.click(function(e) {
		
			e.preventDefault();
			
			oEditBox.hide();
			oDarkBox.hide();
			
		});
		
		oAddBtn.click(function(e) {
		
			e.preventDefault();
			
			if(isAdd) return;
			
			var _html = "<li><a class='hover' href='#'>xxx</a></li>";
			
			oEditList.find("li > a").removeClass("hover");
			oEditList.append(_html);
			isAdd = true;
			
			var _t = oEditList.find(".hover").text();
			var _u = oEditList.find(".hover").attr("href");
			
			jQuery(_opt.txtEdit, oEditArea).val(_t);
			jQuery(_opt.urlEdit, oEditArea).val(_u);
			
			oEditList.find("li > a").unbind("click").click(function(e) {
			
				e.preventDefault();
				
				oEditList.find("li > a").removeClass("hover");
				jQuery(this).addClass("hover");
				
				var _txt = jQuery(this).text();
				var _url = jQuery(this).attr("href");
				
				jQuery(_opt.txtEdit, oEditArea).val(_txt);
				jQuery(_opt.urlEdit, oEditArea).val(_url);
				
			});
		
		});
		
		oDelBtn.click(function(e) {
			
			e.preventDefault();

			jQuery(".hover", oEditList).parents("li").remove();
			isAdd = false;
			
		});
		
		oFinBtn.click(function(e) {
			
			e.preventDefault();
			
			var _sel = oEditList.find(".hover");
			
			if(_sel.length < 1) return;
			
			var _txt = jQuery(_opt.txtEdit, oEditArea).val();
			var _url = jQuery(_opt.urlEdit, oEditArea).val();
			
			if(_txt != "" && _url != "") {
				
				_sel.attr("href", _url).text(_txt);
				isAdd = false;
				
				oPrmp.stop(true, true).fadeIn(function() {
				
					jQuery(this).fadeOut();
					
				});
				
			}
			
		});
		
	}	
		
}


/** 
 * @des: 产品首页：广告图片替换
 * @maker: Songzairan
 * @date: 2015.04.14
 */
jQuery.fn.editBnrPic = function(opt) {
	
	var _opt, oParBox, oEditBtn, oEditBox, oDarkBox, oCloseBtn, oBtnsBox;

	return this.each(function() {

		_opt = jQuery.extend({
		
			parElm: this,
			btnElm: "#btnBnr",
			editBox: "#editBnr",
			closeElm: ".close",
			btnsBox: ".btns",
			darkElm: "#dark"
			
		}, opt);
		
		_staticInit();
		_monitor();
	
	});
	
	function _staticInit() {
		
		oParBox = jQuery(_opt.parElm);
		oEditBtn = oParBox.find(_opt.btnElm, oParBox);
		oEditBox = jQuery(_opt.editBox);
		oCloseBtn = jQuery(_opt.closeElm ,oEditBox);
		oBtnsBox = jQuery(_opt.btnsBox ,oEditBox);
		oDarkBox = jQuery(_opt.darkElm);

	}
	
	function _monitor() {
		
		oEditBtn.click(function(e) {
		
			e.preventDefault();
			if(jQuery("#imageSrc").attr("src") != '' && jQuery("#imageSrc").attr("src") != null){
				jQuery("#bannerImg").attr('src',jQuery("#imageSrc").attr("src"));
				jQuery(".dark").hide();
			}
			oEditBox.show();
			oDarkBox.show();
			
		});
		
		oCloseBtn.click(function(e) {
			
			e.preventDefault();
			
			oEditBox.hide();
			oDarkBox.hide();

		});
		
		oBtnsBox.find("a").click(function(e) {
			
			e.preventDefault();
			
		});
		
	}	
		
}


/** 
 * @des: 产品首页：页面背景色替换
 * @maker: Songzairan
 * @date: 2015.04.14
 */
jQuery.fn.bgSetColor = function(opt) {
	
	var _opt, oBody, oParBox, oCgeSel, oSetBtn, oTxtCol;

	return this.each(function() {

		_opt = jQuery.extend({
		
			parElm: this,
			cgeElm: "#selColor",
			btnElm: "#sbtPreview",
			inputElm: ".txtColor"
			
		}, opt);
		
		_staticInit();
		_monitor();
	
	});
	
	function _staticInit() {
		
		oBody = jQuery("body");
		oParBox = jQuery(_opt.parElm);
		oCgeSel = jQuery(_opt.cgeElm);
		oSetBtn = jQuery(_opt.btnElm);
		oTxtCol = jQuery(_opt.inputElm);

	}
	
	function _monitor() {
	
		oCgeSel.bind("change", function(e) {
		
			e.preventDefault();
			
			var _color = jQuery(this).val();
			
			oBody.css("backgroundColor", _color);
		
		});
		
		oSetBtn.click(function(e) {
		
			e.preventDefault();
			
			var _color = oTxtCol.val();
			
			oBody.css("backgroundColor", _color);
			
		});
		
		
	}	
		
}


/** 
 * @des: 产品首页：产品模块编辑
 * @maker: Songzairan
 * @date: 2015.04.14
 */
jQuery.fn.productBoxEdit = function(opt) {
	
	var _opt, oParBox, oProBox, oDelBtn, oToggleBtn, oEditBtn, oRemdBtn, oEditPBtn, oProEditArea, oProCloseBtn, oDarkBox, oCmtEditArea, oCmtAddBtn, oCmtEditBox, oCmtCloseBtn, oCmtEditBtn, oCmtDelBtn, oCmtSmtBtn, oAddBoxArea, oProductArea;

	return this.each(function() {

		_opt = jQuery.extend({
		
			parElm: this,
			proElm: ".products",
			delElm: ".proDel",
			toggleElm: ".hide",
			editElm: ".edit",
			remdElm: ".remd",
			editPElm: ".editP",
			ctlArea: ".control",
			txtElm: ".text",
			proEditArea: "#popupEdit",
			closeElm: ".close",
			cmtEditArea: ".commentArea",
			cmtAddElm: ".editC",
			cmtEditBox :"#cmtPopup",
			cmtEditBtn: ".editCmt",
			cmtSmtBtn: ".smt",
			cmtDelBtn: ".delCmt",
			darkElm: "#dark",
			addBoxArea: "#addProduct",
			limitNum: 3
			
		}, opt);
		
		_staticInit();
		_monitor();
	
	});
	
	function _staticInit() {
		
		oProductArea = '<div class="productArea section"><p class="proDel"><a href="#">删除模块</a></p><div class="intro editSec"><p class="hide"><a href="#">隐藏</a></p><p class="control"><span class="text"></span><span class="edit"><a href="#">编辑</a><a href="#" class="smt">确定</a></span></p></div><div class="adapt editSec"><p class="hide"><a href="#">隐藏</a></p><p class="control"><span class="text"></span><span class="edit"><a href="#">编辑</a><a href="#" class="smt">确定</a></span></p></div><div class="case editSec"><p class="hide"><a href="#">隐藏</a></p><p class="control"><span class="text"></span><span class="edit"><a href="#">编辑</a><a href="#" class="smt">确定</a></span></p></div><div class="order editSec"><p class="hide"><a href="#">隐藏</a></p><p class="control"><strong>排序</strong><span class="sale order01">销量</span><span class="prix order01">价格</span></p></div><div class="products editSec"><p class="remd"><a href="#">显示推荐</a></p><p class="editP"><a href="#">编辑商品</a></p><input type="hidden" value=""><div class="orderArea"></div><div class="commentArea"><p class="hide"><a href="#">隐藏</a></p><div class="control"><p class="title">用户评论</p><ul class="commentList"></ul><p class="editC"><a href="#">添加评论</a></p></div></div></div></div>';
		oParBox = jQuery(_opt.parElm);
		//oParBox.append(oProductArea);
		
		oProBox = jQuery(_opt.proElm, oParBox);
		oDelBtn = jQuery(_opt.delElm);
		oToggleBtn = jQuery(_opt.toggleElm);
		oEditBtn = jQuery(_opt.editElm);
		oRemdBtn = jQuery(_opt.remdElm);
		oEditPBtn = jQuery(_opt.editPElm);
		oProEditArea = jQuery(_opt.proEditArea);
		oProCloseBtn = jQuery(_opt.closeElm, oProEditArea);
		oCmtEditArea = jQuery(_opt.cmtEditArea);
		oCmtAddBtn = jQuery(_opt.cmtAddElm, oCmtEditArea);
		oCmtEditBox = jQuery(_opt.cmtEditBox, oCmtEditBox);
		oCmtCloseBtn = jQuery(_opt.closeElm);
		oCmtEditBtn = jQuery(_opt.cmtEditBtn, oCmtEditArea);
		oCmtSmtBtn = jQuery(_opt.cmtSmtBtn, oCmtEditArea);
		oCmtDelBtn = jQuery(_opt.cmtDelBtn, oCmtEditArea);
		oDarkBox = jQuery(_opt.darkElm);
		oAddBoxArea = jQuery(_opt.addBoxArea);
		
		//jQuery(".productList").productOrderList();

	}
	
	function _monitor() {
		
		oDelBtn.live("click", function(e) {
			
			e.preventDefault();
			
			jQuery(this).parent().remove();
			
			jQuery(".productArea", oParBox).each(function(i, v) {
				
				jQuery(this).attr("id", "pro" + (i + 1)); 
				
			});
			
			var _num = jQuery(this).parent().attr("id").slice(3);
			jQuery("#menuBox .menuCont").find("li").eq(_num - 1).remove();
			
			jQuery("#menuBox .menuCont").find("li > a").each(function(i, v) {
				
				jQuery(v).attr("href", "#pro" + (i + 1)); 
				jQuery(v).text("可编辑文本" + (i+1)); 
				
			});
			
			var _proId = jQuery(this).parent().attr("id");
			
			deleteModule(_proId);
		});
		
		oToggleBtn.live("click", function(e) {
			
			e.preventDefault();
			
			var _txt = jQuery(this).find("a").text();
			var _ctlArea = jQuery(this).siblings(_opt.ctlArea);
			/*var _id = jQuery(this).parents(".productArea").attr("id") + "_" + jQuery(this).parents(".editSec").attr("class").replace("editSec", "");
			var strId = "";*/
			
			if(_txt == "隐藏") {
				//strId = _id.trim() + "_h";
				var _id = _ctlArea.find("textarea").attr("id");
				jQuery(this).find("a").text("显示");
				_ctlArea.hide();
				jQuery(this).parent().addClass("hideCss");
				
			} else if(_txt == "显示") {
				//strId = _id.trim() + "_s";
				jQuery(this).find("a").text("隐藏");
				_ctlArea.show();
				jQuery(this).parent().removeClass("hideCss");
			}
			//console.log(strId);
			
		});
		
		oEditBtn.live("click", function(e) {
			
			e.preventDefault();
			
			var _text = jQuery(this).find("a:visible").text();
			var _ctlArea = jQuery(this).siblings(_opt.txtElm);
			
			if(_text == "编辑") {	
				
				var _txt = _ctlArea.text();
				var _id = jQuery(this).parents(".productArea").attr("id") + "_" + jQuery(this).parents(".editSec").attr("class").replace("editSec", "");
				
				_ctlArea.empty().append("<textarea id='" + _id.trim()  + "' class='txtControl'>" + _txt + "</textarea>");
				
				jQuery(this).find("a:not(.smt)").hide();
				jQuery(this).find(".smt").show();
				
			} else if(_text == "确定") {
				var _id = _ctlArea.find("textarea").attr("id");
				var _txt = _ctlArea.find("textarea").val();
				_ctlArea.text(_txt);
				jQuery(this).find("a:not(.smt)").show();
				jQuery(this).find(".smt").hide();
				
			}
			
		});
		
		oRemdBtn.live("click", function(e) {
			
			e.preventDefault();
			
			var _text = jQuery(this).find("a").text();
			var oProList = jQuery(this).parents(".products").find(".cf > .stc_shopboxs");
			/*var _id = jQuery(this).parents(".productArea").attr("id") + "_" + jQuery(this).parents(".editSec").find(".orderArea").attr("class");
			var strId = "";*/
			
			if(oProList.length < 1) return;
			
			if(_text == "显示推荐") {
				
				//strId = _id.trim() + "_s";
				
				oProList.each(function(i, v) {
				
					if(i > _opt.limitNum - 1) return;
				
					jQuery(v).addClass("stc_tj");
				
				});
			
				jQuery(this).find("a").text("隐藏推荐");
				
			} else if(_text == "隐藏推荐") {
				
				//strId = _id.trim() + "_h";
				
				oProList.each(function(i, v) {
				
					if(i > _opt.limitNum - 1) return;
				
					jQuery(v).removeClass("stc_tj");
				
				});
			
				jQuery(this).find("a").text("显示推荐");
				
			}
			
			//console.log(strId);
			
		});
		
		
		oEditPBtn.live("click", function(e) {
			
			e.preventDefault();
			var _id = jQuery(this).parents(".productArea").attr("id");
			jQuery("#editPro").val(_id);
			jQuery(".proId").val("");
			jQuery(".proRemark").val("");
			jQuery(".proId").val(jQuery("#"+_id+"_products").val());
			jQuery(".proRemark").val(jQuery("#"+_id+"_productN").val());
			oProEditArea.show();
			oDarkBox.show();
			
		});
		
		oProCloseBtn.live("click", function(e) {
			
			e.preventDefault();
			
			oProEditArea.hide();
			oDarkBox.hide();
			
		});
		
		jQuery(".btn", oProEditArea).live("click", function(e) {
			
			e.preventDefault();
			
		});
		
		oCmtAddBtn.live("click", function(e) {
			
			e.preventDefault();
			var _id = jQuery(this).parents(".productArea").attr("id");
			jQuery("#editPro").val(_id);
			jQuery("#commentId").val("");
			oCmtEditBox.show();
			oDarkBox.show();
			
		});
		
		oCmtCloseBtn.live("click", function(e) {
			
			e.preventDefault();
			
			oCmtEditBox.hide();
			oDarkBox.hide();
			
		});
		
		oCmtEditBox.find(".btn").live("click", function(e) {
			
			e.preventDefault();
			
		});
		
		oCmtEditBtn.live("click", function(e) {
			
			e.preventDefault();
			
			var _oCmt = jQuery(this).parents(".operate").siblings(".txt").find(".cmt");
			var _txt = _oCmt.text();
			
			_oCmt.empty().append("<textarea class='txtControl'>" + _txt + "</textarea>");
			jQuery(this).hide();
			jQuery(this).siblings(".smt").show();
			
		});
		
		oCmtSmtBtn.live("click", function(e) {
			
			e.preventDefault();
			
			var _oCmt = jQuery(this).parents(".operate").siblings(".txt").find(".cmt");
			var _txt = _oCmt.find("textarea").val();
			
			_oCmt.empty().text(_txt);
			jQuery(this).hide();
			jQuery(this).siblings(".editCmt").show();
			
		});
		
		oCmtDelBtn.live("click", function(e) {
			
			e.preventDefault();
			
			var _oCmt = jQuery(this).parents("li");
			
			_oCmt.remove();
			
		});
		
		oAddBoxArea.find("a").click(function(e) {
			
			e.preventDefault();
			
			if(jQuery("#menuBox .menuCont").find("li > input:text").length > 0) {
				
				alert("请完成导航编辑!");
				return;
			}
			
			var contElm = jQuery(oProductArea);
			oParBox.append(contElm);
			
			jQuery(".productArea", oParBox).each(function(i, v) {
				
				jQuery(this).attr("id", "pro" + (i + 1));
				
				
			});
			
			var _num = jQuery("#menuBox .menuCont").find("li").length;
			
			jQuery("#menuBox .menuCont").append("<li><a href='#pro" + (_num + 1) + "'>可编辑文本" + (_num + 1)  + "</a></li>");
			
			//jQuery(".productList").productOrderList();
			
		});
		
	}	
		
}


/** 
 * @des: 产品首页：上传图片
 * @maker: Songzairan
 * @date: 2015.04.14
 */
jQuery.fn.upLoadPicture = function(opt) {
	
	var _opt, oParBox, oEditBtn, oEditBox, oDarkBox, oCloseBtn, oBtnsBox;

	return this.each(function() {

		_opt = jQuery.extend({
		
			parElm: this,
			btnElm: ".btn",
			upLoadBox: "#editIntro",
			closeElm: ".close",
			btnsBox: ".btns",
			darkElm: "#dark"
			
		}, opt);
		
		_staticInit();
		_monitor();
	
	});
	
	function _staticInit() {
		
		oParBox = jQuery(_opt.parElm);
		oEditBtn = oParBox.find(_opt.btnElm, oParBox);
		oEditBox = jQuery(_opt.upLoadBox);
		oCloseBtn = jQuery(_opt.closeElm ,oEditBox);
		oBtnsBox = jQuery(_opt.btnsBox, oEditBox);
		oDarkBox = jQuery(_opt.darkElm);

	}
	
	function _monitor() {
		
		oEditBtn.click(function(e) {
		
			e.preventDefault();
			
			oEditBox.show();
			oDarkBox.show();
			
		});
		
		oCloseBtn.click(function(e) {
			
			e.preventDefault();
			
			oEditBox.hide();
			oDarkBox.hide();

		});
		
		oBtnsBox.find("a").click(function(e) {
			
			e.preventDefault();
			
		});
		
	}	
		
}
 
 
 /** 
 * @des: 产品首页：编辑导航栏
 * @maker: Songzairan
 * @date: 2015.04.15
 */

jQuery.fn.editMenuBox = function(opt) {
	
	var _opt, oParBox, oMenuCont, oAddMenu, oEditMenu, oDelMenu;

	return this.each(function() {

		_opt = jQuery.extend({
		
			parElm: this,
			menuCont: ".menuCont",
			editElm: ".edit",
			delElm: ".del"
			
		}, opt);
		
		_staticInit();
		_monitor();
	
	});
	
	function _staticInit() {
		
		oParBox = jQuery(_opt.parElm);
		oMenuCont = jQuery(_opt.menuCont);
		oEditMenu = jQuery(_opt.editElm, oParBox);
		oDelMenu = jQuery(_opt.delElm, oParBox);
		
	}
	
	function _monitor() {
		
		oEditMenu.toggle(function(e) {
			
			e.preventDefault();
			
			oMenuCont.find("li > a").each(function(i ,v) {
				
				jQuery(v).replaceWith("<input type='text' value='" + jQuery(v).text() + "' />");
				
			});
			
			jQuery(this).text("完成");
			
		}, function(e) {
			
			e.preventDefault();
			
			oMenuCont.find("li > input:text").each(function(i ,v) {
				
				jQuery(v).replaceWith("<a href='#pro" + (i + 1) + "'>" + jQuery(v).val() + "</a>");
				
			});
			
			jQuery(this).text("编辑");
			
		});
	
	}
};
if(jQuery("#hidFlag").val()=="Y"){
	jQuery("#pre").hide();
	jQuery("#hidPre").text("显示");
}
var url = jQuery("#frontURL").val();
//编辑导航
function addNav(){
	var textList = "";
	jQuery(".editList").each(function(){
		textList=jQuery(this).html();
	});
	if(textList == null || textList == ''){
		alert("请为导航填写详细完整信息！");
		return;
	}
	textList = encodeURIComponent(encodeURIComponent(textList));
	jQuery.ajax({
		url: url+"/shop/product_marketing!addNav.action?articleId="+jQuery("#articleId").val()+"&textList="
		+textList+"&catalogId="+jQuery("#catalogId").val(),
		type: "post",
		dataType: "json",
		success: function(data){
			var obj = eval(data);
			if(obj.status=="1"){
				jQuery("#editMenu").hide();
				jQuery("#dark").hide();
				jQuery("#menuArea").html(obj.message);
				jQuery(".editList").html("");
				jQuery(".editText").val("");
				jQuery(".editLink").val("");
			}else{
				alert("编辑导航失败！");
			}
	 	}			
	});
}
//编辑页面背景色
function saveColor(){
	var Color = "";
	if(jQuery("#selColor").val()==""){
		Color = jQuery("#txtColor").val();
	}else{
		Color = jQuery("#selColor").val();
	}
	jQuery.ajax({
		url: url+"/shop/product_marketing!saveColor.action?articleId="+jQuery("#articleId").val()+"&color="
		+Color+"&catalogId="+jQuery("#catalogId").val(),
		type: "post",
		dataType: "json",
		success: function(data){
			var obj = eval(data);
			if(obj.status=="0"){
				alert("保存背景色失败！");
			}else{
				alert("保存成功！");
			}
	 	}			
	});
}

function saveRightWords(){
	var rightWords = encodeURIComponent(encodeURIComponent(jQuery("#rightWords").val()));
	jQuery.ajax({
		url: url+"/shop/product_marketing!saveRightWords.action?articleId="+jQuery("#articleId").val()
		+"&rightWords="+rightWords
		+"&rightLink="+jQuery("#rightLink").val()+"&catalogId="+jQuery("#catalogId").val(),
		type: "post",
		dataType: "json",
		success: function(data){
			var obj = eval(data);
			if(obj.status=="1"){
				jQuery("#rightWords").val(obj.rightWords);
				jQuery("#rightLink").val(obj.rightLink);
				alert("保存成功！");
			}else{
				alert("保存失败！");
			}
	 	}			
	});
}

//编辑banner背景图
function saveBanner(){
	var imageSrc = jQuery("#bannerImg").attr("src");
	jQuery.ajax({
		url: url+"/shop/product_marketing!saveBanner.action",
		type: "post",
		dataType: "json",
		data:{articleId:jQuery("#articleId").val(),catalogId:jQuery("#catalogId").val(),linkTxt:jQuery("#linkTxt").val(),
			wordTxt:jQuery("#wordTxt").val(),wordColor:jQuery("#wordColor").val(),wordBackColor:jQuery("#wordBackColor").val(),
			imageSrc:imageSrc},
		success: function(data){
			var obj = eval(data);
			if(obj.status=="1"){
				jQuery("#editBnr").hide();
				jQuery("#dark").hide();
				jQuery("#imageSrc").attr('src',obj.imageSrc);
			}else{
				alert("保存banner信息失败！");
			}
	 	}			
	});
}

function savePrePicture(){
	var imageSrc = jQuery("#preImg").attr("src");
	jQuery.ajax({
		url: url+"/shop/product_marketing!savePrePicture.action",
		type: "post",
		dataType: "json",
		data:{articleId:jQuery("#articleId").val(),catalogId:jQuery("#catalogId").val(),
			imageSrc:imageSrc},
		success: function(data){
			var obj = eval(data);
			if(obj.status=="1"){
				jQuery("#editIntro").hide();
				jQuery("#dark").hide();
				jQuery("#preImgSrc").attr('src',obj.imageSrc);
			}else{
				alert("保存预约图片信息失败！");
			}
	 	}			
	});
}

/**
 * 上传图片
 * @param sender
 * @returns {Boolean}
 */
function onUploadPhoto(sender,formid) {
	if (!sender.value.match(/.jpg|.jpeg|.bmp/i)) {
		alert("图片格式只支持jpg、jpeg、bmp！");
		return false;
	}
	jQuery("#"+formid+"Form").ajaxSubmit({
		//target : "#infor",
		type : "post",
		dataType : "json",
//		beforeSubmit : function(formData, jqForm, options) {
//			//document.getElementById("infor").innerText = '正在装载数据,请耐心等待...';
//			return true;
//		},
		success : function(data) {
			if(data.status=='1'){
				jQuery("#"+formid+"Img").attr('src',jQuery("#staticUrl").val()+'/'+data.imagsrc);
				jQuery(".dark").hide();
			}else if(data.status=='2'){
				alert("图片大于10M,请重新选择！","error");
				return false;
			}else{
				jQuery("#"+formid+"Img").attr('src','');
			}
		}
	});  
}

function saveInfo(){
	var cuhref = window.location.href;
	cuhref = cuhref.replace(/#/g,""); 
	cuhref = encodeURIComponent(cuhref);
	var menuCont = jQuery(".menuCont").html();
	menuCont = menuCont.replace(/&amp;/g, "&");
	var txtHtml = encodeURIComponent(jQuery(".productList").html());
	var navHtml = encodeURIComponent(menuCont);
	navHtml = navHtml.replace(new RegExp(cuhref,'gm'),'');
	jQuery.ajax({
		url: url+"/shop/product_marketing!saveInfo.action",
		type: "post",
		dataType: "json",
		data:{txtHtml:txtHtml,articleId:jQuery("#articleId").val(),navHtml:navHtml,catalogId:jQuery("#catalogId").val()},
		success: function(data){
			var obj = eval(data);
			if(obj.status=="1"){
				alert("保存成功！");
			}else{
				alert("保存失败！");
			};
	 	}		
	});
}

function savePro(){
	var products = jQuery(".proId").val();
	if(products=="" || products ==null){
		alert("请输入产品编码！");
		return;
	}
	var editPro = jQuery("#editPro").val();
	if(jQuery("#"+editPro+"_products").val()==null){
		jQuery("#"+editPro).after('<input type="hidden" value ="'+products+'" id="'+editPro+'_products">');
	}else{
		jQuery("#"+editPro+"_products").val(products);
	}
	
	jQuery.ajax({
		url: url+"/shop/product_marketing!savePro.action",
		type: "post",
		dataType: "json",
		data:{products:products,editPro:editPro},
		success: function(data){
			var obj = eval(data);
			jQuery(".orderArea","#"+obj.editPro).html(obj.productDetail);
			if(jQuery("#"+obj.editPro+"_productN").val()==null){
				jQuery("#"+obj.editPro).after('<input type="hidden" value ="'+obj.productName+'" id="'+obj.editPro+'_productN">');
			}else{
				jQuery("#"+obj.editPro+"_productN").val(obj.productName);
			}
			jQuery("#dark").hide();
			jQuery("#popupEdit").hide();
	 	}		
	});
}

function saveCom(){
	var commentId = jQuery("#commentId").val();
	var editPro = jQuery("#editPro").val();
	if(jQuery("#"+editPro+"_comments").val()==null){
		jQuery("#"+editPro).after('<input type="hidden" value ="'+commentId+'" id="'+editPro+'_comments">');
	}else{
		jQuery("#"+editPro+"_comments").val(commentId);
	}
	
	var date=new Date();
	var year = date.getFullYear(); 
	var month_now=date.getMonth()+1;
	var date_now=date.getDate();
	while(true){
		var month=Math.round(Math.random()*month_now);
		if(month!=0){
			break;
		}
	}
	while(true){
		var day=Math.round(Math.random()*date_now);
		if(day!=0){
			break;
		}
	}
	var d = new Date(year,month,day); 
	var date = year+"-"+(d.getMonth())+"-"+d.getDate();
	jQuery.ajax({
		url: url+"/shop/product_marketing!saveCom.action",
		type: "post",
		dataType: "json",
		data:{commentContent:commentId,editPro:editPro,roundDate:date,articleId:jQuery("#articleId").val(),catalogId:jQuery("#catalogId").val()},
		success: function(data){
			var obj = eval(data);
			if(obj.status=="1"){
				jQuery("#dark").hide();
				jQuery("#cmtPopup").hide();
				jQuery(".commentList", "#"+obj.editPro).html(obj.commentContent);
				jQuery("#dark").hide();
				jQuery("#cmtPopup").hide();
				saveInfo();
			}
	 	}		
	});
}

function hidPre(){
	var flag = "";
	if(jQuery("#hidPre").text()=="隐藏"){
		flag = "Y";
	}else if(jQuery("#hidPre").text()=="显示"){
		flag = "N";
	}
	jQuery.ajax({
		url: url+"/shop/product_marketing!hidPre.action",
		type: "post",
		dataType: "json",
		data:{hide:flag,articleId:jQuery("#articleId").val(),catalogId:jQuery("#catalogId").val()},
		success: function(data){
			var obj = eval(data);
			if(obj.status=="0"){
				alert("保存失败！");
			};
	 	}		
	});
}

function editComm(id,type){
	var commentContent = jQuery(id).text();
	jQuery.ajax({
		url: url+"/shop/product_marketing!editComm.action",
		type: "post",
		dataType: "json",
		data:{commentId:id,editType:type,commentContent:commentContent},
		success: function(data){
			var obj = eval(data);
			if(obj.status=="0"){
				alert("保存失败！");
			}else{
				saveInfo();
			};
	 	}		
	});
}
function deleteModule(_proId){
	jQuery.ajax({
		url: url+"/shop/product_marketing!deleteModule.action",
		type: "post",
		dataType: "json",
		data:{articleId:jQuery("#articleId").val(),catalogId:jQuery("#catalogId").val(),editPro:_proId},
		success: function(data){
			var obj = eval(data);
			if(obj.status=="0"){
				alert("保存失败！");
			}else{
				saveInfo();
			};
	 	}		
	});
}
function salesVolumeLoad() {
	var list=jQuery('span[id^=SalesV_]');
	var id = new Array(list.length);
	var idStr="";
	for (var i = 0; i < list.length; i++) {
           id[i] = list[i].id;
           idStr+= list[i].id;
	}
	if(idStr==""){
		return;
	}
	jQuery.ajax({ 
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!search.action?productIds='+id,
		dataType: "json",
		async: false,
		success: function(data) {
			jQuery.each(data, function(index, object) {
	        document.getElementById(index).innerHTML="(累计销量：<b>"+object+"</b>)";
			});
		}
	});
	
}
function chakan(str) {
	
	window.open(str);

}

function submitp(str) {
	jQuery.ajax({
		type : 'get',
		url : sinosoft.base+'/shop/favorites!add.action?productId='
				+ str,
		dataType : 'html',
		success : function(de) {

			if (de == 'notlogin') {
				artLogin();//使用弹出窗口登录
			} else {
			
				alert(de);
				jQuery("#id_"+str).addClass("no_add_sc");
			}
		}
	});
}
function doBuy(str,sup) {
	var selectID = str+"_Period";
	var value = jQuery("#"+selectID).val();
	if(value == null || value == "undefined"){
		value = "";
	}
	var Period = "&Period="+value; 

	var Plan_value = jQuery("#"+(str+"_Plan")).val();
	if(Plan_value == null || Plan_value == "undefined"){
		Plan_value = "";
	}
	var Plan = "&Plan="+Plan_value; 
	var TextAge_value = jQuery("#"+(str+"_TextAge")).val();
	if(TextAge_value == null || TextAge_value == "undefined"){
		TextAge_value = "";
	}
	var TextAge = "&TextAge="+TextAge_value; 
	
	if(sup=='1015'){
		window.location.href = sinosoft.base+"/shop/order_config!buyNow.action?productId="
			+ str+Period+Plan+TextAge;
	}else{
		window.location.href = sinosoft.base+"/shop/order!buyNow.action?productId="
			+ str+Period+Plan+TextAge;     
	}
}

function doOrder1(ele){
	
	changeOrderInfoStyle11(ele);
	searchProduct(ele);
}

function searchProduct(curElement1){
	
	var ProductsOrder = "";
	var str="${Article.FAQ}";
	if (curElement1.id == "order_SalesVolumedesc") {
		ProductsOrder = "order_SalesVolumedesc";
	}else if(curElement1.id == "order_SalesVolumeasc")
	{
		ProductsOrder = "order_SalesVolumeasc";
	}
	else if(curElement1.id == "order_InitPremdesc")
	{
		ProductsOrder = "order_InitPremdesc";
	}
	else if(curElement1.id == "order_InitPremasc")
	{
		ProductsOrder = "order_InitPremasc";
	}
	jQuery.ajax({
		url: sinosoft.base+'/shop/filter!searchYXHD.action?condition=' + str +'&ProductsOrder=' +ProductsOrder,
		dataType: "json",
		async: false,
		success: function(data) {
			  if(data.status == '0'){
				    // 替换产品
					jQuery("#products").html(data.message);
					// 拼装分页
			   }
		  }
		
		
	 });
}
function changeOrderInfoStyle11(curElement) {
	var DocElements = document.getElementById("stc_order")
			.getElementsByTagName("SPAN");
	for ( var i = 0; i < DocElements.length; i++) {
		if (DocElements[i].id == curElement.id) {
			if (DocElements[i].id == "order_SalesVolume")
			{
				curElement.id="order_SalesVolumedesc";
			}else if(DocElements[i].id == "order_SalesVolumedesc")
			{
				curElement.id="order_SalesVolumeasc";
			}else if(DocElements[i].id == "order_SalesVolumeasc")
			{
				curElement.id="order_SalesVolumedesc";
			}else if(DocElements[i].id == "order_InitPrem")
			{
				curElement.id="order_InitPremdesc";
			}else if(DocElements[i].id == "order_InitPremdesc")
			{
				curElement.id="order_InitPremasc";
			}else if(DocElements[i].id == "order_InitPremasc")
			{
				curElement.id="order_InitPremdesc";
			}
		}
	}
	
}


jQuery(document).ready(function() {
  var yymol  = jQuery.trim(jQuery("#introArea").html());

  if(yymol =="null"||yymol==""){
  		jQuery("#introArea").remove();
  }

	  jQuery(".control span").live('click',function(){
			jQuery(this).addClass("select").siblings().removeClass('select');
	})
	
	initSemFavorites();
	  jQuery(".control span").tagclickbind({addstyle:'select'});
	  
 })

  function initSemFavorites() {
	if(jQuery('span[id^=id_]').length <= 0){
		return;
	}
	var list = jQuery('span[id^=id_]');
	var productIDArray = new Array(list.length);
	for (var i = 0; i < list.length; i++) {
		productIDArray[i] = jQuery(list[i]).attr("id").replace("id_","");;
	}
	
	jQuery.ajax({
		type : 'get',
		url : sinosoft.front+'/wj/shop/favorites!initSemFavorites.action?ProductIDS='+productIDArray,
		dataType : 'json',
		async: false,
		success : function(data) {
			jQuery.each(data, function(index, flag) {
				jQuery("#id_"+index).addClass("no_add_sc");
			})
		}
	});
}
 

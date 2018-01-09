/**
 * ------------------------------------------
 * @make:郭东奇
 * @version  1.0
 * @des：详细页调用js/tip气泡提示/
 * ------------------------------------------
 */

/**
 * 责任各行换色.
 */
jQuery(document).ready(function() {
	// 积分商城详细页面不调用初始化方法
	if(jQuery("#pointproducttype").length==0){
		/*queryProductStateLoad();// 通过ID 获取该产品状态
		initProduct();// 产品详细页初始化
		getCount();// 获取评论数
		jQuery("#gh_tables tr").mouseover(function() {
			jQuery(this).addClass("row0");
		}).mouseout(function() {
			jQuery(this).removeClass("row0");
		});*/
		// 会员增加浏览记录
		recordMemBrowse();
		queryProductStateLoad1();
		getCount();// 获取评论数
		//解决销量失效问题
		salesVolumeLoad();
		informLoad();
		searchProductListAvtivity();
		weixinFun();
		cpsFun();
		initFavorites();
	    //判断是否存在计划并且计划数量在2个以上则出现对比功能按钮(长期险不添加计划对比按钮)
		if(jQuery('ul[id$="_Plan"]') && jQuery('ul[id$="_Plan"]>li').length>1&&LongInsurance!='L'){
			 jQuery('ul[id$="_Plan"] > li:last-child').after("<li><span  class='elect_plan'>计划对比</span></li>");
		}

	} else {

		queryProductStateLoad();
		if (jQuery("#error_div").css("display") == undefined || jQuery("#error_div").css("display") == 'none') {
			premDocal(riskCode);
		} else {
			var pri = jQuery("#price").val();
			pri = Math.ceil(pri * 10);
			document.getElementById("productPrem_" + riskCode).innerHTML =  pri + "积分";
			document.getElementById("productLashNum_" + riskCode).innerHTML =  0 + "份";
		}
	}

	// 长期险、积分商城保险产品不加计划对比
	if(jQuery('ul[id$="_Plan"]') && jQuery('ul[id$="_Plan"]>li').length>1&&LongInsurance!='L'&&jQuery("#pointproducttype").length==0){
     	jQuery('ul[id$="_Plan"] > li:last-child').click(
		     function(){
	            var aProductId = jQuery("#RiskCode").val();//得到产品ID
	            //得到投保要素 copy doBuy()
	            var rootEle = document.getElementById("divRiskAppFactor_" + aProductId);
	         	var tempURL = "";

					if (rootEle != null) {
						var dc = new DataCollection();
						var ulNodeList = rootEle.getElementsByTagName("UL");
						for ( var i = 0; i < ulNodeList.length; i++) {
							for ( var j = 0; j < ulNodeList[i].childNodes.length; j++) {
								if (ulNodeList[i].childNodes[j].className == "li_selected") {
									var param = ulNodeList[i].id;
									param = param.substring(param.indexOf("_")+1);
									var nameval = ulNodeList[i].childNodes[j].firstChild.getAttribute("name");
									if(nameval == null){
										if(jQuery("#LiDayItemAuto").html() == null  || jQuery("#LiDayItemAuto").html() == 'undefined' || jQuery("#LiDayItemAuto").html() == '帮您选天数▼'
											|| jQuery("#LiDayItemAuto").html().indexOf('自主选择')!=-1
										){
											var txtStartDay = jQuery("#txtStartDay").val();
											var txtEndDay = jQuery("#txtEndDay").val();

											if(txtStartDay == null || txtStartDay == ''){
												jQuery("#DayE").html("请选择出发日期");
												jQuery('#DayE').show();
												return false;
											}
											if(txtEndDay == null || txtEndDay == ''){
												jQuery("#DayE").html("请选择结束日期");
												jQuery('#DayE').show();
												return false;
											}
											jQuery("#DayE").html("请计算天数");
											jQuery('#DayE').show();
											return false;
										}

										var day = parseInt(getDay(jQuery("#LiDayItemAuto").html()));
										if(day <= 0){
											jQuery("#DayE").html("天数只能选择大于等于1天");
											jQuery('#DayE').show();
											return false;
										}
										tempURL += "&"+param + "="+encodeURIComponent(day+"X");
									}else{
										tempURL += "&"+param + "="+encodeURIComponent(encodeURIComponent(ulNodeList[i].childNodes[j].firstChild.getAttribute("name")));
									}

									continue;
								}
							}
						}
						// 份数特殊处理
						if (jQuery(".shop_oknum > .number").length > 0) {
							tempURL += "&Mult=" + jQuery(".shop_oknum > .number").val();
						}
					}
					tempURL += "&complicatedFlag="+complicatedFlag;
	         	//传递到计划对比页面
				 art.dialog.data('productId', aProductId);
				 art.dialog.data('tempURL', tempURL);
		   		 art.dialog.open('../block/price_page.shtml',{title: '计划对比',width:790}, false);
			 }
		);
	}

	// 初始化积分
	//document.getElementById("productIntegral_" + riskCode).innerHTML = parseInt(jQuery("#price").val());
	//checkCookie();// 从cookies获取最近浏览的产品

});
jQuery(function() {
	jQuery('.demo-basic').poshytip({
		className : 'tip-darkgray',
		bgImageFrameSize : 11,
		alignX : 'right',
		alignY : 'center',
		offsetX : 15,
		offsetY : 16,
		allowTipHover : false,
		fade : false,
		slide : false
	});
});

jQuery(function(){
    var pl_tj = jQuery("#pl_btn_tj");
    var pl_obj = jQuery("#pl_con_box");
    var pl_close = jQuery("#close_pl");
    var cp_pl_btns = jQuery("#cp_pl_btns");
	pl_tj.click(function(){
		if (jQuery("#commentLogin").val() != "1") {
//			document.getElementById('headerShowLoginWindow').click();
			art.dialog.data('base', sinosoft.base);
			art.dialog.open(sinosoft.front+'/block/art_login.shtml',	 {id: 'loginArtWindow',title: '您尚未登录',width:'338px', height:'480px',fixed:true}, false);
		} else {
			jQuery.ajax({
				type: 'post',
				url: sinosoft.base+'/shop/comment!getType.action?RelaID='+jQuery("#RelaID").val(),
				dataType: "json",
				async: true,
				success: function(data) {
					if (data.status == 'success') {
						if (data.isBuy == '1') {
							jQuery("#scoreTr").show();

						} else {
							jQuery("#scoreTr").hide();
							jQuery("#commentType").text("* 咨询");
						}

					} else {
						Dialog.alert(data.message);
						return;
					}
				}
			});
		    if(pl_obj.is(":visible"))
		         {pl_obj.slideUp();}
		          else{pl_obj.slideDown();}
		}
	});
	pl_close.click(function(){
	         pl_obj.slideUp();
	})
	jQuery('#PointShowLoginWindow').live('click',function(){
		artLogin();//用弹出窗口登录
	 	   //配置
//	 	 if(jQuery('#artLoginFlag').val()==1){
//	 		 artLogin();//用弹出窗口登录
//	 	 }
//	 	 else{
//	 		 var localURL=window.location.href;
//	 		 var loginBackURL=encodeURIComponent(localURL);
//	 		 window.location.href= sinosoft.base + "/shop/member!newLogin.action?tagid=logintag&loginBackURL="+loginBackURL;
//	 	 }
		 });
});

function clickPraised(commentId) {
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/comment!dealPraised.action?commentId='+commentId,
		dataType: "json",
		async: true,
		success: function(data) {
			if (data.status == 'success') {
				jQuery("#praised_"+commentId).html(data.praisedNum);
				jQuery("#p_praise_"+commentId).removeClass();
				if (data.isPraised == 'Y') {
					jQuery("#p_praise_"+commentId).addClass("praised");
				} else {
					jQuery("#p_praise_"+commentId).addClass("praise");
				}
			} else {
				Dialog.alert(data.message);
				return;
			}
		}
	});
}

jQuery.fn.praise = function(opt) {
	  var _opts ;
	  return this.each(function() {

	    _opts = jQuery.extend({
	      tagid: this,
	      press:"pressed"
	    }, opt);

	    _init();
	    _monitor();

	  });
	  function _init() {
	    othis = jQuery(_opts.tagid);
	  }

	  function _monitor(){
	      othis.click(function(){
	           var b = jQuery(this).hasClass(_opts.press),
	           s =  jQuery(this).find(".count-num i").text(),
	           s = b ? parseInt(s)-1:parseInt(s)+1,
	           d = b === false,
	           count =  parseInt(jQuery(this).attr('count'))+1,
	           f = b ? '取消有用' : '有用';
	           jQuery(this).toggleClass(_opts.press,d).find('.count-num i').text(s).end().attr('aria-pressed', d).attr('title', f),jQuery(this).attr('count', count);

	          var id = jQuery(this).find(".count-num i").attr("id");
	          var commentId = id.substring(id.indexOf("_")+1);
	          jQuery.ajax({
	      		type: 'post',
	      		url: sinosoft.base+'/shop/comment!dealPraised.action?commentId='+commentId+"&flag="+d+"&praisedNum="+s,
	      		dataType: "json",
	      		async: true,
	      		success: function(data) {
	      			if (data.status != 'success') {
	      				art.dialog({
	                        id:'plz',
	                        icon: 'error',
	                        content: data.message
	                    });
	    				return;
	    			}
	      		}
	      	  });

	      })

	  };
	}
jQuery(".zan_active").praise();

/**
 * 为长期险详细页动态绑定投保人生日最大值、最小值
 */
jQuery(function() {
	if (jQuery("#inpRiskAppFactor_TextAge").length > 0) {
		jQuery.ajax({
			type: 'get',
			url: sinosoft.base+"/shop/order_config_new!ajaxDealInsuredDate.action?productId=" + riskCode,
			dataType: "json",
			async: true,
			success: function(data) {
				if (data.status == 'success') {
					var insStartDate = data.insStartDate;
					var insEndDate = data.insEndDate;
					jQuery("#inpRiskAppFactor_TextAge").removeAttr("onclick");
					jQuery("#inpRiskAppFactor_TextAge").click(function(){
						WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-'+insEndDate+'}',maxDate:'%y-%M-{%d-'+insStartDate+'}'});
					});
					//出生日期没有默认值则设置为最小值
					if(jQuery("#inpRiskAppFactor_TextAge").val() == ''){
						jQuery("#inpRiskAppFactor_TextAge").val(data.minDate);
					}
				} else {
					Dialog.alert(data.message);
				}
			}
		});
	}
});

function recordMemBrowse() {
	jQuery.ajax({
		type: 'get',
		url: sinosoft.base+"/shop/browse_record!recordDetailBrowse.action?productId=" + riskCode,
		dataType: "json",
		async: true,
		success: function(data) {

		}
	});
}


function initFavorites(){
	var RiskCode = jQuery("#RiskCode").val();
	jQuery.ajax({
		type : 'get',
		url : sinosoft.front+'/wj/shop/favorites!initFavorites.action?productId='
				+ RiskCode,
		dataType : 'html',
		success : function(de) {
			if (de == '0') {
				jQuery("#favorite").addClass("no_add_sc");
				jQuery("#favorite").html("<em></em>收藏");
			} else {
				jQuery("#favorite").removeClass("no_add_sc");
				jQuery("#favorite").html("<em></em>已收藏");
			}
		}
	});
}

function selfDefineDay(startId, endId, riskcode) {
	var beginDate = jQuery.trim(jQuery('#' + startId).val());
	var endDate = jQuery.trim(jQuery('#' + endId).val());
	var diffDays = 0;
	if ((isDate(beginDate) != false) && (isDate(endDate) != false)) {
		var rbegin = beginDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
		var rend = endDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
		var Date1 = new Date(rbegin[1], rbegin[3] - 1, rbegin[4]);
		var Date2 = new Date(rend[1], rend[3] - 1, rend[4]);
		if (Date1 > Date2) {
			jQuery("#DayE").html("出发日期不能晚于结束日期");
			jQuery('#DayE').show();
		} else {
			diffDays = parseInt(Math.abs(Date2 - Date1) / 1000 / 60 / 60 / 24) + 1;
		}
	}
	jQuery("#hdnSelectedDay").val(diffDays);
	jQuery("#LiDayItemAuto").html(diffDays + '天');
	if (diffDays > 0) {
		jQuery("#help_select_day").hide();
		if(complicatedFlag!="Y"){
			premDocal(riskcode);
		}
	}
}
function Dayclear() {
	jQuery("#UlDayBelongs").find(".LiDayItemAuto").parent("li").each(
			function() {
				jQuery(this).removeClass("li_selected");
			});
	jQuery("#help_select_day").hide();
	jQuery('#LiDayItemAuto').html("自主选择");
	jQuery('#DayE').html("");
	jQuery('#DayE').hide();

}
function DaySelectAuto(type, enday) {
	jQuery("#UlDayBelongs").find("ul").find("li").each(function() {
		jQuery(this).removeClass("li_selected");
	});
	jQuery("#Li" + type + enday).parent().addClass("li_selected");
	jQuery("#hdnIsAutoSelect").val("1");
	jQuery("#help_select_day").show();
	jQuery("#hdnSelectedDay").val("0");
}
function DaySelect(type, enday) {
	jQuery("#UlDayBelongs").find("ul").find("li").each(function() {
		jQuery(this).removeClass("li_selected");
	});
	jQuery("#Li" + type + enday).parent().addClass("li_selected");
	jQuery("#hdnIsAutoSelect").val("0");
	jQuery("#help_select_day").hide();
	jQuery("#hdnSelectedDay").val(enday);
	jQuery('#LiDayItemAuto').html("自主选择");
	jQuery('#DayE').html("");
	jQuery('#DayE').hide();
}
/**
 * 判断是否是日期.
 *
 * @param dateString
 * @return
 */
function isDate(dateString) {
	var r = dateString.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	if (r == null) {
		return false;
	}
	var d = new Date(r[1], r[3] - 1, r[4]);
	var num = (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d
			.getDate() == r[4]);
	var time = d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3]
			&& d.getDate() == r[4];
	if (time != true) {
		return false;
	}
}
/**
 * 清除错误信息.
 *
 * @param erroId
 * @return
 */
function ErroClear(erroId) {
	jQuery('#' + erroId).html("");
	if (erroId == "DayE") {
		jQuery('#' + erroId).hide();
	}
}
/**
 * 判断是否是日期格式.
 *
 * @param id
 * @param erroId
 * @return
 */
function isBirthDate(id, erroId) {
	var date = jQuery.trim(jQuery('#' + id).val());
	if (isDate(date) == false) {
		jQuery('#' + erroId).html("日期格式错误");
		if (erroId == "DayE") {
			jQuery('#' + erroId).show();
		}
		return false;
	}
}

function getCount() {
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			var result = xmlhttp.responseText.split(",");
			// 百年康惠保重大疾病保险产品详细页改版 临时代码，将来统一后可删除
			if (riskCode == '224801001') {
				document.getElementById("count").innerHTML = "用户评价<span>("
					+ result[0] + ")</span>";
			} else {
				document.getElementById("count").innerHTML = "商品评价<span>("
						+ result[0] + ")<span class=\"av_bk\"></span>";
			}
		}
	}
	xmlhttp.open("GET", "/wj/query/counter?url=" + window.location.href, true);
	xmlhttp.send();
	blurcount();
}
function blurcount(){
	var reg = new RegExp("(^|&)count=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null){
	  var rt = unescape(r[2]);
	  if(rt=="count"){
		  jQuery("#count").click();
		  window.scrollTo(document.getElementById('count').offsetLeft,document.getElementById('count').offsetTop);
	  }
	}
}
/**
 * 预约弹窗.
 */
jQuery(function() {
	jQuery("#yuyue_bsfa").click(function() {
		art.dialog({
			content : document.getElementById('yuyue_table2'),
			id : 'yuyue_bsfa2',
			title : '非常感谢您选择开心保，为了确保能联系到您，请准确填写以下信息'
		});
	});
});
/**
 * 从详细页模版移植过来的
 */
jQuery(function(){
	jQuery("#yuyue_table input").change( function(){ jQuery("#yuyueshow").text("");}  )
	 jQuery("#yuyue_table select").change( function(){ jQuery("#yuyueshow").text("");}  )
	 jQuery("#yuyue_table2 input").change( function(){ jQuery("#yuyueshow").text("");}  )
	 jQuery("#yuyue_table2 select").change( function(){ jQuery("#yuyueshow").text("");}  )
	});
/*//初始化方法
function inLoad(){
	salesVolumeLoad();
	weixinFun();
	cpsFun();
}*/
/**
 * 从详细页模版移植过来的－－计算销量
 */
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
		async: true,
		success: function(data) {
			jQuery.each(data, function(index, object) {
				document.getElementById(index).innerHTML=object+"份";
			});
		}
	});
	// 产品详细页活动展示
	var cpsUserId = jQuery.cookie('cpsUserId');
	var channelsn = "wj";

	var channel_sn = jQuery.cookie('channelSn');
	if(typeof(channel_sn)!="undefined" && channel_sn!=null && channel_sn!=""){
		channelsn = channel_sn;
	}
		jQuery.ajax({
			type: 'post',
			url: sinosoft.base+'/shop/sales_volume!showActivity.action?productId='+id+'&channelSn='+channelsn,
			dataType: "json",
			async: true,
			success: function(data) {
				jQuery.each(data, function(index, object) {
					if("success"==index){
						jQuery("#yhinfo").append(data.success);
						var pointtitle = data.pointtitle;
						if (pointtitle != null && pointtitle != '') {
							pointtitle = pointtitle + " ";
						}
						jQuery("#pointtitle_"+id[0].substring(7)).html(pointtitle);
					}

					//团购倒计时
					if("DateCountdown"==index){
						jQuery("#CountdownPart").append(data.DateCountdown);
						 jQuery(".yomibox").each(function(){
				   			 var _this = this;
				   			 jQuery(this).yomi(  {
				        			callback:function(){
				        				jQuery("#gropu_time_show").remove();
				        				jQuery(_this).parents(".group_time").remove();
				        			},
				        			callbackhours:function(){}
				      				});
				  		});
					}
				});

					if(jQuery('#productIntegral_'+riskCode).html()=="0"){
					  	var integer_login = jQuery("#headerLoginMemberUsername").html();
						if (null == integer_login || "" == integer_login ){
							var html = jQuery("#integer_login").html().replace("获得更多积分","").replace("，","");
							jQuery("#integer_login").html(html).show();
						}
					if(jQuery("#yhinfo li").length==1){
						jQuery(".tb_yh").css("display","none");
					}else{
						jQuery("#yhinfo li:first").css("display","none");
					}
				}

			}
		});
}

/**
 * 加载公告
 */
function informLoad() {
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!showInform.action?productId='+riskCode,
		dataType: "json",
		async: true,
		success: function(data) {
			if (data.info == null || data.info == '') {
				jQuery('.gg_box').hide();
			} else {
				jQuery('.gg_lists').html(data.info);
				jQuery('.gg_box').show();
			}

			if (data.companycount == null || data.companycount == '' || typeof(data.companycount) == "undefined") {
				jQuery('#company_product_num').html("（0）");

			} else {
				jQuery('#company_product_num').html("（"+data.companycount+"）");
			}
		}
	});
}

/**
 * 从详细页模版移植过来的－－需要页面初始化加载
 */
function weixinFun(){
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
	var proid = id[0].substring(7, id[0].length);
	jQuery
		.ajax( {
			type : 'post',
			url : sinosoft.base+'/shop/sales_volume!sendWeiXiData.action?productId=' + id,
			dataType : "json",
			async : false,
			success : function(data) {
				var vlt = new VLTrace_Action();
				var sum = new VLTrace_ActionParam();
				sum.dataID = "a0000008";
				sum.CustomerID = data.memberid + "";
				sum.TotalAmount ="0";
				sum.Pieces = "1";
				vlt.setSummary(sum);
				var det = new VLTrace_ActionParam();
				det.dataID = "a0000009";
				det.ProductID = proid + "";
				det.TotalAmount ="0";
				det.Pieces = "1";
				det.ProductAttribute1 = data.riskname + "";
				det.ProductAttribute2 = data.companyname + "";
				vlt.addDetail(det);
				vlt.sendAction();
			}
		});
}
/**
 * 从详细页模版移植过来的－－需要页面初始化加载
 */
function cpsFun(){
	if(isCpsProduct=='02')
	{
		var ddd1=document.getElementById("button_b clearfix");
		var ddd2=document.getElementById("button_b cf");
		var ddd3=document.getElementById("ccp_box_con");
		var ddd4=document.getElementById("ligm_consf");
		ddd1.style.cssText="display:none";
		ddd2.style.cssText="display:block";
		ddd3.style.cssText="display:block";
		ddd4.style.cssText="display:none";
	}
}
/**
 * 从详细页模版移植过来的－－预约
 */
function sure1(){
	jQuery("#tijiaoyue_btn").attr("disabled", true);
	var name = document.getElementById("name1").value;
	var tel = document.getElementById("tel1").value;
	var area1 = document.getElementById("area11").value;
	var area2 = document.getElementById("area22").value;
	var productName = document.getElementById("productName").value;
	if(name==null || name==""){

		jQuery("#yuyueshow1").text("请填写姓名!");
		jQuery("#tijiaoyue_btn").attr("disabled", false);
		return false;
	}
	if(tel==null || tel==""){
		jQuery("#yuyueshow1").text("请填写联系方式!");
		jQuery("#tijiaoyue_btn").attr("disabled", false);
		return false;
	}
	if(tel != null && tel != ""){
		var patrn = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
	    if (!patrn.exec(tel)) {
	    	jQuery("#yuyueshow1").text("请输入正确的手机号码！");
	    	jQuery("#tijiaoyue_btn").attr("disabled", false);
	        return false;
	    }
	}
	if(area1=="-1"){

		jQuery("#yuyueshow1").text("请填写省级地区!");
		jQuery("#tijiaoyue_btn").attr("disabled", false);
		return false;
	}
	if(area2=="-1"){

		jQuery("#yuyueshow1").text("请填写市级地区!");
		jQuery("#tijiaoyue_btn").attr("disabled", false);
		return false;
	}
	name = encodeURI(encodeURI(name));
	tel = encodeURI(encodeURI(tel));
	productName = encodeURI(encodeURI(productName));
	jQuery.ajax({
		type:'get',
        url: sinosoft.base+"/shop/reservation!save1.action?name="+name+"&tel="+tel+"&area1="+area1+"&area2="+area2+"&productName="+productName,
        dataType:'html',
        success:function(de){
		if(de == "ok"){
    		document.getElementById("yuyueshow1").innerHTML="您的预约信息已经提交";
    		jQuery("#tijiaoyue_btn").attr("disabled", false);

    	}else{
    		document.getElementById("yuyueshow1").innerHTML="预约失败，请重新预约";
    		jQuery("#tijiaoyue_btn").attr("disabled", false);
    	}
        }
	});
}
/**
 * 从详细页模版移植过来的－－地区
 */
function getChildCity(areaId){
	jQuery.ajax({
		type:'get',
        url: sinosoft.base+"/shop/reservation!getChildArea.action?areaId="+areaId,
        dataType:'json',
        success:function(de){
        	if(de!=null){
        		fillChildCity(de);
        	}
        }
	});
}function getChildCity1(areaId){
	jQuery.ajax({
		type:'get',
        url: sinosoft.base+"/shop/reservation!getChildArea.action?areaId="+areaId,
        dataType:'json',
        success:function(de){
        	if(de!=null){
        		fillChildCity1(de);
        	}
        }
	});
}
function fillChildCity(item){
	var childAreaOPT = document.getElementById("area2");
	childAreaOPT.options.length=1;
    for(var i=0;i<item.length;i++){
     	var oo=new Option(item[i].placeName,item[i].placeCode);
     	childAreaOPT.options[childAreaOPT.options.length]=oo;
    }
}

function fillChildCity1(item){
	var childAreaOPT = document.getElementById("area22");
	childAreaOPT.options.length=1;
    for(var i=0;i<item.length;i++){
     	var oo=new Option(item[i].placeName,item[i].placeCode);
     	childAreaOPT.options[childAreaOPT.options.length]=oo;
    }
}
/**
 * 从详细页模版移植过来的－－预约
 */
function sure(){
	jQuery("#tijiaoyue_btn").attr("disabled", true);
	var name = document.getElementById("name").value;
	var tel = document.getElementById("tel").value;
	var area1 = document.getElementById("area1").value;
	var area2 = document.getElementById("area2").value;
	var productName = document.getElementById("productName").value;
	if(name==null || name==""){

		jQuery("#yuyueshow").text("请填写姓名!");
		jQuery("#tijiaoyue_btn").attr("disabled", false);
		return false;
	}
	if(tel==null || tel==""){
		jQuery("#yuyueshow").text("请填写联系方式!");
		jQuery("#tijiaoyue_btn").attr("disabled", false);
		return false;
	}
	if(tel != null && tel != ""){
		var patrn = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
	    if (!patrn.exec(tel)) {
	    	jQuery("#yuyueshow").text("请输入正确的手机号码！");
	    	jQuery("#tijiaoyue_btn").attr("disabled", false);
	        return false;
	    }
	}
	if(area1=="-1"){

		jQuery("#yuyueshow").text("请填写省级地区!");
		jQuery("#tijiaoyue_btn").attr("disabled", false);
		return false;
	}
	if(area2=="-1"){

		jQuery("#yuyueshow").text("请填写市级地区!");
		jQuery("#tijiaoyue_btn").attr("disabled", false);
		return false;
	}
	name = encodeURI(encodeURI(name));
	tel = encodeURI(encodeURI(tel));
	productName = encodeURI(encodeURI(productName));
	jQuery.ajax({
		type:'get',
        url: sinosoft.base+"/shop/reservation!save1.action?name="+name+"&tel="+tel+"&area1="+area1+"&area2="+area2+"&productName="+productName,
        dataType:'html',
        success:function(de){
		if(de == "ok"){
    		document.getElementById("yuyueshow").innerHTML="您的预约信息已经提交";
    		jQuery("#tijiaoyue_btn").attr("disabled", false);

    	}else{
    		document.getElementById("yuyueshow").innerHTML="预约失败，请重新预约";
    		jQuery("#tijiaoyue_btn").attr("disabled", false);
    	}
        }
	});
}
/**
 * 从详细页模版移植过来的－－cookie
 *//*
function getCookie() {// 获取指定名称的cookie的值
	var output = "";
	var arrStr = document.cookie.split("; ");
	var tLen = 0;
	for ( var i = arrStr.length-1; i >=0; i--) {
		var temp = arrStr[i].split("=");
		if (temp[0].indexOf("$pid") != -1){
			if(tLen!=0){
				output = output + unescape(temp[1]);
			}
		    tLen = tLen+1;
		}
		if (tLen == 6 ) {
			break;
		}
	}
	if (output == "") {
		output = "<div class='l_box_p'>您最近没有查看过商品<br><p>在查看过商品详细信息页面或搜索结果后，查看这里可以找到一种简单的方法返回到您感兴趣的产品。</p></div>";
	}
	return output;
}
function setCookie() {
	var c_name = "$pid" + riskCode;
	var value = "<li class='clearfix'><a target='_blank' href='"+window.location.href+"'>"+artTitle+"</a><em class='list_xls'>销量："+artSalesVolume+"</em><em class='list_pays'>"
			+jQuery("#productPrem_"+riskCode).text()+ "</em><em class='list_pays_price moneys'>"+jQuery("#productRatePrem_"+riskCode).text()+"</em></li>";
	var expiredays = 365;
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	document.cookie = c_name + "=" + escape(value) + ";expires="+ exdate.toGMTString()+";path=/";
	var oDate = new Date();
	oDate.setDate(oDate.getDate() - 100);
	var tLen = 0;
	var arrStr = document.cookie.split("; ");
	for ( var i = arrStr.length-1; i >=0; i--) {
		var temp = arrStr[i].split("=");
		if (temp[0].indexOf("$pid") != -1){
		    tLen = tLen+1;
		    if (tLen >= 7 ) {
		    	document.cookie = temp[0] + "=a;expires=" + oDate.toGMTString()+";path=/";
			}
		}
	}
}
*/
/*function checkCookie() {
	document.getElementById("RecentView").innerHTML = getCookie();
	setCookie();

}*/
function chakan(str)
{
	window.open(str);

}

function submitp(str) {
jQuery.ajax({
	type : 'get',
	url : sinosoft.front+'/wj/shop/favorites!add.action?productId='
			+ str,
	dataType : 'html',
	success : function(de) {

		if (de == 'notlogin') {
			artLogin();// 使用弹出窗口登录
		} else {
			alert(de);
			jQuery("#favorite").removeClass("no_add_sc");
			jQuery("#favorite").html("<em></em>已收藏");
		}
	}
});
}

/**
 * 下架产品处理.
 */
function queryProductStateLoad1() {

	var pId = jQuery("#RiskCode").val();
	var pTypeName =jQuery("#pTypeName").val();
	var pPrice =jQuery("#price").val();
	var gdUrl = jQuery(pTypeName).attr("href");
	var newTypeName = jQuery(pTypeName).attr("innerText");

	jQuery("#gengduo").attr("href",gdUrl);


	jQuery.ajax({
		type: 'post',
		data: {"pTypeName":newTypeName,"productId":pId,"pPrice":pPrice},
		url: sinosoft.base+'/shop/sales_volume!queryProductState.action',
		dataType: "json",
		async: false,
		success: function(data) {

			if(data.state == "N"){
				jQuery(".content").css("display","none");// 产品详情隐藏
				jQuery(".cp_descon").css("display","none");
				jQuery("#productUnder").css("display","block");// 下架信息展示

				jQuery(".share_link").css("display","none");// 隐藏加入收藏、加入对比

				if(data.product != ""){
					jQuery("#error_div").css("display","block");
					jQuery("#ajaxProduct").html(data.product);
				}

			}
		}
	});
}

/**
 * 下架产品处理.
 */
function queryProductStateLoad() {

	var pId = jQuery("#RiskCode").val();
	var pTypeName =jQuery("#pTypeName").val();
	var pPrice =jQuery("#price").val();
	var gdUrl = jQuery(pTypeName).attr("href");
	var newTypeName = jQuery(pTypeName).attr("innerText");

	jQuery("#gengduo").attr("href",gdUrl);


	jQuery.ajax({
		type: 'post',
		data: {"pTypeName":newTypeName,"productId":pId,"pPrice":pPrice},
		url: sinosoft.base+'/shop/sales_volume!queryProductState.action',
		dataType: "json",
		async: false,
		success: function(data) {

			if(data.state == "N"){
				jQuery(".goto_buy").css("display","none");// 隐藏立即兑换

				jQuery("#descon_under").css("display","block");

				jQuery("#descon_publish").css("display","none");

			}
		}
	});
}
// 页面初始化方法，查询"购买了该产品的还购买了"部分，修改为静态显示
function queryProductRec(){
	jQuery.ajax({
		url:  sinosoft.base+'/shop/sales_volume!queryProductRec.action?productid='+jQuery("#RiskCode").val(),
		type: 'post',
		dataType: 'json',
		success: function(data){
			var obj = eval(data);
			 jQuery("#products").html(obj.products) ;
	 	}
	});
};
function doPoints(){
	var prem = jQuery("#productPrem_"+riskCode).text();
	if (prem.indexOf("积分") != -1) {
		prem = prem.substring(0, prem.indexOf("积分"));
	}
	jQuery.ajax({
		url:  sinosoft.base+'/shop/points!queryPayPrice.action?prem='+prem,
		type: 'post',
		dataType: 'json',
		success: function(data){
			var obj = eval(data);
			 if(obj.status == 1){
				 doBuy();
			 }else if(obj.status == 3){
				 art.dialog({
	    			    id:'yhj_log',
	    			    padding: '25px 50px 5px 50px',
	    			    title:'积分兑换',
	    			    fixed: true,
	    			    drag: false,
	    			    content: "会员需在网站累计购物满"+obj.message+"元后，方可在积分商城兑换商品。",
	    			    button:[{name: '确认'}]
	    			});
			 } else if (obj.status == 4) {
				 // 积分不足
		         art.dialog({
		              lock: true,
		              title:'积分不足',
		              padding:'20px 30px 20px 15px',
		              background: '#000000', // 背景色
		              opacity: 0.6,  // 透明度
		              content: '<span class="ji_dir_tit">对不起，您的账户只有'+obj.message+'积分不够兑换，<br /><a href="'+sinosoft.front+'/daogou/">看看需要的保险</a>提前购买赚积分吧！</span>',
		              icon: 'face-sad',
		              ok: function () {
		                  return true;
		              }
		          });
			 } else if (obj.status == 2) {
				 artLogin();
			 }
	 	}
	});
}
/**
 * 立即购买
 * @returns {Boolean}
 */
function doBuy(){



	// 判断有份数校验，如果就校验

	var oPerNum = jQuery(".shop_oknum > .number");
	if(oPerNum){
		var _num = oPerNum.val();
		var sTotal = jQuery(".shop_oknum > .reperoty > b").text();
		var nTotal = parseInt(sTotal);
		if (_num == "" || isNaN(_num)) {
			oPerNum.val(1);
			_num = 1;
		}

		if (_num > nTotal || _num == 0) {
			art.dialog({
				icon : 'error',
				content : '您输入的份数有误，只能购买1至' + nTotal + '份',
				id : 'errorid'
			});
			return;
		}

	}

	jQuery.ajaxLoadImg.show('showid');
	var rootEle = document.getElementById("divRiskAppFactor_" +riskCode);
	var tempURL = "";
	if (rootEle != null) {
		var dc = new DataCollection();
		var ulNodeList = rootEle.getElementsByTagName("UL");
		for ( var i = 0; i < ulNodeList.length; i++) {
			for ( var j = 0; j < ulNodeList[i].childNodes.length; j++) {
				if (ulNodeList[i].childNodes[j].className == "li_selected") {
					var param = ulNodeList[i].id;
					param = param.substring(param.indexOf("_")+1);
					var nameval = ulNodeList[i].childNodes[j].firstChild.getAttribute("name");
					if(nameval == null){
						if(jQuery("#LiDayItemAuto").html() == null  || jQuery("#LiDayItemAuto").html() == 'undefined' || jQuery("#LiDayItemAuto").html() == '自主选择'){
							var txtStartDay = jQuery("#txtStartDay").val();
							var txtEndDay = jQuery("#txtEndDay").val();

							if(txtStartDay == null || txtStartDay == ''){
								jQuery("#DayE").html("请选择出发日期");
								jQuery('#DayE').show();
								jQuery.ajaxLoadImg.hide('showid');
								return false;
							}
							if(txtEndDay == null || txtEndDay == ''){
								jQuery("#DayE").html("请选择结束日期");
								jQuery('#DayE').show();
								jQuery.ajaxLoadImg.hide('showid');
								return false;
							}
							jQuery("#DayE").html("请计算天数");
							jQuery('#DayE').show();
							jQuery.ajaxLoadImg.hide('showid');
							return false;
						}

						var day = parseInt(getDay(jQuery("#LiDayItemAuto").html()));
						if(day <= 0){
							jQuery("#DayE").html("天数只能选择大于等于1天");
							jQuery('#DayE').show();
							jQuery.ajaxLoadImg.hide('showid');
							return false;
						}
						tempURL += "&"+param + "="+encodeURIComponent(day+"X");
					}else{
						tempURL += "&"+param + "="+encodeURIComponent(encodeURIComponent(ulNodeList[i].childNodes[j].firstChild.getAttribute("name")));
					}
					continue;
				}else if(ulNodeList[i].childNodes[j].id == "birthday_li_id"){//My97DatePicker  时间控件
					var param = ulNodeList[i].id;
					param = param.substring(param.indexOf("_")+1);
					tempURL += "&"+param + "="+encodeURIComponent(encodeURIComponent(jQuery("#inpRiskAppFactor_TextAge").val()));
				}
			}

		}
		// 份数特殊处理
		if (jQuery(".shop_oknum > .number").length > 0) {
			tempURL += "&Mult=" + jQuery(".shop_oknum > .number").val();
		}
	}
	var supplierCode=2007;
	var isCpsProduct = '';
	if(isCpsProduct=="01"){
		//_gaq.push(['_trackEvent', 'CPS','TO']);
		window.location.href=sinosoft.base+"/shop/order_cps!recordJump.action?productId="+riskCode;


	}else{
		if (complicatedFlag == 'Y') {
			var param = {};
			var selectedLi = jQuery(".bz_time .li_selected");
			var selectedTd = jQuery("#gh_tables td.CDutyCol2").find("select, span");
			var jAutoSelDay = jQuery(".cp_descon #UlDayBelongs > ul > li > span:not(#LiDayItemAuto)");
			var jDaySel = jQuery("#LiDayItemAuto");
			var plantemp = "";
			/**/
			selectedLi.each(function(i, v) {
				var _key, _val;

				if(jQuery(v).children().attr("id") == "LiDayItemAuto") {
					var _nowNum = parseInt(jDaySel.text());
					var maxDayScope = jQuery(jAutoSelDay[jAutoSelDay.length - 2]).text().slice(0, -1).split("-");
					var selDayMax;

					if(maxDayScope.length == 1) {
						selDayMax = maxDayScope[0]
					} else if (maxDayScope.length > 1) {
						selDayMax = maxDayScope[1]
					}

					jAutoSelDay.each(function(i, u) {
						if(i == jAutoSelDay.length - 1) return;

						var dayScope = jQuery(u).text().slice(0, -1).split("-");

						if((dayScope.length == 1 && _nowNum == dayScope[0]) || (dayScope.length > 1 && _nowNum >= dayScope[0] && _nowNum <= dayScope[1])) {
							_val = jQuery(u).attr("name");
						}
					});

					if (_nowNum > selDayMax) {
						jDaySel.parent().removeClass("li_selected");
						jDaySel.text("自主选择");
						jAutoSelDay.eq(jAutoSelDay.length -1).parent().addClass("li_selected");
						_val = jAutoSelDay.eq(jAutoSelDay.length -1).attr("name");
					}
				} else {
					_val = jQuery(v).children().attr("name");
				}

				_key = jQuery(v).parent().attr("id");

				param[""+ _key +""] = _val;
				if (_key.indexOf("Plan") > 0) {
					plantemp = _val;
				}
			});
			var duty = "";
			selectedTd.each(function(i, v) {
				var _key = v.id.slice(0, v.id.indexOf("_"));
				var _val;

				if(v.nodeName == "SELECT") {
					_val = jQuery(v).val();
				} else if(v.nodeName == "SPAN") {
					_val = jQuery(v).text();
				}

				if(_val.search(/不投保/) != -1) {
					_val = 0;
				} else if(_val.search(/万/) != -1) {
					_val = parseFloat(_val)*10000;
				} else if(_val.search(/元\/天/) != -1) {
					_val = parseFloat(_val);
					if (riskCode.indexOf("2100") >= 0) {
						_val = _val * 180;
					}
				} else if(_val.search(/-/) != -1) {
					return;
				} else if(_val.search(/是/) != -1) {
					_val = parseFloat(1);
				} else if(_val.search(/否/) != -1) {
					_val = parseFloat(0);
				} else if(!isNaN(parseInt(_val.split("/")[0])) && parseInt(_val.split("/")[0]) != -1) {
					var var1 = parseInt(_val.split("/")[1]);
					_val = parseFloat(_val.split("/")[0]);
					if(_val == 500 && var1 == 6) {
						_val = 500.0;
					}
				} else {
					_val = 0;
				}
				duty +=  _key + '-' + _val +',';

			});
			param["RiskCode"] = riskCode;
			var hidDutyInfo = document.getElementById("hidDutyInfo");
			if (typeof(hidDutyInfo) != "undefined" && hidDutyInfo != null) {
				param["duty"] = duty.substring(0,duty.length-1) + "@" + hidDutyInfo.value;
			} else {
				param["duty"] = duty.substring(0,duty.length-1) ;
			}

			// 百年康惠保重大疾病保险产品详细页责任上调临时代码，后期统一后，可以删除
			if (riskCode=='224801001') {
				var planVal = '';
				selectedLi.each(function(i, v) {
					var _key, _val;
					_val = jQuery(v).children().attr("name");
					_key = jQuery(v).parent().attr("id");

					// 计划关联责任
					if (_key.indexOf("Plan") > 0) {
						planVal = _val;
						duty1 = '224801001001-'+_val;
					}

					if (_key == 'duty' && _val == '0') {
						duty2 ='224801001002-'+_val;
					} else if(_key == 'duty' && _val != '0'){
						duty2 ='224801001002-'+planVal;
					}
				});
				param["duty"] = duty1 + "," + duty2 ;
			}
			jQuery.ajax({
				type: "GET",
				url: sinosoft.base + '/shop/filter!dutySave.action',
				data: param,
				dataType: "json",
				success: function(data){

					if(data && data.status == '1'){
						tempURL += "&complicatedFlag=Y&dutyTempSerials="+data.serials;
						window.location.href=sinosoft.base+"/shop/order_config_new!buyNow.action?productId="+riskCode+tempURL;
					}else {
						jQuery.ajaxLoadImg.hide('showid');
						alert(data.msg);
					}
				}
			});
		} else {
			var uurl = sinosoft.base+"/shop/order_config_new!buyNow.action?productId="+riskCode+tempURL+"&complicatedFlag=N";

			// 积分商城详细页面区分产品详细页面标记
			if (jQuery("#pointproducttype").length != 0) {
				uurl += "&pointExchangeFlag=1" ;

			}

			window.location.href = uurl;

		}
	}
}
function loadXMLDoc(url)
{
var xmlhttp;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("RecentView").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("GET",url+'&url='+window.location.href,true);
xmlhttp.send();
}
jQuery(function(){

	/*说明tip*/

	jQuery('#demo-form-name').poshytip({
             className: 'tip-yellowsimple',
             alignTo: 'target',
             alignX: 'right',
             alignY: 'center',
             offsetX: 5,
             showTimeout: 100
        });

});

//满减活动信息
function searchProductListAvtivity(){
	var list=jQuery('p[id^=Activity_]');
	var id = new Array(list.length);
	var idStr="";
	for (var i = 0; i < list.length; i++) {
           id[i] = list[i].id;
           idStr+= list[i].id;
	}
	if(idStr==""){
		return;
	}
	var cpsUserId = jQuery.cookie('cpsUserId');
	var channelsn = "wj";
	var channel_sn = jQuery.cookie('channelSn');
	if(typeof(channel_sn)!="undefined" && channel_sn!=null && channel_sn!=""){
		channelsn =channel_sn;
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!searchProductListAvtivityRe.action?productId='+id+'&channelSn='+channelsn+"&detailFlag=true",
		dataType: "json",
		async: true,
		success: function(data) {
			jQuery.each(data, function(index, object) {
				index=index.substring(0,index.indexOf("@"));
				document.getElementById(index).innerHTML= "<span  class=\"shop_activity activity1\" >" + object.substring(0,object.indexOf("@")) + "</span>";
			});
		}
	});
}
var Class = {
    create: function() {
        return function() { this.initialize.apply(this, arguments); };
    }
};
var Extend = function(destination, source) {
    for (var property in source) {
        destination[property] = source[property];
    }
}
function stopDefault( e ) {
     if ( e && e.preventDefault ){
        e.preventDefault();
    }else{
        window.event.returnValue = false;
    }
    return false;
}
/**
 * 星星打分组件
 */
var Stars = Class.create();
Stars.prototype = {
    initialize: function(star,options) {
        this.SetOptions(options); //默认属性
        if (typeof(document.getElementById(star)) == undefined || document.getElementById(star) == null) {
    		return;
    	}
        var flag = 999; //定义全局指针
        var isIE = (document.all) ? true : false; //IE?
        var starlist = document.getElementById(star).getElementsByTagName('a'); //星星列表
        var input = document.getElementById(this.options.Input) || document.getElementById(star+"_input"); // 输出结果
        var tips = document.getElementById(this.options.Tips) || document.getElementById(star+"_tips"); // 打印提示
        var nowClass = " " + this.options.nowClass; // 定义选中星星样式名
        var tipsTxt = this.options.tipsTxt; // 定义提示文案
        var tipsstyle = this.options.tipsstyle; // 定义提示文案样式
        var len = starlist.length; //星星数量
        for(i=0;i<len;i++){ // 绑定事件 点击 鼠标滑过
            starlist[i].value = i;
            starlist[i].onclick = function(e){
                stopDefault(e);
                this.className = this.className + nowClass;
                flag = this.value;
                input.value = this.getAttribute("star:value");
                tips.innerHTML = tipsTxt[this.value];
            }
            starlist[i].onmouseover = function(){
                if (flag < 999){
                    var reg = RegExp(nowClass,"g");
                    starlist[flag].className = starlist[flag].className.replace(reg,"");
                  // tips.innerHTML = tipsTxt[this.value]
                }
            }
            starlist[i].onmouseout = function(){
                if (flag < 999){
                    starlist[flag].className = starlist[flag].className + nowClass;

                }
            }
        };
        if (isIE){ //FIX IE下样式错误
            var li = document.getElementById(star).getElementsByTagName('li');
            for (var i = 0, len = li.length; i < len; i++) {
                var c = li[i];
                if (c) {
                    c.className = c.getElementsByTagName('a')[0].className;
                }
            }
        }
    },
    //设置默认属性
    SetOptions: function(options) {
        this.options = {//默认值
            Input:  "",//设置触保存分数的INPUT
            Tips:     "",//设置提示文案容器
            nowClass: "current-rating",//选中的样式名
            tipsTxt:  ["<span class='yb_style'>一般</span>","<span class='yb_style'>一般</span>","<span class='my_style'>满意</span>","<span class='my_style'>满意</span>","<span class='jx_style'>惊喜</span>"]//提示文案
        };
        Extend(this.options, options || {});
    }
};
var Stars1 = new Stars("stars1");
var Stars2 = new Stars("stars2");
var Stars3 = new Stars("stars3");




/**
 * @des: 产品详细页优化: 内容区域的头部导航可随下拉框滚动
 * @maker: Songzairan
 * @date: 2014.10.29
 */
jQuery.fn.fixedNavigation = function(opt) {
	var _opt, jWin, jNav, nNavOffTop;

	return this.each(function() {
		_opt = jQuery.extend({
		navElm: this,
		cldElm: "em",
		bnrElm: ".bnr_box",
		tagElm: "#tag_box",
		fixName: "fixed"
		}, opt);

		_init();
		_monitor();
	});

	/* init */
	function _init() {
		jWin = jQuery(window);
		jNav = jQuery(_opt.navElm);
		jCld = jQuery(_opt.cldElm, jNav);
		jTag = jQuery(_opt.tagElm);
		nNavOffTop = jNav.offset().top;
	}

	/* bind event */
	function _monitor() {
		jWin.bind("scroll", function() {
			var nWinTop = jWin.scrollTop();

			if(nWinTop >= nNavOffTop) {
				jNav.addClass(_opt.fixName);
			} else{
				jTag.css("paddingTop", 0);
				jNav.removeClass(_opt.fixName);
			}
		});

		jCld.bind("click", function() {
			var nWinTop = jWin.scrollTop();
			if(nWinTop < nNavOffTop) return;
			jWin.scrollTop(nNavOffTop + jNav.height());
			jTag.css("paddingTop", jNav.height()*2);
			jQuery(_opt.bnrElm).show();
		});
	}
};


/**
 * @des: 产品详细页优化：页面右侧建立投保框，并可随下拉框滚动
 * @maker: Songzairan
 * @date: 2014.10.29
 */
jQuery.fn.fixedBanner = function(opt) {
	var _opt, jWin, jBnr, jBox, jNav, nFixTop;

	return this.each(function() {
		_opt = jQuery.extend({
			bnrElm: this,
			boxElm: "#tag_box",
			fixTop: 250,
			space: 90,
			nBtm: -48
		}, opt);

		_init();
		_monitor();
	});

	/* init */
	function _init() {
		jWin = jQuery(window);
		jBnr = jQuery(_opt.bnrElm);
		jBox = jQuery(_opt.boxElm);
		jNav = _opt.navElm? jQuery(_opt.navElm) : null;
		if(jNav) nNavOffTop = jNav.offset().top;
		nFixTop = _opt.fixTop;
	}

	/* bind event */
	function _monitor() {
		//jBnr.hide();

		jWin.bind("scroll", function() {
			var nWinTop = jWin.scrollTop();
			var nBnrOffTop = jBnr.offset().top;
			var nBnrMaxTop = jBox.offset().top + jBox.height() - jBnr.height();
			var maxPosTop = nBnrMaxTop - nFixTop + _opt.space;

			if(nWinTop >= nNavOffTop) {
				jBnr.show();
			} else {
				jBnr.hide();
			}

			if(nWinTop >= maxPosTop) {
				jBnr.css({ "position": "absolute",
						   "top": "auto",
						   "bottom": _opt.nBtm
						 });
			} else {
				jBnr.css({ "position": "fixed",
						   "top": nFixTop,
						   "bottom": "auto"
						 });
			}

			jQuery("#_my97DP").hide();
		});
	}
}


/**
 * @des: 产品详细页优化：1.右侧投保框中，模拟select选择框
 *       			   2.右侧投保框中，模拟点击页面上部投保框的选择
 * @maker: Songzairan
 * @date: 2014.10.29
 */
jQuery.fn.selectDropDown = function(opt) {
	var _opt, jBody, jCp, jCpDiv, jCpUl, jCpPriceUl, jCpLi, jCpChild, jDaySelect, jDateAll, jDateBox, jDateStart, jDateEnd, jDateBtn, jAgeClone, liArr, jBnrBox, jSel, jUl, jBnrPrice, nTemp, oTemp;

	return this.each(function() {
		_opt = jQuery.extend({
			bnrElm: this,
			selElm: ".txt_sel",
			ulElm: ".select_ul",
			childElm: " li span",
			priElm: "#txt_price",
			selectLi: ".li_selected",
			nScroll: 120
		}, opt);

		_staticInit();
		_setInit();
		_monitor();

	});

	/* static init */
	function _staticInit() {
		jBody = jQuery("body");
		jCp = jQuery(".cp_descon");
		jCpDiv = jQuery(".tb_age:not(:hidden), .paln_con:not(:hidden)", jCp);
		jCpUl = jQuery("ul:not(.price, #applicantBirthday)", jCp);
		jCpPriceUl =  jQuery("ul.price", jCp);
		jCpLi =  jQuery(_opt.selectLi, jCp);
		jCpChild = jQuery("span:not(#LiDayItemAuto, .elect_plan)", jCpUl);
		jDaySelect = jQuery("#LiDayItemAuto", jCpUl);
		jDateAll = jQuery("#UlDayBelongs", jCp);
		jDateBox = jQuery("#help_select_day", jCp);
		jDateStart = jQuery("#txtStartDay", jDateBox);
		jDateEnd = jQuery("#txtEndDay", jDateBox);
		jDateBtn = jQuery(".input_okday", jDateBox);
		jBnrBox = jQuery(".bnr_box");
		jBnrPrice = jQuery(_opt.priElm);
		liArr = jQuery(jCpLi).toArray();
		nTemp = jCpPriceUl.find("li > span")
		   				  .first()
		   				  .text()
		   				  .slice(1);
		
		jAgeClone = jQuery(".text_age").clone().removeAttr("id").addClass("txt_sel").attr({"data-min": "20452", "data-max": "28"});

	}

	/* set the value of input */
	function _setInit() {
		var _price = jCpPriceUl.find("li > span")
							   .first()
							   .html();
		jBnrPrice.html(_price);

		jCpDiv.each(function(i, v) {
			var _vEm = jQuery(v).find(".cp_title_ms").text();

			if(_vEm.match(/出生日期/) != null) _vEm = "投保年龄";

			var _vInput = jQuery(v).find("ul > .li_selected")
								   .children()
								   .text();
			var _vList = jQuery(v).children()
							  .find("span:not(.help_selec_ss, .elect_plan)");
			var _div = jQuery("<div> " +
							      "<em>" + _vEm + "</em>" +
								  "<input class='txt_sel' type='text' value='" + _vInput + "' readonly='readonly' />" +
								  "<ul class='select_ul'></ul>" +
							  "</div>").addClass("bnr_sel zIn0" + (i+1) + "");
			var _li = jQuery(_opt.ulElm, _div);

			_div.insertBefore(jBnrPrice.parent());

			for(var x=0; x<_vList.length; x++) {
				if(_vList.eq(x).children().is("a")) {
					var _txt = _vList.eq(x).html();
				} else {
					var _txt = _vList.eq(x).text();
				}

				_li.append("<li><span>" + _txt + "</span></li>");
			}

		});

		jBnrBox.hide();
		if(jAgeClone.length != 0) {
			jBnrBox.find(".ttl").after("<div class='bnr_sel birth_sel'><em>出生日期</em></div>");
			jBnrBox.find(".birth_sel").append(jAgeClone.removeClass("txt_sel")).append("<p>" + jAgeClone.attr("title") + "</p>");
		}
		jSel = jQuery(_opt.selElm);
		jUl = jQuery(_opt.ulElm);

		jAgeClone.click(function() {
			WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-'+$(this).attr("data-min")+'}',maxDate:'%y-%M-{%d-'+$(this).attr("data-max")+'}'});
		});

		jQuery(".text_age").focus(function() {
			$(this).addClass("f_acive");
		});

		jQuery(".text_age").blur(function() {
			var _val = this.value;
			jQuery(".text_age").not(this).val(_val).trigger("change");
			$(this).removeClass("f_acive");
			monitoring();
			//setTimeout(function() {console.log(jQuery(".priceC").text())}, 1000);
		});

		jQuery(window).scroll(function() {
			jQuery(".f_acive").blur();
			setTimeout(function() { jQuery(".text_age").eq(0).blur(); }, 10);
		});
	}

	/* bind event */
	function _monitor() {
		jBody.bind("click", function(e) {
			var _elm = e.target;

			if(_elm.className == _opt.selElm.slice(1)) {
				var _input = jQuery(_elm);
				var _ul = jQuery(_elm).next();
				var _child = _ul.find(_opt.childElm);
				var nUlHeight = _ul.height();
				var nUl = jQuery.inArray(_elm, jSel.toArray());
				var _jChild = jQuery("span", jCpUl.eq(nUl));

				if(_ul.css("display") == "none") {
					jUl.hide();
					if(nUlHeight >= _opt.nScroll) {
						_ul.css({ height: _opt.nScroll, "overflow-y":"auto" });
					}

					_ul.show();

					if(!_child.data("events")) {
						_child.bind("click", function(e) {
							if(jQuery(this).children().is("a")) {
								return;
							}
							var nChild = jQuery.inArray(e.target, _child.toArray());
							var _txt = jQuery(this).text();

							_jChild.not(":hidden").eq(nChild).click();
							_input.val(_txt);
							_ul.hide();
							liArr = jQuery(_opt.selectLi, jCp).toArray();
						});
					}
				} else {
					_ul.hide();
				}
			} else {
				jUl.hide();
			}
		});

		jCpChild.bind("click", function(e) {
			var _elm = e.target;
			var _par = jQuery(_elm).parent()[0];

			if(complicatedFlag == "Y") {
				if(jQuery(this).parent().attr("class") == "li_selected") return;

				jQuery(this).parent().siblings().removeClass("li_selected");
				jQuery(this).parent().addClass("li_selected");
				nTemp = jCpPriceUl.find("li > span")
		   				  .first()
		   				  .text()
		   				  .slice(1);
			}

			if(jQuery.inArray(_par, liArr) != -1) {
				return;
			}

			if(_elm == oTemp) {
				return;
			} else {
				oTemp = _elm;
			}

			var _txt = jQuery(_elm).text();
			var _ul = jQuery(_elm).parents("ul")[0];
			var nUl = jQuery.inArray(_ul, jCpUl.toArray());

			liArr = jQuery(_opt.selectLi, jCp).toArray();

			jQuery(jSel[nUl]).val(_txt);

			monitoring();
		});

		jDateBtn.bind("click", function(e) {
			var vStart = jDateStart.val("");
			var vEnd = jDateEnd.val("");
			var _date = jDaySelect.text().slice(0, -1);

			if(vStart == "" || vEnd == "") return;
			if(parseInt(_date.slice(0, 1)) == 0) return;

			liArr = jQuery(_opt.selectLi, jCp).toArray();
			oTemp = jDaySelect[0];

			var strSel = jDateAll.find("em").text();
			var nowSel = jDaySelect.parent();
			var strArr = [];

			jSel.each(function(i, v) {
				strArr[i] = jQuery(v).prev().text();
			});

			if(nowSel.attr("class") == _opt.selectLi.slice(1)) {
				/**/
				if(complicatedFlag == "Y") {
					var maxTxt = jDaySelect.parent().prev().prev().children().text();
					var maxArr = maxTxt.slice(0, -1).split("-");
					var maxNum = maxArr[maxArr.length - 1];

					if(parseInt(_date) > maxNum) {
						_date = jDaySelect.parent().prev().text();
					}
				}
				/**/
				jSel.eq(jQuery.inArray(strSel, strArr))
					.val(_date);
			}

			monitoring();
		});

	}

	/* monitor the price */
	function monitoring() {
		var oPrice = jCpPriceUl.find("li > span").eq(0);

		var outId = setTimeout(function() {
			clearInterval(intId);
		}, 8000);

		var intId = setInterval(function(){
			var oMoney = oPrice.html();
			var nPrice = parseFloat(oPrice.text().slice(1));

			if(nPrice != nTemp) {
				jBnrPrice.html(oMoney);
				nTemp = nPrice;
				clearInterval(intId);
				clearTimeout(outId);
			}
		}, 500);
	}
}


/**
 * @des: 产品详细页优化：1. 复杂产品自选保障计划产品保额变更
						 2. 复杂产品自主选择日期功能追加
						 3. 复杂产品各计划选项的责任保额变更
						 4. 复杂产品各计划选项的保额可设置默认值
						 5. 复杂产品多责任保额可级联选择。
 * @maker: Songzairan
 * @date: 2015.3.12
 */
jQuery.fn.autoShowList = function(opt) {
	if(complicatedFlag != "Y") return;

	var _opts, elmBox, elmLi, tdList, jCpPriceUl, jBnrPrice, jPlanCon, jAutoSelDay, jDateBtn, jSelLi, nTemp;

	return this.each(function() {
		_opts = jQuery.extend({
			boxElm: this,
			cldElm: "ul li",
			selElm: ".li_selected",
			listElm: "#gh_tables td.CDutyCol2",
			addClsName: "CDutyCol2"
		}, opt);

		_staticInit();
		_showList(jQuery(_opts.selElm, elmBox));
		_monitor();
	});

	/* init */
	function _staticInit() {
		elmBox = jQuery(_opts.boxElm);
		elmLi = jQuery(_opts.cldElm, elmBox);
		tdList = jQuery(_opts.listElm);
		jSelLi = jQuery(_opts.selElm, _opts.boxElm);
		jCpPriceUl =  jQuery(".cp_descon ul.price");
		jBnrPrice = jQuery("#txt_price");
		jPlanCon = jQuery(".cp_descon .paln_con > ul");
		jAutoSelDay = jQuery(".cp_descon #UlDayBelongs > ul > li > span:not(#LiDayItemAuto)");
		jDateBtn = jQuery("#help_select_day .input_okday");

		tdList.find("select").each(function(i, v) {
			v.disabled = "";
		});
	}

	/* show all list */
	function _showList(selectedLi, tempValArr) {
		var arr = [];     // 为多数组交集使用
		var strArr = [];  // 为保存JSON KEYNAME使用
		//var selectedLi = jQuery(_opts.selElm, elmBox);  // 查询当前所有已选项
		var strGroup = jQuery(_opts.selElm, jPlanCon).find("span").attr("id");  // 为判断已选的计划选项使用

		tdList.find("select, span").empty();

		selectedLi.each(function(i, v) {
			var strKey = jQuery(v).parent().attr("id") + "_" + jQuery(v).children().attr("id").split("_")[1];
			strArr.push(strKey);
		});

		for(var i=0; i<tdList.length; i++) {
			eval("var arrVal" + i +" =[]");

			for(var j=0; j<strArr.length; j++) {
				var zArr = [];  // 为保存JSON VALUE使用

				for(var x in eval("AppFacDutyRela." + strArr[j])) {
					zArr.push(eval("AppFacDutyRela." + strArr[j] + "." + x));
				}

				eval("arrVal" + i).push(zArr[i]);
			}

			var _itst = intersect(eval("arrVal" + i));

			if(_itst != null) {
				arr.push(_itst);
			} else {
				arr.push(["-"]);
			}
		}

		tdList.each(function(i, v) {
			var num = arr[i].length;
			var _id = jQuery(v).children().attr("id");
			var tempSel;

			for(var j=0; j<arr[i].length; j++) {
				if(num != 1){
					if(jQuery(v).find("select").length == 0 ){
						jQuery(v).empty()
								 .html("<select id=" + _id + "></select>");
					}

					jQuery(v).find("select")
							 .addClass(_opts.addClsName)
							 .append("<option value='" + arr[i][j] + "'>" + arr[i][j] + "</option>")
							 .unbind("change")
							 .bind("change", function() {

								nTemp = jCpPriceUl.find("li > span")
												  .first()
												  .text()
												  .slice(1);

								var _nSelId = jQuery(this).attr("id");
								var _oSelect = jQuery(v).find("select");
								var _nSelVal = _oSelect.val();
								var _nSelArr = [];

								_oSelect.find("option").each(function(i, v) {
									_nSelArr.push(jQuery(v).val());
								});

								var _index = jQuery.inArray(_nSelVal, _nSelArr);

								if(jPlanCon.length > 0) {  //当有计划选项时，设置责任选项联动
									for(var x=0; x<AppFacDutyDef[strGroup][1].length; x++){

										if(jQuery.inArray(_nSelId, AppFacDutyDef[strGroup][1][x]) != -1){
											for(var i=0; i<AppFacDutyDef[strGroup][1][x].length; i++) {
												jQuery("#" + AppFacDutyDef[strGroup][1][x][i]).find("option")[_index].selected = "selected";
											}
										}
									}
								}

								premRecal(riskCode, null, null, [_nSelId, tempSel]);
								monitoring();

								tempSel = jQuery(v).find("select").val();

							 });


				} else {
					jQuery(v).empty()
							 .html("<span id=" + _id + " class=" + _opts.addClsName + ">" + arr[i][j] + "</span>");
				}
			}

			var _mtr = jQuery(v).find("select, span")
								.attr("id")
								.slice(-2);

			if(_mtr == "01" && arr[i][0] == "不投保") {
				jQuery(v).find("select")
						 .children()[1]
						 .selected = true;
			}

			if(num != 1) {
				if(jPlanCon.length > 0) {
					jQuery(v).find("select").val(AppFacDutyDef[strGroup][0][i]);
				} else {
					jQuery(v).find("select").val(AppFacDutyDef[0][i]);
				}
			}

			tempSel = jQuery(v).find("select").val();

		});

		if(tempValArr && tempValArr.length != 0) {
			tdList.children().each(function(i, v) {
				if(jQuery(this).is("select")) {
					var valArr = jQuery(this).find("option");
					var _vArr = [];

					valArr.each(function(j, u) {
						_vArr.push(u.value);
					});

					if(jQuery.inArray(tempValArr[i], _vArr) != -1) {
						jQuery(v).val(tempValArr[i]);
					} else {
						jQuery(v).val(_vArr[0]);
					}
				}
			});
		}

	}

	/* monitor the price */
	function monitoring() {
		var oPrice = jCpPriceUl.find("li > span").eq(0);

		var outId = setTimeout(function() {
			clearInterval(intId);
		}, 8000);

		var intId = setInterval(function(){
			var oMoney = oPrice.html();
			var nPrice = parseFloat(oPrice.text().slice(1));

			if(nPrice != nTemp) {
				jBnrPrice.html(oMoney);
				nTemp = nPrice;
				clearInterval(intId);
				clearTimeout(outId);
			}
		}, 500);
	}

	/* bind event */
	function _monitor() {
		var ofactDayLi, selLiArr, _nowNum = 0;

		elmLi.children(":not(#LiDayItemAuto)").removeAttr("onclick");

		elmLi.bind("click", function(e) {
			if(jQuery(this).find("a").length != 0) return;	//判断计划选项是否链接跳转

			if(jQuery(this).find("span").attr("id") == "LiDayItemAuto") return; //判断是否点击“自主选择”日期按钮

			tdList.find("select").each(function(i, v) {  //默认select选项可用
				v.disabled = "";
			});

			if(jQuery.inArray(this, jSelLi) != -1) return;  //判断是否点击已选选项上，阻止重复发送请求

			selLiArr = jQuery(_opts.selElm, elmBox);

			if(jQuery(this).siblings().children().is("#LiDayItemAuto")) {   //点击的是保险期限栏的选项
				ofactDayLi = null;

				var dayScope = jQuery(this).text().slice(0, -1).split("-");

				if(dayScope.length == 1 && _nowNum == dayScope[0]) return;
				if(dayScope.length > 1 && _nowNum >= dayScope[0] && _nowNum <= dayScope[1]) return;
				_nowNum = 0;
			} else {   //点击的是非保险期限栏的选项
				if(ofactDayLi && _nowNum == 0) {   //自主选择日期为0天时，点击其他选项
					ofactDayLi.addClass(_opts.selElm.slice(1));
					jQuery("#LiDayItemAuto").parent().removeClass(_opts.selElm.slice(1));
					jQuery("#LiDayItemAuto").text("自主选择");
					jQuery("#help_select_day").hide();
					selLiArr = jQuery(_opts.selElm, elmBox);
				} else if(ofactDayLi && _nowNum != 0) {   //自主选择日期不为0天时，点击其他选项

					jQuery("#help_select_day").hide();
					var maxDayScope = jQuery(jAutoSelDay[jAutoSelDay.length - 2]).text().slice(0, -1).split("-");
					var selDayMax;

					if(maxDayScope.length == 1) {
						selDayMax = maxDayScope[0]
					} else if (maxDayScope.length > 1) {
						selDayMax = maxDayScope[1]
					}

					if (_nowNum > selDayMax) {
						jQuery("#LiDayItemAuto").parent().removeClass("li_selected");
						jAutoSelDay.eq(jAutoSelDay.length -1).parent().addClass("li_selected");
						selLiArr = jQuery(_opts.selElm, _opts.boxElm);
					} else {
						jAutoSelDay.each(function(i, u) {  //遍历匹配自选日期区间范围
							if(i == jAutoSelDay.length - 1) return;

							var dayScope = jQuery(u).text().slice(0, -1).split("-");

							if((dayScope.length == 1 && _nowNum == dayScope[0]) || (dayScope.length > 1 && _nowNum >= dayScope[0] && _nowNum <= dayScope[1])) {
								var selDayLi = jQuery(u).parent();
								var filterLi = jQuery(_opts.selElm, elmBox).filter(function() {
									if(jQuery(this).children().attr("id") !="LiDayItemAuto") {
										return this;
									}
								});

								selLiArr = jQuery.merge(selDayLi, filterLi);
								return false;
							}
						});
					}
				}
			}

			var tempValArr = [];

			if(jQuery(this).find("span").attr("id").match(/^Plan/g) == null) {
				tdList.children().each(function(i, v) {
					if(jQuery(this).is("select")) {
						tempValArr.push(jQuery(v).val());
					} else if(jQuery(this).is("span")) {
						tempValArr.push(jQuery(v).text());
					}
				});
			}
			_showList(selLiArr, tempValArr);
			premRecal(riskCode);
			jSelLi = jQuery(_opts.selElm, _opts.boxElm);
		});

		jQuery("#LiDayItemAuto").bind("click", function() {
			tdList.find("select").each(function(i, v) {
				v.disabled = "disabled";
			});

			ofactDayLi = jSelLi.filter(":not(.li_selected)");
		})

		jDateBtn.bind("click", function() {
			_nowNum = parseInt(jQuery("#LiDayItemAuto").text());

			tdList.find("select").each(function(i, v) {
				v.disabled = "";
			});

			if(_nowNum == 0) {
				tdList.find("select").each(function(i, v) {
					v.disabled = "disabled";
				});

				ofactDayLi = jAutoSelDay.eq(0).parent();
				return;
			}

			premRecal(riskCode);
			jSelLi = jQuery(_opts.selElm, _opts.boxElm);
		});
	}

	//多数组交集
	function intersect(arrs) {
		var arr = arrs.shift();

		//兼容IE8以下的版本
		if (typeof Array.prototype.filter != "function") {
			Array.prototype.filter = function (fn, context) {
				var arr = [];
				if (typeof fn === "function") {
				   for (var k = 0, length = this.length; k < length; k++) {
					  fn.call(context, this[k], k, this) && arr.push(this[k]);
				   }
				}
				return arr;
			};
		}

		for(var i=arrs.length; i--;) {
			var p = {
				"boolean": {},
				"number": {},
				"string": {}
				}
			var obj = [];

			arr = arr.concat(arrs[i]).filter(function (x) {
				var t = typeof x;
				return !((t in p) ? !p[t][x] && (p[t][x] = 1) : obj.indexOf(x) < 0 && obj.push(x));
			});

			if(!arr.length) return null; //发现不符合便退出
		}

		return arr;
	}
}


/**
 * @des: 产品详细页优化： 1.产品选项显隐级联
						2.兼容产品保费试算
 * @maker: Songzairan
 * @date: 2015.4.28
 */
jQuery.fn.cascadeSelect = function(opt) {

	var _opt, oParBox, oBnrList, oSelList, oInputDate, oBnrBox, oUlList, oSelLi;

	return this.each(function() {

		_opt = jQuery.extend({
			parElm: this,
			ttlElm: ".cp_title_ms",
			groElms: ".cp_yh_mes, #applicantBirthday",
			dateElm: "#inpRiskAppFactor_TextAge",
			dateText: ".up_li_width",
			dateSpan: ".tb_span_aga",
			selectLi: ".li_selected",
			bnrBox: ".bnr_box"
		}, opt);

		_staticInit();
		_monitor();

	});

	function _staticInit() {

		oParBox = jQuery(_opt.parElm);
		oBnrList = jQuery(_opt.ttlElm, oParBox).next().not(_opt.groElms.split(",")[0]);
		oSelList = jQuery(_opt.ttlElm, oParBox).next().not(_opt.groElms);
		oInputDate = jQuery(_opt.dateElm, oParBox);
		oBnrBox = jQuery(_opt.bnrBox);
		oUlList = jQuery.merge(jQuery(_opt.groElms.split(",")[1]), oSelList);
		oSelLi = jQuery(_opt.selectLi, oParBox);

	}

	function _monitor() {
		//jQuery 1.4版本的change事件IE兼容差，此处用原生事件
		oInputDate[0].onchange =  function() {

			var userAge = parseInt(getAge(oInputDate.val())); //当前投保年龄
			var oTxtAge = jQuery(this).parents("ul").next(_opt.dateText).find(_opt.dateSpan);
			var ageKey = oTxtAge.attr("name", userAge).attr("name");

			oTxtAge.html(userAge + "周岁"); //提示用户输入的年龄
			selShowList(ageKey);
			requireList();

			var _nowTxt = jQuery(this).parents(_opt.groElms.split(",")[1]).next().find("span").text();
			oBnrBox.find(".txt_sel").eq(0).val(_nowTxt);
			oBnrBox.find(".select_ul").eq(0).empty().append("<li><span>" + _nowTxt + "</span></li>");

			premRecal(jQuery(this).attr("data-id"), this, jQuery(this).attr("data-area"));

		}

		oSelList.find("span").click(function() {
			if(jQuery.inArray(jQuery(this).parent()[0], oSelLi) != -1) return;

			var selKey = jQuery(this).attr("name");

			selShowList(selKey,jQuery(this));
			requireList();
			oSelLi = jQuery(_opt.selectLi, oParBox);

		});

	}

	function selShowList(keyName,obj) {

		if(jQuery(obj).parents("li").attr("class")=='li_selected'){
			return;
		}
		oSelList.each(function(i, v) {
			//当前元素不遍历
			if(jQuery(obj).parents("ul").length>0&&jQuery(v).attr('id').indexOf(jQuery(obj).parents("ul")[0].id)!=-1){

				return true;
			}
			var _key = jQuery(v).attr("id");
			var _arr = AppFacDutyRela[keyName][_key];
			jQuery(v).children().hide();
			for(var i=0; i<_arr.length; i++) {
				jQuery(v).find("#" + _arr[i]).parent().show();
			}

			var selLi = jQuery(_opt.selectLi, v);

			if(selLi.is(":hidden")) {

				jQuery(v).children().removeClass(_opt.selectLi.slice(1));
				jQuery(v).children(":visible").first().addClass(_opt.selectLi.slice(1));

			}

		});

	}

	//计算年龄，格式化出生日期
	function getAge(birth) {

		var r = birth.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
		if (r == null) return;
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
	//修改侧边栏快速筛选的值
	function requireList() {

		var jCp = jQuery(".cp_descon");
		var jCpDiv = jQuery(".tb_age:not(:hidden), .paln_con:not(:hidden)",jCp);

		jQuery(".bnr_sel>.select_ul").empty();

		jCpDiv.each(function(i, v) {

			var _vEm = jQuery(v).find(".cp_title_ms").text();
			var _vInput = jQuery(v).find("ul > .li_selected").children().text();
			var _vList = jQuery(v).children().find("span:not(.help_selec_ss, .elect_plan,:hidden)");

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

		});

	}

}



/**
 * @des: 复杂产品详细页优化：1. 复杂产品自选保障计划产品保额变更
							 2. 复杂产品自主选择日期功能追加
							 3. 复杂产品各计划选项的责任保额变更
							 4. 复杂产品各计划选项的保额可设置默认值
							 5. 复杂产品多责任保额可级联选择。
							 6. 产品选项显隐级联
							 7. 兼容产品保费试算
 * @maker: Songzairan
 * @date: 2015.6.05
 */
jQuery.fn.complexProduct = function(opt) {

	if(complicatedFlag != "Y") return;

	var _opts, elmBox, elmLi, tdList, jCpPriceUl, jBnrPrice, jPlanCon, jAutoSelDay, jDateBtn, jSelLi, jAgeClone, nTemp, oInputDate, oSelList, oBnrBox;

	return this.each(function() {
		_opts = jQuery.extend({
			boxElm: this,
			cldElm: "ul li",
			selElm: ".li_selected",
			birElm: ".tb_span_aga",
			listElm: "#gh_tables td.CDutyCol2",
			addClsName: "CDutyCol2",
			dateElm: "#inpRiskAppFactor_TextAge",
			ttlElm: ".cp_title_ms",
			groElms: ".cp_yh_mes, #applicantBirthday",
			dateText: ".up_li_width",
			dateSpan: ".tb_span_aga",
			bnrBox: ".bnr_box"
		}, opt);

		_staticInit();
		_showList(jQuery(_opts.selElm, elmBox));
		_monitor();
	});

	/* init */
	function _staticInit() {
		elmBox = jQuery(_opts.boxElm);
		elmLi = jQuery(_opts.cldElm, elmBox);
		tdList = jQuery(_opts.listElm);
		jSelLi = jQuery(_opts.selElm, _opts.boxElm);
		jCpPriceUl =  jQuery(".cp_descon ul.price");
		jBnrPrice = jQuery("#txt_price");
		jPlanCon = jQuery(".cp_descon .paln_con > ul");
		jAutoSelDay = jQuery(".cp_descon #UlDayBelongs > ul > li > span:not(#LiDayItemAuto)");
		jDateBtn = jQuery("#help_select_day .input_okday");
		oSelList = jQuery(_opts.ttlElm, elmBox).next().not(_opts.groElms);
		oInputDate = jQuery(_opts.dateElm, elmBox);
		oBnrBox = jQuery(_opts.bnrBox);
		jAgeClone = jQuery(".text_age").clone().removeAttr("id").addClass("txt_sel");

		tdList.find("select").each(function(i, v) {
			v.disabled = "";
		});
	}

	/* show all list */
	function _showList(selectedLi, tempValArr) {
		var arr = [];     // 为多数组交集使用
		var strArr = [];  // 为保存JSON KEYNAME使用
		var selPlan = jQuery(_opts.selElm, jPlanCon);
		var strGroup = jQuery(_opts.selElm, jPlanCon).find("span").attr("id");  // 为判断已选的计划选项使用

		tdList.find("select, span").empty();

		selectedLi.each(function(i, v) {
			if(jQuery(v).find(_opts.birElm).length != 0) {

				var strKey = jQuery(v).parent().attr("id") + "_" + jQuery(v).children().attr("name").slice(0, -1);

			} else {

				var strKey = jQuery(v).parent().attr("id") + "_" + jQuery(v).children().attr("id").split("_")[1];

			}

			strArr.push(strKey);
		});

		for(var i=0; i<tdList.length; i++) {
			eval("var arrVal" + i +" =[]");

			for(var j=0; j<strArr.length; j++) {
				var zArr = [];  // 为保存JSON VALUE使用

				if(strArr[j].indexOf("_TextAge") != -1 && oInputDate.length != 0 ) {

					var _strSex = ""; //判断出生日期和性别级联数据的Key值

					jQuery("span[id^='Sex_']").each(function(i, v) {

						if (jQuery(this).parent().hasClass("li_selected")) {

							_strSex = jQuery(this).attr("id");

						}

					});

					var _dutyArr = eval("AppFacDutyRela." + strArr[j] + "_" + _strSex + ".dutyList");

				} else if( strArr[j].indexOf("_Sex") != -1 && oInputDate.length != 0) {
					var _strAge = jQuery("#TextAge_0").text().slice(0, -2);


					var _dutyArr = eval("AppFacDutyRela." + strArr[j] + "_TextAge_" + _strAge + ".dutyList");

				} else {

					var _dutyArr = eval("AppFacDutyRela." + strArr[j] + ".dutyList");


				}

				for(var k=0; k<_dutyArr.length; k++) {

					zArr.push(_dutyArr[k]);

				}

				eval("arrVal" + i).push(zArr[i]);

			}

			var _itst = intersect(eval("arrVal" + i));

			if(_itst != null) {
				arr.push(_itst);
			} else {
				arr.push(["-"]);
			}
		}

		tdList.each(function(i, v) {
			var num = arr[i].length;
			var _id = jQuery(v).children().attr("id");
			var tempSel;

			for(var j=0; j<arr[i].length; j++) {
				if(num != 1){
					if(jQuery(v).find("select").length == 0 ){
						jQuery(v).empty()
								 .html("<select id=" + _id + "></select>");
					}

					jQuery(v).find("select")
							 .addClass(_opts.addClsName)
							 .append("<option value='" + arr[i][j] + "'>" + arr[i][j] + "</option>")
							 .unbind("change")
							 .bind("change", function() {

								nTemp = jCpPriceUl.find("li > span")
												  .first()
												  .text()
												  .slice(1);

								var _nSelId = jQuery(this).attr("id");
								var _oSelect = jQuery(v).find("select");
								var _nSelVal = _oSelect.val();
								var _nSelArr = [];

								_oSelect.find("option").each(function(i, v) {
									_nSelArr.push(jQuery(v).val());
								});

								var _index = jQuery.inArray(_nSelVal, _nSelArr);

								/** begin 责任选项联动 **/
								var _casArr;

								if(jPlanCon.length > 0) {
									_casArr = AppFacDutyDef[strGroup][1];
								} else {
									_casArr = AppFacDutyDef["default"][1];
								}

								for(var x=0; x<_casArr.length; x++){

									if(jQuery.inArray(_nSelId, _casArr[x]) != -1){
										for(var i=0; i<_casArr[x].length; i++) {
											jQuery("#" + _casArr[x][i]).find("option")[_index].selected = "selected";
										}
									}

								}
								/** end 责任选项联动 **/

								premRecal(riskCode, null, null, [_nSelId, tempSel]);
								monitoring();

								tempSel = jQuery(v).find("select").val();

							 });


				} else {
					jQuery(v).empty()
							 .html("<span id=" + _id + " class=" + _opts.addClsName + ">" + arr[i][j] + "</span>");
				}
			}

			var _mtr = jQuery(v).find("select, span")
								.attr("id")
								.slice(-2);

			if(_mtr == "01" && arr[i][0] == "不投保") {
				jQuery(v).find("select")
						 .children()[1]
						 .selected = true;
			}

			/**
				责任选项默认值
			**/
			if(num != 1) {
				if(jPlanCon.length > 0) {
					jQuery(v).find("select").val(AppFacDutyDef[strGroup][0][i]);
				} else {
					jQuery(v).find("select").val(AppFacDutyDef["default"][0][i]);
				}
			}

			tempSel = jQuery(v).find("select").val();

		});

		if(tempValArr && tempValArr.length != 0) {
			tdList.children().each(function(i, v) {
				if(jQuery(this).is("select")) {
					var valArr = jQuery(this).find("option");
					var _vArr = [];

					valArr.each(function(j, u) {
						_vArr.push(u.value);
					});

					if(jQuery.inArray(tempValArr[i], _vArr) != -1) {
						jQuery(v).val(tempValArr[i]);
					} else {
						jQuery(v).val(_vArr[0]);
					}
				}
			});
		}

	}

	/* monitor the price */
	function monitoring() {
		var oPrice = jCpPriceUl.find("li > span").eq(0);

		var outId = setTimeout(function() {
			clearInterval(intId);
		}, 8000);

		var intId = setInterval(function(){
			var oMoney = oPrice.html();
			var nPrice = parseFloat(oPrice.text().slice(1));

			if(nPrice != nTemp) {
				jBnrPrice.html(oMoney);
				nTemp = nPrice;
				clearInterval(intId);
				clearTimeout(outId);
			}
		}, 500);
	}

	/* bind event */
	function _monitor() {
		var ofactDayLi, selLiArr, _nowNum = 0;

		elmLi.not("#birthday_li_id").children(":not(#LiDayItemAuto)").removeAttr("onclick");

		elmLi.bind("click", function(e) {
			if(jQuery(this).find("a").length != 0) return;	//判断计划选项是否链接跳转

			if(jQuery(this).find("span").attr("id") == "LiDayItemAuto") return; //判断是否点击“自主选择”日期按钮

			tdList.find("select").each(function(i, v) {  //默认select选项可用
				v.disabled = "";
			});

			if(jQuery.inArray(this, jSelLi) != -1) return;  //判断是否点击已选选项上，阻止重复发送请求

			selLiArr = jQuery(_opts.selElm, elmBox);

			if(jQuery(this).siblings().children().is("#LiDayItemAuto")) {   //点击的是保险期限栏的选项
				ofactDayLi = null;

				var dayScope = jQuery(this).text().slice(0, -1).split("-");

				if(dayScope.length == 1 && _nowNum == dayScope[0]) return;
				if(dayScope.length > 1 && _nowNum >= dayScope[0] && _nowNum <= dayScope[1]) return;
				_nowNum = 0;
			} else {   //点击的是非保险期限栏的选项
				if(ofactDayLi && _nowNum == 0) {   //自主选择日期为0天时，点击其他选项
					ofactDayLi.addClass(_opts.selElm.slice(1));
					jQuery("#LiDayItemAuto").parent().removeClass(_opts.selElm.slice(1));
					jQuery("#LiDayItemAuto").text("自主选择");
					jQuery("#help_select_day").hide();
					selLiArr = jQuery(_opts.selElm, elmBox);
				} else if(ofactDayLi && _nowNum != 0) {   //自主选择日期不为0天时，点击其他选项
					jQuery("#help_select_day").hide();
					var maxDayScope = jQuery(jAutoSelDay[jAutoSelDay.length - 2]).text().slice(0, -1).split("-");
					var selDayMax;

					if(maxDayScope.length == 1) {
						selDayMax = maxDayScope[0]
					} else if (maxDayScope.length > 1) {
						selDayMax = maxDayScope[1]
					}

					if (_nowNum > selDayMax) {
						jQuery("#LiDayItemAuto").parent().removeClass("li_selected");
						jAutoSelDay.eq(jAutoSelDay.length -1).parent().addClass("li_selected");
						selLiArr = jQuery(_opts.selElm, _opts.boxElm);
					} else {
						jAutoSelDay.each(function(i, u) {  //遍历匹配自选日期区间范围
							if(i == jAutoSelDay.length - 1) return;

							var dayScope = jQuery(u).text().slice(0, -1).split("-");

							if((dayScope.length == 1 && _nowNum == dayScope[0]) || (dayScope.length > 1 && _nowNum >= dayScope[0] && _nowNum <= dayScope[1])) {
								var selDayLi = jQuery(u).parent();
								var filterLi = jQuery(_opts.selElm, elmBox).filter(function() {
									if(jQuery(this).children().attr("id") !="LiDayItemAuto") {
										return this;
									}
								});

								selLiArr = jQuery.merge(selDayLi, filterLi);
								return false;
							}
						});
					}
				}
			}

			var tempValArr = [];

			if(jQuery(this).find("span").attr("id").match(/^Plan/g) == null) {
				tdList.children().each(function(i, v) {
					if(jQuery(this).is("select")) {
						tempValArr.push(jQuery(v).val());
					} else if(jQuery(this).is("span")) {
						tempValArr.push(jQuery(v).text());
					}
				});
			}
			
			_showList(selLiArr, tempValArr);	
			
			var selKey = jQuery(this).children().attr("id").replace(/[^0-9]/ig, "");


			selShowList(selKey, this);
			requireList();
			premRecal(riskCode);
			
		});
		
		jQuery("#LiDayItemAuto").bind("click", function() { 
			tdList.find("select").each(function(i, v) {
				v.disabled = "disabled";
			});
			
			ofactDayLi = jSelLi.filter(":not(.li_selected)");
		})
		
		jDateBtn.bind("click", function() {
			_nowNum = parseInt(jQuery("#LiDayItemAuto").text());
			
			tdList.find("select").each(function(i, v) {
				v.disabled = "";
			});
			
			if(_nowNum == 0) {
				tdList.find("select").each(function(i, v) {
					v.disabled = "disabled";
				});
				
				ofactDayLi = jAutoSelDay.eq(0).parent();
				return;
			}
						
			premRecal(riskCode);
			jSelLi = jQuery(_opts.selElm, _opts.boxElm);
		});
		
		//jQuery 1.4版本的change事件IE兼容差，此处用原生事件
		oInputDate[0].onchange =  function() {
			
			var userAge = parseInt(getAge(oInputDate.val())); //当前投保年龄
			var oTxtAge = jQuery(this).parents("ul").next(_opts.dateText).find(_opts.dateSpan);
			var _strSex = ""; //判断出生日期和性别级联数据的Key值
					
			jQuery("span[id^='Sex_']").each(function(i, v) {  
				
				if (jQuery(this).parent().hasClass("li_selected")) { 
					
					_strSex = jQuery(this).attr("id");
					
				} 
					
			});
			
			var ageKey = oTxtAge.attr("name", userAge + "Y").attr("name").slice(0, -1) + "_" + _strSex;
			var tempValArr = [];
			oTxtAge.html(userAge + "周岁"); //提示用户输入的年龄
			
			selShowList(ageKey, this);
			requireList();
			
			tdList.children().each(function(i, v) {
				
				if(jQuery(this).is("select")) {
					tempValArr.push(jQuery(v).val());
				} else if(jQuery(this).is("span")) {
					tempValArr.push(jQuery(v).text());
				}
				
			});
			
			_showList(jQuery(_opts.selElm, _opts.boxElm), tempValArr);

			
			premRecal(jQuery(this).attr("data-id"), this, jQuery(this).attr("data-area"));
			
		}

	}
	
	function selShowList(keyName, _this) {
		
		var _key;
		var _key2;  // 长期险勾选保险期限和缴费年期时，需要和年龄、性别组成的联合主键共同控制投保要素的显隐(取交集)
		
		if(jQuery(_this).parent().attr("id") == "birthday_li_id") {
			_key = jQuery(_this).attr("data-area") + "_" + keyName;
		} else {
			if(jQuery(_this).children().attr("id").indexOf("Sex_") != -1 && jQuery("li#birthday_li_id").length > 0) {
					var _strAge = jQuery("#TextAge_0").text().slice(0, -2);

					_key = jQuery(_this).parents("ul").attr("id") + "_" + keyName + "_TextAge_" + _strAge;

			} else {
				_key = jQuery(_this).parents("ul").attr("id") + "_" + keyName;
				
				if ((jQuery(_this).children().attr("id").indexOf("Period_") != -1 || jQuery(_this).children().attr("id").indexOf("FeeYear_") != -1)
					|| jQuery(_this).children().attr("id").indexOf("Rule02_") != -1 && jQuery("li#birthday_li_id").length > 0) {
					
					var _strSex;
					jQuery("span[id^='Sex_']").each(function(i, v) {  
						if (jQuery(this).parent().hasClass("li_selected")) { 
							_strSex = jQuery(this).attr("id");
						} 
					});
					if (jQuery("#inpRiskAppFactor_TextAge").length > 0) {
						_key2 = jQuery("#inpRiskAppFactor_TextAge").attr("data-area") + "_" + jQuery("#TextAge_0").text().slice(0, -2) + "_" + _strSex;
					}
				}
				
			}
		}
		
		if(!AppFacDutyRela[_key] && _key.indexOf("TextAge")) {  // 当某年龄段只有一种性别可选时，自动切换可选的性别上。
			if(_key.indexOf("Sex_0")) {
				_key = _key.replace("Sex_0", "Sex_1");		
			} else if (_key.indexOf("Sex_1")) {
				_key = _key.replace("Sex_1", "Sex_0");
			}
		}
		
		var _selObj = AppFacDutyRela[_key].cascade;
		
		// 长期险勾选保险期限和缴费年期时，需要和年龄、性别组成的联合主键共同控制投保要素的显隐(取交集)
		if(_key2) {
			
			var _selObj2 = AppFacDutyRela[_key2].cascade;
			var _result = {};
			
			for(var x in _selObj) {
				
				var _tArr = intersect([_selObj[x], _selObj2[x]]);
				
				_result[x] = _tArr;
			}
			
			_selObj = _result;
		}
		
		oSelList.not("#" + jQuery(_this).parents("ul").attr("id")).children().hide();
		
		for(var x in _selObj) {
			
			var _showArr = _selObj[x];
			
			for(var i=0; i<_showArr.length; i++) {

				jQuery("#" + _showArr[i]).parent().show();
				
			}

			var selLi = jQuery("#" + x).find(_opts.selElm);
			
			if(selLi.is(":hidden")) {
				
				selLi.removeClass(_opts.selElm.slice(1));
				jQuery("#" + x).children(":visible").first().addClass(_opts.selElm.slice(1));
				
			}
			
		}
		
		jSelLi = jQuery(_opts.selElm, _opts.boxElm);
		
	}

	//修改侧边栏快速筛选的值
	function requireList() {
		
		var jCp = jQuery(".cp_descon"); 
		var jCpDiv = jQuery(".tb_age:not(:hidden), .paln_con:not(:hidden)", jCp);
		var _vArr = jQuery();
		jQuery(".bnr_sel>.select_ul").empty();
		
		jCpDiv.each(function(i, v) {
			
			var _vEm = jQuery(v).find(".cp_title_ms").text();
			
			var _vInput = jQuery(v).find("ul > .li_selected").children().text();
			
			var _vList = jQuery(v).children().find("span:not(.help_selec_ss, .elect_plan,:hidden)");
			
			for (var x = 0; x < _vList.length; x++) {
				
				if (_vList.eq(x).children().is("a")) {
					var _txt = _vList.eq(x).html();
				} else {
					var _txt = _vList.eq(x).text();
				}
				
				jQuery(".zIn0" + (i + 1) + "> .select_ul").append(
						"<li><span>" + _txt + "</span></li>");

			}
			
			jQuery(".zIn0" + (i + 1) + "> .txt_sel").val(_vInput);
		});

	}
	
	//计算年龄，格式化出生日期
	function getAge(birth) {
	
		var r = birth.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
		if (r == null) return;
		var d = new Date(r[1], r[3] - 1, r[4]);
		var date = GetNowDateAdd(1);
		if(specialPremCalFlag == '1'){
			date = GetNowDateAdd(0);
		}

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
	
	//多数组交集
	function intersect(arrs) {
		var arr = arrs.shift();
		
		//兼容IE8以下的版本
		if (typeof Array.prototype.filter != "function") {
			Array.prototype.filter = function (fn, context) {
				var arr = [];
				if (typeof fn === "function") {
				   for (var k = 0, length = this.length; k < length; k++) {
					  fn.call(context, this[k], k, this) && arr.push(this[k]);
				   }
				}
				return arr;
			};
		}
		
		for(var i=arrs.length; i--;) {
			var p = {
				"boolean": {}, 
				"number": {}, 
				"string": {}
				}
			var obj = [];
			
			arr = arr.concat(arrs[i]).filter(function (x) {
				var t = typeof x;
				return !((t in p) ? !p[t][x] && (p[t][x] = 1) : obj.indexOf(x) < 0 && obj.push(x));
			});
			
			if(!arr.length) return null; //发现不符合便退出
		}
		
		return arr;
	}
}

function activityCoupon(activitysn) {
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/member_send_coupon!sendCouponDeal.action?activitysn='+ activitysn,
        crossDomain: true,
        dataType: "jsonp",
		jsonp: "callback",
		async: true,
		success: function(data) {
			if (data.status == 'notLogin') {
				art.dialog.data('base', sinosoft.base);
				art.dialog.data('front', sinosoft.front);
				art.dialog.open(sinosoft.front+'/block/art_login.shtml', {id: 'loginArtWindow',title: '您尚未登录',width:'338px', height:'480px',fixed:true}, false);
			}else {
				art.dialog({
					content : data.message,
					id : 'error_id',
					title : '提示'
				});
			}
		}
	});
}

/** 增加理财现份数购买 */
jQuery(function() {

	var oPerNum = jQuery(".shop_oknum > .number");
	var oMinusBtn = jQuery(".shop_oknum > .minus");
	var oAddBtn = jQuery(".shop_oknum > .add");
	var oPrice = jQuery(".price .priceC span");
	var oPrice2 = jQuery("#txt_price");
	var rPrice = jQuery(jQuery(".price .priceB span").get(1));
	var sTotal = jQuery(".shop_oknum > .reperoty > b").text();
	var nTotal = parseInt(sTotal);

	// 手动输入购买份数
	oPerNum.blur(function() {
		var _num = oPerNum.val();
		if (_num == "" || isNaN(_num)) {
			oPerNum.val(1);
			_num = 1;
		}
		if (_num <= nTotal && _num != 0) {
			var sMoney = jQuery("#cp-zprcie").val();
			var rMoney = jQuery("#cp-yprcie").val();
			var _money = parseFloat(sMoney);
			var _rmoney = parseFloat(rMoney);
			var _price = Math.round((_money * (_num)) * 100) / 100;
			var _yprice = Math.round((_rmoney * (_num)) * 100) / 100;
			oPrice.html("<em>￥</em>" + _price.toFixed(2));
			rPrice.html("<em>￥</em>" + _yprice.toFixed(2));
			oPrice2.html("<em>￥</em>" + _price.toFixed(2));

			if (_num == 1) {
				oMinusBtn.addClass("disabled");
				oAddBtn.removeClass("disabled");
			} else if (_num == nTotal) {
				oAddBtn.addClass("disabled");
				oMinusBtn.removeClass("disabled");
			} else {
				oAddBtn.removeClass("disabled");
				oMinusBtn.removeClass("disabled");
			}
		} else {
			var sMoney = jQuery("#cp-zprcie").val();
			var rMoney = jQuery("#cp-yprcie").val();
			var _money = parseFloat(sMoney);
			var _rmoney = parseFloat(rMoney);
			var _price = Math.round(_money * 100) / 100;
			var _yprice = Math.round(_rmoney * 100) / 100;
			// oPerNum.val(1);
			oPrice.html("<em>￥</em>" + _price.toFixed(2));
			rPrice.html("<em>￥</em>" + _yprice.toFixed(2));
			oPrice2.html("<em>￥</em>" + _price.toFixed(2));

			oMinusBtn.addClass("disabled");
			oAddBtn.removeClass("disabled");

			art.dialog({
				icon : 'error',
				content : '您输入的份数有误，只能购买1至' + nTotal + '份',
				id : 'errorid'
			});
		}

	})
	// 增加购买份数
	oAddBtn.bind("click", function() {

		var sMoney = jQuery("#cp-zprcie").val();
		var rMoney = jQuery("#cp-yprcie").val();
		var _money = parseFloat(sMoney);
		var _rmoney = parseFloat(rMoney);
		var _num = parseFloat(oPerNum.val());
		if (_num >= 1 && _num < nTotal) {
			var _price = Math.round((_money * (_num + 1)) * 100) / 100;
			var _yprice = Math.round((_rmoney * (_num + 1)) * 100) / 100;
			oPerNum.val(_num + 1);
			oPrice.html("<em>￥</em>" + _price.toFixed(2));
			rPrice.html("<em>￥</em>" + _yprice.toFixed(2));
			oPrice2.html("<em>￥</em>" + _price.toFixed(2));

			if ((_num + 1) == nTotal)
				jQuery(this).addClass("disabled");
		} else {
			art.dialog({
				icon : 'error',
				content : '您输入的份数有误，只能购买1至' + nTotal + '份',
				id : 'errorid'
			});
		}

		oMinusBtn.removeClass("disabled");
	});

	// 减少购买份数
	oMinusBtn.bind("click", function() {
		var sMoney = jQuery("#cp-zprcie").val();
		var rMoney = jQuery("#cp-yprcie").val();
		var _money = parseFloat(sMoney);
		var _rmoney = parseFloat(rMoney);
		var _num = parseFloat(oPerNum.val());

		if (_num > 1 && _num <= nTotal) {
			var _price = Math.round((_money * (_num - 1)) * 100) / 100;
			var _yprice = Math.round((_rmoney * (_num - 1)) * 100) / 100;
			oPerNum.val(_num - 1);
			oPrice.html("<em>￥</em>" + _price.toFixed(2));
			rPrice.html("<em>￥</em>" + _yprice.toFixed(2));
			oPrice2.html("<em>￥</em>" + _price.toFixed(2));
			if ((_num - 1) == 1)
				oMinusBtn.addClass("disabled");
		} else {
			art.dialog({
				icon : 'error',
				content : '您输入的份数有误，只能购买1至' + nTotal + '份',
				id : 'errorid2'
			});
		}

		oAddBtn.removeClass("disabled");
	});

});

jQuery(document).ready(function($) {
		jQuery("#fun_ppsp").taghoverbind();
});

/** 
 * @des: 点击现金价值计算跳转到现金价值计算页面
 * @maker: guozc
 * @date: 2017.08.04
 */
function goCashValuePage(){
	// 取得保费试算元素默认值
	var defaultVal = "";
	var selectedLi = jQuery(".bz_time .li_selected");
	var duty1 = '';
	var duty2 = '';
	var planVal = '';
	selectedLi.each(function(i, v) {
		var _key, _val;
		_val = jQuery(v).children().attr("name");
		_key = jQuery(v).parent().attr("id");
		defaultVal += _key+"-"+_val + ",";
	});
	sessionStorage.setItem('CASH_VALUE_DEF',defaultVal);
	sessionStorage.setItem('CASH_VALUE_BIRTHDAY',$("#inpRiskAppFactor_TextAge").val());
	// window.location.href = "cashvalue.shtml";
	window.open("cashvalue.shtml");
}


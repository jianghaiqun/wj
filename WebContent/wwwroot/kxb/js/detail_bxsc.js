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
	queryProductStateLoad();// 通过ID 获取该产品状态
	jQuery("#gh_tables tr").mouseover(function() {
		jQuery(this).addClass("row0");
	}).mouseout(function() {
		jQuery(this).removeClass("row0"); 
	});
	//解决销量失效问题
	salesVolumeLoad();
	informLoad();
	searchProductListAvtivity();
	weixinFun();
	cpsFun();
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
	
});

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
	jQuery("#LiDayItemAuto").html(diffDays + '天▼');
	if (diffDays > 0) {
		jQuery("#help_select_day").hide();
		//premDocal(riskcode);
	}
}
function Dayclear() {
	jQuery("#UlDayBelongs").find(".LiDayItemAuto").parent("li").each(
			function() {
				jQuery(this).removeClass("li_selected");
			});
	jQuery("#help_select_day").hide();
	jQuery('#LiDayItemAuto').html("自主选择▼");
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
	jQuery('#LiDayItemAuto').html("自主选择▼");
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
	if(document.getElementById("count")){
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var result = xmlhttp.responseText.split(",");
				document.getElementById("count").innerHTML = "客户评价("
						+ result[0] + ")";
				document.getElementById("commentCount").innerHTML = result[0];
				document.getElementById("shopComment").innerHTML = result[1];
				document.getElementById("commLoad").innerHTML = "";
			}
		}
		xmlhttp.open("GET", "/wj/query/counter?url=" + window.location.href, true);
		xmlhttp.send();
		blurcount();
	}
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
				document.getElementById(index).innerHTML="销量："+object;
			});
		}
	});
	// 产品详细页活动展示
	var cpsUserId = jQuery.cookie('cpsUserId');
	var channelsn = "wj";
	if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
		channelsn = "cps";
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
				});
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
				jQuery(".button_b1").css("display","none");// 隐藏立即购买
				
				jQuery(".error_btn").css("display","block");// 显示下架按钮
				
				jQuery("#InContrast").css("display","none");// 隐藏加入对比
				
				jQuery("#content").css("display","none");// 隐藏宝贝试算、日期、价格等.

				jQuery(".share_link").css("display","none");// 隐藏加入收藏、分享

				if(data.product != ""){
					jQuery("#error_div").css("display","block");
					jQuery("#ajaxProduct").html(data.product);
				}
				
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
	if(typeof(cpsUserId)!="undefined" && cpsUserId!=null && cpsUserId!=""){
		channelsn = "cps";
	}
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/sales_volume!searchProductListAvtivity.action?productId='+id+'&channelSn='+channelsn+"&detailFlag=true",
		dataType: "json",
		async: true,
		success: function(data) {
			jQuery.each(data, function(index, object) {
				index=index.substring(0,index.indexOf("@"));
				document.getElementById(index).innerHTML=object;
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
    	if(!document.getElementById(star)){
    		return ;
    	}
        this.SetOptions(options); //默认属性
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
		});
	}
}


/** 
 * @des: 产品详细页优化：1.右侧投保框中，模拟select选择框
 *       				 2.右侧投保框中，模拟点击页面上部投保框的选择
 * @maker: Songzairan
 * @date: 2014.10.29
 */
jQuery.fn.selectDropDown = function(opt) {
	var jBody, jCp, jCpDiv, jCpUl, jCpPriceUl, jCpLi, jCpChild, jDaySelect, jDateAll, jDateBox, jDateStart, jDateEnd, jDateBtn, liArr, jBnrBox, jSel, jUl, jBnrPrice, nTemp, oTemp;

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
		jCpUl = jQuery("ul:not(.price)", jCp);
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
	}

	/* set the value of input */		
	function _setInit() {
		var _price = jCpPriceUl.find("li > span")
							   .first()
							   .html();
		jBnrPrice.html(_price);

		jCpDiv.each(function(i, v) {
			var _vEm = jQuery(v).find(".cp_title_ms").text();
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
		jSel = jQuery(_opt.selElm);
		jUl = jQuery(_opt.ulElm);
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
							
							_jChild.eq(nChild).click();							
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
	
	var _opts, elmBox, elmLi, tdList, jCpPriceUl, jBnrPrice, jPlanCon, jAutoSelDay, jDateBtn, jDateStart, jDateEnd, jSelLi, nTemp;

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
		jDateStart = jQuery("#help_select_day #txtStartDay");
		jDateEnd = jQuery("#help_select_day #txtEndDay");
		
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
			
			jQuery(this).siblings().removeClass(_opts.selElm.slice(1));
			jQuery(this).addClass(_opts.selElm.slice(1));
			
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
					jQuery("#LiDayItemAuto").text("自主选择▼");
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
			jDateStart.val("");
			jDateEnd.val("");
			
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


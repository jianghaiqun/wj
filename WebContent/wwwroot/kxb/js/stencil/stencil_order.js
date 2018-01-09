  var v=true;
  var v1=true;
  var v2=true;
  var v3=true;
  var ordernum = 0; //预约人数初始化

  //判断是否引入某个js
  function isInclude(name){
      var js= /js$/i.test(name);
  var es=document.getElementsByTagName(js?'script':'link');
  for(var i=0;i<es.length;i++) 
  if(es[i][js?'src':'href'].indexOf(name)!=-1)return true;
      return false;
  }

  //动态引入artdialog插件 写入避免代码冗余
  if(isInclude("default.css")!=true){
   document.write("<link rel='stylesheet' type='text/css' href='http://resource.kaixinbao.com/style/skins/default.css'/>");
  }
  if(isInclude("artDialog.js")!=true){
   document.write("<script src='http://resource.kaixinbao.com/js/artDialog.js'><\/script>");
  }
  
// 电话验证
function TelValidate(value) {
    if (/^13\d{9}$/g.test(value) || (/^15[0-9]\d{8}$/g.test(value)) || (/^18[0-9]\d{8}$/g.test(value)) || (/^147\d{8}$/g.test(value)) || (/^145\d{8}$/g.test(value))) { return true; }else{ return false;}
}

function validName() {
  if(jQuery("#txtName").val().length<=0) {
  v=false;
  }
else {
  var r=/[^\u4E00-\u9FA5]/;
  if(r.test(jQuery("#txtName").val()) || jQuery("#txtName").val().length<=1 || jQuery("#txtName").val().length>10) {
  v1=false;
  }

}
}function validPhone() {
  if(TelValidate(jQuery("#txtPhone").val())==false) {
  v2=false;
}

}

function validEmail() {
var reg=/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
  if(!reg.test(jQuery("#txtEmail").val())){
   v3 = false;
  }
}

function sub() {
	v = v1 = v2 = v3 = true;
	validName();
	validEmail();
	validPhone();
  
	if (v == false) {
		alert("请输入姓名");
		return;
	} else if (v1 == false) {
		alert("姓名请输入2~10位的汉字");
		return;
	} else if (v3 == false) {
		alert("请输入正确的邮箱");
		return;
	} else if (v2 == false) {
		alert("请输入正确手机号码");
		return;
	}

	jQuery.ajax({
		url : sinosoft.base + "/shop/shopping_cart!saveInfo.action",
		data : {
			preName : jQuery("#txtName").val(),
			prePhone : jQuery("#txtPhone").val(),
			preSex : jQuery(":radio[name='sex']:checked").val(),
			preEmail : jQuery("#txtEmail").val(),
			remark : jQuery("#txtMessage").val()
		},
		async : true,
		dataType : "json",
		type : "POST",
		success : function(data) {
			if (data.status == "Y") {
				jQuery("#txtName").val("");
				jQuery("#txtPhone").val("");
				jQuery("#txtEmail").val("");
				jQuery("#txtMessage").val("");
				VL_Send(data.WXID);
				ordernum += 1;
				jQuery("#oreder_num").text(ordernum);
				
				if (data.login == "Y") {
					art.dialog({
						title:'预约消息',
						content: '<p class="order-yesmessage"><span class="order-yes-tit">您已经预约成功</span>赠送您一张优惠券，您可以登录<a href="' + sinosoft.base + '/shop/coupon!queryCoupon.action" target="_blank">会员中心-优惠券</a>页面查看使用</p>',
						ok: function() {}
					});
				} else {
					art.dialog({
						title:'预约消息',
						content: '<p class="order-yesmessage"><span class="order-yes-tit">您已经预约成功</span>赠送您一张优惠券 ' + data.couponSn + ' ，您需要注册登陆后 <br />在<a href="' + sinosoft.base + '/shop/coupon!queryCoupon.action" target="_blank">会员中心-优惠券</a>页面激活使用</p>',
						ok: function() {}
					});
				}
				
			} else {
				art.dialog({
					title:'预约消息',
					width: 320,
					content: '<p class="order-yesmessage"><span class="order-yes-tit">' + data.errMsg + '</span></p>',
					ok: function() {}
				});
			}
		}
	});
}

var order =['<div class="order_box ">',
'             <h3 class="order_tit">免费预约！<span>定制专属保险方案</span><em class="order-tii-des">预约成功立即送优惠券<i class="order-tii-icon"></i></em></h3>',
'             <table class="order_table">',
'               <tr>',
'                 <td class="order_th"><em>*</em>姓名：</td>',
'                 <td colspan="2"><input type="text" id="txtName" tabindex="1" class="order_td"/></td>',
'                 <td></td>',
'               </tr>',
'               <tr>',
'                 <td class="order_th"><em>*</em>称呼：</td>',
'                 <td class="radio_txt"><label>',
'                 <input type="radio" name="sex" tabindex="2"  checked="checked" value="女士" id="sex_0">',
'                 女士</label></td>',
'                 <td class="radio_txt">  <label>',
'                 <input type="radio" name="sex" tabindex="3" value="先生" id="sex_1">',
'                 先生</label></td>',
'               </tr>',
'               <tr>',
'                 <td class="order_th"><em>*</em>邮箱：</td>',
'                 <td colspan="2"><input type="text" id="txtEmail"  tabindex="4" class="order_td" /></td>',
'                 <td></td>',
'               </tr>',
'               <tr>',
'                 <td class="order_th"><em>*</em>手机号码：</td>',
'                 <td colspan="2"><input type="text" id="txtPhone" tabindex="5" class="order_td" /></td>',
'                 <td></td>',
'               </tr>',
'               <tr>',
'                 <td class="order_th order_th_text">留言：</td>',
'                 <td colspan="2"><textarea type="text" id="txtMessage" tabindex="6" class="order_Message">您有哪方面的需求请留言给我们</textarea></td>',
'                 <td></td>',
'               </tr>',
'               <tr>',
'                 <td colspan="3"><p class="order-psc">现有<span class="order-numsc" id="oreder_num">0</span>人已经预约定制专属保险方案</p></td>',
'                 <td></td>',
'               </tr>',
'               <tr>',
'                 <td></td>',
'                 <td colspan="2"><input type="button" class="order_btns" tabindex="7" onclick="sub()" /></td>',
'               </tr>',
'             </table>',
'           </div>'].join("");

jQuery("#order_con").append(order);

jQuery("#txtMessage").focus(function() {
	var tip =  jQuery("#txtMessage").val();
	if (tip=='您有哪方面的需求请留言给我们') {
		jQuery(this).val("");
	};
});
jQuery("#txtMessage").blur(function() {
	var tip = jQuery("#txtMessage").val();
	if (tip == "") {
		jQuery(this).val("您有哪方面的需求请留言给我们");
	}
});

// 获取预约人数
jQuery.ajax({
	url : sinosoft.base + "/shop/shopping_cart!getOrderNum.action",
	async : true,
	dataType : "json",
	type : "POST",
	success : function(data) {
		if (data.status == "Y") {
			ordernum = data.orderNum;
			jQuery("#oreder_num").text(ordernum);
		}
	}
});

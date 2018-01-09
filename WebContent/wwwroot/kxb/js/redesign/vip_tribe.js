/*
 *@des:输入提示js
 *@maker:guodongqi
 *@time:2016-1-4
 */
function sendEmail() {
    var emails = jQuery(".email_list input");
    var str = ""
    var flag = true;
    emails.each(function() {
        if (jQuery(this).val() != '填写您的好友邮件地址即可') {
            if (!verifyElement('电子邮箱|NOTNULL&RECOEMAIL', jQuery(this).val(), this.id)) {
                flag = false;
            } else {
                str += jQuery(this).val() + ";";
            }
        }
    });

    if (!flag) {
        return;    }

    if (str == '') {
        alert("请输入电子邮箱!");
        return;
    }
    str = str.substr(0, str.length - 1);

    art.dialog({
        id: 'artRecommend',
        title: '提示',
        width: 200,
        content: '正在发送...',
        ok : function(){
        	this.close();
        },
	    cancel: false
    });

    jQuery.ajax({
        type: "POST",
        url: sinosoft.base + '/shop/member_recommend!sendMail.action?emailAdress=' + str + '&title=' + jQuery("#recommTitle").val() + '&recommUrl=' + jQuery("#recommUrl").val(),
        dataType: "json",
        async: true,
        beforeSend:function() {
        	art.dialog.list['emaillist'].close();
        },
        success: function(data) {
            art.dialog.list['artRecommend'].content(data.msg);
        }
    });
}

jQuery('#vip_mes div.change_mesdiv').soChange({
    thumbObj: '#vip_mes .ul_change_ems span',
    thumbNowClass: 'on',
    slideTime: 0
});

jQuery(".tribe-list-dl").taghoverbind({
    showClass: ".vipTribeicons"
});
jQuery(".vip_action").taghoverbind({
    showClass: ".vip_ctions_des"
});
jQuery(".tribe-vipUl li").taghoverbind({
    showClass: ".vip_ctions_des"
});

jQuery(document).ready(function() {
    if (jQuery(".vipvip").length <= 0) {
        jQuery(".vip-vip").taghoverbind({
            showClass: ".vip-vip-des"
        });
    }

    var isIE = 0 /* @cc_on+1@ */ ;

    if (!isIE) {

        var clip = new ZeroClipboard(jQuery(".copyLink"), {
            moviePath: "../../js/ZeroClipboard.swf"
        });

        clip.on("complete", function(client, args) {
            alert("代码已成功复制到剪贴板");
        });



    } else {
        jQuery(".copyLink").click(
            function(e) {

                e.preventDefault();

                window.clipboardData.setData("Text",
                    jQuery("#clipboard").text());
                alert("代码已成功复制到剪贴板");

            });
    }
    
    var loginMemberUserName = jQuery.cookie("loginMemberUsername");
    if (null != loginMemberUserName && loginMemberUserName.indexOf("@") > 0) {
    	loginMemberUserName = loginMemberUserName.substring(0, loginMemberUserName.indexOf("@"));
	}
    jQuery('#member_channel_index_username').text(loginMemberUserName);
    
    // 登录
    jQuery('#member_channel_index_login').click(function() {
        var localURL = window.location.href;
        var loginBackURL = encodeURIComponent(localURL);
        window.location.href = sinosoft.httpsbase + "/shop/member!newLogin.action?tagid=logintag&loginBackURL=" + loginBackURL;
    });
    // 注册
    jQuery('#member_channel_index_reg').click(function() {
        var localURL = window.location.href;
        var loginBackURL = encodeURIComponent(localURL);
        window.location.href = sinosoft.httpsbase + "/shop/member!newLogin.action?tagid=regtag&loginBackURL=" + loginBackURL;
    });

     jQuery('.tribe-tap').taghoverbind({  showClass: '.tribe-tap-des'});
})

jQuery("#vip_linkss").click(function() {
		
		//jQuery.getJSON(sinosoft.base + '/shop/member!checkLogin.action?callback=?', function(data) {
			if (jQuery("#memberId").val() != null && jQuery("#memberId").val() != "") {
				art.dialog({
					title : '发送专属推荐链接',
					width : '400px',
					content : document.getElementById('vip_link_box'),
					id : 'viplink',
					lock : true,
					opacity : 0.6
				});
			} else {
				var localURL = window.location.href;
		        var loginBackURL = encodeURIComponent(localURL);
		        window.location.href = sinosoft.httpsbase + "/shop/member!newLogin.action?tagid=logintag&loginBackURL=" + loginBackURL;
			}
		//});
});
jQuery("#vip_friend").click(function() {
	
	//jQuery.getJSON(sinosoft.base + '/shop/member!checkLogin.action?callback=?', function(data){

		//if (data && data.status == '0') {
		if (jQuery("#memberId").val() != null && jQuery("#memberId").val() != "") {
			art.dialog({
		        title: '邮件推荐（最多可增加至10个好友）',
		        content: document.getElementById('emaile_listf'),
		        id: 'emaillist',
		        lock: true,
		        opacity: 0.6
		    });
		}else {
			var localURL = window.location.href;
	        var loginBackURL = encodeURIComponent(localURL);
	        window.location.href = sinosoft.httpsbase + "/shop/member!newLogin.action?tagid=logintag&loginBackURL=" + loginBackURL;
		}
	//});
});
    // 邮件推荐
jQuery(".add_email_bt")
    .click(
        function() {
            var emaillist = jQuery(".email_list li").length;
            var pd = jQuery(this).text();
            if (pd == "+ 增加地址") {
                if (emaillist >= 10) {
                    alert("最多可增加10个好友");
                } else {
                    var s = [
                            '<li><input type="text" id="tj_mail_' + emaillist + '"   verify="电子邮箱|NOTNULL&RECOEMAIL" onblur="verifyElement(\'电子邮箱|NOTNULL&RECOEMAIL\',this.value,this.id);"   onfocus="if (this.value == \'填写您的好友邮件地址即可\') {this.value = \'\';}" value="填写您的好友邮件地址即可"  class="friend_email">',
                            '           <span class="rel_email_bt">- 减少地址</span><label class="requireField"></label></li>'
                        ]
                        .join("");
                    jQuery('.email_list')
                        .append(
                            jQuery(s)
                            .clone(
                                true))
                        .find(".rel_email_bt")
                        .live(
                            "click",
                            function() {
                                jQuery(this)
                                    .parent()
                                    .remove();
                            });
                }
            }

        });

function isCharsInBag(s, bag) {
    var i;
    for (i = 0; i < s.length; i++) {
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) {
            return false
        }
    }
    return true
}

function searchPointsProduct(pageIndex){
	var param = {};
	param.pageIndex = pageIndex;
	jQuery.ajax({
        url: sinosoft.base + '/shop/member_channel!pointsProduct.action',
        type: "post",
        dataType: "json",
        data: param,
        success: function(data) {
            if (data.products != "") {
                jQuery("#points_product").html(data.products + data.productsPageBar);
            }
        }
    })
}


// JavaScript Document
(function($){
    $.fn.myScroll = function(options){

    var defaults = {
        speed:40,  
        rowHeight:24 
    };
    
    var opts = $.extend({}, defaults, options),intId = [];
    
    function marquee(obj, step){
    
        obj.find("ul").animate({
            marginTop: '-=1'
        },0,function(){
                var s = Math.abs(parseInt($(this).css("margin-top")));
                if(s >= step){
                    $(this).find("li").slice(0, 1).appendTo($(this));
                    $(this).css("margin-top", 0);
                }
            });
        }
        
        this.each(function(i){
            var sh = opts["rowHeight"],speed = opts["speed"],_this = $(this);
            intId[i] = setInterval(function(){
                if(_this.find("ul").height()<=_this.height()){
                    clearInterval(intId[i]);
                }else{
                    marquee(_this, sh);
                }
            }, speed);

            _this.hover(function(){
                clearInterval(intId[i]);
            },function(){
                intId[i] = setInterval(function(){
                    if(_this.find("ul").height()<=_this.height()){
                        clearInterval(intId[i]);
                    }else{
                        marquee(_this, sh);
                    }
                }, speed);
            });
        
        });

    }

})(jQuery);
<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  lang="ja">
<head>
<meta charset="utf-8">
<meta name="description" content="关注微信公众账号：kaixinbbx，SOS国内医疗援助服务限时免费抢">
<title>一向高冷的SOS国际救援中心也免费了，告诉我这不是真的……</title> 
<meta id="viewport" name="viewport" content="width=640,initial-scale=0.5,maximum-scale=0.5" />
<meta name="format-detection" content="telephone=no" />
<meta name="applicable-device" content="mobile">
<link rel="stylesheet" href="http://wap.kaixinbao.com/mobile/css/def_import.css" media="screen">
<link rel="stylesheet" href="${shopStaticPath}/shop/css/sos/wap_style.css" media="screen">
<script type="text/javascript" src="${shopStaticPath}/shop/js/sos/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${shopStaticPath}/shop/js/sos/jquery-form.js"></script>
<script type="text/javascript" src="${shopStaticPath}/shop/js/sos/publicValid.js"></script>
<script type="text/javascript" src="${shopStaticPath}/shop/js/sos/public.js"></script>
<script type="text/javascript">
try{
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    WeixinJSBridge.call('hideToolbar');// 隐藏底部
	});
}catch (e) {
}
</script>
</head>

<body>

<div class="header">
	<p><img class="imgH" src="${shopStaticPath}/shop/images/sos/img_header.jpg" /></p>
	<p class="rtn"><span id="back">返回</span></p>
<!-- end header --></div>

<form id="orderInfoForm" enctype="multipart/form-data" method="post" action=""> 	
<div class="content">
<div class="box_big">
	<ul>
		<li>
			<span class="left">姓名：</span>
			<span class="right"><input id="name" type="text" name="sdappnt.appntName" maxlength="20" class="border border_no" value="请输入姓名" /></span>
		</li>
		<li>
			<span class="left">性别：</span>
			<span class="right">
				<select name="sdappnt.appntSex">
					<option value="M">男</option>
					<option value="F">女</option>
				</select>
			</span>
		</li>
		<li class="dateLi">
			<span class="left">出生日期：</span>
			<span class="right"><input id="date" name="sdappnt.appntBirthDay" type="text" maxlength="30" class="border border_no" value="请输入出生日期" /><br /><b>例：19780101</b></span>
		</li>
		<li>
			<span class="left">所属城市：</span>
			<span class="right">
				<select id="addr01" name="sdappnt.appntArea1" >
					<option>请选择</option>
				</select>
				<select id="addr02" name="sdappnt.appntArea2" >
					<option>请选择</option>
				</select> 
			</span>
		</li>
		<li>
			<span class="left">手机号：</span>
			<span class="right"><input id="tel" name="sdappnt.appntMobile" type="text" maxlength="11" class="border border_no" value="请输入手机号码" /></span>
		</li>
		<li>
			<span class="left">邮箱：</span>
			<span class="right"><input id="email" name="sdappnt.appntEmail" type="text" maxlength="32" class="border border_no" value="请输入电子邮箱" /></span>
		</li>
	</ul>
	<input id="channelSn" name="sdappnt.channelSn" type="hidden" value="${channelSn}"/>
<!-- end box_big --></div>
<#include "WEB-INF/template/shop/transmit.ftl"> 
<div class="btn_contral">
	<span id="p3_next">提交</span>
	<span id="reset">重填</span>
</div>
<!-- end content --></div>
</form>

<div id="lock" class="hide"></div>

<div id="tip" class="hide bg_white border">
	<div class="bg_green title">错误提示</div>
	<div class="tip_con">
		<p class="comment"></p>
	</div>
	<div class="tip_f">
		<input type="button" value="我知道了" class="right close" />
	</div>
</div>
    
<div id="tip_wait" class="hide bg_white border">
	<div class="bg_green title">等待提示</div>
	<div class="tip_con">
		 <div style="text-align:center;"><img width="50" height="50" alt="请稍候..." src="${shopStaticPath}/shop/images/sos/index_adloding.gif" /></div> 
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		
		var address1 = ["北京", "江苏", "广东", "福建", "山东", "四川", "河北", "河南", "湖北", "湖南", "辽宁", "黑龙江"];
		var address2 = [["北京"], ["南京", "无锡", "扬州", "徐州", "南通"], ["深圳", "广州", "惠州", "顺德", "中山", "珠海"], ["福州", "厦门", "泉州", "南平", "漳州", "惠安"], ["青岛", "济南", "烟台", "栖霞", "潍坊", "淄博", "济宁", "泰安", "临沂"], ["成都", "乐山", "绵阳", "泸州", "南充", "达州"], ["石家庄", "唐山", "邯郸", "保定", "承德"], ["郑州"], ["武汉", "孝感", "黄石", "荆州"], ["长沙", "衡阳", "岳阳", "株洲", "常德"], ["沈阳","大连", "鞍山", "抚顺"], ["哈尔滨"]];
		
		for(var i=0; i<address1.length; i++) {
		
			$("#addr01").append("<option value=" + address1[i] + ">" + address1[i] + "</option>");
			
		}
		
		$("#addr01").change(function(e) {
			
			e.preventDefault();
			
			var _val = jQuery(this).val();
			
			$("#addr02").empty();
			
			if(_val == "请选择") {
				
				$("#addr02").append("<option>请选择</option>");
				return;
				
			}
			
			var _num = jQuery.inArray(_val, address1);
			
			for(var j=0; j<address2[_num].length; j++) {
			
				$("#addr02").append("<option value=" + address2[_num][j] + ">" + address2[_num][j] + "</option>");
			
			}
			
		});
		$("#name").blur(function(e) {

		  e.preventDefault();
		
		  if($(this).val().length < 2) {
		    $("#lock").removeClass("hide");
		    $("#tip").removeClass("hide");
		    $("#tip .comment").html("用户名长度至少为2位");
		    jQuery(this).val("请输入姓名");
		  }
		
		});
		$("input#date").blur(function(e) {
		
		    e.preventDefault();
		    
			if(jQuery(this).val() == "") {
				$("#lock").removeClass("hide");
				$("#tip").removeClass("hide");
				$("#tip .comment").html("请输入出生日期");
				jQuery(this).val("请输入出生日期");
			} else if(jQuery(this).val().match("^19[0-9]{2}(((0[13578]|(10|12))(0[1-9]|[1-2][0-9]|3[0-1]))|(02(0[1-9]|[1-2][0-9]))|((0[469]|11)(0[1-9]|[1-2][0-9]|30)))$") == null) {
				$("#lock").removeClass("hide");
				$("#tip").removeClass("hide");
				$("#tip .comment").html("请输入正确的出生日期");
				jQuery(this).val("请输入出生日期");
			}

		});
		 
		 
		  $('#back').click(function(e){
		  		e.preventDefault();
		  		window.location.href = "activety_order!initinfo.action?channelSn="+jQuery("#channelSn").val();
		  });
		 $('#p3_next').click(function(e){
		 
		 	e.preventDefault();
			 
			if($("input#name").val() == "请输入姓名" || !validname()){
				$("#lock").removeClass("hide");
				$("#tip").removeClass("hide");
				$("#tip .comment").html("请输入姓名");
				return;
			}
			if($("input#name").val().length < 2) {
			    $("#lock").removeClass("hide");
			    $("#tip").removeClass("hide");
			    $("#tip .comment").html("用户名长度至少为2位");
			    jQuery("input#name").val("请输入姓名");
			    return;
		    }
		
			if($("input#date").val() == "请输入出生日期" || $("input#date").val() == ""){
				$("#lock").removeClass("hide");
				$("#tip").removeClass("hide");
				$("#tip .comment").html("请输入出生日期");
				return;
			}
			if($("select#addr01").val() == "请选择" || $("select#addr02").val() == "请选择") {
				$("#lock").removeClass("hide");
				$("#tip").removeClass("hide");
				$("#tip .comment").html("请选择所属城市");
				return;
			}
			if($("input#tel").val() == "请输入手机号码" || !validtel()){
				$("#lock").removeClass("hide");
				$("#tip").removeClass("hide");
				$("#tip .comment").html("请输入手机号码");
				return;
			}
			if($("input#email").val() == "请输入电子邮箱" || !validemail()){
				$("#lock").removeClass("hide");
				$("#tip").removeClass("hide");
				$("#tip .comment").html("请输入电子邮箱地址");
				return;
			}
			
			$("#orderInfoForm").ajaxSubmit( {
				type : "post",
				url : "activety_order!submitinfo.action",
				dataType : "json",
				beforeSend:function(){
			 		$("#lock").removeClass("hide");
					$("#tip_wait").removeClass("hide");			        
	       		}, 
	       		success : function(data) {
	       			// 关闭等待提示
		        	$("#lock").addClass("hide");
					$("#tip_wait").addClass("hide");
					//返回提示信息       
					var tflag = data.DealFlag;
					if (tflag=="true") {
						var orderSn = data.orderSn;
						window.location.href = "activety_order!resultinfo.action?channelSn="+jQuery("#channelSn").val();
					} else {
						$("#lock").removeClass("hide");
						$("#tip").removeClass("hide");
						$("#tip .comment").html(data.Msg);
						return;
					}
				} 
			});
			 
		});
		
		jQuery("#reset").click(function(e) {
			e.preventDefault();
			document.getElementById("orderInfoForm").reset();
			$("#addr02").empty().append("<option>请选择</option>");
			 
		});
	});
</script>

</body>
</html>
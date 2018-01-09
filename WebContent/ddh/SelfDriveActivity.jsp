<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title>自驾车保险免费领</title>
<meta name="keyword" content="" />
<meta name="description" content="" />
<link rel="stylesheet"  href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css"  href="<%=Config.getValue("StaticResourcePath")%>/style/activity.css"/>
<style>
body{background:#558c00; font-family:"Microsoft YaHei";}
.g-weaper{width:1000px;}
.jk_header1{
    height: 392px;
    background:url(<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/ddh_011.jpg) no-repeat center center;
}
.jk_header2{
    height: 392px;
    background:url(<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/ddh_022.jpg) no-repeat center center;
}
.jk_header3{
    height: 516px;
    background:url(<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/ddh_033.jpg) no-repeat center center;
}
.hjk_header1{
     height: 392px;
     background:url(<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/ddh_01.jpg) no-repeat center center;
}
.hjk_header2{
    height: 392px;
    background:url(<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/ddh_02.jpg) no-repeat center center;
}
.hjk_header3{
    height: 516px;
    background:url(<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/ddh_03.jpg) no-repeat center center;
}
.ddh{
    width:1000px;
    margin: 0 auto;
    background:#f9f1cd;
    margin-top:-670px;
    position:relative;
    padding-top:60px;
}
.tits{
    width:538px;
    height:48px;
    position: absolute;
    top: -13px;
    left:50%;
    margin-left: -268px;
    background:url(<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/tit_03.png) no-repeat center center;
}
.hddes{
    height:307px;
    background:url(<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/hds_22.png) no-repeat center center;
}
.btns{
    height:87px;
    margin-top:75px;
    background:url(<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/bottombg_23.jpg) no-repeat center center;
}
.iamge1{
    display: block;
    margin:0 auto;
}
.ddh_t{
    margin:30px 50px 50px;
    width:900px;
    border:1px solid #d9e1a5;
}
.ddh_t th{
    height:70px;
    line-height: 70px;
    background:#7fa50f;
    color:#fff;
    font-size: 28px;
}
.ddh_t th.th_b{
    border-right: 1px solid #fffae4;
}
.ddh_t th.lth_b{
    border-left: 1px solid #fffae4;
    background:#638304;
}
.ddh_t td{
    padding:30px 40px;
    font-size: 16px;
    color:#558c00;
    background:#fffae4;
    border:1px solid #d9e1a5;
    line-height: 1.6em;
}
.ddh_t td span{
    color:#415505;
    display:block;
}
.ddh_t td.ddh_sf{
    text-align: center;
}
.bmbox{
    height:376px;
    position:relative;
    margin-top: 30px;
}
.bmimg{
    width:1093px;
    height:411px;
    margin:0 auto;
    background:url(<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/ddhs_06.png) no-repeat center center;
    position: absolute;
    left: -48px;
}
.hdd_ul{
    width:280px;
    margin:103px auto 0;
    padding-left:100px;
}
.ddhtxt{
    width:230px;
    height:36px;
    line-height: 36px;
    border:0;
    padding-left: 4px;
    font-size: 16px;
    color:#666;
    background:none;
    margin-bottom: 12px;
}
.ddh_sfz{
    margin-left: 40px;
    width:190px;
}
.ddh_btn{
    width:158px;
    height:40px;
    display:block;
    line-height: 40px;
    font-size: 16px;
    font-weight: bold;
    color:#fff;
    background:#415505;
    border:0;
    cursor: pointer;
    margin:12px auto;
    text-align: center;
    border-radius: 4px;
}
.ddh_btn:hover{
    background:#556b12;
}
.hdd_ul li{
    position:relative;
}
.hdd_ul li .error{ color:red; position:absolute; top:10px; left:1px; font-size: 18px;  background:#f9f1cd;     width:230px;}
.hdd_ul label{
    position:absolute;
    top:5px;
    left: 1px;
    font-size: 18px;
    color:#c2c5a4;

}
.img_tis{
    display:block;
    text-align: left;
    padding-left:60px;
    background:url(<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/logos_10.png) no-repeat  10px 27px;
}
.hdd_ul .ddh_sfzlabel label{ left:42px;}
.hdd_ul li.ddh_sfzlabel .error{ left:42px;     width:190px; }
.ddfunt{ background:#ccc; cursor:not-allowed;}
.ddfunt:hover{ background:#ccc;}
.bmimg dot {
    display: inline-block;
    height: 1em; line-height: 1;
    text-align: left;
    vertical-align: -.25em;
    overflow: hidden;
}
.bmimg dot::before {
    display: block;
    content: '...\A..\A.';
    white-space: pre-wrap;
    animation: dot 1.5s infinite step-start both;
}
@keyframes dot {
    33% { transform: translateY(-2em); }
    66% { transform: translateY(-1em); }
}
.drop{
    width:100%;
    height:100%;
    background-color:#000;
    position:fixed;
    top:0;
    left:0;
    z-index:2;
    opacity:0.6;
    /*兼容IE8及以下版本浏览器*/
    filter: alpha(opacity=60);
        display:none;
}
.win_tip{
    width:470px;
    height:175px;
    position:fixed;
    padding-top: 375px;
    bottom:-550px;
    left:50%;
    margin-left: -235px;
    z-index: 3;
    background:url(<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/ddhbg.png) no-repeat  10px 27px;
    display:none;
}
.win_tip b{
    font-size: 41px;
    color:#f76241;
    display:block;
    text-align: center;
    padding-bottom: 20px;
}
.win_tip p{
    font-size: 26px;
    color:#558c00;
    line-height: 1.6em;
    text-align: center;
}

</style>
</head>

<body class="abg">

<!-- 发布的时候开启静态头 开启公共头 -->
<cms:include file="block/kxb_header_index_new_promotion.shtml" />
    <div class="jk_header1">
        <div class="hjk_header1"></div>
    </div>
    <div class="jk_header2">
        <div class="hjk_header2"></div>
    </div>
    <div class="jk_header3">
        <div class="hjk_header3"></div>
    </div>
    <div class="ddh">
        <h2 class="tits"></h2>
         <img src="<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/cp.jpg" class="iamge1" alt="">
    <table class="ddh_t">
        <thead>
            <tr>
                <th width="710px" class="th_b"><span class="img_tis">保障权益</span></th>
                <th class="lth_b">保额</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td> <span>自驾车意外身故</span>
                <p>被保险人自进入自驾车车厢至抵达目的地走出车厢时止，在驾驶或乘坐的状态中发生意外伤害事故，且自该意外伤害事故发生之日起180日内因该意外伤害事故单独且直接导致身故的，我们将按本合同载明的保险金额扣除累计已给付的自驾车意外伤残保险金后的金额给付自驾车意外身故保险金。</p></td>
                                <td class="ddh_sf">20万</td>
            </tr>
            <tr>
                <td> <span>自驾车意外残疾</span>
                <p>被保险人自进入自驾车车厢至抵达目的地走出车厢时止，在驾驶或乘坐的状态中发生意外伤害事故，且自该意外伤害事故发生之日起一百八十日内因该意外伤害事故单独且直接导致身体伤残的，我们将根据该伤残项目所对应的给付比例乘以本合同载明的保险金额的10%给付自驾车意外伤残保险金。</p></td>
                <td class="ddh_sf">2万</td>
            </tr>
        </tbody>
    </table>
    <img src="<%=Config.getValue("StaticResourcePath")%>/active_file/images/ddh/youshi_15.png" alt="">
    <div class="bmbox">
        <div class="bmimg">
             <ul class="hdd_ul">
            <li>
            <div>
                <input type="text" data-verify="NOTNULL|姓名不为空" id="realName" class="ddhtxt" />
            </div></li>
            <li>
            <div>
                <input type="text" data-verify="NOTNULL|手机号不为空&amp;&amp;TELPHONE|手机号格式不正确" id="mobileNo" class="ddhtxt" />
            </div></li>
            <li>
            <div>
                <input type="text" data-verify="NOTNULL|邮箱不为空&amp;&amp;EMAILANDTEL|邮箱格式不正确" id="email" class="ddhtxt" />
            </div>
            <input  class="card_type" data-name="card_type" readonly="readonly" value="身份证" type="hidden">
            </li>
            <li class="ddh_sfzlabel">
            <div>
                <input type="text"  id="idCode" class="ddhtxt ddh_sfz" data-verify="NOTNULL|身份证不为空&amp;&amp;CARDTYPE|身份证格式不正确"   />
            </div></li>
        </ul>
        <span type="button" value="" class="ddh_btn" id="ddh_sub" >点击领取</span>
        <span type="button" value="" class="ddh_btn ddfunt" id="ddh_subdef" style="display:none" >正在提交<dot>...</dot></span>
        </div>
    </div>
    <div class="hddes">
       
    </div>
    <div class="btns"></div>
    </div>
<div class="g-weaper"></div>

<div class="drop"></div>
<div class="win_tip a-fadeinB">
        <b>您已领取成功</b>
        <p>该保障将于领取后第三日生效<br />我们将会以短信形式告知您</p>
</div>

<!-- 发布的时候注释掉公共尾部 开启公共尾部 -->
<!-- <cms:include file="block/kxb_footer_new_v2.shtml" /> -->
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/redesign/re_base.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/stencil/stencil_order.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/redesign/re_footer.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/artDialog.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/iframeTools.js" ></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/login.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/redesign/re_header.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/redesign/xiaoneng_CustomerService.js?version=20160930"></script>
<script type="text/javascript" src="http://dl.ntalker.com/js/xn6/ntkfstat.js?siteid=kf_9401" charset="utf-8"></script>

<script src="<%=Config.getValue("StaticResourcePath")%>/active_file/js/validate.js" type="text/javascript"></script>
	<script>
		jQuery(document)
				.ready(
						function($) {
							//点击取消错误提示
							$(".hdd_ul .error").live("click", function() {
								$(this).prev().find("input").focus();
								$(this).remove();
							});
							$.each(jQuery(".ddhtxt"), function(index, val) {
								if (jQuery(val).val() != "") {
									jQuery(val).parent().siblings('label')
											.hide();
								}
							});

							jQuery(".ddhtxt").bind(
									"keypress keydown keyup blur",
									function() {
										jQuery(this).parent().siblings('label')
												.hide();
										if (jQuery(this).val() == "") {
											jQuery(this).parent().siblings(
													'label').show();
										}
									})

							/* 录入信息校验 */
							$(document)
									.wjDataVelify(
											{
												tarArea : ".hdd_ul",//校验的DOM作用域
												tarElms : "input[data-verify]", //校验的DOM元素集合
												sbtBtn : "#ddh_sub", //提交按钮
												callBack : function() { //回调函数
													jQuery("#ddh_sub").hide();
													jQuery("#ddh_subdef")
															.show();

													var realName = jQuery(
															'#realName').val();
													var mobileNo = jQuery(
															'#mobileNo').val();
													var email = jQuery('#email')
															.val();
													var idCode = jQuery(
															'#idCode').val();

													jQuery
															.ajax({
																type : "post",
																url :sinosoft.base + "/shop/self_drive!saveInsureData.action",
																dataType : "json",
																data : {
																	"realName" : realName,
																	"mobileNo" : mobileNo,
																	"email" : email,
																	"idCode" : idCode,
																},
																success : function(
																		data) {
																	if (data == "1") {
																		jQuery(
																				".drop")
																				.show();
																		jQuery(
																				".win_tip")
																				.animate(
																						{
																							bottom : 0,
																							opacity : 'show'
																						},
																						500);
																		setTimeout(
																				function() {
																					jQuery(
																							".drop")
																							.hide();
																					jQuery(
																							".win_tip")
																							.animate(
																									{
																										bottom : -550,
																										opacity : 'hide'
																									},
																									500);
																					jQuery(
																							"#ddh_sub")
																							.show();
																					jQuery(
																							"#ddh_subdef")
																							.hide();
																				},
																				4000)

																	} else {
																		art.dialog("您已提交过了，请勿重复提交");
																		jQuery(
																				"#ddh_sub")
																				.show();
																		jQuery(
																				"#ddh_subdef")
																				.hide();
																	}

																}
															});
												}
											});
						});
	</script>
</body>
</html>
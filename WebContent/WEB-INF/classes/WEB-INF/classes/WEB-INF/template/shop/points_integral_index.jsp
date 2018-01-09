<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="积分商城首页" />
	<meta name="description" content="积分商城首页" />
	<title>积分商城-首页-<%=request.getAttribute("siteName") %></title>
	<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
	<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_integral.css" />
	<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"  />
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery-1.4.2.min.js"></script>
	
	<script type="text/javascript">
	 	window.onload=function(){
			/*模块信息*/
			jQuery("div[id^='page_model_info_']").each(function (){
				jQuery("#" + this.id).load("<%=Config.getValue("ServerContext")%>/shop/points!integralMallModelInfo.action?modeltype="+this.id+"&idalias="+this.id);
			});
	    };
	    /*积分段落*/
	    function pointpartclick(id,point){
	    	jQuery("#" + id).load("<%=Config.getValue("ServerContext")%>/shop/points!integralMallModelInfo.action?modeltype="+id+"&idalias="+id+"&pointvalue="+point);
	    }
	    //jf_sel
	    //[id$='2']
	</script>
<style type="text/css">
.g-nav-main li a.nav_4{ color:#f08225; }
</style>
	<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body class="re-bg">
	<!-- 公共头部 -->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 公共头部 -->
	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span> <a target="_self" href="<%=Config.getValue("FrontServerContextPath")%>">
			<span class="orange">您的现在位置：首页</span></a><span class="separator">&gt;</span><span class="separator1">积分商城</span>
		</div>
		<div class="clear h10"></div>
		<!-- 左侧整体部分 -->
		<div class="int_left fleft">
			<!-- 轮播广告 -->
			<div class="int_banner">
				<div id="jifen" class="changeBox_a1">
					<s:include value="/wwwroot/kxb/block/points_index_ad.shtml"></s:include>
				</div>
			</div>
			<!-- 轮播广告 -->
			<!-- 热门兑换 --> 
			<div class="shop_bor shop_hotc clearfix">
				<h2 class="shop_tit">热门兑换</h2>
				<s:if test="#request.hotgiftlist.size()!=0">
					<s:iterator id="hotGift" value="#request.hotgiftlist" status="status">
						<dl class="jf_shop">
							<dt class="jf_shop_img">
								<a href="<s:property value="#hotGift.linkUrl"/>" target="_blank">
									<img src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="#hotGift.logoUrl"/>" width="127px" height="100px" alt="<s:property value="#hotGift.giftTitle"/>">
								</a>
							</dt>
							<dd class="jf_shop_link">
								<a href="<s:property value="#hotGift.linkUrl"/>" target="_blank"><s:property value="#hotGift.giftTitle"/></a>
							</dd>
							<dd class="jf_shop_dd">
								<span class="jf_tip_icon">剩余<s:property value="#hotGift.lastNum"/>个</span>
							</dd>
							<dd class="jf_go_dh"><a href="<s:property value="#hotGift.linkUrl"/>" target="_blank" class="rborder4"><s:property value="#hotGift.points"/> 积分</a>
							</dd>
						</dl>
					</s:iterator>
				</s:if>
				<s:else>
					<p class="no_shop_hot">暂时没有设置热门推荐产品</p>
				</s:else>
			</div>
			<!-- 热门兑换 -->
		</div>
		<!-- 左侧整体部分 -->
		<!-- 右侧部分 -->
		<div class="int_right fright">
			<!-- 登录信息 -->
			<div class="int_box int_box_l cf">
			<s:if test="loginmap.isEmpty()">
				<div class="trobe-loginNo">
					<p class="login-Tit f_mi">赚积分 兑礼品</p>
					<ul class="login-UlMes">
                        <li class="login-UlMes1">
                          <span>注册得积分</span>
                        </li>
                        <li class="login-UlMes2">
                          <span>会员赚积分</span>
                        </li>
                        <li class="login-UlMes5">
                          <span>积分换商品</span>
                        </li>
                    </ul>
                    <a class="trobeLink f_mi" id="PointShowLoginWindow" href="javascript:void(0);" onclick="javascript:void(0);return(VL_FileDL(this));return false;" exturl="http://www.kaixinbao.com/jflogin" vlpageid="jflogin">登录</a><a class="trobeLink f_mi" id="PointShowRegisterWindow" href="javascript:void(0);" onclick="javascript:void(0);return(VL_FileDL(this));return false;" exturl="http://www.kaixinbao.com/jflogin" vlpageid="jflogin">注册</a>
				</div>
			</s:if>
			<s:else>
				<div class="trobe-loginYes">
                    <div class="TroboeHeader">
                        <img width="60px" height="60px" alt="" src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="loginmap.headpicpath"/>">
                        <div class="header-img"></div>
                    </div>
                    <div class="clear"></div>
                    <div class="TroboeCon">
                        <span class="troberName"><span id="troberName"><s:property value="loginmap.realName"/></span><i class="<s:property value="loginmap.gradeClass"/>" title=""></i></span>
                    </div>
                    <dl class="trobelogdl TrobeIntegral">
                        <dd class="integraNum"><a href="<%=Config.getValue("ServerContext")%>/shop/point!newList.action"><s:property value="loginmap.currentvalidatepoint"/></a></dd>
                        <dd>可用积分</dd>
                    </dl>
                    <dl class="trobelogdl TrobeIntegral2">
                        <dd class="integraNum"><a href="<%=Config.getValue("ServerContext")%>/shop/point!newList.action"><s:property value="loginmap.point"/></a></dd>
                        <dd>冻结积分</dd>
                    </dl>
                    <div class="clear trobeBor"></div>
                    <a class="trobeLink f_mi trobeLink2" href="<%=Config.getValue("FrontServerContextPath")%>">投保赚积分</a>
                    <div class="clear"></div>
                    <a href="<%=Config.getValue("ServerContext")%>/shop/member_comment!queryComment.action" class="trop0_linksc"><em><s:property value="loginmap.commentnum"/>个</em>商品待评价,可得<em><s:property value="loginmap.commentpoint"/>积分</em>&gt;</a>
                    <a href="<%=Config.getValue("ServerContext")%>/shop/point!queryPointsDesc.action?source=jfsc" class="trop0_linksc2">如何赚积分&gt;</a>
                </div>
		    </s:else>
            </div>
			<!-- 登录信息 -->
			<!-- 最新上架 -->
			<div class="shop_bor shop_hotc clearfix">
				<h2 class="shop_tit">最新上架</h2>
				<ul class="new_shopli">
					<s:iterator id="newGift" value="#request.newGiftlist" status="status">
						<li><a href="<s:property value="#newGift.linkUrl"/>"  target="_blank"><s:property value="#newGift.giftTitle"/></a><span><s:property value="#newGift.points"/>积分</span></li>
					</s:iterator>
				</ul>
			</div>
			<!-- 最新上架 -->
		</div>
		<!-- 右侧部分 -->
		<div class="clear"></div>
		<!-- 归属模块 -->
		<s:iterator id="model_map" value="#request.modelList" status="status">
			<div class="secion shop_bor clearfix" >
					<h2 class="shop_tit"><s:property value="#model_map.ModelName"/><span class="Jfmini_tit"><s:property value="#model_map.Describtion"/></span>
						 <div class="Jf_screen">
							<s:iterator id="pointPart" value="#model_map.modelList" status="status">
								<s:if test="#status.index==0">
									<span  class="jf_sel" onclick="pointpartclick('page_model_info_<s:property value="#model_map.modeltype"/>','<s:property value="#pointPart.codevalue"/>')" ><s:property value="#pointPart.codename"/><input type="hidden" value="<s:property value="#pointPart.codevalue"/>" ></span>
								</s:if>
								<s:elseif test="((#status.count)==(#model_map.modelList.size()))">
									<span class="no_jf_bor" onclick="pointpartclick('page_model_info_<s:property value="#model_map.modeltype"/>','<s:property value="#pointPart.codevalue"/>')" ><s:property value="#pointPart.codename"/><input type="hidden" value="<s:property value="#pointPart.codevalue"/>" ></span>
								</s:elseif>
								<s:else>
									<span onclick="pointpartclick('page_model_info_<s:property value="#model_map.modeltype"/>','<s:property value="#pointPart.codevalue"/>')" ><s:property value="#pointPart.codename"/><input type="hidden" value="<s:property value="#pointPart.codevalue"/>" ></span>
								</s:else>
							</s:iterator>
						</div>
					</h2>
					<!-- 分页数据 -->
					<div id="page_model_info_<s:property value="#model_map.modeltype"/>" ></div>
			</div>
		</s:iterator>
		<div class="clear h20"></div>
		<h2 class="shop_tit">如何赚积分</h2>
		<div class="secion_img"><img src="<%=Config.getValue("StaticResourcePath")%>/images/jifen/jifen_09.gif" alt=""></div>
		<div class="shop_bor shop_qa clearfix">
			<h2 class="shop_tit">常见问题</h2>
			<dl class="shop-qa">
				<dt class="shop-q"><em class="q-icon"></em><span class="q-icon-down q-icon-up">兑换礼品，没有收到怎么办？</span></dt>
				<dd class="shop-a"><em class="a-icon"></em>若您在2个工作日内未收到开心保发送的兑换信息，请及时与我们的客服联系。如果未发送，则会为您补发。</dd>
			</dl>
			<dl class="shop-qa">
				<dt class="shop-q"><em class="q-icon"></em><span class="q-icon-down">购买产品，为什么没赠送积分？</span></dt>
				<dd class="shop-a" style="display:none"><em class="a-icon"></em>购买产品后，积分会下发到您的账户冻结积分中，当保单达到保险期限时，积分会自动解除冻结状态，返还到可用积分中。如果未赠送积分，请及时与我们的客服联系。</dd>
			</dl>
			<dl class="shop-qa">
				<dt class="shop-q"><em class="q-icon"></em><span class="q-icon-down">发表评论，怎么获得积分？</span></dt>
				<dd class="shop-a" style="display:none"><em class="a-icon"></em>对已支付的产品进行评论打分，评论完成后，即可获得相应的积分奖励。</dd>
			</dl>
			<dl class="shop-qa">
				<dt class="shop-q"><em class="q-icon"></em><span class="q-icon-down">怎么使用积分抵扣支付金额？</span></dt>
				<dd class="shop-a" style="display:none"><em class="a-icon"></em>购买产品时，在支付页面会提示您当前拥有多少积分，可抵值多少金额，填写相应积分后，即可抵值订单总金额。</dd>
			</dl>
			<dl class="shop-qa">
				<dt class="shop-q"><em class="q-icon"></em><span class="q-icon-down">怎么查看我的积分明细？</span></dt>
				<dd class="shop-a" style="display:none"><em class="a-icon"></em>注册会员登录账户后，可在会员中心-我的积分查看积分的使用明细，包括积分的收入和支出。</dd>
			</dl>
			<div class="clear"></div>
		</div>
	</div>
	<div class="clear"></div>
	<!-- 公共底部 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 公共底部 -->
	<!-- 公共js -->
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<!-- 公共js -->
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.soChange-min.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/template/common/js/jquery.jqDnR.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/template/common/js/jquery.jqModal.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/new_jifen.js"></script>
</body>
</html>

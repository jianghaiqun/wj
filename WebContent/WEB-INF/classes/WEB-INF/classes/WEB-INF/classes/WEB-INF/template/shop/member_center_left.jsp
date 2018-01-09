<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="member_left">
	<input type="hidden" id="out_member" value="member"/>
	<h3 class="member_title"><span><a href="member_center!index.action">会员中心</a></span></h3>
	<dl class="member_navs cf" id="member_nav">
		<dt class="mem_nav_b mem_nav_b_icon1">订单中心</dt>
		<dd class="mem_nav_a"><a href="order_query!queryOrder.action" >所有订单<em id="AllOrderEm"></em></a></dd>
		<dd class="mem_nav_a"><a href="order_query!queryNoPayOrder.action" >待支付订单<em id="PrePayEm"></em></a></dd>
    	<dd class="mem_nav_a"><a href="order_query!queryNoEffectOrder.action" >待生效保单<em id="PayedEm"></em></a></dd>
    	<dd class="mem_nav_a"><a href="member_comment!queryComment.action" >待评价订单<em id="CommentNumEm"></em></a></dd>
		<dd class="mem_nav_a"><a href="order_query!queryEffectingOrder.action" >生效中保单<em id="EffectiveEm"></em></a></dd>
		<dd class="mem_nav_a"><a href="order_query!queryOutEffectOrder.action" >已过期保单<em id="OutEffectEm"></em></a></dd>
		<div id="showLcx" style="display:none">
			<dt class="mem_nav_b mem_nav_b_icon7">理财管理</dt>
	        <dd class="mem_nav_a"><a href="order_query!financingBank.action">银行卡信息</a></dd>
	        <dd class="mem_nav_a"><a href="order_query!financingRecord.action">理财险资金记录</a></dd>
	        <dd class="mem_nav_a"><a href="order_query!financingManager.action">理财险投资管理</a></dd>
        </div>
    	<dt class="mem_nav_b mem_nav_b_icon2">优惠券/积分</dt>
    	<dd class="mem_nav_a"><a href="coupon!queryCoupon.action" >我的优惠券<em id="CouponCountEm"></em></a></dd>
    	<dd class="mem_nav_a"><a href="point!newList.action" >我的积分<em id="currentValidatePointEm"></em></a></dd>
    	
    	<!-- <dt class="mem_nav_b mem_nav_b_icon5">我的服务</dt>
		<dd class="mem_nav_a"><a href="bill_query!queryBill.action" >发票申请</a></dd> -->
		
    	<dt class="mem_nav_b mem_nav_b_icon3">邀请好友</dt>
    	<dd class="mem_nav_a"><a href="member_recommend!query.action" >我的邀请</a></dd>
    	<dt class="mem_nav_b mem_nav_b_icon4">我的关注</dt>
    	<dd class="mem_nav_a"><a href="stow!queryScan.action" >我的收藏<em id="collectionNumEm"></em></a></dd>
    	<dd class="mem_nav_a"><a href="my_compare!show.action" >对比记录<em id="compareNumEm"></em></a></dd>
    	<dd class="mem_nav_a"><a href="question!questionList.action" >保险问答</a></dd>
		<dd class="mem_nav_a"><a href="evaluating!evaluatingList.action" >保险评测</a></dd>
		<dt class="mem_nav_b mem_nav_b_icon6">帐号设置</dt>
		<dd class="mem_nav_a"><a href="profile!edit.action">基本信息<em id="fullDegreeEm"></em></a></dd>
    	<dd class="mem_nav_a"><a href="member_info_maintain!memberInfoQuery.action" >常用信息</a></dd>
		<dd class="mem_nav_a"><a href="password!edit.action" >密码修改</a></dd>
		<div id="showchive" style="display: none">
			<dt class="mem_nav_b mem_nav_b_icon8">我的存档</dt>
			<div id="chive1" style="display:none"><dd class="mem_nav_a"><a href="gap_guarantee!Gapguarantee.action?count=1">存档一<em id="datetime1"></em></a></dd></div>
			<div id="chive2" style="display:none"><dd class="mem_nav_a"><a href="gap_guarantee!Gapguarantee.action?count=2">存档二<em id="datetime2"></em></a></dd></div>
			<div id="chive3" style="display:none"><dd class="mem_nav_a"><a href="gap_guarantee!Gapguarantee.action?count=3">存档三<em id="datetime3"></em></a></dd></div>
		</div>
	</dl>
</div> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8" />
<title>会员积分</title>
<link rel="icon" href="../favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_sales.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/vip_tribe.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<style type="text/css">
.g-nav-main li a.nav_5 {
	color: #f08225;
}
</style>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>

</head>
<body class="re-bg">
	<!-- 顶部 开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->
	<!-- 会员部落 位置-->
	<div class="weaper">
		<div class="daohang">
			<span class="daohang_home"></span> <a
				href='<%=Config.getFrontServerContextPath()%>' target='_self'><span
				class="orange">您的现在位置：首页</span></a> <span class="separator">&gt;</span>
			<span class="separator1">会员部落</span>
		</div>
	</div>
	<!-- 会员部落 正文-->
	<div class="tribe-integral">
		<div class="tribebner"
			style="height: 372px; background: url('<%=Config.getValue("StaticResourcePath")%>/images/member/jf_03.jpg') center top;"></div>
		<div class="tribe-integralbox">
			<h2 class="tribe-inttit f_mi">（一）花积分</h2>
			<div class="clear"></div>
			<dl class="tribe-dls dl_first">
				<dt class="tribe_icons1"></dt>
				<dd>
					直接用来支付<br />开心保网站的订单
				</dd>
			</dl>
			<dl class="tribe-dls">
				<dt class="tribe_icons2"></dt>
				<dd>
					在线购买抵值支付<br /><s:property value="#request.showData.PointScalerUnit" />积分可抵值1元现金使用
				</dd>
			</dl>
			<dl class="tribe-dls">
				<dt class="tribe_icons3"></dt>
				<dd>
					在“积分商城”中<br />兑换相应的礼品
				</dd>
			</dl>
			<dl class="tribe-dls dl_end">
				<dt class="tribe_icons4"></dt>
				<dd>抽奖或兑换优惠券</dd>
			</dl>
			<div class="clear h20"></div>
			<div class="fg_br"></div>
			<div class="tribe_zintegral">
				<h2 class="tribe-inttit f_mi">（二） 赚积分</h2>
				<br>
				<h3 class="tribere_h3 f_mi">一、注册和完善个人信息</h3>
				<p>
					1、会员注册成为开心保会员，即可获得注册积分奖励。<br />
					2、会员在“会员中心—账户中心”中，完善个人信息即可获得相应积分奖励。
				</p>
				<table class="tribere-table">
					<tr>
						<th>完善内容项</th>
						<th>获得积分数</th>
						<th>规则</th>
					</tr>
					<tr>
						<td>注册</td>
						<td><s:property value="#request.showData.pointRegister" /></td>
						<td>填写邮箱或手机号成功注册</td>
					</tr>
					<tr>
						<td>邮箱验证</td>
						<td><s:property value="#request.showData.pointEmail" /></td>
						<td>邮箱验证成功后返还</td>
					</tr>
					<tr>
						<td>手机验证</td>
						<td><s:property value="#request.showData.pointMobile" /></td>
						<td>手机验证成功后返还</td>
					</tr>
					<tr>
						<td>性别</td>
						<td><s:property value="#request.showData.pointSex" /></td>
						<td>填写性别</td>
					</tr>
					<tr>
						<td>生日</td>
						<td><s:property value="#request.showData.pointBirthday" /></td>
						<td>填写生日</td>
					</tr>
				</table>
				</p>
				<h3 class="tribere_h3 f_mi">二、评价奖励</h3>
				<p>
					1、获得条件：会员成功购买后，完成订单支付，即可以对产品及服务进行评论打分获得积分奖励。<br />
					2、获得时间：在保单终止后，符合规则的评论提交后，积分即发放至会员账户中。<br />
					3、积分计算：每份订单可以评论一次，每次赠送<s:property value="#request.showData.commPoint" />个开心保积分。<br />
				</p>
				<h3 class="tribere_h3 f_mi">三、推荐好友注册奖励</h3>
				<p>
					1、开心保网为您生成专属推荐链接。<br />
					2、通过您的分享链接，在被推荐人注册并验证手机后，您将获得<s:property value="#request.showData.recommRegCount" />个积分奖励。推荐好友注册上限<s:property value="#request.showData.recommRegisterCount" />人。<br />
					3、若此推荐与其他活动冲突时，以活动具体规则为准。
				</p>
				
				<div class="clear h30"></div>
			</div>
			<div class="fg_br fg_br2"></div>
			<div class="tribe_zintegra2">
				<h2 class="tribe-inttit f_mi">（三）积分说明</h2>
				<p class="f_mi">开心保积分是开心保会员在开心保官方网站（www.kaixinbao.com）评价、推荐好友注册购买等网站活动时给予的开心奖励。</p>
				<ul class="tribe-usl">
					<li><em>1</em> 会员可以在“会员中心—我的积分”中查询到开心保积分的详细情况。</li>
					<li><em>2</em> 积分可与其他优惠券、红包同时使用(以网站活动说明为标准)，在支付时，可以选择抵扣方式。</li>
					<li><em>3</em>
						积分存在有效期限制，会员积分有效期为<s:property value="#request.showData.pointPeriod" />，积分按月判断是否过期，即从积分生效时间点记录<s:property value="#request.showData.pointPeriod" />，在到期自然月的最后一天24点进行清零。</li>
					<li><em>4</em> 会员在使用积分支付时，指定赠送积分的订单依然可以享受积分返还。</li>
					<li><em>5</em> 会员购买指定赠送积分的保单发生退保时，开心保网站将对相应的积分收回。</li>
					<li><em>6</em>
						注册开心保会员账号完善个人信息可获得相应的积分奖励。开心保已自动为投保人注册了会员账号，发送至投保人的<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机或者邮箱中，也可享受积分赠送优惠。</li>
					<li><em>7</em> 车险产品、投资理财型保险产品暂时无法使用开心保积分抵值支付。</li>
				</ul>
				<div class="tribe-tistap">
					<h4>特别提示</h4>
					<ul>
						<li>开心保积分目前只能在开心保网站使用。</li>
						<li>若所购买的产品仍有赠送积分活动时，使用积分抵值或优惠减免的部分金额不奖励积分。</li>
						<li>保单生效前撤销订单，用于该订单抵值的积分在撤单完成后返还。保单生效后办理退保，抵值积分无法返还。</li>
						<li>开心保保留依据相关法律法规调整开心保积分规则权力。积分规则调整时，将不会事先通知而即时按照最新公告执行。</li>
					</ul>
				</div>
			</div>
			<div class="fg_br"></div>
			<div class="tribe_zintegral">
				<h2 class="tribe-inttit f_mi">（四）积分常见问题</h2>
				<br>
				<h3 class="tribere_h3 f_mi">推荐注册后积分什么时候生效？</h3>
				<p>
					推荐好友注册成功后，推荐人所获得积分需要好友通过手机或邮箱验证才能获得。
				</p>
				<h3 class="tribere_h3 f_mi">怎么使用积分抵扣支付金额？</h3>
				<p>购买产品时，在支付页面会提示您当前拥有多少积分，可抵值多少金额，填写相应积分后，即可抵值订单总金额。</p>
				<h3 class="tribere_h3 f_mi">怎么查看我的积分明细？</h3>
				<p>注册会员登录账户后，可在会员中心-我的积分查看积分的使用明细，包括积分的收入和支出。</p>
				<h3 class="tribere_h3 f_mi">购买什么样的产品可以获得积分？</h3>
				<p>购买开心保网站指定的赠送积分产品，方可获得相应的积分。购买产品后，积分会下发到您的账户冻结积分中，当保单达到保险期限时，积分会自动解除冻结状态，返还到可用积分中，如果购买指定产品未赠送积分，请及时与客服联系。</p>
				<h3 class="tribere_h3 f_mi">在积分商城兑换礼品，没有收到怎么办？</h3>
				<p>若您在2个工作日内未收到开心保发送的兑换信息，请及时与我们的客服联系。如果未发送，则会为您补发。</p>
				<h3 class="tribere_h3 f_mi">我有两个开心保账户，积分能合并吗？</h3>
				<p>不同的账户代表您在开心保的不同身份，任何2个账户的积分都不能合并。建议您集中使用一个账户进行购买，以免造成积分分散，无法享受增值服务。</p>
				<h3 class="tribere_h3 f_mi">积分的有效期是多久？</h3>
				<p>获得之日起<s:property value="#request.showData.pointPeriod" />有效，过期积分将在到期当月月底清除，积分过期后将不能使用，如<s:property value="#request.showData.pointStaPeriod" />获得100积分，其将在<s:property value="#request.showData.pointEndPeriod" />失效。</p>
				<h3 class="tribere_h3 f_mi">开心保积分有什么用?</h3>
				<p>积分是开心保给予会员的奖励回馈，目前可在积分商城兑换奖品或者购买产品用于抵值。</p>
				<h3 class="tribere_h3 f_mi">如何查询积分？</h3>
				<p>登陆开心保，在网站导航上方点击会员中心，点击我的积分进行查询。</p>
				<div class="clear h40"></div>
			</div>
		</div>
	</div>

	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
	<!-- js加载 -->
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/artDialog.js"></script>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/ZeroClipboard.min.js"></script>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/redesign/vip_tribe.js"></script>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/VerifyInput.js"></script>
</body>
</html>
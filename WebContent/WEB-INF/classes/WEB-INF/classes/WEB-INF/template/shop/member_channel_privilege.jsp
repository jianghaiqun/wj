<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8"/>
<title>会员特权</title>
<link rel="icon" href="../favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_sales.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/vip_tribe.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<style type="text/css">
.g-nav-main li a.nav_5{ color:#f08225; }
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
	        <span class="daohang_home"></span>
	        <a href='<%=Config.getFrontServerContextPath()%>' target='_self' ><span class="orange">您的现在位置：首页</span></a>
	        <span class="separator">&gt;</span>
	        <span class="separator1">会员部落</span>
	    </div>
	</div>
	<!-- 会员部落 正文-->
    <div class="tribebner"   style="height:480px;background:url('<%=Config.getValue("StaticResourcePath")%>/images/member/tq_02.jpg') center top;"></div>
    <div class="tribe_boxs">
	    <div class="weaper ">
	      <div class="tribe_hdone tribe_hdonebg">
	          <table class="tribe-table1">
	            <tr>
	              <th width="146px;"><span class="tribe-tap">会员特权</span></th>
	              <th>K1会员</th>
	              <th>K2会员</th>
	            </tr>
	            <tr>
	              <td class="tribe-tdc"><span class="tribe-tap">积分返还加成比例
	                <span class="tribe-tap-des border_radiusc_8 f_mi" style="display:none;">
	                <em></em>
	                  购买产品时自动折算积分返还比例，显示为最终积分值。会员积分返还加成需要会员登陆后，在产品详细页显示不同加成信息。积分返还比例为（产品返还比例+会员加成比例）。
	                </span>
	              </span></td>
	              <td><s:property value="#request.showData.K1PointReturn" /></td>
	              <td><s:property value="#request.showData.K2PointReturn" /></td>
	            </tr>
	            <tr>
	              <td class="tribe-tdc2"><span class="tribe-tap" >生日月多倍积分 <span class="tribe-tap-des  border_radiusc_8 f_mi" style="display:none;">
	                <em></em>每月初为当月生日会员发送邮件提醒。会员生日当月所有订单都享受<s:property value="#request.showData.K1BirthPoint" />倍积分返还；备注：会员生日以会员中心的生日信息资料（若未填写生日信息，则无法参加）为依据，会员一年只能参加一次；符合会员生日多倍积分的订单，订单中所有产品都可享受此优惠。
	                </span></span></td>
	              <td><s:property value="#request.showData.K1BirthPoint" />倍</td>
	              <td><s:property value="#request.showData.K2BirthPoint" />倍</td>
	            </tr>
	            <tr>
	              <td class="tribe-tdc"><span class="tribe-tap">积分商城兑换礼品<span class="tribe-tap-des tribe-tap-des2 border_radiusc_8 f_mi" style="display:none;">
	                <em></em>会员可使用账户内积分在积分商城兑换任意礼品。会员需在网站购累计满<s:property value="#request.showData.exchangeBoundary" />元方可兑换礼品。
	                </span></span></td>
	              <td>可享</td>
	              <td>可享</td>
	            </tr>
	            <tr>
	              <td class="tribe-tdc2"><span class="tribe-tap">发票免邮<span class="tribe-tap-des tribe-tap-des2 border_radiusc_8 f_mi" style="display:none;"><em></em>会员登录会员中心，在自助服务—发票申请，可申请补开发票。200元以下发票申请的邮费由会员自行承担，200元以上包邮。
	                </span></span></td>
	              <td>订单满200元</td>
	              <td>订单满200元</td>
	            </tr>
	            <tr>
	              <td class="tribe-tdc"><span class="tribe-tap">评价奖励<span class="tribe-tap-des tribe-tap-des2 border_radiusc_8 f_mi" style="display:none;">
	                <em></em>
	                  成功评价订单，即可获得相应<s:property value="#request.showData.commPoint" />积分奖励。所获积分可用于购买商品抵值及积分商城礼品兑换。
	                </span></span></td>
	              <td><s:property value="#request.showData.commPoint" />积分</td>
	              <td><s:property value="#request.showData.commPoint" />积分</td>
	            </tr>
	            <tr>
	              <td class="tribe-tdc2"><span class="tribe-tap">会员特价<span class="tribe-tap-des tribe-tap-des2 border_radiusc_8 f_mi" style="display:none;">
	                <em></em>
	                  只属于会员的特价产品，优惠更多，更省钱！
	                </span></span></td>
	              <td>无</td>
	              <td>可享</td>
	            </tr>
	            <tr>
	              <td class="tribe-tdc"><span class="tribe-tap">快速理赔<span class="tribe-tap-des tribe-tap-des3 border_radiusc_8 f_mi" style="display:none;">
	                <em></em>
	                开心保理赔专家全程服务，闪电赔付，理赔无忧
	                </span></span></td>
	              <td>可享</td>
	              <td>可享</td>
	            </tr>
	          </table>
	      </div>
	      <div class="tribe_hdone">
	        <h2 class="tribe-titss f_mi border_radiusc_8">VIP除享受以上所有特权，还额外享受以下特权：</h2>
	        <ul class="tribe_uls">
	          <li><img src="<%=Config.getValue("StaticResourcePath")%>/images/member/icon_03.jpg" width="226px" height="163px" alt=""><span>更多特价优惠</span></li>
	          <li><img src="<%=Config.getValue("StaticResourcePath")%>/images/member/icon_05.jpg" width="226px" height="163px" alt=""><span>生日礼品</span></li>
	          <li><img src="<%=Config.getValue("StaticResourcePath")%>/images/member/icon_08.jpg" width="226px" height="163px" alt=""><span>节日专属抵值券</span></li>
	          <li><img src="<%=Config.getValue("StaticResourcePath")%>/images/member/icon_10.jpg" width="226px" height="163px" alt=""><span>VIP升级礼包</span></li>
	          <li><img src="<%=Config.getValue("StaticResourcePath")%>/images/member/icon_16.jpg" width="226px" height="163px" alt=""><span>首单满额赠礼</span></li>
	          <li><img src="<%=Config.getValue("StaticResourcePath")%>/images/member/icon_18.jpg" width="226px" height="163px" alt=""><span>现金红包</span></li>
	          <li><img src="<%=Config.getValue("StaticResourcePath")%>/images/member/icon_20.jpg" width="226px" height="163px" alt=""><span>年终大礼</span></li>
	          <li><img src="<%=Config.getValue("StaticResourcePath")%>/images/member/icon_21.jpg" width="226px" height="163px" alt=""><span>发票全年免邮</span></li>
	        </ul>
	        <div class="clear"></div>
	      </div>
	      <div class="tribe_hdone tribe_hdonebg2">
	       <h2 class="tribe-titss f_mi border_radiusc_8">Vip特权说明：</h2>
	       <div class="tribe-box-fs">
	         <s:iterator id="list" value="#request.showData.vipInfo" status="status" >
	         	<h3><s:property value="#list.title" /></h3>
	            <p><s:property value="#list.desc" escape="false" /></p>
	         </s:iterator>
	         <p>vip会员等级有效期为一年。</p>
	       </div>
	       
	      </div>
	      <div class="clear h30"></div>
	    </div>
  </div>
    <!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
	<!-- js加载 -->
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/artDialog.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/ZeroClipboard.min.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/redesign/vip_tribe.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/VerifyInput.js"></script>
</body>
</html>
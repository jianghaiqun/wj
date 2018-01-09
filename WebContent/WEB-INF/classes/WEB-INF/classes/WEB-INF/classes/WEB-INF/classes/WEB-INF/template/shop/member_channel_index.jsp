<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>
<!DOCTYPE html>
<head>
<meta charset="utf-8"/>
<title>会员部落</title>
<link rel="icon" href="../favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
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
	<form action="">
		<input type="hidden" id="recommTitle" value="<s:property value="#request.showData.recommTitle" />" />
	    <input type="hidden" id="recommUrl" value="<s:property value="#request.showData.recommUrl" />" />
	    <input type="hidden" id="memberId" value="<s:property value="member.id" />" />
	</form>
	<div class="weaper">
	    <div class="tribe-login">
	        <!-- 用户登录状态 -->
	        <s:if test="null!=member">
	        <div class="trobe-loginYes">
	            <div class="TroboeHeader">
	                <s:if test="member.headPicPath == null || member.headPicPath == ''">
						<img src="<%=Config.getValue("StaticResourcePath")%>/images/header_mo_03.jpg" width="60px" height="60px" alt="头像" />
					</s:if>
					<s:else>
						<img src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="member.headPicPath"/>" width="60px" height="60px" alt="头像" />
					</s:else>
	                <div class="header-img">
	                	<a href="/wj/shop/member_center!index.action?tagid=logintag"></a>
	                </div>
	            </div>
	            <div class="clear"></div>
	            <div class="TroboeCon">
	                <span class="troberName"><a id="member_channel_index_username" href="/wj/shop/member_center!index.action?tagid=logintag"></a>
	                <i id="member_channel_index_GradeIcon" title="" class="vip_k <s:property value="gradeIcon" />" ></i></span>
	            </div>
	            <dl class="trobelogdl TrobeIntegral">
	                <dt class="IntegralName">可用积分</dt>
	                <dd class="integraNum"><a href="/wj/shop/point!newList.action"><s:property value="member.currentValidatePoint" /></a></dd>
	                <dd>（冻结<s:property value="member.point" />）</dd>
	            </dl>
	            <dl class="trobelogdl TrobeIntegral2">
	                <dt class="trobelogdteg">优惠券</dt>
	                <dd class="integraNum"><a href="/wj/shop/coupon!queryCoupon.action"><s:property value="#request.memberChannelIndex.couponnum" /></a></dd>
	                <dd>（即将过期<s:property value="#request.memberChannelIndex.coupondatenum" />）</dd>
	            </dl>
	            <div class="clear trobeBor"></div>
	            <a href="<%=Config.getFrontServerContextPath()%>" class="trobeLink f_mi">购物赚积分</a><a href="/wj/shop/member_comment!queryComment.action" class="trobeLink f_mi">评价得积分</a>
	        </div>
	        </s:if>
	        <!-- 用户登录状态end-->
	        <!-- 用户未登录状态 -->
	        <s:if test="null==member">
	        <div class="trobe-loginNo">
	            <p class="login-Tit f_mi">登录查看特权</p>
	            <ul class="login-UlMes">
	                <li class="login-UlMes1">
	                    <span>注册得积分</span>
	                </li>
	                <li class="login-UlMes2">
	                    <span>会员赚积分</span>
	                </li>
	                <li class="login-UlMes3">
	                    <span>查看优惠券</span>
	                </li>
	            </ul>
	            <a id="member_channel_index_login" href="javascript:void(0)" class="trobeLink f_mi" onclick="javascript:void(0);return(VL_FileDL(this));return false;" exturl="http://www.kaixinbao.com/hylogin" vlpageid="hylogin">登录</a><a id="member_channel_index_reg" href="javascript:void(0)" class="trobeLink f_mi" onclick="javascript:void(0);return(VL_FileDL(this));return false;" exturl="http://www.kaixinbao.com/hylogin" vlpageid="hylogin">注册</a>
	        </div>
	        </s:if>
	        <!-- 用户未登录状态end -->
	    </div>
	    <!-- 用户等级进度 -->
	    <div class="tribe-conent">
	        <!-- 等级进度 -->
	        <div class="tribe-vip">
	            <!-- 等级分为
	                        *-不是用户 vipno
	                        *-普通用户 vipuser
	                        *-k1用户 vipK1
	                        *-k2用户 vipK2
	                        *-vip用户 vipVIP
	                        *根据不同用户来判断星星状态。
	                        -->
	            <div class="tribe-vip-con <s:property value="vipCon" />">
	                <ul class="tribe-vipUl">
	                    <li class="vip-K0">
	                    </li>
	                    <li class="vip-K1">
	                    </li>
	                    <li class="vip-K2">
	                    </li>
	                    <li class="vip-VIP">
	                	<s:if test='member.vipFlag=="Y"'>
	                        <i class="vip_ctions_des">
	                               尊贵的VIP会员：<br>您可享受购物额外5%积分返还！及更多vip专属特权优惠！</i>
	                    </s:if>
	                    <s:else>
	                    	<s:if test="member.grade eq 'K2' && #request.showData.dd3percent eq '100'">
		                        <div class="vip_ctions_des">
		                               亲爱的K2会员：<br>您可以<a href="javascript:void(0);" id="qqwap2">联系客服</a>申请成为VIP会员，VIP会员享受购物额外5%积分返还！
		                        </div>
		                    </s:if>
	                    </s:else>
	                    </li>
	                </ul>
	                <dl class="tribe-vip-dl">
	                    <dd><span class="vip-dd1" style="width:<s:property value="#request.showData.dd1percent" />%"></span>
	                    <s:if test="member.grade eq 'K0'">
	                    	<em class="vip_action">
	                    	<i class="vip_ctions_des" style="display:<s:property value="#request.showData.dd1POPUP" />"> 
	                    	亲爱的会员：<br>消费一次即可升级为K1会员，<br>K1会员可享受更多会员特权优惠。</i></em>
	                    </s:if>
	                    </dd>
	                    <dd><span class="vip-dd2" style="width:<s:property value="#request.showData.dd2percent" />%"></span>
	                    <s:if test="member.grade eq 'K1'">
	                    	<em class="vip_action">
	                    	<i class="vip_ctions_des" style="display:<s:property value="#request.showData.dd2POPUP" />"> 
	                    	亲爱的K1会员：<br>您再消费<s:property value="#request.showData.dd2Times" />次，且购买<s:property value="#request.showData.dd2Prem" />元，即可升级为K2会员，k2会员可享受购物额外2%积分返还！</i></em>
	                    </s:if>
	                    </dd>
	                    <dd><span class="vip-dd3" style="width:<s:property value="#request.showData.dd3percent" />%"></span>
	                    <s:if test="member.grade eq 'K2'">
	                    	<s:if test="#request.showData.dd3percent != '100'">
	                    	<em class="vip_action">
	                    	<i class="vip_ctions_des" style="display:<s:property value="#request.showData.dd3POPUP" />">
	                    	亲爱的K2会员：<br>您再购买<s:property value="#request.showData.dd3Prem" />元，即可升级为VIP会员，VIP会员享受购物额外5%积分返还！</i></em>
	                    	</s:if>
	                    </s:if>
	                    </dd>
	                </dl>
	            </div>
	        </div>
	        <!-- 等级进度end -->
	        <div class="tribe-viplist">
	           
	            <dl class="tribe-list-dl">
	                <dt><em class="tribeicon tribeicon6"></em></dt>
	                <dd class="tribe-listTitle">会员特价</dd>
	                <dd>
	                    <div class="vipTribeicons <s:property value="#request.showData.tribe5OK" />" style="display:none">
	                        <s:property value="#request.showData.tribeicon5desc" escape="false"/>
	                    </div>
	                </dd>
	            </dl>
	            
	            <dl class="tribe-list-dl">
	                <dt><em class="tribeicon tribeicon4"></em></dt>
	                <dd class="tribe-listTitle">活动专享</dd>
	                <dd>
	                    <div class="vipTribeicons <s:property value="#request.showData.tribe2OK" />" style="display:none">
	                        <s:property value="#request.showData.tribeicon2desc" escape="false"/>
	                    </div>
	                </dd>
	            </dl>
	            
	            <dl class="tribe-list-dl">
	                <dt><em class="tribeicon tribeicon1"></em></dt>
	                <dd class="tribe-listTitle">生日特权</dd>
	                <dd>
	                    <div class="vipTribeicons <s:property value="#request.showData.tribe0OK" />" style="display:none">
	                        <s:property value="#request.showData.tribeicon0desc" escape="false"/>
	                    </div>
	                </dd>
	            </dl>
	            
	             <dl class="tribe-list-dl">
	                <dt><em class="tribeicon tribeicon2"></em></dt>
	                <dd class="tribe-listTitle">节日特权</dd>
	                <dd>
	                    <div class="vipTribeicons <s:property value="#request.showData.tribe3OK" />" style="display:none">
	                        <s:property value="#request.showData.tribeicon3desc" escape="false"/>
	                    </div>
	                </dd>
	            </dl>
	            
                 <dl class="tribe-list-dl">
	                <dt><em class="tribeicon tribeicon3"></em></dt>
	                <dd class="tribe-listTitle">发票包邮</dd>
	                <dd>
	                    <div class="vipTribeicons <s:property value="#request.showData.tribe1OK" />" style="display:none">
	                        <s:property value="#request.showData.tribeicon1desc" escape="false"/>
	                    </div>
	                </dd>
	            </dl>
	            
	                <dl class="tribe-list-dl">
	                <dt><em class="tribeicon tribeicon5"></em></dt>
	                <dd class="tribe-listTitle">安全达人</dd>
	                <dd>
	                    <div class="vipTribeicons <s:property value="#request.showData.tribe4OK" />" style="display:none">
	                        <s:property value="#request.showData.tribeicon4desc" escape="false"/>
	                    </div>
	                </dd>
	            </dl>
	            
	        </div>
	    </div>
	    <!-- 用户未登录状态edn -->
	    <div class="clear h20"></div>
	    <!--广告 开始-->
	    <s:if test="#request.showData.campaign1!=null && #request.showData.campaign1!=''">
		    <div class="h10 graybordert"></div>
	        <div class="">
	        	<s:property value="#request.showData.campaign1" escape="false"/>
	        </div>
	    </s:if>
        <s:else>
        	<div class="tribe-ad">
		        <img src="<%=Config.getValue("StaticResourcePath")%>/images/redesign/ad_15.jpg" width="980px;" height="135px;" >
		    </div>
        </s:else>
        <div class="clear h20"></div>
        <!--广告 结束-->
        
        <!-- 其他栏目 -->
            <div class="tribe-other">
                <a href="/wj/shop/member_channel!grade.action" target="_blank"><img src="<%=Config.getValue("StaticResourcePath")%>/images/member/vip_03.gif" alt="" /></a>
                <a href="/wj/shop/member_channel!privilege.action" target="_blank"><img src="<%=Config.getValue("StaticResourcePath")%>/images/member/vip_05.gif" class="tribe-othero" alt="" /></a>
                <a href="/wj/shop/member_channel!point.action" target="_blank"><img src="<%=Config.getValue("StaticResourcePath")%>/images/member/vip_07.gif" alt="" /></a>
            </div>
        <!-- 其他栏目end -->
	    <div class="tribe-action floatl">
	        <h2 class="tribe-tits"><span class="tribe-titsc f_mi">会员活动</span></h2>
	        <!--广告 开始-->
		    <s:if test="#request.showData.campaign2!=null && #request.showData.campaign2!=''">
		        <div class="">
		        	<s:property value="#request.showData.campaign2" escape="false"/>
		        </div>
		    </s:if>
	        <s:else>
			    <img src="<%=Config.getValue("StaticResourcePath")%>/images/redesign/hd_20.jpg" width="506px;" height="270px;" >
	        </s:else>
	        <!--广告 结束-->
	    </div>
	    <div class="tribe-action tribe-action2  floatr">
	        <h2 class="tribe-tits"><span class="tribe-titsc f_mi">会员特价</span></h2>
	        <s:iterator id="product" value="#request.activityProduct1" status="status">
	        <div class="oneTribe">
	            <h3 class="oneTribeTitle ellipsis"><a href="<s:property value="#product.URL" />" target="_blank"><s:property value="#product.ProductName" /></a></h3>
	            <p class="oneTribeP"><s:property value="#product.AdaptPeopleInfo" /></p>
	            <ul class="recommend_list">
	            	<s:property value="#product.DutyHTML2" escape="false"/>
	            </ul>
	            <div class="TribePay">
	                <%-- <span class="TribePayVip f_mi">会员价￥<s:property value="#product.Prem" /></span>
	                <span class="TribePayY f_mi">原价￥<s:property value="#product.Remark4" /></span>
	                <a href="<s:property value="#product.URL" />" target="_blank" class="TribeGoTo box_round_4">查看详情</a> --%>
	                <div style="width:250px;"><s:property value="#product.ActivityLabel" escape="false"/></div>
	                <a href="<s:property value="#product.URL" />" target="_blank" class="TribeGoTo box_round_4">查看详情</a>
	            </div>
	        </div>
	        </s:iterator>
	        <s:if test="#request.activityProduct2 != null">
	        <div class="twoTribe">
	        	<s:iterator id="product" value="#request.activityProduct2" status="status">
	        	<dl class="twoTribeDl">
	                <dt>
	                    <a href="<s:property value="#product.URL" />" target="_blank">
	                    	<img src="<s:property value="#product.LogoLink" />" width="190px" height="190px" alt="">
	                    </a>
	                    <span class="ellipsis"><a href="<s:property value="#product.URL" />" target="_blank"><s:property value="#product.ProductName" /></a></span></dt>
	                  <%-- <dd><span class="f_mi fleft">原价￥<s:property value="#product.Remark4" /></span><span class="f_mi fright right twoTribePay">会员价 ￥<s:property value="#product.Prem" /></span></dd> --%>  
	              <span  ><s:property value="#product.ActivityLabel" escape="false"/> </span> 
	            </dl>
	        	</s:iterator>
	        </div>
	        </s:if>
	        
	    </div>
	    <div class="clear h20"></div>
	    <!-- 积分兑换  -->
	    <div class="vipExchange">
	        <h2 class="tribe-tits"><span class="tribe-titsc f_mi">积分兑换</span></h2>
	        <div class="vipExchangeList" id="points_product">
	        	<s:property value="#request.showData.products" escape="false"/>
	        	<s:property value="#request.showData.productsPageBar" escape="false"/>
	        </div>
	    </div>
	    <!-- 积分兑换  end-->
	    <div class="clear h20"></div>
	    <!-- 快速赚积分 -->
	    <div class="quickIntegral">
	        <h2 class="tribe-tits"><span class="tribe-titsc f_mi">快速赚积分</span></h2>
	        <div class="quickIntBox">
	            <div class="quick_box1">
	                <h3 class="quick-tit f_mi">推荐好友得积分 <a href="/wj/shop/member_recommend!query.action">查看详细说明&gt;</a></h3>
	                <p>活动内容：
	                    <br>1、开心保网为您生成专属推荐链接。
	                    <br> 2、通过您的分享链接，在被推荐人注册并验证手机后，您将获得<span class="hspan"><s:property value="#request.showData.recommRegCountPonits" />个积分</span>；推荐好友注册上限<span class="hspan"><s:property value="#request.showData.recommRegisterCounts" />人</span>。
	                    <br> 3、若此推荐与其他活动冲突时，以活动具体规则为准。
	                    <br>
	                </p>
	                <p>
	                    活动对象：
	                    <br>
	                    <i class="vip_k  vip_no" title=""></i><i class="vip_k  vip_k1 " title=""></i><i class="vip_k  vip_k2 " title=""></i><i class="vip_k  vip_kvip " title=""></i>
	                </p>
	                <a href="javascript:void(0)" id="vip_linkss" class="vipLink  box_round_4">发送专属推荐链接</a>
	                <a href="javascript:void(0)" id="vip_friend" class="vipLink  box_round_4">邮件推荐</a>
	            </div>
	            <div class="quick_box2">
	                <h3 class="quick-tit f_mi">完善资料获得积分 <span class="jfs_tit">最多可得<i class="hspan"><s:property value="#request.showData.pointCount" />积分</i>！</span></h3>
	                <div class="quick_box2des">
	                    <p>活动内容：
	                        <br> 在会员中心，完善个人信息即可获得更多积分奖励哦！
	                    </p>
	                    <ul class="quick_uls">
	                        <li class="quick_lis1">邮件：<span class="hspan"><s:property value="#request.showData.pointEmail" />积分</span></li>
	                        <li class="quick_lis2">手机：<span class="hspan"><s:property value="#request.showData.pointMobile" />积分</span></li>
	                        <li class="quick_lis3">性别：<span class="hspan"><s:property value="#request.showData.pointSex" />积分</span></li>
	                        <li class="quick_lis4">生日：<span class="hspan"><s:property value="#request.showData.pointBirthday" />积分</span></li>
	                    </ul>
	                    <p>活动对象：
	                        <br>
	                        <i class="vip_k  vip_no" title=""></i><i class="vip_k  vip_k1 " title=""></i><i class="vip_k  vip_k2 " title=""></i><i class="vip_k  vip_kvip " title=""></i>
	                    </p>
	                    <a href="/wj/shop/profile!edit.action" class="vipLink  box_round_4">立即完善资料赚积分</a>
	                </div>
	            </div>
	        </div>
	    </div>
	    <!-- 快速赚积分 end-->
	    <div class="clear h20"></div>
	    <!-- 会员动态 -->
	    <div class="vip-news">
	        <h2 class="tribe-tits"><span class="tribe-titsc f_mi">会员动态</span></h2>
			<div class="vip_dt_box">
				<div class="vip_user_box">
					<p class="vipdl-tit f_mi">新加入会员</p>
					<ul class="vip-New">
						<s:iterator id="m" value="#request.mc.memberInfo" >
							<li><span><s:property value="#m.createDate"/></span><em><s:property value="#m.registerNo"/></em></li>
						</s:iterator>
					</ul>
				</div>
				<div id="vip_mes" class="changeBox_mes">
					<s:iterator id="mcItem" value="#request.mc.pages" status="status">
					<s:if test="#status.index==0">
					<div class="change_mesdiv" style="display: block;">
					</s:if>
					<s:else>
					<div class="change_mesdiv" style="display: none;">
					</s:else>
						<div class="vipnew-box vipnew-boxs">
							<dl class="vip-mesdl">
								<dt class="vipdl-tit f_mi">刚刚评论</dt>
								<s:iterator id="c" value="#mcItem.comment" status="status">
									<s:if test="#status.index==2">
										<dd class="last">
									</s:if>
									<s:else>
										<dd>
									</s:else>
									<div class="vip-mes">
										<div class="vip-header"><span class="mem-headerimg"></span><img src="<s:property value="#c.headPicPath"/>" width="48px" height="48px;" ></div>
										<div class="vip-mesbox">
											<span><s:property value="#c.username"/><em class="ellipsis"><a href="<s:property value="#c.URL"/>" target="_blank"><s:property value="#c.ProductName"/></a></em></span>
											<p><s:property value="#c.comment"/></p>
										</div>
									</div>
									</dd>
								</s:iterator>
							</dl>
						</div>
					</div>
					</s:iterator>
					<ul class="ul_change_ems">
						<li><span class="on">&nbsp;</span></li>
						<li><span class="">&nbsp;</span></li>
						<li><span class="">&nbsp;</span></li>
						<li><span class="">&nbsp;</span></li>
						<li><span class="">&nbsp;</span></li>
					</ul>
				</div>
			</div>
	    </div>
	    <!-- 会员动态end -->
	</div>

	<div id="emaile_listf" style="display:none">
	  <ul class="email_list" id="">
	          <li>
	            <input type="text" class="friend_email" value="填写您的好友邮件地址即可" onfocus="if (this.value == '填写您的好友邮件地址即可') {this.value = '';}" onblur="verifyElement('电子邮箱|NOTNULL&amp;RECOEMAIL',this.value,this.id);" verify="电子邮箱|NOTNULL&amp;RECOEMAIL" id="tj_mail_">
	            <span class="add_email_bt">+ 增加地址</span><label class="requireField"></label>
	          </li>
	      </ul>
	  <a href="javascript:void(0)" class="recommend_a box_round_4" onclick="sendEmail()">发送</a>
	</div>
	
	<div class="vip_links" id="vip_link_box"  style="display:none">
	  <span name=""  class="recommend_gzs" id="clipboard"><s:property value="#request.showData.recommDesc" /><s:property value="#request.showData.recommUrl" />
	          </span>
	          <a href="javascript:void(0)" class="recommend_a copyLink" data-clipboard-target="clipboard">复制</a>
	</div>
	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.cookie.js"></script>
	<!-- js加载 -->
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/artDialog.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/ZeroClipboard.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/redesign/vip_tribe.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/VerifyInput.js"></script>
<script type="text/javascript">
	jQuery(function(){
		// 会员数据滚动
		jQuery('div.vip_user_box').myScroll({
			speed: 40, //数值越大，速度越慢
			rowHeight: 26 //li的高度
		});
	});
</script>
</body>
</html>
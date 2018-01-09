<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-我的积分</title>
<!--redesign/re_header.css头部脚部全站样式-->
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/jquery-1.4.2.min.js"></script>

<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body class="up-bg-qh">
	<!-- 顶部 开始 1-->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->
	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span> <a
				href='<%=Config.getFrontServerContextPath()%>' target='_self'><span
				class="orange">您现在的位置：首页</span>
			</a><span class="separator">> </span><a href='member_center!index.action'
				target='_self'>会员中心</a>> <span class="separator"></span><span
				class="separator1">我的积分</span>
		</div>
		<div class="member_con">
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right ">
				<s:iterator id="jfList" value="#request.integralList">
				<div class="m-integral-l">
	              	<h3 class="m-integral-tit"><span class="member-titsc">我的积分</span></h3>
		            <div class="m-int-my">
		              <span>总积分</span>
		              <b class="m-int-con"><s:property value="#jfList.sumPoint" /></b>
		            </div>
		            <dl class="m-inte-dl">
		              <dt class="m-yx">有效积分 <em><s:property value="#jfList.currentValidatePoint" /></em></dt>
		              <dd>在线投保可抵值<s:property value="#jfList.PointMoney" />元</dd>
		              <dd><a href="<%=Config.getFrontServerContextPath()%>" target="_blank" class="m-inte-a">去购买保险</a></dd>
		            </dl>
		            <dl class="m-inte-dl ">
		              <dt class="m-gq">即将过期 <em><s:property value="#jfList.aboutToExpirePoints"/></em></dt>
		              <dd>
			              <s:if test="#jfList.aboutToExpireDate == null || #jfList.aboutToExpireDate ==''">&nbsp;</s:if>
			              <s:else>将于<s:property value="#jfList.aboutToExpireDate"/>到期</s:else>
		              </dd>
		              <dd><a href="<%=Config.getFrontServerContextPath()%>/jifen/" target="_blank" class="m-inte-a">去商城兑换</a></dd>
		            </dl>
		             <dl class="m-inte-dl end-intedl">
		              <dt class="m-dj">冻结积分 <em><s:property value="#jfList.point" /></em></dt>
		              <dd>冻结积分暂不可用</dd>
		            </dl>
	       	 	</div>
	       	 	<div class="m-integral-r">
		            <div class="m-evaluate">
		              <a href="member_comment!queryComment.action" class="m-evaluate-a">您有<s:property value="#jfList.commentCount" />个商品待评价<br>可获得<em><s:property value="#jfList.commentPoints" /></em>积分</a>
		            </div>
		            <div class="m-shop">
		              <a href="<%=Config.getFrontServerContextPath()%>/jifen/" target="_blank"><em class="m-shop-icon"></em>积分商城</a>
		            </div>
		        </div>
	       	 	</s:iterator>
	       	 	<div class="clear h20"></div>
				<!-- 我的积分开始-->
				<div class="member_boxs">
					<!-- 我的积分开始-->
					<div class="member_orderlist">
						<h3 class="member_t_up">
							<ul id="member_t_tag" class="member_t_tag">
								<li class="tag_hsf hover"id="member_tabtitle0" onclick="doChangeArea(0);">积分兑换订单</li>
								<li id="member_tabtitle1" onclick="doChangeArea(1);">积分兑换保单</li> 
							</ul>
						</h3>
						<div class="dh_table" id="dh_tables">
							<div id="member_tabarea0">
							<jsp:include page="point_exchangeOrder.jsp"></jsp:include>
							</div>
							<div id="member_tabarea1" class="dh_tablecon" style="display:none">
								<div class="dh_zt">
									<a href="javascript:void(0);" id="selectAll" class="select_sp" onclick="queryOrder('','1');">全部</a>
									<a href="javascript:void(0);" onclick="queryOrder(11,'1');">待生效</a>
									<a href="javascript:void(0);" onclick="queryOrder(12,'1');">已生效</a>
									<a href="javascript:void(0);" onclick="queryOrder(13,'1');">已过期</a>
								</div>
								<div id="member_tabarea1_1">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="clear h20"></div>
				<div class="member_boxs">
					<div class="member-htit"><span class="member-titsc">积分明细</span></div>
					<div id="member_commantable">
					<jsp:include page="point_list.jsp"></jsp:include>
					</div>
				</div>
				<div class="clear h20"></div>
				<s:iterator id="jfList1" value="#request.integralList">
				<div class="member_boxs ">
					<div class="member-htit"><span class="member-titsc">什么是开心保积分？</span> </div>
					<div class="member-bosf">
						<p>开心保积分是开心保会员在开心保官方网站（www.kaixinbao.com）在线投保、评价、推荐好友注册购买等网站活动时给予的开心奖励。</p>
						<img src="<%=Config.getValue("StaticResourcePath") %>/images/vip/jf_03.jpg" alt="">
						
						<ul>
		                    <li>会员可以在“会员中心—我的积分”中可查询到开心保积分的详细情况。</li>
		                    <li>开心保会员需登录投保才能享受积分赠送优惠。</li>
		                    <li>车险产品、投资理财型保险产品暂时无法使用开心保积分抵值支付。</li>
		                    <li>积分不与其他优惠券、红包同时使用，在支付时，可以选择抵扣方式。</li>
		                    <li>积分存在有效期限制，会员积分有效期为<s:property value="#jfList1.pointPeriod" />，积分按月判断是否过期，即从积分生效时间点记录<s:property value="#jfList1.pointPeriod" />，在到期自然月的最后一天24点进行清零。</li>
		                    <li>会员在使用积分支付时，当前订单依然享受积分返还，返还比例以实际支付金额为参照。</li>
		                    <li>会员发生退保时，开心保网站将对相应的积分进行收回。</li>
		                </ul>
		                
		                <p>
		                  <br>
		                  <b>特别提示：</b> <br>
				          <ul>
				            <li>开心保积分目前只能在开心保网站使用。</li>
				            <li>若所购买的产品仍有赠送积分活动时，使用积分抵值或优惠减免的部分金额不奖励积分。</li>
				            <li>保单生效前撤销订单，用于该订单抵值的积分在撤单完成后返还。保单生效后办理退保，抵值积分无法返还。</li>
				          </ul>
		                </p>
					</div>
					<div  class="member-htit" id="gm_id2"><span class="member-titsc">如何获得积分？</span> </div>
                    <div class="member-intdes2">
                      <h3 class="member-intdes-t">（一）评价奖励</h3>
                        <ul class="member-intdes-ul clear_bor-r"><li>获得条件：会员成功购买后，完成订单支付，即可以对产品及服务进行评论打分获得积分奖励。</li>
                        <li>获得时间：符合规则的评论提交后，积分即发放至会员账户中。</li>
                        <li>积分计算：每份订单可以评论一次，每次赠送<s:property value="#jfList1.commPoint" />个开心保积分。</li>
                       </ul>
                    </div>
                    <div class="member-intdes3">
                      <h3 class="member-intdes-t">（二）推荐奖励</h3>
                        <ul class="member-intdes-ul clear_bor-r"><li>开心保网为您生成专属推荐链接。</li>
                        <li>通过您的分享链接，在被推荐人注册并验证手机后，您将获得<s:property value="#jfList1.recommRegCount" />个积分奖励。推荐好友注册上限<s:property value="#jfList1.recommRegisterCount" />人。</li>
                        <li>若此推荐与其他活动冲突时，以活动具体规则为准。</li>
                       </ul>
                    </div>
                    <div class="clear h40"></div>
                </div>
                </s:iterator>
				<!-- 我的积分结束-->
			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
	<script>
	function queryOrder(orderStatus, pageindex) {
		jQuery("#member_tabarea1_1").load('<%=serverContext%>/shop/order_query!memberquerychangeorder.action?orderStatus='+orderStatus+'&page='+pageindex);
	}
	function doChangeArea(intObj) {
		var allSpanTitle = document.getElementById("member_t_tag")
				.getElementsByTagName("li");
		for (var i = 0; i < allSpanTitle.length; i++) {
			var includePage = "member_tabarea" + i;
			document.getElementById(includePage).style.display = "none";
		}
		var selectdPage = "member_tabarea" + intObj;
		document.getElementById(selectdPage).style.display = "block";
		if (intObj == 0) {
			jQuery("#member_tabarea0").load('<%=serverContext%>/shop/point!exchangeOrder.action');
		}
		if (intObj == 1) {
			jQuery("#selectAll").addClass("hover").siblings().removeClass('hover');
			queryOrder('','1');
		}
	}
	function del(Id){
		art.dialog({
	        id:'shaop_car',
	        title: '提示',
	        content: '<div style="padding:10px 40px;">您确认要取消吗?</div>',
	        fixed: true,
	        button: [
	            {
	                name: '确认',
	                callback: function () {
	                	var orderStatus=jQuery("#orderStatus").val();
	                	jQuery.ajax({
							    url:"<%=serverContext %>/shop/order_config_new!cancel.action?orderSn="+Id+"&orderStatus="+orderStatus,
							    type:"get",
								dataType:"json",
								async: false,
								success:function(data) {
									if (data.status == "Y") {
										window.location.reload();
									}else{
										art.dialog({ icon: 'error', content: '取消失败！' });
										return;
									}
								}
						   });  
	                }
	            },
	            {
	                name: '取消'
	             }
	        ]
	    });
	}
	function xiajia(orderSn,orderStatus,isPublish){
		if(isPublish=="N"){
			art.dialog({ icon: 'warning', content: '此产品已下架!' });
			return false;
		}else{
			location.href="order_config_new!update.action?orderStatus="+orderStatus+"&orderSn2="+orderSn;
		}
	}

	function buyNow1(isPublish,linkurl){
		if(isPublish=="N"){
			art.dialog({ icon: 'warning', content: '此产品已下架!' });
			return false;
		}else{
			window.open(linkurl,"_blank");
		}
	}
	/*删除订单*/
	function dellorder(ordersn){
			 art.dialog({
				                 id:'shaop_car',
				                 title: '提示',
				                 content: '<div style="padding:10px 40px;">您确认要删除订单吗?</div>',
				                 fixed: true,
				                 button: [
				                     {
				                         name: '确认',
				                         callback: function () {
				                         	jQuery.ajax({
												    url:"<%=serverContext %>/shop/order_query!dellMemberCenterOrder.action?orderSn="+ordersn+"&pointExchangeFlag=1",
												    type:"post",
													dataType:"json",
													success:function(data) {
														if (data.status == "Y") {
															location.href="point!exchangeOrder.action";
																
														}else if(data.status == "NOLOG"){
															art.dialog({ icon: 'warning', content: '请重新登录!' });
																return;
														}else if(data.status == "NOORDER"){
															art.dialog({ icon: 'error', content: '订单信息错误，请重新登录!' });
																return;
														}else{
															art.dialog({ icon: 'error', content: '会员与订单信息不匹配，请重新登录!' });
																return;
														}
													}
											   });  
				                         }
				                     },
				                     {
				                         name: '取消'
				                      }
				                 ]
				             });
	};
	jQuery(document).ready(function() { 
	    var intver  = jQuery(".m-int-con");
	    if(intver.html().length>5){
	        intver.css("font-size","21px");
	    }else if(intver.html().length>5){
	        intver.css("font-size","25px");
	    }
	})
	</script>
</body>

</html>


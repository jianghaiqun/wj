<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script  type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/mobile.js"></script>
<script type="text/javascript">
uaredirect("http://wap.kaixinbao.com/mobile/member.html");
</script>
<title>会员首页</title>
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css"/>
<!--default.css artdialog样式-->
<link href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>  
<body class="up-bg-qh">
	<!-- 顶部 开始 1-->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->
	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span>
			<a href='<%=Config.getFrontServerContextPath()%>' target='_self' ><span class="orange">您现在的位置：首页</span></a><span class="separator">>
			</span><a href='member_center!index.action' target='_self'>会员中心</a>
			<span class="separator">></span><span class="separator1">会员中心首页</span>
		</div>
		<!-- 首页顶部 -->
		<div class="member-head">
			<div class="member-remes">
				<div class="member-head-img">
					<s:iterator id="meList" value="#request.memberHead">
						<s:if test="#meList.headPicPath == null || #meList.headPicPath ==''">
							<img src="<%=Config.getValue("StaticResourcePath")%>/images/header_mo_03.jpg" width="84px" height="84px" alt=""/> 
						</s:if>
						<s:else>
							<img src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="#meList.headPicPath"/>" width="84px" height="84px" alt=""/> 
						</s:else>
					</s:iterator>
					<span class="mem-rideobg"></span>
				</div>
				<form id="queryMember" action="member_center!index.action" method="post"></form>
				<s:iterator id="mList" value="#request.memberIndexList">
				<div class="member-headmes">
					<h3 class="member-name f_mi">
						<s:if test="#mList.realName =='' || #mList.realName==null">开心保会员</s:if>
						<s:else><s:property value="#mList.realName"/></s:else> 	
					</h3>
					<div class="member-head-l">
						<i class="<s:property value="#mList.gradeClass"/>"></i><s:property value="#mList.gradeDesc"/>
						 <p class="member-head-p"><s:property value="#mList.upgradeInfo"/></p>
					</div>
					<div class="member-head-r">
						<p>
							<span class="member-hi">邮箱：</span>
							<font class="member_detail" title="<s:property value="#mList.email"/>">
								<s:if test="#mList.email =='' || #mList.email==null"> 请验证您的邮箱 </s:if>
								<s:else><s:property value="#mList.email"/></s:else>
                            </font>
                            <s:if test="#mList.isEmailBinding==\"Y\"">
	                            <font class="member_verified">已验证</font>
	                            <span id="member_email_update" class="member_Immediatelyfasten">
	                            	  <s:if test="#mList.registerType!=0">
                           				 <a href="###" onclick="binding('emailModify');">修改</a>
	                            	  </s:if>
                          	    </span>
                            </s:if>
                          	<s:else>
                          		<font class="member_verified">未验证</font>
                          		<span class="member_Immediatelyfasten" id="member_email"><a href="javascript:void(0)" onclick="binding('email');">验证</a></span>
                          	</s:else>
						</p>
						<p>
							<span class="member-hi">手机：</span>
							<font class="member_detail">
								<s:if test="#mList.mobileNO ==null || #mList.mobileNO ==''">请验证您的手机号码</s:if>
								<s:else>
	                            	<s:property value="#mList.mobileNO.substring(0,3)"/>****<s:property value="#mList.mobileNO.substring(7,11)"/>
                            	</s:else>
							</font>
							<s:if test="#mList.isMobileNOBinding==\"Y\"">
	                            <font class="member_verified">已验证</font>
	                            <span class="member_Immediatelyfasten" id="member_immer_update">
                           		<s:if test="#mList.registerType!=1">
                           			<a href="javascript:void(0)" onclick="binding('phoneModify');">修改</a>
	                            </s:if>
                            	</span>
                            </s:if>
                          	<s:else>
                          		<font class="member_verified">未验证</font>
                            	<span class="member_Immediatelyfasten" id="member_imme">
                          	    <a href="javascript:void(0)" onclick="binding('phone');">验证</a></span>
                          	</s:else>
						</p>
					</div>
				</div>
				<input type="hidden" value="<%=Config.getFrontServerContextPath()%>" id="localhost"/>
                <input type="hidden" name="oldtime2" id="olddate3"/>
				<input type="hidden" name="timet" id="timest" value="0"/>
                <input type="hidden" id="emailId" value="<s:property value="#mList.email"/>"/>
                <input type="hidden" id="memberId" value="<s:property value="#mList.id"/>"/>
                <input type="hidden" id="mailBinding" value="<s:property value="#mList.isEmailBinding"/>"/>
                <input type="hidden" id="mobileNOBinding" value="<s:property value="#mList.isMobileNOBinding"/>"/>
                <input type="hidden" id="isfirstMC" value="<s:property value="isfirstMC"/>"/>
                <input type="hidden" id="isfirstMCAfterUngrade" value="<s:property value="isfirstMCAfterUngrade"/>"/>
                <input type="hidden" id="phone" value="<s:property value="#mList.mobileNO"/>" />
                <input type="hidden" name="times" id="timess" value="0"/>
                <input type="hidden" name="oldtime" id="olddate"/>
                <input type="hidden" id="isFirstLoginAutoReg" value="<s:property value="#request.isFirstLoginAutoReg"/>"/>
                <input type="hidden" name="realName" id="realName" value="<s:property value="#mList.realName"/>"/>
				</s:iterator>
			</div>
			<div class="member-yhjdes">
				<a href="coupon!queryCoupon.action" class="a_yhjdes">全部优惠券&gt;</a>
				<s:if test="#request.couponList==null || #request.couponList.size() == 0">
					<div class="no-member-yhjdes f_mi">
						<span class="no-mem-p">目前没有优惠券</span>
	                    <a href="<%=Config.getFrontServerContextPath()%>" target="_blank"><span class="no-mem-cs">参加活动<br>获取优惠券</span></a>
	                </div>
				</s:if>
				<s:else>
					<div class="no-member-yhjdes f_mi" style="display:none">
						<span class="no-mem-p">目前没有优惠券</span>
	                    <a href="<%=Config.getFrontServerContextPath()%>" target="_blank"><span class="no-mem-cs">参加活动<br>获取优惠券</span></a>
	                </div>
	                <div class="member-yhjdes-con">
		   				<div class="changeBox_yhj" id="change_yhj">
		   					<s:iterator id="mList" value="#request.couponList" status="status" >
		   					<a href="coupon!queryCoupon.action">
		                	<div class="changecon">
                                <div class="member-cons">
                                    <p class="home-yhq-tit"><em><s:property value="#mList.unit"/></em><span><s:property value="#mList.parValue"/></span></p>
                                    <p class="home-yhq-tit2"><em><s:property value="#mList.couponType"/></em> <span><s:property value="#mList.shortName"/></span></p>
                                    <div class="clear"></div>
                                    <p class="home-yhq-des">有效时间:<s:property value="#mList.startDate"/>　至　<s:property value="#mList.endDate"/></p>
                                </div>
                            </div>
                            </a>
                            </s:iterator>
                            <a href="###" class="a_last" title="上一个">上一个</a><a href="###" class="a_next" title="下一个">下一个</a>
		                </div>
	                </div>
				</s:else>
			</div>
			<div class="clear"></div>
			<s:iterator id="mList" value="#request.memberIndexList">
			<div class="member-h-order">
                <h3 class="h-order-tit h-order-bor"><span class="member-titsc">我的保单</span></h3>
                <dl class="m-orderlist">
                  <dt class="f_mi"><s:property value="#mList.storeCount"/></dt>
                  <dd class="m-order-icon m-order-ac1"></dd>
                  <dd>暂存的保单<a href="order_query!queryOrder.action?orderStatus=4"></a></dd>
                </dl>
                <dl class="m-orderlist">
                  <dt class="f_mi" id="dPayCountDt">0</dt>
                  <dd class="m-order-icon m-order-ac2"></dd>
                  <dd>待支付订单<a href="order_query!queryOrder.action?orderStatus=5"></a></dd>
                </dl>
                <dl class="m-orderlist">
                  <dt class="f_mi" id="noEffectCountDt">0</dt>
                  <dd class="m-order-icon m-order-ac3"></dd>
                  <dd>待生效保单<a href="order_query!queryNoEffectOrder.action"></a></dd>
                </dl>
                <dl class="m-orderlist">
                  <dt class="f_mi" id="commentNumDt">0</dt>
                  <dd class="m-order-icon m-order-ac4"></dd>
                  <dd>待评价保单<a href="member_comment!queryComment.action"></a></dd>
                </dl>
            </div>
            <div class="member-h-integral">
                <h3 class="h-order-tit"><span class="member-titsc">我的积分</span><a href="point!newList.action#gm_id2" class="m-h-intergralm">如何赚积分?</a></h3>
              	<div class="m-h-intergral-con">
                  <dl class="m-h-int-dl"><a href="point!newList.action">
                    <dt><s:property value="#mList.currentValidatePoint"/></dt>
                    <dd>有效积分</dd></a>
                  </dl>
                   <dl class="m-h-int-dl m-h-inta"><a href="point!newList.action">
                    <dt><s:property value="#mList.point"/></dt>
                    <dd>冻结积分</dd></a>
                  </dl>
                   <dl class="m-h-int-dl m-h-inta"><a href="point!newList.action">
                    <dt><s:property value="#mList.aboutToExpirePoints"/></dt>
                    <dd>即将过期</dd></a>
                  </dl>
                  <dl class="m-h-int-dl m-h-int-dlint"><a href="<%=Config.getFrontServerContextPath() %>/jifen/" target="_blank">
                    <dt class="int-iconsf"></dt>
                    <dd>积分兑换商品</dd></a>
                  </dl>
              	</div>
            </div>
            </s:iterator>
      	</div>
    	<!-- 首页顶部end-->
		<div class="member_con">
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right">
				<div class="member_boxs">
					<div class="member-htit"><span class="member-titsc">近期订单</span><a href="order_query!queryOrder.action" class="member-a-more">全部订单&gt;</a></div>
					<div class="clear h20"></div>
					<div id="member_commantable1">
						<jsp:include page="order_query_newlist.jsp"></jsp:include>
					</div>
				</div>
				<div class="clear h20"></div>
				<div class="member-browse">
					<h3 class="member_t_up">
						<ul class="member_t_tag" id="member_t_tag">
							<li class="tag_hsf hover">最近浏览</li><li>我的收藏</li>
						</ul>
					</h3>
                    <div id="browse-consf">
                    	<div class="m-browsebox" id="browse_record">
                    		<s:if test="#request.memberBrowseList==null || #request.memberBrowseList.size() == 0">
                    			<div class="no-mborwse"><a href="<%=Config.getFrontServerContextPath()%>">还没浏览过商品，去逛逛吧！~</a></div>
                    		</s:if>
                    		<s:else>
                    			<a href="javascript:delBrowsRecord();" class="clear_active" >清空浏览记录</a>
                    			<s:iterator id="mList" value="#request.memberBrowseList" status="status" >
                    			<s:if test="(#request.memberBrowseList.size()-1)==#status.index">
                    				<dl class="m-browsedl cf no-clear-bors">
                    			</s:if>
                    			<s:else>
                    				<dl class="m-browsedl cf">
                    			</s:else>
					                <dt class="m-browse-logo"><div class="icon_C<s:property value="#mList.CompanyCode"/> m-list-logos"><a href="<s:property value="#mList.URL"/>" target="_blank"></a></div></dt>
					                <dd class="m-browse-dd">
					                    <h3><a href="<s:property value="#mList.URL"/>" target="_blank"><s:property value="#mList.ProductName"/></a></h3>
					                    <p>适合人群：<s:property value="#mList.AdaptPeopleInfoV3"/></p>
					                    <span class="f_mi">￥<s:property value="#mList.InitPrem"/></span>
					                </dd>
					            </dl>
					            </s:iterator>
                    		</s:else>
                 		</div>
                 		<div class="m-browsebox" style="display:none;">
                 			<s:if test="#request.memberStowList==null || #request.memberStowList.size() == 0">
                    			<div class="no-mborwse"><a href="<%=Config.getFrontServerContextPath()%>">无收藏，去逛逛吧！~</a></div>
                    		</s:if>
                    		<s:else>
                    			<s:iterator id="mList" value="#request.memberStowList" status="status" >
                    			<s:if test="(#request.memberStowList.size()-1)==#status.index">
                    				<dl class="m-browsedl cf no-clear-bors">
                    			</s:if>
                    			<s:else>
                    				<dl class="m-browsedl cf">
                    			</s:else>
					                <dt class="m-browse-logo"><div class="icon_C<s:property value="#mList.CompanyCode"/> m-list-logos"><a href="<s:property value="#mList.URL"/>" target="_blank"></a></div></dt>
					                <dd class="m-browse-dd">
					                    <h3><a href="<s:property value="#mList.URL"/>" target="_blank"><s:property value="#mList.ProductName"/></a></h3>
					                    <p>适合人群：<s:property value="#mList.AdaptPeopleInfoV3"/></p>
					                    <span class="f_mi">￥<s:property value="#mList.InitPrem"/></span>
					                </dd>
					            </dl>
					            </s:iterator>
                    		</s:else>
                 		</div>
                 	</div>
                 </div>
                 <div class="member-question"> <h3 class="member_t_up"><b>常见问题</b><a href="point!queryPointsDesc.action" target="_blank">更多&gt;</a></h3>
			         <ul class="member_qa_list">
			         <s:iterator id="deList" value="#request.pointsDescList">
            			 <li class="member_qa_li"><a href="point!queryPointsDesc.action?id=<s:property value="#deList.ID" />" target="_blank"><s:property value="#deList.Title" /></a></li>
            		 </s:iterator>
			         </ul>
			     </div>
			     <div class="clear h20"></div>
			     <div class="member_boxs" id="products">
			     </div>
			 </div>
         </div>
     </div>
<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
<!-- 底部结束 -->
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.cookie.js"></script>
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/template/shop/js/member.js"></script>
<script type="text/javascript">


    window.onload=function(){
    	
    	jQuery("#member_commantable1").load(sinosoft.base+'/shop/order_query!queryOrderlist.action?callType=1&newPageSize=5');
    	
    	jQuery.ajax({
			url: "member_center!ajaxProductNew.action",
			type: "post",
			dataType: "json",
			success: function(data){
				var obj = eval(data);
				if (obj.products != '') {
					jQuery("#products").html(obj.products);
				} else {
					jQuery("#products").hide();
				}
				 
		 	}			
		});
    };
	function submitp(str) {
		jQuery.ajax({
			type : 'get',
			url : 'favorites!add.action?productId='
					+ str,
			dataType : 'html',
			success : function(de) {

				if (de == 'notlogin') {
					artLogin();//使用弹出窗口登录
				} else {
					alert(de);
				}
			}
		});
	}
	jQuery('#change_yhj div.changecon').soChange({
	      slideTime:0,  
	      botPrev:'.a_last',
	      botNext:'.a_next',
	      autoChange:false
	});
	function chakan(str)
	{
		var path = jQuery("#localhost").val();
		window.open(path+"/"+str);
	}

	function uploadImgMessage(message){
			alert(message);
		}
	
</script>


<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/new_member.js"></script>
</body>

</html>

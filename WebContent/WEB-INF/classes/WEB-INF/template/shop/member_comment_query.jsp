<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
	String orderSn = request.getParameter("orderSn");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-商品评论</title>
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<script type="text/javascript"
	src="<%=Config.getValue("JsResourcePath")%>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<script type="text/javascript">
	function jumppage(pageindex) {
		location.href="member_comment!queryComment.action?page="+pageindex; 
	}

	function hideMessage(orderSn) {
		jQuery("#error_" + orderSn).hide();
	}
</script>
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
				class="orange">您现在的位置：首页</span> </a><span class="separator">></span><a
				href='member_center!index.action' target='_self'>会员中心</a><span
				class="separator">></span><span class="separator1">待评价订单</span>
		</div>
		<div class="member_con">
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right bor_sild">
				<div class="member_boxs">
					<!-- 商品评论开始-->
					<div class="member-htit"><span class="member-titsc">待评价订单</span></div>

					<div class="member_evaluate">
						<table width="100%" border="1">
							<tr>
								<th scope="col" width="270px">商品信息 <input type="hidden"
									value="<s:property value="page"/>" name="pc" id="pg" /> <input
									type="hidden" value="<s:property value="lastpage"/>" name="lpg"
									id="lg" /><input type="hidden"
									value="<s:property value="notCommOrderSn"/>"
									name="notCommOrderSn" id="notCommOrderSn" />
								</th>
								<th scope="col" width="138px">购买时间</th>
								<th scope="col" width="138px">评价</th>
								<th scope="col" width="138px">获取奖励</th>
							</tr>
							<s:iterator id="list" value="#request.listComment">
								<tr>
									<td>
										<div class="member_img_bxo cf">
											<a target="_blank" href="<s:property value="#list.URL"/>">
												<img src="<s:property value="#list.imgPath"/>" width="68px;"
												height="68px;" alt="" class="member_cp_img"> </a> <a
												target="_blank" href="<s:property value="#list.URL"/>"
												class="member_links"><s:property
													value="#list.productName" />
											</a>
										</div></td>
									<td><s:property value="#list.buyDate" />
									</td>
									<td><span
										id="commented_<s:property value="#list.orderSn" />"
										style="display: <s:property value="#list.commentDis1"/>">已评价</span>
										<span id="notcomment_<s:property value="#list.orderSn" />"
										style="display: <s:property value="#list.commentDis2"/>"
										class="<s:property value="#list.commentClass"/>" 
										onclick="showComment('<s:property value="#list.orderSn" />')">发表评论</span>
									</td>
									<td><span
										id="Integral_<s:property value="#list.orderSn" />"
										style="display: <s:property value="#list.commentDis3"/>">
											已获得<span class="red"
											id="points_<s:property value="#list.orderSn" />"> <s:property
													value="#list.Integral" /> </span>积分 </span>
										<a class="kxg_change" id="a_<s:property value="#list.orderSn" />" 
										style="display: <s:property value="#list.commentDis3"/>"
										href="<%=Config.getFrontServerContextPath()%>/jifen/" target="_blank">立即兑换</a>
										<span id="Integral1_<s:property value="#list.orderSn" />" style="display: <s:property value="#list.commentDis2"/>">
											评价成功可获得<br>
										<b class="red"><s:property
													value="#list.Integral" /> </b>个积分 </span></td>
								</tr>

								<tr id="tr_comment_<s:property value="#list.orderSn" />" class="pl_con_boxs" style="<s:property value="#list.commentDisplay"/>" >
									<td colspan="4" width="740px">

										<table border="0" class="cp_pl_table cf">
											<tbody>
												<tr id="scoreTr" style="display: ">
													<td colspan="4">
														<div class="order-xing ">
															<div class="shop-rating">
																<span class="title">保障范围</span>
																<ul id="stars1_<s:property value="#list.orderSn" />" class="rating-level">
																	<li><a href="#" star:value="1" class="one-star">1</a>
																	</li>
																	<li><a href="#" star:value="2" class="two-stars">2</a>
																	</li>
																	<li><a href="#" star:value="3" class="three-stars">3</a>
																	</li>
																	<li><a href="#" star:value="4" class="four-stars">4</a>
																	</li>
																	<li><a href="#" star:value="5" class="five-stars">5</a>
																	</li>
																</ul>

																<span id="stars1_<s:property value="#list.orderSn" />_tips" class="result"></span> 
																<input type="hidden" size="" value="" name="stars1_<s:property value="#list.orderSn" />_input"
																id="stars1_<s:property value="#list.orderSn" />_input" />
															</div>
															<div class="shop-rating">
																<span class="title">保障程度</span>
																<ul id="stars2_<s:property value="#list.orderSn" />" class="rating-level">
																	<li><a href="#" star:value="1" class="one-star">1</a>
																	</li>
																	<li><a href="#" star:value="2" class="two-stars">2</a>
																	</li>
																	<li><a href="#" star:value="3" class="three-stars">3</a>
																	</li>
																	<li><a href="#" star:value="4" class="four-stars">4</a>
																	</li>
																	<li><a href="#" star:value="5" class="five-stars">5</a>
																	</li>
																</ul>
	
																<span id="stars2_<s:property value="#list.orderSn" />_tips" class="result"></span>
																<input type="hidden" size="" value="" name="stars2_<s:property value="#list.orderSn" />_input"
																	id="stars2_<s:property value="#list.orderSn" />_input">
															</div>
															<div class="shop-rating">
																<span class="title">保障价格</span>
																<ul id="stars3_<s:property value="#list.orderSn" />" class="rating-level">
																	<li><a href="#" star:value="1" class="one-star">1</a>
																	</li>
																	<li><a href="#" star:value="2" class="two-stars">2</a>
																	</li>
																	<li><a href="#" star:value="3" class="three-stars">3</a>
																	</li>
																	<li><a href="#" star:value="4" class="four-stars">4</a>
																	</li>
																	<li><a href="#" star:value="5" class="five-stars">5</a>
																	</li>
																</ul>
	
																<span id="stars3_<s:property value="#list.orderSn" />_tips" class="result"></span>
																<input type="hidden" size="" value="" name="stars3_<s:property value="#list.orderSn" />_input"
																	id="stars3_<s:property value="#list.orderSn" />_input">
															</div>
															<div class="shop-rating">
																<span class="title">售后服务</span>
																<ul id="stars4_<s:property value="#list.orderSn" />" class="rating-level">
																	<li><a href="#" star:value="1" class="one-star">1</a>
																	</li>
																	<li><a href="#" star:value="2" class="two-stars">2</a>
																	</li>
																	<li><a href="#" star:value="3" class="three-stars">3</a>
																	</li>
																	<li><a href="#" star:value="4" class="four-stars">4</a>
																	</li>
																	<li><a href="#" star:value="5" class="five-stars">5</a>
																	</li>
																</ul>
	
																<span id="stars4_<s:property value="#list.orderSn" />_tips" class="result"></span>
																<input type="hidden" size="" value="" name="stars4_<s:property value="#list.orderSn" />_input"
																	id="stars4_<s:property value="#list.orderSn" />_input">
															</div>
															<span class="error_red" id="stars_<s:property value="#list.orderSn" />_error"></span>
														</div>
														<div class="order-xing-pl">
										                    <div class="order-jsf-bor">
											                    <span class="order-muds">投保目的 </span>
											                    <span class="order-muds order-muds2" id="purpose_<s:property value="#list.orderSn" />_span" >
											                    <s:select name="purpose" list="#list.purpose" label="投保目的" headerKey="" headerValue="请选择"  value="" ></s:select>
												                </span>
												                <div class="clear h10"></div>
											                    <textarea class="order-plsf" id="content_<s:property value="#list.orderSn" />" name="content_<s:property value="#list.orderSn" />" 
											                    onfocus="if(this.innerHTML=='产品是否给力？分享你的购买心得给大家吧!'){this.innerHTML='';this.style.color='#000'}" 
											                    onblur="if(this.innerHTML==''){this.innerHTML='产品是否给力？分享你的购买心得给大家吧!';this.style.color='#D1D1D1'}" 
											                    onkeyup="hideMessage(<s:property value="#list.orderSn" />);">产品是否给力？分享你的购买心得给大家吧!</textarea>
										                    </div> <span class="pl_error" id="error_<s:property value="#list.orderSn" />" style="display: none"></span>
										                    <input type="button" class="re-btnts f-mi" value="提交" 
										                    id="cp_pl_btns_<s:property value="#list.orderSn" />" 
										                    onclick="submitComment('<s:property value="#list.orderSn" />');">
										                </div>
										                <div class="clear h10"></div>
													</td>

												</tr>
											</tbody>
										</table>
									</td>
								</tr>
							</s:iterator>
						</table>
					</div>
					<s:if test="listComment.isEmpty()">
					<div class="no-shop"><div class="no-shop-tip"><a href="<%=Config.getFrontServerContextPath() %>">快去购买产品再来评价吧~</a></div></div>
					</s:if>
					<s:if test="#request.lastpage > 1">
					<div class="plpage">
						<!--    翻页    -->
						<div  class="page_area">
							<div id="pagination">
								<ul>
									<s:if test="#request.page==1">
										<li class="page_prev"><a href="#"><span class="default">上一页</span></a></li>
									</s:if>
									<s:else>
										<li class="page_prev"><a href="javascript:jumppage('<s:property value="#request.page-1"/>')"><span class="">上一页</span></a></li>
									</s:else>
									<s:iterator id="pageFootMap" status="st" value="#request.pageFootList">
						        		<li class="<s:property value="#pageFootMap.class" />">
						        		<s:if test="#pageFootMap.index==\"...\"">
						        			<span><s:property value="#pageFootMap.index"/></span>
						        		</s:if>
						        		<s:else>
						        			<a href="javascript:jumppage('<s:property value="#pageFootMap.index"/>')">
							        			<span><s:property value="#pageFootMap.index"/></span>
							        		</a>
						        		</s:else>
						        		</li>
						        	</s:iterator>
						        	<s:if test="#request.page==#request.lastpage || #request.lastpage == 0">
										<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
									</s:if>
									<s:else>
										<li class="page_next"><a href="javascript:jumppage('<s:property value="#request.page+1"/>')"><span class="">下一页</span></a></li>
									</s:else>
								</ul>
							</div>
						</div>
					</div>
					<div class="clear h20"></div>
					</s:if>
					<!-- 商品评论结束-->
									<div class="hot_convert">
									<div class="member-htit"><span class="member-titsc">热门兑换</span></div>
					
					<ul class="jf_hotlist clearfix">
					    <s:iterator id="list" value="#request.listExchange">
						<li class="jf_hotli">
							<a href="<s:property value="#list.exchangeUrl" />" target="_blank"><img src="<s:property value="#list.logoUrl" />" class="jf_hot_tj" width="127px" height="100px"  alt=""></a>
							<div class="jf_hotbox">
									<a href="<s:property value="#list.exchangeUrl" />" target="_blank" class="jf_tit_link"><s:property value="#list.giftName" /></a>
									<span class="jf_icon"><s:property value="#list.points" />积分</span>
									<a href="<s:property value="#list.exchangeUrl" />" class="jf_dh" target="_blank">兑换</a>
							</div>
						</li>
						</s:iterator>
					</ul>
				</div>
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
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/new_member.js"></script>
</body>
<script type="text/javascript">
jQuery(document).ready(function(){
	var orderSn =<%=orderSn%>;
	if(orderSn!=null&&orderSn!=""){
		showComment(orderSn);
	} 
});

</script>
</html>
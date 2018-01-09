<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext(); 
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-机票订单</title> 
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<script type="text/javascript">
	
 	function del(Id){
 		if(confirm("您确认要取消吗?")){
 			location.href="order_config_new!cancelTripOrder.action?orderSn="+Id;
 			return false;
 		}else{
 			return false;
 		}
 	}
	
 	function jumppage(pageindex) {
 		location.href="order_query!memberQueryTripOrder.action?page="+pageindex; 
 	}

</script>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body class="up-bg-qh">
	<input type="hidden" value="<s:property value="page"/>" name="pc"
	id="pg" />
	<input type="hidden" value="<s:property value="lastpage"/>" name="lpg"
		id="lg" />
	<input type="hidden" value="<s:property value="orderStatus"/>" name="orderStatus"
		id="orderStatus" />
	<!-- 顶部 开始 1-->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->
	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span>
			<a href='http://www.kaixinbao.com' target='_self' ><span class="orange">您现在的位置：首页</span></a>
			<span class="separator">></span>
			<a href='member_center!index.action' target='_self'>会员中心</a>
			<span class="separator">></span><span class="separator1">机票订单</span>
		</div>
		<div class="member_con">
			<!-- 会员中心左侧菜单导航 -->
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right bor_sild">
				<div class="member_boxs">
					<div class="member-htit">
				 	    <span class="member-titsc">机票订单</span>
				 	</div>
		            <div class="clear h20"></div>
		            <div class="member_orderlist" id="member_commantable">
		            	<table cellspacing="0" cellpadding="0" border="0" align="center" class="member_nearorderTable member_ordersT" id="order_table">
		            		<tbody>
		            			<tr class="m_order_th">
								    <th width="195">订单信息</th>
								    <th width="80">出行人数</th>
								    <th width="82">支付金额</th>
								    <th width="80">出行地</th>
								    <th width="110">目的地</th>
								    <th width="100">状态</th>
								    <th width="140">操作</th>
								</tr>
								<s:iterator id="list" value="#request.listOrders" status="status" >
								    <tr class="m_order_num">
						                <td class="m_order_bh m_order_lbor" colspan="7">
						                                    订单编号： <b><a class="m_order_nums" target="_blank" href="<s:property value="#list.orderUrl" />"><s:property value="#list.orderSn" /></a></b>
						                </td>
						            </tr>
								    <tr class="m_order_num2">
								        <td><a href="<s:property value="#list.HtmlPath"/>" class="order_links_s" target="_blank"><s:property value="#list.productName" /> </a> </td>
								        <td><s:property value="#list.travelNum" /></td>
								        <td>￥<s:property value="#list.totalPrice" /></td>
								        <td><s:if test='#list.birthland==null || #list.birthland == ""'>-</s:if>
								        <s:else ><s:property value="#list.birthland" /></s:else>
								        </td>
								        <td><s:if test='#list.destination==null || #list.destination == ""'>-</s:if>
								        <s:else ><s:property value="#list.destination" /></s:else>
								        </td>
								        <td><span class="member_order-q"><s:property value="#list.orderStatusName" /></span></td>
								        <td>
								        <div class="m_ding_action">
								        <!--已取消--> 
								        <s:if test="#list.orderStatus==3">
								        	<a class="m_ck_green" target="_blank" href="<s:property value="#list.HtmlPath"/>">再次购买</a>
								        </s:if>
								        <!--待审核--> 
								        <s:if test="#list.orderStatus==0">
								        	<a class="m_ck_hui" target="_blank" href="<s:property value="#list.payUrl" />">马上支付</a>
								            <span onclick="del('<s:property value="#list.orderSn" />');" class="m_ck_qx">取消预约</span>
								        </s:if>
								        <!--已审核--> 
								        <s:if test="#list.orderStatus==1">
								        	<a class="m_ck_green" target="_blank" href="<s:property value="#list.completeUrl" />">完善信息</a>
								            <span onclick="del('<s:property value="#list.orderSn" />');" class="m_ck_qx">取消预约</span>
								        </s:if>
								        <!--待支付--> 
								        <s:if test="#list.orderStatus==5">
								        	<a class="m_ck_red" target="_blank" href="<s:property value="#list.payUrl" />">马上支付</a>
								            <span onclick="del('<s:property value="#list.orderSn" />');" class="m_ck_qx">取消预约</span>
								        </s:if>
								        <!--已支付--> 
								        <s:if test="#list.orderStatus==7">
								        	<a class="m_ck_green" target="_blank" href="<s:property value="#list.HtmlPath"/>">再次购买</a>
								        </s:if>
								        </div></td>
								    </tr>
								    </s:iterator>
		            			</tbody>
		            			
		            		</table>
		            		<s:if test="listOrders.isEmpty()">
		            			<div class="no-shop"><div class="no-shop-tip">您目前没有机票订单记录</div></div>
							</s:if>
		            	</div>
		            	<div class="clear h20"></div>
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
				</div>
			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
</body>
</html>
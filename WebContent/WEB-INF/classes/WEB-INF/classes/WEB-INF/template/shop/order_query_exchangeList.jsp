<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
	<table cellspacing="0" cellpadding="0" border="0" align="center" class="member_nearorderTable member_ordersT" id="order_table">
		<tbody>
			<tr class="m_order_th">
	            <th width="100">订单类型</th>
	            <th width="195">订单信息</th>
	            <th width="82">保费</th>
	            <th width="190">保险期限</th>
	            <th width="100">状态</th>
	            <th width="92">操作</th>
	        </tr>
	        <s:iterator id="list" value="#request.listOrders" status="status">
	        <!--订单号-->
	        <tr class="m_order_num">
	            <td class="m_order_bh m_order_lbor" colspan="6">订单编号： 
	                <b>
	                	<a class="m_order_nums" href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />"
										target="_blank"><s:property value="#list.orderSn" /></a>
					</b>
	            </td>
	        </tr>
	        <tr class="m_order_num2">
	        	<td>保险产品</td>
	        	<!--订单商品-->
				<td>
					<s:if test="#list.IsPublish==\"Y\""> 
						<a target="_blank" class="order_links_s" href="<s:property value="#list.JFHtmlPath"/>"> <s:property value="#list.productName" /></a>
					</s:if><s:else><s:property value="#list.productName" /></s:else>
				</td>
				<!--保费-->
				<td><i class="f_mi">￥</i><s:property value="#list.totalAmount" /></td>
				<!--保险期间-->
				<td>
					<s:date name="#list.startDate" format="yyyy-MM-dd" />
					至
					<s:date name="#list.endDate" format="yyyy-MM-dd" />
				</td>
				<!--状态-->
				<td>
					<s:if test="#list.order_status=='11'">
						<span class="member_ordertimeCase member_sxcolor">待生效</span>
					</s:if>
					<s:elseif test="#list.order_status=='12'">
						<span class="member_ordertimeCase member_sxcolor">生效中</span>
					</s:elseif>
					<s:else>
						<span class="member_ordertimeCase">已过期</span>
					</s:else>
				</td>
				<!--操作-->
				<td>
					<!--已支付-->
					<s:if test="#list.orderStatus==7 || #list.orderStatus==10 || #list.orderStatus==12 || #list.orderStatus==14">
						<div class="m_ding_action">
							<a target="_blank" href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />">查看订单</a>
						</div>
						<div class="m_ding_action">
							<s:if test="#list.epathFlag==1 "><a target="_blank" class="m_bd_down" href="order_query!receiptsDownload.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />&source=jifen">保单下载</a>
							</s:if>
							<s:if test="#list.order_status==13"><a href="#" class="m_bd_down" onclick="return buyNow1('<s:property value="#list.IsPublish"/>','<s:property value="#list.JFHtmlPath"/>' );">再次兑换</a></s:if>
						</div>
					</s:if> 
				</td>
	        </tr>
	        </s:iterator>
		</tbody>
	</table>
	<s:if test="#request.listOrders.isEmpty()">
		<div class="no-shop"><div class="no-shop-tip">您目前没有兑换保单记录</div></div>
	</s:if>
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
					<li class="page_prev"><a href="javascript:queryOrder('<s:property value="#request.orderStatus"/>','<s:property value="#request.page-1"/>')"><span class="">上一页</span></a></li>
				</s:else>
				<s:iterator id="pageFootMap" status="st" value="#request.pageFootList">
					<li class="<s:property value="#pageFootMap.class" />">
						<s:if test="#pageFootMap.index==\"...\"">
							<span><s:property value="#pageFootMap.index"/></span>
						</s:if>
						<s:else>
							<a href="javascript:queryOrder('<s:property value="#request.orderStatus"/>','<s:property value="#pageFootMap.index"/>')">
			        			<span><s:property value="#pageFootMap.index"/></span>
			        		</a>
						</s:else>
	        		</li>
	        	</s:iterator>
	        	<s:if test="#request.page==#request.lastpage || #request.lastpage == 0">
					<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
				</s:if>
				<s:else>
					<li class="page_next"><a href="javascript:queryOrder('<s:property value="#request.orderStatus"/>','<s:property value="#request.page+1"/>')"><span class="">下一页</span></a></li>
				</s:else>
			</ul>
		</div>
	</div>
</div>
<div class="clear h20"></div>
</s:if>

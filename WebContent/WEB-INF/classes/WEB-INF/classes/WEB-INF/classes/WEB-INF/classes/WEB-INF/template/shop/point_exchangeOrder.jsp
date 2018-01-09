<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s"%>

<table cellspacing="0" cellpadding="0" border="0" align="center" class="member_nearorderTable member_ordersT" id="order_table">
	<tbody>
        <tr class="m_order_th">
            <th width="100">订单类型</th>
            <th width="195">订单信息</th>
            <th width="82">支出积分</th>
            <th width="190">下单时间</th>
            <th width="100">状态</th>
            <th width="92">操作</th>
        </tr>
        <s:iterator id="list" value="#request.listOrders" status="status" >
        <!--订单号-->
        <tr class="m_order_num">
            <td class="m_order_bh m_order_lbor" colspan="6">订单编号： 
                <b>
                	<!--保险产品--> 
					<s:if test="#list.productType==1">
						<a target="_blank" href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />" class="m_order_nums"><s:property value="#list.orderSn" /></a>
					</s:if>
					<s:else>
						<a target="_blank" href="points!pointresult.action?orderSn=<s:property value="#list.orderSn" />" class="m_order_nums"><s:property value="#list.orderSn" /></a>
					</s:else>
                </b>
            </td>
        </tr>
        <tr class="m_order_num2">
        	<td><s:property value="#list.productTypeName" /></td>
        	<!--订单商品-->
            <td>
            	<s:if test="#list.IsPublish==\"Y\""> 
					<s:if test="#list.productType==1">
						<a target="_blank" class="order_links_s" href="<s:property value="#list.LinkUrl"/>"> <s:property value="#list.giftName" /> </a>
					</s:if>
					<s:else>
						<a target="_blank" class="order_links_s" href="points!integralMallInformation.action?presentID=<s:property value="#list.presentID"/>"> <s:property value="#list.giftName" /> </a>
					</s:else>
				</s:if>
				<s:else>
					<s:property value="#list.giftName" />
				</s:else>
            </td>
            <!--保费-->
            <td><s:property value="#list.points" /></td>
            <td><s:property value="#list.createDate" escape="false"/></td>
            <td><span class="member_ordertimeCase"><s:property value="#list.orderStatusName" /></span></td>
			<td>
				<!--保险产品--> 
				<s:if test="#list.productType==1">
					<!--暂存--> 
					<s:if test="#list.orderStatus==4">
						<s:if test="#list.IsPublish==\"Y\"">
							<div class="m_ding_action">
								<a href="order_config_new!keepInput.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />" target="_blank" class="m_ck_green">继续录入</a>
								<span class="m_ck_qx" onclick="del('<s:property value="#list.orderSn" />');">取消订单</span>
							</div>
						</s:if>
						<s:else>
							<div class="m_ding_action">
								<a href="###" onclick="return xiajia('<s:property value="#list.orderSn" />','<s:property value="#list.orderStatus" />','<s:property value="#list.IsPublish"/>')" target="_blank" class="m_ck_green">继续录入</a>
								<span class="m_ck_qx" onclick="del('<s:property value="#list.orderSn" />');">取消订单</span>
							</div>
						</s:else>
					</s:if>
					<!--待支付-->
					<s:if test="#list.orderStatus==5">
						<s:if test="#list.IsPublish==\"Y\"">
							<s:if test="#list.needUWCheckFlag==1">
								<div class="m_ding_action">
									<a href="order_config_new!tpyCheckPay.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />" target="_blank" class="m_ck_red">继续支付</a>
									<span class="m_ck_qx" onclick="del('<s:property value="#list.orderSn" />');">取消订单</span>
								</div>
							</s:if>
							<s:else>
								<div class="m_ding_action">
									<a href="order_config_new!pay.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />&paySn=<s:property value="#list.paySn" />" target="_blank" class="m_ck_red">继续支付</a>
									<span class="m_ck_qx" onclick="del('<s:property value="#list.orderSn" />');">取消订单</span>
								</div>
							</s:else>
						</s:if>
					</s:if>
					<!--已支付-->
					<s:if test="#list.orderStatus==7 || #list.orderStatus==10 || #list.orderStatus==12 || #list.orderStatus==14">
						<div class="m_ding_action">
							<a target="_blank"
								href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />">查看订单</a>
						</div>
						<s:if test="#list.epathFlag==1 ">
							<a target="_blank" class="m_bd_down"
								href="order_query!receiptsDownload.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />">保单下载</a>
						</s:if>
					</s:if>
					<!-- 撤单 --> 
					<s:if test="#list.orderStatus==9 || #list.orderStatus==11 || #list.orderStatus==13">
						<span class="member_orderoperateCase">
							<div class="m_ding_action"><a target="_blank"
								href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />">查看订单</a>
							</div>
						
						<s:if test="#list.IsPublish==\"Y\"">
							<div class="m_ding_action"><a href="#"
								onclick="return buyNow1('<s:property value="#list.IsPublish"/>','<s:property value="#list.LinkUrl"/>' );">再次兑换</a>
							</div>
						</s:if>
						</span>
					</s:if>
					<!-- 取消订单 --> 
					<s:if test="#list.orderStatus==3 || #list.orderStatus==8 ">
						<div class="m_ding_action">
						<a href="###" id="<s:property value="#list.orderSn" />_dellorder" onclick="dellorder(<s:property value="#list.orderSn" />)" >删除</a>
						</div>
					</s:if>
				</s:if>
				<s:else>
					<div class="m_ding_action">
						<a target="_blank"
							href="points!pointresult.action?orderSn=<s:property value="#list.orderSn" />">查看订单</a>
					</div>
				</s:else>
				</td>
			</tr>
			</s:iterator>
		</tbody>
	</table>
	<s:if test="#request.listOrders.isEmpty()">
		<div class="no-shop"><div class="no-shop-tip">您目前没有兑换订单记录</div></div>
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
					<li class="page_prev"><a href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#request.page-1"/>','<s:property value="#request.lastpage"/>','member_tabarea0')"><span class="">上一页</span></a></li>
				</s:else>
				<s:iterator id="pageFootMap" status="st" value="#request.pageFootList">
					<li class="<s:property value="#pageFootMap.class" />">
						<s:if test="#pageFootMap.index==\"...\"">
							<span><s:property value="#pageFootMap.index"/></span>
						</s:if>
						<s:else>
							<a href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#pageFootMap.index"/>','<s:property value="#request.lastpage"/>','member_tabarea0')">
			        			<span><s:property value="#pageFootMap.index"/></span>
			        		</a>
						</s:else>
	        		</li>
	        	</s:iterator>
	        	<s:if test="#request.page==#request.lastpage || #request.lastpage == 0">
					<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
				</s:if>
				<s:else>
					<li class="page_next"><a href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#request.page+1"/>','<s:property value="#request.lastpage"/>','member_tabarea0')"><span class="">下一页</span></a></li>
				</s:else>
			</ul>
		</div>
	</div>
</div>
<div class="clear h20"></div>
</s:if>
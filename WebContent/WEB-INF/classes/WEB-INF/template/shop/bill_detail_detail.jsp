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
	<title>会员中心-发票申请</title>
	<!-- 会员中心公共CSS --> 
	<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/base.css" />
	<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"/>
	<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
 	<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
	<body class="up-bg-qh">
		<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
		<div class="wrapper">
			<div class="daohang">
				<span class="daohang_home"></span>
				<a href='<%=Config.getFrontServerContextPath() %>' target='_self' ><span class="orange">您现在的位置：首页</span></a>
				<span class="separator">></span>
				<a href='member_center!index.action' target='_self'>会员中心</a>
				<span class="separator">></span><span class="separator1">发票申请</span>
				<span class="separator">></span><span class="separator1">物流情况</span>
			</div>
			<div class="member_con">
				<input type="hidden" id="fullDegree" value="<%=session.getAttribute("fullDegree")%>"> 
				<input type="hidden" value="<s:property value="deliverStatus"/>" id ="deliverStatus" name="deliverStatus" >
				<input type="hidden" value="<s:property value="logisticsId"/>" id ="logisticsId" name="logisticsId" >
				<input type="hidden" value="<s:property value="logisticsName"/>" id ="logisticsName" name="logisticsName" >
				<input type="hidden" value="<s:property value="wayBillNo"/>" id ="wayBillNo" name="wayBillNo" >
				<!-- 会员中心左侧菜单导航 -->
				<jsp:include page="member_center_left.jsp"></jsp:include>
				 
				<div class="member_right ">
					<div class="member_boxs">
						<div class="member-htit">
				            <span class="member-titsc">发票邮寄详情</span>
				        </div>
						<div class="invoiceTab">
							<p class="ttl">发票补开信息</p>
							<table id="bill_table" class="inTabList" cellspacing="0" cellpadding="0" border="0">
								<tbody>
									<tr class="m_order_th">
										<th>订单号</th>
										<th>名称</th>
										<th>邮寄时间</th>
										<th>发票类型</th>
										<th>发票金额总计</th>
									</tr>
									<s:iterator id="billList" value="#request.listBills" status="status" >
										<s:if test="#status.index%2 == 0 ">
											<tr class="m_order_even">
										</s:if>
										<s:else>
											<tr>
										</s:else>
											<!--订单号-->
							                <td  class="order_id" >
							                    	<a  href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#billList.orderSn" />&KID=<s:property value="#billList.KID" />" target="_blank"><s:property value="#billList.orderSn" /></a>
							                </td>
											<!--订单商品-->
											<td class="order_name">
												<s:if test="#billList.isPublish==\"Y\""> 
														<a target="_blank"  href="<s:property value="#billList.htmlPath"/>"> <s:property value="#billList.productName" /> </a>
												</s:if> 
												<s:else>
													<s:property value="#billList.productName" />
												</s:else> 
											</td>
											<!--邮寄时间-->
											<td class="order_time" id="deliverDate_td_<s:property value="#status.index" />" rowspan="<s:property value="#billList.row_span_num"/>">
												<s:date name="#billList.deliverDate" format="yyyy-MM-dd" />
											</td>
											<!--发票类型-->
											<td id="billType_td_<s:property value="#status.index" />" class="ptype" rowspan="<s:property value="#billList.row_span_num"/>">
												<span ><s:property value="#billList.billTypeName" /></span> 
											</td>
											<!--发票金额合计-->
											<td id="billPayment_td_<s:property value="#status.index" />" class="price" rowspan="<s:property value="#billList.row_span_num"/>">
												¥<s:property value="#billList.billAmount" /> 
											</td>
										</tr>
									</s:iterator>
								</tbody>
							</table>
						</div>
						<div class="postAddr">
							<p class="ttl">邮寄地址</p>
							<p class="txt"><span>邮寄地址：</span><s:property value="#request.addressInfo" /><br /><s:property value="#request.personInfo" /></p>
							
						</div>
						
						<div class="postInfo">
							<p class="ttl">物流信息</p>
							<s:if test="#request.deliverStatus == '03'">
								<p class="txt">您的发票<span>已邮寄</span></p>
							</s:if>
							<s:else>
								<p class="txt">您的发票未邮寄</p>
							</s:else>
							<s:if test="#request.deliverStatus == '03'">
								<ul class="showList">
									<s:iterator id="logisticsItem" value="#request.logisticsInfo.data" status="status" >
										<s:if test="#status.index == (#request.logisticsInfo.data.size() - 1) ">
											<li class="track">
												<span class="time"><s:property value="#logisticsItem.time"/></span>
												<span class="text"><s:property value="#logisticsItem.context"/></span>
												<span class="info">信息来源：<b><s:property value="logisticsName"/></b>运单号：<s:property value="wayBillNo"/></span>
											</li>
										</s:if>
										<s:else>
											<li>
												<span class="time"><s:property value="#logisticsItem.time"/></span>
												<span class="text"><s:property value="#logisticsItem.context"/></span>
											</li>
										</s:else>
									</s:iterator>
								</ul>
								<p class="tips">以上部分信息来自第三方</p>
							</s:if>
						</div>
						
						<div class="invoiceHead">
							<p class="ttl">发票抬头</p>
							<s:if test="#request.billType == '01'">
								<span class="invoiceLine">定额</span>
							</s:if>
							<s:else>
								<s:if test="#request.billType == '04'">
									<span class="invoiceLine"><s:property value="#request.billTitle"/></span>
									<s:if test="#request.billReqUrl !=null && #request.billReqUrl !=''">
										<a href="<s:property value="#request.billReqUrl" />" class="btn" >开票申请表下载</a>
									</s:if>
								</s:if>
								<s:else>
									<span class="invoiceLine"><s:property value="#request.billTitle"/></span>
								</s:else>
							</s:else>
						</div>
						
						<p class="question">有问题？<a href="javascript:void(0);" vlpageid="xiaoneng" exturl="http://www.kaixinbao.com/xiaoneng" id="qqwap2" onclick="return(VL_FileDL(this));return false;"
									rel="nofollow">联系客服</a></p>
					</div>
				</div>
			</div>
		</div>
		<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
		<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
		<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
		<script type="text/javascript">
		jQuery(document).ready(function(){

			//合并单元格多余删除处理
			var tablerownum=jQuery("#bill_table").find("tr").length;
			var rownum=parseInt(tablerownum)-1;
			if(rownum > 0 ){
				var rowspan_num=jQuery("#deliverDate_td_"+0).attr("rowspan");
				if(rowspan_num>1){
					for(var j=1;j<rowspan_num;j++){
						jQuery("#deliverDate_td_"+j).remove();
						jQuery("#billType_td_"+j).remove();
						jQuery("#billPayment_td_"+j).remove();
					}
				}	
			}
			
		});

</script>
	</body>
</html>
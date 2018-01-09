<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext(); 
%>
<html>
<head>
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<link rel="stylesheet" type="text/css"
	href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css">
<script type="text/javascript">
jQuery(document).ready(function(){
	var tablerownum=jQuery("#order_table").find("tr").length;
	var rownum=parseInt(tablerownum)-1;
	for(var i=1;i<rownum;){
		var payamount_index=i;
		if(i!=0){
			payamount_index=(parseInt(i)-1)/2;
		}
		var rowspan_num=jQuery("#payamount_td_"+payamount_index).attr("rowspan");
		if(rowspan_num>1){
			var num=(parseInt(rowspan_num)-1)/2+1;
			for(var j=1;j<num;j++){
				var index_remove=parseInt(payamount_index)+parseInt(j);
				jQuery("#payamount_td_"+index_remove).remove();
				jQuery("#ordersn_td_"+index_remove).next().removeClass("clear_bor_all");
				jQuery("#ordersn_td_"+index_remove).remove();
			}
			i=parseInt(i)+parseInt(rowspan_num)+1;
		}else{
			i=i+2;
		}
	}
	//页码初始化
	var page = jQuery("#pg").val();
	var lastpage = jQuery("#lg").val();
	var selObj = jQuery("#pageselect");
	for(var i=1;i<parseInt(lastpage)+1;i++){
		var value=i;  
		var text=i;
		selObj.append("<option value='"+value+"'>"+text+"</option>");  
	}
	jQuery("#pageselect").attr("value",page); 
});



</script>
</head>
<div class="member_orderlist" id="member_commantable">
	<table cellspacing="0" cellpadding="0" border="0" align="center"
									class="member_nearorderTable member_ordersT" id="order_table">
		<tbody>
			<tr class="m_order_th">
				<th class="lc_des_tds" width="145">时间</th>
				<th class="lc_des_tds" width="160">收支（元）</th>
				<th class="lc_des_tds" width="125">账户余额（元）</th>
				<th width="270">备注</th>
			</tr>
			<s:iterator id="list" value="#request.listRecord" status="status">
				<tr <s:if test="#status.index %2 ==0"> class="odd" </s:if>>
					<td class="lc_des_tds"><s:property value="#list.cdate" /></td>
					<td class="lc_des_tds"><span class="zc_pay_c <s:if test="#list.recordStatus ==1">zc_pay_d</s:if>"><s:property value="#list.principal" /></span></td>
					<td class="lc_des_tds"><span class="zc_pay_c"><s:property value="#list.total" /></span></td>
					<td class="lc_des_td_end"><s:property value="#list.recordDetail" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<s:if test="listRecord.isEmpty()">
		<div class="no-shop">
			<div class="no-shop-tip">
				<a href="<%=Config.getFrontServerContextPath()%>">最近没有订单，去选购商品吧！~</a>
			</div>
		</div>
	</s:if>
</div>
<div class="clear h20"></div>
<input type="hidden" id="callType"
	value="<s:property value="#request.callType" />" />

<s:if test="callType!=1">
	<s:if test="#request.lastpage > 1">
		<div class="plpage">
			<!--    翻页    -->
			<div class="page_area">
				<div id="pagination">
					<ul>
						<s:if test="#request.page==1">
							<li class="page_prev"><a href="#"><span class="default">上一页</span></a></li>
						</s:if>
						<s:else>
							<li class="page_prev"><a
								href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#request.page-1"/>','<s:property value="#request.page_count"/>','member_ordertable')"><span
									class="">上一页</span></a></li>
						</s:else>
						<s:iterator id="pageFootMap" status="st"
							value="#request.pageFootList">
							<li class="<s:property value="#pageFootMap.class" />"><s:if
									test="#pageFootMap.index==\"...\"">
									<span><s:property value="#pageFootMap.index" /></span>
								</s:if> <s:else>
									<a
										href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#pageFootMap.index"/>','<s:property value="#request.page_count"/>','<s:property value="#pageFootMap.idalias"/>')">
										<span><s:property value="#pageFootMap.index" /></span>
									</a>
								</s:else></li>
						</s:iterator>
						<s:if
							test="#request.page==#request.lastpage || #request.lastpage == 0">
							<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
						</s:if>
						<s:else>
							<li class="page_next"><a
								href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#request.page+1"/>','<s:property value="#request.page_count"/>','member_ordertable')"><span
									class="">下一页</span></a></li>
						</s:else>
					</ul>
				</div>
			</div>
		</div>
		<div class="clear h20"></div>
	</s:if>
</s:if>
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
	<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
	<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"/>
 	<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
	<body class="up-bg-qh">
		<!-- 顶部 开始 1-->
		<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
		<!-- 顶部 结束 -->
		<div class="wrapper">
			<div class="daohang">
				<span class="daohang_home"></span>
				<a href='<%=Config.getFrontServerContextPath() %>' target='_self' ><span class="orange">您现在的位置：首页</span></a>
				<span class="separator">></span>
				<a href='member_center!index.action' target='_self'>会员中心</a>
				<span class="separator">></span><span class="separator1">发票申请</span>
			</div>
			
			<div class="member_con">
				<!-- 会员中心左侧菜单导航 -->
				<jsp:include page="member_center_left.jsp"></jsp:include>
				<input type="hidden" value="<s:property value="buyNoneFlag"/>" name="buyNoneFlag" id="buyNoneFlag" />
				<s:form id="orderQuery_form" name="orderQuery_form" action="bill_query!queryBill.action" method="post" >
				<input type="hidden" value="<s:property value="page"/>" name="pc" id="pg" />
				<input type="hidden" value="<s:property value="lastpage"/>" name="lpg" id="lg" />
				<input type="hidden" value="<s:property value="histPage"/>" name="hpc" id="hpg" />
				<input type="hidden" value="<s:property value="histLastpage"/>" name="hlpg" id="hlg" />
				<input type="hidden" value="<s:property value="queryFlag"/>" id ="queryFlag" name="queryFlag" >
				<input type="hidden" value="<s:property value="mergeOrdersInfo"/>" name="mergeOrdersInfo" id="mergeOrdersInfo" />
				<input type="hidden" value="<s:property value="buyNoneFlag"/>" name="buyNoneFlag" id="buyNoneFlag" />
				<div class="member_right mbr_bg">
					<div class="member_boxs">
						<div id="member_commantable" class="member_orderlist invoice">
							<h3 class="member_t_up">
								<ul id="member_t_tag" class="member_t_tag">
									<li class="tag_hsf hover"  onclick="doChangeArea(0);"  id="member_tabtitle0">开具发票</li>
									<li onclick="doChangeArea(1);" id="member_tabtitle1">发票开具记录</li>
								</ul>
							</h3>
							<div id="member_tabarea0" class="member_midonebottom" style="display: none;">
								<div class="tipsArea">
									<div class="tipsCont">
										<p class="img"><img src="<%=Config.getValue("StaticResourcePath")%>/images/vip/img_tips_cont.jpg" alt="" /></p>
										<ol>
											<li><strong>温馨提示：</strong><span>200元以下发票申请的<b>邮费</b>由用户自行承担，200元以上包邮。</span></li>
											<li><span>补开发票根据不同保险公司可以开具的发票不同，具体请补开发票时确认。</span></li>
											<li><span>补开发票按照原价开具，有撤单订单撤销部分不开具发票。</span></li>
											<li><span>用户发票申请后48小时内邮寄，请随时到发票开具记录中查看物流信息。</span></li>
											<li><span>已经开具发票的订单<b>退保</b>时需将发票自费寄回才可退保成功。</span></li>
											<s:property value="prompt" escape="false"/>
										</ol>
									</div>
								</div>
										
								<div class="operate">
									<p class="check"><label><input type="checkbox" class="allCheck" onclick="othersSelect(this)" />全选</label></p>
									<p class="btn"><a href="###" onclick="requireManyOrders()">合并补开发票</a></p>
									<p class="often"><a href="###" onclick="manageDeliverAddr();">常用邮寄地址修改</a></p>
								</div>
										
								<table id="orders_table" class="member_nearorderTable member_netable member_ordersT orderList" cellspacing="0" cellpadding="0" border="0">
									<tbody>
										<tr class="m_order_th">
											<th width="158">订单信息</th>
											<th width="69">投保人</th>
											<th width="67">保费</th>
											<th width="62">状态</th>
											<th width="86">下单时间</th>
											<th width="72">发票金额</th>
											<th width="130">发票类型</th>
											<th width="73">操作</th>
										</tr>
						
										<s:iterator id="list" value="#request.listOrders" status="status" >
										<!--订单号-->
										<tr class="m_order_num">
										    <td colspan="8" class="m_order_bh m_order_lbor" >
										        
										        <s:if test="#list.billTypeName==\"\""> 
										        	&nbsp;
												</s:if> 
												<s:else>
										        	<input id="orderSnChk " name="orderSnChk" class="sel" type="checkbox" onclick="updateOrdersInfoData(this)" />
												</s:else> 
												
										                订单编号： <b><a class="m_order_nums" href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />" target="_blank"><s:property value="#list.orderSn" /></a></b>
										        <input id="billType" name="billType" type="hidden" value="<s:property value="#list.billType"/>"/>
										        <input id="applicantName" name="applicantName" type="hidden" value="<s:property value="#list.applicantName"/>"/>
										        <input id="insuranceCompany" name="insuranceCompany" type="hidden" value="<s:property value="#list.insuranceCompany"/>"/>
										    </td>
										</tr>
										<tr class="m_order_num2">
											<!--订单商品-->
											<td>
											<s:if test="#list.isPublish==\"Y\""> 
												<a target="_blank" class="order_links_s" href="<s:property value="#list.htmlPath"/>"> <s:property value="#list.productName" /> </a>
											</s:if> 
											<s:else>
												<s:property value="#list.productName" />
											</s:else> 
											</td>
											<!--投保人-->
											<td><span class="name"><s:property value="#list.applicantName"/></span></td>
											<!--保费-->
											<td >
												<span class="f_mi">￥<s:property value="#list.totalAmount" /></span>
											</td>
											<!--状态-->
											<td >
												<s:property value="#list.codeName" />
											</td>
											<!--下单时间-->
											<td>
												<s:date name="#list.createDate" format="yyyy-MM-dd" />
											</td>
											<!--发票金额-->
											<td>
												<span class="price">¥<s:property value="#list.totalAmount" /></span> 
											</td>
											<!--发票类型-->
											<td >
												<span class="type"><s:property value="#list.billTypeName" escape="false" /></span> 
											</td>
											<!--操作-->
											<td class="m_ding_action" >
											
												<s:if test="#list.billTypeName==\"\""> 
												</s:if> 
												<s:else>
													<a class="more oper" href="javascript:enterBilling('<s:property value="#list.billType"/>','<s:property value="#list.orderSn"/>')">开具发票</a>
												</s:else> 
												
											</td>
										</tr>
										</s:iterator>
									</tbody>
								</table>
								<div id="message" class="no-shop no-shop-fp" style="display:none"><div class="no-shop-tip">对不起，您还没有购买过保险产品！</div></div>
								<div class="operate">
									<p class="check"><label><input type="checkbox" class="allCheck" onclick="othersSelect(this)" />全选</label></p>
									<p class="btn"><a href="###" onclick="requireManyOrders()">合并补开发票</a></p>
								</div>
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
													<li class="page_prev"><a href="javascript:jumpPage('<s:property value="#request.actionAlias"/>','<s:property value="#request.page-1"/>')"><span class="">上一页</span></a></li>
												</s:else>
												<s:iterator id="pageFootMap" status="st" value="#request.pageFootList1">
									        		<li class="<s:property value="#pageFootMap.class" />"><a href="javascript:jumpPage('<s:property value="#request.actionAlias"/>','<s:property value="#pageFootMap.index"/>')"><span><s:property value="#pageFootMap.index"/></span></a></li>
									        	</s:iterator>
									        	<s:if test="#request.page==#request.lastpage || #request.lastpage == 0">
													<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
												</s:if>
												<s:else>
													<li class="page_next"><a href="javascript:jumpPage('<s:property value="#request.actionAlias"/>','<s:property value="#request.page+1"/>')"><span class="">下一页</span></a></li>
												</s:else>
											</ul>
										</div>
									</div>
									<div class="clear"></div>
								</div>	
								</s:if>	
								
							</div>
							<div id="member_tabarea1" class="member_midonebottomHidden" style="display: none;">
								<table id="billHist_table" class="member_nearorderTable member_ordersT billHistList" cellspacing="0" cellpadding="0" border="0">
									<tbody>
										<tr class="m_order_th">
											<th width="198">订单信息</th>
											<th width="120">发票申请状态</th>
											<th width="92">申请时间</th>
											<th width="89">发票金额</th>
											<th width="147">发票类型</th>
											<th width="73">操作</th>
										</tr>
										<s:iterator id="billList" value="#request.listBills" status="status" >
										<!--订单号-->
										<tr class="m_order_num">
										    <td class="m_order_bh m_order_lbor" >
										        	订单编号： <b><a  class="m_order_nums" href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#billList.orderSn" />&KID=<s:property value="#billList.KID" />" target="_blank"><s:property value="#billList.orderSn" /></a></b>
										    </td>
										    <!--发票申请状态-->
											<td id="status_td_<s:property value="#status.index" />"  rowspan="<s:property value="#billList.row_span_num"/>">
												<s:property value="#billList.billStatusName" />
											</td>
											<!--申请时间-->
											<td id="deliverDate_td_<s:property value="#status.index" />" rowspan="<s:property value="#billList.row_span_num"/>">
												<s:date name="#billList.deliverDate" format="yyyy-MM-dd" />
											</td>
											<!--发票金额-->
											<td id="billPayment_td_<s:property value="#status.index" />" rowspan="<s:property value="#billList.row_span_num"/>">
												<span class="price">¥<s:property value="#billList.billAmount" /></span> 
											</td>
											<!--发票类型-->
											<td id="billType_td_<s:property value="#status.index" />" rowspan="<s:property value="#billList.row_span_num"/>">
												<span ><s:property value="#billList.billTypeName" /></span> 
											</td>
											<!--操作-->
											<td id="operate_td_<s:property value="#status.index" />" class=" m_ding_action" rowspan="<s:property value="#billList.row_span_num"/>">
												<s:if test="#billList.status=='03' || #billList.status=='04'">撤销申请</s:if>
												<s:else>
												<a class="more oper" href="###" onclick="cancelApplication('<s:property value="#billList.id" />')">撤销申请</a>
												</s:else>
												<a class="more oper" href="###" onclick="getDetail('<s:property value="#billList.id" />')">查看详情</a>
											</td>
										</tr>
										<tr class="m_order_num2">
											<!--订单商品-->
											<td>
											<s:if test="#billList.isPublish==\"Y\""> 
												<a target="_blank" class="order_links_s" href="<s:property value="#billList.htmlPath"/>"> <s:property value="#billList.productName" /> </a>
											</s:if> 
											<s:else><s:property value="#billList.productName" /></s:else> 
											</td>	
										</tr>
										</s:iterator>
									</tbody>
								</table>
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
													<li class="page_prev"><a href="javascript:jumpPage('<s:property value="#request.actionAlias"/>','<s:property value="#request.page-1"/>')"><span class="">上一页</span></a></li>
												</s:else>
												<s:iterator id="pageFootMap" status="st" value="#request.pageFootList">
													<li class="<s:property value="#pageFootMap.class" />">
														<s:if test="#pageFootMap.index==\"...\"">
															<span><s:property value="#pageFootMap.index"/></span>
														</s:if>
														<s:else>
															<a href="javascript:jumpPage('<s:property value="#request.actionAlias"/>','<s:property value="#pageFootMap.index"/>')">
											        			<span><s:property value="#pageFootMap.index"/></span>
											        		</a>
														</s:else>
									        		</li>
									        	</s:iterator>
									        	<s:if test="#request.page==#request.lastpage || #request.lastpage == 0">
													<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
												</s:if>
												<s:else>
													<li class="page_next"><a href="javascript:jumpPage('<s:property value="#request.actionAlias"/>','<s:property value="#request.page+1"/>')"><span class="">下一页</span></a></li>
												</s:else>
											</ul>
										</div>
									</div>
									<div class="clear"></div>
								</div>
								</s:if>
							</div>
						</div>
					</div>
				</div>
				</s:form>
			</div>
		</div>
		<!-- 底部开始 -->
		<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
		<!-- 底部结束 -->
		<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.cookie.js"></script>
		<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
		<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
		<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/common/js/json2.js"></script>
		<script type="text/javascript">
		jQuery(document).ready(function(){
			
				if(jQuery("#queryFlag").val()!=null){
					var queryFlag = jQuery("#queryFlag").val();
					var intObj = 0;
					if(queryFlag == "bill"){
						intObj = 1;
					}
					var allSpanTitle = document.getElementById("member_t_tag").getElementsByTagName("li");				
					for(var i=0;i<allSpanTitle.length;i++){
						var includePage = "member_tabarea"+i;
						document.getElementById(includePage).style.display="none";
						var plus = "member_tabtitle"+i;
						document.getElementById(plus).className=plus;
					}
					var selectdPage = "member_tabarea"+intObj;
					document.getElementById(selectdPage).style.display="block";
					var selectID = "member_tabtitle"+intObj;
					var selectPlus = "tag_hsf hover";
					if (intObj == 1) {
						selectPlus = "hover";
					}
					document.getElementById(selectID).className=selectPlus;	
				}
				
				//合并单元格多余删除处理
				var tablerownum=jQuery("#billHist_table").find("tr").length;
				var rownum=parseInt(tablerownum)-1;
				for(var i=1;i<rownum;){
					var payamount_index=i;
					if(i!=0){
						payamount_index=(parseInt(i)-1)/2;
					}
					var rowspan_num=jQuery("#status_td_"+payamount_index).attr("rowspan");
					if(rowspan_num>2){
						var num=(parseInt(rowspan_num)/2-1);
						for(var j=1;j<=num;j++){
							var index_remove=parseInt(payamount_index)+parseInt(j);
							jQuery("#status_td_"+index_remove).remove();
							jQuery("#deliverDate_td_"+index_remove).remove();
							jQuery("#billPayment_td_"+index_remove).remove();
							jQuery("#billType_td_"+index_remove).remove();
							jQuery("#operate_td_"+index_remove).remove();
						}
						i=parseInt(i)+parseInt(rowspan_num);
					}else{
						i=i+2;
					}
				}
	
				//页码初始化
				//订单列表
				var page = jQuery("#pg").val();
				var lastpage = jQuery("#lg").val();
				var selObj = jQuery("#pageselect");
				for(var i=1;i<parseInt(lastpage)+1;i++){
					var value=i;  
					var text=i;
					selObj.append("<option value='"+value+"'>"+text+"</option>");  
				}
				jQuery("#pageselect").attr("value",page); 
	
				//发票历史列表
				var histPage = jQuery("#hpg").val();
				var histLastpage = jQuery("#hlg").val();
				var histSelObj = jQuery("#histPageselect");
				for(var i=1;i<parseInt(histLastpage)+1;i++){
					var value=i;  
					var text=i;
					histSelObj.append("<option value='"+value+"'>"+text+"</option>");  
				}
				jQuery("#histPageselect").attr("value",histPage); 
			
				var mergeOrdersInfo = $("#mergeOrdersInfo").val();
				if(mergeOrdersInfo == "" || mergeOrdersInfo == "undefined"){
					mergeOrdersInfo = '[]';
				}
				var ordersInfoObj = JSON.parse(mergeOrdersInfo);
				for(var i = 0; i< ordersInfoObj.length; i++){
					$("#orders_table").find(":checkbox").each(function(){
				       var orderSn = jQuery(this).next().text();
			    	   if(ordersInfoObj[i].orderSn == orderSn){
			    		   jQuery(this).attr("checked", true);
			    	   }
					});
				}
				if(jQuery("#buyNoneFlag").val() == "1"){
					jQuery("#message").show();
				} else {
					jQuery("#message").hide();
				}
		});

		//tab切换处理
		function doChangeArea(intObj){
			if(intObj == 1){
				location.href="bill_query!queryBillHist.action";	
			}else{
				location.href="bill_query!queryBill.action";
			}
		}
		
		//常用邮寄地址修改
		function manageDeliverAddr(){
			location.href="member_info_maintain!memberInfoQuery.action"; 
		}
		
		//撤销申请
		function cancelApplication(id){
			if(id!="" && id != "undefined"){
				art.dialog({
					title: "提示",
					content: "<div id='artPopup04'><p>取消发票邮寄？</p></div>",
					ok: function () {
						jQuery.ajax({
							url : sinosoft.base
									+ "/shop/bill_query!cancelBillInfo.action?info_id=" + id,
							dataType : "json",
							type : "POST",
							async : false,
							success : function(data) {
								if (data.tFlag == "Suc") {
									alert("撤销申请成功！");
									//art.dialog.alert("撤销发票申请成功！");
									location.href = "bill_query!queryBillHist.action";
								} else {
									art.dialog.alert(data.Msg);
								}
							}
						});
					}
				});
			}
		}
		
		//查看详情处理
		function getDetail(id){
			if(id!="" && id != "undefined"){
				location.href="bill_detail!getBillDetailInfo.action?info_id=" + id;
			}
		}

		//全选按钮同步和全选处理
		function othersSelect(obj){
			var $allCheck = jQuery(".operate input.allCheck");// 全选复选框
			var $idsCheck = jQuery(".orderList input[name='orderSnChk']");// 订单复选框

			var mergeOrdersInfo = $("#mergeOrdersInfo").val();
			if(mergeOrdersInfo == "" || mergeOrdersInfo == "undefined"){
				mergeOrdersInfo = '[]';
			}
			var ordersInfoObj = JSON.parse(mergeOrdersInfo);
			
			if(jQuery(obj).attr("checked") == true){
				$allCheck.attr("checked", true);
				$idsCheck.attr("checked", true);

				//全选checkbox选择时分页订单数据的维护
				$("#orders_table").find(":checkbox").each(function(){
			       var orderSn = $(this).next().text();
			       var billType = $(this).next().next().val();
			       var applicantName = $(this).next().next().next().val();
			       var insuranceCompany = $(this).next().next().next().next().val();
			       if(ordersInfoObj.length == 0){
				       ordersInfoObj.push({"orderSn":orderSn,"billType":billType,"applicantName":applicantName,"insuranceCompany":insuranceCompany});
			       }else{
			    	   var hasValue =false;
				       for(var i = 0; i< ordersInfoObj.length; i++){
				    	   if(ordersInfoObj[i].orderSn == orderSn){
				    		   hasValue = true;
				    		   break;
				    	   }
				       }
				       if(hasValue == false){
					       ordersInfoObj.push({"orderSn":orderSn,"billType":billType,"applicantName":applicantName,"insuranceCompany":insuranceCompany});
				       }
			       }
			    });
				var mergeOrdersInfo = JSON.stringify(ordersInfoObj);
				$("#mergeOrdersInfo").val(mergeOrdersInfo);
			}else{
				$allCheck.attr("checked", false);
				$idsCheck.attr("checked", false);

				//全选checkbox选择时分页订单数据的维护
				$("#orders_table").find(":checkbox").each(function(){
			       var orderSn = jQuery(this).next().text();
			       if(ordersInfoObj.length != 0){
				       for(var i = 0; i< ordersInfoObj.length; i++){
				    	   if(ordersInfoObj[i].orderSn == orderSn){
				    		   ordersInfoObj.splice(i,1);
				    		   break;
				    	   }
				       }
			       }
			    });
				var mergeOrdersInfo = JSON.stringify(ordersInfoObj);
				$("#mergeOrdersInfo").val(mergeOrdersInfo);
			}
		}

		//单个checkbox选择时分页订单数据的维护
		function updateOrdersInfoData(obj){
			var mergeOrdersInfo = $("#mergeOrdersInfo").val();
			if(mergeOrdersInfo == "" || mergeOrdersInfo == "undefined"){
				mergeOrdersInfo = '[]';
			}
			var ordersInfoObj = JSON.parse(mergeOrdersInfo);
			var orderSn = $(obj).next().text();
			if($(obj).attr("checked") == true){
		       var billType = $(obj).next().next().val();
		       var applicantName = $(obj).next().next().next().val();
		       var insuranceCompany = $(obj).next().next().next().next().val();
		       ordersInfoObj.push({"orderSn":orderSn,"billType":billType,"applicantName":applicantName,"insuranceCompany":insuranceCompany});
				
			}else{
				for(var i = 0; i< ordersInfoObj.length; i++){
		    	   if(ordersInfoObj[i].orderSn == orderSn){
		    		   ordersInfoObj.splice(i,1);
		    		   break;
		    	   }
		       }
			}
			var mergeOrdersInfo = JSON.stringify(ordersInfoObj);
			$("#mergeOrdersInfo").val(mergeOrdersInfo);
		}
		
		//合并订单处理
		function requireManyOrders(){
			var mergeOrdersInfoValue = jQuery("#mergeOrdersInfo").val();
			if(mergeOrdersInfoValue==null||mergeOrdersInfoValue==""||mergeOrdersInfoValue=="[]"){
				art.dialog({
					title: "提示",
					content: "<div id='artPopup01'><img src='../../style/images/art_smile.png' width='80' height='87' /><span>您没有选择任何数据，请选择数据后试试哦！</span></div>",
					ok: function () {
					}
				});
				return false;
			}
			jQuery.ajax({
				url : sinosoft.base+ "/shop/bill_query!ajaxValidateMerge.action?",
				dataType : "json",
				type : "POST",
				async : false,
				data:{mergeOrdersInfo:mergeOrdersInfoValue},
				success : function(data) {
					if (data.tFlag == "Suc") {
						var billType = data.TypeData;
						var orderSns = data.OrderSns;
						var applicantNameFlag = data.applicantNameFlag;
						var type = billType.split(",");
						if (null != type && type.length > 1) {
							var selectHtml = "<select id='selectedBillType'>";
							for (var i = 0; i < type.length; i++) {
								if ("01" == type[i]) {
									selectHtml = selectHtml + "<option value='" + type[i] + "'>定额</option>";
								}else if ("02" == type[i]){
									selectHtml = selectHtml + "<option value='" + type[i] + "'>普通发票</option>";
								}else if ("03" == type[i]){
									selectHtml = selectHtml + "<option value='" + type[i] + "'>普通发票（限投保人）</option>";
								}else if ("04" == type[i]){
									selectHtml = selectHtml + "<option value='" + type[i] + "'>普通发票（加盖公章）</option>";
								}else if ("05" == type[i]){
									selectHtml = selectHtml + "<option value='" + type[i] + "'>普通发票（限个人名头）</option>";
								}else if ("06" == type[i]){
									selectHtml = selectHtml + "<option value='" + type[i] + "'>普通发票（限公司名头）</option>";
								}
								
							}
							selectHtml = selectHtml + "</select>";
							
							art.dialog({
								title: "补开类型选择",
								content: "<div id='artPopup02'><p>补开机打发票需要抬头和补开发票类型相同才可合并开<br />具，请选择:</p><p class='selArea'>" + selectHtml + "</p></div>",
								ok: function () {
									if (jQuery("#selectedBillType").val() == '03' &&　applicantNameFlag == 'false') {
										art.dialog({
											title: "提示",
											content: "<div id='artPopup01'><img src='../../style/images/art_smile.png' width='80' height='87' /><span>不同投保人不能合并开具！</span></div>",
											ok: function () {
											}
										});
									} else {
										window.location.href = "bill_save!enterBilling.action?billType="+jQuery("#selectedBillType").val()+"&orderSn="+orderSns;
									}
								}
							});
						}else{
							window.location.href = "bill_save!enterBilling.action?billType="+billType+"&orderSn="+orderSns;
						}
					} else {
						if(data.Msg=="BILLTYPECHECKERR1"){
							art.dialog({
								title: "提示",
								content: "<div id='artPopup01'><img src='../../style/images/art_smile.png' width='80' height='87' /><span>不同类型发票不能开具在一张发票中哦！</span></div>",
								ok: function () {
								}
							});
						}else if(data.Msg=="BILLTYPECHECKERR2"){
							art.dialog({
								title: "提示",
								content: "<div id='artPopup01'><img src='../../style/images/art_smile.png' width='80' height='87' /><span>不同公司不能开具在一张发票中哦！</span></div>",
								ok: function () {
								}
							});
						}else if(data.Msg=="BILLTYPECHECKERR3"){
							art.dialog({
								title: "提示",
								content: "<div id='artPopup01'><img src='../../style/images/art_smile.png' width='80' height='87' /><span>不同投保人不能合并开具！</span></div>",
								ok: function () {
								}
							});
						}else{
							art.dialog.alert(data.Msg);	
						}
						return false;
					}
				}
			});
		}
		
		// 进入开具发票页
		function enterBilling(billType, orderSn){
			var type = billType.split(",");
			if (null != type && type.length > 1) {
				var selectHtml = "<select id='billType'>";
				for (var i = 0; i < type.length; i++) {
					if ("01" == type[i]) {
						selectHtml = selectHtml + "<option value='" + type[i] + "'>定额</option>";
					}else if ("02" == type[i]){
						selectHtml = selectHtml + "<option value='" + type[i] + "'>普通发票</option>";
					}else if ("03" == type[i]){
						selectHtml = selectHtml + "<option value='" + type[i] + "'>普通发票（限投保人）</option>";
					}else if ("04" == type[i]){
						selectHtml = selectHtml + "<option value='" + type[i] + "'>普通发票（加盖公章）</option>";
					}else if ("05" == type[i]){
						selectHtml = selectHtml + "<option value='" + type[i] + "'>普通发票（限个人名头）</option>";
					}else if ("06" == type[i]){
						selectHtml = selectHtml + "<option value='" + type[i] + "'>普通发票（限公司名头）</option>";
					}
				}
				selectHtml = selectHtml + "</select>";
				
				art.dialog({
					title: "补开类型选择",
					content: "<div id='artPopup02' style='width:300px'><p>选择您需要开具的发票类型，请选择:</p><p class='selArea'>" + selectHtml + "</p></div>",
					ok: function () {
						window.location.href = "bill_save!enterBilling.action?billType="+jQuery("#billType").val()+"&orderSn="+orderSn;
					}
				});
			}else{
				window.location.href = "bill_save!enterBilling.action?billType="+billType+"&orderSn="+orderSn;
			}
		}

		function jumpPage(action,pageindex) {
			var path = action+"?page="+pageindex;
			$('#orderQuery_form').attr("action", path).submit(); 
		}
</script>
	</body>
</html>
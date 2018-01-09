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
	<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css">
	<title>会员中心-我的订单</title>
	<!-- 会员中心公共CSS -->
	<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
	<style type="text/css">
	#member_commantable1 table, #member_commantable1 table td,
		#member_commantable1 table th {
		border: 1px solid #cccccc;
		border-collapse: collapse;
	}
	</style>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/common/js/base.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/artDialog.js"></script>
	<script type="text/javascript">
	function del(Id){
		if(confirm("您确认要取消吗?")){
			var orderStatus=jQuery("#orderStatus").val();
			location.href="order_config_new!cancel.action?orderSn="+Id+"&orderStatus="+orderStatus;
			return false;
		}else{
			return false;
		}
	}
	
	function renewalInsure(isPublish,orderSn, kid) {
		if((orderSn != null && orderSn.indexOf("TB") < 0 ) && isPublish=="N"){
			alert("此产品已下架");
			return false;
		}else {
			var artDia = art.dialog({
				id: 'N3690',title: '消息',lock: true,cancel:false,content:"正在为您续保，请稍等片刻..."  
			});
			jQuery.ajax({
				url: "<%=serverContext %>/shop/order_config_new!renewalInsure.action?againBuyOrderSn="+orderSn+"&KID="+kid,
				dataType: "json",
				type:"post",
				success: function(data) {
					if (data.status == 'fail') {
						artDia.close();
						alert(data.message);
					} else {
						artDia.close();
						window.parent.open("<%=serverContext %>/shop/order_config_new!keepInput.action?orderSn="+data.orderSn+"&KID="+data.KID);
					}
				}
			});
		}
	}
	
	function xiajia(orderSn,isPublish){
		if(isPublish=="N"){
			jQuery.tip("此产品已下架");
			return false;
		}else{
			var sd=jQuery("#sd",window.parent.document).val();
			var ed=jQuery("#ed",window.parent.document).val();
			var kw=jQuery("#order_status").val(); 
			var apt=jQuery("#apt",window.parent.document).val();
			location.href="order_config_new!update.action?ldate="+sd+"&hdate="+ed+"&orderStatus="+kw+"&applicant="+apt+"&orderSn2="+orderSn;
		}
		
	}
	
	//首页
	function jumpone() {
		var sd=jQuery("#sd",window.parent.document).val();
		var ed=jQuery("#ed",window.parent.document).val();
		var kw=jQuery("#order_status").val(); 
		var apt=jQuery("#apt",window.parent.document).val();
		var pg=jQuery("#pg").val();
		if(pg==1){
			jQuery.tip("已为首页！");
			return false;
		}else{
			location.href="order_query!memberqueryorder.action?ldate="+encodeURIComponent(encodeURIComponent(sd))+"&hdate="+ed+"&orderStatus="+kw+"&page=1&applicant="+encodeURIComponent(encodeURIComponent(apt));
		}
	}
	/*选择页码*/
	function choosepage() {
		var kw=jQuery("#order_status").val(); 
		var value=jQuery("#pageselect").val();
		location.href="order_query!memberqueryorder.action?orderStatus="+kw+"&page="+value;
	}
	
	//上一页
	function jumpbefore() {
		var sd=jQuery("#sd",window.parent.document).val();
		var ed=jQuery("#ed",window.parent.document).val();
		var kw=jQuery("#order_status").val(); 
		var test =jQuery("#orderStatus",window.parent.document).val(); 
		var apt=jQuery("#apt",window.parent.document).val();
		var pg=jQuery("#pg").val();
		var lg=jQuery("#lg").val();
	
		if(pg==1){
			jQuery.tip("已为首页！");
			return false;
		}
		else{
			var bg=Number(pg) - Number(1);
			location.href="order_query!memberqueryorder.action?ldate="+encodeURIComponent(encodeURIComponent(sd))+"&hdate="+ed+"&orderStatus="+kw+"&page="+bg+"&applicant="+encodeURIComponent(encodeURIComponent(apt));  
		}
	}
	
	//下一页
	function jumpnext() {
		var sd=jQuery("#sd",window.parent.document).val();
		var ed=jQuery("#ed",window.parent.document).val();
		var kw=jQuery("#order_status").val(); 
		var test =jQuery("#orderStatus",window.parent.document).val(); 
		var apt=jQuery("#apt",window.parent.document).val();
		var pg=jQuery("#pg").val();
		var lg=jQuery("#lg").val();
	
		if(pg==lg || lg == 0){
			jQuery.tip("已为尾页！");
			return false;
		}
		else{
			var ng=Number(pg) + Number(1);
			location.href="order_query!memberqueryorder.action?ldate="+encodeURIComponent(encodeURIComponent(sd))+"&hdate="+ed+"&orderStatus="+kw+"&page="+ng+"&applicant="+encodeURIComponent(encodeURIComponent(apt));  
		}
	}
	//尾页
	function jumplast() {
		var sd=jQuery("#sd",window.parent.document).val();
		var ed=jQuery("#ed",window.parent.document).val();
		var kw=jQuery("#order_status").val(); 
		var apt=jQuery("#apt",window.parent.document).val();
		var pg=jQuery("#pg").val();
		var lg=jQuery("#lg").val();
		if(pg==lg){
			jQuery.tip("已为尾页！");
		}else{
			location.href="order_query!memberqueryorder.action?ldate="+encodeURIComponent(encodeURIComponent(sd))+"&hdate="+ed+"&orderStatus="+kw+"&page="+lg+"&applicant="+encodeURIComponent(encodeURIComponent(apt));  
		}
	}
	function buyNow1(isPublish,linkurl){
		if(isPublish=="N"){
			jQuery.tip("此产品已下架");
			return false;
		}else{
			window.open(linkurl,"_blank");
		}
	}
	</script>
	<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
	<body>
		<input type="hidden" value="<s:property value="page"/>" name="pc" id="pg" />
		<input type="hidden" value="<s:property value="lastpage"/>" name="lpg" id="lg" />
		<input type="hidden" value="<s:property value="orderFlag"/>" name="order_status" id="order_status" />
		<input type="hidden" value="<s:property value="orderStatus"/>" name="orderStatus" id="orderStatus" />
		<div id="member_commantable1" style="width: 747px; padding-bottom: 20px; padding-left: 37px;">
			<table id="order_table" align="center" border="0" cellspacing="0" cellpadding="0" class="member_nearorderTable member_ordersT">
				<tbody>
					<tr class="m_order_th">
						<th width="195">订单信息</th>
						<th width="82">保费</th>
						<th width="190">保险期限</th>
						<th width="100">状态</th>
						<th width="160">操作</th>
					</tr>
	
					<s:iterator id="list" value="#request.listOrders" status="status">
						<!--订单号-->
						<tr class="m_order_num">
							<td colspan="4" class="m_order_bh m_order_lbor">订单编号： 
								<b>
									<a class="m_order_nums" 
									href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />"
									target="_blank"><s:property value="#list.orderSn" /></a>
								</b>
							</td>
							<td id="ordersn_td_<s:property value="#status.index" />" class=""></td>
						</tr>
						<tr class="m_order_num2">
							<!--订单商品-->
							<td>
								<s:if test="#list.IsPublish==\"Y\""> 
									<a target="_blank" class="order_links_s" href="<s:property value="#list.HtmlPath"/>"> <s:property value="#list.productName" /></a>
								</s:if> <s:else>
									<s:property value="#list.productName" />
								</s:else></td>
							<!--保费-->
							<td><s:property value="#list.totalAmount" /></td>
							<!--保险期间-->
							<td>
								<s:date name="#list.startDate" format="yyyy-MM-dd" />
								至
								<s:date name="#list.endDate" format="yyyy-MM-dd" />
							</td>
							<!--状态-->
							<td>
								<s:if test="orderFlag==11">
									<span class="member_ordertimeCase member_sxcolor">待生效</span>
								</s:if>
								<s:elseif test="orderFlag==12">
									<span class="member_ordertimeCase member_sxcolor">生效中</span>
								</s:elseif>
								<s:else>
									<span class="member_ordertimeCase">已过期</span>
								</s:else>
							</td>
							<!--操作-->
							<td>
								<!--暂存--> 
								<s:if test="#list.orderStatus==4">
									<s:if test="#list.IsPublish==\"Y\"">
										<div class="m_ding_action">
											<a
												href="order_config_new!keepInput.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />"
												target="_blank" class="m_ck_green">继续录入</a> | <span
												class="m_ck_qx"
												onclick="del('<s:property value="#list.orderSn" />');">取消订单</span>
										</div>
									</s:if>
									<s:else>
										<div class="m_ding_action">
											<a href="###"
												onclick="return xiajia('<s:property value="#list.orderSn" />','<s:property value="#list.IsPublish"/>')"
												target="_blank" class="m_ck_green">继续录入</a> | <span
												class="m_ck_qx"
												onclick="del('<s:property value="#list.orderSn" />');">取消订单</span>
										</div>
									</s:else>
	
								</s:if> 
								<!--待支付--> 
								<s:if test="#list.orderStatus==5">
									<s:if test="#list.IsPublish==\"Y\"">
										<s:if test="#list.needUWCheckFlag==1">
											<div class="m_ding_action">
												<a onclick="payContinue('<s:property value="#list.productId" />', 'order_config_new!tpyCheckPay.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />');" href="javascript:void(0)" class="m_ck_red">继续支付</a>
												| <span class="m_ck_qx" onclick="del('<s:property value="#list.orderSn" />');">取消订单</span>
											</div>
											<s:if test='#list.wjFlag=="true" '>
												<s:if test='#list.memberId==null || #list.memberId == ""'>
													<a href="###"
														id="<s:property value="#list.orderSn" />_addshoppcart"
														onclick="addshopcart(<s:property value="#list.orderSn" />)"
														class="add_chart_go"></a>
												</s:if>
												<s:else>
													<a href="###"
														id="<s:property value="#list.orderSn" />_getshoppcart"
														onclick="getshopinfo()" class="add_chart_go add_chart_to"></a>
												</s:else>
											</s:if>
										</s:if>
										<s:else>
											<div class="m_ding_action">
												<s:if test="#list.shopcartshowflag==0">
													<a onclick="payContinue('<s:property value="#list.productId" />', 'order_config_new!pay.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />&paySn=<s:property value="#list.paySn" />');" href="javascript:void(0)" class="m_ck_red">继续支付</a>
												</s:if>
												<s:else>
													<a onclick="payContinue('<s:property value="#list.productId" />', 'member_shopcart!shopCartPay.action?checkOrderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />&paySn=<s:property value="#list.paySn" />');" href="javascript:void(0)" class="m_ck_red">继续支付</a>
												</s:else>
												| <span class="m_ck_qx"
													onclick="del('<s:property value="#list.orderSn" />');">取消订单</span>
											</div>
											<s:if test='#list.wjFlag=="true" '>
												<s:if test='#list.memberId==null || #list.memberId == ""'>
													<a href="###"
														id="<s:property value="#list.orderSn" />_addshoppcart"
														onclick="addshopcart(<s:property value="#list.orderSn" />)"
														class="add_chart_go"></a>
												</s:if>
												<s:else>
													<a href="###"
														id="<s:property value="#list.orderSn" />_getshoppcart"
														onclick="getshopinfo()" class="add_chart_go add_chart_to"></a>
												</s:else>
											</s:if>
										</s:else>
									</s:if>
								</s:if> 
								<!--已支付-->
								<s:if test="#list.orderStatus==7 || #list.orderStatus==10 || #list.orderStatus==12 || #list.orderStatus==14">
									<div class="m_ding_action">
										<a target="_blank"
											href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />">查看订单</a>
										<s:if test='#list.commentId==null || #list.commentId == ""'>
											<s:if test="#list.IsPublish==\"Y\"">|<a
												target="_top"
												href="member_comment!queryComment.action?orderSn=<s:property value="#list.orderSn" />#tr_comment_<s:property value="#list.orderSn" />"> 产品评价</a>
											</s:if>
										</s:if>
										<s:else>
									|<span class="m_ck_qxs">已评价</span>
										</s:else>
									</div>
									<div class="m_ding_action">
										<s:if test="#list.epathFlag==1 "><a target="_blank" class="m_bd_down" href="order_query!receiptsDownload.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />">保单下载</a>
										|</s:if>
										<a onclick="renewalInsure('<s:property value="#list.IsPublish" />','<s:property value="#list.orderSn" />','<s:property value="#list.KID" />');" class="m_bd_down" href="javascript:void(0);">续保</a>
									</div>
								</s:if> 
								<!-- 撤单 --> 
								<s:if test="#list.orderStatus==9 || #list.orderStatus==11 || #list.orderStatus==13">
										<div class="m_ding_action">
											<a target="_blank"
												href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />">查看订单</a>
											|
											<a onclick="renewalInsure('<s:property value="#list.IsPublish" />','<s:property value="#list.orderSn" />','<s:property value="#list.KID" />');" class="m_bd_down" href="javascript:void(0);">续保</a>
										</div>
								</s:if>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<s:if test="listOrders.isEmpty() && callType ==1">
				<div class="member_noorder" style="margin-left: 0px;">
					<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您目前没有订单记录</span><br />
					<span><a target="_parent"
						href="order_query!queryOrder.action">查看全部订单</a></span>
				</div>
			</s:if>
		</div>
		<s:if test="callType!=1">
			<div id="orderFenYe" class="member_pagearea">
				<span class="member_page_dis">共<s:property value="lastpage"/>页，<s:property value="count"/>条记录</span>
				<span class="member_pagebutton">
					<a href="###" onclick="return jumpone();">首页</a>
				</span> 
				<span class="member_pagebutton">
					<a href="###" onclick="return jumpbefore();">上一页</a>
				</span>
				<span class="member_go_sel">
                   	<select name="pageselect" id="pageselect" class="member_go_sels" onchange="choosepage()">
                   	</select>
                </span>
				<span class="member_pagebutton">
					<a href="###" onclick="return jumpnext();">下一页</a>
				</span> 
				<span class="member_pagebutton">
					<a href="###" onclick="return jumplast();">尾页</a>
				</span>
			</div>
		</s:if>
		<script type="text/javascript">
			/*已支付订单合并列*/
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
			/*添加购物车*/
			function addshopcart(ordersn){
					jQuery.ajax({
					    url:"<%=serverContext %>/shop/shopping_cart!addOrders.action?orderSn="+ordersn,
					    type:"post",
						dataType:"json",
						success:function(data) {
							if (data.status == "Y") {
									//解绑onclick实际,并重新绑定事件
									jQuery('#'+ordersn+'_addshoppcart').unbind('click').removeAttr('onclick').click(function(){
										window.parent.location.href="<%=serverContext %>/shop/member_shopcart!getShopCartINF.action"; 
									});  
									jQuery('#'+ordersn+'_addshoppcart').addClass("add_chart_to");
						             art.dialog({
						                 id:'shaop_car',
						                 title: '提示',
						                 content: '<div style="padding:10px 40px;">订单已成功加入购物车！</div>',
						                 fixed: true,
						                 button: [
						                     {
						                         name: '确认'
						                     },
						                     {
						                         name: '去购物车结算',
						                         callback: function () {
						                         	window.parent.location.href="<%=serverContext %>/shop/member_shopcart!getShopCartINF.action";
						                         }
						                      }
						                 ]
						             });
								}else if(data.status == "N"){
									alert("加入购物车失败，请重新添加");
									return;
								}else if(data.status == "M"){
									alert("购物车中订单渠道来源不同,不能加入购物车");
									return;
								}else if(data.status == "X"){
									alert("该产品不能加入购物车");
								}else{
									alert("购物车中同一被保人同一产品存在不同订单中");
									return;
								}
						}
				   });  
			};
			/*进入购物车*/
			function getshopinfo(){
				window.parent.location.href="<%=serverContext %>/shop/member_shopcart!getShopCartINF.action";
			}
			var xinhuaConfirm = false;  // 新华产品提示框弹出判断
			/*继续支付*/
			function payContinue(productId, url) {
				// 新华产品弹出核保确认提示框
				if (productId.indexOf("1014") == 0 && !xinhuaConfirm) {
					art.dialog({
						title:'消息',
					    content: '<p style="text-align: center;">本产品同一被保人当天只能提交一次<br>确认提交？<p>',
					    ok: function () {
					    	xinhuaConfirm = true;
					    	payContinue(productId, url);
					    },
					    cancelVal: '关闭',
					    cancel: true
					});
					return;
				}
				window.open(url);
			}
			/*会员中心实际支付金额优惠tib信息提示*/
			jQuery(".member_hui").hover(function() {
				jQuery(this).find(".member_h_des").show();
			}, function() {
				jQuery(this).find(".member_h_des").hide();
			});
		</script>
	</body>
</html>

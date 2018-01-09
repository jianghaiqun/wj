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
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css">
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
							window.location.href="<%=serverContext %>/shop/member_shopcart!getShopCartINF.action"; 
						});  
						jQuery('#'+ordersn+'_addshoppcart').addClass("add_chart_to");
						jQuery('#'+ordersn+'_addshoppcart').html("去购物车结算");
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
						art.dialog({ icon: 'error', content: '加入购物车失败，请重新添加' });
						return;
					}else if(data.status == "M"){
						art.dialog({ icon: 'error', content: '购物车中订单渠道来源不同,不能加入购物车' });
						return;
					}else if(data.status == "X"){
						art.dialog({ icon: 'error', content: '该产品不能加入购物车' });
					}else{
						art.dialog({ icon: 'error', content: '购物车中同一被保人同一产品存在不同订单中' });
						return;
					}
			}
	   });  
};
/*进入购物车*/
function getshopinfo(){
	window.parent.location.href="<%=serverContext %>/shop/member_shopcart!getShopCartINF.action";
}
/*会员中心实际支付金额优惠tib信息提示*/
	jQuery(".member_hui").hover(function() {
		jQuery(this).children().find(".member_h_des").show();
	}, function() {
		jQuery(this).children().find(".member_h_des").hide();
	});
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
											    url:"<%=serverContext %>/shop/order_query!dellMemberCenterOrder.action?orderSn="+ordersn,
											    type:"post",
												dataType:"json",
												success:function(data) {
													if (data.status == "Y") {
															window.parent.location.href="<%=serverContext %>/shop/order_query!queryOrder.action";
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
}
var xinhuaConfirm = false;  // 新华产品提示框弹出判断
/*继续支付*/
function payContinue(ordersn, htmlPath, productId, updateWarningFlag, url) {
    // 是否需求弹出产品更新提示
	if (updateWarningFlag == "Y") {
        art.dialog({
            title:'消息',
            content: '<p style="text-align: center;">产品已调整请重新购买，此订单即将取消<p>',
            button: [
                {
                    name: '确认',
                    callback: function () {
                        jQuery.ajax({
                            url:"<%=serverContext %>/shop/order_config_new!cancel.action?orderSn="+ordersn,
                            type:"get",
                            dataType:"json",
                            async: false,
                            success:function(data) {
                                if (data.status == "Y") {
                                    window.location.reload();
                                    window.open(htmlPath, "_blank");
                                }else{
                                    art.dialog({ icon: 'error', content: data.message });
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
        return;
	}
	// 新华产品弹出核保确认提示框
	if (productId.indexOf("1014") == 0 && !xinhuaConfirm) {
		art.dialog({
			title:'消息',
		    content: '<p style="text-align: center;">本产品同一被保人当天只能提交一次<br>确认提交？<p>',
		    ok: function () {
		    	xinhuaConfirm = true;
		    	payContinue(ordersn, htmlPath, productId, updateWarningFlag, url);
		    },
		    cancelVal: '关闭',
		    cancel: true
		});
		return;
	}
	window.open(url);
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
                	jQuery.ajax({
						    url:"<%=serverContext %>/shop/order_config_new!cancel.action?orderSn="+Id,
						    type:"get",
							dataType:"json",
							async: false,
							success:function(data) {
								if (data.status == "Y") {
									window.location.reload();
								}else{
									art.dialog({ icon: 'error', content: data.message });
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

// 继续录入按钮
function keepInput (orderSn, kId, htmlPath, updateWarningFlag) {
    // 是否需求弹出产品更新提示
    if (updateWarningFlag == "Y") {
        art.dialog({
            title:'消息',
            content: '<p style="text-align: center;">产品已调整请重新购买，此订单即将取消<p>',
            button: [
                {
                    name: '确认',
                    callback: function () {
                        jQuery.ajax({
                            url:"<%=serverContext %>/shop/order_config_new!cancel.action?orderSn="+orderSn,
                            type:"get",
                            dataType:"json",
                            async: false,
                            success:function(data) {
                                if (data.status == "Y") {
                                    window.location.reload();
                                    window.open(htmlPath, "_blank");
                                    return false;
                                }else{
                                    art.dialog({ icon: 'error', content: data.message });
                                    return false;
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
        return false;
    }

    var linkurl = "<%=serverContext %>/shop/order_config_new!keepInput.action?orderSn="+orderSn+"&KID="+kId;
    window.open(linkurl,"_blank");
}
function xiajia(orderSn,isPublish){
	if(isPublish=="N"){
		art.dialog({ icon: 'warning', content: '此产品已下架!' });
		return false;
	}else{
		var sd=jQuery("#sd",window.parent.document).val();
		var ed=jQuery("#ed",window.parent.document).val();
		var kw=jQuery("#orderStatus  option:selected").val(); 
		var apt=jQuery("#apt",window.parent.document).val();
		location.href="order_config_new!update.action?ldate="+sd+"&hdate="+ed+"&orderStatus="+kw+"&applicant="+apt+"&orderSn2="+orderSn;
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

function dealRenewal(policyNo) {
	jQuery.ajax({
        url:"<%=serverContext %>/shop/renewal_insurance!checkRenewal.action?policyNo="+policyNo,
        type:"get",
        dataType:"json",
        async: false,
        success:function(data) {
            if (data.status == "Y") {
                window.open("<%=serverContext %>/shop/renewal_insurance!zhenAiChoseProductPlan.action?policyNo="+policyNo, "_blank");
                return false;
            }else{
                art.dialog({ icon: 'error', content: data.message });
                return false;
            }
        }
    });
}
</script>
</head>
<div class="member_orderlist" id="member_commantable">
<table cellspacing="0" cellpadding="0" border="0" align="center" class="member_nearorderTable member_ordersT" id="order_table">
	<tbody>
		<tr class="m_order_th">
            <th width="195">订单信息</th>
            <th width="82">投保人</th>
            <th width="82">保费</th>
            <th width="80">实际支付</th>
            <th width="110">下单时间</th>
            <th width="100">状态</th>
            <th width="92">操作</th>
        </tr>
		<s:iterator id="list" value="#request.listOrders" status="status" >
			<!--订单号-->
			<tr class="m_order_num">
                <td class="m_order_bh m_order_lbor clear_bor_right" colspan="2">
                                    订单编号： <b><a  class="m_order_nums" href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />" target="_blank"><s:property value="#list.orderSn" /></a></b>
                </td>
                <td id="ordersn_td_<s:property value="#status.index" />" class="m_order_bh clear_bor_all"></td>
                <td class="m_order_bh clear_bor_all"></td>
                <td class="m_order_bh clear_bor_all"></td>
                <td class="m_order_bh clear_bor_all"></td>
                <td class="m_order_rbor m_order_bh clear_bor_left"></td>
            </tr>
			<tr class="m_order_num2">
				<!--订单商品-->
				<td>
					<s:if test="#list.IsPublish==\"Y\""> 
							<a target="_blank" class="order_links_s" href="<s:property value="#list.HtmlPath"/>"> <s:property value="#list.productName" /> </a>
					</s:if> 
					<s:else>
						<s:property value="#list.productName" />
					</s:else> 
				</td>
				<!-- 投保人 -->
                <td>
                    <span class="memberOrder-name"><s:property value="#list.applicantname" /></span>
                </td>
				<!--保费-->
				<td>
					<i class="f_mi">￥</i><s:property value="#list.totalAmount" />
				</td>
				<!--实际支付-->
				<td id="payamount_td_<s:property value="#status.index" />" rowspan="<s:property value="#list.row_span_num"/>">
					<s:if test="#list.orderStatusFlag==\"N\"">
						<span class="member_orderchargeCase">
							未支付
						</span>
					</s:if>
					<s:elseif test="#list.couponFlag==\"N\"">
						<span class="member_orderchargeCase member_hui">
						<s:if test="#list.paySn.indexOf('BG')>=0">
							￥0.0
						</s:if>
						<s:else>
							￥<s:property value="#list.payamount" />
						</s:else>
						</span>
					</s:elseif>
					<s:else>
						<em class="member_hui">
							<span class="member-hui-icon">
							<div style="display: none" class="member_h_des">
								<div id="coupon_tib_message">
									<s:property value="#list.activity_message" escape="false"/>
									<s:if test="#list.coupon_message!=\"\""><b>优惠券：</b><s:property value="#list.coupon_message" /><br></s:if>
									<s:if test="#list.point_message!=\"\""><b>积分抵值：</b><s:property value="#list.point_message" /><br></s:if>
									<span class="mes_jao"></span>
								</div>
							</div>
							</span>
							<span class="member_orderchargeCase member_hui">￥<s:property value="#list.payamount" /></span> 
						</em>
					</s:else>
				</td>
				<!--下单时间-->
				<td>
					<s:date name="#list.createDate" format="yyyy-MM-dd" /> <br><s:date name="#list.createDate" format="HH:mm" />
				</td>
				<!--状态-->
				<td>
					<s:if test="#list.orderStatus==7">
						<span class="member_orderastateCase"><s:property value="#list.CodeName" /></span>
					</s:if>
					
					<s:elseif test="#list.orderStatus==6">
						<span class="member_order_red"><s:property value="#list.CodeName" /></span>
					</s:elseif> 
					<s:else>
						<span class="member_ordertimeCase"><s:property value="#list.CodeName" /></span>
					</s:else> 
				</td>
				<!--操作-->
				<td>
					<!--暂存-->
					<s:if test="#list.orderStatus==4">
						<s:if test="#list.IsPublish==\"Y\"">
							<div class="m_ding_action">
								 <a href="javascript:void(0)" onclick="return keepInput('<s:property value="#list.orderSn" />','<s:property value="#list.KID"/>', '<s:property value="#list.HtmlPath"/>', '<s:property value="#list.updateWarningFlag" />')" class="m_ck_green">继续录入</a>
								 <span class="m_ck_qx" onclick="del('<s:property value="#list.orderSn" />');">取消订单</span>
							</div>
						</s:if>
						<s:else>
							<div class="m_ding_action">
								<a href="javascript:void(0)" onclick="return xiajia('<s:property value="#list.orderSn" />','<s:property value="#list.IsPublish"/>')"  class="m_ck_green">继续录入</a>
								<span class="m_ck_qx" onclick="del('<s:property value="#list.orderSn" />');">取消订单</span>
							</div>
						</s:else>

					</s:if>
					<!--待支付-->
					<s:if test="#list.orderStatus==5">
						<s:if test="#list.IsPublish==\"Y\"">
							<s:if test="#list.needUWCheckFlag==1">
								<div class="m_ding_action">
									<s:if test="#list.shopcartshowflag==0">
										<a onclick="payContinue('<s:property value="#list.orderSn" />', '<s:property value="#list.HtmlPath"/>', '<s:property value="#list.productId" />', '<s:property value="#list.updateWarningFlag" />', 'order_config_new!tpyCheckPay.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />');" href="javascript:void(0)" class="m_ck_red">继续支付</a>
									</s:if>
									<s:else>
										<a onclick="payContinue('<s:property value="#list.orderSn" />', '<s:property value="#list.HtmlPath"/>', '<s:property value="#list.productId" />', '<s:property value="#list.updateWarningFlag" />', 'member_shopcart!toCheckInsure.action?checkOrderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />');" href="javascript:void(0)" class="m_ck_red">继续支付</a>
									</s:else>
									<span class="m_ck_qx" onclick="del('<s:property value="#list.orderSn" />');">取消订单</span>
								</div>
								<s:if test="#list.wjFlag=='true' ">
									<s:if test="#list.shopcartshowflag==0">
										<a href="###" id="<s:property value="#list.orderSn" />_addshoppcart" onclick="addshopcart(<s:property value="#list.orderSn" />)" class="add_chart_go">加入购物车</a>
									</s:if>
									<s:else >
										<a href="###" id="<s:property value="#list.orderSn" />_getshoppcart" onclick="getshopinfo()" class="add_chart_go add_chart_to">去购物车结算</a>
									</s:else>
								</s:if>
							</s:if>
							<s:else>
								<div class="m_ding_action">
									<s:if test="#list.shopcartshowflag==0">
										<a onclick="payContinue('<s:property value="#list.orderSn" />', '<s:property value="#list.HtmlPath"/>','<s:property value="#list.productId" />', '<s:property value="#list.updateWarningFlag" />','order_config_new!pay.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />');" href="javascript:void(0)" class="m_ck_red">继续支付</a>
									</s:if>
									<s:else>
										<a onclick="payContinue('<s:property value="#list.orderSn" />', '<s:property value="#list.HtmlPath"/>','<s:property value="#list.productId" />', '<s:property value="#list.updateWarningFlag" />','member_shopcart!shopCartPay.action?checkOrderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />');" href="javascript:void(0)" class="m_ck_red">继续支付</a>
									</s:else>
									<input type="hidden" value="<s:property value="#list.productId" />"/>
									<span class="m_ck_qx" onclick="del('<s:property value="#list.orderSn" />');">取消订单</span>
								</div>
								<s:if test='#list.wjFlag=="true" '>
									<s:if test='#list.shopcartshowflag==0'>
										<a href="###" id="<s:property value="#list.orderSn" />_addshoppcart" onclick="addshopcart(<s:property value="#list.orderSn" />)" class="add_chart_go">加入购物车</a>
									</s:if>
									<s:else >
										<a href="###" id="<s:property value="#list.orderSn" />_getshoppcart" onclick="getshopinfo()" class="add_chart_go add_chart_to">去购物车结算</a>
									</s:else>
								</s:if>
							</s:else>
						</s:if>
                        <s:else>
                            <!--订单过期后仍未支付且产品已下线-->
                            <s:if test="#list.order_status=='13'">
                                <div class="m_ding_action">
                                    <a href="###" id="<s:property value="#list.orderSn" />_dellorder" onclick ="dellorder(<s:property value="#list.orderSn" />)" >删除</a>
                                </div>
                            </s:if>
                        </s:else>
					</s:if>
					<!--已支付-->
					<s:if test="#list.orderStatus==7 || #list.orderStatus==10 || #list.orderStatus==12 || #list.orderStatus==14 || #list.orderStatus==6">
						<div class="m_ding_action">
						<a target="_blank"
							href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />">查看订单</a>
							<!--理财险 处理中 -->
						<s:if test="#list.orderStatus!=6">
							<s:if test='#list.commentId==null || #list.commentId == ""'>
									<s:if test="#list.IsPublish==\"Y\" && #list.productId != '210301001'">
									<a target="_top"
									href="member_comment!queryComment.action?orderSn=<s:property value="#list.orderSn" />#tr_comment_<s:property value="#list.orderSn" />">产品评价</a>
									</s:if>
							</s:if>
							<s:else >
								<span class="m_ck_qxs">已评价</span>
							</s:else>
							</div>
							<div class="m_ding_action">
								<s:if test="#list.epathFlag==1 ">
									<a target="_blank" class="m_bd_down"
									href="order_query!receiptsDownload.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />">保单管理</a>
								</s:if>
								<s:if test="#list.ensureLimitType!=\"L\" && #list.productId != '210301001' ">
								<s:if test="#list.zhenAiXuQi == 1 ">
									<a class="m_bd_buy" href="javascript:void(0)" onclick="dealRenewal('<s:property value="#list.policyNo" />')">续保</a>
								</s:if>
								<s:else >
									<a class="m_bd_buy" href="<s:property value="#list.HtmlPath"/>" target="_blank">再次购买</a>
								</s:else>
								</s:if>
							</div>
						</s:if>
					</s:if>
					<!-- 撤单 -->
					<s:if test="#list.orderStatus==9 || #list.orderStatus==11 || #list.orderStatus==13">
						<div class="m_ding_action"><a target="_blank"
							href="order_config_new!linkOrderDetails.action?orderSn=<s:property value="#list.orderSn" />&KID=<s:property value="#list.KID" />">查看订单</a>
							<s:if test="#list.ensureLimitType!=\"L\"">
							<a class="m_bd_buy" href="<s:property value="#list.HtmlPath"/>" target="_blank">再次购买</a>
							</s:if>
                            <a href="javascript:void(0)" id="<s:property value="#list.orderSn" />_dellorder" onclick="dellorder(<s:property value="#list.orderSn" />)" >删除</a>
						</div>
					</s:if>
					<!-- 取消订单 -->
					<s:if test="#list.orderStatus==3 || #list.orderStatus==8 ">
						<div class="m_ding_action">
						<a href="javascript:void(0)" id="<s:property value="#list.orderSn" />_dellorder" onclick ="dellorder(<s:property value="#list.orderSn" />)" >删除</a>
						</div>
					</s:if>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<s:if test="listOrders.isEmpty()">
<div class="no-shop"><div class="no-shop-tip"><a href="<%=Config.getFrontServerContextPath() %>">最近没有订单，去选购商品吧！~</a></div></div>
</s:if>
</div>
<div class="clear h20"></div>
<input type="hidden" id="callType" value="<s:property value="#request.callType" />"/>

<s:if test="callType!=1">
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
					<li class="page_prev"><a href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#request.page-1"/>','<s:property value="#request.page_count"/>','member_ordertable')"><span class="">上一页</span></a></li>
				</s:else>
				<s:iterator id="pageFootMap" status="st" value="#request.pageFootList">
					<li class="<s:property value="#pageFootMap.class" />">
						<s:if test="#pageFootMap.index==\"...\"">
							<span><s:property value="#pageFootMap.index"/></span>
						</s:if>
						<s:else>
							<a href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#pageFootMap.index"/>','<s:property value="#request.page_count"/>','<s:property value="#pageFootMap.idalias"/>')">
			        			<span><s:property value="#pageFootMap.index"/></span>
			        		</a>
						</s:else>
	        		</li>
	        	</s:iterator>
	        	<s:if test="#request.page==#request.lastpage || #request.lastpage == 0">
					<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
				</s:if>
				<s:else>
					<li class="page_next"><a href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#request.page+1"/>','<s:property value="#request.page_count"/>','member_ordertable')"><span class="">下一页</span></a></li>
				</s:else>
			</ul>
		</div>
	</div>
</div>
<div class="clear h20"></div>
</s:if>
</s:if>

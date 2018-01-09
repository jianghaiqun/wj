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
	<title>会员中心-银行卡信息</title>
	<!-- 会员中心公共CSS --> 
	<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
	<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"/>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
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
			<a href='<%=Config.getFrontServerContextPath() %>' target='_self' ><span class="orange">您现在的位置：首页</span></a>
			<span class="separator">></span>
			<a href='member_center!index.action' target='_self'>会员中心</a>
			<span class="separator">></span><span class="separator1">银行卡信息</span>
		</div>
		<div class="member_con">
			<input type="hidden" id="fullDegree" value="<%=session.getAttribute("fullDegree") %>" />
			<input type="hidden" id ="orderFlag" name="orderFlag" value="<s:property value="orderFlag"/>" />
			<!-- 会员中心左侧菜单导航 -->
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right ">
				<div class="member_boxs">
				<div class="member-htit">
				            <span class="member-titsc">银行卡管理</span>
                        
				        </div>
         				<div class="clear h20"></div>
					<div class="lc_min_height" id="member_commantable">
						<div class="clear h20"></div>
						
						<s:if test="#request.listRecord==null || #request.listRecord.size() == 0">
			                <div class="no-shop">
		                       <div class="no-shop-tip">购买过理财产品才会保存银行卡信息！</div>
		                    </div>
						</s:if>
					
                  
						<s:iterator id="list" value="#request.listRecord" status="status">
						      <table class="member_nearorderTable member_lc" cellspacing="0" cellpadding="0" border="0" align="center">
						        <thead>
						          <tr>
						            <s:if test="#list.bankstatus != ''">
						            	<th colspan="4">已认证银行卡</th>
						            </s:if>
						            <s:else> 
						            	<th colspan="4">现有银行卡</th>
						            </s:else>
						          </tr>
						        </thead>
						        <tbody>
						         <tr class="member_td_c clear_lctable">
						            <td class="clear_lc_tdc" width="130">银行名称</td>
						            <td class="clear_lc_tds" width="320">卡号</td>
						            <td class="clear_lc_tds" width="130">持卡人</td>
						            <td width="140"></td> 
						          </tr>
						           <tr class="clear_lctable">
						            <td class="clear_lc_tdc"><img src="../../style/shop/images/pay/logo/bank<s:property value="#list.bankcode"/>.jpg" alt=""></td>
						            <td class="clear_lc_tds"><s:property value="#list.bankno"/></td>
						            <td class="clear_lc_tds"><s:property value="#list.username"/></td>
						            <td>已认证</td> 
						          </tr>
						          <tr>
						            <td class="member_td_lcsf" colspan="4">
						            <s:if test="#list.bankstatus != ''">
							            <s:if test="#list.orderexists != ''">
							            	此银行卡已绑定未退保的理财险产品，退保后才可删除
							            </s:if>
							            <s:else> 
							            	此银行卡已绑定未支付的理财险产品
							            </s:else>
						            </s:if>
						            <s:else> 
						            	<span class="del_banklist" attr-id="<s:property value="#list.id"/>">
						            	删除
						            	</span>
						            </s:else>
						            </td>
						          </tr>
						        </tbody>
						      </table>
					      </s:iterator>
      
</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
		<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
	 	<script type="text/javascript">
	 	if(jQuery("#orderFlag").val()!=null){
			var orderstatus = jQuery("#orderFlag").val();
			jQuery("#orderStatus").val(orderstatus);
		}
		function queryOrder() {
			var sd = jQuery("#sd").val();
			var ed = jQuery("#ed").val();
			if (sd == "" || ed == "") {
				gotoPage('order_query!queryOrderlist.action', '1','','member_ordertable');
			} else {

				if (sd > ed) {
					alert("开始日期不能大于结束日期！");
					return false;
				} else {
					gotoPage('order_query!queryOrderlist.action', '1','','member_ordertable');
				}
			}
		}
		
		jQuery(".del_banklist").click(function(){
			var bankId = jQuery(this).attr("attr-id");
			art.dialog({
				title:'消息确认',
				content: '是否删除',
				button: [ 
				{ 
					name: '确认', 
					callback: function () { 
						window.location.href="order_query!financingBank.action?cardFlag=Y&cardid="+bankId; 
						 return true; 
					 },focus: true
				},
		        {
		            name: '取消',
		            callback: function () {
		                return true;
		            }
		        }]
			});
		});
		
		
	</script>
	</body>
</html>
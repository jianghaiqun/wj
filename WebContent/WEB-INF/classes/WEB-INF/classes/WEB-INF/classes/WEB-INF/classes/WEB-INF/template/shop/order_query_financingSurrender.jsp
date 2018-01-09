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
	<title>会员中心-申请退保</title>
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
			<span class="separator">></span><span class="separator1">申请退保</span>
		</div>
		<div class="member_con">
			<input type="hidden" id="fullDegree" value="<%=session.getAttribute("fullDegree") %>" />
			<input type="hidden" id ="orderFlag" name="orderFlag" value="<s:property value="orderFlag"/>" />
			<!-- 会员中心左侧菜单导航 -->
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right ">
				<div class="member_boxs">
				<!-- 未支付订单列表 start -->
				 	    <!-- 未支付订单列表 start -->
				 	    <div class="member-htit">
				            <span class="member-titsc">申请退保</span>
				        </div>
         				<div class="clear h20"></div>
	                 	<div id="member_ordertable">
                    <div class="clear h20"></div>
                  <table class="lc_tb_stable">
                    <tr>
                      <td colspan="4"><h2 class="stable_h2"><s:property value="recordMap.productname"/></h2></td>
                    </tr>
                    <tr>
                      <td class="sf_t_lef" width="98px">订单号：</td>
                      <td><s:property value="recordMap.ordersn"/> </td>
                      <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保单号：</td>
                      <td><s:property value="recordMap.policyno"/></td>
                    </tr>
                    <tr>
                      <td class="sf_t_lef">当前收益：</td>
                      <td colspan="3"><b class="sf_t_c"><s:property value="recordMap.income"/>元</b><span class="sf_tip">生效后未满365天年化利率见产品详情页面</span></td>
                    </tr>
                    <tr>
                      <td class="sf_t_lef">投保金额：</td>
                      <td colspan="3"><s:property value="recordMap.principal"/>元</td>
                    </tr>
                    <tr>
                      <td class="sf_t_lef">退保银行卡：</td>
                      <td colspan="3"><s:property value="recordMap.bankname"/>&nbsp;&nbsp;&nbsp;<s:property value="recordMap.bankno"/>&nbsp;&nbsp;&nbsp;（原路返回）</td>
                    </tr>
                    <tr>
                      <td class="sf_t_lef">预计到账金额：</td>
                      <td colspan="3"><em class="sf_t_d"><s:property value="recordMap.ss"/>元<span class="sf_t_spanf">预计到账时间1-3工作日（节假日顺延），预计到账金额仅供参考，实际以退到卡内金额为准</span> </em>
                     </td>
                    </tr>
                  </table>
                  <div class="clear h40"></div>
              <table class="tbr_lctbale">
                <tr>
                  <td width="98px">投保人手机号：</td>
                  <td class="phone_wrap">
                  	<input type="text" readonly="readonly" class="tb_input_cpoo"  value="<s:property value="recordMap.phone"/>"  />
                  	<input type="hidden" id="phone" value="<s:property value="recordMap.mobile"/>"/>
                  	<input type="hidden" id="orderSn" value="<s:property value="recordMap.ordersn"/>"/>
                  	<input type="hidden" id="insStatus" value="<s:property value="recordMap.InsStatus"/>"/>
                  </td>
                  <td  ><p class="code_wrap"><span class="code_again">点击验证</span></p> </td>
                </tr>
                <tr>
                  <td></td>
                  <td ><input type="text" id="code" class="tb_input_cpoo"  value="请输入手机收到的验证码" onfocus="javascript:if(this.value=='请输入手机收到的验证码')this.value='' " onblur="javascript:if(this.value=='')this.value='请输入手机收到的验证码' "  /></td>
                  <td>
                  <!-- <em class="yz_csyes"></em><em class="yz_csyes error"></em> -->
                  </td>
                </tr>
                <tr>
                <td></td><td colspan="2"><input type="button" class="tb_input_tjsf" id="tb_submit" value="提交" /></td></tr>
              </table>
						</div>
						<!-- end -->
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
	</script>
	
	<script>


      jQuery(document).ready(function($) {
    	  
    	  var ins = jQuery("#insStatus").val();
    	  if(ins=='' || ins!='1'){
    		  //不是承保中的产品返回到列表页
    		  window.location.href= sinosoft.base+'/shop/order_query!financingManager.action';
    	  }
    	  
    	  
        jQuery("#tb_submit").click(function(event) {
        	var code = jQuery("#code").val();
        	var orderSn = jQuery("#orderSn").val(); 
        	jQuery.ajax({
	   			url: sinosoft.base + "/shop/order_query!checkCode.action?orderSn="+orderSn+"&phoneCode="+code,
	   			type: "post",
	   			dataType: "json",
	   			success: function(data){
	   				var status = data.status;
	   				var msg = data.msg;
	   				if (status == 0) {
	   					art.dialog({
	  	   	              title:'提交成功',
	  	   	              id: 'tiip',
	  	   	              content: '<div class="tb_mes_des"><span>退保申请提交成功！</span><p>预计到账时间<em>1-3工作日</em>（节假日顺延），如2日内未到账<br />请联系在线客服或拨打开心保客服热线4009-789-789</p></div>',
	  	   	               ok: function () {
	  	   	            	window.location.href= sinosoft.base+'/shop/order_query!financingManager.action';
	  	   	              },
	  	   	            });
					}else{
						art.dialog({
	  	   	              title:'提交失败',
	  	   	              id: 'tiip',
	  	   	              content: '<div class="tb_mes_des"><span>退保申请提交失败！</span><p>'+msg+'</p></div>',
	  	   	               ok: function () {
	  	   	                    return true;
	  	   	                },
	  	   	            });
					}
	   				
	   			}
	   		});
        	
            
        });


        new timeCountDown({
          whichPage: 'sign',
          sendCode: sendPhoneCode
        });
        function sendPhoneCode(){
        	var phone = jQuery("#phone").val();
        	if(phone!=null && phone!=""){
		     	jQuery.ajax({
		   			url: sinosoft.base + "/shop/order_query!sendCode.action?rtype=1&ways="+phone,
		   			type: "post",
		   			dataType: "json",
		   			success: function(data){
		   				
		   			}
		   		});
        	}
        } 
        
      });
    </script>
	</body>
</html>
<!DOCTYPE html>
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
    <script type="text/javascript" src="<%=Config.getValue(" JsResourcePath ") %>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
    <s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>

<body class="up-bg-qh">
    <s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
    <div class="wrapper">
        <div class="daohang">
            <span class="daohang_home"></span>
            <a href='<%=Config.getFrontServerContextPath() %>' target='_self'><span class="orange">您现在的位置：首页</span></a>
            <span class="separator">></span>
            <a href='member_center!index.action' target='_self'>会员中心</a>
            <span class="separator">></span><span class="separator1">发票申请</span>
        </div>
        <div class="member_con">
            <!-- 会员中心左侧菜单导航 -->
            <jsp:include page="member_center_left.jsp"></jsp:include>
            <s:form id="billInfoSave_form" action="bill_save!saveBillInfo.action" method="post" enctype="multipart/form-data" >
            
	            <div class="member_right ">
					<input type="hidden" id="fullDegree" value="<%=session.getAttribute("fullDegree")%>"> 
	            	<input type="hidden" value='<s:property value="#request.deliverAddressCount" />' id="deliverAddrCount" name="deliverAddrCount">
	            	<input type="hidden" value='<s:property value="#request.billType" />' id="billType" name="billType">
	            	<input type="hidden" value='<s:property value="#request.order.orderSn" />' id="orderSn" name="orderSn">
	            	<input type="hidden" value='<s:property value="#request.orderSns" />' id="orderSns" name="orderSns">
	            	<input type="hidden" value='<s:property value="#request.billRequireFlag" />' id="billRequireFlag" name="billRequireFlag">
	            	<input type="hidden" id="deliverProvinceName" name="deliverProvinceName" value='<s:property value="#request.deliverProvinceName" />'>
	            	<input type="hidden" id="deliverCityName" name="deliverCityName" value='<s:property value="#request.deliverCityName" />'>
	            	<input type="hidden" id="deliverDetailAddr" name="deliverDetailAddr" value='<s:property value="#request.deliverDetailAddr" />'>
	            	<input type="hidden" id="deliverName" name="deliverName" value='<s:property value="#request.deliverName" />'>
	            	<input type="hidden" id="deliverTel" name="deliverTel" value='<s:property value="#request.deliverTel" />'>
	            	<input type="hidden" id="deliverZipCode" name="deliverZipCode" value='<s:property value="#request.deliverZipCode" />'>
	            	<input type="hidden" id="titleName" name="titleName" value='<s:property value="#request.titleName" />'>
	            	<input type="hidden" id="billTitleCount" name="billTitleCount" value='<s:property value="#request.billTitleCount" />'>
	            	<input type="hidden" id="moreThan200Flag" name="moreThan200Flag" value='<s:property value="#request.moreThan200Flag" />'>
	                <div class="member_boxs">
	                	<div class="member-htit">
				            <span class="member-titsc">邮寄地址填写</span>
				        </div>
	                    <!-- add by SongZairan. 2015.6.12 -->
	                    <div class="post_address">
	                    	
	                        <b class="member_b">发票类型：<span><s:property value="billTypeName"/></span></b>
	                        <b class="member_b">邮寄方式：
	                        	<span>
	                        		<s:if test="#request.moreThan200Flag">快递包邮</s:if>
	                        		<s:else>快递到付</s:else>
	                        	</span>
	                        </b>
	                        <s:if test="#request.deliverAddressCount == 0">
	                        <b class="member_b">邮寄地址：您还没有增加邮寄地址</b>
	                        	<p class="member_add"><span class="btn_post01" id="addEmailAddress">新增邮寄地址</span></p>
	                        </s:if>
	                        <s:else>
	                        	<div style="padding-left:18px;margin-bottom:12px;"><b>邮寄地址：<span class="btn_post01" id="addEmailAddress">新增邮寄地址</span></b>
		                        	已有<b style="color:#FC7601; padding: 0 2px;"><s:property value="#request.deliverAddressCount" /></b>
		                        	个邮寄地址，还可添加<b style="color:#FC7601; padding: 0 2px;">
		                        	<s:property value="#request.leftdeliverAddressCount" /></b>个
		                        </div>
	                        </s:else>
							
	                        <ul id="sendEmail" class="newSpace">
	                        	<s:iterator id="list" value="#request.deliverAddressList" step="1" status="status">
		                            <li>
		                                <p class="icon">寄送至</p>
		                                <p class="cont">
		                                    <label class="label_radio" for="radio01">
		                                    <s:if test="#request.isDefault==1">
												<input type="radio" name="radioDeliverAddr" id="radioDeliverAddr_<s:property value="#status.index" />" value="#list.ID" checked>
											</s:if> 
											<s:else>
												<input type="radio" name="radioDeliverAddr" id="radioDeliverAddr_<s:property value="#status.index" />" value="#list.ID">
											</s:else>
		                                        
		                                        <input type="hidden" id="province_name_<s:property value="#status.index" />" value="<s:property value="#list.provinceName" />">
		                                        <input type="hidden" id="city_name_<s:property value="#status.index" />" value="<s:property value="#list.cityName" />">
		                                        <input type="hidden" id="detail_addr_<s:property value="#status.index" />" value="<s:property value="#list.detailAddr" />">
		                                        <input type="hidden" id="deliver_name_<s:property value="#status.index" />" value="<s:property value="#list.name" />">
		                                        <input type="hidden" id="deliver_tel_<s:property value="#status.index" />" value="<s:property value="#list.tel" />">
		                                        <input type="hidden" id="zip_code_<s:property value="#status.index" />" value="<s:property value="#list.zipCode" />">
		                                    </label>
		                                    <span>
		                                    	<s:property value="#list.provinceName" />&nbsp;
		                                    	<s:property value="#list.cityName" />&nbsp;
		                                    	<s:property value="#list.detailAddr" /><br />
		                                    	<s:property value="#list.name" />&nbsp;收&nbsp;
		                                    	<s:property value="#list.tel" />&nbsp;
		                                    	邮编：<s:property value="#list.zipCode" />
		                                    </span>
		                                </p>
		                                <p class="btns">
		                                    <s:if test="#request.isDefault==1">
												默认&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</s:if> 
											<s:else>
												<a class="defOn default deliverAddrDefault" href="###" id="deliverAddrDefault_<s:property value="#request.memberID" />_<s:property value="#list.id" />">设为默认</a>
											</s:else>
											<a class="require" href="###" id="deliverAddrUpdate_<s:property value="#list.id" />">修改</a> 
											<a class="delete deliverAddrDelete" href="###" id="deliverAddrDelete_<s:property value="#list.id" />">删除</a>
		                                </p>
		                            </li>
								</s:iterator>
	                        </ul>
	                        <%-- 个人/公司 普通发票 --%>
	                        <s:if test="#request.billType=='02' || #request.billType=='05' || #request.billType=='06'">
	                        <s:if test="#request.billTitleCount == 0">
	                        <b class="member_b">发票抬头：您还没有增加发票信息</b>
	                        	<p class="member_add"><span class="btn_post02" id="addBillTitle">新增发票信息</span></p>
	                        </s:if>
	                        <s:else>
							<div style="padding-left:18px;margin-bottom:12px;">
								<b>发票抬头：<span class="btn_post01" id="addBillTitle">新增发票信息</span></b>
	                        	已有<b style="color:#FC7601; padding: 0 2px;"><s:property value="#request.billTitleCount" /></b>
	                        	个发票信息，还可添加<b style="color:#FC7601; padding: 0 2px;">
	                        	<s:property value="#request.leftbillTitleCount" /></b>个
	                        </div>
	                        </s:else>
	                        <ul id="invoiceHead">
	                        	<s:iterator id="list" value="#request.billTitles" step="1" status="st">
	                            <li>
	                                <p class="cont">
	                                 	<label class="label_radio" for="radioBillTitle">
	                                 	<s:if test="#st.index == 0">
	                                        <input type="radio" name="radioBillTitle" id="radioBillTitle_<s:property value="#st.index" />" value="#list.ID" checked>
	                                    </s:if>
	                        			<s:else>
	                        				<input type="radio" name="radioBillTitle" id="radioBillTitle_<s:property value="#st.index" />" value="#list.ID">
	                        			</s:else>
	                                        <input type="hidden" id="title_name_<s:property value="#st.index" />" value="<s:property value="#list.name" />">
	                                        <input type="hidden" id="titleIndex_<s:property value="#list.id"/>" value="<s:property value="#st.index" />"/>
	                                    </label>
	                                    <span><s:property value="#list.name" /></span>
	                                </p>
	                                <p class="btns">
	                                       <a class="require requireTitleUpdate" href="###" id="billTitleUpdate_<s:property value="#list.id"/>">修改</a>
		                                    
		                                    <a class="delete requireTitleDel" href="###" id="billTitleDelete_<s:property value="#list.id"/>">删除</a>
	                                </p>
	                            </li>
								</s:iterator>
	                        </ul>
	                        </s:if>
	                        <%-- 03 : 仅限投保人 --%>
	                   		<s:elseif test="#request.billType=='03'">
	                   			<b class="member_b">发票抬头：<s:property value="#request.titleName" /></b>
	                   		</s:elseif>
	                        
	                        <%-- 04 : 单位公章 --%>
	                        <s:elseif test="#request.billType=='04'">
		                        <s:if test="#request.billTitleCount == 0">
		                        <b class="member_b">发票抬头：您还没有增加发票信息</b>
		                        	<p class="member_add"><span class="btn_post02" id="addBillTitle">新增发票信息</span></p>
		                        </s:if>
		                        <s:else>
									<div style="padding-left:18px;margin-bottom:12px;">
										<b>发票抬头：<span class="btn_post01" id="addBillTitle">新增发票信息</span></b>
			                        	已有<b style="color:#FC7601; padding: 0 2px;"><s:property value="#request.billTitleCount" /></b>
			                        	个发票信息，还可添加<b style="color:#FC7601; padding: 0 2px;">
			                        	<s:property value="#request.leftbillTitleCount" /></b>个
			                        </div>
		                        </s:else>
		                        <ul id="invoiceHead">
		                        	<s:iterator id="list" value="#request.billTitles" step="1" status="st">
		                            <li>
		                                <p class="cont">
		                                    <label class="label_radio" for="radioBillTitle">
		                                    <s:if test="#st.index == 0">
		                                    	<input type="radio" name="radioBillTitle" id="radioBillTitle_<s:property value="#st.index" />" value="#list.ID" checked>
		                                    </s:if>
	                        				<s:else>
		                                        <input type="radio" name="radioBillTitle" id="radioBillTitle_<s:property value="#st.index" />" value="#list.ID">
		                                    </s:else>  
		                                        <input type="hidden" id="title_name_<s:property value="#st.index" />" value="<s:property value="#list.name" />">
		                                        <input type="hidden" id="titleIndex" value="<s:property value="#st.index" />"/>
		                                    </label>
		                                    <span><s:property value="#list.name" /></span>
		                                </p>
		                                <p class="btns">
		                                    <a class="require requireTitleUpdate" href="###" id="billTitleUpdate_<s:property value="#list.id"/>">修改</a>
		                                    
		                                    <a class="delete requireTitleDel" href="###" id="billTitleDelete_<s:property value="#list.id"/>">删除</a>
		                                </p>
		                            </li>
									</s:iterator>
		                        </ul>
								<p class="member_upload">
									<span id="btn_upload" class="btn_upload">上传开票申请表</span><input type="file" id="fileUpload" name="fileUpload" class="fileUp" onchange="getFileName();" />
									<span id="fileName" class="fileUpNa">未选择文件</span>
								</p>
	                        </s:elseif>
	                        <a id="billReqUrlLink" href="<s:property value="#request.billReqUrl" />" class="btn" style="display:none" target="_blank">开票申请表下载</a>
	                        
	                        <p class="smtArea">
	                            <a class="sbt" href="###" onclick="return saveBillInfo();">提交</a>
	                            <a class="ret" href="bill_query!queryBill.action">取消</a>
	                        </p>
	                    </div>
	                    <!-- add by SongZairan. 2015.6.12 -->
	                </div>
	            </div>
	         </s:form>
        </div>
    </div>
    <s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
    <s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/iframeTools.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/bill_save_billing.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/VerifyInput.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/Common.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/new_member.js"></script>
    <script type="text/javascript">
    jQuery(document).ready(function(){
    	var fullDegree = jQuery("#fullDegree").val();
    	jQuery("#MemberCenter").html(fullDegree);
		if(jQuery("#billType").val() == "04" && jQuery("#billRequireFlag").val() == ""){
			jQuery("#billRequireFlag").val("1");
			art.dialog({
				title: "提示",
				content: "<div id='artPopup03'><p>您需要先下载开票申请表，盖章后再进行申请并上传。</p></div>",
				button: [
					{
						name: "下载",
						focus: true,
						callback: function () {
				        	jQuery("#billReqUrlLink")[0].click();
				        }
					}
				]
			});
		}
		
	});
	function saveBillInfo(){
		var radioDeliverAddrSelected = $('input[name="radioDeliverAddr"]').filter(':checked');
		if(radioDeliverAddrSelected == null || radioDeliverAddrSelected.length == 0){
			alert("请选择邮寄地址！");
			return false;
		}
		if($("#billType").val() == "04" || $("#billType").val() == "02" 
				|| $("#billType").val() == "05" || $("#billType").val() == "06"){
			var radioBillTitleSelected = $('input[name="radioBillTitle"]').filter(':checked');
			if(radioBillTitleSelected == null || radioBillTitleSelected.length == 0){
				alert("请选择发票抬头！");
				return false;
			}	
		}
		if ($("#billType").val() == "04" && ($('input[name="fileUpload"]').val() == null || $('input[name="fileUpload"]').val() == '')) {
			art.dialog({
				title: "提示",
				content: "<div id='artPopup01'><img src='../../style/images/art_smile.png' width='80' height='87' /><span>请上传开票申请表！</span></div>",
				ok: function () {
				}
			});
			return false;
		}
 		
		var deliverAddrIndex = radioDeliverAddrSelected.attr("id").split("_")[1];
		$("#deliverProvinceName").val($("#province_name_"+ deliverAddrIndex).val());
		$("#deliverCityName").val($("#city_name_"+ deliverAddrIndex).val());
		$("#deliverDetailAddr").val($("#detail_addr_"+ deliverAddrIndex).val());
		$("#deliverName").val($("#deliver_name_"+ deliverAddrIndex).val());
		$("#deliverTel").val($("#deliver_tel_"+ deliverAddrIndex).val());
		$("#deliverZipCode").val($("#zip_code_"+ deliverAddrIndex).val());
		
		if($("#billType").val() == "04" || $("#billType").val() == "02" 
				|| $("#billType").val() == "05" || $("#billType").val() == "06"){
			var billTitleIndex = radioBillTitleSelected.attr("id").split("_")[1];
			$("#titleName").val($("#title_name_"+ billTitleIndex).val());	
		}
		var moreThan200Flag = $("#moreThan200Flag").val();
		if(moreThan200Flag == "false"){
			art.dialog({
				title: "提示",
				content: "<div id='artPopup06'>申请发票邮寄，200元以下运费由用户自行承担（到付）。</div>",
				button: [
					{
						name: '确认',
						callback: function () {
							var options = {
									url : "bill_save!saveBillInfo.action",
									async : false,
									type : "POST",
									data : jQuery('#billInfoSave_form').serialize(),
									dataType : "json",
									resetForm : false,
									success : function(data) {
										var tFlag = data.tFlag;
										if (tFlag == "Err") {
											art.dialog.alert(data.Msg);
										} else {
											art.dialog.alert(data.Msg,function(){
												window.location.href = sinosoft.base + "/shop/bill_query!queryBillHist.action";
											});
										}
									}
								};
								jQuery('#billInfoSave_form').ajaxSubmit(options);
						},
						focus: true
					},
					
					{
						name: '取消'
					
					}
				]
			});
		}else{
			var options = {
					url : "bill_save!saveBillInfo.action",
					async : false,
					type : "POST",
					data : jQuery('#billInfoSave_form').serialize(),
					dataType : "json",
					resetForm : false,
					success : function(data) {
						var tFlag = data.tFlag;
						if (tFlag == "Err") {
							art.dialog.alert(data.Msg);
						} else {
							art.dialog.alert(data.Msg,function(){
								window.location.href = sinosoft.base + "/shop/bill_query!queryBillHist.action";
							});
						}
					}
				};
				jQuery('#billInfoSave_form').ajaxSubmit(options);
		}
		
	}
	
	function getFileName() {
		document.getElementById("fileName").innerHTML= document.getElementById("fileUpload").value;

		
	}
    </script>
</body>
</html>
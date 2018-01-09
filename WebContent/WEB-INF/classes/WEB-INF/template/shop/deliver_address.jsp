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
<title>会员中心-个人资料</title>
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<!--default.css artdialog样式-->
<link href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getServerContext()%>/wwwroot/kxb/style/new_shops.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getServerContext()%>/wwwroot/kxb/style/new_member.css" />
<style type="text/css">
.doubleselect br{
display: none;
} 
</style>



 <script type="text/javascript">

</script>

</head>

<body>
<div id="popupEmail">
	<s:form id="deliverAddr_form" name="deliverAddr_form" action="deliver_address_maintain!saveDeliverAddrInfo.action" class="validate"  method="post" >
		<input type="hidden" name="Id" id="Id" value="<s:property value='mSDDeliverAddress.Id'/>"/>
		<table class="emailTab" width="100%" border="0">
			<tr>
				<th><b>*</b>姓名：</th>
				<td><input type="text" name="mSDDeliverAddress.name" style="color:<s:property value='#request.addressColor'/>;" maxlength="50" onblur="verifyElement('姓名|NOTNULL&UFO&LEN>2',this.value,this.id);if (this.value == '') {this.value = '中英文姓名';jQuery(this).css('color', '#ccc');};" verify="姓名|notnull&UFO&LEN>2" id="deliverAddrName" class="manage_sele" onfocus="if (this.value == '中英文姓名') {this.value = '';};jQuery(this).css('color', '#3b3b3b');" value="<s:property value='mSDDeliverAddress.name'/>"><label class="requireField"></label></td>
			</tr>
			<tr>
				<th><b>*</b>电话：</th>
				<td><input type="text" maxlength="11" verify="手机号码|PHONE1" style="color:<s:property value='#request.addressColor'/>;" onblur="verifyElement('手机号码|PHONE1',this.value,this.id);if (this.value == '') {this.value = '手机或固话';jQuery(this).css('color', '#ccc');};" name="mSDDeliverAddress.tel" class="manage_sele" id="deliverAddrTel" onfocus="if (this.value == '手机或固话') {this.value = '';};jQuery(this).css('color', '#3b3b3b');" value="<s:property value='mSDDeliverAddress.tel'/>"> <label class="requireField"></label></td>
			</tr>
			<tr>
				<th><b>*</b>地址：</th>
				<td>		
					<s:if test="#request.areaList!=null && #request.areaList.size()>0">
						<div id="doubleselect" class="doubleselect" >
							<s:doubleselect  onblur="verifyElement('地址省份|NOTNULL',this.value,this.id)"  id="area" doubleId="city" doubleCssClass="member_paperselect" cssClass="member_paperselect" 
	                    	 	   		list="#request.areaList" listValue="areaName"  listKey="areaId" doubleName="mSDDeliverAddress.provinceName" value="areaId" 
	                    	 	   		doubleList="#request.cityMap[top.areaId]"  doubleListKey="cityId" doubleListValue="cityName" />
	                    	 <label class="requireField"></label>	   		
						</div>
					</s:if>
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td> 
					 <div id="addr">
						 <input id="address" type="text" class="member_nameinput member_defaut" style="color:<s:property value='#request.addressColor'/>;" onblur="verifyElement('地址|NOTNULL&LEN>4',this.value,this.id);if (this.value == '') {this.value = '请准确填写，以免无法邮寄给您';jQuery(this).css('color', '#ccc');};" verify="地址|notnull&LEN>4" onfocus="if (this.value == '请准确填写，以免无法邮寄给您') {this.value = '';};jQuery(this).css('color', '#3b3b3b');"  name="mSDDeliverAddress.detailAddr"  value='<s:property value="#request.mSDDeliverAddress.detailAddr"/>' />
						 <label class="requireField"></label>
					 </div>
				</td>
			</tr>
			<tr>
				<th>邮编：</th>
	            <td><input id="zipCode" type="text" style="color:<s:property value='#request.addressColor'/>;" onblur="verifyElement('邮编|ZIPCODE',this.value,this.id)" onfocus="jQuery(this).css('color', '#3b3b3b');" verify="邮编|ZIPCODE" maxlength="6" name="mSDDeliverAddress.zipCode" value="<s:property value='mSDDeliverAddress.zipCode'/>" /><label class="requireField"></label></td>
			 </tr>
		</table>
	</s:form>
</div>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/jquery.form.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/common/js/jquery.jqDnR.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/common/js/jquery.jqModal.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/IDvalidate.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/member.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/artDialog.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/Common.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/VerifyInput.js"></script>
</body>
</html>

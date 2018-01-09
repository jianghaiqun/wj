<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript">

/**
 * 获得温馨提示
 */
function getProductDesc(){
	var productDesc = UE.getEditor('productDesc').getContent();
	if (productDesc == '' || productDesc == null) {
		return '';
	}
	return productDesc;
}

</script>
</head>
<body class="dialogBody">
	
	<z:init method="com.sinosoft.cms.travel.ProductManage.initDialog">
	
		<form id="form2">
		<input type="hidden" id="operation" value="${operation}">
			<input type="hidden"  id="id" name="id" value="${id}">
			<input type="hidden" id="UploadFilePath" name="UploadFilePath" value="${UploadFilePath}" />
			<table width="920" height="580" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td valign="top">
			    			<fieldset >
								 <table>
								        <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">&nbsp;&nbsp;&nbsp;产品名：</td>
								          <td ><input value="${productName}" type="text" id="productName" name="productName" ztype="String"  class="inputText" size="40" verify="产品名|NotNull" ></td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">&nbsp;&nbsp;产品简称：</td>
								          <td ><input value="${shortName}" type="text" id="shortName" name="shortName" ztype="String"  class="inputText" size="20" verify="产品简称|NotNull" ></td>
								          <td>&nbsp;&nbsp;&nbsp;出发地：</td>
										  <td><input value="${birthland}" type="text" id="birthland" name="birthland" ztype="String"  class="inputText" size="20" ></td>
										  <td>&nbsp;&nbsp;目的地：</td>
										  <td><input value="${destination}" type="text" id="destination" name="destination" ztype="String"  class="inputText" size="20" ></td>
								        </tr>
							     </table>
							     <table>
								        <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">产品类型：</td>
								          <td><z:select id="productType" style="width:117px" value="${productType}">${proType}</z:select></td>
								          <td id="points_td">&nbsp;&nbsp;产品分类：</td>
									    <td><z:select id="productClassify" style="width:117px" value="${productClassify}">${Classify}</z:select></td>
									    <td>提前预定天数：</td>
										<td><input value="${reserveDay}" type="text" id="reserveDay" name="reserveDay" ztype="String" verify="提前预定天数|NotNull&&Int"  class="inputText" size="20" ></td>
										<td>&nbsp;&nbsp;是否上架：</td>
										<td><z:select id="state" style="width:117px" value="${state}">${YesOrNo}</z:select></td>
										
								      </tr>
							     </table>
							     <table>
								          <tr>
										        <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">开始时间：</td>
												<td><input name="createStartDate" id="createStartDate" value="${StartDate}" type="text" size="14" ztype="Date"  verify="开始日期|NotNull" /></td>
												<td><input name="createStartTime" id="createStartTime" value="${StartTime}" type="text" size="14" ztype="Time" verify="开始时间|NotNull"/></td>
												<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
												<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">结束时间：</td>
												<td><input name="createEndDate" id="createEndDate" value="${EndDate}" type="text" size="14" ztype="Date" verify="结束日期|NotNull"/></td>
												<td><input name="createEndTime" id="createEndTime" value="${EndTime}" type="text" size="14" ztype="Time" verify="结束时间|NotNull"/></td>
								          </tr>
							     </table>
							     <table>
							     	<tr >
								     	</tr>
							     </table>
							     <table>
							     		<tr>
								          <td height="35" align="right" class="tdgrey1" bordercolor="#eeeeee">归属模块：</td>
								          <td>
								          	${ModelType}
								          </td>
								        </tr>
							     </table>
							     
							     <table>
								          <tr>
										        <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">温馨提示：</td>
									    		<td><input value="${prompt}" type="text" id="prompt" name="prompt" ztype="String"  class="inputText"  size="120"></td>
								          </tr>
							     </table>
							     <table>
							      	<tr id="MetaDescriptionTR">
							      	<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">产品描述：</td>
							      	<td>
							      		<textarea id="productDesc" name="productDesc" style="height:300px;width:750px;">${productDesc}</textarea>
										<script type="text/javascript">var ue = UE.getEditor("productDesc");</script>
							      	</td>
									</tr>
							     </table>
			               </fieldset>
					</td>
			    </tr>
			  </table>
		</form>
	</z:init>
</body>
</html>
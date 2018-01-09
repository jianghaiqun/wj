<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
/**
* 所在地区省改变重新加载市
*/
function changeProv(){
	jQuery("#branchInnercode").remove();
	jQuery("#provs").after("<select id=\"branchInnercode\" name=\"branchInnercode\"  onChange=\"changeCity()\" style=\"height:21px;width:116px;margin-left:4px\"></select>");
	var prov=jQuery("#provs").val();
	var dc = new DataCollection();
	dc.add("prov", prov);
	Server.sendRequest("com.wangjin.lineteam.LineTeamPolicyInfo.loadCitys", dc,
		function(response) {
			if (response.Status == 1) {
				if(response.Message != null){
					jQuery("#branchInnercode").append(response.Message);
				}
			}
		});
}
</script>
</head>
<body class="dialogBody">
	<z:init method="com.wangjin.lineteam.RateManage.initDialog">
		<form id="form2">
			<input type="hidden" id="operFlag" value="${operFlag}">
			<input type="hidden"  id="ID" name="ID" value="${ID}">
			<table width="400" height="200" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td valign="top">
			    			<fieldset >
								 <table>
								         <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">所属机构：</td>
								          <td >
								          <select id="provs" onChange="changeProv()" style="height:21px;width:120px">
												${provs} 
											</select>
											<select id="branchInnercode" name="branchInnercode" style="height:21px;width:116px;">
												${citys} 
											</select>
											<span style="color:red;padding-left:2px;padding-top:13px;" ztype="Verify">*</span>
								          </td>
								        </tr>
								        <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">险种：</td>
								          <td ><z:select style="width:120px;" id="riskType"  verify="险种|NotNull" value="${riskType}">${SubRiskType}</z:select>
								          </td>
										</tr>
								        <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">保险公司：</td>
								          <td ><z:select style="width:120px;" id="companyCode"  verify="保险公司|NotNull" value="${companyCode}">${Supplier}</z:select></td>
										</tr>
								        <tr>
										  <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">结算费率：</td>
								          <td ><input value="${rate}" type="text" id="rate" name="rate" ztype="String" verify="结算费率|NotNull" class="inputText" size="10"  maxlength=10></td>
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

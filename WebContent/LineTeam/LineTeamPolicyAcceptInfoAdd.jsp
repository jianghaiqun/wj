<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">
jQuery(function(){
	changeProv();
});
/**
* 所在地区省改变重新加载市
*/
function changeProv(){
	jQuery("#citys").remove();
	jQuery("#provs").after("<select id=\"citys\" style=\"height:21px;width:116px;margin-left:4px\"></select>");
	var prov=jQuery("#provs").val();
	var dc = new DataCollection();
	dc.add("prov", prov);
	Server.sendRequest("com.wangjin.lineteam.LineTeamPolicyInfo.loadCitys", dc,
		function(response) {
			if (response.Status == 1) {
				if(response.Message != null){
					jQuery("#citys").append(response.Message);
				}
				
			}
		});
}


</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.wangjin.lineteam.LineTeamPolicyInfo.initDialog">
<form id="form1">
		<table width="750" height="200" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td valign="top">
						 <table>
						 	 <tr>
						 	 	<td align="right" height="35">所在地区：</td>
								<td>
									<z:select id="provs" onChange="changeProv()" style="height:21px;width:120px">
										${provs} 
									</z:select>
									<z:select id="citys" name="branchInnercode" style="height:21px;width:116px;">
										${citys} 
									</z:select>
									<span style="color:red;padding-left:2px;padding-top:13px;" ztype="Verify">*</span>
								</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right" height="35">姓名：</td>
						 	 	<td><input id="customName" name="customName" type="text" class="input1" size=24 verify="姓名|NotNull"/></td>
						 	 </tr>
						 	 <tr>
						 	 	<td align="right" height="35">保险公司：</td>
								<td>
									<z:select id="companyCode" name="companyCode" style="height:21px;width:169px;"> ${companys} </z:select>
									<span id="companyCodeWarn" style="color:red;padding-top:13px;" ztype="Verify">*</span>
								</td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right" height="35">险种：</td>
								<td>
									<z:select id="riskType" name="riskType" style="height:21px;width:169px;"> ${riskTypes} </z:select>
									<span id="riskTypeWarn" style="color:red;padding-top:13px;" ztype="Verify">*</span>
								</td>
						 	 </tr>
						 	 <tr>
						 	 	<td align="right" height="35">总保费：</td>
						 	 	<td><input id="prem" name="prem" value="" type="text" size="24" verify="总保费|NotNull&&Number"/></td>
						 	 	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						 	 	<td align="right" height="35">保单号：</td>
						 	 	<td><input id="policyNo" name="policyNo" type="text"
									value="${policyNo}" class="input1" size=24  verify="保单号|NotNull"/></td>
						 	 </tr>
						 	 <tr>
								<td align="right" height="35">工作室：</td>
						 	 	<td><input id="agentName" name="agentName"  value="" type="text" size="24" verify="工作室|NotNull"/></td>
						 	 	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						 	 	<td align="right" height="35">车牌：</td>
						 	 	<td><input id="plateNumber" name="plateNumber"  value="" type="text" size="24"/></td>
						 	 </tr>
						 	 <tr>
						 	 	<td align="right" height="35">出单日期：</td>
						 	 	<td><input name="insureDate" id="insureDate" value="" type="text" size="24" ztype="Date" verify="出单日期|NotNull" readonly="readonly"/></td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right" height="35">保险起期：</td>
						 	 	<td><input name="startDate" id="startDate" value="" type="text" size="24" ztype="Date" verify="保险起期|NotNull" readonly="readonly"/></td>
						 	 </tr>
						 	 <tr>
								<td align="right" height="35">结算费率：</td>
						 	 	<td><input id="agentRate" name="agentRate"  value="" type="text" size="24" verify="结算费率|NotNull&&Number"/></td>
						 	 	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td align="right" height="35">手续费：</td>
						 	 	<td><input id="poundage" name="poundage"  value="" type="text" size="24" verify="手续费|NotNull&&Number"/></td>
						 	 </tr>
						 	  <td><input type='hidden' id='style' value='1'></td>
					     </table>	     
			</td>
	    </tr>
	    </table>
	</form>
</z:init>
</body>
</html>

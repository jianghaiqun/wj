<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>车辆信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/calendar.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.js"></script>
<script language="JavaScript" src="${shopStaticPath}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function newCarCheck(){
	if(document.getElementById("newCar").checked==true){
		document.getElementById("licenseNo").value='';
		document.getElementById("licenseNo").disabled='disabled';
		document.getElementById("lastInsureCom").disabled='disabled';
	}else{
		document.getElementById("licenseNo").disabled='';
		document.getElementById("lastInsureCom").disabled='';
	}
}
function sure(){
    var licenseNo = document.getElementById("licenseNo").value;
	if((licenseNo==null||licenseNo=="")&&(document.getElementById("newCar").checked==false)){
		alert("请录入车牌号码或者勾选新车未上牌 !");
		document.getElementById("licenseNo").focus();
		return false;
	}
	if(document.getElementById("regionCode").value==null||document.getElementById("regionCode").value=="-1"){
		alert("请选择车辆行驶区域");
		document.getElementById("regionCode").focus();
		return false;
	}
	if(document.getElementById("brand").value==null||document.getElementById("brand").value==""){
		alert("请选择车辆品牌型号");
		document.getElementById("brand").focus();
		return false;
	}
	if(document.getElementById("carBuyTime").value==null||document.getElementById("carBuyTime").value==""){
		alert("请填写购车时间");
		document.getElementById("carBuyTime").focus();
		return false;
	}
	if(document.getElementById("purchasePrice").value==null||document.getElementById("purchasePrice").value==""){
		alert("请填写新车购置价");
		document.getElementById("purchasePrice").focus();
		return false;
	}
	if(document.getElementById("insureder").value==null||document.getElementById("insureder").value==""){
		alert("请填写被保人姓名");
		document.getElementById("insureder").focus();
		return false;
	}
	if(document.getElementById("birthday").value==null||document.getElementById("birthday").value==""){
		alert("请填写被保人生日");
		document.getElementById("birthday").focus();
		return false;
	}
	if(document.getElementById("telNo").value==null||document.getElementById("telNo").value==""){
		alert("请填写被保人手机号码");
		document.getElementById("telNo").focus();
		return false;
	}
	var telNo = document.getElementById("telNo").value;
	if(telNo!=null&&telNo!="") {
	    var patrn = /(^0{0,1}1[3|4|5|6|7|8|9][0-9]{9}$)/;
	    if (!patrn.exec(telNo)) {
	    	alert("请输入正确的手机号码！");
	    	document.getElementById("telNo").focus();
	        return false;
	    }
	} 
	if(document.getElementById("email").value==null||document.getElementById("email").value==""){
	    alert("请填写邮箱");
	    document.getElementById("email").focus();
	    return false;
	}
	if(document.getElementById("email").value!=""&&!isEmail(document.getElementById("email").value)){
	    alert("邮箱格式不正确");
	    document.getElementById("email").focus();
	    return false;
	}
	document.getElementById("insuredForm").submit();
	return true;
}
function isEmail(strEmail) { 
    var str=/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
    return str.test(strEmail);
} 
</script>
</head>
<body>
    <form id="insuredForm" action="${base}/shop/premium_trial!selectInsured.action"  method="post">
			    <input type="hidden" name="sdTagetInformation.load" id="load"  onkeyup="if(isNaN(value))execCommand('undo')"/>
				<input type="hidden" name="sdTagetInformation.passenger" id="passenger" maxlength=3 onkeyup="this.value=this.value.replace(/\D/g,'')"/>
      <table width="100%" align="left">
         <tr>
            <td>
            <font color="red">&nbsp;&nbsp;&nbsp;&nbsp;标的信息</font>
		<hr/>
		<table width="100%" align="left">
            <tbody>
               <tr>
               <th width="16%" align="right"><font color="red">*</font>车辆行驶地区：</th>
                <td width="34%" align="left">
					<select id="regionCode" name="sdTagetInformation.regionCode" >
					   <option value="-1">---请选择---</option>
					   <#if (citys != null && citys?size > 0)>
							<#list citys as list>
					            <option value="${list.placeCode}">${list.placeName}</option>
					        </#list>
					   </#if>
					</select>
                </td>
               <th width="16%" align="right"><font color="red">*</font>车牌号码：</th>
               <td width="34%" align="left">
                  <input type="text" class="input01" id="licenseNo" name="sdTagetInformation.licenseNo" style="width: 82px;" />&nbsp;&nbsp;
				  <input type="checkbox" name="newCar" id="newCar" onchange="newCarCheck()" /> 新车未上牌
               </td>
             </tr>
             <tr>
               <th width="16%" align="right"><font color="red">*</font>车辆品牌型号：</th>
               <td width="34%" align="left">
                  <input  name="sdTagetInformation.carBrand" id="brand" value="" onkeyup="selectCar();"/>
				  <div id="showCar">
				      <div id="show" style="position:absolute;background:white;overflow:auto;height=0;width=0;">
				          <ul id="selectCar"></ul>
			          </div>
				  </div>
               </td>
				<th width="16%" align="right"><font color="red">*</font>购车时间：</th>
				<td width="34%" align="left">
					<input id="carBuyTime" name="sdTagetInformation.carBuyTime" type="text" onclick="WdatePicker()" value=""/>
				</td>
			</tr>
			<tr>
				<th width="16%" align="right"><font color="red">*</font>新车购置价：</th>
				<td width="34%" align="left">
					<input name="sdTagetInformation.carPrice" id="purchasePrice" onkeyup="if(isNaN(value))execCommand('undo')" />元
				</td>
				<th width="16%" align="right">上一年度投保公司：</th>
                <td width="34%" align="left">
                    <select style="width: 155px" name="sdTagetInformation.lastInsureCom" id="lastInsureCom">
                        <option value="-1">---请选择---</option>
                        <#if (coms != null && coms?size > 0)>
							<#list coms as list>
					            <option value="${list.supplierCode}">${list.shortName}</option>
					        </#list>
					    </#if>
                    </select>
                 </td>
			</tr>
		</tbody>
	</table>
            </td>
         </tr>
          <tr>
            <td>
            <font color="red">&nbsp;&nbsp;&nbsp;&nbsp;被保人基本信息</font>
				<hr/>
		   <table width="100%" align="left">
		      <tbody>
			    <tr>
				  <th width="16%" align="right"><font color="red">*</font>车主姓名（投保人）：</th>
				  <td width="34%" align="left"><input name="sdTagetInformation.insureder" id="insureder"/></td>
					<th width="16%" align="right"><font color="red">*</font>被保人性别：</th>
					<td width="34%" align="left">
					    <select style="width: 155px" name="sdTagetInformation.sex">
						    <option value="0">男 </option>
							<option value="1">女</option>
					    </select>
					</td>
			   </tr>
				<tr>
					<th width="16%" align="right"><font color="red">*</font>被保人生日：</th>
					<td width="34%" align="left">
					   <input id="birthday" name="sdTagetInformation.birthday" type="text" onclick="WdatePicker()" value="${today}"/>
					</td>
					<th width="16%" align="right"><font color="red">*</font>手机：</th>
					<td width="34%" align="left"><input name="sdTagetInformation.telNo" id="telNo" onkeyup="this.value=this.value.replace(/\D/g,'')" />
					</td>
				</tr>
				<tr>
					<th width="16%" align="right"><font color="red">*</font>E-Mail：</th>
					<td width="34%" align="left"><input name="sdTagetInformation.email" id="email" /></td>
				</tr>
				</tbody>
		    </table>
            </td>
         </tr>
          <tr>
            <td>
            <table width="100%" align="left">
			<tbody>
			    <tr>
                 <th align="center"><table width="139" border="0" align="center" cellspacing="0" >
			     <tbody>
			      <tr>
                    <td>
                    <a href="#" onclick="sure();" ><b>提交</b></a></td>
                  </tr>
                  </tbody>
                  </table>
                  </th>
                  </tr>
			</tbody>				
        </table>
            </td>
         </tr>
      </table>
      </form>
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>车险套餐修改</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function getCarMenu(code){
	jQuery.ajax({
       type:'get',
       url:' ${base}/admin/car_menu!selectCarMenu.action?code='+code,
       dataType:'json',
       success:function(de){
    	   fillCarMenu(de);
       }
  });
}
function fillCarMenu(obj){
    var carmenu = null;
    for(var i=0;i<obj.length;i++){
        carmenu = obj[0];
    }
    if(obj.length>0){
       document.getElementById("carMenuId").value=carmenu.id;
       document.getElementById("carMenuName").value=carmenu.name;
       document.getElementById("insrueType1").value=carmenu.r001;
       document.getElementById("R1_amount").value=carmenu.r001_premium;
       document.getElementById("insrueType2").value=carmenu.r002;
       if(carmenu.r002_premium>0){
           document.getElementById("R2_amount").value=carmenu.r002_premium;
       }
       document.getElementById("insrueType3").value=carmenu.r0030;
       document.getElementById("R3_amount").value=carmenu.r0030_premium;
       document.getElementById("insrueType4").value=carmenu.r0031;
       document.getElementById("R4_amount").value=carmenu.r0031_premium;
       document.getElementById("insrueType5").value=carmenu.r004;
       document.getElementById("R5_amount").value=carmenu.r004_premium;
       document.getElementById("insrueType6").value=carmenu.r006;
       document.getElementById("R6_type").value=carmenu.r006_type;
       document.getElementById("insrueType7").value=carmenu.r008;
       document.getElementById("R7_amount").value=carmenu.r008_premium;
       document.getElementById("insrueType9").value=carmenu.sclar;
       document.getElementById("SCLAR_amount").value=carmenu.sclar_premium;
       document.getElementById("nonDeductible1").value=carmenu.NR001;
       document.getElementById("nonDeductible2").value=carmenu.NR002;
       document.getElementById("nonDeductible3").value=carmenu.NR0030;
       document.getElementById("nonDeductible4").value=carmenu.NR0031;
       document.getElementById("nonDeductible5").value=carmenu.NR004;
       document.getElementById("nonDeductible6").value=carmenu.NR008;
    }
}
function fsure(){
   if(document.getElementById("code").value=="-1"){
      alert("请选择套餐名称");
      return false;
   }
   if(document.getElementById("insrueType5").value=="Y"&&document.getElementById("R5_amount").value==""){
      alert("请输入盗抢险保额");
      return false;
   }
   if(document.getElementById("insrueType9").value=="Y"&&document.getElementById("SCLAR_amount").value==""){
      alert("请输入自然损失险保额");
      return false;
   }
   if(document.getElementById("insrueType1").value=="N"&&document.getElementById("nonDeductible1").value=="Y"){
      alert("第三责任险未投保，不能投保第三责任不计免赔险");
      return false;
   }
   if(document.getElementById("insrueType2").value=="N"&&document.getElementById("nonDeductible2").value=="Y"){
      alert("机动车损失险未投保，不能投保机动车损失不计免赔险");
      return false;
   }
   if(document.getElementById("insrueType3").value=="N"&&document.getElementById("nonDeductible3").value=="Y"){
      alert("司机责任险未投保，不能投保司机责任不计免赔险");
      return false;
   }
   if(document.getElementById("insrueType4").value=="N"&&document.getElementById("nonDeductible4").value=="Y"){
      alert("乘客责任险未投保，不能投保乘客责任不计免赔险");
      return false;
   }
   if(document.getElementById("insrueType5").value=="N"&&document.getElementById("nonDeductible5").value=="Y"){
      alert("盗抢险未投保，不能投保盗抢不计免赔险");
      return false;
   }
   if(document.getElementById("insrueType7").value=="N"&&document.getElementById("nonDeductible6").value=="Y"){
      alert("车身划痕损失险未投保，不能投保车身划痕损失不计免赔险");
      return false;
   }
   document.getElementById("inputForm").submit();
    return true;
}
</script>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>车险套餐修改</h1>
		</div>
		<form id="inputForm" class="validate" action="car_menu!update.action" method="post" enctype="multipart/form-data">
			<input type="hidden" id="carMenuId" name="carMenu.id" value=""/>
			<input type="hidden" id="carMenuName" name="carMenu.name" value=""/>
			<table class="inputTable">
				<tr>
					<th>
						套餐名称:
					</th>
					<td>
					   <select id="code" name="carMenu.code" onchange="getCarMenu(this.value);">
						  <option value="-1">----请选择----</option>
						  <option value="CM001">标准型</option>
						  <option value="CM002">经济型</option>
						  <option value="CM003">豪华型</option>
					   </select>
					</td>
				</tr>
				<tr>
					<th>
						套餐险种:
					</th>
					<td>
					   <table>
				         <tr>
					       <th align="left">商业险<em class="redd">基本</em>险种</th>
                           <th align="left">是否投保/保额</th>
				        </tr>
					    <tr>
						<td align="left" class="subth">
							<span>第三者责任险</span>
						</td>
						<td align="left">
							<select id="insrueType1" name="carMenu.R001">
								<option value="N">不投保</option>
								<option value="Y">投保</option>
							</select>
							<em class="blue">
								<select name="carMenu.R001_premium" id="R1_amount" style="width:100px;" >
							        <option value="5">5万元</option>
							        <option value="10">10万元</option>
							        <option value="15">15万元</option>
							        <option value="20">20万元</option>
							        <option value="30">30万元</option>
							        <option value="50">50万元</option>
							        <option value="100">100万元</option>
					             </select>
					          </em>
						</td>
					</tr>
					<tr>
						<td align="left">
							<span>机动车损失险</span>
						</td>
						<td align="left">
							<em class="blue">
								<select id="insrueType2" name="carMenu.R002">
								     <option value="N">不投保</option>
									 <option value="Y">投保</option>
								</select>
							</em>
							<em class="blue">
								<input type="text" name="carMenu.R002_premium" id="R2_amount"
						                      style="width: 90px" onkeyup="if(isNaN(value))execCommand('undo')"/>元
						     </em>
						</td>
					</tr>
				    <tr>
								<td align="left" class="subth">
								   <span>司机座位责任险</span>
								</td>
								<td align="left">
								     <select name="carMenu.R0030" id="insrueType3">
								        <option value="N">不投保</option>
									    <option value="Y">投保</option>
								     </select>
								    <em class="blue">
								       <select name="carMenu.R0030_premium" id="R3_amount">
							                <option value="10000">10000元</option>
							                <option value="20000">20000元</option>
							                <option value="30000">30000元</option>
							                <option value="40000">40000元</option>
							                <option value="50000">50000元</option>
							                <option value="60000">60000元</option>
							                <option value="70000">70000元</option>
							                <option value="80000">80000元</option>
							                <option value="90000">90000元</option>
							                <option value="100000">100000元</option>
					                   </select>
								    </em>
								</td>
							</tr>
							<tr>
								<td align="left">
								    <span>乘客座位责任险</span>
								</td>
								<td align="left">
								    <select name="carMenu.R0031" id="insrueType4">
								        <option value="N">不投保</option>
									    <option value="Y">投保</option>
								    </select>
								    <em class="blue">
								         <select name="carMenu.R0031_premium" id="R4_amount" style="width:100px;">
							                <option value="10000">10000元</option>
							                <option value="20000">20000元</option>
							                <option value="30000">30000元</option>
							                <option value="40000">40000元</option>
							                <option value="50000">50000元</option>
							                <option value="60000">60000元</option>
							                <option value="70000">70000元</option>
							                <option value="80000">80000元</option>
							                <option value="90000">90000元</option>
							                <option value="100000">100000元</option>
					                    </select> 
								    </em>
								</td>
							</tr>
							<tr>
								<td align="left">
								    <span>盗抢险</span>
								</td>
								<td align="left">
								   <select name="carMenu.R004" id="insrueType5">
								        <option value="N">不投保</option>
									    <option value="Y">投保</option>
								   </select>
								    <em class="blue">
								      <input name="carMenu.R004_premium" id="R5_amount" style="width: 90px" onkeyup="if(isNaN(value))execCommand('undo')"/>元
								    </em>
								</td>
							</tr>
							<tr>
								<td align="left">
								    <span>车身划痕险</span>
								</td>
								<td align="left">
								    <select name="carMenu.R008" id="insrueType7">
								        <option value="N">不投保</option>
									    <option value="Y">投保</option>
								    </select>
								    <em class="blue">
								         <select name="carMenu.R008_premium" id="R7_amount" style="width: 100px">
							                <option value="2000">2000元</option>
							                <option value="5000">5000元</option>
							                <option value="10000">10000元</option>
							                <option value="20000">20000元</option>
					                      </select>
								    </em>
								</td>
							</tr>
							<tr>
								<td align="left" class="subth">
								    <span>玻璃单独破碎险</span>
								</td>
								<td align="left">
								   <select name="carMenu.R006" id="insrueType6">
								        <option value="N">不投保</option>
									    <option value="Y">投保</option>
								   </select>
								    <em class="blue">
								      <select name="carMenu.R006_type" id="R6_type" style="width: 100px">
							              <option value="0">国产</option>
							              <option value="1">进口</option>
					                  </select>
								    </em>
								</td>
							</tr>
							
							<tr>
								<td align="left">
								    <span>自然损失险</span>
								</td>
								<td align="left">
								   <select name="carMenu.sclar" id="insrueType9">
								        <option value="N">不投保</option>
									    <option value="Y">投保</option>
								   </select>
								    <em class="blue">
								      <input type="text" name="carMenu.sclar_premium" id="SCLAR_amount"
						                     style="width: 90px" onkeyup="if(isNaN(value))execCommand('undo')"/>元
								    </em>
								</td>
							</tr>
                            <tr>
								<th align="left">商业险<em class="redd">不计免赔</em>险种</th>
                                <th align="left">是否投保</th>
							</tr>
                            <tr>
								<td align="left">
								    <span>第三责任险</span>
								</td>
								<td align="left">
								   <select name="carMenu.NR001" id="nonDeductible1">
								        <option value="N">不投保</option>
									    <option value="Y">投保</option>
								   </select>
								</td>
							</tr>
							<tr>
								<td align="left">
								    <span>机动车损失险</span>
								</td>
								<td align="left">
								   <select name="carMenu.NR002" id="nonDeductible2">
								        <option value="N">不投保</option>
									    <option value="Y">投保</option>
								   </select>
								</td>
							</tr>
							<tr>
								<td align="left" class="subth">
								    <span>司机座位责任险</span>
								</td>
								<td align="left">
								   <select name="carMenu.NR0030" id="nonDeductible3">
								        <option value="N">不投保</option>
									    <option value="Y">投保</option>
								   </select>
								</td>
							</tr>
							<tr>
								<td align="left">
								    <span>乘客座位责任险</span>
								</td>
								<td align="left">
								   <select name="carMenu.NR0031" id="nonDeductible4">
								        <option value="N">不投保</option>
									    <option value="Y">投保</option>
								   </select>
								</td>
							</tr>
							<tr>
								<td align="left">
								    <span>盗抢险</span>
								</td>
								<td align="left">
								   <select name="carMenu.NR004" id="nonDeductible5">
								        <option value="N">不投保</option>
									    <option value="Y">投保</option>
								   </select>
								</td>
							</tr>
							<tr>
								<td align="left">
								    <span>车身划痕损失险</span>
								</td>
								<td align="left">
								   <select name="carMenu.NR008" id="nonDeductible6">
								        <option value="N">不投保</option>
									    <option value="Y">投保</option>
								   </select>
								</td>
							</tr>
					   </table>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="button" id="sure" class="formButton" onclick="fsure();" value="保  存" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
			</div>
		</form>
	</div>
</body>
</html>
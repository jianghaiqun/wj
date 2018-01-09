<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>车辆信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<style>
.white_content {
 display: none;
 position: absolute;
 top: 20px;
 left: 10px;
 width: 750px;
 border: 12px solid #FFD39B;
 z-index:1002;
}
.black_overlay {
 display: none;
 position: absolute;
 top: 0%;
 left: 0%;
 width: 100%;
 height: 100%;
 background-color:#FFFFFF;
 z-index:1001;
 -moz-opacity: 0.8;
 opacity:.80;
 filter: alpha(opacity=80);
}
.close {
 float:right;
 clear:both;
 width:100%;
 text-align:right;
 margin:0 0 6px 0
}
.close a {
 color:#333;
 text-decoration:none;
 font-size:14px;
 font-weight:700
}
.con {
 text-indent:1.5pc;
 line-height:21px;
 overflow:scroll;
 width:750px; 
 height:800px;
}
</style>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/calendar.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.js"></script>
<script language="JavaScript" src="${shopStaticPath}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function showPremiumDetail(comCode,cmType,serialNumber){
    var item = null;
    if(comCode!=null&&comCode!=""){
		if(window.XMLHttpRequest) {//非IE内核浏览器
			req=new XMLHttpRequest();
	    }else if(window.ActiveXObject) {//IE内核浏览器
	        try {//IE6.0
	    	     req=new ActiveXObject("Microsoft.XMLHTTP");
	        } catch(e1) {
	            try {
	        	   req=new ActiveXObject("MSXML2.XMLHTTP");
	            }catch(e2){
	                try {
	            	  req=new ActiveXObject("MSXML3.XMLHTTP");
	                }catch(e3){
	                  alert("创建Ajax失败："+e3);
	                }
	            }
	         }
	    } else {//未知浏览器
	        alert("未能识别的浏览器");
	    }
	    var url = "${base}/shop/premium_trial!showPremiumDetail.action?comCode=" + comCode +"&cmType="+cmType+"&serialNumber="+serialNumber;
		req.open('get', url, true);
		req.onreadystatechange = function() {
		    if (req.readyState == 4) {
			    var json2 = req.responseText;
			    item = eval("(" + json2 + ")");
			    fillobjectType(item);
			}
		};
		req.send(null);
	}else{
	   fillobjectType(item)
	}
}
function fillobjectType(item) {
   if(item!=null){
      document.getElementById("r001").value=item.r001;
  	  document.getElementById("r001").disabled='disabled';
   	  document.getElementById("r001_premium").value=item.r001_premium;
      document.getElementById("r001_premium").disabled='disabled';
      document.getElementById("premium_R001").value=item.premium_R001;
      
      document.getElementById("r002").value=item.r002;
      document.getElementById("r002").disabled='disabled';
      document.getElementById("r002_premium").value=item.r002_premium;
      document.getElementById("r002_premium").readOnly=true;
      document.getElementById("premium_R002").value=item.premium_R002;
      
      document.getElementById("r0030").value=item.r0030;
      document.getElementById("r0030").disabled='disabled';
      document.getElementById("r0030_premium").value=item.r0030_premium;
      document.getElementById("r0030_premium").disabled='disabled';
      document.getElementById("premium_R0030").value=item.premium_R0030;
      
      document.getElementById("r0031").value=item.r0031;
      document.getElementById("r0031").disabled='disabled';
      document.getElementById("r0031_premium").value=item.r0031_premium;
      document.getElementById("r0031_premium").disabled='disabled';
      document.getElementById("premium_R0031").value=item.premium_R0031;
      
      document.getElementById("r004").value=item.r004;
      document.getElementById("r004").disabled='disabled';
      document.getElementById("r004_premium").value=item.r004_premium;
      document.getElementById("r004_premium").readOnly=true;
      document.getElementById("premium_R004").value=item.premium_R004;
      
      document.getElementById("r006").value=item.r006;
      document.getElementById("r006").disabled='disabled';
      document.getElementById("r006_type").value=item.r006_type;
      document.getElementById("r006_type").disabled='disabled';
      document.getElementById("premium_R006").value=item.premium_R006;
      
      document.getElementById("r008").value=item.r008;
      document.getElementById("r008").disabled='disabled';
      document.getElementById("r008_premium").value=item.r008_premium;
      document.getElementById("r008_premium").disabled='disabled';
      document.getElementById("premium_R008").value=item.premium_R008;
      
      document.getElementById("sclar").value=item.sclar;
      document.getElementById("sclar").disabled='disabled';
      document.getElementById("sclar_premium").value=item.sclar_premium;
      document.getElementById("sclar_premium").readOnly=true;
      document.getElementById("premium_SCLAR").value=item.premium_SCLAR;
      
      document.getElementById("NR001").value=item.NR001;
      document.getElementById("NR001").disabled='disabled';
      document.getElementById("nonDeductible_R001").value=item.nonDeductible_R001;
      document.getElementById("NR002").value=item.NR002;
      document.getElementById("NR002").disabled='disabled';
      document.getElementById("nonDeductible_R002").value=item.nonDeductible_R002;
      document.getElementById("NR0030").value=item.NR0030;
      document.getElementById("NR0030").disabled='disabled';
      document.getElementById("nonDeductible_R0030").value=item.nonDeductible_R0030;
      document.getElementById("NR0031").value=item.NR0031;
      document.getElementById("NR0031").disabled='disabled';
      document.getElementById("nonDeductible_R0031").value=item.nonDeductible_R0031;
      document.getElementById("NR004").value=item.NR004;
      document.getElementById("NR004").disabled='disabled';
      document.getElementById("nonDeductible_R004").value=item.nonDeductible_R004;
      document.getElementById("NR008").value=item.NR008;
      document.getElementById("NR008").disabled='disabled';
      document.getElementById("nonDeductible_R008").value=item.nonDeductible_R008;
      document.getElementById("dicount").value=item.dicount;
      document.getElementById("totle").value=item.totle;
   }else{
      document.getElementById("r001").value="N";
  	  document.getElementById("r001").disabled='';
   	  document.getElementById("r001_premium").value="5";
      document.getElementById("r001_premium").disabled='';
      document.getElementById("premium_R001").value='';
      document.getElementById("r002").value="N";
      document.getElementById("r002").disabled='';
      document.getElementById("r002_premium").value="";
      document.getElementById("r002_premium").readOnly=false;
      document.getElementById("premium_R002").value='';
      
       
      document.getElementById("r0030").value="N";
      document.getElementById("r0030").disabled='';
      document.getElementById("r0030_premium").value="10000";
      document.getElementById("r0030_premium").disabled='';
      document.getElementById("premium_R0030").value='';
      
      document.getElementById("r0031").value="N";
      document.getElementById("r0031").disabled='';
      document.getElementById("r0031_premium").value="10000";
      document.getElementById("r0031_premium").disabled='';
      document.getElementById("premium_R0031").value='';
      
      document.getElementById("r004").value="N";
      document.getElementById("r004").disabled='';
      document.getElementById("r004_premium").value="";
      document.getElementById("r004_premium").readOnly=false;
      document.getElementById("premium_R004").value='';
      
      document.getElementById("r006").value="N";
      document.getElementById("r006").disabled='';
      document.getElementById("r006_type").value="0";
      document.getElementById("r006_type").disabled='';
      document.getElementById("premium_R006").value='';
      
      document.getElementById("r008").value="N";
      document.getElementById("r008").disabled='';
      document.getElementById("r008_premium").value="2000";
      document.getElementById("r008_premium").disabled='';
      document.getElementById("premium_R008").value='';
      
      document.getElementById("sclar").value="N";
      document.getElementById("sclar").disabled='';
      document.getElementById("sclar_premium").value='';
      document.getElementById("sclar_premium").readOnly=false;
      document.getElementById("premium_SCLAR").value='';
      
      document.getElementById("NR001").value="N";
      document.getElementById("NR001").disabled='';
      document.getElementById("nonDeductible_R001").value='';
      document.getElementById("NR002").value="N";
      document.getElementById("NR002").disabled='';
      document.getElementById("nonDeductible_R002").value='';
      document.getElementById("NR0030").value="N";
      document.getElementById("NR0030").disabled='';
      document.getElementById("nonDeductible_R0030").value='';
      document.getElementById("NR0031").value="N";
      document.getElementById("NR0031").disabled='';
      document.getElementById("nonDeductible_R0031").value='';
      document.getElementById("NR004").value="N";
      document.getElementById("NR004").disabled='';
      document.getElementById("nonDeductible_R004").value='';
      document.getElementById("NR008").value="N";
      document.getElementById("NR008").disabled='';
      document.getElementById("nonDeductible_R008").value='';
      document.getElementById("dicount").value='';
      document.getElementById("totle").value='';
   }
   selectInsured();
}
function selectInsured(){
 var light=document.getElementById("light");
 var fade=document.getElementById('fade');
 light.style.display='block';
 fade.style.display='block';
 }
function checkValue(value,min,max){
	if(value>max){
		alert("录入值高于优惠上线，如需修改请重新录入");
	}
	if(value<min){
		alert("录入值低于优惠下线，如需修改请重新录入");
	}
}
function hide(){
 var light=document.getElementById("light");
 var fade=document.getElementById('fade');
 light.style.display='none';
 fade.style.display='none';
}
function sure(){
    document.getElementById("insuredForm").submit();
    return true;
}
</script>
</head>
<body>
    <form id="insuredForm" action="${base}/shop/premium_trial!getChangePremmium.action"  method="post">
       <table width="100%">
          <tr width="100%">
             <table width="60%">
                <tr>
                   <td>车辆行驶区域：
                     <select id="regionCode" name="sdTagetInformation.regionCode" >
					   <option value="-1">---请选择---</option>
					   <#if (citys != null && citys?size > 0)>
							<#list citys as list>
					            <option value="${list.placeCode}"
					               <#if (list.placeCode==sdTagetInformation.regionCode)!>
					                   selected
					            </#if>>${list.placeName}</option>
					        </#list>
					   </#if>
					 </select>
                   </td>
                   <td>上年投保公司：
                      <select style="width: 155px" name="sdTagetInformation.lastInsureCom" id="lastInsureCom">
                        <option value="-1">---请选择---</option>
                        <#if (coms != null && coms?size > 0)>
							<#list coms as list>
					            <option value="${list.supplierCode}"
					            <#if (list.supplierCode==sdTagetInformation.lastInsureCom)!>
					                   selected
					            </#if>>${list.shortName}</option>
					        </#list>
					   </#if>
                     </select>
                   </td>
                </tr>
                <tr>
                   <td>购车时间：
                      <input type="text" id="carBuyTime" name="sdTagetInformation.carBuyTime" value="${(sdTagetInformation.carBuyTime?string("yyyy-MM-dd"))!}"  onclick="WdatePicker();"/>
                   </td>
                   <td>车价：
                      <input type="text" id="carPrice" name="sdTagetInformation.carPrice"  value="${sdTagetInformation.carPrice}"/>
                   </td>
                   <td><input type="button" id="" name="" value="更新报价" onclick="sure();"/></td>
                </tr>
             </table>
          </tr>
          <tr width="100%">
             <table width="100%">
                <tr>
                   <td>公司</td>
                   <td>经济型</td>
                   <td>标准型</td>
                   <td>豪华型</td>
                   <td><a onclick="showPremiumDetail('','','')">自定义</a></td>
                   <td>理赔说明</td>
                   <td>其他说明</td>
                </tr>
                <#if (showInsurances != null && showInsurances?size > 0)>
					<#list showInsurances as list>
					   <tr>
					      <td>
					          <#if (coms != null && coms?size > 0)>
							     <#list coms as list1>
					                  <#if (list1.supplierCode==list.comCode)>
					                      ${list1.shortName}
					                  </#if>
					             </#list>
					          </#if>
					      </td>
					      <td>
					         <#if (list.ordinaryFlag== "Y")>
								 <a onclick="showPremiumDetail('${list.comCode}','CM002','${list.serialNumber}');"><font color="red">${list.economicPremium}</font></a>
							 <#else>
								<a onclick="showPremiumDetail('${list.comCode}','CM002','${list.serialNumber}');">${list.economicPremium}</a>
							 </#if>
					      </td>
					      <td>
					         <#if (list.economicFlag== "Y")>
								 <a onclick="showPremiumDetail('${list.comCode}','CM001','${list.serialNumber}');"><font color="red">${list.ordinaryPremium}</font></a>
							 <#else>
								<a onclick="showPremiumDetail('${list.comCode}','CM001','${list.serialNumber}');">${list.ordinaryPremium}</a>
							 </#if>
					      </td>
					      <td>
					         <#if (list.luxuryFlag== "Y")>
								 <a onclick="showPremiumDetail('${list.comCode}','CM003','${list.serialNumber}');"><font color="red">${list.luxuryPremium}</font></a>
							 <#else>
								<a onclick="showPremiumDetail('${list.comCode}','CM003','${list.serialNumber}');">${list.luxuryPremium}</a>
							 </#if>
					      </td>
					      <td>
					         <#if (list.zdyFlag== "Y")>
								 <a onclick="showPremiumDetail('${list.comCode}','CM004','${list.serialNumber}');"><font color="red">${list.zdyPremium}</font></a>
							 <#else>
								<a onclick="showPremiumDetail('${list.comCode}','CM004','${list.serialNumber}');">${list.zdyPremium}</a>
							 </#if>
					      </td>
					      <td></td>
					      <td></td>
					   </tr>
					</#list>
				</#if>
             </table>
             
          </tr>
       </table>
       <div id="light" class="white_content">
      <div class="con">
      <div  style="color:red;">
		试算结果仅供参考
	   </div>
        <table width="650px"  bordercolor="#FF8C69"  id = "discontElement">
            <tr><td align="center" width="25%">险种</td><td align="center" width="25%">是否投保</td><td align="center" width="25%">保额</td><td align="center" width="25%">保费</td></tr>
            <tr>
			  <td colspan="4">商业险<em class="redd">基本</em>险种</th>
		    </tr>
			<tr>
				<td align="center" width="25%">
					<span>第三者责任险</span>
				</td>
			    <td align="center" width="25%">
				    <select id="r001" name="carMenu.r001" disabled="disabled">
					    <option value="N">不投保</option>
					    <option value="Y">投保</option>
				    </select>
			    </td>
			    <td align="center" width="25%">
				    <select name="carMenu.r001_premium" id="r001_premium" style="width:100px;" disabled="disabled">
					    <option value="5">5万元</option>
						<option value="10">10万元</option>
						<option value="15">15万元</option>
						<option value="20">20万元</option>
						<option value="30">30万元</option>
						<option value="50">50万元</option>
						<option value="100">100万元</option>
					</select>
				</td>
				<td align="center" width="25%">
				   <input id="premium_R001" value="" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<span>机动车损失险</span>
				</td>
				<td align="center">
					<select id="r002" name="carMenu.r002" disabled="disabled">
						<option value="N">不投保</option>
						<option value="Y">投保</option>
				    </select>
				</td>
				<td align="center">
					<input type="text" name="carMenu.r002_premium" id="r002_premium"
						  style="width: 90px" onkeyup="if(isNaN(value))execCommand('undo')"/>元
				</td>
				<td align="center">
				   <input id="premium_R002" value="" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<span>司机座位责任险</span>
				</td>
			    <td align="center">
				    <select name="carMenu.r0030" id="r0030" disabled="disabled">
						<option value="N">不投保</option>
						<option value="Y">投保</option>
				    </select>
			    </td>
			    <td align="center">
				    <select name="carMenu.r0030_premium" id="r0030_premium" disabled="disabled">
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
			    </td align="center">
			    <td align="center">
				   <input id="premium_R0030" value="" readonly="readonly"/>
			    </td>
		    </tr>
		    <tr>
			    <td align="center">
				   <span>乘客座位责任险</span>
			    </td>
			    <td align="center">
				    <select name="carMenu.r0031" id="r0031" disabled="disabled">
						<option value="N">不投保</option>
						<option value="Y">投保</option>
				    </select>
			    </td>
			    <td align="center">
				    <select name="carMenu.r0031_premium" id="r0031_premium" style="width:100px;" disabled="disabled">
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
			    </td>
			    <td align="center">
			       <input id="premium_R0031" value="" readonly="readonly"/>
			    </td>
		    </tr>
			<tr>
				<td align="center">
					<span>盗抢险</span>
				</td>
				<td align="center">
				   <select name="carMenu.r004" id="r004" disabled="disabled">
						<option value="N">不投保</option>
						<option value="Y">投保</option>
				    </select>
				</td>
				<td align="center">
					<input name="carMenu.r004_premium" id="r004_premium" style="width: 90px" onkeyup="if(isNaN(value))execCommand('undo')" readonly="readonly"/>元
				</td>
				<td align="center">
				    <input id="premium_R004" value="" readonly="readonly"/>
				</td>
		    </tr>
		    <tr>
			    <td align="center">
				    <span>车身划痕险</span>
			    </td>
		        <td align="center">
			        <select name="carMenu.r008" id="r008" disabled="disabled">
				        <option value="N">不投保</option>
				        <option value="Y">投保</option>
			        </select>
		        </td>
		        <td align="center"> 
			        <select name="carMenu.r008_premium" id="r008_premium" style="width: 100px" disabled="disabled">
				         <option value="2000">2000元</option>
				         <option value="5000">5000元</option>
				         <option value="10000">10000元</option>
				         <option value="20000">20000元</option>
				    </select>
			   </td>
			   <td align="center">
			        <input id="premium_R008" value="" readonly="readonly"/>
			   </td>
		   </tr>
		   <tr>
			<td align="center" class="subth">
			    <span>玻璃单独破碎险</span>
			</td>
			<td align="center">
				<select name="carMenu.r006" id="r006" disabled="disabled">
					<option value="N">不投保</option>
					<option value="Y">投保</option>
				</select>
			</td>
			<td>
				<select name="carMenu.r006_type" id="r006_type" style="width: 100px" disabled="disabled">
					<option value="0">国产</option>
					<option value="1">进口</option>
				</select>
			</td>
			<td>
				<input id="premium_R006" value="" readonly="readonly"/>
			</td>
		</tr>
							
			<tr>
				<td align="left">
					<span>自然损失险</span>
				</td>
				<td align="left">
					<select name="carMenu.sclar" id="sclar" disabled="disabled">
						<option value="N">不投保</option>
						<option value="Y">投保</option>
					</select>
				</td>
				<td>
					<input type="text" name="carMenu.sclar_premium" id="sclar_premium"
						  style="width: 90px" onkeyup="if(isNaN(value))execCommand('undo')" readonly="readonly"/>元
				</td>
				<td>
				    <input id="premium_SCLAR" value="" readonly="readonly"/>
				</td>
			</tr>
            <tr>
				<th align="left" colspan="4">商业险<em>不计免赔</em>险种</th>
			</tr>
            <tr>
				<td align="left">
					<span>第三责任险</span>
				</td>
				<td align="left">
					<select name="carMenu.NR001" id="NR001" disabled="disabled">
						<option value="N">不投保</option>
						<option value="Y">投保</option>
					</select>
				</td>
				<td></td>
				<td>
				   <input id="nonDeductible_R001" value="" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td align="left">
					<span>机动车损失险</span>
				</td>
				<td align="left">
					<select name="carMenu.NR002" id="NR002" disabled="disabled">
						<option value="N">不投保</option>
						<option value="Y">投保</option>
					</select>
				</td>
				<td></td>
				<td>
				   <input id="nonDeductible_R002" value="" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td align="left" class="subth">
					<span>司机座位责任险</span>
				</td>
				<td align="left">
					<select name="carMenu.NR0030" id="NR0030" disabled="disabled">
						<option value="N">不投保</option>
						<option value="Y">投保</option>
				    </select>
				</td>
				<td></td>
				<td>
				   <input id="nonDeductible_R0030" value="" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td align="left">
					<span>乘客座位责任险</span>
			</td>
			<td align="left">
				<select name="carMenu.NR0031" id="NR0031" disabled="disabled">
					<option value="N">不投保</option>
					<option value="Y">投保</option>
				</select>
			</td>
			<td></td>
			<td>
			   <input id="nonDeductible_R0031" value="" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td align="left">
				<span>盗抢险</span>
			</td>
			<td align="left">
				<select name="carMenu.NR004" id="NR004" disabled="disabled">
					<option value="N">不投保</option>
					<option value="Y">投保</option>
				</select>
			</td>
			<td></td>
			<td>
			   <input id="nonDeductible_R004" value="" readonly="readonly"/>
			</td>
		</tr>
		<tr>
			<td align="left">
				<span>车身划痕损失险</span>
			</td>
			<td align="left">
				<select name="carMenu.NR008" id="NR008" disabled="disabled">
					<option value="N">不投保</option>
					<option value="Y">投保</option>
				</select>
			</td>
			<td></td>
			<td>
			   <input id="nonDeductible_R008" value="" readonly="readonly"/>
			</td>
		</tr>
		<tr>
		    <td>优惠率<td>
		    <td><td>
		    <td>
		       <input id="dicount" value="" readOnly="readonly"/>
		    <td>
		</tr>
		<tr>
		    <td>保费总价为<td>
		    <td><td>
		    <td>
		       <input id="totle" value="" readonly="readonly"/>
		    <td>
		</tr>
		<tr><td id ="closed" align="center" colspan="4"><a href="javascript:void(0)" onclick="hide();"> 关闭</a></td></tr>
        </table>
        <div  style="color:red;">
		试算结果仅供参考
	   </div>
      </div>
</div>
<div id="fade" class="black_overlay"></div>
    </form>
</body>
</html>
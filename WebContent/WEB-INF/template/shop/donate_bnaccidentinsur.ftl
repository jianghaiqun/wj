<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<!--<%@ taglib prefix="s" uri="/struts-tags" %>-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>填写投保信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/template/shop/css/insure.css" />
<link rel="stylesheet" type="text/css" href="${base}/template/shop/css/insure_table.css" />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/redesign/re_header.css" />
<style type="text/css">
<!--
table { table-layout: fixed; } 
td {overflow:hidden; white-space:nowrap; text-overflow:ellipsis;}

.btn1_mouseout {
	 BORDER-RIGHT: #7EBF4F 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7EBF4F 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#B3D997); BORDER-LEFT: #7EBF4F 1px solid; CURSOR: hand; PADDING-TOP: 2px; BORDER-BOTTOM: #7EBF4F 1px solid
} 
.btn1_mouseover {
BORDER-RIGHT: #7EBF4F 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7EBF4F 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#CAE4B6); BORDER-LEFT: #7EBF4F 1px solid; CURSOR: hand;PADDING-TOP: 2px; BORDER-BOTTOM: #7EBF4F 1px solid
} 
label.error {   
    color: red;display: none;  font-size:12px; 
}

.btn-more{padding:10px 0}
	.btn-more label{margin:0 7px}
	.button{padding-bottom:20px;margin-top:10px;overflow:hidden;zoom:1;}
	.butL,.butM,.butS,.butB,.butMax{background-image:url(/images/buts.png);background-color:#fd623c;cursor:pointer;height:27px;line-height:26px;border:0;padding:0;font-size:14px;color:#fff;font-weight:bold;text-shadow:1px 1px #c62a0a;}
	
-->
</style>

<script language="javascript" type="text/javascript" defer>

function loadRelation(){
	 var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
	 /* zhangjinquan 11180 2012-10-23修改对保险期限的处理，以保证暂存后能够正确带出曾经保存的数据 */
	 if(("true"==protectionPeriodFlag) && ("" == "${(information.protectionPeriod)!}"))
	 {
		 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		 var d = new Date();
		 d.setDate(d.getDate()+1);
		 var ndate = d.getFullYear()+"-"+add_zero(d.getMonth()+1)+"-"+add_zero(d.getDate());
		 
		 if(document.getElementById("effective").value== ""){
			 document.getElementById("effective").value = ndate;
			 var temp = addDate(protectionPeriodTy,protectionPeriodLast, ndate);
			 document.getElementById("fail").value = temp;
			 document.getElementById("fail_").value = temp;
		 }
	 }
	   // 证件类型
	 var applicantTypeId = document.getElementById('applicantTypeId');
	 var applicantTypeId_index = applicantTypeId.selectedIndex;          
	 var applicantTypeId_value = applicantTypeId.options[applicantTypeId_index].text; 
	 if(applicantTypeId_value.indexOf('身份证') == -1){
	 	 document.getElementById("applicantId").removeAttributeNode(document.getElementById("applicantId").getAttributeNode("class"));
	 }
	  
}
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body onLoad="loadRelation()">
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">



<div class="line_log">
<div class="sdong"></div>

</div>
 
    <#if (status =="update")!>
 	<form id="orderInfoForm" action="donate!bbxinfoUpdate.action?" method="post" enctype ="multipart/form-data" onsubmit="insuredAndDuty();">
 	</#if>
 	<#if (status =="")!>
 	<form id="orderInfoForm" action="donate!buildOrder.action?" method="post" enctype ="multipart/form-data" onsubmit="insuredAndDuty();">
   </#if>
   <div class="line_a">
   <div class="pri">
   <span>【${productName}】</span>
   </div>
   <div class="pri">
   <span><font color="red">温馨提示：尊贵的开心保会员，请您如实填写投保信息，免费获得20万航空意外险！</font></span>
   &nbsp;&nbsp;&nbsp;
   </div>
</div>
    
    <div>&nbsp;</div>
       <div class="line_a">
            <div  class="line_at">保障期间<img width="18" height="16" title="是指保险合同约定承担保险责任的一段时间。您可以根据实际需要选择起保时间和终止时间。" src="${base}/template/shop/images/wen_a.png"/></div>
            <div class="form">
				  <#if (periodList.size()>0)!>
				      <p>
				          <span>保障期限</span>
				           <#if (periodList?size>1)>
					        <select name="period"  id="period"  >
									<#list periodList as list>
										<option value="${list.factorValue}">
											${list.factorDisplayValue}
										</option>
									</#list>
							  </select>
						   <#else>
						   		<#list periodList as list>
						   		        <input value="${list.factorValue}" name="period" id="period" type="hidden">
										<span>${list.factorDisplayValue}</span>
								</#list>
						   </#if>
				      </p>
				  </#if>
				 <p>
                    <span>保单起保日期:</span> 
                    <input id="effective" name="effective" type="text" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}'});effchange();"   value="${(information.effective)!}"  class="required"/>
                 </p>
            <#if (periodList.size()>0)!>
	                 <p>
	                    <span>保单终止日期:</span> 
	                    <input id="fail_" name="fail_" disabled="disabled" value="${(information.fail)!}" />
	                    <input id="fail" name="fail" type="hidden"   onclick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'effective\')}'})" onchange = "failchange()" value="${(information.fail)!}" />
	                </p>
                 <#else>
                     <p>
	                    <span>保单终止日期:</span> 
	                    <input id="fail_" name="fail_" type="hidden"   value="${(information.fail)!}" />
	                    <input id="fail" name="fail"   onclick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'effective\')}'})" onchange = "failchange()" value="${(information.fail)!}" />
	                </p>
                 </#if>
                 
            </div>
       </div>
       
       
       <div class="line_a">
       <div  class="line_at">投保人信息<img width="18" height="16" title="投保人,是指与保险公司签订保险合同、缴纳保费的人。投保人同时也是保险合同的所有人。投保人信息非常重要，因此请按照有效身份证件记载的信息准确填写。" src="${base}/template/shop/images/wen_a.png"/></div>
       <div class="form">
       <input type="hidden" id="price"  name="price" value="${price}" />
	    
	    <input type ="hidden" name="productName" value="${productName}">
	    <input type ="hidden" id="productId" name="productId" value="${productId}">
	    <input type ="hidden" name="insurType" value="${insurType}">
	    <input type ="hidden" id="orderStatus" name="orderStatus" value="${orderstatus}" />
	    <input type ="hidden"  id ="status" name="status" value="${status}" />
	    <input type ="hidden"  id ="orderId" name="orderId" value="${orderId}" />
	    <input type ="hidden"  id ="dutyReq" name="dutyReq" value="${dutyReq}" />
	    <input type ="hidden"  id ="dutyDisReq" name="dutyDisReq" value="${dutyDisReq}" />
	    <input type ="hidden"  id ="insureReq" name="insureReq" value="${insureReq}" />
	    <input type ="hidden" id="insuranceCompany" name="insuranceCompany" value="${insuranceCompany}">
	    <input type ="hidden" id="insuranceCompanySn" name="insuranceCompanySn" value="${insuranceCompanySn}">
	    <input type ="hidden" id="outRiskCode" name="outRiskCode" value="${outRiskCode}">
	    <input type ="hidden" id="protectionPeriodTy" name="protectionPeriodTy" value="${protectionPeriodTy}">
	    <input type ="hidden" id="protectionPeriodLast" name="protectionPeriodLast" value="${protectionPeriodLast}">
	    <input type ="hidden" id="protectionPeriodFlag" name="protectionPeriodFlag" value="true">
	    <input type="hidden" id="relation1name" name="informationInsureds1.recognizeeAppntRelationName" value="${(informationInsureds1.recognizeeAppntRelationName)!}"/>
	    <input type="hidden" id="applicanTypeName" name="informationAppnt.applicantIdentityTypeName" value="${(informationAppnt.applicantIdentityTypeName)!}"/>
	    <input type="hidden" id="recognizeeIdentityTypeName" name="informationInsureds1.recognizeeIdentityTypeName" value="${(informationInsureds1.recognizeeIdentityTypeName)!}"/>
	    <input type="hidden" id="recognizeeSexName" name="informationInsureds1.recognizeeSexName" value="${(informationInsureds1.recognizeeSexName)!}"/>
	    <input type="hidden" id="applicantSexName" name="informationAppnt.applicantSexName" value="${(informationAppnt.applicantSexName)!}"/>
	    <input type ="hidden"  id ="insurTypeChild" name="insurTypeChild" value="${insurTypeChild}" />
	    <input type ="hidden"  id ="textAgeFlag" name="textAgeFlag" value="${textAgeFlag}" />
	    
       <p>
               <span>姓名:</span> 
               <input type="text" id="applicantName" name="informationAppnt.applicantName" value="${(informationAppnt.applicantName)!}"  onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"class="required"  maxlength=50/>
            <label class="requireField">*</label>
           </p>
           <p>
           <span></span>
           <font color="#808080">填写的姓名一定要与有效身份证件保持一致，以保证日后理赔顺利。</font>
           </p>
            <p>
               <span>证件类型:</span>
               <select id="applicantTypeId" name="informationAppnt.applicantIdentityType" style="width:125px;" onchange="changeVerification('applicantTypeId','applicantId');">
                   <#list certificateCode as list>
                       <option value="${list.get('codeValue')}"  <#if (informationAppnt.applicantIdentityType==list.get('codeValue'))>selected="selected"</#if>>${list.get("codeName")}</option>
                   </#list>
				</select>
				<label class="requireField">*</label>
            </p>
            <p>
               <span>证件号码:</span>   
               <input id="applicantId" name="informationAppnt.applicantIdentityId" maxlength=64 type="text" value="${(informationAppnt.applicantIdentityId)!}" name="informationAppnt.applicantIdentityId" onblur="idcheck('applicantId','applicantBirthday','applicantSex');"  class="required isIdCardNo"/>
           	<label class="requireField">*</label>
            </p>
            <p>
            <span></span>
            <font color="#808080">证件类型和号码请您仔细选择或填写并核对无误，以免影响日后理赔。</font>
            </p>
                <p>
	                 <span>性别:</span>
		                <select id="applicantSex" name="informationAppnt.applicantSex" style="width:125px;">
		                  <#list sexs as list>
		                      <option value="${list.get('codeValue')}" <#if (informationAppnt.applicantSex==list.get('codeValue'))>selected="selected"</#if>>${list.get("codeName")}</option>
		                  </#list>
		                </select>
	                 <label class="requireField">*</label>
                </p>
                <p>
                   <span>出生日期:</span> 
                   <input id="applicantBirthday" name="informationAppnt.applicantBirthday" type="text" onclick="WdatePicker({skin:'whyGreen',startDate:'1980-01-01'})"  value="${(informationAppnt.applicantBirthday)!}"  class="required"  />
                	<label class="requireField">*</label>
                	<font id="applicantBirthdayID" color="red"></font> 
                </p>
            <p>
               <span>手机号码:</span>
               <input type="text" name="informationAppnt.applicantMobile" value="${(informationAppnt.applicantMobile)!}"  class="required mobile" />
           	<label class="requireField">*</label>
			</p>
			<p>
            <span></span>
            <font color="#808080">请填写自己真实的号码，方便开心保为您提供优质服务。</font>
            </p>
            <p>
               <span>电子邮箱:</span>
               <input type="text" name="informationAppnt.applicantMail" value="${(informationAppnt.applicantMail)!}" class="required email"/>
               <label class="requireField">*</label>
           </p>
           <p>
           <span></span>
           <font color="#808080">提醒您！我们仅向该邮箱发送电子保单。并且在办理保单业务时，开心保网也只接收您通过该邮箱提交的材料。</font>
       </p>
      </div>
  </div>
  
  <div class="line_a">
     <div  class="line_at">被保人信息<img width="18" height="16" title="被保险人，就是指保险合同的保障对象。通常，被保险人或其继承人（被保险人身故且未指定受益人时）会享有保险金的申请权。 " src="${base}/template/shop/images/wen_a.png"/></div>
     <div class="form">
       <p>
           <span>与投保人关系:</span> 
			 <input type="hidden" id="relationFlag1" name="relationFlag1" value="5"/> 
			 <input type="hidden" id="relation1" name="informationInsureds1.recognizeeAppntRelation" value="本人"/>本人
        </p>
       
      </div>
   </div>

 <div class="line_a">
 <div  class="line_at">受益人<img width="18" height="16" title="受益人是指人身保险合同中由投保人和被保险人共同指定的享有保险金请求权利的人。由于网上投保时，受益人指定无法根据法律规定得到被保险人的书面确认而合法有效，因此通常采取默认被保险人的法定继承人作为保单的身故受益人。法定继承人是指根据法律规定直接继承被保险人遗产的配偶、父母、子女等人。" src="${base}/template/shop/images/wen_a.png"/></div>
 <div class="syr">
法定继承人
     </div>
     </div>
 <div class="clear"></div>
 
 
 <div class="line_a">
     <div class="line_at">保障内容信息</div>
     <div>
     <table id="mytable" cellspacing="1" class="datagrid3"> 
     <tr> 
     <th scope="col" >保障项目</th> 
     <th scope="col" >保额</th> 
     <th scope="col" >保障内容</th> 
     </tr> 
 		<caption> </caption> 
			<#assign index = 1 />
				<#list dutyFactor as list>
					<tr  style="background-color: #EFEFEF; " >
						<td width="25%">
							${list.dudtyFactorName}
						</td>
						<td style="padding-left:135px" align="left" width="25%">
						    <#if (list.fdAmntPremList.size()>0)!>
						        <#if (list.fdAmntPremList.size()>1)>
								    <select onchange="changeAmnt('amnt${index}','amntDis${index}');">
										<#list list.fdAmntPremList as list1>
											<option value="${list1.backUp1}">
												${list1.amnt}
											</option>
										</#list>
									</select>
									<input type="hidden" id="amnt${index}" name="amnt${index}" value="${list.fdAmntPremList.get(0).backUp1}">
									<input type="hidden" id="amntDis${index}" name="amntDis${index}" value="${list.fdAmntPremList.get(0).amnt}">
								<#else>
						            <#list list.fdAmntPremList as list2>
						                ${list2.amnt}
						                <input type="hidden" id="amnt${index}" name="amnt${index}" value="${list2.backUp1}">
						                <input type="hidden" id="amntDis${index}" name="amntDis${index}" value="${list2.amnt}">
						            </#list>
						         </#if>
						    </#if>
						</td>
						<td width="25%">
						${list.define}
					</td>
						<input type="hidden" id="dutyCode${index}" name="dutyCode${index}" value="${list.dutyCode}" />
					</tr>
			<#assign index=index+1 />
				</#list>
		</table>
			</div>
			<br>
			<input type="hidden" id="dutyFactorSize" name="dutyFactorSize" value="${dutyFactor.size()}" />
 </div>
 <div class="line_a">
 <div class="pri">
 <center>
	 <span>
	 	保费:
	</span>
	 <font  size="4" color="red">
	 ￥	<span id = "priceTotle">${price}</span>
	 </font>
 </center>
 </div>
</div>
 <div class="syr2">
 <input type="submit" value="  下一步  "  class="butM dev_submitButton" style="height=20px;width=100px;" >
 &nbsp; &nbsp; &nbsp; 

 </div>
 <div class="clear"></div>
   </form>

 <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
 <script type="text/javascript" src="${base}/template/shop/js/calendar.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${base}/template/common/js/json2.js"></script>
<script type="text/javascript" src="${base}/wwwroot/kxb/js/donateDuty.js"></script>
<script type="text/javascript">
function idcheck(idCode,birthdayId,sexId){
    str=document.getElementById(idCode).value;
    if(str.length==15 || str.length==18){
    	if(str.length==18){
    		var sex;
    		if(str.substring(16,17)%2==0){   
    			sex = "1";   
            }else{   
            	sex = "0";   
            }   
    		var re=/\d{6}([12]\d{3})([01]\d)([0123]\d)\d{4}/;
    		var id=re.exec(str);
    		document.getElementById(birthdayId).value = id[1]+"-"+id[2]+"-"+id[3];
    		document.getElementById(sexId).value = sex;
    	}else{
    		var sex;
    		if(str.substring(14,15)%2==0){   
    			sex = "1";   
            }else{   
            	sex = "0";   
            }   
    		var year = "19"+str.substring(6, 8);      
            var month = str.substring(8, 10);      
    		var day = str.substring(10, 12);      
    		document.getElementById(birthdayId).value = year+"-"+month+"-"+day;
    		document.getElementById(sexId).value = sex;
    	}
    }
}
</script>
 </div>
</body>
</html>
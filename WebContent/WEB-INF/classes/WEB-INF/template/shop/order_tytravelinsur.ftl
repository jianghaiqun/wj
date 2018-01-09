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
table.datagrid3 { table-layout: fixed; } 
table.datagrid3 td {overflow:hidden; white-space:nowrap; text-overflow:ellipsis;}

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

<script language="javascript" type="text/javascript">
function loadRelation(){
	 var s = document.getElementById("relation1").options[0].text;
	 var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
	 if("本人"==s){
		 document.getElementById("relationFlag1").value="true";
	 }else{
		 document.getElementById("relationFlag1").value="";
	 }
	 /* zhangjinquan 11180 2012-10-23修改对保险期限的处理，以保证暂存后能够正确带出曾经保存的数据 */
	 if(("true"==protectionPeriodFlag) && ("" == "${(information.protectionPeriod)!}"))
	 {
			 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		 var d = new Date();
		 d.setDate(d.getDate()+1);
		 var ndate = d.getFullYear()+"-"+add_zero(d.getMonth()+1)+"-"+add_zero(d.getDate());
		 document.getElementById("effective").value = ndate;
		 var temp = addDate(protectionPeriodTy,protectionPeriodLast, ndate);
		 document.getElementById("fail").value = temp;
		 document.getElementById("fail_").value = temp;
	 }
	  // 证件类型
	 var applicantTypeId = document.getElementById('applicantTypeId');
	 var applicantTypeId_index = applicantTypeId.selectedIndex;          
	 var applicantTypeId_value = applicantTypeId.options[applicantTypeId_index].text;     
	 if(applicantTypeId_value.indexOf('身份证') == -1){
		 document.getElementById("applicantId").className = "required";
	 }
	 
	 // 与投保人关系
	 var relation1 = document.getElementById('relation1');
	 var relation1_index = relation1.selectedIndex;          
	 var relation1_value = relation1.options[relation1_index].text; 
	 if(relation1_value.indexOf('本人') == -1){
	 	 addValidation();
	 	 document.getElementById("insurancedOne").style.display="block";
	 } else {
	 	 cancelVerification();
		 document.getElementById("insurancedOne").style.display="none";
	 }
}  
function changeInformation(objForm,aa){
	if(aa=="insurancedOne"){
		var objSelect = objForm.relation1;
		var s = objSelect.options[objSelect.selectedIndex].text;
	}
   if(s=="本人"){
       	   cancelVerification();
	       /* zhangjinquan 11180 bug963 修改与被保人关系时是本人，则拷贝相关信息 2012-10-12 start */
	       document.getElementById("recognizeeName1").value = document.getElementById("applicantName").value;
	       document.getElementById("recognizeeTypeId1").options.selectedIndex = document.getElementById("applicantTypeId").options.selectedIndex;
	       document.getElementById("recognizeeId1").value = document.getElementById("applicantId").value;
	       document.getElementById("recognizeeSex1").options.selectedIndex = document.getElementById("applicantSex").options.selectedIndex;
	       document.getElementById("recognizeeBirthday1").value = document.getElementById("applicantBirthday").value;
	       document.getElementById("recognizeeMobile1").value=document.getElementById("applicantMobile").value;
	       document.getElementById("recognizeeMail1").value =document.getElementById("applicantMail").value;
	       document.getElementById("recognizeeAddress1").value =document.getElementById("applicantAddress").value;
	       document.getElementById("recognizeeZipCode1").value =document.getElementById("applicantZipCode").value;
	       document.getElementById("insuredAreaA1").options.selectedIndex = document.getElementById("applicantArea1").options.selectedIndex;
	       document.getElementById("insuredAreaA2").options.selectedIndex = document.getElementById("applicantArea2").options.selectedIndex;
	       /* zhangjinquan 11180 bug963 修改与被保人关系时是本人，则拷贝相关信息 2012-10-12 end   */
        
       document.getElementById(aa).style.display="none";
   }else{
        	addValidation();
	        /* zhangjinquan 11180 bug963 修改与被保人关系时不是本人，则清空相关信息 2012-10-12 start */
	        document.getElementById("recognizeeName1").value = "";
	        document.getElementById("recognizeeTypeId1").options.selectedIndex = 0;
	        document.getElementById("recognizeeId1").value = "";
	        document.getElementById("recognizeeSex1").options.selectedIndex = 0;
	        document.getElementById("recognizeeBirthday1").value = "";
	        document.getElementById("recognizeeMobile1").value="";
	        document.getElementById("recognizeeMail1").value ="";
	        document.getElementById("recognizeeAddress1").value ="";
		    document.getElementById("recognizeeZipCode1").value ="";
		    document.getElementById("insuredAreaA1").options.selectedIndex = 0;
		    document.getElementById("insuredAreaA2").options.selectedIndex = 0;
	        /* zhangjinquan 11180 bug963 修改与被保人关系时不是本人，则清空相关信息 2012-10-12 end   */
        
        document.getElementById(aa).style.display="block";
   }
   validateBirthday();
   dateTrigger();   
}

function addValidation(){
	document.getElementById("recognizeeName1").className = "required";
    if(document.getElementById("insuredAreaA2")!=null){
    	document.getElementById("insuredAreaA2").className =  "selectTest {selectTest:false, messages: {selectTest: '请选择地区'}}";
    }
    document.getElementById("recognizeeMobile1").className = "required mobile";
    var recognizeeTypeId1 = document.getElementById('recognizeeTypeId1');
	var recognizeeTypeId_index = recognizeeTypeId1.selectedIndex;          
	var recognizeeTypeId_value = recognizeeTypeId1.options[recognizeeTypeId_index].text;     
	if(recognizeeTypeId_value.indexOf("身份证") == -1){
		document.getElementById("recognizeeId1").className = "required";
	}else{
		document.getElementById("recognizeeId1").className = "required isIdCardNo";
	}
    document.getElementById("recognizeeBirthday1").className = "required";
    document.getElementById("recognizeeMail1").className ="required email";
    if(document.getElementById("recognizeeAddress1")!=null){
    	document.getElementById("recognizeeAddress1").className ="required adress";
    }
    if(document.getElementById("recognizeeZipCode1")!=null){
    	document.getElementById("recognizeeZipCode1").className ="required zipCode";
    }
    document.getElementById("relationFlag1").value="";
}
function cancelVerification(){
	remoteAttributeNew(document.getElementById("recognizeeName1"), "class");
    if(document.getElementById("insuredAreaA2")!=null){
    	remoteAttributeNew(document.getElementById("insuredAreaA2"), "class");
    }
    remoteAttributeNew(document.getElementById("recognizeeMobile1"), "class");
    remoteAttributeNew(document.getElementById("recognizeeId1"), "class");
    remoteAttributeNew(document.getElementById("recognizeeBirthday1"), "class");
    remoteAttributeNew(document.getElementById("recognizeeMail1"), "class");
    if(document.getElementById("recognizeeAddress1")!=null){
    	remoteAttributeNew(document.getElementById("recognizeeAddress1"), "class");
    }
    if(document.getElementById("recognizeeZipCode1")!=null){
    	remoteAttributeNew(document.getElementById("recognizeeZipCode1"), "class");
    }
    document.getElementById("relationFlag1").value="true";
}

</script>
</head>
<body onLoad="loadRelation()">
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">



<div class="line_log">
<div class="sdong"></div>

</div>
 
    <#if (status =="update")!>
 	<form id="orderInfoForm" action="order!bbxinfoUpdate.action?" method="post" enctype ="multipart/form-data" onsubmit="return insuredAndDuty();">
 	</#if>
 	<#if (status =="")!>
 	<form id="orderInfoForm" action="order!bbxinfosave.action?" method="post" enctype ="multipart/form-data" onsubmit="return insuredAndDuty();">
   </#if>
   
   <div class="line_a">
   <div class="pri">
   ${productName}&nbsp;&nbsp;&nbsp;
   <span>【${insuranceCompany}】</span>  
   &nbsp;&nbsp;&nbsp;
   </div>
</div>
    
    <div>&nbsp;</div>
     <div class="line_a">
 <div class="pri">
 <center>
	<span>
	 	现价:
	</span>
	 <font  size="4" color="red">
	 ￥	<span id = "priceTotle_"><#if (information.protectionPeriod)??>${order.productTotalPrice}<#else>${price}</#if></span>
	 </font>
	 &nbsp;
	 <#if (discount) ? has_content >
		 <span>
		 	原价:
		</span>
		 <font  size="4" color="red">
		 ￥	<span id = "discountPrice_" style="text-decoration:line-through;">${countPrice}</span>
		 </font>
	 <#else>
	    <span id = "discountPrice_" style="display:none;">${countPrice}</span>
	 </#if>
 </center>
 </div>
</div>
       <div class="line_a">
            <div  class="line_at">保险期限<img width="18" height="16" title="是指保险合同约定承担保险责任的一段时间。您可以根据实际需要选择起保时间和终止时间。" src="${base}/template/shop/images/wen_a.png"/></div>
            <div class="form">
            	 <#if (planList.size()>0)!>
                    <p>
                        <span>投保计划</span> 
                        <#if planList.size() &gt; 1>
	                        <select name="plan"  id="plan" onchange="planChange();">
									<#list planList as list>
									   <#if (order.brkRiskCode)??>
											<option value="${list.factorValue}" <#if (list.factorValue == order.brkRiskCode) !>selected="selected"</#if> >
										<#else>
											<option value="${list.factorValue}" <#if (list.factorValue == brkRiskCode)!> selected</#if>>
										</#if>
											${list.factorDisplayValue}
										</option>
									</#list>
							 </select>
						<#else>
							<#list planList as list>
						   		<input value="${list.factorValue}" name="plan" id="plan" type="hidden">
								<span>${list.factorDisplayValue}</span>
							</#list>
						</#if>
                    </p>
                 </#if>
                 
            
                 <#if (gradeList.size()>0)!>
                    <p>
                        <span>产品级别</span> 
                        <select name="grade"  id="grade">
							<#list gradeList as list>
								<option value="${list.factorValue}">
									${list.factorDisplayValue}
								</option>
							</#list>
						</select>
                    </p>
                 </#if>
                 <#if (feeYearList.size()>0)!>
					<p>
						<span>缴费年期</span>
						<select name="feeYear"  id="feeYear">
							<#list feeYearList as list>
								<option value="${list.factorValue}">
									${list.factorDisplayValue}
								</option>
							</#list>
						</select>
					</p>
				  </#if>
				  <#if (appLevelList.size()>0)!>
				      <p>
				         <span>缴费方式</span>
				         <select name="appLevel"  id="appLevel">
								<#list appLevelList as list>
									<option value="${list.factorValue}">
										${list.factorDisplayValue}
									</option>
								</#list>
						 </select>
				      </p>
				  </#if>
				  <#if (appTypeList.size()>0)!>
				      <p>
				          <span>投保类别</span>
				          <select name="appType"  id="appType">
								<#list appTypeList as list>
									<option value="${list.factorValue}">
										${list.factorDisplayValue}
									</option>
								</#list>
						  </select>
				      </p>
				  </#if>
				  <#if (periodList.size()>0)!>
				      <p>
				          <span>保险期限</span>
				          <#if (periodList?size>1)>
					        <select name="period"  id="period" onchange="periodChange();">
									<#list periodList as list>
										<!-- zhangjinquan 11180 bug880 通过字符串包含判断是否为选中的保险期限 2012-10-11 -->
										<#if (information.allPeriod)??>
										<option value="${list.factorValue}" <#if (list.factorValue == information.allPeriod) !>selected="selected"</#if> >
										<#else>
										<option value="${list.factorValue}" <#if (list.factorValue == period)!> selected</#if>>
										</#if>
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
                    <input id="effective" name="effective" type="text" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}'});effchange();"   value="${(information.effective)!}"   class="required"/>
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
       <input type="hidden"  id="price"  name="price" value="<#if (information.protectionPeriod)??>${order.productTotalPrice}<#else>${price}</#if>" />
       <input type ="hidden" id="countPrice" name="countPrice" value="<#if (information.protectionPeriod)??>${order.currentTermAmount}<#else>${countPrice}</#if>">
	    <input type ="hidden" name="productName" value="${productName}">
	    <input type ="hidden"  id="productId"  name="productId" value="${productId}">
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
	    <input type ="hidden" id="protectionPeriodFlag" name="protectionPeriodFlag" value="${protectionPeriodFlag}">
	    <input type="hidden" id="relation1name" name="informationInsureds1.recognizeeAppntRelationName" value="${(informationInsureds1.recognizeeAppntRelationName)!}"/>
	    <input type="hidden" id="applicanTypeName" name="informationAppnt.applicantIdentityTypeName" value="${(informationAppnt.applicantIdentityTypeName)!}"/>
	    <input type="hidden" id="recognizeeIdentityTypeName" name="informationInsureds1.recognizeeIdentityTypeName" value="${(informationInsureds1.recognizeeIdentityTypeName)!}"/>
	    <input type="hidden" id="recognizeeSexName" name="informationInsureds1.recognizeeSexName" value="${(informationInsureds1.recognizeeSexName)!}"/>
	    <input type="hidden" id="applicantSexName" name="informationAppnt.applicantSexName" value="${(informationAppnt.applicantSexName)!}"/>
	    <input type ="hidden"  id ="insurTypeChild" name="insurTypeChild" value="${insurTypeChild}" />
	    <input type ="hidden"  id ="textAgeFlag" name="textAgeFlag" value="${textAgeFlag}" />
	    <input type ="hidden"  id ="amntNum" name="amntNum" value="${amntNum}" />
	    <input type ="hidden"  id ="brkRiskCode" name="brkRiskCode" value="${brkRiskCode}" />
	    <input type ="hidden"  id ="brkRiskName" name="brkRiskName" value="${brkRiskName}" />
	    <input type ="hidden"  id ="discount" name="discount" value="${discount}" />
	    <input type ="hidden"  id ="brkKindCode" name="brkKindCode" value="${brkKindCode}" />
	     <input type ="hidden"  id ="artLoginFlag" name="artLoginFlag" value="1" />
       <p>
               <span>姓名:</span> 
               <input type="text" id="applicantName" name="informationAppnt.applicantName" value="${(informationAppnt.applicantName)!}"  onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')" class="required"  maxlength=50/>
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
               <input id="applicantId" name="informationAppnt.applicantIdentityId" maxlength=64 type="text" value="${(informationAppnt.applicantIdentityId)!}" name="informationAppnt.applicantIdentityId" onblur="idcheck('applicantTypeId','applicantId','applicantBirthday','applicantSex');"  class="required isIdCardNo"/>
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
                   <input id="applicantBirthday" name="informationAppnt.applicantBirthday" type="text" onclick="WdatePicker({skin:'whyGreen',startDate:'1980-01-01'})"  value="${(informationAppnt.applicantBirthday)!}"  class="required applicantBirthday" onchange="validateBirthday();dateTrigger();"  />
                	<label class="requireField">*</label>
                	<font id="applicantBirthdayID" color="red"></font> 
                </p>
            <p>
               <span>手机号码:</span>
               <input type="text"  id = "applicantMobile"  name="informationAppnt.applicantMobile" value="${(informationAppnt.applicantMobile)!}"  class="required mobile" />
           	<label class="requireField">*</label>
			</p>
			<p>
            <span></span>
            <font color="#808080">请填写自己真实的号码，方便开心保为您提供优质服务。</font>
            </p>
            <p>
               <span>电子邮箱:</span>
               <input type="text" id = "applicantMail"  name="informationAppnt.applicantMail" value="${(informationAppnt.applicantMail)!}" class="required email"/>
               <label class="requireField">*</label>
           </p>
           <p>
           <span></span>
           <font color="#808080">提醒您！我们仅向该邮箱发送电子保单。并且在办理保单业务时，开心保网也只接收您通过该邮箱提交的材料。</font>
       </p>
           <p>
           <span>所在地区:</span>
           <select id="applicantArea1" name="informationAppnt.applicantArea1" onchange="getChildrenArea(this.value,'applicantArea2','-1');" style="width:125px;">
                   <option value="-1">--请选择--</option>
                   <#list areas as list>
                   		<option value="${list.get('id')}" <#if (informationAppnt.applicantArea1==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>
        			</#list>
              </select>
              <select id="applicantArea2" name="informationAppnt.applicantArea2" class="selectTest {selectTest:false, messages: {selectTest: '请选择地区'}}"  style="width:125px;">
                  <option value="-1">--请选择--</option> 
                  <#list appntAreas2 as list>
                      <option value="${list.get('id')}" <#if (informationAppnt.applicantArea2==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>
       			  </#list>
              </select>
    		      <label class="requireField">*</label>
        </p>
        <p>
        <span>联系地址:</span> 
        <input type="text" id="applicantAddress" maxlength=200 name="informationAppnt.applicantAddress" value="${(informationAppnt.applicantAddress)!}"/>
        <label class="requireField">*</label>
    </p>
    <p>
        <span>邮编:</span> 
        <input type="text" id="applicantZipCode" name="informationAppnt.applicantZipCode" value="${(informationAppnt.applicantZipCode)!}"/>
        <label class="requireField">*</label>
    </p>
      </div>
  </div>
  
  <div class="line_a">
     <div  class="line_at">被保人信息<img width="18" height="16" title="被保险人，就是指保险合同的保障对象。通常，被保险人或其继承人（被保险人身故且未指定受益人时）会享有保险金的申请权。 " src="${base}/template/shop/images/wen_a.png"/></div>
     <div class="form">
       <p>
           <span>与投保人关系:</span> 
           <select name="informationInsureds1.recognizeeAppntRelation"  id="relation1" class = "selectTest {selectTest:false, messages: {selectTest: '请选择投保人关系'}}" onchange="changeInformation(this.form,'insurancedOne');" style="width:125px;">
					<#list listRelation as list>
						<option value="${list.get('codeValue')}" <#if (informationInsureds1.recognizeeAppntRelation==list.get('codeValue'))>selected="selected"</#if>>
							${list.get("codeName")}
						</option>
					</#list>
			 </select>
			 <input type="hidden" id="relationFlag1" name="relationFlag1" value=""/>
			 <label class="requireField">*</label>
        </p>
        <div id="insurancedOne"  style="display:none;">
             <p>
                <span>姓名:</span> 
                <input type="text" id="recognizeeName1" name="informationInsureds1.recognizeeName" value="${(informationInsureds1.recognizeeName)!}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')" maxlength=50/>
            	 <label class="requireField">*</label>
             </p>
            <p>
               <span>证件类型:</span> 
               <select id="recognizeeTypeId1" name="informationInsureds1.recognizeeIdentityType" style="width:125px;" onchange="changeVerification('recognizeeTypeId1','recognizeeId1');">
					<#list certificateCode as list>
                    <option value="${list.get('codeValue')}" <#if (informationInsureds1.recognizeeIdentityType==list.get('codeValue'))>selected="selected"</#if>>${list.get("codeName")}</option>
                </#list>
					<label class="requireField">*</label>
				</select>
				<label class="requireField">*</label>	
            </p>
            <p>
                <span>证件号码:</span> 
                <input id="recognizeeId1" type="text" name="informationInsureds1.recognizeeIdentityId" value="${(informationInsureds1.recognizeeIdentityId)!}"  onblur="idcheck('recognizeeTypeId1','recognizeeId1','recognizeeBirthday1','recognizeeSex1');"/>
            	<label class="requireField">*</label>
            </p>
	             <p>
	             	<span>证件性别:</span> 
		             <select id="recognizeeSex1" name="informationInsureds1.recognizeeSex" style="width:125px;">
		             	<#list sexs as list>
		             		<option value="${list.get('codeValue')}" <#if (informationInsureds1.recognizeeSex==list.get('codeValue'))>selected="selected"</#if>>${list.get("codeName")}</option>
		             		</#list>
		             </select>
		             <label class="requireField">*</label>
	             </p>
	             <p>
	                 <span>出生日期:</span> 
	                 <input id="recognizeeBirthday1" name="informationInsureds1.recognizeeBirthday" type="text" onclick="WdatePicker({skin:'whyGreen',startDate:'1980-01-01'})" value="${informationInsureds1.recognizeeBirthday}" onchange="validateBirthday();dateTrigger();"  />
	            	 <label class="requireField">*</label>
	            	  <font id="recognizeeBirthdayID" color="red"></font> 
	             </p>
            <p>
                <span>手机号码:</span> 
                <input type="text" id="recognizeeMobile1" name="informationInsureds1.recognizeeMobile" value="${(informationInsureds1.recognizeeMobile)!}" />
            </p>
            <p>
                <span>电子邮箱:</span> 
                <input type="text" id="recognizeeMail1" name="informationInsureds1.recognizeeMail" value="${(informationInsureds1.recognizeeMail)!}" />
            	<label class="requireField">*</label>
            </p>
            <p>
            <span>所在地区:</span> 
              <select id="insuredAreaA1" name="informationInsureds1.recognizeeArea1" onchange="getChildrenArea(this.value,'insuredAreaA2','-1');" style="width:125px;">
                    <option value="-1">--请选择--</option>
                    <#list areas as list>
                    	<option value="${list.get('id')}" <#if (informationInsureds1.recognizeeArea1==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>
                    </#list>
               </select>
               <select id="insuredAreaA2" name="informationInsureds1.recognizeeArea2"  style="width:125px;">
                   <option value="-1">--请选择--</option>
                   <#list regAreas2 as list>
                       <option value="${list.get('id')}" <#if (informationInsureds1.recognizeeArea2==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>
                   </#list>
               </select>
               <label class="requireField">*</label>
         </p>
         <p>
         <span>联系地址:</span> 
         <input type="text" id="recognizeeAddress1" maxlength=200 name="informationInsureds1.recognizeeAddress" value="${(informationInsureds1.recognizeeAddress)!}"/>
         <label class="requireField">*</label>
     </p>
     <p>
         <span>邮编:</span> 
         <input type="text" id="recognizeeZipCode1" name="informationInsureds1.recognizeeZipCode" value="${(informationInsureds1.recognizeeZipCode)!}"/>
         <label class="requireField">*</label>
     </p>
        </div>
        <p>
        <span>职业:</span>
              <select name="informationInsureds1.occupation1" id="s1" onchange="getChildrenOPT(this.value,'s2','s3');" style="width:125px;_width:180px;" >
             	<option value="-1">--请选择--</option>
             	<#list opts as list>
             		<option value="${list.get('id')}" <#if (informationInsureds1.occupation1==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>	
        		</#list>
             </select> 
             <select name="informationInsureds1.occupation2" id="s2" onchange="getChildrenOPT(this.value,'s3');" style="width:125px;_width:200px;" >
             		<option value="-1">--请选择--</option>
             	<#list opts2 as list>
             		<option value="${list.get('id')}" <#if (informationInsureds1.occupation2==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>	
        		</#list>
             </select>
             <select name="informationInsureds1.occupation3" id="s3" style="width:125px;_width:200px;" class="selectTest {selectTest:false, messages: {selectTest: '请选择职业'}}">
             		<option value="-1">--请选择--</option>
             	<#list opts3 as list>
             		<option value="${list.get('id')}" <#if (informationInsureds1.occupation3==list.get('id'))>selected="selected"</#if>>${list.get("name")}</option>	
        		</#list>
             </select>
             <label class="requireField">*</label>
             <font id="occupID" color="red"></font>
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
 
  
 <input type="hidden" id="dutyFactor-plan" name="dutyFactor-plan" value="" />
  <input type="hidden" id="dutyFactorSize" name="dutyFactorSize" value="${dutyFactor.size()}" />
 
 <#if (planList.size()>0)!>
 <#assign index_ = 1 />
 <#list dutyFactor as list>
	 <input type="hidden" id="amnt${index_}" name="amnt${index_}" value="${list.fdAmntPremList.get(0).backUp1}">
	 <input type="hidden" id="amntDis${index_}" name="amntDis${index_}" value="${list.fdAmntPremList.get(0).amnt}">
	 <input type="hidden" id="dutyCode${index_}" name="dutyCode${index_}" value="${list.dutyCode}" />
	 <#assign index_=index_+1 />
 </#list>
 
 <#list planList as planList>
		 <div class="line_a" id="planList-${planList.factorValue}" style="display:none;">
		     <div class="line_at">保障内容信息</div>
		     <div>
		     <table id="mytable" cellspacing="1" class="datagrid3" style="TABLE-LAYOUT: fixed;word-wrap: break-word; word-break: break-all;">
		     <tr> 
		     <th  width="25%">保障项目</th> 
		     <th  width="25%">保额</th> 
		     <th>保障内容</th> 
		     </tr> 
		 		<caption> </caption> 
					<#assign index = 1 />
						<#list dutyFactor as list>
							<tr  style="background-color: #EFEFEF; " >
								<td style="word-break:break-all" width="260">
									<span>${list.dudtyFactorName} </span>
								</td>
								<td style="padding-left:100px" align="left">
								    <#if (list.fdAmntPremList.size()>0)!>
								    <#assign amt_flag = 0 />
							            <#list list.fdAmntPremList as list2>
							                <#if (planList.factorValue == list2.appFactorValue) || (list2.appFactorValue=='0000')  !>
							                	<span>${list2.amnt}</span>
							               	    <input type="hidden" id="amnt-${planList.factorValue}-${index}" name="amnt-${planList.factorValue}${index}" value="${list2.backUp1}">
							               	    <input type="hidden" id="amntDis-${planList.factorValue}-${index}" name="amntDis-${planList.factorValue}${index}" value="${list2.amnt}">
							                <#else>
							                	<#assign amt_flag = amt_flag + 1 />
							                </#if>
							            </#list>
							            <#if amt_flag == list.fdAmntPremList.size()>
							            	<span>-</span>
							            </#if>
								    </#if>
								</td>
								<td style="word-break:break-all" width="520">
								${list.define}
							</td>
								<input type="hidden" id="dutyCode${index}" name="dutyCode${index}" value="${list.dutyCode}" />
							</tr>
					<#assign index=index+1 />
						</#list>
				</table>
		</div>
			<br>
 	</div>
  </#list>
			
  <#else>
	  	<div class="line_a">
	     <div class="line_at">保障内容信息</div>
	     <div>
	     <table id="mytable" cellspacing="1" class="datagrid3" style="TABLE-LAYOUT: fixed;word-wrap: break-word; word-break: break-all;">
	     <tr> 
	     <th  width="25%">保障项目</th> 
	     <th  width="25%">保额</th> 
	     <th>保障内容</th> 
	     </tr> 
	 		<caption> </caption> 
				<#assign index = 1 />
					<#list dutyFactor as list>
						<tr  style="background-color: #EFEFEF; " >
							<td style="word-break:break-all" width="260">
								<span>${list.dudtyFactorName} </span>
							</td>
							<td style="padding-left:100px" align="left">
							    <#if (list.fdAmntPremList.size()>0)!>
							        <#if (list.fdAmntPremList.size()>1)>
									    <select onchange="changeAmnt('amnt${index}','amntDis${index}');">
											<#list list.fdAmntPremList as list1>
												<option value="${list1.backUp1}">
													<span>${list1.amnt}</span>
												</option>
											</#list>
										</select>
										<input type="hidden" id="amnt${index}" name="amnt${index}" value="${list.fdAmntPremList.get(0).backUp1}">
										<input type="hidden" id="amntDis${index}" name="amntDis${index}" value="${list.fdAmntPremList.get(0).amnt}">
									<#else>
							            <#list list.fdAmntPremList as list2>
							               <span>${list2.amnt}</span>
							                <input type="hidden" id="amnt${index}" name="amnt${index}" value="${list2.backUp1}">
							                <input type="hidden" id="amntDis${index}" name="amntDis${index}" value="${list2.amnt}">
							            </#list>
							         </#if>
							    </#if>
							</td>
							<td style="word-break:break-all" width="520">
							${list.define}
						</td>
							<input type="hidden" id="dutyCode${index}" name="dutyCode${index}" value="${list.dutyCode}" />
						</tr>
				<#assign index=index+1 />
					</#list>
			</table>
				</div>
				<br>
	 </div>
  </#if>
 
  
 <div class="line_a">
 <div class="pri">
 <center>
	 <span>
	 	现价:
	</span>
	 <font  size="4" color="red">
	 ￥	<span id = "priceTotle"><#if (information.protectionPeriod)??>${order.productTotalPrice}<#else>${price}</#if></span>
	 </font>
	 &nbsp;
	  <#if (discount) ? has_content >
		 <span>
		 原价:
		</span>
		 <font  size="4" color="red">
		 ￥	<span id = "discountPrice" style="text-decoration:line-through;">${countPrice}</span>
		 </font>
	 <#else>
	    <span id = "discountPrice" style="display:none;">${countPrice}</span>
	 </#if>
 </center>
 </div>
</div>
 <div class="syr2">
 <#if (loginFlag =="true")!>
 <input  id="comfirmToPay" class="butM dev_submitButton" onclick="temptorysave();" type="button" value="   暂存   " >
</#if>
 <input type="submit" value="  下一步  "  class="butM dev_submitButton" style="height=20px;width=100px;" >
 &nbsp; &nbsp; &nbsp; 

 </div>
 <div class="clear"></div>
   </form>
<script type="text/javascript">
function idcheck(idtype,idCode,birthdayId,sexId){
    str=document.getElementById(idCode).value;
    var idType = document.getElementById(idtype);
	var idType_index = idType.selectedIndex;          
	var idType_value = idType.options[idType_index].text;     
	if(idType_value.indexOf("身份证") != -1){
    if(str.length==15 || str.length==18){
    	if(str.length==18){
    		var sex;
    		if(str.substring(16,17)%2==0){   
    			sex = "0";   
            }else{   
            	sex = "1";   
            }   
    		var year = str.substring(6, 10);      
            var month = str.substring(10, 12);      
    		var day = str.substring(12, 14);
    		document.getElementById(birthdayId).value = year+"-"+month+"-"+day;
    		document.getElementById(sexId).value = sex;
    	}else{
    		var sex;
    		if(str.substring(14,15)%2==0){   
    			sex = "0";   
            }else{   
            	sex = "1";   
            }   
    		var year = "19"+str.substring(6, 8);      
            var month = str.substring(8, 10);      
    		var day = str.substring(10, 12);      
    		document.getElementById(birthdayId).value = year+"-"+month+"-"+day;
    		document.getElementById(sexId).value = sex;
    		}
    	}
		validateBirthday();
		dateTrigger();    	
    }   
}
</script>
 </div>
 <script type="text/javascript" src="${base}/template/common/js/json2.js"></script>
 <script type="text/javascript" src="${base}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript" src="${base}/template/shop/js/calendar.js"></script>
 <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
 <script type="text/javascript" src="${base}/wwwroot/kxb/js/productDuty.js"></script>
<script type="text/javascript">
   // 只有存在计划列表的时候才执行此方法 
 	<#if (planList.size()>0)!>
 		planChange();
    </#if>
	pubGetChildrenArea("${informationAppnt.applicantArea1}", "${informationAppnt.applicantArea2}", "${informationInsureds1.recognizeeArea1}", "${informationInsureds1.recognizeeArea2}");
</script>
</body>
</html>
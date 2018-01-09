<#if  (sdinformation.ensure)??>
<p>
					  <span class="span_t">保险期限：</span>
					  <span style="width:500px;">
		                
		                	${sdinformation.ensureDisplay}
		                	<input type="hidden" name="sdinformation.ensure" id = "Ensure" value="${sdinformation.ensure}" />
		                	<input type="hidden" name="sdinformation.ensureDisplay" id = "ensureDisplay" value="${sdinformation.ensureDisplay}" />
		                
	                  </span>
			  </p>
				  </#if><p>
               <span class="span_t">*投保人姓名：</span> 
               <input type="text" id="applicantName" 
<#if (isLcx =="0")>class="tap_name"</#if>
name="sdinformationAppnt.applicantName" verify="投保人姓名|notnull&UFO&LEN>2" 
onblur="verifyElement('投保人姓名|notnull&UFO&LEN>2',this.value,this.id);"
onfocus="verifyShowInf(this.value,this.id);"
value="${(sdinformationAppnt.applicantName)!}"   maxlength=60 />
               <label class="requireField"></label>
                 <font color="#E7AC59" style="display:none"> 
<#if (isLcx !="0")>
<i class="yz_mes_des">请填写与有效证件相符的姓名，以保障您的权益</i>
</#if>
<#if (isLcx =="0")>
<i class="yz_mes_des">理财险投保人必须与付款银行卡持卡人姓名一致</i>
</#if>
</font>
<#if (isLcx =="0")>
<em class="other_tip" style="display:none"> <i class="yz_mes_des">理财险投保人必须与付款银行卡持卡人姓名一致</i></em>
</#if>
</p><p class="idControl" style="display:none">
	                 <span  class="span_t">*性别：</span>
		                  <#list sexList as list>
		                     <label><input type="radio" name="sdinformationAppnt.applicantSex" value="${list.codeValue}" id="applicantSex" class="frome_radio" <#if (sdinformationAppnt.applicantSex==list.codeValue)>checked="checked"<#elseif (list.codeName=="男" && sdinformationAppnt.applicantSex==null ) >checked="checked"</#if>>${list.codeName}</label>
		                  </#list>
		             <label class="requireField"></label>
                </p><p class="idControl" style="display:none">
                   <span class="span_t">*出生日期：</span> 
                   <input id="applicantBirthday" name="sdinformationAppnt.applicantBirthday" type="text" value="${(sdinformationAppnt.applicantBirthday)!}" 
onchange="verifyElement('出生日期|NOTNULL&APPAGE',this.value,this.id)"
 verify="出生日期|notnull&APPAGE"  class="shop_day" />
                	<label class="requireField"></label>
                	<font id="applicantBirthdayID" color="red"></font> 
                </p><div class="pos-tb-p">
<p>
               <span class="span_t">*手机号码：</span>
               <input type="text" id="applicantMobile" 
<#if (isLcx =="0")> class="tap_name" </#if>name="sdinformationAppnt.applicantMobile" value="${(sdinformationAppnt.applicantMobile)!}"  
onfocus="verifyShowInf(this.value,this.id);" 
onblur="verifyElement('手机号码|MOBILENO',this.value,this.id)"
verify="手机号码|MOBILENO" maxlength=11 />
           	<label class="requireField"></label>
<font color="#E7AC59" style="display:none"> 
<#if (isLcx !="0")><i class="yz_mes_des">请填写正确的手机号</i></#if>
<#if (isLcx =="0")><i class="yz_mes_des">理财险投保人手机号必须真实，否则无法完成退保</i></#if>
</font>
<#if (isLcx =="0")>
<em class="other_tip" style="display:none"> <i class="yz_mes_des">理财险投保人手机号必须真实，否则无法完成退保</i></em></#if>
                   </p>
				   <#if (sdinformationAppnt.applicantMobile)??>
				   <label class="app_mobile" for="applicantMobile" style="display:none">
				   <#else>
				   <label class="app_mobile" for="applicantMobile" >
				   </#if>
                               用来接收保单生效通知</label> </div>
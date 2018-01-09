<div class="line_a ">
       <div  class="line_at cf"><div class="tbr_mes">被保人信息 <span class="tbr-b">（我要为ta买保险）</span></div>
       
</div>
     <div class="form tb-up-from"><div id="radiobox" class="radioboxs"><div style="display:none" id="lookOccuDiv"><div class="add_zy zy_shows"><label for="sel_zys">
    <input type="checkbox" id="sel_zys" />确认被保人属于可投保职业 </label><i onclick="showOccupations();">职业类别表（必看）</i> </div></div></div>
<#list 1..insuredCount as x>
<#if (loginFlag =="true")!>
				<#if recognizeeList? has_content>
					<div class="cy_user order-user">
				<#else>
					<div class="cy_user order-user" style="display:none;">
				</#if>
			<#else>
					<div class="cy_user order-user" style="display:none;">
			</#if>
<dl class="cy_dl_csf">
<dt class="cy_user_t" >一键添加常用被保人：</dt>
<dd class="cy_user_ch" >
					<#list recognizeeList as list>
							<label><input type="radio" name="quickrecognizee" onclick="quickQueryInsured(this,this.value,'_${x-1}');" value="${list.recognizeeName}">${list.recognizeeName}</label>
						</#list>
</dd>
</dl>
<div class="clear"></div>
</div>


<div id="insurance_${x-1}"><div><p> 
           <span class="span_t">*与投保人关系：</span> 
           <select name="sdinformationinsuredList[${x-1}].recognizeeAppntRelation"  id="recognizeeRelation_${x-1}" verify="与投保人关系|APPRELATION" onblur="verifyElement('所在地区|APPRELATION',this.value,this.id)" onchange="changeInformation(this.id,'insurance_${x-1}');" style="width:125px;">
<option value="-1">--请选择--</option>
					<#list relationList as list>
						<option value="${list.codeValue}" <#if (sdinformationinsuredList[x-1].recognizeeAppntRelation==list.codeValue)!>selected="selected"</#if>>
							${list.codeName}
						</option>
					</#list>
			 </select>
			 <input type="hidden" id="relationFlag_${x-1}" name="sdinformationinsuredList[${x-1}].isSelf" value="${(sdinformationinsuredList[x-1].isSelf)!}"/>
<input type="hidden" id="sdinformationinsured_${x-1}" name="sdinformationinsuredList[${x-1}].id" value="${(sdinformationinsuredList[x-1].id)!}"/>
			 <label class="requireField"></label>
         </p><p>
                <span class="span_t">*被保人姓名：</span> 
                <input type="text" id="recognizeeName_${x-1}" name="sdinformationinsuredList[${x-1}].recognizeeName" value="${(sdinformationinsuredList[x-1].recognizeeName)!}"
onblur="verifyElement('被保人姓名|NOTNULL&UFO&LEN>2&CHANDEH',this.value,this.id)"
maxlength=60 verify="被保人姓名|NOTNULL&UFO&LEN>2&CHANDEH"/>
            	 <label class="requireField"></label>
             </p><p>
<span class="span_t">*英文名或拼音：</span> 
	 	             	 <input type="text" id="recognizeeEnName_${x-1}"  name="sdinformationinsuredList[${x-1}].recognizeeEnName" value="${(sdinformationinsuredList[x-1].recognizeeEnName)!}" onblur="verifyElement('英文名或拼音|NOTNULL&UFO&ENG&LEN>2',this.value,this.id)" 
onfocus="verifyShowInf(this.value,this.id);" 
maxlength=50 verify="英文名或拼音|NOTNULL&UFO&ENG&LEN>2"/>
<label class="requireField"></label>
<font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请填写与您证件上相同的汉语拼音或英文名</i></font>
</p><div class="clearfix">
  <p class="from_table" id="testid111">
               <span  class="span_t">*证件号码：</span>
               <select id="recognizeeTypeId_${x-1}" class="typeoption"  name="sdinformationinsuredList[${x-1}].recognizeeIdentityType" style="width:105px;" onchange="changeVerification('recognizeeTypeId_${x-1}','recognizeeId_${x-1}');">
					<#list certificateList as list>
	                    <option  id="${list.flagType}"     value="${list.codeValue}" <#if (sdinformationinsuredList[x-1].recognizeeIdentityType==list.codeValue)!>selected="selected"</#if>>${list.codeName}</option>
	                </#list>
				</select>
							 </p>
				 <p class="from_table2">	 
               <input id="recognizeeId_${x-1}" type="text" name="sdinformationinsuredList[${x-1}].recognizeeIdentityId" value="${(sdinformationinsuredList[x-1].recognizeeIdentityId)!}"  
verify="证件号码|NOTNULL&BIRAGE"
onblur="idcheck('recognizeeTypeId_${x-1}','recognizeeId_${x-1}','recognizeeBirthday_${x-1}','recognizeeSex_${x-1}','sdinformation.InsuranceCompany');" />
            	<label class="requireField"></label>
</p>
 </div><p class="idControl" style="display:none">
	<span class="span_t">*性别：</span>
	<#list sexList as list>
		<label>
			<input type="radio" name="sdinformationinsuredList[${x-1}].recognizeeSex" value="${list.codeValue}" id="recognizeeSex_${x-1}" class="frome_radio" onclick="dateTriggerFront('recognizeeBirthday_${x-1}');"
			<#if (sdinformationinsuredList[x-1].recognizeeSex==list.codeValue)!>checked="checked"<#elseif (list.codeName=="男" && sdinformationinsuredList[x-1]==null ) !>checked="checked"</#if>>${list.codeName}</label>
		             	</#list>
		             <label class="requireField"></label>
	             </p><p  class="idControl" style="display:none">
	                 <span class="span_t">*出生日期：</span> 
	                 <input id="recognizeeBirthday_${x-1}" name="sdinformationinsuredList[${x-1}].recognizeeBirthday"  type="text"  value="${(sdinformationinsuredList[x-1].recognizeeBirthday)!}" onBlur="verifyElement('出生日期|NOTNULL&AGE',this.value,this.id);dateTriggerFront('recognizeeBirthday_${x-1}');"
verify="出生日期|NOTNULL&AGE" class="shop_day" />
	            	 <label class="requireField"></label>
<input type="hidden" id="recognizeePrem_${x-1}" name = "sdinformationinsuredList[${x-1}].recognizeePrem" value="${(sdinformationinsuredList[x-1].recognizeePrem)!}"/>
<input type="hidden" id="recognizeeTotalPrem_${x-1}" name = "sdinformationinsuredList[${x-1}].recognizeeTotalPrem" value="${(sdinformationinsuredList[x-1].recognizeeTotalPrem)!}"/>
<input type="hidden" id="discountPrice_${x-1}" name = "sdinformationinsuredList[${x-1}].discountPrice" value="${(sdinformationinsuredList[x-1].discountPrice)!}"/>
	            	 <em class="birthday-shop-money">价格:<em  id="recognizeeBirthdayID_${x-1}">${(sdinformationinsuredList[x-1].discountPrice)!}</em>元 输入出生日期后试算</em> 
	             </p><p>
                <span class="span_t">*手机号码：</span> 
                <input type="text" id="recognizeeMobile_${x-1}" name="sdinformationinsuredList[${x-1}].recognizeeMobile" value="${(sdinformationinsuredList[x-1].recognizeeMobile)!}" 
onblur="verifyElement('手机号码|MOBILENO',this.value,this.id)" 
onfocus="verifyShowInf(this.value,this.id);"  
verify="手机号码|MOBILENO" maxlength=11 />
                <label class="requireField"></label>
<font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请填写正确的手机号</i></font>
            </p></div><#if (limitCount>1)>
                 <p>
	             	<span class="span_t">*购买份数：</span> 
		             <select id="recognizeeMul_${x-1}" name="sdinformationinsuredList[${x-1}].recognizeeMul" onchange="changePremByCont(this.id,this.value);" style="width:125px;">
		             	 <#list 1..limitCount as k>
		             		<option value="${k}" <#if (sdinformationinsuredList[x-1].recognizeeMul==k)!>selected="selected"</#if>>${k}份</option>
		             	</#list>
		             </select>
		             <label class="requireField"></label>
	             </p>
	        </#if></#list>	
</div>
</div>
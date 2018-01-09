<div class="line_a ">
       <div  class="line_at cf"><div class="tbr_mes">被保人信息 <span class="tbr-b">（我要为ta买保险）</span></div>
       
</div>
     <div class="form tb-up-from"><#list 1..insuredCount as x>
		 <div id="insurance_${x-1}"><#list relationList as list>
<div class="radioboxs">
<p> 
           <span class="span_t">*与投保人关系：</span> 
           <span>${list.codeName}</span>
<input type="hidden" id="recognizeeRelation_${x-1}" name="sdinformationinsured.recognizeeAppntRelation" value="${list.codeValue}"/>
<input type="hidden" id="sdinformationinsured_${x-1}" name="sdinformationinsured.id" value="${(sdinformationinsured.id)!}"/>
			 <input type="hidden" id="relationFlag_${x-1}" name="sdinformationinsured.isSelf" value="Y"/>
 <input type="hidden" id="relationIsSelf" name="relationIsSelf" value="Y"/>
         </p>
</div>
</#list><#if (limitCount>1)>
                 <p>
	             	<span class="span_t">*购买份数：</span> 
		             <select id="recognizeeMul" name="sdinformationinsured.recognizeeMul" onchange="changePremByCont(this.id,this.value);" style="width:125px;">
		             	 <#list 1..limitCount as k>
		             		<option value="${k}" <#if (sdinformationinsured.recognizeeMul==k)!>selected="selected"</#if>>${k}份</option>
		             	</#list>
		             </select>
		             <label class="requireField"></label>
	             </p>
</#if></#list>	
</div>
</div>
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


<div id="insurance_${x-1}"><div class="br_box clearfix"><div><p>
<span class="span_t">*所在地区：</span> 
					    <select id="recognizeeArea1" name="sdinformationinsured.recognizeeArea1" onchange="getChildrenArea(this.value,'recognizeeArea2','-1',this);" style="width:125px;">
					            <option value="-1">--请选择--</option>
					            <#list areaList as list>
					            	<option value="${list.id}" <#if (sdinformationinsured.recognizeeArea1==list.id)!>selected="selected"</#if>>${list.name}</option>
					            </#list>
					     </select>
					     <select id="recognizeeArea2" name="sdinformationinsured.recognizeeArea2"  style="width:125px;" verify="所在地区|NOTNULL" onblur="verifyElement('地区|AreaFormat',this.value,this.id)">
					           <option value="-1">--请选择--</option>
					     </select>
<input type="text" id="recognizeeAddress" maxlength=200 name="sdinformationinsured.recognizeeAddress" value="${(sdinformationinsured.recognizeeAddress)!}" 
onblur="verifyElement('联系地址|NOTNULL&ADDRESS',this.value,this.id)"
verify="联系地址|NOTNULL&ADDRESS" />
<label class="requireField"></label>
		            </p><p>
 <span class="span_t">*联系地址：</span> 
<input type="text" id="recognizeeAddress" maxlength=200 name="sdinformationinsured.recognizeeAddress" value="${(sdinformationinsured.recognizeeAddress)!}" 
onblur="verifyElement('联系地址|NOTNULL&ADDRESS',this.value,this.id)"
verify="联系地址|NOTNULL&ADRESS" />
			            <label class="requireField"></label>
			        </p><p>
<span class="span_t">*国籍：</span>
	                <select id="recognizeenationality" name="sdinformationinsured.nationality" style="width:125px;">
	                  <#list nationalityList as list>
	                      <option value="${list.codeValue}" <#if (sdinformationinsured.nationality==list.codeValue)!>selected="selected"</#if>>${list.codeName}</option>
	                  </#list>
	                </select>
	             <label class="requireField"></label>
	    	</p><p>
<span class="span_t">*身高：</span> 
 <input type="text" id="recognizeeheight" name="sdinformationinsured.height" value="${(sdinformationinsured.height)!}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength=3 
onblur="verifyElement('身高|NOTNULL',this.value,this.id)"
verify="身高|NOTNULL" />(厘米)
<label class="requireField"></label>
</p>
<p>
<span>体重:</span> 
<input type="text" id="recognizeeweight" name="sdinformationinsured.weight" value="${(sdinformationinsured.weight)!}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" maxlength=7
onblur="verifyElement('体重|NOTNULL',this.value,this.id)"
 verify="体重|NOTNULL"/>(公斤 )
 <label class="requireField"></label>
		     	</p><p>
<span class="span_t">*已购身故保额：</span> 
		        	 <input type="text" id="recognizeehaveBuy" name="sdinformationinsured.haveBuy" value="${(sdinformationinsured.haveBuy)!}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" maxlength=10
onblur="verifyElement('已身故保额|NOTNULL',this.value,this.id)"
  verify="已购身故保额|NOTNULL"/>(万元 )
<label class="requireField"></label>
<font color="#E7AC59" style="display:none"> <i class="yz_mes_des">被保险人已经或正在购买其他保险公司的身故保险金额。</i></font>
	     	</p><p>
	        <span class="span_t">*航班号：</span> 
	        <input type="text" id="recognizeeflightNo" maxlength=200 name="sdinformationinsured.flightNo" value="${(sdinformationinsured.flightNo)!}" 
onblur="verifyElement('航班号|NOTNULL',this.value,this.id)"
verify="航班号|NOTNULL"/>
	        <label class="requireField"></label>
	    </p><p>
<span class="span_t">*起飞时间：</span> 
	        <input type="text" id="recognizeeflightTime" maxlength=200 name="sdinformationinsured.flightTime" class="shop_day" value="${(sdinformationinsured.flightTime)!}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-{%d+${startPeriod}} 00:00:00',maxDate:'%y-%M-{%d+${startPeriod}+${endPeriod}} 23:59:59'});"
onblur="verifyElement('起飞时间|NOTNULL&FIGHTTIME',this.value,this.id)"
verify="起飞时间|NOTNULL&FIGHTTIME"/>
	        <label class="requireField"></label>
	    </p><p>
<span style="line-height:15px;" class="span_t">*留学学校或境外工作公司：</span>
	        <input type="text" id="recognizeeschoolOrCompany" name="sdinformationinsured.schoolOrCompany" value="${(sdinformationinsured.schoolOrCompany)!}" 
onblur="verifyElement('留学学校或境外工作司|NOTNULL',this.value,this.id)"
verify="留学学校或境外工作公司|NOTNULL"/>
	    	<label class="requireField"></label>
	     </p><p>
	        <span class="span_t">*境外工作职业：</span>
	        <input type="text" id="recognizeeoverseasOccupation" name="sdinformationinsured.overseasOccupation" value="${(sdinformationinsured.overseasOccupation)!}" 
onblur="verifyElement('境外工作职业|NOTNULL',this.value,this.id)"
verify="境外工作职业|NOTNULL"/>
	    	<label class="requireField"></label>
	     </p><p>
            <span class="span_t">*驾校名称：</span>
            <input type="text" id = "recognizeedriverSchoolName" name="sdinformationinsured.driverSchoolName" value="${(sdinformationinsured.driverSchoolName)!}" maxlength=50
onblur="verifyElement('驾校名称|NOTNULL',this.value,this.id)"
 verify="驾校名称|NOTNULL"/>
<label class="requireField"></label>
        </p><p>
	        <span  class="span_t">*学员编号：</span>
	        <input type="text" id = "recognizeedriverNo" name="sdinformationinsured.driverNo" value="${(sdinformationinsured.driverNo)!}" maxlength=50 
onblur="verifyElement('学员编号|NOTNULL',this.value,this.id)"
verify="学员编号|NOTNULL"/>
<label class="requireField"></label>
        </p><p>
	        <span  class="span_t">*起飞地点：</span> 
	        <input type="text" id="recognizeeflightLocation" maxlength=20 name="sdinformationinsured.flightLocation" value="${(sdinformationinsured.flightLocation)!}" 
onblur="verifyElement('起飞地点|NOTNULL&LEN<=60',this.value,this.id)"
verify="起飞地点|NOTNULL"/>
	        <label class="requireField"></label>
	    </p><p>
<span  class="span_t">*职业：</span>
 <select name="sdinformationinsured.recognizeeOccupation1" id="recognizeeOccupation1" onchange="getChildrenOPT(this.value,'recognizeeOccupation3','-1',this,'3');" style="width:125px;" verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)">
			             	<option value="-1">--请选择--</option>
			             	<#list occupationList as list>
			             		<option value="${list.id}" <#if (sdinformationinsured.recognizeeOccupation1==list.id)!>selected="selected"</#if>>${list.name}</option>	
			        		</#list>
			             </select> 
			             <select name="sdinformationinsured.recognizeeOccupation3" id="recognizeeOccupation3" style="width:125px;" verify="职业|OccuFormat" onblur="verifyElement('所在地区|OccuFormat',this.value,this.id)">
			             		<option value="-1">--请选择--</option>
			             </select>
<label class="requireField"></label>
</p><p>
		     		<span class="span_t">*旅游目的地：</span> 
		     		<input type="text" id="recognizeedestinationCountryText" name="sdinformationinsured.destinationCountryText" value="${(sdinformationinsured.destinationCountryText)!}" 
onfocus="verifyShowInf(this.value,this.id);" 
onblur="verifyElement('旅游目的地|NOTNULL',this.value,this.id)"
verify="旅游目的地|NOTNULL"/>
<label class="requireField"></label>
<font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请将签证国写在第一位，如法国（签证国）、德国、瑞典等。 </i></font>
</p><p>
           <span class="span_t">*旅游目的地：</span>
           <select id="recognizeedestinationCountry" name="sdinformationinsured.destinationCountry">
               <#if (listCountryCode!=null && listCountryCode.size()>0)>
                    <#list listCountryCode as list>
                         <option value="${list.codeValue}" <#if (sdinformationinsured.destinationCountry==list.codeValue)>selected="selected"</#if>>${list.codeName}</option>
                    </#list>
               </#if>
           </select>
        </p>
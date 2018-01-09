<div  class="line_at">房屋信息<img width="18" height="16" title="" src="${base}/wwwroot/kxb/style/shop/images/wen_a.png"/>
	     </div> 
     <div class="form"><#list 1..insuredCount as x>
<div id="insurance_${x-1}"><p> 
           <span class="span_t">*房屋所有者：</span> 
          <select id="propertyToRecognizee_${x-1}" name="sdinformationpropertyList[${x-1}].propertyToRecognizee">
	          <#list propertyToRecognizeeList as list>
		      	<option value="${list.id}" <#if (sdinformationpropertyList[x-1].propertyToRecognizee==list.id)!>selected="selected"</#if>>${list.codeName}</option>
		      </#list>
    	 </select>
           <label class="requireField"></label>
         </p><p>
                <span class="span_t">*房龄：</span> 
                <select id="hourseAge_${x-1}" name="sdinformationpropertyList[${x-1}].hourseAge" verify="房龄|HOURSEAGE" onblur="verifyElement('房龄|HOURSEAGE',this.value,this.id)">
		     	 	<option value="-1">--请选择--</option>
		     	 	<#list hourseAgeList as list>
			      		<option value="${list.id}" <#if (sdinformationpropertyList[x-1].hourseAge==list.id)!>selected="selected"</#if>>${list.codeName}</option>
		      		</#list>
		     	 </select>
            	 <label class="requireField"></label>
         </p><p>
		         <span class="span_t">*房屋类型：</span> 
		     	 <select id="hourseType_${x-1}" name="sdinformationpropertyList[${x-1}].hourseType" verify="房屋类型|HOURSETYPE" onblur="verifyElement('房屋类型|HOURSETYPE',this.value,this.id)">
		     	 	<option value="-1">--请选择--</option>
		     	 	<#list hourseTypeList as list>
			      		<option value="${list.id}" <#if (sdinformationpropertyList[x-1].hourseType==list.id)!>selected="selected"</#if>>${list.codeName}</option>
		      		</#list>
		     	 </select>
		         <label class="requireField"></label>
		  </p><p>
		         <span class="span_t">*房屋地址：</span> 
		         <select id="propertyArea1_${x-1}" name="sdinformationpropertyList[${x-1}].propertyArea1" onchange="getChildrenArea(this.value,'propertyArea2_${x-1}','-1',this);" style="width:100px;" >
		            <option value="-1">--请选择--</option>
		            <#list areaList as list>
		            	<option value="${list.id}" <#if (sdinformationpropertyList[x-1].propertyArea1==list.id)!>selected="selected"</#if>>${list.name}</option>
		            </#list>
			     </select>
			     <select id="propertyArea2_${x-1}" name="sdinformationpropertyList[${x-1}].propertyArea2"  style="width:100px;" verify="所在地区|AreaFormat" onblur="verifyElement('所在地区|AreaFormat',this.value,this.id)" onchange="getChildrenArea(this.value,'propertyArea3_${x-1}','-1',this);">
			           <option value="-1">--请选择--</option>
			     </select>
				 <select id="propertyArea3_${x-1}" name="sdinformationpropertyList[${x-1}].propertyArea3"  style="width:100px;" verify="所在地区|AreaFormat" onblur="verifyElement('所在地区|AreaFormat',this.value,this.id)">
			           <option value="-1">--请选择--</option>
			     </select>
			     <input type="text" id="propertyAdress_${x-1}" maxlength=200 name="sdinformationpropertyList[${x-1}].propertyAdress"  value="${(sdinformationpropertyList[x-1].propertyAdress)!}"  
onfocus="verifyShowInf(this.value,this.id);"  
onblur="verifyElement('房屋地址|NOTNULL&ADDRESS',this.value,this.id)"  verify="房屋地址|NOTNULL&ADDRESS"/>
			     <label class="requireField"></label>
			     <font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请正确填写房屋地址。</i></font>
		  </p><p>
	          <span class="span_t">*房屋所在地邮编：</span> 
	          <input type="text" id="propertyZip_${x-1}" name="sdinformationpropertyList[${x-1}].propertyZip" value="${(sdinformationpropertyList[x-1].propertyZip)!}" 
onfocus="verifyShowInf(this.value,this.id);"  maxlength=6 
onblur="verifyElement('邮编|NOTNULL&ZIPCODE',this.value,this.id)" verify="邮编|NOTNULL&ZIPCODE"/>
	          <label class="requireField"></label>
         </p></div>
	    </#list>	
</div>
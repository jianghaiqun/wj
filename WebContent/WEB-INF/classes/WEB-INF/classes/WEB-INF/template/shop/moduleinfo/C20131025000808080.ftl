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


<div id="insurance_${x-1}"><p> 
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
         </p><div><p>
                <span class="span_t">*被保人姓名：</span> 
                <input type="text" id="recognizeeName_${x-1}" name="sdinformationinsuredList[${x-1}].recognizeeName" value="${(sdinformationinsuredList[x-1].recognizeeName)!}"
onblur="verifyElement('被保人姓名|NOTNULL&UFO&LEN>2&CHANDEH',this.value,this.id)"
maxlength=60 verify="被保人姓名|NOTNULL&UFO&LEN>2&CHANDEH"/>
            	 <label class="requireField"></label>
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
            </p><p>
                <span class="span_t">*电子邮箱：</span> 
                <input type="text" id="recognizeeMail_${x-1}" name="sdinformationinsuredList[${x-1}].recognizeeMail" value="${(sdinformationinsuredList[x-1].recognizeeMail)!}" 
onblur="verifyElement('电子邮箱|NOTNULL&EMAIL',this.value,this.id)"
verify="电子邮箱|NOTNULL&EMAIL"/>
            	<label class="requireField"></label>
            </p><p>
<span class="span_t">*所在地区：</span> 
					    <select id="recognizeeArea1_${x-1}" name="sdinformationinsuredList[${x-1}].recognizeeArea1" onchange="getChildrenArea(this.value,'recognizeeArea2_${x-1}','-1',this);" style="width:125px;">
					            <option value="-1">--请选择--</option>
					            <#list areaList as list>
					            	<option value="${list.id}" <#if (sdinformationinsuredList[x-1].recognizeeArea1==list.id)!>selected="selected"</#if>>${list.name}</option>
					            </#list>
					     </select>
					     <select id="recognizeeArea2_${x-1}" name="sdinformationinsuredList[${x-1}].recognizeeArea2"  style="width:125px;" verify="所在地区|AreaFormat" onblur="verifyElement('所在地区|AreaFormat',this.value,this.id)">
					           <option value="-1">--请选择--</option>
					     </select>
<input type="text" id="recognizeeAddress_${x-1}" maxlength=200 name="sdinformationinsuredList[${x-1}].recognizeeAddress" value="${(sdinformationinsuredList[x-1].recognizeeAddress)!}" 
onblur="verifyElement('联系地址|NOTNULL&ADDRESS',this.value,this.id)"
verify="联系地址|NOTNULL&ADDRESS"/>			<label class="requireField"></label>
		            </p><#if (limitCount>1)>
                 <p>
	             	<span class="span_t">*购买份数：</span> 
		             <select id="recognizeeMul_${x-1}" name="sdinformationinsuredList[${x-1}].recognizeeMul" onchange="changePremByCont(this.id,this.value);" style="width:125px;">
		             	 <#list 1..limitCount as k>
		             		<option value="${k}" <#if (sdinformationinsuredList[x-1].recognizeeMul==k)!>selected="selected"</#if>>${k}份</option>
		             	</#list>
		             </select>
		             <label class="requireField"></label>
	             </p>
	        </#if></div><p>
        <span class="span_t">*职业：</span>
              <select name="sdinformationinsuredList[${x-1}].recognizeeOccupation1" id="recognizeeOccupation1_${x-1}" onchange="getChildrenOPT(this.value,'recognizeeOccupation2_${x-1}','recognizeeOccupation3_${x-1}',null,'2');" style="width:125px;_width:160px;" verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)">
             	<option value="-1">--请选择--</option>
             	<#list occupationList as list>
             		<option value="${list.id}" <#if (sdinformationinsuredList[x-1].recognizeeOccupation1==list.id)!>selected="selected"</#if>>${list.name}</option>	
        		</#list>
             </select> 
             <select name="sdinformationinsuredList[${x-1}].recognizeeOccupation2" id="recognizeeOccupation2_${x-1}" onchange="getChildrenOPT(this.value,'recognizeeOccupation3_${x-1}',null,null,'3');" style="width:125px;_width:237px;" verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)">
             		<option value="-1">--请选择--</option>
             </select>
             <select name="sdinformationinsuredList[${x-1}].recognizeeOccupation3" id="recognizeeOccupation3_${x-1}" style="width:125px;_width:237px;" verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)">
             		<option value="-1">--请选择--</option>
             </select>
             <label class="requireField"></label>
　　</p></#list>	
</div>
</div><div class="line_a">
       <div  class="line_at">旅游目的地</div>
       <div class="form dq_pos">
              <span class="span_t">
          <i class="red">*</i>
          旅游目的地：
        </span>
         <div class="add_travemdd f_mi" id="trave_dss" >
           + 选择目的地
         </div>
         <input type="hidden" id="trave_id" value="${sdinformationinsured.destinationCountry}" />
         <input type="hidden" id="trave_name" value="${sdinformationinsured.destinationCountryText}" />
         <div class="clear"></div>
         <div id="mdd_con"></div>                
 
<div class="city_sele jqmID1" id="flow_md" style=" display: none;">
       <div class="xz_dq" >            
              <div class="wrap_gj_sx0">
                     <span >所有国家和地区</span>
                     <div class="search_city">
                       <input type="text" id="key_word" class="input_test" value="请输入关键字" />
                       <span class="search_fsfbtn"></span>
                     </div>
              </div>
              <div class="wrap_gj_sx">按首字母查找：<a href="javascript:void(0)" id="ALL" class="selected" onclick="setTabIndex(this, 'wrap_gj_sx');">全部</a><#list mapCountryCode?keys as s><#if s?length==1><a href="javascript:void(0)" id="${s}" onclick="setTabIndex(this, 'wrap_gj_sx');">${s}</a></#if></#list>
              </div>
     <span class="css_Country_span"> 最多可以选择10个国家</span>        <ul class="css_Country_ul"  id="index_Country_Set">                  
                    <#list mapCountryCode?keys as s>                      
                            <#assign alist=mapCountryCode[s] />                        
                        <#list alist as list>                                      
                               <#assign countryText = ""/>                                 
                               <li class="css_Country_${s}" ><input type="checkbox" name="DestinationCountry" data-name="${list.codeName}" id="${list.codeValue}" onclick="checkSchenGen(this)" value="${list.codeValue}"  <#list detisnateList as ss><#assign countryText = countryText+" "+ss+" "/><#if (ss==list.codeName)!>checked</#if></#list> /><label forCode="${list.codeValue}">${list.codeName}[${list.codeEnName}]</label></li>
                        </#list>
                     </#list>
              </ul>
              <div class="sg_qzcity">
                <span class="sgall"></span><em class="sg_tip">*选择申根国家，会为您投保全部申根国成员国家</em>
                <div class="yx_ctiy">
                  <span class="yx_city_span" style="display:none">已选国家：</span><ul class="yx_ctiy_con">
                  </ul>
                </div>
              </div>
            </div>
          <div class="clear"></div>
          </div>

        </div>
      </div>
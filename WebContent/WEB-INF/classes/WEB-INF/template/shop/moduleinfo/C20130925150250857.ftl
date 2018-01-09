<div class="line_a ">
       <div  class="line_at cf"><div class="tbr_mes">被保人信息 <span class="tbr-b">（我要为ta买保险）</span></div>
       
</div>
     <div class="form tb-up-from"><div id="radiobox" class="radioboxs"><div style="display:none" id="lookOccuDiv"><div class="add_zy zy_shows"><label for="sel_zys">
    <input type="checkbox" id="sel_zys" />确认被保人属于可投保职业 </label><a href="javascript:;" onclick="showOccupations();">职业类别表（必看）</a> </div></div></div>
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
onblur="verifyElement('被保人姓名|NOTNULL&UFO&LEN>2',this.value,this.id)"
maxlength=60 verify="被保人姓名|NOTNULL&UFO&LEN>2"/>
            	 <label class="requireField"></label>
             </p><div class="clearfix">
  <p class="from_table" id="testid111">
               <span  class="span_t">*证件号码：</span>
               <select id="recognizeeTypeId_${x-1}" classs="typeoption"  name="sdinformationinsuredList[${x-1}].recognizeeIdentityType" style="width:105px;" onchange="changeVerification('recognizeeTypeId_${x-1}','recognizeeId_${x-1}');">
					<#list certificateList as list>
	                    <option  id="${list.flagType}"     value="${list.codeValue}" <#if (sdinformationinsuredList[x-1].recognizeeIdentityType==list.codeValue)!>selected="selected"</#if>>${list.codeName}</option>
	                </#list>
				</select>
							 </p>
				 <p class="from_table2">	 
               <input id="recognizeeId_${x-1}" type="text" name="sdinformationinsuredList[${x-1}].recognizeeIdentityId" value="${(sdinformationinsuredList[x-1].recognizeeIdentityId)!}"  
verify="证件号码|NOTNULL"
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
	        <span style="line-height:15px;" class="span_t">*留学学校或境外工作公司：</span>
	        <input type="text" id="recognizeeschoolOrCompany_${x-1}" name="sdinformationinsuredList[${x-1}].schoolOrCompany" value="${(sdinformationinsuredList[x-1].schoolOrCompany)!}" 
onblur="verifyElement('留学学校或境外工作公司|NOTNULL',this.value,this.id)"
verify="留学学校或境外工作公司|NOTNULL"/>
	    	<label class="requireField"></label>
	     </p><p>
	        <span class="span_t">*境外工作职业：</span>
	        <input type="text" id="recognizeeoverseasOccupation_${x-1}" name="sdinformationinsuredList[${x-1}].overseasOccupation" value="${(sdinformationinsuredList[x-1].overseasOccupation)!}" 
onblur="verifyElement('境外工作职业|NOTNULL',this.value,this.id)"
verify="境外工作职业|NOTNULL"/>
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
           +&nbsp;选择目的地
         </div>
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
                               <li class="css_Country_${s}" ><input type="checkbox" name="DestinationCountry" dataname="${list.codeName}" id="${list.codeValue}" onclick="checkSchenGen(this)" value="${list.codeValue}"  <#list detisnateList as ss><#assign countryText = countryText+" "+ss+" "/><#if (ss==list.codeName)!>checked</#if></#list> /><label forCode="${list.codeValue}">${list.codeName}</label></li>
                        </#list>
                     </#list>
              </ul>
              <div class="sg_qzcity">
                <span class="sgall"><input type="checkbox" id="DZ0089"  dataname="申根国家" name="DestinationCountry" onclick="checkSchenGen(this)" value="DZ0089"   /><label forCode="DZ0086">申根国家</label></span><em class="sg_tip">*选择申根国家，会为您投保全部申根国成员国家</em>
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
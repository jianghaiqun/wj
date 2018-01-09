<div class="line_a ">
       <div  class="line_at cf"><div class="tbr_mes">被保人信息 <span class="tbr-b">（我要为ta买保险）</span></div>
       
</div>
     <div class="form tb-up-from"><DIV id="radiobox" class="radioboxs">
	<span class="span_t">被保人：</span>
             <DIV style="display:none" id="lookOccuDiv"><DIV class="add_zy zy_shows"><label FOR="sel_zys">
    <input TYPE="checkbox" id="sel_zys" />确认被保人属于可投保职业 </label><i onclick="showOccupations();">职业类别表（必看）</i> </DIV>
            <DIV class="clear zy_shows"></DIV>
            <span class="span_t zy_shows"></span></DIV>
             <label FOR="rid_me"> <input   TYPE="radio" onclick="radioShow();"  VALUE="1" NAME="myradio" id="rid_me"  <#if (recognizeeOperate=='1')>checked="checked"</#if>>本人</label>
             <label FOR="rid_orther"><input   TYPE="radio" onclick="radioShow();"  VALUE="2" NAME="myradio" id="rid_orther"   <#if (recognizeeOperate=='2')>checked="checked"</#if>>其他被保险人<em>（可包括本人）</em></label>
 
</DIV>  
<DIV id="hidd_insuredInfo" style="display:none"></DIV>        
<input TYPE="hidden" id="mulInsuredFlag" NAME="mulInsuredFlag" VALUE="${mulInsuredFlag?default('rid_me')}"/>
<DIV class="bbr_des" id="from_tab"><DIV class="br_box clearfix"><#if (limitCount>1)>
                 <p>
	             	<span class="span_t">*购买份数：</span> 
		             <select id="recognizeeMul" name="sdinformationinsured.recognizeeMul" onchange="changePremByCont(this.id,this.value);" style="width:125px;">
		             	 <#list 1..limitCount as k>
		             		<option value="${k}" <#if (sdinformationinsured.recognizeeMul==k)!>selected="selected"</#if>>${k}份</option>
		             	</#list>
		             </select>
		             <label class="requireField"></label>
	             </p>
</#if><p>
        <span class="span_t">*职业：</span>
              <select name="sdinformationinsured.recognizeeOccupation1" id="recognizeeOccupation1" onchange="getChildrenOPT(this.value,'recognizeeOccupation2','recognizeeOccupation3',null,'2');" style="width:125px;_width:160px;" verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)">
             	<option value="-1">--请选择--</option>
             	<#list occupationList as list>
             		<option value="${list.id}" <#if (sdinformationinsured.recognizeeOccupation1==list.id)!>selected="selected"</#if>>${list.name}</option>	
        		</#list>
             </select> 
             <select name="sdinformationinsured.recognizeeOccupation2" id="recognizeeOccupation2" onchange="getChildrenOPT(this.value,'recognizeeOccupation3',null,null,'3');" style="width:125px;_width:237px;" verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)">
             		<option value="-1">--请选择--</option>
             </select>
             <select name="sdinformationinsured.recognizeeOccupation3" id="recognizeeOccupation3" style="width:125px;_width:237px;" verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)">
             		<option value="-1">--请选择--</option>
             </select>
             <label class="requireField"></label>
　　</p></DIV>
           
      <DIV class="orther_boxs clearfix"  id="insInfo" style="display:none;">
      <#list 1..insuredCount as x>
              	<dl id="ins_${x-1}" class="clearfix bbr_boxs bbr_bor">
                	<dt  class="clearfix bbr_boxs_t"> 
                	<span class="bxr_names"><i class="bxr_num">${X}</i><DIV class="bxr_tit">被保险人 <b id="nametitle_${x-1}"  >${(sdinformationinsuredList[X-1].recognizeeName)!}</b></DIV>
                    </span><a href="javascript:void(0);">收起</a> </dt>
                    <dd>
	
			<#if (loginFlag =="true")!>
				<#if recognizeeList? has_content>
					<DIV class="cy_user order-user">
				<#else>
					<DIV class="cy_user order-user" style="display:none;">
				</#if>
			<#else>
					<DIV class="cy_user order-user" style="display:none;">
			</#if>

                    <dl class="cy_dl_csf">
                  
                    <dt class="cy_user_t" >一键添加常用被保人：</dt>
                    <dd class="cy_user_ch" >
					    <#list recognizeeList as list>
							<label><input TYPE="radio" NAME="quickrecognizee" onclick="quickQueryInsured(this,this.value,'_${x-1}');" VALUE="${list.recognizeeName}">${list.recognizeeName}</label>
						</#list>
                    </dd>
                </dl>
                <DIV class="clear"></DIV>
            </DIV>
<DIV id="insurance_${x-1}"><p> 
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
onblur="verifyElement('被保人姓名|NOTNULL&UFO&LEN>2',this.value,this.id)"
maxlength=60 verify="被保人姓名|NOTNULL&UFO&LEN>2"/>
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
	        </#if><span class="bbr-mes-btn">确认</span>
</DIV><DIV class="clear"></DIV>
	     
      </dd>
   </dl>  
</#list>
</DIV>
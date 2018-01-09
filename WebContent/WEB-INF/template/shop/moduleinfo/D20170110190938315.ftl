<div  class="line_at">受益人信息</div>
  <input type="hidden" id="benefitb_num" value="${beneficiaryNum}">
<div class="form tb-up-from"><div class="favoree_select">
  			<span class="span_t">*受益人：</span> 
	  		<label for="recognizeetg_fd"><input type="radio" checked="checked" value="0"  class="favoree_tag" id="recognizeetg_fd" name="benefitOperate" <#if (benefitOperate == "0")>checked="checked"</#if>>法定受益人<i class="span_t_hs">（直系亲属）</i></label>	
	  		<label for="recognizeetg_zd"><input type="radio" value="1" class="favoree_tag" id="recognizeetg_zd" name="benefitOperate" <#if (benefitOperate== "1")>checked="checked"</#if>>指定受益人</label>  
  		</div>
              			<div style="display: none;" id="benefitb_box" class="orther_boxs clearfix">
		<#list 1..beneficiaryCount as x>
              	<dl class="clearfix bbr_boxs-syr bbr_bor" id="ins2_${x-1}">
                	<dt class="clearfix bbr_boxs_t"> 
                	<span class="bxr_names">
                	<i class="bxr_num">${x}</i>
                	<input type="hidden" value="${x}" class="favoreeOrder" name="sdinformationbnfList[${x-1}].bnfNo" />
                	<input type="hidden" value="" id="favrelationFlag_${x-1}" />
			 		<input type="hidden" id="sdinformationbnfList_${x-1}" name="sdinformationbnfList[${x-1}].id" value="${(sdinformationbnfList[x-1].id)!}"/>
                	<div class="bxr_tit">受益人 <b id="favoreenametitle_${x-1}"></b></div>
                    </span><a href="javascript:void(0);" class="kg_jia">打开</a> <i class="bxr-up-data">修改</i> <em>删除</em></dt>
                    <dd style="">
         <div id="favoreeinsurance_0"><p> 
           <span class="span_t">*与被保人关系：</span> 
           <select style="width:125px;" onchange="FavoreechangeInformation(this.id,'favoreeinsurance_${x-1}');" onblur="verifyElement('所在地区|APPRELATION2',this.value,this.id)" verify="与被保人关系|APPRELATION2" id="favoreeRelation_${x-1}" name="sdinformationbnfList[${x-1}].relationToInsured">
				<option value="-1">--请选择--</option>
						<#list bnfRelationList as list>
						<option value="${list.codeValue}" <#if (sdinformationbnfList[x-1].relationToInsured==list.codeValue)!>selected="selected"</#if>>
							${list.codeName}
						</option>
					</#list>
			 	</select>
			 <label class="requireField"></label>
         </p><p>
                <span class="span_t">*受益人姓名：</span> 
                <input type="text" verify="姓名|NOTNULL&UFO&LEN>2" maxlength="60" onblur="verifyElement('姓名|NOTNULL&UFO&LEN>2',this.value,this.id)"  value="${(sdinformationbnfList[x-1].bnfName)!}" name="sdinformationbnfList[${x-1}].bnfName" id="favoreeName_${x-1}">
            	 <label class="requireField"></label>
             </p><div class="clearfix">
             <p class="from_table">
               <span class="span_t">*证件类型：</span>
               <select onchange="changeVerification('favoreeTypeId_${x-1}','favoreeId_${x-1}');" style="width:105px;" name="sdinformationbnfList[${x-1}].bnfIDType" id="favoreeTypeId_${x-1}">
	                    <#list certificateList as list>
	                    <option  id="${list.flagType}"     value="${list.codeValue}" <#if (sdinformationbnfList[x-1].bnfIDType==list.codeValue)!>selected="selected"</#if>>${list.codeName}</option>
	                </#list>
				</select>
				<label class="requireField"></label>	
            </p>
            <p class="from_table2">
                <input type="text" onblur="idcheck('favoreeTypeId_${x-1}','favoreeId_${x-1}','favoreeBirthday_${x-1}','favoreeSex_${x-1}','sdinformation.InsuranceCompany');" verify="证件号码|NOTNULL" value="${(sdinformationbnfList[x-1].bnfIDNo)!}" name="sdinformationbnfList[${x-1}].bnfIDNo" id="favoreeId_${x-1}">
            	<label class="requireField"></label>
            </p>
        </div><p>
	            <span class="span_t">*证件有效日期：</span> 
	            <input type="text" class="shop_day" verify="证件有效日期|NOTNULL" onblur="verifyElement('证件有效日期|NOTNULL',this.value,this.id)" value="${(sdinformationbnfList[x-1].bnfStartID)!}" onfocus="effchange()" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-18000}',maxDate:'%y-%M-{%d+0}'})" name="sdinformationbnfList[${x-1}].bnfStartID" id="favoreeStartID_${x-1}"> 
	           至
		           <input type="text" class="shop_day" <#if (sdinformationbnfList[x-1].bnfEndID!= '9999-12-31')!>verify="证件有效日期|NOTNULL"</#if> onblur="verifyElement('证件有效日期|NOTNULL',this.value,this.id)" value="<#if (sdinformationbnfList[x-1].bnfEndID!= '9999-12-31')!>${(sdinformationbnfList[x-1].bnfEndID)!}</#if>" onfocus="effchange()" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+0}',maxDate:'%y-%M-{%d+18000}'})" name="sdinformationbnfList[${x-1}].bnfEndID" id="favoreeEndID_${x-1}" <#if (sdinformationbnfList[x-1].bnfEndID== '9999-12-31')!> disabled = 'disabled' </#if>><input type="checkbox" class="favoreeDatelong"  onclick="CheckapplicantEndID('#favoreeEndID_${x-1}','#reCheckzjnum_long_${x-1}')" id="reCheckzjnum_long_${x-1}" name='' <#if (sdinformationbnfList[x-1].bnfEndID== '9999-12-31')!>checked = 'checked'</#if>>长期有效
                      <label class="requireField"></label> 
                      <#if (sdinformationbnfList[x-1].bnfEndID== '9999-12-31')!>
				      </#if>
	         </p><p>
	<span class="span_t">*性别：</span>
		<#list sexList as list>
		<label>
			<input type="radio" name="sdinformationbnfList[${x-1}].bnfSex" value="${list.codeValue}" id="favoreeSex_${x-1}" class="frome_radio" onclick="dateTriggerFront('favoreeSex_${x-1}');"
			<#if (sdinformationbnfList[x-1].bnfSex==list.codeValue)!>checked="checked"<#elseif (list.codeName=="男" && sdinformationbnfList[x-1]==null ) !>checked="checked"</#if>>${list.codeName}</label>
     	</#list>
			</p><p>
	             <span class="span_t">*出生日期：</span> 
	             <input type="text" class="shop_day"  verify="出生日期|notnull" onBlur="verifyElement('出生日期|NOTNULL',this.value,this.id);"
	             value="${(sdinformationbnfList[x-1].bnfBirthday)!}"  onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-36500}',maxDate:'%y-%M-{%d-1}'})"  name="sdinformationbnfList[${x-1}].bnfBirthday" id="favoreeBirthday_${x-1}">
	        	 <label class="requireField"></label>
	         </p><p>
                <span class="span_t">*手机号码：</span> 
                <input type="text" maxlength="11" verify="手机号码|MOBILENO" onfocus="verifyShowInf(this.value,this.id);" onblur="verifyElement('手机号码|MOBILENO',this.value,this.id)" value="${(sdinformationbnfList[x-1].mobile)!}" name="sdinformationbnfList[${x-1}].mobile" id="favoreeMobile_${x-1}">
                <label class="requireField"></label>
<font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请填写正确的手机号</i></font>
            </p><p>
					   <span class="span_t">*受益比例：</span> 
					       <input type="text" maxlength="3" verify="受益人比例|BENEFIT"  onblur="verifyElement('受益人比例|BENEFIT',this.value,this.id)" onfocus="" onblur="" value="${(sdinformationbnfList[x-1].benePer)!}" name="sdinformationbnfList[${x-1}].benePer" id="favoreeRatio_${x-1}">
						<label class="requireField"></label>
				</p><p>
   <span class="span_t">*受益人地区：</span>
   <select id="favoreeBnfArea1_${x-1}" name="sdinformationbnfList[${x-1}].bnfArea1" onchange="getChildrenArea(this.value,'favoreeBnfArea2_${x-1}','-1',this);" style="width:125px;">
		   <option value="-1">--请选择--</option>
		   <#list areaList as list>
				<option value="${list.id}" <#if (sdinformationbnfList[x-1].bnfArea1==list.id)!>selected="selected"</#if>>${list.name}</option>
			</#list>
  </select>
  <select id="favoreeBnfArea2_${x-1}" name="sdinformationbnfList[${x-1}].bnfArea2" verify="所在地区|AREAFORMAT" onblur="verifyElement('所在地区|AreaFormat',this.value,this.id)" style="width:125px;">
	  <option value="-1">--请选择--</option> 
  </select>
	  <label class="requireField"></label>
</p><p>
	<span class="span_t">*受益人职业:</span>
	<select name="sdinformationbnfList[${x-1}].bnfOccupation1" id="favoreeBnfOccupation1_${x-1}" onchange="getChildrenOPT(this.value,'favoreeBnfOccupation2_${x-1}',-1,this,'2');" style="width:125px;_width:160px;" verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)">
		<option value="-1">--请选择--</option>
		<#list occupationList as list>
			<option value="${list.id}" <#if (sdinformationbnfList[x-1].bnfOccupation1==list.id)!>selected="selected"</#if>>${list.name}</option>	
		</#list>
	 </select> 
	<select name="sdinformationbnfList[${x-1}].bnfOccupation2" id="favoreeBnfOccupation2_${x-1}" onchange="getChildrenOPT(this.value,'favoreeBnfOccupation3_${x-1}',-1,this,'3');"  style="width:125px;_width:237px;" verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)" >
		<option value="-1">--请选择--</option>
	</select>
	<select name="sdinformationbnfList[${x-1}].bnfOccupation3" id="favoreeBnfOccupation3_${x-1}" style="width:125px;_width:237px;"  verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)">
		<option value="-1">--请选择--</option>
	</select>
	<label class="requireField"></label>
</p><span class="bbr-remes-btn">确认</span>
            </div>
	     
      </dd>
   </dl>
</#list>  
              <div class="add_ortherholder" id="addfavoreeins"> <a class="more_holder" id="addfavoree" type="button" href="###">+ 添加受益人</a></div> 
  </div>
	

              </div>
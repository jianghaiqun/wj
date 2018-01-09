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
					   <span class="span_t">*受益比例：</span> 
					       <input type="text" maxlength="3" verify="受益人比例|BENEFIT"  onblur="verifyElement('受益人比例|BENEFIT',this.value,this.id)" onfocus="" onblur="" value="${(sdinformationbnfList[x-1].benePer)!}" name="sdinformationbnfList[${x-1}].benePer" id="favoreeRatio_${x-1}">
						<label class="requireField"></label>
				</p><span class="bbr-remes-btn">确认</span>
            </div>
	     
      </dd>
   </dl>
</#list>  
  </div>
              </div>
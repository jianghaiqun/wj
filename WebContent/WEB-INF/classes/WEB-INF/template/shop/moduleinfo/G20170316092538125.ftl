<#if (sdinformation.appType == 12 || sdinformation.chargeYear != "0C")>
	<div id="renewal_box" class="line_a clear_mar">
		<div class="line_at">续期交费信息<input type="hidden" id="bankinforFlag" name="bankinforFlag" value="Y"/></div>
		<div class="form">
<#else>
	<div class="line_a clear_mar" style="display:none">
		<div class="line_at">续期交费信息<input type="hidden" id="bankinforFlag" name="bankinforFlag" value="N"/></div>
		<div>
</#if><#if (sdinformation.appType == 12 || sdinformation.chargeYear != "0C")>
		<p>
			<span class="span_t">*续期缴费：</span>
			<select id="renewal_sel" name="directPayBankInfo.prop5" style="width:125px;" >
			<#if directPayBankInfo.prop5?? && directPayBankInfo.prop5 =="Y" >
					<option value="N"  >不续保</option>
					<option value="Y"  selected="selected" >到期续保</option>
			<#else>
					<option value="N" selected="selected" >不续保</option>
					<option value="Y"  >到期续保</option>
			</#if>
			</select>
		</p>
</#if><#if (sdinformation.appType == 12 || sdinformation.chargeYear != "0C")>
		<p>
			<span class="span_t">*开户银行：</span>
			<select id="bankCode" name="directPayBankInfo.bankCode" style="width:125px;" verify="开户银行|BANKCODE" onblur="verifyElement('开户银行|BANKCODE',this.value,this.id)">
				<option selected="selected" value="-1">--请选择--</option>
				<#list bankList as list>
					<option value="${list.codeValue}" <#if (directPayBankInfo.bankCode==list.codeValue)!>selected="selected"</#if>>${list.codeName}</option>
				</#list>
			</select>
			<label class="requireField"></label> <font color="#808080">* 必须为储蓄卡账户，否则将无法划转保费导致缴费不成功。</font> 
		</p>
</#if><#if (sdinformation.appType == 12 || sdinformation.chargeYear != "0C")>
		<p>
			<span class="span_t">*账户名： </span>
			<span>默认为投保人 </span>
		</p>
</#if><#if (sdinformation.appType == 12 || sdinformation.chargeYear != "0C")>
	<p>
		   <span class="span_t">*开户行地区：</span>
		   <select id="bankProvince" name="directPayBankInfo.bankProvince" onchange="getChildrenArea(this.value,'bankCity','-1',this);" style="width:125px;">
				   <option value="-1">--请选择--</option>
				   <#list areaList as list>
						<option value="${list.id}" <#if (directPayBankInfo.bankProvince==list.id)!>selected="selected"</#if>>${list.name}</option>
					</#list>
		  </select>
		  <select id="bankCity" name="directPayBankInfo.bankCity" verify="所在地区|AREAFORMAT" onblur="verifyElement('所在地区|AreaFormat',this.value,this.id)" style="width:125px;">
			  <option value="-1">--请选择--</option> 
		  </select>
			  <label class="requireField"></label>
	   </p>
</#if><#if (sdinformation.appType == 12 || sdinformation.chargeYear != "0C")>
		<p>
			<span class="span_t">*卡号： </span>
			<input id="bankAccNo" name="directPayBankInfo.bankNo" value="${(directPayBankInfo.bankNo)!}" type="text" onfocus="verifyShowInf(this.value,this.id);" onblur="verifyElement('银行卡号|BANKNO',this.value,this.id)" verify="银行卡号|BANKNO"> <label class="requireField"></label> 
			<font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请填写开卡信息与投保人姓名、证件号码一致的卡号。</i></font>
		</p>
</#if><#if (sdinformation.appType == 12 || sdinformation.chargeYear != "0C")>
		<p>
			<span class="span_t">*确认卡号：</span>
			<input id="rebankAccNo" name="rebankAccNo" value="${(directPayBankInfo.bankNo)!}" type="text" onblur="verifyElement('确认银行卡号|REBANKNO',this.value,this.id)" verify="确认银行卡号|REBANKNO"> <label class="requireField"></label> 
		</p>
</#if><div class="clear"></div>	
	</div>
</div>
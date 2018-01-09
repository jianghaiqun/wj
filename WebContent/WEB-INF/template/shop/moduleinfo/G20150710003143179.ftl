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
        <span class="span_t">*开户银行：</span>
        <select id="bankCode" name="directPayBankInfo.bankCode" style="width:125px;" verify="开户银行|BANKCODE"
                onblur="verifyElement('开户银行|BANKCODE',this.value,this.id)">
            <option selected="selected" value="-1">--请选择--</option>
            <#list bankList as list>
                <option value="${list.codeValue}"
                        <#if (directPayBankInfo.bankCode==list.codeValue)!>selected="selected"</#if>>${list.codeName}</option>
            </#list>
        </select>
        <label class="requireField"></label> <font color="#808080">* 必须为储蓄卡账户，否则将无法划转保费导致缴费不成功。</font>
    </p>
</#if><#if (sdinformation.appType == 12 || sdinformation.chargeYear != "0C")>
    <p>
        <span class="span_t">*账户名： </span>
        <span id="zhm_name">默认为投保人 </span>
    </p>
</#if><#if (sdinformation.appType == 12 || sdinformation.chargeYear != "0C")>
    <div class="pos-tb-p">
        <p>
            <span class="span_t">*卡号： </span>
            <input id="bankAccNo" name="directPayBankInfo.bankNo" value="${(directPayBankInfo.bankNo)!}" type="text"
                   onfocus="verifyShowInf(this.value,this.id);" onblur="verifyElement('银行卡号|BANKNO',this.value,this.id)"
                   verify="银行卡号|BANKNO"> <label class="requireField"></label>
            <font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请填写开卡信息与投保人姓名、证件号码一致的卡号。</i></font>
        </p>
        <label class="app_mobile" for="bankAccNo" style="display: block;">只限投保人储蓄类型银行卡卡号</label>
    </div>
</#if><#if (sdinformation.appType == 12 || sdinformation.chargeYear != "0C")>
    <div class="pos-tb-p">
        <p>
            <span class="span_t">*确认卡号：</span>
            <input id="rebankAccNo" name="rebankAccNo" value="${(directPayBankInfo.bankNo)!}" type="text"
                   onblur="verifyElement('确认银行卡号|REBANKNO',this.value,this.id)" verify="确认银行卡号|REBANKNO"> <label
                class="requireField"></label>
        </p>
        <label class="app_mobile" for="rebankAccNo" style="display: block;">只限投保人储蓄类型银行卡卡号</label>
    </div>
</#if>
    <div class="clear"></div>
</div>
</div>
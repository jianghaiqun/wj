<div  class="line_at">产品信息</div>
<div class="form up_from_p">
<p>
	        	<span class="span_t">保险名称：</span> 
	            <span style="width:500px;">
	            	
	                	${sdinformation.productName}
	                
	            </span>
</p><#if (sdinformation.planCode)??>
<p>
	        	<span class="span_t">投保计划：</span> 
	            <span style="width:500px;">
	                
	                	${sdinformation.planName}
		                <input type="hidden" name="sdinformation.planName" id = "planName" value="${sdinformation.planName}" />
		                <input type="hidden" name="sdinformation.planCode" id = "planCode" value="${sdinformation.planCode}" />
	                
	            </span>
</p>
	    	</#if><#if (gradeList.size()>0)!>
	            <p>
	                <span class="span_t" >产品级别：</span> 
	                <select name="grade"  id="grade">
						<#list gradeList as list>
							<option value="${list.factorValue}">
								${list.factorDisplayValue}
							</option>
						</#list>
					</select>
	            </p>
	         </#if><#if (sdinformation.chargeYear)??>
<p>
	            	<span class="span_t">缴费年期：</span> 
	                <span>
		                
		                	${sdinformation.chargeYearName}
<input type="hidden" name="sdinformation.chargeYearName" id = "chargeYearName" value="${sdinformation.chargeYearName}"/>		                	
<input type="hidden" name="sdinformation.chargeYear" id = "chargeYear" value="${sdinformation.chargeYear}"/>
		                
	                </span>
</p>
            	</#if><#if (sdinformation.appLevel)??>
<p>
	            	<span class="span_t">投保类别：</span> 
	                <span>
		                
		                	${sdinformation.appLevelName}
<input type="hidden" name="sdinformation.appLevelName" id = "appLevelName" value="${sdinformation.appLevelName}"/>		                	
<input type="hidden" name="sdinformation.appLevel" id = "appLevel" value="${sdinformation.appLevel}"/>
		                
	                </span>
</p>
            	</#if><#if (sdinformation.appType)??>
<p>
					  <span class="span_t">缴费方式：</span>
					  <span>
		                
		                	${sdinformation.appTypeName}
<input type="hidden" name="sdinformation.appTypeName" id = "AppTypeName" value="${sdinformation.appTypeName}" />		                	
<input type="hidden" name="sdinformation.appType" id = "AppType" value="${sdinformation.appType}" />
		                
	                  </span>
 </#if><#if  (sdinformation.ensure)??>
<p>
					  <span class="span_t">保险期限：</span>
					  <span style="width:500px;">
		                
		                	${sdinformation.ensureDisplay}
		                	<input type="hidden" name="sdinformation.ensure" id = "Ensure" value="${sdinformation.ensure}" />
		                	<input type="hidden" name="sdinformation.ensureDisplay" id = "ensureDisplay" value="${sdinformation.ensureDisplay}" />
		                
	                  </span>
			  </p>
				  </#if><div class="clearfix"  >
	 <p class="from_update xg_width">
		<span class="span_t">保单起保日期：</span>
		<#if (effectiveNextDayFlag=="true")!>
		<input id="effective_" name="effective_" type="text" value="${effectiveDayValue}" readonly="readonly" class="shop_day_cfs"  style="border:0;background:transparent;"/>
		<input id="effective" name="m_startDate" type="hidden" value="${effectiveDayValue}"  class="shop_day_cfs"  style="border:0;background:transparent;"/>
		<#else>
		<input id="effective" name="m_startDate" type="text" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+${startPeriod}}',maxDate:'%y-%M-{%d+${endPeriod}}'})"  onFocus = "effchange()"  value="${(sdinformation.startDate)!}" readonly="readonly" class="shop_day_cfs"/>
		<label class="requireField">*</label>
		<font color="#808080">请选择保单起保日期</font>
		</#if>
	</p>
	<p class="from_update xg_width">
		<span class="span_t">保单终止日期：</span>
		<#if (sdinformation.endDate=="9999-12-31")|| (sdinformation.endDate=="终身")>
			<input id="fail_" name="fail_" readonly="readonly"   class="shop_day_cfs"  style="border:0;background:transparent;" value="终身" />
			<em class="from-up-time" id="ensureDate_em">(从 ${sdinformation.startDate} 00时起到 终身为止)</em>
			<input id="fail" name="m_endDate" type="hidden"   value="终身" />
		<#else>
			<input id="fail_" name="fail_" readonly="readonly"   class="shop_day_cfs"  style="border:0;background:transparent;" value="${(sdinformation.endDate)!}" />
			<em class="from-up-time" id="ensureDate_em">(从 ${sdinformation.startDate} 00时起到 ${sdinformation.endDate} 24时为止)</em>
			<input id="fail" name="m_endDate" type="hidden"   value="${(sdinformation.endDate)!}" />
		</#if>
	</p>
</div><#if (sdinformation.appMult)??>
	<p>
		<span class="span_t">购买份数：</span>
		<span>
			${sdinformation.appMult}份
			<input type="hidden" name="sdinformation.appMult" id = "AppMult" value="${sdinformation.appMult}" />
		</span>
	</p>
</#if><!--<p>
		<span class="span_t">价格：</span>
			<span style="width:500px;">
				<b class="shop_m_f red">￥</b>
				<b class="up_font_s"   id="priceTotle_">${sdorder.payPrice}</b>
				</span>
</p>-->
</div>
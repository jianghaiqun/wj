            <div  class="line_at">保险期限<img width="18" height="16" title="是指保险合同约定承担保险责任的一段时间。您可以根据实际需要选择起保时间和终止时间。" src="${staticPath}/style/shop/images/wen_a.png"/></div>
            <div class="form">
	            <#if (planList.size()>0)!>
	            <p>
	                <span>投保计划</span> 
	                <#if planList.size() &gt; 1>
	                    <select name="plan"  id="plan" onchange="planChange();">
								<#list planList as list>
								   <#if (sdinformation.brkRiskCode)??>
										<option value="${list.factorValue}" <#if (list.factorValue == sdinformation.brkRiskCode) !>selected="selected"</#if> >
									<#else>
										<option value="${list.factorValue}" <#if (list.factorValue == sdinformation.brkRiskCode)!> selected</#if>>
									</#if>
										${list.factorDisplayValue}
									</option>
								</#list>
						 </select>
					<#else>
						<#list planList as list>
					   		<input value="${list.factorValue}" name="plan" id="plan" type="hidden">
							<span>${list.factorDisplayValue}</span>
						</#list>
					</#if>
	            </p>
	         </#if>
	         
	    
	         <#if (gradeList.size()>0)!>
	            <p>
	                <span>产品级别</span> 
	                <select name="grade"  id="grade">
						<#list gradeList as list>
							<option value="${list.factorValue}">
								${list.factorDisplayValue}
							</option>
						</#list>
					</select>
	            </p>
	         </#if>
	         <#if (feeYearList.size()>0)!>
				<p>
					<span>缴费年期</span>
					<select name="feeYear"  id="feeYear">
						<#list feeYearList as list>
							<option value="${list.factorValue}">
								${list.factorDisplayValue}
							</option>
						</#list>
					</select>
				</p>
			  </#if>
			  <#if (appLevelList.size()>0)!>
			      <p>
			         <span>缴费方式</span>
			         <select name="appLevel"  id="appLevel">
							<#list appLevelList as list>
								<option value="${list.factorValue}">
									${list.factorDisplayValue}
								</option>
							</#list>
					 </select>
			      </p>
			  </#if>
			  <#if (appTypeList.size()>0)!>
			      <p>
			          <span>投保类别</span>
			          <select name="appType"  id="appType">
							<#list appTypeList as list>
								<option value="${list.factorValue}">
									${list.factorDisplayValue}
								</option>
							</#list>
					  </select>
			      </p>
			  </#if>
			  <#if (periodList.size()>0)!>
			      <p>
			          <span>保险期限</span>
			        <#if (periodList?size>1)>
				          <select name="period"  id="period"  onchange="periodChange();">
								<#list periodList as list>
									<!-- zhangjinquan 11180 bug880 通过字符串包含判断是否为选中的保险期限 2012-10-11 -->
									<#if (sdinformation.allPeriod)??>
									<option value="${list.factorValue}" <#if (list.factorValue == sdinformation.allPeriod) !>selected="selected"</#if> >
									<#else>
									<option value="${list.factorValue}" <#if (list.factorValue == period)!> selected</#if> >
									</#if>
										${list.factorDisplayValue}
									</option>
								</#list>
						  </select>
					   <#else>
					   		<#list periodList as list>
					   		        <input value="${list.factorValue}" name="period" id="period" type="hidden">
									<span>${list.factorDisplayValue}</span>
							</#list>
					   </#if>
			      </p>
			  </#if>
			 <p from_update xg_width>
	            <span class="from_update xg_width">保单起保日期:</span> 
	            <input id="effective" name="effective" type="text" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+${startPeriod}}'})"  onchange = "effchange()" value="${(sdinformation.startdate)!}"  class="required shop_day" />
	         </p>
	          <#if (periodList.size()>0)!>
	             <p>
	                <span>保单终止日期:</span> 
	                <input id="fail_" name="fail_" disabled="disabled" value="${(sdinformation.enddate)!}" />
	                <input id="fail" name="fail" type="hidden"   onclick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'effective\')}'})" onchange = "failchange()" value="${(sdinformation.enddate)!}" />
	            </p>
	         <#else>
	             <p>
	                <span>保单终止日期:</span> 
	                <input id="fail_" name="fail_" type="hidden"   value="${(sdinformation.enddate)!}" />
	                <input id="fail" name="fail"   onclick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'effective\')}'})" onchange = "failchange()" value="${(sdinformation.enddate)!}" />
	            </p>
	         </#if>
                 
            </div>
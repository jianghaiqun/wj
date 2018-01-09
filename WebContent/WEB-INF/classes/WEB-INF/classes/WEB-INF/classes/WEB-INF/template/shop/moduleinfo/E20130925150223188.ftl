<div style="display:none;">
<input type="hidden" id="dutyFactor-plan" name="dutyFactor-plan" value="" />
<input type="hidden" id="dutyFactorSize" name="dutyFactorSize" value="${dutyFactor.size()}" />
<#if (planList.size()>0)!>
 <#assign index_ = 1 />
<#list dutyFactor as list>
	 <input type="hidden" id="dutyCode${index_}" name="dutyCode${index_}" value="${list.dutyCode}" />
	 <#assign index_=index_+1 />
</#list>
<#list planList as planList>
		 <div class="line_a" id="planList-${planList.factorValue}" <#if (planList.factorValue==sdinformation.planCode)!>style="display:bolck;"<#else>style="display:none;"</#if>>
		     <div class="line_at">保障内容信息</div>
		     <div>
		     <table id="mytable" cellspacing="1" class="datagrid3" style="TABLE-LAYOUT: fixed;word-wrap: break-word; word-break: break-all;">
		     <tr> 
		     <th  width="25%">保障项目</th> 
		     <th  width="25%">保额</th> 
		     <th style="display:none" width="10%">保费</th> 
		     <th>保障内容</th> 
		     </tr> 
		 		<caption> </caption> 
					<#assign index = 1 />
						<#list dutyFactor as list>
							<#assign display = "" />
							<#assign findFactorFlag = 0 >
							<#if (list.fdAmntPremList.size() > 0)>
								<#list list.fdAmntPremList as listaa>
									<#if (planList.factorValue == listaa.appFactorValue) || (listaa.appFactorValue=='0000') >
										<#assign findFactorFlag = 1 >
									</#if>
								</#list>
							</#if>
							<#if findFactorFlag==0 >
								<#assign display = "display:none;" />
							</#if>
							<tr  style="background-color: #EFEFEF; ${display}" >
								<td style="word-break:break-all;word-wrap:break-word;white-space:normal;" width="520">
									<span>${list.dudtyFactorName} </span>
								</td>
								<td style="padding-left:100px;word-break:break-all;word-wrap:break-word;white-space:normal;" align="left">
								    <#if (list.fdAmntPremList.size()>0)!>
							            <#list list.fdAmntPremList as list2>
							                <#if (planList.factorValue == list2.appFactorValue) || (list2.appFactorValue=='0000')  !>
							                	<span>${list2.amnt}</span>
							                	<input type="hidden" id="amnt-${planList.factorValue}-${index}" name="amnt-${planList.factorValue}${index}" value="${list2.backUp1}">
							               	    <input type="hidden" id="amntDis-${planList.factorValue}-${index}" name="amntDis-${planList.factorValue}${index}" value="${list2.amnt}">
							               	    <input type="hidden" id="prem-${planList.factorValue}-${list.dutyCode}" name="prem-${planList.factorValue}${index}" value="${list2.prem}">
							               	    </#if>
							            </#list>
								    </#if>
								</td>
								<td  style="display:none" >
								 <#if (list.fdAmntPremList.size()>0)!>
						            <#list list.fdAmntPremList as list2>
						            <#if (planList.factorValue == list2.appFactorValue) || (list2.appFactorValue=='0000')  !>
						                	<span id="${planList.factorValue}${list.dutyCode}">${list2.prem}</span>
						                	
						            </#if>
						            </#list>
							    </#if>
								</td>
								<td style="word-break:break-all;word-wrap:break-word;white-space:normal;" width="520">
								${list.define}
							</td>
							</tr>
					<#assign index=index+1 />
						</#list>
				</table>
		</div>
			<br>
	</div>
 </#list>
			
 <#else>
	  	<div class="line_a">
	     <div class="line_at">保障内容信息</div>
	     <div>
	     <table id="mytable" cellspacing="1" class="datagrid3" style="TABLE-LAYOUT: fixed;word-wrap: break-word; word-break: break-all;">
	     <tr> 
	     <th  width="25%">保障项目</th> 
	     <th  width="25%">保额</th> 
	     <th style="display:none" width="10%">保费</th> 
	     <th>保障内容</th> 
	     </tr> 
	 		<caption> </caption> 
				<#assign index = 1 />
					<#list dutyFactor as list>
						<tr  style="background-color: #EFEFEF; " >
							<td style="word-break:break-all;word-wrap:break-word;white-space:normal;" width="520">
								<span>${list.dudtyFactorName} </span>
							</td>
							<td style="padding-left:100px;word-break:break-all;word-wrap:break-word;white-space:normal;" align="left">
							    <#if (list.fdAmntPremList.size()>0)!>
							        <#if (list.fdAmntPremList.size()>1)>
									    <select onchange="changeAmnt('amnt${index}','amntDis${index}');">
											<#list list.fdAmntPremList as list1>
												<option value="${list1.backUp1}">
													<span>${list1.amnt}</span>
												</option>
											</#list>
										</select>
										<input type="hidden" id="amnt--${index}" name="amnt${index}" value="${list.fdAmntPremList.get(0).backUp1}">
										<input type="hidden" id="amntDis--${index}" name="amntDis${index}" value="${list.fdAmntPremList.get(0).amnt}">
										<input type="hidden" id="prem--${list.dutyCode}" name="prem-${index}" value="${list.fdAmntPremList.get(0).prem}">
									<#else>
							            <#list list.fdAmntPremList as list2>
							               <span>${list2.amnt}</span>
							                <input type="hidden" id="amnt--${index}" name="amnt${index}" value="${list2.backUp1}">
							                <input type="hidden" id="amntDis--${index}" name="amntDis${index}" value="${list2.amnt}">
							                <input type="hidden" id="prem--${list.dutyCode}" name="prem-${index}" value="${list2.prem}">
							            </#list>
							         </#if>
							    </#if>
							</td>
							<td style="display:none">
							 <#if (list.fdAmntPremList.size()>0)!>
						        <#if (list.fdAmntPremList.size()>1)>
										<#list list.fdAmntPremList as list1>
												<span id="${list.dutyCode}">${list1.prem}</span>
										</#list>
								<#else>
						            <#list list.fdAmntPremList as list2>
						               <span id="${list.dutyCode}">${list2.prem}</span>
						            </#list>
						         </#if>
					         </#if>
							</td>
							<td style="word-break:break-all;word-wrap:break-word;white-space:normal;" width="520">
								${list.define}
							</td>
							<input type="hidden" id="dutyCode${index}" name="dutyCode${index}" value="${list.dutyCode}" />
						</tr>
				<#assign index=index+1 />
					</#list>
			</table>
				</div>
				<br>
	 </div>
 </#if>
</div>
<div class="line_a ">
       <div  class="line_at cf"><div class="tbr_mes">被保人信息 <span class="tbr-b">（我要为ta买保险）</span></div>
       
</div>
     <div class="form tb-up-from"><div id="radiobox" class="radioboxs">
	<span class="span_t">被保人：</span>
             <div style="display:none" id="lookOccuDiv"><div class="add_zy zy_shows"><label for="sel_zys">
    <input type="checkbox" id="sel_zys" />确认被保人属于可投保职业 </label><i onclick="showOccupations();">职业类别表（必看）</i> </div>
            <div class="clear zy_shows"></div>
            <span class="span_t zy_shows"></span></div>
             <label for="rid_me"> <input   type="radio" onclick="radioShow();"  value="1" name="myradio" id="rid_me"  <#if (recognizeeOperate=='1')>checked="checked"</#if>>本人</label>
             <label for="rid_orther"><input   type="radio" onclick="radioShow();"  value="2" name="myradio" id="rid_orther"   <#if (recognizeeOperate=='2')>checked="checked"</#if>>其他被保险人<em>（可包括本人）</em></label>
             <#if (productExcelFlag=='Y')>
              <label for="rid_td"><input   type="radio" onclick="radioShow();"  value="3" name="myradio" id="rid_td"   <#if (recognizeeOperate=='3')>checked="checked"</#if>>被保人批量上传<em>（推荐10人以上被保人使用）</em></label>
 </#if>
</div>  
<div id="hidd_insuredInfo" style="display:none"></div>        
<input type="hidden" id="mulInsuredFlag" name="mulInsuredFlag" value="${mulInsuredFlag?default('rid_me')}"/>
<div class="bbr_des" id="from_tab"><div class="br_box clearfix"><#if (limitCount>1)>
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
           <span class="span_t">*旅游目的地：</span>
           <select id="recognizeedestinationCountry" name="sdinformationinsured.destinationCountry">
               <#if (listCountryCode!=null && listCountryCode.size()>0)>
                    <#list listCountryCode as list>
                         <option value="${list.codeValue}" <#if (sdinformationinsured.destinationCountry==list.codeValue)>selected="selected"</#if>>${list.codeName}</option>
                    </#list>
               </#if>
           </select>
        </p></div>
           
      <div class="orther_boxs clearfix"  id="insInfo" style="display:none;">
      <#list 1..insuredCount as x>
              	<dl id="ins_${x-1}" class="clearfix bbr_boxs bbr_bor">
                	<dt  class="clearfix bbr_boxs_t"> 
                	<span class="bxr_names"><i class="bxr_num">${x}</i><div class="bxr_tit">被保险人 <b id="nametitle_${x-1}"  >${(sdinformationinsuredList[x-1].recognizeeName)!}</b></div>
                    </span><a href="javascript:void(0);">收起</a> <i class="bxr-up-data" style="display:none;">修改</i> <em>删除</em></dt>
                    <dd>
	
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
         </p><p>
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
           <span class="span_t">*旅游目的地：</span>
           <select id="destinationCountry_${x-1}" name="sdinformationinsuredList[${x-1}].destinationCountry">
               <#if (listCountryCode!=null && listCountryCode.size()>0)>
                    <#list listCountryCode as list>
                         <option value="${list.codeValue}" <#if (sdinformationinsuredList[x-1].destinationCountry==list.codeValue)!>selected="selected"</#if>>${list.codeName}</option>
                    </#list>
               </#if>
           </select>
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
	        </#if><span class="bbr-mes-btn">确认</span>
</div><div class="clear"></div>
	     
      </dd>
   </dl>  
</#list>
              <div id="addIns" class="add_ortherholder"> <a href="###" type="button" id="addIns1" class="more_holder">+ 添加被保险人</a></div> 
  </div>
  
                           <div class="class_team_box clearfix"  style="display:none;">
					<div class="xztd first_xztd  clearfix">
                        <a href="${base}/traveltemplate/${productExcelTemp}"><img src="${base}/wwwroot/kxb/style/shop/images/up-load_03.gif" width="200px" height="41px"> </a>
                    </div>
                    <div class="xztd clearfix">
                        <a><input type="file" name="uploadify" id="uploadify" /></a>
                        <span id="fileQueue" style="height:23px;width:310px; display:block;"></span>
                    </div> 

					<div class="clear"></div>
					<div class="xztd-ptip">
                      *先下载团体表格，填写完成后再上传团体表格
                    </div>
		          <#if sdinsuredList? has_content>
		              <div id="insuedlistDiv"  class="grop_bable">
		          <#else>
		              <div id="insuedlistDiv" style="display:none;" class="grop_bable">
		          </#if> 
                       <table width="100%" border="1" id="insuredtab">
						  <tr>                                                                      
						    <th scope="col">序号</th>
						    <th scope="col">被保险人姓名</th>
						    <th scope="col">与投保人关系</th>
						    <th scope="col">证件类型</th>
						    <th scope="col">证件号码</th>
						    <th scope="col">出生日期</th>
						    <th scope="col">性别</th>
						    <th scope="col">手机号</th>
						    <#if excleTempEnName=="Y" ><th scope="col">英文名</th></#if>
						    <th scope="col">价格</th>
							<th scope="col" style="width:130px">操作</th>
						  </tr>
						  <#assign c_info="" />
						  <#list 1..insuredImpCount as i>
						    <tr id='sdrecognizee_${i-1}'>
							    <td scope="col">${i}</td>
							    <td scope="col"><em class="tb_mes" id="em_sdrecognizeeName_${i-1}" >${(sdinsuredList[i-1].recognizeeName)!}</em><em class='tb_up_date' style='display:none'><input type="text" id="sdrecognizeeName_${i-1}" name="sdinsuredList[${i-1}].recognizeeName" class='td_input_a' value="${(sdinsuredList[i-1].recognizeeName)!}"/></em></td>
							    <td scope="col"><em class="tb_mes" id="em_sdrecognizeeRelationName_${i-1}">${(sdinsuredList[i-1].recognizeeAppntRelationName)!}</em><em class='tb_up_date' style='display:none'><select id="sdrecognizeeRelationName_${i-1}" excelverify='与投保人关系|NOTNULL&ONLYONE=本人' name="sdinsuredList[${i-1}].recognizeeAppntRelationName" class='td_select_a'>
							    <#list excelrelationList as list>
									<option value="${list.codeValue}" <#if (sdinsuredList[i-1].recognizeeAppntRelationName==list.codeValue)!>selected="selected"</#if>>
										${list.codeName}
									</option>
								</#list>
							    </select></em></td>
							    <td scope="col"><em class="tb_mes" id="em_sdrecognizeeTypeName_${i-1}">${(sdinsuredList[i-1].recognizeeIdentityTypeName)!}</em><em class='tb_up_date' style='display:none'><select id="sdrecognizeeTypeName_${i-1}" excelverify='证件类型|NOTNULL' name="sdinsuredList[${i-1}].recognizeeIdentityTypeName" class='td_select_a'>
							    <#list excelcertificateList as list>
									<option value="${list.codeValue}" <#if (sdinsuredList[i-1].recognizeeIdentityTypeName==list.codeValue)!>selected="selected"</#if>>
										${list.codeName}
									</option>
								</#list>
							    </select></em></td>
							    <td scope="col"><em class="tb_mes" id="em_sdrecognizeeId_${i-1}">${(sdinsuredList[i-1].recognizeeIdentityId)!}</em><em class='tb_up_date' style='display:none'><input type="text" id="sdrecognizeeId_${i-1}" onchange='validateExcelId(this)' excelverify='证件号码|NOTNULL&IDCARDEXCEL' name="sdinsuredList[${i-1}].recognizeeIdentityId" class='td_input_b' value="${(sdinsuredList[i-1].recognizeeIdentityId)!}"/></em></td>
							    <td scope="col"><em class="tb_mes" id="em_sdrecognizeeBirthday_${i-1}">${(sdinsuredList[i-1].recognizeeBirthday)!}</em><em class='tb_up_date' style='display:none'><input type="text" id="sdrecognizeeBirthday_${i-1}" excelverify='出生日期|NOTNULL&AGE&CALLPREM' name="sdinsuredList[${i-1}].recognizeeBirthday" class='td_input_a' value="${(sdinsuredList[i-1].recognizeeBirthday)!}"/></em></td>
							    <td scope="col"><em class="tb_mes" id="em_sdrecognizeeSexName_${i-1}">${(sdinsuredList[i-1].recognizeeSexName)!}</em><em class='tb_up_date' style='display:none'><select id="sdrecognizeeSexName_${i-1}" excelverify='证件类型|NOTNULL' name="sdinsuredList[${i-1}].recognizeeSexName" class='td_select_b'>
							    <#list excelsexList as list>
									<option value="${list.codeValue}" <#if (sdinsuredList[i-1].recognizeeSexName==list.codeValue)!>selected="selected"</#if>>
										${list.codeName}
									</option>
								</#list>
							    </select></em></td>
							    <td scope="col"><em class="tb_mes" id="em_sdrecognizeeMobile_${i-1}">${(sdinsuredList[i-1].recognizeeMobile)!}</em><em class='tb_up_date' style='display:none'><input type="text" id="sdrecognizeeMobile_${i-1}" excelverify='手机号|MOBILENO&LEN=11' name="sdinsuredList[${i-1}].recognizeeMobile" class='td_input_c' value="${(sdinsuredList[i-1].recognizeeMobile)!}"/></em></td>
							    <#if excleTempEnName=="Y" >
							    	<td scope="col"><em class="tb_mes" id="em_sdrecognizeeEnName_${i-1}">${(sdinsuredList[i-1].recognizeeEnName)!}</em><em class='tb_up_date' style='display:none'><input type="text" id="sdrecognizeeEnName_${i-1}" excelverify='英文名|NOTNULL&UFO&ENG&LEN>2' name="sdinsuredList[${i-1}].recognizeeEnName" class='td_input_a' value="${(sdinsuredList[i-1].recognizeeEnName)!}"/></em></td>
							    </#if>
							    <td scope="col"><em id="em_sddiscountPrice_${i-1}">${(sdinsuredList[i-1].discountPrice)!}</em><em style='display:none'><input type="text" id="sddiscountPrice_${i-1}" name="sdinsuredList[${i-1}].discountPrice" class='td_input_d' readonly value="${(sdinsuredList[i-1].discountPrice)!}"/></em></td>
							    <td style="width:130px">
								<span  onclick="edit_user(sdrecognizee_${i-1},this)" class="eidit-uptable eidit-btns2s">修改 </span><span class="eidit-uptable td_ac_del" onclick="del_user(sdrecognizee_${i-1},this);" class="eidit-uptable ">删除</span>
								</td>
							    <td style="display:none;" scope="col"><em class="tb_mes" id="em_sdrecognizeePrem_${i-1}">${(sdinsuredList[i-1].recognizeePrem)!}</em><em class='tb_up_date' style='display:none'><input type="hidden" id="sdrecognizeePrem_${i-1}" name="sdinsuredList[${i-1}].recognizeePrem" value="${(sdinsuredList[i-1].recognizeePrem)!}"/></em></td>
							    <td style="display:none;" scope="col"><em class="tb_mes" id="em_sdrecognizeeTotalPrem_${i-1}">${(sdinsuredList[i-1].recognizeeTotalPrem)!}</em><em class='tb_up_date' style='display:none'><input type="hidden" id="sdrecognizeeTotalPrem_${i-1}" name="sdinsuredList[${i-1}].recognizeeTotalPrem" value="${(sdinsuredList[i-1].recognizeeTotalPrem)!}"/></em></td>
							    <td style="display:none;" scope="col"><em class="tb_mes" id="em_sdrecognizeeTypeId_${i-1}">${(sdinsuredList[i-1].recognizeeIdentityType)!}</em><em class='tb_up_date' style='display:none'><input type="hidden" id="sdrecognizeeTypeId_${i-1}" name="sdinsuredList[${i-1}].recognizeeIdentityType" value="${(sdinsuredList[i-1].recognizeeIdentityType)!}"/></em></td>
							    <td style="display:none;" scope="col"><em class="tb_mes" id="em_sdrecognizeeRelation_${i-1}">${(sdinsuredList[i-1].recognizeeAppntRelation)!}</em><em class='tb_up_date' style='display:none'><input type="hidden" id="sdrecognizeeRelation_${i-1}" name="sdinsuredList[${i-1}].recognizeeAppntRelation" value="${(sdinsuredList[i-1].recognizeeAppntRelation)!}"/></em></td>
							    <td style="display:none;" scope="col"><em class="tb_mes" id="em_sdrecognizeeSex_${i-1}">${(sdinsuredList[i-1].recognizeeSex)!}</em><em class='tb_up_date' style='display:none'><input type="hidden" id="sdrecognizeeSex_${i-1}" name="sdinsuredList[${i-1}].recognizeeSex" value="${(sdinsuredList[i-1].recognizeeSex)!}"/></em></td>
	                            <td style="display:none;" scope="col"><em class="tb_mes" id="em_sdrecognizeedestinationCountry_${i-1}">${(sdinsuredList[i-1].destinationCountry)!}</em><em class='tb_up_date' style='display:none'><input type="hidden" id="sdrecognizeedestinationCountry_${i-1}" name="sdinsuredList[${i-1}].destinationCountry" value="${(sdinsuredList[i-1].destinationCountry)!}"/></em></td>
							    <td style="display:none;" scope="col"><em class="tb_mes" id="em_sdrecognizeedestinationCountryText_${i-1}">${(sdinsuredList[i-1].destinationCountryText)!}</em><em class='tb_up_date' style='display:none'><input type="hidden" id="sdrecognizeedestinationCountryText_${i-1}" name="sdinsuredList[${i-1}].destinationCountryText" value="${(sdinsuredList[i-1].destinationCountryText)!}"/></em></td>
							    <td style="display:none;" scope="col"><em class="tb_mes" id="em_sdrecognizeeflightLocation_${i-1}">${(sdinsuredList[i-1].flightLocation)!}</em><em class='tb_up_date' style='display:none'><input type="hidden" id="sdrecognizeeflightLocation_${i-1}" name="sdinsuredList[${i-1}].flightLocation" value="${(sdinsuredList[i-1].flightLocation)!}"/></em></td>
							    <td style="display:none;" scope="col"><em class="tb_mes" id="em_sdrecognizeeflightNo_${i-1}">${(sdinsuredList[i-1].flightNo)!}</em><em class='tb_up_date' style='display:none'><input type="hidden" id="sdrecognizeeflightNo_${i-1}" name="sdinsuredList[${i-1}].flightNo" value="${(sdinsuredList[i-1].flightNo)!}"/></em></td>
							    <#if (sdinsuredList[i-1].flightTime)!>
							    <td style="display:none;" scope="col"><em class="tb_mes" id="em_sdrecognizeeflightTime_${i-1}">${(sdinsuredList[i-1].flightTime)!}</em><em class='tb_up_date' style='display:none'><input type="hidden" id="sdrecognizeeflightTime_${i-1}" name="sdinsuredList[${i-1}].flightTime" value="${(sdinsuredList[i-1].flightTime)!}"/></em></td>
	                            </#if>                                                 
                            </tr>
                            
						  </#list>
				      </table>
				   </div> 
				   <div id="importMessage" style="display:none;" class="table-error-mes"></div>
				</div>  
        </div> 
</div>
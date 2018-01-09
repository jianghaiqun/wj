<div class="line_a " style="overflow:hidden;">
       <div  class="line_at cf"><div class="tbr_mes">投保人信息 <span class="tbr-b">（填写我的信息）</span></div>
       
</div>
  <div class="form">
<#if (loginFlag =="true")!>
	<div id="shop_login" style="display:none;">
<#else>
	<div id="shop_login">
</#if>
							<div class="shop_login" >
								<p class="shop-login-p">会员登录购买，自动填写保单，下次购买省时省事，还有积分、优惠券等你拿！</p>
								<table width="886px" border="0" class="shop_q_log">
									<tr>
										<td class="l_u_w" >帐号：</td>
										<td class="l_u_b">
											<input type="text" id="shoploginname" class="log_bts"></td>
										<td  class="l_u_w">密码：</td>
										<td class="l_u_b">
											<input type="password" id="shoppass"   maxlength="16"  class="log_bts"></td>
										<td class="l_u_w">
											<input type="button" class="log_but" id="shoplogin" hidefocus="true"  value="登录"></td>
										<td>
										</td>
									</tr>
<tr id="logveriCode" style="display:none">
                    <td  class="l_u_w" >
                      验证码：
                    </td>
                     <td style="vertical-align: middle;" colspan="5">
                      <input type="text" id="shopCode" style="width:114px"  name="j_captcha">  <img width="90" height="28" style="vertical-align: middle;"  alt="验证码" src="../captcha.jpg"  id="loginCaptchaImage">
                    </td>                   
                  </tr>
									<tr>
										<td colspan="6" class="logisn-tdlh"><i class="red"  id="shoperror"></i></td>
								    </tr>
									<tr>
										<td colspan="2"> <p class="shop-login-p">首次购买成功的用户会自动注册，无需在此填写</p> </td>
										<td> </td>
										
										<td class="lh_login" colspan="2">
											<ul class="open-auth cf">
												<li>
													<i class="sina"></i>
													<a class="min" gaevent="" id="sina" data-mtevent=" " onclick="otherLogin('sina','wblogin');" href="###">微博</a>
												</li>
											    <!-- 	<li>
											        <i class="wechart"></i>
											        <a class="min" gaevent="" id="wechart" data-mtevent=" " onclick="otherLogin('wechart')" href="###">微信</a>
											    </li> -->
												<li>
													<i class="qq"></i>
													<a class="min min_qq" gaevent="" id="tencent" data-mtevent=" " onclick="otherLogin('tencent','qqlogin');" href="###">QQ</a>
												</li>
												<li>
													<i class="alipay"></i>
													<a class="min" gaevent="" id="alipay" data-mtevent=" " onclick="otherLogin('alipay','zfblogin');" href="###" exturl="http://www.kaixinbao.com/zfblogin" vlpageid="zfblogin">支付宝</a>
												</li>
												<div id="wb_connect_btn"></div>
											</ul>
										</td>
									</tr>
								</table>

							</div>
	</div>
	<#if (loginFlag =="true")!>
		<#if reappntList? has_content>
			<div class="cy_user" id="quick_2" >
		<#else>
			<div class="cy_user" id="quick_2" style="display:none;">
		</#if>
	<#else>
		<div class="cy_user" id="quick_2" style="display:none;">
	</#if>
				<dl class="cy_dl_csf">
					<#if reappntList? has_content>
						<dt id="quick_2_2" class="cy_user_t">常用投保人：</dt>
						<dd id="quick_2_1" class="cy_user_ch">
					<#else>
						<dt id="quick_2_2" class="cy_user_t" style="display:none;">常用投保人：</dt>
						<dd id="quick_2_1" class="cy_user_ch" style="display:none;">
					</#if>
						<#list reappntList as list>
							<label><input TYPE="radio" VALUE="${list.applicantName}" onclick="quickQueryAppnt(this,this.value);" NAME="quickappnt">${list.applicantName}</label>
						</#list>
						</dd>
                </dl>
                <DIV class="clear"></DIV>
		</DIV>
		<DIV class="h10"></DIV>
		<DIV class="h2"></DIV>
		<input TYPE ="hidden" id="productName" NAME="sdinformation.productName" VALUE="${sdinformation.productName}">
	    <input TYPE ="hidden" id="productId" NAME="sdinformation.productId" VALUE="${sdinformation.productId}">
	    <input TYPE ="hidden" id ="artLoginFlag" NAME="artLoginFlag" VALUE="1" />
	    <input TYPE ="hidden" id="protectionPeriodTy" NAME="protectionPeriodTy" VALUE="${protectionPeriodTy}">
	    <input TYPE ="hidden" id="protectionPeriodLast" NAME="protectionPeriodLast" VALUE="${protectionPeriodLast}">
	    <input TYPE ="hidden" id="protectionPeriodFlag" NAME="protectionPeriodFlag" VALUE="${protectionPeriodFlag}"><p>
               <span class="span_t">*投保人姓名：</span> 
               <input type="text" id="applicantName" 
<#if (isLcx =="0")>class="tap_name"</#if>
name="sdinformationAppnt.applicantName" verify="投保人姓名|notnull&UFO&LEN>2&CHANDEH" 
onblur="verifyElement('投保人姓名|notnull&UFO&LEN>2&CHANDEH',this.value,this.id);"
onfocus="verifyShowInf(this.value,this.id);"
value="${(sdinformationAppnt.applicantName)!}"   maxlength=60 />
               <label class="requireField"></label>
                 <font color="#E7AC59" style="display:none"> 
<#if (isLcx !="0")>
<i class="yz_mes_des">请填写与有效证件相符的姓名，以保障您的权益</i>
</#if>
<#if (isLcx =="0")>
<i class="yz_mes_des">理财险投保人必须与付款银行卡持卡人姓名一致</i>
</#if>
</font>
<#if (isLcx =="0")>
<em class="other_tip" style="display:none"> <i class="yz_mes_des">理财险投保人必须与付款银行卡持卡人姓名一致</i></em>
</#if>
</p><div class="clearfix">
  <p class="from_table">
               <span  class="span_t">*证件号码：</span>
               <select id="applicantTypeId" class="typeoption"  name="sdinformationAppnt.applicantIdentityType" style="width:105px;" onchange="changeVerification('applicantTypeId','applicantId');">
                   <#list certificateList as list>
                       <option id="${list.flagType}"    value="${list.codeValue}"  <#if (sdinformationAppnt.applicantIdentityType==list.codeValue)>selected="selected"</#if>>${list.codeName}</option>
                   </#list>
				</select>
							 </p>
				 <p class="from_table2">	 
               <input id="applicantId" name="sdinformationAppnt.applicantIdentityId" maxlength=64 type="text" value="${(sdinformationAppnt.applicantIdentityId)!}"   
onfocus="verifyShowInf(this.value,this.id);"   
name="sdinformationAppnt.applicantIdentityId" 
verify="证件号码|NOTNULL&BIRAPPAGE"   
onblur="idcheck('applicantTypeId','applicantId','applicantBirthday','applicantSex','${sdinformation.InsuranceCompany}');"  
/>
<label class="requireField"></label> 
<font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请准确填写有效证件号码，以保障您的权益</i></font>
</p>
 </div><div class="pos-tb-p">
<p>
               <span class="span_t">*手机号码：</span>
               <input type="text" id="applicantMobile" 
<#if (isLcx =="0")> class="tap_name" </#if>name="sdinformationAppnt.applicantMobile" value="${(sdinformationAppnt.applicantMobile)!}"  
onfocus="verifyShowInf(this.value,this.id);" 
onblur="verifyElement('手机号码|MOBILENO',this.value,this.id)"
verify="手机号码|MOBILENO" maxlength=11 />
           	<label class="requireField"></label>
<font color="#E7AC59" style="display:none"> 
<#if (isLcx !="0")><i class="yz_mes_des">请填写正确的手机号</i></#if>
<#if (isLcx =="0")><i class="yz_mes_des">理财险投保人手机号必须真实，否则无法完成退保</i></#if>
</font>
<#if (isLcx =="0")>
<em class="other_tip" style="display:none"> <i class="yz_mes_des">理财险投保人手机号必须真实，否则无法完成退保</i></em></#if>
                   </p>
				   <#if (sdinformationAppnt.applicantMobile)??>
				   <label class="app_mobile" for="applicantMobile" style="display:none">
				   <#else>
				   <label class="app_mobile" for="applicantMobile" >
				   </#if>
                               用来接收保单生效通知</label> </div><div class="pos-tb-p">
 <p>
               <span class="span_t">*电子邮箱：</span>
               <input type="text" id="applicantMail" name="sdinformationAppnt.applicantMail" value="${(sdinformationAppnt.applicantMail)!}" 
onblur="verifyElement('电子邮箱|NOTNULL&EMAIL',this.value,this.id)" 
onfocus="verifyShowInf(this.value,this.id);"  onchange="verifyShowInf(this.value,this.id);"  
verify="电子邮箱|NOTNULL&EMAIL" />
               <label class="requireField"></label> <font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请填写常用邮箱，以便接收电子保单，此邮箱将自动成为您登录的账号</i></font>
           </p>
		   <#if (sdinformationAppnt.applicantMail)??>
				<label class="app_mobile" for="applicantMail" style="display:none">
		   <#else>
				<label class="app_mobile" for="applicantMail">
		    </#if>
					填写常用邮箱，接收电子保单邮件</label>
</div><p>
			           <span class="span_t">*所在地区：</span>
			           <select id="applicantArea1" name="sdinformationAppnt.applicantArea1" onchange="getChildrenArea(this.value,'applicantArea2','-1',this);" style="width:125px;">
			                   <option value="-1">--请选择--</option>
			                   <#list areaList as list>
			                   		<option value="${list.id}" <#if (sdinformationAppnt.applicantArea1==list.id)>selected="selected"</#if>>${list.name}</option>
			        			</#list>
		              </select>
		              <select id="applicantArea2" name="sdinformationAppnt.applicantArea2" verify="所在地区|AREAFORMAT" onblur="verifyElement('所在地区|AreaFormat',this.value,this.id)" style="width:125px;">
		                  <option value="-1">--请选择--</option> 
		              </select>
 <input type="text" id="applicantAddress" name="sdinformationAppnt.applicantAddress" maxlength=200
onblur="verifyElement('联系地址|NOTNULL&ADDRESS',this.value,this.id)"
 verify="联系地址|NOTNULL&ADDRESS" value="${(sdinformationAppnt.applicantAddress)!}"/>
					      <label class="requireField"></label>
			       </p><p>
				        <span class="span_t">*邮编：</span> 
				        <input type="text" id="applicantZipCode" name="sdinformationAppnt.applicantZipCode" value="${(sdinformationAppnt.applicantZipCode)!}" 
onblur="verifyElement('邮编|NOTNULL&ZIPCODE',this.value,this.id)"
verify="邮编|NOTNULL&ZIPCODE" maxlength=6 
/>
				        <label class="requireField"></label>
			        </p><p>
		    		<span class="span_t">*职业:</span>
		            <select name="sdinformationAppnt.applicantOccupation1" id="applicantOccupation1" onchange="getChildrenOPT(this.value,'applicantOccupation2',-1,this,'2');" style="width:125px;_width:160px;" verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)">
		             	<option value="-1">--请选择--</option>
		             	<#list occupationList as list>
		             		<option value="${list.id}" <#if (sdinformationAppnt.applicantOccupation1==list.id)>selected="selected"</#if>>${list.name}</option>	
		        		</#list>
		             </select> 
<select name="sdinformationAppnt.applicantOccupation2" id="applicantOccupation2" onchange="getChildrenOPT(this.value,'applicantOccupation3',-1,this,'3');"  style="width:125px;_width:237px;" verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)" >
<option value="-1">--请选择--</option>
</select>
<select name="sdinformationAppnt.applicantOccupation3" id="applicantOccupation3" style="width:125px;_width:237px;"  verify="职业|OccuFormat" onblur="verifyElement('职业|OccuFormat',this.value,this.id)">
<option value="-1">--请选择--</option>
</select>
<label class="requireField"></label>
</p><p>
	<span class="span_t">*证件有效日期：</span> 
	<input id="applicantStartID" name="sdinformationAppnt.applicantStartID" type="text" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d-18000}',maxDate:'%y-%M-{%d-1}'})"  onFocus = "effchange()"  value="${(sdinformationAppnt.applicantStartID)!}"  onblur="verifyElement('证件有效日期|NOTNULL',this.value,this.id)" verify="证件有效日期|NOTNULL" class="shop_day"/> 
	至
	<#if (sdinformationAppnt.applicantEndID == "9999-12-31")>
		<input id="applicantEndID" name="sdinformationAppnt.applicantEndID" type="text" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+0}',maxDate:'%y-%M-{%d+18000}'})"  onFocus = "effchange()" value=""  onblur="verifyElement('证件有效日期|NOTNULL',this.value,this.id)" class="shop_day" disabled="disabled"/>
		<input type="checkbox" id="Checkzjnum_long" onclick=CheckapplicantEndID('#applicantEndID','#Checkzjnum_long') checked="checked" style="width: 20px; border: 0px; padding-right: 0px; vertical-align: middle;">长期
	<#else>
		<input id="applicantEndID" name="sdinformationAppnt.applicantEndID" type="text" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+0}',maxDate:'%y-%M-{%d+18000}'})"  onFocus = "effchange()" value="${(sdinformationAppnt.applicantEndID)!}"  onblur="verifyElement('证件有效日期|NOTNULL',this.value,this.id)" verify="证件有效日期|NOTNULL" class="shop_day"/>
		<input type="checkbox" id="Checkzjnum_long" onclick=CheckapplicantEndID('#applicantEndID','#Checkzjnum_long')   style="width: 20px; border: 0px; padding-right: 0px; vertical-align: middle;">长期
	</#if>
	<label class="requireField"></label> 
</p></div>
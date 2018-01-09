<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-基本信息</title>
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<!--default.css artdialog样式-->
<link href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.doubleselect br{
display: none;
} 
</style>
 <script type="text/javascript">
 function onUploadImg(sender) {
		
		if (!sender.value.match(/.jpeg|.jpg|.gif|.png|.bmp/i)) {
			alert('图片格式无效！');
			return false;
		}
		var img = $('#jiazaidonghua');
		var img1 = $('#preview');
		img.attr('style', 'display:block');
		img1.attr('style', 'display:none');
		//submit();
		var options = { 
			url:'<%= serverContext%>/shop/profile!uploadImg.action', 
			async:true,
			type:"POST",  
			dataType: "json",
			resetForm:false,
		    success: function(data){
		    	img.attr('style', 'display:none');
				img1.attr('style', 'display:block');
		    	if (data.status == 'Y') {
					$('#preview').attr('src', '<%=Config.getValue("StaticResourcePath")%>/'+data.savePath);
				} else {
					alert(data.message);
				}
		    }
		    	
		}
		$('#uploadImgFrom').ajaxSubmit(options);
		
	}

</script>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>

<body class="up-bg-qh">
<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span>
			<a href='<%=Config.getFrontServerContextPath()%>' target='_self' ><span class="orange">您现在的位置：首页</span></a><span class="separator">>
			</span><a href='member_center!index.action' target='_self'>会员中心</a>>
			<span class="separator"></span><span class="separator1">基本信息</span>
			
		</div>
		<div class="member_con">
		    <jsp:include page="member_center_left.jsp"></jsp:include>
		    <form id="queryMember" target="_self" action="profile!edit.action" method="post"></form>
		    <div class="member_right mbr_bg">
		    	<!-- 基本信息 -->
		    	<div class="member_boxs">
		    		<div class="member-htit clear-bor-b"><span class="member-titsc">个人资料</span></div>
		    		<div class="member_overview">
		    			<div class="member_headphoto">
		    			<form enctype="multipart/form-data" method="post" target="touxiang" action="profile!uploadImg.action" name="uploadImgFrom" id="uploadImgFrom">
								<p class="bor_round" title="点击设置头像">
								<img id="jiazaidonghua" src="<%=Config.getValue("StaticResourcePath")%>/style/images/loading2.gif" width="84" height="84" style="display:none"/> 
<s:if test="member.headPicPath == null || member.headPicPath ==''">
<img id="preview" src="<%=Config.getValue("StaticResourcePath")%>/images/header_mo_03.jpg" width="84" height="84"/> 
</s:if>
<s:else>
<img id="preview" src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="member.headPicPath"/>" width="84" height="84" alt="头像" /> 
</s:else>
<span class="mem-rideobg"></span>
</p>
								<input type="file" size="3" name="uploads" tabindex="3" onchange="onUploadImg(this)" id="upload_img" class="filePrew">
								
								</form>
						</div>
						<div class="member_basicinfor up_member_basicinfor">
							<p class="member_guest">
								<s:if test="member.realName =='' || member.realName==null">开心保会员</s:if>
								<s:else><s:property value="member.realName"/></s:else>
							</p>
							<input type="hidden" name="oldtime2" id="olddate3"/>
						       <input type="hidden" name="timet" id="timest" value="0"/>
                           	   <input type="hidden" id="emailId" value="<s:property value="member.email"/>"/>
                           	   <input type="hidden" id="memberId" value="<s:property value="member.id"/>"/>
                           	    <input type="hidden" id="mailBinding" value="<s:property value="member.isEmailBinding"/>"/>
                           	    <input type="hidden" id="fullDegree" value="<%=session.getAttribute("fullDegree") %>">
                           	    <input type="hidden" id="phone" value="<s:property value="member.mobileNO"/>"/>
                           	    <input type="hidden" name="times" id="timess" value="0"/>
                           	    <input type="hidden" name="oldtime" id="olddate"/>
                           	<p class="level"><i class="<s:property value="#request.gradeClass"/>"></i>&nbsp;&nbsp;<s:property value="#request.gradeDesc"/></p>
                           	<p class="lv_txt"><s:property value="#request.upgradeInfo"/></p>
                           	<p>邮箱：<font class="member_detail" title="<s:property value="member.email"/>">
								<s:if test="member.email =='' || member.email==null"> 请验证您的邮箱 </s:if>
								<s:else><s:property value="member.email"/></s:else>
                            </font>
                            <s:if test="member.isEmailBinding==\"Y\"">
	                            <font class="member_verified">已验证</font>
	                            <span id="member_email_update" class="member_Immediatelyfasten">
	                            	  <s:if test="member.registerType!=0">
                           				 <a href="###" onclick="binding('emailModify');">修改</a>
	                            	  </s:if>
                          	    </span>
                            </s:if>
                          	<s:else>
                          		<font class="member_verified">未验证</font>
                          		<span class="member_Immediatelyfasten" id="member_email"><a href="javascript:void(0)" onclick="binding('email');">验证</a></span>
                          	</s:else>
                           	</p>
            				<p>手机：<font class="member_detail">
								<s:if test="member.mobileNO ==null || member.mobileNO ==''">请验证您的手机号码</s:if>
								<s:else>
	                            	<s:property value="member.mobileNO.substring(0,3)"/>****<s:property value="member.mobileNO.substring(7,11)"/>
                            	</s:else>
							</font>
							<s:if test="member.isMobileNOBinding==\"Y\"">
	                            <font class="member_verified">已验证</font>
	                            <span class="member_Immediatelyfasten" id="member_immer_update">
                           		<s:if test="member.registerType!=1">
                           			<a href="javascript:void(0)" onclick="binding('phoneModify');">修改</a>
	                            </s:if>
                            	</span>
                            </s:if>
                          	<s:else>
                          		<font class="member_verified">未验证</font>
                            	<span class="member_Immediatelyfasten" id="member_imme">
                          	    <a href="javascript:void(0)" onclick="binding('phone');">验证</a></span>
                          	</s:else>
							</p>
						</div>
						<div class="member_integral">
							<div class="member_integraltitle"><span class="member-titsc">您的积分</span><a href="point!newList.action#gm_id2">如何赚积分？</a></div>
							<div class="member_integralnum">
								<p class="member_inte"><font class="member_number use"><b><s:property value="member.currentValidatePoint"/></b></font>有效积分</p>
								<p class="member_inte"><font class="member_number"><b><s:property value="member.point"/></b></font>冻结积分</p>
								<p class="member_inte"><font class="member_number"><b><s:property value="member.aboutToExpirePoints"/></b></font>即将过期</p>
							</div>
							<div class="info_more">
								<p><a target="_blank" href="point!newList.action">查看详情</a></p>
								<p><a target="_blank" href="<%=Config.getFrontServerContextPath()%>/jifen/">积分兑换商品</a></p>
							</div>
							<div class="member_integrallink"><a target="_blank" href="<%=Config.getFrontServerContextPath()%>/jifen/"><span class="icon"></span>积分商城</a></div>
						</div>
		    		</div>
		    		<div class="member-htit clear-bor-b"><span class="member-titsc">修改资料</span></div>
		    		<s:form id="inputForm" name="inputForm" class="validate" action="member!profect.action"  method="post" >
                    	<div class="member_material"> 
                    	 <ul>
                    	 	<li>
                    	 		<input type="hidden" name="member.id" id="mid" value="<s:property value="member.id"/>"/>
                    	 		<input type="hidden" name="email" id="email" value="<s:property value="member.email"/>"/>
                    	 		
                    	 		<span class="member_passchangeNote">资料完整度:</span>
                    	 		<span class="member_progressbar">
                    	 		     <em >
	                    	 		     <var style="width:<s:property value="member.fullDegree"/>;"></var>
	                    	 		     <div style="margin-left:<s:property value="member.fullDegree"/>;"class="member_barRight"></div>
                    	 		     </em>
                    	 		</span>
                    	 		<span class="member_percentage"><s:property value="member.fullDegree"/> </span>&nbsp;&nbsp;&nbsp;&nbsp;<em class="vip_tip_ts_l" id="tips"></em>
                    	 	<li>
	                    	 	 <span class="member_passchangeNote" >真实姓名:</span>
	                    	 	 <input  type="text" id="rname"  name="realName" maxlength="100" value="<s:property value="member.realName"/>" class="member_nameinput member_defaut"   onblur="if(/^(([\u0391-\uFFE5])*([a-zA-Z]*))$/.test(this.value)){document.getElementById('isrm').innerHTML='';}" />&nbsp;&nbsp;
	                    	 	 <div class="user_des">
                                        <dl class="use_desli">
											<dd id="user_more_dd"><span id="user_more" class="user_more" style="display:none">更多>></span></dd>
											<dd class="user_tip"> 如果是ta们点下就可以哦！</dd>
                                        </dl>
                                        <span id="isrm" style="font-size:12px;color:red;"></span>
                                 </div>
                    	 	</li>
                    	 	<li>
                    	 	 	<span class="member_passchangeNote">性别:</span>
                    	 	 	&nbsp;&nbsp;
                    	 	 	<s:radio cssClass="sex_radios" list="#{0:'男',1:'女'}" value="member.sex" id="sex" name="member.sex"  listKey="key" listValue="value" ></s:radio>
                    	 	</li>
                    	 	<li>
                    	 	 	<span class="member_passchangeNote">出生日期:</span>
                    	 	 	<input type="text" id="wall"  name="member.birthday" value="<s:property value="member.birthday"/>" class="member_brithdateinput member_defaut"  onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});"   />
                    	 
                    	 	</li>
                    	 	<li>
                    	 	     <span class="member_passchangeNote">证件类型:</span>
								
								<s:select name="member.IDType" id="xuanzhong" cssClass="member_paperselect" list="listid" listValue="dictName" listKey="dictSerial" value="member.IDType" headerKey="-1" headerValue="--请选择--"></s:select>
					             <input type="text" id="IDNO"  maxlength="50" name="member.IDNO" value="<s:property value="member.IDNO"/>" class="member_nameinput member_defaut" value="&nbsp;&nbsp;请输入证件号码" onblur="if (this.value == '') {this.value = '&nbsp;&nbsp;请输入证件号码';}" onfocus="if (this.value == '&nbsp;&nbsp;请输入证件号码') {this.value = '';}"    />&nbsp;&nbsp;<span id="zjhmyz" style="font-size:12px;color:red;"></span>
                    	 	</li>  
                    	 	<li>
									<div class="doubleselect">
									<span class="member_passchangeNote">所属地区:</span>
                    	 	   		<s:doubleselect id="area" doubleId="city" doubleCssClass="member_paperselect" cssClass="member_paperselect"  headerKey="-1" headerValue="--请选择--"
                    	 	   		list="#request.areaList" listValue="areaName"  listKey="areaId" doubleName="member.location" value="areaId"
                    	 	   		doubleList="#request.cityMap[top.areaId]"  doubleListKey="cityId" doubleListValue="cityName" />
						            <input type="text" id="address"  name="member.address"  class="member_nameinput member_defaut" value="<s:property value="member.address"/>" onblur="if (this.value == '') {this.value = '&nbsp;&nbsp;请输入具体联系地址';}" onfocus="if (this.value == '&nbsp;&nbsp;请输入具体联系地址') {this.value = '';}"    />
									</div>
                    	 	     	
                    	 	</li> 
                    	 	<li>
                    	 	 	<span class="member_passchangeNote">邮编:</span>
                    	 	 	<input type="text" id="zipcode"  name="member.zipcode" maxlength="6" value="<s:property value="member.zipcode"/>" class="member_nameinput member_defaut"     />
                    	 	</li>  
                    	 	<li>
                    	 	     <span class="member_passchangeNote">婚姻状况:</span>
	                    	 	 <s:select name="member.marriageStatus" cssClass="member_paperselect" list="#{'0':'未婚','1':'已婚'}" value="member.marriageStatus" listKey="key" listValue="value"  headerKey="-1" headerValue="--请选择--"></s:select>
                    	 	</li>
                    	 	<li>
                    	 	     <span class="member_passchangeNote">会员类型:</span>
									<s:select name="member.VIPType" cssClass="member_paperselect" list="#{'Person':'个人','Company':'集体'}" value="member.VIPType" listKey="key" listValue="value"  headerKey="-1" headerValue="--请选择--"></s:select>
                    	 	</li> 
                    	 	<li>
                    	 	 	<span class="member_passchangeNote">职业类型:</span>
                    	 	 	<input type="text" id="industryType"  name="member.industryType" maxlength="120" value="<s:property value="member.industryType"/>" class="member_nameinput member_defaut"   />
                    	 	</li> 
                    	 	<li>
                    	 	 	<span class="member_passchangeNote">职位:</span>
                    	 	 	<input type="text" id="position"  name="member.position" maxlength="120" value="<s:property value="member.position"/>" class="member_nameinput member_defaut"    />
                    	 	</li> 
                    	 		
                            </ul>
                             <div class="zs_vip_mes zs_vip_s"></div>
                            <button type="button" class="member_materialsummit" onclick="validateForm()" >提交</button>
                    	</div>
                    </s:form>
		    	</div>
		    </div>
		</div>
</div>
<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/IDvalidate.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/member.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/profile_input.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
</body>
</html>

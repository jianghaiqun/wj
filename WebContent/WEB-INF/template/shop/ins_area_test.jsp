<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<title>福建省投保地址校验页</title>
<meta name="keyword" content="" />
<meta name="description" content="" />

<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/uploadify.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css">
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/shop/css/re_shops.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"/>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body>

	<!-- 顶部 开始 1-->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->
	
	
	
<div class="wrapper">
	 <div class="form">
 				  
			  <p style="padding-top: 50px;">
		           <span class="span_t">*所在地区：</span>
		           <select id="applicantArea1" onchange="getChildCity(this.value);" style="width: 125px;">
						<option value="-1">--请选择--</option>
						<s:iterator value="area" status="st" var="item">
							<option value="<s:property value="#item.id"/>"><s:property
									value="#item.name" /></option>
						</s:iterator>
					 </select>
					 
					 <select id="applicantArea2" style="width: 125px;"  verify="所在地区|AREAFORMAT">
						<option value="-1">--请选择--</option>
					</select>
					
					 <input type="text" id="address" class="ddhtxt" verify="联系地址|NOTNULL&amp;ADDRESS" /><label class="requireField"></label>
		       </p>
			   
			   
			   
		 </div>
			 <div class="syr2 syr33"> 
					<font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
						<input type="button" id="ddh_sub" value="点击校验  " class="next_btn dev_submitButton" >
					</font></font>
		 			<div id="ddh_subdef" class="londing_mes" style="display:none"><img src="http://resource.kaixinbao.com/images/nloading.gif" width="20px" height="20px" alt="">正在缓存，请稍等</div>
		 			<div id="ddh_msg" class="londing_mes" style="color: red;display:none " >正在缓存，请稍等</div>
		 			
 			</div>
 			      
</div>	  
        	

<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
<!-- 底部结束 -->

<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/redesign/re_base.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/stencil/stencil_order.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/redesign/re_footer.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/artDialog.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/iframeTools.js" ></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/login.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/redesign/re_header.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/redesign/xiaoneng_CustomerService.js?version=20160930"></script>
<script type="text/javascript" src="http://dl.ntalker.com/js/xn6/ntkfstat.js?siteid=kf_9401" charset="utf-8"></script>
<script src="<%=Config.getValue("StaticResourcePath")%>/active_file/js/validate.js" type="text/javascript"></script>


<script>

	
	function getChildCity(areaId){
		jQuery.ajax({
			type:"post",
			url :sinosoft.base + "/shop/ins_area!getChildArea.action?areaId="+areaId,
			dataType : "JSON",
	        success:function(de){
	        	if(de!=null){
	        		 fillChildCity(de);
	        	} 
	        }
		});
	}
	
	function fillChildCity(item){
		var obj=document.getElementById('applicantArea2');
		obj.options.length=0;
		var json = eval(item);
	    for(var i=0;i<item.length;i++){
	    	var id = json[i].id;
	    	var name = json[i].name;
	    	$("#applicantArea2").append("<option value=" + id + ">" + name + "</option>");
	    }
	}
	
	
		jQuery(document).ready(
			function($) {
				/* 录入信息校验 */
				$(document).wjDataVelify({
					sbtBtn : "#ddh_sub", //提交按钮
					callBack : function() { //回调函数
						/*按钮样式变化*/
						jQuery("#ddh_sub").hide();
						jQuery("#ddh_subdef").show();
						var area1=$("#applicantArea1").find("option:selected").text();
						var area2=$("#applicantArea2").find("option:selected").text();
						var address = jQuery('#address').val();
						
						if(area1 == "--请选择--" ||   area2 == "--请选择--" ||  address == ""){
							alert("地址填写不全");
							jQuery("#ddh_sub").show();
							jQuery("#ddh_subdef").hide();
						}else{
						jQuery.ajax({
								type : "post",
								url : sinosoft.base + "/shop/ins_area!checkArea.action",/* sinosoft.base */
								dataType : "JSON",
								data : {
									"area1" : area1,
									"area2" : area2,
									"address" : address,
										},
								success : function(data) {
										jQuery("#ddh_subdef").hide();
										jQuery("#ddh_msg").show();
										jQuery("#ddh_sub").show();
										
										if (data == "1") {
											
											jQuery("#ddh_msg").html("地址校验通过");
											
										  } else {
											jQuery("#ddh_msg").html("地址校验不通过");
										 }
								 	}
							  });
							}
						}
				});
			});
	</script>
</body>
</html>
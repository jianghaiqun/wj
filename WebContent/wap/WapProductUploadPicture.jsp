<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>上传wap图片</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>

</head>
<body>
 <z:init method="com.sinosoft.wap.ProductManage.initUploadPictureDialog">
 <br></br>
 	<input type="hidden" id="wapPictureUrl" value="">
 	<input type="hidden" id=productId value="<%=request.getParameter("productid")%>">
 	<input type="hidden" id=submitType value="add">

			<tr>
			 <td>
				&nbsp;&nbsp;图片类型： <z:select name="type" id="type" style="width:120px;">${WapProductPictureType}</z:select>&nbsp;&nbsp; 
				 上传图片： <input type="file" id="imgTest"  accept=".gif,.jpg,.jpeg,.png"> 
				 &nbsp;&nbsp;  <input   type="button" id="submitPicture" onclick="gen_base64()" value = "上传" width:820px" > 
				 
				 </td>
			</tr>
  
 </z:init>
      
</body>
<script> 


   function gen_base64() {
	   var file = "";
	   try{
		   file = jQuery('#imgTest')[0].files[0];
	   } catch(error) {
		   Dialog.alert("您使用的浏览器版本过低，请升级至新版本或使用兼容视图！");
		   return;
	   }
       if(jQuery(imgTest).val()==""){
    	   Dialog.alert("请选择上传路径");
    	   return;
       } 
       r = new FileReader();  
       var imgBase64="";
       r.onload = function(){
    	   imgBase64 = r.result; 
     	    	var dc = new DataCollection();
    	        dc.add("pictureFormat",getPictureFormat(imgTest));
    	        dc.add("productId",jQuery('#productId').val());
    	        dc.add("imgBase64",imgBase64);
    	        dc.add("type",$V("type"));
    	    	Server.sendRequest("com.sinosoft.wap.ProductManage.uploadPicture",dc,function(response){
    	 		Dialog.alert(response.Message,function(){
    	 			if(response.Status==1){
    	 	    	   Dialog.getInstance('Diag2').close();
    	 	    	  window.Parent.doSearch();
    	 			}
    	 		}) 
    	 	}); 
       }
       r.readAsDataURL(file);  
       
         
   }
   
   function getPictureFormat(imgTest){
	   var formatIs = jQuery(imgTest).val();
	   formatIs = formatIs.substr(formatIs.lastIndexOf(".")).toLowerCase();
	   return formatIs;
   }


   
</script>
</html>

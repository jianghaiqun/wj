<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.cms.resource.ConfigImageLib"%>
<%@page import="com.sinosoft.framework.utility.Mapx"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style>
.ErrorMsg {
background:#FFF2E9   scroll 5px;
border:1px solid #FF6600;
color:#000000;
padding:5px 5px 5px 25px;
}

#preview_wrapper{   
    display:inline-block;   
    width:125px;   
    height:340px;   
}   
.preview_fake{ /* 该对象用户在IE下显示预览图片 */   
    filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);   
}
#preview_size_fake{ /* 该对象只用来在IE下获得图片的原始尺寸，无其它用途 */
position:absolute;
    filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);     
}   

</style>
<script src="../Framework/Main.js"></script>
<script type="text/javascript"><!--
var oralWidth = 450;
var oralHeight = 450;
var imagefilesize;
var allowsize = 153;  

var extsStr = '<%=Config.getValue("AllowImageExt")%>';
var exts = extsStr.split(",");

function upload(){
	var flag = false;
	var count = 1;  //5
	for(var i=1;i<=count;i++){
			var imgName = $V("File");
			var ext = imgName.substring(imgName.lastIndexOf(".")+1).toLowerCase();
			if(imgName==""){
				continue;
			}
			flag=false;
			for(var j=0; j<exts.length; j++) {
				if(ext.trim()==exts[j].toLowerCase().trim()) {
					flag = true;
				}
			}
			if(!flag) {
				Dialog.alert("图片上传不支持"+ext+"文件，请重新选择！");
				return;
			}
	}
	if(flag){
	    $E.disable(window.Parent.$D.CancelButton);
	    $E.disable(window.Parent.$D.OKButton);
		$F("form2").submit();
	}else{
		Dialog.alert("请先浏览选择文件!");
		return;
	}
}

function getExtname(filename){
	var pos = filename.lastIndexOf("\\");
	return (filename.substr(pos+1));
}

function doPreview(sender){   
	
    var flag = 0;
    for(var i=0; i<exts.length; i++) {
	  	if(sender.value.toLowerCase().trim().endsWith("." + exts[i].toLowerCase().trim())) {
			flag = 1;
	  	}
	}
    if(flag == 0) {
    	Dialog.alert('图片格式无效！');
        return false;   
	}

	if(!sender.value.toLowerCase().trim().endsWith(".zip")) {
	    var objPreview = document.getElementById( 'preview'+sender.id );
	    var objPreviewFake = document.getElementById( 'preview_fake'+sender.id );   
	    var objPreviewSizeFake = document.getElementById( 'preview_size_fake' );   
	    
	    oralWidth = objPreviewSizeFake.offsetWidth;
	    oralHeight = objPreviewSizeFake.offsetHeight;
	    
	    if( sender.files &&  sender.files[0] ){
	        objPreview.style.display = 'block';   
	        objPreview.style.width = 'auto';   
	        objPreview.style.height = 'auto';   
	           
	        // Firefox 因安全性问题已无法直接通过 input[file].value 获取完整的文件路径   
	        objPreview.src = sender.files[0].getAsDataURL();
	        
	        //得到FF中的图片的大小并判断
	        imagefilesize = Math.round(sender.files[0].fileSize / 1024 * 100) / 100; //kb  
	    }else if( objPreviewFake.filters ){    
	        // IE7,IE8 在设置本地图片地址为 img.src 时出现莫名其妙的后果   
	        //（相同环境有时能显示，有时不显示），因此只能用滤镜来解决   
	           
	        // IE7, IE8因安全性问题已无法直接通过 input[file].value 获取完整的文件路径   
	        sender.select();   
	        var imgSrc = document.selection.createRange().text;   
	           
	        objPreviewFake.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = imgSrc;   
	        objPreviewSizeFake.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = imgSrc;   
	        
	        //得到ie中的图片的大小并判断
	        var img = new Image();
	        //img.dynsrc = imgSrc;
	        img.DYNSRC = imgSrc;
	        imagefilesize =Math.round(img.fileSize / 1024 * 100) / 100; //kb
	        
	        autoSizePreview( objPreviewFake,    
	        objPreviewSizeFake.offsetWidth, objPreviewSizeFake.offsetHeight );   
	      
	        objPreview.style.display = 'none';
	    }
	} 
}   
  
function onPreviewLoad(sender){ 
    autoSizePreview( sender, sender.offsetWidth, sender.offsetHeight );   
}   
  
function autoSizePreview( objPre, originalWidth, originalHeight ){
    var zoomParam = clacImgZoomParam( 120, 120, originalWidth, originalHeight );  
    objPre.style.width = zoomParam.width + 'px';   
    objPre.style.height = zoomParam.height + 'px';   
    objPre.style.marginTop = zoomParam.top + 'px';   
    objPre.style.marginLeft = zoomParam.left + 'px';
    //判断图片的大小是否大于制定高度
    if(imagefilesize>allowsize){
    	alert("图片大于1MB,系统将自动压缩图片,也可在制定缩略图高,图片原始高度为"+oralHeight);
    }  
}   
var n = 0;  
function clacImgZoomParam(maxWidth, maxHeight, width, height ){
	n = n+1;
	if(n>1){
		oralWidth = width;
		oralHeight = height;
	}
	
    var param = { width:width, height:height, top:0, left:0 };   
    if( width>maxWidth || height>maxHeight ){   
        rateWidth = width / maxWidth;   
        rateHeight = height / maxHeight;   
           
        if( rateWidth > rateHeight ){   
            param.width =  maxWidth;   
            param.height = height / rateWidth;   
        }else{   
            param.width = width / rateHeight;   
            param.height = maxHeight;   
        }   
    }   
       
    param.left = (maxWidth - param.width) / 2;   
    param.top = (maxHeight - param.height) / 2;   
       
    return param;   
}   

function onUploadCompleted(returnValue,returnID,errorMessage){
	switch ( returnValue )
	{
		case 0 :	// No errors
			break ;
		case 1 :	// 
			Dialog.alert(errorMessage) ;
			$E.enable(window.Parent.$D.CancelButton);
    		$E.enable(window.Parent.$D.OKButton);
			return ;
		case 202 :
			Dialog.alert( '无效的文件类型！以下文件上传失败:'+errorMessage ) ;
			return ;
		case 203 :
			Dialog.alert( "您没有权限上传此文件，请检查服务器设置" ) ;
			return ;
		default :
			Dialog.alert( '上传失败: ' + errorMessage ,function(){
				try {
		  			window.Parent.Dialog.close();
	    		}catch(ex){
	    		}
			}) ;
			return ;
	}
	window.Parent.onReturnBack(returnID);
	try {
	  window.Dialog.close();
  }catch(ex){}
}

function onImageOver(ele){
	ele.style.backgroundColor='#fffabf';
}

function onImageOut(ele){
	ele.style.backgroundColor='';
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div style="display:none"><iframe name="formTarget"
	src="javascript:void(0)"></iframe></div>
<z:init>
<form enctype="multipart/form-data" id="form2" action="../Editor/filemanager/upload/simpleuploader.jsp" method="post" target="formTarget">
<input type="hidden" id="FileType" name="FileType" value="Image">
<input type="hidden" id="productId" name="productId" value="${productId}">
<input type="hidden"id="flag" name="flag" value="${flag}"/>

<table width="760" cellpadding="3" cellspacing="0">
	<tr>
		<td width="75%" valign="top">
		<table width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td style="height:190px" valign="top">
				<fieldset><legend>图片上传:(支持<%=Config.getValue("AllowImageExt")%>文件上传)</legend>
				<table id="imagetable0" style="display:" width="100%"
					cellpadding="2" cellspacing="0">
					<tr>
						<td><input name='File' id='File' type='file' value=''
							size='30' onChange="doPreview(this);"></td>
					</tr>
				</table>
				</fieldset>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</z:init>
</body>
</html>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>

<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <title></title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css" />
    <script src="../Framework/Main.js"></script>
    <script type="text/javascript">

    Page.onLoad(function(){
		var Image = $V("Image");
		if(Image!=null && Image !=''){
			$("Img").src =$V("UploadFilePath") + Image;
		}
	});
    function imageBrowse(ele) {
        var diag = new Dialog("Diag4");
        diag.Width = 800;
        diag.Height = 460;
        diag.Title = "浏览图片库";
        diag.URL = "Resource/ImageLibDialogCover.jsp?Search=Search&SelectType=radio";
        diag.OKEvent = BrowseOK;
        diag.show();
    }

    function BrowseOK() {
        if ($DW.Tab.getCurrentTab() == $DW.Tab.getChildTab("ImageUpload")) {
            $DW.Tab.getCurrentTab().contentWindow.upload();
        } else if ($DW.Tab.getCurrentTab() == $DW.Tab.getChildTab("ImageBrowse")) {
            $DW.Tab.getCurrentTab().contentWindow.onReturnBack();
        }
    }

    function onReturnBack(returnID){
    	var ImgID = returnID.split(",");
    	var dc = new DataCollection();
    	dc.add("ImgID",ImgID[0]);
    	Server.sendRequest("com.sinosoft.cms.dataservice.TopAdInformation.getImgSrc",dc,function(response){
    		$S("Image",response.get("ImgSrc"));
    		$("Img").src = $V("UploadFilePath")+response.get("ImgSrc");
    	});
    }
    
    function lastPageChange(){
    	var display = document.getElementById("divLink").style.display;
    	
    	if ("none" == display) {
    		document.getElementById("divLink").style.display = "block";
    	}else{
    		document.getElementById("Link").value = "";
    		document.getElementById("divLink").style.display = "none";
    	}
    }
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body class="dialogBody">
    <z:init method="com.sinosoft.cms.dataservice.ShoppingGuide.initDialog">
        <form id="form2">
            <input type="hidden" id="UploadFilePath" name="UploadFilePath" value="${UploadFilePath}" />
            <input type="hidden" id="Image" name="Image" value="${Image}"/>
            <table width="500" align="center" cellpadding="2" cellspacing="0">
                <tr>
                    <td width="117" height="10"></td>
                    <td width="373"></td>
                </tr>
                <tr>
                    <td align="right">上级导购频道：</td>
                    <td height="30">
                        <z:select id="ParentId"> ${ParentId} </z:select>
                    </td>
                </tr>
                <tr>
                    <td align="right">导购频道名称：</td>
                    <td height="30">
                        <input id="Name" name="Name" type="text" value="${Name}" class="input1" verify="导购频道名称|NotNull" size="36" />
                    </td>
                </tr>
                <tr>
                    <td align="right">导购频道描述：</td>
                    <td height="30">
                        <input id="Prop1" name="Prop1" type="text" value="${Prop1}" class="input1" size="50" />
                    </td>
                </tr>
                <tr>
                    <td valign="top" colspan="2">
                        <table width='100%' border='0' cellpadding='4' cellspacing='0' id="imageTable">
                            <tr>
                                <td colspan='2' align='center' bgcolor="#E6F8FF"><b>图片设置</b></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div style="border:#D9D9D9 dotted 1px;">
                                        <table id="imgADTable" width="100%" cellpadding='2'>
                                            <tr>
                                                <td width="75%">
                                                	<table>
                                                		<tr>
			                                                <td width="100%" style="border-bottom:1px dotted #D9D9D9;">
			                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最终页：
			                                                    <z:select id="YesOrNo" onChange="lastPageChange()" value="N">${YesOrNo}</z:select>
			                                                </td>
                                                		<tr>
                                                		</tr>
			                                                <td width="100%" style="border-bottom:1px dotted #D9D9D9;">
			                                                	<div id="divLink" style="display:none;">
			                                                    &nbsp;&nbsp;链接地址：
			                                                    <input type="text" id="Link" name="Link" value="${Link}" size="40" />
			                                                    </div>
			                                                </td>
                                                		</tr>
                                                	</table>
                                                </td>
                                                <td width="10%" style="border-bottom:1px dotted #D9D9D9;" rowspan="2">
                                                	<img style="cursor:pointer;" title="点击选择或上传图片" alt="" onerror="this.alt='图片载入失败'" onClick="imageBrowse(this);" src="${UploadFilePath}/Images/addpicture.jpg" width="120" height="90" id="Img">
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>上传推荐有礼活动页面图片</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js"></script>
    <script type="text/javascript" src="../template/common/js/jquery.js"></script>

</head>
<body>
<z:init method="com.wangjin.activity.ActivityInfo.initUploadPictureDialog">
    <br></br>
    <input type="hidden" id=activitySn value="<%=request.getParameter("activitySn")%>">
    <tr>
        <td>
            &nbsp;&nbsp;图片类型： <z:select name="location" id="location"
                                        style="width:120px;">${ActivityImgLocation}</z:select>&nbsp;&nbsp;
            上传图片： <input type="file" id="imgTest" accept=".gif,.jpg,.jpeg,.png">
            &nbsp;&nbsp; <input type="button" id="submitPicture" onclick="gen_base64()" value="上传">
        </td>
    </tr>
</z:init>

</body>
<script>
    function gen_base64() {
        var file = "";
        try {
            file = jQuery('#imgTest')[0].files[0];
        } catch (error) {
            Dialog.alert("您使用的浏览器版本过低，请升级至新版本或使用兼容视图！");
            return;
        }
        if (jQuery('#imgTest').val() == "") {
            Dialog.alert("请选择上传路径");
            return;
        }
        r = new FileReader();
        var imgBase64 = "";
        r.onload = function () {
            imgBase64 = r.result;
            var dc = new DataCollection();
            var image = jQuery('#imgTest').val();
            var imageName = image.substring(image.lastIndexOf("\\")+1).toLowerCase();
            dc.add("image", imageName);
            dc.add("activitySn", jQuery('#activitySn').val());
            dc.add("imgBase64", imgBase64);
            dc.add("location", $V("location"));
            Server.sendRequest("com.wangjin.activity.ActivityInfo.uploadPicture", dc, function (response) {
                Dialog.alert(response.Message, function () {
                    if (response.Status == 1) {
                        Dialog.getInstance('Diag2').close();
                        window.Parent.doSearch();
                    }
                })
            });
        }
        r.readAsDataURL(file);
    }
</script>
</html>

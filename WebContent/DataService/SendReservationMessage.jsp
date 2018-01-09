<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>发送健康保活动上线通知</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../Framework/Main.js"></script>
</head>
<body>


<table width="100%" border="0" cellspacing="6" cellpadding="0"
       style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td>
            <form action="#" id="form">
                <label for="openid">openid</label><input type="text" name="openid" id="openid"
                                                         verify="用户openid|NotNull">
                <input id="sendBtn" type="button" value="发送(测试)" onclick="send()">
            </form>
            <br>
            <input id="sendAllBtn" type="button" value="发送到全部" onclick="send2All()">
        </td>
    </tr>
</table>
<script type="text/javascript">
    function send() {
        if (Verify.hasError()) {
            return;
        }

        Dialog.alert("请耐心等待,正在发送中......");
        var dc = Form.getData($F("form"));
        Server.sendRequest("com.sinosoft.cms.dataservice.SendWeiXinTemplateMessageUI.send", dc, function (response) {
            Dialog.closeAlert();
            console.log(response)
            Dialog.alert(response.Message);
        });
    }

    function send2All() {
        Dialog.alert("请耐心等待,正在发送中......");

        var dc = new DataCollection();
        Server.sendRequest("com.sinosoft.cms.dataservice.SendWeiXinTemplateMessageUI.send2All", dc, function (response) {
            Dialog.closeAlert();
            console.log(response)

            Dialog.alert(response.Message);
        });
    }

</script>
</body>
</html>
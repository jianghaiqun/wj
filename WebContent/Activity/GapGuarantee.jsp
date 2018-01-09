<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js" type="text/javascript"></script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
       style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td><z:tab>
            <z:childtab id="GapGuaranteeCopywriting1"
                        src="../Platform/GapGuaranteeCopywriting1.jsp" selected="true">
                <img src="../Icons/icon002a1.gif"/>
                <b>十年存款能力比拼文案</b>
            </z:childtab>
            <%-- <z:childtab id="GapGuaranteeCopywriting2" onClick="onTabChange('GapGuaranteeCopywriting2')"
                src="about:blank">
                <img src="../Icons/icon023a7.gif" width="20" height="20" />
                <b>保险测评结果标题文案</b>
            </z:childtab> --%>
            <z:childtab id="GapGuaranteeCopywriting3"
                        src="../Platform/GapGuaranteeCopywriting3.jsp">
                <img src="../Icons/icon018a1.gif"/>
                <b>保障缺口测评结果页标签文案</b>
            </z:childtab>
            <%-- <z:childtab id="GapGuaranteeCopywriting4" onClick="onTabChange('GapGuaranteeCopywriting4')"
                src="about:blank">
                <img src="../Icons/icon018a1.gif" />
                <b>缺口保障社保情况文案</b>
            </z:childtab> --%>
            <z:childtab id="GapGuaranteeProvinceData"
                        src="../Platform/GapGuaranteeProvinceData.jsp">
                <img src="../Icons/icon018a1.gif"/>
                <b>各省统计数据</b>
            </z:childtab>
            <z:childtab id="WxSecurityData"
                        src="../Platform/WxSecurityData.jsp">
                <img src="../Icons/icon018a1.gif"/>
                <b>缺口保障数据统计</b>
            </z:childtab>
            <z:childtab id="WeiXinSecurityCouponsConfig"
                        src="weixin_security_coupons.jsp">
                <img src="../Icons/icon018a1.gif"/>
                <b>缺口保障优惠券管理</b>
            </z:childtab>
        </z:tab></td>
    </tr>
</table>
</body>
</html>

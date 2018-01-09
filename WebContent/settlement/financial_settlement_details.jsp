<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>财务结算</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js"></script>
    <script>
        var currentTab = "Coverage_cx";
        function onTabChange(tab) {
            currentTab = tab;
            var url;
            url = Server.ContextPath + "Platform/" + currentTab + ".jsp";
            if (Tab.getChildTab(tab).src.indexOf(url) < 0) {
                Tab.getChildTab(tab).src = url;
            }
        }
        Page.onLoad(function () {
            url = Server.ContextPath + "Platform/" + currentTab + ".jsp";
            if (Tab.getChildTab(currentTab).src.indexOf(url) < 0) {
                Tab.getChildTab(currentTab).src = url;
            }
        });
    </script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="6">
    <tr>
        <td height="26"><z:tab>
            <z:childtab id="details" src="financial_settlement_details_tab.jsp"  lazy="false"  selected="true">
                <img src='../Icons/icon003a15.gif'/>
                <b>财务结算明细查询</b>
            </z:childtab>
            <z:childtab id="statistics_new" src="financial_settlement_statistics_new_tab.jsp" lazy="false">
                <img src='../Icons/icon003a15.gif'/>
                <b>财务结算统计</b>
            </z:childtab>
            <z:childtab id="statistics" src="financial_settlement_statistics_tab.jsp" lazy="false">
                <img src='../Icons/icon003a15.gif'/>
                <b>费用汇总统计</b>
            </z:childtab>
            <z:childtab id="importData" src="financial_settlement_details_import_tab.jsp" lazy="false">
                <img src='../Icons/icon003a20.gif'/>
                <b>导入财务结算明细</b>
            </z:childtab>
            <z:childtab id="Coverage_cx" onClick="onTabChange('Coverage_cx')" src="about:blank" lazy="false">
                <img src="../Icons/icon002a1.gif"/>
                <b>产险分类</b>
            </z:childtab>
            <z:childtab id="Coverage_rsx" onClick="onTabChange('Coverage_rsx')" src="about:blank" lazy="false">
                <img src="../Icons/icon002a1.gif" width="20" height="20"/>
                <b>人身险分类</b>
            </z:childtab>
        </z:tab></td>
    </tr>
</table>
</body>
</html>

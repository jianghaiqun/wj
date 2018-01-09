<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js"></script>
    <script src="../Framework/OrderTree.js"></script>

</head>
<body onContextMenu="return false;">
<z:tree id="tree1" method="com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.branchTreeDataBind" level="3"
        lazy="true" resizeable="true">
    <img src="../Framework/Images/icon_drag.gif" width="16" height="16">
    <p cid='${ID}' level="${TreeLevel}" cname="${Name}"><input type="checkbox"
                                                               name="contant" id='contant_${ID}' value='${ID}'
                                                               onClick="onCheck(this);">
        <label for="contant_${ID}">${Name}</label>
    </p>
</z:tree>
</body>

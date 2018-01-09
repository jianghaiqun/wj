<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <title></title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js"></script>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<form id="form2">
    <table width="100%" cellpadding="2" cellspacing="0">
        <tr>
            <td width="90" height="10"></td>
            <td height="10"></td>
        </tr>
        <tr>
            <td align="right">批次号：</td>
            <td><input name="CodeValue" type="text" id="CodeValue" size=30 verify="批次号|NotNull"/></td>
        </tr>
        <tr>
            <td align="right">名称：</td>
            <td><input name="CodeName" type="text" id="CodeName" size=30 verify="名称|NotNull"/></td>
        </tr>
        <tr>
            <td align="right">备注：</td>
            <td><input name="Memo" type="text" id="Memo" size=30/></td>
        </tr>
    </table>
</form>
</body>
</html>
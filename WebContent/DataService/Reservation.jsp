<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>预约信息管理</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js"></script>
</head>
<body>


<table width="100%" border="0" cellspacing="6" cellpadding="0"
       style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
                <z:init method="com.sinosoft.cms.dataservice.TeamReservation.init">
                    <tr>
                        <td>
                            <label for="reservationCode">预约批次代码:</label><input type="text" name="reservationCode"
                                                                               id="reservationCode">
                            <label for="productName">预约产品名称:</label><input type="text" id="productName"
                                                                           name="customerTel">
                            <label for="customerTel">预约手机号:</label><input type="text" id="customerTel"
                                                                          name="customerTel">
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <label for="reservationStartDate">预约时间 从:</label><input name="reservationStartDate"
                                                                                    id="reservationStartDate"
                                                                                    verify="Date"
                                                                                    value="${reservationStartDate}"
                                                                                    type="text"
                                                                                    size="15" ztype="date"/>
                            <label for="reservationEndDate">至:</label><input name="reservationEndDate"
                                                                             id="reservationEndDate"
                                                                             verify="Date"
                                                                             value="${reservationEndDate}" type="text"
                                                                             size="15"
                                                                             ztype="date"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <z:tbutton onClick="doSearch()"><img src="../Icons/icon403a3.gif" width="20"/>查询</z:tbutton>
                            <input type="button" title="默认使用当前查询条件导出统计数据" value="导出Excel" onclick="writeFile()">
                        </td>
                    </tr>
                </z:init>
                <tr>
                    <td style="padding: 0px 6px 8px;">
                        <z:datagrid id="dg3" method="com.sinosoft.cms.dataservice.TeamReservation.reservationDGA"
                                    size="15"
                                    lazy="true"
                                    scroll="true">
                            <table width="100%" cellpadding="2" cellspacing="0"
                                   class="dataTable">
                                <tr ztype="head" class="dataTableHead">
                                    <td width="15%"><b>预约产品</b></td>
                                    <td width="10%"><b>预约代码</b></td>
                                    <td width="10%"><b>客户名称</b></td>
                                    <td width="10%"><b>联系方式</b></td>
                                    <td width="5%"><b>新老用户</b></td>
                                    <td width="15%"><b>关联会员用户名</b></td>
                                    <td width="15%"><b>所在地区</b></td>
                                    <td width="10%"><b>预约创建日期</b></td>
                                </tr>
                                <tr style="background-color:#F9FBFC">
                                    <td title="${productName}">${productName}</td>
                                    <td title="${reservationCode}">${reservationCode}</td>
                                    <td title="${customerName}">${customerName}</td>
                                    <td title="${customerTel}">${customerTel}</td>
                                    <td title="${isOldMember}">${isOldMemberName}</td>
                                    <td title="${username}">${username}</td>
                                    <td title="${areaShow}">${areaShow}</td>
                                    <td title="${createDate}">${createDate}</td>
                                </tr>
                                <tr ztype="pagebar">
                                    <td height="25" colspan="11">${PageBar}</td>
                                </tr>
                            </table>
                        </z:datagrid>

                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<script type="text/javascript">
    function doSearch() {
        if (Verify.hasError()) {
            return;
        }
        DataGrid.setParam("dg3", Constant.PageIndex, 0);
        DataGrid.setParam("dg3", "reservationStartDate", $V("reservationStartDate"));
        DataGrid.setParam("dg3", "reservationEndDate", $V("reservationEndDate"));
        DataGrid.setParam("dg3", "reservationCode", $V("reservationCode"));
        DataGrid.setParam("dg3", "productName", $V("productName"));
        DataGrid.setParam("dg3", "customerTel", $V("customerTel"));

        DataGrid.loadData("dg3");
    }

    function writeFile() {
        if (Verify.hasError()) {
            return false;
        }
        doSearch();
        DataGrid.toExcel("dg3", true);

    }
</script>
</body>
</html>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>统计计划管理</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js" type="application/javascript"></script>
    <script src="../Framework/OrderTree.js" type="application/javascript"></script>

</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
       style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="6"
                   class="blockTable">
                <tr>
                    <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif"/>会员回购统计</td>
                </tr>
                <tr>
                    <td>
                        <z:init method="com.sinosoft.cms.memberreport.StatisticsPlanRecordManagement.init">
                            <table>
                                <tr>
                                    <td>统计日期：</td>
                                    <td><input name="startDate" type="text" id="startDate" value="${startDate}"
                                               style="width: 90px" class="inputText" ztype="date"></td>
                                    <td>到：</td>
                                    <td><input name="endDate" type="text" id="endDate" value="${endDate}"
                                               style="width: 90px" class="inputText" ztype="date"></td>
                                    <td>创建时间:</td>
                                    <td><input name="createDate" type="text" id="createDate" value="${createDate}"
                                               style="width: 90px" class="inputText" ztype="date"></td>
                                </tr>
                                <tr>
                                    <td colspan="6"><input type="button" name="Submit" value="查询" onClick="doSearch()">
                                    </td>
                                </tr>
                            </table>
                        </z:init>
                    </td>
                </tr>
                <tr>
                    <td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
                        <z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.StatisticsPlanRecordManagement.dg1DataBind"
                                    lazy="true" size="20">
                            <table width="100%" cellpadding="2" cellspacing="0"
                                   class="dataTable" style="text-align: center;">
                                <tr ztype="head" class="dataTableHead">
                                    <td width="30" height="30" ztype="RowNo" drag="true"><img
                                            src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                                    <td width="30" height="30" ztype="Selector" field="id">&nbsp;</td>
                                    <td width="10%">计划名称</td>
                                    <td width="20%">统计URL</td>
                                    <td width="12%">统计起始日期</td>
                                    <td width="12%">统计截止日期</td>
                                    <td width="12%">统计执行时间</td>
                                    <td width="5%">查询状态</td>
                                   <%-- <td width="5%">下载状态</td>--%>
                                    <td width="10%">计划创建人</td>
                                    <td width="12%">计划创建时间</td>
                                    <td width="5%">操作</td>
                                </tr>
                                <tr style1="background-color:#FFFFFF"
                                    style2="background-color:#F9FBFC">
                                    <td onmouseover="">&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td title="${plan_name}">${plan_name}</td>
                                    <td title="${source_url}">${source_url}</td>
                                    <td title="${start_date}">${start_date}</td>
                                    <td title="${end_date}">${end_date}</td>
                                    <td title="${rc}">${rc}</td>
                                    <td title="${query_status}">${query_statusName}</td>
                                   <%-- <td title="${download_status}">${download_statusName}</td>--%>
                                    <td title="${create_user}">${create_user}</td>
                                    <td title="${pc}">${pc}</td>
                                    <td>
                                        <a href="<%=Config.getValue("ContextPath") %>${download_url}"
                                           target="_blank">
                                            <img src="../Framework/Images/icon_down.gif" width="15" height="15"
                                                 style="cursor:pointer;" alt="下载" title="${download_file_name}}"/>
                                        </a>
                                        <span>&nbsp;</span>
                                        <a href="javascript:void(0)" onclick="reExecute(${id})">
                                            <img src="../Icons/icon003a13.gif" width="15" height="15"
                                                 style="cursor:pointer;" alt="重新执行" title="重新执行"/>
                                        </a>
                                    </td>
                                </tr>
                                <tr ztype="pagebar">
                                    <td height="25" colspan="11">${PageBar}</td>
                                </tr>
                            </table>
                        </z:datagrid></td>

                </tr>
            </table>
        </td>
    </tr>
</table>
<script type="application/javascript">
    function doSearch() {
        DataGrid.setParam("dg1", Constant.PageIndex, 0);
        DataGrid.setParam("dg1", "startDate", $V("startDate"));
        DataGrid.setParam("dg1", "endDate", $V("endDate"));
        DataGrid.setParam("dg1", "createDate", $V("createDate"));
        if ($V("startDate") == "") {
            Dialog.alert("请填写统计开始时间！");
            return;
        }
        if ($V("endDate") == "") {
            Dialog.alert("请填写统计结束时间！");
            return;
        }
        // 会员渠道
//        DataGrid.setParam("dg1", "channelsn", $NV("contant"));

        DataGrid.setParam("dg1", "Search", "search");
        DataGrid.loadData("dg1");
    }
    function reExecute(recordId) {
        var dc = new DataCollection();
        dc.add("recordIdStr",recordId);
        Dialog.confirm("您确定要重新执行吗？", function () {
            Server.sendRequest("com.sinosoft.cms.memberreport.StatisticsPlanRecordManagement.reExecuteStatisticsPlan", dc);
            Dialog.alert("已经重新执行,稍后请在此页面查询结果.")
        });

    }
</script>
</body>
</html>
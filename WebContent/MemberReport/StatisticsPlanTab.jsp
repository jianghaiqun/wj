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
    <script type="text/javascript" src="../template/common/js/jquery.js"></script>

</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
       style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="6"
                   class="blockTable">
                <tr>
                    <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif"/>会员统计计划</td>
                </tr>
                <tr>
                    <td>
                        <z:init method="com.sinosoft.cms.memberreport.StatisticsPlanManagement.init">
                            <table>
                                <tr>
                                    <td>创建时间：</td>
                                    <td><input name="startDate" type="text" id="startDate" value="${startDate}"
                                               style="width: 90px" class="inputText" ztype="date"></td>
                                    <td>到：</td>
                                    <td><input name="endDate" type="text" id="endDate" value="${endDate}"
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
                        <z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.StatisticsPlanManagement.dg1DataBind"
                                    lazy="true" size="20">
                            <table width="100%" cellpadding="2" cellspacing="0"
                                   class="dataTable" style="text-align: center;">
                                <tr ztype="head" class="dataTableHead">
                                    <td width="30" height="30" ztype="RowNo" drag="true"><img
                                            src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                                    <td width="30" height="30" ztype="Selector" field="id">&nbsp;</td>
                                    <td width="10%">计划名称</td>
                                    <td width="20%">统计URL</td>
                                    <td width="12%">执行日期</td>
                                    <td width="12%">计划状态</td>
                                    <td width="12%">统计周期</td>
                                    <td width="20%">创建时间</td>
                                    <td width="5%">创建人</td>
                                    <td width="20%">修改时间</td>
                                    <td width="5%">修改人</td>
                                    <td width="5%">操作</td>
                                </tr>
                                <tr style1="background-color:#FFFFFF"
                                    style2="background-color:#F9FBFC">
                                    <td onmouseover="">&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td title="${plan_name}">${plan_name}</td>
                                    <td title="${source_url}">${source_url}</td>
                                    <td title="${execute_datetime}">${execute_datetime}</td>
                                    <td title="${plan_status}">${plan_statusName}</td>
                                    <td title="${period}">${period}</td>
                                    <td title="${create_datetime}">${create_datetime}</td>
                                    <td title="${create_user}">${create_user}</td>
                                    <td title="${modify_datetime}">${modify_datetime}</td>
                                    <td title="${modify_user}">${modify_user}</td>
                                    <td>
                                        <span>&nbsp;</span>
                                        <a href="javascript:void(0)" onclick="deletePlan(${id})">
                                            <img src="../Icons/icon025a3.gif" width="15" height="15"
                                                 style="cursor:pointer;" alt="删除" title="删除"/>
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
        if ($V("startDate") == "") {
            Dialog.alert("请填写统计开始时间！");
            return;
        }
        if ($V("endDate") == "") {
            Dialog.alert("请填写统计结束时间！");
            return;
        }
        DataGrid.loadData("dg1");
    }
    function deletePlan(planIdStr) {
        var dc = new DataCollection();
        dc.add("planIdStr", planIdStr);
        Dialog.confirm("您确定要删除吗？", function () {
            Server.sendRequest("com.sinosoft.cms.memberreport.StatisticsPlanManagement.delete", dc);
            Dialog.alert("删除成功!",function(){
                DataGrid.setParam("dg1",Constant.PageIndex,0);
                DataGrid.loadData("dg1");
            });
        });

    }
</script>
</body>
</html>
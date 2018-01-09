<%--
  Created by IntelliJ IDEA.
  User: dongsheng
  Date: 2017/5/26
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>费用汇总统计</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js" type="text/javascript"></script>
    <script type="text/javascript">

        function selectBranch() {
            var dialog = new Dialog("branchDialog");
            dialog.Width = 300;
            dialog.Height = 400;
            dialog.Title = "选择机构";
            dialog.URL = "settlement/branch_tree_dialog.jsp";
            dialog.onLoad = function () {
                $DW.$("Title").focus();
            };
            dialog.OKEvent = function () {
                var branchCode = $DW.$NV("contant");
                $S("branchCode", branchCode);
                dialog.close();
            };
            dialog.show();
        }

        function doSearch() {
            if (Verify.hasError()) {
                return;
            }
            DataGrid.setParam("dg2", "companyName", $V("companyName"));
            DataGrid.setParam("dg2", "branchCode", $V("branchCode"));
            DataGrid.setParam("dg2", "invoiceStartDate", $V("invoiceStartDate"));
            DataGrid.setParam("dg2", "invoiceEndDate", $V("invoiceEndDate"));

            DataGrid.loadData("dg2");
        }

        function writeFile() {
            if (Verify.hasError()) {
                return false;
            }
            doSearch();
            DataGrid.toExcel("dg2", true);

        }
    </script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
       style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
                <z:init method="com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.init">
                    <tr>
                        <td>
                            <label for="companyName">保险公司名称（多个名称请用英文逗号分隔）：</label><input type="text" name="companyName"
                                                                                         id="companyName">
                            <input type="button" value="选择机构" onclick="selectBranch()">
                            <input type="text" id="branchCode" value="" verify="NotNull" readonly>
                        </td>

                    </tr>
                    <tr>
                        <td>
                            <label for="invoiceStartDate">开票时间 从：</label><input name="invoiceStartDate"
                                                                                id="invoiceStartDate"
                                                                                verify="Date"
                                                                                value="${invoiceStartDate}" type="text"
                                                                                size="15" ztype="date"/>
                            <label for="invoiceEndDate">至：</label><input name="invoiceEndDate" id="invoiceEndDate"
                                                                         verify="Date"
                                                                         value="${invoiceEndDate}" type="text" size="15"
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
                        <z:datagrid id="dg2"
                                    method="com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.dg2DataBind"
                                    page="false" lazy="true">
                            <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                                <tr ztype="head" class="dataTableHead">
                                    <td width="5%" ztype="RowNo"><b>序号</b></td>
                                    <td width="20%"><b>保险公司</b></td>
                                    <td width="15%"><b>一级渠道</b></td>
                                    <td width="10%"><b>总保费</b></td>
                                    <td width="10%"><b>总手续费</b></td>
                                    <td width="10%"><b>总保单优惠券</b></td>
                                    <td width="10%"><b>总活动优惠</b></td>
                                    <td width="10%"><b>总积分低值</b></td>
                                    <td width="10%"><b>总优惠</b></td>
                                </tr>
                                <tr style1="background-color:#FFFFFF"
                                    style2="background-color:#F9FBFC">
                                    <td>&nbsp;</td>
                                    <td align="left">${insurancecompanyname}</td>
                                    <td align="left">${channel}</td>
                                    <td align="right">${sump}</td>
                                    <td align="right">${sumf}</td>
                                    <td align="right">${sumcd}</td>
                                    <td align="right">${sumad}</td>
                                    <td align="right">${sumpd}</td>
                                    <td align="right">${sumtd}</td>
                                </tr>
                            </table>
                        </z:datagrid></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>

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
    <title>财务结算统计</title>
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

        function doSearchdgRSX() {
            if (Verify.hasError()) {
                return;
            }
            DataGrid.setParam("dgRSX", "companyName", $V("companyName"));
            DataGrid.setParam("dgRSX", "branchCode", $V("branchCode"));
            DataGrid.setParam("dgRSX", "invoiceStartDate", $V("invoiceStartDate"));
            DataGrid.setParam("dgRSX", "invoiceEndDate", $V("invoiceEndDate"));
            DataGrid.setParam("dgRSX", "coverage", $V("coverage"));

            DataGrid.loadData("dgRSX");
        }

        function doSearchdgCX() {
            if (Verify.hasError()) {
                return;
            }
            DataGrid.setParam("dgCX", "companyName", $V("companyName"));
            DataGrid.setParam("dgCX", "branchCode", $V("branchCode"));
            DataGrid.setParam("dgCX", "invoiceStartDate", $V("invoiceStartDate"));
            DataGrid.setParam("dgCX", "invoiceEndDate", $V("invoiceEndDate"));
            DataGrid.setParam("dgCX", "coverage", $V("coverage"));

            DataGrid.loadData("dgCX");
        }

        function selectType() {
            switch ($V("coverage")) {
                case "coverage_rsx":
                    $E.show("dgRSXDiv");
                    $E.hide("dgCXDiv");
                    $E.show("dgRSXBtnDiv");
                    $E.hide("dgCXBtnDiv");
                    break;
                case "coverage_cx":
                    $E.show("dgCXDiv");
                    $E.hide("dgRSXDiv");
                    $E.show("dgCXBtnDiv");
                    $E.hide("dgRSXBtnDiv");
                    break;
            }
        }


        function writeFile() {
            if (Verify.hasError()) {
                return false;
            }
            var companyName = $V("companyName");
            var branchCode = $V("branchCode");
            var invoiceStartDate = $V("invoiceStartDate");
            var invoiceEndDate = $V("invoiceEndDate");
            $NS("downloadFile_companyName", companyName)
            $NS("downloadFile_branchCode", branchCode)
            $NS("downloadFile_invoiceStartDate", invoiceStartDate)
            $NS("downloadFile_invoiceEndDate", invoiceEndDate)

            Dialog.confirm("默认使用当前页面的查询条件统计数据，确定导出吗？", function () {
                $("downloadFile").submit();
            });
        }
    </script>
</head>
<body>
<div style="display: none">
    <iframe src="javascript:void(0);" name="fsdTargetFrame" width="0" height="0" frameborder="0"></iframe>
    <form id="downloadFile" action="SimpleFileDownloadHandler.jsp" target="fsdTargetFrame">
        <input type="hidden" name="downloadFile_companyName">
        <input type="hidden" name="downloadFile_branchCode">
        <input type="hidden" name="downloadFile_invoiceStartDate">
        <input type="hidden" name="downloadFile_invoiceEndDate">
    </form>
</div>

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
                            <div id="dgRSXBtnDiv">
                                <z:tbutton onClick="doSearchdgRSX()"><img src="../Icons/icon403a3.gif"
                                                                        width="20"/>查询人身险</z:tbutton>
                            </div>
                            <div id="dgCXBtnDiv" style="display: none">
                                <z:tbutton onClick="doSearchdgCX()"><img src="../Icons/icon403a3.gif"
                                                                        width="20"/>查询产险</z:tbutton>
                            </div>
                            <z:select name="coverage" id="coverage" onChange="selectType()">
                                <option value="coverage_rsx">人身险</option>
                                <option value="coverage_cx">产险</option>
                            </z:select>
                            <input type="button" title="默认使用当前查询条件导出统计数据" value="导出Excel" onclick="writeFile()">
                        </td>
                    </tr>
                </z:init>
                <tr>
                    <td style="padding: 0px 6px 8px;">
                        <div id="dgRSXDiv">
                            <z:datagrid id="dgRSX"
                                        method="com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.dgRSXDataBind"
                                        page="false" lazy="true">
                                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                                    <tr ztype="head" class="dataTableHead">
                                        <td width="50" ztype="RowNo"><b>序号</b></td>
                                        <td width="200" ztype="tree" level="treeLevel"><b>险种类型</b></td>
                                        <td width="150"><b>新单保费</b></td>
                                        <td width="150"><b>续期保费</b></td>
                                        <td width="150"><b>新单代理佣金</b></td>
                                        <td width="150"><b>续期代理佣金</b></td>
                                        <td width="150"><b>自营新单保费</b></td>
                                        <td width="150"><b>自营续期保费</b></td>
                                        <td width="150"><b>自营新单代理佣金</b></td>
                                        <td width="150"><b>自营续期代理佣金</b></td>
                                        <td width="150"><b>第三方新单保费</b></td>
                                        <td width="150"><b>第三方续期保费</b></td>
                                        <td width="150"><b>第三方新单代理佣金</b></td>
                                        <td width="150"><b>第三方续期代理佣金</b></td>
                                    </tr>
                                    <tr style1="background-color:#FFFFFF"
                                        style2="background-color:#F9FBFC">
                                        <td>&nbsp;</td>
                                        <td>${type}</td>
                                        <td align="right">${sump1}</td>
                                        <td align="right">${sump2}</td>
                                        <td align="right">${sumf1}</td>
                                        <td align="right">${sumf2}</td>
                                        <td align="right">${sump1z}</td>
                                        <td align="right">${sump2z}</td>
                                        <td align="right">${sumf1z}</td>
                                        <td align="right">${sumf2z}</td>
                                        <td align="right">${sump1d}</td>
                                        <td align="right">${sump2d}</td>
                                        <td align="right">${sumf1d}</td>
                                        <td align="right">${sumf2d}</td>
                                    </tr>
                                </table>
                            </z:datagrid>
                        </div>
                        <div id="dgCXDiv" style="display: none">
                            <z:datagrid id="dgCX"
                                        method="com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.dgCXDataBind"
                                        page="false" lazy="true">
                                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                                    <tr ztype="head" class="dataTableHead">
                                        <td width="50" ztype="RowNo"><b>序号</b></td>
                                        <td width="200" ztype="tree" level="treeLevel"><b>险种类型</b></td>
                                        <td width="150"><b>保费金额</b></td>
                                        <td width="150"><b>代理佣金</b></td>
                                        <td width="150"><b>自营保费金额</b></td>
                                        <td width="150"><b>自营代理佣金</b></td>
                                        <td width="150"><b>第三方保费金额</b></td>
                                        <td width="150"><b>第三方代理佣金</b></td>
                                    </tr>
                                    <tr onDblClick='edit(this);' style1="background-color:#FFFFFF"
                                        style2="background-color:#F9FBFC">
                                        <td>&nbsp;</td>
                                        <td title="${type}">${type}</td>
                                        <td align="right" title="${sump}">${sump}</td>
                                        <td align="right" title="${sumf}">${sumf}</td>
                                        <td align="right" title="${sumpz}">${sumpz}</td>
                                        <td align="right" title="${sumfz}">${sumfz}</td>
                                        <td align="right" title="${sumpd}">${sumpd}</td>
                                        <td align="right" title="${sumfd}">${sumfd}</td>

                                    </tr>
                                </table>
                            </z:datagrid>
                        </div>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>

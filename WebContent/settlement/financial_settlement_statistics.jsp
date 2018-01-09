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
    <title>全国汇总统计</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js" type="text/javascript"></script>
    <script type="text/javascript" src="../template/common/js/jquery.js"></script>
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
            DataGrid.setParam("dg1", Constant.PageIndex, 0);
            DataGrid.setParam("dg1", "branchCode", $V("branchCode"));
            DataGrid.setParam("dg1", "invoiceStartDate", $V("invoiceStartDate"));
            DataGrid.setParam("dg1", "invoiceEndDate", $V("invoiceEndDate"));
            DataGrid.loadData("dg1");
            
            var dc = new DataCollection();
    		dc.add("branchCode", $V("branchCode"));
    		dc.add("invoiceStartDate", $V("invoiceStartDate"));
    		dc.add("invoiceEndDate", $V("invoiceEndDate"));
    		
    		Server.sendRequest("com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.orderCountStatistics", dc,function() {
    					var response = Server.getResponse();
    					var strs= new Array(); //定义一数组 
    					strs=response.Message.split("&");
    					jQuery("#underlineInCome").text(strs[0]);
    					jQuery("#onlineInCome").text(strs[1]);
    					jQuery("#total").text(strs[2]);
    				});
        }

        function deleteWithBatchNumber() {
            var batchNumber = $NV("batchNumber");
            if (!batchNumber || batchNumber == '') {
                Dialog.alert("请输入批次号！");
                return;
            }
            var dc = new DataCollection();
            dc.add("batchNumber", batchNumber);
            Dialog.confirm("确定要全部删除批次号为【" + batchNumber + "】的数据吗？", function () {
                Server.sendRequest("com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.delete", dc, function (response) {
                    if (response.Status == 1) {
                        //成功。
                        Dialog.alert(response.Message);
                        DataGrid.loadData("dg1");
                    } else {
                        Dialog.alert(response.Message);
                    }
                });
            });
        }
        function makeSure() {
        	var sureBatchNumber = $V("sureBatchNumber");
        	if (!sureBatchNumber || sureBatchNumber == '') {
                Dialog.alert("请正确填写要通过确认的批次号");
                return;
            }
        	var dc = new DataCollection();
            dc.add("sureBatchNumber", sureBatchNumber);
        	Dialog.confirm("确定要全部确认批次号为【" + sureBatchNumber + "】的数据吗？", function () {
                Server.sendRequest("com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.makeSure", dc, function (response) {
                    if (response.Status == 1) {
                        //成功。
                        Dialog.alert(response.Message);
                        DataGrid.loadData("dg1");
                    } else {
                        Dialog.alert(response.Message);
                    }
                });
            });
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
                            <input type="button" value="选择机构" onclick="selectBranch()">
                            <input type="text" id="branchCode" value="" verify="NotNull" readonly>
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
                        </td>
                    </tr>
                    <tr>
						<td>
						&nbsp;&nbsp;保费：<em id="underlineInCome">0.00</em>
                       </td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;手续费：<em id="onlineInCome">0.00</em></td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;合计：<em id="total">0.00</em></td>
					</tr>
                    
                </z:init>
                <tr>
                    <td style="padding: 0px 6px 8px;">
                        <z:datagrid id="dg1"
                                    method="com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.dgDataBindStatistics"
                                    page="true" size="15" lazy="true">
                            <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                                <tr ztype="head" class="dataTableHead">
                                    <td width="30" ztype="RowNo"><b>序号</b></td>
                                    <td width="200"><b>保监险种类别</b></td>
                                    <td width="150"><b>新单保费总和</b></td>
                                    <td width="100"><b>续期保费总和</b></td>
                                    <td width="150"><b>新单手续费总和</b></td>
                                    <td width="150"><b>续期手续费总和</b></td>
                                </tr>
                                <tr style1="background-color:#FFFFFF"
                                    style2="background-color:#F9FBFC">
                                    <td>&nbsp;</td>
                                    <td title="保监险种类别:${insurancetypeName}\n保监险种细分类:${insurancesubtypeName}">${insurancetypeName}
                                        / ${insurancesubtypeName}</td>>
                                    <td title="${premium1stSUM}">${premium1stSUM}</td>
                                    <td title="${premium2ndSUM}">${premium2ndSUM}</td>
                                    <td title="${fee1stSUM}">${fee1stSUM}</td>
                                    <td title="${fee2ndSUM}">${fee2ndSUM}</td>
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
</body>
</html>

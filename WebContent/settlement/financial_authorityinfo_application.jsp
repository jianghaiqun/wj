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
    <title>财务结算修改申请</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js" type="text/javascript"></script>
    <script type="text/javascript" src="../template/common/js/jquery.js"></script>
    <script type="text/javascript">


        function doSearch() {
            if (Verify.hasError()) {
                return;
            }
            DataGrid.setParam("dg1", Constant.PageIndex, 0);
            DataGrid.setParam("dg1", "startDate", $V("startDate"));
            DataGrid.setParam("dg1", "endDate", $V("endDate"));
            DataGrid.loadData("dg1");
        }
        
        function application() {
        	var applicationBatchNumber = $V("applicationBatchNumber");
        	var reason = $V("reason");
        	if (!applicationBatchNumber || applicationBatchNumber == '') {
                Dialog.alert("请正确填写要提交审核的批次号");
                return;
            }
        	if (!reason || reason == '') {
                Dialog.alert("请正确填写要提交审核的理由");
                return;
            }
        	var dc = new DataCollection();
            dc.add("applicationBatchNumber", applicationBatchNumber);
            dc.add("reason", reason);
        	Dialog.confirm("确定提交申请吗？", function () {
                Server.sendRequest("com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.application", dc, function (response) {
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
                            <label for="startDate">提起申请时间 从：</label><input name="startDate"
                                                                                id="startDate"
                                                                                verify="Date"
                                                                                value="${startDate}" type="text"
                                                                                size="15" ztype="date"/>
                            <label for="endDate">至：</label><input name="endDate" id="endDate"
                                                                         verify="Date"
                                                                         value="${endDate}" type="text" size="15"
                                                                         ztype="date"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" value="查询申请记录" onclick="doSearch()">
                        </td>
                    </tr>
                    <tr>
						<td>
						申请批次号:<input type="text" id="applicationBatchNumber" value="" width="200" style="width:200px">
						申请理由:<input type="text" id="reason" value="" width="200" style="width:500px">
						<input type="button" value="提起申请" onclick="application()">
                       </td>
					</tr>
                </z:init>
                <tr>
                    <td style="padding: 0px 6px 8px;">
                        <z:datagrid id="dg1"
                                    method="com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.dgDataBindApplication"
                                    page="true" size="15" lazy="true">
                            <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                                <tr ztype="head" class="dataTableHead">
                                    <td width="30" ztype="RowNo"><b>序号</b></td>
                                    <td width="150"><b>ID</b></td>
                                    <td width="150"><b>批次号</b></td>
                                    <td width="100"><b>机构号</b></td>
                                    <td width="100"><b>申请理由</b></td>
                                    <td width="100"><b>申请状态</b></td>
                                    <td width="100"><b>申请时间</b></td>
                                    <td width="100"><b>申请人</b></td>
                                    <td width="100"><b>处理时间</b></td>
                                    <td width="100"><b>处理人</b></td>
                                </tr>
                                <tr style1="background-color:#FFFFFF"
                                    style2="background-color:#F9FBFC">
                                    <td>&nbsp;</td>
                                    <td title="${ID}">${ID}</td>
                                    <td title="${batchnumber}">${batchnumber}</td>
                                    <td title="${branchCode}">${branchCode}</td>
                                    <td title="${reason}">${reason}</td>
                                    <td title="${state}">${state}</td>
                                    <td title="${createdate}">${createdate}</td>
                                    <td title="${createuser}">${createuser}</td>
                                    <td title="${modifydate}">${modifydate}</td>
                                    <td title="${modifyuser}">${modifyuser}</td>
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

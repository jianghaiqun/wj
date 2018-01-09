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
    <title>财务结算明细查询</title>
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

        function selectInsuranceType() {
            var dialog = new Dialog("branchDialog");
            dialog.Width = 300;
            dialog.Height = 400;
            dialog.Title = "选择保监险种类别";
            dialog.URL = "settlement/insurance_type_tree_dialog.jsp?type=" + $V("coverage");
            dialog.onLoad = function () {
                $DW.$("Title").focus();
            };
            dialog.OKEvent = function () {
                var insuranceType = $DW.$NV("contant");
                $S("insuranceType", insuranceType);
                dialog.close();
            };
            dialog.show();
        }

        function doSearch() {
            if (Verify.hasError()) {
                return;
            }
            DataGrid.setParam("dg1", Constant.PageIndex, 0);
            DataGrid.setParam("dg1", "companyName", $V("companyName"));
            DataGrid.setParam("dg1", "companyType", $V("companyType"));
            DataGrid.setParam("dg1", "policyNo", $V("policyNo"));
            DataGrid.setParam("dg1", "insuranceType", $V("insuranceType"));
            DataGrid.setParam("dg1", "branchCode", $V("branchCode"));
            DataGrid.setParam("dg1", "insureStartDate", $V("insureStartDate"));
            DataGrid.setParam("dg1", "insureEndDate", $V("insureEndDate"));
            DataGrid.setParam("dg1", "svalidateBeforeDate", $V("svalidateBeforeDate"));
            DataGrid.setParam("dg1", "evalidateAfterDate", $V("evalidateAfterDate"));
            DataGrid.setParam("dg1", "invoiceStartDate", $V("invoiceStartDate"));
            DataGrid.setParam("dg1", "invoiceEndDate", $V("invoiceEndDate"));
            DataGrid.loadData("dg1");
            
            var dc = new DataCollection();
    		dc.add("companyName", $V("companyName"));
    		dc.add("companyType", $V("companyType"));
    		dc.add("policyNo", $V("policyNo"));
    		dc.add("insuranceType", $V("insuranceType"));
    		dc.add("branchCode", $V("branchCode"));
    		dc.add("insureStartDate", $V("insureStartDate"));
    		dc.add("insureEndDate", $V("insureEndDate"));
    		dc.add("svalidateBeforeDate", $V("svalidateBeforeDate"));
    		dc.add("evalidateAfterDate", $V("evalidateAfterDate"));
    		dc.add("invoiceStartDate", $V("invoiceStartDate"));
    		dc.add("invoiceEndDate", $V("invoiceEndDate"));
    		
    		Server.sendRequest("com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.orderCount", dc,function() {
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
                        <td style="padding:0 8px 4px;">
                            <label for="batchNumber">批次号(只用于删除):</label>
                            <input type="text" name="batchNumber" id="batchNumber">
                            <input type="button" value="按批次删除" onclick="deleteWithBatchNumber()">

                            <label for="insureStartDate">承保时间 从：</label><input name="insureStartDate"
                                                                               id="insureStartDate"
                                                                               verify="Date"
                                                                               value="" type="text"
                                                                               size="15" ztype="date"/>
                            <label for="insureEndDate">至：</label><input name="insureEndDate" id="insureEndDate"
                                                                        verify="Date"
                                                                        value="" type="text" size="15"
                                                                        ztype="date"/>
                            <span style="padding:0px 10px;"></span>
                            <label for="svalidateBeforeDate">保单有效时间范围 从：</label><input
                                name="svalidateBeforeDate" id="svalidateBeforeDate" value=""
                                verify="Date"
                                type="text" size="15" ztype="date"/>
                            <label for="evalidateAfterDate">至：</label><input name="evalidateAfterDate"
                                                                             id="evalidateAfterDate"
                                                                             verify="Date"
                                                                             value="" type="text"
                                                                             size="15" ztype="date"/>

                    </tr>
                    <tr>
                        <td>
                            <label for="companyName">保险公司名称（多个名称请用英文逗号分隔）：</label><input type="text" name="companyName"
                                                                                         id="companyName">
                            <label for="companyType">保险公司性质（多个性质请用英文逗号分隔）：</label><input type="text" name="companyType"
                                                                                         id="companyType">
                            <label for="policyNo">保单号（多个保单号请用英文逗号分隔）：</label><input type="text" name="policyNo"
                                                                                    id="policyNo">

                        </td>

                    </tr>
                    <tr>
                        <td>
                            <input type="button" value="选择机构" onclick="selectBranch()">
                            <input type="text" id="branchCode" value="" verify="NotNull" readonly>
                            <z:select name="coverage" id="coverage">
                                <option value="coverage_rsx">人身险</option>
                                <option value="coverage_cx">产险</option>
                            </z:select>
                            <input type="button" value="选择保监险种类别" onclick="selectInsuranceType()">
                            <input type="text" id="insuranceType" value="" readonly>

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
						&nbsp;&nbsp;线下收入金额：<em id="underlineInCome">0.00</em>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						需要确认批次号（多个名称请用英文逗号分隔）：<input type="text" id="sureBatchNumber" value="" width="200" style="width:600px">
						<input type="button" value="确认数据" onclick="makeSure()">
                       </td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;线上收入金额：<em id="onlineInCome">0.00</em></td>
					</tr>
					<tr>
						<td>&nbsp;&nbsp;合计：<em id="total">0.00</em></td>
					</tr>
                    <tr>
                        <td>
                            <z:tbutton onClick="doSearch()"><img src="../Icons/icon403a3.gif" width="20"/>查询</z:tbutton>
                        </td>
                    </tr>
                </z:init>
                <tr>
                    <td style="padding: 0px 6px 8px;">
                        <z:datagrid id="dg1"
                                    method="com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.dg1DataBind"
                                    page="true" size="15" lazy="true">
                            <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                                <tr ztype="head" class="dataTableHead">
                                    <td width="30" ztype="RowNo"><b>序号</b></td>
                                    <td width="30" ztype="selector" field="ID">&nbsp;</td>
                                    <td width="150"><b>批次号</b></td>
                                    <td width="100"><b>开票日期</b></td>
                                    <td width="150"><b>保单号</b></td>
                                    <td width="200"><b>保监险种类别</b></td>
                                    <td width="100"><b>渠道</b></td>
                                    <td width="100"><b>渠道归属</b></td>
                                    <td width="100"><b>保险公司</b></td>
                                    <td width="100"><b>保险公司性质</b></td>
                                    <td width="100"><b>保单状态</b></td>
                                    <td width="100"><b>新单保费</b></td>
                                    <td width="100"><b>续期保费</b></td>
                                    <td width="100"><b>新单手续费率</b></td>
                                    <td width="100"><b>续期手续费率</b></td>
                                    <td width="100"><b>新单手续费</b></td>
                                    <td width="100"><b>续期手续费</b></td>
                                    <td width="100"><b>状态</b></td>
                                    <td width="100"><b>承保日期</b></td>
                                    <td width="100"><b>生效日期</b></td>
                                    <td width="100"><b>失效日期</b></td>
                                    <td width="100"><b>保费批次号</b></td>
                                    <td width="100"><b>续期手批次号</b></td>
                                    <td width="120"><b>创建时间</b></td>
                                    <td width="100"><b>创建人</b></td>
                                </tr>
                                <tr style1="background-color:#FFFFFF"
                                    style2="background-color:#F9FBFC">
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td title="${batchnumber}" style="text-align: right">${batchnumber}</td>
                                    <td title="${invoicedate}">${invoicedateName}</td>
                                    <td title="${policyno}" style="text-align: right">${policyno}</td>
                                    <td title="保监险种类别:${insurancetypeName}\n保监险种细分类:${insurancesubtypeName}">${insurancetypeName}
                                        / ${insurancesubtypeName}</td>
                                    <td title="一级渠道:${channel}\n二级渠道:${subchannel}">${channel} / ${subchannel}</td>
                                    <td title="渠道归属:${channeltype}">${channeltype}</td>
                                    <td title="${insurancecompanyname}">${insurancecompanyname}</td>
                                    <td title="${insurancecompanytype}">${insurancecompanytype}</td>
                                    <td title="${appstatus}">${appstatus}</td>
                                    <td title="${premium1st}" style="text-align: right">${premium1st}</td>
                                    <td title="${premium2nd}" style="text-align: right">${premium2nd}</td>
                                    <td title="${feeratio1st}" style="text-align: right">${feeratio1st}</td>
                                    <td title="${feeratio2nd}" style="text-align: right">${feeratio2nd}</td>
                                    <td title="${fee1st}" style="text-align: right">${fee1st}</td>
                                    <td title="${fee2nd}" style="text-align: right">${fee2nd}</td>
                                    <td title="${state}">${state}</td>
                                    <td title="${insuredate}">${insuredateName}</td>
                                    <td title="${svalidate}">${svalidateName}</td>
                                    <td title="${evalidate}">${evalidateName}</td>
                                    <td title="${premiumbatchnumber}">${premiumbatchnumber}</td>
                                    <td title="${feebatchnumber}">${feebatchnumber}</td>
                                    <td>${createddatetime}</td>
                                    <td>${createduser}</td>
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

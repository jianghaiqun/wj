<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>合众福享金生授权书下载</title>
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
                    <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif"/>合众福享金生授权书下载</td>
                </tr>
                <tr>
                    <td>
                        <z:init method="com.sinosoft.cms.memberreport.HZFXJSauthorization.init">
                            <table>
                                <tr>
                                    <td>下单时间 从：</td>
                                    <td><input name="startDate" type="text" id="startDate" value="${startDate}"
                                               style="width: 90px" class="inputText" ztype="date"></td>
                                    <td>&nbsp;到：</td>
                                    <td><input name="endDate" type="text" id="endDate" value="${endDate}"
                                               style="width: 90px" class="inputText" ztype="date"></td>
									<td>&nbsp;身份证号：</td>
									<td><input name="IDNember" type="text"
											id="IDNember" value="" style="width: 150px" /></td>
									<td>&nbsp;银行账号：</td>
									<td><input name="bankCardNo" type="text"
											id="bankCardNo" value="" style="width: 220px" /></td>
                                </tr>
								<tr>
                                    <td colspan="6">
                                    	<input type="button" name="Submit" value="查询" onClick="doSearch()">
                                    	<input type="button" name="download" value="下载" onClick="download()">
                                    </td>
                                </tr>
                            </table>
                        </z:init>
                    </td>
                </tr>
                <tr>
                    <td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
                        <z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.HZFXJSauthorization.dg1DataBind"
                                    lazy="true" size="15">
                            <table width="100%" cellpadding="2" cellspacing="0"
                                   class="dataTable" style="text-align: center;">
                                <tr ztype="head" class="dataTableHead">
                                    <td width="30" height="30" ztype="RowNo" drag="true"><img
                                            src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                                    <td width="30" height="30" ztype="Selector" field="id">&nbsp;</td>
                                    <td width="10%">订单号</td>
                                    <td width="10%">户名</td>
                                    <td width="15%">账号</td>
                                    <td width="10%">开户银行</td>
                                    <td width="10%">证件类型</td>
                                    <td width="15%">证件号码</td>
                                    <td width="10%">授权日期</td>
                                </tr>
                                <tr style1="background-color:#FFFFFF"
                                    style2="background-color:#F9FBFC">
                                    <td onmouseover="">&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td title="${ordersn}">${ordersn}</td>
                                    <td title="${bankusername}">${bankusername}</td>
                                    <td title="${bankno}">${bankno}</td>
                                    <td title="${bankname}">${bankname}</td>
                                    <td title="${applicantidentityTypeName}">${applicantidentityTypeName}</td>
                                    <td title="${applicantIdentityId}">${applicantIdentityId}</td>
                                    <td title="${createdate}">${createdate}</td>
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
        DataGrid.setParam("dg1", "IDNember", $V("IDNember"));
        DataGrid.setParam("dg1", "bankCardNo", $V("bankCardNo"));
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
    
    function download(){
        var dt = DataGrid.getSelectedData("dg1");
        var drs = dt.Rows;
        if (!drs || drs.length == 0) {
            Dialog.alert("请先选择要下载的授权书信息！");
            return;
        }
        var dc = new DataCollection();
        dc.add("tableData", dt);
        Dialog.wait("文件生成中，请稍等");
        Server.sendRequest("com.sinosoft.cms.memberreport.HZFXJSauthorization.download",dc,function(response){
            Dialog.closeEx();
            if(response.Status==1){
            	window.open(response.Message);
            } else {
                Dialog.alert("失败");
			}
        });
	}
</script>
</body>
</html>
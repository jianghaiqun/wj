<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>缺口保障优惠券管理</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js"></script>
    <script>
        function add() {
            var diag = new Dialog("Diag1");
            diag.Width = 500;
            diag.Height = 150;
            diag.Title = "新建优惠券";
            diag.URL = "Activity/weixin_security_coupon_dialog.jsp";
            diag.onLoad = function () {
                $DW.$("CodeValue").focus();
            };
            diag.OKEvent = addSave;
            diag.show();
        }

        function addSave() {
            if ($DW.Verify.hasError()) {
                return;
            }
            var dc = $DW.Form.getData("form2");
            dc.add("ParentCode", "WeiXinSecurityCouponConfig");
            dc.add("CodeType", "WeiXinSecurityCouponConfig");
            Server.sendRequest("com.wangjin.activity.WeiXinSecurityCouponsConfig.add", dc, function (response) {
                Dialog.alert(response.Message, function () {
                    if (response.Status == 1) {
                        $D.close();
                        DataGrid.loadData('dg1');
                    }
                });
            });
        }

        function del() {
            var dt = DataGrid.getSelectedData("dg1");
            if (dt.getRowCount() == 0) {
                Dialog.alert("请先选择要删除的行！");
                return;
            }
            var dc = new DataCollection();
            dc.add("DT", dt);
            Server.sendRequest("com.sinosoft.platform.Code.del", dc, function (response) {
                DataGrid.loadData('dg1');
                Dialog.alert(response.Message);
            });
        }
    </script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
                <tr>
                    <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif"/>优惠券列表</td>
                </tr>
                <tr>
                    <td>
                        <z:tbutton onClick="add()"><img src="../Icons/icon018a2.gif"/>新建</z:tbutton>
                        <z:tbutton onClick="del()"> <img src="../Icons/icon018a3.gif"/>删除</z:tbutton>
                        <z:tbutton onClick=" DataGrid.loadData('dg1');"> <img src="../Icons/icon018a3.gif"/>刷新</z:tbutton>
                    </td>
                </tr>
                <tr>
                    <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
                        <z:datagrid id="dg1" method="com.wangjin.activity.WeiXinSecurityCouponsConfig.dg1DataBind">
                            <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                                <tr ztype="head" class="dataTableHead">
                                    <td width="4%" ztype="RowNo"><b>序号</b></td>
                                    <td width="3%" ztype="selector" field="type">&nbsp;</td>
                                    <td width="24%"><b>优惠券名称</b></td>
                                    <td width="25%"><b>优惠券批次号</b></td>
                                    <td width="38%"><b>创建时间</b></td>
                                </tr>
                                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
                                    <td align="center">&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>${CodeName}</td>
                                    <td>${CodeValue}</td>
                                    <td>${AddTime}</td>
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

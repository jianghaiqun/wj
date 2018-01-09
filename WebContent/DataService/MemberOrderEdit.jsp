<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<%@page import="com.sinosoft.cms.pub.PubFun" %>
<%String KID = PubFun.getKeyValue();%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>订单信息管理</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js"></script>
    <script>
        var KID = "<%=KID%>";

        function doSearch() {
            DataGrid.setParam("dg1", Constant.PageIndex, 0);
            DataGrid.setParam("dg1", "createDate", $V("createDate"));
            DataGrid.setParam("dg1", "endCreateDate", $V("endCreateDate"));
            DataGrid.setParam("dg1", "productName", $V("productName"));
            DataGrid.setParam("dg1", "shipMobile", $V("shipMobile"));
            DataGrid.setParam("dg1", "email", $V("email"));
            DataGrid.setParam("dg1", "tbTradeSeriNo", $V("tbTradeSeriNo"));
            DataGrid.setParam("dg1", "orderSn", $V("orderSn"));
            DataGrid.setParam("dg1", "OrderStatus", $V("OrderStatus"));
            DataGrid.setParam("dg1", "applicantName", $V("applicantName"));
            DataGrid.setParam("dg1", "recognizeeName", $V("recognizeeName"));
            DataGrid.setParam("dg1", "memberEmail", $V("memberEmail"));
            DataGrid.setParam("dg1", "memberMobile", $V("memberMobile"));
            DataGrid.setParam("dg1", "applicantId", $V("applicantId"));
            DataGrid.setParam("dg1", "recognizeeId", $V("recognizeeId"));
            DataGrid.setParam("dg1", "policyNo", $V("policyNo"));
            DataGrid.loadData("dg1");
        }

        function edit(id, rid, orderSn, recognizeeSn) {
            var arr = DataGrid.getSelectedData("dg1");
            if (!arr || arr.getRowCount() == 0) {
                Dialog.alert("请先选择要编辑的条目！");
                return;
            }
            if (!arr || arr.getRowCount() >= 2) {
                Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
                return;
            }
            if (id == null) {
                id = arr.get(0, "id");
            }
            if (rid == null) {
                rid = arr.get(0, "rid");
            }
            if (orderSn == null) {
                orderSn = arr.get(0, "orderSn");
            }
            if (recognizeeSn == null) {
                recognizeeSn = arr.get(0, "recognizeeSn");
            }
            var diag = new Dialog("Diag1");
            diag.Width = 1200;
            diag.Height = 500;
            diag.Title = "订单信息修改";
            diag.URL = "DataService/MemberOrderEditDialogtest.jsp?id=" + id + "&rid=" + rid + "&orderSn=" + orderSn + "&recognizeeSn=" + recognizeeSn;
            diag.OKEvent = editSave;
            diag.ShowMessageRow = true;
            diag.MessageTitle = "修改订单信息 ";
            diag.Message = "修改订单信息，修改投/被保人信息";
            diag.show();
            diag.OKButton.hide();
            diag.CancelButton.value = "关闭";
        }


        //显示订单详细信息
        function showOrderDetail(orderSn) {
            var queryID = orderSn;
            var KKID = '';
            var dc = new DataCollection();
            dc.add("KID", KID + queryID);
            var method = "cn.com.sinosoft.util.StringUtilC.md5Hex";

            Server.sendRequest(method, dc, function (response) {
                //alert(response.get("KID"));
                KKID = response.get("KID");
                //alert(KKID);
                window.open('../shop/order_config_new!linkOrderDetails.action?orderSn=' + queryID + "&KID=" + KKID);
            });
        }


        function editSave() {
            var dc = $DW.Form.getData("form2");
            //dc.add("id",$DW.$NV("id"));
            //var id=$DW.$NV("id");
            //alert(id);
            //dc.add("id",$DW.$NV("id"));
            if ($DW.Verify.hasError()) {
                return;
            }
            if ($DW.Verify.hasError()) {
                return;
            }
            Server.sendRequest("com.wangjin.cms.orders.QueryOrders.dg1Edit3", dc, function () {
                var response = Server.getResponse();
                Dialog.alert(response.Message);
                if (response.Status == 1) {
                    $D.close();
                    DataGrid.loadData('dg1');
                }
            });
        }

        function doblank() {
            createDate.value = "";
            endCreateDate.value = "";
            productName.value = "";
            shipMobile.value = "";
            email.value = "";
            tbTradeSeriNo.value = "";
            orderSn.value = "";
            applicantName.value = "";
            document.getElementById("OrderStatus").value = "";
            mid.value = "";
            idNO.value = "";
            policyNo.value = "";

        }

        function cancel() {
            Dialog.confirm("您确认要申请退保吗?", function () {
                if (checked() == false) {
                    return;
                }
                var dc = new DataCollection();
                var arr = DataGrid.getSelectedValue("dg1");
                dc.add("IDs", arr.join());
                Server.sendRequest("com.sinosoft.orders.CancelCont.dealCancel", dc,
                    function (response) {
                        Dialog.alert(response.Message, function () {
                            if (response.Status == 1) {
                                DataGrid.loadData("dg1");
                                window.location.reload();
                                $D.close();
                            } else {
                                window.location.reload();
                            }

                        });
                    });
            });
        }

        function checked() {
            var flag = true;
            var arr1 = DataGrid.getSelectedData("dg1");
            var drs = arr1.Rows;
            for (var i = 0; i < drs.length; i++) {

                dr = drs[i];
                if (dr.get("orderstatus") != "7") {
                    Dialog.alert("订单未成功支付或已撤单！请选择正确的订单！");
                    flag = false;
                    return flag;
                }
                var nowDate = new Date();//获取当前系统时间
                var startDate = dr.get("effective");
                var startDateTemp = startDate.split(" ");
                var arrStartDate = startDateTemp[0].split("-");
                var allStartDate = new Date(arrStartDate[0], arrStartDate[1] - 1,
                    arrStartDate[2]);
                var allNowDate = new Date(nowDate.getFullYear(), nowDate.getMonth(),
                    nowDate.getDate());
                if (allNowDate.getTime() > allStartDate.getTime()) {
                    Dialog.alert("该订单已起保，不可撤单！");
                    flag = false;
                    return flag;
                }
            }
            return flag;
        }

        //add by wangej 20130904 订单关联会员
        function editMemderId() {

            var arr = DataGrid.getSelectedData("dg1");
            if (!arr || arr.getRowCount() == 0) {
                Dialog.alert("请先选择要编辑的条目！");
                return;
            }
            if (!arr || arr.getRowCount() >= 2) {
                Dialog.alert("您选择了太多的条目，请您只选择其中的一条 ！");
                return;
            }
            var diag = new Dialog("Diag1");
            diag.Width = 1200;
            diag.Height = 500;
            diag.Title = "订单信息修改";
            diag.URL = "DataService/MemberOrderEditMemberId.jsp";
            diag.OKEvent = editMemderIdUpdate;
            diag.ShowMessageRow = true;
            diag.MessageTitle = "会员信息 ";
            diag.Message = "会员信息";
            diag.show();
            diag.CancelButton.value = "关闭";

        }

        //add by wangej 20130904 订单关联会员
        function editMemderIdUpdate() {
            var dt = $DW.DataGrid.getSelectedData("dg1");
            var memberEmail = dt.get(0, "email");
            var memberMobileNo = dt.get(0, "mobileno");
            var orderSn = DataGrid.getSelectedData("dg1").get(0, "orderSn");
            var dc = new DataCollection();
            dc.add("memberEmail", memberEmail);
            dc.add("orderSn", orderSn);
            dc.add("memberMobileNo", memberMobileNo);
            var memberid = $DW.DataGrid.getSelectedValue("dg1");
            dc.add("memberid", memberid);
            Server.sendRequest("com.wangjin.cms.orders.QueryOrders.updateMemberId", dc, function () {
                var response = Server.getResponse();
                Dialog.alert(response.Message);
                if (response.Status == 1) {
                    $D.close();
                    DataGrid.loadData('dg1');
                }
            });
        }

        function baoquanedit(insuredid) {
            var diag = new Dialog();
            diag.Width = 500;
            diag.Height = 300;
            diag.Title = "保全记录修改";
            diag.URL = "DataService/BaoquanEdit.jsp?ID=" + insuredid;
            diag.OKEvent = save;
            diag.ShowMessageRow = true;
            diag.MessageTitle = "保全记录 ";
            diag.Message = "保全记录";
            diag.show();
            diag.CancelButton.value = "关闭";
        }

        function save() {
            var dc = $DW.Form.getData("fm_reply");
            Server.sendRequest("com.wangjin.cms.orders.QueryOrders.save", dc, function () {
                var response = Server.getResponse();
                Dialog.alert(response.Message, function () {
                    if (response.Status == 1) {
                        $D.close();
                        window.location.reload();
                    }
                });
            });
        }

    </script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
                <tr>
                    <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20"/> 订单信息
                    </td>
                </tr>
                <tr>
                    <td>
                        <z:init method="com.wangjin.cms.orders.QueryOrders.init">
                            <table cellspacing="0" cellpadding="3">
                                <tr>
                                    <td>渠道订单号：</td>
                                    <td><input name="tbTradeSeriNo" type="text" id="tbTradeSeriNo" value=""
                                               style="width:100px"></td>
                                    <td>订单号：</td>
                                    <td><input name="orderSn" type="text" id="orderSn" value="" style="width:100px">
                                    </td>
                                    <td>产品名称：</td>
                                    <td><input name="productName" type="text" id="productName" value=""
                                               style="width:100px"></td>
                                    <td>订单状态</td>
                                    <td><z:select style="width:100px;" name="OrderStatus"
                                                  id="OrderStatus">${OrderStatus}</z:select></td>
                                    <td>订单时间 从：</td>
                                    <td><input name="createDate" type="text" id="createDate" value="${createDate}"
                                               style="width:100px" ztype="date"></td>
                                    <td>至：</td>
                                    <td><input name="endCreateDate" type="text" id="endCreateDate"
                                               value="${endCreateDate}" style="width:100px" ztype="date"></td>
                                </tr>
                                <tr>
                                    <td>投保人姓名：</td>
                                    <td><input name="applicantName" type="text" id="applicantName" value=""
                                               style="width:100px"/></td>
                                    <td>投保人Email：</td>
                                    <td><input name="email" type="text" id="email" value="" style="width:100px"/></td>
                                    <td>手机号：</td>
                                    <td><input name="shipMobile" type="text" id="shipMobile" value=""
                                               style="width:100px"></td>
                                    <td>投保人证件号：</td>
                                    <td><input name="applicantId" type="text" id="applicantId" value=""
                                               style="width:100px"/></td>
                                    <td>被保人姓名：</td>
                                    <td><input name="recognizeeName" type="text" id="recognizeeName" value=""
                                               style="width:100px"/></td>
                                    <td>被保人证件号：</td>
                                    <td><input name="recognizeeId" type="text" id="recognizeeId" value=""
                                               style="width:100px"/></td>
                                </tr>
                                <tr>
                                    <td>保单号：</td>
                                    <td><input name="policyNo" type="text" id="policyNo" value="" style="width:100px"/>
                                    </td>
                                    <td>会员邮箱：</td>
                                    <td><input name="memberEmail" type="text" id="memberEmail" value=""
                                               style="width: 100px"/></td>
                                    <td>会员手机号：</td>
                                    <td><input name="memberMobile" type="text" id="memberMobile" value=""
                                               style="width: 100px"/></td>
                                </tr>
                                <tr>
                                    <td colspan="4">
                                        <z:tbutton onClick="doSearch()" id="Submitbutton">
                                            <img src="../Icons/icon005a2.gif" width="20" height="20"/>查询
                                        </z:tbutton>
                                        <z:tbutton onClick="doblank()"><img src="../Icons/icon401a3.gif" width="20"
                                                                            height="20"/>清空查询条件</z:tbutton>
                                        <z:tbutton onClick="edit()" id="Submitbutton">
                                            <img src="../Icons/icon021a4.gif" width="20" height="20"/>修改
                                        </z:tbutton>
                                        <z:tbutton onClick="editMemderId()" id="Submitbutton">
                                            <img src="../Icons/icon021a4.gif" width="20" height="20"/>关联会员
                                        </z:tbutton>
                                        <!--<z:tbutton onClick="cancel()"  id="Submitbutton">
			            	     <img src="../Icons/icon021a4.gif" width="20" height="20"/>撤单
			            	   </z:tbutton>-->
                                    </td>
                                </tr>
                            </table>
                        </z:init>
                    </td>
                </tr>
                <tr>
                    <td style="padding: 0px 5px;">
                        <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable"
                               style="table-layout: fixed;">
                            <tr>
                                <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
                                    <z:datagrid id="dg1" method="com.wangjin.cms.orders.QueryOrders.orderInquery"
                                                autoFill="true" scroll="true" lazy="true" size="20" multiSelect="true">
                                        <table width="100%" cellpadding="2" cellspacing="0"
                                               class="dataTable" style="table-layout : fixed" fixedHeight="480px"
                                               afterdrag="afterRowDragEnd">
                                            <tr ztype="head" class="dataTableHead">
                                                <td width="30" ztype="RowNo" style="text-align:center;">
                                                    <strong>序号</strong></td>
                                                <td width="15" ztype="selector" field="id" style="text-align:center;">
                                                    &nbsp;
                                                </td>
                                                <td width="120" style="text-align:center;"><b>订单编号</b></td>
                                                <td width="120" style="text-align:center;"><b>渠道订单号</b></td>
                                                <td width="145" style="text-align:center;"><b>保单号</b></td>
                                                <td width="85" style="text-align:center;"><b>保全记录</b></td>
                                                <td width="50" style="text-align:center;"><b>投保人</b></td>
                                                <td width="50" style="text-align:center;"><b>被保人数</b></td>
                                                <td width="70" style="text-align:center;"><b>起保日期</b></td>
                                                <td width="120" style="text-align:center;"><b>修改时间</b></td>
                                                <td width="150" style="text-align:center;"><b>产品名称</b></td>
                                                <td width="55" style="text-align:center;"><b>产品计划</b></td>
                                                <td width="50" style="text-align:center;"><b>保费</b></td>
                                                <td width="50" style="text-align:center;"><b>已付费</b></td>
                                                <td width="110" style="text-align:center;"><b>会员ID</b></td>
                                                <td width="60" style="text-align:center;"><b>订单状态</b></td>
                                            </tr>
                                            <tr onDblClick="edit('${id}','${rid}','${OrderSn}','${recognizeeSn}')"
                                                style="background-color:#F9FBFC">
                                                <td width="3%">&nbsp;</td>
                                                <td style="text-align:center;">&nbsp;</td>
                                                <td><a style="cursor: hand;"
                                                       onClick="showOrderDetail('${orderSn}')">${orderSn}</a></td>
                                                <td>${tbTradeSeriNo}</td>
                                                <td>${policyNo}</td>
                                                <td onClick="baoquanedit('${insuredid}');">${remark}</td>
                                                <td style="text-align:center;">${ApplicantName}</td>
                                                <td style="text-align:center;">${recognizeeNu}</td>
                                                <td style="text-align:center;">${svalidate}</td>
                                                <td style="text-align:center;">${ModifyDate}</td>
                                                <td>${ProductName}</td>
                                                <td>${planName}</td>
                                                <td style="text-align:right;">${totalAmount}</td>
                                                <td style="text-align:right;">${notfee}</td>
                                                <td style="text-align:right;">${mid}</td>
                                                <td style="text-align:center;">${orderstatusname}</td>
                                            </tr>
                                            <tr ztype="pagebar">
                                                <td height="25" colspan="11">${PageBar}</td>
                                                <input name="id" type="hidden" id="id" value="${id}"
                                                       style="width:150px">
                                            </tr>
                                        </table>
                                    </z:datagrid></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>

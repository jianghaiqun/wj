<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>

<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>续保统计</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css" />
    <script src="../Framework/Main.js"></script>
    <script src="../Framework/OrderTree.js"></script>
    <script src="../wwwroot/kxb/js/Common.js"></script>
    <script src="../template/common/js/jquery.js"></script>
    <script type="text/javascript">
        function showChannel(){
            var check_flag=jQuery('#show_channel').is(':checked');
            if(check_flag==true){
                jQuery("#channel_tree").show();
            }else{
                jQuery("#channel_tree").hide();
            }
        }

        function doSearch(){
            DataGrid.setParam("dg1",Constant.PageIndex,0);
            DataGrid.setParam("dg1","startDate",$V("startDate"));
            DataGrid.setParam("dg1","endDate",$V("endDate"));

            if( $V("startDate") == null || $V("startDate") == "" ){
                Dialog.alert("请填写统计开始时间！");
                return;
            }
            if($V("endDate") == null || $V("endDate") == "" ){
                Dialog.alert("请填写统计结束时间！");
                return;
            }
            // 会员渠道
            DataGrid.setParam("dg1","contant",$NV("contant"));
            DataGrid.loadData("dg1",getDataGrid2);
        }

        function getDataGrid2() {
            DataGrid.setParam("dg2",Constant.PageIndex,0);
            DataGrid.setParam("dg2","startDate",$V("startDate"));
            DataGrid.setParam("dg2","contant",$NV("contant"));
            DataGrid.setParam("dg2","endDate",$V("endDate"));

            DataGrid.loadData("dg2");
        }

        //显示订单详情
        function showOrderDetail(strorders, type){
            if(type=='2'){
                Dialog.alert("'成功率'不支持订单信息查询！");
                return;
            }
            var diag = new Dialog("Diag1");
            diag.Width = 1300;
            diag.Height = 580;
            if ('0' == type) {
                diag.Title = "呼出续保订单查询";
            } else {
                diag.Title = "实际呼出订单查询";
            }

            diag.URL = "MemberReport/ContinueInsureOrderDialog.jsp?orderSns="+strorders;
            diag.onLoad = function() {
                $DW.init();
            };
            diag.ShowMessageRow = true;
            diag.MessageTitle = "查看订单信息";
            diag.show();
            diag.OKButton.hide();
            diag.CancelButton.value = "关闭";
        }

        //显示保单详情
        function showRiskTypeDetail(strorders, type){
            if(type=='2'){
                Dialog.alert("'成功率'不支持订单信息查询！");
                return;
            }
            var diag = new Dialog("Diag1");
            diag.Width = 1300;
            diag.Height = 580;
            if ('0' == type) {
                diag.Title = "呼出续保回购保单查询";
            } else {
                diag.Title = "实际呼出保单查询";
            }

            diag.URL = "MemberReport/ContinueInsureRiskTypeDialog.jsp?orderSns="+strorders;
            diag.onLoad = function() {
                $DW.init();
            };
            diag.ShowMessageRow = true;
            diag.MessageTitle = "查看保单信息";
            diag.show();
            diag.OKButton.hide();
            diag.CancelButton.value = "关闭";
        }
    </script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td id="channel_tree" style="display: none">
            <table width="50" border="0" cellspacing="0" cellpadding="6"
                   class="blockTable">
                <tr>
                    <td style="padding: 6px;"  width="50" class="blockTd">
                        <z:tree id="tree1" style="height:440px;width:120px;"
                                method="com.sinosoft.platform.Channel.treeDataBind"
                                level="3" lazy="true" resizeable="true">
                            <img src="../Framework/Images/icon_drag.gif" width="16" height="16">
                            <p cid='${ID}' level="${TreeLevel}">
                                <input type="checkbox" name="contant" id='contant_${ID}' value='${ChannelCode}' onClick="onCheck(this);">
                                <label for="contant_${ID}"><span id="span_${ID}">${Name}</span> </label>
                            </p>
                        </z:tree>
                    </td>
                </tr>
            </table>
        </td>
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
                <tr>
                    <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" /> 续保统计</td>
                </tr>
                <tr>
                    <td>
                        <z:init method="com.sinosoft.cms.memberreport.ContinueInsureCount.init">
                            <table  cellspacing="0" cellpadding="3">
                                <tr>
                                    <th>统计时间(到期日期) 从：</th>
                                    <td> <input name="startDate" type="text" id="startDate" value="${startDate}" style="width:100px" ztype="date"></td>
                                    <th>至：</th>
                                    <td><input name="endDate" type="text" id="endDate" value="${endDate}"style="width:100px" ztype="date"></td>
                                    <td><input type="checkbox" id="show_channel" onclick="showChannel()"/>订单渠道</td>
                                </tr>
                                <tr>
                                    <td  colspan="5" nowrap>
                                        <z:tButtonPurview>
                                            ${_MemberReport_ContinueInsureCount_Button}
                                        </z:tButtonPurview>
                                    </td>
                                </tr>
                            </table>
                        </z:init>
                    </td>
                </tr>
                <tr>
                    <td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">
                        <z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.ContinueInsureCount.dg1DataBind" lazy="true">
                            <table width="80%" cellpadding="2" cellspacing="0" class="dataTable"  style="text-align: center;">
                            <tr ztype="head" class="dataTableHead">
                                <td width="10%">类别</td>
                                <td width="10%">续保订单数</td>
                                <td width="10%">续保保费(原价)</td>
                                <td width="15%">续保保单</td>
                            </tr>
                            <tr style1="background-color:#FFFFFF"
                                style2="background-color:#F9FBFC" >
                                <td align="center">${_Columns_0}</td>
                                <td align="center"><a style="cursor: pointer;" onClick="showOrderDetail('${_Columns_4}', '${_Columns_5}')">${_Columns_1}</a></td>
                                <td align="center">${_Columns_2}</td>
                                <td align="center"><a style="cursor: pointer;" onClick="showRiskTypeDetail('${_Columns_4}', '${_Columns_5}')">${_Columns_3}</a></td>
                            </tr>
                        </table>
                        </z:datagrid>
                    </td>
                </tr>
                <tr>
                    <td></td>
                </tr>
                <tr>
                    <td style="padding: 0px 5px;">
                        <z:datagrid id="dg2" method="com.sinosoft.cms.memberreport.ContinueInsureCount.dg2DataBind" lazy="true">
                            <table width="80%" cellpadding="2" cellspacing="0" class="dataTable"  style="text-align: center;">
                                <tr ztype="head" class="dataTableHead">
                                    <td width="10%">类别</td>
                                    <td width="10%">客服呼出回购订单数</td>
                                    <td width="10%">客服呼出回购保费(原价)</td>
                                    <td width="15%">客服呼出回购保单</td>
                                </tr>
                                <tr style1="background-color:#FFFFFF"
                                    style2="background-color:#F9FBFC" >
                                    <td align="center">${_Columns_0}</td>
                                    <td align="center"><a style="cursor: pointer;" onClick="showOrderDetail('${_Columns_4}', '${_Columns_5}')">${_Columns_1}</a></td>
                                    <td align="center">${_Columns_2}</td>
                                    <td align="center"><a style="cursor: pointer;" onClick="showRiskTypeDetail('${_Columns_4}', '${_Columns_5}')">${_Columns_3}</a></td>
                                </tr>
                            </table>
                        </z:datagrid>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>

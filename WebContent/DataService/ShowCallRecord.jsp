<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css" />
    <script src="../Framework/Main.js"></script>
    <script type="text/javascript">
        // 呼出状态修改
        function updateCallRecord(){
            var dc = new DataCollection();
            var arr = DataGrid.getSelectedData("dg2");
            if (!arr || arr.getRowCount() == 0) {
                Dialog.alert("请先选择要编辑的条目！");
                return;
            }
            var id = arr.get(0,"id");
            var remark = $V("rem");

            dc.add("id", id);
            dc.add("remark", remark);
            Server.sendRequest("com.sinosoft.cms.dataservice.ContinueInsureManage.updateCallRecord",dc,function() {
                var response = Server.getResponse();
                if (response.Status == 1) {
                    Dialog.alert(response.Message,function(){
                        DataGrid.loadData("dg2");
                    });
                } else {
                    Dialog.alert(response.Message);
                }

            })
        }

        function setValue(){
            var arr = DataGrid.getSelectedData("dg2");
            $S("rem", arr.get(0,"remark"));
        }
    </script>
</head>
<body class="dialogBody">
    <div>
        <z:init>
            <table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
                <tr>
                    <td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
                        <z:datagrid id="dg2" method="com.sinosoft.cms.dataservice.ContinueInsureManage.showCallRecord" size="5" scroll="true" lazy="false">
                            <table width="100%" cellpadding="2" cellspacing="0"
                                   class="dataTable" style="text-align: center;table-layout : fixed;"fixedHeight="480px" >
                                <tr ztype="head" class="dataTableHead">
                                    <td width="50" ztype="RowNo" style="text-align:center;"><strong>第几次</strong></td>
                                    <td width="120" style="text-align:center;"><b>记录时间</b></td>
                                    <td width="80" style="text-align:center;"><b>记录人</b></td>
                                    <td width="180" style="text-align:center;"><b>备注</b></td>
                                </tr>
                                <tr  style="background-color:#F9FBFC" onclick="setValue()">
                                    <td style="text-align:center;">&nbsp;</td>
                                    <td style="text-align:center;" title="${createDate}">${createDate}</td>
                                    <td style="text-align:center;" title="${servicePerson}">${servicePerson}</td>
                                    <td style="text-align:center;" title="${remark}">${remark}</td>
                                </tr>
                            </table>
                        </z:datagrid>
                    </td>
                </tr>
            </table>
            <table width="100%" cellpadding="2" cellspacing="0" class="blockTable" id="remark">
                <tr height="200px">
                    <td align="center"><textarea id="rem"
                                                 style="width: 300px; height: 100px;resize: none" ></textarea>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: center;">
                        <input id="update" name="update" onClick="updateCallRecord();"
                               type='button' value='备注修改'>
                    </td>
                </tr>
            </table>
        </z:init>
    </div>
</body>
</html>
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
        // 增加呼出记录
        function addCallRecord() {
            var dc = Form.getData("from");
            if(Verify.hasError()){
                return;
            }

            Server.sendRequest("com.sinosoft.cms.dataservice.ContinueInsureManage.addCallStatus",dc,function() {
                var response = Server.getResponse();
                if (response.Status == 1) {
                    Dialog.alert(response.Message,function(){
                        Dialog.close();
                        DataGrid.loadData("dg1");
                    });
                } else {
                    Dialog.alert(response.Message);
                }

            })
        }
    </script>
</head>
<body class="dialogBody">
<form id="from">
    <div>
        <z:init>
            <table width="100%" border="0" cellspacing="0" cellpadding="5" class="blockTable">
                <tr height="50px">
                </tr>
                <tr valign="middle">
                    <td width="10%"></td>
                    <th>是否接听</th>
                    <td valign="middle">
                        <z:select style="width:100px;">
                        <select name="callConnect" id="callConnect" style="width: 100px">
                            <option value="1">是</option>
                            <option value="2">否</option>
                        </select>
                        </z:select></td>
                    <td width="10%"></td>
                </tr>
                <tr>
                    <td width="10%"></td>
                    <th>呼出记录</th>
                    <td>
                        <textarea id="remark" name="remark" rows="5" style="width: 250px;resize: none"></textarea>
                    </td>
                    <td width="10%"></td>
                </tr>
                <tr>
                    <td colspan="4" style="text-align: center;">
                        <input name="importBtn" type="button" class="inputButton" id="importBtn"
                               onClick="return addCallRecord()" value="提 交" />
                    </td>
                </tr>
            </table>
            <input type="hidden" value="${orderSn}" id="orderSn" name="orderSn">
            <input type="hidden" value="${memberId}" id="memberId" name="memberId">
        </z:init>
    </div>
</form>
</body>
</html>
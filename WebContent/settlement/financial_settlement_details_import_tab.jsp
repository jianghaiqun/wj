<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>数据导入</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js" type="text/javascript"></script>
    <script type="text/javascript">

        function readFile(filePath) {
            var dc = new DataCollection();
            dc.add("branchCode", $V("branchCode"));
            dc.add("invoiceDate", $V("invoiceDate"));
            dc.add("batchNumber", $V("batchNumber"));
            dc.add("filePath", filePath);
            Server.sendRequest("com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.excelImport", dc, function (response) {
                if (response.Status == 1) {
                    //成功。
                    var p = new Progress(response.get("TaskID"), "正在解析Excel文件...", 300, 100);
                    p.show();
                } else {
                    Dialog.alert(response.Message);
                }
                refreshBatchNumber();
            });
        }
        function submitForm() {
            if (Verify.hasError()) {
                return false;
            }
            Dialog.alert("文件正在上传。请耐心等待");
            return true;
        }


        function afterUpload(status, errorMessage, filePath) {
            Dialog.closeAlert();
            switch (status) {
                case "success" :
                    Dialog.alert("上传成功，准备解析数据！", function () {
                        readFile(filePath);
                    });
                    break;
                case "error" :
                    Dialog.alert(errorMessage);
                    break;
                default :
                    break;
            }
        }


        function refreshBatchNumber(){
            var dc = new DataCollection();
            Server.sendRequest("com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.refreshBatchNumber", dc, function (response) {
                if (response.Status == 1) {
                    $S("batchNumber",response.get("batchNumber"));
                }else{
                    Dialog.alert("自动刷新批次号失败,请手动修改批次号.");
                }
            });
        }


    </script>
    <style type="text/css">
        li span{
            color: red;
        }
    </style>
</head>
<body>
<div style="display:none;">
    <iframe src="javascript:void(0);" name="fsdTargetFrame" width="0" height="0" frameborder="0"></iframe>
</div>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
       style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
                <tr>
                    <td>
                        <form action="SimpleFileUploadHandler.jsp" method="post" enctype="multipart/form-data"
                              onsubmit="return submitForm()" target="fsdTargetFrame">
                            <z:init method="com.sinosoft.cms.settlement.FinancialSettlementDetailsManager.init">
                                <label for="branchCode">机构：</label>
                                <z:select id="branchCode" style="width:250px"
                                          verify="NotNull">${branchSelect}</z:select>
                                <label for="invoiceDate">开票时间：</label><input name="invoiceDate"
                                                                             id="invoiceDate"
                                                                             verify="Date&&NotNull"
                                                                             value="${invoiceDate}" type="text"
                                                                             size="15" ztype="date"/>
                                <label for="batchNumber">批次号：</label>
                                <input type="text" name="batchNumber" id="batchNumber" verify="NotNull"
                                       value="${batchNumber}">
                                <input type="hidden" name="maxFileSize" value="maxFileSize">
                                <label for="file">Excel文件：</label>
                                <input type="file" id="file" name="file"
                                       verify="请选择xlsx格式的文件|Regex=^.*?\.(xlsx)$&&NotNull"
                                       accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                                <input type="submit" value="上传">
                            </z:init>
                        </form>
                        <a href="../DataService/ADTemplate/FinancialSettlementDetailsTemplate.xlsx">导入模板下载</a><br>
                    </td>
                </tr>
                <tr>
                    <td>
                        <ol>
                            <li>导入前请再三确认规则</li>
                            <li>1：上传文件必须为<span>XLSX</span>类型，单元格类型请严格按照模板中统一设置,<span>请勿随意修改</span>；<br></li>
                            <li>2：上传前要选择对应<span>机构</span>和<span>开票时间</span>；<br></li>
                            <li>3：“保监险种类别”对应保监报表中大类险种，“保监类别细分类”对应保监明细险种，<span> 无细分类填写与大类相同的数据或不填。</span>举例：意外伤害险为大类险种，一年期以内业务为明细险种；<br></li>
                            <li>4：“保单状态”填“<span>承保</span>”、“<span>撤单</span>”、“<span>退保</span>”；<br></li>
                            <li>5：“渠道归属”填“<span>线下</span>”、“<span>第三方</span>”、“<span>自营</span>”；<br></li>
                            <li>6：如果无法区分新单与续期，将保费及对应的手续费都填到新单中；保费，手续费格式为<span>四位小数</span>；<br></li>
                            <li>7：保险公司名称填写全称；正规汉字输入，勿填入空格和标点符号；<br></li>
                            <li>8：保险公司性质填写“<span>产险</span>”或“<span>人身险</span>”；<br></li>
                            <li>9：<span>保单号，保单状态，首期，续期保费，首期，续期手续费，保险公司名称，开票时间</span>均不能为空,共同判断唯一性。<br></li>
                            <li>10：请在导入过程中留意你导入时的批次号，后续可通过该号码批量删除这一批数据。</li>
                        </ol>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
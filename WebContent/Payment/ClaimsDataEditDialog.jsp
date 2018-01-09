<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>

<script type="text/javascript">
function upload(){
	var diag = new Dialog("Diag2");
	diag.Width = 530;
	diag.Height = 200;
	diag.Title = "上传理赔资料";
	diag.URL = "Payment/ClaimsDataUploadDialog.jsp?claimsNo="+$V("claimsNo") + "&claimsItemsId="+$V("claimsItemsId")+"&productId="+$V("productId");
	diag.onLoad = function(){
	};
    diag.OKEvent=OK;
	diag.show();
}

function OK(){
	$DW.upload();
}

function RepeatUpload(ext,id){
	if (id == null || id == '') {
		var arr = DataGrid.getSelectedValue("dg3");
		if(!arr||arr.length==0){
			Dialog.alert("请先选择要重新上传的理赔资料！");
			return;
		}
		id = arr[0];
	}
	
	var diag = new Dialog("Diag2");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "重新上传";
	diag.URL = "Payment/ClaimsDataReUpload.jsp?ID="+id+"&suffix="+ext;
	diag.OKEvent = RepeatOK;
	diag.show();
	diag.CancelButton.value="关闭";
}

function RepeatOK(){
	$DW.RepeatUpload();
}

function del(id){
	if (id == null || id == '') {
		var arr = DataGrid.getSelectedValue("dg3");
		if(!arr||arr.length==0){
			Dialog.alert("请先选择要删除的理赔文件！");
			return;
		}
		id = arr[0];
	}
	Dialog.confirm("确定要删除吗？",function(){
		var dc = new DataCollection();	
		dc.add("id", id);
		Server.sendRequest("com.sinosoft.cms.payment.PaymentManage.delClaimsFile",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("删除成功");
        		DataGrid.loadData("dg3");
			}
		});
	});
}

function clickView(ext,path){
	ext = ext.toUpperCase();
	if (ext != 'BMP' && ext != 'JPG' && ext != 'JPEG' && ext != 'PNG' && ext != 'GIF' && ext != 'TIF' ) {
		Dialog.alert("只有图片才能预览");
		return;
	}
	
	var diag = new Dialog("Diag4");
	diag.Width = 800;
	diag.Height = 600;
	diag.Title = "图片预览";
	diag.URL = "Payment/ImagePre.jsp?path="+path+"&height=600&width=800";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function doSearch(){
	DataGrid.setParam("dg3", Constant.PageIndex, 0);
	DataGrid.setParam("dg3", "claimsNo", $V("claimsNo"));
	DataGrid.loadData("dg3");
}

function onFileUploadCompleted(){
	doSearch();
	if($D){
		setTimeout(function(){$D.close()},100);
	}
}

function companyUpload(){
	var arr = DataGrid.getSelectedData("dg3");
	if (arr == null || arr.getRowCount() == 0){
		Dialog.alert("请先选择要向保险公司上传的文件 ！");
		return;
	}
	
	if ($V("productId").indexOf("2049") != 0) {
		Dialog.alert("只有安联才可以向保险公司上传文件！");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("dt", arr);
	dc.add("id", $V("id"));
	Dialog.wait("正在上传......"); 
	Server.sendRequest("com.sinosoft.cms.payment.PaymentManage.sendFile",dc,function(response){
		Dialog.closeEx();
		Dialog.alert(response.Message);
	});
}

function downFiles() {
	
	var dt = DataGrid.getSelectedData("dg3");
	if (dt == null || dt.getRowCount() == 0){
		Dialog.alert("请先选择要下载的文件 ！");
		return;
	}
	var dc = new DataCollection();
	dc.add("dt", dt);
	dc.add("fileName", $V("claimsNo"));
	Dialog.wait("正在打包......"); 
	Server.sendRequest("com.sinosoft.cms.payment.PaymentManage.condense",dc,function(response){
		Dialog.closeEx();
		if (response.Status == '0') {
			Dialog.alert(response.Message);
		} else {
			$("BatchDownload").href=('./download.jsp?FilePath='+response.Message);
			$("BatchDownload").click();
		}
		
	});
	

}
</script>
</head>
<body class="dialogBody">
	<table width="100%" border="0" cellspacing="0" cellpadding="1"
		class="blockTable">
		<tr height="20px">
			<td style="padding: 0px 5px;"></td>
		</tr>
		<z:init method="com.sinosoft.cms.payment.PaymentManage.initDialog">
		<input name="id" type="hidden" value="${id}" id="id" />
		<input name="claimsItemsId" type="hidden" value="${claimsItemsId}" id="claimsItemsId" />
		<input name="productId" type="hidden" value="${productId}" id="productId" />
		<input name="id" type="hidden" value="${claimsNo}" id="claimsNo" />
		<a id="BatchDownload" href="" target="_blank"></a>
		</z:init>
		<tr>
			<td style="padding: 0px 5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">
					<tr>
					    <td style="padding-top: 3px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
					    	<z:tbutton onClick="upload();"><img src="../Icons/icon018a4.gif" />上传理赔资料文件</z:tbutton> 
					    	<z:tbutton onClick="companyUpload();"><img src="../Icons/icon018a4.gif" />保险公司上传文件</z:tbutton> 
					    	<z:tbutton onClick="downFiles();"><img src="../Icons/icon018a4.gif" />下载理赔资料文件</z:tbutton> 
					    
					    </td>
					</tr>
					<tr>
						<td
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
							<z:datagrid id="dg3"
								method="com.sinosoft.cms.payment.PaymentManage.queryCliamsData" scroll="true">
								<table width="100%" cellpadding="2" cellspacing="0"
									class="dataTable"
									style="text-align: center; table-layout: fixed;"
									fixedHeight="470px">
									<tr ztype="head" class="dataTableHead">
										<td width="30" ztype="RowNo" style="text-align: center;"><strong>序号</strong></td>
										<td width="20" ztype="selector" field="id">&nbsp;</td>
										<td width="120" style="text-align:center;"><b>理赔资料名称</b></td>
										<td width="50" style="text-align:center;"><b>文件大小</b></td>
										<td width="50" style="text-align:center;"><b>文件格式</b></td>
										<td width="100" style="text-align:center;"><b>文件缩略图</b></td>
										<td width="100" style="text-align:center;"><b>操作</b></td>
									</tr>
									<tr style="background-color: #F9FBFC">
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td style="text-align: left;" title="${claimsDataName}">${claimsDataName}</td>
										<td style="text-align: left;" title="${fileSize}">${fileSize}</td>
										<td style="text-align: left;" title="${fileSuffix}">${fileSuffix}</td>
										<td style="text-align: left;" ><img src='${remark2}' width="92px" height="52px" title='${fileName}' ></td>
										<td style="text-align: left;"><a href="javascript:void(0);" onClick="clickView('${fileSuffix}','${filePath}');">预览</a>|<a href="./download.jsp?FilePath=${downPath}" target="_blank">下载</a>|<a href="javascript:void(0);" onClick="RepeatUpload('${fileSuffix}','${id}');">重新上传</a>|<a href="javascript:void(0);" onClick="del('${id}');">删除</a></td>
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
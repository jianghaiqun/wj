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
	diag.Width = 600;
	diag.Height = 100; 
	diag.Title = "增加图片";
	diag.URL = "wap/WapProductUploadPicture.jsp?productid="+$V("productid");
	diag.ShowButtonRow = false;
	diag.show();
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
		Server.sendRequest("com.sinosoft.wap.ProductManage.delWapProductPicture",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("删除成功");
        		DataGrid.loadData("dg3");
			}
		});
	});
}

function doSearch(){
	DataGrid.setParam("dg3",Constant.PageIndex,0);
	DataGrid.loadData("dg3");
}
function clickView(path){
	
	var diag = new Dialog("Diag4");
	diag.Width = 800;
	diag.Height = 600;
	diag.Title = "图片预览";
	diag.URL = "Payment/ImagePre.jsp?path="+path+"&height=600&width=800";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function setSortNum(){
	DataGrid.save("dg3","com.sinosoft.wap.ProductManage.setPictureSortNum",function(){
		doSearch();
	});
}
Page.onLoad(function() {
	$("dg3").afterEdit = function (tr, dr) {
		dr.set("orderFlag", $V("orderFlag"));
		return true;
	}
});
</script>
</head>
<body class="dialogBody">
	<table width="100%" border="0" cellspacing="0" cellpadding="1"
		class="blockTable">
		<tr height="20px">
			<td style="padding: 0px 5px;"></td>
		</tr>
		<z:init>
		<input name="productid" type="hidden" value="${productid}" id="productid" />
		</z:init>
		<tr>
			<td style="padding: 0px 5px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="6"
					class="blockTable" style="table-layout: fixed;">
					<tr>
					    <td style="padding-top: 3px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
					    	<z:tbutton onClick="upload();"><img src="../Icons/icon018a4.gif" />上传图片</z:tbutton>
					    	<z:tbutton onClick="setSortNum()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>调整顺序</z:tbutton>
					    </td>
					</tr>
					<tr>
						<td
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
							<z:datagrid id="dg3"
								method="com.sinosoft.wap.ProductManage.queryWapPicture" scroll="true">
								<table width="100%" cellpadding="2" cellspacing="0"
									class="dataTable"
									style="text-align: center; table-layout: fixed;"
									fixedHeight="470px">
									<tr ztype="head" class="dataTableHead">
										<td width="30" ztype="RowNo" style="text-align: center;"><strong>序号</strong></td>
										<td width="20" ztype="selector" field="id">&nbsp;</td>
										<td width="100" style="text-align:center;"><b>图片类型</b></td>
										<td width="80" style="text-align:center;"><b>图片顺序</b></td>
										<td width="70" style="text-align:center;"><b>操作</b></td>
									</tr>
									<tr style="background-color: #F9FBFC">
										<td>&nbsp;</td>
										<td>&nbsp;</td>
										<td style="text-align: left;" >${typeName}</td>
										<td style="text-align: left;" >${orderFlag}</td>
										<td style="text-align: left;"><a href="javascript:void(0);" onClick="clickView('${PictureUrl}');">预览</a>&nbsp;|&nbsp;<a href="javascript:void(0);" onClick="del('${ID}');">删除</a></td>
									</tr>
									<tr ztype="edit">
						                  <td>&nbsp;</td>
						                  <td>&nbsp;</td>
						                  <td>${typeName}</td>
										  <td> <input name="orderFlag" type="text" id="orderFlag" value="${orderFlag}" size="20" style="width: 100%"></td>
						                  <td> </td>
						                  
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
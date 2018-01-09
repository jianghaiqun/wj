<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片分类</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function upload(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 200;
	diag.Title = "图片上传";
	diag.URL = "Travel/ImageDialog.jsp?flag=OtProductImage&productId="+$V("productId");
	diag.OKEvent = OK;	
	diag.show();
}

function OK(){
	$DW.upload();
}

function onReturnBack(returnID){
	DataList.loadData("dg2");
}

function del(imageID){
	var count=1;
	if(!imageID){
	
		var arr = $NV("ImageID");
		if(!arr||arr.length==0){
			Dialog.alert("请先选择要删除的图片！");
			return;
		}
		imageID = arr.join();
		count=arr.length;
	}
	Dialog.confirm("你确认删除这"+count+"张图片吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",imageID);
		Server.sendRequest("com.sinosoft.cms.travel.ProductManage.delImage",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataList.loadData("dg2");
				}
			});
		});
	});
 
}



function clickView(path){
	var diag = new Dialog("Diag4");
	diag.Width = 500;
	diag.Height = 400;
	diag.Title = "图片浏览";
	diag.URL = "Travel/ProductImagePre.jsp?path="+path;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

</script>
</head>
<body >
	<z:init>
	<input type="hidden" id="productId" value="${productId}" />
		<table width="100%" border="0" cellspacing="6" cellpadding="0"
			style="border-collapse: separate; border-spacing: 6px;">
			<tr valign="top">
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="blockTable">
						<tr>
							<td style="padding: 6px 10px;" class="blockTd">
								<div style="float: left">
									<z:tbutton id="uploadButton" priv="image_modify" onClick="upload()"> <img src="../Icons/icon030a2.gif" />上传</z:tbutton>
									<z:tbutton id="delButton" priv="image_modify" onClick="del()"> <img src="../Icons/icon030a3.gif" />删除</z:tbutton>
								</div>
							</td>
						</tr>
						<tr>
							<td style="padding: 0 5px">
								<ul class="img-wrapper" style="height: 380px; overflow: hidden; display: block; margin: 0;">
									<z:datalist id="dg2" method="com.sinosoft.cms.travel.ProductManage.dg2DataBind" size="8" page="true" >
										<li>
											<dl>
												<dt>
													<a href='#;' hidefocus>
														<em>
															<img imageid='${id}' width="130px" height="130px" src='${path}' title=''  >
														</em>
												    </a>
												</dt>
												<dd>
													<em> 
														<label> 
															<input id='ImageID_box${id}' name='ImageID' type='checkbox' value='${id}'> 
															<a href='#;'><img src='../Framework/Images/icon_zoomout.gif' alt='view' title='预览' onClick="clickView('${path}');"></a>
															<a href='#;'><img src='../Framework/Images/icon_delete15.gif' alt='delete' title='删除' onClick="del('${id}');"></a>
														</label>
													</em>
												</dd>
											</dl>
										</li>
									</z:datalist>
								</ul>
							</td>
						</tr>
						<tr>
							<td style="padding: 0px 5px;"><z:pagebar target="dg2" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</z:init>
</body>
</html>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(doSearch);

function clickImage(ele){
	var box = $("ImageID_box"+$(ele).$A("ImageID"));
	if(box.checked){
		box.checked = false;
	}else{
		box.checked = true;
	}
}
function view(id){
	  var url= "underwriting/ImageViewDialog.jsp?ID="+id+"&infoID="+$V("infoID");
	  zoom(url);
}

function clickView(id){
	view(id);
}

function zoom(url){
	var pw = window.top;
	var doc = pw.document;
	var sw = Math.max(doc.documentElement.scrollWidth, doc.body.scrollWidth);;
	var sh = Math.max(doc.documentElement.scrollHeight, doc.body.scrollHeight);;
	var cw = doc.compatMode == "BackCompat"?doc.body.clientWidth:doc.documentElement.clientWidth;
	var ch = doc.compatMode == "BackCompat"?doc.body.clientHeight:doc.documentElement.clientHeight;
	var zoomdiv = pw.$("_zoomdiv");
	if(!zoomdiv){
		zoomdiv = pw.document.createElement("div");	
		zoomdiv.id = "_zoomdiv";
		$E.hide(zoomdiv);
	 	pw.document.getElementsByTagName("body")[0].appendChild(zoomdiv);
		zoomdiv.innerHTML="\
			<div id='_zoomBGDiv' style='background-color:#222;position:absolute;top:0px;left:0px;opacity:0.9;filter:alpha(opacity=90);width:" + sw + "px;height:" + sh + "px;z-index:991'></div>\
			<iframe id='_zoomIframe' src='picturezoom.htm' frameborder='0' allowtransparency='true' style='background-color:#transparent;position:absolute;top:0px;left:0px;width:" + sw + "px;height:" + sh + "px;z-index:991;'></iframe>\
			"
	}
	pw.$("_zoomIframe").src=url;
	$E.show(zoomdiv);
}

function clickDelete(imageID){
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
		Server.sendRequest("com.wangjin.underwriting.UnderwritingOfflineImages.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					doSearch();
				}
			});
		});
	});
}

function doSearch(){
	DataList.setParam("dg1", "infoID", $V("infoID"));
	DataList.loadData("dg1");
}

function upload(){
	var diag = new Dialog("Diag2");
	diag.Width = 500;
	diag.Height = 260;
	diag.Title = "图片上传";
	diag.URL = "underwriting/ImageUploadDialog.jsp?infoID="+$V("infoID");
	diag.OKEvent = OK;	
	diag.show();
}

function OK(){
	$DW.upload();
}

function onUploadCompleted(){
	doSearch();
}

function onFileUploadCompleted(){
	doSearch();
	if($D){
		setTimeout(function(){$D.close()},100);
	}
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.wangjin.underwriting.UnderwritingOfflineInfo.initDialog">
<form id="form1">
		<table width="100%"  align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td valign="top">
				<table>
				 	<tr>
				 	 	<td align="right" height="30" width="90px">产品名称：</td>
				 	 	<td width="140px">${productName}</td>
				 	 	<td align="right" height="30" width="40px">姓名：</td>
				 	 	<td width="50px">${name}</td>
				 	 	<td align="right" height="30" width="50px">手机号：</td>
				 	 	<td width="80px">${mobile}</td>
				 	 	<td align="right" height="30" width="40px">邮箱：</td>
				 	 	<td width="100px">${email}</td>
				 	 	
				 	</tr>
				 	<tr>
				 		<td align="right" height="30">情况说明：</td>
				 	 	<td colspan="6"><textarea style="width:500px;height:100px;" disabled>${situationDesc}</textarea></td>
				 	</tr>
				 	<tr>
				 		<td align="right">保险公司邮箱：</td>
				 		<td colspan="6"><input type="text" id="companyEmail" name="companyEmail" value="${companyEmail}" size="30"/></td>
				 	</tr>
				 	<tr>
				 		<td align="right">邮件备注：</td>
				 		<td colspan="6"><textarea id="remark" name="remark" style="width:500px;height:50px"></textarea></td>
				 	</tr>
				</table>	     
			</td>
	    </tr>
	    <tr>
	    	<td>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" class="blockTable">
					<tr>
						<td style="padding:6px 10px;" class="blockTd">
							<div style="float: left"> 
								<z:tbutton id="uploadButton" priv="image_modify" onClick="upload()"><img src="../Icons/icon030a2.gif" />补充材料</z:tbutton> 
								<z:tbutton id="delButton" priv="image_modify" onClick="clickDelete()"><img src="../Icons/icon030a3.gif" />删除</z:tbutton>
								&nbsp;&nbsp;&nbsp;&nbsp;<label> <input name="checkbox" type="checkbox" id="AllCheck" onClick="selectAll(this,'ImageID')"> 全选</label>
								<input type="hidden" id="infoID" name="infoID" value="${infoID}" />
							</div>
						</td>
					</tr>
					<tr>
						<td style="padding:0 5px">
						<ul class="img-wrapper" style="height:380px; overflow:hidden; display:block; margin:0;">
							<z:datalist id="dg1" method="com.wangjin.underwriting.UnderwritingOfflineImages.dg1DataList" >
								<li style="width:90px;height:120px">
								<dl>
									<dt><a href='#;' hidefocus>
											<em style="display: table-cell;zoom: 1;vertical-align: middle;text-align: center;width: 80px;height: 90px;line-height: 100px;">
												<img imageid='${id}' style="width:80px;height:77px" src='${thum_url1}' title='' onClick='clickImage(this)' onDblClick="clickView('${id}');">
											</em>
										</a></dt>
									<dd><input id='ImageID_box${id}' name='ImageID' type='checkbox' value='${id}'/><a href='#;'><img src='../Framework/Images/icon_zoomout.gif' alt='view' title='预览' onClick="clickView('${id}');"></a><a href='#;'><img src='../Framework/Images/icon_delete15.gif' alt='delete' title='删除' onClick="clickDelete('${id}');"></a></dd>
								</dl>
								</li>
							</z:datalist>
						</ul>
						</td>
					</tr>
				 	
				</table>
			</td>
		</tr>
	    </table>
	</form>
</z:init>
</body>
</html>
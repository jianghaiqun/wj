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

function add(param){
	var ParentID;
	var catalogName;
	if(!param){
		if(Tree.CurrentItem!=null){
			ParentID = Tree.CurrentItem.getAttribute("cid");
			catalogName = Tree.CurrentItem.innerText;
		}
	} else {
		ParentID = param.substring(0, param.indexOf(","));
		catalogName = param.substring(param.indexOf(",")+1);
	}
	if(!ParentID||ParentID=="null"||ParentID==null){
		ParentID = "0";
		catalogName = "图片库";
	}
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 150;
	diag.Title = "新建图片分类";
	diag.URL = "Resource/ImageLibAddDialog.jsp?ParentID=" +ParentID;
	diag.onLoad = function(){
		$DW.Selector.setValueEx($DW.$("ParentID"),ParentID,catalogName);
		$DW.$("Name").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
    }
	Server.sendRequest("com.sinosoft.cms.resource.ImageLib.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				Tree.loadData("tree1");
				$D.close();
			}
		});
	});
}

function upload(){
	var emailType = $V("emailType");
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 250;
	diag.Title = "图片上传";
	diag.URL = "Document/MailConfigImageShowUpload.jsp?emailType="+emailType;
	diag.OKEvent = OK;	
	diag.show();
}

function OK(){
	$DW.upload();
}

function edit(imageID){
	if(!imageID){
		var arr = $NV("ImageID");
		
		
		if(!arr){
			Dialog.alert("请先选择要编辑的图片！");
			return;
		}
		
		if(arr.length>1){
			Dialog.alert("请选择一项进行操作！");
			return;
		}

		imageID = arr.join();
	  }
		var diag = new Dialog("Diag1");
		diag.Width = 500;
		diag.Height = 400;
		diag.Title = "编辑图片";
		diag.URL = "Document/MailConfigImageShowEditDialog.jsp?ID="+imageID;
		diag.OKEvent = editSave;
		diag.show();
	
}

function editSave(){
	$DW.RepeatUpload();
	//DataList.loadData("dg1");
	//$D.close();
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
		Server.sendRequest("com.sinosoft.cms.document.MailConfigImage.mailConfigImageDel",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataList.loadData("dg1");
				}
			});
		});
	});
 
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'Name'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	var keyWord = $V("Name");
	if (keyWord == "请输入图片名称") {
		$S("Name","");
	}
}

function clickImage(ele){
	var box = $("ImageID_box"+$(ele).$A("ImageID"));
	if(box.checked){
		box.checked = false;
	}else{
		box.checked = true;
	}
}

function clickEdit(id){
	edit(id);
}

function clickDelete(id){
	del(id);
}
</script>
</head>
<body oncontextmenu="return false;">
<input type="hidden" id="emailType" name="emailType" value="<%=request.getParameter("emailType")%>">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="blockTable">
			<tr>
				<td style="padding:6px 10px;" class="blockTd">
				<div style="float: left">
					<z:tbutton onClick="upload()"><img src="../Icons/icon018a4.gif" />上传</z:tbutton> 
                    <z:tbutton onClick="edit()"><img src="../Icons/icon018a4.gif" />编辑</z:tbutton> 
                     <z:tbutton onClick="del()"><img src="../Icons/icon018a4.gif" />删除</z:tbutton> 
				</div>
				</td>
			</tr>
			<tr>
				<td style="padding:2px 10px;">
				</td>
			</tr>
			<tr>
				<td style="padding:0 5px">
				<ul class="img-wrapper" style="height:380px; overflow:hidden; display:block; margin:0;">
					<z:datalist id="dg1" method="com.sinosoft.cms.document.MailConfigImage.dg1DataList" size="10" page="true">
						<li>
						<dl>
							<dt>
								<a href='#;' hidefocus>
									<em>
										<img imageid='${id}' width="130px" height="130px" src='../${ImagePath}'  oncontextmenu='showImageMenu(event,"${id}");' onClick='clickImage(this)'">
									</em>
								</a>
							</dt>
							<dd><em><label><input id='ImageID_box${id}' name='ImageID' type='checkbox' value='${id}'>${ImageDesc}</label></em>
							<a href='#;'>
							<img src='../Framework/Images/icon_edit15.gif' alt='edit' title='编辑' onClick="clickEdit('${id}');"></a>
							<a href='#;'>
							<img src='../Framework/Images/icon_delete15.gif' alt='delete' title='删除' onClick="clickDelete('${id}');">
							</a>
							</dd>
						</dl>
						</li>
					</z:datalist>
				</ul>
				</td>
			</tr>
			<tr>
				<td style="padding:0px 5px;"><z:pagebar target="dg1" /></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>

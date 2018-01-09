<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑礼品</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
function onCompleted(returnValue, errorMessage) {
		if(returnValue == '0'){
			document.getElementById("showdiv").style.display="";
			document.getElementById("showmessage").innerHTML=errorMessage;
		}else{
			document.getElementById("showdiv").style.display="none";
			document.getElementById("showmessage").innerHTML="";
		}
		
	}
	/*提交表单*/
	function submitform() {
		var name = document.getElementById("name").value;
		if (name == '' || name == null) {
			onCompleted('0', '您还没有填写礼品名称');
			return;
		}
		var presentImages = document.getElementById("presentImages").value;
		if (presentImages == '' || presentImages == null) {
			onCompleted('0', '您还没有选择图片');
			return;
		}
		var metaDescription = UE.getEditor('metaDescription').getContent()
		if (metaDescription == '' || metaDescription == null) {
			onCompleted('0', '您还没有填写页面描述');
			return;
		}
		document.getElementById("submitbutton").click();
	}

//根据渠道显示礼品分类	
function checkboxonchange()
{
var r=document.getElementsByTagName("input"); 
var aa='';
    for(var i=0;i<r.length-1;i++){
         if(r[i].checked){

    if(r[i].type == "checkbox"  ){
     aa += r[i].value+",";
    }
}
}

var presentCategoryName = jQuery("#presentCategoryName");
jQuery.post("present!findAllChannelpresentCategory.action", {

			"channelList.id" :aa
		}, function(data, textStatus) {
			if (data != "") {
				presentCategoryName.html(data);
			} else {
				presentCategoryName.html("");
	}
	}	)



}			


jQuery().ready(function() {

	// 查询礼品属性
	jQuery("#presentTypeId").change( function() {
		jQuery(".presentAttributeContentTr").remove();
		var presentTypeId = jQuery("#presentTypeId").val();
		jQuery.ajax({
			url: "present_attribute!ajaxPresentAttribute.action",
			dataType: "json",
			data:{presentTypeId: presentTypeId},
			async: false,
			success: function(json) {
				var presentAttributeTrHtml = "";
				jQuery.each(json, function(i) {
					if(json[i]["attributeType"] == "text") {
						presentAttributeTrHtml += '<tr class="presentAttributeContentTr"><th>' + json[i].name + ':</th><td><input type="text" name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="formText {required: true}"' : ' class="formText"') + ' />' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeType"] == "number") {
						presentAttributeTrHtml += '<tr class="presentAttributeContentTr"><th>' + json[i].name + ':</th><td><input type="text" name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="formText {required: true, number: true}"' : ' class="formText {number: true}"') + ' />' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeType"] == "alphaint"){
						presentAttributeTrHtml += '<tr class="presentAttributeContentTr"><th>' + json[i].name + ':</th><td><input type="text" name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="formText {required: true, lettersonly: true}"' : ' class="formText {lettersonly: true}"') + ' />' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeType"] == "select") {
						var presentAttributeOption = '<option value="">请选择...</option>';
						for(var key in json[i]["attributeOptionList"]) {
							presentAttributeOption += ('<option value="' + json[i]["attributeOptionList"][key] + '">' + json[i]["attributeOptionList"][key] + '</option>');
						}
						presentAttributeTrHtml += '<tr class="presentAttributeContentTr"><th>' + json[i].name + ':</th><td><select name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="{required: true}"' : '') + '>' + presentAttributeOption + '</select>' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeType"] == "checkbox") {
						var presentAttributeOption = "";
						for(var key in json[i]["attributeOptionList"]) {
							presentAttributeOption += ('<label><input type="checkbox" name="' + json[i].id + '" value="' + json[i]["attributeOptionList"][key] + '"' + ((json[i].isRequired == true) ? ' class="{required: true, messagePosition: \'#' + json[i].id + 'MessagePosition\'}"' : '') +' />' + json[i]["attributeOptionList"][key] + '</label>&nbsp;&nbsp;');
						}
						presentAttributeTrHtml += '<tr class="presentAttributeContentTr"><th>' + json[i].name + ':</th><td>' + presentAttributeOption + ((json[i].isRequired == true) ? '<span id="' + json[i].id + 'MessagePosition"></span><label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeType"] == "date") {
						presentAttributeTrHtml += '<tr class="presentAttributeContentTr"><th>' + json[i].name + ':</th><td><input type="text" name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="formText datePicker {required: true, dateISO: true}"' : ' class="formText datePicker {dateISO: true}"') + ' />' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					}
				})
				jQuery("#presentTypeTr").after(presentAttributeTrHtml);
				jQuery.bindDatePicker();
			}
		});
	});
	<!---====================================投保信息添加 上============================================================------------------->
		// 查询礼品投保属性
	jQuery("#presentInsTypeId").change( function() {
		jQuery(".presentInsAttributeContentTr").remove();
		var presentInsTypeId = jQuery("#presentInsTypeId").val();
		jQuery.ajax({
			url: "present_ins_attribute!ajaxPresentInsAttribute.action",
			dataType: "json",
			data:{presentInsTypeId: presentInsTypeId},
			async: false,
			success: function(json) {
				var presentInsAttributeTrHtml = "";
				jQuery.each(json, function(i) {
					if(json[i]["attributeInsType"] == "text") {
						presentInsAttributeTrHtml += '<tr class="presentInsAttributeContentTr"><th>' + json[i].name + ':</th><td><input type="text" name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="formText {required: true}"' : ' class="formText"') + ' />' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeInsType"] == "number") {
						presentInsAttributeTrHtml += '<tr class="presentInsAttributeContentTr"><th>' + json[i].name + ':</th><td><input type="text" name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="formText {required: true, number: true}"' : ' class="formText {number: true}"') + ' />' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeInsType"] == "alphaint"){
						presentInsAttributeTrHtml += '<tr class="presentInsAttributeContentTr"><th>' + json[i].name + ':</th><td><input type="text" name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="formText {required: true, lettersonly: true}"' : ' class="formText {lettersonly: true}"') + ' />' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeInsType"] == "select") {
						var presentInsAttributeOption = '<option value="">请选择...</option>';
						for(var key in json[i]["attributeOptionList"]) {
							presentInsAttributeOption += ('<option value="' + json[i]["attributeOptionList"][key] + '">' + json[i]["attributeOptionList"][key] + '</option>');
						}
						presentInsAttributeTrHtml += '<tr class="presentInsAttributeContentTr"><th>' + json[i].name + ':</th><td><select name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="{required: true}"' : '') + '>' + presentInsAttributeOption + '</select>' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeInsType"] == "checkbox") {
						var presentInsAttributeOption = "";
						for(var key in json[i]["attributeOptionList"]) {
							presentInsAttributeOption += ('<label><input type="checkbox" name="' + json[i].id + '" value="' + json[i]["attributeOptionList"][key] + '"' + ((json[i].isRequired == true) ? ' class="{required: true, messagePosition: \'#' + json[i].id + 'MessagePosition\'}"' : '') +' />' + json[i]["attributeOptionList"][key] + '</label>&nbsp;&nbsp;');
						}
						presentInsAttributeTrHtml += '<tr class="presentInsAttributeContentTr"><th>' + json[i].name + ':</th><td>' + presentInsAttributeOption + ((json[i].isRequired == true) ? '<span id="' + json[i].id + 'MessagePosition"></span><label class="requireField">*</label>' : '') + '</td></tr>';
					} else if(json[i]["attributeInsType"] == "date") {
						presentInsAttributeTrHtml += '<tr class="presentInsAttributeContentTr"><th>' + json[i].name + ':</th><td><input type="text" name="' + json[i].id + '"' + ((json[i].isRequired == true) ? ' class="formText datePicker {required: true, dateISO: true}"' : ' class="formText datePicker {dateISO: true}"') + ' />' + ((json[i].isRequired == true) ? '<label class="requireField">*</label>' : '') + '</td></tr>';
					}
				})
				jQuery("#presentTypeTr").after(presentInsAttributeTrHtml);
				jQuery.bindDatePicker();
			}
		});
	});
	<!---====================================投保信息添加 下============================================================------------------->
	
	// 礼品图片预览滚动栏
	jQuery(".presentImageArea .scrollable").scrollable({
		speed: 600
	});
	
	// 显示礼品图片预览操作层
	jQuery(".presentImageArea li").livequery("mouseover", function() {
		jQuery(this).find(".presentImageOperate").show();
	});
	
	// 隐藏礼品图片预览操作层
	jQuery(".presentImageArea li").livequery("mouseout", function() {
		jQuery(this).find(".presentImageOperate").hide();
	});
	
	// 礼品图片左移
	jQuery(".left").livequery("click", function() {
		var $presentImageLi = jQuery(this).parent().parent().parent();
		var $presentImagePrevLi = $presentImageLi.prev("li");
		if ($presentImagePrevLi.length > 0) {
			$presentImagePrevLi.insertAfter($presentImageLi);
		}
	});
	
	// 礼品图片右移
	jQuery(".right").livequery("click", function() {
		var $presentImageLi = jQuery(this).parent().parent().parent();
		var $presentImageNextLi = $presentImageLi.next("li");
		if ($presentImageNextLi.length > 0) {
			$presentImageNextLi.insertBefore($presentImageLi);
		}
	});
	
	// 礼品图片删除
	jQuery(".delete").livequery("click", function() {
		var $presentImageLi = jQuery(this).parent().parent().parent();
		var $presentImagePreview = $presentImageLi.find(".presentImagePreview");
		var $presentImageIds = $presentImageLi.find("input[name='presentImageIds']");
		var $presentImageFiles = $presentImageLi.find("input[name='presentImages']");
		var $presentImageParameterTypes = $presentImageLi.find("input[name='presentImageParameterTypes']");
		$presentImageIds.remove();
		$presentImageFiles.after('<input type="file" name="presentImages" hidefocus="true" />');
		$presentImageFiles.remove();
		$presentImageParameterTypes.remove();
		
		$presentImagePreview.html("暂无图片");
		$presentImagePreview.removeAttr("title");
		if (jQuery.browser.msie) {
			if(window.XMLHttpRequest) {
				$presentImagePreview[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = 'scale', src='')";
			}
		}
	});
	
	// 礼品图片选择预览
	var $presentImageScrollable = jQuery(".presentImageArea .scrollable").scrollable();
	var presentImageLiHtml = '<li><div class="presentImageBox"><div class="presentImagePreview">暂无图片</div><div class="presentImageOperate"><a class="left" href="javascript: void(0);" alt="左移" hidefocus="true"></a><a class="right" href="javascript: void(0);" title="右移" hidefocus="true"></a><a class="delete" href="javascript: void(0);" title="删除" hidefocus="true"></a></div><a class="presentImageUploadButton" href="javascript: void(0);"><input type="file" name="presentImages" hidefocus="true" /><div>上传新图片</div></a></div></li>';

})
</script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加礼品<#else>编辑礼品</#if></h1>
		</div>
		<div class="blank"></div>
		<form id="inputForm" class="validate"   action="<#if isAdd??>present!save.action<#else>present!update.action</#if>" method="post" enctype="multipart/form-data"   >
			<input type="hidden" value="<#if isAdd??>Y<#else>N</#if>" name="flag">		
			<table class="inputTable tabContent">
				<tr>
					<th>
						礼品名称:
					</th>
					<td>
						<input type="text" name="present.name" id="name" class="formText {required: true}" value="${(present.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
<!--
				<tr>
					<th>
						路径:
					</th>
					<td>
						<input type="text" name="present.dutyDownLoadPath" class="formText {required: true}" value="${(present.dutyDownLoadPath)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
-->
				<tr >
					<th>
						礼品分类:
					</th>
					<td>
						<select name="present.presentCategory.id" id="presentCategoryName" class="{required: true}">
							<#list presentCategoryTreeList as list>
								<option value="${list.id}"<#if (list.id == present.presentCategory.id)!> selected</#if>>
									<#if list.level != 0>
										<#list 1..list.level as i>------</#list>
									</#if>
									${list.name}
								</option>
							</#list>
						</select>
					</td>
				</tr>

				<tr>
					<th>
						库存分类:
					</th>
					<td>
						<select name="present.stock.id" id="stockName" class="{required: true}">
							<#list stockList as list>
								<option value="${list.id}"<#if (list.id == present.stock.id)!> selected</#if>>
									${list.name}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						栏目选择:
					</th>
					<td>
						<select name="present.catalog" id="catalogName">
							<option value="9440" <#if ( '9440'==present.catalog) !>selected="selected" </#if>>
								手机充值卡
							</option>
							<!--
							<option value="9765" <#if ( '9765'==present.catalog) !>selected="selected" </#if>>
								经验抽奖
							</option>
							-->	
						</select>
					</td>
				</tr>
				<tr>
					<th>
						充值卡类型选择:
					</th>
					<td>
						<select name="present.actCode" id="actCode">
							<option value="A30" selected="selected">
								移动30
							</option>
							<option value="A50" <#if ( 'A50'==present.actCode) !>selected="selected"</#if>>
								移动50
							</option>
							<option value="A100" <#if ( 'A100'==present.actCode) !>selected="selected"</#if>>
								移动100
							</option>
							<option value="B30" <#if ( 'B30'==present.actCode) !>selected="selected"</#if>>
								联通30
							</option>
							<option value="B50" <#if ( 'B50'==present.actCode) !>selected="selected"</#if>>
								联通50
							</option>
							<option value="B100" <#if ( 'B100'==present.actCode) !>selected="selected"</#if>>
								联通100
							</option>
							<option value="C30" <#if ( 'C30'==present.actCode) !>selected="selected"</#if>>
								电信30
							</option>
							<option value="C50" <#if ( 'C50'==present.actCode) !>selected="selected"</#if>>
								电信50
							</option>
							<option value="C100" <#if ( 'C100'==present.actCode) !>selected="selected"</#if>>
								电信100
							</option>
							
						</select>
					</td>
				</tr>
				<tr>
					<th>
						市场售价:
					</th>
					<td>
						<input type="text" name="present.marketPrice" class="formText {required: true, min: 0}" value="${(present.marketPrice)!"0"}" />
						<label class="requireField">*</label>
					</td>
				</tr>
					<tr>
						<th>
							积分:
						</th>
						<td>
						<input type="text" name="present.point" class="formText {required: true, digits: true}" value="${(present.point)!"0"}" />
						<label class="requireField">*</label>
						</td>
					</tr>
				<!-- <tr>
					<th>
						库存量:
					</th>
					<td>
						<input type="text" name="present.store" class="formText {digits: true}" value="${(present.store)!}" title="只允许输入零或正整数，为空表示不计库存" />				 						
					</td>
				</tr>
				
				<tr>
					<th>
						是否精品推荐:
					</th>
					<td>
						<label><input type="radio" name="present.isBest" value="true"<#if (present.isBest == true)!> checked</#if> />是</label>
						<label><input type="radio" name="present.isBest" value="false"<#if (isAdd || present.isBest == false)!> checked</#if> />否</label>
					</td>
				</tr>
				
				-->
				<tr>
					<th>
						是否新品推荐:
					</th>
					<td>
						<label><input type="radio" name="present.isNew" value="true"<#if (present.isNew == true)!> checked</#if> />是</label>
						<label><input type="radio" name="present.isNew" value="false"<#if (isAdd || present.isNew == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<!--
				<tr>
					<th>
						是否热销推荐:
					</th>
					<td>
						<label><input type="radio" name="present.isHot" value="true"<#if (present.isHot == true)!> checked</#if> />是</label>
						<label><input type="radio" name="present.isHot" value="false"<#if (isAdd || present.isHot == false)!> checked</#if> />否</label>
					</td>
				</tr>
				-->
					<!--
				<tr>
					<th>
						是否上架:
					</th>
					<td>
						<label><input type="radio" name="present.isMarketable" value="true"<#if (isAdd || present.isMarketable == true)!> checked</#if> />是</label>
						<label><input type="radio" name="present.isMarketable" value="false"<#if (present.isMarketable == false)!> checked</#if> />否</label>
					</td>
				</tr>-->
	<div id="showdiv"  style="display:none;position:absolute; top:80px; left:480px; z-index:1000;" >
		<div class="messageBox" style="width:200px;">
			<div class="boxTop">
				<div class="boxTitle">提示信息&nbsp;</div>
				<a hidefocus="true" href="#" class="boxClose windowClose"></a>
			</div>
			<div class="boxMiddle">
				<div class="messageContent">
					<span class="icon error">&nbsp;</span>
					<span class="messageText" id="showmessage">
					</span>
				</div>
				<input type="button" hidefocus="true" value="确  定" onclick="document.getElementById('showdiv').style.display='none'"; return false;" class="formButton messageButton">
			</div>
			<div class="boxBottom"></div>
		</div>
	</div>
				<tr> 
					<th>
						上传礼品图片
					</th>
					<td>
						<div class="presentImageArea">
							<div class="example"></div>
							<a class="prev browse" href="javascript:void(0);" hidefocus="true"></a>
							<div class="scrollable">
								<ul class="items">
									  <li>
										<div class="presentImageBox">
											<div class="presentImagePreview png">暂无图片</div>
											<div class="presentImageOperate" display:none;>
												<a class="left" href="javascript: void(0);" alt="左移" hidefocus="true"></a>
												<a class="right" href="javascript: void(0);" title="右移" hidefocus="true"></a>
												<a class="delete" href="javascript: void(0);" title="删除" hidefocus="true"></a>
											</div>
											<a class="presentImageUploadButton" href="javascript: void(0);">
												<#if systemConfig.allowedUploadImageExtension != "">
													<input type="file" name="presentImages" id="presentImages" hidefocus="true"  />
													<label class="requireField">*</label>
													<div>上传新图片</div>
												<#else>
													<div>不允许上传</div>
												</#if>
											</a>
										</div>
									</li>  
								</ul>
							</div>
							<a class="next browse" href="javascript:void(0);" hidefocus="true"></a>
							<div class="blank"></div>
							<#if systemConfig.allowedUploadImageExtension != "">
								<span class="warnInfo"><span class="icon">&nbsp;</span><#if (systemConfig.uploadLimit) != 0 && (systemConfig.uploadLimit < 1024)>小于${systemConfig.uploadLimit}KB<#elseif (systemConfig.uploadLimit >= 1024)>小于${systemConfig.uploadLimit / 1024}MB</#if> (<#list systemConfig.allowedUploadImageExtension?split(stack.findValue("@cn.com.sinosoft.bean.SystemConfig@EXTENSION_SEPARATOR")) as list><#if list_has_next>*.${list};<#else>*.${list}</#if></#list>)</span>
							<#else>
								<span class="warnInfo"><span class="icon">&nbsp;</span>系统设置不允许上传图片文件!</span>
							</#if>
						</div>
					</td>
				</tr>
				<!--
				<tr>
					<th>
						页面关键词:
					</th>
					<td>
						<input type="text" name="present.metaKeywords" class="formText" value="${(present.metaKeywords)!}" />
					</td>
				</tr>-->
				<tr>
					<th>
						页面描述:
					</th>
					<td>
						<textarea id="metaDescription" name="present.metaDescription" style="height:300px;width:900px;">${(present.metaDescription)!}</textarea>
						<script type="text/javascript">var ue = UE.getEditor("metaDescription");</script>
					</td>
				</tr>
				
			</table>
			
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" onclick="submitform()"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
				<input type="submit" style="display: none;" id="submitbutton"></input>
			</div>
			<input type="hidden" name="id" value="${id}" />
		</form>
	</div>
</body>
</html>
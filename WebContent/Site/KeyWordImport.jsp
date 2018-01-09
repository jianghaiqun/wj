<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
function importWords(){
	var file=trim($V("WordsFile"));
	var txt=format($V("KeyWords"));
	//如果选择了导入文件按上传文件导入
	if(file!=''&&/\S+\.xls$/.test(file) || file!=''&&/\S+\.txt$/.test(file)){
		$("form2").submit();
		return;
	}
	//如果没有导入文件,且填写了栏目,按填写栏目导入
	if(txt==''||txt==$("KeyWords").getAttribute("oldValue")){
		Dialog.alert("请选择上传的txt文件<br/>或填写关键词(更改示例内容)");
		return;
	}
	doImport();
}

function doImport(){
	var KeyWords = $V("KeyWords");
	var dc = new DataCollection();
	dc.add("KeyWords",KeyWords);
	Server.sendRequest("com.sinosoft.cms.site.Keyword.importWords",dc,function(response){
	Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.Parent.loadWordData();
				window.Parent.loadTreeData();
				Dialog.close();
			}
		});
	});
}

function afterUpload(FilePath){
	var dc = new DataCollection();
	dc.add("FilePath",FilePath);
	Server.sendRequest("com.sinosoft.cms.site.Keyword.importWords",dc,function(response){
	Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.Parent.loadWordData();
				window.Parent.loadTreeData();
				Dialog.close();
			}
		});
	});
}
</script>
<style type="text/css">
<!--
.style1 {color: #445566; font-weight:bold;}
-->
</style>
</head>
<body class="dialogBody">
<div style="display:none;"><iframe src="javascript:void(0);"
	name="targetFrame" width="0" height="0" frameborder="0"></iframe></div>
<form id="form2" enctype="multipart/form-data" target="targetFrame"	action="KeyWordImportSave.jsp" method="POST">
<table width="100%" align="center" cellpadding="1" cellspacing="0"	>
	<tr>
		<td width="11%" height="10" align="left">&nbsp;</td>
		<td width="89%" height="10" align="left">&nbsp;</td>
	</tr>
	<tr>
		<td width="11%" height="30" align="left">&nbsp;&nbsp;</td>
		<td width="80%" height="30" align="left">选择文件： <input
			name="WordsFile" type="file" class="input1" id="WordsFile" size="40">&nbsp;(支持txt、xls格式)
			<br/><br/>
		模板下载：<a href="<%=Config.getValue("ServerContext")%>/xlstemplate/KeyWord.xls">关键词导入模板.xls</a>
		</td>
	</tr>
</table>
</form>
<form id="form3">
<table width="100%" align="center" cellpadding="1" cellspacing="0"	>
	<tr>
		<td width="11%" height="10" align="left"></td>
		<td valign="top"><br>
		<p><span class="style1">要求：</span><br>
		<span style="margin-left:3.6em;">1.上传文件必须为文本文件；</span><br>
		<span style="margin-left:3.6em;">2.文件要求每行一个关键词条目；</span><br>
		<span style="margin-left:3.6em;">3.请使用英文标点，参数之间用英文<font
			color="#FF0000"> “,” </font>隔开。</span><br />
		<span style="margin-left:3.6em;">4.打开方式由数字 1,2,3代替，<font
			color="#FF6600">1--原窗口；2--新窗口；3--父窗口。</font></span><br />
		<span style="margin-left:3.6em;">5.内链类别为关键词汇库中分类名，多个分类用"<font
			color="#FF0000">/</font>"隔开；</span><br>
		<span style="margin-left:3.6em;">6.搜索值、优先级请输入合法数字</span><br>
		<span style="margin-left:3.6em;">7.是否收录由数字 01,02代替，<font
			color="#FF6600">01--是；02--否。</font></span><br />
		<span style="margin-left:3.6em;">8.所属分类由在代码管理中配置的数字代替</span><br />
		<span style="margin-left:3.6em;">9.关键字、打开方式、内链分类为必填项</span><br />
		<span style="margin-left:3.6em;">10.您可以选择上传.txt/.xls文件,或直接更改以下示例内容,然后点击"确定"提交。</span><br />
		<span style="margin-left:3.6em;">11.使用xls模板导入时，如果需要导入空值，请用<font
			color="#FF0000"> “#” </font>代替。</span>
		<p><span class="style1">示例：</span><br/>
		<textarea cols="60"
			style="height:150px; width:440px; vertical-align:top;" id="KeyWords"
			name="KeyWords">
关键字,http://,链接提示,打开方式,内链类别,搜索值,优先级,是否收录,所属分类
热点词,http://www.baidu.com,保险产品,2,保险公司,888,88,01,01
</textarea>
		</td>
	</tr>
</table>
</form>
<script type="text/javascript">

	init();
	 //设定textarea的初始值 
	function init(){
		var obj=document.getElementById("KeyWords");
		var str=obj.value;
		obj.setAttribute('oldValue',format(str));
	}
	
	function trim(str){
		str=str.replace(/^\s*/g,'');
		str=str.replace(/\s*$/g,'');
		return str;
	}
	//格式化,去掉左边空格,右边空格,空字符(换行符除外)
	function format(str){
		str=trim(str);
		str=str.replace(/[\t\r\f]/g,'');
		str=str.replace(/[ \t\r\f]*\n[ \t\r\f]*\n+/g,'\n');
		return str;
	}
</script>
</body>
</html>

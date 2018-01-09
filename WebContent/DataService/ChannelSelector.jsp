<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function onTypeChange(){
	loadTreeData();
}
function onCheck(ele){
	ele = $(ele);
	var checked = ele.checked;
	if(ele.value=="-1"){
	
	}
	var p = ele.getParent("P");
	var level = p.$A("level");
	var arr = $("tree1").$T("P");
	var flag = true;
	for(var i=0;i<arr.length;i++){
		var c = arr[i];
		var cid = c.$A("cid");
		alert(cid+"--"+checked);
		if(cid){
			if(cid!="-1"&&ele.value=="-1"){
				if(checked){
					$("Catalog_"+cid).disable();
				}else{
					$("Catalog_"+cid).enable();
				}
			}else{
				if(c!=p&&flag){
					continue;
				}
				if(c==p){
					flag = false;
					continue;
				}
				if(c.$A("level")>level){
					$("Catalog_"+cid).checked = checked;
				}else{
					break;
				}
			}
		}
	}
}

function loadTreeData(){
	Tree.setParam("tree1","Type",$NV("Type"));
	Tree.loadData("tree1",function(){
		if(window.RelaStr){
			var rela = ","+window.RelaStr+",";
			var arr = $N("Catalog");;
			for(var i=0;i<arr.length;i++){
				if(rela.indexOf(","+arr[i].value+",")>=0){
					arr[i].checked = true;
				}
			}
			if(rela.indexOf(",-1,")>=0){
				onCheck(arr[0]);
			}
		}
	});
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<form id="F1">
	  <z:tree id="tree1" style="height:320px;width:300px" method="com.sinosoft.platform.Channel.treeDataBind" level="2" lazy="true" >
		  	<p cid='${ID}' level="${Level}"><input type="checkbox" name="Catalog" id='Catalog_${ID}' value='${ID}' onClick="onCheck(this);"><label for="Catalog_${ID}"><span id="span_${ID}">${Name}</span></label></p>
	  </z:tree>	
</form>
</body>
</html>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script type="text/javascript" src="../../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
var num=1;
var index="0";
jQuery(document).ready(function(){
		//加载亮点列表
		var Detail1=jQuery("#Detail1").val();
		var Detail2=jQuery("#Detail2").val();
		if(Detail1!=''&&Detail1!=null){
			var array_Detail1=Detail1.split(",");
			var array_Detail2=Detail2.split(",");
			for(var i=0;i<array_Detail1.length;i++){
				if(i!=0){
					addtable();
				}
				jQuery("#Detail1_"+i).val(array_Detail1[i]);
				jQuery("#Detail2_"+i).val(array_Detail2[i]);
			}
		}
		if((jQuery("#ModuleType").val()=="01"||jQuery("#ModuleType").val()=="02")){
			jQuery("#hightwo_td").remove();
			jQuery("#hightwo_input").remove();
		}
});
function  addtable(){
	if((jQuery("#ModuleType").val()=="01"||jQuery("#ModuleType").val()=="02")&&parseInt(num)>3){
		Dialog.alert("该类型模板最多添加四个产品亮点！");
		return;
	}
	if((jQuery("#ModuleType").val()=="03"||jQuery("#ModuleType").val()=="04")&&parseInt(num)>4){
		Dialog.alert("该类型模板最多添加五个产品亮点！");
		return;
	}
	var tablestr="";
	if((jQuery("#ModuleType").val()=="01"||jQuery("#ModuleType").val()=="02")){
		tablestr="<table>"
			+"<tr>"
			    +"<td  height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\">亮&nbsp;&nbsp;&nbsp;点&nbsp;&nbsp;&nbsp;描&nbsp;&nbsp;&nbsp;述1：</td>"
			    +"<td ><input value=\"\" type=\"text\" id=\"Detail1_"+num+"\" name=\"Detail1_"+num+"\" ztype=\"String\"  class=\"inputText\" size=\"20\" verify=\"亮点描述1|NULL\"></td>"
			    +"<td ><input type=\"button\" id=\"\" value=\"删除\" onclick=\"deleterow(this,'"+num+"');\"></td>"
			    +"<td ></td>"
		    +"</tr>"
		+"</table>";
	}else{
		tablestr="<table>"
			+"<tr>"
			    +"<td  height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\">亮&nbsp;&nbsp;&nbsp;点&nbsp;&nbsp;&nbsp;描&nbsp;&nbsp;&nbsp;述1：</td>"
			    +"<td ><input value=\"\" type=\"text\" id=\"Detail1_"+num+"\" name=\"Detail1_"+num+"\" ztype=\"String\"  class=\"inputText\" size=\"20\" verify=\"亮点描述1|NULL\"></td>"
			    +"<td  height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\" >亮&nbsp;&nbsp;&nbsp;点&nbsp;&nbsp;&nbsp;描&nbsp;&nbsp;&nbsp;述2：</td>"
			    +"<td ><input value=\"\" type=\"text\" id=\"Detail2_"+num+"\" name=\"Detail2_"+num+"\" ztype=\"String\"  class=\"inputText\" size=\"20\" ></td>"
			    +"<td ><input type=\"button\" id=\"\" value=\"删除\" onclick=\"deleterow(this,'"+num+"');\"></td>"
			    +"<td ></td>"
		    +"</tr>"
		+"</table>";
	}
	jQuery("#add_table_div").append(tablestr);
	num++;
	jQuery("#DetailNum").val(num);
	index=index+","+(parseInt(num)-1);
	jQuery("#index").val(index);
}

function deleterow(t,index_now){
	t.parentNode.parentNode.parentNode.parentNode.parentNode.removeChild(t.parentNode.parentNode.parentNode.parentNode);
	num--;
	jQuery("#DetailNum").val(num);
	if(index.indexOf(index_now+"")!=-1){
		index=index.substring(0,index.indexOf(index_now+"")-1)+index.substring(index.indexOf(index_now+"")+1,index.length);
	}
	jQuery("#index").val(index);
}
</script>
</head>
<body class="dialogBody" >
<z:init method="com.sinosoft.cms.document.PromotionManage.initHighlightsDialog">
<form id="form4">
		<input type="hidden" value="${ProductId}" id="ProductId" name="ProductId">
		<input type="hidden" value="${Detail1}" id="Detail1" name="Detail1">
		<input type="hidden" value="${Detail2}" id="Detail2" name="Detail2">
		<input type="hidden" value="${DetailId}" id="DetailId" name="DetailId">
		<input type="hidden" value="${ModuleType}" id="ModuleType" name="ModuleType">
		<input type="hidden" value="1" id="DetailNum" name="DetailNum">
		<input type="hidden" value="0" id="index" name="index">
		<tr>
			<td valign="top">
	    			<fieldset>
					     <table id="yh_talbe">
						          <tr>
						          <td  height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">亮&nbsp;&nbsp;&nbsp;点&nbsp;&nbsp;&nbsp;描&nbsp;&nbsp;&nbsp;述1：</td>
						          <td ><input value="" type="text" id="Detail1_0" name="Detail1_0" ztype="String"  class="inputText"    maxlength=200  ></td>
						          <td id="hightwo_td" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" >亮&nbsp;&nbsp;&nbsp;点&nbsp;&nbsp;&nbsp;描&nbsp;&nbsp;&nbsp;述2：</td>
						          <td id="hightwo_input"><input value="" type="text" id="Detail2_0" name="Detail2_0" ztype="String"  class="inputText"    maxlength=200  ></td>
						          <td ><input type="button" id="add_button" value="增加" onclick="addtable();" ></td>
						          </tr>
					     </table>
					     <div id="add_table_div"></div>
	               </fieldset>
			</td>
	    </tr>
	    </table>
	</form>
</z:init>
</body>
</html>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
var num=1;
var index="0";
jQuery(document).ready(function(){
		//加载优势信息列表
		var OrderFlag=jQuery("#OrderFlag").val();
		var Info1=jQuery("#Info1").val();
		var Info2=jQuery("#Info2").val();
		if(OrderFlag!=''&&OrderFlag!=null){
			var array_ShowNo=OrderFlag.split(",");
			var array_Info1=Info1.split(",");
			var array_Info2=Info2.split(",");
			for(var i=0;i<array_ShowNo.length;i++){
				if(i!=0){
					addtable();
				}
				jQuery("#Info1_"+i).val(array_Info1[i]);
				jQuery("#Info2_"+i).val(array_Info2[i]);
			}
		}
});
function  addtable(){
	if (parseInt(num)>=8) {
		Dialog.alert("最多设置8条优势信息！");
		return;
	}
	var tablestr="";

		tablestr="<table><tr>"
			    +"<td  height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\" >产品1优势信息：</td>"
			    +"<td ><input value=\"\" type=\"text\" id=\"Info1_"+num+"\" name=\"Info1_"+num+"\" ztype=\"String\"  class=\"inputText\" size=\"30\" ></td>"
			    +"<td  height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\" >产品2优势信息：</td>"
			    +"<td ><input value=\"\" type=\"text\" id=\"Info2_"+num+"\" name=\"Info2_"+num+"\" ztype=\"String\"  class=\"inputText\" size=\"30\" ></td>"
			    +"<td ><input type=\"button\" id=\"\" value=\"删除\" onclick=\"deleterow(this,'"+num+"');\"></td>"
			    +"<td ></td>"
		    +"</tr>"
		+"</table>";
	
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
<z:init method="com.sinosoft.cms.document.ShoppingGuidePKManage.initPKAdvantageDialog">
<form id="form4">
		<input type="hidden" value="${articleid}" id="articleid" name="articleid">
		<input type="hidden" value="${OrderFlag}" id="OrderFlag" name="OrderFlag">
		<input type="hidden" value="${Info1}" id="Info1" name="Info1">
		<input type="hidden" value="${Info2}" id="Info2" name="Info2">
		<input type="hidden" value="1" id="DetailNum" name="DetailNum">
		<input type="hidden" value="0" id="index" name="index">
		<table>
		<tr>
			<td valign="top">
	    			<fieldset>
					     <table id="yh_talbe">
						          <tr>
						          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" >产品1优势信息：</td>
						          <td ><input value="" type="text" id="Info1_0" name="Info1_0" ztype="String"  class="inputText" size="30" maxlength=200 ></td>
						          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" >产品2优势信息：</td>
						          <td ><input value="" type="text" id="Info2_0" name="Info2_0" ztype="String"  class="inputText" size="30" maxlength=200 ></td>
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

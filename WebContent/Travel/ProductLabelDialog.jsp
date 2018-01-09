<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
var num=1;
var index="0";
jQuery(document).ready(function(){
		var Detail=jQuery("#Detail").val();
		if(Detail!=''&&Detail!=null){
			var array_Detail=Detail.split(",");
			for(var i=0;i<array_Detail.length;i++){
				if(i!=0){
					addtable();
				}
				jQuery("#Detail_"+i).val(array_Detail[i]);
			}
		}
});
function  addtable(){
	var tablestr="";
		tablestr="<table>"
			+"<tr>"
			    +"<td  height=\"35\" align=\"right\" bordercolor=\"#eeeeee\">标签:</td>"
			    +"<td ><input value=\"\" type=\"text\" id=\"Detail_"+num+"\" name=\"Detail_"+num+"\" ztype=\"String\"  class=\"inputText\" size=\"20\" ></td>"
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
<body class="dialogBody">
<z:init method="com.sinosoft.cms.travel.ProductManage.initDialog2">
<form id="form4">
	<input type="hidden" id="productId" value="<%=request.getParameter("productId")%>">
	<input type="hidden" value="${Detail}" id="Detail" name="Detail">
	<input type="hidden" value="1" id="DetailNum" name="DetailNum">
	<input type="hidden" value="0" id="index" name="index">
	<table>
		<tr>
			<td valign="top">
	    			<fieldset>
					     <table id="yh_talbe">
						          <tr>
						          <td  height="35" align="right" bordercolor="#eeeeee">标签:</td>
						          <td ><input value="" type="text" id="Detail_0" name="Detail_0" ztype="String"  class="inputText"  maxlength=200  ></td>
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
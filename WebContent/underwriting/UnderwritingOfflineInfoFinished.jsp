<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../ueditor/lang/zh-cn/zh-cn.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.wangjin.underwriting.UnderwritingOfflineInfo.initDialog1">
<form id="form2">
		<table width="750" height="570" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td valign="top">
				<table>
				 	<tr>
				 	 	<td align="right" height="35" weight="60px">产品名称：</td>
				 	 	<td weight="140px">${productName}</td>
				 	 	<td align="right" height="35">姓名：</td>
				 	 	<td>${name}</td>	
				 	 	<td align="right" height="35">手机号：</td>
				 	 	<td>${mobile}</td>
				 	 	<td align="right" height="35">邮箱：</td>
				 	 	<td>${email}
				 	 		<input type="hidden" id="infoID" name="infoID" value="${infoID}" />
				 	 	</td>
				 	 	
				 	</tr>
				 	<tr>
				 		<td align="right" height="35">核保结果：</td>
				 	 	<td ><z:select id="UnderWritingStatus" name="UnderWritingStatus" style="width:80px" onChange="changeStatus()">${UnderWritingStatus}</z:select></td>
						<td colspan="7">
							<div id="offlineCodeDiv" style="display: none">百年康惠宝线下核保编码<input type="text" readonly="true" value="${offlineCode}"></div>
						</td>
				 	</tr>
					 
					<tr>
				 		<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">邮件内容：</td>
				 		
				 		
				 		<td colspan="8" >
				 		<div id ="MetaDescription_No2" style="display: none" >

							<textarea id="MetaDescription_2" name="MetaDescription_2" style="height:300px;width:600px;">${MetaDescription_2}</textarea>
							<script type="text/javascript">var ue = UE.getEditor("MetaDescription_2",{  
				                //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个  
				                toolbars:[['source','forecolor','link']]});
							</script>
						
						</div>
						<div id ="MetaDescription_No1">
					   
							<textarea id="MetaDescription_1" name="MetaDescription_1" style="height:300px;width:600px;">${MetaDescription_1}</textarea>
							<script type="text/javascript">var ue = UE.getEditor("MetaDescription_1",{  
				                //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个  
				                toolbars:[['source','forecolor','link']]});
							</script>
						 
						<div>
						</td>
						
						
					</tr>
					
					
					<tr>
				 		<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">固定内容：</td>
						<td colspan="8" >
							<textarea id="remark" name="remark" style="height:60px;width:600px;">如果客户已被售前核保明确告知拒保，但仍通过不实告知的方式投保成功的，一经发现将做退保处理</textarea>
						</td>
					</tr>
				</table>	     
			</td>
	    </tr>
	    </table>
	<input type="hidden" id="productId" value="${productId}">

	</form>
</z:init>
</body>
<script type="text/javascript">
if ("2248" == $V("productId").substring(0,4)) {
	$("offlineCodeDiv").style.display = "block";
}
/**
 * 获得温馨提示
 */
function getMetaDescription(){
	var MetaDescription_1 = UE.getEditor('MetaDescription_1').getContent();
	var MetaDescription_2 = UE.getEditor('MetaDescription_2').getContent();
	var MetaDescription = "";
	if($V("UnderWritingStatus")==1){
		MetaDescription = MetaDescription_1;
	}else{
		MetaDescription = MetaDescription_2;
	}
	if (MetaDescription == '' || MetaDescription == null) {
		return '';
	}
	return MetaDescription;
}

function changeStatus() {
	if($V("UnderWritingStatus") == "1"){
		$("MetaDescription_No2").style.display = "none";
		$("MetaDescription_No1").style.display = "block";
		if ("2248" == $V("productId").substring(0,4)) {
			$("offlineCodeDiv").style.display = "block";
		}
	}else{
		$("MetaDescription_No1").style.display = "none";
		$("MetaDescription_No2").style.display = "block";
		if ("2248" == $V("productId").substring(0,4))  {
			$("offlineCodeDiv").style.display = "none";
		}
	}
}
	
 


</script>
</html>
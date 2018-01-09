<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="./JavaScript/resultSQL.js"></script>
<script src="../Framework/Main.js"></script>
<script language="JavaScript">

	function initForm() {
	  fm.RuleTemplateName.value="";
	  fm.RuleTemplateDes.value="";
	}
	
	function verifyRuleTemplateName(){
		
		var tSql = "select id from lrruletemplate where ruletemplatename = trim('"+$V("RuleTemplateName")+"')";
    	var strQueryResult  = easyQueryVer3(tSql);	
    	var arrResult = decodeEasyQueryResult(strQueryResult);
		if (arrResult!=null) 
		{
			Dialog.alert("模板名称已存在，请使用其他模板名!");
		    return false;
		}
		return true;
	}
	
	function formSubmit(){
		if (Verify.hasError()) {
			return;
		}
		var url="./RuleTemplateMake.jsp?"+
    	"flag="+$V("flag")+
    	"&RuleTemplateName="+encodeURI(encodeURI($V("RuleTemplateName")))+
    	"&Creator="+$V("Creator")+
    	"&Operate="+$V("Operate")+
    	"&RuleTemplateDes="+encodeURI(encodeURI($V("RuleTemplateDes")));
    	window.location.href=url;
	}
</script>
<title>规则模板定制</title>
<LINK href="../Include/Default.css" rel=stylesheet type=text/css>
<LINK href="../common/css/tab.css" rel=stylesheet type=text/css>
</head>
<body onload="initForm();">

	<form method=post name=fm target="fraTitle">

		<Div id="divGroupPol1" style="display: ''">

			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable" style="table-layout: fixed;">
				<tr>
					<td><fieldset>
							<legend>
								<label>规则模板基本信息录入</label>
							</legend>
							<table width="100%">

								<TR>
									<TD width="17%">规则模板名</TD>
									<TD width="17%"><Input type="text" name=RuleTemplateName
										style="width: 170px"  verify="NotNull&&Length<21"></TD>
									<TD width="17%">功能描述</TD>
									<TD width="30%"><Input type="text" name=RuleTemplateDes
										style="width: 300px" verify="NotNull&&Length<500"></TD>
								</TR>
								
							</table>

						</fieldset></td>
				</tr>
				<tr>
					<td>
						<table border="0">
							<tr>
								<td><z:tbutton onClick="formSubmit();">
										<img src="../Icons/icon403a18.gif" />下一步</z:tbutton>
								</td>
								<td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
			</table>
		</Div>
		
		<input type="hidden" name=Creator value=<%=User.getUserName()%>><!-- 规则创建人即登陆用户 -->
		
		<input type="hidden" name=flag value='1'><!-- 规则模板定制 -->
		
		<input type="hidden" name="Operate" value='ADD'>
	</form>
</body>
</html>
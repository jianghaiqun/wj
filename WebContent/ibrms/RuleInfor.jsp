<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="./JavaScript/resultSQL.js"></script>
<script language="JavaScript">

	function initForm() {
	  
	}
	
	function getTemplateID(){
		
		if($V("LRRuleTemplateName")){
			var tSql = "select id from lrruletemplate where ruletemplatename = trim('"+$V("LRRuleTemplateName")+"')";
	    	var strQueryResult = easyQueryVer3(tSql);	
	    	var arrResult = decodeEasyQueryResult(strQueryResult);
			if (arrResult==null)
			{
				alert("未查找到该模板,请刷新页面重新操作!");
			    return false;
			}
	    	fm.LRTemplate_ID.value = arrResult[0][0];
	    	fm.LRTemplateName.value="lrruletemplate";
		}
		return true;
	}
	
	function formSubmit(){
		if(!verifyRuleTemplateName()){
			return;
		}
		
		if(!getTemplateID()){
			return;
		}
		if (Verify.hasError()) {
			return;
		}
		var url="./RuleMake.jsp?"+
    	"flag="+$V("flag")+
    	"&RuleName="+ encodeURI(encodeURI($V("RuleName"))) +
    	
    	"&Creator="+$V("Creator")+
    	"&RuleStartDate="+$V("RuleStartDate")+
    	"&RuleEndDate="+$V("RuleEndDate")+
    	"&TempalteLevel="+$V("TemplateLevel")+
    	"&Business="+$V("Business")+
    	"&State="+$V("State")+
    	"&Valid="+$V("Valid")+
    	"&LRTemplate_ID="+$V("LRTemplate_ID")+
    	"&LRTemplateName="+$V("LRTemplateName")+
    	"&RuleDes="+encodeURI(encodeURI($V("RuleDes")))+
		"&MarketingNum="+$V("MarketingNum");
    	//window.location.href=url;
		window.location.href= url ;
	}
	
	function verifyRuleTemplateName(){
		
		var tSql = "select 1 from lrtemplatet where rulename = trim('"+$V("RuleName")+"')";
    	var strQueryResult = easyQueryVer3(tSql);
    	var arrResult = decodeEasyQueryResult(strQueryResult);
		if (arrResult!=null) 
		{
			Dialog.alert("模板名称已存在，请使用其他模板名!");
		    return false;
		}
		return true;
	}
	
	function afterSubmit(Content){
		alert(Content);
	}
	
	
</script>
<title>规则定制</title>
<LINK href="../Include/Default.css" rel=stylesheet type=text/css>
<LINK href="../common/css/tab.css" rel=stylesheet type=text/css>
</head>
<body onload="initForm();">
	<input type="hidden" id="strResult" />
	<form action="RuleMake.jsp" method=post name=fm target="fraTitle">


		<Div id="divGroupPol1" style="display: ''">

			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable" style="table-layout: fixed;">
				<tr>
					<td><fieldset>
							<legend>
								<label>规则基本信息录入</label>
							</legend>
							<table width="100%">

								<TR>
									<TD width="17%">规则名</TD>
									<TD width="17%"><Input type="text" id=RuleName name=RuleName
										style="width: 170px"  verify="NotNull&&Length<21"></TD>
									<TD width="17%">生效日期</TD>
									<td><input id="RuleStartDate" name="RuleStartDate" type="text" class="input1" id="RuleStartDate" verify="NotNull"
											ztype="Date" /></td>
									<TD width="17%">失效日期</TD>
									<td><input id="RuleEndDate" name="RuleEndDate" type="text" class="input1" id="RuleEndDate"
											ztype="Date" /></td>
								</TR>
								<TR>
									<TD width="17%">业务模块</TD>
									<TD width="17%"><z:select id="Business" name="Business"
											listWidth="170" input="true" style="width:170px"
											code="ibrmsbusiness" lazy="true" showValue="true" verify="NotNull"></z:select></TD>
									<TD width="17%">规则模板名</TD>
									<TD width="17%"><z:select id="LRRuleTemplateName" name="LRRuleTemplateName"
											listWidth="170" input="true" style="width:170px"
											code="#com.sinosoft.ibrms.RuleCodeSource.getRuleTemplateID" lazy="true" showValue="true"></z:select></TD>
									<TD width="17%">营销活动编码</TD>
									<TD width="17%"><z:select id="MarketingNum" name="MarketingNum"
											listWidth="170" input="true" style="width:170px"
											code="MarketingNum" lazy="true" showValue="true">
											</z:select></TD>
								</TR>
								<TR>
									<TD width="17%">功能描述</TD>
									<TD width="25%"><Input type="text" id=RuleDes name=RuleDes
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
		<input type="hidden" id=TemplateLevel name=TemplateLevel value="6" ><!-- 级别：6.保单级规则 -->
		<input type="hidden" id=Creator name=Creator value=<%=User.getUserName()%>><!-- 规则创建人即登陆用户 -->
		<input type="hidden" id=Valid name=Valid value="1"><!-- 是否有效：1.有效 -->
		<input type="hidden" id=State name=State value="1"><!-- 当前状态：初始  -->
		<input type="hidden" id=flag name=flag value="1">
		<!--//flag对应的页面： 1：规则定制 2 ：规则复制 3 规则测试 4：规则修改 5：规则审批 6：规则发布 7：规则作废 8：规则查询 9：规则打印  -->
		<input type="hidden" id=LRTemplate_ID name=LRTemplate_ID value=""><!-- 规则模板  -->
		<input type="hidden" id=LRTemplateName name=LRTemplateName value=""><!-- 规则模板名称  -->
		<input type="hidden" id="LRTemplateT_Id" name="LRTemplateT_Id">
	</form>
</body>
</html>
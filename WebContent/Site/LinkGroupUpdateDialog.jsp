<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.site.LinkGroup.initDialog">
<form id="form3">
<table width="570" height="227" align="center" cellpadding="2" cellspacing="0">
    <tr>
      <td height="10"></td>
      <td></td>
    </tr>
    <tr>
      <td width="311" >
   <fieldset>
    <legend><label>基本信息</label></legend>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="39%" height="30" align="right" >链接分组名称：</td>
          <td width="61%"><input name="LinkName"  type="text" id="LinkName" value="<%=request.getParameter("LinkName")%>" /></td>
        </tr>
        <!-- <tr>
          <td height="30" align="right" >真实姓名：</td>
          <td><input name="RealName"  type="text" value="" id="RealName"  /></td>
        </tr>
        <tr>
          <td height="30" align="right" >用户标记：</td>
          <td><input name="UserSign"  type="text" value="" id="UserSign"  /></td>
        </tr>
        <tr id ="tr_Password2" style="display:none">
          <td height="30" align="right">密码：</td>
          <td><input name="Password" type="password" value="password" id="Password" verify="密码为4-20位|Regex=\S{4,20}&&NotNull" /></td>
        </tr>
        <tr id ="tr_ConfirmPassword2" style="display:none">
          <td height="30" align="right" >重复密码：</td>
          <td><input name="ConfirmPassword" type="password" value="password" id="ConfirmPassword" verify="重复密码为4-20位|Regex=\S{4,20}&&NotNull" /></td>
        </tr>
        <tr>
          <td height="30" align="right" >所属机构：</td>
          <td ><z:select id="BranchInnerCode"> ${BranchInnerCode} </z:select></td>
        </tr>
        <tr id ="tr_IsBranchAdmin" style="display:none">
          <td height="30" align="right" >机构管理员：</td>
          <td >${IsBranchAdmin}</td>
        </tr>
        <tr>
          <td height="30" align="right" >是否停用：</td>
          <td >${Status}</td>
        </tr>
        <tr style="display:none">
          <td height="30" align="right" >电子邮件：</td>
          <td><input name="Email"  type="text"  id="Email" value="" verify="电子邮件|Email"/></td>
        </tr>
        <tr style="display:none">
          <td height="30" align="right" >联系电话：</td>
          <td><input name="Tel"  type="text" value=""  id="Tel" verify="联系电话|Number" size="12" />
            <br>01012345678(示例)</td>
        </tr>
        <tr style="display:none">
          <td height="30" align="right" >手机号码：</td>
          <td><input name="Mobile"  type="text" value=""  id="Mobile" verify="手机号码|Number&&Length=11" size="12"/>
            <br>13912345678(示例)</td>
        </tr>
        <tr style="display:none">
          <td height="30" align="right" >备注：</td>
          <td><input name="Memo" type="text"  id="Memo" value=""/></td>
        </tr>-->
      </table></fieldset></td>
      
      <td  width="270"  valign="top">
	  <fieldset>
		<legend><label>所属目录</label></legend>
		  <table width="100%" border="0" cellpadding="2" cellspacing="0">
			<tr>
			<td>
	 <!--<z:tree id="tree1" style="width:230px;height:300px" method="com.sinosoft.platform.UserList.initRoleTree">
	      <p cid='${RoleCode}' >
	        <input type="radio" name="RoleCode" value='${RoleCode}' ${checked}>
	        ${RoleCode}(${RoleName})</p>
	      </z:tree>
	       -->
	       
	   <z:tree id="tree1" style="width:230px;height:300px" method="com.sinosoft.cms.site.LinkGroup.treeDataBind" >
		<p cid='${ID}'><input type="radio" name='MENU'	value='${ID}' ${checked}>${Name}</input></p>
					</z:tree>
		</td>
		 </tr>
      </table></fieldset>
	  </td>
    </tr>
	</table>
</form>
</z:init>
</body>
</html>
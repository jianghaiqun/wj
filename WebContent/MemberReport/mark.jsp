<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>标记人</title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css" />
    <script src="../Framework/Main.js"></script>
 
</head>
<body>
	<form id="form2">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
       style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
        <td>
            <z:init>
            <table width="100%" border="0" cellspacing="0" cellpadding="6"
                   class="blockTable">
                <tr>
                    <td>
                    </td>
                </tr>
                <tr>
                    <td style="padding-top:0px;padding-left:6px;  padding-right:6px;padding-bottom:8px;">

                        <z:datagrid id="dg1" method="com.sinosoft.cms.memberreport.Mark.dg1DataBind"
                                    size="20" lazy="false" autoFill="true"  multiSelect="false">
                            <table width="100%" style="" cellpadding="2" cellspacing="0" class="dataTable" >
                                <tr ztype="head" class="dataTableHead">
                                
		  	     				    <td width="4%" ztype="RowNo">序号</td>
							        <td width="4%" ztype="selector" field="UserName">&nbsp;</td>
						            <td width="12%" ><b>用户名</b></td>
									<td width="10%" ><b>真实姓名</b></td>
									<td width="8%" ><b>用户状态</b></td>
			 
		                            <td width="42%" ><b>所属角色</b></td>            
                                </tr>
                                 
                                <tr  style="background-color:#F9FBFC">
							          <td >&nbsp;</td>
								      <td>&nbsp;</td>
								 
									  <td title="${UserName}">${UserName}</td>
									  <td title="${RealName}">${RealName}</td>
									  <td title="正常">正常</td>
	 
			                          <td title="${RoleName}">${RoleName}</td>
                                </tr>
                                 <tr ztype="pagebar">
                                    <td height="25" colspan="11">${PageBar}</td>
                                </tr>
                            </table>
                        </z:datagrid></td>

                </tr>
            </table>
            </z:init>
        </td>
    </tr>
</table>
</form>
</body>
</html>
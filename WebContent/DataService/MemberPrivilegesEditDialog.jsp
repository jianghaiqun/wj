<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.sinosoft.cms.dataservice.Member.editPrivilegesInit">
   <form id="EditForm">
   	   <input type="hidden" id="id" name="id" value="${id}">
       <table width="500" align="center" cellpadding="2" cellspacing="0">
           <tr>
               <td width="117" height="10"></td>
               <td width="373"></td>
           </tr>
           <tr>
               <td align="right">特权名称：</td>
               <td height="30">
                  <input id="PrivilegesName" name="PrivilegesName" type="text" value="${PrivilegesName}" class="input1" verify="特权名称|NotNull" size="36" />
               </td>
           </tr>
           <tr>
               <td align="right">会员级别：</td>
               <td height="30">
                   <input type="checkbox" name="MemberLevel" value="K0" ${MemberLevelK0}>普通会员
                   <input type="checkbox" name="MemberLevel" value="K1" ${MemberLevelK1}>K1会员
                   <input type="checkbox" name="MemberLevel" value="K2" ${MemberLevelK2}>K2会员
                   <input type="checkbox" name="MemberLevel" value="VIP" ${MemberLevelVIP}>VIP会员
               </td>
           </tr>
           <!-- <tr>
               <td align="right">展示图片链接：</td>
               <td height="30">
                   <input id="PgLink" name="PgLink" type="text" value="${PgLink}" class="input1" size="50" verify="特权名称|NotNull"/>
               </td>
           </tr> -->
           <tr>
               <td align="right">启用标志：</td>
               <td height="30">
               	<z:select style="width:120px;">
               		<select name="useFlag" id="useFlag" value="${useFlag}"> 
	                	<option value="Y" >是</option>
	                	<option value="N">否 </option>
               		</select>
               	</z:select>
               </td>
           </tr>
           <tr>
               <td align="right">展示顺序：</td>
               <td height="30">
                   <input id="orderFlag" name="orderFlag" type="text" value="${orderFlag}" class="input1" size="20" verify="展示顺序|NotNull&&Int"/>
               </td>
           </tr>
           <tr>
               <td align="right">展示内容：</td>
               <td align="left">
                   <textarea name="content" id="content" style="width:300px;height:50px" verify="展示内容|NotNull" >${content}</textarea>
               </td>
           </tr>
           <tr>
               <td align="right">描述：</td>
               <td align="left">
                   <textarea name="description" id="description" style="width:300px;height:100px" verify="描述|NotNull" >${description}</textarea>
               </td>
           </tr>
       </table>
   </form>
</z:init>
</body>
</html>
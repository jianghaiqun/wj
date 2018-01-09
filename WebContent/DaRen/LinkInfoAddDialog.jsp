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
    <script type="text/javascript">
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>

<body class="dialogBody">
   <form id="AddForm">
       <table width="500" align="center" cellpadding="2" cellspacing="0">
           <tr>
               <td width="117" height="10"></td>
               <td width="160"></td>
               <td height="30"></td>
           </tr>
           <tr>
           <z:init method="com.wangjin.daren.AuthorDetailInfo.init">
               <td align="right">来源：</td>
               <td height="30">
               <z:select style="width:80px;" name="Platform" id="Platform">${source}</z:select>
               </td>
               <td></td>
           </z:init>
           </tr>
           <tr>
               <td align="right">帖子链接：</td>
               <td height="30" colspan="2">
                  <input id="Link" name="Link" type="text" value="" class="input1" verify="帖子链接|NotNull" style="width:280px"/>
               </td height="30">
           </tr>
           <tr id="pointInfo">
               <td align="right">作者：</td>
               <td height="30">
                  <input id="Author" name="Author" type="text" value="" class="input1" />
               </td height="30">
               <td align="left">
               </td>
           </tr>
           <tr id="PolicyNoTR">
               <td align="right">联系人：</td>
               <td height="30">
                   <input id="linkman" name="linkman" type="text" value="" class="input1"/>
               </td>
               <td>
               </td>
           </tr>
           <tr>
               <td align="right">备注：</td>
               <td align="left">
                   <input id="Remark" name="Remark" type="text" value="" class="input1"/>
               </td>
           </tr>
       </table>
   </form>
</body>
</html>

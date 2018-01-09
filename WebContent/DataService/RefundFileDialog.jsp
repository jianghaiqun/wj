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
               <td width="360"></td>
           </tr>
           <tr>
				<td height="30" align="right" class="tdgrey1">起始时间：</td>
				<td class="tdgrey1"><span class="tdgrey2"> <input
					name="StartDate" type="text" class="inputText" id="StartDate"
					ztype="Date" size=14 verify="NotNull" /> </span><span class="tdgrey2">
				<input name="StartTime" type="text" class="inputText"
					id="StartTime" ztype="Time" size=14 verify="NotNull" /> </span></td>
			</tr>
           <tr>
				<td height="30" align="right" class="tdgrey1">截止时间：</td>
				<td class="tdgrey1"><span class="tdgrey2"> <input
					name="EndDate" type="text" class="inputText" id="EndDate"
					ztype="Date" size=14 verify="NotNull" /> </span><span class="tdgrey2">
				<input name="EndTime" type="text" class="inputText"
					id="EndTime" ztype="Time" size=14 verify="NotNull" /> </span></td>
			</tr>
           <!-- <tr>
               <td align="right">文件名称：</td>
               <td align="left" colspan="2">
                   <input name="fileName" type="text" class="inputText" id="fileName" />
               </td>
           </tr> -->
           <tr>
           		<td align="right">说明：</td>
           		<td colspan="2">
           			<span style="color: red;">
           				起始截止时间为撤单时间或者手动发起退款发起时间。
           			</span>
           		</td>
           </tr>
       </table>
   </form>
</body>
</html>

<%@page import="com.sinosoft.platform.Application"%>
<%@page import="com.sinosoft.framework.User"%>
<%@page import="com.sinosoft.framework.data.QueryBuilder"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.cms.pub.SiteUtil"%>
<%
//判断是否登录
if(!User.isLogin()){
	StringBuffer sb = new StringBuffer();
 	sb.append("document.write(\"<form id='formLoginVerify' name='formLoginVerify'>\");\n");
 	
	sb.append("document.write(\"<table width='100%' height='127' border='0' cellpadding='0' cellspacing='0' style='border:#CCCCCC 1PX solid'>\");\n");
 	sb.append("document.write(\"<tr>\");\n");
	sb.append("document.write(\"<td class='Left_js_title'>&nbsp;&nbsp;<span class='STYLE1'>用户登录</span></td>\");\n");
	sb.append("document.write(\"</tr>\");\n");
	sb.append("document.write(\"<tr>\");\n");
	sb.append("document.write(\"<td height='96' valign='top'>\");\n");
	sb.append("document.write(\"<table width='100%' height='96' border='0' cellpadding='0' cellspacing='0'>\");\n");
	sb.append("document.write(\"<tr>\");\n");
	sb.append("document.write(\"<td width='29%' height='33' align='right'>用户名:</td>\");\n");
	sb.append("document.write(\"<td width='71%'><input type='text' name='name' id='name' size='10' /></td>\");\n");
	sb.append("document.write(\"</tr>\");\n");
	sb.append("document.write(\"<tr>\");\n");
	sb.append("document.write(\"<td height='27' align='right'>密 &nbsp;&nbsp;码 :</td>\");\n");
	sb.append("document.write(\"<td><input type='password' name='code' id='code' size='10' /></td>\");\n");
	sb.append("document.write(\"</tr>\");\n");
	sb.append("document.write(\"<tr>\");\n");
	sb.append("document.write(\"<td colspan='2' align='center'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\");\n");
	sb.append("document.write(\"<input type='image' src='images/button.gif' onClick='doSysLogin()'/></td>\");\n");
	sb.append("document.write(\"</tr>\");\n");
	sb.append("document.write(\"</table></td></tr></table>\");\n");
	
	sb.append("document.write(\"</form>\");\n");
	out.println(sb.toString());
} else {
	StringBuffer sb = new StringBuffer();
	sb.append("document.write(\"<table width='100%' height='127' border='0' cellpadding='0' cellspacing='0' style='border:#CCCCCC 1PX solid'>\");\n");
 	sb.append("document.write(\"<tr>\");\n");
	sb.append("document.write(\"<td class='Left_js_title'>&nbsp;&nbsp;<span class='STYLE3'><span class='STYLE4'>当前登录：</span>" + User.getRealName() + "</span></td>\");\n");
	sb.append("document.write(\"</tr>\");\n");
	sb.append("document.write(\"<tr>\");\n");
	sb.append("document.write(\"<td height='96' valign='top'>\");\n");
	sb.append("document.write(\"<table width='100%' height='96' border='0' cellpadding='0' cellspacing='0'>\");\n");
	sb.append("document.write(\"<tr>\");\n");
	sb.append("document.write(\"<td colspan='2' align='left' valign='middle' background='images/bgbg_01.jpg'>\");\n");
	sb.append("document.write(\"<table width='162' height='51' border='0' align='center' cellpadding='0' cellspacing='0'>\");\n");
	sb.append("document.write(\"<tr>\");\n");
	sb.append("document.write(\"<td align='center'><span class='STYLE1'>" + User.getRealName() + " 欢迎您会员登录</span></td>\");\n");
	sb.append("document.write(\"</tr>\");\n");
	sb.append("document.write(\"<tr>\");\n");
	sb.append("document.write(\"<td height='18'>&nbsp;&nbsp;&nbsp;<span class='STYLE1'>&nbsp;成功！</span></td>\");\n");
	sb.append("document.write(\"</tr></table></td></tr></table></td></tr></table>\");\n");
	out.println(sb.toString());
}
%>

	
	

          
        
          
            
            
          
          
            
            
          
          
            
              
            
        
      
    


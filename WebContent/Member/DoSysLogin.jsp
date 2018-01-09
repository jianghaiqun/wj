
<%@page import="com.sinosoft.schema.ZDUserSchema"%><%@page import="java.util.Date"%>
<%@page import="com.sinosoft.framework.data.QueryBuilder"%>
<%@page import="com.sinosoft.framework.extend.ExtendManager"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.member.Member"%>
<%@page import="com.sinosoft.framework.User"%>
<%
	String flag = "Y";

	String userName = request.getParameter("UserName");
	String passWord = request.getParameter("PassWord");
	
	ZDUserSchema user = new ZDUserSchema();
	user.setUserName(userName);
	if (user.fill()) {
		if (user.getPassword().equals(StringUtil.md5Hex(passWord))) {
			String VerifyCookie = StringUtil.md5Hex(user.getPassword().substring(0, 10) + (System.currentTimeMillis() + ""));
			User.setManager(true);
			User.setLogin(true);
			User.setUserName(userName);
			User.setRealName(user.getRealName());
			User.setType(user.getType());
			User.setMember(false);
			User.setValue("UserName", user.getUserName());
			User.setBranchInnerCode(user.getBranchInnerCode());//
			User.setValue("Type", user.getType());
			//ExtendManager.executeAll("Member.Login", new Object[] { member });// 调用扩展
			if (StringUtil.isNotEmpty(user.getRealName())) {
				User.setValue("Name", user.getRealName());
			} else {
				User.setValue("Name", user.getUserName());
			}
			
			StringBuffer sb = new StringBuffer();
			sb.append("<div id='loginMenu2'>");
			sb.append("<b>欢迎：" + User.getRealName() + "</b>&nbsp;");
			sb.append("[<a href='javascript:void(0);' onClick='goSystem();'>系统后台</a>]&nbsp;");
			sb.append("[<a href='javascript:void(0);' onClick='doSysLogout();'>退出</a>]</form>");
			sb.append("</div>");
			response.getWriter().write(flag + "&" + sb.toString());
		} else {
			flag = "N";
			response.getWriter().write(flag + "&会员名或密码错误，请重新输入");
		}
	} else {
		flag = "N";
		response.getWriter().write(flag + "&会员名不存在，请重新输入");
	}
%>
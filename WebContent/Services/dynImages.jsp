
<%@page import="cn.com.sinosoft.action.shop.AjaxImage"%>
<%@page import="com.sinosoft.framework.utility.LogUtil"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@ page import="org.slf4j.Logger" %>
<%@ page import="org.slf4j.LoggerFactory" %>
<%
Logger logger = LoggerFactory.getLogger(this.getClass());
String dynName = request.getParameter("dynName");
String dynWidth = request.getParameter("dynWidth");
String dynHeight = request.getParameter("dynHeight");
String dynCount = request.getParameter("dynCount");
String dCharwidth = request.getParameter("dCharwidth");

String outImage = AjaxImage.getImagePlayer(dynName,dynWidth,dynHeight,dynCount,dCharwidth);
if(!StringUtil.isEmpty(outImage)){
String dType = outImage.substring(0,2);
outImage = outImage.substring(2,outImage.length());
if("03".equals(dType)){
	//Flash
	out.println("document.write('"+outImage+"');");
}else if("04".equals(dType)){
	//Div
	out.println(outImage);
}else{
	//default Flash
	out.println("document.write('"+outImage+"');");
}
}else{
	logger.error("动态广告位,异常!没有数据!");
}


%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.net.URLEncoder"%>
<%
	// 乱码处理 
	String filepath = request.getParameter("filepath");
	String filename = request.getParameter("filename");
	//判断是否是IE11 IE8
	Boolean flag8 = request.getHeader("User-Agent").toUpperCase()
			.indexOf("MSIE") > 0;
	Boolean flag11 = request.getHeader("User-Agent").toUpperCase()
			.indexOf("LIKE GECKO") > 0;
	if (flag8 || flag11) {
		filename = URLEncoder.encode(filename, "UTF-8");//IE浏览器
	} else {
		filename = new String(filename.replaceAll(" ", "").getBytes(
				"UTF-8"), "ISO8859-1");
	}

	response.setContentType("application/x-download");
	response.setHeader("Location", "电子保单");
	response.setHeader("Content-Disposition", "attachment; filename="
			+ filename);
	OutputStream outputStream = null;
	InputStream inputStream = null;
	try {
		outputStream = response.getOutputStream();
		inputStream = new FileInputStream(filepath);
		byte[] buffer = new byte[1024];
		int i = -1;
		while ((i = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, i);
		}
		outputStream.flush();
		outputStream.close();
		inputStream.close();
		out.clear();
		out = pageContext.pushBody();
		outputStream = null;
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (inputStream != null) {
			inputStream.close();
			inputStream = null;
		}
		if (outputStream != null) {
			outputStream.close();
			outputStream = null;
		}
	}
%>
<script language="javascript">
	document.close();
</script>
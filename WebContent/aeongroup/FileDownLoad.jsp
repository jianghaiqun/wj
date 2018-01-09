<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.net.URLEncoder"%>
<%
	String filepath = request.getParameter("filepath");
	String filename = request.getParameter("filename");
	response.setContentType("application/x-download");
	response.setHeader("Location", "电子保单");
	response.setHeader("Content-Disposition", "attachment; filename="
			+ URLEncoder.encode(filename, "UTF-8"));
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
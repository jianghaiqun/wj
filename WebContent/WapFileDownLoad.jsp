<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.framework.data.DataTable"%>
<%@page import="com.sinosoft.framework.data.QueryBuilder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.inter.MailAction"%>

<%
	String orderSn = request.getParameter("orderSn");
	if(StringUtil.isEmpty(orderSn)){
		return;
	}
	//wptest0000001635
	///usr/local/dzbd/EPolicy/2007/2015/02/54a951fcf3074cc059aced2ea799f06a.pdf
    QueryBuilder qb = new QueryBuilder(" SELECT a.electronicpath FROM sdinformationrisktype a WHERE a.orderSn=? ");
    qb.add(orderSn);
    DataTable dt = qb.executeDataTable();
    int tempSumCount = dt.getRowCount();
    if(dt==null || tempSumCount<=0){
    	return;
    }
    String filepath = dt.getString(0, "electronicpath");
    if(StringUtil.isEmpty(filepath)){
    	return;
    }
    File filePolicy = null;
    List<File> listFile = new ArrayList<File>();
	for(int k = 0; k < tempSumCount; k++){
		String electronicPath = dt.getString(k,"electronicpath");
		if(StringUtil.isNotEmpty(electronicPath)){
			filePolicy = new File(electronicPath);
			listFile.add(filePolicy);
		}
	}
	String arr[] = filepath.split("/");
	String filename = arr[arr.length -1];
	String zipFileName = orderSn+".rar";
	String zipFilePath = filepath.replaceAll(filename, zipFileName);
	
	File zipfile=new File(zipFilePath);
	MailAction.zipFiles(listFile, zipfile);
   
	response.setContentType("application/x-download");
	response.setHeader("Location", "电子保单");
	response.setHeader("Content-Disposition", "attachment; filename="
			+ URLEncoder.encode(zipFileName, "UTF-8"));
	OutputStream outputStream = null;
	InputStream inputStream = null;
	try {
		outputStream = response.getOutputStream();
		inputStream = new FileInputStream(zipFilePath);
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
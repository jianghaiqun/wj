<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%
	String FlagStr = "";
	String Content = "";
	String FileName = "";
	
	try {
		String FilePath = request.getParameter("FilePath");
		System.out.println(FilePath);
		if (FilePath != null && !"".equals(FilePath)) {
			File fileload = new File(FilePath);
			if (!fileload.exists()) {
				Content = FilePath + " does not exists!";
				FlagStr = "Fail";
			} else {
				FileName = FilePath.substring(FilePath.lastIndexOf("/") + 1);
				InputStream ins = new FileInputStream(fileload);
				BufferedInputStream bin = new BufferedInputStream(ins);
				response.reset();
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition",
						"attachment; filename=" + new String(FileName.getBytes("gb2312"),"iso8859-1"));
				OutputStream os = response.getOutputStream();
				BufferedOutputStream bos = new BufferedOutputStream(os);

				byte[] b = new byte[4096];
				int intLength;

				while ((intLength = bin.read(b)) > 0) {
					bos.write(b, 0, intLength);
				}
				bos.flush();
				bos.close();
				bin.close();
				ins.close();
				out.clear();
	            out = pageContext.pushBody();
			}
		}
		
	} catch (java.io.FileNotFoundException notFoundEx) {
		Content = "File does not exists!";
		FlagStr = "Fail";
	}
		
%>
<html>
	<script language="javascript">
	alert("2<%=Content%>");
</script>
</html>


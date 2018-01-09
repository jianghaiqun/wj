<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="org.apache.commons.fileupload.disk.*"%>
<%@page import="org.apache.commons.fileupload.disk.*"%>
<%@page import="com.wangjin.infoseeker.DialogInfo"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<script src="../Framework/Main.js"></script>
<%@ taglib uri="controls" prefix="z"%>
<%
String uploadPath = "",ext="";
FileItemFactory fileFactory = new DiskFileItemFactory();
ServletFileUpload fu = new ServletFileUpload(fileFactory);
List fileItems = fu.parseRequest(request);

String newFileName = "";
boolean isSucess = false;
String fileName = "";

fu.setHeaderEncoding("UTF-8");
Iterator iter = fileItems.iterator();
while (iter.hasNext()){
	FileItem item = (FileItem) iter.next();
	if (!item.isFormField()){
		String oldFileName = item.getName();
		long size = item.getSize();
		if((oldFileName==null||oldFileName.equals("")) && size==0){
			continue;
		}
		oldFileName = oldFileName.substring(oldFileName.lastIndexOf("\\")+1);
		ext = oldFileName.substring(oldFileName.lastIndexOf("."));
		String extname=ext.substring(ext.indexOf(".")+1);

		if(!(ext.toLowerCase().equals(".zip"))){
				out.println("<script>Dialog.alert('导入失败，只能导入zip格式的文件!')"+";</script>");
				return;
		}
		
		String realDir = request.getRealPath("/");
		//本地测试用
		//String realDir = "D:";
		uploadPath = realDir+"/Upload/Temp/Live800/";
		
		File path = new File(uploadPath);
		if(!path.exists()){
		    path.mkdirs();
		}
		
		newFileName = oldFileName.substring(0,oldFileName.indexOf("."))+"_"+User.getUserName()+"_"+System.currentTimeMillis()+ext;
		uploadPath = uploadPath+newFileName;
		uploadPath = uploadPath.replace('\\', '/');
		
		//备份压缩包文件
		item.write(new File(uploadPath));
		
		//解析XML文件
		DialogInfo di = new DialogInfo();
		if(".zip".equalsIgnoreCase(ext)){
			
		    fileName = di.resolveZipFile(uploadPath);
		    
		    if("".equals(fileName)){
		    	isSucess = true;
		    }else{
		    	isSucess = false;
		    }
		}
		if(isSucess){
			out.println("<script>Dialog.alert('导入成功');</script>");
		}else{
			File f = new File(uploadPath);
			f.delete();
			out.println("<script>Dialog.alert('导入压缩包中存在错误文件，错误文件名："+fileName+"，请手动更改或删除该文件!');</script>");
		} 	
	}
}



%>

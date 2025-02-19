<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="org.apache.commons.fileupload.disk.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%

String siteID = request.getParameter("SiteID");
String siteCode = SiteUtil.getAlias(siteID);
String uploadPath = "",ext="";

FileItemFactory fileFactory = new DiskFileItemFactory();
ServletFileUpload fu = new ServletFileUpload(fileFactory);
List fileItems = fu.parseRequest(request);

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
		if(!PubFun.isAllowExt(extname)){
			  String referer = request.getHeader("Referer");
			  out.println("<script>alert('导入失败，不允许导入"+extname+"格式的文件!');window.location='"+referer+"';</script>");
			  return;
		}
		String realDir = Config.getContextRealPath()+Config.getValue("Statical.TemplateDir")+"/";
		uploadPath = realDir+siteCode+"/";
		if(".zip".equals(ext.toLowerCase())){
		    uploadPath = uploadPath+"temp/"+siteCode+"_"+User.getUserName()+"_"+System.currentTimeMillis()+"/";
	  }
		
		File path = new File(uploadPath);
		if(!path.exists()){
		    path.mkdirs();
		}
		
		uploadPath = uploadPath+oldFileName;
		try{
		  item.write(new File(uploadPath));
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
	}
}

Template t = new Template();
String uploadDir = uploadPath.substring(0,uploadPath.lastIndexOf("/"));
boolean isSucess = true;
if(".zip".equalsIgnoreCase(ext)){
    isSucess = t.unzipFile(uploadPath,uploadDir,siteCode);
}else{
    isSucess = t.processFile(uploadPath,siteCode);
}

if(isSucess){
	out.println("<script>alert('导入成功');</script>");
	out.println("<script>parent.Parent.location.reload();parent.closeDialog();</script>");
}else{
	out.println("<script>alert(\"上传失败:"+t.getResponse().getString(Constant.ResponseMessageAttrName)+"\");</script>");
	out.println("<script>window.location = 'ImportTemplateDialog.jsp?SiteID="+siteID+"';</script>");
}

%>

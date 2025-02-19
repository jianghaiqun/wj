<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="com.sinosoft.platform.pub.NoUtil"%>
<%@page import="com.sinosoft.framework.Config"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>

<%

String Path = request.getParameter("Path");
File dir = new File(Config.getContextRealPath()+Path);
if(!dir.exists()){
	dir.mkdirs();
}
FileItemFactory fileFactory = new DiskFileItemFactory();
ServletFileUpload fu = new ServletFileUpload(fileFactory);
List fileItems = fu.parseRequest(request);
String oldFileName = "";
String RealFileName = "";
String ext = "";
String AliasName = Config.getAppCode();
fu.setHeaderEncoding("UTF-8");
Iterator iter = fileItems.iterator();
FileItem uploadItem = null;
while (iter.hasNext()){
	FileItem item = (FileItem) iter.next();
	if (!item.isFormField()){
		oldFileName = item.getName();
		System.out.println("-----UploadFileName:-----" + oldFileName);
		long size = item.getSize();
		if((oldFileName==null||oldFileName.equals("")) && size==0){
			continue;
		} else {
			if(size>2000000){
				out.println("<script>alert('文件太大，请上传合适大小的文件(不大于2MB)');</script>");
				return;
			}
			uploadItem = item;
			oldFileName = oldFileName.replaceAll("\\\\", "/");
			oldFileName = oldFileName.substring(oldFileName.lastIndexOf("/") + 1);
			ext = oldFileName.substring(oldFileName.lastIndexOf(".")+1);
			ext = ext.toLowerCase();
			if(PubFun.isAllowExt(ext,"Image")){
				long webAttachID = NoUtil.getMaxID("UserFileID");
				if(uploadItem !=null){
					RealFileName = webAttachID+"."+ext;
					uploadItem.write(new File(Config.getContextRealPath()+Path+RealFileName));
					out.println("<script>window.parent.afterUpload('/"+AliasName+Path+RealFileName+"');</script>");
				}
			}else{
				out.println("<script>alert('图片格式错误');</script>");
				return;
			}
		}
	}
}

%>
<%
	/* 保全导入保存处理
	 ************************************************************** 
	 *  程序名 : BaoquanInfoUpLoadSave.jsp
	 *  建立日期:2016/09/20
	 *  作者   :  liyinfeng
	 *  模块   :  CMS
	 *  描述   :  批量保全信息导入保存处理
	 *  备注   :  
	 * ------------------------------------------------------------ 
	 *  修改历史 
	 *  序号   日期   修改人     修改原因 
	 * 1 
	 * 2 
	 ************************************************************** 
	 */
%>
<%@page import="com.sinosoft.utility.CErrors"%>
<%@page import="com.sinosoft.utility.ExeSQL"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.FileItemFactory"%>
<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import="com.sinosoft.cms.dataservice.CommentUpLoad"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<script src="../Framework/Main.js"></script>
<%
	CErrors tError = new CErrors(); 
	String FlagStr = "Fail";
	String Content = "";
	String uploadPath = "";
	String newFileName = "";
	FileItemFactory fileFactory = new DiskFileItemFactory();
	ServletFileUpload fu = new ServletFileUpload(fileFactory);
	List fileItems = fu.parseRequest(request);
	fu.setHeaderEncoding("UTF-8");

	// 依次处理每个上传的文件
	Iterator iter = fileItems.iterator();

	while (iter.hasNext()) {
		FileItem item = (FileItem) iter.next();
		if (!item.isFormField()) {
			String fileName = item.getName();
			ExeSQL exesql = new ExeSQL();

			// 文件扩展名必须是xls
			if (!fileName.toLowerCase().endsWith(".xls")) {
				Content = " 导入失败，原因是:文件不是xls文件!";
				FlagStr = "Fail";

			} else {
				String oldFileName = fileName.substring(fileName.lastIndexOf("\\")+1);
				String realDir = request.getRealPath("/");
				// 取得上传路径
				uploadPath = realDir+"/Upload/Temp/baoquan/";
				File path = new File(uploadPath);
				if(!path.exists()){
				    path.mkdirs();
				}
				// 取得上传文件名
				newFileName = oldFileName.substring(0,oldFileName.indexOf("."))+"_"+User.getUserName()+"_"+System.currentTimeMillis();
				uploadPath = uploadPath+newFileName;
				uploadPath = uploadPath.replace('\\', '/');
				
				//备份文件
				item.write(new File(uploadPath));

				BaoquanInfoUpLoad baoquanInfoUpLoad = new BaoquanInfoUpLoad();
				try {
					FlagStr = baoquanInfoUpLoad.upLoadSave(uploadPath);
				} catch (Exception ex) {
					Content = "导入失败，原因是:" + ex.toString();
					FlagStr = "Fail";
					ex.printStackTrace();
				}

				tError = baoquanInfoUpLoad.mErrors;
				if (FlagStr == "Fail") {
					if (!tError.needDealError()) {
						Content = " 导入失败! ";
					} else {
						Content = "导入失败，原因是:" + tError.getFirstError();
					}
				} else {
					Content = " 导入成功!共计导入" + baoquanInfoUpLoad.getCount()
							+ "条评论信息！";
					FlagStr = "Succ";
				}
			}
			out.println("<script>Dialog.closeEx();Dialog.alert('"
					+ Content + "');</script>");
		}
	}
%>
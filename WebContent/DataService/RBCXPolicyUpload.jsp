<%@page import="org.apache.commons.fileupload.*"%>
<%@page import="org.apache.commons.fileupload.servlet.*"%>
<%@page import="org.apache.commons.fileupload.disk.*"%>
<%@page import="com.tenpay.util.MD5Util"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<script src="../Framework/Main.js"></script>
<%
	String orderSn = request.getParameter("orderSn");
	String insuredSn = request.getParameter("insuredSn");
	String policyNo = request.getParameter("policyNo");
	String applyPolicyNo = request.getParameter("applyPolicyNo");
	String receiveDate = request.getParameter("receiveDate");
	String channel = request.getParameter("channel");
	String uploadPath = "", ext = "";
	
	FileItemFactory fileFactory = new DiskFileItemFactory();
	ServletFileUpload fu = new ServletFileUpload(fileFactory);
	List fileItems = fu.parseRequest(request);
	
	fu.setHeaderEncoding("UTF-8");
	Iterator iter = fileItems.iterator();
	while (iter.hasNext()) {
		FileItem item = (FileItem) iter.next();
		if (!item.isFormField()) {
			String oldFileName = item.getName();
			long size = item.getSize();
			if ((oldFileName == null || oldFileName.equals("")) && size==0) {
				continue;
			}
			ext = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
			if (!"pdf".equals(ext)) {
				  String referer = request.getHeader("Referer");
				  out.println("<script>Dialog.alert('上传失败，只能上传pdf格式的文件!');window.location='"+referer+"';</script>");
				  return;
			}

			if (StringUtil.isEmpty(receiveDate)) {
				receiveDate = PubFun.getCurrentDate();
			}
			String insureYear = receiveDate.substring(0, 4);
			String insureMonth = receiveDate.substring(5, 7);
			String newPath = Config.getValue("newPolicyPath");
			if (StringUtil.isEmpty(newPath)) {
				newPath = Config.getContextRealPath();
			}
			uploadPath = newPath + File.separator + "EPolicy" + File.separator + "2005" + File.separator + insureYear + File.separator + insureMonth;
			String fileName = MD5Util.MD5Encode(policyNo, "UTF-8") + ".pdf";
			
			File path = new File(uploadPath);
			if (!path.exists()) {
			    path.mkdirs();
			}
			uploadPath = uploadPath + File.separator + fileName;
			try {
				item.write(new File(uploadPath));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
	RBCXUnderwriting rbcxUnderwriting = new RBCXUnderwriting();
	boolean isSucess = rbcxUnderwriting.dealUnderwriting(orderSn, insuredSn, policyNo, applyPolicyNo, channel, uploadPath);
	String msg = rbcxUnderwriting.getResponse().getString(Constant.ResponseMessageAttrName) ;
	out.println("<script>parent.Dialog.alert(\"" + msg + "\");</script>");
	
	if (isSucess) {
		out.println("<script>window.parent.frames[0].DataGrid.loadData('dg1');</script>");
	}
	out.println("<script>Dialog.close();</script>");
%>

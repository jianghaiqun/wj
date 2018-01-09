<%
	/* 问答导入保存处理
	 ************************************************************** 
	 *  程序名 : InsureInfoUpLoadSave.jsp
	 *  建立日期:2013/08/23 
	 *  作者   :  wangcaiyun
	 *  模块   :  CMS
	 *  描述   :  订单导入保存处理
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
<%@page import="com.sinosoft.cms.dataservice.OrderImport"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<script src="../Framework/Main.js"></script>
<%
	CErrors tError = new CErrors();
	String FlagStr = "Fail";
	String uploadPath = "";
	String Content = "";
	String url = "";
	String newFileName = "";
	FileItemFactory fileFactory = new DiskFileItemFactory();
	ServletFileUpload fu = new ServletFileUpload(fileFactory);
	String productId = request.getParameter("productId");
	String startDate = request.getParameter("startDate");
	String endDate = request.getParameter("endDate");
	String period = request.getParameter("period");
	System.out.println("period="+period);
	String plan = request.getParameter("plan");
	String occup = request.getParameter("occup");
	String textAge = request.getParameter("textAge");
	String appType = request.getParameter("appType");
	String feeYear = request.getParameter("feeYear");
	String grade = request.getParameter("grade");
	String appLevel = request.getParameter("appLevel");
	String mulPeople = request.getParameter("mulPeople");
	String protectionPeriodTy = request.getParameter("protectionPeriodTy");
	String protectionPeriodLast = request.getParameter("protectionPeriodLast");
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
				uploadPath = realDir+"/Upload/Temp/order/";
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
				Map<String, String> appfactorMap = new HashMap<String, String>();
				appfactorMap.put("Plan", plan);
				appfactorMap.put("Period", period);
				appfactorMap.put("Occup", occup);
				appfactorMap.put("TextAge", textAge);
				appfactorMap.put("AppType", appType);
				appfactorMap.put("FeeYear", feeYear);
				appfactorMap.put("Grade", grade);
				appfactorMap.put("AppLevel", appLevel);
				appfactorMap.put("MulPeople", mulPeople);
				OrderImport orderImport = new OrderImport();
				orderImport.setProductId(productId);
				orderImport.setStartDate(startDate);
				orderImport.setEndDate(endDate);
				orderImport.setAppfactorMap(appfactorMap);
				orderImport.setProtectionPeriodTy(protectionPeriodTy);
				orderImport.setProtectionPeriodLast(protectionPeriodLast);
				
				try {
					FlagStr = orderImport.upLoadSave(uploadPath);
				} catch (Exception ex) {
					Content = "导入失败，原因是:" + ex.toString();
					FlagStr = "Fail";
					ex.printStackTrace();
				}

				tError = orderImport.mErrors;
				if ("Fail".equals(FlagStr)) {
					if (!tError.needDealError()) {
						Content = " 导入失败! ";
					} else {
						Content = "导入失败，原因是:" + tError.getFirstError();
					}
				} else {
					Content = " 导入成功!";
					url = FlagStr;
				}
			}
			out.println("<script>Dialog.closeEx();var diagC = new Dialog('Diag1');diagC.Width = 450;diagC.Height = 150;"+
						"diagC.Title = '导入信息';var url = 'DataService/InsureInfoUpLoadInfo.jsp?Content="+Content+"&url="+url+
						"';diagC.URL = encodeURI(url);diagC.ShowMessageRow = true;diagC.show();diagC.OKButton.hide();diagC.CancelButton.value='关闭';</script>");
		}
	}
%>
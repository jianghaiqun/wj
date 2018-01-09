package com.sinosoft.cms.resource.uploader;

import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.ExcelReadUtil;
import cn.com.sinosoft.util.ExcelReadUtil.FileType;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.claimstatisticsdetailsSchema;
import com.sinosoft.schema.claimstatisticsdetailsSet;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class ClaimStatisticsImportServlet
 */
/**
 * ClassName: ClaimStatisticsImportServlet <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选,不用就删除). <br/>
 * date: 2016年10月17日 下午6:48:14 <br/>
 *
 * @author dongsheng
 * @version
 */
public class ClaimStatisticsImportServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(ClaimStatisticsImportServlet.class);

	private static final long serialVersionUID = 1L;
	private static boolean enabled = false;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClaimStatisticsImportServlet() {

		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		String str = config.getInitParameter("enabled");
		if (!StringUtil.isEmpty(str)) {
			enabled = Boolean.parseBoolean(str);
		} else {
			enabled = true;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html; charset=" + Constant.GlobalCharset);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String returnValue = "0";
		String errorMessage = "未知";

		if (!enabled) {
			returnValue = "0";
			errorMessage = "This file uploader is disabled. Please check the WEB-INF/web.xml file";
		} else {
			DiskFileItemFactory fileFactory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fileFactory);
			upload.setHeaderEncoding(Constant.GlobalCharset);
			upload.setSizeMax(1024 * 1024 * 20);// 20M
			try {
				List<FileItem> items = upload.parseRequest(request);
				Mapx<String, Object> fileMapx = new Mapx<String, Object>();
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					if (item.isFormField()) {
						fileMapx.put(item.getFieldName(),
								item.getString(Constant.GlobalCharset));
					} else {
						String OldFileName = item.getName();
						long size = item.getSize();
						if (StringUtil.isEmpty(OldFileName)
								&& size == 0) {
							continue;
						} else {
							logger.info("-----UploadFileName:-----{}", OldFileName);
							fileMapx.put(item.getFieldName(), item);
							fileMapx.put("OldFileName", OldFileName);
						}
					}
				}

				String[] arr = this.importData(fileMapx);

				returnValue = arr[0];
				errorMessage = arr[1];
			} catch (SizeLimitExceededException ex) {
				logger.error(ex.getMessage(), ex);
				returnValue = "-1";
				errorMessage = "文件过大，最大允许上传20M文件";
			} catch (Throwable ex) {
				logger.error(ex.getMessage(), ex);
				returnValue = "-1";
				errorMessage = "上传文件失败:" + ex.getMessage();
			}
		}
		out.println("<script type=\"text/javascript\">");
		out.println("window.parent.onUploadCompleted(" + returnValue + ",'"
				+ errorMessage + "');");
		out.println("</script>");
		out.flush();
		out.close();

	}

	@SuppressWarnings("rawtypes")
	private String[] importData(Mapx fileMapx)
			throws Exception {

		String returnValue = "1";
		String errorMessage = "导入成功";
		FileItem fileItem = (FileItem) fileMapx.get("File");
		String OldFileName = fileMapx.getString("OldFileName");

		FileType fileType;
		if (OldFileName.endsWith("xls")) {
			fileType = FileType.xls;
		} else if (OldFileName.endsWith("xlsx")) {
			fileType = FileType.xlsx;
		} else {
			returnValue = "-2";
			errorMessage = "导入失败,请确认数据!";
			return new String[] { returnValue, errorMessage };
		}

		List<Map<String, String>> result = ExcelReadUtil.getData(fileItem.getInputStream(), fileType, 1);
		Transaction transaction = new Transaction();
		claimstatisticsdetailsSet set = new claimstatisticsdetailsSet();

		for (Map<String, String> map : result) {
			claimstatisticsdetailsSchema schema = new claimstatisticsdetailsSchema();
			QueryBuilder qb = new QueryBuilder(
					"where policyNo=? and  claimsItemsName=? and claimsAmount=? and claimsDate=DATE(?) ");
			qb.add(map.get("0"));
			qb.add(map.get("1"));
			qb.add(map.get("2"));
			qb.add(map.get("3"));
			claimstatisticsdetailsSet qSet = schema.query(qb);
			if (qSet == null || qSet.size() == 0) {
				schema.setid(CommonUtil.getUUID());
				schema.setpolicyNo(map.get("0"));
				schema.setclaimsItemsName(map.get("1"));
				schema.setclaimsAmount(map.get("2"));
				Date date = DateUtil.parseDateTime(map.get("3"));
				schema.setclaimsTime(date);
				schema.setclaimsDate(date);
				Date nowDate = new Date();
				schema.setcreateDateTime(nowDate);
				schema.setmodifyDateTime(nowDate);
				set.add(schema);
			}
		}
		transaction.add(set, Transaction.INSERT);
		if (!transaction.commit()) {
			returnValue = "-2";
			errorMessage = "导入失败,请确认数据!";
		}
		return new String[] { returnValue, errorMessage };
	}
}

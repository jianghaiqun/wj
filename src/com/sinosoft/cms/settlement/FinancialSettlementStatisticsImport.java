package com.sinosoft.cms.settlement;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.utility.Mapx;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * 财务结算明细导入类 Created by dongsheng on 2017/5/26.
 */
public class FinancialSettlementStatisticsImport extends Page {

	private static final long MB_SIZE = 1024 * 1024;
	private static final long MAX_SIZE = 20 * 1024 * 1024;// 设置上传文件最大为 20MB
	private static final long MAX = 30 * 1024 * 1024;// 设置上传文件最大为 20MB
	// 指向'/alidata/kxb/EFile'
	private static final String defaultDir = Config.getValue("default_upload_path");
	// LifeCycleHandler handler;
	private static final String TASK_ID = "financial_settlement_details";
	private String errorMsg;
	private String status;
	private String absolutePath;
	private int progress;

	public int getProgress() {

		return progress;
	}

	public FinancialSettlementStatisticsImport setProgress(int progress) {

		this.progress = progress;
		return this;
	}

	public String getErrorMsg() {

		return errorMsg;
	}

	public FinancialSettlementStatisticsImport setErrorMsg(String errorMsg) {

		this.errorMsg = errorMsg;
		return this;
	}

	public String getStatus() {

		return status;
	}

	public FinancialSettlementStatisticsImport setStatus(String status) {

		this.status = status;
		return this;
	}

	public void fileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html; charset=" + Constant.GlobalCharset);
		response.setHeader("Cache-Control", "no-cache");
		final PrintWriter out = response.getWriter();
		final DiskFileItemFactory factory = new DiskFileItemFactory();
		final ServletFileUpload upload = new ServletFileUpload(factory);
		// Set overall request size constraint
		upload.setSizeMax(MAX);
		ProgressListener progressListener = new ProgressListener() {

			private long megaBytes = -1;

			public void update(long pBytesRead, long pContentLength, int pItems) {

				long mBytes = pBytesRead / 1000000;
				if (megaBytes == mBytes) {
					return;
				}
				megaBytes = mBytes;
				if (pContentLength == -1) {
					setProgress(100);
				} else {
					setProgress((int) (100 * pBytesRead /
					pContentLength));
				}
				Object[] argArr = {getProgress(), pBytesRead, pContentLength};
				logger.info("{}%,So far,{} of {} bytes have been read.", argArr);
			}
		};
		upload.setProgressListener(progressListener);
		try {
			// Parse the request
			List<FileItem> items = upload.parseRequest(request);
			// Process the uploaded items
			Iterator<FileItem> iterator = items.iterator();
			Mapx<String, String> param = new Mapx<String, String>();

			while (iterator.hasNext()) {
				FileItem item = iterator.next();
				if (item.isFormField()) {
					processFormField(item, param);
				} else {
					if (item.getSize() <= MAX_SIZE) {
						absolutePath = processUploadedFile(item);
						// readFile(absolutePath);
						setResult("success", "文件导入成功！");

					} else {
						setResult("error", "文件大小异常。");
					}
				}
			}
		} catch (FileUploadBase.SizeLimitExceededException e) {
			logger.error(e.getMessage(), e);
			setResult("error", "文件大小异常。");
		} catch (FileUploadException e) {
			logger.error(e.getMessage(), e);
			setResult("error", "文件上传异常。");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			setResult("error", e.getMessage());
		}

		absolutePath = StringUtils.isNotEmpty(absolutePath) ? absolutePath.replace("\\", "/") : null;
		out.println("<script>window.parent.afterUpload('" + getStatus() + "','" + getErrorMsg() + "','" + absolutePath
				+ "');</script>");
		out.flush();
		out.close();
	}

	private void setResult(String status, String errorMsg) {

		this.status = status;
		this.errorMsg = errorMsg;
	}

	private void processFormField(FileItem item, Mapx<String, String> param) {

		String name = item.getFieldName();
		String value = item.getString();
		param.put(name, value);
	}

	private String processUploadedFile(FileItem item) throws Exception {

		String fieldName = item.getFieldName();
		// System.out.println("fieldName = " + fieldName);
		String fileName = item.getName();
		int indexOf = fileName.lastIndexOf(File.separator);
		fileName = fileName.substring(indexOf + 1);
		// System.out.println("fileName = " + fileName);
		String contentType = item.getContentType();
		// System.out.println("contentType = " + contentType);
		boolean isInMemory = item.isInMemory();
		// System.out.println("isInMemory = " + isInMemory);
		long sizeInBytes = item.getSize();
		// System.out.println("sizeInBytes = " + sizeInBytes);
		String absolutePath;
		// Process a file upload
		File dirFile = new File(defaultDir, "financialSettlement");
		if (!dirFile.exists()) {

			if (!dirFile.mkdirs()) {
				throw new Exception("路径创建失败。");
			}
		}

		File uploadedFile = new File(dirFile, fileName);
		absolutePath = uploadedFile.getAbsolutePath();

		if (uploadedFile.exists()) {
			if (!uploadedFile.delete()) {
				throw new Exception("文件已经存在，且不可操作。");
			}
		}
		item.write(uploadedFile);
		// System.out.println(absolutePath);
		return absolutePath;
	}

}

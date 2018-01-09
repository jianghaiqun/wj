package com.sinosoft.cms.settlement;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.SessionListener;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.Mapx;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 *
 * Created by dongsheng on 2017/6/12.
 */
public class SimpleFileUploadManager extends Page {

	private static final long MB_SIZE = 1024 * 1024;
	private static final long MAX_SIZE = 20 * 1024 * 1024;// 设置上传文件最大为 20MB
	private static final boolean writeToFile = true;
	// 指向'/alidata/kxb/EFile'
	private static final String defaultDir = Config.getValue("default_upload_path");
	LifeCycleHandler handler;

	private String errorMsg;
	private String status;

	public void fileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException {

		final PrintWriter writer = response.getWriter();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(MAX_SIZE);
		// Create a progress listener
		ProgressListener progressListener = new ProgressListener() {

			private long megaBytes = -1;

			public void update(long pBytesRead, long pContentLength, int pItems) {

				long mBytes = pBytesRead / MB_SIZE;
				if (megaBytes == mBytes) {
					return;
				}
				megaBytes = mBytes;
				//System.out.println("We are currently reading item " + pItems);
				writer.println("<p>正在读取第" + pItems + "个项目</p>");
				if (pContentLength == -1) {
					//System.out.println("So far, " + pBytesRead + " bytes have been read.");
					writer.println("So far, " + pBytesRead + " bytes have been read.");
				} else {
					//System.out.println("So far, " + pBytesRead + " of " + pContentLength
//							+ " bytes have been read.");
					writer.println("<p>正在上传：" + pBytesRead / MB_SIZE + "/" + pContentLength / MB_SIZE
							+ " MB </p>");
				}
			}
		};
		upload.setProgressListener(progressListener);
		// Parse the request
		List<FileItem> items = null;
		try {
			items = upload.parseRequest(request);
			// Process the uploaded items
			Iterator<FileItem> iterator = items.iterator();
			Mapx<String, String> param = new Mapx<String, String>();

			while (iterator.hasNext()) {
				FileItem item = iterator.next();
				if (item.isFormField()) {
					processFormField(item, param);
				} else {
					String absolutePath = processUploadedFile(item);
					// readFile(absolutePath);
				}
			}
		} catch (FileUploadException e) {
			logger.error(e.getMessage(), e);
			setResult("error", "文件上传异常。");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			setResult("error", e.getMessage());
		}

		LongTimeTask ltt = LongTimeTask.getInstanceByType("financial_settlement_details");
		if (ltt != null) {
			response.sendRedirect("DBUpload.jsp?Error=相关任务正在运行中，请先中止！");
			return;
		} else {
			ltt = new LongTimeTask() {

				@Override
				public void execute() {

				}
			};
			ltt.setType("financial_settlement_details");
		}

		SessionListener.forceExit();
		Config.isAllowLogin = false;
		ltt.execute();

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
		//System.out.println("fieldName = " + fieldName);
		String fileName = item.getName();
		//System.out.println("fileName = " + fileName);
		String contentType = item.getContentType();
		//System.out.println("contentType = " + contentType);
		boolean isInMemory = item.isInMemory();
		//System.out.println("isInMemory = " + isInMemory);
		long sizeInBytes = item.getSize();
		//System.out.println("sizeInBytes = " + sizeInBytes);
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
		//System.out.println(absolutePath);
		return absolutePath;
	}

}

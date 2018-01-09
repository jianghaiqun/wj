package com.wangjin.infoseeker.iReport;

import com.sinosoft.framework.utility.StringUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * ireport报表后台核心接口
 * 
 */
public class ReportServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(ReportServlet.class);

	private static final long serialVersionUID = 1L;
	
	public static final String ADMIN_CAPTCHA_ERROR_URL = "/admin/admin!login.action?error=captcha";
	
	@SuppressWarnings("unchecked")
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		String method = request.getParameter("method");
		String sessionId = request.getParameter("sessionId");
		
		String defaultFilename = request.getParameter("defaultFilename");
		if (StringUtil.isEmpty(type) || StringUtil.isEmpty(method)) {
			logger.error("type or method is null . type: {} method:{}", type, method);
			return;
		}
		
		if (session == null) {
			logger.warn("用户Session超时,请重新登录!");
			response.sendRedirect(request.getContextPath() + ADMIN_CAPTCHA_ERROR_URL);
			return;
		}
		
		JasperPrint cache = (JasperPrint) session.getAttribute(sessionId);
		JasperPrint jasperPrint = null;
		
		if("html".equals(type) || ("excel".equals(type) && cache == null)){
			Map paramp = getElement(request.getParameterMap());
			try {
				String className = method.substring(0, method.lastIndexOf("."));
				String mothodName = method.substring(method.lastIndexOf(".") + 1, method.length());
				Class<?> c = Class.forName(className);
				Object obj = (Object) c.newInstance();
	
				Class<?>[] parameterTypes = new Class[] { Map.class };
				Method class_method = c.getMethod(mothodName, parameterTypes);
				Object[] args = new Object[] { paramp };
				Object r = class_method.invoke(obj, args);
				if (r instanceof JasperPrint) {
					jasperPrint = (JasperPrint) r;
				} else {
					logger.error("the method result is error . must be JasperPrint type");
					return;
				}
				
				if("html".equals(type)){
					exportHtml(jasperPrint, defaultFilename, request, response);
					session.setAttribute(sessionId, jasperPrint);
				}
				if("excel".equals(type)){
					exportExcel(jasperPrint, defaultFilename, request, response);
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}else {
			exportExcel(cache, defaultFilename, request, response);
		}
	}

	private void exportExcel(JasperPrint jasperPrint, String defaultFilename, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("application/vnd.ms-excel");
			String defaultname = null;
			if (StringUtil.isNotEmpty(defaultFilename)) {
				defaultname = defaultFilename + ".xls";
			} else {
				defaultname = "Export.xls";
			}
			response.setHeader("Content-disposition", "attachment; filename=" + defaultname);
			ServletOutputStream ouputStream = response.getOutputStream();
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporter.exportReport();
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	private void exportHtml(JasperPrint jasperPrint, String defaultFilename, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			JRHtmlExporter exporter = new JRHtmlExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
			exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
			exporter.exportReport();
			out.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} catch (JRException e) {
			logger.error(e.getMessage(), e);
		}

	}

	@SuppressWarnings("unchecked")
	private Map getElement(Map parameterMap) {
		HashMap newMap = new HashMap();
		for (Iterator iter = parameterMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry element = (Map.Entry) iter.next();
			Object strKey = element.getKey();
			String[] value = (String[]) element.getValue();
			String valueStr = "";
			for (int i = 0; i < value.length; i++) {
				try {
					valueStr = new String(value[i].trim().getBytes("iso-8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
				}
			}
			newMap.put(strKey, valueStr);
		}
		return newMap;
	}
}

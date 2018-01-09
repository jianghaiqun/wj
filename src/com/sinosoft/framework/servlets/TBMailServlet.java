package com.sinosoft.framework.servlets;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.ParseXMLToObject;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 经代通发送邮件接口
 * 
 * @author sinosoft
 * 
 */
public class TBMailServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(TBMailServlet.class);

	private static final long serialVersionUID = 1138717953245092512L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "未收到请求数据";
		OutputStream out = response.getOutputStream();
		try {
			ParseXMLToObject tParseXMLToObject = new ParseXMLToObject();
			Document document = tParseXMLToObject.deealStreamToXML(request);
			if (document == null) {
				result = "xml为空（未收到请求数据）--- time：" + PubFun.getCurrentDate() + " " + PubFun.getCurrentTime() + "";
				logger.error(result);
				throw new NullPointerException();
			}
			// 重新获取订单号
			Element root = document.getRootElement();
			if (null == root) {
				result = "发送邮件接口root节点为空";
				logger.error("发送邮件接口root节点为空");
				throw new NullPointerException();
			}

			/* 取得header部分 */
			Element eleHeader = root.getChild("Header");
			if (null == eleHeader) {
				result = "发送邮件接口Header节点为空";
				logger.error("发送邮件接口Header节点为空");
				throw new NullPointerException();
			}

			/* 取得订单号 */
			String orderSn = eleHeader.getChildTextTrim("orderSn");

			/* 取得Request部分 */
			Element Request = root.getChild("Request");
			if (null == Request) {
				result = "发送邮件接口Request节点为空";
				logger.error("发送邮件接口Request节点为空");
				throw new NullPointerException();
			}
			ActionUtil.sendAlarmMail(Request.getChildTextTrim("msgInf"));
			/* 取得订单号 */
			result = "success";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result += "error:" + e.getMessage();

		} finally {
			out.write(result.getBytes("utf-8"));
			out.flush();
			out.close();
		}
	}

}

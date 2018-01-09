package com.sinosoft.forward;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ShopServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(ShopServlet.class);

	public void service(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		try {
			request.setCharacterEncoding("utf-8");
			String operate = request.getParameter("operate");
			String now = request.getParameter("now");
			logger.info("now:" + now);
			// 执行逻辑
			String result = null;
			try {
				ShopAction action = ShopFactory.getAction(operate);
				result = action.execute(request);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			String method = request.getParameter("method");
			if (method == null || "".equals(method)) {
				request.setAttribute("method", "execute");
			}

			// 返回给客户端
			PrintWriter out = response.getWriter();
			logger.info("result:{}", result);
			out.print(result);
			out.flush();
			out.close();
			
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}

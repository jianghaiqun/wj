package com.sinosoft.framework.clustering.server;

import com.sinosoft.framework.clustering.Clustering;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author 王育春
 * @Date 2010-4-22
 * @Mail wyuch@midding.com
 */
public class HttpClusteringServer extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(HttpClusteringServer.class);

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		if (!Clustering.isClusteringServer()) {
			try {
				logger.warn("本应用未被配置成集群服务器!");
				response.getWriter().print("本应用未被配置成集群服务器!");
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		String type = request.getParameter("Type");
		String key = request.getParameter("Key");
		String action = request.getParameter("Action");
		String value = request.getParameter("Value");
		String result = ClusteringData.dealRequest(type, key, action, value);
		try {
			response.getWriter().print(result);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}

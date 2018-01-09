package com.sinosoft.framework.cache;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.ConfigLoader;
import com.sinosoft.framework.license.SystemInfo;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ObjectUtil;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.XMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class CacheRefreshServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(CacheRefreshServlet.class);

	private static final long serialVersionUID = 1L;
	private static String nodeID = null;
	private static String selfUrl = null;
	private static boolean clusteringEnable = false;
	private static Mapx<String, String> configMap = null;
	private static ArrayList<String> nodes = new ArrayList();

	private static void loadConfig() {
		if (configMap == null)
			try {
				configMap = new Mapx();
				XMLLoader.NodeData[] datas = ConfigLoader.getNodeDataList("clustering.config");
				for (int i = 0; (datas != null) && (i < datas.length); i++) {
					configMap.put(datas[i].getAttributes().getString("name"), datas[i].getBody());
				}
				clusteringEnable = "true".equals(configMap.get("ClusteringEnable"));
				datas = ConfigLoader.getNodeDataList("clustering.node");
				if (datas != null) {
					for (int i = 0; i < datas.length; i++) {
						String url = datas[i].getAttributes().getString("URL");
						if (!url.endsWith("/")) {
							url = url + "/";
						}
						nodes.add(url);
					}
				}
				logger.info("----{}({}): Load Clustering Configuration----",  Config.getAppCode(), Config.getAppName());
			} catch (Exception e) {
				logger.error("----" + Config.getAppCode() + "(" + Config.getAppName() + "): Load Clustering Configuration Failure----" + e.getMessage(), e);
			}
	}

	public static boolean isClusteringEnable() {
		loadConfig();
		return clusteringEnable;
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!isClusteringEnable()) {
			return;
		}
		response.setHeader("Pragma", "No-Cache");
		response.setHeader("Cache-Control", "No-Cache");
		response.setDateHeader("Expires", 0L);

		if (nodeID == null) {
			nodeID = StringUtil.md5Hex(SystemInfo.getMacAddress());
		}

		String p = request.getParameter("p");
		String t = request.getParameter("t");
		String k = request.getParameter("k");
		if (ObjectUtil.empty(p)) {
			return;
		}
		if (ObjectUtil.empty(t)) {
			return;
		}
		if (ObjectUtil.empty(k))
			CacheManager.removeType(p, t);
		else {
			CacheManager.remove(p, t, k);
		}

		response.getWriter().print(nodeID);
	}

	public static void refresh(final String p, final String t, final String k) {
		new Thread() {
			public void run() {
				for (String url : CacheRefreshServlet.nodes) {
					if (url.equals(CacheRefreshServlet.selfUrl)) {
						continue;
					}
					StringBuilder sb = new StringBuilder(url);
					sb.append("CacheRefreshServlet.jsp?");
					sb.append("p=");
					sb.append(p);
					sb.append("&t=");
					sb.append(t);
					if (k != null) {
						sb.append("&k=");
						sb.append(k);
					}
					try {
						String result = ServletUtil.getURLContent(sb.toString()).trim();
						if (result.equals(CacheRefreshServlet.nodeID))
							CacheRefreshServlet.selfUrl = url;
					} catch (Exception e) {
						logger.error("Clustering cache refresh failed:" + sb + e.getMessage(), e);
					}
				}
			}
		}.start();
	}

	public static void refresh(String p, String t) {
		refresh(p, t, null);
	}

	public static void remove(String p, String t) {
		refresh(p, t, null);
	}

	public static void remove(String p, String t, String k) {
		refresh(p, t, k);
	}
}
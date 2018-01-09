package com.sinosoft.framework.clustering;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.User.UserData;
import com.sinosoft.framework.clustering.server.SocketClusteringServer;
import com.sinosoft.framework.utility.Mapx;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * 本框架中的集群功能只限于所有节点共用一个数据库的情况<br>
 * （此数据库可能也是一个集群，由数据库产品本身提供集群功能）。
 * 
 * 
 * @since 1.3
 */
public class Clustering {
	private static final Logger logger = LoggerFactory.getLogger(Clustering.class);

	private static long LastUpdateTime;// 最后一次读取配置文件的时间

	private static long RefershPeriod = 60000;// 隔多长时间读取一次配置

	private static Mapx configMap;

	private static Server[] Servers;

	private static Object mutex = new Object();

	/**
	 * 加载集群配置，从clustering.xml加载，会定时检测是否有改变
	 */
	private static void init() {
		if (System.currentTimeMillis() - LastUpdateTime > RefershPeriod) {
			synchronized (mutex) {
				if (System.currentTimeMillis() - LastUpdateTime > RefershPeriod) {
					String path = Config.getContextRealPath();
					File f = new File(path + "WEB-INF/classes/clustering.xml");
					if (!f.exists()) {
						return;
					}
					SAXReader reader = new SAXReader(false);
					Document doc;
					try {
						doc = reader.read(f);
						Element root = doc.getRootElement();
						List elements = root.elements();
						configMap = new Mapx();
						for (int i = 0; i < elements.size(); i++) {
							Element ele = (Element) elements.get(i);
							if (ele.getName().equalsIgnoreCase("config")) {
								configMap.put(ele.attributeValue("name"), ele.getTextTrim());
							} else {
								List serverList = ele.elements("server");
								Server[] arr = new Server[serverList.size()];
								for (int j = 0; j < serverList.size(); j++) {
									Element s = (Element) serverList.get(j);
									Server server = new Server();
									server.URL = s.attributeValue("url");
									server.Weight = Integer.parseInt(s.attributeValue("weight"));
									server.RetryCount = Integer.parseInt(s.attributeValue("retrycount"));
									server.Timeout = Integer.parseInt(s.attributeValue("timeout"));
									arr[j] = server;
								}
								Servers = arr;
							}
						}
						tryInitServer();
						if (LastUpdateTime == 0) {
							logger.info("----{}({}): Clustering Initialized----",Config.getAppCode(), Config.getAppName());
						}
					} catch (Exception e) {
						logger.error("----" + Config.getAppCode() + "(" + Config.getAppName()
								+ "): Clustering Failure----" + e.getMessage(), e);
					}
				}
				LastUpdateTime = System.currentTimeMillis();
			}
		}
	}

	/**
	 * 如果是SocketServer,则尝试打开端口
	 */
	private static void tryInitServer() {
		String flag = configMap.getString("UserAsClusteringServer");
		if (!"true".equalsIgnoreCase(flag)) {
			return;
		}
		String type = configMap.getString("ClusteringServerType");
		String portStr = configMap.getString("SocketPort");
		if ("Socket".equalsIgnoreCase(type)) {
			try {
				final int port = Integer.parseInt(portStr);
				new Thread() {
					public void run() {
						SocketClusteringServer.start(port);
					}
				}.start();
			} catch (Exception e) {
				throw new RuntimeException("clustering.xml中配置的SocketPort不是数字：" + portStr);
			}
		}
	}

	/**
	 * 当前应用是否处于集群之中
	 */
	public static boolean isClustering() {
		init();
		if (configMap == null) {
			return false;
		}
		return "true".equals(configMap.getString("ClusteringEnable"));
	}

	/**
	 * 当前应用是否是一个集群服务提供者
	 */
	public static boolean isClusteringServer() {
		init();
		if (configMap == null) {
			return false;
		}
		return "true".equals(configMap.getString("UserAsClusteringServer"));
	}

	public static void put(String key, String value) {
		init();
	}

	public static void putObject(String key, Object value) {
		init();
	}

	public static String get(String key) {
		init();
		return null;
	}

	public static Object getObject(String key) {
		init();
		return null;
	}

	public static boolean containsKey(String key) {
		init();
		return false;
	}

	public static void remove(String key) {
		init();
	}

	public static void putMapx(String key, Mapx map) {
		init();
	}

	public static Mapx getMapx(String key) {
		init();
		return null;
	}

	public static String[] getAllKeys() {
		init();
		return null;
	}

	public static void cacheUser(UserData user) {
		init();
	}

	public static class Server {
		/**
		 * 集群服务器URL
		 */
		public String URL;

		/**
		 * 操作失败时重试次数
		 */
		public int RetryCount;

		/**
		 *多长时间后即认为服务器宕机
		 */
		public int Timeout;

		/**
		 * 集群服务器权重，权重大的将存储更多数据
		 */
		public int Weight;

		/**
		 * 是否存活，如果为false则ServerValidteTask线程会定期尝试连接
		 */
		public boolean isAlive;
	}

}

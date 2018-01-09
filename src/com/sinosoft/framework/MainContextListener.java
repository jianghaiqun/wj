package com.sinosoft.framework;

import com.sinosoft.framework.clustering.server.SocketClusteringServer;
import com.sinosoft.framework.data.DBConnPool;
import com.sinosoft.framework.extend.ExtendManager;
import com.sinosoft.framework.schedule.CronManager;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.beans.Introspector;
import java.lang.ref.Reference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.Timer;

/**
 * 监听Servlet上下文
 * 
 */
public class MainContextListener implements ServletContextListener {
	private static final Logger logger = LoggerFactory.getLogger(MainContextListener.class);
	private CronManager manager;

	/**
	 * 上下文初始化时同时初始化一些全局对象
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		// 以下两行代码MainFilter中也有，有可能是MainFilter先初始化，也有可能是MainContextListener先初始化（考虑WebSphere）
		ServletContext sc = arg0.getServletContext();
		Config.configMap.put("System.ContainerInfo", sc.getServerInfo());// 连接池需要这个属性，所以先置
		Config.interfaceMap = Config.getInterfaceMapx();
		Config.getJBossInfo();// 考虑JBoss
		Config.loadConfig();
		manager = CronManager.getInstance();
		ExtendManager.loadConfig(); 
		//获取property资源文件
		getProperty(); 
	}

	/**
	 * 上下文销毁时清除掉某些全局对象
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			logger.info("服务器结束接口文件{}", Config.interfaceMap.getString("FDInsComService"));
			if (manager != null) {
				manager.destory();
			}
			SessionListener.clear();

			// 以下试图关闭SocketClusteringServer
			SocketClusteringServer.stop();

			// 以下为防止Log4j的ThreadDeath异常
			LogManager.shutdown();
			LogFactory.releaseAll();
			Introspector.flushCaches();

		} catch (Throwable t) {
			logger.error(t.getMessage(), t);
		}

		// 以下处理ThreadLocal相关的内存泄漏
		clearThreadLocals();

		// 以下处理Tomcat下JDBC驱动内存泄漏问题
		if (Config.isTomcat() && !DBConnPool.getDBConnConfig().isJNDIPool) {
			try {
				for (Enumeration e = DriverManager.getDrivers(); e.hasMoreElements();) {
					Driver driver = (Driver) e.nextElement();
					if (driver.getClass().getClassLoader() == getClass().getClassLoader()) {
						DriverManager.deregisterDriver(driver);
					}
				}
			} catch (Exception e) {
				logger.error("Exception cleaning up java.sql.DriverManager's driver: {}", e.getMessage());
			}
		}

		// 以下处理Mysql驱动的Cacellation timer内存泄露问题
		try {
			if (DBConnPool.getDBConnConfig().isMysql()) {
				Class ConnectionImplClass = Thread.currentThread().getContextClassLoader().loadClass("com.mysql.jdbc.ConnectionImpl");
				if (ConnectionImplClass != null && ConnectionImplClass.getClassLoader() == getClass().getClassLoader()) {
					Field f = ConnectionImplClass.getDeclaredField("cancelTimer");
					f.setAccessible(true);
					Timer timer = (Timer) f.get(null);
					timer.cancel();
				}
			}
		} catch (Exception e) {
			logger.error("清除MySQL Cancellation Timer时发生错误: {}", e.getMessage());
		}
	}

	private static Thread[] getThreads() {
		ThreadGroup tg = Thread.currentThread().getThreadGroup();
		while (tg.getParent() != null) {
			tg = tg.getParent();
		}
		int threadCountGuess = tg.activeCount() + 100;
		Thread[] threads = new Thread[threadCountGuess];
		int threadCountActual = tg.enumerate(threads);
		while (threadCountActual == threadCountGuess) {
			threadCountGuess *= 2;
			threads = new Thread[threadCountGuess];
			threadCountActual = tg.enumerate(threads);
		}
		return threads;
	}

	public static void clearThreadLocals() {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		Thread[] threads = getThreads();
		try {
			Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
			threadLocalsField.setAccessible(true);
			Field inheritableThreadLocalsField = Thread.class.getDeclaredField("inheritableThreadLocals");
			inheritableThreadLocalsField.setAccessible(true);
			Class tlmClass = Class.forName("java.lang.ThreadLocal$ThreadLocalMap");
			Field tableField = tlmClass.getDeclaredField("table");
			tableField.setAccessible(true);
			for (int i = 0; i < threads.length; ++i) {
				if (threads[i] == null)
					continue;
				Object threadLocalMap = threadLocalsField.get(threads[i]);
				clearThreadLocalMap(threadLocalMap, tableField, classloader);
				threadLocalMap = inheritableThreadLocalsField.get(threads[i]);
				clearThreadLocalMap(threadLocalMap, tableField, classloader);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static void clearThreadLocalMap(Object map, Field internalTableField, ClassLoader classloader) throws NoSuchMethodException, IllegalAccessException, NoSuchFieldException,
			InvocationTargetException {
		if (map != null) {
			Method mapRemove = map.getClass().getDeclaredMethod("remove", new Class[] { ThreadLocal.class });
			mapRemove.setAccessible(true);
			Object[] table = (Object[]) internalTableField.get(map);
			int staleEntriesCount = 0;
			if (table != null) {
				for (int j = 0; j < table.length; ++j) {
					if (table[j] != null) {
						boolean remove = false;
						Object key = ((Reference) table[j]).get();
						if ((key != null) && (key.getClass().getClassLoader() == classloader)) {
							remove = true;
						}
						Field valueField = table[j].getClass().getDeclaredField("value");
						valueField.setAccessible(true);
						Object value = valueField.get(table[j]);

						if ((value != null) && (value.getClass().getClassLoader() == classloader)) {
							remove = true;
						}
						if (remove) {
							if (key == null) {
								++staleEntriesCount;
							} else {
								mapRemove.invoke(map, new Object[] { key });
							}
						}
					}
				}
			}
			if (staleEntriesCount > 0) {
				Method mapRemoveStale = map.getClass().getDeclaredMethod("expungeStaleEntries", new Class[0]);
				mapRemoveStale.setAccessible(true);
				mapRemoveStale.invoke(map, new Object[0]);
			}
		}
	}
	private static void getProperty() {
		// 获得资源包
		ResourceBundle bundle = ResourceBundle.getBundle("CPSConfig");
		// 通过资源包拿到所有的名称
		Enumeration<String> allName = bundle.getKeys();
		// 遍历
		while (allName.hasMoreElements()) {
			// 获取每一个名称
			String name = (String) allName.nextElement();
			// 利用已得到的名称通过资源包获得相应的值
			String value = bundle.getString(name);
			//config静态变量
			Config.map_property.put(name,value);
		}
	}

}

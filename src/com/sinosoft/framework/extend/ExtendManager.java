package com.sinosoft.framework.extend;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.extend.plugin.IPlugin;
import com.sinosoft.framework.extend.plugin.PluginConfig;
import com.sinosoft.framework.extend.plugin.PluginException;
import com.sinosoft.framework.extend.plugin.PluginManager;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ObjectUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExtendManager {
	private static final Logger logger = LoggerFactory.getLogger(ExtendManager.class);

	private static Mapx extendMap;
	private static Object mutex = new Object();

	private static Mapx extendActionMap;
	private static Mapx extendPointMap;
	private static Mapx extendServiceMap;
	private static Mapx extendServiceClassMap;

	public static void loadConfig() {
		if (extendMap == null) {
			synchronized (mutex) {
				if (extendMap == null) {
					Mapx map = new Mapx();
					String path = Config.getContextRealPath() + "WEB-INF/classes/framework.xml";
					if (new File(path).exists()) {
						readConfigFile(path, map);
					}
					/*
					File[] fs = new File(Config.getContextRealPath() + "WEB-INF/classes").listFiles();
					for (int i = 0; i < fs.length; i++) {
						File f = fs[i];
						if (f.isFile() && f.getName().endsWith(".xml")) {
							System.out.println("读取文件" + path);
							readConfigFile(f.getAbsolutePath(), map);
							System.out.println("读取文件结束" + path);
						}
					}*/
					extendMap = map;
					// 加载插件
					extendActionMap = new Mapx();
					extendPointMap = new Mapx();
					extendServiceMap = new Mapx();
					extendServiceClassMap = new Mapx();
					PluginManager.init();
					ArrayList<PluginConfig> configList = PluginManager.getAllPluginConfig();
					for (PluginConfig pc : configList) {
						if (!pc.isEnabling() || pc.isRunning())
							continue;

						try {
							startPlugin(pc);
						} catch (PluginException e) {
							logger.error(e.getMessage(), e);
						}
					}
					invoke("com.sinosoft.framework.AfterPluginInit", new Object[0]);
				}
			}
		}
	}

	public static void startPlugin(PluginConfig pc) throws PluginException {
		if (pc == null || !pc.isEnabling() || pc.isRunning()) {
			return;
		}
		if (ObjectUtil.notEmpty(pc.getClassName()))
			try {
				pc.setRunning(true);
				pc.setEnabling(true);
				for (Object id : pc.getRequiredPlugins().keyArray()) {
					startPlugin(PluginManager.getPluginConfig(id + ""));
				}
				Class c = Class.forName(pc.getClassName());
				if (!IPlugin.class.isAssignableFrom(c)) {
					logger.info("Plugin class '{}' isn't inherit from IPlugin", pc.getClassName());
					return;
				}

				extendPointMap.putAll(pc.getExtendPoints());
				Collection<ExtendActionConfig> actions = pc.getExtendActions().values();
				ArrayList list;
				for (ExtendActionConfig action : actions) {
					list = (ArrayList) extendActionMap.get(action.getExtendPointID());
					if (list == null) {
						list = new ArrayList();
						extendActionMap.put(action.getExtendPointID(), list);
					}
					list.add(action);
				}

				Object[] esStr = pc.getExtendServices().valueArray();
				for (Object esc : esStr) {
					ExtendServiceConfig es = (ExtendServiceConfig) esc;
					extendServiceMap.put(es.getID(), es);
					extendServiceClassMap.put(es.getClassName(), es);
				}

				Collection<ExtendItemConfig> items = pc.getExtendItems().values();
				for (ExtendItemConfig item : items) {
					IExtendItem ei = item.getInstance();
					ExtendServiceConfig es = (ExtendServiceConfig) extendServiceMap.get(item.getExtendServiceID());
					if (es == null) {
						throw new RuntimeException("ExtendService " + item.getExtendServiceID() + " not found, error occurs in " + pc.getID());
					}
					es.getInstance().register(ei);
				}
				
				IPlugin plugin = (IPlugin) c.newInstance();
				plugin.start();
				logger.info("{} started!", pc.getID());
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			} catch (InstantiationException e) {
				logger.error(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			}
	}

	public static Object[] invoke(String extendPointID, Object[] args) {
		try {
			loadConfig();
			if (!extendPointMap.containsKey(extendPointID)) {
				throw new RuntimeException("ExtendPoint is not found:" + extendPointID);
			}
			ArrayList<ExtendActionConfig> actions = findActions(extendPointID);
			if (actions == null) {
				return null;
			}
			Object[] arr = new Object[actions.size()];
			int i = 0;
			for (ExtendActionConfig eac : actions) {
				try {
					arr[(i++)] = eac.getInstance().execute(args);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new Exception(e.getMessage());
				}
			}
			return arr;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static ArrayList<ExtendActionConfig> findActions(String extendPointID) {
		loadConfig();
		return (ArrayList) extendActionMap.get(extendPointID);
	}

	public static ExtendPointConfig findExtendPoint(String extendPointID) {
		loadConfig();
		return (ExtendPointConfig) extendPointMap.get(extendPointID);
	}

	public static ExtendServiceConfig findExtendService(String extendServiceID) {
		loadConfig();
		return (ExtendServiceConfig) extendServiceMap.get(extendServiceID);
	}

	public static ExtendServiceConfig findExtendServiceByClass(String className) {
		loadConfig();
		return (ExtendServiceConfig) extendServiceClassMap.get(className);
	}

	private static void readConfigFile(String path, Mapx map) {
		SAXReader reader = new SAXReader(false);
		Document doc;
		try {
			
			
			
			doc = reader.read(new File(path));
			Element root = doc.getRootElement();
			Element extend = root.element("extend");
			if (extend != null) {
				List types = extend.elements("action");
				for (int i = 0; i < types.size(); i++) {
					Element type = (Element) types.get(i);
					String className = type.attributeValue("class");
					String enable = type.attributeValue("enable");
					try {
						Object obj = Class.forName(className).newInstance();
						if (!(obj instanceof IExtendAction)) {
							logger.warn("类{}必须继承IExtendAction!", className);
							continue;
						}
						if (!"false".equals(enable)) {
							IExtendAction action = (IExtendAction) obj;
							Mapx targetMap = (Mapx) map.get(action.getTarget());
							if (targetMap == null) {
								targetMap = new Mapx();
							}
							targetMap.put(action.getClass().getName(), action);
							map.put(action.getTarget(), targetMap);
						}
					} catch (InstantiationException e) {
						logger.error(e.getMessage(), e);
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage(), e);
					} catch (ClassNotFoundException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void addExtendAction(IExtendAction action, String target) {
		synchronized (mutex) {
			Mapx targetMap = (Mapx) extendMap.get(target);
			if (targetMap == null) {
				targetMap = new Mapx();
				extendMap.put(target, targetMap);
			}
			targetMap.put(action.getClass().getName(), action);
		}
	}

	public static boolean hasAction(String target) {
		loadConfig();
		return extendMap.get(target) != null;
	}

	public static IExtendAction[] find(String target) {
		loadConfig();
		Mapx targetMap = (Mapx) extendMap.get(target);
		if (targetMap == null) {
			return new IExtendAction[0];
		} else {
			IExtendAction[] arr = new IExtendAction[targetMap.size()];
			Object[] vs = targetMap.valueArray();
			for (int i = 0; i < arr.length; i++) {
				arr[i] = (IExtendAction) vs[i];
			}
			return arr;
		}
	}

	public static void executeAll(String target, Object[] args) {
		IExtendAction[] actions = find(target);
		for (int i = 0; i < actions.length; i++) {
			actions[i].execute(args);
		}
	}
}

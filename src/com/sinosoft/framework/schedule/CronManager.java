package com.sinosoft.framework.schedule;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.ConfigLoader;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.XMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;

/**
 * 初始化一个定时器，该定时器按指定的间隔调用CronMontior中的run()
 * 
 * @author guobin
 */
public class CronManager {
	private static final Logger logger = LoggerFactory.getLogger(CronManager.class);
	private Timer mTimer;// 仅用来定期调用CronMontior.run()的计时器

	private CronMonitor mMonitor;

	private Mapx map = new Mapx();

	private static CronManager instance;

	public static final long SCAN_INTERVAL = 1000L;

	private static Object mutex = new Object();

	public static synchronized CronManager getInstance() {
		if (instance == null) {
			synchronized (mutex) {
				if (instance == null) {
					instance = new CronManager();
				}
			}
		}
		return instance;
	}

	public Mapx getManagers() {
		return this.map;
	}

	private CronManager() {
		init();
	}

	public void init() {
		if (!Config.isInstalled) {
			return;
		}
		loadConfig();
		this.mTimer = new Timer(true);
		this.mMonitor = new CronMonitor();
		this.mTimer.schedule(this.mMonitor, System.currentTimeMillis() % 1000L, 1000L);
		logger.info("----{}({}): CronManager Initialized----", Config.getAppCode(), Config.getAppName());
	}

	private void loadConfig() {
		SystemTaskManager gtm = new SystemTaskManager();
		this.map.put(gtm.getID(), gtm);
		ConfigLoader.reload();
		XMLLoader.NodeData[] datas = ConfigLoader.getNodeDataList("*.cron.taskmanager");
		for (int i = 0; (datas != null) && (i < datas.length); i++) {
			String className = datas[i].getAttributes().getString("class");
			try {
				Object o = Class.forName(className).newInstance();
				if ((o instanceof AbstractTaskManager)) {
					AbstractTaskManager ctm = (AbstractTaskManager) o;
					this.map.put(ctm.getID(), ctm);
					Object[] argArr = {ctm.getID(), ctm.getClass(), ctm.getCode()};
					logger.info("ctm.getID():{} ctm:{} code:{}", argArr);
				} else {
					throw new RuntimeException(className + " must extend CronTaskManager");
				}
			} catch (Exception e) {
				throw new RuntimeException("Cron Task class not found:" + className);
			}
		}
		datas = ConfigLoader.getNodeDataList("*.cron.task");
		for (int i = 0; datas != null && i < datas.length; i++) {
			String className = datas[i].getAttributes().getString("class");
			String time = datas[i].getAttributes().getString("time");
			try {
				Object o = Class.forName(className).newInstance();
				if ((o instanceof SystemTask)) {
					SystemTask gt = (SystemTask) Class.forName(className).newInstance();
					if (StringUtil.isNotEmpty(time)) {
						gt.cronExpression = time;
					}
					gt.cronExpression = time;
					gtm.add(gt);
				} else {
					throw new RuntimeException(className + " must extend SystemTask");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				i++;
			}
		}
	}

	public Mapx getTaskTypes() {
		Mapx rmap = new Mapx();
		for (Object ctm1 : map.values()) {
			if ((ctm1 instanceof SystemTaskManager)) {
				continue;
			}
			AbstractTaskManager ctm = (AbstractTaskManager) ctm1;
			rmap.put(ctm.getID(), ctm.getName());
		}
		return rmap;
	}

	public Mapx getConfigEnableTasks(String id) {
		AbstractTaskManager ctm = (AbstractTaskManager) this.map.get(id);
		if (ctm == null) {
			return null;
		}
		return ctm.getConfigEnableTasks();
	}

	public AbstractTaskManager getCronTaskManager(String id) {
		return (AbstractTaskManager) this.map.get(id);
	}

	protected AbstractTaskManager removeCronTaskManager(String id) {
		return (AbstractTaskManager) this.map.remove(id);
	}

	protected void registerCronTaskManager(AbstractTaskManager manager) {
		this.map.put(manager.getID(), manager);
	}

	protected void registerSystemTask(SystemTask task) {
		SystemTaskManager gtm = (SystemTaskManager) this.map.get("SYSTEM");
		if (gtm == null) {
			gtm = new SystemTaskManager();
			this.map.put("SYSTEM", gtm);
		}
		gtm.add(task);
	}

	public void destory() {
		if (this.mMonitor != null) {
			this.mMonitor.destory();
			this.mTimer.cancel();
		}
	}
}
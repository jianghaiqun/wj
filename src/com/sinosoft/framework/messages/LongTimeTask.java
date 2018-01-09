package com.sinosoft.framework.messages;

import com.sinosoft.framework.User;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * 长时间运行的任务需要单独开辟线程，并向前台报告进度
 * 
 */
public abstract class LongTimeTask extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(LongTimeTask.class);

	private static Mapx map = new Mapx();

	private static long IDBase = System.currentTimeMillis();

	private static final int MaxListSize = 1000;

	private long id;

	private ArrayList list = new ArrayList();

	protected int percent;

	protected String currentInfo;

	protected ArrayList Errors = new ArrayList();

	private boolean stopFlag;

	private User.UserData user;

	private String type;// 用来标识同一个来源的任务

	private long stopTime = System.currentTimeMillis() + 1000 * 60 * 24;

	public static LongTimeTask createEmptyInstance() {// 构造一个实例，此实例仅用于避免空指针
		return new LongTimeTask(false) {
			public void execute() {
			}
		};
	}

	public static LongTimeTask getInstanceById(long id) {
		return (LongTimeTask) map.get(new Long(id));
	}

	public static void removeInstanceById(long id) {
		synchronized (LongTimeTask.class) {
			map.remove(new Long(id));
		}
	}

	/**
	 * 中止指定type的任务
	 */
	public static String cancelByType(String type) {
		String message = "没有相关的正在运行的任务!";
		LongTimeTask ltt = getInstanceByType(type);
		if (ltt != null) {
			ltt.stopTask();
			message = "任务己成功中止!";
		}
		return message;
	}

	/**
	 * 根据type查找指定任务，一个type的任务同时只能有一个在运行
	 */
	public static LongTimeTask getInstanceByType(String type) {
		if (StringUtil.isNotEmpty(type)) {
			Object[] ks = map.keyArray();
			Object[] vs = map.valueArray();
			long current = System.currentTimeMillis();
			for (int i = 0; i < map.size(); i++) {
				LongTimeTask ltt = (LongTimeTask) vs[i];
				if (type.equals(ltt.getType())) {
					if (current - ltt.stopTime > 60000) {
						map.remove(ks[i]);
						return null;
					}
					return ltt;
				}
			}
		}
		return null;
	}

	public LongTimeTask() {
		this(true);
	}

	private LongTimeTask(boolean flag) {// flag为false时构造一个仅用来避免空指针的任务
		if (flag) {
			this.setName("LongTimeTask Thread");
			synchronized (LongTimeTask.class) {
				id = IDBase++;
				map.put(new Long(id), this);
				clearStopedTask();
			}
		}
	}

	private void clearStopedTask() {
		synchronized (LongTimeTask.class) {
			long current = System.currentTimeMillis();
			Object[] ks = map.keyArray();
			Object[] vs = map.valueArray();
			for (int i = 0; i < map.size(); i++) {
				LongTimeTask ltt = (LongTimeTask) vs[i];
				if (current - ltt.stopTime > 60000) {
					map.remove(ks[i]);
				}
			}
		}
	}

	public long getTaskID() {
		return id;
	}

	public void info(String message) {
		logger.info(message);
		list.add(message);
		if (list.size() > MaxListSize) {
			list.remove(0);
		}
	}

	public String[] getMessages() {
		String[] arr = new String[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (String) list.get(i);
		}
		list.clear();
		return arr;
	}

	public void run() {
		if (StringUtil.isNotEmpty(this.type)) {
			LongTimeTask ltt = getInstanceByType(this.type);
			if (ltt != null && ltt != this) {
				return;
			}
		}
		try {
			User.setCurrent(user);
			execute();
		} catch (StopThreadException ie) {
			this.interrupt();
		} finally {
			this.stopTime = System.currentTimeMillis();
		}
	}

	public abstract void execute();

	public boolean checkStop() {
		return stopFlag;
	}

	public void stopTask() {
		clearStopedTask();
		stopFlag = true;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public void setCurrentInfo(String currentInfo) {
		this.currentInfo = currentInfo;
		/* ${_ZVING_LICENSE_CODE_} */
	}

	public String getCurrentInfo() {
		return currentInfo;
	}

	public void setUser(User.UserData user) {
		this.user = user;
	}

	public void addError(String error) {
		Errors.add(error);
	}

	public void addError(String[] errors) {
		for (int i = 0; i < errors.length; i++) {
			Errors.add(errors[i]);
		}
	}

	public String getAllErrors() {
		if (Errors.size() == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("共有" + Errors.size() + "个错误:<br>");
		for (int i = 0; i < Errors.size(); i++) {
			sb.append(i + 1);
			sb.append(": ");
			sb.append(Errors.get(i));
			sb.append("<br>");
		}
		return sb.toString();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

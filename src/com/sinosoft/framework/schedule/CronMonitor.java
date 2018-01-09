package com.sinosoft.framework.schedule;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class CronMonitor extends TimerTask {
	private static final Logger logger = LoggerFactory.getLogger(CronMonitor.class);
	private boolean isRunning = false;

	private Timer mTimer = new Timer();

	private static Pattern P1 = Pattern.compile("\\d+", 32);

	private static Pattern P2 = Pattern.compile("\\d+\\-\\d+", 32);

	private static Pattern P3 = Pattern.compile("(((\\d+\\-\\d+)|\\d+)(,|$))+", 32);

	private static Pattern P4 = Pattern.compile("((\\d+\\-\\d+)|\\*)\\/\\d+", 32);

	public void run() {
		if (!this.isRunning) {
			User.UserData u = new User.UserData();
			u.setBranchInnerCode("0000");
			u.setUserName("CronTask");
			u.setLogin(true);
			User.setCurrent(u);
			runMain();
		}
	}

	private synchronized void runMain() {
		if (!this.isRunning) {
			this.isRunning = true;
			final Date current = new Date();
			try {
				Mapx map = CronManager.getInstance().getManagers();
				Object[] obj = map.valueArray();
				for (Object ob : obj) {
					final AbstractTaskManager tm = (AbstractTaskManager) ob;
					new Thread() {
						public void run() {
							Mapx tmap = tm.getUsableTasks();
							
							for (Object id : tmap.keySet())
								try {
//									String exp = tm.getTaskCronExpression(id + "");
//									if (StringUtil.isEmpty(exp) || "null".equalsIgnoreCase(exp))
//										exp = "1 * * * * *";
									if (CronMonitor.isOnTime(current, tm.getTaskCronExpression(id + ""))
											&& Config.complateInitTime != 0
											&& Config.complateInitTime + 5 * 60 * 1000 < System.currentTimeMillis()) {
										if (tm.isRunning(id + ""))
											logger.warn("Cron Task is on time ,but its last execution isn't finished!");
										else
											new childClass(tm,id).start();
									}
								} catch (Exception e) {
									logger.error(e.getMessage(), e);
								}
						}
					}.run();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				this.isRunning = false;
			}
		}
	}
	public class childClass  extends Thread{
		
		AbstractTaskManager tm;
		Object id;
		public childClass(AbstractTaskManager cTm,Object cId){
			id = cId;
			tm = cTm;
		}
		public childClass(){
			
		}
		@Override
		public void run() {
			
			try {
				long start = System.currentTimeMillis();
				Object[] argArr = {tm.getName(), id, start};
				logger.info("*************cms后台定时任务开始，任务名：{}--------任务id={}----开始时间：{}", argArr);
				tm.execute(id + "");
				long end = System.currentTimeMillis();
				argArr = new Object[]{tm.getName(), id, end-start};
				logger.info("*************cms后台定时任务结束，任务名：{}--------任务id={}----开始时间：{}", argArr);
			} catch (Exception e) {
				logger.error("定时任务失败：{}", e.getMessage());
			}
			
		}
		
	};
	public static boolean isOnTime(Date date, String cronExpr) {
		if (StringUtil.isEmpty(cronExpr))
			return false;
		try {
			Date d = getNextRunTime(date, cronExpr);
			long t1 = d.getTime();
			long t2 = date.getTime();
			if ((t1 / 1000L == t2 / 1000L) && (Math.abs(t1 - t2) < 1000L))
				return true;
		} catch (CronExpressionException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	public static int[] getSuitNumbers(String exp, int min, int max) throws CronExpressionException {
		ArrayList list = new ArrayList();
		if (P1.matcher(exp).matches()) {
			int v = Integer.parseInt(exp);
			v = v > max ? max : v;
			v = v < min ? min : v;
			list.add(new Integer(v));
		} else if (P2.matcher(exp).matches()) {
			String[] arr = exp.split("\\-");
			int[] is = { Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) };
			is[0] = (is[0] > max ? max : is[0]);
			is[1] = (is[1] > max ? max : is[1]);
			is[0] = (is[0] < min ? min : is[0]);
			is[1] = (is[1] < min ? min : is[1]);
			if (is[0] > is[1]) {
				for (int j = is[0]; j <= max; j++) {
					list.add(new Integer(j));
				}
				for (int j = min; j <= is[1]; j++)
					list.add(new Integer(j));
			} else {
				for (int j = is[0]; j <= is[1]; j++)
					list.add(new Integer(j));
			}
		} else if (P3.matcher(exp).matches()) {
			String[] arr = exp.split(",");
			for (int i = 0; i < arr.length; i++) {
				String str = arr[i];
				if (str.indexOf('-') > 0) {
					String[] arr2 = str.split("\\-");
					int[] tmp = { Integer.parseInt(arr2[0]), Integer.parseInt(arr2[1]) };
					tmp[0] = (tmp[0] > max ? max : tmp[0]);
					tmp[1] = (tmp[1] > max ? max : tmp[1]);
					tmp[0] = (tmp[0] < min ? min : tmp[0]);
					tmp[1] = (tmp[1] < min ? min : tmp[1]);
					if (tmp[0] > tmp[1]) {
						for (int j = tmp[0]; j <= max; j++) {
							list.add(new Integer(j));
						}
						for (int j = min; j <= tmp[1]; j++)
							list.add(new Integer(j));
					} else {
						for (int j = tmp[0]; j <= tmp[1]; j++)
							list.add(new Integer(j));
					}
				} else {
					list.add(new Integer(Integer.parseInt(str)));
				}
			}
		} else if (P4.matcher(exp).matches()) {
			String[] arr = exp.split("\\/");
			int step = Integer.parseInt(arr[1]);
			int[] is = new int[2];
			if (arr[0].equals("*")) {
				is[0] = min;
				is[1] = max;
			} else {
				arr = arr[0].split("\\-");
				is = new int[] { Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) };
				is[0] = (is[0] > max ? max : is[0]);
				is[1] = (is[1] > max ? max : is[1]);
				is[0] = (is[0] < min ? min : is[0]);
				is[1] = (is[1] < min ? min : is[1]);
			}
			int cm = is[1];
			int len = max - min + 1;
			if (is[0] > is[1]) {
				cm = is[1] + len;
			}
			for (int i = is[0]; i <= cm; i += step)
				list.add(new Integer(i > max ? i - len : i));
		} else if (exp.equals("*")) {
			for (int i = min; i <= max; i++)
				list.add(new Integer(i));
		} else {
			throw new CronExpressionException("Invalid cron expression:" + exp);
		}
		int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = ((Integer) list.get(i)).intValue();
		}
		Arrays.sort(arr);
		return arr;
	}

	public static Date getNextRunTime(Date lastDate, String cronExpression) throws CronExpressionException {
		if (StringUtil.isEmpty(cronExpression)) {
			throw new CronExpressionException("Invalid cron expression:" + cronExpression);
		}
		String[] expArr = cronExpression.split("\\s");
		if (expArr.length < 5)
			throw new CronExpressionException("Invalid cron expression:" + cronExpression);
		if (expArr.length == 5) {
			expArr = (String[]) ArrayUtils.add(expArr, 0, "0");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(lastDate);

		int month = c.get(2) + 1;
		int[] ms = getSuitNumbers(expArr[4], 1, 12);
		int mi = -1;
		boolean carryFlag = false;
		boolean flag = false;
		for (int i = 0; i < ms.length; i++) {
			if (ms[i] == month) {
				mi = i;
				flag = true;
				break;
			}
			if (ms[i] > month) {
				c.set(2, ms[i] - 1);
				carryFlag = true;
				mi = i;
				flag = true;
				break;
			}
		}
		if (!flag) {
			mi = 0;
			c.set(2, ms[mi] - 1);
			c.add(1, 1);
			carryFlag = true;
		}

		int day = c.get(5);
		int[] ds = getSuitNumbers(expArr[3], 1, c.getActualMaximum(5));
		int di = -1;
		if (carryFlag) {
			di = 0;
			c.set(5, ds[0]);
		} else {
			flag = false;
			for (int i = 0; i < ds.length; i++) {
				if (ds[i] == day) {
					di = i;
					flag = true;
					break;
				}
				if (ds[i] > day) {
					c.set(5, ds[i]);
					carryFlag = true;
					di = i;
					flag = true;
					break;
				}
			}
			if (!flag) {
				c.set(5, ds[0]);
				if (mi != ms.length - 1) {
					mi++;
				} else {
					mi = 0;
					c.add(1, 1);
				}
				c.set(2, ms[mi] - 1);
				carryFlag = true;
				di = 0;
			}

		}

		int hour = c.get(11);
		int[] hs = getSuitNumbers(expArr[2], 0, 23);
		int hi = -1;
		if (carryFlag) {
			hi = 0;
			c.set(11, hs[0]);
		} else {
			flag = false;
			for (int i = 0; i < hs.length; i++) {
				if (hs[i] == hour) {
					hi = i;
					flag = true;
					break;
				}
				if (hs[i] > hour) {
					c.set(11, hs[i]);
					carryFlag = true;
					hi = i;
					flag = true;
					break;
				}
			}
			if (!flag) {
				c.set(11, hs[0]);
				if (di != ds.length - 1) {
					di++;
				} else {
					di = 0;
					if (mi != ms.length - 1) {
						mi++;
					} else {
						mi = 0;
						c.add(1, 1);
					}
					c.set(2, ms[mi] - 1);
				}
				c.set(5, ds[di]);
				carryFlag = true;
				hi = 0;
			}

		}

		int minute = c.get(12);
		int fi = -1;
		int[] fs = getSuitNumbers(expArr[1], 0, 59);
		if (carryFlag) {
			fi = 0;
			c.set(12, fs[0]);
		} else {
			flag = false;
			for (int i = 0; i < fs.length; i++) {
				if (fs[i] == minute) {
					flag = true;
					fi = i;
					break;
				}
				if (fs[i] > minute) {
					c.set(12, fs[i]);
					fi = i;
					carryFlag = true;
					flag = true;
					break;
				}
			}
			if (!flag) {
				c.set(12, fs[0]);
				fi = 0;
				if (hi != hs.length - 1) {
					hi++;
				} else {
					if (di != ds.length - 1) {
						di++;
					} else {
						di = 0;
						if (mi != ms.length - 1) {
							mi++;
						} else {
							mi = 0;
							c.add(1, 1);
						}
						c.set(2, ms[mi] - 1);
					}
					hi = 0;
				}
				c.set(10, hs[hi]);
				carryFlag = true;
			}

		}

		int second = c.get(13);
		int[] ss = getSuitNumbers(expArr[0], 0, 59);
		if (carryFlag) {
			c.set(13, ss[0]);
		} else {
			flag = false;
			for (int i = 0; i < ss.length; i++) {
				if (ss[i] == second) {
					flag = true;
					break;
				}
				if (ss[i] > second) {
					c.set(13, ss[i]);
					carryFlag = true;
					flag = true;
					break;
				}
			}
			if (!flag) {
				c.set(13, ss[0]);
				if (fi != fs.length - 1) {
					fi++;
				} else if (hi != hs.length - 1) {
					hi++;
				} else {
					if (di != ds.length - 1) {
						di++;
					} else {
						di = 0;
						if (mi != ms.length - 1) {
							mi++;
						} else {
							mi = 0;
							c.add(1, 1);
						}
						c.set(2, ms[mi] - 1);
					}
					hi = 0;
				}

				c.set(12, fs[fi]);
				carryFlag = true;
			}

		}

		int week = c.get(7) - 1;
		if (week == 0) {
			week = 7;
		}
		int[] ws = getSuitNumbers(expArr[5], 1, 7);
		flag = false;
		for (int i = 0; i < ws.length; i++) {
			if (ws[i] == week) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			c.add(5, 1);
			return getNextRunTime(c.getTime(), cronExpression);
		}
		return new Date(c.getTimeInMillis() - c.getTimeInMillis() % 1000L);
	}

	public static Date getNextRunTime(String cronExpression) throws CronExpressionException {
		return getNextRunTime(new Date(), cronExpression);
	}

	public void destory() {
		cancel();
		this.mTimer.cancel();
	}
	
	public static Date getPreviousRunTime(String cronExpression) throws CronExpressionException {
		return getPreviousRunTime(new Date(), cronExpression);
	}
	
	private static Date getPreviousRunTime(Date lastDate, String cronExpression) throws CronExpressionException {

		String[] expArr = cronExpression.split("\\s");
		if (expArr.length < 5)
			throw new CronExpressionException("Invalid cron expression:" + cronExpression);

		Date nextRunTime = getNextRunTime(lastDate, cronExpression);

		Calendar c = Calendar.getInstance();
		c.setTime(nextRunTime);

		if (expArr[0].indexOf("/") > 0) {
			c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - Integer.parseInt(expArr[0].substring(expArr[0].indexOf("/") + 1)));

		} else if (expArr[1].indexOf("/") > 0) {
			c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(expArr[1].substring(expArr[1].indexOf("/") + 1)));

		} else if (expArr[2].indexOf("/") > 0) {
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - Integer.parseInt(expArr[2].substring(expArr[2].indexOf("/") + 1)));

		} else if (expArr[3].indexOf("/") > 0) {
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) - Integer.parseInt(expArr[3].substring(expArr[3].indexOf("/") + 1)));

		}
		return new Date(c.getTimeInMillis() - c.getTimeInMillis() % 1000L);
	}
}
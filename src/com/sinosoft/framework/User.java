package com.sinosoft.framework;

import com.sinosoft.framework.clustering.Clustering;
import com.sinosoft.framework.utility.Mapx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 用户数据全局访问类，一个线程内的所有代码都可以直接访问用户数据<br>
 * 本类替代了HttpSession的作用，便利于单元测试。
 * 
 */
public class User {
	private static final Logger logger = LoggerFactory.getLogger(User.class);
	/**
	 * 用户状态：在线
	 */
	public static final String ONLINE = "online";

	/**
	 * 用户状态：下线
	 */
	public static final String OFFLINE = "offline";

	/**
	 * 用户状态：忙碌
	 */
	public static final String BUSY = "busy";

	/**
	 * 用户状态：离开
	 */
	public static final String AWAY = "away";

	/**
	 * 用户状态：隐身
	 */
	public static final String HIDDEN = "hidden";

	public static final Mapx USER_STATUS_MAP = new Mapx();

	static {
		USER_STATUS_MAP.put(ONLINE, "在线");
		USER_STATUS_MAP.put(OFFLINE, "下线");
		USER_STATUS_MAP.put(BUSY, "忙碌");
		USER_STATUS_MAP.put(AWAY, "离开");
		USER_STATUS_MAP.put(HIDDEN, "隐身");
	}

	private static ThreadLocal UserLocal = new ThreadLocal();

	/**
	 * 获取当前用户名
	 * 
	 * @return
	 */
	public static String getUserName() {
		return getCurrent().UserName;
	}

	/**
	 * 设置当前用户名
	 * 
	 * @param UserName
	 */
	public static void setUserName(String UserName) {
		getCurrent().setUserName(UserName);
	}

	/**
	 * 获取当前用户的真实名称
	 * 
	 * @return
	 */
	public static String getRealName() {
		return getCurrent().RealName;
	}

	/**
	 * 设置当前用户的真实名称
	 * 
	 * @param RealName
	 */
	public static void setRealName(String RealName) {
		getCurrent().RealName = RealName;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * 获取当前用户的分支机构内部编码
	 * 
	 * @return
	 */
	public static String getBranchInnerCode() {
		return getCurrent().BranchInnerCode;
	}

	/**
	 * 设置当前用户的分支机构内部编码
	 * 
	 * @param BranchInnerCode
	 */
	public static void setBranchInnerCode(String BranchInnerCode) {
		getCurrent().BranchInnerCode = BranchInnerCode;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * 获取当前用户的用户类型
	 * 
	 * @return
	 */
	public static String getType() {
		return getCurrent().Type;
	}

	/**
	 *设置当前用户的用户类型
	 * 
	 * @param Type
	 */
	public static void setType(String Type) {
		getCurrent().Type = Type;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * 获取当前用户的用户状态
	 * 
	 * @return
	 */
	public static String getStatus() {
		return getCurrent().Status;
	}

	/**
	 * 设置当前用户的用户状态
	 * 
	 * @param Status
	 */
	public static void setStatus(String Status) {
		getCurrent().Status = Status;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * 获取User对象中的名值对个数
	 * 
	 * @return
	 */
	public static int getValueCount() {
		return getCurrent().Map.size();
	}

	/**
	 * 按key获取指定数据项
	 * 
	 * @param key
	 * @return
	 */
	public static Object getValue(Object key) {
		return getCurrent().Map.get(key);
	}

	/**
	 * 获取当前用户的名值对数据
	 * 
	 * @return
	 */
	public static Mapx getValues() {
		return getCurrent().Map;
	}

	/**
	 * 设置当前用户指定数据项
	 * 
	 * @param key
	 * @param value
	 */
	public static void setValue(Object key, Object value) {
		Mapx map = getCurrent().Map;
		synchronized (map) {
			map.put(key, value);
		}
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * 当前用户是否己登录
	 * 
	 * @return
	 */
	public static boolean isLogin() {
		return getCurrent().isLogin;
	}

	/**
	 * 设置当前用户的登录状态
	 * 
	 * @param isLogin
	 */
	public static void setLogin(boolean isLogin) {
		if (isLogin) {
			if (!getCurrent().isLogin) {// 开发模式下未登录之前Session中可能就已经有User的实例了
				Config.LoginUserCount++;
			}
		} else {
			if (getCurrent().isLogin) {
				Config.LoginUserCount--;
			}
		}
		getCurrent().isLogin = isLogin;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * 设置当前用户对象
	 * 
	 * @param user
	 */
	public static void setCurrent(UserData user) {
		UserLocal.set(user);
		if (Config.isDebugMode()) {
			cacheUser(user);
		}
	}

	/**
	 * 获取当前用户对象
	 * 
	 * @return
	 */
	public static UserData getCurrent() {
		return (UserData) UserLocal.get();
	}

	/**
	 * 缓存当前用户对象到文件系统
	 * 
	 * @param u
	 */
	protected static void cacheUser(UserData u) {
		if (getCurrent() != u || getCurrent() == null) {
			return;// 当前UserData对象未被置为Current
		}
		if (Clustering.isClustering()) {// 首先缓存到集群中
			Clustering.cacheUser(u);
		}
		if (Config.isDebugMode()) {
			try {
				FileOutputStream f = new FileOutputStream(Config.getContextRealPath() + "WEB-INF/cache/"
						+ u.getSessionID());
				ObjectOutputStream s = new ObjectOutputStream(f);
				s.writeObject(u);
				s.flush();
				s.close();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 根据会话ID从文件系统中返回己缓存的用户对象，如果没有则返回null
	 * 
	 * @param sessionID
	 * @return
	 */
	protected static UserData getCachedUser(String sessionID) {
		try {
			File in = new File(Config.getContextRealPath() + "WEB-INF/cache/" + sessionID);
			if (in.exists()) {
				ObjectInputStream s = new ObjectInputStream(new FileInputStream(in));
				Object o = s.readObject();
				if (User.class.isInstance(o)) {
					s.close();
					in.delete();
					UserData u = (UserData) o;
					if (u.isLogin()) {
						Config.LoginUserCount++;
					}
					return u;
				}
				s.close();
			}
		} catch (Exception e) {
			logger.error("未取到缓存的User对象" + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 销毁当前用户对象
	 */
	public static void destory() {
		User.setCurrent(new UserData());
	}

	/**
	 * 返回当前用户的会话ID
	 * 
	 * @return
	 */
	public static String getSessionID() {
		return getCurrent().SessionID;
	}

	/**
	 * 设置当前用户的会话ID
	 * 
	 * @param sessionID
	 */
	protected static void setSessionID(String sessionID) {
		getCurrent().SessionID = sessionID;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * 当前用户是否是会员用户（仅是会员不能使用后台）
	 */
	public static boolean isMember() {
		return getCurrent().isMember;
	}

	/**
	 * 设置当前用户是否是会员
	 * 
	 * @param isMember
	 */
	public static void setMember(boolean isMember) {
		getCurrent().isMember = isMember;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * 当前用户是否是后台管理人员（可以使用后台）
	 * 
	 * @return
	 */
	public static boolean isManager() {
		return getCurrent().isManager;
	}

	/**
	 * 设置当前用户是否是后台管理人员
	 * 
	 * @param isManager
	 */
	public static void setManager(boolean isManager) {
		getCurrent().isManager = isManager;
		if (Config.isDebugMode()) {
			cacheUser(getCurrent());
		}
	}

	/**
	 * 保存用户数据
	 * 
	 */
	public static class UserData implements Serializable {
		private static final long serialVersionUID = 1L;

		/**
		 * 用户类型
		 */
		private String Type;

		/**
		 * 用户状态
		 */
		private String Status;

		/**
		 * 用户名
		 */
		private String UserName;

		/**
		 * 用户真实姓名
		 */
		private String RealName;

		/**
		 * 所属分支机构
		 */
		private String BranchInnerCode;

		/**
		 * 是否己登录
		 */
		private boolean isLogin = false;

		/**
		 * 是否是会员
		 */
		private boolean isMember = true;// 默认是会员

		/**
		 * 是否是管理人员，即有权登录后台的人员，可以调用继承Page的类
		 */
		private boolean isManager = false;

		/**
		 * 会话ID
		 */
		private String SessionID;

		/**
		 * 保存名值对形式的用户数据
		 */
		private Mapx Map = new Mapx();

		public String getType() {
			return Type;
		}

		public void setType(String type) {
			Type = type;
			cacheUser(getCurrent());
		}

		public String getStatus() {
			return Status;
		}

		public void setStatus(String status) {
			Status = status;
			cacheUser(getCurrent());
		}

		public String getUserName() {
			return UserName;
		}

		public void setUserName(String userName) {
			UserName = userName;
			cacheUser(getCurrent());
		}

		public String getRealName() {
			return RealName;
		}

		public void setRealName(String realName) {
			RealName = realName;
			cacheUser(getCurrent());
		}

		public String getBranchInnerCode() {
			return BranchInnerCode;
		}

		public void setBranchInnerCode(String branchInnerCode) {
			BranchInnerCode = branchInnerCode;
			cacheUser(getCurrent());
		}

		public boolean isLogin() {
			return isLogin;
		}

		public void setLogin(boolean isLogin) {
			this.isLogin = isLogin;
			cacheUser(getCurrent());
		}

		public boolean isMember() {
			return isMember;
		}

		public void setMember(boolean isMember) {
			this.isMember = isMember;
			cacheUser(getCurrent());
		}

		public boolean isManager() {
			return isManager;
		}

		public void setManager(boolean isManager) {
			this.isManager = isManager;
			cacheUser(getCurrent());
		}

		public String getSessionID() {
			return SessionID;
		}

		public void setSessionID(String sessionID) {
			SessionID = sessionID;
			cacheUser(getCurrent());
		}

		public Mapx getMap() {
			return Map;
		}

		public void setMap(Mapx map) {
			Map = map;
			cacheUser(getCurrent());
		}
	}
}

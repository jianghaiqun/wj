package com.sinosoft.framework;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.sinosoft.framework.User.UserData;
import com.sinosoft.framework.extend.AfterSessionCreateAction;
import com.sinosoft.framework.extend.BeforeSessionDestroyAction;
import com.sinosoft.framework.extend.ExtendManager;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;

/**
 * Session监听器，监听用户的增减情况，并提供用户相关的数据。
 * 
 */
public class SessionListener implements HttpSessionListener {
	private static Mapx map = new Mapx();
	private static Object mutex = new Object();

	/**
	 * 会话创建时执行本方法
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent arg0) {
		Config.OnlineUserCount++;
		synchronized (mutex) {
			HttpSession hs = arg0.getSession();
			map.put(hs.getId(), hs);
			if (ExtendManager.hasAction(AfterSessionCreateAction.Type)) {
				ExtendManager.executeAll(AfterSessionCreateAction.Type, new Object[] { hs });
			}
		}
	}

	/**
	 * 会话失效时执行本方法
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		Config.OnlineUserCount--;
		UserData u = (UserData) arg0.getSession().getAttribute(Constant.UserAttrName);
		if (u != null) {
			if (u.isLogin()) {
				Config.LoginUserCount--;
			}
			if (Config.isDebugMode()) {
				FileUtil.delete(Config.getContextRealPath() + "WEB-INF/cache/" + u.getSessionID());
			}
		}
		if (ExtendManager.hasAction(BeforeSessionDestroyAction.Type)) {
			ExtendManager.executeAll(BeforeSessionDestroyAction.Type, new Object[] { arg0.getSession() });
		}
		synchronized (mutex) {
			map.remove(arg0.getSession().getId());
		}
	}

	/**
	 * 清除所有的会话相关数据
	 */
	public static void clear() {
		synchronized (mutex) {
			map.clear();
		}
	}

	/**
	 * 踢出除自己以外的其他所有用户
	 */
	public static void forceExit() {
		synchronized (mutex) {
			Object[] ks = map.keyArray();
			Object[] vs = map.valueArray();
			HttpSession session = null;
			for (int i = 0; i < map.size(); i++) {
				if (ks[i].equals(User.getSessionID())) {
					continue;
				}
				session = (HttpSession) vs[i];
				session.invalidate();
			}
		}
	}

	/**
	 * 踢除指定SessionID的用户
	 * 
	 * @param sid
	 */
	public static void forceExit(String sid) {
		synchronized (mutex) {
			HttpSession session = (HttpSession) map.get(sid);
			session.invalidate();
		}
	}

	/**
	 * 获取所有状态的用户
	 * 
	 * @return
	 */
	public static UserData[] getUsers() {
		Object[] vs = map.keyArray();
		UserData[] arr = new UserData[vs.length];
		HttpSession session = null;
		for (int i = 0; i < vs.length; i++) {
			session = (HttpSession) map.get(vs[i]);
			UserData u = (UserData) session.getAttribute(Constant.UserAttrName);
			arr[i] = u;
		}
		return arr;
	}

	/**
	 * 获取指定状态的用户
	 * 
	 * @param status
	 *            用户状态
	 * @return
	 */
	public static UserData[] getUsers(String status) {
		Object[] vs = map.keyArray();
		ArrayList arr = new ArrayList(vs.length);
		HttpSession session = null;
		for (int i = 0; i < vs.length; i++) {
			session = (HttpSession) map.get(vs[i]);
			UserData u = (UserData) session.getAttribute(Constant.UserAttrName);
			if (status.equalsIgnoreCase(u.getStatus())) {
				arr.add(u);
			}
		}
		return (UserData[]) arr.toArray(new UserData[arr.size()]);
	}

	/**
	 * 获取指定状态的用户名列表
	 * 
	 * @param status
	 *            用户状态
	 * @return
	 */
	public static List getUserNames(String status) {
		UserData[] arr = getUsers(status);
		List userNameArr = new ArrayList(arr.length);
		for (int i = 0; i < arr.length; i++) {
			userNameArr.add(arr[i].getUserName());
		}
		return userNameArr;
	}

	/**
	 * 获取指定用户名的User对象
	 * 
	 * @param userName
	 *            用户名
	 * @return
	 */
	public static UserData getUser(String userName) {
		UserData[] users = getUsers();
		for (int i = 0; i < users.length; i++) {
			if (userName.equals(users[i].getUserName())) {
				return users[i];
			}
		}
		return null;
	}

}

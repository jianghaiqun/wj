package com.sinosoft.platform.pub;

import java.util.ArrayList;
import java.util.Date;

import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.cache.CacheProvider;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Executor;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringFormat;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCMessageSchema;
import com.sinosoft.schema.ZCMessageSet;

/**
 * 短消息工具类，用于解决高并发时短消息频繁查询时性能问题
 * 
 */
public class MessageCache extends CacheProvider {
	public String getID() {
		return "Message";
	}

	public String getName() {
		return "系统消息缓存";
	}

	public static final String ProviderName = "Message";

	/**
	 * 增加一条短消息，加入消息队列，默认发送人为当前用户
	 */
	public static boolean addMessage(String subject, String content, String toUser) {
		Transaction tran = new Transaction();
		addMessage(tran, subject, content, new String[] { toUser }, User.getUserName(), true);
		return tran.commit();
	}

	public static boolean addMessage(Transaction tran, String subject, String content, String toUser) {
		return addMessage(tran, subject, content, new String[] { toUser }, User.getUserName(), true);
	}

	public static boolean addMessage(Transaction tran, String subject, String content, String toUser, String formUser) {
		return addMessage(tran, subject, content, new String[] { toUser }, formUser, true);
	}

	/**
	 * 增加一条短消息，加入消息队列，指定发送人。
	 */
	public static boolean addMessage(String subject, String content, String[] userList, String fromUser) {
		Transaction tran = new Transaction();
		addMessage(tran, subject, content, userList, fromUser, true);
		return tran.commit();
	}

	public static boolean addMessage(Transaction tran, String subject, String content, String[] userList, String fromUser, final boolean popFlag) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < userList.length; i++) {
			if (!userList[i].equals(fromUser) && StringUtil.isNotEmpty(userList[i])) {
				ZCMessageSchema message = new ZCMessageSchema();
				message.setID(NoUtil.getMaxID("MessageID"));
				message.setSubject(subject);
				message.setBox("outbox");
				message.setContent(content);
				message.setFromUser(fromUser);
				message.setToUser(userList[i]);
				message.setReadFlag(0);
				message.setPopFlag(popFlag ? 0 : 1);
				message.setAddTime(new Date());
				PopMessage pm = new PopMessage(message.getID(), getHtmlMessage(message.getID(), message.getSubject(), content), System.currentTimeMillis(), userList[i]);
				list.add(pm);
				tran.add(message, Transaction.INSERT);
			}
		}
		tran.addExecutor(new Executor(list) {
			public boolean execute() {
				ArrayList list = (ArrayList) param;
				for (int i = 0; i < list.size(); i++) {
					PopMessage pm = (PopMessage) list.get(i);
					PopMessageList pml = (PopMessageList) CacheManager.get(ProviderName, "LastMessage", pm.ToUser);
					synchronized (pml) {
						if (popFlag) {
							pml.list.add(pm);
						}
					}
					String count = (String) CacheManager.get(ProviderName, "Count", pm.ToUser);
					CacheManager.set(ProviderName, "Count", pm.ToUser, Integer.parseInt(count) + 1);
				}
				return true;
			}
		});
		return true;
	}

	public static void removeIDs(ZCMessageSet set) {
		PopMessageList pml = (PopMessageList) CacheManager.get(ProviderName, "LastMessage", User.getUserName());
		int NoReadCount = 0;
		synchronized (pml) {
			for (int j = 0; j < set.size(); j++) {
				for (int i = 0; i < pml.list.size(); i++) {
					PopMessage pm = (PopMessage) pml.list.get(i);
					if (set.get(j).getID() == pm.ID) {
						pml.list.remove(pm);
					}
				}
				if (set.get(j).getReadFlag() == 0) {
					NoReadCount++;
				}

			}
		}
		String count = (String) CacheManager.get(ProviderName, "Count", User.getUserName());
		CacheManager.set(ProviderName, "Count", User.getUserName(), Integer.parseInt(count) - NoReadCount);
	}

	public static int getNoReadCount() {
		String count = (String) CacheManager.get(ProviderName, "Count", User.getUserName());
		if (StringUtil.isEmpty(count)) {
			return 0;
		} else {
			int c = Integer.parseInt(count);
			if (c < 0) {
				CacheManager.set(ProviderName, "Count", User.getUserName(), "0");
				return 0;
			} else {
				return c;
			}
		}
	}

	/**
	 * 获取当前用户第一条弹出消息
	 */
	public static String getFirstPopMessage() {
		return getFirstPopMessage(User.getUserName());
	}

	/**
	 * 获取指定用户第一条弹出消息
	 */
	public static String getFirstPopMessage(String userName) {
		PopMessageList pml = (PopMessageList) CacheManager.get(ProviderName, "LastMessage", userName);
		if (pml == null) {
			return "";
		}
		return pml.getLastMessage();
	}

	public String getProviderName() {
		return ProviderName;
	}

	public void onKeyNotFound(String type, Object key) {
		if (type.equals("Count")) {// 查找用户未读消息条数
			QueryBuilder qb = new QueryBuilder("select count(1) from ZCMessage where ReadFlag=0 and ToUser=?", key);
			int count = qb.executeInt();
			CacheManager.set(ProviderName, type, key, count);
		}
		if (type.equals("LastMessage")) {// 查找用户最新的未弹出的消息
			PopMessageList pml = new PopMessageList(key.toString());
			CacheManager.set(ProviderName, type, key, pml);
		}
	}

	public void onTypeNotFound(String type) {
		// 延迟加载
		CacheManager.setMapx(ProviderName, type, new Mapx());
	}

	/**
	 * 增加此类的原因是：<br>
	 * 1，有多台机器用同一个用户登录时，所有机器都可以收到弹出消息<br>
	 * 2，在服务器较频繁重启的情况下，也能确保所有未弹出过的消息都有机会弹出
	 */
	static class PopMessageList {
		private ArrayList list = new ArrayList();
		public static final int Interval = 10000;// 每隔10秒前台取一次消息

		public PopMessageList(String userName) {
			long current = System.currentTimeMillis();

			// 2010-11-03 弹出短消息显示用户真实姓名
			// ZDUserSchema theuser = new ZDUserSchema().query(new
			// QueryBuilder("where username=?", userName)).get(0);
			// String realName = theuser.getRealName();
			// System.out.println("+++++++++++++++++++++++");
			// System.out.println(realName);
			// System.out.println("+++++++++++++++++++++++");
			// ==================

			QueryBuilder qb = new QueryBuilder("select * from ZCMessage where ReadFlag=0 and PopFlag=0 and ToUser=? order by AddTime asc", userName);
			DataTable dt = qb.executeDataTable();

			for (int i = 0; i < dt.getRowCount(); i++) {
				String html = MessageCache.getHtmlMessage(dt.getLong(i, "ID"), dt.getString(i, "Subject"), dt.getString(i, "Content"));
				list.add(new PopMessage(dt.getLong(i, "ID"), html, current, userName));
			}
		}

		public synchronized String getLastMessage() {
			if (list.size() == 0) {
				return null;
			} else {
				for (int i = list.size() - 1; i >= 0; i--) {
					PopMessage pm = (PopMessage) list.get(i);// 最后一个
					if (System.currentTimeMillis() - pm.LastTime > 30 * 60 * 1000) {// 30分钟后清除掉
						list.remove(pm);
					}
					if (pm.SessionIDMap.containsKey(User.getSessionID())) {
						continue;
					}
					pm.SessionIDMap.put(User.getSessionID(), "1");
					if (!pm.PopedFlag) {// 第一次弹出，以后可能其他客户端还会来取
						QueryBuilder qb = new QueryBuilder("update ZCMessage set PopFlag=1 where ID=?", pm.ID);
						qb.executeNoQuery();
						pm.PopedFlag = true;
					}
					return pm.Message;
				}
			}
			return null;
		}
	}

	public static String getHtmlMessage(long id, String subject, String content) {
		StringFormat sf = new StringFormat("您有新的短消息：<hr>?<hr>?<br><br>?");
		sf.add(subject);
		sf.add(content);
		sf.add("<p align='center' width='100%'><input type='button' class='inputButton' value='我知道了'" + " onclick=\"Server.getOneValue('com.sinosoft.cms.document.Message.updateReadFlag'," + id
				+ ",function(){MsgPop.closeSelf();});\"></p>");
		return sf.toString();
	}

	static class PopMessage {
		public long ID;
		public String Message;
		public long LastTime;
		public boolean PopedFlag = false;
		public String ToUser;
		public Mapx SessionIDMap = new Mapx();

		public PopMessage(long id, String message, long lastTime, String toUser) {
			ID = id;
			Message = message;
			LastTime = lastTime;
			ToUser = toUser;
		}
	}
}

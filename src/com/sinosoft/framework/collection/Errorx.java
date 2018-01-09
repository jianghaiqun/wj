package com.sinosoft.framework.collection;

import java.util.ArrayList;

/**
 * 本次后中调用中的所有消息
 */
public class Errorx {
	protected ArrayList list = new ArrayList();

	protected boolean ErrorFlag = false;

	protected boolean ErrorDealedFlag = true;

	protected static ThreadLocal ErrorXLocal = new ThreadLocal();

	/**
	 * 增加一条普通消息
	 * 
	 * @param message
	 */
	public static void addMessage(String message) {
		add(message, false);
	}

	/**
	 * 增加一条错误消息
	 * 
	 * @param message
	 */
	public static void addError(String message) {
		add(message, true);
	}

	/**
	 * 增加一条消息，isError为true表示是错误消息
	 * 
	 * @param message
	 * @param isError
	 */
	private static void add(String message, boolean isError) {
		Message msg = new Message();
		msg.isError = isError;
		msg.Message = message;
		if (isError) {
			getCurrent().ErrorFlag = true;
			getCurrent().ErrorDealedFlag = false;
			StackTraceElement stack[] = (new Throwable()).getStackTrace();
			StringBuffer sb = new StringBuffer();
			sb.append("com.sinosoft.framework.utility.Errorx : ");
			sb.append(message);
			sb.append("\n");
			for (int i = 2; i < stack.length; i++) {// 前两个是Errorx里的方法，过滤
				StackTraceElement ste = stack[i];
				if (ste.getClassName().indexOf("DBConnPool") == -1) {
					sb.append("\tat ");
					sb.append(ste.getClassName());
					sb.append(".");
					sb.append(ste.getMethodName());
					sb.append("(");
					sb.append(ste.getFileName());
					sb.append(":");
					sb.append(ste.getLineNumber());
					sb.append(")\n");
				}
			}
			msg.StackTrace = sb.toString();
		}
		getCurrent().list.add(msg);
	}

	/**
	 * 返回所有消息
	 * 
	 * @return
	 */
	public static ArrayList getErrors() {
		return getCurrent().list;
	}

	/**
	 * 是否有错误消息
	 * 
	 * @return
	 */
	public static boolean hasError() {
		return getCurrent().ErrorFlag;
	}

	/**
	 * 是否所有错误消息都已经被处理过了
	 * 
	 * @return
	 */
	public static boolean hasDealed() {
		return getCurrent().ErrorDealedFlag;
	}

	/**
	 * 清除所有消息
	 */
	public void clear() {
		getCurrent().list.clear();
		getCurrent().ErrorFlag = false;
		getCurrent().ErrorDealedFlag = true;
	}

	/**
	 * 转换所有错误消息为可读的形式
	 * 
	 * @return
	 */
	public static String printString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0, j = 1; i < getCurrent().list.size(); i++) {
			Message msg = (Message) getCurrent().list.get(i);
			if (msg.isError) {
				// sb.append(j);
				// sb.append(",");
				sb.append("错误：");
				sb.append(msg.Message);
				sb.append("<br>\n");
				// LogUtil.getLogger().warning(msg.StackTrace);
				j++;
			}
		}
		for (int i = 0; i < getCurrent().list.size(); i++) {
			Message msg = (Message) getCurrent().list.get(i);
			if (!msg.isError) {
				// sb.append(j);
				// sb.append(",");
				sb.append("提示：");
				sb.append(msg.Message);
				sb.append("\n");
				// LogUtil.getLogger().warning(msg.StackTrace);
			}
		}
		// throw new RuntimeException("s");
		getCurrent().ErrorDealedFlag = true;
		return sb.toString();
	}

	/**
	 * 返回所有的消息以字符串的形式
	 * 
	 * @return
	 */
	public static String[] getMessages() {
		String[] arr = new String[getCurrent().list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = ((Message) getCurrent().list.get(i)).Message;
		}
		((Errorx) getCurrent()).clear();
		return arr;
	}

	/**
	 * 本次调用之前的必须初始化
	 */
	public static void init() {
		Object t = ErrorXLocal.get();
		if (t == null || !Errorx.class.isInstance(t)) {
			t = new Errorx();
		} else {
			((Errorx) t).clear();
		}
	}

	/**
	 * 返回当前的Errorx对象
	 * 
	 * @return
	 */
	public static Errorx getCurrent() {
		Object t = ErrorXLocal.get();
		if (t == null || !Errorx.class.isInstance(t)) {
			t = new Errorx();
			ErrorXLocal.set(t);
		}
		return (Errorx) t;
	}

	/**
	 * 消息对象类
	 * 
	 */
	static class Message {
		/**
		 * 是否是错误
		 */
		public boolean isError;

		/**
		 * 消息内容
		 */
		public String Message;

		/**
		 * 堆栈信息
		 */
		public String StackTrace;
	}

	public static String getAllMessage() {
		StringBuilder sb = new StringBuilder();
		int index = 1;
		for (int i = 0; i < getCurrent().list.size(); i++) {
			Message msg = (Message) getCurrent().list.get(i);
			if (msg.isError) {
				sb.append("\n").append(index).append(". Error: ").append(msg.Message);
				index++;
			}
		}
		for (int i = 0; i < getCurrent().list.size(); i++) {
			Message msg = (Message) getCurrent().list.get(i);
			if (!msg.isError) {
				sb.append("\n").append(index).append(". Warning: ").append(msg.Message);
				index++;
			}
		}
		getCurrent().clear();
		return sb.toString();
	}
}

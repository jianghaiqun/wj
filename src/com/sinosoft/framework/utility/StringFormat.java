package com.sinosoft.framework.utility;

import java.util.ArrayList;

/**
 * 字符串格式化类，类似于参数化SQL，可以避免大量的字符串拼接。<br>
 * 例如：
 * 
 * <pre>
 * StringFormat sf = new StringFormat(&quot;欢迎?于?访问本站.&quot;);
 * sf.add(&quot;wyuch&quot;);
 * sf.add(&quot;2006-10-11&quot;);
 * System.out.println(sf);
 * </pre>
 * 
 * 执行后输出：欢迎wyuch于2006-10-11访问本站.
 * 
 */
public class StringFormat {
	private String formatStr;

	private ArrayList params = new ArrayList();

	public StringFormat(String str) {
		this.formatStr = str;
	}

	public void add(String v) {
		params.add(v);
	}

	public void add(long v) {
		add(String.valueOf(v));
	}

	public void add(int v) {
		add(String.valueOf(v));
	}

	public void add(float v) {
		add(String.valueOf(v));
	}

	public void add(double v) {
		add(String.valueOf(v));
	}

	public void add(Object v) {
		add(String.valueOf(v));
	}

	public String toString() {
		String[] arr = StringUtil.splitEx(formatStr, "?");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length - 1; i++) {
			sb.append(arr[i]);
			sb.append(params.get(i));
		}
		sb.append(arr[arr.length - 1]);
		return sb.toString();
	}
}

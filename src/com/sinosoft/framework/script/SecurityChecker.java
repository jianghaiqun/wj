package com.sinosoft.framework.script;

public class SecurityChecker {
	protected static String[] CheckPrefixs =
			{"org.", "net.", "oracle.", "java.", "sun.", "javax.", "System.", "Runtime.", "Process.", "Package.", "Thread.",
					"ThreadGroup." };

	/**
	 * 去掉代码中的字符串,以例于进一步检查
	 */
	public static String clear(String script) {
		char[] cs = script.toCharArray();
		char lastStringChar = 0;
		boolean flag = false;// 前一字符是否转义起始字符
		StringBuffer sb = new StringBuffer();
		int index = 0;
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (lastStringChar == 0) {
				if (c == '\'' || c == '\"') {
					sb.append(script.substring(index, i));
					lastStringChar = c;
				}
			} else {
				if (!flag) {
					if (c == '\\') {
						flag = true;
					}
					if (c == lastStringChar) {
						lastStringChar = 0;
						index = i + 1;
					}
				} else {
					flag = false;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 检查脚本中是否手工引用了其他的包或者类
	 */
	public static boolean check(String script) {
		String str = clear(script);
		for (int i = 0; i < CheckPrefixs.length; i++) {
			String prefix = CheckPrefixs[i];
			int index = str.indexOf(prefix);
			if (index >= 0) {
				if (index == 0 || !Character.isJavaIdentifierPart(str.charAt(index - 1))) {
					return false;
				}
			}
		}
		return true;
	}
}

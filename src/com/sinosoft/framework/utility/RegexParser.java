package com.sinosoft.framework.utility;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * ${A}格式的简易正则表达式解析器，避免了常规正则表达式需要转义太多字符的毛病<br>
 * <br>
 * 在简易表达式中，<font color='red'>${T:Name}</font>表示一个占位符，其中:<br>
 * <font color='red'> T </font>表示占位符类型,<font color='red'> Name
 * </font>表示占位符的引用名称，解释之后得到一组Mapx，在这些Mapx中可以通过Name引用匹配到的值。
 * T共有A,D,W,-D,-W五种类型，共中A表示.*?，D表示\d*?，W表示任意长度的\w*?,-D表示\D*?,-W表示\W*?<br>
 * 
 * 别外还可以使用<font color='red'> ${{Pattern:Name}} </font>的方式直接使用简易正则表达式
 * 支持${(AB|CD|E)3+:Name}和${[ab]+:Name}两种写法，第一种写法用于一组字符串之间的或关系<br>
 * 第二种写法用于一组字符的或关系
 * 
 */
public class RegexParser {
	private static final Logger logger = LoggerFactory.getLogger(RegexParser.class);

	private String regex;

	private int currentPos;

	private String text;

	private String orginalText;

	private ArrayList list = new ArrayList(16);

	private ArrayList groups = null;// 本次匹配结果,包括未命名的结果

	private Mapx map = null;// 本次匹配的名值映射

	private int startPos = 0;

	private boolean caseIngore = true;

	private boolean lineWrapIngore = true;

	public RegexParser(String regex) {
		this(regex, true, true);
	}

	public RegexParser(String regex, boolean caseIngoreFlag, boolean lineWrapIngoreFlag) {
		this.regex = caseIngoreFlag ? regex.toLowerCase() : regex;
		caseIngore = caseIngoreFlag;
		lineWrapIngore = lineWrapIngoreFlag;
		parse();
	}

	public String getText() {
		return orginalText;
	}

	public synchronized void setText(String text) {
		orginalText = text;
		this.text = caseIngore ? text.toLowerCase() : text;
		currentPos = 0;// 重新开始匹配
	}

	private void parse() {
		if (StringUtil.isEmpty(regex)) {
			throw new RuntimeException("简易正则表达式不能为空!");
		}
		int lastIndex = 0;
		while (true) {
			int start = regex.indexOf("${", lastIndex);
			if (start < 0) {
				break;
			}
			int end = regex.indexOf("}", start);
			if (end < 0) {
				break;
			}
			String previous = regex.substring(lastIndex, start);
			if (StringUtil.isNotEmpty(previous)) {
				if (lineWrapIngore) {
					String[] arr = previous.split("\\n");
					for (int i = 0; i < arr.length; i++) {
						if (StringUtil.isNotEmpty(arr[i])) {
							list.add(arr[i].trim());
						}
					}
				} else {
					list.add(previous);
				}
			}
			String item = regex.substring(start + 2, end);
			list.add(new RegexItem(item));
			lastIndex = end + 1;
		}
		if (lastIndex != regex.length() - 1) {
			String str = regex.substring(lastIndex);
			if (lineWrapIngore) {
				String[] arr = str.split("\\n");
				for (int i = 0; i < arr.length; i++) {
					if (StringUtil.isNotEmpty(arr[i])) {
						list.add(arr[i].trim());
					}
				}
			} else {
				list.add(str);
			}
		}
	}

	public synchronized boolean match() {
		if (currentPos == text.length()) {
			return false;// 已经到了尽头
		}
		boolean matchFlag = true;
		map = new CaseIgnoreMapx();
		groups = new ArrayList(16);
		startPos = currentPos;
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			if (obj instanceof String) {
				String item = (String) obj;
				int index = text.indexOf(item, currentPos);
				if (index < 0) {
					matchFlag = false;
					break;
				}
				if (i != 0 && index != currentPos) {// 除开始时以外，其他次数匹配必须要紧接着上一次
					matchFlag = false;
					break;
				}
				if (i == 0) {
					startPos = index;
				}
				currentPos = index + item.length();
				if (lineWrapIngore) {
					for (int j = currentPos; j < text.length(); j++) {
						char c = text.charAt(j);
						if (c != '\n' && c != '\t' && c != '\r' && c != ' ' && c != '\b' && c != '\f') {
							currentPos = j;
							break;
						}
					}
				}
			} else {
				RegexItem item = (RegexItem) obj;
				int pos = currentPos;
				int count = 0;
				while (true) {
					if (i == 0 && count == 0) {// 确定起始匹配位置
						while (true) {
							int index = item.match(text, pos, 0);
							if (pos == text.length() - 1) {
								return false;
							}
							if (index >= 0) {
								startPos = pos;
								pos = index;
								break;
							}
							pos++;
						}
					}
					pos = item.match(text, currentPos, count);// 此处返回匹配结束位置
					count++;
					if (pos < 0) {
						return false;
					}
					if (!item.greaterFlag || i == list.size() - 1) {// 指定了次数或者是最后一个
						break;
					} else {// 尝试匹配下一个
						obj = list.get(i + 1);
						if (obj instanceof String) {
							int index = text.indexOf(obj.toString(), pos);
							if (index == -1) {
								return false;
							}
							if (index != pos) {// 如果未匹配，则再次尝试
								if (item.parts == null) {// 说明是逐字符匹配
									count += index - pos - 1;
								}
								continue;
							} else {
								break;
							}
						} else {
							RegexItem nextItem = (RegexItem) obj;
							int nextPos = nextItem.match(text, pos, 0);// 下一个匹配项肯定不是开始项
							if (nextPos < 0) {
								continue;
							} else {
								break;
							}
						}
					}
				}
				try {
					String str = this.orginalText.substring(currentPos, pos);
					if (StringUtil.isNotEmpty(item.getName())) {
						map.put(item.getName(), str);
					}
					groups.add(str);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				currentPos = pos;
			}
		}
		if (!matchFlag) {
			map = null;
			groups = null;
			if (currentPos > 0 && startPos != currentPos) {// 说明至少前几个占位符或者文件有被匹配到，需要继续尝试后面的文本
				return match();
			}
		}
		return matchFlag;
	}

	public String replace(String content, String replacement) {
		this.setText(content);
		StringBuffer sb = new StringBuffer();
		int lastIndex = 0;
		while (this.match()) {
			sb.append(this.orginalText.substring(lastIndex, startPos));
			sb.append(replacement);
			lastIndex = currentPos;
		}
		sb.append(this.orginalText.substring(lastIndex));
		return sb.toString();
	}

	public String[] getGroups() {
		if (groups == null) {
			return null;
		}
		String[] arr = new String[groups.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (String) groups.get(i);
		}
		return arr;
	}

	public Mapx getMapx() {
		return map;
	}

	public String getMacthedText() {
		return this.orginalText.substring(startPos, currentPos);
	}

	static class RegexItem {
		private String name;

		private String expr;

		private char[] charArr;

		private boolean exclusive;// 排除模式

		private boolean greaterFlag = true;

		private int needCount = 0;

		private String[] parts;

		public RegexItem(String item) {
			int index = item.lastIndexOf(":");
			if (index >= 0) {// 有可能没有名称
				name = item.substring(index + 1);
				expr = item.substring(0, index);
			} else {
				expr = item;
			}
			if (StringUtil.isEmpty(expr)) {
				throw new RuntimeException("不正确的简单正则表达式占位符：" + item);
			}
			// 先将五种简易写法翻译成一般写法
			if (expr.equalsIgnoreCase("A")) {
				expr = "-[]*";
			} else if (expr.equalsIgnoreCase("D")) {
				expr = "[\\d]*";
			} else if (expr.equalsIgnoreCase("-D")) {
				expr = "-[\\d]*";
			} else if (expr.equalsIgnoreCase("W")) {
				expr = "[\\w]*";
			} else if (expr.equalsIgnoreCase("-W")) {
				expr = "-[\\w]*";
			}
			if (expr.startsWith("(")) {// 字符串组合，字符集串组合中不支持表达式
				index = expr.lastIndexOf(")");
				if (index < expr.length() - 1) {
					String tail = expr.substring(index + 1);
					if (tail.equals("")) {
						greaterFlag = false;
						needCount = 1;
					} else if (tail.equals("*")) {
						greaterFlag = true;
						needCount = 0;
					} else if (tail.equals("+")) {
						greaterFlag = true;
						needCount = 1;
					} else if (tail.endsWith("+")) {// 形如3+,表示3次以上
						greaterFlag = true;
						try {
							needCount = Integer.parseInt(tail.substring(0, tail.length() - 1));
						} catch (Exception e) {
							throw new RuntimeException("不正确的简单正则表达式占位符：" + item);
						}
					} else {// 指定次数
						greaterFlag = false;
						try {
							needCount = Integer.parseInt(tail.substring(0, tail.length()));
						} catch (Exception e) {
							throw new RuntimeException("不正确的简单正则表达式占位符：" + expr);
						}
					}
				}
				expr = expr.substring(1, index);
				// 开始拆分
				ArrayList list = new ArrayList();
				String[] arr = StringUtil.splitEx(expr, "|");
				for (int i = arr.length - 1; i >= 0; i--) {// 需要去掉空的元素以便于判断转义
					if (StringUtil.isEmpty(arr[i])) {
						ArrayUtils.remove(arr, i);
					}
				}
				for (int i = 0; i < arr.length; i++) {
					if (arr[i].endsWith("\\")) {// 说明是转义
						if (i == arr.length - 1) {// 最后不能转义了
							throw new RuntimeException("不正确的简单正则表达式占位符：" + item);
						}
						String str = arr[i] + "|" + arr[i + 1];
						list.add(str);
						i++;
					} else {
						String str = arr[i];
						list.add(str);
					}
				}
				arr = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					arr[i] = (String) list.get(i);
				}
				parts = arr;
			} else {// 表达式
				if (expr.startsWith("-")) {
					exclusive = true;
					expr = expr.substring(1);
				}
				int end = expr.lastIndexOf("]");
				int start = expr.indexOf("[");
				if (start != 0) {// 表明是表达式
					throw new RuntimeException("不正确的简单正则表达式占位符：" + expr);
				}
				if (start < 0 || end < 0 || end < start) {
					throw new RuntimeException("不正确的简单正则表达式占位符：" + expr);
				}
				String content = expr.substring(start + 1, end);
				if (end < expr.length() - 1) {
					// 以下解析匹配次数
					String tail = expr.substring(end + 1);
					if (tail.equals("")) {
						greaterFlag = false;
						needCount = 1;
					} else if (tail.equals("*")) {
						greaterFlag = true;
						needCount = 0;
					} else if (tail.equals("+")) {
						greaterFlag = true;
						needCount = 1;
					} else if (tail.endsWith("+")) {// 形如3+,表示3次以上
						greaterFlag = true;
						try {
							needCount = Integer.parseInt(tail.substring(0, tail.length() - 1));
						} catch (Exception e) {
							throw new RuntimeException("不正确的简单正则表达式占位符：" + expr);
						}
					} else {// 指定次数
						greaterFlag = false;
						try {
							needCount = Integer.parseInt(tail.substring(0, tail.length()));
						} catch (Exception e) {
							throw new RuntimeException("不正确的简单正则表达式占位符：" + expr);
						}
					}
				}
				StringBuffer sb = new StringBuffer(128);
				boolean flag = false;// 转义标志
				for (int i = 0; i < content.length(); i++) {
					char c = content.charAt(i);
					if (flag) {
						if (c == 'd') {
							sb.append("0123456789");
						} else if (c == 'w') {
							sb.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789-");
						} else if (c == 's') {
							sb.append("\t\n\b\f\r");
						} else if (c == 't') {
							sb.append("\t");
						} else if (c == 'n') {
							sb.append("\n");
						} else if (c == 'b') {
							sb.append("\n");
						} else if (c == 'f') {
							sb.append("\f");
						} else if (c == 'r') {
							sb.append("\r");
						} else if (c == '\\') {
							sb.append("\\");
						} else {
							sb.append(c);// 除上述以外，转义符无作用
						}
					} else if (c != '\\') {
						sb.append(c);
					}
					if (c == '\\') {
						flag = true;
					} else {
						flag = false;
					}
				}
				charArr = sb.toString().toCharArray();
			}
		}

		public int match(String text, int startPos, int extraCount) {
			int pos = startPos;
			int count = needCount + extraCount;
			for (int j = 0; j < count; j++) {
				if (pos >= text.length()) {
					return -1;
				}
				if (parts == null) {// 说明是表达式
					char c = text.charAt(pos);
					boolean flag = false;
					for (int i = 0; i < charArr.length; i++) {
						if (charArr[i] == c) {
							if (this.exclusive) {// 说明匹配失败
								return -1;
							}
							flag = true;
							break;
						}
					}
					if (!exclusive && !flag) {
						return -1;
					}
					pos++;
				} else {
					boolean flag = false;
					for (int i = 0; i < parts.length; i++) {
						int index = text.indexOf(parts[i], pos);
						if (index == pos) {// 说明被匹配
							pos = index + parts[i].length();
							flag = true;
							break;
						}
					}
					if (!flag) {
						return -1;
					}
				}
			}
			return pos;
		}

		public String getName() {
			return name;
		}
	}
}

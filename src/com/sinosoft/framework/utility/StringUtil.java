package com.sinosoft.framework.utility;

import com.sinosoft.framework.Constant;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.VerifyRule;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.schema.ZCTagSchema;
import com.sinosoft.schema.ZCTagSet;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	/**
	 * UTF-8的三个字节的BOM
	 */
	public static final byte[] BOM = new byte[] { (byte) 239, (byte) 187, (byte) 191 };

	/**
	 * 十六进制字符
	 */
	public static final char HexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 获取指定字符串的MD5摘要
	 */
	public static byte[] md5(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md = md5.digest(src.getBytes());
			return md;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取指定二进制数组的MD5摘要
	 */
	public static byte[] md5(byte[] src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md = md5.digest(src);
			return md;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将字符串进行md5摘要，然后输出成十六进制形式
	 */
	public static String md5Hex(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md = md5.digest(src.getBytes());
			return hexEncode(md);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将字符串进行MD5摘要，输出成BASE64形式
	 */
	public static String md5Base64(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return base64Encode(md5.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 将十六制进表示MD5摘要转换成BASE64格式
	 */
	public static String md5Base64FromHex(String md5str) {
		char[] cs = md5str.toCharArray();
		byte[] bs = new byte[16];
		for (int i = 0; i < bs.length; i++) {
			char c1 = cs[i * 2];
			char c2 = cs[i * 2 + 1];
			byte m1 = 0;
			byte m2 = 0;
			for (byte k = 0; k < 16; k++) {
				if (HexDigits[k] == c1) {
					m1 = k;
				}
				if (HexDigits[k] == c2) {
					m2 = k;
				}
			}
			bs[i] = (byte) (m1 << 4 | 0x0 + m2);

		}
		String newstr = base64Encode(bs);
		return newstr;
	}

	/**
	 * 将十六制进表示MD5摘要转换成BASE64格式
	 */
	public static String md5HexFromBase64(String base64str) {
		return hexEncode(base64Decode(base64str));
	}

	/**
	 * 将二进制数组转换成十六进制表示
	 */
	public static String hexEncode(byte[] bs) {
		return new String(new Hex().encode(bs));
	}

	/**
	 * 将字符串转换成十六进制表示
	 */
	public static byte[] hexDecode(String str) {
		try {
			if (str.endsWith("\n")) {
				str = str.substring(0, str.length() - 1);
			}
			char[] cs = str.toCharArray();
			return Hex.decodeHex(cs);
		} catch (DecoderException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 将字节数组转换成二制形式字符串
	 */
	public static String byteToBin(byte[] bs) {
		char[] cs = new char[bs.length * 9];
		for (int i = 0; i < bs.length; i++) {
			byte b = bs[i];
			int j = i * 9;
			cs[j] = (b >>> 7 & 1) == 1 ? '1' : '0';
			cs[j + 1] = (b >>> 6 & 1) == 1 ? '1' : '0';
			cs[j + 2] = (b >>> 5 & 1) == 1 ? '1' : '0';
			cs[j + 3] = (b >>> 4 & 1) == 1 ? '1' : '0';
			cs[j + 4] = (b >>> 3 & 1) == 1 ? '1' : '0';
			cs[j + 5] = (b >>> 2 & 1) == 1 ? '1' : '0';
			cs[j + 6] = (b >>> 1 & 1) == 1 ? '1' : '0';
			cs[j + 7] = (b & 1) == 1 ? '1' : '0';
			cs[j + 8] = ',';
		}
		return new String(cs);
	}

	/**
	 * 转换字节数组为十六进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
			resultSb.append(" ");
		}
		return resultSb.toString();
	}

	/**
	 * 字节转换为十六进制字符串
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HexDigits[d1] + "" + HexDigits[d2];
	}

	/**
	 * 判断指定的二进制数组是否是一个UTF-8字符串
	 */
	public static boolean isUTF8(byte[] bs) {
		if (StringUtil.hexEncode(ArrayUtils.subarray(bs, 0, 3)).equals("efbbbf")) {// BOM标志
			return true;
		}
		int lLen = bs.length;
		for (int i = 0; i < lLen;) {
			byte b = bs[i++];
			if (b >= 0) {
				continue;// >=0 is normal ascii
			}
			if (b < (byte) 0xc0 || b > (byte) 0xfd) {
				return false;
			}
			int c = b > (byte) 0xfc ? 5 : b > (byte) 0xf8 ? 4 : b > (byte) 0xf0 ? 3 : b > (byte) 0xe0 ? 2 : 1;
			if (i + c > lLen) {
				return false;
			}
			for (int j = 0; j < c; ++j, ++i) {
				if (bs[i] >= (byte) 0xc0) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 返回二进制数组的BASE64编码结果
	 */
	public static String base64Encode(byte[] b) {
		if (b == null) {
			return null;
		}
		return (new BASE64Encoder()).encode(b);
	}

	/**
	 * 将 BASE64 编码的字符串进行解码
	 */
	public static byte[] base64Decode(String s) {
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				return decoder.decodeBuffer(s);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * 将字符串转换成可以在JAVA表达式中直接使用的字符串，处理一些转义字符
	 */
	public static String javaEncode(String txt) {
		if (txt == null || txt.length() == 0) {
			return txt;
		}
		txt = replaceEx(txt, "\\", "\\\\");
		txt = replaceEx(txt, "\r\n", "\n");
		txt = replaceEx(txt, "\r", "\\r");
		txt = replaceEx(txt, "\t", "\\t");
		txt = replaceEx(txt, "\n", "\\n");
		txt = replaceEx(txt, "\"", "\\\"");
		txt = replaceEx(txt, "\'", "\\\'");
		return txt;
	}

	/**
	 * 将StringUtil.javaEncode()处理过的字符还原
	 */
	public static String javaDecode(String txt) {
		if (txt == null || txt.length() == 0) {
			return txt;
		}
		txt = replaceEx(txt, "\\\\", "\\");
		txt = replaceEx(txt, "\\n", "\n");
		txt = replaceEx(txt, "\\r", "\r");
		txt = replaceEx(txt, "\\\"", "\"");
		txt = replaceEx(txt, "\\\'", "\'");
		return txt;
	}

	/**
	 * 将一个字符串按照指下的分割字符串分割成数组。分割字符串不作正则表达式处理，<br>
	 * String类的split方法要求以正则表达式分割字符串，有时较为不便，可以转为采用本方法。
	 */
	public static String[] splitEx(String str, String spilter) {
		if (str == null) {
			return null;
		}
		if (spilter == null || spilter.equals("") || str.length() < spilter.length()) {
			String[] t = { str };
			return t;
		}
		ArrayList al = new ArrayList();
		char[] cs = str.toCharArray();
		char[] ss = spilter.toCharArray();
		int length = spilter.length();
		int lastIndex = 0;
		for (int i = 0; i <= str.length() - length;) {
			boolean notSuit = false;
			for (int j = 0; j < length; j++) {
				if (cs[i + j] != ss[j]) {
					notSuit = true;
					break;
				}
			}
			if (!notSuit) {
				al.add(str.substring(lastIndex, i));
				i += length;
				lastIndex = i;
			} else {
				i++;
			}
		}
		if (lastIndex <= str.length()) {
			al.add(str.substring(lastIndex, str.length()));
		}
		String[] t = new String[al.size()];
		for (int i = 0; i < al.size(); i++) {
			t[i] = (String) al.get(i);
		}
		return t;
	}

	/**
	 * 将一个字符串中的指定片段全部替换，替换过程中不进行正则处理。<br>
	 * 使用String类的replaceAll时要求片段以正则表达式形式给出，有时较为不便，可以转为采用本方法。
	 */
	public static String replaceEx(String str, String subStr, String reStr) {
		if (str == null) {
			return null;
		}
		if (subStr == null || subStr.equals("") || subStr.length() > str.length() || reStr == null) {
			return str;
		}
		StringBuffer sb = new StringBuffer();
		int lastIndex = 0;
		while (true) {
			int index = str.indexOf(subStr, lastIndex);
			if (index < 0) {
				break;
			} else {
				sb.append(str.substring(lastIndex, index));
				sb.append(reStr);
			}
			lastIndex = index + subStr.length();
		}
		sb.append(str.substring(lastIndex));
		return sb.toString();
	}

	/**
	 * 不区分大小写的全部替换，替换时使用了正则表达式。
	 */
	public static String replaceAllIgnoreCase(String source, String oldstring, String newstring) {
		Pattern p = Pattern.compile(oldstring, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(source);
		return m.replaceAll(newstring);
	}

	/**
	 * 以全局编码进行URL编码
	 */
	public static String urlEncode(String str) {
		return urlEncode(str, Constant.GlobalCharset);
	}

	/**
	 * 以全局编码进行URL解码
	 */
	public static String urlDecode(String str) {
		return urlDecode(str, Constant.GlobalCharset);
	}

	/**
	 * 以指定编码进行URL编码
	 */
	public static String urlEncode(String str, String charset) {
		try {
			return new URLCodec().encode(str, charset);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 以指定编码进行URL解码
	 */
	public static String urlDecode(String str, String charset) {
		try {
			return new URLCodec().decode(str, charset);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 对字符串进行HTML编码
	 */
	public static String htmlEncode(String txt) {
		return StringEscapeUtils.escapeHtml(txt);
	}

	/**
	 * 对字符串进行HTML解码
	 */
	public static String htmlDecode(String txt) {
		txt = replaceEx(txt, "&#8226;", "·");
		return StringEscapeUtils.unescapeHtml(txt);
	}

	/**
	 * 替换字符串中的双引号
	 */
	public static String quotEncode(String txt) {
		if (txt == null || txt.length() == 0) {
			return txt;
		}
		txt = replaceEx(txt, "&", "&amp;");
		txt = replaceEx(txt, "\"", "&quot;");
		return txt;
	}

	/**
	 * 还原通过StringUtil.quotEncode()编码的字符串
	 */
	public static String quotDecode(String txt) {
		if (txt == null || txt.length() == 0) {
			return txt;
		}
		txt = replaceEx(txt, "&quot;", "\"");
		txt = replaceEx(txt, "&amp;", "&");
		return txt;
	}

	/**
	 * Javascript中escape的JAVA实现
	 */
	public static String escape(String src) {
		char j;
		StringBuffer sb = new StringBuffer();
		sb.ensureCapacity(src.length() * 6);
		for (int i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
				sb.append(j);
			} else if (j < 256) {
				sb.append("%");
				if (j < 16) {
					sb.append("0");
				}
				sb.append(Integer.toString(j, 16));
			} else {
				sb.append("%u");
				sb.append(Integer.toString(j, 16));
			}
		}
		return sb.toString();
	}

	/**
	 * Javascript中unescape的JAVA实现
	 */
	public static String unescape(String src) {
		StringBuffer sb = new StringBuffer();
		sb.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					sb.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					sb.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					sb.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					sb.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 在一字符串左边填充若干指定字符，使其长度达到指定长度
	 */
	public static String leftPad(String srcString, char c, int length) {
		if (srcString == null) {
			srcString = "";
		}
		int tLen = srcString.length();
		int i, iMax;
		if (tLen >= length)
			return srcString;
		iMax = length - tLen;
		StringBuffer sb = new StringBuffer();
		for (i = 0; i < iMax; i++) {
			sb.append(c);
		}
		sb.append(srcString);
		return sb.toString();
	}

	/**
	 * 将长度超过length的字符串截取length长度，若不足，则返回原串
	 */
	public static String subString(String src, int length) {
		if (src == null) {
			return null;
		}
		int i = src.length();
		if (i > length) {
			return src.substring(0, length);
		} else {
			return src;
		}
	}

	/**
	 * 将长度超过length的字符串截取length长度，若不足，则返回原串。<br>
	 * 其中ASCII字符只算半个长度单位。
	 */
	public static String subStringEx(String src, int length) {
		length = length * 2;
		if (src == null) {
			return null;
		}
		int k = lengthEx(src);
		if (k > length) {
			int m = 0;
			boolean unixFlag = false;
			String osname = System.getProperty("os.name").toLowerCase();
			if (osname.indexOf("sunos") > 0 || osname.indexOf("solaris") > 0 || osname.indexOf("aix") > 0) {
				unixFlag = true;
			}
			try {
				byte[] b = src.getBytes("Unicode");
				for (int i = 2; i < b.length; i += 2) {
					byte flag = b[i + 1];
					if (unixFlag) {
						flag = b[i];
					}
					if (flag == 0) {
						m++;
					} else {
						m += 2;
					}
					if (m > length) {
						return src.substring(0, (i - 2) / 2);
					}
				}
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException("执行方法getBytes(\"Unicode\")时出错！");
			}
		}
		return src;
	}

	/**
	 * 获得字符串的长度，其中ASCII字符算1个长度单位,非ASCII字符算两个长度单位
	 */
	public static int lengthEx(String src) {
		int length = 0;
		boolean unixFlag = false;
		String osname = System.getProperty("os.name").toLowerCase();
		if (osname.indexOf("sunos") > 0 || osname.indexOf("solaris") > 0 || osname.indexOf("aix") > 0) {
			unixFlag = true;
		}
		try {
			byte[] b = src.getBytes("Unicode");
			for (int i = 2; i < b.length; i += 2) {
				byte flag = b[i + 1];
				if (unixFlag) {
					flag = b[i];
				}
				if (flag == 0) {
					length++;
				} else {
					length += 2;
				}
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException("执行方法getBytes(\"Unicode\")时出错！");
		}
		return length;
	}

	/**
	 * 计算输入字符串的字节长度
	 * 
	 * @author zhangjinquan 11180
	 * @createdate 2013-01-15
	 * @param str
	 * @return
	 */
	public static int getByteLength(String str) {
		if (null == str) {
			return 4;
		}
		int len = str.length();
		int byteLen = 0;
		for (int i = 0; i < len; i++) {
			if (str.charAt(i) > 255) {
				byteLen += 2;
			} else {
				byteLen += 1;
			}
		}
		return byteLen;
	}

	/**
	 * 右侧字节截取替换
	 * 
	 * @author zhangjinquan 11180
	 * @createdate 2013-01-15
	 * @param str
	 * @param len
	 * @param padChar
	 * @return
	 */
	public static String rPadByte(String str, int len, String padChar) {
		if (null == str) {
			return str;
		}

		int preSublen = 0;
		String substr = "";
		int strlen = str.length();
		for (int i = 0; i < strlen; i++) {
			substr = str.substring(0, i + 1);
			int sublen = getByteLength(substr);
			if (sublen > len) {
				substr = str.substring(0, i);
				break;
			}
			preSublen = sublen;
		}

		if ((null == padChar) || ("".equals(padChar))) {
			return substr;
		}

		for (int i = 0; i < (len - preSublen); i++) {
			substr += padChar;
		}
		return substr;
	}

	/**
	 * 左侧字节截取替换
	 * 
	 * @author zhangjinquan 11180
	 * @createdate 2013-01-15
	 * @param str
	 * @param len
	 * @param padChar
	 * @return
	 */
	public static String lPadByte(String str, int len, String padChar) {
		if (null == str) {
			return str;
		}

		int preSublen = 0;
		String substr = "";
		int strlen = str.length();
		for (int i = 0; i < strlen; i++) {
			substr = str.substring(strlen - i, strlen);
			int sublen = getByteLength(substr);
			if (sublen > len) {
				substr = str.substring(strlen - i + 1, strlen);
				break;
			}
			preSublen = sublen;
		}

		if ((null == padChar) || ("".equals(padChar))) {
			return substr;
		}

		for (int i = 0; i < (len - preSublen); i++) {
			substr = padChar + substr;
		}
		return substr;
	}

	/**
	 * 在一字符串右边填充若干指定字符，使其长度达到指定长度
	 */
	public static String rightPad(String srcString, char c, int length) {
		if (srcString == null) {
			srcString = "";
		}
		int tLen = srcString.length();
		int i, iMax;
		if (tLen >= length)
			return srcString;
		iMax = length - tLen;
		StringBuffer sb = new StringBuffer();
		sb.append(srcString);
		for (i = 0; i < iMax; i++) {
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 以指定的校验规则校验字符串
	 */
	public static boolean verify(String value, String rule) {
		VerifyRule vr = new VerifyRule(rule);
		boolean flag = vr.verify(value);
		return flag;
	}

	/**
	 * 清除字符右边的空格
	 */
	public static String rightTrim(String src) {
		if (src != null) {
			char[] chars = src.toCharArray();
			for (int i = chars.length - 1; i >= 0; i--) {
				if (chars[i] == ' ' || chars[i] == '\t' || chars[i] == '\r') {
					continue;
				} else {
					return src.substring(0, i + 1);
				}
			}
			return "";// 说明全是空格
		}
		return src;
	}


	/**
	 * 半角转全角，转除英文字母之外的字符
	 */
	public static String toSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if ((c[i] > 64 && c[i] < 91) || (c[i] > 96 && c[i] < 123)) {
				continue;
			}

			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}

	/**
	 * 半角转全角，转所有能转为全角的字符，包括英文字母
	 */
	public static String toNSBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}

			if (c[i] < 127)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}

	/**
	 * 全角转半角的函数
	 * 
	 * 全角空格为12288，半角空格为32 <br>
	 * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
	 */
	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 返回字符串的全拼,是汉字转化为全拼,其它字符不进行转换
	 * 
	 * @param cnStr String 字符串
	 * @return String 转换成全拼后的字符串
	 */
	public static String getChineseFullSpell(String cnStr) {
		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}
		return ChineseSpelling.convert(cnStr);
	}

	/**
	 * 返回字符串的第一个汉字的全拼
	 * 
	 * @param cnStr
	 * @return
	 */
	public static String getChineseFamilyNameSpell(String cnStr) {
		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}
		return ChineseSpelling.convertName(cnStr);
	}

	public static String getChineseFirstAlpha(String cnStr) {
		if (null == cnStr || "".equals(cnStr.trim())) {
			return cnStr;
		}
		return ChineseSpelling.getFirstAlpha(cnStr);
	}

	public static final Pattern PTitle = Pattern.compile("<title>(.+?)</title>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	/**
	 * 从一段html文本中提取出<title>标签内容
	 */
	public static String getHtmlTitle(File f) {
		String html = FileUtil.readText(f);
		String title = getHtmlTitle(html);
		return title;
	}

	/**
	 * 从一段html文本中提取出<title>标签内容
	 */
	public static String getHtmlTitle(String html) {
		Matcher m = PTitle.matcher(html);
		if (m.find()) {
			return m.group(1).trim();
		}
		return null;
	}

	public static Pattern patternHtmlTag = Pattern.compile("<[^<>]+>", Pattern.DOTALL);

	/**
	 * 清除HTML文本中所有标签
	 */
	public static String clearHtmlTag(String html) {
		String text = patternHtmlTag.matcher(html).replaceAll("");
		if (isEmpty(text)) {
			return "";
		}
		text = StringUtil.htmlDecode(html);
		return text.replaceAll("[\\s　]{2,}", " ");
	}
	
	/**
	 * delHTMLTag:删除html标签. <br/>
	 *
	 * @author wwy
	 * @param html
	 * @return
	 */
	public static String delHTMLTag(String html) {
		if (isEmpty(html)) {
			return "";
		}
		String regEx_html="<[^>]+>";
		Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(html); 
        html = m_html.replaceAll("");
		return html;
	}

	/**
	 * 首字母大写
	 */
	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	/**
	 * 字符串是否为空，null或空字符串时返回true,其他情况返回false
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0 || "null".equalsIgnoreCase(str.trim());
	}
	/**
	 * 字符串是否为空，null或空字符串时返回true,其他情况返回false
	 */
	public static boolean isEmpty(Object str) {
		return str == null || "".equals(str.toString().trim()) || "null".equalsIgnoreCase(str.toString().trim());
	}

	/**
	 * 字符串是否不为空，null或空字符串时返回false,其他情况返回true
	 */
	public static boolean isNotEmpty(String str) {
		return !StringUtil.isEmpty(str);
	}

	/**
	 * 字符串为空时返回defaultString，否则返回原串
	 */
	public static final String noNull(String string, String defaultString) {
		return isEmpty(string) ? defaultString : string;
	}

	/**
	 * 字符串为空时返回defaultString，否则返回空字符串
	 */
	public static final String noNull(String string) {
		return noNull(string, "");
	}

	/**
	 * 将一个数组拼成一个字符串，数组项之间以逗号分隔
	 */
	public static String join(Object[] arr) {
		return join(arr, ",");
	}

	/**
	 * 将一个二维数组拼成一个字符串，第二维以逗号分隔，第一维以换行分隔
	 */
	public static String join(Object[][] arr) {
		return join(arr, "\n", ",");
	}

	/**
	 * 将一个数组以指定的分隔符拼成一个字符串
	 */
	public static String join(Object[] arr, String spliter) {
		if (arr == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			// 2011-1-20 更改，当arr[i]为空时不能加入到sb中
			if (StringUtil.isNotEmpty(String.valueOf(arr[i]))) {
				if (i != 0) {
					sb.append(spliter);
				}
				sb.append(arr[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 将一个二维数组拼成一个字符串，第二维以指定的spliter2参数分隔，第一维以换行spliter1分隔
	 */
	public static String join(Object[][] arr, String spliter1, String spliter2) {
		if (arr == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i != 0) {
				sb.append(spliter2);
			}
			sb.append(join(arr[i], spliter2));
		}
		return sb.toString();
	}

	/**
	 * 将一个List拼成一个字符串，数据项之间以逗号分隔
	 */
	public static String join(List list) {
		return join(list, ",");
	}

	/**
	 * 将一个List拼成一个字符串，数据项之间以指定的参数spliter分隔
	 */
	public static String join(List list, String spliter) {
		if (list == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				sb.append(spliter);
			}
			sb.append(list.get(i));
		}
		return sb.toString();
	}

	/**
	 * 计算一个字符串中某一子串出现的次数
	 */
	public static int count(String str, String findStr) {
		int lastIndex = 0;
		int length = findStr.length();
		int count = 0;
		int start = 0;
		while ((start = str.indexOf(findStr, lastIndex)) >= 0) {
			lastIndex = start + length;
			count++;
		}
		return count;
	}

	public static final Pattern PLetterOrDigit = Pattern.compile("^\\w*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern PLetter = Pattern.compile("^[A-Za-z]*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public static final Pattern PDigit = Pattern.compile("^\\d*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	/**
	 * 判断字符串是否全部由数字或字母组成
	 */
	public static boolean isLetterOrDigit(String str) {
		return PLetterOrDigit.matcher(str).find();
	}

	/**
	 * 判断字符串是否全部字母组成
	 */
	public static boolean isLetter(String str) {
		return PLetter.matcher(str).find();
	}

	/**
	 * 判断字符串是否全部由数字组成
	 */
	public static boolean isDigit(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		return PDigit.matcher(str).find();
	}

	private static Pattern chinesePattern = Pattern.compile("[^\u4e00-\u9fa5]+", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	/**
	 * 判断字符串中是否含有中文字符
	 */
	public static boolean containsChinese(String str) {
		if (!chinesePattern.matcher(str).matches()) {
			return true;
		}
		return false;
	}

	private static Pattern idPattern = Pattern.compile("[\\w\\s\\_\\.\\,]*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	/**
	 * 检查ID，防止SQL注入，主要是在删除时传入多个ID时使用
	 */
	public static boolean checkID(String str) {
		if (StringUtil.isEmpty(str)) {
			return true;
		}
		if (idPattern.matcher(str).matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 将一个类似于Name=John,Age=18,Gender=3的字符串拆成一个Mapx
	 */
	public static Mapx splitToMapx(String str, String entrySpliter, String keySpliter) {
		Mapx map = new Mapx();
		String[] arr = StringUtil.splitEx(str, entrySpliter);
		for (int i = 0; i < arr.length; i++) {
			String[] arr2 = StringUtil.splitEx(arr[i], keySpliter);
			String key = arr2[0];
			if (StringUtil.isEmpty(key)) {
				continue;
			}
			key = key.trim();
			String value = null;
			if (arr2.length > 1) {
				value = arr2[1];
			}
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 得到URL的文件扩展名
	 */
	public static String getURLExtName(String url) {
		if (isEmpty(url)) {
			return null;
		}
		int index1 = url.indexOf('?');
		if (index1 == -1) {
			index1 = url.length();
		}
		int index2 = url.lastIndexOf('.', index1);
		if (index2 == -1) {
			return null;
		}
		int index3 = url.indexOf('/', 8);
		if (index3 == -1) {
			return null;
		}
		String ext = url.substring(index2 + 1, index1);
		if (ext.matches("[^\\/\\\\]*")) {
			return ext;
		}
		return null;
	}

	/**
	 * 得到URL的文件名
	 */
	public static String getURLFileName(String url) {
		if (isEmpty(url)) {
			return null;
		}
		int index1 = url.indexOf('?');
		if (index1 == -1) {
			index1 = url.length();
		}
		int index2 = url.lastIndexOf('/', index1);
		if (index2 == -1 || index2 < 8) {
			return null;
		}
		String ext = url.substring(index2 + 1, index1);
		return ext;
	}

	/**
	 * 将一个GBK编码的字符串转成UTF-8编码的二进制数组，转换后没有BOM位
	 */
	public static byte[] GBKToUTF8(String chinese) {
		return GBKToUTF8(chinese, false);
	}

	/**
	 * 将一个GBK编码的字符串转成UTF-8编码的二进制数组，如果参数bomFlag为true，则转换后有BOM位
	 */
	public static byte[] GBKToUTF8(String chinese, boolean bomFlag) {
		return CharsetConvert.GBKToUTF8(chinese, bomFlag);
	}

	/**
	 * 将UTF-8编码的字符串转成GBK编码的字符串
	 */
	public static byte[] UTF8ToGBK(String chinese) {
		return CharsetConvert.UTF8ToGBK(chinese);
	}

	/**
	 * 去掉XML字符串中的非法字符, 在XML中0x00-0x20 都会引起一定的问题
	 */
	public static String clearForXML(String str) {
		char[] cs = str.toCharArray();
		char[] ncs = new char[cs.length];
		int j = 0;
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] > 0xFFFD) {
				continue;
			} else if (cs[i] < 0x20 && cs[i] != '\t' & cs[i] != '\n' & cs[i] != '\r') {
				continue;
			}
			ncs[j++] = cs[i];
		}
		ncs = ArrayUtils.subarray(ncs, 0, j);
		return new String(ncs);
	}

	public static boolean isMail(String mail) {
		if (isEmpty(mail)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9_\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(mail);
		return matcher.matches();
	}

	public static boolean isMobileNO(String mobiles) {
		if (isEmpty(mobiles)) {
			return false;
		}
		Pattern p = Pattern.compile("^[1][3-8][0-9]{9}$");
		Matcher m = p.matcher(mobiles);
		boolean flag = m.matches();
		return m.matches();
	}

	public static boolean isPasword(String password) {
		if (isEmpty(password) || (password.length() < 6 || password.length() > 16)) {
			return false;
		}
		int num = 0;
		num = Pattern.compile("\\d").matcher(password).find() ? num + 1 : num;
		num = Pattern.compile("[a-z]").matcher(password).find() ? num + 1 : num;
		num = Pattern.compile("[A-Z]").matcher(password).find() ? num + 1 : num;
		num = Pattern.compile("[-.!@#$%^&*()+?><]").matcher(password).find() ? num + 1 : num;
		if (num >= 2) {
			return true;
		}
		return false;
	}

	/**
	 * 转化输入字符串中的特殊字符为HTML编码字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeSpecialLetter(String str) {
		if (null == str) {
			return str;
		}
		return str.replaceAll("\\&(?!#)", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("'", "&#39;").replaceAll("!", "&#33;")
				.replaceAll(":", "&#58;").replaceAll("\\\\", "&#92;").replaceAll("/", "&#47;").replaceAll("%", "&#37;").replaceAll("\\(", "&#40").replaceAll("\\)", "&#41").replaceAll("=", "&#61;");
	}

	/**
	 * 校验jsp输入非法后的页面回退处理
	 * 
	 * @param out
	 * @param message
	 */
	public static void invalidInputCope(javax.servlet.jsp.JspWriter out, String message) {
		if (null == out || null == message) {
			return;
		}
		try {
			out.println("<script type='text/javascript'>");
			out.println("alert(\"" + message + "点击【确定】返回上一页。\");");
			out.println("window.history.back();");
			out.println("</script>");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	 * @param tag 类型 String tag词
	 * @return 类型 String 将tag加a标签的链接
	 */
	public static String tagToHtml(String tag) throws Exception{
		try {
			if (StringUtil.isNotEmpty(tag)) {
				String[] tagArr = tag.split(" ");
				String tagHtml = "";
				for (String str : tagArr) {
					ZCTagSchema tZCTagSchema = new ZCTagSchema();
					ZCTagSet tZCTagSet = tZCTagSchema.query(new QueryBuilder("where tag= '" + str + "' "));
					
					String taglink;
					if(tZCTagSet.get(0)!=null){
						
						String tempStr = StringUtil.getLexiconLink(str);
						
						if(!isEmpty(tempStr)){
							taglink = tempStr;
						}else{
							taglink = tZCTagSet.get(0).getLinkURL();
						}
						tagHtml += "<a href=\"" + taglink +"\""+"target=\"_blank\">" + str + "</a>&nbsp;&nbsp;";
					}else{
					tagHtml=tag;	
					}
				}
				return tagHtml;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("从数据库读取数据失败");
		}
		return tag;
	}
	
	/**
	 * 去掉小数尾巴多余的0
	 * @param s
	 * @return
	 */
	public static String subZeroAndDot(String s){  
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }
        return s;  
    }
	
	public static String tagToHtml1(String tag) throws Exception{
		try {
			if (StringUtil.isNotEmpty(tag)) {
				String[] tagArr = tag.split(" ");
				String tagHtml = "";
				String str=tagArr[0];
				
					ZCTagSchema tZCTagSchema = new ZCTagSchema();
					ZCTagSet tZCTagSet = tZCTagSchema.query(new QueryBuilder("where tag= '" + str + "' "));
					
					String taglink;
					if(tZCTagSet.get(0)!=null){
						
						String tempStr = StringUtil.getLexiconLink(str);
						
						if(!isEmpty(tempStr)){
							taglink = tempStr;
						}else{
							taglink = tZCTagSet.get(0).getLinkURL();
						}
						tagHtml +=taglink;
					}else{
					tagHtml=tag;	
					}
				
				return tagHtml;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("从数据库读取数据失败");
		}
		return tag;
	}
	
	/**
	 * 通过 关键字 在词库查找对应链接.
	 * @param str
	 * @return
	 * @throws Exception 
	 */
	public static String getLexiconLink(String str) throws Exception{
		
		String link = null;
		
			GetDBdata db = new GetDBdata();
			
			try {
				
				String queryKeyWord = "select keyword,linkurl,modifytime from ZCKeyword z,(select max(prioritylevel) as cklevel from ZCKeyword where  keyword = '"+str+"' ) z1  where z.prioritylevel >= 5 and z.prioritylevel = z1.cklevel and z.keyword = '"+str+"' order by length(linkurl) desc,modifytime desc";
				
				List<HashMap<String, String>> keyWordList =  db.query(queryKeyWord);
				
				for(Map<String, String> map : keyWordList){
					if(map.get("Keyword").equals(str)){
						link = map.get("LinkUrl");
						return link;
					}
				}
				
			} catch (Exception e) {
				logger.error("通过 关键字 在词库查找对应链接 getLexiconLink() ："+e.getMessage(), e);
			}
			
		return link;
	}
	/**
	 * 清除带有UTF-8排版用空格C2A0,是 UTF8里的排版用空格,和头尾空格trim()
	 */
	public static String trim(String src) {
		if(src!= null&&src.contains(" ")){
			src=src.replaceAll(" ", " ");
		}
		return src.trim();
	}
	/**
	 * 
	* @Title: isInteger 
	* @Description: TODO(判断字符串中是否是非负整数) 
	* @return boolean    返回类型 
	* @author zhangjing
	 */
	public static boolean isInteger(String value) {
		try {
			int val = Integer.parseInt(value);
			if (val >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * 
	 * @Title: isPositiveInteger 
	 * @Description: TODO(判断字符串中是否是正整数) 
	 * @return boolean    返回类型 
	 * @author zhangjing
	 */
	public static boolean isPositiveInteger(String value) {
		try {
			int val = Integer.parseInt(value);
			if (val > 0) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * 
	* @Title: isDouble 
	* @Description: TODO(判断字符串中是否是非负浮点数) 
	* @return boolean    返回类型 
	* @author zhangjing
	 */
	public static boolean isDouble(String value) {
		try {
			double val = Double.parseDouble(value);
			if (val >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * 
	* @Title: changeStringToInt 
	* @Description: TODO(将string转化为整型，截取掉小数部分) 
	* @return boolean    返回类型 
	* @author zhangjing
	 */
	public static int changeStringToInt(String value) {
		try {
			if (value == null || value.length() == 0 || ("null".equals(value.toLowerCase()))) {
				return 0;
			} else if (value.indexOf(".") != -1) {
				value = value.substring(0, value.indexOf("."));
				return Integer.parseInt(value);
			} else {
				return Integer.parseInt(value);
			}
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	/**
	 * 
	* @Title: isNullToZero 
	* @Description: TODO(null和非数字类型数据转换为0) 
	* @return int    返回类型 
	* @author
	 */
	public static String isNullToZero(String value) {
		if (isEmpty(value)) {
			return "0";
		} else {
			try {
				double val = Double.parseDouble(value);
				return String.valueOf(val);
			} catch (NumberFormatException e) {
				return "0";
			}
		}
	}
	/**
	 * 
	* @Title: isLong 
	* @Description: TODO(判断字符串中是否是非负Long) 
	* @return boolean    返回类型 
	* @author zhangjing
	 */
	public static boolean isLong(String value) {
		try {
			Long val = Long.parseLong(value);
			if (val >= 0) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 将字符串补数,将sourString的<br>后面</br>用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
	 * <p><b>Example: </b><p>
	 * <p>RCh("Minim", "0", 10) returns "Minim00000"<p>
	 * @param sourString 源字符串
	 * @param cChar 补数用的字符
	 * @param cLen 字符串的目标长度
	 * @return 字符串
	 */
	public static String RCh(String sourString, String cChar, int cLen) {
		int tLen = sourString.trim().length();
		StringBuffer tReturn = new StringBuffer();
		if (tLen >= cLen)
			return sourString.trim();
		
		for (int i = 0, iMax = cLen - tLen; i < iMax; i++) {
			tReturn.append(cChar);
		}
		
		return sourString.trim() + tReturn.toString();
	}

	/**
	 * 将字符串补数,将sourString的<br>前面</br>用cChar补足cLen长度的字符串,如果字符串超长，则不做处理
	 * <p><b>Example: </b><p>
	 * <p>LCh("Minim", "0", 10) returns "00000Minim"<p>
	 * @param sourString 源字符串
	 * @param cChar 补数用的字符
	 * @param cLen 字符串的目标长度
	 * @return 字符串
	 */
	public static String LCh(String sourString, String cChar, int cLen) {
		int tLen = sourString.trim().length();
		if (tLen >= cLen)
			return sourString.trim();
		
		StringBuffer tempStr = new StringBuffer(sourString.trim());
		tempStr = tempStr.reverse();
		tempStr = new StringBuffer(RCh(tempStr.toString(), cChar, cLen));
		
		return tempStr.reverse().toString();
	}
	
	/**
	 * replaceBlank:去除回车换行. <br/>
	 * @author wwy
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {

		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	/**
	 * 去掉emoji表情
	 * @param str
	 * @return
	 */
	public static String deleteEmoji(String str) {
		if (isNotEmpty(str)) {
			Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
	        Matcher emojiMatcher = emoji.matcher(str);
	        if ( emojiMatcher.find())
	        {
	        	str = emojiMatcher.replaceAll("");
	        }
		}
		
		Pattern pattern = Pattern.compile(unicodeReg);
		StringBuffer sbBuffer = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			String temp = String.valueOf(c);
			Matcher matcher = pattern.matcher(temp);
			if (matcher.find()) {
				sbBuffer.append(temp);
			} else {
				sbBuffer.append("□");
			}
		}
		
		return str;
	}
	
	public final static String unicodeReg= "["+  
            "\u4E00-\u9FBF"+//：CJK 统一表意符号 (CJK Unified Ideographs)  
            "\u4DC0-\u4DFF"+//：易经六十四卦符号 (Yijing Hexagrams Symbols)  
            "\u0000-\u007F"+//：C0控制符及基本拉丁文 (C0 Control and Basic Latin)  
            "\u0080-\u00FF"+//：C1控制符及拉丁：补充-1 (C1 Control and Latin 1 Supplement)  
            "\u0100-\u017F"+//：拉丁文扩展-A (Latin Extended-A)  
            "\u0180-\u024F"+//：拉丁文扩展-B (Latin Extended-B)  
            "\u0250-\u02AF"+//：国际音标扩展 (IPA Extensions)  
            "\u02B0-\u02FF"+//：空白修饰字母 (Spacing Modifiers)  
            "\u0300-\u036F"+//：结合用读音符号 (Combining Diacritics Marks)  
            "\u0370-\u03FF"+//：希腊文及科普特文 (Greek and Coptic)  
            "\u0400-\u04FF"+//：西里尔字母 (Cyrillic)  
            "\u0500-\u052F"+//：西里尔字母补充 (Cyrillic Supplement)  
            "\u0530-\u058F"+//：亚美尼亚语 (Armenian)  
            "\u0590-\u05FF"+//：希伯来文 (Hebrew)  
            "\u0600-\u06FF"+//：阿拉伯文 (Arabic)  
            "\u0700-\u074F"+//：叙利亚文 (Syriac)  
            "\u0750-\u077F"+//：阿拉伯文补充 (Arabic Supplement)  
            "\u0780-\u07BF"+//：马尔代夫语 (Thaana)  
            //"\u07C0-\u077F"+//：西非书面语言 (N'Ko)  
            "\u0800-\u085F"+//：阿维斯塔语及巴列维语 (Avestan and Pahlavi)  
            "\u0860-\u087F"+//：Mandaic  
            "\u0880-\u08AF"+//：撒马利亚语 (Samaritan)  
            "\u0900-\u097F"+//：天城文书 (Devanagari)  
            "\u0980-\u09FF"+//：孟加拉语 (Bengali)  
            "\u0A00-\u0A7F"+//：锡克教文 (Gurmukhi)  
            "\u0A80-\u0AFF"+//：古吉拉特文 (Gujarati)  
            "\u0B00-\u0B7F"+//：奥里亚文 (Oriya)  
            "\u0B80-\u0BFF"+//：泰米尔文 (Tamil)  
            "\u0C00-\u0C7F"+//：泰卢固文 (Telugu)  
            "\u0C80-\u0CFF"+//：卡纳达文 (Kannada)  
            "\u0D00-\u0D7F"+//：德拉维族语 (Malayalam)  
            "\u0D80-\u0DFF"+//：僧伽罗语 (Sinhala)  
            "\u0E00-\u0E7F"+//：泰文 (Thai)  
            "\u0E80-\u0EFF"+//：老挝文 (Lao)  
            "\u0F00-\u0FFF"+//：藏文 (Tibetan)  
            "\u1000-\u109F"+//：缅甸语 (Myanmar)  
            "\u10A0-\u10FF"+//：格鲁吉亚语 (Georgian)  
            "\u1100-\u11FF"+//：朝鲜文 (Hangul Jamo)  
            "\u1200-\u137F"+//：埃塞俄比亚语 (Ethiopic)  
            "\u1380-\u139F"+//：埃塞俄比亚语补充 (Ethiopic Supplement)  
            "\u13A0-\u13FF"+//：切罗基语 (Cherokee)  
            "\u1400-\u167F"+//：统一加拿大土著语音节 (Unified Canadian Aboriginal Syllabics)  
            "\u1680-\u169F"+//：欧甘字母 (Ogham)  
            "\u16A0-\u16FF"+//：如尼文 (Runic)  
            "\u1700-\u171F"+//：塔加拉语 (Tagalog)  
            "\u1720-\u173F"+//：Hanunóo  
            "\u1740-\u175F"+//：Buhid  
            "\u1760-\u177F"+//：Tagbanwa  
            "\u1780-\u17FF"+//：高棉语 (Khmer)  
            "\u1800-\u18AF"+//：蒙古文 (Mongolian)  
            "\u18B0-\u18FF"+//：Cham  
            "\u1900-\u194F"+//：Limbu  
            "\u1950-\u197F"+//：德宏泰语 (Tai Le)  
            "\u1980-\u19DF"+//：新傣仂语 (New Tai Lue)  
            "\u19E0-\u19FF"+//：高棉语记号 (Kmer Symbols)  
            "\u1A00-\u1A1F"+//：Buginese  
            "\u1A20-\u1A5F"+//：Batak  
            "\u1A80-\u1AEF"+//：Lanna  
            "\u1B00-\u1B7F"+//：巴厘语 (Balinese)  
            "\u1B80-\u1BB0"+//：巽他语 (Sundanese)  
            "\u1BC0-\u1BFF"+//：Pahawh Hmong  
            "\u1C00-\u1C4F"+//：雷布查语(Lepcha)  
            "\u1C50-\u1C7F"+//：Ol Chiki  
            "\u1C80-\u1CDF"+//：曼尼普尔语 (Meithei/Manipuri)  
            "\u1D00-\u1D7F"+//：语音学扩展 (Phone tic Extensions)  
            "\u1D80-\u1DBF"+//：语音学扩展补充 (Phonetic Extensions Supplement)  
            "\u1DC0-\u1DFF"+//结合用读音符号补充 (Combining Diacritics Marks Supplement)  
            "\u1E00-\u1EFF"+//：拉丁文扩充附加 (Latin Extended Additional)  
            "\u1F00-\u1FFF"+//：希腊语扩充 (Greek Extended)  
            "\u2000-\u206F"+//：常用标点 (General Punctuation)  
            "\u2070-\u209F"+//：上标及下标 (Superscripts and Subscripts)  
            "\u20A0-\u20CF"+//：货币符号 (Currency Symbols)  
            "\u20D0-\u20FF"+//：组合用记号 (Combining Diacritics Marks for Symbols)  
            "\u2100-\u214F"+//：字母式符号 (Letterlike Symbols)  
            "\u2150-\u218F"+//：数字形式 (Number Form)  
            "\u2190-\u21FF"+//：箭头 (Arrows)  
            "\u2200-\u22FF"+//：数学运算符 (Mathematical Operator)  
            "\u2300-\u23FF"+//：杂项工业符号 (Miscellaneous Technical)  
            "\u2400-\u243F"+//：控制图片 (Control Pictures)  
            "\u2440-\u245F"+//：光学识别符 (Optical Character Recognition)  
            "\u2460-\u24FF"+//：封闭式字母数字 (Enclosed Alphanumerics)  
            "\u2500-\u257F"+//：制表符 (Box Drawing)  
            "\u2580-\u259F"+//：方块元素 (Block Element)  
            "\u25A0-\u25FF"+//：几何图形 (Geometric Shapes)  
            "\u2600-\u26FF"+//：杂项符号 (Miscellaneous Symbols)  
            "\u2700-\u27BF"+//：印刷符号 (Dingbats)  
            "\u27C0-\u27EF"+//：杂项数学符号-A (Miscellaneous Mathematical Symbols-A)  
            "\u27F0-\u27FF"+//：追加箭头-A (Supplemental Arrows-A)  
            "\u2800-\u28FF"+//：盲文点字模型 (Braille Patterns)  
            "\u2900-\u297F"+//：追加箭头-B (Supplemental Arrows-B)  
            "\u2980-\u29FF"+//：杂项数学符号-B (Miscellaneous Mathematical Symbols-B)  
            "\u2A00-\u2AFF"+//：追加数学运算符 (Supplemental Mathematical Operator)  
            "\u2B00-\u2BFF"+//：杂项符号和箭头 (Miscellaneous Symbols and Arrows)  
            "\u2C00-\u2C5F"+//：格拉哥里字母 (Glagolitic)  
            "\u2C60-\u2C7F"+//：拉丁文扩展-C (Latin Extended-C)  
            "\u2C80-\u2CFF"+//：古埃及语 (Coptic)  
            "\u2D00-\u2D2F"+//：格鲁吉亚语补充 (Georgian Supplement)  
            "\u2D30-\u2D7F"+//：提非纳文 (Tifinagh)  
            "\u2D80-\u2DDF"+//：埃塞俄比亚语扩展 (Ethiopic Extended)  
            "\u2E00-\u2E7F"+//：追加标点 (Supplemental Punctuation)  
            "\u2E80-\u2EFF"+//：CJK 部首补充 (CJK Radicals Supplement)  
            "\u2F00-\u2FDF"+//：康熙字典部首 (Kangxi Radicals)  
            "\u2FF0-\u2FFF"+//：表意文字描述符 (Ideographic Description Characters)  
            "\u3000-\u303F"+//：CJK 符号和标点 (CJK Symbols and Punctuation)  
            "\u3040-\u309F"+//：日文平假名 (Hiragana)  
            "\u30A0-\u30FF"+//：日文片假名 (Katakana)  
            "\u3100-\u312F"+//：注音字母 (Bopomofo)  
            "\u3130-\u318F"+//：朝鲜文兼容字母 (Hangul Compatibility Jamo)  
            "\u3190-\u319F"+//：象形字注释标志 (Kanbun)  
            "\u31A0-\u31BF"+//：注音字母扩展 (Bopomofo Extended)  
            "\u31C0-\u31EF"+//：CJK 笔画 (CJK Strokes)  
            "\u31F0-\u31FF"+//：日文片假名语音扩展 (Katakana Phonetic Extensions)  
            "\u3200-\u32FF"+//：封闭式 CJK 文字和月份 (Enclosed CJK Letters and Months)  
            "\u3300-\u33FF"+//：CJK 兼容 (CJK Compatibility)  
            "\u3400-\u4DBF"+//：CJK 统一表意符号扩展 A (CJK Unified Ideographs Extension A)  
            "\u4DC0-\u4DFF"+//：易经六十四卦符号 (Yijing Hexagrams Symbols)  
            "\u4E00-\u9FBF"+//：CJK 统一表意符号 (CJK Unified Ideographs)  
            "\uA000-\uA48F"+//：彝文音节 (Yi Syllables)  
            "\uA490-\uA4CF"+//：彝文字根 (Yi Radicals)  
            "\uA500-\uA61F"+//：Vai  
            "\uA660-\uA6FF"+//：统一加拿大土著语音节补充 (Unified Canadian Aboriginal Syllabics Supplement)  
            "\uA700-\uA71F"+//：声调修饰字母 (Modifier Tone Letters)  
            "\uA720-\uA7FF"+//：拉丁文扩展-D (Latin Extended-D)  
            "\uA800-\uA82F"+//：Syloti Nagri  
            "\uA840-\uA87F"+//：八思巴字 (Phags-pa)  
            "\uA880-\uA8DF"+//：Saurashtra  
            "\uA900-\uA97F"+//：爪哇语 (Javanese)  
            "\uA980-\uA9DF"+//：Chakma  
            "\uAA00-\uAA3F"+//：Varang Kshiti  
            "\uAA40-\uAA6F"+//：Sorang Sompeng  
            "\uAA80-\uAADF"+//：Newari  
            "\uAB00-\uAB5F"+//：越南傣语 (Vi?t Thái)  
            "\uAB80-\uABA0"+//：Kayah Li  
            "\uAC00-\uD7AF"+//：朝鲜文音节 (Hangul Syllables)  
            //"\uD800-\uDBFF"+//：High-half zone of UTF-16  
            //"\uDC00-\uDFFF"+//：Low-half zone of UTF-16  
            "\uE000-\uF8FF"+//：自行使用区域 (Private Use Zone)  
            "\uF900-\uFAFF"+//：CJK 兼容象形文字 (CJK Compatibility Ideographs)  
            "\uFB00-\uFB4F"+//：字母表达形式 (Alphabetic Presentation Form)  
            "\uFB50-\uFDFF"+//：阿拉伯表达形式A (Arabic Presentation Form-A)  
            "\uFE00-\uFE0F"+//：变量选择符 (Variation Selector)  
            "\uFE10-\uFE1F"+//：竖排形式 (Vertical Forms)  
            "\uFE20-\uFE2F"+//：组合用半符号 (Combining Half Marks)  
            "\uFE30-\uFE4F"+//：CJK 兼容形式 (CJK Compatibility Forms)  
            "\uFE50-\uFE6F"+//：小型变体形式 (Small Form Variants)  
            "\uFE70-\uFEFF"+//：阿拉伯表达形式B (Arabic Presentation Form-B)  
            "\uFF00-\uFFEF"+//：半型及全型形式 (Halfwidth and Fullwidth Form)  
            "\uFFF0-\uFFFF]";//：特殊 (Specials);
	
	public static void main(String[] args) {

		System.out.println(deleteEmoji("/:,@-D哎呦不错哦"));
	}
}

package com.sinosoft.framework.utility;

import com.sinosoft.framework.Config;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
/**
 * 
 * @ClassName: WeixiManage
 * @Description: TODO(维析关于支付成功后的数据处理--包括page和action)
 * @author zhangjing
 * @date 2014-2-20 下午05:17:17
 * 
 */
public class WeixiManage {
	private static final Logger logger = LoggerFactory.getLogger(WeixiManage.class);

	// 维析服务器地址
	private static String TraceURL = "http://121.199.20.156/VL/Trace?";

	/**
	 * 
	 * @Title: sendWeixiPaySuccess
	 * @Description: TODO(支付成功后向维析发送数据)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	public static String sendWeixiPaySuccess(String vlid_1001, Map<String, String> map) {
		// 时间戳有配对
		String time_A = String.valueOf(System.currentTimeMillis());
		// 时间戳有配对
		String time_B = String.valueOf(System.currentTimeMillis());
		// page数据
		String param_page = pagaData(vlid_1001, time_A, time_B, map);
		// action数据
		String param_action = actionData(vlid_1001, time_A, time_B, map);
		//CPSConfig.properties  资源文件中取得是否向服务器发送数据的标志位   Y：为发送  N：不发送
		if("Y".equals(Config.map_property.get("trace_flag"))){
			// flag参数:
			// 1： 表示page和action数据全部发送
			// 2： 表示单发page数据
			// 3：表示单发action数据
			if ("1".equals(String.valueOf(map.get("flag")))) {
				// 发送page数据
				sendWeiXiData(TraceURL, param_page);
				logger.info("维析所需param_page 的数据为：{}", param_page);
				// 发送action数据
				sendWeiXiData(TraceURL, param_action);
				logger.info("维析所需param_action的数据位：{}", param_action);
			} else if ("2".equals(String.valueOf(map.get("flag")))) {
				// 发送page数据
				sendWeiXiData(TraceURL, param_page);
				logger.info("维析所需param_page 的数据为：{}", param_page);
			} else if ("3".equals(String.valueOf(map.get("flag")))) {
				// 发送action数据
				sendWeiXiData(TraceURL, param_action);
				logger.info("维析所需param_action的数据位：{}", param_action);
			}else{
				StackTraceElement stack[] = Thread.currentThread().getStackTrace();
				Object[] argArr ={stack[2].getClassName(), stack[2].getMethodName(), stack[2].getLineNumber(), stack[2].getFileName()};
				logger.info("维析发送数据无判断标志位，调用者信息（一级）---类名：{};方法名：{};行数：{};文件：{}", argArr);
				argArr = new Object[]{stack[3].getClassName(), stack[3].getMethodName(), stack[3].getLineNumber(), stack[3].getFileName()};
				logger.info("维析发送数据无判断标志位，调用者信息（一级）---类名：{};方法名：{};行数：{};文件：{}", argArr);
			}
		}else{
			if(!("N".equals(Config.map_property.get("trace_flag")))){
				logger.error("CPSConfig.properties资源文件中关于维析标志位的配置有误，不发送数据配置应为“N”：");
			}
		}
		return "success";
	}

	/**
	 * 
	 * @Title: manage
	 * @Description: TODO(page数据组装)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private static String pagaData(String vlid_1001, String time_A, String time_B,Map<String, String> map) {
		/**
		 * vlid_1001 结构详解（前台cookie传递的参数）
		 * 中划线（6位）+ID文字列（8位）+有效期限制（10位）+广告有效期限制（10位
		 * ）+RFM开始时间（10位）+初始访问时间（10位）+上次访问时间（10位）+
		 * 访问次数（3位）+日数（3位）+间隔（3位）+总PV（3位）+重复数（3位）
		 */
		// 固定常量值
		String g = "g=/1";
		
		String c = "c="+Config.map_property.get("wj_channelsn_flag");
		//渠道编码
		String channelsn=map.get("channelsn");
		//区分是否为wap站订单
		if("wap".equals(channelsn)){
			c = "c="+Config.map_property.get("wap_channelsn_flag");
		}
		String r = "r=http%3A//www.kaixinbao.com/";
		String orderid = "W" + map.get("orderid");
		String l="";
		if(StringUtil.isNotEmpty(map.get("orderid"))){
			//页面url
			 l = "l=http%3A//www.kaixinbao.com/"+StringUtil.noNull(map.get("url"))+"?post_id="+encodeUtil(orderid);
		}else{
			//页面url
			 l = "l=http%3A//www.kaixinbao.com/"+StringUtil.noNull(map.get("url"));
		}
		//html页面title内容
		String t = "t="+StringUtil.noNull(map.get("title"));
		String k = "k=true";
		String sf = "sf=true";
		String j = "j=true";
		String w = "w=1280";
		String h = "h=1024";
		String d = "d=32";
		String o = "o=http%3A";
		// cookie中的动态参数
		String cval="";
		if (StringUtil.isEmpty(vlid_1001)) {
			cval = "cval=2:1387436796_NoCookie_1392779138";
		}else{
			// 访问次数（3位）+：+初始访问时间（10位）+_+ID文字列（8位）+_+随机时间戳
			cval = "cval=" + changeTo_10(vlid_1001.substring(64, 67)) + ":" + vlid_1001.substring(44, 54) + "_" + vlid_1001.substring(6, 14) + "_" + time_A.substring(3, time_A.length());
			logger.info("维析的vlid_1001的cookie值为：{}", vlid_1001);
			logger.info("访问次数的参数为：{}", vlid_1001.substring(64, 67));
			logger.info("访问次数的参数解析后的值：{}", changeTo_10(vlid_1001.substring(64, 67)));
		}
		String lng = "lng=zh";
		String jt = "jt=" + time_B;
		//分组名称（及weixitrack.js中的正则表达式的分组名称）
		String u1 = "u1="+StringUtil.noNull(map.get("groupname"));
		// 时间戳无配对
		String time_C = String.valueOf(System.currentTimeMillis());
		String jd = "jd=" + time_C + "_1";
		// 组装page参数字符串
		String param = g + "&" + c + "&" + r + "&" + l + "&" + t + "&" + k + "&" + sf + "&" + j + "&" + w + "&" + h + "&" + d + "&" + o + "&" + cval + "&" + lng + "&" + jt + "&" + u1 + "&" + jd;
		return param;
	}

	/**
	 * 
	 * @Title: actionData
	 * @Description: TODO(action数据组装)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private static String actionData(String vlid_1001, String time_A, String time_B, Map<String, String> map) {
		/**
		 * vlid_1001 结构详解（前台cookie传递的参数）
		 * 中划线（6位）+ID文字列（8位）+有效期限制（10位）+广告有效期限制（10位
		 * ）+RFM开始时间（10位）+初始访问时间（10位）+上次访问时间（10位）+
		 * 访问次数（3位）+日数（3位）+间隔（3位）+总PV（3位）+重复数（3位）
		 */
		// 固定常量值
		String g = "g=/2";
		String c = "c="+Config.map_property.get("wj_channelsn_flag");
		//渠道编码
		String channelsn=map.get("channelsn");
		//区分是否为wap站订单
		if("wap".equals(channelsn)){
			c = "c="+Config.map_property.get("wap_channelsn_flag");
		}
		String jt = "jt=" + time_B;
		// cookie中的动态参数
		String fpcval="";
		if (StringUtil.isEmpty(vlid_1001)) {
			fpcval = "fpcval=2:1387436796_NoCookie_1392779138";
		}else{
		// 访问次数（3位）+：+初始访问时间（10位）+_+ID文字列（8位）+_+随机时间戳
			fpcval = "fpcval=" + changeTo_10(vlid_1001.substring(64, 67)) + ":" + vlid_1001.substring(44, 54) + "_" + vlid_1001.substring(6, 14) + "_"
				+ time_A.substring(3, time_A.length());
		}
		// 时间戳无配对
		String time_C = String.valueOf(System.currentTimeMillis());
		String jd = "jd=" + time_C + "_1";
		int c1_length = 0;
		int c2_length = 0;
		StringBuffer a1 = new StringBuffer("a1=");
		a1.append(StringUtil.noNull(map.get("a1")));
		String memberid = StringUtil.noNull(map.get("memberid"));
		String orderid ="";
		if(StringUtil.isNotEmpty(map.get("orderid"))){
			orderid = "W" + StringUtil.noNull(map.get("orderid"));
		}
		String totalamout = StringUtil.noNull(map.get("totalamout"));
		//订单总金额是否存在.00的格式数据
		if(totalamout.indexOf(".")!=-1){
			totalamout=totalamout.substring(0,totalamout.indexOf("."));
			logger.info("维析支付成功后发送的数据中，订单总金额的数据为非整数，已做格式化处理，订单号为：{},不合法的金额为：{}", orderid, StringUtil.noNull(map.get("totalamout")));
		}
		String pieces = StringUtil.noNull(map.get("pieces"));
		String productid = StringUtil.noNull(map.get("productid"));
		String riskname = StringUtil.noNull(map.get("riskname"));
		String companyname = StringUtil.noNull(map.get("companycode"));
		String productname = StringUtil.noNull(map.get("productname"));
		// a1部分
		if (StringUtil.isEmpty(memberid)) {
			memberid = "noLoginMember";
		}
		a1.append(changeTo_62(lengthUtil(encodeUtil(memberid)), 2));
		c1_length += lengthUtil(encodeUtil(memberid));
		a1.append(encodeUtil(memberid));
		// 空位以00补充位数(11位)
		a1.append("0000000000");
		a1.append(changeTo_62(lengthUtil(encodeUtil(productname)), 2));
		c1_length += lengthUtil(encodeUtil(productname));
		a1.append(encodeUtil(productname));
		a1.append("0000000000");
		a1.append(changeTo_62(lengthUtil(encodeUtil(orderid)), 2));
		c1_length += lengthUtil(encodeUtil(orderid));
		a1.append(encodeUtil(orderid));
		// 空位以00补充位数(2位)
		a1.append("0000");
		a1.append(changeTo_62(lengthUtil(encodeUtil(totalamout)), 2));
		c1_length += lengthUtil(encodeUtil(totalamout));
		a1.append(encodeUtil(totalamout));

		a1.append(changeTo_62(lengthUtil(encodeUtil(pieces)), 2));
		c1_length += lengthUtil(encodeUtil(pieces));
		a1.append(encodeUtil(pieces));

		// 空位以00补充最后位数
		a1.append("00");
		// a1的len的和转成62进制
		String c1 = "c1=" + changeTo_62(c1_length, 3);
		// a2部分
		StringBuffer a2 = new StringBuffer("a2=");
		a2.append(StringUtil.noNull(map.get("a2")));
		// 空位以00补充位数(2位)
		a2.append("0000");
		a2.append(changeTo_62(lengthUtil(encodeUtil(productid)), 2));
		c2_length += lengthUtil(encodeUtil(productid));
		a2.append(encodeUtil(productid));
		// 空位以00补充位数(9位)
		a2.append("000000");
		a2.append(changeTo_62(lengthUtil(encodeUtil(productname)), 2));
		c2_length += lengthUtil(encodeUtil(productname));
		a2.append(encodeUtil(productname));
		a2.append("0000000000");
		a2.append(changeTo_62(lengthUtil(encodeUtil(orderid)), 2));
		c2_length += lengthUtil(encodeUtil(orderid));
		a2.append(encodeUtil(orderid));
		// 空位以00补充位数(2位)
		a2.append("0000");
		a2.append(changeTo_62(lengthUtil(encodeUtil(totalamout)), 2));
		c2_length += lengthUtil(encodeUtil(totalamout));
		a2.append(encodeUtil(totalamout));

		a2.append(changeTo_62(lengthUtil(encodeUtil(pieces)), 2));
		c2_length += lengthUtil(encodeUtil(pieces));
		a2.append(encodeUtil(pieces));

		a2.append(changeTo_62(lengthUtil(encodeUtil(riskname)), 2));
		c2_length += lengthUtil(encodeUtil(riskname));
		a2.append(encodeUtil(riskname));

		a2.append(changeTo_62(lengthUtil(encodeUtil(companyname)), 2));
		c2_length += lengthUtil(encodeUtil(companyname));
		a2.append(encodeUtil(companyname));
		// 空位以00补充最后位数
		a2.append("00");
		// a2的len的和转成62进制
		String c2 = "c2=" + changeTo_62(c2_length, 3);
		// 组装action参数字符串
		String param = g + "&" + c + "&" + jt + "&" + fpcval + "&" + jd + "&" + a1 + "&" + c1 + "&" + a2 + "&" + c2;
		return param;
	}

	/**
	 * 
	 * @Title: sendWeiXiData
	 * @Description: TODO(发送请求)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	private static void sendWeiXiData(String url, String param) {
//		System.setProperty("http.proxyHost", "localhost"); 
//		System.setProperty("http.proxyPort", "8888"); 
//		System.setProperty("https.proxyHost", "localhost");
//		System.setProperty("https.proxyPort", "8888");
		HttpClient client = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true));
		GetMethod get = new GetMethod(url + param);
		try {
			
			// 把参数按照UTF-8格式编码
			// URI uri = new URI(param, false, "UTF-8");
			// param = uri.toString();
			// PostMethod post = new PostMethod(url + param);
			// post.setRequestBody(body)
			
			client.executeMethod(get);
		} catch (Exception e) {
			logger.error("支付成功维析推送数据发生异常!" + e.getMessage(), e);
		} finally {
			get.releaseConnection();
		}

	}

	/**
	 * 
	 * @Title: lengthUtil
	 * @Description: TODO(获取字符串长度)
	 * @return int 返回类型
	 * @author zhangjing
	 */
	private static int lengthUtil(String str) {
		if ("".equals(str) || str == null) {
			return 0;
		} else {
			return str.length();
		}
	}

	/**
	 * 
	 * @Title: encodeUtil
	 * @Description: TODO(解码)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private static String encodeUtil(String str) {
		if ("".equals(str) || str == null) {
			return "";
		} else {
			try {
				return URLEncoder.encode(str, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
				return "";
			}
		}
	}

	/**
	 * 
	 * @Title: changeTo_64
	 * @Description: TODO(转换62进制)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private static String changeTo_62(int r, int figure) {
		String str = "";
		int d;
		String strZero = "";
		String strRet = "";
		do {
			d = r % 62;
			if (d >= 0 && d <= 9) {
				// 数字(0～9)
				str = (char) (d + "0".codePointAt(0)) + str;
			} else if (d >= 10 && d <= 35) {
				// 小文字(a～z)
				str = (char) (d - 10 + "a".codePointAt(0)) + str;
			} else {
				// 大文字(A～Z)
				str = (char) (d - 36 + "A".codePointAt(0)) + str;
			}
			r = (int) Math.floor(r / 62);
		} while (r > 0);
		int i;
		for (i = 0; i < figure; i++) {
			strZero = strZero + '0';
		}
		strRet = strZero + str;
		str = strRet.substring(str.length());
		return str;
	}

	/**
	 * 
	 * @Title: changeTo_10 
	 * @Description: TODO(62进制转换成10进制)
	 * @return int 返回类型
	 * @author zhangjing
	 */
	private static int changeTo_10(String str) {
		int v, c, i;
		int r = 0;
		for (i = 0; i < str.length(); i++) {
			c = str.codePointAt(i);
			if (c >= "0".codePointAt(0) && c <= "9".codePointAt(0)) {
				v = c - "0".codePointAt(0);
			} else if (c >= "a".codePointAt(0) && c <= "z".codePointAt(0)) {
				v = c - "a".codePointAt(0) + 10;
			} else if (c >= "A".codePointAt(0) && c <= "Z".codePointAt(0)) {
				v = c - "A".codePointAt(0) + 36;
			} else {
				break;
			}
			r = r * 62 + v;
		}
		return r;
	}
}

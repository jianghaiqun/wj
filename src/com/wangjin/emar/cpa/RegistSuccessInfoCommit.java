package com.wangjin.emar.cpa;

import cn.com.sinosoft.entity.Member;
import com.sinosoft.framework.GetDBdata;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistSuccessInfoCommit {
	private static final Logger logger = LoggerFactory.getLogger(RegistSuccessInfoCommit.class);
	public static void infoCommitForEmar(HttpServletRequest request,
			Member member) {
		try {
			Cookie[] cookies = request.getCookies();
			String src = "";
			String wi = "";
			String cid = "";
			if (cookies != null && cookies.length > 0) {
				for (Cookie c : cookies) {
					if ("src".equals(c.getName())) {
						src = c.getValue();
					} else if ("wi".equals(c.getName())) {
						wi = c.getValue();
					} else if ("cid_cpa".equals(c.getName())) {
						cid = c.getValue();
					}
				}
			}
			if ("emar_cpa".equals(src)) {
				String an = member.getId();
				String ana = "";
				if (StringUtils.isNotBlank(member.getUsername())) {
					ana = member.getUsername();
				} else if (StringUtils.isNotBlank(member.getEmail())) {
					ana = member.getEmail();
				} else if (StringUtils.isNotBlank(member.getMobileNO())) {
					ana = member.getMobileNO();
				}
				String ct = "basic";
				int ta = 1;
				Date currentTime = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String date = formatter.format(currentTime);
				String ip = member.getRegisterIp();
				GetDBdata db = new GetDBdata();
				try {
					String sql_insert = "insert into cpa(cid,wi,an,ana,ct,ta,sd,ip) values('"
							+ cid
							+ "','"
							+ wi
							+ "','"
							+ an
							+ "','"
							+ ana
							+ "','"
							+ ct
							+ "','"
							+ ta
							+ "','"
							+ date
							+ "','"
							+ ip + "') ";
					db.execUpdateSQL(sql_insert, null);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				try {
					// 实时传送往亿起发推送注册信息
					String url = "http://o.yiqifa.com/servlet/handleCpaIn";
					StringBuffer sb = new StringBuffer();
					sb.append("cid=" + cid);
					sb.append("&wi=" + wi);
					sb.append("&an=" + an);
					sb.append("&ana=" + ana);
					sb.append("&ct=" + ct);
					sb.append("&ta=" + ta);
					sb.append("&sd="
							+ URLEncoder.encode(date.toString(), "utf-8"));
					sb.append("&ta=" + ta);
					sb.append("&ip=" + ip);
					SendURLPost(url, sb.toString());
				} catch (UnsupportedEncodingException e) {
					logger.error("发送给亿起发的实时数据出现异常！" + e.getMessage(), e);
				} catch (IOException e) {
					logger.error("发送给亿起发的实时数据出现异常！" + e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param urlStr
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	private static void SendURLPost(String urlStr, String param)
			throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection(); // 获取连接
		httpURLConnection.setRequestMethod("POST"); // 设置请求方法为POST, 也可以为GET
		httpURLConnection.setDoOutput(true);
		OutputStream os = httpURLConnection.getOutputStream();
		os.write(param.getBytes());
		os.flush();
		os.close();
		httpURLConnection.getInputStream();
	}
}

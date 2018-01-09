package com.wangjin.emar.cps;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDOrder.SDOrderStatus;
import cn.com.sinosoft.entity.SDOrder.SDPayStatus;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;


public class OrderInfoCommit {
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoCommit.class);
	
	public static void infoCommitForEmar(HttpServletRequest request, SDOrder order , SDInformation info) {
		try {
			Cookie[] cookies = request.getCookies();
			String src = "";
			String channel = "";
			String wi = "";
			String cid = "";
			
			String inswindow_src = "";
			String inswindow_channel = "";
			
			String fnMiao_source = "";
			
			String jinTi_src = "";
			
			String participator="";
			
			String qq_login="";
			String QQCB_headshow="";
			if (cookies != null && cookies.length > 0) {
				for (Cookie c : cookies) {
					if ("src".equals(c.getName())) {
						src = c.getValue();
					} else if ("channel".equals(c.getName())) {
						channel = c.getValue();
					} else if ("wi".equals(c.getName())) {
						wi = c.getValue();
					} else if ("cid_cps".equals(c.getName())) {
						cid = c.getValue();
					} else if ("inswindow_src".equals(c.getName())){
						inswindow_src = c.getValue();
					} else if ("inswindow_channel".equals(c.getName())){
						inswindow_channel = c.getValue();
					} else if ("fnMiao_source".equals(c.getName())){
						fnMiao_source = c.getValue();
					} else if ("jinTi_src".equals(c.getName())){
						jinTi_src = c.getValue();
					} else if ("participator".equals(c.getName())){
						participator = c.getValue();
					}else if ("participator".equals(c.getName())){
						participator = c.getValue();
					}else if(Member.LOGIN_MEMBER_ID_SESSION_NAME.equals(c.getName())){
						String Send_QQCB_Flag=Config.map_property.get("Send_QQCB_Flag");
						if("Y".equals(Send_QQCB_Flag)){
							qq_login = c.getValue();
						}else{
							qq_login="";
						}
					}else if("QQCB_headshow".equals(c.getName())){
							QQCB_headshow = c.getValue();
					}
				}
			}
			
			if ("emar_cps".equals(src) && "cps".equals(channel)) {
				String on = order.getOrderSn();
				int ta = 1;
				String pna = info.getProductName();
				String pn = info.getProductId();
				String dt = "web";
				BigDecimal pp = order.getTotalAmount();
				Date sd = order.getCreateDate();
				SDOrderStatus os = order.getOrderStatus();
				SDPayStatus ps = order.getPayStatus();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				GetDBdata db = new GetDBdata();
				try {
					String sql_insert = "insert into cps  (`id`, `cid`, `wi`, `on`, `ta`, `pna`, `dt`, `pp`, `sd`, `ud`, `os`, `ps`, `pw`, `oo`) values(LAST_INSERT_ID(id),'" + cid
							+ "','" + wi + "','" + on + "','" + ta + "','"
							+ pna + "','" + dt + "','" + pp + "','"
							+ formatter.format(sd) + "','"
							+ "0000-00-00 00:00:00" + "','" + os.toString()
							+ "','" + ps.toString() + "'," + "'','01') ";
					db.execUpdateSQL(sql_insert, null);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				try {
					// 实时传送往亿起发推送订单信息
					String url = Config.map_property.get("yqf_send_url");
					StringBuffer sb = new StringBuffer();
					sb.append("cid=" + cid);
					sb.append("&wi=" + wi);
					sb.append("&on=" + on);
					sb.append("&ta=" + ta);
					sb.append("&pna=" + URLEncoder.encode(pna, "GBK"));
					sb.append("&pn=" + pn);
					sb.append("&dt=" + dt);
					sb.append("&pp=" + pp);
					sb.append("&sd="
							+ URLEncoder.encode(
								formatter.format(sd).toString(), "GBK"));
					sb.append("&os=" + os.toString());
					sb.append("&ps=" + ps.toString());
					sb.append("&pw=");
					sb.append("&far=");
					sb.append("&fav=");
					sb.append("&fac=");
					sb.append("&encoding=GBK");
					logger.info("亿起发数据推送成功");
					SendURLGet(url, sb.toString());
				} catch (UnsupportedEncodingException e) {
					logger.error("发送给亿起发的实时数据出现异常！" + e.getMessage(), e);
				} catch (IOException e) {
					logger.error("发送给亿起发的实时数据出现异常！" + e.getMessage(), e);
				}
				//一起发更新订单状态
				new QueryBuilder(" update sdorders set channelsn = 'cps_01' where ordersn='" + on + "'").executeNoQuery();
			}
			
			if ("inswindow_cps".equals(inswindow_src) && "cps".equals(inswindow_channel)) {
				String on = order.getOrderSn();
				int ta = 1;
				String pna = info.getProductName();
				String dt = "web";
				BigDecimal pp = order.getTotalAmount();
				Date sd = order.getCreateDate();
				SDOrderStatus os = order.getOrderStatus();
				SDPayStatus ps = order.getPayStatus();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				GetDBdata db = new GetDBdata();
				try {
					String sql_insert = "insert into cps  (`id`, `cid`, `wi`, `on`, `ta`, `pna`, `dt`, `pp`, `sd`, `ud`, `os`, `ps`, `pw`, `oo`) values(LAST_INSERT_ID(id),'" 
							+ "','" + "','" + on + "','" + ta + "','"
							+ pna + "','" + dt + "','" + pp + "','"
							+ formatter.format(sd) + "','"
							+ "0000-00-00 00:00:00" + "','" + os.toString()
							+ "','" + ps.toString() + "'," + "'','02') ";
					db.execUpdateSQL(sql_insert, null);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			
			if ("59miao".equals(fnMiao_source)) {
				String on = order.getOrderSn();
				int ta = 1;
				String pna = info.getProductName();
				String dt = "web";
				BigDecimal pp = order.getTotalAmount();
				Date sd = order.getCreateDate();
				SDOrderStatus os = order.getOrderStatus();
				SDPayStatus ps = order.getPayStatus();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				GetDBdata db = new GetDBdata();
				try {
					String sql_insert = "insert into cps  (`id`, `cid`, `wi`, `on`, `ta`, `pna`, `dt`, `pp`, `sd`, `ud`, `os`, `ps`, `pw`, `oo`) values(LAST_INSERT_ID(id),'" 
							+ "','" + "','" + on + "','" + ta + "','"
							+ pna + "','" + dt + "','" + pp + "','"
							+ formatter.format(sd) + "','"
							+ "0000-00-00 00:00:00" + "','" + os.toString()
							+ "','" + ps.toString() + "'," + "'','04') ";
					db.execUpdateSQL(sql_insert, null);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				new QueryBuilder(" update sdorders set channelsn = 'cps_04' where ordersn='" + on + "'").executeNoQuery();
			}
			
			if ("JinTi".equals(jinTi_src)) {
				String on = order.getOrderSn();
				int ta = 1;
				String pna = info.getProductName();
				String dt = "web";
				BigDecimal pp = order.getTotalAmount();
				Date sd = order.getCreateDate();
				SDOrderStatus os = order.getOrderStatus();
				SDPayStatus ps = order.getPayStatus();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				GetDBdata db = new GetDBdata();
				try {
					String sql_insert = "insert into cps  (`id`, `cid`, `wi`, `on`, `ta`, `pna`, `dt`, `pp`, `sd`, `ud`, `os`, `ps`, `pw`, `oo`) values(LAST_INSERT_ID(id),'" 
							+ "','" + "','" + on + "','" + ta + "','"
							+ pna + "','" + dt + "','" + pp + "','"
							+ formatter.format(sd) + "','"
							+ "0000-00-00 00:00:00" + "','" + os.toString()
							+ "','" + ps.toString() + "'," + "'','05') ";
					db.execUpdateSQL(sql_insert, null);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			if ("woso".equals(participator)) {
				String on = order.getOrderSn();
				int ta = 1;
				String pna = info.getProductName();
				String dt = "web";
				BigDecimal pp = order.getTotalAmount();
				Date sd = order.getCreateDate();
				SDOrderStatus os = order.getOrderStatus();
				SDPayStatus ps = order.getPayStatus();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				GetDBdata db = new GetDBdata();
				try {
					String sql_insert = "insert into cps  (`id`, `cid`, `wi`, `on`, `ta`, `pna`, `dt`, `pp`, `sd`, `ud`, `os`, `ps`, `pw`, `oo`) values(LAST_INSERT_ID(id),'" 
							+ "','" + "','" + on + "','" + ta + "','"
							+ pna + "','" + dt + "','" + pp + "','"
							+ formatter.format(sd) + "','"
							+ "0000-00-00 00:00:00" + "','" + os.toString()
							+ "','" + ps.toString() + "'," + "'','06') ";
					db.execUpdateSQL(sql_insert, null);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			//QQ联合登陆，下单后向亿起发推送订单信息
			if ("tencent".equals(qq_login)&&(QQCB_headshow==""||QQCB_headshow==null)) {
				String on = order.getOrderSn();
				int ta = 1;
				String pna = info.getProductName();
				String pn = info.getProductId();
				String dt = "web";
				BigDecimal pp = order.getTotalAmount();
				Date sd = order.getCreateDate();
				SDOrderStatus os = order.getOrderStatus();
				SDPayStatus ps = order.getPayStatus();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				GetDBdata db = new GetDBdata();
				String openID=String.valueOf(request.getSession().getAttribute("qq_openid"));
				try {
					//07代表QQ联合登陆
					String sql_insert = "insert into cps  (`id`, `cid`, `wi`, `on`, `ta`, `pna`, `dt`, `pp`, `sd`, `ud`, `os`, `ps`, `pw`,`openid`, `oo`) values(LAST_INSERT_ID(id),'"+Config.map_property.get("cid")
							+ "','" + Config.map_property.get("wid") + "','" + on + "','" + ta + "','"
							+ pna + "','" + dt + "','" + pp + "','"
							+ formatter.format(sd) + "','"
							+ "0000-00-00 00:00:00" + "','" + os.toString()
							+ "','" + ps.toString() + "'," + "'','"+openID+"','07') ";
					db.execUpdateSQL(sql_insert, null);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				try {
					// 实时传送往亿起发推送订单信息
					String url = Config.map_property.get("yqf_send_url");
					StringBuffer sb = new StringBuffer();
					sb.append("cid="+Config.map_property.get("cid"));
					sb.append("&wid="+Config.map_property.get("wid"));
					sb.append("&qqoid="+openID);
					sb.append("&qqmid=kaixinbao");
					sb.append("&ct=qqlogin003");		
					sb.append("&on="+on);
					sb.append("&ta="+ta);
					sb.append("&pna=" + URLEncoder.encode(pna, "GBK"));
					sb.append("&pn="+pn);
					sb.append("&dt=" + dt);
					sb.append("&pp=" + pp);
					sb.append("&sd="+URLEncoder.encode(formatter.format(sd).toString(), "GBK"));
					sb.append("&os=" + os.toString());
					sb.append("&ps=" + ps.toString());
					sb.append("&pw=");
					sb.append("&far=");
					sb.append("&fav=");
					sb.append("&fac=");
					sb.append("&encoding=GBK");
					logger.info("亿起发数据推送成功");
					SendURLGet(url, sb.toString());
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
	 * 只处理 59秒、亿起发数据
	 * @param request
	 * @param order
	 * @param info
	 */
	public static void infoCommitForEmar(HttpServletRequest request, SDOrder order , SDInformation info
			,String cpsUserId,String cpsUserSource) {
		try {
			Cookie[] cookies = request.getCookies();
			String channel = "";
			String wi = "";
			
			if (cookies != null && cookies.length > 0) {
				for (Cookie c : cookies) {
					if ("channel".equals(c.getName())) {
						channel = c.getValue();
					} else if ("wi".equals(c.getName())) {
						wi = c.getValue();
					} 
				}
			}
			
			if ("emar_cps".equals(cpsUserSource) && "cps".equals(channel)) {
				String on = order.getOrderSn();
				int ta = 1;
				String pna = info.getProductName();
				String pn = info.getProductId();
				String dt = "web";
				BigDecimal pp = order.getTotalAmount();
				Date sd = order.getCreateDate();
				SDOrderStatus os = order.getOrderStatus();
				SDPayStatus ps = order.getPayStatus();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				GetDBdata db = new GetDBdata();
				try {
					String sql_insert = "insert into cps  (`id`, `cid`, `wi`, `on`, `ta`, `pna`, `dt`, `pp`, `sd`, `ud`, `os`, `ps`, `pw`, `oo`) values(LAST_INSERT_ID(id),'" + cpsUserId
							+ "','" + wi + "','" + on + "','" + ta + "','"
							+ pna + "','" + dt + "','" + pp + "','"
							+ formatter.format(sd) + "','"
							+ "0000-00-00 00:00:00" + "','" + os.toString()
							+ "','" + ps.toString() + "'," + "'','01') ";
					db.execUpdateSQL(sql_insert, null);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				try {
					// 实时传送往亿起发推送订单信息
					String url = Config.map_property.get("yqf_send_url");
					StringBuffer sb = new StringBuffer();
					sb.append("cid=" + cpsUserId);
					sb.append("&wi=" + wi);
					sb.append("&on=" + on);
					sb.append("&ta=" + ta);
					sb.append("&pna=" + URLEncoder.encode(pna, "GBK"));
					sb.append("&pn="+pn);
					sb.append("&dt=" + dt);
					sb.append("&pp=" + pp);
					sb.append("&sd="
							+ URLEncoder.encode(
									formatter.format(sd).toString(), "GBK"));
					sb.append("&os=" + os.toString());
					sb.append("&ps=" + ps.toString());
					sb.append("&pw=");
					sb.append("&far=");
					sb.append("&fav=");
					sb.append("&fac=");
					sb.append("&encoding=GBK");
					logger.info("亿起发数据推送成功");
					SendURLGet(url, sb.toString());
				} catch (UnsupportedEncodingException e) {
					logger.error("发送给亿起发的实时数据出现异常！" + e.getMessage(), e);
				} catch (IOException e) {
					logger.error("发送给亿起发的实时数据出现异常！" + e.getMessage(), e);
				}
				//一起发更新订单状态
				new QueryBuilder(" update sdorders set channelsn = 'cps_01' where ordersn='" + on + "'").executeNoQuery();
			}
			
			if ("59miao".equals(cpsUserSource)) {
				String on = order.getOrderSn();
				int ta = 1;
				String pna = info.getProductName();
				String dt = "web";
				BigDecimal pp = order.getTotalAmount();
				Date sd = order.getCreateDate();
				SDOrderStatus os = order.getOrderStatus();
				SDPayStatus ps = order.getPayStatus();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				GetDBdata db = new GetDBdata();
				try {
					String sql_insert = "insert into cps  (`id`, `cid`, `wi`, `on`, `ta`, `pna`, `dt`, `pp`, `sd`, `ud`, `os`, `ps`, `pw`, `oo`) values(LAST_INSERT_ID(id),'" 
							+ "','" + "','" + on + "','" + ta + "','"
							+ pna + "','" + dt + "','" + pp + "','"
							+ formatter.format(sd) + "','"
							+ "0000-00-00 00:00:00" + "','" + os.toString()
							+ "','" + ps.toString() + "'," + "'','04') ";
					db.execUpdateSQL(sql_insert, null);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				new QueryBuilder(" update sdorders set channelsn = 'cps_04' where ordersn='" + on + "'").executeNoQuery();
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param urlStr
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @param flag
	 *            01：亿起发，02：自建联盟
	 * @return URL所代表远程资源的响应
	 */
	private static void SendURLGet(String urlStr, String param )
			throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection(); // 获取连接
		
		httpURLConnection.setRequestMethod("POST");
		
		httpURLConnection.setDoOutput(true);
		OutputStream os = httpURLConnection.getOutputStream();
		os.write(param.getBytes());
		os.flush();
		os.close();
		httpURLConnection.getInputStream();
	}
}

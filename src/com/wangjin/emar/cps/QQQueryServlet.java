package com.wangjin.emar.cps;

import com.sinosoft.framework.GetDBdata;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @ClassName: QQQueryServlet
 * @Description: TODO(QQ登陆提供给亿起发查询接口)
 * @author zhangjing
 * @date 2014-1-13 下午04:26:07
 * 
 */
public class QQQueryServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(QQQueryServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -8574548310203690630L;

	public void service(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/plain; charset=utf-8");
		try {
			request.setCharacterEncoding("utf-8");
			StringBuffer result = new StringBuffer();
			PrintWriter out = response.getWriter();
			if (StringUtils.isBlank(request.getParameter("key")) || !WangJinKey.getkey().equals(request.getParameter("key"))) {
				if (StringUtils.isBlank(request.getParameter("key"))) {
					result.append("您的开心保密匙为空！详情请联系开心保客服或者拨打4009789789。");
				} else {
					result.append("您的开心保密匙未授权或者已失效！详情请联系开心保客服或者拨打4009789789。");
				}
				out.print(result.toString());
				out.flush();
				out.close();
				return;
			}
			// 执行逻辑
			String cid = request.getParameter("cid");
			String date = request.getParameter("d");
			String ud = request.getParameter("ud");
			GetDBdata db = new GetDBdata();
			String sql_query = "select C.openid as openid,'qqlogin003' as ct,'kaixinbao' as merchantId,C.cid,C.wi,C.on,C.ta,C.pna,C.dt,C.pp,C.sd,O.modifyDate as ud,O.orderStatus as os,O.payStatus as ps,T.payType as pw from cps C left join SDorders O on  C.on=O.orderSn left join tradeinformation T on C.on=T.ordID  WHERE C.openid IS NOT NULL AND C.openid!='null'";
			if (StringUtils.isNotBlank(cid)) {
				sql_query = sql_query + " and C.cid='" + cid + "'";
				if (StringUtils.isNotBlank(date)) {
					sql_query = sql_query + " and DATE_FORMAT(C.sd,'%Y%m%d')='" + date + "'";
				} else if (StringUtils.isNotBlank(ud)) {
					sql_query = sql_query + " and DATE_FORMAT(O.modifyDate,'%Y%m%d')='" + ud + "'";
				}
			} else {
				if (StringUtils.isNotBlank(date)) {
					sql_query = sql_query + " and DATE_FORMAT(C.sd,'%Y%m%d')='" + date + "'";
				} else if (StringUtils.isNotBlank(ud)) {
					sql_query = sql_query + " and DATE_FORMAT(O.modifyDate,'%Y%m%d')='" + ud + "'";
				}
			}
			List<HashMap<String, String>> list = db.query(sql_query);
			for (HashMap<String, String> map : list) {
				result.append(map.get("openid"));
				result.append("||");
				result.append(map.get("ct"));
				result.append("||");
				result.append(map.get("merchantId"));
				result.append("||");
				result.append(map.get("wi"));
				result.append("||");
				result.append(map.get("sd"));
				result.append("||");
				result.append(map.get("on"));
				result.append("||");
				result.append(map.get("pp"));
				result.append("||");
				result.append(map.get("pna"));
				result.append("||");
				if (StringUtils.isNotBlank(map.get("os"))) {
					result.append(map.get("os"));
				} else if (StringUtils.isNotBlank(map.get("orderStatus"))) {
					result.append(map.get("orderStatus"));
				} else {
					result.append("");
				}
				result.append("||");
				if (StringUtils.isNotBlank(map.get("ps"))) {
					result.append(map.get("ps"));
				} else if (StringUtils.isNotBlank(map.get("paymentStatus"))) {
					result.append(map.get("paymentStatus"));
				} else {
					result.append("");
				}
				result.append("||");
				if (StringUtils.isNotBlank(map.get("pw"))) {
					result.append(map.get("pw"));
				} else if (StringUtils.isNotBlank(map.get("payType"))) {
					result.append(map.get("payType"));
				} else {
					result.append("");
				}
				result.append("||");
				result.append("");
				result.append("||");
				result.append("");
				result.append("||");
				result.append("");
				result.append("\n");
			}
			// 返回给客户端

			out.print(result.toString());
			out.flush();
			out.close();
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}

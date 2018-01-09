package com.wangjin.emar.cpa;

import com.sinosoft.framework.GetDBdata;
import com.wangjin.emar.cps.WangJinKey;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class QueryServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(QueryServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/plain; charset=utf-8");
		try {
			request.setCharacterEncoding("utf-8");
			// 执行逻辑
			StringBuffer result = new StringBuffer();
			PrintWriter out = response.getWriter();
			if (StringUtils.isBlank(request.getParameter("key"))
					|| !WangJinKey.getkey().equals(request.getParameter("key"))) {
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
			try {
				String date = request.getParameter("d");
				GetDBdata db = new GetDBdata();
				String sql_query = "select * from cpa  where DATE_FORMAT(sd,'%Y%m%d')='"
						+ date + "'";
				List<HashMap<String, String>> list = db.query(sql_query);
				for(HashMap<String, String> map:list){
					result.append(map.get("wi"));
					result.append("||");
					result.append(map.get("sd"));
					result.append("||");
					result.append(map.get("an"));
					result.append("||");
					result.append(map.get("ana"));
					result.append("||");
					result.append(map.get("ct"));
					result.append("\n");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			// 返回给客户端
			logger.info("result:{}", result.toString());
			out.print(result.toString());
			out.flush();
			out.close();
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}

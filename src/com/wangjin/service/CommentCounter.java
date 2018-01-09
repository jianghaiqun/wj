package com.wangjin.service;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class CommentCounter extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(CommentCounter.class);

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/plain; charset=utf-8");
		try {
			request.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			String url = request.getParameter("url");
			if (StringUtil.isEmpty(url)) {
				logger.error("产品详细页面获取评价数异常：{}", url);
				out.print("0");
				out.flush();
				out.close();
				return;
			}else if ("renewal_insurance!zhenAiChoseProductPlan".equals(FilenameUtils.getBaseName(url).toString())){
				return;
			}
			// 取得评论条数
			QueryBuilder qb = new QueryBuilder("select count(1) from zccomment where relaid=? and isBuy = '1' and verifyflag='Y'");
			long param = Long.parseLong(FilenameUtils.getBaseName(url));
			qb.add(param);
			String result = qb.executeInt()+"";
			out.print(result);
			out.flush();
			out.close();
			return;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}
}

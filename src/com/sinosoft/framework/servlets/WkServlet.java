package com.sinosoft.framework.servlets;

import com.sinosoft.framework.data.QueryBuilder;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

public class WkServlet  extends HttpServlet{
	private static final Logger logger = LoggerFactory.getLogger(WkServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "未收到请求数据";
		OutputStream out = response.getOutputStream();
		
		BufferedReader bf = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 解析输入流,InputStream转换为装饰类BufferedReader处理
			bf = new BufferedReader(new InputStreamReader(request.getInputStream()));
			bf = new BufferedReader(new InputStreamReader(request.getInputStream(),request.getCharacterEncoding()));
			String line = "";
			StringBuilder sb = new StringBuilder();
			while ((line = bf.readLine()) != null) {
				buffer.append(line);
				sb.append(line);
			}
			line =sb.toString();
			Map<String, Object> json = JSONObject.fromObject(line);
 			String msgCode =json.get("msgCode").toString();
			String bizContent =json.get("bizContent").toString();

		    if("success".equals(msgCode)){
				Map<String, Object>  map = JSONObject.fromObject(bizContent);
				QueryBuilder qb = new QueryBuilder("  UPDATE sdinformationrisktype SET policyNo = ? , electronicCout=?",map.get("policyCode").toString(),map.get("electronicPolicy").toString());
				qb.append("  WHERE applyPolicyNo = ?",map.get("orderCode"));
				qb.executeNoQuery();
				
		    }
		} catch (Exception e) {
			logger.error("**********悟空保险回调失败**********" + e.getMessage(), e);
		} finally{
			out.write(result.getBytes("utf-8"));
			out.flush();
			out.close();
		}
	}

}

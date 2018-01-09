package com.sinosoft.forward;

import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDConfigSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GotoMail {
	private static final Logger logger = LoggerFactory.getLogger(GotoMail.class);

	public void deal(String subject, String templateFilePath, Map<String, Object> data, String toMail) {
		HttpURLConnection conn = null;
		try {
			ZDConfigSchema tZDConfigSchema=new ZDConfigSchema();
			tZDConfigSchema.setType("ServerContext");
			tZDConfigSchema.fill();
			URL url = new URL(tZDConfigSchema.getValue()+"/shop/send_mail!deal.action");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setUseCaches(false);
			conn.setDoOutput(true);
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());// 发送请求
			StringBuffer sb = new StringBuffer();
			addPair(sb, "subject", subject);
			addPair(sb, "templateFilePath", templateFilePath);
			addPair(sb, "data", data);
			addPair(sb, "toMail", toMail);
			if(StringUtil.isNotEmpty((String)data.get("MemberName"))){
				
				data.put("MemberName", java.net.URLEncoder.encode((String)data.get("MemberName"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("ToName"))){
				
				data.put("ToName", java.net.URLEncoder.encode((String)data.get("ToName"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("ProductName"))){
				
				data.put("ProductName", java.net.URLEncoder.encode((String)data.get("ProductName"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("ApplicantName"))){
				
				data.put("ApplicantName", java.net.URLEncoder.encode((String)data.get("ApplicantName"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("UserName"))){
				
				data.put("UserName", java.net.URLEncoder.encode((String)data.get("UserName"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("InsuredSn"))){
				data.put("InsuredSn", java.net.URLEncoder.encode((String)data.get("InsuredSn"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("InsuredName"))){
				data.put("InsuredName", java.net.URLEncoder.encode((String)data.get("InsuredName"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("InsuranceCompany"))){
				
				data.put("InsuranceCompany", java.net.URLEncoder.encode((String)data.get("InsuranceCompany"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("FileName"))){
				
				data.put("FileName", java.net.URLEncoder.encode((String)data.get("FileName"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("ErrMsg"))){
				
				data.put("ErrMsg", java.net.URLEncoder.encode((String)data.get("ErrMsg"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("ProductCode"))){
				
				data.put("ProductCode", java.net.URLEncoder.encode((String)data.get("ProductCode"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("companyname"))){
				data.put("companyname", java.net.URLEncoder.encode((String)data.get("companyname"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("peoplenum"))){
				data.put("peoplenum", java.net.URLEncoder.encode((String)data.get("peoplenum"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("connectname"))){
				data.put("connectname", java.net.URLEncoder.encode((String)data.get("connectname"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("customerName"))){
				data.put("customerName", java.net.URLEncoder.encode((String)data.get("customerName"),"utf8"));
			}
//			if(StringUtil.isNotEmpty((String)data.get("productName"))){
//				data.put("productName", java.net.URLEncoder.encode((String)data.get("productName"),"utf8"));
//			}
			if(StringUtil.isNotEmpty((String)data.get("areaShow"))){
				data.put("areaShow", java.net.URLEncoder.encode((String)data.get("areaShow"),"utf8"));
			}
			
			if(StringUtil.isNotEmpty((String)data.get("policyNo"))){
				data.put("policyNo", java.net.URLEncoder.encode((String)data.get("policyNo"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("sdn"))){
				data.put("sdn", java.net.URLEncoder.encode((String)data.get("sdn"),"utf8"));
			}
			if(StringUtil.isNotEmpty((String)data.get("direction"))){
				data.put("direction", java.net.URLEncoder.encode((String)data.get("direction"),"utf8"));
			} 
			if(StringUtil.isNotEmpty((String)data.get("title"))){
				data.put("title", java.net.URLEncoder.encode((String)data.get("title"),"utf8"));
			} 
			if(StringUtil.isNotEmpty((String)data.get("name"))){
				data.put("name", java.net.URLEncoder.encode((String)data.get("name"),"utf8"));
			} 
			if(StringUtil.isNotEmpty((String)data.get("sex"))){
				data.put("sex", java.net.URLEncoder.encode((String)data.get("sex"),"utf8"));
			} 
			if(StringUtil.isNotEmpty((String)data.get("content"))){
				data.put("content", java.net.URLEncoder.encode((String)data.get("content"),"utf8"));
			} 
			if(StringUtil.isNotEmpty(data.get("detail")+"")){
				data.put("detail", java.net.URLEncoder.encode(data.get("detail")+"","utf8"));
			} 
			for (Object m : data.keySet()) {
				if(data.get(m)!=null){
					if(data.get(m).getClass().getName().equals("java.lang.String")){
						if (StringUtil.isNotEmpty((String)data.get(m))) {
							data.put(String.valueOf(m), java.net.URLEncoder.encode((String) data.get(m), "utf8"));
						}
					}
				}
			}
	
//			data.put("MemberName", new String(data.get("MemberName").toString().getBytes("UTF-8"),"ISO-8859-1"));
			if (data != null) {
				Set<Map.Entry<String, Object>> set = data.entrySet();
				for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
					Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
					addPair(sb, entry.getKey(), entry.getValue());
				}
			}
			osw.write(sb.substring(0, sb.length() - 1));
			osw.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));// 获得响应
			String line = null;
			sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			line = sb.toString();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}

	public static void addPair(StringBuffer sb, String name, Object value) {
		sb.append(name);
		sb.append("=");
		sb.append(value);
		sb.append("&");
	}

}

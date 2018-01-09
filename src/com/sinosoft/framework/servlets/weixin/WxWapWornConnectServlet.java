package com.sinosoft.framework.servlets.weixin;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.weixin.wxtools.WeiXinTools;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.WxWornInfoSchema;
import org.jdom.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信平台对接 微信消息处理 请求地址 http://域名/weixinchexian
 * 
 * @author sinosoft
 * 
 */
public class WxWapWornConnectServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(WxWapWornConnectServlet.class);

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@SuppressWarnings("static-access")
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			Document document = WeiXinTools.deealStreamToXML(request.getInputStream());
			Transaction tran = new Transaction();
			String wxSn = NoUtil.getMaxNo("WxSn",20);
			WxWornInfoSchema tWxWornInfoSchema = (WxWornInfoSchema)WeiXinTools.getSchema("worn", null, wxSn, document);
			tran.add(tWxWornInfoSchema,tran.INSERT);
			if(!tran.commit()){
				logger.error("微信维权/警告数据提交数据库失败");
			}else{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("Description", document.getRootElement().getChildTextTrim("Description"));
				map.put("Detail", document.getRootElement().getChildTextTrim("AlarmContent"));
				String toEmail = Config.getValue("WxWornEmail");
				if(StringUtil.isNotEmpty(toEmail)){
					ActionUtil.sendMail("wjwx002", toEmail, map);
				}
			}
		} catch (Exception e) {
			logger.error("微信接口类型错误");
		}
		
	}
}

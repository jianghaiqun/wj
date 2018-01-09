package com.sinosoft.framework.servlets.weixin;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.weixin.wxtools.WeiXinTools;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRigConMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRigRejMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRigReqMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRightsMsg;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.WxRightsInfoSchema;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信平台对接 微信消息处理 请求地址 http://域名/weixinchexian
 * 
 * @author sinosoft
 * 
 */
public class WxWapConnectServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(WxWapConnectServlet.class);

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
			InputStream in = request.getInputStream();
			WxRightsMsg msg = WeiXinTools.recvRig(in);
			Map<String, Object> map = new HashMap<String, Object>();
			Transaction tran = new Transaction();
			String wxSn = NoUtil.getMaxNo("WxSn",20);
			WxRightsInfoSchema tWxRightsInfoSchema = null;
			//维权申请
			if (msg instanceof WxRigReqMsg) {
				tWxRightsInfoSchema = (WxRightsInfoSchema)WeiXinTools.getSchema("request", msg, wxSn, null);
				tran.add(tWxRightsInfoSchema,tran.INSERT);
				map.put("Msg", "用户申请维权");
			}else if (msg instanceof WxRigConMsg) {
				//维权确认，用户消除投诉
				tWxRightsInfoSchema = (WxRightsInfoSchema)WeiXinTools.getSchema("confirm", msg, wxSn, null);
				tran.add(tWxRightsInfoSchema,tran.INSERT);
				map.put("Msg", "用户取消维权");
			}else if (msg instanceof WxRigRejMsg) {
				//维权确认，用户拒绝消除投诉
				tWxRightsInfoSchema = (WxRightsInfoSchema)WeiXinTools.getSchema("reject", msg, wxSn, null);
				tran.add(tWxRightsInfoSchema,tran.INSERT);
				map.put("Msg", "用户拒绝消除维权");
			}else{
				logger.error("微信维权类型处理失败！");
			}
			if(!tran.commit()){
				logger.error("微信维权数据提交数据库失败！");
			}else{
				// 发送邮件
				String toEmail = Config.getValue("WxRightsEmail");
				if(tWxRightsInfoSchema != null && StringUtil.isNotEmpty(toEmail)){
					String orderSn = WeiXinTools.getorderSn(String.valueOf(tWxRightsInfoSchema.getTransId()));
					map.put("Reason", WeiXinTools.getString(msg.getReason()));
					map.put("orderSn", orderSn);
					map.put("Solution", WeiXinTools.getString(tWxRightsInfoSchema.getSolution()));
					map.put("ExtInfo", WeiXinTools.getString(tWxRightsInfoSchema.getExtInfo()));
					ActionUtil.sendMail("wjwx001", toEmail, map);
				}
			}
		} catch (JDOMException e) {
			logger.error("微信接口类型错误" + e.getMessage(), e);
		}
		
	}
}

package com.sinosoft.framework.servlets.weixin;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.weixin.wxtools.WeiXinTools;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvEventMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.send.WxSendMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.send.WxSendNewsMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.send.WxSendTextMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 微信平台对接 微信消息处理 请求地址 http://域名/weixinchexian
 * 
 * @author sinosoft
 * 
 */
public class WxCarConnectServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(WxCarConnectServlet.class);

	private static final long serialVersionUID = 1L;
	final String TOKEN = "kaixinchexianweixin";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	    Properties props = new Properties();
		try {
		    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("weixin.properties");
		    InputStreamReader rd = new InputStreamReader(is, "UTF-8");
		    props.load(rd);
		    rd.close();
		    is.close();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    logger.error(e.getMessage(), e);
		}
		boolean flag = Boolean.valueOf(String.valueOf(new QueryBuilder("SELECT VALUE FROM zdconfig WHERE TYPE='WxCarAccessFlag' LIMIT 1").executeString()));
		// 判断是否已经验证过
		if (flag) {
			// 进行接口验证
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");
			if (null != timestamp && null != nonce && null != echostr
					&& null != signature) {
				if (WeiXinTools.access(TOKEN, signature, timestamp, nonce)) {
					response.getWriter().write(echostr);
					return;
				}
				return;
			} else {
				return;
			}
			
		} else {
			
			try {
				WxRecvMsg msg = WeiXinTools.recv(request.getInputStream());
				WxSendMsg sendMsg = WeiXinTools.builderSendByRecv(msg);
				String openID = msg.getFromUser();
				QueryBuilder qb = null;
				String tSQL= "&openID="+openID;
				if (msg instanceof WxRecvEventMsg) {
					WxRecvEventMsg recvMsg = (WxRecvEventMsg) msg;
					String event = recvMsg.getEvent();

					if ("subscribe".equals(event)) {
						// 订阅消息
						sendMsg = new WxSendTextMsg(sendMsg, String.valueOf((props.get("subscribeinfo_chexian"))));
						WeiXinTools.send(sendMsg, response.getOutputStream());
					} else if ("unsubscribe".equals(event)) {
						// 取消订阅

						return;

					} else if ("CLICK".equals(event)) {
						// 自定义菜单点击事件
						String eventKey = recvMsg.getEventKey();
						// 判断自定义菜单中的key回复消息
					    qb = new QueryBuilder(" SELECT ResponseType,PicTitle,PicDesc,PicURL,PicLinkURL FROM WxResponseInfo WHERE MenuAttribute=? ORDER BY TextOrder ASC");
						qb.add(eventKey);
						DataTable dt = qb.executeDataTable();
						//路况查询
						if ("LKCX_C".equals(eventKey)) {
							if("pic".equals(dt.getString(0, "ResponseType"))){
								int dtLen = dt.getRowCount();
								if(dt!=null && dtLen>=1){
									WxSendNewsMsg sendNewsMsg = new WxSendNewsMsg(sendMsg);
									for(int i = 0;i < dtLen;i++){
										sendNewsMsg.addItem(dt.getString(i, "PicTitle"),
												dt.getString(i, "PicDesc"), dt.getString(i, "PicURL"),
												dt.getString(i, "PicLinkURL")+tSQL);
									}
									sendMsg = sendNewsMsg;
								}
							}
						}else {//未找到事件
							sendMsg = new WxSendTextMsg(sendMsg, String.valueOf((props.get("unevent"))) );
						}
						WeiXinTools.send(sendMsg, response.getOutputStream());
					} else {
						// 无法识别的事件消息
						return;
					}

				}else{
					return ;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}

package com.sinosoft.framework.servlets.weixin;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.weixin.wxtools.WeiXinTools;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvEventMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvGeoMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvLinkMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvPicMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvTextMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvVoiceMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.send.WxSendImgMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.send.WxSendMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.send.WxSendNewsMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.send.WxSendTextMsg;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

/**
 * 微信平台对接 微信消息处理 请求地址 http://域名/wj/weiXinConnectServlet
 * 
 * @author sinosoft
 * 
 */
public class WeiXinConnectServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(WeiXinConnectServlet.class);

	private static final long serialVersionUID = 1L;
	private static Object mutex = new Object();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Properties props = new Properties();
		String  sendToxiaoneng=sendToxiaoneng=IOUtils.toString(request.getInputStream(),"UTF-8");
		InputStream request_getInputStream = new ByteArrayInputStream(sendToxiaoneng.getBytes("UTF-8"));   
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("weixin.properties");
			InputStreamReader rd = new InputStreamReader(is, "UTF-8");
			props.load(rd);
			rd.close();
			is.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		boolean flag = Boolean.valueOf(String.valueOf(new QueryBuilder(
				"SELECT VALUE FROM zdconfig WHERE TYPE='WeiXinAccessFlag' LIMIT 1").executeString()));
		// 判断是否已经验证过
		if (flag) {
			// 进行接口验证
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");
			if (null != timestamp && null != nonce && null != echostr
					&& null != signature) {
				if (WeiXinTools.access((String) props.get("appToken"), signature, timestamp, nonce)) {
					response.getWriter().write(echostr);
					return;
				}
				return;
			} else {
				return;
			}

		} else {

			try {
				WxRecvMsg msg = WeiXinTools.recv(request_getInputStream);
				WxSendMsg sendMsg = WeiXinTools.builderSendByRecv(msg);
				String openID = msg.getFromUser();
				QueryBuilder qb = new QueryBuilder(" SELECT COUNT(1) FROM wxbind WHERE 1=1 AND openid=? ");
				qb.add(openID);
				int tCount = qb.executeInt();

				String IsBind = "false";
				if (tCount >= 1) {
					IsBind = "true";
				}
				String tSQL = "&OpenID=" + openID + "&IsBind=" + IsBind;

				/** -------------------1.接受到的文本消息，回复处理-------------------------- */
				if (msg instanceof WxRecvTextMsg) {
					WxRecvTextMsg recvMsg = (WxRecvTextMsg) msg;
					// 用户输入的内容
					String text = recvMsg.getContent().trim();

					QueryBuilder textTypeQB = new QueryBuilder(
							" SELECT CodeValue FROM zdcode WHERE ParentCode='WeiXin.TextType' ");
					String textType = textTypeQB.executeString();
					QueryBuilder textQB = new QueryBuilder(
							" SELECT CodeValue,CodeName FROM zdcode WHERE codetype='WeiXin.TextRes' ");
					DataTable textDT = textQB.executeDataTable();
					boolean textFlag = false;
					if (textDT != null && textDT.getRowCount() >= 1) {
						int textLen = textDT.getRowCount();
						String textContent = "";
						for (int i = 0; i < textLen; i++) {
							textContent = textContent + textDT.get(i, "CodeValue") + ":" + textDT.get(i, "CodeName") + ";";
							String codeValue = String.valueOf(textDT.get(i, "CodeValue"));
							if (textType.equals("All")) {
								// 完全匹配
								if (text.equals(codeValue)) {
									textFlag = true;
									// 判断自定义菜单中的key回复消息
									QueryBuilder textResQB = new QueryBuilder(
											" SELECT ResponseType,PicTitle,PicDesc,PicURL,PicLinkURL, " +
													"TextContent,MusicTitle,MusicDesc,MusicURL,HpMusicURL " +
													"FROM WxResponseInfo WHERE MenuAttribute=? ORDER BY TextOrder ASC");
									textResQB.add(codeValue);
									DataTable textResDT = textResQB.executeDataTable();
									// 获取随机数据。
									int result = 0;
									if (textResDT == null || textResDT.getRowCount() == 0) {
										return;
									}
									if (textResDT.getRowCount() > 1) {
										java.util.Random random = new java.util.Random();
										result = random.nextInt(textResDT.getRowCount());
									}
									
									if ("pic".equals(textResDT.getString(result, "ResponseType"))) {
										int dtLen = textResDT.getRowCount();
										if (textResDT != null && dtLen >= 1) {
											WxSendNewsMsg sendNewsMsg = new WxSendNewsMsg(sendMsg);
											for (int j = 0; j < dtLen; j++) {
												sendNewsMsg.addItem(textResDT.getString(j, "PicTitle"),
														textResDT.getString(j, "PicDesc"), textResDT.getString(j, "PicURL"),
														textResDT.getString(j, "PicLinkURL") + tSQL);

											}

											sendMsg = sendNewsMsg;

										}
									} else if ("img".equals(textResDT.getString(result, "ResponseType"))) {
										int dtLen = textResDT.getRowCount();
										if (textResDT != null && dtLen >= 1) {
											WxSendImgMsg sendImgMsg = new WxSendImgMsg(sendMsg, textResDT.getString(result, "PicURL"));
											sendMsg = sendImgMsg;
										}
									} else if ("text".equals(textResDT.getString(result, "ResponseType"))) {
										int dtLen = textResDT.getRowCount();
										if (textResDT != null && dtLen >= 1) {
											sendMsg = new WxSendTextMsg(sendMsg, textResDT.getString(result, "TextContent"));
										}
									} else if ("select".equals(textResDT.getString(result, "ResponseType"))) {
										if (textResDT != null && textResDT.getRowCount() >= 1) {
											// sendMsg = new
											// WxSendTextMsg(sendMsg,
											// textResDT.getString(0,
											// "TextContent"));
											if ("我要门票".equals(text.trim())) {
												int tacket_count = Integer.parseInt(Config.getValue("wxflag01"));// 激活码可领取次数
												DataTable dt_re = new QueryBuilder(
														"select count(1),ActiveCode from WxActiveCodeInfo where ReceiveFlag='Y' and OpenID=?",
														openID).executeDataTable();
												int i_count = dt_re.getInt(0, 0);
												String ttActiveCode = dt_re.getString(0, 1);
												if (i_count >= tacket_count) {
													sendMsg = new WxSendTextMsg(sendMsg, "您已领取的激活码是：" + ttActiveCode
															+ "，请勿重复领取。");
												} else {
													synchronized (mutex) {
														DataTable dt_code = new QueryBuilder(
																"SELECT getNewActiveCode(?) FROM DUAL", openID)
																.executeDataTable();
														String tActiveCode = dt_code.getString(0, 0);
														if (StringUtil.isEmpty(tActiveCode)) {
															sendMsg = new WxSendTextMsg(sendMsg, "来晚一步，活动已结束。");
														} else {
															sendMsg = new WxSendTextMsg(sendMsg, "激活码：" + tActiveCode);
														}
													}
												}

											} else {
												String selectSQL = textResDT.getString(result, "TextContent");
												QueryBuilder qb_select = new QueryBuilder(selectSQL);
												DataTable dt_msg = qb_select.executeDataTable();
												if (dt_msg != null && dt_msg.getRowCount() >= 1) {
													sendMsg = new WxSendTextMsg(sendMsg, dt_msg.getString(0, 0));
												} else {
													sendMsg = new WxSendTextMsg(sendMsg, "活动暂未开始。");
												}
											}
										}
									}
									WeiXinTools.send(sendMsg, response.getOutputStream());
								}
							} else if (textType.equals("Index")) {
								// 不完全匹配
								if (text.indexOf(String.valueOf(textDT.get(i, "CodeValue"))) != -1) {
									textFlag = true;
									// 判断自定义菜单中的key回复消息
									QueryBuilder textResQB = new QueryBuilder(
											" SELECT ResponseType,PicTitle,PicDesc,PicURL,PicLinkURL, " +
													"TextContent,MusicTitle,MusicDesc,MusicURL,HpMusicURL " +
													"FROM WxResponseInfo WHERE MenuAttribute=? ORDER BY TextOrder ASC");
									textResQB.add(codeValue);
									DataTable textResDT = textResQB.executeDataTable();
									if ("pic".equals(textResDT.getString(0, "ResponseType"))) {
										int dtLen = textResDT.getRowCount();
										if (textResDT != null && dtLen >= 1) {
											WxSendNewsMsg sendNewsMsg = new WxSendNewsMsg(sendMsg);
											for (int j = 0; j < dtLen; j++) {
												sendNewsMsg.addItem(textResDT.getString(j, "PicTitle"),
														textResDT.getString(j, "PicDesc"), textResDT.getString(j, "PicURL"),
														textResDT.getString(j, "PicLinkURL") + tSQL);
											}
											sendMsg = sendNewsMsg;
										}
									} else if ("text".equals(textResDT.getString(0, "ResponseType"))) {
										int dtLen = textResDT.getRowCount();
										if (textResDT != null && dtLen >= 1) {
											sendMsg = new WxSendTextMsg(sendMsg, textResDT.getString(0, "TextContent"));
										}
									} else if ("select".equals(textResDT.getString(0, "ResponseType"))) {
										int dtLen = textResDT.getRowCount();
										if (textResDT != null && dtLen >= 1) {
											// sendMsg = new
											// WxSendTextMsg(sendMsg,
											// textResDT.getString(0,
											// "TextContent"));
											if ("我要门票".equals(text)) {
												int tacket_count = Integer.parseInt(Config.getValue("wxflag01"));// 激活码可领取次数
												DataTable dt_re = new QueryBuilder(
														"select count(1),ActiveCode from WxActiveCodeInfo where ReceiveFlag='Y' and OpenID=?",
														openID).executeDataTable();
												int i_count = dt_re.getInt(0, 0);
												String ttActiveCode = dt_re.getString(0, 1);
												if (i_count >= tacket_count) {
													sendMsg = new WxSendTextMsg(sendMsg, "您已领取的激活码是：" + ttActiveCode
															+ "，请勿重复领取。");
												} else {
													synchronized (mutex) {
														DataTable dt_code = new QueryBuilder(
																"SELECT getNewActiveCode(?) FROM DUAL", openID)
																.executeDataTable();
														String tActiveCode = dt_code.getString(0, 0);
														if (StringUtil.isEmpty(tActiveCode)) {
															sendMsg = new WxSendTextMsg(sendMsg, "来晚一步，活动已结束。");
														} else {
															sendMsg = new WxSendTextMsg(sendMsg, "激活码：" + tActiveCode);
														}
													}
												}

											} else {
												String selectSQL = textResDT.getString(0, "TextContent");
												QueryBuilder qb_select = new QueryBuilder(selectSQL);
												DataTable dt_msg = qb_select.executeDataTable();
												if (dt_msg != null && dt_msg.getRowCount() >= 1) {
													sendMsg = new WxSendTextMsg(sendMsg, dt_msg.getString(0, 0));
												} else {
													sendMsg = new WxSendTextMsg(sendMsg, "活动暂未开始。");
												}
											}
										}
									}
									WeiXinTools.send(sendMsg, response.getOutputStream());
								}
							}
						}
						if (!textFlag) {
							/*//System.out.println("消息转发到多客服~~开始");
							LogUtil.info("消息转发到多客服~~开始" + " 消息内容：" + text);
							// 消息转发到多客服
							sendMsg.setMsgType("transfer_customer_service");
							WeiXinTools.send(sendMsg, response.getOutputStream());
							//System.out.println("消息转发到多客服~~结束");
							LogUtil.info("消息转发到多客服~~结束");*/  
							logger.info("消息转发到多客服~~");
							if("Success".equals(xiaoneng_SendMsg(sendToxiaoneng))){
								logger.info("微信消息发送小能消息(文本): {}", sendToxiaoneng);
							}else{
								logger.error("微信消息发送小能失败(文本)： {}", sendToxiaoneng);
							}
							return;
						}
					} else {//SELECT CodeValue,CodeName FROM zdcode WHERE codetype='WeiXin.TextRes' 无值
						// sendMsg = new WxSendTextMsg(sendMsg, "您发的内容是：" +
						// text+";未找到匹配项");
						// WeiXinTools.send(sendMsg,
						// response.getOutputStream());
						if("Success".equals(xiaoneng_SendMsg(sendToxiaoneng))){
							logger.info("微信消息发送小能消息(文本): {}",sendToxiaoneng);
						}else{
							logger.error("微信消息发送小能失败(文本)：  {}",sendToxiaoneng);
						}
						return;
					}

				} else if (msg instanceof WxRecvEventMsg) {
					/** -------------------2.接受到的事件消息-------------------------- */
					WxRecvEventMsg recvMsg = (WxRecvEventMsg) msg;
					String event = recvMsg.getEvent();

					if ("subscribe".equals(event)) {
						
						// 理赔频道二维码扫一扫 对应，根据场景(最初设置为8)提示理赔信息。
						// 发送消息内容
						String sendTextMsg = "";
						String eventKey = recvMsg.getEventKey();
						eventKey = eventKey.replace("qrscene_", "");
						if (eventKey.equals(Config.getValue("LipeiEventKey"))) {
							qb = new QueryBuilder("SELECT TextContent FROM WxResponseInfo WHERE MenuAttribute = '理赔'");
							String lipeiText = qb.executeString();
							if (StringUtil.isNotEmpty(lipeiText)) {
								sendTextMsg = lipeiText;
							} else {
								sendTextMsg = "申请理赔请点击菜单【理赔申请】";
							}
						} else if (eventKey.equals(Config.getValue("wxActivityBargain"))) {
							qb = new QueryBuilder("SELECT TextContent FROM WxResponseInfo WHERE MenuAttribute = 'qrscene_" + eventKey + "_bargain'");
							String lipeiText = qb.executeString();
							sendTextMsg = lipeiText;
						}else{
							// 订阅消息
							qb = new QueryBuilder("SELECT TextContent FROM WxResponseInfo WHERE MenuAttribute = 'subscribeinfo'");
							
							String subscribeinfo = qb.executeString();
							if (StringUtil.isEmpty(subscribeinfo)) {
								subscribeinfo = String.valueOf((props.get("subscribeinfo")));
							}
							sendTextMsg = subscribeinfo;
						}
						
						sendMsg = new WxSendTextMsg(sendMsg, sendTextMsg);
						WeiXinTools.send(sendMsg, response.getOutputStream());
						// 记录关注数据，统计使用
						WeiXinTools.createRecord(recvMsg);
					} else if ("unsubscribe".equals(event)) {
						// 取消订阅
						return;

					} else if ("CLICK".equals(event)) {
						// 自定义菜单点击事件
						String eventKey = recvMsg.getEventKey();
						// sendMsg = new WxSendTextMsg(sendMsg,
						// "您发的内容是：<a href=\"http://www.baidu.com/\">欢迎登录开心保微信！</a>"
						// );
						/*
						 * sendMsg = new WxSendNewsMsg(sendMsg).addItem("图文标题",
						 * "图文描述", "http://www.baidu.com/img/bdlogo.gif",
						 * "http://www.baidu.com/");
						 */
						// 判断自定义菜单中的key回复消息
						qb = new QueryBuilder(
								" SELECT ResponseType,PicTitle,PicDesc,PicURL,PicLinkURL FROM WxResponseInfo WHERE MenuAttribute=? ORDER BY TextOrder ASC");
						qb.add(eventKey);
						DataTable dt = qb.executeDataTable();
						// 配置菜单回复方式
						QueryBuilder qb1 = new QueryBuilder(
								" SELECT COUNT(1) FROM zdcode WHERE codetype='WeiXin.Menu' AND codevalue=?");
						qb1.add(eventKey);
						int qb_menu = qb1.executeInt();
						if (qb_menu >= 1) {
							if ("pic".equals(dt.getString(0, "ResponseType"))) {
								int dtLen = dt.getRowCount();
								if (dt != null && dtLen >= 1) {
									WxSendNewsMsg sendNewsMsg = new WxSendNewsMsg(sendMsg);
									for (int i = 0; i < dtLen; i++) {
										sendNewsMsg.addItem(dt.getString(i, "PicTitle"),
												dt.getString(i, "PicDesc"), dt.getString(i, "PicURL"),
												dt.getString(i, "PicLinkURL") + tSQL);
									}
									sendMsg = sendNewsMsg;
								}
							}
						} else if ("BDZH".equals(eventKey)) {// 绑定账号
							if (tCount <= 0) {
								sendMsg = new WxSendTextMsg(sendMsg, String.valueOf((props.get("unbind"))).replace("OpenID",
										"OpenID=" + openID));
							} else {
								sendMsg = new WxSendTextMsg(sendMsg, String.valueOf((props.get("bind"))));
							}
						} else if ("LXKF".equals(eventKey)) {// 联系客服
							sendMsg = new WxSendTextMsg(sendMsg, String.valueOf((props.get("hotline"))));
						} else {// 未找到事件
							sendMsg = new WxSendTextMsg(sendMsg, String.valueOf((props.get("unevent"))));
						}
						WeiXinTools.send(sendMsg, response.getOutputStream());

					} else if ("SCAN".equals(event)) {

						// 理赔频道二维码扫一扫 对应，根据场景(最初设置为8)提示理赔信息。
						String eventKey = recvMsg.getEventKey();
						if (eventKey.equals(Config.getValue("LipeiEventKey"))) {
							// 发送消息内容
							String sendTextMsg = "";
							qb = new QueryBuilder("SELECT TextContent FROM WxResponseInfo WHERE MenuAttribute = '理赔'");
							String lipeiText = qb.executeString();
							if (StringUtil.isNotEmpty(lipeiText)) {
								sendTextMsg = lipeiText;
							} else {
								sendTextMsg = "申请理赔请点击菜单【理赔申请】";
							}
							sendMsg = new WxSendTextMsg(sendMsg, sendTextMsg);
							WeiXinTools.send(sendMsg, response.getOutputStream());
						}
						// 记录扫描已关注用户数据，统计使用
						WeiXinTools.createRecord(recvMsg);
					} else {
						// 无法识别的事件消息
						return;
					}

				} else if (msg instanceof WxRecvVoiceMsg) {
					//语音消息
					if("Success".equals(xiaoneng_SendMsg(sendToxiaoneng))){
						logger.info("微信消息发送小能消息(语音):{}", sendToxiaoneng);
					}else{
						logger.error("微信消息发送小能失败(语音):{}", sendToxiaoneng);
					}
					return;
/*					WxRecvVoiceMsg recvMsg = (WxRecvVoiceMsg) msg;
					System.out.println("语音：" + recvMsg.getMediaId() + ";" + recvMsg.getMsgType() + ";" + recvMsg.getFormat());*/
				}
				else if (msg instanceof WxRecvPicMsg) {
					//图片消息
					if("Success".equals(xiaoneng_SendMsg(sendToxiaoneng))){
						logger.info("微信消息发送小能消息(图片): {}", sendToxiaoneng);
					}else{
						logger.error("微信消息发送小能失败(图片)：{}", sendToxiaoneng);
					}
					return;
				}else if (msg instanceof WxRecvLinkMsg) {
					//超链接
					logger.info("微信消息发送小能消息(超链接转换前):{}", sendToxiaoneng);
					if("Success".equals(xiaoneng_SendMsg(ChangeToWxRecvLinkMsg(sendToxiaoneng)))){
						logger.info("微信消息发送小能消息(超链接转换（文本）后):{}", ChangeToWxRecvLinkMsg(sendToxiaoneng));
					}else{
						logger.error("微信消息发送小能失败(超链接转换（文本）后)：{}", ChangeToWxRecvLinkMsg(sendToxiaoneng));;
					}
				}else if (msg instanceof WxRecvGeoMsg) {
					//地理位置
					logger.info("微信消息发送小能消息(地理位置):{}", sendToxiaoneng);
					sendMsg = new WxSendTextMsg(sendMsg, "您在发定位吗？我看不到喔~~。");
				    WeiXinTools.send(sendMsg, response.getOutputStream());
				    return;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	
	/**
	 * 
	 * @param request 接收信息转换string
	 * @return
	 * @throws IOException
	 */
	public String streamToString(HttpServletRequest request) throws IOException{
		return IOUtils.toString(request.getInputStream(),"UTF-8");
	}
	
	/**
	* 微信报文URL转换文本格式
	* @param message 文本、图片、语音消息
	*/
	public String ChangeToWxRecvLinkMsg(String xml){//转换url信息 变成test信息发送小能
		xml=deleteXmlRow(xml,"Description");
		xml=deleteXmlRow(xml,"Title");
		if(xml.contains("Url")){
		    xml=xml.replace("Url", "Content ");
		}else if(xml.contains("url")){
			xml=xml.replace("url", "content ");
		}
		return xml;
	}


	//删除 <Description> <title>
	public static String deleteXmlRow(String xml,String deleteName){
		String delrowstr="";
		String delname=toLowerCaseFirstOne(deleteName);
		String Delname=toUpperCaseFirstOne(deleteName);
		if(xml.contains(delname)){
			delrowstr= xml.substring(xml.indexOf(delname)-1, xml.indexOf("/"+delname)+delname.length()+2);
		}else if(xml.contains(Delname)){
			delrowstr= xml.substring(xml.indexOf(Delname)-1, xml.indexOf("/"+Delname)+deleteName.length()+2);
		}
		return xml.replace(delrowstr, "");
	}
    
     //首字母转小写 
    public static String toLowerCaseFirstOne(String s){   
    	if(Character.isLowerCase(s.charAt(0)))     
    		return s;   
    	else    
    		return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString(); 
    	} 
    
    //首字母转大写 
    public static String toUpperCaseFirstOne(String s){   
    	if(Character.isUpperCase(s.charAt(0)))     
    		return s;
    	else    
    		return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString(); 
    	} 

	
	/**
	* 发送消息给小能在线客服
	* @param message 文本、图片、语音消息
	*/
	public String xiaoneng_SendMsg(String message) {
	String http = "http://bwx2.ntalker.com/agent/weixin";// http://wxtest.ntalker.com/agent/weixin 
	HttpURLConnection httpConn = null;
	try {
	    URL url = new URL(http);
	    httpConn = (HttpURLConnection) url.openConnection();
	    httpConn.setRequestProperty("Content-Type", "text/xml");
	    httpConn.setRequestProperty("User-agent", "MSIE8.0");
	    httpConn.setRequestMethod("POST");
	    httpConn.setDoOutput(true);
		httpConn.setReadTimeout(5000);
		httpConn.setConnectTimeout(5000);
		OutputStream op = httpConn.getOutputStream();
		op.write(message.getBytes("utf-8"));
		op.flush();
		op.close();
		if (httpConn.getResponseCode() == 200)
		{
			return "Success";
		}else{
			return "";
		}
		} catch (Exception e) {
		    logger.error(e.getMessage(), e);
		} finally {
		   if (httpConn != null)
		      httpConn.disconnect();
		   }
	return "Success";
	}

}

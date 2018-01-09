package com.sinosoft.framework.utility.weixin.wxtools;

import cn.com.sinosoft.util.CommonUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.weixin.wxtools.parser.WxMsgKit;
import com.sinosoft.framework.utility.weixin.wxtools.parser.WxRigMsgKit;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvEventMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRecvMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRigReqMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.recv.WxRightsMsg;
import com.sinosoft.framework.utility.weixin.wxtools.vo.send.WxSendMsg;
import com.sinosoft.schema.WeixinPlatformRecordSchema;
import com.sinosoft.schema.WeixinPlatformUserSchema;
import com.sinosoft.schema.WxRightsInfoSchema;
import com.sinosoft.schema.WxWornInfoSchema;
import com.sinosoft.weixin.WeiXinCommon;
import net.sf.json.JSONObject;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;



public final class WeiXinTools {
	private static final Logger logger = LoggerFactory.getLogger(WeiXinTools.class);

	public static boolean access(String token,String signature,String timestamp,String nonce) {
		List<String> ss = new ArrayList<String>();
		ss.add(timestamp);
		ss.add(nonce);
		ss.add(token);
		
		Collections.sort(ss);
		
		StringBuilder builder = new StringBuilder();
		for(String s : ss) {
			builder.append(s);
		}
		return signature.equalsIgnoreCase(HashKit.sha1(builder.toString()));
	}
	
	public static WxRecvMsg recv(InputStream in) throws JDOMException, IOException {
		return WxMsgKit.parse(in);
	}
	public static WxRightsMsg recvRig(InputStream in) throws JDOMException, IOException {
		return WxRigMsgKit.parse(in);
	}
	
	public static void send(WxSendMsg msg,OutputStream out) throws JDOMException,IOException {
		Document doc = WxMsgKit.parse(msg);
		try {
			logger.info(doc2String(doc));
		} catch (Exception e) {
			logger.error("输出xml字符串异常." + e.getMessage(), e);
		}
		if(null != doc) {
			new XMLOutputter().output(doc, out);
		} else {
			logger.warn("发送消息时,解析出dom为空 msg :{}", msg);
		}
	}
	
	public static WxSendMsg builderSendByRecv(WxRecvMsg msg) {
		WxRecvMsg m = new WxRecvMsg(msg);
		String from = m.getFromUser();
		m.setFromUser(m.getToUser());
		m.setToUser(from);
		m.setCreateDt((System.currentTimeMillis() / 1000) + "");
		return new WxSendMsg(m);
	}
	
	public static Document deealStreamToXML(InputStream in)  {
		Document doc = null;
		try {
			SAXBuilder parser=new SAXBuilder();
			doc=parser.build(in);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");
		XMLOutputter a = new XMLOutputter(format);
		logger.info(a.outputString(doc));
		return doc;

	}
	public static Schema getSchema(String cType,WxRightsMsg msg,String wxSn,Document doc){
		if(cType.equals("request")){
			WxRightsInfoSchema tWxRightsInfoSchema = new  WxRightsInfoSchema();
			tWxRightsInfoSchema.setID(wxSn);
			tWxRightsInfoSchema.setAppId(msg.getAppId());
			tWxRightsInfoSchema.setAppSignature(msg.getAppSignature());
			tWxRightsInfoSchema.setCreateDate(DateUtil.parse(PubFun.getCurrent()));
			tWxRightsInfoSchema.setExtInfo(((WxRigReqMsg) msg).getExtInfo());
			tWxRightsInfoSchema.setFeedBackId(msg.getFeedBackId());
			tWxRightsInfoSchema.setModifyDate(DateUtil.parse(PubFun.getCurrent()));
			tWxRightsInfoSchema.setMsgType(msg.getMsgType());
			tWxRightsInfoSchema.setOpenId(msg.getMsgType());
			tWxRightsInfoSchema.setReason(msg.getReason());
			tWxRightsInfoSchema.setSignMethod(msg.getSignMethod());
			tWxRightsInfoSchema.setSolution(((WxRigReqMsg) msg).getSolution());
			tWxRightsInfoSchema.setTransId(((WxRigReqMsg) msg).getTransId());
			return tWxRightsInfoSchema;
		}else if(cType.equals("confirm") || cType.equals("reject")){
			WxRightsInfoSchema tWxRightsInfoSchema = new  WxRightsInfoSchema();
			tWxRightsInfoSchema.setID(wxSn);
			tWxRightsInfoSchema.setAppId(msg.getAppId());
			tWxRightsInfoSchema.setAppSignature(msg.getAppSignature());
			tWxRightsInfoSchema.setCreateDate(DateUtil.parse(PubFun.getCurrent()));
			tWxRightsInfoSchema.setFeedBackId(msg.getFeedBackId());
			tWxRightsInfoSchema.setModifyDate(DateUtil.parse(PubFun.getCurrent()));
			tWxRightsInfoSchema.setMsgType(msg.getMsgType());
			tWxRightsInfoSchema.setOpenId(msg.getMsgType());
			tWxRightsInfoSchema.setReason(msg.getReason());
			tWxRightsInfoSchema.setSignMethod(msg.getSignMethod());
			return tWxRightsInfoSchema;
		}else{
			//警告
			Element ele = doc.getRootElement();
			
			WxWornInfoSchema tWxWornInfoSchema = new  WxWornInfoSchema();
			tWxWornInfoSchema.setID(wxSn);
			 /* <AppId><![CDATA[wxf8b4f85f3a794e77]]></AppId>
		    <ErrorType>1001</ErrorType>
		    <Description><![CDATA[错识描述]]></Description>
		    <AlarmContent><![CDATA[错误详情]]></AlarmContent>
		    <TimeStamp>1393860740</TimeStamp>
		    <AppSignature><![CDATA[f8164781a303f4d5a944a2dfc68411a8c7e4fbea]]></AppSignature>
		    <SignMethod><![CDATA[sha1]]></SignMethod>*/
			tWxWornInfoSchema.setAppId(ele.getChildTextTrim("AppId"));
			tWxWornInfoSchema.setAppSignature(ele.getChildTextTrim("AppSignature"));
			tWxWornInfoSchema.setCreateDate(DateUtil.parse(PubFun.getCurrent()));
			tWxWornInfoSchema.setModifyDate(DateUtil.parse(PubFun.getCurrent()));
			tWxWornInfoSchema.setSignMethod(ele.getChildTextTrim("SignMethod"));
			tWxWornInfoSchema.setErrorType(ele.getChildTextTrim("ErrorType"));
			tWxWornInfoSchema.setDescription(ele.getChildTextTrim("Description"));
			tWxWornInfoSchema.setAlarmContent(ele.getChildTextTrim("AlarmContent"));
			return tWxWornInfoSchema;
		}
		
	}
	
	public static String getorderSn(String paySn){
		DataTable dt = new QueryBuilder(" select orderSn from sdorders where paysn=? ",String.valueOf(paySn)).executeDataTable();
		String orderSn = "";
		if(dt!=null && dt.getRowCount()>=1){
			orderSn = dt.getString(0, 0);
		}
		return orderSn;
	}
	
	public static String getString(Object obj){
		
		if(obj==null || "null".equals(obj)){
			return "";
		}
		
		return String.valueOf(obj);
	}

	/**
	 * 
	 * createRecord:(创建微信公众平台扫描二维码记录). <br/>
	 *
	 * @author 王文英
	 * @param recvMsg
	 * @return
	 */
	public static int createRecord(WxRecvEventMsg recvMsg){

		WeixinPlatformRecordSchema schema = new WeixinPlatformRecordSchema();
		schema.setid(CommonUtil.getUUID());
		String event = recvMsg.getEvent();
		// 未注册扫描用户
		if ("subscribe".equals(event)) {
			String eventKey = recvMsg.getEventKey();
			eventKey = eventKey.replace("qrscene_", "");
			schema.setsceneId(eventKey);
			schema.setstatus("0");
		} 
		// 已注册扫描用户
		else if ("SCAN".equals(event)) {
			schema.setsceneId(recvMsg.getEventKey());
			schema.setstatus("1");
		} else {
			logger.warn("未知事件！event：{}", event);
		}
		
		Date date = new Date();
        date.setTime(Long.valueOf(recvMsg.getCreateDt()) * 1000L);
		schema.setCreateTime(date);
		schema.setToUserName(recvMsg.getToUser());
		schema.setFromUserName(recvMsg.getFromUser());
		schema.setAddTime(new Date());
		
		int result = 0;
		if (schema.insert()) {
			result = 1;
		}
		try {
			// 获取微信用户信息 并插入到数据库中
			String openid = recvMsg.getFromUser();
			createWXUser(openid);
		}catch (Exception e) {
			logger.error("创建微信用户信息异常！" + e.getMessage(), e);
		}
		
		return result;
	}
	
	/**
	 * 创建微信公众号关注用户信息
	 * 
	 * @param openid
	 * @return
	 */
	private static int createWXUser(String openid){
		String jsonInfo = WeiXinCommon.ajaxFromUserInfo(WeiXinCommon.ajaxtoken(), openid);
		logger.info(jsonInfo);
		JSONObject jsonObject = JSONObject.fromObject(jsonInfo);
		
		if ("1".equals(jsonObject.getString("subscribe"))) {

			Transaction trans = new Transaction();
			
			WeixinPlatformUserSchema schema = new WeixinPlatformUserSchema();
			schema.setopenid(openid);
			
			schema.setnickname(StringUtil.deleteEmoji(jsonObject.getString("nickname")));
			schema.setsex(jsonObject.getString("sex"));
			schema.setlanguage(jsonObject.getString("language"));
			schema.setcity(jsonObject.getString("city"));
			schema.setprovince(jsonObject.getString("province"));
			schema.setcountry(jsonObject.getString("country"));
			schema.setheadimgurl(jsonObject.getString("headimgurl"));

			Date date = new Date();
	        date.setTime(Long.valueOf(jsonObject.getString("subscribe_time")) * 1000L);
			schema.setsubscribe_time(date);
			schema.setremark(jsonObject.getString("remark"));
			schema.setgroupid(jsonObject.getString("groupid"));
			trans.add(schema, Transaction.DELETE_AND_INSERT);
			
			if (trans.commit()) {
				return 1;
			}
		}
		
		return 0;
	}
	/**   
     * Document转换为字符串   
     *    
     * @param xmlFilePath XML文件路径   
     * @return xmlStr 字符串   
     * @throws Exception   
     */    
    public static String doc2String(Document doc) throws Exception {     
        Format format = Format.getPrettyFormat();     
        //format.setEncoding("UTF-8");// 设置xml文件的字符为UTF-8，解决中文问题     
        XMLOutputter xmlout = new XMLOutputter(format);     
        ByteArrayOutputStream bo = new ByteArrayOutputStream();     
        xmlout.output(doc, bo);     
        return bo.toString();     
    }  
}

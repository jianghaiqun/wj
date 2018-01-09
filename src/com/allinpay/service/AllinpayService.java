package com.allinpay.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Project Name:wj-code
 * File Name:AllinpayService.java
 * Package Name:com.allinpay.service
 * Date:2017年11月14日上午9:44:08
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.allinpay.bean.AipgReq;
import com.allinpay.bean.AipgRsp;
import com.allinpay.bean.InfoReq;
import com.allinpay.bean.InfoRsp;
import com.allinpay.bean.Rnpa;
import com.allinpay.bean.RnpaRet;
import com.allinpay.bean.Rnpc;
import com.allinpay.bean.RnpcRet;
import com.allinpay.bean.Rnpr;
import com.allinpay.bean.RnprRet;
import com.allinpay.bean.Trans;
import com.allinpay.bean.TransQueryReq;
import com.allinpay.bean.TransRet;
import com.allinpay.constant.TranxConsts;
import com.allinpay.util.XmlTools;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * ClassName:AllinpayService <br/>
 * Function:TODO 通联代收接口调用. <br/>
 * Date:2017年11月14日 上午9:44:08 <br/>
 *
 * @author:guozc
 */
public class AllinpayService {
	private static final Logger LOG = LoggerFactory.getLogger(AllinpayService.class);

	/**
	 * 实名付申请
	 * 
	 * @param rnpa
	 * @throws Exception
	 */
	public Map<String, Object> payApply(Rnpa rnpa) throws Exception {
		AipgReq aipgReq = new AipgReq();
		InfoReq info = makeReq("211006");
		aipgReq.setInfo(info);
		rnpa.setMERCHANT_ID(TranxConsts.merchantId);
		aipgReq.setRnpa(rnpa);
		XStream xs = new XStream(new DomDriver());
		xs.processAnnotations(AipgReq.class);
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>" + xs.toXML(aipgReq);
		xml = xml.replaceAll("__", "_");
		xml = signMsg(xml);
		String retXml = sendXml(xml,info);
		XStream retXs = new XStream(new DomDriver());
		retXs.processAnnotations(AipgRsp.class);
		AipgRsp rsp = (AipgRsp) retXs.fromXML(retXml);
		InfoRsp infoRsp = rsp.getInfo();
		RnpaRet repaRet = rsp.getRnpaRet();
		if (TranxConsts.SUCCESS_CODE.equals(infoRsp.getRET_CODE())) {
			if (!TranxConsts.SUCCESS_CODE.equals(repaRet.getRET_CODE())) {
				return PubFun.errMsg(repaRet.getRET_CODE(), repaRet.getERR_MSG());
			} else {
				if (!"1".equals(repaRet.getISSENDSMS())) {
					return PubFun.errMsg("sendAuthCodeFail","短信验证码发送失败");
				}
			}
		} else {
			return PubFun.errMsg(infoRsp.getRET_CODE(), infoRsp.getERR_MSG());
		}
		return PubFun.sucMsg(infoRsp.getREQ_SN());
	}

	/**
	 * 实名付短信重发
	 * 
	 * @param reqSn
	 *            实名付申请接口交易流水号(REQ_SN)
	 * @throws Exception
	 */
	public Map<String, Object> payApplyAgain(String reqSn) throws Exception {
		AipgReq aipgReq = new AipgReq();
		InfoReq info = makeReq("211006R");
		aipgReq.setInfo(info);

		Rnpr rnpr = new Rnpr();
		rnpr.setMERCHANT_ID(TranxConsts.merchantId);
		rnpr.setSRCREQSN(reqSn);
		aipgReq.setRnpr(rnpr);

		XStream xs = new XStream(new DomDriver());
		xs.processAnnotations(AipgReq.class);
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>" + xs.toXML(aipgReq);
		xml = xml.replaceAll("__", "_");
		xml = signMsg(xml);
		String retXml = sendXml(xml,info);
		XStream retXs = new XStream(new DomDriver());
		retXs.processAnnotations(AipgRsp.class);
		AipgRsp rsp = (AipgRsp) retXs.fromXML(retXml);
		InfoRsp infoRsp = rsp.getInfo();
		RnprRet reprRet = rsp.getRnprRet();
		if (TranxConsts.SUCCESS_CODE.equals(infoRsp.getRET_CODE())) {
			if (!TranxConsts.SUCCESS_CODE.equals(reprRet.getRET_CODE())) {
				return PubFun.errMsg(reprRet.getRET_CODE(), reprRet.getERR_MSG());
			}
		} else {
			return PubFun.errMsg(infoRsp.getRET_CODE(), infoRsp.getERR_MSG());
		}
		return PubFun.sucMsg(infoRsp.getREQ_SN());
	}

	/**
	 * 功能：实名付确认
	 * 
	 * @param reqSn
	 *            实名付申请接口交易流水号(REQ_SN)
	 * @param authCode
	 *            短信验证码(实名付申请或实名付短信重发获取的验证码)
	 * @throws Exception
	 */
	public Map<String, Object> payConfirm(String reqSn, String authCode) throws Exception {
		AipgReq aipgReq = new AipgReq();
		InfoReq info = makeReq("211006C");
		aipgReq.setInfo(info);

		Rnpc rnpc = new Rnpc();
		rnpc.setMERCHANT_ID(TranxConsts.merchantId);
		rnpc.setSRCREQSN(reqSn);
		rnpc.setVERCODE(authCode);
		aipgReq.setRnpc(rnpc);

		XStream xs = new XStream(new DomDriver());
		xs.processAnnotations(AipgReq.class);
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>" + xs.toXML(aipgReq);
		xml = xml.replaceAll("__", "_");
		xml = signMsg(xml);
		String retXml = sendXml(xml,info);
		XStream retXs = new XStream(new DomDriver());
		retXs.processAnnotations(AipgRsp.class);
		AipgRsp rsp = (AipgRsp) retXs.fromXML(retXml);
		InfoRsp infoRsp = rsp.getInfo();
		RnpcRet rnpcRet = rsp.getRnpcRet();
		if (TranxConsts.SUCCESS_CODE.equals(infoRsp.getRET_CODE())) {
			if (!TranxConsts.SUCCESS_CODE.equals(rnpcRet.getRET_CODE())) {
				return PubFun.errMsg(rnpcRet.getRET_CODE(), rnpcRet.getERR_MSG());
			}
		} else {
			return PubFun.errMsg(infoRsp.getRET_CODE(), infoRsp.getERR_MSG());
		}
		return PubFun.sucMsg();
	}

	/**
	 * 实时代笔代收
	 * 
	 * @param trx_code
	 * @throws Exception
	 */
	public Map<String, Object> singleTranx(Trans trans) throws Exception {
		AipgReq aipgReq = new AipgReq();
		InfoReq info = makeReq("100011");
		aipgReq.setInfo(info);
		trans.setBUSINESS_CODE("19900");
		trans.setMERCHANT_ID(TranxConsts.merchantId);
		trans.setSUBMIT_TIME(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		aipgReq.setTrans(trans);

		XStream xs = new XStream(new DomDriver());
		xs.processAnnotations(AipgReq.class);
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>" + xs.toXML(aipgReq);
		xml = xml.replaceAll("__", "_");
		xml = signMsg(xml);
		String retXml = sendXml(xml,info);
		XStream retXs = new XStream(new DomDriver());
		retXs.processAnnotations(AipgRsp.class);
		AipgRsp rsp = (AipgRsp) retXs.fromXML(retXml);
		InfoRsp infoRsp = rsp.getInfo();
		TransRet transRet = rsp.getRransRet();
		if (TranxConsts.SUCCESS_CODE.equals(infoRsp.getRET_CODE())) {
			if (!TranxConsts.SUCCESS_CODE.equals(transRet.getRET_CODE())) {
				return PubFun.errMsg(transRet.getRET_CODE(), transRet.getERR_MSG());
			}
		} else if ("2000".equals(infoRsp.getRET_CODE()) || "2001".equals(infoRsp.getRET_CODE())
				|| "2003".equals(infoRsp.getRET_CODE()) || "2005".equals(infoRsp.getRET_CODE())
				|| "2007".equals(infoRsp.getRET_CODE()) || "2008".equals(infoRsp.getRET_CODE())) {
			LOG.info("交易处理中或者不确定状态，需要在稍后5分钟后进行交易结果查询（轮询）");
			return PubFun.errMsg("WAIT_POLLING", "交易处理中或者不确定状态，需要在稍后5分钟后进行交易结果查询（轮询）");
		} else if (infoRsp.getRET_CODE().startsWith("1")) {
			String errormsg = infoRsp.getERR_MSG() == null ? "连接异常，请重试" : infoRsp.getERR_MSG();
			return PubFun.errMsg(infoRsp.getRET_CODE(), errormsg);
		} else {
			return PubFun.errMsg(transRet.getRET_CODE(), transRet.getERR_MSG());
		}
		return PubFun.sucMsg(info.getREQ_SN());
	}
	
	/**
	 * @param reqsn 交易流水号
	 * @param url 通联地址
	 * @param isTLTFront 是否通过前置机
	 * @param startDate YYYYMMDDHHmmss
	 * @param endDate YYYYMMDDHHmmss
	 * 日期：Sep 4, 2012
	 * 功能：交易结果查询
	 * @throws Exception 
	 */
	public Map<String, Object> queryTradeNew(String reqsn) throws Exception {
		AipgReq aipgReq=new AipgReq();
		InfoReq info=makeReq("200004");
		aipgReq.setInfo(info);
		TransQueryReq dr=new TransQueryReq();
		aipgReq.setTransQueryReq(dr);
		dr.setMERCHANT_ID(TranxConsts.merchantId) ;
		dr.setQUERY_SN(reqsn);
		dr.setSTATUS(1);
		dr.setTYPE(1) ;
		XStream xs = new XStream(new DomDriver());
		xs.processAnnotations(AipgReq.class);
		String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?>" + xs.toXML(aipgReq);
		xml = xml.replaceAll("__", "_");
		xml = signMsg(xml);
		String retXml = sendXml(xml,info);
		XStream retXs = new XStream(new DomDriver());
		retXs.processAnnotations(AipgRsp.class);
		AipgRsp rsp = (AipgRsp) retXs.fromXML(retXml);
		if(!TranxConsts.SUCCESS_CODE.equals(rsp.getInfo().getRET_CODE())){
			if("2000".equals(rsp.getInfo().getRET_CODE())
					||"2001".equals(rsp.getInfo().getRET_CODE())
					||"2003".equals(rsp.getInfo().getRET_CODE())
					||"2005".equals(rsp.getInfo().getRET_CODE())
					||"2007".equals(rsp.getInfo().getRET_CODE())
					||"2008".equals(rsp.getInfo().getRET_CODE())){
				return PubFun.errMsg("processing", "交易处理中");
			}else if("2004".equals(rsp.getInfo().getRET_CODE())){
				return PubFun.errMsg("fail", "交易处理失败");
			}else if("1002".equals(rsp.getInfo().getRET_CODE())){
				return PubFun.errMsg("noquery", "无法流水号对应交易信息");
			}else{
				return PubFun.errMsg("queryFailt", "查询失败");
			}
		}
		return PubFun.sucMsg();
	}

	/**
	 * 报文头INFO节点封装
	 * 
	 * @param trxcod
	 *            交易代码
	 * @return
	 */
	private InfoReq makeReq(String trxcod) {
		InfoReq info = new InfoReq();
		info.setTRX_CODE(trxcod);
		info.setREQ_SN(TranxConsts.merchantId + "-" + String.valueOf(System.currentTimeMillis()) + "-"
				+ NoUtil.getMaxNo("AllinpayDs", 6));
		info.setUSER_NAME(TranxConsts.userName);
		info.setUSER_PASS(TranxConsts.userPassword);
		info.setLEVEL("5");
		info.setDATA_TYPE("2");
		info.setVERSION("04");
		return info;
	}

	/**
	 * 调用接口
	 * 
	 * @param xml
	 *            请求报文
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	private String sendXml(String xml, InfoReq info) throws UnsupportedEncodingException, Exception {
		LOG.info("======================发送报文======================：\n" + xml);
		writeLog(info,"in",xml);
		String retXml = XmlTools.send(TranxConsts.INTERFACE_URL, xml);
		writeLog(info,"out",retXml);
		LOG.info("======================响应报文======================：\n" + retXml);
		boolean flag = verifyMsg(retXml);
		if (!flag) {
			LOG.error("响应报文验签不通过");
		}
		return retXml;
	}
	
	private void writeLog(InfoReq info, String type, String log){
		String filePath = TranxConsts.LOG_PATH + "/" + type + "/" + DateUtil.getCurrentTime("yyyyMMdd")+
				"/"+info.getTRX_CODE()+"-"+info.getREQ_SN()+"-"+DateUtil.getCurrentTime("HHmmss") + ".xml";
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			File file = new File(filePath);
			if(!file.exists()){
				File dir = new File(file.getParent());
				dir.mkdirs();
				file.createNewFile();
			}
            fw = new FileWriter(filePath, true);
            bw = new BufferedWriter(fw);
            bw.write(log);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
    		try {
    			if(bw != null){
    				bw.close();
    			}
    			if(fw != null){
            		fw.close();
            	}
			} catch (IOException e) {
				e.printStackTrace();
        	}
        }
	}

	/**
	 * 请求报文签名
	 * 
	 * @param xml
	 *            请求报文
	 * @return
	 * @throws Exception
	 */
	private String signMsg(String xml) throws Exception {
		xml = XmlTools.signMsg(xml, TranxConsts.pfxPath, TranxConsts.pfxPassword, false);
		return xml;
	}

	/**
	 * 响应报文验签. <br/>
	 * 
	 * @param msg
	 *            返回报文
	 * @param cer
	 *            公钥
	 * @return
	 * @throws Exception
	 */
	private boolean verifyMsg(String msg) throws Exception {
		boolean flag = XmlTools.verifySign(msg, TranxConsts.tltcerPath, false, false);
		return flag;
	}
	
}

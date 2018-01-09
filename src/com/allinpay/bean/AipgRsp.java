package com.allinpay.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 请求响应报文
 */
@XStreamAlias("AIPG")
public class AipgRsp{
	
	@XStreamAlias("INFO")
	private InfoRsp info;
	
	@XStreamAlias("RNPARET")
	private RnpaRet rnpaRet;
	
	@XStreamAlias("RNPRRET")
	private RnprRet rnprRet;
	
	@XStreamAlias("RNPCRET")
	private RnpcRet rnpcRet;
	
	@XStreamAlias("TRANSRET")
	private TransRet rransRet;
	
	@XStreamAlias("QTRANSRSP")
	private QTransRsp qTransRsp;

	public InfoRsp getInfo() {
		return info;
	}

	public void setInfo(InfoRsp info) {
		this.info = info;
	}

	public RnpaRet getRnpaRet() {
		return rnpaRet;
	}

	public void setRnpaRet(RnpaRet rnpaRet) {
		this.rnpaRet = rnpaRet;
	}

	public RnprRet getRnprRet() {
		return rnprRet;
	}

	public void setRnprRet(RnprRet rnprRet) {
		this.rnprRet = rnprRet;
	}

	public RnpcRet getRnpcRet() {
		return rnpcRet;
	}

	public void setRnpcRet(RnpcRet rnpcRet) {
		this.rnpcRet = rnpcRet;
	}

	public TransRet getRransRet() {
		return rransRet;
	}

	public void setRransRet(TransRet rransRet) {
		this.rransRet = rransRet;
	}

	public QTransRsp getqTransRsp() {
		return qTransRsp;
	}

	public void setqTransRsp(QTransRsp qTransRsp) {
		this.qTransRsp = qTransRsp;
	}
	
}

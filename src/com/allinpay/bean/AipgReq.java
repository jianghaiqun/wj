package com.allinpay.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("AIPG")
public class AipgReq
{
	@XStreamAlias("INFO")
	private InfoReq info;
	
	@XStreamAlias("RNPA")
	private Rnpa rnpa;
	
	@XStreamAlias("RNPR")
	private Rnpr rnpr;
	
	@XStreamAlias("RNPC")
	private Rnpc rnpc;
	
	@XStreamAlias("TRANS")
	private Trans trans;
	
	@XStreamAlias("QTRANSREQ")
	private TransQueryReq transQueryReq;
	
	public AipgReq(){}

	public InfoReq getInfo() {
		return info;
	}

	public void setInfo(InfoReq info) {
		this.info = info;
	}

	public Rnpa getRnpa() {
		return rnpa;
	}

	public void setRnpa(Rnpa rnpa) {
		this.rnpa = rnpa;
	}

	public Rnpr getRnpr() {
		return rnpr;
	}

	public void setRnpr(Rnpr rnpr) {
		this.rnpr = rnpr;
	}

	public Rnpc getRnpc() {
		return rnpc;
	}

	public void setRnpc(Rnpc rnpc) {
		this.rnpc = rnpc;
	}

	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}

	public TransQueryReq getTransQueryReq() {
		return transQueryReq;
	}

	public void setTransQueryReq(TransQueryReq transQueryReq) {
		this.transQueryReq = transQueryReq;
	}
	
}

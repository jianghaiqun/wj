package com.wangjin.emar.cpa;

import java.util.Date;

public class Cpa {

	private String cid;

	private String wi;

	private String an;

	private String ana;

	private String ct;

	private int ta;

	private Date sd;

	private String ip;

	public Cpa(String cid, String wi, String an, String ana, String ct, int ta,
			Date sd, String ip) {
		this.cid = cid;
		this.wi = wi;
		this.an = an;
		this.ana = ana;
		this.ct = ct;
		this.ta = ta;
		this.sd = sd;
		this.ip = ip;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getWi() {
		return wi;
	}

	public void setWi(String wi) {
		this.wi = wi;
	}

	public String getAn() {
		return an;
	}

	public void setAn(String an) {
		this.an = an;
	}

	public String getAna() {
		return ana;
	}

	public void setAna(String ana) {
		this.ana = ana;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public int getTa() {
		return ta;
	}

	public void setTa(int ta) {
		this.ta = ta;
	}

	public Date getSd() {
		return sd;
	}

	public void setSd(Date sd) {
		this.sd = sd;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}

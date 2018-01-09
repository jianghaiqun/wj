package com.sinosoft.ibrms.bom;

import java.util.Date;

public class OrderBOM extends AbstractBOM{
	
	private Date currentdate;
	
	private double prem = 0;
	
	private String riskcode = "";
	
	private double chargerate = 0;

	public Date getCurrentdate() {
		return currentdate;
	}

	public void setCurrentdate(Date currentdate) {
		this.currentdate = currentdate;
	}

	public double getPrem() {
		return prem;
	}

	public void setPrem(double prem) {
		this.prem = prem;
	}

	public String getRiskcode() {
		return riskcode;
	}

	public void setRiskcode(String riskcode) {
		this.riskcode = riskcode;
	}

	public double getChargerate() {
		return chargerate;
	}

	public void setChargerate(double chargerate) {
		this.chargerate = chargerate;
	}
	
	
	
}

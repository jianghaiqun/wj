package com.sinosoft.ibrms.bom;

import java.util.Date;

public class MemberBOM  extends AbstractBOM{
	
	private Date registerdate = null;
	
	private Double integral = 0.0;
	
	private Double finalintegral = 0.0;

	public Date getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(Date registerdate) {
		this.registerdate = registerdate;
	}

	public Double getIntegral() {
		return integral;
	}

	public void setIntegral(Double integral) {
		this.integral = integral;
	}

	public Double getFinalintegral() {
		return finalintegral;
	}

	public void setFinalintegral(Double finalintegral) {
		this.finalintegral = finalintegral;
	}
	
	
	
}

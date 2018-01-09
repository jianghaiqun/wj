package com.sinosoft.ibrms.bom;

import java.util.Date;

public class PayBOM extends AbstractBOM{

private double  integralNum = 0;//积分
private double  prem;//保费
private double  poundAge;//手续费率
private int  date;//yyyyMMdd
private int times;//倍数
private int isRegister;//是否注册
public int getIsRegister() {
	return isRegister;
}

public void setIsRegister(int isRegister) {
	this.isRegister = isRegister;
}

public void setIntegralNum(double  integralNum) {
	this.integralNum = integralNum;
}

public double  getIntegralNum() {
	return integralNum;
}

public void setPoundAge(double poundAge) {
	this.poundAge = poundAge;
}

public double getPoundAge() {
	return poundAge;
}

public void setPrem(double prem) {
	this.prem = prem;
}

public double getPrem() {
	return prem;
}



public void setTimes(int times) {
	this.times = times;
}

public int getTimes() {
	return times;
}

public void setDate(int date) {
	this.date = date;
}

public int getDate() {
	return date;
}
	
	
}

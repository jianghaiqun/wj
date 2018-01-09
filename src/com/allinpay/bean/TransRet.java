package com.allinpay.bean;

public class TransRet {

	private String RET_CODE;
	private String ERR_MSG;
	private String SETTLE_DAY;

	public String getRET_CODE() {
		return RET_CODE;
	}

	public void setRET_CODE(String rET_CODE) {
		RET_CODE = rET_CODE;
	}

	public String getERR_MSG() {
		return ERR_MSG;
	}

	public void setERR_MSG(String eRR_MSG) {
		ERR_MSG = eRR_MSG;
	}

	public String getSETTLE_DAY() {
		return SETTLE_DAY;
	}

	public void setSETTLE_DAY(String sETTLE_DAY) {
		SETTLE_DAY = sETTLE_DAY;
	}

}

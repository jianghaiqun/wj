package com.allinpay.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class QTransRsp {

	@XStreamImplicit(itemFieldName = "QTDETAIL")
	private List<QTDetail> details;

	public List<QTDetail> getDetails() {
		return details;
	}

	public void setDetails(List<QTDetail> details) {
		this.details = details;
	}

}

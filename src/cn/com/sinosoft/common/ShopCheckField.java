package cn.com.sinosoft.common;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.com.sinosoft.entity.SDInformation;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDInformationDuty;
import cn.com.sinosoft.entity.SDInformationInsured;
import cn.com.sinosoft.entity.SDInformationProperty;
import cn.com.sinosoft.entity.SDOrder;

import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.utility.CErrors;

public class ShopCheckField {
	
    public CErrors mErrors = new CErrors(); //错误处理类
	/*
	 * 购买流程校验类
	 */

	 
	//投保人信息
	private SDInformationAppnt app;
	//被保人信息
	private SDInformationInsured ins;
	//多被保人信息
	private List<SDInformationInsured> insList;
	//订单信息
	private SDOrder sdorder;
	//订单详细信息
	private SDInformation sdinf;
	//房屋信息
	private List<SDInformationProperty> sdproList;
	//产品小类
	private String subRiskType;
	//存储错误信息
	Errorx tErrorx = new Errorx();
	//责任信息
	private List<SDInformationDuty> dutyList;
	
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

	public CErrors getmErrors() {
		return mErrors;
	}

	public void setmErrors(CErrors mErrors) {
		this.mErrors = mErrors;
	}

	public SDInformationAppnt getApp() {
		return app;
	}

	public void setApp(SDInformationAppnt app) {
		this.app = app;
	}

	public SDInformationInsured getIns() {
		return ins;
	}

	public void setIns(SDInformationInsured ins) {
		this.ins = ins;
	}

	public List<SDInformationInsured> getInsList() {
		return insList;
	}

	public void setInsList(List<SDInformationInsured> insList) {
		this.insList = insList;
	}

	public SDOrder getSdorder() {
		return sdorder;
	}

	public void setSdorder(SDOrder sdorder) {
		this.sdorder = sdorder;
	}

	public SDInformation getSdinf() {
		return sdinf;
	}

	public void setSdinf(SDInformation sdinf) {
		this.sdinf = sdinf;
	}

	public Errorx gettErrorx() {
		return tErrorx;
	}

	public void settErrorx(Errorx tErrorx) {
		this.tErrorx = tErrorx;
	}

	public SimpleDateFormat getSf() {
		return sf;
	}

	public void setSf(SimpleDateFormat sf) {
		this.sf = sf;
	}

	public String getSubRiskType() {
		return subRiskType;
	}

	public void setSubRiskType(String subRiskType) {
		this.subRiskType = subRiskType;
	}

	public List<SDInformationProperty> getSdproList() {
		return sdproList;
	}

	public void setSdproList(List<SDInformationProperty> sdproList) {
		this.sdproList = sdproList;
	}

	public List<SDInformationDuty> getDutyList() {
		return dutyList;
	}

	public void setDutyList(List<SDInformationDuty> dutyList) {
		this.dutyList = dutyList;
	}
	

}
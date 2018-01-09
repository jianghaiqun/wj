package com.sinosoft.platform.service;

import com.sinosoft.framework.extend.IExtendItem;
import com.sinosoft.framework.utility.Mapx;

public class FixedConfigItem implements IExtendItem {
	private String Code;
	private String DataType;
	private String ControlType;
	private String Memo;
	private Mapx<String, String> Options = new Mapx();

	public FixedConfigItem(String code, String dataType, String controlType, String memo) {
		this.Code = code;
		this.DataType = dataType;
		this.ControlType = controlType;
		this.Memo = memo;
	}

	public void addOption(String key, String value) {
		this.Options.put(key, value);
	}

	public String getCode() {
		return this.Code;
	}

	public String getDataType() {
		return this.DataType;
	}

	public String getControlType() {
		return this.ControlType;
	}

	public Mapx<String, String> getOptions() {
		return this.Options;
	}

	public String getID() {
		return getCode();
	}

	public String getName() {
		return getMemo();
	}

	public String getMemo() {
		return this.Memo;
	}
}
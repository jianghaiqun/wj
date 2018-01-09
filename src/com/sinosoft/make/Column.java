package com.sinosoft.make;

//定义列的javabean
public class Column {
	private String name;

	private String type;

	private String defaultValue;

	private boolean pkFlag = false;

	private Integer length;

	private String code;

	private String mandatory;

	private String Comment;

	private boolean mandatoryFlag = false;

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPkFlag() {
		return pkFlag;
	}

	public void setPkFlag(boolean pkFlag) {
		this.pkFlag = pkFlag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setComment(String comment) {
		this.Comment = comment;
	}

	public String getComment() {
		return Comment;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isMandatoryFlag() {
		return mandatoryFlag;
	}

	public void setMandatoryFlag(boolean mandatoryFlag) {
		this.mandatoryFlag = mandatoryFlag;
	}
}

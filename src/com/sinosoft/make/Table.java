package com.sinosoft.make;

//定义表的javabean
public class Table {

	private String tableName;

	private String tableCode;

	private Column[] cols;

	private String pkField;

	public Column[] getCols() {
		return cols;
	}

	public void setCols(Column[] cols) {
		this.cols = cols;
	}

	public String getPkField() {
		return pkField;
	}

	public void setPkField(String pkField) {
		this.pkField = pkField;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}

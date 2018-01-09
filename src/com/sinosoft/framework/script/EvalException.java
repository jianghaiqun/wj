package com.sinosoft.framework.script;

public class EvalException extends Exception {
	private String message;

	private String originMessage;

	private String lineSource;

	private int rowNo;

	private int colNo;

	protected EvalException(String message, String originMessage, String lineSource, int rowNo, int colNo) {
		super(message);
		this.message = message;
		this.originMessage = originMessage;
		this.rowNo = rowNo;
		this.colNo = colNo;
		this.lineSource = lineSource;
	}

	public String toString() {
		return message;
	}

	public int getColNo() {
		return colNo;
	}

	public String getMessage() {
		return message;
	}

	public String getOriginMessage() {
		return originMessage;
	}

	public int getRowNo() {
		return rowNo;
	}

	public String getLineSource() {
		return lineSource;
	}
}

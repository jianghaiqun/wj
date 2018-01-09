package com.sinosoft.workflow;

/**
 * 普通的工作流异常
 * 
 */
public class WorkflowException extends Exception {

	private static final long serialVersionUID = 1L;

	public WorkflowException(String message) {
		super(message);
	}
}

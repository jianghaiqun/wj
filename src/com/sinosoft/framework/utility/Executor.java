package com.sinosoft.framework.utility;

/**
 * 执行器,将要执行的JAVA逻辑传递给其他函数，让其他函数择机调用。<br>
 * 
 */
public abstract class Executor {
	protected Object param;

	public Executor(Object param) {
		this.param = param;
	}

	public abstract boolean execute();
}

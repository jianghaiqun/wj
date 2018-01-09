package com.sinosoft.framework.extend;


/**
 * 需要在宿主类中扩展逻辑的类必须继承此类
 * 
 */
public interface IExtendAction {
	/**
	 * 目标扩展点
	 */
	public String getTarget();

	/**
	 * 扩展名称
	 */
	public String getName();

	/**
	 * 扩展逻辑
	 */
	public void execute(Object[] args);
}

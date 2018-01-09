/**
 * 
 */
package com.sinosoft.dubbo.interfaces;

/**
 * @author yuzaijiang
 * 核保数据保存接口 
 */
public interface TbOrderInsureDataSaving {
	public static final String SUCCESS = "in_success";
	public static final String FAIL = "fail";
	public String saveData(String xmlReq);
}

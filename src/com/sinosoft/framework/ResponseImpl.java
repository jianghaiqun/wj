package com.sinosoft.framework;

import com.sinosoft.framework.data.DataCollection;

/**
 * 对ServletResponse的封装
 * 
 */
public class ResponseImpl extends DataCollection {
	private static final long serialVersionUID = 1L;

	public int Status = 1;

	public String Message = "";

	/**
	 * 获取本次后台方法返回给JavaScript的消息
	 * 
	 * @return
	 */
	public String getMessage() {
		return Message;
	}

	/**
	 * 设置本次后台方法返回给JavaScript的消息，并将状态设为0
	 * 
	 * @param message
	 */
	public void setError(String message) {
		setMessage(message);
		setStatus(0);
	}

	/**
	 * 设置本次后台方法返回给JavaScript的消息
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		Message = message;
		this.put(Constant.ResponseMessageAttrName, Message);
	}

	/**
	 * 获取本次后台方法返回给JavaScript的状态码，一般情况下0表示执行异常，1表示执行成功
	 * 
	 * @return
	 */
	public int getStatus() {
		return Status;
	}

	/**
	 * 设置本次后台方法返回给JavaScript的状态码
	 * 
	 * @param status
	 */
	public void setStatus(int status) {
		Status = status;
		this.put(Constant.ResponseStatusAttrName, Status);
	}

	/**
	 * 设置本次后台方法返回给JavaScript的消息及状态码
	 * 
	 * @param status
	 * @param message
	 */
	public void setLogInfo(int status, String message) {
		Status = status;
		this.put(Constant.ResponseStatusAttrName, Status);
		Message = message;
		this.put(Constant.ResponseMessageAttrName, Message);
	}
	
	public void setLogInfo(int status, String message, String ID) {
		Status = status;
		this.put(Constant.ResponseStatusAttrName, Status);
		Message = message;
		this.put(Constant.ResponseMessageAttrName, Message);
		this.put(Constant.ID, ID);
	}
	
	/**
	 * 将ResponseImpl转化成XML
	 * 
	 * @see com.sinosoft.framework.data.DataCollection#toXML()
	 */
	public String toXML() {
		this.put(Constant.ResponseStatusAttrName, Status);
		return super.toXML();
	}
}

package cn.com.sinosoft.entity;

import java.util.Map;

/**
 * 实体类-通知 （邮件或短信）
 * 
 * @author guobin
 * 
 */
public class Notify {
	/**
	 * 动作编码
	 */
	private String ActionID;
	/**
	 * 发送的地址
	 */
	private String NotifyAddress;
	/**
	 * 发送的时间,如果为空表示实时发送；
	 */
	private String SendDate;
	/**
	 * 发送的内容
	 */
	private Map<String, Object> Content;

	public Notify(String actionID, String notifyAddress, Map<String, Object> content) {
		super();
		ActionID = actionID;
		NotifyAddress = notifyAddress;
		Content = content;
	}

	public Notify(String actionID, String notifyAddress, String sendDate, Map<String, Object> content) {
		super();
		ActionID = actionID;
		NotifyAddress = notifyAddress;
		SendDate = sendDate;
		Content = content;
	}

	public String getActionID() {
		return ActionID;
	}

	public void setActionID(String actionID) {
		ActionID = actionID;
	}

	public String getNotifyAddress() {
		return NotifyAddress;
	}

	public void setNotifyAddress(String notifyAddress) {
		NotifyAddress = notifyAddress;
	}

	public String getSendDate() {
		return SendDate;
	}

	public void setSendDate(String sendDate) {
		SendDate = sendDate;
	}

	public Map<String, Object> getContent() {
		return Content;
	}

	public void setContent(Map<String, Object> content) {
		Content = content;
	}

}

package cn.com.sinosoft.entity;

import java.util.List;

/**
 * 物流信息类
 *
 */
public class LogisticsInfo {

	/**
	 * 物流状态信息时间，地点
	 *
	 */
	public static class Item{
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getContext() {
			return context;
		}
		public void setContext(String context) {
			this.context = context;
		}
		private String time;
		private String context;
	}
	
	private String status;//返回结果状态
	private String message;//错误消息
	private String errCode;//错误代码
	private List<Item> data;//物流信息
	private String html;
	private String mailNo;
	private String expTextName;
	private String expSpellName;
	private String update;//更新时间
	private String cache;
	private String ord;//排序
	private String tel;//电话

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public List<Item> getData() {
		return data;
	}

	public void setData(List<Item> data) {
		this.data = data;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getMailNo() {
		return mailNo;
	}

	public void setMailNo(String mailNo) {
		this.mailNo = mailNo;
	}

	public String getExpTextName() {
		return expTextName;
	}

	public void setExpTextName(String expTextName) {
		this.expTextName = expTextName;
	}

	public String getExpSpellName() {
		return expSpellName;
	}

	public void setExpSpellName(String expSpellName) {
		this.expSpellName = expSpellName;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getCache() {
		return cache;
	}

	public void setCache(String cache) {
		this.cache = cache;
	}

	public String getOrd() {
		return ord;
	}

	public void setOrd(String ord) {
		this.ord = ord;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}

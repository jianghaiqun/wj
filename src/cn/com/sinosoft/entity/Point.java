package cn.com.sinosoft.entity;

public class Point {
	public static final int LIST_COUNT = 10; 
	private String point;
	private  int usedPoint;
	private int currentValidatePoint;
	private String createDate;
	private String createTime;
	private String Source;
	private String manner;
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public int getUsedPoint() {
		return usedPoint;
	}
	public void setUsedPoint(int usedPoint) {
		this.usedPoint = usedPoint;
	}
	public int getCurrentValidatePoint() {
		return currentValidatePoint;
	}
	public void setCurrentValidatePoint(int currentValidatePoint) {
		this.currentValidatePoint = currentValidatePoint;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSource() {
		return Source;
	}
	public void setSource(String source) {
		Source = source;
	}
	public String getManner() {
		return manner;
	}
	public void setManner(String manner) {
		this.manner = manner;
	}
}

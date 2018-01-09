/**
 * @CopyRight:Sinosoft
 * @File:QueryBuilder.java
 * @CreateTime:2012-3-13 上午8:45:32
 * @Package:cn.com.sinosoft.entity
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LiuXin
 * 
 */
public class QueryWGQ implements Serializable{
	private static final long serialVersionUID = 3092212646991009521L;
	
	private String property;
	private String sign;
	private String value;
	private Date date;
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	
}

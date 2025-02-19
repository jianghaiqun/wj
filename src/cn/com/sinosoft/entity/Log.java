package cn.com.sinosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 实体类 - 日志
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTF75093F3168097AE8B8496F61E075DA3
 * ============================================================================
 */

@Entity
public class Log extends BaseEntity {

	private static final long serialVersionUID = -4494144902110236826L;

	private String operationName;// 操作名称
	private String operator;// 操作员
	private String actionClassName;// 操作Action名称
	private String actionMethodName;// 操作方法名称
	private String ip;// IP
	private String info;// 日志信息
	private String channel;//日志所属渠道
	@Column(nullable = false)
	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	@Column(nullable = false)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Column(nullable = false)
	public String getActionClassName() {
		return actionClassName;
	}

	public void setActionClassName(String actionClassName) {
		this.actionClassName = actionClassName;
	}

	@Column(nullable = false)
	public String getActionMethodName() {
		return actionMethodName;
	}

	public void setActionMethodName(String actionMethodName) {
		this.actionMethodName = actionMethodName;
	}

	@Column(nullable = false)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(length = 5000)
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	@Column(nullable = false)
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
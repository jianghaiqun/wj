package cn.com.sinosoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 实体类 - 会员注册协议
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTB434FB5ED72E29A02354F64D0573E959
 * ============================================================================
 */

@Entity
public class Agreement extends BaseEntity {

	private static final long serialVersionUID = 7226979256801891226L;

	public static final String AGREEMENT_ID = "1";// 记录ID

	private String content;// 内容

	@Column(length = 10000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
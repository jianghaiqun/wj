/**
 * Project Name:wj-code
 * File Name:RiskCompanyCodeEnum.java
 * Package Name:com.sinosoft.framework.enums
 * Date:2017年11月2日下午1:42:57
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.framework.enums;
/**
 * ClassName:RiskCompanyCodeEnum <br/>
 * Function:TODO 保险公司编码枚举. <br/>
 * Date:2017年11月2日 下午1:42:57 <br/>
 *
 * @author:guozc
 */
public enum RiskCompanyCodeEnum {

	ALLIANZ("2049", "安联");
	
	private final String code;

	private final String name;

	private RiskCompanyCodeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return name;
	}
	
}


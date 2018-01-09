/**
 * Project Name:wj
 * File Name:Gender.java
 * Package Name:com.sinosoft.asyninter
 * Date:2016年9月21日下午3:08:57
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */
/**
 * Project Name:wj
 * File Name:Gender.java
 * Package Name:com.sinosoft.asyninter
 * Date:2016年9月21日下午3:08:57
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.asyninter;

import com.sinosoft.framework.utility.StringUtil;

/**
 * ClassName: Gender <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选,不用就删除). <br/>
 * date: 2016年9月21日 下午3:08:57 <br/>
 *
 * @author dongsheng
 * @version
 */
public enum Gender {
	MALE("男", "M"), FEMALE("女", "F"), NEUTRAL("N", "N");

	private String name;
	private String code;

	/**
	 * Creates a new instance of Gender.
	 *
	 */

	private Gender(String name, String code) {

		this.name = name;
		this.code = code;
	}

	/**
	 * name.
	 *
	 * @return the name
	 */
	public String getName() {

		return name;
	}

	/**
	 * code.
	 *
	 * @return the code
	 */
	public String getCode() {

		return code;
	}

	public static Gender getGenderByName(String name) {

		if (StringUtil.isEmpty(name)) {
			return null;
		}
		name = name.toUpperCase().trim();
		Gender[] all = Gender.values();
		for (Gender gender : all) {
			if (gender.getName().equals(name)) {

				return gender;

			}
		}
		return null;

	}

	public static Gender getGenderByCode(String code) {

		if (StringUtil.isEmpty(code)) {
			return null;
		}
		code = code.toUpperCase().trim();
		Gender[] all = Gender.values();
		for (Gender gender : all) {
			if (gender.getCode().equals(code)) {
				return gender;

			}
		}
		return null;

	}

}

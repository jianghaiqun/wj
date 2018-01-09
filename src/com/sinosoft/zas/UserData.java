package com.sinosoft.zas;

import java.util.HashMap;

public class UserData {
	private String UserName;
	private HashMap<String,String> data = new HashMap<String,String>();

	public String getValue(String key) {
		return (String) this.data.get(key);
	}

	public void setValue(String key, String value) {
		this.data.put(key, value);
	}

	public String getUserName() {
		return this.UserName;
	}

	public void setUserName(String userName) {
		this.UserName = userName;
	}
}

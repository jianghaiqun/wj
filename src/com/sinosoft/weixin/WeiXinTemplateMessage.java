/**
 * Project Name:FrontTrade File WeiXinTemplateMessage.java Package
 * Name:com.finance.model.activity Date:2017年2月9日下午3:13:24 Copyright (c) 2017,
 * www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.weixin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: WeiXinTemplateMessage <br/>
 * date: 2017年2月9日 下午3:13:24 <br/>
 *
 * @author dongsheng
 * @version
 */
public class WeiXinTemplateMessage implements WeiXinMessage {

	private String toOpenid;
	private String templateId;
	private String url;
	// WeiXinCommon.getWXDataMap("value", value)
	private Map<String, Object> first;
	private Map<String, Object> word1;
	private Map<String, Object> word2;
	private Map<String, Object> word3;
	private Map<String, Object> word4;
	private Map<String, Object> word5;
	private Map<String, Object> remark;
	private Date sendDateTime;
	private String message;

	public static Map<String, Object> buildValueMap(String value, String colorRGB) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("value", value);
		map.put("color", colorRGB);
		return map;
	}

	public static Map<String, Object> buildValueMap(String value) {

		return buildValueMap(value, "#000000");
	}

	@Override
	public String toString() {

		final StringBuilder sb = new StringBuilder("WeiXinTemplateMessage{");
		sb.append("toOpenid='").append(toOpenid).append('\'');
		sb.append(", templateId='").append(templateId).append('\'');
		sb.append(", url='").append(url).append('\'');
		sb.append(", first=").append(first);
		sb.append(", word1=").append(word1);
		sb.append(", word2=").append(word2);
		sb.append(", word3=").append(word3);
		sb.append(", word4=").append(word4);
		sb.append(", word5=").append(word5);
		sb.append(", remark=").append(remark);
		sb.append(", sendDateTime=").append(sendDateTime);
		sb.append(", message='").append(message).append('\'');
		sb.append('}');
		return sb.toString();
	}

	public String getToOpenid() {

		return toOpenid;
	}

	public WeiXinTemplateMessage setToOpenid(String toOpenid) {

		this.toOpenid = toOpenid;
		return this;
	}

	public String getTemplateId() {

		return templateId;
	}

	public WeiXinTemplateMessage setTemplateId(String templateId) {

		this.templateId = templateId;
		return this;
	}

	public String getUrl() {

		return url;
	}

	public WeiXinTemplateMessage setUrl(String url) {

		this.url = url;
		return this;
	}

	public Map<String, Object> getFirst() {

		return first;
	}

	public WeiXinTemplateMessage setFirst(Map<String, Object> first) {

		this.first = first;
		return this;
	}

	public Map<String, Object> getWord1() {

		return word1;
	}

	public WeiXinTemplateMessage setWord1(Map<String, Object> word1) {

		this.word1 = word1;
		return this;
	}

	public Map<String, Object> getWord2() {

		return word2;
	}

	public WeiXinTemplateMessage setWord2(Map<String, Object> word2) {

		this.word2 = word2;
		return this;
	}

	public Map<String, Object> getWord3() {

		return word3;
	}

	public WeiXinTemplateMessage setWord3(Map<String, Object> word3) {

		this.word3 = word3;
		return this;
	}

	public Map<String, Object> getWord4() {

		return word4;
	}

	public WeiXinTemplateMessage setWord4(Map<String, Object> word4) {

		this.word4 = word4;
		return this;
	}

	public Map<String, Object> getWord5() {

		return word5;
	}

	public WeiXinTemplateMessage setWord5(Map<String, Object> word5) {

		this.word5 = word5;
		return this;
	}

	public Map<String, Object> getRemark() {

		return remark;
	}

	public WeiXinTemplateMessage setRemark(Map<String, Object> remark) {

		this.remark = remark;
		return this;
	}

	public Date getSendDateTime() {

		return sendDateTime;
	}

	public WeiXinTemplateMessage setSendDateTime(Date sendDateTime) {

		this.sendDateTime = sendDateTime;
		return this;
	}

	public String getMessage() {

		return message;
	}

	public WeiXinTemplateMessage setMessage(String message) {

		this.message = message;
		return this;
	}
}

/**
 * Project Name:wj_sstry
 * File Name:points5.java
 * Package Name:cn.com.sinosoft.util
 * Date:2016年8月22日上午11:01:51
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.util;

import java.util.HashMap;
import java.util.Map;

import cn.com.sinosoft.entity.Member;

import com.sinosoft.framework.utility.StringUtil;

/**
 * ClassName:points5 <br/>
 * Function:TODO 积分商城--账号直冲. <br/>
 * Date:2016年8月22日 上午11:01:51 <br/>
 *
 * @author:"liang.sl" 
 */
public class Points6 extends PointExchangeInterface {


	@Override
	public Map<String, String>  checkParams(String mobile) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		String id = memberid;
		if (StringUtil.isEmpty(id)) {
			jsonMap.put(MESSAGE, "您还没有登录，不能进行兑换!");
			jsonMap.put(STATUS, "2");
			return jsonMap;
		}
		Member member = memberService.load(id);
		if (member == null) {
			jsonMap.put(MESSAGE, "您还没有登录，不能进行兑换!");
			jsonMap.put(STATUS, "2");
			return jsonMap;
		}
		jsonMap.put(STATUS, "success");
		jsonMap.put(MESSAGE, "账号直冲 " + mobile);
		jsonMap.put("type", "1");
		jsonMap.put("memberId", member.getId());
		jsonMap.put("mem_point", String.valueOf(member.getCurrentValidatePoint()));
		return jsonMap;
	}

	@Override
	public Map<String,String> checkPoint(String cInfo) {
		return super.checkPoint(cInfo);
	}
	
	@Override
	public Map<String, String> Exchange(String cInfo) {
		return super.Exchange(cInfo);
	}
}


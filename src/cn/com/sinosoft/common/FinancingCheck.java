/**
 * Project Name:wj
 * File Name:IsLcxCheck.java
 * Package Name:cn.com.sinosoft.common
 * Date:2016年6月23日上午8:35:52
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.common;

import java.util.Arrays;
import java.util.List;

import com.sinosoft.framework.Config;

/**
 * ClassName:IsLcxCheck <br/>
 * Function:TODO 描述类是干什么的. <br/>
 * Date:2016年6月23日 上午8:35:52 <br/>
 *
 * @author:chouweigao
 */
public class FinancingCheck {

	/**
	 * 1.判断当前产品是不是理财险 
	 * 2.理财险产品必须先登录
	 * 
	 * @param productId 产品id
	 * @return 0：是理财险 1:正常保险
	 * @author chouweigao
	 * 
	 */
	public static String getLcx(String productId) {

		String[] lcx_arr = Config.getValue("LicaiBaoxianProducts").split(",");
		List<String> listLcx = Arrays.asList(lcx_arr);
		if (listLcx.contains(productId)) {
			// 记录产品是理财险
			return "0";
		} else {
			return "1";
		}
	}
}

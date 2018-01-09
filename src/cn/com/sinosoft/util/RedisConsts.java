/**
 * Project Name:wj-code
 * File Name:RedisConsts.java
 * Package Name:cn.com.sinosoft.util
 * Date:2017年5月15日下午5:46:06
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.util;
/**
 * ClassName:RedisConsts <br/>
 * Function:TODO redis缓存相关常量定义. <br/>
 * Date:2017年5月15日 下午5:46:06 <br/>
 *
 * @author:guozc
 */
public class RedisConsts {
	

	//------------------------------db定义-------------------------------

	/**
	 * 存放产品属性(销量数、评论数) db定义
	 */
	public static final int DB_PRODUCT_ATTR = 0;
	
	
	//------------------------------key定义-------------------------------
	
	/**
	 * 产品属性 key前缀定义
	 */
	public static final String KEY_PREFIX_PRODUCTATTR = "PRODUCTATTR_";
	/**
	 * 产品属性-销售统计map key名
	 */
	public static final String MAPKEY_PRODUCTATTR_SALESVLUME = "sales_vlume";
	/**
	 * 产品属性-评论数量统计map key名
	 */
	public static final String MAPKEY_PRODUCTATTR_COMMENTCOUNT = "comment_count";
	
	/**
	 * 首页旅游目的地key名
	 */
	public static final String REDIS_KEY_TRAVEL_ADDR = "TRAVEL_ADDR";
	
	
}


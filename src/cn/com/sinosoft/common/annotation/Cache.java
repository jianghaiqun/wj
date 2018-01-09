/**
 * Project Name:email
 * File Name:Cache.java
 * Package Name:com.finance.annotation
 * Date:2017年5月2日上午8:49:09
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ClassName:Cache <br/>
 * Function:TODO 查询缓存注解. <br/>
 * Date:2017年5月2日 上午8:49:09 <br/>
 *
 * @author:guozc 
 */
@Target({ElementType.METHOD,ElementType.TYPE})  
@Retention(RetentionPolicy.RUNTIME)  
@Documented 
public @interface Cache {

	/**
	 * key值前缀
	 */
	String keyPrefix() default "";
	
	/**
	 * map类型key名称
	 */
	String mapKey() default "";
	
	/**
	 * 数据类型(1:string,2:map,3:list)
	 */
	int dataType() default 1;
	
	/**
	 * 数据库索引
	 */
	int db() default 0;
	
	
	
	
}


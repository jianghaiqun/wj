/**
 * Project Name:email
 * File Name:CacheAspect.java
 * Package Name:com.finance.aop
 * Date:2017年5月2日上午8:53:15
 * Copyright (c) 2017, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.common.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.finance.util.JedisCommonUtil;
import cn.com.sinosoft.common.annotation.Cache;

/**
 * ClassName:CacheAspect <br/>
 * Function:TODO 查询缓存处理. <br/>
 * Date:2017年5月2日 上午8:53:15 <br/>
 *
 * @author:guozc
 */
@Aspect
@Component
public class CacheAspect {

	@Pointcut("@annotation(cn.com.sinosoft.common.annotation.Cache)")
	public void cachePointcutAop() {
	}

	@Around("cachePointcutAop()")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method m = signature.getMethod();
		if (m.getDeclaringClass().isInterface()) {
			m = joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(),
					m.getParameterTypes());
		}
		Class returnType = signature.getReturnType();// 方法返回类型
		Object[] args = joinPoint.getArgs();// 方法参数
		Cache cache = m.getAnnotation(Cache.class);// 注解实例
		int db = cache.db();
		/**
		 * 拼装key名称
		 */
		String cacheKey = cache.keyPrefix();
		if (args.length > 0) {
			cacheKey += StringUtils.join(args, ",");
		}
		/**
		 * 根据不同数据类型从redis中取值
		 */
		String cacheStr = null;
		int dataType = cache.dataType();
		if (dataType == 2) {
			List<String> values = JedisCommonUtil.getMap(db, cacheKey, cache.mapKey());
			if (values.get(0) != null) {
				cacheStr = values.get(0);
			}
		} else if (dataType == 3) {

		} else {
			cacheStr = JedisCommonUtil.getString(db, cacheKey);
		}
		/**
		 * 判断如果redis中没有则从数据库中查询并放入redis中
		 */
		Object object = null;
		if (cacheStr != null) {
			object = JSONObject.parseObject(cacheStr, returnType);
		} else {
			object = joinPoint.proceed();
			cacheStr = JSONObject.toJSONString(object);
			if (dataType == 2) {
				Map<String, String> propertys = new HashMap<String, String>();
				propertys.put(cache.mapKey(), cacheStr);
				JedisCommonUtil.setMap(db, cacheKey, propertys);
			} else if (dataType == 3) {

			} else {
				JedisCommonUtil.setString(db, cacheKey, cacheStr);
			}
		}
		return object;
	}
}

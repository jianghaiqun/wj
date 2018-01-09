/**
 * @CopyRight:Sinosoft
 * @File:CityService.java
 * @CreateTime:2012-6-13 上午8:32:02
 * @Package:cn.com.sinosoft.service
 * @Author:LiuXin
 * @Version:1.0
 */
package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.City;

/**
 * @author LiuXin
 *
 */
public interface CityService extends BaseService<City,String>{
	public List<City> selectCity();

}

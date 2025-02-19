package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.Navigation;


/**
 * Service接口 - 导航
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTE9613E05E492035FCAAF274063C63401
 * ============================================================================
 */

public interface NavigationService extends BaseService<Navigation, String> {

	/**
	 * 获取顶部Navigation对象集合（只包含isVisible=true的对象）
	 * 
	 * @return Navigation对象集合
	 * 
	 */
	public List<Navigation> getTopNavigationList();
	
	/**
	 * 获取中间Navigation对象集合（只包含isVisible=true的对象）
	 * 
	 * @return Navigation对象集合
	 * 
	 */
	public List<Navigation> getMiddleNavigationList();
	
	/**
	 * 获取底部Navigation对象集合（只包含isVisible=true的对象）
	 * 
	 * @return Navigation对象集合
	 * 
	 */
	public List<Navigation> getBottomNavigationList();

}
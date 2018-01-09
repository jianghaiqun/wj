package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.Footer;

/**
 * Dao接口 - 页面底部信息
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT6FB6866ABE8BB8E7249ABB5DCF4C570F
 * ============================================================================
 */

public interface FooterDao extends BaseDao<Footer, String> {
	
	/**
	 * 获取Footer对象
	 * 
	 * @return Footer对象
	 * 
	 */
	public Footer getFooter();

}

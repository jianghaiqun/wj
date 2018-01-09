package cn.com.sinosoft.dao.impl;


import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.FooterDao;
import cn.com.sinosoft.entity.Footer;

/**
 * Dao实现类 - 网页底部信息
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTBA46AA09A308786C17905F09A398AB80
 * ============================================================================
 */

@Repository
public class FooterDaoImpl extends BaseDaoImpl<Footer, String> implements FooterDao {

	public Footer getFooter() {
		return load(Footer.FOOTER_ID);
	}

}

package cn.com.sinosoft.dao.impl;

import cn.com.sinosoft.dao.WxbindDao;
import cn.com.sinosoft.entity.Wxbind;
import cn.com.sinosoft.util.CommonUtil;
import com.sinosoft.framework.data.QueryBuilder;
import org.springframework.stereotype.Repository;


/**
 * Dao实现类 - 会员
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTAC8DDA6F41F51B7BA7B541180E9FE7F7
 * ============================================================================
 */

@Repository
public class WxbindDaoImpl extends BaseDaoImpl<Wxbind, String> implements WxbindDao {
	
	public Wxbind getWxbindByOpenID(String openID){
		String hql = "from Wxbind where openId=?";
		Wxbind tWxbind =  (Wxbind) getSession().createQuery(hql).setParameter(0, openID).uniqueResult();
		if (tWxbind != null) {
			return tWxbind;
		} else {
			return null;
		}
		
	}

	public int bindOpenIdAndMemberId(Wxbind wxbind) {
		Wxbind wxbindTmp = getWxbindByOpenID(wxbind.getOpenId());

		if (wxbindTmp == null) {
			String sql = "insert into wxbind values('"
					+ CommonUtil.getUUID() + "',now(),now(),'"
					+ wxbind.getMemAccount() + "','"
					+ wxbind.getMemberId() + "','"
					+ wxbind.getOpenId() + "','"
					+ wxbind.getRemark() + "')";
			QueryBuilder qb = new QueryBuilder(sql);
			return qb.executeNoQuery();
		}
		
		// removed by guozc 之前微信已经有绑定开心保账号，则不进行绑定变更@req20171019702-微信活动规则梳理
		/*else {
			sql = "insert into wxbind values('"
					+ CommonUtil.getUUID() + "',now(),now(),'"
					+ wxbind.getMemAccount() + "','"
					+ wxbind.getMemberId() + "','"
					+ wxbind.getOpenId() + "','"
					+ wxbind.getRemark() + "')";
		}*/
		
		return 0;
	}
}
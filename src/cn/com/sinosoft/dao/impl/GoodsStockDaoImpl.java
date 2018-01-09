/**
 * 
 */
package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.GoodsStockDao;
import cn.com.sinosoft.entity.GoodsStock;

/**
 * @author Administrator
 *
 */
@Repository
public class GoodsStockDaoImpl extends BaseDaoImpl<GoodsStock, String> implements GoodsStockDao {

	/**
	 * 
	 * @param paySn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsStock> goodslist(String giftid) {
		String hql = "from GoodsStock  where giftID = ? and status='0' AND UNIX_TIMESTAMP(DATE_FORMAT(outDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%S')) order by createdate asc ";
		return (List<GoodsStock>) getSession().createQuery(hql).setParameter(0,giftid).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsStock> goodslistByType(String giftid,String type){
		String hql = "from GoodsStock  where giftID = ? and type=? and status='0' AND UNIX_TIMESTAMP(DATE_FORMAT(outDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%S')) order by createdate asc ";
		return (List<GoodsStock>) getSession().createQuery(hql).setParameter(0,giftid).setParameter(1,type).list();
	}
}

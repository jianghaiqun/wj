/**
 * 
 */
package cn.com.sinosoft.dao;

import java.util.List;

import cn.com.sinosoft.entity.GoodsStock;

/**
 * @author Administrator
 *
 */
public interface GoodsStockDao extends BaseDao<GoodsStock, String> {

	/**
	 * 根据礼品ID查询库存
	 * @param giftid
	 * @return
	 */
	public List<GoodsStock> goodslist(String giftid);
	/**
	 * 
	* @Title: goodslistByType 
	* @Description: TODO( 根据礼品ID和类型查询库存) 
	* @return List<GoodsStock>    返回类型 
	* @author
	 */
	public List<GoodsStock> goodslistByType(String giftid,String type);
}

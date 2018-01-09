package cn.com.sinosoft.dao;

import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.GiftClassify;

import com.sinosoft.framework.data.DataTable;

/**
 * Dao接口 - 礼品
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT9B6182BB453DB3970191ECBF6F4D8AD0
 * ============================================================================
 */

public interface GiftClassifyDao extends BaseDao<GiftClassify, String> {
	
	/**
	 * 获取热销礼品
	 * @return
	 */
	public List<GiftClassify> hotgiftlistby();
	/**
	 * 获取热销礼品
	 * @return
	 */
	public List<GiftClassify> recgiftlistby();
	
	/**
	 * 热门兑换查询
	 * @return
	 */
	public List<GiftClassify> hotExchange();
	
	/**
	 * 取得保险产品礼品库存量
	 * 
	 * @param productId
	 *            保险产品id
	 * @return 库存量
	 */
	public int getLastNum(String productId);
	
	/**
	 * 取得保险产品礼品信息
	 * 
	 * @param productId
	 *            保险产品id
	 * @return 礼品信息
	 */
	public GiftClassify getGiftClassifyByProductId(String productId);
	
	/**
	 * 取得礼品信息
	 * 
	 * @param Id
	 * @return 礼品信息
	 */
	public GiftClassify getGiftClassify(String id);
	/**
	 * 
	* @Title: getGiftClassifyList 
	* @Description: TODO(获取礼品列表) 
	* @return List<GiftClassify>    返回类型 
	* @author
	 */
	public List<GiftClassify> getGiftClassifyList(Map<String,String> map) ;
	/**
	 * 
	* @Title: getGiftClassifyList_ibatis 
	* @Description: (获取礼品列表) 
	* @return DataTable    返回类型 
	* @author
	 */
	public DataTable getGiftClassifyList_ibatis(Map<String,String> map) ;
	/**
	 * 
	* @Title: getGiftClassifyList 
	* @Description: TODO(获取礼品列表数量) 
	* @return List<GiftClassify>    返回类型 
	* @author
	 */
	public int getGiftClassifyListNum(Map<String,String> map) ;
}
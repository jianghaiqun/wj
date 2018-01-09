package cn.com.sinosoft.service;

import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.GiftClassify;

import com.sinosoft.framework.data.DataTable;

/**
 * Service接口 - 订单
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTC25D50D18A27A8E1B4A11F7643DAA055
 * ============================================================================
 */

public interface GiftClassifyService extends BaseService<GiftClassify, String> {
	
	/**
	 * 获取热销礼品
	 * @return
	 */
	public List<GiftClassify> hotgiftlistby();
	/**
	 * 获取推荐礼品
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
	 *            id
	 * @return 礼品信息
	 */
	public GiftClassify getGiftClassify(String Id);
	/**
	 * 
	* @Title: getGiftClassifyList 
	* @Description: TODO(获取礼品列表) 
	* @return List<GiftClassify>    返回类型 
	* @author
	 */
	public List<GiftClassify> getGiftClassifyList(Map<String,String> map);
	/**
	 * 
	* @Title: getGiftClassifyList_ibatis 
	* @Description: (获取礼品列表) 
	* @return DataTable    返回类型 
	* @author
	 */
	public DataTable getGiftClassifyList_ibatis(Map<String,String> map);
	/**
	 * 
	* @Title: getGiftClassifyList 
	* @Description: TODO(获取礼品列表数量) 
	* @return List<GiftClassify>    返回类型 
	* @author
	 */
	public int getGiftClassifyListNum(Map<String,String> map);
}
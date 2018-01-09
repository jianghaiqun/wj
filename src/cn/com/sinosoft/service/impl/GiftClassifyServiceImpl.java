package cn.com.sinosoft.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.GiftClassifyDao;
import cn.com.sinosoft.entity.GiftClassify;
import cn.com.sinosoft.service.GiftClassifyService;

import com.sinosoft.framework.data.DataTable;

/**
 * Service实现类 - 礼品表
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTDD7076EB801A97081A271D1D0D7AF8F7
 * ============================================================================
 */

@Service
public class GiftClassifyServiceImpl extends BaseServiceImpl<GiftClassify, String> implements GiftClassifyService {
	
	@Resource
	private GiftClassifyDao mGiftClassifyDao;

	@Resource
	public void setBaseDao(GiftClassifyDao tGiftClassifyDao) { 
		super.setBaseDao(tGiftClassifyDao);
	}

	@Override
	public List<GiftClassify> hotgiftlistby() {
		
		return mGiftClassifyDao.hotgiftlistby();
	}
	@Override
	public List<GiftClassify> recgiftlistby() {
		
		return mGiftClassifyDao.recgiftlistby();
	}
	
	/**
	 * 热门兑换查询
	 * @return
	 */
	public List<GiftClassify> hotExchange() {
		return mGiftClassifyDao.hotExchange();
	}
	
	/**
	 * 取得保险产品礼品库存量
	 * 
	 * @param productId
	 *            保险产品id
	 * @return 库存量
	 */
	public int getLastNum(String productId) {
		return mGiftClassifyDao.getLastNum(productId);
	}
	
	/**
	 * 取得保险产品礼品信息
	 * 
	 * @param productId
	 *            保险产品id
	 * @return 礼品信息
	 */
	public GiftClassify getGiftClassifyByProductId(String productId) {
		return mGiftClassifyDao.getGiftClassifyByProductId(productId);
	}
	
	/**
	 * 取得礼品信息
	 * 
	 * @param Id
	 * @return 礼品信息
	 */
	public GiftClassify getGiftClassify(String Id) {
		return mGiftClassifyDao.getGiftClassify(Id);
	}
	/**
	 * 
	* @Title: getGiftClassifyList 
	* @Description: TODO(获取礼品列表) 
	* @return List<GiftClassify>    返回类型 
	* @author
	 */
	public List<GiftClassify> getGiftClassifyList(Map<String,String> map) {
		
		return mGiftClassifyDao.getGiftClassifyList(map);
	}
	/**
	 * 
	* @Title: getGiftClassifyList_ibatis 
	* @Description: (获取礼品列表) 
	* @return DataTable    返回类型 
	* @author
	 */
	public DataTable getGiftClassifyList_ibatis(Map<String,String> map) {
		
		return mGiftClassifyDao.getGiftClassifyList_ibatis(map);
	}
	/**
	 * 
	* @Title: getGiftClassifyList 
	* @Description: TODO(获取礼品列表数量) 
	* @return List<GiftClassify>    返回类型 
	* @author
	 */
	public int getGiftClassifyListNum(Map<String,String> map) {
		
		return mGiftClassifyDao.getGiftClassifyListNum(map);
	}
	
}
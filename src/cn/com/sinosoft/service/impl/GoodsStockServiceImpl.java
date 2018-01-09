/**
 * 
 */
package cn.com.sinosoft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.GoodsStockDao;
import cn.com.sinosoft.entity.GoodsStock;
import cn.com.sinosoft.service.GoodsStockService;

/**
 * @author Administrator
 *
 */
@Service
public class GoodsStockServiceImpl extends BaseServiceImpl<GoodsStock, String> implements GoodsStockService {
	
	@Resource
	private GoodsStockDao goodsStockDao;
	
	@Resource
	public void setBaseDao(GoodsStockDao goodsStockDao) {
		super.setBaseDao(goodsStockDao);
	}


	@Override
	public List<GoodsStock> goodslist(String giftid) {
		return goodsStockDao.goodslist(giftid);
	}
	@Override
	public List<GoodsStock> goodslistByType(String giftid,String type) {
		return goodsStockDao.goodslistByType(giftid,type);
	}
}

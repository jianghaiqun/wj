package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.FavoritesDao;
import cn.com.sinosoft.dao.PointsDao;
import cn.com.sinosoft.entity.ProductCollection;
import cn.com.sinosoft.service.FavoritesService;

/**
 * Service实现类 - 发货项
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT5CCDCA53AF8463D621530B1ADA0CE130
 * ============================================================================
 */

@Service
public class FavoritesServiceImpl extends BaseServiceImpl<ProductCollection, String> implements FavoritesService {

	@Resource
	private FavoritesDao favoritesDao;
//	@Resource
//	public void setFavoritesDao(FavoritesDao favoritesDao) {
//		super.setBaseDao(favoritesDao);
//	}
//	
	@Resource
	public void setBaseDao(FavoritesDao favoritesDao) {
		super.setBaseDao(favoritesDao);
	}
	
	@Override
	public boolean contains(ProductCollection productCollection) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("productID","=",productCollection.getProductID()));
		qbs.add(createQB("memberID","=",productCollection.getMemberID()));
		List<ProductCollection> pcs = favoritesDao.findByQBs(qbs, "id", "desc");
		if(pcs!=null&&pcs.size()>0){
			return true;
		}
		
		return false;
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}

}
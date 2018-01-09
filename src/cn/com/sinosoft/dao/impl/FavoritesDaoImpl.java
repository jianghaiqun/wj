package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.FavoritesDao;
import cn.com.sinosoft.entity.ProductCollection;
@Repository
public class FavoritesDaoImpl extends BaseDaoImpl<ProductCollection,String> implements FavoritesDao{

}
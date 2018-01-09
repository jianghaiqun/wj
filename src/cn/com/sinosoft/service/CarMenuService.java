package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.entity.CarMenu;

/**
 * @author LiuXin
 *
 */
public interface CarMenuService extends BaseService<CarMenu,String>{
	public List<CarMenu> findByQBs(List<QueryBuilder> qbs, String orderBy,String orderType);

}

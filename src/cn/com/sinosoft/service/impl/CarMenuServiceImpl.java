package cn.com.sinosoft.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.CarMenuDao;
import cn.com.sinosoft.entity.CarMenu;
import cn.com.sinosoft.service.CarMenuService;

/**
 * @author LiuXin
 *
 */
@Service
public class CarMenuServiceImpl extends BaseServiceImpl<CarMenu,String> implements CarMenuService{
	@Resource
	private CarMenuDao carMenuDao;
	@Resource
	public void setBaseDao(CarMenuDao carMenuDao) {
		super.setBaseDao(carMenuDao);
	}

	@Override
	public List<CarMenu> findByQBs(List<QueryBuilder> qbs, String orderBy,
			String orderType) {
		return carMenuDao.findByQBs(qbs, orderBy, orderType);
	}

}

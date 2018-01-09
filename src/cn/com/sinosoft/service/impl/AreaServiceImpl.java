package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.AreaDao;
import cn.com.sinosoft.entity.Area;
import cn.com.sinosoft.entity.Occupation;
import cn.com.sinosoft.service.AreaService;

/**
 * Service实现类 - 地区
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT2B3A4FEF05A42E033E79040EB368B28E
 * ============================================================================
 */

@Service
public class AreaServiceImpl extends BaseServiceImpl<Area, String> implements AreaService {
	
	@Resource
	private AreaDao areaDao;

	@Resource
	public void setBaseDao(AreaDao areaDao) {
		super.setBaseDao(areaDao);
	}

	@Override
	public List<Area> findSuperByCom(String productId,String comCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("parent","ISNULL",""));
		qbs.add(createQB("insuranceCompany","=",comCode));
		qbs.add(createQB("productId","=",productId));
		List<Area> tAreaList = areaDao.findByQBs(qbs, "id", "asc");
		if(tAreaList==null || tAreaList.size()<=0){
			if("1061".equals(comCode)||"2007".equals(comCode)||"2011".equals(comCode)
					||"1018".equals(comCode)||"1014".equals(comCode)){
				tAreaList = areaDao.findByCom(comCode);
			} 
		}
		return tAreaList;
	}
	
	@Override
	public String getAreaName(String code) {
		Area a = areaDao.get(code);
		if(a!=null){
			return a.getName();
		}
		return "";
	}

	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
	@Cacheable(modelId = "caching")
	public List<Area> getRootAreaList() {
		List<Area> rootAreaList = areaDao.getRootAreaList();
		if (rootAreaList != null) {
			for (Area rootArea : rootAreaList) {
				Hibernate.initialize(rootArea);
			}
		}
		return rootAreaList;
	}
	
	@Cacheable(modelId = "caching")
	public List<Area> getParentAreaList(Area area) {
		List<Area> parentAreaList = areaDao.getParentAreaList(area);
		if (parentAreaList != null) {
			for (Area parentArea : parentAreaList) {
				Hibernate.initialize(parentArea);
			}
		}
		return parentAreaList;
	}
	
	@Cacheable(modelId = "caching")
	public List<Area> getChildrenAreaList(Area area) {
		List<Area> childrenAreaList = areaDao.getChildrenAreaList(area);
		if (childrenAreaList != null) {
			for (Area childrenArea : childrenAreaList) {
				Hibernate.initialize(childrenArea);
			}
		}
		return childrenAreaList;
	}
	
	public Boolean isNameUnique(String parentId, String oldName, String newName) {
		return areaDao.isNameUnique(parentId, oldName, newName);
	}
	
	public Boolean isAreaPath(String areaPath) {
		Area area = areaDao.get("path", areaPath);
		if (area == null) {
			return false;
		} else {
			return true;
		}
	}
	
	@Cacheable(modelId = "caching")
	public String getAreaString(Area area) {
		StringBuilder stringBuilder = new StringBuilder();
		List<Area> parentAreaList = this.getParentAreaList(area);
		if (parentAreaList != null) {
			for (Area parentArea : parentAreaList) {
				stringBuilder.append(parentArea.getName());
			}
		}
		stringBuilder.append(area.getName());
		return stringBuilder.toString();
	}
	
	@Cacheable(modelId = "caching")
	public String getAreaString(String areaPath) {
		if (!isAreaPath(areaPath)) {
			return null;
		}
		StringBuffer stringBuffer = new StringBuffer();
		String[] ids = areaPath.split(Area.PATH_SEPARATOR);
		for (String id : ids) {
			Area area = super.load(id);
			stringBuffer.append(area.getName());
		}
		return stringBuffer.toString();
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(Area entity) {
		areaDao.delete(entity);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String id) {
		areaDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		areaDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public String save(Area entity) {
		return areaDao.save(entity);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void update(Area entity) {
		areaDao.update(entity);
	}


}
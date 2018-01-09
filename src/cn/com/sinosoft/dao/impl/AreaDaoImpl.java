package cn.com.sinosoft.dao.impl;

import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.AreaDao;
import cn.com.sinosoft.entity.Area;
import cn.com.sinosoft.entity.Occupation;

/**
 * Dao实现类 - 地区
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT1C7D46AEE8221AFDAF3E739DCCA1D09E
 * ============================================================================
 */

@Repository
public class AreaDaoImpl extends BaseDaoImpl<Area, String> implements AreaDao {
	
	@SuppressWarnings("unchecked")
	public List<Area> getRootAreaList() {
		String hql = "from Area area where area.parent is null and area.insuranceCompany is null";
		return getSession().createQuery(hql).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Area> getParentAreaList(Area area) {
		String hql = "from Area area where area != ? and area.id in(:ids) and area.insuranceCompany is null";
		String[] ids = area.getPath().split(Area.PATH_SEPARATOR);
		return getSession().createQuery(hql).setParameter(0, area).setParameterList("ids", ids).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Area> getChildrenAreaList(Area area) {
		String hql = "from Area area where area != ? and area.path like ? and area.insuranceCompany is null";
		return getSession().createQuery(hql).setParameter(0, area).setParameter(1, area.getPath() + "%").list();
	}

	public Boolean isNameUnique(String parentId, String oldName, String newName) {
		if (StringUtils.equalsIgnoreCase(newName, oldName)) {
			return true;
		}
		if (StringUtils.isEmpty(parentId)) {
			String hql = "from Area area where area.name = ? and area.parent is null and area.insuranceCompany is null";
			return getSession().createQuery(hql).setParameter(0, newName).uniqueResult() == null;
		} else {
			String hql = "from Area area where area.name = ? and area.parent.id = ? and area.insuranceCompany is null";
			return getSession().createQuery(hql).setParameter(0, newName).setParameter(1, parentId).uniqueResult() == null;
		}
	}
	public List<Area> findByCom(String comCode){
		String hql = "select * from Area o where o.insuranceCompany='"+comCode+"' and (o.parent_id is null or o.parent_id='') and (o.productid is null or o.productid='') ";
		return getSession().createSQLQuery(hql).addEntity(Area.class).list();
		
	}
	// 设置path值
	@Override
	public String save(Area area) {
		String id = super.save(area);
		Area parent = area.getParent();
		if (parent != null) {
			String parentPath = parent.getPath();
			area.setPath(parentPath + Area.PATH_SEPARATOR + id);
		} else {
			area.setPath(id);
		}
		super.update(area);
		return id;
	}
	
	// 设置path值
	@Override
	public void update(Area area) {
		Area parent = area.getParent();
		if (parent != null) {
			String parentPath = parent.getPath();
			area.setPath(parentPath + Area.PATH_SEPARATOR + area.getId());
		} else {
			area.setPath(area.getId());
		}
		super.update(area);
	}

}
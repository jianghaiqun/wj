package cn.com.sinosoft.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.opensymphony.oscache.util.StringUtil;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;

import cn.com.sinosoft.dao.OccupationDao;
import cn.com.sinosoft.entity.Occupation;

@Repository
public class OccupationDaoImpl extends BaseDaoImpl<Occupation, String>
		implements OccupationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Occupation> getRootOPTList() {
		String hql = "from Occupation o where o.parent is null";
		return getSession().createQuery(hql).list();
	}

	@Override
	public Boolean isNameUnique(String parentId, String oldName, String newName) {
		if (StringUtils.equalsIgnoreCase(newName, oldName)) {
			return true;
		}
		if (StringUtils.isEmpty(parentId)) {
			String hql = "from Occupation o where o.name = ? and o.parent is null";
			return getSession().createQuery(hql).setParameter(0, newName)
					.uniqueResult() == null;
		} else {
			String hql = "from Occupation o where o.name = ? and o.parent.id = ?";
			return getSession().createQuery(hql).setParameter(0, newName)
					.setParameter(1, parentId).uniqueResult() == null;
		}
	}

	@Override
	public Boolean isCodeUnique(String parentId, String oldCode, String newCode) {
		if (StringUtils.equalsIgnoreCase(newCode, oldCode)) {
			return true;
		}
		if (StringUtils.isEmpty(parentId)) {
			String hql = "from Occupation o where o.code = ? and o.parent is null";
			return getSession().createQuery(hql).setParameter(0, newCode)
					.uniqueResult() == null;
		} else {
			String hql = "from Occupation o where o.code = ? and o.parent.id = ?";
			return getSession().createQuery(hql).setParameter(0, newCode)
					.setParameter(1, parentId).uniqueResult() == null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Occupation> getParentOPTList(Occupation op) {
		String hql = "from Occupation o where o != ? and o.id in(:ids)";
		String[] ids = op.getPath().split(Occupation.PATH_SEPARATOR);
		return getSession().createQuery(hql).setParameter(0, op)
				.setParameterList("ids", ids).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Occupation> getChildrenOPTList(Occupation op) {
		String hql = "from Occupation o where o != ? and o.path like ?";
		return getSession().createQuery(hql).setParameter(0, op)
				.setParameter(1, op.getPath() + "%").list();
	}

	// 设置path值
	@Override
	public String save(Occupation op) {
		String id = super.save(op);
		Occupation parent = op.getParent();
		if (parent != null) {
			String parentPath = parent.getPath();
			op.setPath(parentPath + Occupation.PATH_SEPARATOR + id);
		} else {
			op.setPath(id);
		}
		super.update(op);
		return id;
	}

	// 设置path值
	@Override
	public void update(Occupation op) {
		Occupation parent = op.getParent();
		if (parent != null) {
			String parentPath = parent.getPath();
			op.setPath(parentPath + Occupation.PATH_SEPARATOR + op.getId());
		} else {
			op.setPath(op.getId());
		}
		super.update(op);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Occupation> findSuperByCom(String comCode, String productId) {
		List<Occupation> result = new ArrayList<Occupation>();
		String hql = "";
		if (StringUtil.isEmpty(productId)) {
			hql = "select * from Occupation o where o.insuranceCompany= ? and (productId is null or productId = '') and (o.parent_id is null or o.parent_id = '')";
			result = getSession().createSQLQuery(hql).addEntity(Occupation.class).setParameter(0, comCode).list();
		} else {
			hql = "select * from Occupation o where o.insuranceCompany= ? and (productId = ?) and (o.parent_id is null or o.parent_id = '')";
			result = getSession().createSQLQuery(hql).addEntity(Occupation.class).setParameter(0, comCode).setParameter(1, productId).list();
		}
		
		if (result.size() == 0) {
			hql = "select * from Occupation o where o.insuranceCompany= ? and (productId is null or productId = '') and (o.parent_id is null or o.parent_id = '')";
			result = getSession().createSQLQuery(hql).addEntity(Occupation.class).setParameter(0, comCode).list();
		}
		return result;
	}


	@Override
	public int findOccupNoBySql(String sql) {
		int occup = 0;
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		if(dt.getRowCount()>0){
			occup = dt.getInt(0, 0);
		}
		return occup;
	}

	@Override
	public String getOccupLimitBy(String productId) {
		String occupLimit = "";
		DataTable dt = new QueryBuilder("select occup from sdproduct where productId = ?",productId).executeDataTable();
		if(dt.getRowCount()>0){
			occupLimit = dt.getString(0, 0);
		}
		return occupLimit;
	}

	@Override
	public DataTable findThreeLevelOccupation(String comCode, String productId, String occupLevel) {
		DataTable dt = null;
		QueryBuilder qb = new QueryBuilder();
		String[] occupLevelArray = occupLevel.split("-");
		String minGrade = null;
		String maxGrade = null;
		if(occupLevelArray.length == 1){
			minGrade = occupLevelArray[0];
			maxGrade = occupLevelArray[0]; 
		}else if(occupLevelArray.length == 2){
			minGrade = occupLevelArray[0];
			maxGrade = occupLevelArray[1]; 
		}
		String sqlProductId = "SELECT a.`name` AS 'level1',b.`name` AS 'level2',c.`name` AS 'level3',c.grade"
				+ " FROM Occupation a LEFT OUTER JOIN Occupation b ON a.id = b.parent_id"
				+ " LEFT OUTER JOIN Occupation c ON b.id = c.parent_id"
				+ " WHERE c.productId = ?"
				+ " and (a.parent_id is null or a.parent_id = '')"
				+ " and c.grade >= ? and c.grade <= ?"
				+ " order by a.name,b.`name`";
		String sqlComCode = "SELECT a.`name` AS 'level1',b.`name` AS 'level2',c.`name` AS 'level3',c.grade"
				+ " FROM Occupation a LEFT OUTER JOIN Occupation b ON a.id = b.parent_id"
				+ " LEFT OUTER JOIN Occupation c ON b.id = c.parent_id"
				+ " WHERE a.insuranceCompany = ? AND (a.productId IS NULL OR a.productId = '')"
				+ " and (a.parent_id is null or a.parent_id = '')"
				+ " and c.grade >= ? and c.grade <= ?"
				+ " order by a.name,b.`name`";
		if (StringUtil.isEmpty((productId))) {
			qb.setSQL(sqlComCode);
			qb.add(comCode);
			qb.add(minGrade);
			qb.add(maxGrade);
			dt = qb.executeDataTable();
		}else{
			qb.setSQL(sqlProductId);
			qb.add(productId);
			qb.add(minGrade);
			qb.add(maxGrade);
			dt = qb.executeDataTable();
			if(dt.getRowCount() == 0){
				qb = new QueryBuilder(sqlComCode);
				qb.add(comCode);
				qb.add(minGrade);
				qb.add(maxGrade);
				dt = qb.executeDataTable();
			}
		}
		return dt;
	}
	
}

package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;

import cn.com.sinosoft.dao.OccupationDao;
import cn.com.sinosoft.entity.Occupation;
import cn.com.sinosoft.service.OccupationService;

@Service
public class OccupationServiceImpl extends BaseServiceImpl<Occupation,String> implements OccupationService{
	@Resource
	private OccupationDao occupationDao;

	@Resource
	public void setOccupationDao(OccupationDao occupationDao) {
		super.setBaseDao(occupationDao);
	}

	@Override
	public List<Occupation> findSuperByCom(String comCode, String productId) {
		//List<Occupation> list = new ArrayList<Occupation>();
		List<Occupation> listOne = occupationDao.findSuperByCom(comCode, productId);
//		String occupLimit = getOccupLimitBy(productId);
//		if(StringUtil.isNotEmpty(occupLimit)){
//			String[] occupArray = occupLimit.split("-");
//			listOne = occupationDao.findSuperByCom(comCode,occupArray);
//			/*if(listOne!=null && listOne.size()>0){
//				for(Occupation o : listOne){
//					if(occupArray.length==2){
//						String sql = "select count(1) from Occupation b where b.parent_id in (select id from Occupation a where a.parent_id='"+o.getId()+"') " +
//								"and b.grade>="+occupArray[0]+" and b.grade<="+occupArray[1];
//						int occupNo = occupationDao.findOccupNoBySql(sql);
//						if(occupNo>=1){
//							list.add(o);
//						}
//					}
//				}
//			}*/
//			//return list;
//		}else{
//			return listOne;
//		}
		return listOne;
	}
	
	@Override
	public String getOccupLimitBy(String productId) {
		return occupationDao.getOccupLimitBy(productId);
	}

	@Override
	public String getOccupationName(String code) {
		Occupation o = occupationDao.get(code);
		if(o!=null){
			return o.getName();
		}
		return "";
	}

	@Override
	@Cacheable(modelId = "caching")
	public List<Occupation> getRootOPTList() {
		List<Occupation> rootOccupationList = occupationDao.getRootOPTList();
		if (rootOccupationList != null) {
			for (Occupation rootOccupat : rootOccupationList) {
				Hibernate.initialize(rootOccupat);
			}
		}
		return rootOccupationList;
	}

	@Override
	public Boolean isNameUnique(String parentId, String oldName, String newName) {
		return occupationDao.isNameUnique(parentId, oldName, newName);
	}

	@Override
	public Boolean isCodeUnique(String parentId, String oldCode, String newCode) {
		return occupationDao.isCodeUnique(parentId, oldCode, newCode);
	}

	@Override
	@Cacheable(modelId = "caching")
	public List<Occupation> getParentOPTList(Occupation opt) {
		List<Occupation> parentOPTList = occupationDao.getParentOPTList(opt);
		if (parentOPTList != null) {
			for (Occupation parentOPT : parentOPTList) {
				Hibernate.initialize(parentOPT);
			}
		}
		return parentOPTList;
	}

	@Override
	@Cacheable(modelId = "caching")
	public List<Occupation> getChildrenOPTList(Occupation opt) {
		List<Occupation> childrenOPTList = occupationDao.getChildrenOPTList(opt);
		if (childrenOPTList != null) {
			for (Occupation childrenOPT : childrenOPTList) {
				Hibernate.initialize(childrenOPT);
			}
		}
		return childrenOPTList;
	}

	@Override
	public Boolean isOPTPath(String Path) {
		Occupation op = occupationDao.get("path", Path);
		if (op == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String getOPTString(Occupation opt) {
		StringBuilder stringBuilder = new StringBuilder();
		List<Occupation> parentOPTList = this.getParentOPTList(opt);
		if (parentOPTList != null) {
			for (Occupation parentOPT : parentOPTList) {
				stringBuilder.append(parentOPT.getName());
			}
		}
		stringBuilder.append(opt.getName());
		return stringBuilder.toString();
	}

	@Override
	@Cacheable(modelId = "caching")
	public String getOPTString(String path) {
		if (!isOPTPath(path)) {
			return null;
		}
		StringBuffer stringBuffer = new StringBuffer();
		String[] ids = path.split(Occupation.PATH_SEPARATOR);
		for (String id : ids) {
			Occupation op = super.load(id);
			stringBuffer.append(op.getName());
		}
		return stringBuffer.toString();
	}
	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(Occupation entity) {
		occupationDao.delete(entity);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String id) {
		occupationDao.delete(id);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void delete(String[] ids) {
		occupationDao.delete(ids);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public String save(Occupation entity) {
		return occupationDao.save(entity);
	}

	@Override
	@CacheFlush(modelId = "flushing")
	public void update(Occupation entity) {
		occupationDao.update(entity);
	}

	@Override
	public List<Map<String,String>> findThreeLevelOccupation(String comCode, String productId, String occupLevel) {
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		DataTable dt =  occupationDao.findThreeLevelOccupation(comCode, productId, occupLevel);
		Map<String,Integer> levelCount = new HashMap<String,Integer>();
		for(int i=0;i<dt.getRowCount();i++){
			DataRow dr = dt.get(i);
			Map<String,String> row = new HashMap<String,String>();
			row.put("level1", dr.getString("level1"));
			row.put("level2", dr.getString("level2"));
			row.put("level3", dr.getString("level3"));
			row.put("grade", dr.getString("grade"));
			String l1key = "l1_"+dr.get("level1");
			String l2key = "l2_"+l1key+"_"+dr.get("level2");
			if(levelCount.get(l1key) == null){
				levelCount.put(l1key, 1);
			}else{
				levelCount.put(l1key,levelCount.get(l1key)+1);
			}
			if(levelCount.get(l2key) == null){
				levelCount.put(l2key, 1);
			}else{
				levelCount.put(l2key,levelCount.get(l2key)+1);
			}
			result.add(row);
		}
		for(Map<String,String> m : result){
			m.put("l1Count", String.valueOf(levelCount.get("l1_"+m.get("level1"))));
			m.put("l2Count", String.valueOf(levelCount.get("l2_l1_"+m.get("level1")+"_"+m.get("level2"))));
		}
		return result;
	}
}

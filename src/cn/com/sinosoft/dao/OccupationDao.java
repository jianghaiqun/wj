package cn.com.sinosoft.dao;

import java.util.List;

import com.sinosoft.framework.data.DataTable;

import cn.com.sinosoft.entity.Occupation;

public interface OccupationDao extends BaseDao<Occupation, String> {
	/**
	 * 获取所有顶级地区集合;
	 * 
	 * @return 所有顶级地区集合
	 * 
	 */
	public List<Occupation> getRootOPTList();

	/**
	 * 根据地区名称及父类ID查询在其子类中是否唯一(parentId为null则查询根地区是否唯一)
	 * 
	 * @param id
	 *            父类ID
	 * 
	 */
	public Boolean isNameUnique(String parentId, String oldName, String newName);

	/**
	 * 根据地区名称及父类ID查询在其子类中是否唯一(parentId为null则查询根地区是否唯一)
	 * 
	 * @param id
	 *            父类ID
	 * 
	 */
	public Boolean isCodeUnique(String parentId, String oldCode, String newCode);

	/**
	 * 根据Area对象获取所有上级地区集合，若无上级地区则返回null;
	 * 
	 * @return 上级地区集合
	 * 
	 */
	public List<Occupation> getParentOPTList(Occupation op);

	/**
	 * 根据Area对象获取所有子类集合，若无子类则返回null;
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<Occupation> getChildrenOPTList(Occupation op);

	/**
	 * 查询所属保险公司一级职业
	 */
	public List<Occupation> findSuperByCom(String comCode, String productId);

	/**
	 * 
	 * 根据sql查询下级职业个数
	 */
	public int findOccupNoBySql(String hql);

	/**
	 * 根据产品编码查询产品的职业限制
	 */
	public String getOccupLimitBy(String productId);
	
	/**
	 * 查询三级职业列表
	 * @param comCode 公司编码	
	 * @param productId 产品编码
	 * @param occupLevel 职业等级
	 * @return
	 */
	public DataTable findThreeLevelOccupation(String comCode, String productId, String occupLevel);

}

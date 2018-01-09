package cn.com.sinosoft.service;

import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.Occupation;

public interface OccupationService extends BaseService<Occupation, String> {
	/**
	 * 获取所有职业地区集合;
	 * 
	 * @return 所有顶级职业集合
	 * 
	 */
	public List<Occupation> getRootOPTList();

	/**
	 * 根据Occupation对象获取所有上级地区集合，若无上级职业则返回null;
	 * 
	 * @return 上级职业集合
	 * 
	 */
	public List<Occupation> getParentOPTList(Occupation opt);

	/**
	 * 根据Occupation对象获取所有子类集合，若无子类则返回null;
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<Occupation> getChildrenOPTList(Occupation opt);

	/**
	 * 根据职业名称及父类ID查询在其子类中是否唯一(parentId为null则查询根地区是否唯一)
	 * 
	 * @param id
	 *            父类ID
	 * 
	 */
	public Boolean isNameUnique(String parentId, String oldName, String newName);

	/**
	 * 根据职业名称及父类ID查询在其子类中是否唯一(parentId为null则查询根地区是否唯一)
	 * 
	 * @param id
	 *            父类ID
	 * 
	 */
	public Boolean isCodeUnique(String parentId, String oldCode, String newCode);

	/**
	 * 判断职业Path字符串是否正确;
	 */
	public Boolean isOPTPath(String Path);

	/**
	 * 根据职业获取完整地址字符串;
	 * 
	 */
	public String getOPTString(Occupation opt);

	/**
	 * 根据职业路径获取完整地区字符串，若职业路径错误则返回null
	 * 
	 * @param areaPath
	 *            职业路径
	 */
	public String getOPTString(String optPath);
    /**
     * 
     * 查询所属保险公司的一级职业
     */
	public List<Occupation> findSuperByCom(String comCode,String productId);
	/**
     * 
     * 根据编码查询职业名称
     */
	public String getOccupationName(String code);

	/**
	 * 根据产品编码查询职业限制级别
	 * @param productId
	 * @return
	 */
	public String getOccupLimitBy(String productId);
	
	/**
	 * 查询三级职业列表
	 * @param comCode 公司编码	
	 * @param productId 产品编码
	 * @param occupLevel 职业等级
	 * @return
	 */
	public List<Map<String,String>> findThreeLevelOccupation(String comCode, String productId, String occupLevel);
}

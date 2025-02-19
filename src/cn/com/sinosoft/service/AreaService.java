package cn.com.sinosoft.service;

import java.util.List;

import cn.com.sinosoft.entity.Area;


/**
 * Service接口 - 地区
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT0093A02DC48CD73BDC71C06BA52A9AD2
 * ============================================================================
 */

public interface AreaService extends BaseService<Area, String> {
	
	/**
	 * 获取所有顶级地区集合;
	 * 
	 * @return 所有顶级地区集合
	 * 
	 */
	public List<Area> getRootAreaList();
	
	/**
	 * 根据Area对象获取所有上级地区集合，若无上级地区则返回null;
	 * 
	 * @return 上级地区集合
	 * 
	 */
	public List<Area> getParentAreaList(Area area);
	
	/**
	 * 根据Area对象获取所有子类集合，若无子类则返回null;
	 * 
	 * @return 子类集合
	 * 
	 */
	public List<Area> getChildrenAreaList(Area area);

	/**
	 * 根据地区名称及父类ID查询在其子类中是否唯一(parentId为null则查询根地区是否唯一)
	 * 
	 * @param id
	 *            父类ID
	 * 
	 */
	public Boolean isNameUnique(String parentId, String oldName, String newName);
	
	/**
	 * 判断地区Path字符串是否正确;
	 */
	public Boolean isAreaPath(String areaPath);
	
	/**
	 * 根据地区获取完整地址字符串;
	 * 
	 */
	public String getAreaString(Area area);
	
	/**
	 * 根据地区路径获取完整地区字符串，若地区路径错误则返回null
	 * 
	 * @param areaPath
	 *         地区路径
	 */
	public String getAreaString(String areaPath);

	/**
	 * 
	 * 查询保险公司及产品编码所属的一级地区
	 */
	public List<Area> findSuperByCom(String comCode, String insuranceCompanySn);

	/**
	 * 
	 * 根据地区编码的查名称
	 */
	public String getAreaName(String code);


}
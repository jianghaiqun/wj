package cn.com.sinosoft.service;

import java.util.List;
import java.util.Map;

import cn.com.sinosoft.entity.Dictionary;

public interface DictionaryService extends BaseService<Dictionary,String>{

	/**
	 * 
	 * 根据保险公司编码和类型查询所属类型数据集
	 */
	public List<Dictionary> findListByCom(String string, String comCode);

	/**
	 * 
	 * 根据地区编码查询地区名称
	 */
	public String findCountryNameByCode(String insuranceCompanySn, String trim);
	

	/**
	 * 
	 * 根据地区编码查询地区英文名称
	 */
	public String getCodeEnNameByCodeValue(String insuranceCode, String codeType, String codeValue);
	/**
	 * 
	 * 根据保险公司编码、值、类型查询名称
	 */
	public String getNameByCodeType(String insuranceCode ,String codetype, String codeValue);
	/**
	 * 
	 * 根据保险公司编码、值、类型查询名称
	 */
	public Dictionary getNameByCodeValue(String insuranceCode ,String codetype, String codeValue);

	/**
	 * 根据类型、保险公司编码、产品id查询列表
	 */
	public List<Dictionary> findListByCom(String type, String comCode, String productId);
	
	/**
	 * 类型、名称、保险公司查询编码
	 * @return
	 */
	public String getCodeValueByCodeName(String  insuranceCode, String codeType, String codeValue);
	
	/**
	 * 产品、编码、查询名称
	 */
	public String findCountryNameByProduct(String productId, String  trim);
	/**
	 * 
	 * 根据保险公司编码、产品，值、类型查询名称
	 */
	public String getNameByCodeTypePro(String productId,String insuranceCode, String codetype,String codeValue);

	/**
	 * 
	 * 先根据产品查询对应的名称及编码，若未查询到信息再根据保险公司查询对应的名称及编码，key：名称,value:编码
	 */
	public Map<String, String> getCodeNamesInfo(String productId,String insuranceCode, String codetype);
}

package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.DictionaryDao;
import cn.com.sinosoft.entity.Dictionary;
import cn.com.sinosoft.service.DictionaryService;

@Service
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary,String> implements DictionaryService{
	@Resource
	private DictionaryDao dictionaryDao;

	@Resource
	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		super.setBaseDao(dictionaryDao);
	}

	@Override
	public List<Dictionary> findListByCom(String type, String comCode,
			String productId) {
		List<Dictionary> list = new ArrayList<Dictionary>();
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("codeType","=",type));
		qbs.add(createQB("productId","=",productId));
		list = dictionaryDao.findByQBs(qbs, "id", "asc");
		if(list ==null || list.size()<=0){
			list = this.findListByCom(type, comCode);
		}
		return list;
	}

	@Override
	public List<Dictionary> findListByCom(String codeType, String comCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("codeType","=",codeType));
		qbs.add(createQB("insuranceCode","=",comCode));
		qbs.add(createQB("productId","isNULLorEMPTY",null));
		return dictionaryDao.findByQBs(qbs, "id", "asc");
	}
	
	@Override
	public String findCountryNameByCode(String insuranceCompanySn, String trim) {
		List<Dictionary> list = new ArrayList<Dictionary>();
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		//zhangjinquan 11180 字段名的大小写必须和实体类中的属性名大小写完全一致 2012-11-29
		qbs.add(createQB("codeType","=","CountryCode"));
		qbs.add(createQB("codeValue","=",trim));
		list = dictionaryDao.findByQBs(qbs, "id", "asc");
		if(list!=null&&list.size()>0){
			return list.get(0).getCodeName();
		}
		return null;
	}

	@Override
	public String findCountryNameByProduct(String productId, String  trim) {
		List<Dictionary> list = new ArrayList<Dictionary>();
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		//zhangjinquan 11180 字段名的大小写必须和实体类中的属性名大小写完全一致 2012-11-29
		qbs.add(createQB("codeType","=","CountryCode"));
		qbs.add(createQB("codeValue","=",trim));
		qbs.add(createQB("productId","=",productId));
		list = dictionaryDao.findByQBs(qbs, "id", "asc");
		if(list!=null&&list.size()>0){
			return list.get(0).getCodeName();
		}
		return null;
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}

	@Override
	public String getNameByCodeType(String insuranceCode, String codetype,
			String codeValue) {
		List<Dictionary> list = new ArrayList<Dictionary>();
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("codeType","=",codetype));
		qbs.add(createQB("codeValue","=",codeValue));
		qbs.add(createQB("insuranceCode","=",insuranceCode));
		list = dictionaryDao.findByQBs(qbs, "id", "asc");
		if(list!=null && list.size()>0){
			return list.get(0).getCodeName();
		}
		return null;
	}
	@Override
	public String getNameByCodeTypePro(String productId,String insuranceCode, String codetype,
			String codeValue) {
		
		
		List<Dictionary> list1 = new ArrayList<Dictionary>();
		List<QueryBuilder> qbs1 = new ArrayList<QueryBuilder>();
		qbs1.add(createQB("codeType","=",codetype));
		qbs1.add(createQB("codeValue","=",codeValue));
		qbs1.add(createQB("productId","=",productId));
		list1 = dictionaryDao.findByQBs(qbs1, "id", "asc");
		if(list1!=null && list1.size()>0){
			return list1.get(0).getCodeName();
		}
		List<Dictionary> list = new ArrayList<Dictionary>();
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("codeType","=",codetype));
		qbs.add(createQB("codeValue","=",codeValue));
		qbs.add(createQB("insuranceCode","=",insuranceCode));
		qbs.add(createQB("productId","isNULLorEMPTY",null));
		list = dictionaryDao.findByQBs(qbs, "id", "asc");
		if(list!=null && list.size()>0){
			return list.get(0).getCodeName();
		}
		return null;
	}

	@Override
	public String getCodeEnNameByCodeValue(String insuranceCode, String codeType, String codeValue)
	{
		List<Dictionary> list = new ArrayList<Dictionary>();
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("codeType","=",codeType));
		qbs.add(createQB("codeValue","=",codeValue));
		qbs.add(createQB("insuranceCode","=",insuranceCode));
		list = dictionaryDao.findByQBs(qbs, "id", "asc");
		if((list!=null) && (list.size()>0))
		{
			//需要特殊处理&符号
			return list.get(0).getCodeEnName().replaceAll("&amp;", "&");
		}
		return null;
	}

	@Override
	public String getCodeValueByCodeName(String insuranceCode, String codeType, String codeName)
	{
		List<Dictionary> list = new ArrayList<Dictionary>();
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("codeType","=",codeType));
		qbs.add(createQB("codeName","=",codeName));
		qbs.add(createQB("insuranceCode","=",insuranceCode));
		list = dictionaryDao.findByQBs(qbs, "id", "asc");
		if((list!=null) && (list.size()>0))
		{
			//需要特殊处理&符号
			return list.get(0).getCodeValue();
		}
		return null;
	}
	@Override
	public Dictionary getNameByCodeValue(String insuranceCode, String codetype,
			String codeValue) {
		List<Dictionary> list = new ArrayList<Dictionary>();
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("codeType","=",codetype));
		qbs.add(createQB("codeValue","=",codeValue));
		qbs.add(createQB("insuranceCode","=",insuranceCode));
		list = dictionaryDao.findByQBs(qbs, "id", "asc");
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public Map<String, String> getCodeNamesInfo(String productId,String insuranceCode, String codetype) {
		Map<String, String> codeNamesInfo = new HashMap<String, String>();
		List<Dictionary> list = new ArrayList<Dictionary>();
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("codeType","=",codetype));
		qbs.add(createQB("productId","=",productId));
		list = dictionaryDao.findByQBs(qbs, "id", "asc");
		if(list == null || list.size() == 0){
			list = new ArrayList<Dictionary>();
			qbs = new ArrayList<QueryBuilder>();
			qbs.add(createQB("codeType","=",codetype));
			qbs.add(createQB("insuranceCode","=",insuranceCode));
			qbs.add(createQB("productId","isNULLorEMPTY",null));
			list = dictionaryDao.findByQBs(qbs, "id", "asc");
		}
		if(list!=null && list.size()>0){
			for (Dictionary dictionary : list) {
				codeNamesInfo.put(dictionary.getCodeName(), dictionary.getCodeValue());
			}
		}
		return codeNamesInfo;
	}
}

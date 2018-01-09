package com.sinosoft.product;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.FEMSearchConditionInfoSchema;
import com.sinosoft.schema.FEMSearchConditionInfoSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 默认全部编码定义为：default_编码_level
 * 
 * @author guobin
 */
public class Combination extends Ajax {
	/**
	 * @param args
	 */

	/**
	 * 获取指定分类筛选条件，所有随机组合
	 * 
	 * @param UpperId
	 * @return
	 */
	public static List<List<String>> getAllCombination(String UpperId) {
		if (StringUtil.isEmpty(UpperId)) {
			logger.warn("筛选条件编码为空!", UpperId);
			return null;
		}
		List<List<String>> combinationList = getAllSearch(UpperId);
		while (combinationList.size() > 1) {
			combinationList = compose(combinationList);
		}
		return combinationList;
	}

	/**
	 * 获取指定分类筛选条件
	 * 
	 * @param UpperId
	 * @return
	 */
	public static List<List<String>> getAllSearch(String UpperId) {
		FEMSearchConditionInfoSet tSet = getRoot(UpperId);
		List<List<String>> temList = new ArrayList<List<String>>();
		for (int i = 0; tSet != null && i < tSet.size(); i++) {
			FEMSearchConditionInfoSchema tConSchema = tSet.get(i);
			if (validateCode(tConSchema.getId())) {
				return null;
			}

			if (existChild(tConSchema)) {
				List<String> temp = new ArrayList<String>();
				//定义一级的默认值为-1
//				temp.add( tConSchema.getId() + "|N");					
//				temp.addAll(getChild(tConSchema.getId(), Integer.parseInt(tConSchema.getSearchLevel()) + 1));
				temp.add("default_" + tConSchema.getSearchCode() + "_" + tConSchema.getSearchLevel() + "|N");
				temp.addAll(getChild(tConSchema.getId(), Integer.parseInt(tConSchema.getSearchLevel()) + 1));
				temList.add(temp);
			}
		}
		// 根据筛选条件判断多选组合
		List<List<String>> allCombination = new ArrayList<List<String>>();
		for (int i = 0; i < temList.size(); i++) {
			allCombination.add(MultipleChoice(temList.get(i)));
		}

		// 去除筛选条件
		List<List<String>> result = new ArrayList<List<String>>();
		List<String> temp = null;
		for (List<String> list : allCombination) {
			temp = new ArrayList<String>();
			for (String str : list) {
				temp.add(str.replace("|N", "").replace("|Y", ""));
			}
			result.add(temp);
		}
		return result;
	}

	/**
	 * 校验code，不能存在|,字符
	 * 
	 * @param tConSchema
	 * @return
	 */
	private static boolean validateCode(String code) {
		if (StringUtil.isEmpty(code) || code.indexOf("|") != -1 || code.indexOf(",") != -1) {
			logger.error("筛选条件编码存在非法字符{}", code);
			return true;
		}
		return false;
	}

	/**
	 * 判断是否存在子节点
	 * 
	 * @param tConSchema
	 * @return
	 */
	private static boolean existChild(FEMSearchConditionInfoSchema tConSchema) {
		if (StringUtil.isNotEmpty(tConSchema.getSubNodeNum()) && !"0".equals(tConSchema.getSubNodeNum())) {
			return true;
		}
		return false;
	}

	/**
	 * 解决多选问题
	 * 
	 * @param newList
	 * @return
	 */
	private static List<String> MultipleChoice(List<String> newList) {
		if (newList == null || newList.size() == 0) {
			return null;
		}
		List<String> radioList = new ArrayList<String>();
		List<String> checkboxList = new ArrayList<String>();
		for (String str : newList) {
			if (str.toUpperCase().endsWith("|Y")) {
				checkboxList.add(str);
			} else {
				radioList.add(str);
			}
		}

		if (checkboxList != null && checkboxList.size() >= 2) {
			radioList.addAll(getAllCombinations(checkboxList));
		}

		return radioList;
	}

	/**
	 * 获取父节点数据
	 * 
	 * @param UpperId
	 * @return
	 */
	private static FEMSearchConditionInfoSet getRoot(String UpperId) {
		FEMSearchConditionInfoSchema tConditionSchema = new FEMSearchConditionInfoSchema();
		return tConditionSchema.query(new QueryBuilder("where upperid=? and SearchLevel='1' order by -SearchOrder", UpperId));
	}

	/**
	 * 根据节点数据
	 * 
	 * @param type
	 * @param level
	 * @return
	 */
	public static List<String> getChild(String UpperId, int level) {
		List<String> result = new ArrayList<String>();
		FEMSearchConditionInfoSchema tConditionSchema = new FEMSearchConditionInfoSchema();
		FEMSearchConditionInfoSet tCon = tConditionSchema.query(new QueryBuilder("where upperid=? and SearchLevel=? order by -SearchOrder", UpperId, level));
		if (tCon == null || tCon.size() == 0) {
			return null;
		}

		for (int i = 0; i < tCon.size(); i++) {
			FEMSearchConditionInfoSchema tConSchema = tCon.get(i);
			if (validateCode(tConSchema.getId())) {
				return null;
			}
			if (existChild(tConSchema)) {
				List<String> temp = getChild(tConSchema.getId(), Integer.parseInt(tConSchema.getSearchLevel()) + 1);
				if (temp != null && temp.size() > 0) {
//					temp.add("default_" + tConSchema.getId() + "_" + tConSchema.getSearchLevel() + "|N");
					temp.add(tConSchema.getId() +  "|N");
					result.addAll(temp);
				}
			} else {
				result.add(tConSchema.getId() + "|" + tConSchema.getIsMultipleChoice());
			}
		}
		return result;
	}

	/**
	 * 获取所有随机组合数据
	 * 
	 * @param data
	 * @return
	 */
	public static List<String> getAllCombinations(List<String> data) {
		List<String> allCombinations = new ArrayList<String>();
		for (int i = 1; i <= data.size(); i++) {
			allCombinations.addAll(vectorToString(getAllCombinations(data, i)));
		}
		return allCombinations;
	}

	/**
	 * 字符串拼装
	 * 
	 * @param temp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<String> vectorToString(Vector temp) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < temp.size(); i++) {
			Vector vec = (Vector) temp.get(i);
			String s = "";
			for (int j = 0; j < vec.size(); j++) {
				s += vec.get(j) + ((j == vec.size() - 1) ? "" : ",");
			}
			result.add(s);
		}
		return result;
	}

	/**
	 * 获取固定长度组合
	 * 
	 * @param data
	 * @param length
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Vector getAllCombinations(List<String> data, int length) {
		Vector allCombinations = new Vector();
		Vector initialCombination = new Vector();
		combination(allCombinations, data, initialCombination, length);
		return allCombinations;
	}

	@SuppressWarnings("unchecked")
	private static void combination(Vector allCombinations, List<String> data, Vector initialCombination, int length) {
		if (length == 1) {
			for (int i = 0; i < data.size(); i++) {
				Vector newCombination = new Vector(initialCombination);
				newCombination.add(data.get(i));
				allCombinations.add(newCombination);
			}
		}
		if (length > 1) {
			for (int i = 0; i < data.size(); i++) {
				Vector newCombination = new Vector(initialCombination);
				newCombination.add(data.get(i));

				Vector newData = new Vector(data);
				for (int j = 0; j <= i; j++)
					newData.remove(data.get(j));

				combination(allCombinations, newData, newCombination, length - 1);
			}
		}
	}

	/**
	 * 获取所有组合
	 * 
	 * @param list
	 * @return
	 */
	private static List<List<String>> compose(List<List<String>> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		List<List<String>> result = new ArrayList<List<String>>();
		for (int i = 0; i < list.size(); i = i + 2) {
			if (i != list.size() - 1) {
				List<String> s = compose(list.get(i), list.get(i + 1));
				if (s != null)
					result.add(s);
			} else {
				result.add(list.get(i));
			}
		}
		return result;
	}

	/**
	 * 2个2个结合
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	private static List<String> compose(List<String> s1, List<String> s2) {
		if (s1 == null || s1.size() == 0) {
			return null;
		}
		if (s2 == null || s2.size() == 0) {
			return null;
		}
		List<String> str = new ArrayList<String>();
		for (String ss1 : s1) {
			for (String ss2 : s2) {
				str.add(ss1 + "|" + ss2);
			}
		}
		return str;
	}

}

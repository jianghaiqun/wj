package cn.com.sinosoft.action.shop;

import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.utility.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文章最终页产品推荐.
 * 
 * @author congzn .
 * 
 */
public class AjaxWapProduct {
	private static final Logger logger = LoggerFactory.getLogger(AjaxWapProduct.class);

	// 产品展示数量
	private static int showCount = 2;
	// 产品随机范围
	private static int limitCount = 20;
	// 首次查询数量
	private static int firstCount = 0;

	private static int twoQueryCount = 0;
	
	public static List<HashMap<String, String>> queryProductById(String productID1, String productID2){
		List<HashMap<String, String>> resultProductList = new ArrayList<HashMap<String, String>>();
		StringBuffer productByIdSql = new StringBuffer(
				"select SQL_CACHE a.* from SDSearchRelaProduct a, productrelainfo b where a.productid=b.productid and b.IsPublish='Y'");
		String parame = "";
		if(StringUtil.isNotEmpty(productID1) && StringUtil.isNotEmpty(productID2)){
			parame = "'"+productID1+"'"+",'"+productID2+"'";
		}else if(StringUtil.isEmpty(productID1)){
			parame = "'"+productID2+"'";
		}else if(StringUtil.isEmpty(productID2)){
			parame = "'"+productID1+"'";
		}
		productByIdSql.append(" and a.productID in (" + parame + ")");
		
		GetDBdata dBdata = new GetDBdata();
		try {
			resultProductList = dBdata.query(productByIdSql.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return resultProductList;
	}
	
	/**
	 * 通过保险类型+保险公司查询产品.
	 * 
	 * @param producttype
	 * @param insurance
	 * @throws Exception
	 */
	public static List<HashMap<String, String>> queryProduct(
			String producttype, String insurance) throws Exception {

		firstCount = 0;

		twoQueryCount = 0;

		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();

		List<HashMap<String, String>> queryResult = new ArrayList<HashMap<String, String>>();

		List<HashMap<String, String>> tempList = new ArrayList<HashMap<String, String>>();
		
		GetDBdata dBdata = new GetDBdata();

		String[] quertParame = new String[1];

		quertParame[0] = producttype;
		
		String superior;
		
		if(!StringUtil.isEmpty(producttype)){
			superior = producttype.substring(0, 1) + "00";
		}else{
			return resultList;
		}

		String type = "";

		// SQL_CACHE 使用mysql 缓存
		StringBuffer sb = new StringBuffer();
		sb.append("select SQL_CACHE b.textvalue ");
		sb.append("from ");
		sb.append("zdcolumnvalue b, ");
		sb.append("zccatalog z1, ");
		sb.append("zcarticle z2 ");
		sb.append("where ");
		sb.append("b.relaid=z2.ID and ");
		sb.append("z1.id = z2.CatalogID and ");
		sb.append("z1.producttype is not null and z2.Status='30' and ");
		sb.append("b.columncode='RiskCode' and z1.producttype=? ");

		// F 车险 G 综合 H 品牌
		if ("F00".equals(producttype) || "G00".equals(producttype)
				|| "H00".equals(producttype)) {

			String sql = "select b.* from (select a.* from (select  srp.* from SDSearchRelaProduct srp, productrelainfo pri where srp.productid=pri.productid and pri.IsPublish='Y' and left(srp.productID,4)= "
					+ insurance
					+ " "
					+ "order by salesvolume + 0  desc LIMIT 0,"
					+ limitCount
					+ ") a   order by RAND()  limit "
					+ showCount
					+ ") b "
					+ "union "
					+ "select b.* from (select a.* from (select srp.* from SDSearchRelaProduct srp, productrelainfo pri where srp.productid=pri.productid and pri.IsPublish='Y'  order by salesvolume + 0  desc LIMIT 0,"
					+ limitCount
					+ ") a  order by RAND()  limit "
					+ limitCount
					+ ") b";

			if ("".equals(insurance) || insurance == null) {
				tempList = queryProductAll();
				resultList = randomValue(tempList, 0, showCount);
			} else {
				tempList = dBdata.query(sql);
				for (int i = 0; i < showCount; i++) {
					resultList.add(tempList.get(i));
				}
			}

			return resultList;

		} else {

			tempList = dBdata.query(sb.toString(), quertParame);

			for (Map<String, String> map : tempList) {
				
				if (StringUtil.isNotEmpty(map.get("TextValue"))) {
					String insuranceStr = map.get("TextValue").substring(0, 4);
					if (insuranceStr.equals(insurance)) {
						HashMap<String, String> tempMap = new HashMap<String, String>();
						tempMap.put("TextValue", map.get("TextValue"));
						queryResult.add(tempMap);
					}
				}
				

			}

			if (queryResult.size() == 0) {
				queryResult = tempList;
			}
			
		}

		try {

			if (queryResult.size() < showCount) {

				type = "A";

				firstCount = queryResult.size();

				for (int i = 0; i < queryResult.size(); i++) {
					resultList.add(queryResult.get(i));
				}

				if (!superior.equals(producttype)) {

					if (firstCount > 0) {
						String parame = CombinationConditions(queryResult,
								firstCount);
						sb.append(" and b.textvalue not in (" + parame + ") ");
					}
					
					// 重新设置险种
					if(tempList.size()-firstCount > 0){
						quertParame[0] = producttype;
					}else{
						quertParame[0] = superior;
					}

					List<HashMap<String, String>> queryResult1 = dBdata.query(
							sb.toString(), quertParame);

					twoQueryCount = queryResult1.size();

					int Count = twoQueryCount + firstCount;

					if (queryResult1.size() > 0) {

						if (Count < showCount) {
							for (int i = 0; i < queryResult1.size(); i++) {
								resultList.add(queryResult1.get(i));
							}
						}else {
							for (int i = 0; i < queryResult1.size(); i++) {
								resultList.add(queryResult1.get(i));
							}
						}

					} else {
						type += "B";
						tempList = queryProductAll();
						for (int i = 0; i < tempList.size(); i++) {
							resultList.add(tempList.get(i));
						}
					}
				} else {
					type += "B";
					tempList = queryProductAll();
					for (int i = 0; i < tempList.size(); i++) {
						resultList.add(tempList.get(i));
					}
				}
			} else {
				type = "B";
				resultList = queryResult;
			}
		} catch (Exception e) {
			
		}

		if (resultList.size() < showCount) {
			type += "B";
			tempList = queryProductAll();
			for (int i = 0; i < tempList.size(); i++) {
				resultList.add(tempList.get(i));
			}
		}
		
		return queryProductDetail(resultList, type);
	}

	/**
	 * 查询全部产品
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<HashMap<String, String>> queryProductAll()
			throws Exception {

		GetDBdata dBdata = new GetDBdata();
		String sql = "select SQL_CACHE srp.* from SDSearchRelaProduct srp, productrelainfo pri where srp.productid=pri.productid and pri.IsPublish='Y'   order by salesvolume+0 desc limit 0,"
				+ limitCount;
		List<HashMap<String, String>> listAll = dBdata.query(sql);

		return listAll;
	}

	/**
	 * 通过产品ID 查询产品.
	 * 
	 * @param list
	 * @param type
	 * @throws Exception
	 */
	public static List<HashMap<String, String>> queryProductDetail(
			List<HashMap<String, String>> list, String type) throws Exception {
		// type A.进入再次查询 AB.二次查询+全部查询
		List<HashMap<String, String>> resultProductList = new ArrayList<HashMap<String, String>>();
		List<HashMap<String, String>> tempProductList = null;
		List<HashMap<String, String>> tempList = null;
		GetDBdata dBdata = new GetDBdata();
		String parame = null;

		StringBuffer productByIdSql = new StringBuffer(
				"select SQL_CACHE srp.* from SDSearchRelaProduct srp, productrelainfo pri where srp.productid=pri.productid and pri.IsPublish='Y' and 1=1 ");

		int tempCount = 0;

		if (list.size() > 0) {

			// 首次 0 + 二次 0 + 全部
			if (firstCount == 0 && twoQueryCount == 0 && "AB".equals(type)) {
				resultProductList = randomValue(list, 0, showCount);
				return resultProductList;
			}
			// ID + 产品(+全部)
			if ("AB".equals(type)) {

				tempCount = firstCount + twoQueryCount;

				parame = CombinationConditions(list, tempCount);

				productByIdSql.append(" and srp.productID in(" + parame + ")");

				resultProductList = dBdata.query(productByIdSql.toString());

				if (resultProductList.size() > 0) {
					tempProductList = randomValue(list, tempCount, showCount
							- tempCount);
				} else {
					tempProductList = randomValue(list, tempCount, showCount);
				}

				for (int i = 0; i < tempProductList.size(); i++) {
					resultProductList.add(tempProductList.get(i));
				}

			}

			// ID+ID
			if ("A".equals(type)) {

				if (firstCount != 0 && firstCount < showCount) {
					tempCount = firstCount;
					parame = CombinationConditions(list, firstCount);
					
				} else if(twoQueryCount != 0 && twoQueryCount < showCount){
					
					tempCount = twoQueryCount;
					parame = CombinationConditions(list, twoQueryCount);
				}

				if (tempCount > 0) {

					productByIdSql.append(" and srp.productID in(" + parame + ")");

					resultProductList = dBdata.query(productByIdSql.toString());

				}

				String tempparame = CombinationConditions(list, list.size());

				StringBuffer sql = new StringBuffer("select SQL_CACHE srp.* from SDSearchRelaProduct srp, productrelainfo pri where srp.productid=pri.productid and pri.IsPublish='Y'  and srp.productID in("
						+ tempparame+ ")");
				if(tempCount > 0){
					sql.append(" and srp.productID not in("+parame+")");
				}
				sql.append(" order by salesvolume + 0 desc LIMIT 0,"+ limitCount + "");
				
				tempList = dBdata.query(sql.toString());

				tempList = randomValue(tempList, 0, showCount
						- resultProductList.size());

				for (int i = 0; i < tempList.size(); i++) {
					resultProductList.add(tempList.get(i));
				}

			}

			// 2-20 || > 20
			if ("B".equals(type)) {

				parame = CombinationConditions(list, list.size());

				productByIdSql.append(" and srp.productID in(" + parame
						+ ") order by salesvolume + 0 desc LIMIT 0,"
						+ limitCount + "");

				resultProductList = dBdata.query(productByIdSql.toString());

				resultProductList = randomValue(resultProductList, 0, showCount);
				

			}

			if (resultProductList.size() < showCount) {
				
				tempProductList = randomValue(queryProductAll(), 0, showCount
						- resultProductList.size());
				for (int i = 0; i < tempProductList.size(); i++) {
					resultProductList.add(tempProductList.get(i));
				}
				
				
			}

		} else {
			// System.out.println("数据库.没有任何产品");
		}

		return resultProductList;
	}

	/**
	 * 随机取值.
	 * 
	 * @param list
	 *            集合.
	 * @param start
	 *            随机起始位置.
	 * @param queryCount
	 *            随机次数.
	 * @return
	 */
	public static List<HashMap<String, String>> randomValue(
			List<HashMap<String, String>> list, int start, int queryCount) {
		
		List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();
		try{
			
			if (list.size() > 0) {
				if(list.size() < queryCount){
					queryCount = list.size();
				}
				List<Integer> tempList = new ArrayList<Integer>();
				
				A: for (int i = 0; i < queryCount; i++) {
					int tempRandom = (int) (Math.random() * ((list.size() - 1)
							- start + 1))
							+ start;
					for (int j = 0; j < tempList.size(); j++) {
						if (tempList.get(j).equals(tempRandom)) {
							i--;
							continue A;
						}
					}
					tempList.add(tempRandom);
					resultList.add(list.get(tempRandom));
					
				}
			}
		}catch (Exception e) {
		}
		return resultList;
	}

	/**
	 * 组合条件.
	 * 
	 * @param list
	 * @param count
	 * @return
	 */
	public static String CombinationConditions(
			List<HashMap<String, String>> list, int count) {

		StringBuffer sb = new StringBuffer();

		if (count > 0) {
			for (int i = 0; i < count; i++) {
				if(i < list.size()){
					if (StringUtil.isNotEmpty(list.get(i).get("TextValue"))) {
						sb.append(list.get(i).get("TextValue") + ",");
					}
				}
			}

			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

}

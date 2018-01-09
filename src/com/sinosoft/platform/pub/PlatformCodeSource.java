/**
 * 
 */
package com.sinosoft.platform.pub;

import org.apache.commons.lang3.StringUtils;

import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.CodeSource;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

/**
 * @author Administrator
 * 
 */
public class PlatformCodeSource extends CodeSource {

	public DataTable getCodeData(String codeType, Mapx params) {
		DataTable dt = null;
		String conditionField = params.getString("ConditionField");
		String conditionValue = params.getString("ConditionValue");
		if ("District".equals(codeType)) {
			QueryBuilder qb = new QueryBuilder("select code,name from ZDDistrict where " + conditionField + "=?",
					conditionValue);
			String parentCode = params.getString("ParentCode");
			if (StringUtil.isNotEmpty(parentCode)) {
				qb.append(" and Code like ?");

				if (parentCode.startsWith("11") || parentCode.startsWith("12") || parentCode.startsWith("31")
						|| parentCode.startsWith("50")) {
					qb.add(parentCode.substring(0, 2) + "%");
					qb.append(" and TreeLevel=3");
				} else if (parentCode.endsWith("0000")) {
					qb.add(parentCode.substring(0, 2) + "%");
					qb.append(" and TreeLevel=2");
				} else if (parentCode.endsWith("00")) {
					qb.add(parentCode.substring(0, 4) + "%");
					qb.append(" and TreeLevel=3");
				} else {
					qb.add("#");// 返回空列表
				}
			} else if (conditionField.equals("1")) {
				return new DataTable();
			}
			dt = qb.executeDataTable();
		} else if ("User".equals(codeType)) {
			QueryBuilder qb = new QueryBuilder(
					"select UserName,UserName as 'Name',RealName,isBranchAdmin from ZDUser where " + conditionField
							+ "=?", conditionValue);
			dt = qb.executeDataTable();
		}  else if ("areaF".equals(codeType)) {
			QueryBuilder qb = new QueryBuilder(
					"select concat(concat(areavalue,'_'),id),name from area where parent_id = '' order by areavalue ");
			dt = qb.executeDataTable();
		} else if ("areaS".equals(codeType)) {
			QueryBuilder qb = new QueryBuilder(
					"select areavalue,name from area where parent_id = ? order by areavalue ", conditionValue);
			dt = qb.executeDataTable();
		}else if ("areaF1".equals(codeType)) {
			QueryBuilder qb = new QueryBuilder(
					"select id,name from area WHERE  parent_id IS NULL AND insuranceCompany IS NULL ORDER BY NAME ");
			dt = qb.executeDataTable();
		} else if ("areaS1".equals(codeType)) {
			QueryBuilder qb = new QueryBuilder(
					"select id,name from area where parent_id = ? ORDER BY NAME ", conditionValue);
			dt = qb.executeDataTable();
		}else if ("riskType".equals(codeType)) {
			QueryBuilder qb = new QueryBuilder(
					"select CodeValue,CodeName from zdcode where parentcode = 'ContentSign' and CodeValue in ('A','B','C','D','E','F','G') order by CodeValue ");
			dt = qb.executeDataTable();
		} //fhz 返回所在地区-----ysc
		else if ("areaHZ".equals(codeType)) {
		    String sql="select id,name from area where (parent_id = '' or parent_id is null) and insuranceCompany =(select insuranceCompany from SDInformation where orderSn='"+conditionValue.split(",")[0]+"' and informationSn='" +conditionValue.split(",")[1]+"') and (productId='" +conditionValue.split(",")[2]+"' or productId is null) order by areavalue ";
		    QueryBuilder qb = new QueryBuilder(sql);
	          dt = qb.executeDataTable();
        }else if ("areaHZS".equals(codeType)) {
			QueryBuilder qb = new QueryBuilder(
					"select id,name from area where parent_id = ? order by areavalue ", conditionValue);
			dt = qb.executeDataTable();
		}
        else if ("areaHZ1".equals(codeType)) {
                    String sql="select id,name from area where (parent_id = '' or parent_id is null) and insuranceCompany =(select insuranceCompany from SDInformation where orderSn='"+conditionValue.split(",")[0]+"' and informationSn='" +conditionValue.split(",")[1]+"') and (productId='" +conditionValue.split(",")[2]+"' or productId is null)order by areavalue ";
			QueryBuilder qb = new QueryBuilder(sql);
	                dt = qb.executeDataTable();
        }else if ("areaHZS1".equals(codeType)) {
			QueryBuilder qb = new QueryBuilder(
					"select id,name from area where parent_id = ? order by areavalue ", conditionValue);
			dt = qb.executeDataTable();
		} else if ("MenuChild".equals(codeType) && StringUtil.isNotEmpty(conditionValue)) {
			QueryBuilder qb = new QueryBuilder("select ID ,Name from zdmenu where ParentID=? order by OrderFlag ", conditionValue);
			dt = qb.executeDataTable();
			
		} else {
			Mapx map = CacheManager.getMapx("Code", codeType);
			if (conditionValue.equals("2")) {
				map.remove("01");
			}
			if (map != null) {
				dt = map.toDataTable();
			}
		}
		return dt;
	}
}

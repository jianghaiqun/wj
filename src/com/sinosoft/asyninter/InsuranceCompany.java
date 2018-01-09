package com.sinosoft.asyninter;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.schema.ZDCodeSchema;
import com.sinosoft.schema.ZDCodeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongsheng.
 */
/**
 * ClassName: InsuranceCompany <br/>
 * date: 2016年12月1日 上午9:28:31 <br/>
 *
 * @author dongsheng
 * @version
 */
public class InsuranceCompany {

	private String dir;
	private String productPrefix;
	private String name;

	/**
	 * Creates a new instance of InsuranceCompany.
	 *
	 * @param productPrefix
	 * @param dir
	 */

	private InsuranceCompany(String name, String dir, String productPrefix) {

		// foresealife("/alidata/sftpFile/sftp_fsl", "1095");
		this.name = name;
		this.dir = dir;
		this.productPrefix = productPrefix;
	}

	/**
	 * dir.
	 *
	 * @return the dir
	 */
	public String getDir() {

		return dir;
	}

	/**
	 * dir.
	 *
	 * @param dir
	 *            the dir to set
	 */
	public void setDir(String dir) {

		this.dir = dir;
	}

	/**
	 * productPrefix.
	 *
	 * @return the productPrefix
	 */
	public String getProductPrefix() {

		return productPrefix;
	}

	/**
	 * productPrefix.
	 *
	 * @param productPrefix
	 *            the productPrefix to set
	 */
	public void setProductPrefix(String productPrefix) {

		this.productPrefix = productPrefix;
	}

	/**
	 * name.
	 *
	 * @return the name
	 */
	public String getName() {

		return name;
	}

	/**
	 * name.
	 *
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {

		this.name = name;
	}

	public static List<InsuranceCompany> build() {

		List<InsuranceCompany> list = new ArrayList<InsuranceCompany>();

		String sql = "WHERE CodeType LIKE 'SFTP_PATHS' AND zdcode.ParentCode LIKE  'SFTP_PATHS'";
		ZDCodeSchema zdCodeSchema = new ZDCodeSchema();
		QueryBuilder qb = new QueryBuilder(sql);
		ZDCodeSet set = zdCodeSchema.query(qb);

		if (set != null && set.size() > 0) {

			for (int i = 0; i < set.size(); i++) {
				zdCodeSchema = set.get(i);
				InsuranceCompany company = new InsuranceCompany(zdCodeSchema.getMemo(), zdCodeSchema.getCodeValue(),
						zdCodeSchema.getCodeName());
				list.add(company);
			}
		}

		return list;

	}

	public static void main(String[] args) {

		List<InsuranceCompany> companyList = InsuranceCompany.build();
//		for (InsuranceCompany company : companyList) {
//			System.out.println("保险公司:" + company.getName() +
//					"路径:" + company.getDir() + "产品前缀:" + company.getProductPrefix());
//		}

	}
}

/**
 * Copyright (c) 2006 sinosoft Co. Ltd.
 * All right reserved.
 */
package cn.com.sinosoft.common;

import cn.com.sinosoft.util.ArithUtil;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.FMCalFactorSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ClassName: SalesCalculator
 * </p>
 * <p>
 * Company: Sinosoft Co. Ltd.
 * </p>
 * 
 * @author xijiahui
 * @version 1.0
 */
public class ShopCalculator
{
	private static final Logger logger = LoggerFactory.getLogger(ShopCalculator.class);

	public CErrors mErrors = new CErrors();

	public FMCalFactorSet mCalFactors = new FMCalFactorSet();
	private FMCalFactorSet mCalFactorsBase = new FMCalFactorSet();
	private String CalCode = "";
	private List<HashMap<String, String>> mFMCalModeList;
	private String mCalSQL = "";
	private List<HashMap<String, String>> mFactorList = new ArrayList<HashMap<String, String>>();

	/**
	 * addBasicFactor
	 * 
	 * @param String
	 *            cFactorCode
	 * @param String
	 *            cFactorValue
	 */
	public void addBasicFactor(String cFactorCode, String cFactorValue)
	{

		// 改造后添加，使用Map存储
		HashMap<String, String> mFactorMap = new HashMap<String, String>();
		mFactorMap.put("FactorCode", cFactorCode);
		mFactorMap.put("FactorDefault", cFactorValue);
		mFactorMap.put("FactorType", "1");
		int i, iMax;
		iMax = this.mFactorList.size();
		HashMap<String, String> tFactorMap = new HashMap<String, String>();
		for (i = 0; i < iMax; i++)
		{
			tFactorMap = this.mFactorList.get(i);
			if (tFactorMap.get("FactorCode").toLowerCase().equals(cFactorCode.toLowerCase()))
			{
				mFactorList.remove(i);
				break;
			}
		}
		mFactorList.add(mFactorMap);

	}

	/**
	 * setCalCode
	 * 
	 * @param String
	 *            tCalCode
	 */
	public void setCalCode(String tCalCode)
	{

		CalCode = tCalCode;
	}

	/**
	 * calculate
	 */
	public String calculate()
	{

		if (!checkCalculate()) {
			return "0";
		}
		if (!getSQL()) {
			return "0";
		}
		{
			if (!interpretFactorInSQL())
				return "0";
		}
		return executeSQL();
	}

	/**
	 * executeSQL
	 */
	private String executeSQL()
	{

		String tReturn = "0";
		try {
			// ExeSQL tExeSQL = new ExeSQL();
			// log.info("解析前SQL==========="+mFMCalModeList.get(0).get("CalSQL"));
			// log.info("-------------------------------");
			// log.info("解析后SQL==========="+mCalSQL);
			// tReturn = tExeSQL.getOneValue(mCalSQL);

			if ("K00004".equals(CalCode)) {
				String sqlStr[] = mCalSQL.split(";");
				String limit = new QueryBuilder(sqlStr[0]).executeOneValue() + "";

				if (StringUtil.isEmpty(limit)) {
					return "Y";
				}

				String wjReturn = new QueryBuilder(sqlStr[1]).executeOneValue() + "";
				String b2bReturn = new QueryBuilder(sqlStr[2]).executeDataTable("B2B").getString(0, 0);
				if (ArithUtil.sub(ArithUtil.add(wjReturn, b2bReturn), limit).startsWith("-")) {
					tReturn = "Y";
				} else {
					tReturn = "N";
				}
			}
			else if ("K00011".equals(CalCode)) {
				String sqlStr [] = mCalSQL.split(";");
				
				String exists = new QueryBuilder(sqlStr[0]).executeOneValue() + "";
				if (Integer.valueOf(exists) == 0) {
					return "Y";
				}
				String wjReturn = new QueryBuilder(sqlStr[1]).executeOneValue() + "";
				if(Integer.valueOf(wjReturn) > 0){
					tReturn = "N";
				}else{
					String b2bReturn = new QueryBuilder(sqlStr[2]).executeDataTable("B2B").getString(0, 0);
					
					if(Integer.valueOf(b2bReturn) > 0){
						tReturn = "N";
					}else{
						tReturn = "Y";
					}
				}
				
			}
			else {
				tReturn = new QueryBuilder(mCalSQL).executeOneValue() + "";
			}

		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "Calculator";
			tError.functionName = "executeSQL";
			tError.errorMessage = "执行SQL语句：" + mFMCalModeList.get(0).get("CalSQL") + "失败!";
			this.mErrors.addOneError(tError);
		}
		return tReturn;
	}

	/**
	 * interpretFactorInSQL
	 */
	private boolean interpretFactorInSQL()
	{

		String tStr = null; 

		String tSql = mFMCalModeList.get(0).get("CalSQL").toString();
		try
		{
			while (true)
			{
				tStr = PubFun.getStr(tSql, 2, "#");
				if (tStr.equals(""))
				{
					break;
				}
				StringBuffer tSBql = new StringBuffer();
				tSBql.append("#");
				tSBql.append(tStr);
				tSBql.append("#");
				while (true)
				{
					String tStr1 = PubFun.getStr(tStr, 2, "?");
					if (tStr1.equals(""))
					{
						break;
					}
					StringBuffer tSBql1 = new StringBuffer();
					tSBql1.append("?");
					tSBql1.append(tStr1);
					tSBql1.append("?");
					tStr = StrTool.replaceEx(tStr, tSBql1.toString(),
							"!" + tStr1 + "=" +
									getValueByName(tStr1) + "!");
				}
				tSql = StrTool.replaceEx(tSql, tSBql.toString(),
						"$" + tStr + "$");
			}
			while (true)
			{
				tStr = PubFun.getStr(tSql, 2, "?");
				if (tStr.equals(""))
				{
					break;
				}
				StringBuffer tSBql = new StringBuffer();
				tSBql.append("?");
				tSBql.append(tStr);
				tSBql.append("?");
				tSql = StrTool.replaceEx(tSql, tSBql.toString(),
						getValueByName(tStr));
			}
			while (true)
			{
				tStr = PubFun.getStr(tSql, 2, "$");
				if (tStr.equals(""))
				{
					break;
				}
				StringBuffer tSBql = new StringBuffer();
				tSBql.append("$");
				tSBql.append(tStr);
				tSBql.append("$");
				;
				tSql = StrTool.replaceEx(tSql, tSBql.toString(),
						getValueByClassName(tStr));
			}
		} catch (Exception ex)
		{
			CError tError = new CError();
			tError.moduleName = "Calculator";
			tError.functionName = "interpretFactorInSQL";
			tError.errorMessage = "解释" + tSql + "的变量:" + tStr + "时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		mCalSQL = tSql;
		return true;
	}

	/**
	 * getSQL
	 */
	private boolean getSQL()
	{

		// CachedCalInfo cci = CachedCalInfo.getInstance();
		List<HashMap<String, String>> tFMCalMode = this.findCalModeByCalCode(CalCode);
		if (tFMCalMode == null)
		{
			CError tError = new CError();
			tError.moduleName = "Calculator";
			tError.functionName = "getSql";
			tError.errorMessage = "得到" + CalCode + "的SQL语句时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		mFMCalModeList = tFMCalMode;
		return true;
	}

	/**
	 * checkCalculate
	 */
	private boolean checkCalculate()
	{

		if (CalCode == null || CalCode.equals(""))
		{
			CError tError = new CError();
			tError.moduleName = "Calculator";
			tError.functionName = "checkCalculate";
			tError.errorMessage = "计算时必须有计算编码。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * getValueByClassName
	 * 
	 * @param String
	 *            cVarName
	 */
	private String getValueByClassName(String cVarName)
	{

		String tReturn = "";
		String className = cVarName.substring(0, cVarName.indexOf("("));
		Map tMap = new HashMap();
		while (true)
		{
			String tStr = PubFun.getStr(cVarName, 2, "!");
			if (tStr.equals(""))
			{
				break;
			}
			if (tStr.equals(""))
			{
				break;
			}
			String tStr1 = "!" + tStr.trim() + "!";
			cVarName = StrTool.replaceEx(cVarName, tStr1, "***");
			tMap.put(tStr.substring(0, tStr.indexOf("=")), tStr.substring(tStr.indexOf("=") + 1));
		}
		try
		{
			Class tClass = Class.forName("com.sinosoft.jbs.agentcalclass." +
					className);
			SalesCalInterface tservice = (SalesCalInterface) tClass.newInstance();
			tservice.submitData(tMap);
			tReturn = tservice.getResult();
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
		return tReturn;
	}

	/**
	 * getValueByName
	 * 
	 * @param String
	 *            cVarName
	 */
	private String getValueByName(String cVarName)
	{

		cVarName = cVarName.toLowerCase();
		int i, iMax;
		String tReturn = "";
		iMax = this.mFactorList.size();
		HashMap<String, String> tFactorMap = new HashMap<String, String>();
		for (i = 0; i < iMax; i++)
		{
			tFactorMap = this.mFactorList.get(i);
			if (tFactorMap.get("FactorCode").toLowerCase().equals(cVarName))
			{
				tReturn = tFactorMap.get("FactorDefault");
				break;
			}
		}
		return tReturn;
	}

	/*
	 * FMCalMode信息
	 */
	public List findCalModeByCalCode(String strCalCode) {

		// 使用list存储FMCalMode信息
		String sql = " select * from FMCalMode where calcode = ? ";
		String[] sqltemp = { strCalCode };
		GetDBdata db = new GetDBdata();
		List<HashMap<String, String>> tFMCalMode = null;
		try {
			tFMCalMode = db.query(sql, sqltemp);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		return tFMCalMode;

	}

	/**
	 * main 应用测试
	 * 
	 * @param String
	 *            [] args
	 */
	public static void main(String[] args)
	{

	}
}

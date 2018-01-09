package com.wangjin.lineteam;

import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.LineTeamPolicyInfoSchema;
import com.sinosoft.schema.LineTeamPolicyInfoSet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineTeamPolicyInfo extends Page {
	
	public static Mapx<String, String> init(Mapx<String, String> params) {
		// 保险公司
		params.put("companys", HtmlUtil.codeToOptions("LineTeam.Supplier", true));
		// 险种
		params.put("riskTypes", HtmlUtil.codeToOptions("LineTeam.SubRiskType", true));
		// 区域
		String lineBranchInnerCode =  new QueryBuilder("select Memo from zdcode "
				+ "where CodeType = 'BranchCode' and CodeValue = 'LineTeam'").executeString();
		String userBranchInnerCode = User.getBranchInnerCode();
		// 分公司角色
		if (userBranchInnerCode.length() == 16) {
			String userBranchInnerCodeStr = userBranchInnerCode.substring(0, 12);
			params.put(
					"provs",
					HtmlUtil.queryToOptions(new QueryBuilder("select Name,BranchInnerCode from zdbranch "
							+ "where TreeLevel = 3 and BranchInnerCode like '" + userBranchInnerCodeStr
							+ "%' and parentinnerCode = '" + lineBranchInnerCode + "'"), false));
			params.put(
					"citys",
					HtmlUtil.queryToOptions(new QueryBuilder("select Name,BranchInnerCode from zdbranch "
							+ "where TreeLevel = 4 and BranchInnerCode = '" + userBranchInnerCode + "'"), false));
			// 省级角色
		} else if (userBranchInnerCode.length() == 12) {
			params.put(
					"provs",
					HtmlUtil.queryToOptions(new QueryBuilder("select Name,BranchInnerCode from zdbranch "
							+ "where TreeLevel = 3 and BranchInnerCode like '" + userBranchInnerCode
							+ "%' and parentinnerCode = '" + lineBranchInnerCode + "'"), false));
			params.put(
					"citys",
					HtmlUtil.queryToOptions(new QueryBuilder("select Name,BranchInnerCode from zdbranch "
							+ "where TreeLevel = 4 and BranchInnerCode like '" + userBranchInnerCode + "%'"), true));
		} else {
			params.put(
					"provs",
					HtmlUtil.queryToOptions(new QueryBuilder("select Name,BranchInnerCode from zdbranch "
							+ "where TreeLevel = 3 and BranchInnerCode like '" + userBranchInnerCode
							+ "%' and parentinnerCode = '" + lineBranchInnerCode + "'"), true));
		}
		String today = DateUtil.toString(new Date(), "yyyy-MM-dd");
		String yesterday = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
		params.put("yesterday", yesterday);
		params.put("today", today);
		return params;
	}
	
	public static Mapx<String, Object> initDialog(Mapx<String, Object> params) {
//		String lineBranchInnerCode =  new QueryBuilder("select Memo from zdcode "
//				+ "where CodeType = 'BranchCode' and CodeValue = 'LineTeam'").executeString();
//		//所在地区
//		StringBuffer provStr = new StringBuffer();
//		StringBuffer cityStr = new StringBuffer();
//		if(User.getBranchInnerCode().startsWith(lineBranchInnerCode)){
//			QueryBuilder qb = new QueryBuilder(
//					"select a.`Name`,a.BranchInnerCode,a.TreeLevel,b.`Name` as ParentName,b.BranchInnerCode as parentBranchInnerCode "
//					+ "from zdbranch a left outer join zdbranch b on a.ParentInnerCode = b.BranchInnerCode "
//					+ "where a.BranchInnerCode = ?",User.getBranchInnerCode());
//			DataTable dt = qb.executeDataTable();
//			if(dt.getRowCount() > 0){
//				DataRow dr = dt.get(0);
//				int treeLevel = Integer.parseInt(dr.getString(2));
//				if(treeLevel == 3){
//					provStr.append("<option value='");
//					provStr.append(dr.getString(1));
//					provStr.append("'>");
//					provStr.append(dr.getString(0));
//					provStr.append("</option>");
//					try {
//						List<Map<String,Object>> citys = new GetDBdata().queryObj("select Name,BranchInnerCode from zdbranch "
//								+ "where ParentInnerCode = ?",new String[]{dr.getString(1)});
//						for(Map<String,Object> city : citys){
//							cityStr.append("<option value='");
//							cityStr.append(city.get("BranchInnerCode"));
//							cityStr.append("'>");
//							cityStr.append(city.get("Name"));
//							cityStr.append("</option>");
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}else if(treeLevel == 4){
//					provStr.append("<option value='");
//					provStr.append(dr.getString(4));
//					provStr.append("'>");
//					provStr.append(dr.getString(3));
//					provStr.append("</option>");
//					cityStr.append("<option value='");
//					cityStr.append(dr.getString(1));
//					cityStr.append("'>");
//					cityStr.append(dr.getString(0));
//					cityStr.append("</option>");
//				}
//			}
//		}else{
//			try {
//				List<Map<String,Object>> provs = new GetDBdata().queryObj("select Name,BranchInnerCode from zdbranch "
//						+ "where TreeLevel = 3 and ParentInnerCode = ?",new String[]{lineBranchInnerCode});
//				for(Map<String,Object> prov : provs){
//					provStr.append("<option value='");
//					provStr.append(prov.get("BranchInnerCode"));
//					provStr.append("'>");
//					provStr.append(prov.get("Name"));
//					provStr.append("</option>");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		params.put("provs", provStr.toString());
//		params.put("citys", cityStr.toString());
		
		String lineBranchInnerCode =  new QueryBuilder("select Memo from zdcode "
				+ "where CodeType = 'BranchCode' and CodeValue = 'LineTeam'").executeString();
		String userBranchInnerCode = User.getBranchInnerCode();
		// 分公司角色
		if (userBranchInnerCode.length() == 16) {
			String userBranchInnerCodeStr = userBranchInnerCode.substring(0, 12);
			params.put(
					"provs",
					HtmlUtil.queryToOptions(new QueryBuilder("select Name,BranchInnerCode from zdbranch "
							+ "where TreeLevel = 3 and BranchInnerCode like '" + userBranchInnerCodeStr
							+ "%' and parentinnerCode = '" + lineBranchInnerCode + "'"), false));
			params.put(
					"citys",
					HtmlUtil.queryToOptions(new QueryBuilder("select Name,BranchInnerCode from zdbranch "
							+ "where TreeLevel = 4 and BranchInnerCode = '" + userBranchInnerCode + "'"), false));
			// 省级角色
		} else if (userBranchInnerCode.length() == 12) {
			params.put(
					"provs",
					HtmlUtil.queryToOptions(new QueryBuilder("select Name,BranchInnerCode from zdbranch "
							+ "where TreeLevel = 3 and BranchInnerCode like '" + userBranchInnerCode
							+ "%' and parentinnerCode = '" + lineBranchInnerCode + "'"), false));
			params.put(
					"citys",
					HtmlUtil.queryToOptions(new QueryBuilder("select Name,BranchInnerCode from zdbranch "
							+ "where TreeLevel = 4 and BranchInnerCode like '" + userBranchInnerCode + "%'"), false));
		} else {
			params.put(
					"provs",
					HtmlUtil.queryToOptions(new QueryBuilder("select Name,BranchInnerCode from zdbranch "
							+ "where TreeLevel = 3 and BranchInnerCode like '" + userBranchInnerCode
							+ "%' and parentinnerCode = '" + lineBranchInnerCode + "'"), true));
		}
		
		//证件类型
		Map<String,Object> idType = new HashMap<String,Object>();
		idType.put("0", "身份证");
		idType.put("7", "护照");
		idType.put("99", "其他");
		params.put("idType", HtmlUtil.mapxToOptions(idType));
		// 保险公司
		params.put("companys", HtmlUtil.codeToOptions("LineTeam.Supplier", true));
		// 险种
		params.put("riskTypes", HtmlUtil.codeToOptions("LineTeam.SubRiskType", true));
		
		return params;
	}

	/**
	 * 加载城市列表
	 */
	public void loadCitys(){
		StringBuffer cityStr = new StringBuffer();
		try{
			String prov = $V("prov");
			List<Map<String,Object>> citys = new GetDBdata().queryObj("select name,BranchInnerCode from zdbranch "
					+ "where ParentInnerCode = ?", new String[]{prov});
			for(Map<String,Object> city : citys){
				cityStr.append("<option value='");
				cityStr.append(city.get("BranchInnerCode"));
				cityStr.append("'>");
				cityStr.append(city.get("Name"));
				cityStr.append("</option>");
			}
			Response.setStatus(1);
			Response.setMessage(cityStr.toString());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
		}
	}
	
	/**
	 * 加载保险公司
	 */
	public void loadInsurCompany(){
		StringBuffer companyStr = new StringBuffer();
		try{
			String cityInnercode = $V("cityInnercode");
			List<Map<String,Object>>  companys = new GetDBdata().queryObj("select DISTINCT b.CodeName,b.CodeValue "
					+ "from LineTeamRate a,zdcode b where b.CodeType = 'LineTeam.Supplier' "
					+ "and a.companyCode = b.CodeValue and a.branchInnercode = ?", new String[]{cityInnercode});
			for(Map<String,Object> company : companys){
				companyStr.append("<option value='");
				companyStr.append(company.get("CodeValue"));
				companyStr.append("'>");
				companyStr.append(company.get("CodeName"));
				companyStr.append("</option>");
			}
			Response.setStatus(1);
			Response.setMessage(companyStr.toString());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
		}
	}
	
	/**
	 * 加载险种
	 */
	public void loadRiskType(){
		StringBuffer riskTypeStr = new StringBuffer();
		try{
			String insurCompanyCode = $V("insurCompanyCode");
			String branchInnercode = $V("branchInnercode");
			List<Map<String,Object>>  companys = new GetDBdata().queryObj("select a.riskType,b.CodeName "
					+ "from LineTeamRate a,zdcode b where b.CodeType = 'LineTeam.SubRiskType' "
					+ "and  a.riskType = b.CodeValue and a.companyCode = ? and a.branchInnercode = ?", new String[]{insurCompanyCode,branchInnercode});
			for(Map<String,Object> company : companys){
				riskTypeStr.append("<option value='");
				riskTypeStr.append(company.get("riskType"));
				riskTypeStr.append("'>");
				riskTypeStr.append(company.get("CodeName"));
				riskTypeStr.append("</option>");
			}
			Response.setStatus(1);
			Response.setMessage(riskTypeStr.toString());
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
		}
	}
	
	/**
	 * 加载结算费率
	 */
	public void loadRate(){
		try{
			String branchInnercode = $V("branchInnercode");
			String insurCompanyCode = $V("insurCompanyCode");
			String riskType = $V("riskType");
			String rate = calculateRate(branchInnercode,insurCompanyCode,riskType);
			Response.setStatus(1);
			Response.setMessage(rate);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
		}
	}
	
	/**
	 * 结算费率计算
	 * @param branchInnercode 机构编码
	 * @param insurCompanyCode 保险公司编码
	 * @param riskType 险种
	 * @return
	 */
	public String calculateRate(String branchInnercode,String insurCompanyCode,String riskType){
		QueryBuilder qb = new QueryBuilder("select rate from LineTeamRate "
				+ "where branchInnercode = ? and companyCode = ? and riskType = ?");
		qb.add(branchInnercode);
		qb.add(insurCompanyCode);
		qb.add(riskType);
		String rate = qb.executeString();
		return formatDouble(new Double(rate));
	}

	/**
	 * 加载列表数据
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String policyNo = dga.getParams().getString("policyNo");
		String beginInsureDate = dga.getParams().getString("beginInsureDate");
		String endInsureDate = dga.getParams().getString("endInsureDate");
		String riskType = dga.getParams().getString("riskType");
		String companyCode = dga.getParams().getString("companyCode");
		String beginStartDate = dga.getParams().getString("beginStartDate");
		String endStartDate = dga.getParams().getString("endStartDate");
		String provs = dga.getParams().getString("provs");
		String citys = dga.getParams().getString("citys");
		String customName = dga.getParams().getString("customName");
		String beginCreatedate = dga.getParams().getString("beginCreatedate");
		String endCreatedate = dga.getParams().getString("endCreatedate");
		String beginCreatetime = dga.getParams().getString("beginCreatetime");
		String endCreatetime = dga.getParams().getString("endCreatetime");
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,a.customName,a.policyNo,a.prem,a.agentName,a.riskType,a.startDate,a.insureDate,"
				+ "a.plateNumber,a.agentPrice,a.status,date_format(a.cancelTime,'%Y-%c-%d') as cancelTime,"
				+ "b.`Name` as city,b.ParentName as prov,c.CodeName as companyName,d.CodeName as riskTypeName,IF (a.status = 1,'撤单',IF(a.remark1 = 0,'预收','承保')) AS remark1,a.remark2 as remark2 ,a.agentRate,a.createdate ");
		sql.append(" from LineTeamPolicyInfo a,");
		sql.append("(select a.`Name`,b.`Name` as ParentName,a.BranchInnerCode "
				+ "from zdbranch a left outer join zdbranch b on a.ParentInnerCode = b.BranchInnerCode) b,");
		sql.append("zdcode c,zdcode d");
		sql.append(" where c.CodeType = 'LineTeam.Supplier' and d.CodeType = 'LineTeam.SubRiskType' "
				+ "and a.branchInnercode = b.BranchInnerCode and a.companyCode = c.CodeValue "
				+ "and a.riskType = d.CodeValue ");
//		sql.append(" and ((a.userBranchInnerCode like '" + User.getBranchInnerCode()
//				+ "%' and a.userBranchInnerCode != ?) or a.createUser = ? ) ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		if (StringUtil.isNotEmpty(policyNo)) {
			qb.append(" and a.policyNo like '%" + policyNo.trim() + "%'");
		}
		if (StringUtil.isNotEmpty(beginInsureDate)) {
			qb.append(" and a.insureDate >= ? ", beginInsureDate);
		}
		if (StringUtil.isNotEmpty(endInsureDate)) {
			qb.append(" and a.insureDate <= ? ", endInsureDate);
		}
		if(StringUtil.isNotEmpty(beginStartDate)){
			qb.append(" and a.startDate >= ? ", beginStartDate);
		}
		if(StringUtil.isNotEmpty(endStartDate)){
			qb.append(" and a.startDate <= ? ", endStartDate);
		}
		if(StringUtil.isNotEmpty(riskType)){
			qb.append(" and a.riskType = ?",riskType);
		}
		if(StringUtil.isNotEmpty(companyCode)){
			qb.append(" and a.companyCode = ?",companyCode);
		}
		if(StringUtil.isNotEmpty(customName)){
			qb.append(" and a.customName like '%"+customName.trim()+"%'");
		}
		if(StringUtil.isNotEmpty(beginCreatedate)){
			if(StringUtil.isEmpty(beginCreatetime)){
				beginCreatetime = "00:00:00";
			}else {
				if (beginCreatetime.length() == 7) {
					beginCreatetime = "0" + beginCreatetime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(" + "'"
					+ beginCreatedate.trim() + " " + beginCreatetime.trim() + "')");
		}
		if(StringUtil.isNotEmpty(endCreatedate)){
			if(StringUtil.isEmpty(endCreatetime)){
				endCreatetime = "23:59:59";
			}else{
				if (endCreatetime.length() == 7) {
					endCreatetime = "0" + endCreatetime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP(" + "'"
					+ endCreatedate.trim() + " " + endCreatetime.trim() + "')");
		}
		if(StringUtil.isNotEmpty(citys)){
			citys = citys.trim();
			qb.append(" and (b.BranchInnerCode like '%"+citys+"%' or b.`Name` like '%"+citys+"%' "
					+ "or concat(b.ParentName, '-', b.`Name`) like '%"+citys+"%')");
		} else {
			if (StringUtil.isNotEmpty(provs)) {
				provs = provs.trim();
				qb.append(" and (b.BranchInnerCode like '%" + provs + "%' or b.`Name` like '%" + provs + "%' "
						+ "or concat(b.ParentName, '-', b.`Name`) like '%" + provs + "%')");
			}
		}
		//标识为预收数据
		qb.append("and a.remark1 = '0' ");
		qb.append(" order by createDate desc ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	/**
	 * 加载列表数据
	 */
	public static void dg1DataBindAccept(DataGridAction dga) {
		String policyNo = dga.getParams().getString("policyNo");
		String beginInsureDate = dga.getParams().getString("beginInsureDate");
		String endInsureDate = dga.getParams().getString("endInsureDate");
		String riskType = dga.getParams().getString("riskType");
		String companyCode = dga.getParams().getString("companyCode");
		String beginStartDate = dga.getParams().getString("beginStartDate");
		String endStartDate = dga.getParams().getString("endStartDate");
		String provs = dga.getParams().getString("provs");
		String citys = dga.getParams().getString("citys");
		String customName = dga.getParams().getString("customName");
		String beginCreatedate = dga.getParams().getString("beginCreatedate");
		String endCreatedate = dga.getParams().getString("endCreatedate");
		String beginCreatetime = dga.getParams().getString("beginCreatetime");
		String endCreatetime = dga.getParams().getString("endCreatetime");
		StringBuffer sql = new StringBuffer();
		sql.append("select a.id,a.customName,a.policyNo,a.prem,a.agentName,a.riskType,a.startDate,a.insureDate,"
				+ "a.plateNumber,a.agentPrice,a.status,date_format(a.cancelTime,'%Y-%c-%d') as cancelTime,"
				+ "b.`Name` as city,b.ParentName as prov,c.CodeName as companyName,d.CodeName as riskTypeName,"
				+ "IF (a.status = 1,'撤单',IF(a.remark1 = 0,'预收','承保')) AS remark1,a.remark2 as remark2 ,a.agentRate,a.createdate ");
		sql.append(" from LineTeamPolicyInfo a,");
		sql.append("(select a.`Name`,b.`Name` as ParentName,a.BranchInnerCode "
				+ "from zdbranch a left outer join zdbranch b on a.ParentInnerCode = b.BranchInnerCode) b,");
		sql.append("zdcode c,zdcode d");
		sql.append(" where c.CodeType = 'LineTeam.Supplier' and d.CodeType = 'LineTeam.SubRiskType' "
				+ "and a.branchInnercode = b.BranchInnerCode and a.companyCode = c.CodeValue "
				+ "and a.riskType = d.CodeValue ");
//		sql.append(" and ((a.userBranchInnerCode like '" + User.getBranchInnerCode()
//				+ "%' and a.userBranchInnerCode != ?) or a.createUser = ? ) ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		if (StringUtil.isNotEmpty(policyNo)) {
			qb.append(" and a.policyNo like '%" + policyNo.trim() + "%'");
		}
		if (StringUtil.isNotEmpty(beginInsureDate)) {
			qb.append(" and a.insureDate >= ? ", beginInsureDate);
		}
		if (StringUtil.isNotEmpty(endInsureDate)) {
			qb.append(" and a.insureDate <= ? ", endInsureDate);
		}
		if(StringUtil.isNotEmpty(beginStartDate)){
			qb.append(" and a.startDate >= ? ", beginStartDate);
		}
		if(StringUtil.isNotEmpty(endStartDate)){
			qb.append(" and a.startDate <= ? ", endStartDate);
		}
		if(StringUtil.isNotEmpty(riskType)){
			qb.append(" and a.riskType = ?",riskType);
		}
		if(StringUtil.isNotEmpty(companyCode)){
			qb.append(" and a.companyCode = ?",companyCode);
		}
		if(StringUtil.isNotEmpty(customName)){
			qb.append(" and a.customName like '%"+customName.trim()+"%'");
		}
		if(StringUtil.isNotEmpty(beginCreatedate)){
			if(StringUtil.isEmpty(beginCreatetime)){
				beginCreatetime = "00:00:00";
			}else {
				if (beginCreatetime.length() == 7) {
					beginCreatetime = "0" + beginCreatetime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(" + "'"
					+ beginCreatedate.trim() + " " + beginCreatetime.trim() + "')");
		}
		if(StringUtil.isNotEmpty(endCreatedate)){
			if(StringUtil.isEmpty(endCreatetime)){
				endCreatetime = "23:59:59";
			}else{
				if (endCreatetime.length() == 7) {
					endCreatetime = "0" + endCreatetime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP(" + "'"
					+ endCreatedate.trim() + " " + endCreatetime.trim() + "')");
		}
		if(StringUtil.isNotEmpty(citys)){
			citys = citys.trim();
			qb.append(" and (b.BranchInnerCode like '%"+citys+"%' or b.`Name` like '%"+citys+"%' "
					+ "or concat(b.ParentName, '-', b.`Name`) like '%"+citys+"%')");
		} else {
			if (StringUtil.isNotEmpty(provs)) {
				provs = provs.trim();
				qb.append(" and (b.BranchInnerCode like '%" + provs + "%' or b.`Name` like '%" + provs + "%' "
						+ "or concat(b.ParentName, '-', b.`Name`) like '%" + provs + "%')");
			}
		}
		//标识为承保数据
		qb.append("and a.remark1 = '1' ");
		qb.append(" order by createDate desc ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public String formatDouble(double value) {
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(value);
	}

	/**
	 * 线下预收逐条添加
	 */
	public void add() {
		//保单号校验
		String id = new QueryBuilder("select id,remark1 from lineteampolicyinfo "
				+ "where policyNo = ? and remark1 = ? limit 1",$V("policyNo"),$V("style")).executeString();
		
		if(id != null){
			Response.setStatus(0);
			Response.setMessage("保单号已经存在!");
			return;
		}
		LineTeamPolicyInfoSchema schema = new LineTeamPolicyInfoSchema();
		schema.setValue(Request);
		schema.setid(NoUtil.getMaxID("LineTeamPolicyInfoID") + "");
		schema.setbranchInnercode($V("citys"));
		schema.setcompanyCode($V("companyCode"));
		schema.setriskType($V("riskType"));
		schema.setprem(formatDouble(Double.parseDouble(schema.getprem())));
		schema.setpolicyNo($V("policyNo"));
		schema.setagentRate(formatDouble(Double.parseDouble(schema.getagentRate())));
		schema.setstartDate($V("startDate"));
		schema.setinsureDate($V("insureDate"));
		schema.setcustomName($V("customName"));
		schema.setagentName($V("agentName"));
		// 代理人手续费
		schema.setagentPrice($V("poundage"));
		schema.setplateNumber($V("plateNumber"));
		schema.setstatus("0");
		//新增预收数据标识
		schema.setremark1($V("style"));
		schema.setuserBranchInnercode(User.getBranchInnerCode());
		schema.setcreateDate(new Date());
		schema.setcreateUser(User.getUserName());
		Transaction trans = new Transaction();
		trans.add(schema, Transaction.INSERT);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("保存成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("保存失败！");
			return;
		}
	}

	/**
	 * 预收批量导入
	 */
	public void asyncBatchAdd() {
		LongTimeTask ltt = new LongTimeTask() {
			String currUser = User.getUserName();//当前用户
			public void execute() {
				uploadExcel(this,currUser,"0");
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始导入数据文件......");
		$S("TaskID", "" + ltt.getTaskID());
	}
	
	/**
	 * 承保批量导入
	 */
	public void asyncBatchAddAccept() {
		LongTimeTask ltt = new LongTimeTask() {
			String currUser = User.getUserName();//当前用户
			public void execute() {
				uploadExcel(this,currUser,"1");
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始导入数据文件......");
		$S("TaskID", "" + ltt.getTaskID());
	}
		
	public void uploadExcel(LongTimeTask task,String currUser,String style) {
		try{
			String fileaddress = $V("fileaddress");
			FileInputStream finput = null;
			try {
				finput = new FileInputStream(fileaddress);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
				task.addError("导入失败！请选择正确模板");
				return;
			}
			POIFSFileSystem fs = null;
			try {
				fs = new POIFSFileSystem(finput);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				task.addError("导入失败！请选择正确模板");
				return;
			}
			HSSFWorkbook wb = null;
			try {
				wb = new HSSFWorkbook(fs);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				task.addError("导入失败！请选择正确模板");
				return;
			}
			HSSFSheet sheet = wb.getSheetAt(0);
			try {
				finput.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				task.addError("导入失败！请选择正确模板");
				return;
			}
			// 最大行数
			int maxRow = sheet.getLastRowNum();
			if (maxRow - 9 <= 0) {
				task.addError("导入失败！导入的文件中不包含有效数据！");
				return;
			}
			//批次号
			 Date d = new Date();
		     SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
		     String dateNowStr = style + style +style + sdf.format(d);  
			
			// 添加数据
			Transaction trasaction = new Transaction();
			
			//修改分公司为代码
			String lineBranchInnerCode =  new QueryBuilder("select Memo from zdcode where CodeType = 'BranchCode' and CodeValue = 'LineTeam'").executeString();
			String sql = "select Name,BranchInnerCode from zdbranch where TreeLevel = 4 and BranchInnerCode like '"+ lineBranchInnerCode +"%'";		  
			QueryBuilder qb = new QueryBuilder(sql);
			DataTable dt = qb.executeDataTable();
			Map LineTeamMap = dt.toMapx(0, 1);
			
			//修改保险公司为代码
			String sql1 = " select codename,codevalue from zdcode where codetype = 'LineTeam.Supplier'";
			QueryBuilder qb1 = new QueryBuilder(sql1);
			DataTable dt1 = qb1.executeDataTable();
			Map compnayCodeMap = dt1.toMapx(0, 1);
			
			//修改险种为代码
			String sql2 = " select codename,codevalue from zdcode where codetype = 'LineTeam.SubRiskType'";
			QueryBuilder qb2 = new QueryBuilder(sql2);
			DataTable dt2 = qb2.executeDataTable();
			Map riskTypeMap = dt2.toMapx(0, 1);
			
//			Set<String> set = new HashSet<String>();
			
			for (int j = 10; j < maxRow + 1; j++) {
				
				//终止任务
				if (task.checkStop()) {
					return;
				}
				// 获取行数据
				HSSFRow row = sheet.getRow(j);
				task.setCurrentInfo("正在添加(" + (j - 9) + "/" + (maxRow - 9) + ")......");
				task.setPercent(Integer
						.valueOf((j - 9) * 100 / (maxRow - 9)));
				int rowNumber = j+1;
				
				String batchInnerCode = getAndValidExcelCellValue(row.getCell(1),"batchInnerCode",rowNumber);
				String compnayCode = getAndValidExcelCellValue(row.getCell(2),"compnayCode",rowNumber);
				String customName = getAndValidExcelCellValue(row.getCell(3),"customName",rowNumber);
				String plateNumber = getAndValidExcelCellValue(row.getCell(4),"plateNumber",rowNumber);
				String policyNo = getAndValidExcelCellValue(row.getCell(5),"policyNo",rowNumber);
				String prem = getAndValidExcelCellValue(row.getCell(6),"prem",rowNumber);
				String poundage = getAndValidExcelCellValue(row.getCell(7),"poundage",rowNumber);
				String bagentRate = getAndValidExcelCellValue(row.getCell(8),"bagentRate",rowNumber);
				String agentName = getAndValidExcelCellValue(row.getCell(9),"agentName",rowNumber);
				String riskType = getAndValidExcelCellValue(row.getCell(10), "riskType", rowNumber);
				String insureDate = getAndValidExcelCellValue(row.getCell(11),"insureDate",rowNumber);
				String startDate = getAndValidExcelCellValue(row.getCell(12),"startDate",rowNumber);
				
				
				if (LineTeamMap.containsKey(batchInnerCode)) {
					batchInnerCode = (String) LineTeamMap.get(batchInnerCode);
				}else {
					task.addError("第" + (j + 1) + "行数据错误,分公司名称有误");
					return;
				}
				
				if (compnayCodeMap.containsKey(compnayCode)) {
					compnayCode = (String) compnayCodeMap.get(compnayCode);
				}else{
					task.addError("第" + (j + 1) + "行数据错误,保险公司名称有误");
					return;
				}
				
				
				if (riskTypeMap.containsKey(riskType)) {
					riskType = (String) riskTypeMap.get(riskType);
				}else{
					task.addError("第" + (j + 1) + "行数据错误,险种名称有误");
					return;
				}
				
				//数据校验
//				String id = new QueryBuilder("select id from lineteampolicyinfo "
//						+ "where policyNo = ? and remark1 = ? ",policyNo,style).executeString();
//				if(id != null){
//					task.addError("第" + (j + 1) + "行数据错误,保单号"+policyNo+"已经存在");
//					return;
//				}
//				if (set.contains(policyNo)) {
//					task.addError("第" + (j + 1) + "行数据错误,保单号"+policyNo+"在本模版中已经存在");
//					return;
//				}
				
				LineTeamPolicyInfoSchema schema = new LineTeamPolicyInfoSchema();
				schema.setid(NoUtil.getMaxID("LineTeamPolicyInfoID") + "");
				schema.setbranchInnercode(batchInnerCode);
				schema.setcompanyCode(compnayCode);
				schema.setriskType(riskType);
				schema.setprem(prem);
				schema.setpolicyNo(policyNo);
				schema.setagentRate(bagentRate);
				schema.setstartDate(startDate);
				schema.setinsureDate(insureDate);
				schema.setcustomName(customName);
				schema.setagentName(agentName);
				schema.setagentPrice(poundage);
				schema.setplateNumber(plateNumber);
				schema.setstatus("0");
				schema.setremark1(style);
				schema.setremark2(dateNowStr);
				schema.setuserBranchInnercode(User.getBranchInnerCode());
				schema.setcreateDate(new Date());
				schema.setcreateUser(User.getUserName());
				trasaction.add(schema, Transaction.INSERT);
//				set.add(policyNo);
			}
			task.setPercent(100);
			if (!trasaction.commit()) {
				task.addError("导入异常！请选择正确模板！");
			}
		}catch(RuntimeException e){
			String error = e.getMessage();
			if(error != null && error.startsWith("行数据校验错误")){
				task.addError(error);
			}else{
				task.addError("导入异常！请选择正确模板！");
			}
			logger.error(e.getMessage(), e);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			task.addError("导入异常！请选择正确模板！");
		}
	}
	
	public String getAndValidExcelCellValue(HSSFCell cell,String proName,int rowNum){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	    DecimalFormat decimalFormat = new DecimalFormat("#.##");
	    decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
	    String nullEnabled = "plateNumber";
	    if(cell == null){
	    	if(nullEnabled.contains(proName)){
		    	return "";
			}else{
				throw new RuntimeException("行数据校验错误,第"+rowNum+"行必填字段不能为空!");
			}
	    }
		String val = null;
		if (cell.getCellType() == 0) {
			if(HSSFDateUtil.isCellDateFormatted(cell)) {
                double d = cell.getNumericCellValue();
                Date date = HSSFDateUtil.getJavaDate(d);
                val = sf.format(date);
            }else{
            	val = decimalFormat.format((cell.getNumericCellValue()));
            }
		} else if (cell.getCellType() == 1) {
			val = cell.getStringCellValue().trim();
		} else {
			val = cell.getStringCellValue().trim();
		}
		logger.info("cellType:{} value:{}", cell.getCellType(), val);
		if(StringUtil.isEmpty(val) && !nullEnabled.contains(proName)){
			throw new RuntimeException("行数据校验错误,第"+rowNum+"行必填字段不能为空!");
		}
		//日期格式校验
		if("insureDate".equals(proName) || "startDate".equals(proName)){
			try {
				sf.parse(val);
			} catch (ParseException e) {
				if("insureDate".equals(proName)){
					throw new RuntimeException("行数据校验错误,第"+rowNum+"行出单日期必须是yyyy-mm-dd的日期格式!");
				}else{
					throw new RuntimeException("行数据校验错误,第"+rowNum+"行保单起期必须是yyyy-mm-dd的日期格式!");
				}
			}
		}
		return val;
	}

	/**
	 * 删除
	 */
	public void delete() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		LineTeamPolicyInfoSchema lineTeam = new LineTeamPolicyInfoSchema();
		LineTeamPolicyInfoSet set = lineTeam.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "删除失败!");
		}
	}
	/**
	 * 批次删除
	 */
	public void batchdel() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {	
			Response.setStatus(0);
			Response.setMessage("传入批次号时发生错误!");
			return;
		}
		QueryBuilder qBuilder = new QueryBuilder("select * from lineteampolicyinfo where id = '"+ ids +"'");
		DataTable dt = qBuilder.executeDataTable();
		String remark2 = dt.getString(0, "remark2");
		if (StringUtil.isEmpty(remark2)) {
			Response.setLogInfo(1, "不是批次导入的数据，不可批次删除。");
			return;
		}
		Transaction trans = new Transaction();
		LineTeamPolicyInfoSchema lineTeam = new LineTeamPolicyInfoSchema();
		LineTeamPolicyInfoSet set = lineTeam.query(new QueryBuilder("where remark2 in (" + remark2 + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "删除失败!");
		}
	}
	
	/**
	 * 撤单
	 */
	public void cancelOrder(){
		String prem = $V("prem");//冲销金额
		String cancelTime = $V("cancelTime");//撤单时间
		String id = $V("id");
		String agentPrice = $V("agentPrice");
		LineTeamPolicyInfoSchema schema = new LineTeamPolicyInfoSchema();
		schema.setid(id);
		LineTeamPolicyInfoSet lineSet = schema.query();
		LineTeamPolicyInfoSchema lineSchema = lineSet.get(0);//原保单数据
		//数据验证
		if(Double.parseDouble(prem) > Double.parseDouble(lineSchema.getprem())){
			Response.setStatus(0);
			Response.setMessage("冲销金额不能大于原保费！");
			return;
		}
		Transaction trans = new Transaction();
		LineTeamPolicyInfoSchema newSchema = (LineTeamPolicyInfoSchema)lineSchema.clone();
		newSchema.setprem("-"+formatDouble(Double.parseDouble(prem)));
		newSchema.setid(NoUtil.getMaxID("LineTeamPolicyInfoID") + "");
		newSchema.setagentPrice("-"+formatDouble(Double.parseDouble(agentPrice)));
		newSchema.setstatus("1");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date cancelTimeDate = null;
		try {
			cancelTimeDate = df.parse(cancelTime);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		newSchema.setuserBranchInnercode(User.getBranchInnerCode());
		newSchema.setcreateUser(User.getUserName());
		newSchema.setcreateDate(new Date());
		newSchema.setmodifyUser("");
		newSchema.setmodifyDate(null);
		newSchema.setcancelTime(cancelTimeDate);
		lineSchema.setcancelTime(cancelTimeDate);
		trans.add(lineSchema, Transaction.UPDATE);
		trans.add(newSchema, Transaction.INSERT);
		if (trans.commit()) {
			UserLog.log("LineTeam", "cancelPolicy", "用户："+User.getUserName()+",撤单保单号:"+lineSchema.getpolicyNo(), Request.getClientIP());
			Response.setLogInfo(1, "撤单成功!");
		} else {
			Response.setLogInfo(0, "撤单失败!");
		}
	}
	
	public void orderCount(){
		String policyNo = $V("policyNo");
		String beginInsureDate = $V("beginInsureDate");
		String endInsureDate = $V("endInsureDate");
		String riskType = $V("riskType");
		String companyCode = $V("companyCode");
		String beginStartDate = $V("beginStartDate");
		String endStartDate = $V("endStartDate");
		String provs = $V("provs");
		String citys = $V("citys");
		String customName = $V("customName");
		String style = $V("style");
		String beginCreatedate = $V("beginCreatedate");
		String endCreatedate = $V("endCreatedate");
		String beginCreatetime = $V("beginCreatetime");
		String endCreatetime = $V("endCreatetime");
		StringBuffer sql = new StringBuffer();
		sql.append("select round(sum(a.agentPrice),2) as agentPrice , round(sum(a.prem),2) as prem ");
		sql.append(" from LineTeamPolicyInfo a,");
		sql.append("(select a.`Name`,b.`Name` as ParentName,a.BranchInnerCode "
				+ "from zdbranch a left outer join zdbranch b on a.ParentInnerCode = b.BranchInnerCode) b,");
		sql.append("zdcode c,zdcode d");
		sql.append(" where c.CodeType = 'LineTeam.Supplier' and d.CodeType = 'LineTeam.SubRiskType' "
				+ "and a.branchInnercode = b.BranchInnerCode and a.companyCode = c.CodeValue "
				+ "and a.riskType = d.CodeValue ");
//		sql.append(" and ((a.userBranchInnerCode like '" + User.getBranchInnerCode()
//				+ "%' and a.userBranchInnerCode != ?) or a.createUser = ? ) ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		if (StringUtil.isNotEmpty(policyNo)) {
			qb.append(" and a.policyNo like '%" + policyNo.trim() + "%'");
		}
		if (StringUtil.isNotEmpty(beginInsureDate)) {
			qb.append(" and a.insureDate >= ? ", beginInsureDate);
		}
		if (StringUtil.isNotEmpty(endInsureDate)) {
			qb.append(" and a.insureDate <= ? ", endInsureDate);
		}
		if(StringUtil.isNotEmpty(beginStartDate)){
			qb.append(" and a.startDate >= ? ", beginStartDate);
		}
		if(StringUtil.isNotEmpty(endStartDate)){
			qb.append(" and a.startDate <= ? ", endStartDate);
		}
		if(StringUtil.isNotEmpty(riskType)){
			qb.append(" and a.riskType = ?",riskType);
		}
		if(StringUtil.isNotEmpty(companyCode)){
			qb.append(" and a.companyCode = ?",companyCode);
		}
		if(StringUtil.isNotEmpty(customName)){
			qb.append(" and a.customName like '%"+customName.trim()+"%'");
		}
		if(StringUtil.isNotEmpty(beginCreatedate)){
			if(StringUtil.isEmpty(beginCreatetime)){
				beginCreatetime = "00:00:00";
			}else {
				if (beginCreatetime.length() == 7) {
					beginCreatetime = "0" + beginCreatetime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) >= UNIX_TIMESTAMP(" + "'"
					+ beginCreatedate.trim() + " " + beginCreatetime.trim() + "')");
		}
		if(StringUtil.isNotEmpty(endCreatedate)){
			if(StringUtil.isEmpty(endCreatetime)){
				endCreatetime = "23:59:59";
			}else{
				if (endCreatetime.length() == 7) {
					endCreatetime = "0" + endCreatetime;
				}
			}
			qb.append(" and UNIX_TIMESTAMP(DATE_FORMAT(a.createDate,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP(" + "'"
					+ endCreatedate.trim() + " " + endCreatetime.trim() + "')");
		}
		if(StringUtil.isNotEmpty(citys)){
			citys = citys.trim();
			qb.append(" and (b.BranchInnerCode like '%"+citys+"%' or b.`Name` like '%"+citys+"%' "
					+ "or concat(b.ParentName, '-', b.`Name`) like '%"+citys+"%')");
		} else {
			if (StringUtil.isNotEmpty(provs)) {
				provs = provs.trim();
				qb.append(" and (b.BranchInnerCode like '%" + provs + "%' or b.`Name` like '%" + provs + "%' "
						+ "or concat(b.ParentName, '-', b.`Name`) like '%" + provs + "%')");
			}
		}
		//标识为预收数据
		qb.append("and a.remark1 = '" + style + "' ");
		qb.append(" order by createDate desc ");

		DataTable dt = qb.executeDataTable();

		Response.setMessage(dt.getString(0, 0) + "&" + dt.getString(0, 1));

	}
}

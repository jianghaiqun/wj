/**
 * 
 */
package com.wangjin.lineteam;

import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.LineTeamRateSchema;
import com.sinosoft.schema.LineTeamRateSet;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wangcaiyun
 *
 */
public class RateManage extends Page {

	
	public static void treeDataBind(TreeAction ta) {
		DataTable dt = null;
		StringBuffer sql = new StringBuffer("select BranchInnerCode as ID, ParentInnerCode as ParentID, Name, TreeLevel from zdbranch where BranchInnerCode=? or ParentInnerCode like ?  ");
		sql.append(" order by OrderFlag ");
		String BranchInnerCode = User.getBranchInnerCode();
		String lineBranchInnerCode =  new QueryBuilder("select Memo from zdcode "
				+ "where CodeType = 'BranchCode' and CodeValue = 'LineTeam'").executeString();
		if (!BranchInnerCode.startsWith(lineBranchInnerCode)) {
			BranchInnerCode = lineBranchInnerCode;
		}
		dt = new QueryBuilder(sql.toString(), BranchInnerCode, BranchInnerCode+"%").executeDataTable();
		ta.setRootText("分支机构");
		dt.setWebMode(false);
		ta.bindData(dt);
		
	}
	
	public static Mapx<String, String> initDialog(Mapx<String, String> params) {
		String operFlag = params.getString("operFlag");
		// 用户所属机构
		String branchInnercode = User.getBranchInnerCode();
		
		String checkedBranchInnercode = "";
		String checkedParentBranchInnercode = "";
		// 修改
		if ("edit".equalsIgnoreCase(operFlag)) {
			String id = params.getString("ID");
			LineTeamRateSchema lineTeamRateSchema = new LineTeamRateSchema();
			lineTeamRateSchema.setid(id);
			if (lineTeamRateSchema.fill()) {
				checkedBranchInnercode = lineTeamRateSchema.getbranchInnercode();
				params.put("branchInnercode", checkedBranchInnercode);
				params.put("riskType", lineTeamRateSchema.getriskType());
				params.put("companyCode", lineTeamRateSchema.getcompanyCode());
				params.put("rate", lineTeamRateSchema.getrate());
				checkedParentBranchInnercode = new QueryBuilder("select ParentInnerCode from zdbranch where BranchInnerCode=?", checkedBranchInnercode).executeString();
			}
		}
		
		String lineBranchInnerCode =  new QueryBuilder("select Memo from zdcode "
				+ "where CodeType = 'BranchCode' and CodeValue = 'LineTeam'").executeString();
		//所在地区
		StringBuffer provStr = new StringBuffer();
		StringBuffer cityStr = new StringBuffer();
		if(branchInnercode.startsWith(lineBranchInnerCode) && !branchInnercode.equals(lineBranchInnerCode)) {
			QueryBuilder qb = new QueryBuilder(
					"select a.`Name`,a.BranchInnerCode,a.TreeLevel,b.`Name` as ParentName,b.BranchInnerCode as parentBranchInnerCode "
					+ "from zdbranch a left outer join zdbranch b on a.ParentInnerCode = b.BranchInnerCode "
					+ "where a.BranchInnerCode = ?",User.getBranchInnerCode());
			DataTable dt = qb.executeDataTable();
			if(dt.getRowCount() > 0){
				DataRow dr = dt.get(0);
				int treeLevel = Integer.parseInt(dr.getString(2));
				if(treeLevel == 3){
					provStr.append("<option value='");
					provStr.append(dr.getString(1));
					provStr.append("'>");
					provStr.append(dr.getString(0));
					provStr.append("</option>");
					try {
						List<Map<String,Object>> citys = new GetDBdata().queryObj("select Name,BranchInnerCode from zdbranch "
								+ "where ParentInnerCode = ?",new String[]{dr.getString(1)});
						for(Map<String,Object> city : citys){
							cityStr.append("<option value='");
							cityStr.append(city.get("BranchInnerCode")+"'");
							if (checkedBranchInnercode.equals(city.get("BranchInnerCode"))) {
								cityStr.append(" selected='selected' ");
							}
							cityStr.append(">");
							cityStr.append(city.get("Name"));
							cityStr.append("</option>");
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}else if(treeLevel == 4){
					provStr.append("<option value='");
					provStr.append(dr.getString(4));
					provStr.append("'>");
					provStr.append(dr.getString(3));
					provStr.append("</option>");
					cityStr.append("<option value='");
					cityStr.append(dr.getString(1));
					cityStr.append("'>");
					cityStr.append(dr.getString(0));
					cityStr.append("</option>");
				}
			}
		}else{
			try {
				List<Map<String,Object>> provs = new GetDBdata().queryObj("select Name,BranchInnerCode from zdbranch "
						+ "where TreeLevel = 3 and ParentInnerCode = ?",new String[]{lineBranchInnerCode});
				provStr.append("<option value=''></option>");
				for(Map<String,Object> prov : provs){
					provStr.append("<option value='");
					provStr.append(prov.get("BranchInnerCode")+"'");
					if (checkedParentBranchInnercode.equals(prov.get("BranchInnerCode"))) {
						provStr.append(" selected='selected' ");
						try {
							List<Map<String,Object>> citys = new GetDBdata().queryObj("select Name,BranchInnerCode from zdbranch "
									+ "where ParentInnerCode = ?",new String[]{checkedParentBranchInnercode});
							for(Map<String,Object> city : citys){
								cityStr.append("<option value='");
								cityStr.append(city.get("BranchInnerCode")+"'");
								if (checkedBranchInnercode.equals(city.get("BranchInnerCode"))) {
									cityStr.append(" selected='selected' ");
								}
								cityStr.append(">");
								cityStr.append(city.get("Name"));
								cityStr.append("</option>");
							}
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					}
					provStr.append(">");
					provStr.append(prov.get("Name"));
					provStr.append("</option>");
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		params.put("provs", provStr.toString());
		params.put("citys", cityStr.toString());
		// 保险公司
		params.put("Supplier", HtmlUtil.codeToOptions("LineTeam.Supplier", true));
		// 险种
		params.put("SubRiskType", HtmlUtil.codeToOptions("LineTeam.SubRiskType", true));
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		
		String branchInnerCode = dga.getParam("branchInnerCode");
		String whereSql = "";
		// 当前用户的下级机构数据全部可见
		if (StringUtil.isNotEmpty(branchInnerCode) && !branchInnerCode.equalsIgnoreCase(User.getBranchInnerCode())) {
			whereSql = " and a.userBranchInnerCode like '"+branchInnerCode+"%' ";
		} else {
			branchInnerCode = User.getBranchInnerCode();
			whereSql = " and ((a.userBranchInnerCode like '"+branchInnerCode+"%' and a.userBranchInnerCode != '"+branchInnerCode+"') or a.createUser = '"+User.getUserName()+"' ) ";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.id, a.companyCode, a.branchInnercode, a.rate, a.createUser, a.createDate, a.modifyUser, a.modifyDate, ");
		sb.append("(select RealName from zduser where UserName=a.createUser) createUserName,");
		sb.append("if (a.modifyUser is not null and a.modifyUser !='',(select RealName from zduser where UserName=a.modifyUser),'') modifyUserName,");
		sb.append("(SELECT CodeName FROM zdcode WHERE CodeType='LineTeam.SubRiskType' and ParentCode='LineTeam.SubRiskType' and CodeValue=riskType) riskTypeName,");
		sb.append("(SELECT CodeName FROM zdcode WHERE CodeType='LineTeam.Supplier' and ParentCode='LineTeam.Supplier' and CodeValue=companyCode) companyName,");
		sb.append("b. NAME branchName,if (b.TreeLevel = 4, (select NAME from zdbranch where branchInnercode=b.ParentInnerCode), '') parentBranchName ");
		sb.append("FROM LineTeamRate a, zdbranch b where a.branchInnercode=b.branchInnercode "+whereSql);
		sb.append(" order by id desc ");
		QueryBuilder qb = new QueryBuilder(sb.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			int len = dt.getRowCount();
			int i = 0;
			for (; i < len; i++) {
				if (StringUtil.isEmpty(dt.getString(i, "createUserName"))) {
					dt.set(i, "createUserName", dt.getString(i, "createUser"));
				}
				if (StringUtil.isEmpty(dt.getString(i, "modifyUserName"))) {
					dt.set(i, "modifyUserName", dt.getString(i, "modifyUser"));
				}
				if (StringUtil.isNotEmpty(dt.getString(i, "parentBranchName"))) {
					dt.set(i, "branchName", dt.getString(i, "parentBranchName") + "-" + dt.getString(i, "branchName"));
				}
			}
		}
		
		dga.bindData(dt);
	}
	
	public void delete() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		LineTeamRateSchema schema = new LineTeamRateSchema();
		LineTeamRateSet set = schema.query(new QueryBuilder("where id in ('"+ids.replace(",", "','")+"')"));
		Transaction trans = new Transaction();
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功!");
		} else {
			Response.setLogInfo(0, "删除失败!");
		}
	}
	
	public void save() {
		String operFlag = Request.getString("operFlag");
		LineTeamRateSchema schema = new LineTeamRateSchema();
		Transaction trans = new Transaction();
		int tranOper = Transaction.INSERT;
		String userName = User.getUserName();
		Date operDate = new Date();
		String newBranchInnercode = Request.getString("branchInnercode");
		String newRiskType = Request.getString("riskType");
		String newCompanyCode = Request.getString("companyCode");
		String newRate = Request.getString("rate");
		if (StringUtil.isEmpty(newBranchInnercode)) {
			Response.setStatus(0);
			Response.setMessage("请选择机构！");
			return;
		}
		if (StringUtil.isEmpty(newRiskType)) {
			Response.setStatus(0);
			Response.setMessage("请选择险种！");
			return;
		}
		if (StringUtil.isEmpty(newCompanyCode)) {
			Response.setStatus(0);
			Response.setMessage("请选择保险公司！");
			return;
		}
		if (StringUtil.isEmpty(newRate)) {
			Response.setStatus(0);
			Response.setMessage("请填写结算费率！");
			return;
		} else if (!NumberUtil.isNumber(newRate)) {
			Response.setStatus(0);
			Response.setMessage("请填写正确的结算费率0-1之间！");
			return;
		} else {
			double rate = Double.valueOf(newRate);
			if (rate <=0 || rate >= 1) {
				Response.setStatus(0);
				Response.setMessage("请填写正确的结算费率0-1之间！");
				return;
			}
		}
		
		// 用机构、险种、保险公司查询录入的结算费率的id
		QueryBuilder qb = new QueryBuilder("select id from LineTeamRate where branchInnercode = ? and riskType = ? and companyCode = ?");
		qb.add(newBranchInnercode);
		qb.add(newRiskType);
		qb.add(newCompanyCode);
		String checkId = qb.executeString();
		
		String logMessage = "用户："+userName;
		if ("edit".equalsIgnoreCase(operFlag)) {
			tranOper = Transaction.UPDATE;
			String id = Request.getString("ID");
			if (StringUtil.isEmpty(id)) {
				Response.setStatus(0);
				Response.setMessage("数据ID不能为空！");
				return;
			}
			schema.setid(id);
			if (!schema.fill()) {
				Response.setStatus(0);
				Response.setMessage("未找到该结算费率数据，无法修改！");
				return;
			}
			// 校验修改的机构、险种、保险公司是否已录入结算费率
			if (StringUtil.isNotEmpty(checkId) && !id.equalsIgnoreCase(checkId)) {
				Response.setStatus(0);
				Response.setMessage("修改失败，您修改的机构、险种、保险公司已存在结算费率！");
				return;
			}
			if (!newBranchInnercode.equalsIgnoreCase(schema.getbranchInnercode())) {
				logMessage += (",机构修改："+schema.getbranchInnercode()+"->"+newBranchInnercode);
				schema.setbranchInnercode(newBranchInnercode);
			}
			if (!newRiskType.equalsIgnoreCase(schema.getriskType())) {
				logMessage += (",险种修改："+schema.getriskType()+"->"+newRiskType);
				schema.setriskType(newRiskType);
			}
			if (!newCompanyCode.equalsIgnoreCase(schema.getcompanyCode())) {
				logMessage += (",保险公司修改："+schema.getcompanyCode()+"->"+newCompanyCode);
				schema.setcompanyCode(newCompanyCode);
			}
			if (!newRate.equalsIgnoreCase(schema.getrate())) {
				logMessage += (",保险公司修改："+schema.getrate()+"->"+newRate);
				schema.setrate(newRate);
			}
			schema.setmodifyDate(operDate);
			schema.setmodifyUser(userName);
		} else {
			// 校验机构、险种、保险公司是否已录入结算费率
			if (StringUtil.isNotEmpty(checkId)) {
				Response.setStatus(0);
				Response.setMessage("保存失败，您录入的机构、险种、保险公司已存在结算费率！");
				return;
			}
			schema.setuserBranchInnercode(User.getBranchInnerCode());
			schema.setValue(Request);
			schema.setid(String.valueOf(NoUtil.getMaxID("LineTeamRateID")));
			schema.setcreateDate(operDate);
			schema.setcreateUser(userName);
		}
		
		trans.add(schema, tranOper);
		
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("保存数据成功！");
			if ("edit".equalsIgnoreCase(operFlag)) {
				try {
					UserLog.log("LineTeam", "rateModify", logMessage, Request.getClientIP());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			
		} else {
			Response.setStatus(0);
			Response.setMessage("保存数据失败！");
		}
	}
}

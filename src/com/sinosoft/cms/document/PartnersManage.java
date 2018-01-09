package com.sinosoft.cms.document;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.PartnersManageSchema;

/**
 * @ClassName: PartnersManage 
 * @Description: TODO(合作商管理) 
 * @author CongZN 
 * @date 2015-1-13 下午04:36:24 
 *
 * <p> 修改历史</p>
 * <p> 序号 日期 修改人 修改原因</p>
 */
public class PartnersManage extends Page {

	/**
	 * @Title: initAddDialog
	 * @Description: TODO(合作商管理-ADD 初始化选项).
	 * @return Mapx 返回类型
	 * @author CongZN.
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initAddDialog(Mapx params) {
		DataTable dt = new QueryBuilder(
				"select CodeName,CodeValue from zdcode where codeType = 'MailTemplate' and prop1 ='Y' ")
				.executeDataTable();
		params.put("MailConfigRiskType", HtmlUtil.codeToOptions(
				"MailConfigRiskType"));
		params.put("MailTemplate", HtmlUtil.dataTableToOptions(dt, true));
		return params;
	}

	/**
	 * @Title: initAddDialogEditor.
	 * @Description: TODO(合作商管理-Editor 初始化选项).
	 * @return Mapx 返回类型.
	 * @author CongZN.
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initDialogEditor(Mapx params) {
		String partnersCode = params.getString("PartnersCode");
		PartnersManageSchema pms = new PartnersManageSchema();
		pms.setPartnersCode(partnersCode);
		if (pms.fill()) {
			params.put("PartnersCode", pms.getPartnersCode());
			params.put("PartnersName", pms.getPartnersName());
			params.put("CookieTime", pms.getCookieTime());
			params.put("SendUrl",  pms.getSendUrl());
			if ("Y".equals(pms.getState())) {
				params.put("StateChecked", "checked");
			} else {
				params.put("StateChecked_1", "checked");
			}
			
			if ("Y".equals(pms.getSendState())) {
				params.put("SendStateChecked", "checked");
			} else {
				params.put("SendStateChecked_1", "checked");
			}
		}
		return params;
	}

	/**
	 * @Title: savePartnersManage.
	 * @Description: TODO(合作商管理-新增).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void savePartnersManage() {
		String partnersCode = $V("PartnersCode");
		String partnersName = $V("PartnersName");
		String cookieTime = $V("CookieTime");
		String sendUrl = $V("SendUrl");
		String sendState = $V("SendState");
		String state = $V("State");

		try {
			Transaction ts = new Transaction();
			PartnersManageSchema pms = new PartnersManageSchema();
			pms.setPartnersCode(partnersCode);
			
			if(pms.fill()){
				Response.setMessage("合作商编码已存在!");
			}
			
			pms.setPartnersName(partnersName);
			pms.setCookieTime(cookieTime);
			pms.setSendUrl(sendUrl);
			pms.setState(state);
			pms.setSendState(sendState);
			pms.setCreateDate(PubFun.getCurrentDate());
			ts.add(pms, Transaction.INSERT);
			
			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("保存失败! error:提交事务失败!");
			}

		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("保存失败! error:" + e.getMessage());
			logger.error("savePartnersManage 方法异常! error:" + e.getMessage(), e);
		}

	}

	/**
	 * @Title: updateMailConfig.
	 * @Description: TODO(合作商管理-编辑).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void updatePartnersManage() {
		String partnersCode = $V("PartnersCode");
		String partnersName = $V("PartnersName");
		String cookieTime = $V("CookieTime");
		String sendUrl = $V("SendUrl");
		String state = $V("State");
		String sendState = $V("SendState");

		try {
			Transaction ts = new Transaction();

			PartnersManageSchema pms = new PartnersManageSchema();
			pms.setPartnersCode(partnersCode);
			if(pms.fill()){
				pms.setPartnersName(partnersName);
				pms.setCookieTime(cookieTime);
				pms.setSendUrl(sendUrl);
				pms.setState(state);
				pms.setSendState(sendState);
			}

			ts.add(pms, Transaction.UPDATE);

			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("修改成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("修改失败! error:提交事务失败!");
			}

		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("修改失败! error:" + e.getMessage());
			logger.error("updatePartnersManage 方法异常! error:" + e.getMessage(), e);
		}

	}

	/**
	 * @Title: deletePartnersManage.
	 * @Description: TODO(合作商管理-删除).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void deletePartnersManage() {
		String partnersCode = $V("PartnersCode");
		try {
			
			Transaction ts = new Transaction();
			String[] partnersCodeArr = partnersCode.split(",");
			
			for(int i = 0; i < partnersCodeArr.length; i++){
				partnersCode = partnersCodeArr[i];
				PartnersManageSchema psm = new PartnersManageSchema();
				psm.setPartnersCode(partnersCode);
				ts.add(psm, Transaction.DELETE);
			}
			
			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("删除成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("删除失败! error:提交事务失败!");
			}
		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("删除失败! error:" + e.getMessage());
			logger.error("deletePartnersManage 方法异常! error:" + e.getMessage(), e);
		}

	}

	/**
	 * @Title: queryPartnersManage.
	 * @Description: TODO(合作商管理-查询).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void queryPartnersManage(DataGridAction dga) {

		String PartnersName = dga.getParams().getString("PartnersName");

		StringBuffer query_Sql = new StringBuffer("");
		query_Sql
				.append("select * from PartnersManage where 1=1 ");

		if (StringUtil.isNotEmpty(PartnersName)) {
			query_Sql.append(" and PartnersName like '%" + PartnersName.trim() + "%'");
		}

		query_Sql.append(" order by createDate desc ");
		QueryBuilder qb = new QueryBuilder(query_Sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.decodeColumn("State", HtmlUtil.codeToMapx("YesOrNo"));
		dga.setTotal(qb);
		dga.bindData(dt);
	}


	/**
	 * @Title: CombinationConditions.
	 * @Description: TODO(组合条件).
	 * @return String    返回类型.
	 * @author CongZN.
	 */
	public static String CombinationConditions(String[] StrArray) {
		StringBuffer sb = new StringBuffer("");
		if (StrArray.length > 0) {
			for (int i = 0; i < StrArray.length; i++) {
				sb.append("'"+StrArray[i] + "',");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
}

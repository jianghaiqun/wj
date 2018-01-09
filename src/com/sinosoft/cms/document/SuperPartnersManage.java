package com.sinosoft.cms.document;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.PartnerInfoSchema;

/**
 * @ClassName: SuperPartnersManage 
 * @Description: TODO(超级伙伴管理) 
 * @author xialianfu 
 * @date 2015-1-13 下午04:36:24 
 *
 * <p> 修改历史</p>
 * <p> 序号 日期 修改人 修改原因</p>
 */
public class SuperPartnersManage extends Page {

	/**
	 * @Title: initAddDialog
	 * @Description: TODO(超级伙伴管理-ADD 初始化选项).
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
	 * @Description: TODO(超级伙伴管理-Editor 初始化选项).
	 * @return Mapx 返回类型.
	 * @author CongZN.
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initDialogEditor(Mapx params) {
		String id = params.getString("id");
		PartnerInfoSchema pms = new PartnerInfoSchema();
		pms.setid(id);
		if (pms.fill()) {
			params.put("id", pms.getid());
			params.put("partnerId", pms.getpartnerId());
			params.put("partnerName", pms.getpartnerName());
			if ("0".equals(pms.getpartnerStatus())) {
				params.put("partnerStatusChecked", "checked");
			} else {
				params.put("partnerStatusChecked_1", "checked");
			}
			
			if ("0".equals(pms.getpayType())) {
				params.put("payTypeChecked", "checked");
			} else {
				params.put("payTypeChecked_1", "checked");
			}
			params.put("partnerKey", pms.getpartnerKey());
			params.put("telphone", pms.gettelphone());
			params.put("channelSn", pms.getchannelSn());
			params.put("returnUrl", pms.getreturnUrl());
			params.put("returnErrorUrl", pms.getreturnErrorUrl());
			params.put("bgReturnUrl", pms.getbgReturnUrl());
		}
		return params;
	}

	/**
	 * @Title: savePartnersManage.
	 * @Description: TODO(超级伙伴管理-新增).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void savePartnersManage() {
		String partnersCode = $V("partnerId");
		String partnersName = $V("partnerName");
		String partnerKey = $V("partnerKey");
		String partnerStatus = $V("partnerStatus"); 
		String telphone = $V("telphone");
		String payType = $V("payType");
		String channelSn = $V("channelSn");
		String returnUrl = $V("returnUrl");
		String returnErrorUrl = $V("returnErrorUrl");
		String bgReturnUrl = $V("bgReturnUrl");

		try {
			Transaction ts = new Transaction();
			PartnerInfoSchema pms = new PartnerInfoSchema();
			//验证合作商编号，是否存在
			String strParID = this.queryPartnersInfoByPartnerId(partnersCode);
			
			//验证渠道编号，是否存在
			String strChanneSn = this.queryPartnersInfoByChannelSn(channelSn);
			if(StringUtil.isNotEmpty(strParID)){
				Response.setMessage("合作商编码已存在!");
			}else if(StringUtil.isNotEmpty(strChanneSn)){
				Response.setMessage("渠道编号已存在!");
			}else{
				pms.setid(NoUtil.getMaxNo("PartnerInfoId", 10));
				pms.setpartnerId(partnersCode);
				pms.setpartnerName(partnersName); 
				pms.setpartnerKey(partnerKey);
				pms.setpartnerStatus(partnerStatus);
				pms.settelphone(telphone);
				pms.setpayType(payType);
				pms.setchannelSn(channelSn);
				pms.setreturnUrl(returnUrl);
				pms.setreturnErrorUrl(returnErrorUrl);
				pms.setbgReturnUrl(bgReturnUrl);
				pms.setcreateTime(PubFun.StringToDate(PubFun.getCurrent(),"yyyy-MM-dd HH:mm:ss"));
				pms.setoperUserId(User.getUserName());
				ts.add(pms, Transaction.INSERT);
				
				if (ts.commit()) {
					Response.setStatus(1);
					Response.setMessage("保存成功!");
				} else {
					Response.setStatus(2);
					Response.setMessage("保存失败! error:提交事务失败!");
				}
			}

		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("保存失败! error:" + e.getMessage());
			logger.error("savePartnersManage 方法异常! error:" + e.getMessage(), e);
		}

	}

	/**
	 * @Title: updateMailConfig.
	 * @Description: TODO(超级伙伴管理-编辑).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void updatePartnersManage() {
		String id = $V("id");
		String partnersCode = $V("partnerId");
		String partnersName = $V("partnerName");
		String partnerKey = $V("partnerKey");
		String partnerStatus = $V("partnerStatus"); 
		String telphone = $V("telphone");
		String payType = $V("payType");
		String channelSn = $V("channelSn");
		String oldchannelSn = $V("oldchannelSn");
		String returnUrl = $V("returnUrl");
		String returnErrorUrl = $V("returnErrorUrl");
		String bgReturnUrl = $V("bgReturnUrl");

		try {
			//验证渠道编号，是否存在
			String strChanneSn = "";
			if(oldchannelSn.equals(channelSn)){//数据库值与页面新值进行比较，如果相同说明，没用改变；如果不相同，说明新值已改变
				strChanneSn = "";
			}else{
				//验证渠道编号，是否存在
				strChanneSn = this.queryPartnersInfoByChannelSn(channelSn);
			}
			if(StringUtil.isNotEmpty(strChanneSn)){
				Response.setMessage("渠道编号已存在!");
			}else{
				Transaction ts = new Transaction();

				PartnerInfoSchema pms = new PartnerInfoSchema();
				pms.setid(id);
				if(pms.fill()){
					pms.setpartnerId(partnersCode);
					pms.setpartnerName(partnersName); 
					pms.setpartnerKey(partnerKey);
					pms.setpartnerStatus(partnerStatus);
					pms.settelphone(telphone);
					pms.setpayType(payType);
					pms.setchannelSn(channelSn);
					pms.setreturnUrl(returnUrl);
					pms.setreturnErrorUrl(returnErrorUrl);
					pms.setbgReturnUrl(bgReturnUrl);
					pms.setupdateTime(PubFun.StringToDate(PubFun.getCurrent(),"yyyy-MM-dd HH:mm:ss"));
					pms.setoperUserId(User.getUserName());
				}

				ts.add(pms, Transaction.UPDATE);

				if (ts.commit()) {
					Response.setStatus(1);
					Response.setMessage("修改成功!");
				} else {
					Response.setStatus(2);
					Response.setMessage("修改失败! error:提交事务失败!");
				}

			}
			
		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("修改失败! error:" + e.getMessage());
			logger.error("updatePartnersManage 方法异常! error:" + e.getMessage(), e);
		}

	}

	/**
	 * @Title: deletePartnersManage.
	 * @Description: TODO(超级伙伴管理-删除).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void deletePartnersManage() {
		String id = $V("id");
		try {
			
			Transaction ts = new Transaction();
			String[] idArr = id.split(",");
			
			for(int i = 0; i < idArr.length; i++){
				id = idArr[i];
				PartnerInfoSchema psm = new PartnerInfoSchema();
				psm.setid(id);
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
	 * @Description: TODO(超级伙伴管理-查询).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void queryPartnersManage(DataGridAction dga) {

		String partnerName = dga.getParams().getString("partnerName");

		StringBuffer query_Sql = new StringBuffer("");
		
		query_Sql.append("SELECT ");
		query_Sql.append("p.id,p.partnerId,p.partnerName,p.partnerKey,p.telphone,p.bgReturnUrl,p.channelSn, ");
		query_Sql.append("p.createTime,p.operUserId,p.returnErrorUrl,p.returnUrl,p.updateTime, ");
		query_Sql.append("p.partnerStatus,(case when p.partnerStatus='0' then '启用' when p.partnerStatus='1' then '停用' else '无'end) as partnerStatusName, ");
		query_Sql.append("p.payType,(case when p.payType='0' then '直付' when p.payType='1' then '预付' else '无'end) as payTypeName ");
		query_Sql.append("FROM  partnerinfo p  ");
		query_Sql.append(" where 1=1 ");
		if (StringUtil.isNotEmpty(partnerName)) {
			query_Sql.append(" and partnerName like '%" + partnerName.trim() + "%'");
		}

		query_Sql.append(" order by createTime desc ");
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
	
	/**
	 * 
	 * queryPartnersInfoByPartnerId:(通过合作商编号，查询合作商信息是否存在). <br/> 
	 *
	 * @author xialianuf
	 * @param strPartnerId
	 * @return
	 */
	public String queryPartnersInfoByPartnerId(String strPartnerId) {


		StringBuffer query_Sql = new StringBuffer("");
		query_Sql.append("select * from partnerinfo where 1=1 ");

		if (StringUtil.isNotEmpty(strPartnerId)) {
			query_Sql.append(" and partnerId = '"+strPartnerId+"'");
		}
 
		QueryBuilder qb = new QueryBuilder(query_Sql.toString());
		String dt = qb.executeString(); 
		return dt;
	}
	
	/**
	 * 
	 * queryPartnersInfoByChannelSn:(通过渠道编号，查询合作商信息是否存在). <br/> 
	 *
	 * @author xialianuf
	 * @param strChannelSn
	 * @return
	 */
	public String queryPartnersInfoByChannelSn(String strChannelSn) {


		StringBuffer query_Sql = new StringBuffer("");
		query_Sql.append("select * from partnerinfo where 1=1 ");

		if (StringUtil.isNotEmpty(strChannelSn)) {
			query_Sql.append(" and channelSn = '"+strChannelSn+"'");
		}
 
		QueryBuilder qb = new QueryBuilder(query_Sql.toString());
		String dt = qb.executeString(); 
		return dt;
	}
}

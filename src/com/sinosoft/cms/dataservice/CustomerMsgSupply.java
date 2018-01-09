package com.sinosoft.cms.dataservice;

import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.jdt.ServiceClient;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.CustomerMsgSupplySchema;
import com.sinosoft.schema.CustomerMsgSupplySet;

public class CustomerMsgSupply extends Page{

	public static Mapx initDialog(Mapx params) {
		// 证件类型
		Mapx certMap =  new QueryBuilder("select codename,codevalue from `dictionary`  	where   productid='224801001'  and codetype='certificate' ").executeDataTable().toMapx("codevalue", "codename");
		params.put("identityTypeList",  HtmlUtil.mapxToOptions(certMap));
		//性别 
		Mapx sexMap =  new QueryBuilder("SELECT codename,codevalue FROM `dictionary`  	WHERE  insuranceCode='2248'   AND codetype='Sex'  ").executeDataTable().toMapx("codevalue", "codename");
		params.put("sexList",  HtmlUtil.mapxToOptions(sexMap));
		// 银行
		Mapx bankMap =  new QueryBuilder("SELECT codename,codevalue FROM `dictionary`  	WHERE  insuranceCode='2248'   AND codetype='Bank'  ").executeDataTable().toMapx("codevalue", "codename");
		params.put("bankList",  HtmlUtil.mapxToOptions(bankMap));
		//与投保人关系
		Mapx relationMap =  new QueryBuilder("SELECT codename,codevalue FROM `dictionary`  	WHERE  productid='224801001'   AND codetype='Relationship'  ").executeDataTable().toMapx("codevalue", "codename");
		params.put("relationList",  HtmlUtil.mapxToOptions(relationMap));
		
		String id = params.getString("id");
		if (StringUtil.isNotEmpty(id)) {
			initDataById(id, params);
		}
		// 渠道
	//	params.put("tbCustomerChannel", HtmlUtil.codeToOptions("tbCustomerChannel"));
		
		return params;
	}
	private static void initDataById(String id, Mapx params){
		CustomerMsgSupplySchema customerMsg = new CustomerMsgSupplySchema();
		customerMsg.setID(id);
		CustomerMsgSupplySet cusMsgSet = customerMsg.query(new QueryBuilder("where id ='" + id + "'"));
		customerMsg = cusMsgSet.get(0);
		params.putAll(customerMsg.toMapx());
	}
	public void save() {
		try {
			String id = $V("id");
			CustomerMsgSupplySchema customerMsg = new CustomerMsgSupplySchema();
			if(StringUtil.isNotEmpty(id)){//修改是保留创建时间
				customerMsg.setID(id);
				customerMsg.fill();
			}
			customerMsg.setValue(Request);
			//投被保人关系为本人时替换被保人信息
			replaceSelfMsg(customerMsg);
			/*String errorMsg = checkBeforeSave(id, customerMsg);
			if(StringUtil.isNotEmpty(errorMsg) && errorMsg.indexOf("|")!=-1){
				Response.setLogInfo(0, errorMsg.split("\\|")[1]);
				return;
			}*/
			
			
			Transaction trans = new Transaction();
			if(StringUtil.isEmpty(id)){
				id = String.valueOf(NoUtil.getMaxID("CustomerMsgSupply"));
				customerMsg.setID(id);
				customerMsg.setcreateDate(new Date());
				trans.add(customerMsg, Transaction.INSERT);
			}else{
				trans.add(customerMsg, Transaction.UPDATE);
			}
			
			
			customerMsg.setmodifyDate(new Date());
			customerMsg.setbankUserName($V("applicantName"));
			if (trans.commit()) {
				//调用jdt服务、设置返回信息
				String[] result = (String[]) ServiceClient.execute(new ObjectMapper().writeValueAsString(customerMsg), null, null, "2248-11");
				if("00".equals(result[0])){
					String sql ="UPDATE CustomerMsgSupply SET msgValid='Y' WHERE ID='"+id+"'";
					QueryBuilder qbupdate = new QueryBuilder(sql);
					qbupdate.executeNoQuery();
					Response.setLogInfo(1, "调用百年接口成功!", id);
				}else if("02".equals(result[0])){
					Response.setLogInfo(0, "调用百年接口失败："+result[1], id);
				}else{
					Response.setLogInfo(0, "系统内部错误："+result[1], id);
				}
				
			} else {
				Response.setLogInfo(0, "保存失败!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Response.setLogInfo(0, "保存失败!");
		}

	}
	private String checkBeforeSave(String id, CustomerMsgSupplySchema customerMsg){
		QueryBuilder qb = new QueryBuilder("SELECT COUNT(id) FROM CustomerMsgSupply WHERE 1=1");
		qb.append(" and applicantName=?", customerMsg.getapplicantName())
		.append(" AND applicantIdentityId=?", customerMsg.getapplicantIdentityId())
		.append(" AND recognizeeName=?", customerMsg.getrecognizeeName())
		.append(" AND recognizeeIdentityId=?", customerMsg.getrecognizeeIdentityId())
		.append(" AND msgValid=?", "Y")
		.append(" AND id!=?", id);
		
		int count = qb.executeInt();
		if(count >0){
			return "fail|投、被保险人数据已经补全，不可以重复录入！";
		}
		return null;
	}
	private void replaceSelfMsg(CustomerMsgSupplySchema customerMsg){
		//投被保人关系为本人时替换被保人信息
		if("00".equals(customerMsg.getinsuredRelation())){
			customerMsg.setrecognizeeName(customerMsg.getapplicantName());
			customerMsg.setrecognizeeSex(customerMsg.getapplicantSex());
			customerMsg.setrecognizeeIdentityType(customerMsg.getapplicantIdentityType());
			customerMsg.setrecognizeeIdentityId(customerMsg.getapplicantIdentityId());
			customerMsg.setrecognizeeStartID(customerMsg.getapplicantStartID());
			customerMsg.setrecognizeeEndID(customerMsg.getapplicantEndID());
			customerMsg.setrecognizeeMail(customerMsg.getapplicantMail());
		}
	}
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"SELECT id,applicantName,applicantIdentityId,recognizeeName,recognizeeIdentityId,recognizeeMobile"
				+ ",(CASE tbCustomerChannel WHEN '01' THEN '百年淘宝店' ELSE '开心保淘宝店' END)tbCustomerChannel"
				+ ",(CASE msgValid WHEN 'Y' THEN '完整' ELSE '不完整' END)msgValid FROM CustomerMsgSupply WHERE 1=1");
		String applicantName = dga.getParams().getString("applicantName");
		if (StringUtil.isNotEmpty(applicantName)) {
			qb.append(" AND applicantName=?", applicantName.trim());
		}
		String applicantIdentityId = dga.getParams().getString("applicantIdentityId");
		if (StringUtil.isNotEmpty(applicantIdentityId)) {
			qb.append(" AND applicantIdentityId=?", applicantIdentityId.trim());
		}
		String recognizeeMobile = dga.getParams().getString("recognizeeMobile");
		if (StringUtil.isNotEmpty(recognizeeMobile)) {
			qb.append(" AND recognizeeMobile=?", recognizeeMobile.trim());
		}
		
		qb.append(" ORDER BY createDate desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
}

package com.sinosoft.platform;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import com.finance.util.JedisCommonUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZDCodeSchema;
import com.sinosoft.schema.ZDCodeSet;

/**
 */
public class Code extends Page {

	public static void dg1BindCode(DataGridAction dga) {
		// 特殊代码处理 如：地区代码 见菜单地区代码管理
		String SearchCodeType = dga.getParam("SearchCodeType");
		QueryBuilder qb = new QueryBuilder(
				"select CodeType,ParentCode,CodeValue,CodeName,CodeOrder,Memo,CodeType "
						+ "as CodeType_key,ParentCode as ParentCode_key,CodeValue as CodeValue_key from ZDCode where ParentCode ='System' ");
		qb.append(" and (prop4<>'S' or prop4 is null)");
		if (StringUtil.isNotEmpty(SearchCodeType)) {
			qb.append(" and (CodeType like ? ", "%" + SearchCodeType + "%");
			qb.append(" or CodeName like ? )", "%" + SearchCodeType + "%");
		}
		qb.append(" order by CodeType,ParentCode");
		dga.bindData(qb);
	}

	/**
	 * 查询达人录入统计的基础数据
	 * @param dga
	 */
	public static void dg2BindCode(DataGridAction dga) {
		String SearchCodeType = dga.getParam("SearchCodeType");
		QueryBuilder qb = new QueryBuilder(
				"select CodeType,CodeName,Memo from ZDCode where ParentCode ='System' and CodeType like 'Author.%' ");

		if (StringUtil.isNotEmpty(SearchCodeType)) {
			qb.append(" and (CodeType like ? ", "%" + SearchCodeType + "%");
			qb.append(" or CodeName like ? )", "%" + SearchCodeType + "%");
		}
		qb.append(" order by CodeType,ParentCode");
		dga.bindData(qb);
	}
	
	public static void dg1BindCodeList(DataGridAction dga) {
		// Type_key 主键隐藏列
		QueryBuilder qb = new QueryBuilder("select CodeType,ParentCode,CodeValue,CodeName,CodeOrder ,Memo,CodeType "
				+ "as CodeType_key,ParentCode as ParentCode_key,CodeValue as CodeValue_key "
				+ "from ZDCode where ParentCode =?", dga.getParam("CodeType"));
		qb.append(" order by CodeOrder,CodeType,ParentCode");
		dga.bindData(qb);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			QueryBuilder qb = new QueryBuilder(
					"update ZDCode set Codetype=?,ParentCode =?,CodeName=?,CodeValue=?,Memo=? where CodeType=? and ParentCode=? and CodeValue=?");
			qb.add(dt.getString(i, "CodeType"));
			qb.add(dt.getString(i, "ParentCode"));
			qb.add(dt.getString(i, "CodeName"));
			qb.add(dt.getString(i, "CodeValue"));
			qb.add(dt.getString(i, "Memo"));
			qb.add(dt.getString(i, "CodeType_Key"));
			qb.add(dt.getString(i, "ParentCode_Key"));
			qb.add(dt.getString(i, "CodeValue_Key"));
			trans.add(qb);
			// 如果CodeType更改了，就需要更新这个代码类别下所有的代码
			if ("System".equals(dt.getString(i, "ParentCode"))
					&& !dt.getString(i, "CodeType").equals(dt.getString(i, "CodeType_Key"))) {
				qb = new QueryBuilder("update ZDCode set Codetype=?,ParentCode =? where CodeType=? and ParentCode=?");
				qb.add(dt.getString(i, "CodeType"));
				qb.add(dt.getString(i, "CodeType"));
				qb.add(dt.getString(i, "CodeType_Key"));
				qb.add(dt.getString(i, "CodeType_Key"));
				trans.add(qb);
			}
		}
		if (trans.commit()) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				CacheManager.set("Code", dt.getString(i, "CodeType"), dt.getString(i, "CodeValue"), dt.getString(i,
						"CodeName"));
			}
			Response.setLogInfo(1, "修改成功!");
			//加redis缓存先删除后添加
			delZDCodeMapsFromRedis();
			setZDCodeMapIntoRedis();
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}

	public static Mapx init(Mapx params) {
		return params;
	}

	public static Mapx initList(Mapx params) {
		String codeType = params.getString("CodeType");
		ZDCodeSchema code = new ZDCodeSchema();
		code.setCodeType(codeType);
		code.setParentCode("System");
		code.setCodeValue("System");
		code.fill();
		return code.toMapx();
	}

	public void add() {
		ZDCodeSchema code = new ZDCodeSchema();
		code.setValue(Request);
		if (code.fill()) {
			Response.setLogInfo(0, "代码值" + code.getCodeValue() + "已经存在了!");
			return;
		}
		code.setCodeOrder(System.currentTimeMillis());
		code.setAddTime(new Date());
		code.setAddUser(User.getUserName());
		if (code.insert()) {
			CacheManager.set("Code", code.getCodeType(), code.getCodeValue(), code.getCodeName());
			Response.setLogInfo(1, "新建代码成功!");
			//加redis缓存先删除后添加
			delZDCodeMapsFromRedis();
			setZDCodeMapIntoRedis();
		} else {
			Response.setLogInfo(0, "新建代码失败!");
		}
	}

	public void del() {
		DataTable dt = (DataTable) Request.get("DT");
		ZDCodeSet set = new ZDCodeSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDCodeSchema code = new ZDCodeSchema();
			code.setValue(dt.getDataRow(i));
			code.fill();
			set.add(code);
			if ("System".equals(code.getParentCode())) {
				ZDCodeSchema childCode = new ZDCodeSchema();
				childCode.setParentCode(code.getCodeType());
				set.add(childCode.query());
			}
		}
		// 日志
		StringBuffer codeLog = new StringBuffer("删除代码:");
		if (set.size() > 0) {
			codeLog.append(set.get(0).getCodeName() + " 等");
		}
		if (set.deleteAndBackup()) {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCODE, codeLog + "成功", Request.getClientIP());
			for (int i = 0; i < set.size(); i++) {
				CacheManager.remove("Code", set.get(i).getCodeType(), set.get(i).getCodeValue());
				if ("System".equals(set.get(i).getParentCode())) {
					CacheManager.removeType("Code", set.get(i).getCodeType());
				}
			}
			Response.setLogInfo(1, "删除代码成功!");
			//加redis缓存先删除后添加
			delZDCodeMapsFromRedis();
			setZDCodeMapIntoRedis();
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCODE, codeLog + "失败", Request.getClientIP());
			Response.setLogInfo(0, "删除代码失败!");
		}
		
	}

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String parentCode = $V("ParentCode");
		if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
			return;
		}
		if (OrderUtil.updateOrder("ZDCode", "CodeOrder", type, target, orders, " ParentCode = '" + parentCode + "'")) {
			Response.setMessage("排序成功");
		} else {
			Response.setError("排序失败");
		}
	}
	
	/*
	 * 险种维护初始页面
	 */
	public static void dg1RiskTypeManage(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select a.CodeType,a.ParentCode,a.CodeValue,a.CodeName,a.Memo,a.CodeOrder,a.CodeType "
				+ "as CodeType_key,a.ParentCode as ParentCode_key,a.CodeValue as CodeValue_key,"
				+ "(select CodeName from ZDCode where ParentCode ='LineTeam.RiskType' and CodeValue=a.Memo) MemoName "
				+ "from ZDCode a where a.ParentCode ='LineTeam.SubRiskType' order by a.CodeOrder,a.CodeType,a.ParentCode");
		dga.bindData(qb);
	}
	
	public static Mapx initRiskTypeManageList(Mapx params) {
		ZDCodeSchema code = new ZDCodeSchema();
		code.setCodeType("LineTeam.SubRiskType");
		code.setParentCode("System");
		code.setCodeValue("System");
		code.fill();
		return code.toMapx();
	}
	/*
	 * 新增、修改页面险种下拉菜单
	 */
	public static Mapx<String, String> initByLineTeamRiskType(Mapx<String, String> params) {
		params.put("Memo", HtmlUtil.codeToOptions("LineTeam.RiskType",true));
		return params;
	}
	
	
	/*
	 * 险种维护修改
	 */
	public void dg1update(){
		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(
				"update ZDCode set CodeName=?,CodeValue=?,Memo=? "
				+ "where CodeType='LineTeam.SubRiskType' and ParentCode='LineTeam.SubRiskType' "
				+ "and CodeName=? and CodeValue=? and Memo=? ");
		qb.add(Request.getString("CodeName"));
		qb.add(Request.getString("CodeValue"));
		qb.add(Request.getString("Memo"));
		qb.add(Request.getString("CodeNameOld"));
		qb.add(Request.getString("CodeValueOld"));
		qb.add(Request.getString("MemoOld"));
		trans.add(qb);
		if (trans.commit()) {
			CacheManager.set("Code", "LineTeam.SubRiskType", Request.getString("CodeValue"), Request.getString("CodeName"));
			Response.setLogInfo(1, "修改成功!");
			//加redis缓存先删除后添加
			delZDCodeMapsFromRedis();
			setZDCodeMapIntoRedis();
		} else {
			ZDCodeSchema code = new ZDCodeSchema();
			code.setValue(Request);
			if (code.fill()) {
				Response.setLogInfo(0, "编码值" + code.getCodeValue() + "已经存在了!");
				return;
			}else{
			    Response.setLogInfo(0, "修改失败!");
			    }
		}
	
	}
	/*
	 * 删除险种数据
	 */
	public void Riskdel() {
		DataTable dt = (DataTable) Request.get("DT");
		QueryBuilder LineTeamPolicyInfoQb = new QueryBuilder("select 1 from LineTeamPolicyInfo where risktype =? ",dt.getDataRow(0).get("CodeValue"));
		DataTable LineTeamPolicyInfoDt = LineTeamPolicyInfoQb.executeDataTable();
		if (LineTeamPolicyInfoDt.getRowCount() > 0) {
			Response.setLogInfo(0, "删除险种失败,险种“"+dt.getDataRow(0).get("CodeValue")+"”存在业务数据不可以删除!");
			return;
		}
		
		Transaction trans = new Transaction();
		String LineTeamRateDeleteSQL = " DELETE FROM LineTeamRate WHERE riskType = ?";
		QueryBuilder LineTeamRatedel = new QueryBuilder(LineTeamRateDeleteSQL);
		LineTeamRatedel.add(dt.getDataRow(0).get("CodeValue"));
		trans.add(LineTeamRatedel);

		String ZdcodeDeleteSQL = " DELETE FROM ZDCode WHERE ParentCode ='LineTeam.SubRiskType' AND codevalue= ?";
		QueryBuilder Zdcodedel = new QueryBuilder(ZdcodeDeleteSQL);
		Zdcodedel.add(dt.getDataRow(0).get("CodeValue"));
		trans.add(Zdcodedel);

		if (trans.commit()) {
 
			Response.setLogInfo(1, "删除险种成功!");
			//加redis缓存先删除后添加
			delZDCodeMapsFromRedis();
			setZDCodeMapIntoRedis();
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCODE, "险种  "+dt.getDataRow(0).get("CodeValue")  + "  删除失败", Request.getClientIP());
			Response.setLogInfo(0, "删除险种失败!");
		}
		
	}
	
	
	public void Riskadd() {
		ZDCodeSchema code = new ZDCodeSchema();
		code.setValue(Request);
		if(StringUtil.isEmpty(Request.get("CodeValue").toString())
				|| StringUtil.isEmpty(Request.get("CodeName").toString())
				|| StringUtil.isEmpty(Request.get("Memo").toString())){
			Response.setLogInfo(0, "名称、编码不能为空且必须选择险种分类");
			return;
		}
		if (code.fill()) {
			Response.setLogInfo(0, "险种编码" + code.getCodeValue() + "已经存在了!");
			return;
		}
		code.setCodeOrder(System.currentTimeMillis());
		code.setAddTime(new Date());
		code.setAddUser(User.getUserName());
		if (code.insert()) {
			CacheManager.set("Code", code.getCodeType(), code.getCodeValue(), code.getCodeName());
			Response.setLogInfo(1, "新建险种成功!");
			//加redis缓存先删除后添加
			delZDCodeMapsFromRedis();
			setZDCodeMapIntoRedis();
		} else {
			Response.setLogInfo(0, "新建险种失败!");
		}
	}
	
	/*
	 * 保险公司维护初始页面
	 */
	public static void dg1CompanyManage(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select a.CodeType,a.ParentCode,a.CodeValue,a.CodeName "
				+ "from ZDCode a where a.ParentCode ='LineTeam.Supplier' order by a.CodeOrder,a.CodeType,a.ParentCode");
		dga.bindData(qb);
	}
	
	public static Mapx initCompanyManageList(Mapx params) {
		ZDCodeSchema code = new ZDCodeSchema();
		code.setCodeType("LineTeam.Supplier");
		code.setParentCode("System");
		code.setCodeValue("System");
		code.fill();
		return code.toMapx();
	}
	/*
	 * 新增、修改页面保险公司下拉菜单
	 */
	public static Mapx<String, String> initByLineTeamCompany(Mapx<String, String> params) {
		return params;
	}
	
	
	/*
	 * 保险公司维护修改
	 */
	public void dgCompanyUpdate(){
		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(
				"update ZDCode set CodeName=?,CodeValue=? "
				+ "where CodeType='LineTeam.Supplier' and ParentCode='LineTeam.Supplier' "
				+ "and CodeName=? and CodeValue=?");
		qb.add(Request.getString("CodeName"));
		qb.add(Request.getString("CodeValue"));
		qb.add(Request.getString("CodeNameOld"));
		qb.add(Request.getString("CodeValueOld"));
		trans.add(qb);
		if (trans.commit()) {
			CacheManager.set("Code", "LineTeam.Supplier", Request.getString("CodeValue"), Request.getString("CodeName"));
			Response.setLogInfo(1, "修改成功!");
			//加redis缓存先删除后添加
			delZDCodeMapsFromRedis();
			setZDCodeMapIntoRedis();
		} else {
			ZDCodeSchema code = new ZDCodeSchema();
			code.setValue(Request);
			if (code.fill()) {
				Response.setLogInfo(0, "编码值" + code.getCodeValue() + "已经存在了!");
				return;
			}else{
			    Response.setLogInfo(0, "修改失败!");
			}
		}
		
	}
	/*
	 * 删除保险公司数据
	 */
	public void companyDel() {
		DataTable dt = (DataTable) Request.get("DT");
		QueryBuilder LineTeamPolicyInfoQb = new QueryBuilder("select 1 from LineTeamPolicyInfo where companyCode =? ",dt.getDataRow(0).get("CodeValue"));
		DataTable LineTeamPolicyInfoDt = LineTeamPolicyInfoQb.executeDataTable();
		if (LineTeamPolicyInfoDt.getRowCount() > 0) {
			Response.setLogInfo(0, "删除保险公司失败,保险公司“"+dt.getDataRow(0).get("CodeValue")+"”存在业务数据不可以删除!");
			return;
		}
		
		Transaction trans = new Transaction();
		String LineTeamRateDeleteSQL = " DELETE FROM LineTeamRate WHERE companyCode = ?";
		QueryBuilder LineTeamRatedel = new QueryBuilder(LineTeamRateDeleteSQL);
		LineTeamRatedel.add(dt.getDataRow(0).get("CodeValue"));
		trans.add(LineTeamRatedel);

		String ZdcodeDeleteSQL = " DELETE FROM ZDCode WHERE ParentCode ='LineTeam.Supplier' AND codevalue= ?";
		QueryBuilder Zdcodedel = new QueryBuilder(ZdcodeDeleteSQL);
		Zdcodedel.add(dt.getDataRow(0).get("CodeValue"));
		trans.add(Zdcodedel);

		if (trans.commit()) {
 
			Response.setLogInfo(1, "删除保险公司成功!");
			//加redis缓存先删除后添加
			delZDCodeMapsFromRedis();
			setZDCodeMapIntoRedis();
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCODE, "保险公司  "+dt.getDataRow(0).get("CodeValue")  + "  删除失败", Request.getClientIP());
			Response.setLogInfo(0, "删除保险公司失败!");
		}
		
	}
	
	
	public void companyAdd() {
		ZDCodeSchema code = new ZDCodeSchema();
		code.setValue(Request);
		if(StringUtil.isEmpty(Request.get("CodeValue").toString())
				|| StringUtil.isEmpty(Request.get("CodeName").toString())){
			Response.setLogInfo(0, "名称、编码不能为空");
			return;
		}
		if (code.fill()) {
			Response.setLogInfo(0, "保险公司" + code.getCodeValue() + "已经存在了!");
			return;
		}
		code.setCodeOrder(System.currentTimeMillis());
		code.setAddTime(new Date());
		code.setAddUser(User.getUserName());
		if (code.insert()) {
			CacheManager.set("Code", code.getCodeType(), code.getCodeValue(), code.getCodeName());
			Response.setLogInfo(1, "新建保险公司成功!");
			//加redis缓存先删除后添加
			delZDCodeMapsFromRedis();
			setZDCodeMapIntoRedis();
		} else {
			Response.setLogInfo(0, "新建保险公司失败!");
		}
		
	}
	
	
	
	/**
	 * setZDCodeMapIntoRedis:(将zdcode信息从数据库加载到redis). <br/>
	 *
	 * @author miaozhiqiang
	 */
	

	public void setZDCodeMapIntoRedis(){
		HashMap<String,String> zdcodeVMap=new HashMap<String, String>();
		HashMap<String,String> zdcodeNMap=new HashMap<String, String>();
		String queryAllZDCodeFromDB = " select codeType,codeValue,codeName from zdcode where ParentCode!='System'  ORDER BY CodeType ";
		QueryBuilder zdcodeQB = new QueryBuilder(queryAllZDCodeFromDB);
        DataTable zdcodeDT = zdcodeQB.executeDataTable();
        
		if(zdcodeDT.getRowCount()>0){
		String key = String.valueOf(zdcodeDT.get(0).get("codeType")); 
		
		
		for(int i = 0;i<zdcodeDT.getRowCount();i++){
			try{
			if(StringUtil.isEmpty(String.valueOf(zdcodeDT.get(i).get("codeValue")))){
				key=String.valueOf(zdcodeDT.get(i).get("codeType"));
				continue; 
			}	
			
			//key发生变化将上一组zdcode数据放入redis
			 if(!key.equals(zdcodeDT.get(i).get("codeType"))){
				//set ZDCodeV into redis
				 JedisCommonUtil.setMap(0,"ZDCodeV_"+key, zdcodeVMap);
				 zdcodeVMap.clear();
				//set ZDCodeN into redis
				 JedisCommonUtil.setMap(0,"ZDCodeN_"+key, zdcodeNMap);
				 zdcodeNMap.clear();
			 }
			 
			 zdcodeVMap.put(String.valueOf(zdcodeDT.get(i).get("codeValue")),String.valueOf(zdcodeDT.get(i).get("codeName")));
			
			 zdcodeNMap.put(String.valueOf(zdcodeDT.get(i).get("codeName")),String.valueOf(zdcodeDT.get(i).get("codeValue")));
			 
			 //最后一次循环把最后一组zdcode放入redis
			 if(i==zdcodeDT.getRowCount()-1){
				//set ZDCodeV into redis
				 JedisCommonUtil.setMap(0,"ZDCodeV_"+key, zdcodeVMap);
				 zdcodeVMap.clear();
				//set ZDCodeN into redis
				 JedisCommonUtil.setMap(0,"ZDCodeN_"+key, zdcodeNMap);
				 zdcodeNMap.clear();
			 }
			 key=String.valueOf(zdcodeDT.get(i).get("codeType"));
			}catch(Exception e){
				continue;
			}
		}
	  }
	}
	
	/**
	 * delZDCodeMapsFromRedis:(删除zdcode全部缓存). <br/> 
	 *
	 * @author miaozhiqiang
	 */
	public void delZDCodeMapsFromRedis(){
		Set<String> redisKeys = JedisCommonUtil.findKeys(0,"ZDCode*");
		if(redisKeys.size()!=0){
			String[] keyArray =   (String[]) redisKeys.toArray(new String[redisKeys.size()]);
			JedisCommonUtil.remove(0,keyArray);
		}
	}
}

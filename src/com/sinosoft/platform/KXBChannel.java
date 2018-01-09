package com.sinosoft.platform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.controls.TreeItem;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;

public class KXBChannel extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select InnerChannelCode ID,ParentInnerChanelCode ParentID,CONCAT(ChannelCode,'-',ChannelName) Name,ChannelLevel as TreeLevel,"
					+"CASE IsCoupon when 'Y' THEN '是' WHEN 'N' THEN '否' ELSE '' END IsCoupon,CASE IsActivity when 'Y' THEN '是' WHEN 'N' THEN '否' ELSE '' END IsActivity from KxbChannelInfo order by CreateDate";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		dga.bindData(dt);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx dg2DataBind(Mapx params) {
		String sql = "select ChannelCode ID,InnerChannelCode,ParentInnerChanelCode ParentID,ChannelName Name,ChannelLevel as TreeLevel,Prop1,IsCoupon,IsActivity from KxbChannelInfo where InnerChannelCode = "+params.get("Id");
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		params.put("Code", dt.get(0, "ID"));
		params.put("Name", dt.get(0, "Name"));
		Map<String,String> isshow = new HashMap<String,String>();
		Map<String,String> iscoupon = new HashMap<String,String>();
		Map<String,String> isactivity = new HashMap<String,String>();
		isshow.put("Y", "显示");
		isshow.put("N", "隐藏");
		iscoupon.put("Y", "是");
		iscoupon.put("N", "否");
		isactivity.put("Y", "是");
		isactivity.put("N", "否");
		params.put("IsShow", HtmlUtil.mapxToOptions(isshow,dt.get(0, "Prop1")));
		params.put("IsCoupon", HtmlUtil.mapxToOptions(iscoupon,dt.get(0, "IsCoupon")));
		params.put("IsActivity", HtmlUtil.mapxToOptions(isactivity,dt.get(0, "IsActivity")));
		DataTable dt1 = new QueryBuilder("select ChannelName name,InnerChannelCode id from KxbChannelInfo order by CreateDate").executeDataTable();
		params.put("ParentMenu", HtmlUtil.dataTableToOptions(dt1,dt.get(0, "ParentID")));
		return params;
	}
	public void Edit() {
		String parentID = $V("ParentID");
		String name = $V("Name");
		String code = $V("Code");
		String show = $V("IsShow");
		String coupon = $V("IsCoupon");
		String activity = $V("IsActivity");
		if(StringUtil.isEmpty(show)){
			show = "Y";
		}
		if (StringUtil.isEmpty(coupon)) {
			coupon = "Y";
		}
		if (StringUtil.isEmpty(activity)) {
			activity = "Y";
		}
		if(StringUtil.isEmpty(code) || StringUtil.isEmpty(name)){
			Response.setStatus(0);
			Response.setMessage("修改失败，渠道编码、渠道名称不能为空!");
			return;
		}
		DataTable dt1 = new QueryBuilder("select InnerChannelCode,ChannelLevel from KxbChannelInfo where ChannelCode =?",code).executeDataTable();
		String innerCode = dt1.getString(0, "InnerChannelCode");
		String oldChannelLevel = dt1.getString(0, "ChannelLevel");
		String[] arr = dealChildList(innerCode);
		for(int i=0;i<arr.length;i++){
			if(arr[i].equals(parentID)){
				Response.setStatus(0);
				Response.setMessage("修改失败，不能更新到同一渠道或起子渠道下!");
				return;
			}
		}
		Transaction trans = new Transaction();
		String insertSQL = " UPDATE KxbChannelInfo set ChannelName=?,ParentInnerChanelCode=?,ChannelLevel=?,ModifyDate=?,Prop1=?,IsCoupon=?,IsActivity=? where ChannelCode=? ";
		if(StringUtil.isEmpty(parentID)){
			//一级渠道
			parentID = "0";
		}
		String channelLevel = getChannelLevel(parentID);
		QueryBuilder qb1 = new QueryBuilder(insertSQL);
		//qb1.add(getInnerChannelCode(parentID));//InnerChannelCode 渠道内部编码
		qb1.add(name);//渠道名称
		qb1.add(parentID);//父渠道
		qb1.add(channelLevel);//ChannelLevel 渠道级别
		qb1.add(PubFun.getCurrent());//渠道修改时间
		qb1.add(show);//隐藏显示
		qb1.add(coupon);//优惠券
		qb1.add(activity);//活动
		qb1.add(code);//渠道外部编码
		trans.add(qb1);
		int channelDiff = calLevelDiff(channelLevel, oldChannelLevel);
		if(channelDiff!=0){
			for(int i=0;i<arr.length;i++){
				if(!"$".equals(arr[i]) && !innerCode.equals(arr[i])){
					QueryBuilder qb2 = new QueryBuilder(" UPDATE KxbChannelInfo SET ChannelLevel=ChannelLevel+? WHERE InnerChannelCode=? ");
					qb2.add(channelDiff);
					qb2.add(arr[i]);
					trans.add(qb2);
				}
			}
		}
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("添加成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("添加失败，操作数据库时发生错误!");
		}
	}

	/**
	 * 计算渠道级别差
	 * @param channelLevel
	 * @param oldChannelLevel
	 * @return 小于0：级别上升；等于0：级别不变；大于0：级别下降
	 */
	public static int calLevelDiff(String channelLevel,String oldChannelLevel){
		
		return Integer.parseInt(channelLevel)-Integer.parseInt(oldChannelLevel);
		
	}
	/**
	 * 得到所有子渠道
	 * @param parentID
	 * @return
	 */
	public static String[] dealChildList(String parentID){
		
		DataTable dt = new QueryBuilder(" SELECT  getChannelChildList(?) Child FROM DUAL ",parentID).executeDataTable();
		
		String[] arr = null;
		if(dt!=null && dt.getRowCount()>=1){
			arr = dt.getString(0, "Child").split(",");
		}
		if(arr.length>=2){
			return arr;
		}
		return null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx init(Mapx params) {
		Mapx map = new Mapx();
		DataTable dt = new QueryBuilder("select ChannelName name,InnerChannelCode id from KxbChannelInfo order by CreateDate").executeDataTable();
		map.put("ParentMenu", HtmlUtil.dataTableToOptions(dt));
		Map<String,String> isshow = new HashMap<String,String>();
		Map<String,String> iscoupon = new HashMap<String,String>();
		Map<String,String> isactivity = new HashMap<String,String>();
		isshow.put("Y", "显示");
		isshow.put("N", "隐藏");
		iscoupon.put("Y", "是");
		iscoupon.put("N", "否");
		isactivity.put("Y", "是");
		isactivity.put("N", "否");
		map.put("IsShow", HtmlUtil.mapxToOptions(isshow));
		map.put("IsCoupon", HtmlUtil.mapxToOptions(iscoupon));
		map.put("IsActivity", HtmlUtil.mapxToOptions(isactivity));
		return map;
	}

	public void add() {
		String parentID = $V("ParentID");
		String name = $V("Name");
		String code = $V("Code");
		String show = $V("IsShow");
		String coupon = $V("IsCoupon");
		String activity = $V("IsActivity");
		if(StringUtil.isEmpty(show)){
			show = "Y";
		}
		if (StringUtil.isEmpty(coupon)) {
			coupon = "Y";
		}
		if (StringUtil.isEmpty(activity)) {
			activity = "Y";
		}
		if(StringUtil.isEmpty(code) || StringUtil.isEmpty(name)){
			Response.setStatus(0);
			Response.setMessage("添加失败，渠道编码、渠道名称不能为空!");
			return;
		}
		int k = new QueryBuilder(" select count(1) from KxbChannelInfo where ChannelCode=? ",code).executeInt();
		if(k>=1){
			Response.setStatus(0);
			Response.setMessage("添加失败，渠道编码不唯一，请重新输入!");
			return;
		}
		Transaction trans = new Transaction();
		String insertSQL = " INSERT INTO KxbChannelInfo(ChannelCode,InnerChannelCode,ChannelName,ParentInnerChanelCode,ChannelLevel,CreateDate,ModifyDate,Prop1,IsCoupon,IsActivity) VALUE(?,?,?,?,?,?,?,?,?,?) ";
		if(StringUtil.isEmpty(parentID)){
			//一级渠道
			parentID = "0";
		}
		QueryBuilder qb1 = new QueryBuilder(insertSQL);
		qb1.add(code);//渠道外部编码
		qb1.add(getInnerChannelCode(parentID));//InnerChannelCode 渠道内部编码
		qb1.add(name);//渠道名称
		qb1.add(parentID);//父渠道
		qb1.add(getChannelLevel(parentID));//ChannelLevel 渠道级别
		qb1.add(PubFun.getCurrent());//渠道创建时间
		qb1.add(PubFun.getCurrent());//渠道修改时间
		qb1.add(show);//隐藏显示
		qb1.add(coupon);//优惠券
		qb1.add(activity);//活动
		trans.add(qb1);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("添加成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("添加失败，操作数据库时发生错误!");
		}
	}

	/**
	 * 根据父渠道计算内部编码   InnerChannelCode
	 * @param parentId
	 * @return
	 */
	public static String getInnerChannelCode(String parentId){
		//渠道每级编码为4位，即一级渠道：0001，二级渠道00010001，三级渠道000100010001；以此类推！
		String innerChannelCode = "";
		if(StringUtil.isEmpty(parentId) || "0".equals(parentId)){
			innerChannelCode = StringUtil.rightPad(NoUtil.getMaxNo("KXBINNERCHANNELCODE"), '0', 5);
		}else{
			innerChannelCode = parentId+StringUtil.rightPad(NoUtil.getMaxNo("KXBINNERCHANNELCODE"), '0', 5);
		}
		return innerChannelCode;
	}
	/**
	 * 根据父渠道计算渠道级别   ChannelLevel
	 * @param parentId
	 * @return
	 */
	public static String getChannelLevel(String cChannelCode){
		
		String channelLevel = "0";
		DataTable dt = new QueryBuilder(" SELECT ChannelLevel FROM KxbChannelInfo WHERE innerChannelCode=? ",cChannelCode).executeDataTable();
		
		if(dt!=null && dt.getRowCount()>=1){
			 int cl = Integer.parseInt(String.valueOf(dt.getString(0, "ChannelLevel")))+1;
			 channelLevel = cl+"";
		}
		return channelLevel;
	}
	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		
		QueryBuilder qb1 = new QueryBuilder(" SELECT InnerChannelCode,ChannelName FROM KxbChannelInfo where InnerChannelCode in (" + ids + ")");
		DataTable dt1 = qb1.executeDataTable();
		int tLen = dt1.getRowCount();
		if(dt1!=null && tLen>=1){
			for(int j=0;j<tLen;j++){
				long count = new QueryBuilder("select count(*) from KxbChannelInfo where ParentInnerChanelCode=" + dt1.getString(j, "InnerChannelCode") + " and InnerChannelCode not in (" + ids + ")").executeLong();
				if (count > 0) {
					Response.setStatus(0);
					UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELMENU, "删除渠道" + dt1.getString(j, "ChannelName") + "失败", Request.getClientIP());
					Response.setMessage("不能删除渠道\"" + dt1.getString(j, "ChannelName")  + "\",该渠道下还有子渠道未被删除!");
					return;
				}
			}
		}
		QueryBuilder qb2 = new QueryBuilder(" DELETE FROM KxbChannelInfo where InnerChannelCode in (" + ids + ")");
		Transaction trans = new Transaction();
		trans.add(qb2);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("删除失败，操作数据库时发生错误!");
		}
	}

	/**
	 * treeDataBindForVip:新会员查询使用. <br/>
	 *
	 * @author wwy
	 * @param ta
	 */
	public static void treeDataBindForVip(TreeAction ta) {
		DataTable dt = null;
		StringBuffer sql = new StringBuffer("select ChannelCode,InnerChannelCode ID,ParentInnerChanelCode ParentID,ChannelName Name,ChannelLevel as TreeLevel from KxbChannelInfo where Prop1='Y' ");
		sql.append(" order by CreateDate ");
		dt = new QueryBuilder(sql.toString()).executeDataTable();
		ta.setRootText("会员来源渠道");
		dt.setWebMode(false);
		ta.bindData(dt);
	}
	
	@SuppressWarnings("rawtypes")
	public static void treeDataBind(TreeAction ta) {
		DataTable dt = null;
		Mapx params = ta.getParams();
		long CatalogType = params.getLong("Type");
		//int parentLevel = params.getInt("ParentLevel");
		String parentID = (String) params.get("ParentID");
		StringBuffer sql = new StringBuffer("select ChannelCode,InnerChannelCode ID,ParentInnerChanelCode ParentID,ChannelName Name,ChannelLevel as TreeLevel from KxbChannelInfo where Prop1='Y' ");
		if(StringUtil.isNotEmpty(parentID)){
			String innercode = new QueryBuilder("select InnerChannelCode from KxbChannelInfo where ChannelCode='"+parentID+"'").executeString();
			if("Member".equals(parentID)){
				innercode = new QueryBuilder("select InnerChannelCode from KxbChannelInfo where ChannelCode='b2b'").executeString();
				if(StringUtil.isNotEmpty(innercode)){
					sql.append(" AND InnerChannelCode !='"+innercode+"' AND ParentInnerChanelCode !='"+innercode+"' ");
				}
			}else{
				if(StringUtil.isNotEmpty(innercode)){
					sql.append(" AND (InnerChannelCode='"+innercode+"' or ParentInnerChanelCode='"+innercode+"')");
				}
			}
		}
		sql.append(" order by ID ");
		dt = new QueryBuilder(sql.toString()).executeDataTable();
		ta.setRootText("订单渠道");
		dt.setWebMode(false);
		ta.bindData(dt);
		if (CatalogType == 1) {
			List items = ta.getItemList();
			for (int i = 1; i < items.size(); i++) {
				TreeItem item = (TreeItem) items.get(i);
				if ("Y".equals(item.getData().getString("SingleFlag"))) {
					item.setIcon("Icons/treeicon11.gif");
				}
			}
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public static void treeAllDataBind(TreeAction ta) {
		DataTable dt = null;
		Mapx params = ta.getParams();
		long CatalogType = params.getLong("Type");
		//int parentLevel = params.getInt("ParentLevel");
		String parentID = (String) params.get("ParentID");
		StringBuffer sql = new StringBuffer("select ChannelCode,InnerChannelCode ID,ParentInnerChanelCode ParentID,ChannelName Name,ChannelLevel as TreeLevel from KxbChannelInfo ");
		if(StringUtil.isNotEmpty(parentID)){
			String innercode = new QueryBuilder("select InnerChannelCode from KxbChannelInfo where ChannelCode='"+parentID+"'").executeString();
			if("Member".equals(parentID)){
				innercode = new QueryBuilder("select InnerChannelCode from KxbChannelInfo where ChannelCode='b2b'").executeString();
				if(StringUtil.isNotEmpty(innercode)){
					sql.append(" AND InnerChannelCode !='"+innercode+"' AND ParentInnerChanelCode !='"+innercode+"' ");
				}
			}else{
				if(StringUtil.isNotEmpty(innercode)){
					sql.append(" AND (InnerChannelCode='"+innercode+"' or ParentInnerChanelCode='"+innercode+"')");
				}
			}
		}
		sql.append(" order by CreateDate ");
		dt = new QueryBuilder(sql.toString()).executeDataTable();
		ta.setRootText("订单渠道");
		dt.setWebMode(false);
		ta.bindData(dt);
		if (CatalogType == 1) {
			List items = ta.getItemList();
			for (int i = 1; i < items.size(); i++) {
				TreeItem item = (TreeItem) items.get(i);
				if ("Y".equals(item.getData().getString("SingleFlag"))) {
					item.setIcon("Icons/treeicon11.gif");
				}
			}
		}
	}
	/**
	 * treeDataBindForCoupon:优惠券左侧树结构. <br/>
	 * @author wangtz
	 * @param ta
	 */
	public static void treeDataBindForCoupon(TreeAction ta) {
		DataTable dt = null;
		DataTable dt1 = null;
		String csnid = ta.getParam("ID");//优惠券id
		StringBuffer sql = new StringBuffer("select ChannelCode,InnerChannelCode ID,ParentInnerChanelCode ParentID,ChannelName Name,ChannelLevel as TreeLevel,'' as Checked,'' as disabled from KxbChannelInfo where Prop1='Y' and IsCoupon='Y' ");
		sql.append(" order by CreateDate ");
		dt = new QueryBuilder(sql.toString()).executeDataTable();
		if (StringUtil.isNotEmpty(csnid)) {
			String sql1 = "SELECT channelsn FROM couponinfo WHERE Id ='"+ csnid +"'";
			dt1 = new QueryBuilder(sql1.toString()).executeDataTable();
			String[] channelsn = dt1.getString(0, "channelsn").split(",");
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "disabled", "disabled");
				for (int j = 0; j < channelsn.length; j++) {
					if (dt.getString(i, "ChannelCode").equals(String.valueOf(channelsn[j]))) {

						dt.set(i, "Checked", "Checked");
					}
				}
			}
		}
		ta.setRootText("优惠券应用渠道");
		dt.setWebMode(false);
		ta.bindData(dt);

	}
	/**
	 * treeDataBindForActivity:促销活动左侧树结构. <br/>
	 * @author wangtz
	 * @param ta
	 */
	public static void treeDataBindForActivity(TreeAction ta) {
		DataTable dt = null;
		DataTable dt1 = null;
		String csnid = ta.getParam("ID");//活动id
		StringBuffer sql = new StringBuffer("select ChannelCode,InnerChannelCode ID,ParentInnerChanelCode ParentID,ChannelName Name,ChannelLevel as TreeLevel,'' as Checked,'' as disabled from KxbChannelInfo where Prop1='Y' and IsActivity = 'Y' ");
		sql.append(" order by CreateDate ");
		dt = new QueryBuilder(sql.toString()).executeDataTable();
		if (StringUtil.isNotEmpty(csnid)) {
			String sql1 = "SELECT channelsn FROM SDCouponActivityInfo WHERE id = '"+ csnid + "'";
			dt1 = new QueryBuilder(sql1.toString()).executeDataTable();
			String[] channelsn = dt1.getString(0, "channelsn").split(",");
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "disabled", "disabled");
				for (int j = 0; j < channelsn.length; j++) {
					if (dt.getString(i, "ChannelCode").equals(String.valueOf(channelsn[j]))) {

						dt.set(i, "Checked", "Checked");
					}
				}
			}
		}
		ta.setRootText("促销活动应用渠道");
		dt.setWebMode(false);
		ta.bindData(dt);

	}
}

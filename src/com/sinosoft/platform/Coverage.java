package com.sinosoft.platform;

import java.util.Map;
import java.util.TreeMap;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

public class Coverage extends Page {

	public static void dg1DataBind_cx(DataGridAction dga) {
		String sql = "select a.InnerCode ID, a.ParentInnerCode ParentID,a.Name,a.TreeLevel,a.Code,"
					+"(SELECT name FROM DataInfo WHERE InnerCode = a.ParentInnerCode) ParentName, "
					+ " IF(a.Prop2 = '1', '一级层级', IF(a.Prop2 = '2','二级层级', '')) as Prop2 from DataInfo a "
					+ " where Type = 'coverage_cx' order by a.InnerCode, a.orderflag";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		dga.bindData(dt);
	}
	
	public static void dg1DataBind_rsx(DataGridAction dga) {
		String sql = "select a.InnerCode ID, a.ParentInnerCode ParentID,a.Name,a.TreeLevel,a.Code,"
					+"(SELECT name FROM DataInfo WHERE InnerCode = a.ParentInnerCode) ParentName, "
					+ " IF(a.Prop2 = '1', '一级层级', IF(a.Prop2 = '2','二级层级', '')) as Prop2 from DataInfo a "
					+ " where Type = 'coverage_rsx' order by a.InnerCode, a.orderflag";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		dga.bindData(dt);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx initDialog(Mapx params) {
		String type = params.getString("Type");
		Mapx map = new Mapx();
		DataTable dt = new QueryBuilder("select Name name,InnerCode id from dataInfo where type = ? order by addtime", type).executeDataTable();
		map.put("ParentMenu", HtmlUtil.dataTableToOptions(dt));
		Map<String,String> isshow = new TreeMap<String,String>();
		isshow.put("Y", "显示");
		isshow.put("N", "隐藏");
		map.put("IsShow", HtmlUtil.mapxToOptions(isshow, "Y"));
		Map<String,String> isNode = new TreeMap<String,String>();
		isNode.put("1", "一级节点");
		isNode.put("2", "二级节点");
		map.put("IsNode", HtmlUtil.mapxToOptions(isNode, true));
		map.put("type", type);
		return map;
	}
	
	public void add() {
		String parentID = $V("ParentID");
		String name = $V("Name");
		String code = $V("Code");
		String show = $V("IsShow");
		String node = $V("IsNode");
		String type = $V("Type");
		if(StringUtil.isEmpty(show)){
			show = "Y";
		}
		if(StringUtil.isEmpty(code) || StringUtil.isEmpty(name)){
			Response.setStatus(0);
			Response.setMessage("添加失败，渠道编码、渠道名称不能为空!");
			return;
		}
		int k = new QueryBuilder(" select count(1) from DataInfo where Code=? ",code).executeInt();
		if(k>=1){
			Response.setStatus(0);
			Response.setMessage("添加失败，编码不唯一，请重新输入!");
			return;
		}
		Transaction trans = new Transaction();
		String insertSQL = " INSERT INTO DataInfo(Code,InnerCode,Name,ParentInnerCode,Type,OrderFlag,TreeLevel,AddTime,ModifyTime,IsLeaf,AddUser,Prop2) VALUE(?,?,?,?,?,?,?,?,?,?,?,?) ";
		if(StringUtil.isEmpty(parentID)){
			//一级渠道
			parentID = "0";
		}
		QueryBuilder qb1 = new QueryBuilder(insertSQL);
		qb1.add(code);//渠道外部编码
		qb1.add(getInnerCode(parentID));//InnerChannelCode 渠道内部编码
		qb1.add(name);//渠道名称
		qb1.add(parentID);//父渠道
		qb1.add(type);//类型
		qb1.add(System.currentTimeMillis());//排序
		qb1.add(getTreeLevel(parentID));//ChannelLevel 渠道级别
		qb1.add(PubFun.getCurrent());//渠道创建时间
		qb1.add(PubFun.getCurrent());//渠道修改时间
		qb1.add(show);//隐藏显示
		qb1.add(User.getUserName());//操作人
		qb1.add(node);//操作人
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
	 * 根据父渠道计算内部编码   getInnerCode
	 * @param parentId
	 * @return
	 */
	public static String getInnerCode(String parentId){
		//渠道每级编码为4位，即一级渠道：0001，二级渠道0001，三级渠道000100010001；以此类推！
		String innerCode = "";
		innerCode = new QueryBuilder(" select InnerCode from DataInfo where ParentInnerCode=? order by InnerCode desc limit 1 ", parentId).executeString();
		if (StringUtil.isEmpty(innerCode)) {
			innerCode = parentId + "0000";
		}
		if(StringUtil.isEmpty(parentId) || "0".equals(parentId)){
			// innerCode = StringUtil.rightPad(NoUtil.getMaxNo("INNERDATACODE"), '0', 4);
			int i = Integer.valueOf(innerCode) + 1;
			innerCode = String.format("%04d", i);
		}else{
			String temp = innerCode.substring(parentId.length(), innerCode.length());
			int i = Integer.valueOf(temp) + 1;
			innerCode = parentId + String.format("%04d", i);
			//innerCode = parentId+StringUtil.rightPad(NoUtil.getMaxNo("INNERDATACODE"), '0', 4);
		}
		return innerCode;
	}
	/**
	 * 根据父渠道计算渠道级别   treeLevel
	 * @param cCode
	 * @param type
	 * @return
	 */
	public static String getTreeLevel(String cCode) {

		String treeLevel = "1";
		DataTable dt = new QueryBuilder(" SELECT TreeLevel FROM dataInfo WHERE innerCode=?", cCode).executeDataTable();

		if (dt != null && dt.getRowCount() >= 1) {
			int cl = Integer.parseInt(String.valueOf(dt.getString(0, "TreeLevel"))) + 1;
			treeLevel = cl + "";
		}
		return treeLevel;
	}
	
	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		
		QueryBuilder qb1 = new QueryBuilder(" SELECT InnerCode,Name FROM DataInfo where InnerCode in (" + ids + ")");
		DataTable dt1 = qb1.executeDataTable();
		int tLen = dt1.getRowCount();
		if(dt1!=null && tLen>=1){
			for(int j=0;j<tLen;j++){
				long count = new QueryBuilder("select count(*) from DataInfo where ParentInnerCode=" + dt1.getString(j, "InnerCode") + " and InnerCode not in (" + ids + ")").executeLong();
				if (count > 0) {
					Response.setStatus(0);
					UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELMENU, "删除渠道" + dt1.getString(j, "Name") + "失败", Request.getClientIP());
					Response.setMessage("不能删除渠道\"" + dt1.getString(j, "Name")  + "\",该渠道下还有子渠道未被删除!");
					return;
				}
			}
		}
		QueryBuilder qb2 = new QueryBuilder(" DELETE FROM DataInfo where InnerCode in (" + ids + ")");
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

	@SuppressWarnings("rawtypes")
	public static void treeDataBind_rsx(TreeAction ta) {
		DataTable dt = null;
		Mapx params = ta.getParams();
		String parentID = (String) params.get("ParentID");
		StringBuffer sql = new StringBuffer("select Code,InnerCode ID,ParentInnerCode ParentID,Name,TreeLevel from dataInfo where 1=1 and type = 'coverage_rsx' ");
		if(StringUtil.isNotEmpty(parentID)){
			String innercode = new QueryBuilder("select InnerCode from dataInfo where Code='"+parentID+"'").executeString();
			if(StringUtil.isNotEmpty(innercode)){
				sql.append(" AND (InnerCode='"+innercode+"' or ParentInnerCode='"+innercode+"')");
			}
		}
		sql.append(" order by addtime ");
		dt = new QueryBuilder(sql.toString()).executeDataTable();
		ta.setRootText("险种分类");
		dt.setWebMode(false);
		ta.bindData(dt);
	}

	@SuppressWarnings("rawtypes")
	public static void treeDataBind_cx(TreeAction ta) {
		DataTable dt = null;
		Mapx params = ta.getParams();
		String parentID = (String) params.get("ParentID");
		StringBuffer sql = new StringBuffer("select Code,InnerCode ID,ParentInnerCode ParentID,Name,TreeLevel from dataInfo where 1=1 and type = 'coverage_cx' ");
		if(StringUtil.isNotEmpty(parentID)){
			String innercode = new QueryBuilder("select InnerCode from dataInfo where Code='"+parentID+"'").executeString();
			if(StringUtil.isNotEmpty(innercode)){
				sql.append(" AND (InnerCode='"+innercode+"' or ParentInnerCode='"+innercode+"')");
			}
		}
		sql.append(" order by addtime ");
		dt = new QueryBuilder(sql.toString()).executeDataTable();
		ta.setRootText("险种分类");
		dt.setWebMode(false);
		ta.bindData(dt);
	}
}

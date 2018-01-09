package com.sinosoft.platform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZDBranchSchema;
public class Branch extends Page {

	public static Mapx initDialog(Mapx params) {
		String branchInnerCode = params.getString("BranchInnerCode");
		if (StringUtil.isNotEmpty(branchInnerCode)) {
			ZDBranchSchema branch = new ZDBranchSchema();
			branch.setBranchInnerCode(branchInnerCode);
			branch.fill();
			params = branch.toMapx();
			params.put("ParentName",
					new QueryBuilder("select name from zdbranch where branchInnercode=?", branch.getParentInnerCode())
							.executeString());
		} else {
			String source = params.getString("source");
			if ("lineTeam".equalsIgnoreCase(source)) {
				String ParentInnerCode = params.getString("ParentInnerCode");
				if (StringUtil.isEmpty(ParentInnerCode)) {
					ParentInnerCode = new QueryBuilder("select Memo from zdcode where CodeType='BranchCode' and ParentCode='BranchCode' and CodeValue='LineTeam'").executeString();
					
				}
				params.put("ParentInnerCode",
						HtmlUtil.dataTableToOptions(getBranchTable(ParentInnerCode), ParentInnerCode));
			} else {
				params.put("ParentInnerCode",
						HtmlUtil.dataTableToOptions(getBranchTable(""), params.getString("ParentInnerCode")));
			}
			
		}
		return params;
	}

	public static DataTable getBranchTable(String parentInnerCode) {
		String sql = "select Name,BranchInnerCode,TreeLevel,ParentInnerCode from zdbranch ";
		if (StringUtil.isNotEmpty(parentInnerCode)) {
			sql += " where BranchInnerCode like '"+parentInnerCode+"%'";
		}
		sql += " order by orderflag";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		dt = DataGridAction.sortTreeDataTable(dt, "BranchInnerCode", "ParentInnerCode");
		com.sinosoft.cms.pub.PubFun.indentDataTable(dt);
		return dt;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String parentInnerCode = (String) dga.getParam("ParentInnerCode");
		QueryBuilder qb = new QueryBuilder("select * from zdbranch order by orderflag");
		DataTable dt = qb.executeDataTable();
		dt = DataGridAction.sortTreeDataTable(dt, "BranchInnerCode", "ParentInnerCode");
		DataTable result = new DataTable(dt.getDataColumns(), null);
		if (StringUtil.isNotEmpty(parentInnerCode)) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (parentInnerCode.startsWith(dt.getString(i, "BranchInnerCode"))) {
					result.insertRow(dt.getDataRow(i));
					dt.set(i,
							"Name",
							"<a href='Branch.jsp?ParentInnerCode=" + dt.get(i, "BranchInnerCode") + "'>"
									+ dt.get(i, "Name") + "</a>");
				}
				if (dt.getString(i, "BranchInnerCode").equals(parentInnerCode)) {
					int level = dt.getInt(i, "TreeLevel");
					for (int j = i + 1; j < dt.getRowCount(); j++) {
						int level2 = dt.getInt(j, "TreeLevel");
						if (level2 <= level) {
							break;
						}
						if (level2 <= level + 1) {
							result.insertRow(dt.getDataRow(j));
							if (j != dt.getRowCount() - 1 && dt.getInt(j + 1, "TreeLevel") > level2) {
								dt.set(j, "Name", "<a href='Branch.jsp?ParentInnerCode=" + dt.get(j, "BranchInnerCode")
										+ "'>" + dt.get(j, "Name") + "</a>");
							}
						}
					}
					break;
				}
			}
		} else {
			for (int i = 0; i < dt.getRowCount(); i++) {
				int level = dt.getInt(i, "TreeLevel");
				if (level <= 2) {
					result.insertRow(dt.getDataRow(i));
					if (i != dt.getRowCount() - 1 && dt.getInt(i + 1, "TreeLevel") > level) {
						dt.set(i, "Name", "<a href='Branch.jsp?ParentInnerCode=" + dt.get(i, "BranchInnerCode") + "'>"
								+ dt.get(i, "Name") + "</a>");
					}
				}
			}
		}
		dga.bindData(result);
	}

	public static void dg2DataBind(DataGridAction dga) {
		String parentInnerCode = (String) dga.getParam("ParentInnerCode");
		if (StringUtil.isEmpty(parentInnerCode)) {
			parentInnerCode = new QueryBuilder("select Memo from zdcode where CodeType='BranchCode' and ParentCode='BranchCode' and CodeValue='LineTeam'").executeString();
		}
		QueryBuilder qb;
		if (StringUtil.isNotEmpty(parentInnerCode)) {
			qb = new QueryBuilder("select * from zdbranch where BranchInnerCode like '"+parentInnerCode+"%' order by orderflag");
		} else {
			qb = new QueryBuilder("select * from zdbranch order by orderflag");
		}
		
		DataTable dt = qb.executeDataTable();
		dt = DataGridAction.sortTreeDataTable(dt, "BranchInnerCode", "ParentInnerCode");
		DataTable result = new DataTable(dt.getDataColumns(), null);
		
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (parentInnerCode.startsWith(dt.getString(i, "BranchInnerCode"))) {
				result.insertRow(dt.getDataRow(i));
				dt.set(i,
						"Name",
						"<a href='BranchManage.jsp?ParentInnerCode=" + dt.get(i, "BranchInnerCode") + "'>"
								+ dt.get(i, "Name") + "</a>");
			}
			if (dt.getString(i, "BranchInnerCode").equals(parentInnerCode)) {
				int level = dt.getInt(i, "TreeLevel");
				for (int j = i + 1; j < dt.getRowCount(); j++) {
					int level2 = dt.getInt(j, "TreeLevel");
					if (level2 <= level) {
						break;
					}
					if (level2 <= level + 1) {
						result.insertRow(dt.getDataRow(j));
						if (j != dt.getRowCount() - 1 && dt.getInt(j + 1, "TreeLevel") > level2) {
							dt.set(j, "Name", "<a href='BranchManage.jsp?ParentInnerCode=" + dt.get(j, "BranchInnerCode")
									+ "'>" + dt.get(j, "Name") + "</a>");
						}
					}
				}
				break;
			}
			
		}
		
		dga.bindData(result);
	}
	
	public void add() {
		String parentInnerCode = $V("ParentInnerCode");
		Transaction trans = new Transaction();
		if (StringUtil.isEmpty(parentInnerCode)) {
			parentInnerCode = "0";
			ZDBranchSchema branch = new ZDBranchSchema();
			branch.setValue(Request);
			branch.setBranchInnerCode(NoUtil.getMaxNo("BranchInnerCode", 4));
			branch.setParentInnerCode(parentInnerCode);
			branch.setTreeLevel(1);
			branch.setType("0");
			branch.setIsLeaf("Y");

			DataTable dt = new QueryBuilder("select * from zdbranch order by orderflag").executeDataTable();
			String orderflag = "";
			if (dt != null && dt.getRowCount() > 0) {
				orderflag = dt.getString(dt.getRowCount() - 1, "OrderFlag");
			} else {
				orderflag = "0";
			}
			branch.setOrderFlag(orderflag + 1);
			branch.setAddTime(new Date());
			branch.setAddUser(User.getUserName());
			trans.add(branch, Transaction.INSERT);

			trans.add(new QueryBuilder("update zdbranch set orderflag = orderflag+1 where orderflag>?", orderflag));
			if (trans.commit()) {
				Response.setLogInfo(1, "新建成功");
			} else {
				Response.setLogInfo(0, "新建失败");
			}
		} else {
			ZDBranchSchema pBranch = new ZDBranchSchema();
			pBranch.setBranchInnerCode(parentInnerCode);
			pBranch.fill();
			long pTreeLevel = pBranch.getTreeLevel();

			ZDBranchSchema branch = new ZDBranchSchema();
			branch.setValue(Request);
			branch.setBranchInnerCode(NoUtil.getMaxNo("BranchInnerCode", pBranch.getBranchInnerCode(), 4));
			branch.setParentInnerCode(pBranch.getBranchInnerCode());
			branch.setTreeLevel(pTreeLevel + 1);
			branch.setType("0");
			branch.setIsLeaf("Y");
			branch.setAddTime(new Date());
			branch.setAddUser(User.getUserName());

			DataTable dt = new QueryBuilder("select * from zdbranch where BranchInnerCode like '"
					+ pBranch.getBranchInnerCode() + "%' order by orderflag").executeDataTable();
			long orderflag = Long.parseLong(dt.getString(dt.getRowCount() - 1, "OrderFlag"));
			branch.setOrderFlag(orderflag + 1);

			trans.add(new QueryBuilder("update zdbranch set orderflag = orderflag+1 where orderflag>?", orderflag));
			trans.add(branch, Transaction.INSERT);

			trans.add(new QueryBuilder("update zdbranch set IsLeaf='N' where branchInnerCode =?", pBranch
					.getBranchInnerCode()));
			if (trans.commit()) {
				Response.setLogInfo(1, "新建成功");
			} else {
				Response.setLogInfo(0, "新建失败");
			}
		}
	}

	public void save() {
		String branchInnerCode = $V("BranchInnerCode");
		Transaction trans = new Transaction();
		if (StringUtil.isEmpty(branchInnerCode)) {
			Response.setLogInfo(0, "传入数据错误！");
			return;
		}
		ZDBranchSchema branch = new ZDBranchSchema();
		branch.setBranchInnerCode(branchInnerCode);
		if (!branch.fill()) {
			Response.setLogInfo(0, branchInnerCode + "机构不存在！");
			return;
		}

		branch.setValue(Request);
		branch.setModifyUser(User.getUserName());
		branch.setModifyTime(new Date());
		trans.add(branch, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}

	public void delLineTeamBranch() {
		String branchInnerCode = $V("ID");
		ZDBranchSchema branch = new ZDBranchSchema();
		branch.setBranchInnerCode(branchInnerCode);
		if (branch.fill()) {
			String name = branch.getName();
			if ("0".equals(branch.getParentInnerCode())) {
				Response.setLogInfo(0, "不能删除顶级机构");
				return;
			}
			// 线下团队机构顶级机构码
			String innerCode = new QueryBuilder("select Memo from zdcode where CodeType='BranchCode' and ParentCode='BranchCode' and CodeValue='LineTeam'").executeString();
			if (StringUtil.isNotEmpty(innerCode) && innerCode.equalsIgnoreCase(branchInnerCode)) {
				Response.setLogInfo(0, "不能删除顶级机构");
				return;
			}
			// 判断是否有子机构
			int count = new QueryBuilder("select count(1) from zdbranch where ParentInnerCode=?", branchInnerCode).executeInt();
			if (count > 0) {
				Response.setLogInfo(0, "要删除的机构下有子机构，请从最小机构开始删除！");
				return;
			}
			// 判断该机构下是否有用户
			count = new QueryBuilder("select count(1) from zduser where BranchInnerCode=?", branchInnerCode).executeInt();
			if (count > 0) {
				Response.setLogInfo(0, "该机构有用户关联，请修改用户机构后删除！");
				return;
			}
			// 判断该机构下是否有业务数据
			count = new QueryBuilder("select count(1) from LineTeamPolicyInfo where BranchInnerCode=?", branchInnerCode).executeInt();
			if (count > 0) {
				Response.setLogInfo(0, "该机构有出单数据，不能删除！");
				return;
			}
			if (branch.deleteAndBackup()) {
				UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELBRANCH, "删除机构-"+name+"("+branchInnerCode+")成功", Request.getClientIP());
				Response.setLogInfo(1, "删除成功");
			}
			// 删除对应的手续费率
			new QueryBuilder("delete from LineTeamRate where branchInnercode=?", branchInnerCode).executeNoQuery();
		} else {
			Response.setLogInfo(0, "未找到要删除的组织机构");
		}
	}
	
	public void del() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		Transaction trans = new Transaction();
		ZDBranchSchema branch = new ZDBranchSchema();
		for (int i = 0; i < ids.length; i++) {
			branch.setBranchInnerCode(ids[i]);
			if (branch.fill()) {
				if ("0".equals(branch.getParentInnerCode())) {
					Response.setLogInfo(0, "删除失败：不能删除顶级机构");
					UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELBRANCH, "删除机构:" + branch.getName() + "失败",
							Request.getClientIP());
					return;
				}
				QueryBuilder qb = new QueryBuilder("where BranchInnerCode like ?", branch.getBranchInnerCode() + "%");
				trans.add(branch.query(qb), Transaction.DELETE_AND_BACKUP);
			}
		}

		if (trans.commit()) {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELBRANCH, "删除机构成功", Request.getClientIP());
			Response.setLogInfo(1, "删除成功");
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELBRANCH, "删除机构失败", Request.getClientIP());
			Response.setLogInfo(0, "删除失败");
		}
	}

	public void sortBranch() {
		String orderBranch = $V("OrderBranch");
		String nextBranch = $V("NextBranch");
		String ordertype = $V("OrderType");
		if (StringUtil.isEmpty(orderBranch) || StringUtil.isEmpty(nextBranch) || StringUtil.isEmpty(ordertype)) {
			Response.setLogInfo(0, "传递数据有误！");
			return;
		}

		Transaction trans = new Transaction();
		DataTable DT = new QueryBuilder("select * from zdbranch order by orderflag").executeDataTable();

		List branchList = new ArrayList();

		// 需要排序的机构所在的树（该机构及其子机构）
		DataTable orderDT = new QueryBuilder("select * from zdbranch where branchinnercode like '" + orderBranch
				+ "%' order by orderflag").executeDataTable();

		// 要放置（机构前或机构后）机构所对应的树
		DataTable nextDT = new QueryBuilder("select * from zdbranch where branchinnercode like '" + nextBranch
				+ "%' order by orderflag").executeDataTable();

		// 从下往上拉
		if ("before".equalsIgnoreCase(ordertype)) {
			for (int i = 0; i < DT.getRowCount(); i++) {
				if (DT.getString(i, "BranchInnerCode").equals(nextBranch)) {
					for (int m = 0; orderDT != null && m < orderDT.getRowCount(); m++) {
						branchList.add(orderDT.getDataRow(m));
					}
				} else if (DT.getString(i, "BranchInnerCode").equals(orderBranch)) {
					// 跳过排序机构树
					i = i - 1 + orderDT.getRowCount();
					continue;
				}
				branchList.add(DT.getDataRow(i));
			}

			// 从上往下拉
		} else if ("after".equalsIgnoreCase(ordertype)) {
			for (int i = 0; DT != null && i < DT.getRowCount(); i++) {
				if (DT.getString(i, "BranchInnerCode").equals(orderBranch)) {
					// 跳过排序机构树
					i = i - 1 + orderDT.getRowCount();
					continue;
				} else if (DT.getString(i, "BranchInnerCode").equals(nextBranch)) {

					// 先排 选择树，再排 排序机构树
					for (int m = 0; nextDT != null && m < nextDT.getRowCount(); m++) {
						branchList.add(nextDT.getDataRow(m));
					}

					for (int j = 0; orderDT != null && j < orderDT.getRowCount(); j++) {
						branchList.add(orderDT.getDataRow(j));
					}

					// 继续循环排序
					i = i - 1 + nextDT.getRowCount();
				} else {
					branchList.add(DT.getDataRow(i));
				}
			}
		}

		for (int i = 0; branchList != null && i < branchList.size(); i++) {
			DataRow dr = (DataRow) branchList.get(i);
			trans.add(new QueryBuilder("update zdbranch set orderflag = ? where BranchInnerCode = ?", i, dr
					.getString("BranchInnerCode")));
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "排序成功！");
		} else {
			Response.setLogInfo(0, "排序失败！");
		}
	}
}

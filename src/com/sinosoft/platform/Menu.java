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
import com.sinosoft.framework.extend.plugin.PluginConfig;
import com.sinosoft.framework.extend.plugin.PluginException;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ObjectUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.XMLLoader;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZDMenuSchema;
import com.sinosoft.schema.ZDMenuSet;

public class Menu extends Page {
	public static Mapx MenuCacheMap = new Mapx();

	static {
		updateCache();
	}

	private static void updateCache() {
		String sql = "select * from ZDMenu where (parentid in(select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and visiable='Y' order by OrderFlag,id";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			MenuCacheMap.put(dt.getString(i, "URL"), dt.getString(i, "ID"));
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select ID,ParentID,Name,Icon,URL,Visiable,Addtime,Memo,Type,'' as Expand,'' as TreeLevel from ZDMenu order by OrderFlag,id";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("1".equals(dt.get(i, "Type"))) {
				dt.set(i, "Expand", "Y");
			} else {
				dt.set(i, "Expand", "N");
			}
			if ("2".equals(dt.get(i, "Type"))) {
				dt.set(i, "TreeLevel", "1");
			} else {
				dt.set(i, "TreeLevel", "0");
			}
		}
		dga.bindData(dt);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		ZDMenuSet set = new ZDMenuSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDMenuSchema menu = new ZDMenuSchema();
			menu.setID(Integer.parseInt(dt.getString(i, "ID")));
			menu.fill();
			menu.setName(dt.getString(i, "Name"));
			menu.setURL(dt.getString(i, "URL"));
			menu.setMemo(dt.getString(i, "Memo"));
			menu.setIcon(dt.getString(i, "Icon"));
			menu.setVisiable(dt.getString(i, "Visiable"));
			if (menu.getParentID() == 0) {
				if (dt.getString(i, "Expand").equals("Y")) {
					menu.setType("1");
				} else {
					menu.setType("3");
				}
			}
			set.add(menu);
		}
		if (set.update()) {
			updateCache();
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public static Mapx init(Mapx params) {
		Mapx map = new Mapx();
		DataTable dt = new QueryBuilder("select name,id from zdmenu where ParentID=0 order by OrderFlag,id").executeDataTable();
		map.put("ParentMenu", HtmlUtil.dataTableToOptions(dt));
		return map;
	}

	public void add() {
		ZDMenuSchema menu = new ZDMenuSchema();
		menu.setIcon($V("Icon").substring($V("Icon").indexOf("Icons/")));
		menu.setID(NoUtil.getMaxID("MenuID"));
		menu.setAddTime(new Date());
		menu.setAddUser(User.getUserName());
		menu.setMemo($V("Memo"));
		menu.setName($V("Name"));
		menu.setURL($V("URL"));
		menu.setVisiable($V("Visiable"));
		menu.setParentID(Long.parseLong($V("ParentID")));
		DataTable parentDT = new QueryBuilder("select * from zdmenu where parentID = ? order by orderflag,id", Long.parseLong($V("ParentID"))).executeDataTable();
		if ("0".equals($V("ParentID"))) {
			parentDT = new QueryBuilder("select * from zdmenu order by orderflag,id").executeDataTable();
		}
		long orderflag = 0;
		if (parentDT != null && parentDT.getRowCount() > 0) {
			orderflag = Long.parseLong(parentDT.getString(parentDT.getRowCount() - 1, "OrderFlag"));
		} else {
			orderflag = Long.parseLong(new QueryBuilder("select OrderFlag from zdmenu where ID = ?", Long.parseLong($V("ParentID"))).executeString());
			if ("0".equals($V("ParentID"))) {
				orderflag = 0;
			}
		}
		menu.setOrderFlag(orderflag + 1);
		Transaction trans = new Transaction();
		if (menu.getParentID() == 0) {
			menu.setType("1");
		} else {
			menu.setType("2");
		}

		trans.add(new QueryBuilder("update zdmenu set orderflag = orderflag + 1 where orderflag > ?", orderflag));
		trans.add(menu, Transaction.INSERT);
		if (trans.commit()) {
			updateCache();
			Response.setStatus(1);
			Response.setMessage("添加成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("添加失败，操作数据库时发生错误!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		ZDMenuSchema menu = new ZDMenuSchema();
		ZDMenuSet set = menu.query(new QueryBuilder("where id in (" + ids + ")"));
		StringBuffer menuLog = new StringBuffer("删除菜单：");
		for (int i = 0; i < set.size(); i++) {
			menu = set.get(i);
			if (menu.getParentID() == 0) {
				long count = new QueryBuilder("select count(*) from zdmenu where parentid=" + menu.getID() + " and id not in (" + ids + ")").executeLong();
				if (count > 0) {
					Response.setStatus(0);
					UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELMENU, "删除菜单" + menu.getName() + "失败", Request.getClientIP());
					Response.setMessage("不能删除菜单\"" + menu.getName() + "\",该菜单下还有子菜单未被删除!");
					return;
				}
			}
			menuLog.append(menu.getName() + ",");
		}
		if (set.delete()) {
			updateCache();
			Response.setStatus(1);
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELMENU, menuLog + "成功", Request.getClientIP());
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELMENU, menuLog + "失败", Request.getClientIP());
			Response.setMessage("删除失败，操作数据库时发生错误!");
		}
	}

	public void sortMenu() {
		String orderMenu = $V("OrderMenu");
		String nextMenu = $V("NextMenu");
		String ordertype = $V("OrderType");
		if (StringUtil.isEmpty(orderMenu) || StringUtil.isEmpty(nextMenu) || StringUtil.isEmpty(ordertype)) {
			Response.setLogInfo(0, "传递数据有误！");
			return;
		}

		Transaction trans = new Transaction();
		DataTable DT = new DataTable();
		DataTable parentDT = new QueryBuilder("select * from zdMenu where parentID = 0 order by orderflag,id").executeDataTable();
		for (int i = 0; i < parentDT.getRowCount(); i++) {
			DT.insertRow(parentDT.getDataRow(i));
			DataTable childDT = new QueryBuilder("select * from zdMenu where parentID = ? order by orderflag,id", parentDT.getLong(i, "ID")).executeDataTable();
			for (int j = 0; j < childDT.getRowCount(); j++) {
				DT.insertRow(childDT.getDataRow(j));
			}
		}

		List branchList = new ArrayList();

		// 需要排序的菜单所在的树（该菜单及其子菜单）
		DataTable orderDT = new QueryBuilder("select * from zdMenu where parentID = ? or id = ? order by orderflag,id", Long.parseLong(orderMenu), Long.parseLong(orderMenu)).executeDataTable();

		// 要放置（菜单前或菜单后）菜单所对应的树
		DataTable nextDT = new QueryBuilder("select * from zdMenu where parentID = ? or id = ? order by orderflag,id", Long.parseLong(nextMenu), Long.parseLong(nextMenu)).executeDataTable();

		// 从下往上拉
		if ("before".equalsIgnoreCase(ordertype)) {
			for (int i = 0; DT != null && i < DT.getRowCount(); i++) {
				if (DT.getString(i, "ID").equals(nextMenu)) {
					for (int m = 0; orderDT != null && m < orderDT.getRowCount(); m++) {
						branchList.add(orderDT.getDataRow(m));
					}
				} else if (DT.getString(i, "ID").equals(orderMenu)) {
					// 跳过排序菜单树
					i = i - 1 + orderDT.getRowCount();
					continue;
				}
				branchList.add(DT.getDataRow(i));
			}

			// 从上往下拉
		} else if ("after".equalsIgnoreCase(ordertype)) {
			for (int i = 0; DT != null && i < DT.getRowCount(); i++) {
				if (DT.getString(i, "ID").equals(orderMenu)) {
					// 跳过排序菜单树
					i = i - 1 + orderDT.getRowCount();
					continue;
				} else if (DT.getString(i, "ID").equals(nextMenu)) {

					// 先排 选择树，再排 排序菜单树
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

		for (int i = 0; i < branchList.size(); i++) {
			DataRow dr = (DataRow) branchList.get(i);
			trans.add(new QueryBuilder("update zdmenu set orderflag = ? where ID = ?", i, dr.getLong("ID")));
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "排序成功！");
		} else {
			Response.setLogInfo(0, "排序失败！");
		}
	}

	public static final String Type_Backend = "Backend";
	public static final String Type_Frontend = "Frontend";
	private String ID;
	private String parentID;
	private String description;
	private String name;
	private String icon;
	private String order;
	private String URL;
	private String type;
	private PluginConfig config;

	public void parse(PluginConfig pc, XMLLoader.NodeData parent) throws PluginException {
		this.config = pc;
		for (XMLLoader.NodeData nd : parent.getChildrenDataList()) {
			if (nd.getTagName().equalsIgnoreCase("id")) {
				this.ID = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("parentId")) {
				this.parentID = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("description")) {
				this.description = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("name")) {
				this.name = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("icon")) {
				this.icon = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("URL")) {
				this.URL = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("order")) {
				this.order = nd.getBody().trim();
			}
			if (nd.getTagName().equalsIgnoreCase("type")) {
				this.type = nd.getBody().trim();
			}
		}
		if (ObjectUtil.empty(this.ID)) {
			throw new PluginException("menu's id is empty!");
		}
		if (ObjectUtil.empty(this.name))
			throw new PluginException("menu's name is empty!");
	}

	public PluginConfig getPluginConfig() {
		return this.config;
	}

	public String getID() {
		return this.ID;
	}

	public String getParentID() {
		return this.parentID;
	}

	public String getName() {
		return this.name;
	}

	public String getURL() {
		return this.URL;
	}

	public String getType() {
		return this.type;
	}

	public String getDescription() {
		return this.description;
	}

	public String getIcon() {
		return this.icon;
	}

	public String getOrder() {
		return this.order;
	}

	public void setPluginConfig(PluginConfig pc) {
		this.config = pc;
	}

	public void setID(String id) {
		this.ID = id;
	}

	public void setParentID(String parentId) {
		this.parentID = parentId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setURL(String uRL) {
		this.URL = uRL;
	}

	public void setType(String type) {
		this.type = type;
	}
}

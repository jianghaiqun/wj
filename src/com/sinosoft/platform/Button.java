package com.sinosoft.platform;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZDButtonSchema;
import com.sinosoft.schema.ZDButtonSet;

import java.util.Date;

public class Button extends Page {
	public static Mapx	ButtonCacheMap	= new Mapx();

	static {
		updateCache();
	}

	/**
	 * 更新按钮缓存
	 */
	@SuppressWarnings("unchecked")
	public static void updateCache() {
		String sql = "select * from zdbutton order by Code,OrderFlag";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		ZDButtonSet tZDButtonSet = new ZDButtonSet();
		String lastUrl = "";
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDButtonSchema tZDButtonSchema = new ZDButtonSchema();
			tZDButtonSchema.setValue(dt.get(i));
			if (i == 0) {
				lastUrl = dt.getString(i, "Code");
			}

			if (dt.getString(i, "Code").equals(lastUrl)) {
				tZDButtonSet.add(tZDButtonSchema);

			} else {

				ButtonCacheMap.put(lastUrl, tZDButtonSet.toDataTable());
				lastUrl = dt.getString(i, "Code");
				tZDButtonSet = new ZDButtonSet();
				tZDButtonSet.add(tZDButtonSchema);
			}

			if (i == dt.getRowCount() - 1) {
				ButtonCacheMap.put(dt.getString(i, "Code"), tZDButtonSet.toDataTable());
			}

		}
	}

	/**
	 * 查询菜单与按钮
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String sqlMenu = "select ID,ParentID,Name,Icon,'' as Code,Addtime,Memo, '' as TreeLevel,Type,'0' as 'TT','' URL,'' OnClick from ZDMenu where Visiable='Y' order by OrderFlag,id";
		DataTable dt = new QueryBuilder(sqlMenu).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("2".equals(dt.get(i, "Type"))) {
				dt.set(i, "TreeLevel", "1");
			} else {
				dt.set(i, "TreeLevel", "0");
			}
		}

		String sqlButton = "select ID,ParentID,Name, Icon ,Code ,Addtime,Memo, '3' as TreeLevel,'','1' as 'TT', URL ,OnClick from ZDButton order by OrderFlag,id";
		DataTable dtButton = new QueryBuilder(sqlButton).executeDataTable();

		DataTable dtResult = new DataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			Object ID = dt.get(i, "ID");
			dt.set(i, "ID", ID);
			dtResult.insertRow(dt.get(i));
			if (!"0".equals(dt.get(i, "ParentID"))) {
				for (int j = 0; j < dtButton.getRowCount(); j++) {
					if (ID.equals(dtButton.get(j, "ParentID"))) {
						DataRow dr = dtButton.get(j);
						dtResult.insertRow(dr);
					}
				}
			}
		}
		dga.bindData(dtResult);
	}

	/**
	 * 按钮菜单页面初始化
	 */
	@SuppressWarnings("unchecked")
	public static Mapx init(Mapx params) {
		String ID = params.getString("ID");
		String OperType = params.getString("OperType");
		if (StringUtil.isNotEmpty(ID)) {
			ZDButtonSchema tZDButton = new ZDButtonSchema();
			tZDButton.setID(ID);
			if (tZDButton.fill()) {
				params = tZDButton.toMapx();
				String URLID = Menu.MenuCacheMap.getString(tZDButton.getURL());
				DataTable dt = new QueryBuilder("select Name,ID,ParentID from ZDMenu where ParentID=0 order by OrderFlag,id").executeDataTable();

				if (StringUtil.isNotEmpty(URLID)) {
					String sql = "select  Name,ID,ParentID from ZDMenu where (parentid in(select parentid from ZDMenu where   visiable='Y' and id=? ) ) and visiable='Y' order by OrderFlag,id";
					DataTable dtURLID = new QueryBuilder(sql, URLID).executeDataTable();
					String parentID = "";
					if (dtURLID != null && dtURLID.getRowCount() >= 1) {
						parentID = dtURLID.getString(0, "ParentID");
					}
					params.put("Parent_ID", parentID);

					params.put("TopMenu", HtmlUtil.dataTableToOptions(dt, parentID, true));
					params.put("ParentMenu", HtmlUtil.dataTableToOptions(dtURLID, URLID));
				} else {
					params.put("TopMenu", HtmlUtil.dataTableToOptions(dt, true));
				}

				params.put("TypeList", HtmlUtil.codeToOptions("ButtonType", tZDButton.getType()));
			}

		} else {
			DataTable dt = new QueryBuilder("select name,id from zdmenu where ParentID=0 order by OrderFlag,id").executeDataTable();
			params.put("TopMenu", HtmlUtil.dataTableToOptions(dt, true));
			params.put("TypeList", HtmlUtil.codeToOptions("ButtonType"));
			params.put("Icon", "Icons/icon018a4.gif");
			
		}
		params.put("OperType", OperType);
		return params;
	}

	/**
	 * 添加菜单按钮
	 */
	public void save() {
		try {
			long ID = 0;
			ZDButtonSchema tZDButton = new ZDButtonSchema();
			String OPR = $V("OperType");
			if (StringUtil.isEmpty(OPR)) {
				Response.setLogInfo(0, "保存失败，操作类型为空!");
				return;
			}
			if (StringUtil.isEmpty($V("Icon"))) {
				Response.setLogInfo(0, "保存失败，图标不能为空!");
				return;
			}
			if ("MODIFY".equals(OPR) && StringUtil.isNotEmpty($V("ID"))) {
				ID = Long.parseLong($V("ID"));

			} else {
				ID =2014000+ NoUtil.getMaxID("ZDButtonID");
			}
			tZDButton.setID(ID);
			tZDButton.fill();
			tZDButton.setValue(Request);
			tZDButton.setIcon($V("Icon").substring($V("Icon").indexOf("Icons/")));
			String URL = $V("URL");
			if (StringUtil.isEmpty(URL)) {
				Response.setLogInfo(0, "保存失败，操作数据库时发生错误!(路径为空)");
				return;
			}
			String Code = URL.substring(0, URL.indexOf("."));
			Code = Code.replace("/", "_");
			if (!Code.startsWith("_")) {
				Code = "_" + Code + "_Button";
			}
			tZDButton.setCode(Code);
			boolean result = false;
			if (!"MODIFY".equals(OPR)) {
				tZDButton.setAddTime(new Date());
				tZDButton.setAddUser(User.getUserName());
				tZDButton.setOrderFlag(System.currentTimeMillis());
				result = tZDButton.insert();
			} else {
				tZDButton.setModifyTime(new Date());
				tZDButton.setModifyUser(User.getUserName());
				result = tZDButton.update();
			}
			if (result) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
			}
			updateCache();
		} catch (Exception e) {
			Response.setStatus(0);
			Response.setMessage("保存失败，操作数据库时发生错误!" + e.getMessage());
		}
	}

	/**
	 * 根据菜单id获取菜单路径
	 */
	public void getCode() {
		String ParentID = $V("ParentID");
		if (StringUtil.isNotEmpty(ParentID)) {
			QueryBuilder qb = new QueryBuilder("select URL from zdmenu where ID=?");
			qb.add(ParentID);
			String URL = qb.executeOneValue() + "";
			if (StringUtil.isNotEmpty(URL)) {
				Response.setMessage(URL);
			}
		}
	}

	/**
	 * 删除
	 */
	public void del() {
		try {
			String ids = $V("IDs");
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}

			if (ids.endsWith(",")) {
				ids = ids.substring(0, ids.length() - 1);
			}

			ZDButtonSchema button = new ZDButtonSchema();
			ZDButtonSet set = button.query(new QueryBuilder("where id in (" + ids + ")"));
			if (set.deleteAndBackup()) {
				Response.setStatus(1);
				Response.setMessage("删除成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("删除失败，操作数据库时发生错误!");
			}
			updateCache();
		} catch (Exception e) {
			Response.setStatus(0);
			Response.setMessage("删除失败，操作数据库时发生错误!");
		}
	}
}

package com.sinosoft.platform.service;

import java.util.ArrayList;

import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.extend.AbstractExtendService;
import com.sinosoft.framework.extend.menu.Menu;
import com.sinosoft.framework.menu.MenuManager;
import com.sinosoft.framework.utility.ObjectUtil;
import com.sinosoft.platform.privilege.AbstractMenuPriv;

public class MenuPrivService extends AbstractExtendService<AbstractMenuPriv> {
	public static MenuPrivService getInstance() {
		return (MenuPrivService) findInstance(MenuPrivService.class);
	}

	public static DataTable getAllMenus() {
		ArrayList<Menu> list = MenuManager.getMenus();
		DataTable dt = new DataTable();
		dt.insertColumn("ID");
		dt.insertColumn("ParentID");
		dt.insertColumn("Name");
		dt.insertColumn("Icon");
		dt.insertColumn("URL");
		dt.insertColumn("Order");
		dt.insertColumn("Description");
		dt.insertColumn("PluginID");
		dt.insertColumn("PluginName");
		dt.insertColumn("HasChild");
		for (Menu menu : list) {
			if (!"Backend".equals(menu.getType())) {
				continue;
			}
			dt.insertRow(new Object[] { menu.getID(), menu.getParentID(), menu.getName(), menu.getIcon(), menu.getURL(), menu.getOrder(), menu.getDescription(),
					menu.getPluginConfig().getID(), menu.getPluginConfig().getName(), Boolean.valueOf(hasChild(menu.getID())) });
		}
		dt.sort("Order", "asc");
		dt = DataGridAction.sortTreeDataTable(dt, "ID", "ParentID");
		return dt;
	}

	public static boolean hasChild(String menuID) {
		ArrayList<Menu> list = MenuManager.getMenus();
		for (Menu menu : list) {
			if ((menu.getParentID() != null) && (menu.getParentID().equals(menuID))) {
				return true;
			}
		}
		return false;
	}

	public static DataTable getChildMenus(String parentID) {
		ArrayList<Menu> list = MenuManager.getMenus();
		DataTable dt = new DataTable();
		dt.insertColumn("ID");
		dt.insertColumn("ParentID");
		dt.insertColumn("Name");
		dt.insertColumn("Icon");
		dt.insertColumn("URL");
		dt.insertColumn("Order");
		dt.insertColumn("Description");
		dt.insertColumn("PluginID");
		dt.insertColumn("PluginName");
		dt.insertColumn("HasChild");
		for (Menu menu : list) {
			if ((menu.getParentID() == null) || (!menu.getParentID().equals(parentID))) {
				continue;
			}
			dt.insertRow(new Object[] { menu.getID(), menu.getParentID(), menu.getName(), menu.getIcon(), menu.getURL(), menu.getOrder(), menu.getDescription(),
					menu.getPluginConfig().getID(), menu.getPluginConfig().getName(), Boolean.valueOf(hasChild(menu.getID())) });
		}
		dt.sort("Order", "asc");
		return dt;
	}

	public static DataTable getMainMenus() {
		ArrayList<Menu> list = MenuManager.getMenus();
		DataTable dt = new DataTable();
		dt.insertColumn("ID");
		dt.insertColumn("ParentID");
		dt.insertColumn("Name");
		dt.insertColumn("Icon");
		dt.insertColumn("URL");
		dt.insertColumn("Order");
		dt.insertColumn("Description");
		dt.insertColumn("PluginID");
		dt.insertColumn("PluginName");
		dt.insertColumn("HasChild");
		for (Menu menu : list) {
			if (!ObjectUtil.empty(menu.getParentID())) {
				continue;
			}
			dt.insertRow(new Object[] { menu.getID(), menu.getParentID(), menu.getName(), menu.getIcon(), menu.getURL(), menu.getOrder(), menu.getDescription(),
					menu.getPluginConfig().getID(), menu.getPluginConfig().getName(), Boolean.valueOf(hasChild(menu.getID())) });
		}
		dt.sort("Order", "asc");
		return dt;
	}
}
package com.sinosoft.platform.privilege;

import com.sinosoft.framework.extend.IExtendItem;
import com.sinosoft.framework.menu.MenuManager;
import com.sinosoft.framework.utility.Mapx;

public class AbstractMenuPriv implements IExtendItem {
	private String MenuID;
	private Mapx<String, String> PrivItems = new Mapx<String, String>();
	private String Memo;

	public AbstractMenuPriv(String MenuID, String Memo) {
		this.Memo = Memo;
		this.MenuID = MenuID;
	}

	public String getID() {
		return this.MenuID;
	}

	public void addItem(String itemID, String name) {
		this.PrivItems.put(itemID, name);
	}

	public Mapx<String, String> getPrivItems() {
		return this.PrivItems;
	}

	public String getName() {
		return MenuManager.getMenu(this.MenuID).getName();
	}

	public String getMemo() {
		return this.Memo;
	}
}
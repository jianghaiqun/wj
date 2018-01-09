package com.sinosoft.schema;

import com.sinosoft.schema.MenuDefineSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class MenuDefineSet extends SchemaSet {
	public MenuDefineSet() {
		this(10,0);
	}

	public MenuDefineSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public MenuDefineSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = MenuDefineSchema._TableCode;
		Columns = MenuDefineSchema._Columns;
		NameSpace = MenuDefineSchema._NameSpace;
		InsertAllSQL = MenuDefineSchema._InsertAllSQL;
		UpdateAllSQL = MenuDefineSchema._UpdateAllSQL;
		FillAllSQL = MenuDefineSchema._FillAllSQL;
		DeleteSQL = MenuDefineSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new MenuDefineSet();
	}

	public boolean add(MenuDefineSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(MenuDefineSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(MenuDefineSchema aSchema) {
		return super.remove(aSchema);
	}

	public MenuDefineSchema get(int index) {
		MenuDefineSchema tSchema = (MenuDefineSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, MenuDefineSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(MenuDefineSet aSet) {
		return super.set(aSet);
	}
}
 
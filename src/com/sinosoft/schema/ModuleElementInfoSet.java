package com.sinosoft.schema;

import com.sinosoft.schema.ModuleElementInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ModuleElementInfoSet extends SchemaSet {
	public ModuleElementInfoSet() {
		this(10,0);
	}

	public ModuleElementInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ModuleElementInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ModuleElementInfoSchema._TableCode;
		Columns = ModuleElementInfoSchema._Columns;
		NameSpace = ModuleElementInfoSchema._NameSpace;
		InsertAllSQL = ModuleElementInfoSchema._InsertAllSQL;
		UpdateAllSQL = ModuleElementInfoSchema._UpdateAllSQL;
		FillAllSQL = ModuleElementInfoSchema._FillAllSQL;
		DeleteSQL = ModuleElementInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ModuleElementInfoSet();
	}

	public boolean add(ModuleElementInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ModuleElementInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ModuleElementInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ModuleElementInfoSchema get(int index) {
		ModuleElementInfoSchema tSchema = (ModuleElementInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ModuleElementInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ModuleElementInfoSet aSet) {
		return super.set(aSet);
	}
}
 
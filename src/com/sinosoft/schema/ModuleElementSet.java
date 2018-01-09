package com.sinosoft.schema;

import com.sinosoft.schema.ModuleElementSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ModuleElementSet extends SchemaSet {
	public ModuleElementSet() {
		this(10,0);
	}

	public ModuleElementSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ModuleElementSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ModuleElementSchema._TableCode;
		Columns = ModuleElementSchema._Columns;
		NameSpace = ModuleElementSchema._NameSpace;
		InsertAllSQL = ModuleElementSchema._InsertAllSQL;
		UpdateAllSQL = ModuleElementSchema._UpdateAllSQL;
		FillAllSQL = ModuleElementSchema._FillAllSQL;
		DeleteSQL = ModuleElementSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ModuleElementSet();
	}

	public boolean add(ModuleElementSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ModuleElementSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ModuleElementSchema aSchema) {
		return super.remove(aSchema);
	}

	public ModuleElementSchema get(int index) {
		ModuleElementSchema tSchema = (ModuleElementSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ModuleElementSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ModuleElementSet aSet) {
		return super.set(aSet);
	}
}
 
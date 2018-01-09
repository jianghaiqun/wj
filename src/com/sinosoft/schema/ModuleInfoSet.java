package com.sinosoft.schema;

import com.sinosoft.schema.ModuleInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ModuleInfoSet extends SchemaSet {
	public ModuleInfoSet() {
		this(10,0);
	}

	public ModuleInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ModuleInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ModuleInfoSchema._TableCode;
		Columns = ModuleInfoSchema._Columns;
		NameSpace = ModuleInfoSchema._NameSpace;
		InsertAllSQL = ModuleInfoSchema._InsertAllSQL;
		UpdateAllSQL = ModuleInfoSchema._UpdateAllSQL;
		FillAllSQL = ModuleInfoSchema._FillAllSQL;
		DeleteSQL = ModuleInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ModuleInfoSet();
	}

	public boolean add(ModuleInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ModuleInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ModuleInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public ModuleInfoSchema get(int index) {
		ModuleInfoSchema tSchema = (ModuleInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ModuleInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ModuleInfoSet aSet) {
		return super.set(aSet);
	}
}
 
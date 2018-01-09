package com.sinosoft.schema;

import com.sinosoft.schema.SDMarketModuleSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDMarketModuleSet extends SchemaSet {
	public SDMarketModuleSet() {
		this(10,0);
	}

	public SDMarketModuleSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDMarketModuleSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDMarketModuleSchema._TableCode;
		Columns = SDMarketModuleSchema._Columns;
		NameSpace = SDMarketModuleSchema._NameSpace;
		InsertAllSQL = SDMarketModuleSchema._InsertAllSQL;
		UpdateAllSQL = SDMarketModuleSchema._UpdateAllSQL;
		FillAllSQL = SDMarketModuleSchema._FillAllSQL;
		DeleteSQL = SDMarketModuleSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDMarketModuleSet();
	}

	public boolean add(SDMarketModuleSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDMarketModuleSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDMarketModuleSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDMarketModuleSchema get(int index) {
		SDMarketModuleSchema tSchema = (SDMarketModuleSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDMarketModuleSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDMarketModuleSet aSet) {
		return super.set(aSet);
	}
}
 
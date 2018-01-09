package com.sinosoft.schema;

import com.sinosoft.schema.LRBOMSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LRBOMSet extends SchemaSet {
	public LRBOMSet() {
		this(10,0);
	}

	public LRBOMSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LRBOMSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LRBOMSchema._TableCode;
		Columns = LRBOMSchema._Columns;
		NameSpace = LRBOMSchema._NameSpace;
		InsertAllSQL = LRBOMSchema._InsertAllSQL;
		UpdateAllSQL = LRBOMSchema._UpdateAllSQL;
		FillAllSQL = LRBOMSchema._FillAllSQL;
		DeleteSQL = LRBOMSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LRBOMSet();
	}

	public boolean add(LRBOMSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LRBOMSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LRBOMSchema aSchema) {
		return super.remove(aSchema);
	}

	public LRBOMSchema get(int index) {
		LRBOMSchema tSchema = (LRBOMSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LRBOMSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LRBOMSet aSet) {
		return super.set(aSet);
	}
}
 
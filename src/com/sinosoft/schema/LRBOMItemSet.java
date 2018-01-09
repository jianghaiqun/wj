package com.sinosoft.schema;

import com.sinosoft.schema.LRBOMItemSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LRBOMItemSet extends SchemaSet {
	public LRBOMItemSet() {
		this(10,0);
	}

	public LRBOMItemSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LRBOMItemSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LRBOMItemSchema._TableCode;
		Columns = LRBOMItemSchema._Columns;
		NameSpace = LRBOMItemSchema._NameSpace;
		InsertAllSQL = LRBOMItemSchema._InsertAllSQL;
		UpdateAllSQL = LRBOMItemSchema._UpdateAllSQL;
		FillAllSQL = LRBOMItemSchema._FillAllSQL;
		DeleteSQL = LRBOMItemSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LRBOMItemSet();
	}

	public boolean add(LRBOMItemSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LRBOMItemSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LRBOMItemSchema aSchema) {
		return super.remove(aSchema);
	}

	public LRBOMItemSchema get(int index) {
		LRBOMItemSchema tSchema = (LRBOMItemSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LRBOMItemSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LRBOMItemSet aSet) {
		return super.set(aSet);
	}
}
 
package com.sinosoft.schema;

import com.sinosoft.schema.LRCommandSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LRCommandSet extends SchemaSet {
	public LRCommandSet() {
		this(10,0);
	}

	public LRCommandSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LRCommandSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LRCommandSchema._TableCode;
		Columns = LRCommandSchema._Columns;
		NameSpace = LRCommandSchema._NameSpace;
		InsertAllSQL = LRCommandSchema._InsertAllSQL;
		UpdateAllSQL = LRCommandSchema._UpdateAllSQL;
		FillAllSQL = LRCommandSchema._FillAllSQL;
		DeleteSQL = LRCommandSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LRCommandSet();
	}

	public boolean add(LRCommandSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LRCommandSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LRCommandSchema aSchema) {
		return super.remove(aSchema);
	}

	public LRCommandSchema get(int index) {
		LRCommandSchema tSchema = (LRCommandSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LRCommandSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LRCommandSet aSet) {
		return super.set(aSet);
	}
}
 
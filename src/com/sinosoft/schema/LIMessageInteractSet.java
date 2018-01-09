package com.sinosoft.schema;

import com.sinosoft.schema.LIMessageInteractSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class LIMessageInteractSet extends SchemaSet {
	public LIMessageInteractSet() {
		this(10,0);
	}

	public LIMessageInteractSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public LIMessageInteractSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = LIMessageInteractSchema._TableCode;
		Columns = LIMessageInteractSchema._Columns;
		NameSpace = LIMessageInteractSchema._NameSpace;
		InsertAllSQL = LIMessageInteractSchema._InsertAllSQL;
		UpdateAllSQL = LIMessageInteractSchema._UpdateAllSQL;
		FillAllSQL = LIMessageInteractSchema._FillAllSQL;
		DeleteSQL = LIMessageInteractSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new LIMessageInteractSet();
	}

	public boolean add(LIMessageInteractSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(LIMessageInteractSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(LIMessageInteractSchema aSchema) {
		return super.remove(aSchema);
	}

	public LIMessageInteractSchema get(int index) {
		LIMessageInteractSchema tSchema = (LIMessageInteractSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, LIMessageInteractSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(LIMessageInteractSet aSet) {
		return super.set(aSet);
	}
}
 
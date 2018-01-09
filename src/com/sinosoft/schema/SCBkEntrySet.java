package com.sinosoft.schema;

import com.sinosoft.schema.SCBkEntrySchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SCBkEntrySet extends SchemaSet {
	public SCBkEntrySet() {
		this(10,0);
	}

	public SCBkEntrySet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SCBkEntrySet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SCBkEntrySchema._TableCode;
		Columns = SCBkEntrySchema._Columns;
		NameSpace = SCBkEntrySchema._NameSpace;
		InsertAllSQL = SCBkEntrySchema._InsertAllSQL;
		UpdateAllSQL = SCBkEntrySchema._UpdateAllSQL;
		FillAllSQL = SCBkEntrySchema._FillAllSQL;
		DeleteSQL = SCBkEntrySchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SCBkEntrySet();
	}

	public boolean add(SCBkEntrySchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SCBkEntrySet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SCBkEntrySchema aSchema) {
		return super.remove(aSchema);
	}

	public SCBkEntrySchema get(int index) {
		SCBkEntrySchema tSchema = (SCBkEntrySchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SCBkEntrySchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SCBkEntrySet aSet) {
		return super.set(aSet);
	}
}
 
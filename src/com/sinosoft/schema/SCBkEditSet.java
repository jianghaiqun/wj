package com.sinosoft.schema;

import com.sinosoft.schema.SCBkEditSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SCBkEditSet extends SchemaSet {
	public SCBkEditSet() {
		this(10,0);
	}

	public SCBkEditSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SCBkEditSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SCBkEditSchema._TableCode;
		Columns = SCBkEditSchema._Columns;
		NameSpace = SCBkEditSchema._NameSpace;
		InsertAllSQL = SCBkEditSchema._InsertAllSQL;
		UpdateAllSQL = SCBkEditSchema._UpdateAllSQL;
		FillAllSQL = SCBkEditSchema._FillAllSQL;
		DeleteSQL = SCBkEditSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SCBkEditSet();
	}

	public boolean add(SCBkEditSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SCBkEditSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SCBkEditSchema aSchema) {
		return super.remove(aSchema);
	}

	public SCBkEditSchema get(int index) {
		SCBkEditSchema tSchema = (SCBkEditSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SCBkEditSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SCBkEditSet aSet) {
		return super.set(aSet);
	}
}
 
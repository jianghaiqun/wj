package com.sinosoft.schema;

import com.sinosoft.schema.SCFAQSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SCFAQSet extends SchemaSet {
	public SCFAQSet() {
		this(10,0);
	}

	public SCFAQSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SCFAQSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SCFAQSchema._TableCode;
		Columns = SCFAQSchema._Columns;
		NameSpace = SCFAQSchema._NameSpace;
		InsertAllSQL = SCFAQSchema._InsertAllSQL;
		UpdateAllSQL = SCFAQSchema._UpdateAllSQL;
		FillAllSQL = SCFAQSchema._FillAllSQL;
		DeleteSQL = SCFAQSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SCFAQSet();
	}

	public boolean add(SCFAQSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SCFAQSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SCFAQSchema aSchema) {
		return super.remove(aSchema);
	}

	public SCFAQSchema get(int index) {
		SCFAQSchema tSchema = (SCFAQSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SCFAQSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SCFAQSet aSet) {
		return super.set(aSet);
	}
}
 
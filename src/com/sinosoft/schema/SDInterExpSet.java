package com.sinosoft.schema;

import com.sinosoft.schema.SDInterExpSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDInterExpSet extends SchemaSet {
	public SDInterExpSet() {
		this(10,0);
	}

	public SDInterExpSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDInterExpSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDInterExpSchema._TableCode;
		Columns = SDInterExpSchema._Columns;
		NameSpace = SDInterExpSchema._NameSpace;
		InsertAllSQL = SDInterExpSchema._InsertAllSQL;
		UpdateAllSQL = SDInterExpSchema._UpdateAllSQL;
		FillAllSQL = SDInterExpSchema._FillAllSQL;
		DeleteSQL = SDInterExpSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDInterExpSet();
	}

	public boolean add(SDInterExpSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDInterExpSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDInterExpSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDInterExpSchema get(int index) {
		SDInterExpSchema tSchema = (SDInterExpSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDInterExpSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDInterExpSet aSet) {
		return super.set(aSet);
	}
}
 
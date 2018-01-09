package com.sinosoft.schema;

import com.sinosoft.schema.SDActivityPayamountSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDActivityPayamountSet extends SchemaSet {
	public SDActivityPayamountSet() {
		this(10,0);
	}

	public SDActivityPayamountSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDActivityPayamountSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDActivityPayamountSchema._TableCode;
		Columns = SDActivityPayamountSchema._Columns;
		NameSpace = SDActivityPayamountSchema._NameSpace;
		InsertAllSQL = SDActivityPayamountSchema._InsertAllSQL;
		UpdateAllSQL = SDActivityPayamountSchema._UpdateAllSQL;
		FillAllSQL = SDActivityPayamountSchema._FillAllSQL;
		DeleteSQL = SDActivityPayamountSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDActivityPayamountSet();
	}

	public boolean add(SDActivityPayamountSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDActivityPayamountSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDActivityPayamountSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDActivityPayamountSchema get(int index) {
		SDActivityPayamountSchema tSchema = (SDActivityPayamountSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDActivityPayamountSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDActivityPayamountSet aSet) {
		return super.set(aSet);
	}
}
 
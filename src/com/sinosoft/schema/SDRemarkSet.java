package com.sinosoft.schema;

import com.sinosoft.schema.SDRemarkSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDRemarkSet extends SchemaSet {
	public SDRemarkSet() {
		this(10,0);
	}

	public SDRemarkSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDRemarkSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDRemarkSchema._TableCode;
		Columns = SDRemarkSchema._Columns;
		NameSpace = SDRemarkSchema._NameSpace;
		InsertAllSQL = SDRemarkSchema._InsertAllSQL;
		UpdateAllSQL = SDRemarkSchema._UpdateAllSQL;
		FillAllSQL = SDRemarkSchema._FillAllSQL;
		DeleteSQL = SDRemarkSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDRemarkSet();
	}

	public boolean add(SDRemarkSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDRemarkSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDRemarkSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDRemarkSchema get(int index) {
		SDRemarkSchema tSchema = (SDRemarkSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDRemarkSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDRemarkSet aSet) {
		return super.set(aSet);
	}
}
 
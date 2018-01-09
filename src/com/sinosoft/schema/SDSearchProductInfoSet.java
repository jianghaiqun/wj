package com.sinosoft.schema;

import com.sinosoft.schema.SDSearchProductInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDSearchProductInfoSet extends SchemaSet {
	public SDSearchProductInfoSet() {
		this(10,0);
	}

	public SDSearchProductInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDSearchProductInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDSearchProductInfoSchema._TableCode;
		Columns = SDSearchProductInfoSchema._Columns;
		NameSpace = SDSearchProductInfoSchema._NameSpace;
		InsertAllSQL = SDSearchProductInfoSchema._InsertAllSQL;
		UpdateAllSQL = SDSearchProductInfoSchema._UpdateAllSQL;
		FillAllSQL = SDSearchProductInfoSchema._FillAllSQL;
		DeleteSQL = SDSearchProductInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDSearchProductInfoSet();
	}

	public boolean add(SDSearchProductInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDSearchProductInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDSearchProductInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDSearchProductInfoSchema get(int index) {
		SDSearchProductInfoSchema tSchema = (SDSearchProductInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDSearchProductInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDSearchProductInfoSet aSet) {
		return super.set(aSet);
	}
}
 
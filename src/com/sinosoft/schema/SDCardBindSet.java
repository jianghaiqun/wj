package com.sinosoft.schema;

import com.sinosoft.schema.SDCardBindSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDCardBindSet extends SchemaSet {
	public SDCardBindSet() {
		this(10,0);
	}

	public SDCardBindSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDCardBindSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDCardBindSchema._TableCode;
		Columns = SDCardBindSchema._Columns;
		NameSpace = SDCardBindSchema._NameSpace;
		InsertAllSQL = SDCardBindSchema._InsertAllSQL;
		UpdateAllSQL = SDCardBindSchema._UpdateAllSQL;
		FillAllSQL = SDCardBindSchema._FillAllSQL;
		DeleteSQL = SDCardBindSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDCardBindSet();
	}

	public boolean add(SDCardBindSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDCardBindSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDCardBindSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDCardBindSchema get(int index) {
		SDCardBindSchema tSchema = (SDCardBindSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDCardBindSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDCardBindSet aSet) {
		return super.set(aSet);
	}
}
 
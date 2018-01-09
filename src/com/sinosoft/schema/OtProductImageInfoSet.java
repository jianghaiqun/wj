package com.sinosoft.schema;

import com.sinosoft.schema.OtProductImageInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class OtProductImageInfoSet extends SchemaSet {
	public OtProductImageInfoSet() {
		this(10,0);
	}

	public OtProductImageInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public OtProductImageInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = OtProductImageInfoSchema._TableCode;
		Columns = OtProductImageInfoSchema._Columns;
		NameSpace = OtProductImageInfoSchema._NameSpace;
		InsertAllSQL = OtProductImageInfoSchema._InsertAllSQL;
		UpdateAllSQL = OtProductImageInfoSchema._UpdateAllSQL;
		FillAllSQL = OtProductImageInfoSchema._FillAllSQL;
		DeleteSQL = OtProductImageInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new OtProductImageInfoSet();
	}

	public boolean add(OtProductImageInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(OtProductImageInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(OtProductImageInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public OtProductImageInfoSchema get(int index) {
		OtProductImageInfoSchema tSchema = (OtProductImageInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, OtProductImageInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(OtProductImageInfoSet aSet) {
		return super.set(aSet);
	}
}
 
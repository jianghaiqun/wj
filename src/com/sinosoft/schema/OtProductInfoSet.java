package com.sinosoft.schema;

import com.sinosoft.schema.OtProductInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class OtProductInfoSet extends SchemaSet {
	public OtProductInfoSet() { 
		this(10,0);
	}

	public OtProductInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public OtProductInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = OtProductInfoSchema._TableCode;
		Columns = OtProductInfoSchema._Columns;
		NameSpace = OtProductInfoSchema._NameSpace;
		InsertAllSQL = OtProductInfoSchema._InsertAllSQL;
		UpdateAllSQL = OtProductInfoSchema._UpdateAllSQL;
		FillAllSQL = OtProductInfoSchema._FillAllSQL;
		DeleteSQL = OtProductInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new OtProductInfoSet();
	}

	public boolean add(OtProductInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(OtProductInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(OtProductInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public OtProductInfoSchema get(int index) {
		OtProductInfoSchema tSchema = (OtProductInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, OtProductInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(OtProductInfoSet aSet) {
		return super.set(aSet);
	}
}
 
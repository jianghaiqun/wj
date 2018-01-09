package com.sinosoft.schema;

import com.sinosoft.schema.OtOrderInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class OtOrderInfoSet extends SchemaSet {
	public OtOrderInfoSet() {
		this(10,0);
	} 

	public OtOrderInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public OtOrderInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = OtOrderInfoSchema._TableCode;
		Columns = OtOrderInfoSchema._Columns;
		NameSpace = OtOrderInfoSchema._NameSpace;
		InsertAllSQL = OtOrderInfoSchema._InsertAllSQL;
		UpdateAllSQL = OtOrderInfoSchema._UpdateAllSQL;
		FillAllSQL = OtOrderInfoSchema._FillAllSQL;
		DeleteSQL = OtOrderInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new OtOrderInfoSet();
	}

	public boolean add(OtOrderInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(OtOrderInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(OtOrderInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public OtOrderInfoSchema get(int index) {
		OtOrderInfoSchema tSchema = (OtOrderInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, OtOrderInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(OtOrderInfoSet aSet) {
		return super.set(aSet);
	}
}
 
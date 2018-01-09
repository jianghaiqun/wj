package com.sinosoft.schema;

import com.sinosoft.schema.OtProductLabelInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class OtProductLabelInfoSet extends SchemaSet {
	public OtProductLabelInfoSet() {
		this(10,0);
	}

	public OtProductLabelInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public OtProductLabelInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = OtProductLabelInfoSchema._TableCode;
		Columns = OtProductLabelInfoSchema._Columns;
		NameSpace = OtProductLabelInfoSchema._NameSpace;
		InsertAllSQL = OtProductLabelInfoSchema._InsertAllSQL;
		UpdateAllSQL = OtProductLabelInfoSchema._UpdateAllSQL;
		FillAllSQL = OtProductLabelInfoSchema._FillAllSQL;
		DeleteSQL = OtProductLabelInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new OtProductLabelInfoSet();
	}

	public boolean add(OtProductLabelInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(OtProductLabelInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(OtProductLabelInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public OtProductLabelInfoSchema get(int index) {
		OtProductLabelInfoSchema tSchema = (OtProductLabelInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, OtProductLabelInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(OtProductLabelInfoSet aSet) {
		return super.set(aSet);
	}
}
 
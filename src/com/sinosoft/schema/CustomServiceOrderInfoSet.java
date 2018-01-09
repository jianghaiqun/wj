package com.sinosoft.schema;

import com.sinosoft.schema.CustomServiceOrderInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class CustomServiceOrderInfoSet extends SchemaSet {
	public CustomServiceOrderInfoSet() {
		this(10,0);
	}

	public CustomServiceOrderInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public CustomServiceOrderInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = CustomServiceOrderInfoSchema._TableCode;
		Columns = CustomServiceOrderInfoSchema._Columns;
		NameSpace = CustomServiceOrderInfoSchema._NameSpace;
		InsertAllSQL = CustomServiceOrderInfoSchema._InsertAllSQL;
		UpdateAllSQL = CustomServiceOrderInfoSchema._UpdateAllSQL;
		FillAllSQL = CustomServiceOrderInfoSchema._FillAllSQL;
		DeleteSQL = CustomServiceOrderInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new CustomServiceOrderInfoSet();
	}

	public boolean add(CustomServiceOrderInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(CustomServiceOrderInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(CustomServiceOrderInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public CustomServiceOrderInfoSchema get(int index) {
		CustomServiceOrderInfoSchema tSchema = (CustomServiceOrderInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, CustomServiceOrderInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(CustomServiceOrderInfoSet aSet) {
		return super.set(aSet);
	}
}
 
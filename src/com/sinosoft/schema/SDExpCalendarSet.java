package com.sinosoft.schema;

import com.sinosoft.schema.SDExpCalendarSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDExpCalendarSet extends SchemaSet {
	public SDExpCalendarSet() {
		this(10,0);
	}

	public SDExpCalendarSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDExpCalendarSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDExpCalendarSchema._TableCode;
		Columns = SDExpCalendarSchema._Columns;
		NameSpace = SDExpCalendarSchema._NameSpace;
		InsertAllSQL = SDExpCalendarSchema._InsertAllSQL;
		UpdateAllSQL = SDExpCalendarSchema._UpdateAllSQL;
		FillAllSQL = SDExpCalendarSchema._FillAllSQL;
		DeleteSQL = SDExpCalendarSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDExpCalendarSet();
	}

	public boolean add(SDExpCalendarSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDExpCalendarSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDExpCalendarSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDExpCalendarSchema get(int index) {
		SDExpCalendarSchema tSchema = (SDExpCalendarSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDExpCalendarSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDExpCalendarSet aSet) {
		return super.set(aSet);
	}
}
 
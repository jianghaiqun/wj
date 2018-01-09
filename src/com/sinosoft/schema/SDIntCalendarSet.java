package com.sinosoft.schema;

import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class SDIntCalendarSet extends SchemaSet { 
	public SDIntCalendarSet() {
		this(10,0);
	}

	public SDIntCalendarSet(int initialCapacity) { 
		this(initialCapacity,0);
	}

	public SDIntCalendarSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDIntCalendarSchema._TableCode;
		Columns = SDIntCalendarSchema._Columns;
		NameSpace = SDIntCalendarSchema._NameSpace;
		InsertAllSQL = SDIntCalendarSchema._InsertAllSQL;
		UpdateAllSQL = SDIntCalendarSchema._UpdateAllSQL;
		FillAllSQL = SDIntCalendarSchema._FillAllSQL;
		DeleteSQL = SDIntCalendarSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDIntCalendarSet();
	}

	public boolean add(SDIntCalendarSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDIntCalendarSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDIntCalendarSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDIntCalendarSchema get(int index) {
		SDIntCalendarSchema tSchema = (SDIntCalendarSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDIntCalendarSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDIntCalendarSet aSet) {
		return super.set(aSet);
	}
}
 
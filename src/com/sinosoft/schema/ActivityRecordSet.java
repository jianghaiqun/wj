package com.sinosoft.schema;

import com.sinosoft.schema.ActivityRecordSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ActivityRecordSet extends SchemaSet {
	public ActivityRecordSet() {
		this(10,0);
	}

	public ActivityRecordSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ActivityRecordSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ActivityRecordSchema._TableCode;
		Columns = ActivityRecordSchema._Columns;
		NameSpace = ActivityRecordSchema._NameSpace;
		InsertAllSQL = ActivityRecordSchema._InsertAllSQL;
		UpdateAllSQL = ActivityRecordSchema._UpdateAllSQL;
		FillAllSQL = ActivityRecordSchema._FillAllSQL;
		DeleteSQL = ActivityRecordSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ActivityRecordSet();
	}

	public boolean add(ActivityRecordSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ActivityRecordSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ActivityRecordSchema aSchema) {
		return super.remove(aSchema);
	}

	public ActivityRecordSchema get(int index) {
		ActivityRecordSchema tSchema = (ActivityRecordSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ActivityRecordSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ActivityRecordSet aSet) {
		return super.set(aSet);
	}
}
 
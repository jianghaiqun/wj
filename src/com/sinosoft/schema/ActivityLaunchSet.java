package com.sinosoft.schema;

import com.sinosoft.schema.ActivityLaunchSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ActivityLaunchSet extends SchemaSet {
	public ActivityLaunchSet() {
		this(10,0);
	}

	public ActivityLaunchSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ActivityLaunchSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ActivityLaunchSchema._TableCode;
		Columns = ActivityLaunchSchema._Columns;
		NameSpace = ActivityLaunchSchema._NameSpace;
		InsertAllSQL = ActivityLaunchSchema._InsertAllSQL;
		UpdateAllSQL = ActivityLaunchSchema._UpdateAllSQL;
		FillAllSQL = ActivityLaunchSchema._FillAllSQL;
		DeleteSQL = ActivityLaunchSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ActivityLaunchSet();
	}

	public boolean add(ActivityLaunchSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ActivityLaunchSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ActivityLaunchSchema aSchema) {
		return super.remove(aSchema);
	}

	public ActivityLaunchSchema get(int index) {
		ActivityLaunchSchema tSchema = (ActivityLaunchSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ActivityLaunchSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ActivityLaunchSet aSet) {
		return super.set(aSet);
	}
}
 
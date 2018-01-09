package com.sinosoft.schema;

import com.sinosoft.schema.ActivityInfoWXSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ActivityInfoWXSet extends SchemaSet {
	public ActivityInfoWXSet() {
		this(10,0);
	}

	public ActivityInfoWXSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ActivityInfoWXSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ActivityInfoWXSchema._TableCode;
		Columns = ActivityInfoWXSchema._Columns;
		NameSpace = ActivityInfoWXSchema._NameSpace;
		InsertAllSQL = ActivityInfoWXSchema._InsertAllSQL;
		UpdateAllSQL = ActivityInfoWXSchema._UpdateAllSQL;
		FillAllSQL = ActivityInfoWXSchema._FillAllSQL;
		DeleteSQL = ActivityInfoWXSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ActivityInfoWXSet();
	}

	public boolean add(ActivityInfoWXSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ActivityInfoWXSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ActivityInfoWXSchema aSchema) {
		return super.remove(aSchema);
	}

	public ActivityInfoWXSchema get(int index) {
		ActivityInfoWXSchema tSchema = (ActivityInfoWXSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ActivityInfoWXSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ActivityInfoWXSet aSet) {
		return super.set(aSet);
	}
}
 
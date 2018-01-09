package com.sinosoft.schema;

import com.sinosoft.schema.ActivityproductSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ActivityproductSet extends SchemaSet {
	public ActivityproductSet() {
		this(10,0);
	}

	public ActivityproductSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ActivityproductSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ActivityproductSchema._TableCode;
		Columns = ActivityproductSchema._Columns;
		NameSpace = ActivityproductSchema._NameSpace;
		InsertAllSQL = ActivityproductSchema._InsertAllSQL;
		UpdateAllSQL = ActivityproductSchema._UpdateAllSQL;
		FillAllSQL = ActivityproductSchema._FillAllSQL;
		DeleteSQL = ActivityproductSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ActivityproductSet();
	}

	public boolean add(ActivityproductSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ActivityproductSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ActivityproductSchema aSchema) {
		return super.remove(aSchema);
	}

	public ActivityproductSchema get(int index) {
		ActivityproductSchema tSchema = (ActivityproductSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ActivityproductSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ActivityproductSet aSet) {
		return super.set(aSet);
	}
}
 
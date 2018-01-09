package com.sinosoft.schema;

import com.sinosoft.schema.ActivitySendCouponLogSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class ActivitySendCouponLogSet extends SchemaSet {
	public ActivitySendCouponLogSet() {
		this(10,0);
	}

	public ActivitySendCouponLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public ActivitySendCouponLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = ActivitySendCouponLogSchema._TableCode;
		Columns = ActivitySendCouponLogSchema._Columns;
		NameSpace = ActivitySendCouponLogSchema._NameSpace;
		InsertAllSQL = ActivitySendCouponLogSchema._InsertAllSQL;
		UpdateAllSQL = ActivitySendCouponLogSchema._UpdateAllSQL;
		FillAllSQL = ActivitySendCouponLogSchema._FillAllSQL;
		DeleteSQL = ActivitySendCouponLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new ActivitySendCouponLogSet();
	}

	public boolean add(ActivitySendCouponLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(ActivitySendCouponLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(ActivitySendCouponLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public ActivitySendCouponLogSchema get(int index) {
		ActivitySendCouponLogSchema tSchema = (ActivitySendCouponLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, ActivitySendCouponLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(ActivitySendCouponLogSet aSet) {
		return super.set(aSet);
	}
}
 
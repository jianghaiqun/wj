package com.sinosoft.schema;

import com.sinosoft.framework.orm.SchemaSet;

public class SDCouponInfoSet extends SchemaSet {
	public SDCouponInfoSet() {
		this(10,0);
	}

	public SDCouponInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDCouponInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDCouponInfoSchema._TableCode;
		Columns = SDCouponInfoSchema._Columns;
		NameSpace = SDCouponInfoSchema._NameSpace;
		InsertAllSQL = SDCouponInfoSchema._InsertAllSQL;
		UpdateAllSQL = SDCouponInfoSchema._UpdateAllSQL;
		FillAllSQL = SDCouponInfoSchema._FillAllSQL;
		DeleteSQL = SDCouponInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDCouponInfoSet();
	}

	public boolean add(SDCouponInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDCouponInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDCouponInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDCouponInfoSchema get(int index) {
		SDCouponInfoSchema tSchema = (SDCouponInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDCouponInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDCouponInfoSet aSet) {
		return super.set(aSet);
	}
	
}
 
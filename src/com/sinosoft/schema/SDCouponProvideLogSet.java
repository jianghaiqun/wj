package com.sinosoft.schema;

import com.sinosoft.framework.orm.SchemaSet;

public class SDCouponProvideLogSet extends SchemaSet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1226459692554961740L;

	public SDCouponProvideLogSet() {
		this(10,0);
	}

	public SDCouponProvideLogSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public SDCouponProvideLogSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = SDCouponProvideLogSchema._TableCode;
		Columns = SDCouponProvideLogSchema._Columns;
		NameSpace = SDCouponProvideLogSchema._NameSpace;
		InsertAllSQL = SDCouponProvideLogSchema._InsertAllSQL;
		UpdateAllSQL = SDCouponProvideLogSchema._UpdateAllSQL;
		FillAllSQL = SDCouponProvideLogSchema._FillAllSQL;
		DeleteSQL = SDCouponProvideLogSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new SDCouponProvideLogSet();
	}

	public boolean add(SDCouponProvideLogSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(SDCouponProvideLogSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(SDCouponProvideLogSchema aSchema) {
		return super.remove(aSchema);
	}

	public SDCouponProvideLogSchema get(int index) {
		SDCouponProvideLogSchema tSchema = (SDCouponProvideLogSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, SDCouponProvideLogSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(SDCouponProvideLogSet aSet) {
		return super.set(aSet);
	}
}
 
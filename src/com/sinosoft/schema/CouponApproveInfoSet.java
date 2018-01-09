package com.sinosoft.schema;

import com.sinosoft.schema.CouponApproveInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class CouponApproveInfoSet extends SchemaSet {
	public CouponApproveInfoSet() {
		this(10,0);
	}

	public CouponApproveInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public CouponApproveInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = CouponApproveInfoSchema._TableCode;
		Columns = CouponApproveInfoSchema._Columns;
		NameSpace = CouponApproveInfoSchema._NameSpace;
		InsertAllSQL = CouponApproveInfoSchema._InsertAllSQL;
		UpdateAllSQL = CouponApproveInfoSchema._UpdateAllSQL;
		FillAllSQL = CouponApproveInfoSchema._FillAllSQL;
		DeleteSQL = CouponApproveInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new CouponApproveInfoSet();
	}

	public boolean add(CouponApproveInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(CouponApproveInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(CouponApproveInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public CouponApproveInfoSchema get(int index) {
		CouponApproveInfoSchema tSchema = (CouponApproveInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, CouponApproveInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(CouponApproveInfoSet aSet) {
		return super.set(aSet);
	}
}

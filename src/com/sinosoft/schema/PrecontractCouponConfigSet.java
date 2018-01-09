package com.sinosoft.schema;

import com.sinosoft.schema.PrecontractCouponConfigSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PrecontractCouponConfigSet extends SchemaSet {
	public PrecontractCouponConfigSet() {
		this(10,0);
	}

	public PrecontractCouponConfigSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PrecontractCouponConfigSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PrecontractCouponConfigSchema._TableCode;
		Columns = PrecontractCouponConfigSchema._Columns;
		NameSpace = PrecontractCouponConfigSchema._NameSpace;
		InsertAllSQL = PrecontractCouponConfigSchema._InsertAllSQL;
		UpdateAllSQL = PrecontractCouponConfigSchema._UpdateAllSQL;
		FillAllSQL = PrecontractCouponConfigSchema._FillAllSQL;
		DeleteSQL = PrecontractCouponConfigSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PrecontractCouponConfigSet();
	}

	public boolean add(PrecontractCouponConfigSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PrecontractCouponConfigSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PrecontractCouponConfigSchema aSchema) {
		return super.remove(aSchema);
	}

	public PrecontractCouponConfigSchema get(int index) {
		PrecontractCouponConfigSchema tSchema = (PrecontractCouponConfigSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PrecontractCouponConfigSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PrecontractCouponConfigSet aSet) {
		return super.set(aSet);
	}
}
 
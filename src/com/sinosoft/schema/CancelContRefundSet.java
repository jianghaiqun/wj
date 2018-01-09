package com.sinosoft.schema;

import com.sinosoft.framework.orm.SchemaSet;

public class CancelContRefundSet extends SchemaSet {
	public CancelContRefundSet() {
		this(10,0);
	}

	public CancelContRefundSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public CancelContRefundSet(int initialCapacity, int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = CancelContRefundSchema._TableCode;
		Columns = CancelContRefundSchema._Columns;
		NameSpace = CancelContRefundSchema._NameSpace;
		InsertAllSQL = CancelContRefundSchema._InsertAllSQL;
		UpdateAllSQL = CancelContRefundSchema._UpdateAllSQL;
		FillAllSQL = CancelContRefundSchema._FillAllSQL;
		DeleteSQL = CancelContRefundSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new CancelContRefundSet();
	}

	public boolean add(CancelContRefundSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(CancelContRefundSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(CancelContRefundSchema aSchema) {
		return super.remove(aSchema);
	}

	public CancelContRefundSchema get(int index) {
		CancelContRefundSchema tSchema = (CancelContRefundSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, CancelContRefundSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(CancelContRefundSet aSet) {
		return super.set(aSet);
	}
}
 
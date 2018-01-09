package com.sinosoft.schema;

import com.sinosoft.schema.PaymemntDetailInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PaymemntDetailInfoSet extends SchemaSet {
	public PaymemntDetailInfoSet() {
		this(10,0);
	}

	public PaymemntDetailInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PaymemntDetailInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PaymemntDetailInfoSchema._TableCode;
		Columns = PaymemntDetailInfoSchema._Columns;
		NameSpace = PaymemntDetailInfoSchema._NameSpace;
		InsertAllSQL = PaymemntDetailInfoSchema._InsertAllSQL;
		UpdateAllSQL = PaymemntDetailInfoSchema._UpdateAllSQL;
		FillAllSQL = PaymemntDetailInfoSchema._FillAllSQL;
		DeleteSQL = PaymemntDetailInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PaymemntDetailInfoSet();
	}

	public boolean add(PaymemntDetailInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PaymemntDetailInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PaymemntDetailInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PaymemntDetailInfoSchema get(int index) {
		PaymemntDetailInfoSchema tSchema = (PaymemntDetailInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PaymemntDetailInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PaymemntDetailInfoSet aSet) {
		return super.set(aSet);
	}
}
 
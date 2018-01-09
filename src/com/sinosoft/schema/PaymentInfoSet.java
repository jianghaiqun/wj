package com.sinosoft.schema;

import com.sinosoft.schema.PaymentInfoSchema;
import com.sinosoft.framework.orm.SchemaSet;

public class PaymentInfoSet extends SchemaSet {
	public PaymentInfoSet() {
		this(10,0);
	}

	public PaymentInfoSet(int initialCapacity) {
		this(initialCapacity,0);
	}

	public PaymentInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PaymentInfoSchema._TableCode;
		Columns = PaymentInfoSchema._Columns;
		NameSpace = PaymentInfoSchema._NameSpace;
		InsertAllSQL = PaymentInfoSchema._InsertAllSQL;
		UpdateAllSQL = PaymentInfoSchema._UpdateAllSQL;
		FillAllSQL = PaymentInfoSchema._FillAllSQL;
		DeleteSQL = PaymentInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PaymentInfoSet();
	}

	public boolean add(PaymentInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PaymentInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PaymentInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PaymentInfoSchema get(int index) {
		PaymentInfoSchema tSchema = (PaymentInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PaymentInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PaymentInfoSet aSet) {
		return super.set(aSet);
	}
}
 
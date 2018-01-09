package com.sinosoft.schema;

import com.sinosoft.framework.orm.SchemaSet;

/**
 * b2b工程同样用到这个表PaymentClaimsInfo，生成时候要通知b2b工程人员
 */
public class PaymentClaimsInfoSet extends SchemaSet {
	public PaymentClaimsInfoSet() {
		this(10,0);
	}

	public PaymentClaimsInfoSet(int initialCapacity) {
		this(initialCapacity, 0);
	}

	public PaymentClaimsInfoSet(int initialCapacity,int capacityIncrement) {
		super(initialCapacity,capacityIncrement);
		TableCode = PaymentClaimsInfoSchema._TableCode;
		Columns = PaymentClaimsInfoSchema._Columns;
		NameSpace = PaymentClaimsInfoSchema._NameSpace;
		InsertAllSQL = PaymentClaimsInfoSchema._InsertAllSQL;
		UpdateAllSQL = PaymentClaimsInfoSchema._UpdateAllSQL;
		FillAllSQL = PaymentClaimsInfoSchema._FillAllSQL;
		DeleteSQL = PaymentClaimsInfoSchema._DeleteSQL;
	}

	protected SchemaSet newInstance(){
		return new PaymentClaimsInfoSet();
	}

	public boolean add(PaymentClaimsInfoSchema aSchema) {
		return super.add(aSchema);
	}

	public boolean add(PaymentClaimsInfoSet aSet) {
		return super.add(aSet);
	}

	public boolean remove(PaymentClaimsInfoSchema aSchema) {
		return super.remove(aSchema);
	}

	public PaymentClaimsInfoSchema get(int index) {
		PaymentClaimsInfoSchema tSchema = (PaymentClaimsInfoSchema) super.getObject(index);
		return tSchema;
	}

	public boolean set(int index, PaymentClaimsInfoSchema aSchema) {
		return super.set(index, aSchema);
	}

	public boolean set(PaymentClaimsInfoSet aSet) {
		return super.set(aSet);
	}
}
 